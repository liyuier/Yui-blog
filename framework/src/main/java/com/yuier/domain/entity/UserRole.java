package com.yuier.domain.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author Yui
 * @since 2023-08-11 00:29:42
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_role")
public class UserRole {
    
    // 用户ID
    private Long userId;
    
    // 角色ID
    private Long roleId;
    
}

