package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.vo.link.LinkListVo;
import com.yuier.mapper.LinkMapper;
import com.yuier.domain.entity.Link;
import com.yuier.service.LinkService;
import com.yuier.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author Yui
 * @since 2023-08-05 09:45:08
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

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
}

