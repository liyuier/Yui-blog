package com.yuier.domain.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author Yui
 * @since 2023-08-11 00:29:27
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_menu")
public class RoleMenu {
    
    // 角色ID
    private Long roleId;
    
    // 菜单ID
    private Long menuId;
    
}

