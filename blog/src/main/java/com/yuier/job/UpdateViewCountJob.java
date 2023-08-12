package com.yuier.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.entity.Article;
import com.yuier.service.ArticleService;
import com.yuier.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 11:10
 **/

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;


    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateViewCountToDatabase() {
        // 获取 redis 中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.REDIS_KEYS.ARTICLE_VIEW_COUNT);
        // 更新到数据库
        viewCountMap.forEach((id, viewCount) -> {
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, id);
            updateWrapper.set(Article::getViewCount, viewCount);
            articleService.update(updateWrapper);
        });
    }
}
