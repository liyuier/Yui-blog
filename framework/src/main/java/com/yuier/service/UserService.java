package com.yuier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.UpdateUserInfoDto;
import com.yuier.domain.dto.user.UserRegisterDto;
import com.yuier.domain.entity.User;

/**
 * 用户表(User)表服务接口
 *
 * @author Yui
 * @since 2023-08-06 21:23:07
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(UpdateUserInfoDto user);

    ResponseResult register(UserRegisterDto user);
}

