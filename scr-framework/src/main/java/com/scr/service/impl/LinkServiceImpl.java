package com.scr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scr.constants.SystemConstants;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.link.LinkListDto;
import com.scr.domain.entity.Link;
import com.scr.domain.vo.LinkVo;
import com.scr.domain.vo.PageVo;
import com.scr.mapper.LinkMapper;
import com.scr.service.LinkService;
import com.scr.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-08-28 12:22:56
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult<PageVo> pageLinkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(linkListDto.getName()),Link::getName,linkListDto.getName());
        queryWrapper.eq(StringUtils.hasText(linkListDto.getStatus()),Link::getStatus,linkListDto.getStatus());
        Page page=new Page();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}

