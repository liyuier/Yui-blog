package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.UpdateUserInfoDto;
import com.yuier.domain.dto.user.UserRegisterDto;
import com.yuier.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/7 15:17
 **/

@RestController
@RequestMapping("/user")
@Api(tags = "用户", description = "用户个人信息相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "查询个人信息", notes = "获取当前登录用户的个人信息")
    @SystemLog(businessName = "查询个人信息")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @ApiOperation(value = "更新个人信息", notes = "更新当前登录用户的个人信息")
    @SystemLog(businessName = "更新个人信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "updateUserInfoDto", value = "前端传递的用户信息自动装配为 UpdateUserInfoDto 类对象"))
    public ResponseResult updateUserInfo(@RequestBody UpdateUserInfoDto updateUserInfoDto) {
        return userService.updateUserInfo(updateUserInfoDto);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "用户注册")
    @ApiOperation(value = "用户注册", notes = "注册一个新用户")
    @ApiImplicitParams(@ApiImplicitParam(name = "userRegisterDto", value = "前端传递的用户信息自动装配为 userRegisterDto 类对象"))
    public ResponseResult register(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.register(userRegisterDto);
    }
}
