package com.scr.controller;

import com.scr.annotation.SystemLog;
import com.scr.domain.ResponseResult;
import com.scr.domain.dto.tag.AddTagDto;
import com.scr.domain.dto.tag.EditTagDto;
import com.scr.domain.dto.tag.TagListDto;
import com.scr.domain.entity.Tag;
import com.scr.domain.vo.PageVo;
import com.scr.service.TagService;
import com.scr.utils.BeanCopyUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 查询标签列表
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    @GetMapping("/list")
    @SystemLog(businessName = "获取标签列表")
    @ApiOperation(value = "获取标签列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagListDto",value = "标签列表切片"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    /**
     * 添加
     * @param tagDto
     * @return
     */
    @PostMapping
    @SystemLog(businessName = "添加标签")
    @ApiOperation(value = "添加标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagDto",value = "标签切片")
    }
    )
    public ResponseResult add(@RequestBody AddTagDto tagDto){
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除标签")
    @ApiOperation(value = "删除标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "标签id")
    }
    )
    public ResponseResult delete(@PathVariable Long id){
        tagService.removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 修改
     * @param tagDto
     * @return
     */
    @PutMapping
    @SystemLog(businessName = "修改标签")
    @ApiOperation(value = "修改标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagDto",value = "修改标签切片")
    }
    )
    public ResponseResult edit(@RequestBody EditTagDto tagDto){
        Tag tag = BeanCopyUtils.copyBean(tagDto,Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }


    /**
     * 获取相关权限信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @SystemLog(businessName = "获取标签相关信息")
    @ApiOperation(value = "获取标签相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "标签id")
    }
    )
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Tag tag = tagService.getById(id);
        return ResponseResult.okResult(tag);
    }

    /**
     * 写文章首页菜单项接口
     * @return
     */
    @GetMapping(value = "/listAllTag")
    @SystemLog(businessName = "获取首页菜单项标签信息")
    @ApiOperation(value = "获取首页菜单项标签信息")
    public ResponseResult listAllTag(){
        return tagService.getAllTags();
    }
}
