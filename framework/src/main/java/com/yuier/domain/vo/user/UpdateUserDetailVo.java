package com.yuier.domain.vo.user;

import com.yuier.domain.vo.role.UpdateUserRoleVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 11:39
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDetailVo {
    private List<Long> roleIds;

    private List<UpdateUserRoleVo> roles;

    private UpdateUserInfoVo user;
}
