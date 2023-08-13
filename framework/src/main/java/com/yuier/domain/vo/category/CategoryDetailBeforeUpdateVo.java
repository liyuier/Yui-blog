package com.yuier.domain.vo.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 13:28
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetailBeforeUpdateVo {

    private Long id;

    //分类名
    private String name;

    //描述
    private String description;

    //状态0:正常,1禁用
    private String status;

}
