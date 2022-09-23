package com.scr.domain.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 沈才人
 * @Date 2022 09 04 14 22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {
    //标题
    private String title;
    //文章摘要
    private String summary;
}
