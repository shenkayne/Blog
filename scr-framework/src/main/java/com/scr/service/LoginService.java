package com.scr.service;

import com.scr.domain.ResponseResult;
import com.scr.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);


    ResponseResult logout();
}
