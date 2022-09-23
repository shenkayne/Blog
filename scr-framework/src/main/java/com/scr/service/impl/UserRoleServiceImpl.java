package com.scr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scr.domain.entity.UserRole;
import com.scr.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;
import com.scr.service.UserRoleService;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-09-04 16:38:04
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

