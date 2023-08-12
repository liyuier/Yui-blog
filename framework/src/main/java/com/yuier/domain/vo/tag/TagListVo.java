package com.yuier.domain.vo.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/11 18:34
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagListVo {

    private Long id;

    private String name;

    private String remark;
}
