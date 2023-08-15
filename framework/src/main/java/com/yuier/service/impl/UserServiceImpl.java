package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.*;
import com.yuier.domain.entity.Role;
import com.yuier.domain.entity.UserRole;
import com.yuier.domain.vo.page.PageVo;
import com.yuier.domain.vo.role.UpdateUserRoleVo;
import com.yuier.domain.vo.user.AdminUserListVo;
import com.yuier.domain.vo.user.UpdateUserDetailVo;
import com.yuier.domain.vo.user.UpdateUserInfoVo;
import com.yuier.domain.vo.user.UserInfoVo;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.exception.SystemException;
import com.yuier.mapper.UserMapper;
import com.yuier.domain.entity.User;
import com.yuier.service.RoleService;
import com.yuier.service.UserRoleService;
import com.yuier.service.UserService;
import com.yuier.utils.BeanCopyUtils;
import com.yuier.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author Yui
 * @since 2023-08-06 21:23:07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    // 前台获取用户个人信息
    @Override
    public ResponseResult userInfo() {
        User user = getById(SecurityUtils.getUserId());
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    // 前台用户信息
    @Override
    public ResponseResult updateUserInfo(UpdateUserInfoDto updateUserInfoDto) {
        if (!(SecurityUtils.getUserId().equals(updateUserInfoDto.getId()))) {
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, updateUserInfoDto.getId());
        updateWrapper.set(User::getAvatar, updateUserInfoDto.getAvatar())
                .set(User::getEmail, updateUserInfoDto.getEmail())
                .set(User::getNickName, updateUserInfoDto.getNickName())
                .set(User::getSex, updateUserInfoDto.getSex());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    // 用户注册
    @Override
    public ResponseResult register(UserRegisterDto userRegisterDto) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(userRegisterDto.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        } else if (!StringUtils.hasText(userRegisterDto.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        } else if (!StringUtils.hasText(userRegisterDto.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        } else if (!StringUtils.hasText(userRegisterDto.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        // 对数据进行重复判断
        if (userNameExists(userRegisterDto.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        } else if (emailExists(userRegisterDto.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 对密码进行加密处理
        String encodedPassword = passwordEncoder.encode(userRegisterDto.getPassword());
        userRegisterDto.setPassword(encodedPassword);
        User user = BeanCopyUtils.copyBean(userRegisterDto, User.class);

        // 存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    // 后台获取用户列表
    @Override
    public ResponseResult adminListUser(Integer pageNum, Integer pageSize, AdminUserListDto adminUserListDto) {
        // 根据姓名、手机号进行模糊查询；根据状态进行查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(
                StringUtils.hasText(adminUserListDto.getUserName()),
                User::getUserName,
                adminUserListDto.getUserName()
        );
        queryWrapper.like(
                StringUtils.hasText(adminUserListDto.getPhonenumber()),
                User::getPhonenumber,
                adminUserListDto.getPhonenumber()
        );
        queryWrapper.eq(
                StringUtils.hasText(adminUserListDto.getStatus()),
                User::getStatus,
                adminUserListDto.getStatus()
        );
        Page<User> userPage = new Page<>();
        page(userPage, queryWrapper);
        List<User> userList = userPage.getRecords();
        List<AdminUserListVo> adminUserListVos = BeanCopyUtils.copyBeanList(userList, AdminUserListVo.class);
        Long total = userPage.getTotal();
        PageVo pageVo = new PageVo(adminUserListVos, total);
        return ResponseResult.okResult(pageVo);
    }

    // 后台添加用户
    @Override
    @Transactional
    public ResponseResult adminAddUser(AdminAddUserDto adminAddUserDto) {
        // 先保存用户
        // 对用户名、用户昵称、用户密码判空
        if (!StringUtils.hasText(adminAddUserDto.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        } else if (!StringUtils.hasText(adminAddUserDto.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        } else if (!StringUtils.hasText(adminAddUserDto.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        // 用户名、手机号、邮箱都不能存在
        if (userNameExists(adminAddUserDto.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        } else if (emailExists(adminAddUserDto.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        } else if (phonenumberExists(adminAddUserDto.getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        // 对密码进行加密处理
        String encodedPassword = passwordEncoder.encode(adminAddUserDto.getPassword());
        adminAddUserDto.setPassword(encodedPassword);
        User user = BeanCopyUtils.copyBean(adminAddUserDto, User.class);
        save(user);

        // 再保存 user-role 列表
        // 先查询有效的 roleId 列表，然后转化为 user-role 实体列表
        List<Long> validRoleIds = getValidRoleIds(adminAddUserDto.getRoleIds());
        List<UserRole> userRoleList = validRoleIds.stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .toList();
        // 然后插入数据库
        userRoleService.saveBatch(userRoleList);
        return ResponseResult.okResult();
    }

    // 删除用户
    @Override
    public ResponseResult deleteUser(List<Long> idList) {
        for (Long id : idList) {
            // 不能删除当前正在操作的用户
            if (SecurityUtils.getUserId().equals(id)) {
                throw new SystemException(AppHttpCodeEnum.NO_DELETE_OPERATING_USER);
            }
            removeById(id);
        }
        return ResponseResult.okResult();
    }

    /**
     *   更新用户前先回显用户详情
     * @param id  传入用户 id
     * @return  返回用户所关联的角色 id 列表、所有角色列表、用户信息
     */
    @Override
    public ResponseResult adminUserDetail(Long id) {
        // 先获取用户
        User user = getById(id);
        // 然后封装为 updateUserInfoVo
        UpdateUserInfoVo updateUserInfoVo = BeanCopyUtils.copyBean(user, UpdateUserInfoVo.class);
        // 再获取用户对应的角色 id 列表，要求角色不能被删除
        LambdaQueryWrapper<UserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoleList = userRoleService.list(userRoleWrapper);
        List<Long> roleIds = userRoleList.stream().map(UserRole::getRoleId).toList();
        List<Long> validRoleIds = getValidRoleIds(roleIds);
        // 最后获取所有角色列表，并封装为 vo
        List<Role> roleList = roleService.list();
        List<UpdateUserRoleVo> updateUserRoleVos = BeanCopyUtils.copyBeanList(roleList, UpdateUserRoleVo.class);
        UpdateUserDetailVo updateUserDetailVo = new UpdateUserDetailVo(validRoleIds, updateUserRoleVos, updateUserInfoVo);
        return ResponseResult.okResult(updateUserDetailVo);
    }

    // 后台更新用户信息
    @Override
    @Transactional
    public ResponseResult adminUpdateUser(AdminUpdateUserDto adminUpdateUserDto) {
        // 对用户邮箱与手机号码进行存在性判断
        if (emailExists(adminUpdateUserDto.getEmail()) &&
                !adminUpdateUserDto.getEmail().equals(getById(adminUpdateUserDto.getId()).getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (StringUtils.hasText(adminUpdateUserDto.getPhonenumber()) &&
                phonenumberExists(adminUpdateUserDto.getPhonenumber()) &&
                    !adminUpdateUserDto.getPhonenumber().equals(getById(adminUpdateUserDto.getId()).getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        // 先保存用户
        User user = BeanCopyUtils.copyBean(adminUpdateUserDto, User.class);
        updateById(user);
        // 再保存 user-role 表
        // 先删除用户对应的 user-role 表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, adminUpdateUserDto.getId());
        userRoleService.remove(queryWrapper);
        // 然后获取用户对应的 role 列表，并过滤为有效的 role 列表
        List<Long> validRoleIds = getValidRoleIds(adminUpdateUserDto.getRoleIds());
        List<UserRole> userRoleList = validRoleIds.stream()
                .map(roleId -> new UserRole(adminUpdateUserDto.getId(), roleId))
                .toList();
        userRoleService.saveBatch(userRoleList);
        return ResponseResult.okResult();
    }

    private boolean emailExists(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExists(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }

    private boolean phonenumberExists(String phonenumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, phonenumber);
        return count(queryWrapper) > 0;
    }

    private List<Long> getValidRoleIds(List<Long> roleIds) {
        List<Long> validRoleIds = roleIds.stream()
                .filter(roleId -> Objects.nonNull(roleService.getById(roleId)))
                .toList();
        return validRoleIds;
    }

}
