package com.ft.export.impl;

import com.ft.business.api.IOrderService;
import com.ft.business.param.MyOrderParam;
import com.ft.export.api.IExportCommonService;
import com.ft.export.api.IExportService;
import com.ft.export.constant.ExportCenterCommonConfig;
import com.ft.export.dto.ExportCoreInfo;
import com.ft.export.entity.ExportInfo;
import com.ft.export.enums.ExportTypeEnum;
import com.ft.export.util.ExcelCreator;
import com.ft.export.util.ExcelUtil;
import com.ft.export.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mask
 * @date 2019/6/11 14:09
 * @desc
 */
@Service("exportService")
public class ExportServiceImpl implements IExportService{

    @Value("${file.tmp.path}")
    private String fileTmpPath;

    @Autowired
    private SpringContextUtil springContextUtil;

    @Override
    public <T> void doExportJob(ExportInfo exportInfo, T t) {
        IExportCommonService exportCommonService = springContextUtil.getRealExportService(exportInfo.getExportTypeEnum());
        Integer totalCount = exportCommonService.findExportListCount(t);

        int totalPageCount = totalCount / ExportCenterCommonConfig.pageCount;
        List<T> dataList = new ArrayList<>(100000);

        String fileName = "asdasd";

        ExportCoreInfo exportCoreInfo = null;

        int sheetNo = 0;
        for (int i = 1; i < totalPageCount; i++) {
            //如果查询次数 > 50次 sheetNo要变成1了 然后dataList要清空
            int nextSheetNo = (i - 1) / ExportCenterCommonConfig.sheetMaxQueryTimes;
            if(sheetNo != nextSheetNo){
                ExcelCreator.outputExcelToDisk(dataList, exportCoreInfo, sheetNo);
                sheetNo = nextSheetNo;
                dataList.clear();
            }
            List<T> currentList = exportCommonService.findExportList(t);
            if(exportCoreInfo == null){
                exportCoreInfo = ExcelCreator.getExportCoreInfo(currentList, exportInfo.getFieldList(), fileTmpPath, fileName, ExcelUtil.VERSION_2007);
            }
            dataList.addAll(currentList);
        }
        //如果只有一个sheet, 或者到了最后一个sheet 因为没有触发sheetNo != nextSheetNo 所以在这里手动生成
        ExcelCreator.outputExcelToDisk(dataList, exportCoreInfo, sheetNo);

        System.out.println("生成结束");
    }

    @Override
    public void asd() {
        System.out.println("@@@@@@@@@@@@@@@@");
    }
}
