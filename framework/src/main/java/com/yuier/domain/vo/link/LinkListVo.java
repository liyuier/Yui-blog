package com.yuier.domain.vo.link;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/5 9:54
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkListVo {

    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
}
