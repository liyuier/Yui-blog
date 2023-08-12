package com.yuier.domain.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/12 19:42
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuListDto {

    // 菜单名称
    private String menuName;

    // 菜单状态（0正常 1停用）
    private String status;
}
