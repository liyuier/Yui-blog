package com.yuier.domain.vo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/4 21:46
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
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
