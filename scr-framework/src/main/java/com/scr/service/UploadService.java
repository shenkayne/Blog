package com.scr.service;

import com.scr.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img) throws IOException;
}
