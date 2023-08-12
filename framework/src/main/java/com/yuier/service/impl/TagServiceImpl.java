package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.tag.AddTagDto;
import com.yuier.domain.dto.tag.TagListDto;
import com.yuier.domain.dto.tag.UpdateTagDto;
import com.yuier.domain.vo.page.PageVo;
import com.yuier.domain.vo.tag.AllTagListVo;
import com.yuier.domain.vo.tag.SingleTagVo;
import com.yuier.domain.vo.tag.TagListVo;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.exception.SystemException;
import com.yuier.mapper.TagMapper;
import com.yuier.domain.entity.Tag;
import com.yuier.service.TagService;
import com.yuier.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author Yui
 * @since 2023-08-10 21:06:34
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        // 分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());

        Page<Tag> tagPage = new Page<>();
        tagPage.setCurrent(pageNum);
        tagPage.setSize(pageSize);
        Page<Tag> tagPageResult = page(tagPage, queryWrapper);
        List<Tag> tagList = tagPageResult.getRecords();
        Long total = tagPageResult.getTotal();

        // 封装数据
        List<TagListVo> tagListVo = BeanCopyUtils.copyBeanList(tagList, TagListVo.class);
        PageVo tagPageVo = new PageVo(tagListVo, total);
        return ResponseResult.okResult(tagPageVo);
    }

    @Override
    public ResponseResult addTag(AddTagDto addTagDto) {
        // 判断 tag 名称是否为空
        if (!StringUtils.hasText(addTagDto.getName())) {
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_NOT_NULL);
        }
        // 添加 tag
        Tag tag = BeanCopyUtils.copyBean(addTagDto, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(List<Long> idList) {
        for (Long id : idList) {
            removeById(id);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getSingleTag(Long id) {
        Tag tag = getById(id);
        SingleTagVo singleTagVo = BeanCopyUtils.copyBean(tag, SingleTagVo.class);
        return ResponseResult.okResult(singleTagVo);
    }

    @Override
    public ResponseResult updateTag(UpdateTagDto updateTagDto) {
        Tag tag = BeanCopyUtils.copyBean(updateTagDto, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> tagList = list();
        List<AllTagListVo> allTagListVos = BeanCopyUtils.copyBeanList(tagList, AllTagListVo.class);
        return ResponseResult.okResult(allTagListVos);
    }
}

