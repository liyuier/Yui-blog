package com.yuier.domain.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author Yui
 * @since 2023-08-11 21:36:53
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("yui_article_tag")
@Accessors(chain = true)
public class ArticleTag {
    
    // 文章id
    private Long articleId;
    
    // 标签id
    private Long tagId;
    
}

