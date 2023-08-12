package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.article.AddArticleDto;
import com.yuier.domain.dto.article.AllArticleListDto;
import com.yuier.domain.dto.article.UpdateArticleDto;
import com.yuier.domain.entity.ArticleTag;
import com.yuier.domain.entity.Category;
import com.yuier.domain.vo.article.*;
import com.yuier.domain.vo.page.PageVo;
import com.yuier.mapper.ArticleMapper;
import com.yuier.domain.entity.Article;
import com.yuier.service.ArticleService;
import com.yuier.service.ArticleTagService;
import com.yuier.service.CategoryService;
import com.yuier.service.TagService;
import com.yuier.utils.BeanCopyUtils;
import com.yuier.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author Yui
 * @since 2023-07-30 22:58:30
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    @Lazy
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private TagService tagService;

    // 查询热门文章，封装为 ResponseResult 返回
    @Override
    public ResponseResult hotArticleList() {
        // 构造条件构造器
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章而非草稿 —— getStatus() 方法返回值为 0
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 不能被逻辑删除 —— 配置文件中已经预先配置好，此处不需额外约束
        // 按照浏览量进行降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多展示 10 条 —— 可以使用 page 实现
        Page<Article> page = new Page(1, 10);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();

        // Bean 拷贝
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        hotArticleVos.forEach(hotArticleVo -> {
            try {
                setViewCountFromRedis(hotArticleVo);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return ResponseResult.okResult(hotArticleVos);
    }

    // 前台首页分页查询文章
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果传送了 categoryId ，那么查询时要和传入的相同
        lambdaQueryWrapper.eq(
                Objects.nonNull(categoryId)&&categoryId>0,  // 当该条件为真时
                Article::getCategoryId,  // 后边两个参数相等
                categoryId
        );
        // 文章状态为正式发布的文章
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对 isTop 进行降序排序，于是该值为 1 的文章排在前面，即可实现置顶文章排在前面的功能
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        // 查询分类名称
        List<Article> articles = page.getRecords();
        // 使用 categoryId 去再次查询 categoryName 并进行设置
        articles = articles.stream()
                .peek(article -> {
                    Long cId = article.getCategoryId();
                    if (Objects.nonNull(cId)) {
                        Category cate = categoryService.getById(cId);
                        if (Objects.nonNull(cate)) {
                            article.setCategoryName(cate.getName());
                        }
                    }
                })
                .collect(Collectors.toList());

        // 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        articleListVos.forEach(articleListVo -> {
            try {
                setViewCountFromRedis(articleListVo);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    // 前台查询文章详情
    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 根据 id 查询文章
        Article article = getById(id);
        // 转换成 Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        try {
            setViewCountFromRedis(articleDetailVo);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // 根据分类 ID 查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        // 封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    // 更新文章浏览量到 Redis 中
    @Override
    public ResponseResult updateViewCountInRedis(Long id) {
        redisCache.increCacheMapValue(
                SystemConstants.REDIS_KEYS.ARTICLE_VIEW_COUNT,
                id.toString(),
                SystemConstants.ARTICLE_VIEW_COUNT_INCREMENT
        );
        return ResponseResult.okResult();
    }

    // 后台新增博文
    @Override
    @Transactional
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        // 添加博客
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        // 添加 article-tag 记录，只能添加未被删除的 tag
        List<Long> tagIdList = addArticleDto.getTags();
        List<Long> validTags = getValidTags(tagIdList);
        List<ArticleTag> articleTagList = validTags.stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .toList();
        articleTagService.saveBatch(articleTagList);
        return ResponseResult.okResult();
    }

    // 后台查询所有文章列表
    @Override
    public ResponseResult listAllArticle(Integer pageNum, Integer pageSize, AllArticleListDto allArticleListDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(
                StringUtils.hasText(allArticleListDto.getTitle()),
                Article::getTitle,
                allArticleListDto.getTitle()
        );
        queryWrapper.like(
                StringUtils.hasText(allArticleListDto.getSummary()),
                Article::getSummary,
                allArticleListDto.getSummary()
        );
        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        page(articlePage, queryWrapper);
        List<Article> articleList = articlePage.getRecords();
        List<AllArticleListVo> allArticleListVos = BeanCopyUtils.copyBeanList(articleList, AllArticleListVo.class);
        Long total = articlePage.getTotal();
        PageVo pageVo = new PageVo(allArticleListVos, total);
        return ResponseResult.okResult(pageVo);
    }

    // 后台查询文章详情
    @Override
    public ResponseResult adminGetArticleDetail(Long id) {
        // 根据 id 查询文章并赋值给 vo
        Article article = getById(id);
        AdminArticleDetailVo adminArticleDetailVo = BeanCopyUtils.copyBean(article, AdminArticleDetailVo.class);
        // 为 vo 的 tags 赋值，要判断 tag 是否有效
        LambdaQueryWrapper<ArticleTag> articleTagWrapper = new LambdaQueryWrapper<>();
        articleTagWrapper.eq(ArticleTag::getArticleId, adminArticleDetailVo.getId());
        List<ArticleTag> tags = articleTagService.list(articleTagWrapper);
        List<Long> tagIds = tags.stream().map(ArticleTag::getTagId).toList();
        List<Long> validTagIds = getValidTags(tagIds);
        adminArticleDetailVo.setTags(validTagIds);
        return ResponseResult.okResult(adminArticleDetailVo);
    }

    // 后台更新文章详情
    @Override
    @Transactional
    public ResponseResult updateArticle(UpdateArticleDto updateArticleDto) {
        // 更新文章
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        updateById(article);

        // 更新 article-tag 表，只能保存未被删除的 tag
        List<Long> validTags = getValidTags(updateArticleDto.getTags());
        // 先删除原本的记录
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, article.getId());
        articleTagService.getBaseMapper().delete(queryWrapper);
        // 然后再保存新的记录
        List<ArticleTag> articleTagList = validTags.stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .toList();
        articleTagService.saveBatch(articleTagList);
        return ResponseResult.okResult();
    }

    // 后台删除文章
    @Override
    @Transactional
    public ResponseResult deleteArticle(List<Long> idList) {
        idList.forEach(id -> {
            removeById(id);
            LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ArticleTag::getArticleId, id);
            articleTagService.remove(queryWrapper);
        });
        return ResponseResult.okResult();
    }

    // 从 redis 中取出文章浏览量赋值给文章
    private <T> void setViewCountFromRedis(T vo) throws NoSuchFieldException, IllegalAccessException {
        // 获取对象类实例
        Class<?> clazz = vo.getClass();

        // 获取类的 id 字段实例，并取出对象中该字段值
        Field idField = clazz.getDeclaredField("id");
        idField.setAccessible(true);
        Long id = (Long) idField.get(vo);

        // 获取类的 viewCount 字段实例，并设置该字段为 accessible
        Field viewCountField = clazz.getDeclaredField("viewCount");
        viewCountField.setAccessible(true);

        // 从 Redis 中取出 id 对应的浏览量
        Integer viewCountInRedis = redisCache.getCacheMapValue(
                SystemConstants.REDIS_KEYS.ARTICLE_VIEW_COUNT,
                id.toString()
        );
        // 将其转为 Long 类型
        Long viewCount = viewCountInRedis.longValue();

        // 将浏览量赋值给对应的对象中的字段
        viewCountField.set(vo, viewCount);
    }

    // 过滤已被删除的标签
    private List<Long> getValidTags(List<Long> tagIdList) {
        List<Long> validTagIds = tagIdList.stream()
                .filter(tagId -> Objects.nonNull(tagService.getById(tagId)))
                .toList();
        return validTagIds;
    }
}

