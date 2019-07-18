package com.ft.export.api;

import com.ft.export.entity.ExportInfo;

/**
 * @author mask
 * @date 2019/6/11 13:59
 * @desc
 */
public interface IExportService {

    /**
     * 接入层调用导出方法
     * (侵入式 提供数据的dubbo接口需要继承 {@link com.ft.export.api.IExportCommonService})
     * @param exportInfo
     * @param t
     * @param <T>
     */
    <T> void doExportJob(ExportInfo exportInfo, T t);

    /**
     * 接入层调用导出方法
     * (非侵入式 提供数据的dubbo接口需要暴露这两个方法 并且参数只有一个T
     * @see com.ft.export.enums.ExportTypeProEnum#dataMethod
     * @see com.ft.export.enums.ExportTypeProEnum#countMethod
     * )
     * @param exportInfo
     * @param t
     * @param <T>
     */
    <T> void doExportJobPro(ExportInfo exportInfo, T t);
}
