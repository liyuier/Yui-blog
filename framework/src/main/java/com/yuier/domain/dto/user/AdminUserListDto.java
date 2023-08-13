package com.yuier.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 10:08
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserListDto {

    //用户名
    private String userName;

    //账号状态（0正常 1停用）
    private String status;

    //手机号
    private String phonenumber;

}
