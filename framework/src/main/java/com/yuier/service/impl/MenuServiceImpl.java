package com.yuier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.domain.dto.menu.AddMenuDto;
import com.yuier.domain.dto.menu.MenuListDto;
import com.yuier.domain.dto.menu.UpdateMenuDto;
import com.yuier.domain.vo.admin.MenuVo;
import com.yuier.domain.vo.menu.AdminMenuDetailVo;
import com.yuier.domain.vo.menu.MenuListVo;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.exception.SystemException;
import com.yuier.mapper.MenuMapper;
import com.yuier.domain.entity.Menu;
import com.yuier.service.MenuService;
import com.yuier.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author Yui
 * @since 2023-08-11 00:28:43
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    // 根据用户 id 获取权限列表
    @Override
    public List<String> selectPermsByUserId(Long id) {

        List<String> perms = null;

        if (id.equals(SystemConstants.SUPER_ADMIN_ID)) {
            perms = superAdminMenuPermsList();
        } else {
            perms = getBaseMapper().selectPermsByUserId(id);
        }

        return perms;
    }

    // 传入用户 id ，返回菜单树
    @Override
    public List<MenuVo> selectMenuTreeByUserId(Long id) {
        MenuMapper menuMapper = getBaseMapper();
        List<MenuVo> menuVoList;

        // 获取一维 menuVoList
        if (id.equals(SystemConstants.SUPER_ADMIN_ID)) {
            // 如果用户为超级管理员，返回所有符合要求的 Menu
            List<Menu> menuList = superAdminMenuList();
            menuVoList = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);
        } else {
            // 否则按照 id 查询 meanu
            List<Menu> menuList = menuMapper.selectMenuListByUserId(id);
            menuVoList = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);
        }
        // 先构建一个由第一层菜单组成的 tree ，然后以此为基础装配整个 tree
        List<MenuVo> menuVoTreeRoot = new ArrayList<>();
        for (MenuVo menuVo : menuVoList) {
            if (menuVo.getParentId().equals(SystemConstants.ROOT_MENU_ID)) {
                menuVoTreeRoot.add(menuVo);
            }
        }
        // 以第一层为基础创建 tree
        List<MenuVo> menuVoTree = toTree(menuVoTreeRoot, menuVoList);
        return menuVoTree;
    }

    // 返回全部菜单列表
    @Override
    public ResponseResult menuList(MenuListDto menuListDto) {
        // 如果传入了 status 或 name ，那么以这两条值进行模糊查询
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(
                StringUtils.hasText(menuListDto.getStatus()),
                Menu::getStatus,
                menuListDto.getStatus()
        );
        queryWrapper.like(
                StringUtils.hasText(menuListDto.getMenuName()),
                Menu::getMenuName,
                menuListDto.getMenuName()
        );
        queryWrapper.orderByAsc(Menu::getParentId);
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menuList = list(queryWrapper);
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(menuList, MenuListVo.class);
        return ResponseResult.okResult(menuListVos);
    }

    // 新增菜单
    @Override
    public ResponseResult addMenu(AddMenuDto addMenuDto) {
        Menu menu = BeanCopyUtils.copyBean(addMenuDto, Menu.class);
        save(menu);
        return ResponseResult.okResult();
    }

    // 获取菜单详情
    @Override
    public ResponseResult adminGetMenuDetail(Long id) {
        Menu menu = getById(id);
        AdminMenuDetailVo adminMenuDetailVo = BeanCopyUtils.copyBean(menu, AdminMenuDetailVo.class);
        return ResponseResult.okResult(adminMenuDetailVo);
    }

    // 修改菜单
    @Override
    public ResponseResult updateMenu(UpdateMenuDto updateMenuDto) {
        if (updateMenuDto.getParentId().equals(updateMenuDto.getId())) {
            throw new SystemException(AppHttpCodeEnum.PARENT_MENU_NOT_SELF);
        }
        Menu menu = BeanCopyUtils.copyBean(updateMenuDto, Menu.class);
        updateById(menu);
        return ResponseResult.okResult();
    }

    // 删除菜单
    @Override
    public ResponseResult deleteMenu(Long menuId) {
        // 判断是否有子菜单
        if (childrenMenuExists(menuId)) {
            throw new SystemException(AppHttpCodeEnum.CHILDREN_MENU_EXIST);
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    // 直接返回超级管理员的权限列表
    public List<String> superAdminMenuPermsList() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL);
        queryWrapper.in(
                Menu::getMenuType,
                SystemConstants.MENU_TYPE.CATEGORY,
                SystemConstants.MENU_TYPE.FUNCTION
        );
        List<String> validMenuPermsList = list(queryWrapper).stream().map(Menu::getPerms).toList();
        return validMenuPermsList;
    }

    // 直接返回超级管理员的 Menu 列表
    public List<Menu> superAdminMenuList() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL);
        queryWrapper.in(
                Menu::getMenuType,
                SystemConstants.MENU_TYPE.CATEGORY,
                SystemConstants.MENU_TYPE.MENU
        );
        return list(queryWrapper);
    }

    /**
     *  将一维的 menuVo 列表转为树结构
     *
     * @param parentMenuVoList  传入父菜单列表
     * @param menuVoList  原一维菜单列表
     * @return  以父菜单列表为起点，组装出一个菜单树
     */
    public List<MenuVo> toTree(List<MenuVo> parentMenuVoList, List<MenuVo> menuVoList) {
        for (MenuVo parentMenuVo : parentMenuVoList) {
            if (childrenExists(parentMenuVo.getId(), menuVoList)) {
                // 如果存在子菜单，递归地为 children 属性赋值
                List<MenuVo> childrenMenuList = getChildrenMenuList(parentMenuVo, menuVoList);
                parentMenuVo.setChildren(toTree(childrenMenuList, menuVoList));
            }
        }
        // 如果不存在子菜单，返回原列表
        return parentMenuVoList;
    }

    /**
     *  根据菜单 id 和菜单列表判断是否存在子菜单
     *
     * @param id  要查询的父菜单 id
     * @param menuVoList  在该列表中查询是否存在子菜单
     * @return  如果存在子菜单，返回 true，否则返回 false
     */
    public Boolean childrenExists(Long id, List<MenuVo> menuVoList) {
        Boolean flag = false;
        for (MenuVo menuVo : menuVoList) {
            if (menuVo.getParentId().equals(id)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     *  根据父菜单，取出菜单列表中的子菜单
     *
     * @param parentMenuVo  父菜单
     * @param menuVoList  从该列表中取出父菜单的子菜单
     * @return  由子菜单组成的列表
     */
    public List<MenuVo> getChildrenMenuList(MenuVo parentMenuVo, List<MenuVo> menuVoList) {
        List<MenuVo> childrenList = new ArrayList<>();
        for (MenuVo vo : menuVoList) {
            if (vo.getParentId().equals(parentMenuVo.getId())) {
                childrenList.add(vo);
            }
        }
        return childrenList;
    }

    private Boolean childrenMenuExists(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, menuId);
        List<Menu> childrenMenuList = list(queryWrapper);
        return childrenMenuList.size() > 0;
    }
}

