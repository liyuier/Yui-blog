package com.yuier.domain.dto.link;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 13:54
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminGetLinkByPageDto {

    private String name;

    //审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
    private String status;
}
