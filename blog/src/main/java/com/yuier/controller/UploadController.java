package com.yuier.controller;

import com.yuier.annotation.SystemLog;
import com.yuier.domain.ResponseResult;
import com.yuier.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/8 11:14
 **/

@RestController
@Api(tags = "上传文件", description = "上传文件接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @SystemLog(businessName = "上传头像")
    @ApiOperation(value = "上传头像", notes = "向服务器上传头像并获取图片上传后的 url")
    @ApiImplicitParams(@ApiImplicitParam(name = "img", value = "上传的头像文件"))
    public ResponseResult uploadImg(MultipartFile img) {
        return uploadService.uploadImg(img);
    }
}
