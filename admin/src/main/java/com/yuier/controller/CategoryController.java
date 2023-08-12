package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.vo.category.AllCategoryListVo;
import com.yuier.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/12 13:11
 **/

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    @SystemLog(businessName = "查询所有分类的列表")
    public ResponseResult<List<AllCategoryListVo>> listAllCategory() {
        return categoryService.listAllCategory();
    }
}
