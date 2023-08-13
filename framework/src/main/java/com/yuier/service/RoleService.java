package com.yuier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.role.AddRoleDto;
import com.yuier.domain.dto.role.AdminGetRoleListDto;
import com.yuier.domain.dto.role.ChangeRoleStatusDto;
import com.yuier.domain.dto.role.UpdateRoleDto;
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

    ResponseResult adminGetRoleList(Integer pageNum, Integer pageSize, AdminGetRoleListDto adminGetRoleListDto);

    ResponseResult changeStatus(ChangeRoleStatusDto changeRoleStatusDto);

    ResponseResult addRole(AddRoleDto addRoleDto);

    ResponseResult roleDetail(Long id);

    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    ResponseResult deleteRole(Long id);

    ResponseResult addUserRoleList();
}

