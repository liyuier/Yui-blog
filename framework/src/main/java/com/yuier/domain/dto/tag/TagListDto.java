package com.yuier.domain.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/11 18:18
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagListDto {

    private String name;

    private String remark;
}
