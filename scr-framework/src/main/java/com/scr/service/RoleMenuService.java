package com.scr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scr.domain.entity.RoleMenu;

/**
 * @Author 小小西瓜
 * @Date 2022 09 23 11 27
 **/
public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}
