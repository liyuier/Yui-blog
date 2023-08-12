package com.yuier.domain.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/12 21:15
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRoleStatusDto {

    private Long roleId;

    // 角色状态（0正常 1停用）
    private String status;
}
