package com.yuier.domain.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/12 12:59
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTagDto {

    private Long id;

    private String name;

    private String remark;
}
