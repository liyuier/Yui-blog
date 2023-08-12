package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.UserLoginDto;
import com.yuier.domain.vo.admin.AdminUserInfoVo;
import com.yuier.domain.vo.admin.RoutersVo;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.exception.SystemException;
import com.yuier.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 22:01
 **/

@RestController
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping("/admin/login")
    @SystemLog(businessName = "后台用户登录")
    public ResponseResult adminLogin(@RequestBody UserLoginDto admin) {
        if (!StringUtils.hasText(admin.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.adminLogin(admin);
    }

    @GetMapping("/getInfo")
    @SystemLog(businessName = "获取后台用户信息")
    public ResponseResult<AdminUserInfoVo> getAdminInfo() {
        return adminLoginService.getAdminInfo();
    }

    @GetMapping("/getRouters")
    @SystemLog(businessName = "获取后台用户菜单树")
    public ResponseResult<RoutersVo> getMenuRouters() {
        return adminLoginService.getMenuRouters();
    }

    @PostMapping("/admin/logout")
    @SystemLog(businessName = "后台用户退出登录")
    public ResponseResult adminLogout() {
        return adminLoginService.adminLogout();
    }

}
