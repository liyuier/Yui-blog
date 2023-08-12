package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/7/30 16:56
 **/

@RestController
@RequestMapping("/article")
@Api(tags = "文章", description = "文章相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 查询热门文章，封装为 ResponseResult 返回
    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "查询热门文章列表")
    @ApiOperation(value = "查询热门文章列表", notes = "查询浏览量最高的 10 篇文章")
    public ResponseResult hotArticleList() {
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    // 分页查询文章列表
    @GetMapping("/articleList")
    @SystemLog(businessName = "分页查询文章列表")
    @ApiOperation(value = "分页查询文章列表", notes = "在首页进行分页查询文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前查询页数"),
            @ApiImplicitParam(name = "pageSize", value = "每页查询条数"),
            @ApiImplicitParam(name = "categoryId", value = "如果要查询指定分类的文章，将传递该数据，表示分类 id")
    })
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    // 查询文章详情
    @GetMapping("/{id}")
    @SystemLog(businessName = "查询文章详情")
    @ApiOperation(value = "查询文章详情", notes = "查询当前文章的相关信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "要查询的文章 id"))
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    // 更新文章浏览量
    @PutMapping("/updateViewCount/{id}")
    @SystemLog(businessName = "更新文章浏览量")
    @ApiOperation(value = "更新文章浏览量", notes = "更新文章浏览量")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "要更新浏览量的文章 id"))
    public ResponseResult updateViewCount(@PathVariable("id") Long id) {
        return articleService.updateViewCountInRedis(id);
    }
}
