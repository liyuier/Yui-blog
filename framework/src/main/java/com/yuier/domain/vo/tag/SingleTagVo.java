package com.yuier.domain.vo.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/12 12:55
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleTagVo {

    private Long id;

    private String name;

    private String remark;
}
