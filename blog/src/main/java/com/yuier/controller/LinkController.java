package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/5 9:40
 **/

@RestController
@RequestMapping("/link")
@Api(tags = "友链", description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @ApiOperation(value = "查询友链", notes = "查询所有友链信息")
    @SystemLog(businessName = "查询友链")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}
