package com.yuier.service.impl;

import com.yuier.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 16:15
 **/

@Service("ps")
public class PermissionService {

    public boolean hasPermission(String permission) {
        // 判断当前用户是否有对应权限
        // 如果是超级管理员，直接返回 true
        if (SecurityUtils.isAdmin()) {
            return true;
        } else {
            // 否则获取当前登录用户所具有的权限，判断是否存在 permission
            List<String> perms = SecurityUtils.getLoginUser().getPerms();
            return perms.contains(permission);
        }
    }
}
