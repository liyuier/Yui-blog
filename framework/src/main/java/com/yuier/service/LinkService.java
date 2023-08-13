package com.yuier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.link.AddLinkDto;
import com.yuier.domain.dto.link.AdminGetLinkByPageDto;
import com.yuier.domain.dto.link.UpdateLinkDto;
import com.yuier.domain.entity.Link;

import java.util.List;

/**
 * 友链(Link)表服务接口
 *
 * @author Yui
 * @since 2023-08-05 09:45:08
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult adminGetLinkByPage(Integer pageNum, Integer pageSize, AdminGetLinkByPageDto adminGetLinkByPageDto);

    ResponseResult addLink(AddLinkDto addLinkDto);

    ResponseResult linkDetailBeforeUpdate(Long id);

    ResponseResult updateLink(UpdateLinkDto updateLinkDto);

    ResponseResult deleteLink(List<Long> idList);
}

