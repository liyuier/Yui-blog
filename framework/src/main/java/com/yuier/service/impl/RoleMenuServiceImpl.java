package com.yuier.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.mapper.RoleMenuMapper;
import com.yuier.domain.entity.RoleMenu;
import com.yuier.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author Yui
 * @since 2023-08-11 00:29:27
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

