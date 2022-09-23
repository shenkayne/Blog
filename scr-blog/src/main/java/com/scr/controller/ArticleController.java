package com.scr.controller;


import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.service.ArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "获取热门文章信息")
    @ApiOperation(value = "获取热门文章信息")
    public ResponseResult hotArticleList(){

        ResponseResult result =  articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "获取文章信息")
    @ApiOperation(value = "获取文章信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId",value = "分类id"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @PutMapping("/updateViewCount/{id}")
    @SystemLog(businessName = "更新文章信息")
    @ApiOperation(value = "更新文章信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id"),
    }
    )
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取文章详情信息")
    @ApiOperation(value = "获取文章详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id"),
    }
    )
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
}
