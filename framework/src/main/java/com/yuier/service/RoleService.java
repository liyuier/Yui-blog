package com.yuier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author Yui
 * @since 2023-08-11 00:29:07
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeysByUserId(Long id);
}

