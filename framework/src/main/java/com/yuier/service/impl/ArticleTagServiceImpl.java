package com.yuier.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.mapper.ArticleTagMapper;
import com.yuier.domain.entity.ArticleTag;
import com.yuier.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author Yui
 * @since 2023-08-11 21:36:53
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

