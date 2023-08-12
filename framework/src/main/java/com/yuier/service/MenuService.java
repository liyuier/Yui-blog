package com.yuier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.menu.AddMenuDto;
import com.yuier.domain.dto.menu.MenuListDto;
import com.yuier.domain.dto.menu.UpdateMenuDto;
import com.yuier.domain.entity.Menu;
import com.yuier.domain.vo.admin.MenuVo;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author Yui
 * @since 2023-08-11 00:28:43
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectMenuTreeByUserId(Long id);

    ResponseResult menuList(MenuListDto menuListDto);

    ResponseResult addMenu(AddMenuDto addMenuDto);

    ResponseResult adminGetMenuDetail(Long id);

    ResponseResult updateMenu(UpdateMenuDto updateMenuDto);

    ResponseResult deleteMenu(Long menuId);

    ResponseResult adminGetMenuTree();
}

