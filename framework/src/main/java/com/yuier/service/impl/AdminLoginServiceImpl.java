package com.yuier.service.impl;

import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.user.UserLoginDto;
import com.yuier.domain.entity.*;
import com.yuier.domain.vo.admin.AdminLoginVo;
import com.yuier.domain.vo.admin.AdminUserInfoVo;
import com.yuier.domain.vo.admin.MenuVo;
import com.yuier.domain.vo.admin.RoutersVo;
import com.yuier.domain.vo.user.UserInfoVo;
import com.yuier.service.*;
import com.yuier.utils.BeanCopyUtils;
import com.yuier.utils.JwtUtil;
import com.yuier.utils.RedisCache;
import com.yuier.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 22:00
 **/

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult adminLogin(UserLoginDto admin) {
        // 实现用户校验
        // UsernamePasswordAuthenticationToken 是一个身份验证类型
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(admin.getUserName(), admin.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 获取用户 ID ，生成 jwt ，存入 Redis 中
        // 判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取 userid ，生成 token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();  // 获取认证主体，强转为 LoginUser
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入 Redis
        redisCache.setCacheObject(SystemConstants.REDIS_KEYS.ADMIN_USER + userId, loginUser);
        // 返回 token
        AdminLoginVo adminLoginVo = new AdminLoginVo(jwt);
        return ResponseResult.okResult(adminLoginVo);
    }

    // /getInfo 接口
    @Override
    public ResponseResult<AdminUserInfoVo> getAdminInfo() {
        // 获取当前登录的用户信息
        Long id = SecurityUtils.getUserId();

        // 根据用户 id 查询权限列表
        List<String> permsList = menuService.selectPermsByUserId(id);

        // 根据用户 id 查角色信息
        List<String> roleKeys = roleService.selectRoleKeysByUserId(id);

        // 根据用户 id 查询用户对象并封装为 userInfoVo
        User user = userService.getById(id);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        // 封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(permsList, roleKeys, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @Override
    public ResponseResult<RoutersVo> getMenuRouters() {
        // 获取当前登录的用户信息
        Long id = SecurityUtils.getUserId();

        // 根据用户 id 查询菜单层次
        List<MenuVo> menuTree = menuService.selectMenuTreeByUserId(id);
        RoutersVo routersVo = new RoutersVo(menuTree);
        return ResponseResult.okResult(routersVo);
    }

    @Override
    public ResponseResult adminLogout() {
        // 获取当前用户 id
        Long id = SecurityUtils.getUserId();
        redisCache.deleteObject(SystemConstants.REDIS_KEYS.ADMIN_USER + id);
        return ResponseResult.okResult();
    }
}
