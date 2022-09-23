package com.scr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scr.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-28 22:36:44
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}

