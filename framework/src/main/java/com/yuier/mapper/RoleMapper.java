package com.yuier.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuier.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author Yui
 * @since 2023-08-11 00:29:06
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeysByUserId(Long id);
}

