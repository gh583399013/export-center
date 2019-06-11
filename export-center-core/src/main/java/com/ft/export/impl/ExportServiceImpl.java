package com.ft.export.impl;

import com.ft.business.api.IOrderService;
import com.ft.business.param.MyOrderParam;
import com.ft.export.api.IExportCommonService;
import com.ft.export.api.IExportService;
import com.ft.export.enums.ExportTypeEnum;
import com.ft.export.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mask
 * @date 2019/6/11 14:09
 * @desc
 */
@Service("exportService")
public class ExportServiceImpl implements IExportService{

    @Autowired
    private SpringContextUtil springContextUtil;

    @Override
    public <T> void doExportJob(ExportTypeEnum exportTypeEnum, T t) {
        IExportCommonService exportCommonService = springContextUtil.getRealExportService(exportTypeEnum);
        Long totalCount = exportCommonService.findExportListCount(t);
        System.out.println(totalCount);
    }

    @Override
    public void asd() {
        System.out.println("@@@@@@@@@@@@@@@@");
    }
}
