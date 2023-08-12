package com.yuier.filter;

import com.alibaba.fastjson.JSON;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.entity.LoginUser;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.utils.JwtUtil;
import com.yuier.utils.RedisCache;
import com.yuier.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/6 9:07
 **/

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                                                                                    throws ServletException, IOException {
        // 获取请求头中的 token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 说明该接口不需要登录，直接放行
            filterChain.doFilter(request,response);
            return;
        }
        // 解析并获取 userid
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            /**
             * 出错原因：
             *  1. 登录超时
             *  2. token 非法
             * 响应告诉前端，要求重新登录
             */
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        // 从 redis 中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject(SystemConstants.REDIS_KEYS.BLOG_USER + userId);
        // 如果获取不到用户信息
        if (Objects.isNull(loginUser)) {
            // 说明登录过期，响应要求重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        // 如果能找到信息，存入 SecurityContextHolder
        // 这里调用三参数构造方法，标识已认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
