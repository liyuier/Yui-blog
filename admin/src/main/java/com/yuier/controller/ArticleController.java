package com.yuier.controller;

import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.article.AddArticleDto;
import com.yuier.domain.dto.article.AllArticleListDto;
import com.yuier.domain.dto.article.UpdateArticleDto;
import com.yuier.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult listAllArticle(Integer pageNum, Integer pageSize, AllArticleListDto allArticleListDto) {
        return articleService.listAllArticle(pageNum, pageSize, allArticleListDto);
    }

    @GetMapping("/{id}")
    public ResponseResult adminGetArticleDetail(@PathVariable("id") Long id) {
        return articleService.adminGetArticleDetail(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody UpdateArticleDto updateArticleDto) {
        return articleService.updateArticle(updateArticleDto);
    }

}
