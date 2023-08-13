package com.yuier.domain.vo.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 0:35
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleListVo {

    private Long id;

    // 角色名称
    private String roleName;

    // 角色权限字符串
    private String roleKey;

    // 显示顺序
    private Integer roleSort;

    // 角色状态（0正常 1停用）
    private String status;
}
