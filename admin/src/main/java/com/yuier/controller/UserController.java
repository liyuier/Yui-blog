package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.AdminAddUserDto;
import com.yuier.domain.dto.user.AdminUpdateUserDto;
import com.yuier.domain.dto.user.AdminUserListDto;
import com.yuier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 10:11
 **/

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:user:query')")
    @SystemLog(businessName = "后台分页查询用户列表")
    public ResponseResult adminListUser(Integer pageNum, Integer pageSize, AdminUserListDto adminUserListDto) {
        return userService.adminListUser(pageNum, pageSize, adminUserListDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增用户")
    @PreAuthorize("@ps.hasPermission('system:user:add')")
    public ResponseResult adminAddUser(@RequestBody AdminAddUserDto adminAddUserDto) {
        return userService.adminAddUser(adminAddUserDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除用户列表")
    @PreAuthorize("@ps.hasPermission('system:user:remove')")
    public ResponseResult deleteUser(@PathVariable("id") List<Long> id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "修改用户之前获取用户详情")
    @PreAuthorize("@ps.hasPermission('system:user:edit')")
    public ResponseResult adminUserDetail(@PathVariable("id") Long id) {
        return userService.adminUserDetail(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改用户信息")
    @PreAuthorize("@ps.hasPermission('system:user:edit')")
    public ResponseResult updateUser(@RequestBody AdminUpdateUserDto adminUpdateUserDto) {
        return userService.adminUpdateUser(adminUpdateUserDto);
    }

}
