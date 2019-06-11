package com.ft.export.api;

import com.ft.export.enums.ExportTypeEnum;

/**
 * @author mask
 * @date 2019/6/11 13:59
 * @desc
 */
public interface IExportService {
    /**
     * 接入层调用导出方法
     * @param exportTypeEnum
     * @param t
     * @param <T>
     */
    <T> void doExportJob(ExportTypeEnum exportTypeEnum, T t);

    void asd();
}
