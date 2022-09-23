package com.scr.controller;

import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.menu.EditMenuDto;
import com.scr.domain.dto.menu.MenuListDto;
import com.scr.domain.dto.role.EditRoleDto;
import com.scr.domain.dto.role.RoleListDto;
import com.scr.domain.entity.Menu;
import com.scr.domain.entity.Role;
import com.scr.domain.vo.MenuTreeVo;
import com.scr.domain.vo.MenuVo;
import com.scr.domain.vo.PageVo;
import com.scr.domain.vo.RoleMenuTreeSelectVo;
import com.scr.service.MenuService;
import com.scr.utils.BeanCopyUtils;
import com.scr.utils.SystemConverter;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 沈才人
 * @Date 2022 09 04 17 06
 **/

@RestController
@RequestMapping("/system/menu")
public class MenuAdminController {
    @Autowired
    private MenuService menuService;
    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")

    @ApiOperation(value = "获取菜单下拉树列表")
    public ResponseResult treeselect() {
        //复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")

    @ApiOperation(value = "加载对应角色菜单列表树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色id")
    }
    )
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }
    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menu",value = "菜单信息")
    }
    )
    public ResponseResult list(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @PostMapping

    @ApiOperation(value = "添加菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menu",value = "菜单信息")
    }
    )
    public ResponseResult add(@RequestBody Menu menu)
    {
        menuService.save(menu);
        return ResponseResult.okResult();
    }


    /**
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}")

    @ApiOperation(value = "根据菜单编号获取详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId",value = "菜单id")
    }
    )
    public ResponseResult getInfo(@PathVariable Long menuId)
    {
        return ResponseResult.okResult(menuService.getById(menuId));
    }

    /**
     * 修改菜单
     */
    @PutMapping

    @ApiOperation(value = "修改菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menu",value = "菜单信息")
    }
    )
    public ResponseResult edit(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")

    @ApiOperation(value = "删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId",value = "菜单id")
    }
    )
    public ResponseResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChild(menuId)) {
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }
}
