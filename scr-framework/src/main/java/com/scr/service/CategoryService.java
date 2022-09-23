package com.scr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.category.CategoryListDto;
import com.scr.domain.entity.Category;
import com.scr.domain.vo.CategoryVo;
import com.scr.domain.vo.PageVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-08-28 12:29:50
 */
public interface CategoryService extends IService<Category> {


    ResponseResult getCategoryList();

    ResponseResult getAllcategory();

    List<CategoryVo> listAllCategory();

    ResponseResult<PageVo> pageCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);
}

