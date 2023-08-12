package com.yuier.domain.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/4 21:51
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {

    private List rows;
    private Long total;
}
