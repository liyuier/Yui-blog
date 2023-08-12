package com.yuier.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuier.domain.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户表(User)表数据库访问层
 *
 * @author Yui
 * @since 2023-08-05 10:56:29
 */

@Repository
public interface UserMapper extends BaseMapper<User> {

}

