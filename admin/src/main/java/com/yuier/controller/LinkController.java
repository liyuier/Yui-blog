package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.link.AddLinkDto;
import com.yuier.domain.dto.link.AdminGetLinkByPageDto;
import com.yuier.domain.dto.link.UpdateLinkDto;
import com.yuier.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Yui
 * @Description:
 * @DateTime: 2023/8/13 13:51
 **/

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    @SystemLog(businessName = "分页查询友链")
    @PreAuthorize("@ps.hasPermission('content:link:list')")
    public ResponseResult adminGetLinkByPage(Integer pageNum, Integer pageSize, AdminGetLinkByPageDto adminGetLinkByPageDto) {
        return linkService.adminGetLinkByPage(pageNum, pageSize, adminGetLinkByPageDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增友链")
    @PreAuthorize("@ps.hasPermission('content:link:add')")
    public ResponseResult addLink(@RequestBody AddLinkDto addLinkDto) {
        return linkService.addLink(addLinkDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "修改友链前回显友链信息")
    @PreAuthorize("@ps.hasPermission('content:link:edit')")
    public ResponseResult linkDetailBeforeUpdate(@PathVariable("id") Long id) {
        return linkService.linkDetailBeforeUpdate(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改友链信息")
    @PreAuthorize("@ps.hasPermission('content:link:edit')")
    public ResponseResult updateLink(@RequestBody UpdateLinkDto updateLinkDto) {
        return linkService.updateLink(updateLinkDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除友链列表")
    @PreAuthorize("@ps.hasPermission('content:link:remove')")
    public ResponseResult deleteLink(@PathVariable("id") List<Long> idList) {
        return linkService.deleteLink(idList);
    }

}
