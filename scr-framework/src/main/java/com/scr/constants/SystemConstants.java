package com.scr.constants;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;


    /**
     * 逻辑删除与否   0未删除,1逻辑删除
     */

    public static final int ISDELETE=0;

    /**
     * 友链状态为审核通过
     */
    public static final String  LINK_STATUS_NORMAL = "0";
    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 菜单项状态正常，0正常1停用
     */
    public static final String  STATUS_NORMAL = "0";

    /**
     * 菜单项类型
     */
    public static final String MENU = "C";
    public static final String BUTTON = "F";

    /**
     * 分类的状态正常，0正常 1禁用
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";
    /**
     * 管理员
     */
    public static final String ADMIN = "1";
}