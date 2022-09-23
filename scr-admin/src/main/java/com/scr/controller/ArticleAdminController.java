package com.scr.controller;

import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.article.ArticleListDto;
import com.scr.domain.dto.article.EditArticleDto;
import com.scr.domain.dto.tag.AddTagDto;
import com.scr.domain.dto.tag.EditTagDto;
import com.scr.domain.entity.Article;
import com.scr.domain.entity.Tag;
import com.scr.domain.vo.PageVo;
import com.scr.service.ArticleService;
import com.scr.utils.BeanCopyUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 沈才人
 * @Date 2022 09 03 15 38
 **/


@RestController
@RequestMapping("/content/article")
public class ArticleAdminController {

    @Autowired
    private ArticleService articleService;

    /**
     * 发表文章
     * @param article
     * @return
     */
    @PostMapping()
    @SystemLog(businessName = "后台发表文章")
    @ApiOperation(value = "后台发表文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "artivcle",value = "文章id"),
    }
    )
    public ResponseResult publishArticle(@RequestBody Article article){
        return articleService.publishArticle(article);
    }

    /**
     * 获取所有文章信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "获取所有文章信息")
    @ApiOperation(value = "获取所有文章信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleListDto",value = "文章详情切片"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult<PageVo> getAllArticlelist(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){
        return articleService.pageArticleList(pageNum,pageSize,articleListDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @SystemLog(businessName = "后台删除文章")
    @ApiOperation(value = "后台删除文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id")
    }
    )
    public ResponseResult delete(@PathVariable Long id){
        articleService.removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 修改
     * @param articleDto
     * @return
     */
    @PutMapping
    @SystemLog(businessName = "后台修改文章")
    @ApiOperation(value = "后台修改文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleDto",value = "修改文章切片")
    }
    )
    public ResponseResult edit(@RequestBody EditArticleDto articleDto){
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        articleService.updateById(article);
        return ResponseResult.okResult();
    }


    /**
     * 获取相关信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @SystemLog(businessName = "获取文章相关信息")
    @ApiOperation(value = "获取文章相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "文章id"),
    }
    )
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Article article = articleService.getById(id);
        return ResponseResult.okResult(article);
    }
}
