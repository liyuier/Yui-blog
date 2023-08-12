package com.yuier.domain.vo.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/12 13:16
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllCategoryListVo {

    private Long id;

    private String name;

    private String description;
}
