package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/4 16:33
 **/

@RestController
@Api(tags = "标签", description = "标签相关接口")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "查询标签列表")
    @ApiOperation(value = "查询标签列表", notes = "获取所有标签列表")
    public ResponseResult getCategoryList() {
        return categoryService.getCategoryList();
    }
}
