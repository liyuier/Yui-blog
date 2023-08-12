package com.yuier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.article.AddArticleDto;
import com.yuier.domain.dto.article.AllArticleListDto;
import com.yuier.domain.dto.article.UpdateArticleDto;
import com.yuier.domain.entity.Article;

import java.util.List;

/**
 * 文章表(Article)表服务接口
 *
 * @author Yui
 * @since 2023-07-30 22:55:34
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCountInRedis(Long id);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult listAllArticle(Integer pageNum, Integer pageSize, AllArticleListDto allArticleListDto);

    ResponseResult adminGetArticleDetail(Long id);

    ResponseResult updateArticle(UpdateArticleDto updateArticleDto);

    ResponseResult deleteArticle(List<Long> idList);
}

