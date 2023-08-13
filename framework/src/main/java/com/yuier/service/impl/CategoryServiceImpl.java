package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.category.AddCategoryDto;
import com.yuier.domain.dto.category.ListCategoryByPageDto;
import com.yuier.domain.dto.category.UpdateCategoryDto;
import com.yuier.domain.entity.Article;
import com.yuier.domain.vo.category.AdminListCategoryByPageVo;
import com.yuier.domain.vo.category.AllCategoryListVo;
import com.yuier.domain.vo.category.CategoryDetailBeforeUpdateVo;
import com.yuier.domain.vo.category.CategoryVo;
import com.yuier.domain.vo.page.PageVo;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.exception.SystemException;
import com.yuier.mapper.CategoryMapper;
import com.yuier.domain.entity.Category;
import com.yuier.service.ArticleService;
import com.yuier.service.CategoryService;
import com.yuier.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author Yui
 * @since 2023-08-04 11:52:28
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    // 前台查询分类列表
    @Override
    public ResponseResult getCategoryList() {
        // 查询文章表 状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        // 获取文章的分类 id ，并去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());  // 使用 toSet 可以直接去重
        // 查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());  // 过滤标签状态为正常
        // 封装 vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    // 后台写文章时查询所有分类列表
    @Override
    public ResponseResult<List<AllCategoryListVo>> listAllCategory() {
        List<Category> allCategoryList = list();
        List<AllCategoryListVo> allCategoryListVo = BeanCopyUtils.copyBeanList(allCategoryList, AllCategoryListVo.class);
        return ResponseResult.okResult(allCategoryListVo);
    }

    // 后台分页查询分类列表
    @Override
    public ResponseResult listCategoryByPage(Integer pageNum, Integer pageSize, ListCategoryByPageDto listCategoryByPageDto) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(
                StringUtils.hasText(listCategoryByPageDto.getName()),
                Category::getName,
                listCategoryByPageDto.getName()
        );
        queryWrapper.eq(
                StringUtils.hasText(listCategoryByPageDto.getStatus()),
                Category::getStatus,
                listCategoryByPageDto.getStatus()
        );
        Page<Category> categoryPage = new Page<>();
        page(categoryPage, queryWrapper);
        List<Category> categoryList = categoryPage.getRecords();
        List<AdminListCategoryByPageVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, AdminListCategoryByPageVo.class);
        Long total = categoryPage.getTotal();
        PageVo pageVo = new PageVo(categoryVos, total);
        return ResponseResult.okResult(pageVo);
    }

    // 后台添加标签
    @Override
    public ResponseResult addCategory(AddCategoryDto addCategoryDto) {
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    // 修改标签前回显标签信息
    @Override
    public ResponseResult categoryDetailBeforeUpdate(Long id) {
        Category category = getById(id);
        CategoryDetailBeforeUpdateVo categoryVo = BeanCopyUtils.copyBean(category, CategoryDetailBeforeUpdateVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    // 修改标签
    @Override
    public ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto) {
        Category category = BeanCopyUtils.copyBean(updateCategoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    // 删除标签
    @Override
    public ResponseResult deleteCategory(Long id) {
        // 查询分类下是否有文章，如果有，无法删除
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getCategoryId, id);
        if (articleService.list(queryWrapper).size() > 0) {
            throw new SystemException(AppHttpCodeEnum.CATEGORY_HAVING_ARTICLES);
        }
        removeById(id);
        return ResponseResult.okResult();
    }
}

