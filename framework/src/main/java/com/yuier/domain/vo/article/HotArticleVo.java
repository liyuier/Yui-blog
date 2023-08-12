package com.yuier.domain.vo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/2 12:11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class HotArticleVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
