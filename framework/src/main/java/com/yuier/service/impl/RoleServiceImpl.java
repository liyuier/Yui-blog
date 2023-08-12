package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.role.AddRoleDto;
import com.yuier.domain.dto.role.AdminGetRoleListDto;
import com.yuier.domain.dto.role.ChangeRoleStatusDto;
import com.yuier.domain.entity.RoleMenu;
import com.yuier.domain.vo.admin.MenuVo;
import com.yuier.domain.vo.menu.AddRoleMenuVo;
import com.yuier.domain.vo.page.PageVo;
import com.yuier.mapper.RoleMapper;
import com.yuier.domain.entity.Role;
import com.yuier.service.MenuService;
import com.yuier.service.RoleMenuService;
import com.yuier.service.RoleService;
import com.yuier.service.UserRoleService;
import com.yuier.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Yui
 * @since 2023-08-11 00:29:07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;

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

    // 查询角色列表
    @Override
    public ResponseResult adminGetRoleList(Integer pageNum, Integer pageSize, AdminGetRoleListDto adminGetRoleListDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(
                StringUtils.hasText(adminGetRoleListDto.getRoleName()),
                Role::getRoleName,
                adminGetRoleListDto.getRoleName()
        );
        queryWrapper.like(
                StringUtils.hasText(adminGetRoleListDto.getStatus()),
                Role::getStatus,
                adminGetRoleListDto.getStatus()
        );
        queryWrapper.orderByAsc(Role::getRoleSort);
        Page<Role> rolePage = new Page<>(pageNum, pageSize);
        page(rolePage, queryWrapper);
        List<Role> roleList = rolePage.getRecords();
        Long total = rolePage.getTotal();
        PageVo pageVo = new PageVo(roleList, total);
        return ResponseResult.okResult(pageVo);
    }

    // 改变角色状态：停用 / 启用
    @Override
    public ResponseResult changeStatus(ChangeRoleStatusDto changeRoleStatusDto) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId, changeRoleStatusDto.getRoleId());
        updateWrapper.set(Role::getStatus, changeRoleStatusDto.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    // 插入角色
    @Override
    @Transactional
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        // 保存 role
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);

        // 保存 role-menu 表
        // 先查出未被删除的 menu
        List<Long> validMenuIds = getValidMenuIds(addRoleDto.getMenuIds());
        // 然后转化为实体列表，批量插入
        List<RoleMenu> roleMenuList = validMenuIds.stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .toList();
        roleMenuService.saveBatch(roleMenuList);
        return null;
    }

    // 获取有效的 menuId
    public List<Long> getValidMenuIds(List<Long> menuIds) {
        return menuIds.stream()
                .filter(menuId -> Objects.nonNull(menuService.getById(menuId)))
                .toList();
    }

}

