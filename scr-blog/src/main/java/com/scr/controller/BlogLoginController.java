package com.scr.controller;

import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.domain.entity.User;
import com.scr.enums.AppHttpCodeEnum;
import com.scr.exception.SystemException;
import com.scr.service.BlogLoginService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "登录")
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户信息"),
    }
    )
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @PostMapping("/logout")
    @SystemLog(businessName = "退出")
    @ApiOperation(value = "退出")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
