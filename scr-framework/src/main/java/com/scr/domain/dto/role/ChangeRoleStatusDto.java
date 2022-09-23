package com.scr.domain.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 小小西瓜
 * @Date 2022 09 23 11 40
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoleStatusDto {

    private Long roleId;
    private String status;
}
