package com.yuier.service;

import com.yuier.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/8 11:18
 **/

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
