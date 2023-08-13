package com.yuier.domain.vo.category;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 13:10
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminListCategoryByPageVo {

    private Long id;

    //分类名
    private String name;

    //描述
    private String description;

    //状态0:正常,1禁用
    private String status;
}
