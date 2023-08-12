package com.yuier.domain.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/12 11:28
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTagDto {

    private Long id;

    private String name;

    private String remark;
}
