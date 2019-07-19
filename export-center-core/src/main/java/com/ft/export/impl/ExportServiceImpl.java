package com.ft.export.impl;

import com.ft.export.api.IExportCommonService;
import com.ft.export.api.IExportService;
import com.ft.export.constant.ExportCenterCommonConfig;
import com.ft.export.dto.ExportCoreInfo;
import com.ft.export.entity.ExportInfo;
import com.ft.export.enums.ExceptionTypeEnum;
import com.ft.export.enums.ExportTypeProEnum;
import com.ft.export.exception.ExportException;
import com.ft.export.util.ExcelCreator;
import com.ft.export.util.ExcelUtil;
import com.ft.export.util.ExportCoreInfoBuildUtil;
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
                ExcelCreator.outputExcel(dataList, exportCoreInfo, sheetNo);
                sheetNo = nextSheetNo;
                dataList.clear();
            }

            List<T> currentList = exportCommonService.findExportList(t);
            if(exportCoreInfo == null){
                try {
                    exportCoreInfo = ExportCoreInfoBuildUtil.getExportCoreInfo(currentList, exportInfo.getFieldList(), fileTmpPath, fileName, ExcelUtil.VERSION_2007);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dataList.addAll(currentList);
        }
        //如果只有一个sheet, 或者到了最后一个sheet 因为没有触发sheetNo != nextSheetNo 所以在这里手动生成
        ExcelCreator.outputExcel(dataList, exportCoreInfo, sheetNo);

        //TODO 更新到处结果表
    }

    /**
     * 核心导出方法
     * 这里可以不抛异常 返回一个异常结果类亦可
     * @param exportInfo
     * @param t
     * @param <T>
     * @throws Exception
     */
    private <T> void doExportJobProCore(ExportInfo exportInfo, T t) throws Exception{
        ExportTypeProEnum exportTypeProEnum = exportInfo.getExportTypeProEnum();

        //初始化核心导出信息类
        // TODO id自增文件名
        String fileName = "asdasdasd";
        ExportCoreInfo exportCoreInfo = null;
        try {
            exportCoreInfo = ExportCoreInfoBuildUtil.getExportCoreInfo(exportTypeProEnum.getDataClass(), exportInfo.getFieldList(), fileTmpPath, fileName, ExcelUtil.VERSION_2007);
        } catch (Exception e) {
            if(e instanceof ExportException){
                throw e;
            }else{
                throw new ExportException(ExceptionTypeEnum.BUILD_EXPORT_INFO_FAIL);
            }
        }

        //获取dubbo接口
        Object dataSourceService = springContextUtil.getExportCoreService(exportTypeProEnum);
        Integer totalCount = null;
        try {
            totalCount = Integer.valueOf(exportTypeProEnum.getGetCountMethod().invoke(dataSourceService, t).toString());
            if(totalCount <= 0){
                //return ExportResult.exportFail(ExceptionTypeEnum.DATA_EMPTY);
                throw new ExportException(ExceptionTypeEnum.DATA_EMPTY);
            }
        } catch (Exception e) {
            //return ExportResult.exportFail(exportTypeProEnum.getDataSourceClass(), exportTypeProEnum.getGetCountMethod());
            if(e instanceof ExportException){
                throw e;
            }else{
                throw ExportException.getExportException(exportTypeProEnum.getDataSourceClass(), exportTypeProEnum.getGetCountMethod());
            }
        }

        int sheetNo = 0;
        //0-2000 查询1次 2001-4000查询两次
        int totalPageCount = ((totalCount - 1) / ExportCenterCommonConfig.pageCount + 1);
        List<T> dataList = new ArrayList<>(100000);
        for (int page = 1; page <= totalPageCount; page++) {
            //拉取数据
            List<T> currentList = null;
            try {
                currentList = (List) exportTypeProEnum.getGetDataMethod().invoke(dataSourceService, t);
                //TODO 这里要自己控制分页
                if(currentList == null || currentList.size() == 0){
                    //return ExportResult.exportFail(ExceptionTypeEnum.DATA_EMPTY);
                    throw new ExportException(ExceptionTypeEnum.DATA_EMPTY);
                }
//                //初始化核心导出信息类 这里通过注解里面的dataClass来初始化  不需要取数据了 取数据在初始化也不合理 应该先校验 导出信息合不合法 再取数据
//                if(exportCoreInfo == null){
//                    try {
//                        exportCoreInfo = ExcelCreator.getExportCoreInfo(currentList, exportInfo.getFieldList(), fileTmpPath, fileName, ExcelUtil.VERSION_2007);
//                    } catch (Exception e) {
//                        throw new ExportException(ExceptionTypeEnum.DATA_EMPTY);
//                    }
//                }
            } catch (Exception e) {
                //return ExportResult.exportFail(exportTypeProEnum.getDataSourceClass(), exportTypeProEnum.getGetCountMethod());
                if(e instanceof ExportException){
                    throw e;
                }else{
                    throw ExportException.getExportException(exportTypeProEnum.getDataSourceClass(), exportTypeProEnum.getGetCountMethod());
                }
            }
            dataList.addAll(currentList);
        }
        ExcelCreator.outputExcel(dataList, exportCoreInfo, sheetNo);
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
        } catch (Exception e) {
            //TODO 更新到处失败记录
            e.printStackTrace();
        }

    }

    @Override
    public <T> void doExportJobPro(final ExportInfo exportInfo, final T t) {
        //TODO 记录到处记录
        try {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        doExportJobProCore(exportInfo, t);
                    }catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
        }catch (TaskRejectedException tre){
            //TODO 更新到处失败记录 配置的策略是接不下了就拒绝
        } catch (Exception e) {
            //TODO 更新到处失败记录
            e.printStackTrace();
        }
    }

}
