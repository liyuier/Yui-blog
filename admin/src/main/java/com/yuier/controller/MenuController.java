package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.menu.AddMenuDto;
import com.yuier.domain.dto.menu.MenuListDto;
import com.yuier.domain.dto.menu.UpdateMenuDto;
import com.yuier.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/12 19:37
 **/

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @SystemLog(businessName = "获取菜单列表")
    @PreAuthorize("@ps.hasPermission('system:menu:list')")
    public ResponseResult menuList(MenuListDto menuListDto) {
        return menuService.menuList(menuListDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增菜单")
    @PreAuthorize("@ps.hasPermission('system:menu:add')")
    public ResponseResult addMenu(@RequestBody AddMenuDto addMenuDto) {
        return menuService.addMenu(addMenuDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询菜单详情")
    @PreAuthorize("@ps.hasPermission('system:menu:edit')")
    public ResponseResult adminGetMenuDetail(@PathVariable("id") Long id) {
        return menuService.adminGetMenuDetail(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改菜单")
    @PreAuthorize("@ps.hasPermission('system:menu:edit')")
    public ResponseResult updateMenu(@RequestBody UpdateMenuDto updateMenuDto) {
        return menuService.updateMenu(updateMenuDto);
    }

    @DeleteMapping("/{menuId}")
    @SystemLog(businessName = "删除菜单")
    @PreAuthorize("@ps.hasPermission('system:menu:remove')")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId) {
        return menuService.deleteMenu(menuId);
    }

    @GetMapping("/treeselect")
    @SystemLog(businessName = "后台新增角色时先获取菜单树列表")
    @PreAuthorize("@ps.hasPermission('system:role:add')")
    public ResponseResult adminAddRoleGetMenuTree() {
        return menuService.adminGetMenuTree();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    @SystemLog(businessName = "查询角色对应菜单树")
    @PreAuthorize("@ps.hasPermission('system:role:list')")
    public ResponseResult updateRoleMenuTree(@PathVariable("id") Long id) {
        return menuService.getUpdateRoleMenuTree(id);
    }

}
