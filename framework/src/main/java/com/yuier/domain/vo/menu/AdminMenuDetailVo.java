package com.yuier.domain.vo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/12 20:10
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMenuDetailVo {

    private Long id;

    // 菜单名称
    private String menuName;

    // 父菜单ID
    private Long parentId;

    // 显示顺序
    private Integer orderNum;

    // 路由地址
    private String path;

    // 菜单类型（M目录 C菜单 F按钮）
    private String menuType;

    // 菜单状态（0显示 1隐藏）
    private String visible;

    // 菜单状态（0正常 1停用）
    private String status;

    // 菜单图标
    private String icon;

    // 备注
    private String remark;
}
