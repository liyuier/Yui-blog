package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.UserLoginDto;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.exception.SystemException;
import com.yuier.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/5 15:48
 **/

@RestController
@Api(tags = "用户登录", description = "用户登录与退出登录")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @SystemLog(businessName = "用户登录")
    @ApiImplicitParams(@ApiImplicitParam(name = "userLonginDto", value = "前端传递的用户账密自动装配为 UserLoginDto 类对象"))
    public ResponseResult login(@RequestBody UserLoginDto userLonginDto) {
        if (!StringUtils.hasText(userLonginDto.getUserName())) {
            // 提示必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(userLonginDto);
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "用户退出登录")
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }
}
