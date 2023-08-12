package com.yuier.domain.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/12 13:45
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleDto {

    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;

    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;
    // tag id 列表
    private List<Long> tags;

}

