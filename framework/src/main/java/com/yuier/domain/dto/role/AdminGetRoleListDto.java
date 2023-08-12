package com.yuier.domain.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/12 21:01
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminGetRoleListDto {

    // 角色名称
    private String roleName;

    // 角色状态（0正常 1停用）
    private String status;
}
