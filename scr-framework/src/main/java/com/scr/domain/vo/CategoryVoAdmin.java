package com.scr.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 沈才人
 * @Date 2022 09 03 15 03
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVoAdmin {
    private Long id;
    private String name;
    private String description;
}
