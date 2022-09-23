package com.scr.controller;

import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.category.AddCategoryDto;
import com.scr.domain.dto.category.CategoryListDto;
import com.scr.domain.dto.category.EditCategoryDto;
import com.scr.domain.dto.link.AddLinkDto;
import com.scr.domain.dto.link.EditLinkDto;
import com.scr.domain.dto.link.LinkListDto;
import com.scr.domain.entity.Category;
import com.scr.domain.entity.Link;
import com.scr.domain.vo.PageVo;
import com.scr.service.LinkService;
import com.scr.utils.BeanCopyUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 沈才人
 * @Date 2022 09 04 15 03
 **/

@RestController
@RequestMapping("/content/link")
public class LinkAdminController {
    @Autowired
    private LinkService linkService;

    /**
     * 获取所有友链列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('content:link:query')")
    @SystemLog(businessName = "获取所有友链列表")
    @ApiOperation(value = "获取所有友链列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkListDto",value = "友链切片"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult<PageVo> getAllLinklist(Integer pageNum, Integer pageSize, LinkListDto linkListDto){
        return linkService.pageLinkList(pageNum,pageSize,linkListDto);
    }

    /**
     * 添加
     * @param linkDto
     * @return
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('content:link:add')")
    @SystemLog(businessName = "后台添加友链")
    @ApiOperation(value = "后台添加友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkDto",value = "友链切片")
    }
    )
    public ResponseResult add(@RequestBody AddLinkDto linkDto){
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        linkService.save(link);
        return ResponseResult.okResult();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:link:remove')")
    @SystemLog(businessName = "后台删除友链信息")
    @ApiOperation(value = "后台删除友链信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "友链id")
    }
    )
    public ResponseResult delete(@PathVariable Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 修改
     * @param linkDto
     * @return
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('content:link:edit')")
    @SystemLog(businessName = "后台修改友链")
    @ApiOperation(value = "后台修改友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkDto",value = "修改友链切片")
    }
    )
    public ResponseResult edit(@RequestBody EditLinkDto linkDto){
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        linkService.updateById(link);
        return ResponseResult.okResult();
    }


    /**
     * 获取相关权限信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @SystemLog(businessName = "获取友链相关权限")
    @ApiOperation(value = "获取友链相关权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "友链id")
    }
    )
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }

}
