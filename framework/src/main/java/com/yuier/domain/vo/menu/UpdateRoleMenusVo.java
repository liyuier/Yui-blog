package com.yuier.domain.vo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 1:17
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleMenusVo {

    private List<RoleMenuVo> menus;


    private List<Long> checkedKeys;
}
