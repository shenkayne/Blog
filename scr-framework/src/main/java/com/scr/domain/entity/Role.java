package com.scr.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 角色信息表(Role)表实体类
 *
 * @author makejava
 * @since 2022-08-28 22:36:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role  {
    //角色ID@TableId
    private Long id;

    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    @TableField(exist = false)
    //数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
    private String dataScope;
    @TableField(exist = false)
    //菜单树选择项是否关联显示
    private Integer menuCheckStrictly;
    //角色状态（0正常 1停用）
    private String status;
    //删除标志（0代表存在 2代表删除）
    private String delFlag;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //备注
    private String remark;
    //关联菜单id数组，不是表中的字段  用来接收参数使用
    @TableField(exist = false)
    private Long[] menuIds;
}
