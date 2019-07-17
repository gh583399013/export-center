package com.ft.export.impl;

import com.ft.export.api.IExportCommonService;
import com.ft.export.api.IExportService;
import com.ft.export.constant.ExportCenterCommonConfig;
import com.ft.export.dto.ExportCoreInfo;
import com.ft.export.entity.ExportInfo;
import com.ft.export.util.ExcelCreator;
import com.ft.export.util.ExcelUtil;
import com.ft.export.util.SpringContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mask
 * @date 2019/6/11 14:09
 * @desc
 */
@Service("exportService")
public class ExportServiceImpl implements IExportService{

    private Logger logger = LogManager.getLogger(ExportServiceImpl.class);

    @Value("${file.tmp.path}")
    private String fileTmpPath;

    @Autowired
    private SpringContextUtil springContextUtil;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private <T> void doExportJobCore(ExportInfo exportInfo, T t){
        IExportCommonService exportCommonService = springContextUtil.getExportCommonService(exportInfo.getExportTypeEnum());
        Integer totalCount = exportCommonService.findExportListCount(t);

        //0-2000 查询1次 2001-4000查询两次
        int totalPageCount = ((totalCount - 1) / ExportCenterCommonConfig.pageCount + 1);
        List<T> dataList = new ArrayList<>(100000);

        String fileName = "测试10w行数据";
        ExportCoreInfo exportCoreInfo = null;

        int sheetNo = 0;
        for (int page = 1; page <= totalPageCount; page++) {
            System.out.println("开始遍历10w行数据 第" + page + "页");

            //如果查询次数 > 50次 sheetNo要变成1了 然后dataList要清空
            int nextSheetNo = (page - 1) / ExportCenterCommonConfig.sheetMaxQueryTimes;
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

        //TODO 更新到处结果表
    }

    @Override
    public <T> void doExportJob(final ExportInfo exportInfo, final T t) {
        //TODO 记录到处记录
        //加入到线程池执行核心到处功能
        try {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    doExportJobCore(exportInfo, t);
                }
            });
        }catch (TaskRejectedException tre){
            //TODO 更新到处失败记录 配置的策略是接不下了就拒绝
            System.out.println("tiao guo ren wu");
            //tre.printStackTrace();
        } catch (Exception e) {
            //TODO 更新到处失败记录
            e.printStackTrace();
        }

    }

    @Override
    public void doExportTestData(ExportInfo exportInfo) {

    }

}
