package com.scr.controller;

import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.domain.entity.LoginUser;
import com.scr.domain.entity.Menu;
import com.scr.domain.entity.User;
import com.scr.domain.vo.AdminUserInfoVo;
import com.scr.domain.vo.RoutersVo;
import com.scr.domain.vo.UserInfoVo;
import com.scr.enums.AppHttpCodeEnum;
import com.scr.exception.SystemException;
import com.scr.service.LoginService;
import com.scr.service.MenuService;
import com.scr.service.RoleService;
import com.scr.utils.BeanCopyUtils;
import com.scr.utils.SecurityUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;


    @PostMapping("/user/login")
    @SystemLog(businessName = "后台用户登录")
    @ApiOperation(value = "后台用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户信息")
    }
    )
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    @SystemLog(businessName = "后台用户退出")
    @ApiOperation(value = "后台用户退出")
    public ResponseResult logout(){
        return loginService.logout();
    }

    @GetMapping("getInfo")
    @SystemLog(businessName = "获取后台用户相关信息")
    @ApiOperation(value = "获取后台用户信息")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }


    @GetMapping("getRouters")
    @SystemLog(businessName = "获取路由信息")
    @ApiOperation(value = "获取路由信息")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}
