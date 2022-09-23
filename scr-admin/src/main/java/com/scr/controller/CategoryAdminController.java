package com.scr.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.article.ArticleListDto;
import com.scr.domain.dto.article.EditArticleDto;
import com.scr.domain.dto.category.AddCategoryDto;
import com.scr.domain.dto.category.CategoryListDto;
import com.scr.domain.dto.category.EditCategoryDto;
import com.scr.domain.dto.tag.AddTagDto;
import com.scr.domain.entity.Article;
import com.scr.domain.entity.Category;
import com.scr.domain.entity.Tag;
import com.scr.domain.vo.CategoryVo;
import com.scr.domain.vo.ExcelCategoryVo;
import com.scr.domain.vo.PageVo;
import com.scr.enums.AppHttpCodeEnum;
import com.scr.service.CategoryService;
import com.scr.utils.BeanCopyUtils;
import com.scr.utils.WebUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author 沈才人
 * @Date 2022 09 03 14 49
 **/

@RestController
@RequestMapping("/content/category")
public class CategoryAdminController {

    @Autowired
    CategoryService categoryService;

//    @GetMapping("/list")
//    @SystemLog(businessName = "查询所有分类信息")
//    public ResponseResult list() {
//        return ResponseResult.okResult(categoryService.list());
//    }
    /**
     * 获取所有分类列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "获取所有分类列表")
    @ApiOperation(value = "获取所有分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryListDto",value = "分类列表切片"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult<PageVo> getAllCategorylist(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto){
        return categoryService.pageCategoryList(pageNum,pageSize,categoryListDto);
    }

    /**
     * 添加
     * @param categoryDto
     * @return
     */
    @PostMapping
    @SystemLog(businessName = "后台添加分类信息")
    @ApiOperation(value = "后台添加分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryDto",value = "添加分类切片")
    }
    )
    public ResponseResult add(@RequestBody AddCategoryDto categoryDto){
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @SystemLog(businessName = "后台删除分类信息")
    @ApiOperation(value = "后台删除分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "分类id")
    }
    )
    public ResponseResult delete(@PathVariable Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 修改
     * @param categoryDto
     * @return
     */
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @PutMapping
    @SystemLog(businessName = "后台修改分类信息")
    @ApiOperation(value = "获取评论信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryDto",value = "修改分类切片"),
    }
    )
    public ResponseResult edit(@RequestBody EditCategoryDto categoryDto){
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    /**
     * 获取相关信息
     * @param id
     * @return
     */
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping(value = "/{id}")
    @SystemLog(businessName = "获取分类相关信息")
    @ApiOperation(value = "获取分类相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "分类id"),
    }
    )
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }


    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    @SystemLog(businessName = "导出分类信息")
    @ApiOperation(value = "导出分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "response",value = "响应报文")
    }
    )
    public void export(HttpServletResponse response) {
        //设置下载文件的请求头
        try {
            WebUtils.setDownLoadHeaderExcel("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos=categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos= BeanCopyUtils.copyBeanList(categoryVos,ExcelCategoryVo.class);

            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {

            //出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
            e.printStackTrace();
        }
    }

    @GetMapping("/listAllCategory")
    @SystemLog(businessName = "写文章页面的分类菜单项")
    @ApiOperation(value = "写文章页面的分类菜单项")
    public ResponseResult listAllCategory() {
        return categoryService.getAllcategory();
    }


}
