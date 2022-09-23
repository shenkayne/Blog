package com.scr.utils;

/**
 * @Author 沈才人
 * @Date 2022 09 04 16 27
 **/

import java.util.UUID;

/**
 * 获取UUID工具类
 */
public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
