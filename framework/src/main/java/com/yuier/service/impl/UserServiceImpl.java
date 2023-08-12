package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.UpdateUserInfoDto;
import com.yuier.domain.dto.user.UserRegisterDto;
import com.yuier.domain.vo.user.UserInfoVo;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.exception.SystemException;
import com.yuier.mapper.UserMapper;
import com.yuier.domain.entity.User;
import com.yuier.service.UserService;
import com.yuier.utils.BeanCopyUtils;
import com.yuier.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

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

    @Override
    public ResponseResult userInfo() {
        User user = getById(SecurityUtils.getUserId());
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    // 修改用户信息
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

        // 设置创建时间与创建人
        user.setCreateTime(new Date());
        user.setCreateBy(user.getId());

        // 存入数据库
        save(user);
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
}
