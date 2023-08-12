package com.yuier.domain.vo.menu;

import com.yuier.domain.vo.admin.MenuVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/12 21:44
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddRoleMenuVo {

    private List<AddRoleMenuVo> children;

    // 菜单ID
    private Long id;

    // 菜单名称
    private String menuName;

    // 父菜单ID
    private Long parentId;
}
