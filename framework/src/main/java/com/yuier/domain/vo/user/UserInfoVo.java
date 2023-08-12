package com.yuier.domain.vo.user;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/5 21:32
 **/

@Data
@Accessors(chain = true)
public class UserInfoVo {
    // id
    private Long id;

    // 昵称
    private String nickName;

    // 头像
    private String avatar;

    private String sex;

    private String email;
}
