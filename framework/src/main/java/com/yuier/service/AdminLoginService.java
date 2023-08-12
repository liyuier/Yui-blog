package com.yuier.service;

import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.UserLoginDto;
import com.yuier.domain.vo.admin.AdminUserInfoVo;
import com.yuier.domain.vo.admin.RoutersVo;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 22:04
 **/
public interface AdminLoginService {
    ResponseResult adminLogin(UserLoginDto admin);

    ResponseResult<AdminUserInfoVo> getAdminInfo();

    ResponseResult<RoutersVo> getMenuRouters();

    ResponseResult adminLogout();
}
