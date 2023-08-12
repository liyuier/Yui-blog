package com.yuier.domain.vo.admin;

import com.yuier.domain.vo.user.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/11 0:27
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoVo {

    private List<String> permissions;

    private List<String> roles;

    private UserInfoVo user;
}
