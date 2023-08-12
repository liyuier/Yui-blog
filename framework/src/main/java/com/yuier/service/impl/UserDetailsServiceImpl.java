package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuier.domain.entity.LoginUser;
import com.yuier.domain.entity.User;
import com.yuier.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/5 17:07
 **/

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        // 如果没查到用户，抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名不存在");
        }
        // 返回用户
        // TODO 查询权限信息并封装
        return new LoginUser(user);
    }
}
