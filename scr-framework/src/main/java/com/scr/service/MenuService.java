package com.scr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.menu.MenuListDto;
import com.scr.domain.entity.Menu;
import com.scr.domain.vo.PageVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-08-28 22:32:09
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

//    ResponseResult<PageVo> pageRoleList(Integer pageNum, Integer pageSize, MenuListDto menuListDto);

    ResponseResult pageMenuList(MenuListDto menuListDto);

    List<Menu> selectMenuList(Menu menu);

    boolean hasChild(Long menuId);

    List<Long> selectMenuListByRoleId(Long roleId);
}

