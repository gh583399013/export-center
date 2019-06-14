package com.ft.export.api;

import java.util.List;

/**
 * @author mask
 * @date 2019/6/10 17:29
 * @desc
 */
public interface IExportCommonService<T, V> {
    /**
     * 查询需要导出的数量
     * @param t
     * @return
     */
    Integer findExportListCount(T t);

    /**
     * 分页查询导出的数据
     * @param t
     * @return
     */
    List<V> findExportList(T t);
}
