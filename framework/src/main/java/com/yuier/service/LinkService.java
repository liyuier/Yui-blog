package com.yuier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author Yui
 * @since 2023-08-05 09:45:08
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

