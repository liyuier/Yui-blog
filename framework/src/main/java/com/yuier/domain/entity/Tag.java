package com.yuier.domain.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签(Tag)表实体类
 *
 * @author Yui
 * @since 2023-08-10 21:06:34
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("yui_tag")
public class Tag {
    
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    
    // 标签名
    private String name;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    
    // 删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
    
    // 备注
    private String remark;
    
}

