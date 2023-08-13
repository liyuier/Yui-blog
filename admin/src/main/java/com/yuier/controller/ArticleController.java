package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.article.AddArticleDto;
import com.yuier.domain.dto.article.AllArticleListDto;
import com.yuier.domain.dto.article.UpdateArticleDto;
import com.yuier.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/12 13:43
 **/

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @SystemLog(businessName = "新增博文")
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }

    @GetMapping("/list")
    @SystemLog(businessName = "展示博文列表")
    public ResponseResult listAllArticle(Integer pageNum, Integer pageSize, AllArticleListDto allArticleListDto) {
        return articleService.listAllArticle(pageNum, pageSize, allArticleListDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取博文详情")
    public ResponseResult adminGetArticleDetail(@PathVariable("id") Long id) {
        return articleService.adminGetArticleDetail(id);
    }

    @PutMapping
    @SystemLog(businessName = "更新博文")
    public ResponseResult updateArticle(@RequestBody UpdateArticleDto updateArticleDto) {
        return articleService.updateArticle(updateArticleDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除博文列表")
    public ResponseResult deleteArticle(@PathVariable("id") List<Long> idList) {
        return articleService.deleteArticle(idList);
    }

}
