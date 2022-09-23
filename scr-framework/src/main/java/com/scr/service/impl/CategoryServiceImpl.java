package com.scr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scr.constants.SystemConstants;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.category.CategoryListDto;
import com.scr.domain.entity.Article;
import com.scr.domain.entity.Category;
import com.scr.domain.vo.CategoryVo;
import com.scr.domain.vo.CategoryVoAdmin;
import com.scr.domain.vo.PageVo;
import com.scr.mapper.CategoryMapper;
import com.scr.service.ArticleService;
import com.scr.service.CategoryService;
import com.scr.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-08-27 12:29:52
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取该分类下的文章
     * @return
     */
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }


    /**
     * 获取所有的分类信息
     * @return
     */
    @Override
    public ResponseResult getAllcategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SystemConstants.CATEGORY_STATUS_NORMAL);
        queryWrapper.eq(Category::getDelFlag,SystemConstants.ISDELETE);
        List<Category> categoryList = categoryService.list(queryWrapper);
        List<CategoryVoAdmin> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVoAdmin.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SystemConstants.CATEGORY_STATUS_NORMAL);
        queryWrapper.eq(Category::getDelFlag,SystemConstants.ISDELETE);
        List<Category> categoryList = categoryService.list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult<PageVo> pageCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(categoryListDto.getName()),Category::getName,categoryListDto.getName());
        queryWrapper.eq(StringUtils.hasText(categoryListDto.getDescription()),Category::getDescription,categoryListDto.getDescription());
        queryWrapper.eq(StringUtils.hasText(categoryListDto.getStatus()),Category::getStatus,categoryListDto.getStatus());

        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());

        return ResponseResult.okResult(pageVo);

    }
}

