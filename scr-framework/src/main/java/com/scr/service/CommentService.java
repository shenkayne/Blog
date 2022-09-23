package com.scr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scr.domain.ResponseResult;
import com.scr.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-08-28 23:49:35
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

