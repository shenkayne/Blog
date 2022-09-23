package com.scr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.article.ArticleListDto;
import com.scr.domain.entity.Article;
import com.scr.domain.vo.PageVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult publishArticle(Article article);

    ResponseResult getAllArticlelist(Integer pageNum, Integer pageSize);

    ResponseResult<PageVo> pageArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

}
