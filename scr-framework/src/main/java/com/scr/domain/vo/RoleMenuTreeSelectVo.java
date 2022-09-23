package com.scr.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author 小小西瓜
 * @Date 2022 09 23 11 52
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuTreeSelectVo {

    private List<Long> checkedKeys;

    private List<MenuTreeVo> menus;

}

