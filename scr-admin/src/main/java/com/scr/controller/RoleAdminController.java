package com.scr.controller;

import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.article.ArticleListDto;
import com.scr.domain.dto.article.EditArticleDto;
import com.scr.domain.dto.role.ChangeRoleStatusDto;
import com.scr.domain.dto.role.EditRoleDto;
import com.scr.domain.dto.role.RoleListDto;
import com.scr.domain.entity.Article;
import com.scr.domain.entity.Role;
import com.scr.domain.vo.PageVo;
import com.scr.service.RoleService;
import com.scr.utils.BeanCopyUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 沈才人
 * @Date 2022 09 04 15 29
 **/

@RestController
@RequestMapping("/system/role")
public class RoleAdminController {

    @Autowired
    RoleService roleService;

    @GetMapping("/listAllRole")
    @PreAuthorize("@ps.hasPermission('system:role:query')")
    @SystemLog(businessName = "获取角色列表")
    @ApiOperation(value = "获取角色信息")
    public ResponseResult listAllRole(){
        return ResponseResult.okResult(roleService.list());
    }

    /**
     * 新增角色
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('system:role:add')")
    @SystemLog(businessName = "新增角色")
    @ApiOperation(value = "新增角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role",value = "角色信息")
    }
    )
    public ResponseResult add( @RequestBody Role role)
    {
        roleService.insertRole(role);
        return ResponseResult.okResult();

    }
    /**
     * 获取所有角色列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:role:query')")
    @SystemLog(businessName = "分页获取所有角色列表，包括添加")
    @ApiOperation(value = "分页获取所有角色列表，包括添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleListDto",value = "角色列表切片"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult<PageVo> getAllRolelist(Integer pageNum, Integer pageSize, RoleListDto roleListDto){
        return roleService.pageRoleList(pageNum,pageSize,roleListDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:role:remove')")
    @SystemLog(businessName = "后台删除角色")
    @ApiOperation(value = "后台删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "角色id"),
    }
    )
    public ResponseResult delete(@PathVariable Long id){
        roleService.removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 修改保存角色
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('system:role:edit')")
    @SystemLog(businessName = "后台修改角色")
    @ApiOperation(value = "后台修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role",value = "角色信息")
    }
    )
    public ResponseResult edit(@RequestBody Role role)
    {
        roleService.updateRole(role);
        return ResponseResult.okResult();
    }


    @PutMapping("/changeStatus")
    @SystemLog(businessName = "切换角色状态")
    @ApiOperation(value = "切换角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleStatusDto",value = "角色状态切片")
    }
    )
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto){
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        return ResponseResult.okResult(roleService.updateById(role));
    }

    /**
     * 获取相关信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @SystemLog(businessName = "获取角色相关信息")
    @ApiOperation(value = "获取角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "角色id"),
    }
    )
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Role role = roleService.getById(id);
        return ResponseResult.okResult(role);
    }
}
