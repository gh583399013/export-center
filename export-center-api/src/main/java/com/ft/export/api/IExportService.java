package com.ft.export.api;

import com.ft.export.entity.ExportInfo;
import com.ft.export.enums.ExportTypeEnum;

import java.util.List;

/**
 * @author mask
 * @date 2019/6/11 13:59
 * @desc
 */
public interface IExportService {

    /**
     * 接入层调用导出方法
     * @param exportInfo
     * @param t
     * @param <T>
     */
    <T> void doExportJob(ExportInfo exportInfo, T t);

    void asd();
}
