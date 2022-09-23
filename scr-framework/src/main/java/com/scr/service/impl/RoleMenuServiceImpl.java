package com.scr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scr.domain.entity.RoleMenu;
import com.scr.mapper.RoleMenuMapper;
import com.scr.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @Author 小小西瓜
 * @Date 2022 09 23 11 29
 **/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public void deleteRoleMenuByRoleId(Long id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        remove(queryWrapper);
    }
}
