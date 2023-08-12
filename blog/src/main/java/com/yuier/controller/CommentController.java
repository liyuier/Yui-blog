package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.comment.AddCommentDto;
import com.yuier.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/6 20:09
 **/

@RestController
@RequestMapping("/comment")
@Api(tags = "评论", description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 查询文章评论
    @GetMapping("/commentList")
    @ApiOperation(value = "查询文章评论列表", notes = "分页查询当前文章评论列表")
    @SystemLog(businessName = "查询文章评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "当前查询的文章 id"),
            @ApiImplicitParam(name = "pageNum", value = "当前查询页数"),
            @ApiImplicitParam(name = "pageSize", value = "每页查询条数")
    })
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    // 发送评论
    @PostMapping
    @SystemLog(businessName = "发送文章或友链评论")
    @ApiOperation(value = "发送评论", notes = "发送文章或友链评论")
    @ApiImplicitParams(@ApiImplicitParam(name = "comment", value = "前端发送字段自动装配为 Comment 对象"))
    public ResponseResult addComment(@RequestBody AddCommentDto comment) {
        return commentService.addComment(comment);
    }

    // 查询友链评论
    @GetMapping("/linkCommentList")
    @SystemLog(businessName = "查询友链评论")
    @ApiOperation(value = "查询友链评论", notes = "查询友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前查询页数"),
            @ApiImplicitParam(name = "pageSize", value = "每页查询条数"),
    })
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }
}
