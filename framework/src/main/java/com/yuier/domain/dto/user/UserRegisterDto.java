package com.yuier.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 17:56
 **/

@Data  // lombok 自动生成 Getters 和 Setters
@NoArgsConstructor  // 自动生成无参构造方法
@AllArgsConstructor  // 自动生成全参数构造方法
public class UserRegisterDto {

    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;
    //邮箱
    private String email;
}
