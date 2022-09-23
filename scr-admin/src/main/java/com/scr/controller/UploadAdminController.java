package com.scr.controller;


import com.scr.domain.ResponseResult;
import com.scr.service.UploadService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadAdminController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation(value = "图片下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "img",value = "图片详情")
    }
    )
    public ResponseResult uploadImg(MultipartFile img) throws IOException {
        return uploadService.uploadImg(img);
    }
}
