package com.yuier.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 17:46
 **/

@Data  // lombok 自动生成 Getters 和 Setters
@NoArgsConstructor  // 自动生成无参构造方法
@AllArgsConstructor  // 自动生成全参数构造方法
public class UpdateUserInfoDto {

    private Long id;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    //昵称
    private String nickName;
    //邮箱
    private String email;
}
