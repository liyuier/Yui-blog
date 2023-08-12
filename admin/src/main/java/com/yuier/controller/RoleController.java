package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.role.AddRoleDto;
import com.yuier.domain.dto.role.AdminGetRoleListDto;
import com.yuier.domain.dto.role.ChangeRoleStatusDto;
import com.yuier.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult adminGetRoleList(Integer pageNum, Integer pageSize, AdminGetRoleListDto adminGetRoleListDto) {
        return roleService.adminGetRoleList(pageNum, pageSize, adminGetRoleListDto);
    }

    @PutMapping("/changeStatus")
    @SystemLog(businessName = "改变角色状态")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto) {
        return roleService.changeStatus(changeRoleStatusDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增角色")
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto) {
        return roleService.addRole(addRoleDto);
    }

}
