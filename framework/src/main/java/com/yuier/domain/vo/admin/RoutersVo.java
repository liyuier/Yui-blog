package com.yuier.domain.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/11 14:01
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutersVo {
    private List<MenuVo> menus;
}
