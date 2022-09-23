package com.scr.service;

import com.scr.domain.ResponseResult;
import com.scr.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();

}
