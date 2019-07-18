package com.ft.export.api;

import com.ft.export.entity.ExportInfo;

/**
 * @author mask
 * @date 2019/7/17 23:18
 * @desc
 */
public interface ITestService {
    void testThreadPool();
    void testGetData(ExportInfo exportInfo);
    void testGetBeanAndGetData();
}
