package com.yuier.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuier.domain.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author Yui
 * @since 2023-08-11 00:28:43
 */

@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectMenuListByUserId(Long id);

}

