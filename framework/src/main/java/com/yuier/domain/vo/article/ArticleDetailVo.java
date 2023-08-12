package com.yuier.domain.vo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/4 23:07
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //文章内容
    private String content;
    //所属分类id
    private Long categoryId;
    //所属分类名称
    private String categoryName;
    //缩略图
    private String thumbnail;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    private Date createTime;
}
