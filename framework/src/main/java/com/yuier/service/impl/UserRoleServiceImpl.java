package com.yuier.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.mapper.UserRoleMapper;
import com.yuier.domain.entity.UserRole;
import com.yuier.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author Yui
 * @since 2023-08-11 00:29:42
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

