package com.yuier.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.constants.SystemConstants;
import com.yuier.mapper.RoleMapper;
import com.yuier.domain.entity.Role;
import com.yuier.service.RoleService;
import com.yuier.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Yui
 * @since 2023-08-11 00:29:07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    // 根据用户 id 查询 roleKey 列表
    @Override
    public List<String> selectRoleKeysByUserId(Long id) {

        List<String> roleKeyList = new ArrayList<>();

        if (id.equals(SystemConstants.SUPER_ADMIN_ID)) {
            roleKeyList.add(SystemConstants.ADMIN_ROLE_KEY);
        } else {
            roleKeyList = getBaseMapper().selectRoleKeysByUserId(id);
        }
        return roleKeyList;
    }
}

