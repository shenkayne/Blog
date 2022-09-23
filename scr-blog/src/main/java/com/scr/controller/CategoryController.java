package com.scr.controller;


import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.service.CategoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "获取分类信息")
    @ApiOperation(value = "获取分类信息")
    public ResponseResult getCategoryList(){
       return categoryService.getCategoryList();
    }
}
