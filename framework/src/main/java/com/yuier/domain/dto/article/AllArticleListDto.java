package com.yuier.domain.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/12 14:35
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllArticleListDto {

    private String title;

    private String summary;
}
