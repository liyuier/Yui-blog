package com.yuier.runner;

import com.yuier.constants.SystemConstants;
import com.yuier.domain.entity.Article;
import com.yuier.service.ArticleService;
import com.yuier.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 9:13
 **/

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息 id viewCount'
        List<Article> articles = articleService.list();
        // 将查询到的 List<article> 转为 Map<String, Integer> ，键值分别为 id 和 viewCount
        Map<String, Integer> viewCountMap = articles.stream().collect(Collectors.toMap(
                article -> article.getId().toString(),
                article -> article.getViewCount().intValue()
        ));
        // 存储到 Redis 中
        redisCache.setCacheMap(SystemConstants.REDIS_KEYS.ARTICLE_VIEW_COUNT, viewCountMap);
    }
}
