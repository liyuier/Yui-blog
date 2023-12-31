package com.yuier.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 11:46
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoVo {

    private Long id;

    //用户名
    private String userName;

    //昵称
    private String nickName;

    //账号状态（0正常 1停用）
    private String status;

    //邮箱
    private String email;

    //用户性别（0男，1女，2未知）
    private String sex;
}
