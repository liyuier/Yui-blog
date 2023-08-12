package com.yuier.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/8 14:22
 **/

@Data  // lombok 自动生成 Getters 和 Setters
@NoArgsConstructor  // 自动生成无参构造方法
@AllArgsConstructor  // 自动生成全参数构造方法
public class SMMSImage {
    private Integer file_id;
    private Integer width;
    private Integer height;
    private Integer size;
    private String filename;
    private String storename;
    private String path;
    private String hash;
    private String url;
    private String delete;
    private String page;
}
