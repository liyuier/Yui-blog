package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.tag.AddTagDto;
import com.yuier.domain.dto.tag.TagListDto;
import com.yuier.domain.dto.tag.UpdateTagDto;
import com.yuier.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 21:09
 **/

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @SystemLog(businessName = "分页查询标签列表")
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    public ResponseResult tagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    @SystemLog(businessName = "添加标签")
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    public ResponseResult addTag(@RequestBody AddTagDto addTagDto) {
        return tagService.addTag(addTagDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除标签列表")
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    public ResponseResult deleteTag(@PathVariable("id") List<Long> idList) {
        return tagService.deleteTag(idList);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取单个标签信息")
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    public ResponseResult getSingleTag(@PathVariable("id") Long id) {
        return tagService.getSingleTag(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改标签")
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    public ResponseResult updateTag(@RequestBody UpdateTagDto updateTagDto) {
        return tagService.updateTag(updateTagDto);
    }

    @GetMapping("/listAllTag")
    @SystemLog(businessName = "查询所有标签列表")
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }
}
