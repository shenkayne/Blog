package com.scr.controller;

import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.link.AddLinkDto;
import com.scr.domain.dto.link.EditLinkDto;
import com.scr.domain.dto.link.LinkListDto;
import com.scr.domain.dto.user.AddUserDto;
import com.scr.domain.dto.user.EditUserDto;
import com.scr.domain.dto.user.UserListDto;
import com.scr.domain.entity.Link;
import com.scr.domain.entity.User;
import com.scr.domain.vo.PageVo;
import com.scr.service.UserService;
import com.scr.utils.BeanCopyUtils;
import com.scr.utils.SecurityUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 沈才人
 * @Date 2022 09 04 13 24
 **/

@RestController
@RequestMapping("/system/user")
public class UserAdminController {

    @Autowired
    UserService userService;

    /**
     * 获取所有用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('system:user:query')")
    @SystemLog(businessName = "获取所有用户列表")
    @ApiOperation(value = "获取所有用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userListDto",value = "用户列表切片"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult<PageVo> getAllUserlist(Integer pageNum, Integer pageSize, UserListDto userListDto){
        return userService.pageUserList(pageNum,pageSize,userListDto);
    }

    /**
     * 添加
     * @param user
     * @return
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('system:user:add')")
    @SystemLog(businessName = "后台添加用户")
    @ApiOperation(value = "后台添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户信息")
    }
    )
    public ResponseResult add(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * 删除
     * @param userIds
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:user:remove')")
    @SystemLog(businessName = "后台删除用户")
    @ApiOperation(value = "后台删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds",value = "用户id集合"),
    }
    )
    public ResponseResult delete(@PathVariable List<Long> userIds){
        if(userIds.contains(SecurityUtils.getUserId())){
            return ResponseResult.errorResult(500,"不能删除当前你正在使用的用户");
        }
        userService.removeByIds(userIds);
        return ResponseResult.okResult();
    }


    /**
     * 修改
     * @param user
     * @return
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('system:user:edit')")
    @SystemLog(businessName = "后台修改用户")
    @ApiOperation(value = "后台修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户信息")
    }
    )
    public ResponseResult edit(@RequestBody User user){
        userService.updateById(user);
        return ResponseResult.okResult();
    }


    /**
     * 获取相关信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @SystemLog(businessName = "获取用户相关信息")
    @ApiOperation(value = "获取用户相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id")
    }
    )
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        User user = userService.getById(id);
        return ResponseResult.okResult(user);
    }


}
