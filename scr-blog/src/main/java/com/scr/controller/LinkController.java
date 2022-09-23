package com.scr.controller;

import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.service.LinkService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(businessName = "获取友链信息")
    @ApiOperation(value = "获取友链信息")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
