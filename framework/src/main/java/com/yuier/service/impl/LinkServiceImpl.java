package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.link.AddLinkDto;
import com.yuier.domain.dto.link.AdminGetLinkByPageDto;
import com.yuier.domain.dto.link.UpdateLinkDto;
import com.yuier.domain.vo.link.AdminGetLinkByPageVo;
import com.yuier.domain.vo.link.LinkDetailBeforeUpdateVo;
import com.yuier.domain.vo.link.LinkListVo;
import com.yuier.domain.vo.page.PageVo;
import com.yuier.mapper.LinkMapper;
import com.yuier.domain.entity.Link;
import com.yuier.service.LinkService;
import com.yuier.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author Yui
 * @since 2023-08-05 09:45:08
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    // 前台查询所有友链
    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        // 查询所有审核通过的
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_APPROVED);
        List<Link> linkList = list(queryWrapper);  // 接收 MyBatis 查询结果的返回集合
        // 转换成 Vo
        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(linkList, LinkListVo.class);
        // 封装
        return ResponseResult.okResult(linkListVos);
    }

    // 后台分页查询友链
    @Override
    public ResponseResult adminGetLinkByPage(Integer pageNum, Integer pageSize, AdminGetLinkByPageDto adminGetLinkByPageDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(
                StringUtils.hasText(adminGetLinkByPageDto.getName()),
                Link::getName,
                adminGetLinkByPageDto.getName()
        );
        queryWrapper.eq(
                StringUtils.hasText(adminGetLinkByPageDto.getStatus()),
                Link::getStatus,
                adminGetLinkByPageDto.getStatus()
        );
        Page<Link> linkPage = new Page<>();
        page(linkPage, queryWrapper);
        List<AdminGetLinkByPageVo> linkVos = BeanCopyUtils.copyBeanList(linkPage.getRecords(), AdminGetLinkByPageVo.class);
        Long total = linkPage.getTotal();
        PageVo pageVo = new PageVo(linkVos, total);
        return ResponseResult.okResult(pageVo);
    }

    // 后台添加友链
    @Override
    public ResponseResult addLink(AddLinkDto addLinkDto) {
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    // 后台修改友链前回显友链信息
    @Override
    public ResponseResult linkDetailBeforeUpdate(Long id) {
        Link link = getById(id);
        LinkDetailBeforeUpdateVo linkVo = BeanCopyUtils.copyBean(link, LinkDetailBeforeUpdateVo.class);
        return ResponseResult.okResult(linkVo);
    }

    // 后台修改友链信息
    @Override
    public ResponseResult updateLink(UpdateLinkDto updateLinkDto) {
        Link link = BeanCopyUtils.copyBean(updateLinkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    // 后台删除友链
    @Override
    public ResponseResult deleteLink(List<Long> idList) {
        for (Long id : idList) {
            removeById(id);
        }
        return ResponseResult.okResult();
    }

}

