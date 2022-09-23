package com.scr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.link.LinkListDto;
import com.scr.domain.dto.user.AddUserDto;
import com.scr.domain.dto.user.UserListDto;
import com.scr.domain.entity.User;
import com.scr.domain.vo.PageVo;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-08-28 00:28:29
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult<PageVo> pageUserList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult addUser(User user);
}

