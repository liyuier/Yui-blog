package com.yuier.domain.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 13:00
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListCategoryByPageDto {

    //分类名
    private String name;

    //状态0:正常,1禁用
    private String status;

}
