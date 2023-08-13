package com.yuier.domain.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类表(Category)表实体类
 *
 * @author Yui
 * @since 2023-08-04 11:53:52
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("yui_category")
public class Category {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    //分类名
    private String name;

    //父分类id，如果没有父分类为-1
    private Long pid;

    //描述
    private String description;

    //状态0:正常,1禁用
    private String status;

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

