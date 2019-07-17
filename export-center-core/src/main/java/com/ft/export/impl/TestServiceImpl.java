package com.ft.export.impl;

import com.ft.business.resp.MyOrderPageResp;
import com.ft.export.api.ITestService;
import com.ft.export.dto.ExportCoreInfo;
import com.ft.export.entity.ExportInfo;
import com.ft.export.util.ExcelCreator;
import com.ft.export.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mask
 * @date 2019/7/17 23:18
 * @desc
 */
@Service("testService")
public class TestServiceImpl implements ITestService {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Value("${file.tmp.path}")
    private String fileTmpPath;

    @Override
    public void testThreadPool() {
        for (int i = 0; i < 5; i++) {

            try {
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("##########1");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch (TaskRejectedException tre){
                System.out.println("tiao guo ren wu");
                //tre.printStackTrace();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    @Override
    public void testGetData(ExportInfo exportInfo) {
        List dataList = new ArrayList<MyOrderPageResp>(20000);
        MyOrderPageResp myOrderPageResp = new MyOrderPageResp();
        myOrderPageResp.setOrderNo("111");
        myOrderPageResp.setDoublePride(2.3d);
        myOrderPageResp.setTotalPrice(new BigDecimal(2.9d));
        myOrderPageResp.setOrderStatus(2);
        myOrderPageResp.setOrderTime(new Date());
        for (int i = 0; i < 10000; i++) {
            dataList.add(myOrderPageResp);
            dataList.add(myOrderPageResp);
        }

        String fileName = "测试10w行数据";
        ExportCoreInfo exportCoreInfo = ExcelCreator.getExportCoreInfo(dataList, exportInfo.getFieldList(), fileTmpPath, fileName, ExcelUtil.VERSION_2007);
        //如果只有一个sheet, 或者到了最后一个sheet 因为没有触发sheetNo != nextSheetNo 所以在这里手动生成
        ExcelCreator.outputExcelToDisk(dataList, exportCoreInfo, 0);
    }
}
