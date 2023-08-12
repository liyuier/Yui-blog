package com.yuier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.comment.AddCommentDto;
import com.yuier.domain.entity.Comment;

/**
 * 评论表(Comment)表服务接口
 *
 * @author Yui
 * @since 2023-08-06 17:19:48
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(AddCommentDto comment);
}

