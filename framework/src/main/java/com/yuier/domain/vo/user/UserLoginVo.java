package com.yuier.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/5 21:29
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVo {

    private String token;
    private UserInfoVo userInfo;
}