package com.yuier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.tag.AddTagDto;
import com.yuier.domain.dto.tag.TagListDto;
import com.yuier.domain.dto.tag.UpdateTagDto;
import com.yuier.domain.entity.Tag;

import java.util.List;

/**
 * 标签(Tag)表服务接口
 *
 * @author Yui
 * @since 2023-08-10 21:06:34
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(AddTagDto addTagDto);

    ResponseResult deleteTag(List<Long> idList);

    ResponseResult getSingleTag(Long id);

    ResponseResult updateTag(UpdateTagDto updateTagDto);

    ResponseResult listAllTag();
}

