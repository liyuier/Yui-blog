package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.category.AddCategoryDto;
import com.yuier.domain.dto.category.ListCategoryByPageDto;
import com.yuier.domain.dto.category.UpdateCategoryDto;
import com.yuier.domain.vo.category.AllCategoryListVo;
import com.yuier.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    public ResponseResult<List<AllCategoryListVo>> listAllCategory() {
        return categoryService.listAllCategory();
    }

    @GetMapping("/list")
    @SystemLog(businessName = "分页查询所有分类列表")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    public ResponseResult listCategoryByPage(Integer pageNum, Integer pageSize, ListCategoryByPageDto listCategoryByPageDto) {
        return categoryService.listCategoryByPage(pageNum, pageSize, listCategoryByPageDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增分类")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    public ResponseResult addCategory(@RequestBody AddCategoryDto addCategoryDto) {
        return categoryService.addCategory(addCategoryDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "修改分类前回显分类信息")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    public ResponseResult categoryDetailBeforeUpdate(@PathVariable("id") Long id) {
        return categoryService.categoryDetailBeforeUpdate(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改分类")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    public ResponseResult updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto) {
        return categoryService.updateCategory(updateCategoryDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除分类列表")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    public ResponseResult deleteCategory(@PathVariable("id") List<Long> id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:list')")
    public void export2Excel(HttpServletResponse response) {
        categoryService.export2Excel(response);
    }

}
