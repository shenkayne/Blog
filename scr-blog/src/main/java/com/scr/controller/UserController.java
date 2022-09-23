package com.scr.controller;

import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.domain.entity.User;
import com.scr.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "查询用户详情信息")
    @ApiOperation(value = "查询用户详情信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    @ApiOperation(value = "更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户信息"),
    }
    )
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "用户注册")
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户信息"),
    }
    )
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
