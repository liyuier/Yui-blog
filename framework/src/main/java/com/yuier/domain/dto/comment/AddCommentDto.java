package com.yuier.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 17:16
 **/

@Data  // lombok 自动生成 Getters 和 Setters
@NoArgsConstructor  // 自动生成无参构造方法
@AllArgsConstructor  // 自动生成全参数构造方法
public class AddCommentDto {

    //评论类型（0代表文章评论，1代表友链评论）
    private String type;

    //文章id
    private Long articleId;

    //根评论id
    private Long rootId;

    //评论内容
    private String content;

    //所回复的目标评论的userid
    private Long toCommentUserId;

    //回复目标评论id
    private Long toCommentId;

}
