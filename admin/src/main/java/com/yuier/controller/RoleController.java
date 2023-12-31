package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.role.AddRoleDto;
import com.yuier.domain.dto.role.AdminGetRoleListDto;
import com.yuier.domain.dto.role.ChangeRoleStatusDto;
import com.yuier.domain.dto.role.UpdateRoleDto;
import com.yuier.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/12 20:58
 **/

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @SystemLog(businessName = "获取角色列表")
    @PreAuthorize("@ps.hasPermission('system:role:list')")
    public ResponseResult adminGetRoleList(Integer pageNum, Integer pageSize, AdminGetRoleListDto adminGetRoleListDto) {
        return roleService.adminGetRoleList(pageNum, pageSize, adminGetRoleListDto);
    }

    @PutMapping("/changeStatus")
    @SystemLog(businessName = "改变角色状态")
    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto) {
        return roleService.changeStatus(changeRoleStatusDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增角色")
    @PreAuthorize("@ps.hasPermission('system:role:add')")
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto) {
        return roleService.addRole(addRoleDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "角色信息回显")
    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    public ResponseResult roleDetail(@PathVariable("id") Long id) {
        return roleService.roleDetail(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改角色")
    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    public ResponseResult updateRole(@RequestBody UpdateRoleDto updateRoleDto) {
        return roleService.updateRole(updateRoleDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除角色列表")
    @PreAuthorize("@ps.hasPermission('system:role:remove')")
    public ResponseResult deleteRole(@PathVariable("id") List<Long> id) {
        return roleService.deleteRole(id);
    }

    @GetMapping("/listAllRole")
    @SystemLog(businessName = "新增角色时先查询所有有效角色")
    @PreAuthorize("@ps.hasPermission('system:role:add')")
    public ResponseResult addUserRoleList() {
        return roleService.addUserRoleList();
    }

}
