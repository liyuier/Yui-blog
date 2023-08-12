package com.yuier.domain.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文章表(Article)表实体类
 *
 * @author Yui
 * @since 2023-07-30 22:50:57
 */
@SuppressWarnings("serial")
@Data  // lombok 自动生成 Getters 和 Setters
@NoArgsConstructor  // 自动生成无参构造方法
@AllArgsConstructor  // 自动生成全参数构造方法
@Accessors(chain = true)  // 所有 set 方法返回值都为类本身，便于链式操作
@TableName("yui_article")
public class Article {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;
    //所属分类名称
    @TableField(exist = false)
    private String categoryName;
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

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}

