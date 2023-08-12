package com.yuier.domain.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/6 20:38
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentVo {
    private Long id;
    // 发表评论的用户名
    private String username;
    // 发表评论的用户头像
    private String avatar;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //所回复的目标评论的 userName
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;

    // 评论的用户 ID
    private Long createBy;

    private Date createTime;

    private List<CommentVo> children;

}
