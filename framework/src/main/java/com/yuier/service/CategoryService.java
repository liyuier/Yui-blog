package com.yuier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.category.AddCategoryDto;
import com.yuier.domain.dto.category.ListCategoryByPageDto;
import com.yuier.domain.dto.category.UpdateCategoryDto;
import com.yuier.domain.entity.Category;
import com.yuier.domain.vo.category.AllCategoryListVo;

import java.util.List;

/**
 * 分类表(Category)表服务接口
 *
 * @author Yui
 * @since 2023-08-04 11:52:28
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult<List<AllCategoryListVo>> listAllCategory();

    ResponseResult listCategoryByPage(Integer pageNum, Integer pageSize, ListCategoryByPageDto listCategoryByPageDto);

    ResponseResult addCategory(AddCategoryDto addCategoryDto);

    ResponseResult categoryDetailBeforeUpdate(Long id);

    ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto);

    ResponseResult deleteCategory(Long id);
}

