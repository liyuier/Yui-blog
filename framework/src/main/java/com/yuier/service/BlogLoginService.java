package com.yuier.service;

import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.UserLoginDto;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/5 15:54
 **/
public interface BlogLoginService {
    ResponseResult login(UserLoginDto user);

    ResponseResult logout();
}
