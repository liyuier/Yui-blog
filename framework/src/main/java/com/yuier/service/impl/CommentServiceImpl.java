package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.comment.AddCommentDto;
import com.yuier.domain.vo.comment.CommentVo;
import com.yuier.domain.vo.page.PageVo;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.exception.SystemException;
import com.yuier.mapper.CommentMapper;
import com.yuier.domain.entity.Comment;
import com.yuier.service.CommentService;
import com.yuier.service.UserService;
import com.yuier.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author Yui
 * @since 2023-08-06 17:19:48
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    // 查询文章或友链评论
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 只有 commentType 为文章评论类型时才查询对应文章 ID 的评论
        queryWrapper.eq(
                SystemConstants.ARTICLE_COMMENT.equals(commentType),
                Comment::getArticleId,
                articleId
        );
        // 查询传入类型的评论
        queryWrapper.eq(Comment::getType, commentType);
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_COMMENT);
        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        // 封装
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        PageVo pageVo = new PageVo(commentVoList, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    // 插入评论
    @Override
    public ResponseResult addComment(AddCommentDto addCommentDto) {
        // 判断评论内容不为空
        if (!StringUtils.hasText(addCommentDto.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NULL);
        }
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        save(comment);
        return ResponseResult.okResult();
    }

    // 封装方法，将原始 comment 集合转为 commentVo 集合
    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        commentVoList.forEach(
            (commentVo) -> {
                commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName());
                commentVo.setAvatar(userService.getById(commentVo.getCreateBy()).getAvatar());
                if (commentVo.getToCommentId() != -1) {  // 如果 toCommentId 不为 -1 ，该评论是一条回复评论
                    commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName());
                } else {  // 该评论是根评论，查询该评论的子评论
                    List<Comment> children = getChildren(commentVo.getId());
                    commentVo.setChildren(toCommentVoList(children));
                }
            }
        );
        return commentVoList;
    }

    // 根据根评论 id 查询子评论
    private List<Comment> getChildren(Long commentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, commentId);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        return list(queryWrapper);  // 这里的 list() 方法就是 CommentService 接口里的 list() 方法
    }
}

