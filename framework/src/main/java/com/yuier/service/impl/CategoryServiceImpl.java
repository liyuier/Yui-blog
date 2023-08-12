package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.entity.Article;
import com.yuier.domain.vo.category.AllCategoryListVo;
import com.yuier.domain.vo.category.CategoryVo;
import com.yuier.mapper.CategoryMapper;
import com.yuier.domain.entity.Category;
import com.yuier.service.ArticleService;
import com.yuier.service.CategoryService;
import com.yuier.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public ResponseResult getCategoryList() {
        // 查询文章表 状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        // 获取文章的分类 id ，并去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
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

    @Override
    public ResponseResult<List<AllCategoryListVo>> listAllCategory() {
        List<Category> allCategoryList = list();
        List<AllCategoryListVo> allCategoryListVo = BeanCopyUtils.copyBeanList(allCategoryList, AllCategoryListVo.class);
        return ResponseResult.okResult(allCategoryListVo);
    }
}

