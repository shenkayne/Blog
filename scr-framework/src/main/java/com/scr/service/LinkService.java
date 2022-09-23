package com.scr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.link.LinkListDto;
import com.scr.domain.entity.Link;
import com.scr.domain.vo.PageVo;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-08-28 12:22:53
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult<PageVo> pageLinkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto);
}

