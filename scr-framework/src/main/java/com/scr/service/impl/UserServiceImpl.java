package com.scr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.user.AddUserDto;
import com.scr.domain.dto.user.UserListDto;
import com.scr.domain.entity.User;
import com.scr.domain.vo.PageVo;
import com.scr.domain.vo.UserInfoVo;
import com.scr.enums.AppHttpCodeEnum;
import com.scr.exception.SystemException;
import com.scr.mapper.UserMapper;
import com.scr.service.UserService;
import com.scr.utils.BeanCopyUtils;
import com.scr.utils.SecurityUtils;
import com.scr.domain.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.scr.service.UserRoleService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-08-28 00:28:30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    private void insertUserRole(User user) {
        List<UserRole> sysUserRoles = Arrays.stream(user.getRoleIds())
                .map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(sysUserRoles);
    }
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo> pageUserList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(userListDto.getUserName()),User::getUserName,userListDto.getUserName());
        queryWrapper.eq(StringUtils.hasText(userListDto.getPhonenumber()),User::getPhonenumber,userListDto.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(userListDto.getStatus()),User::getStatus,userListDto.getStatus());
        Page page=new Page();
        page.setCurrent(1);
        page.setSize(10);
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public ResponseResult addUser(User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        userService.save(user);
        if(user.getRoleIds()!=null&&user.getRoleIds().length>0){
            insertUserRole(user);
        }

        return ResponseResult.okResult();
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }


}

