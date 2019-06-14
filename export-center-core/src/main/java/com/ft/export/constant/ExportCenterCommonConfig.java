package com.ft.export.constant;

import java.io.Serializable;

/**
 * @author mask
 * @date 2019/6/14 18:20
 * @desc
 */
public class ExportCenterCommonConfig implements Serializable{
    //每页查询量
    public static final Integer pageCount = 1;

    //单sheet最多写的行数
    public static final Integer sheetMaxCount = 1;

    //写满单sheet需要查询最大次数
    public static final Integer sheetMaxQueryTimes = sheetMaxCount / pageCount;
}
