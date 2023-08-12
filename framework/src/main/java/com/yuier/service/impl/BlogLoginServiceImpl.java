package com.yuier.service.impl;

import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.UserLoginDto;
import com.yuier.domain.entity.LoginUser;
import com.yuier.domain.vo.user.UserInfoVo;
import com.yuier.domain.vo.user.UserLoginVo;
import com.yuier.service.BlogLoginService;
import com.yuier.utils.BeanCopyUtils;
import com.yuier.utils.JwtUtil;
import com.yuier.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/5 15:56
 **/

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(UserLoginDto user) {
        // 实现用户校验
        // UsernamePasswordAuthenticationToken 是一个身份验证类型
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 获取用户 ID ，生成 jwt ，存入 Redis 中
        // 判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取 userid ，生成 token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();  // 获取认证主体，强转为 LoginUser
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入 Redis
        redisCache.setCacheObject(SystemConstants.REDIS_KEYS.BLOG_USER + userId, loginUser);
        // 把 toke 和 userinfo 封装并返回
        // 把 User 拷贝封装为 UserInfo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo userLoginVo = new UserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(userLoginVo);
    }

    @Override
    public ResponseResult logout() {
        // 获取 token ，解析并获取 userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        // 删除 redis 中的用户信息
        redisCache.deleteObject(SystemConstants.REDIS_KEYS.BLOG_USER + userId);
        return ResponseResult.okResult();
    }
}
