package com.ft.export;

import com.ft.export.api.IExportService;
import com.ft.export.api.ITestService;
import com.ft.export.entity.ExportInfo;
import com.ft.export.enums.ExportTypeEnum;
import com.ft.export.enums.ExportTypeProEnum;
import com.ft.test.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class JunitTest extends BaseTest {

    @Autowired
    private IExportService exportService;

    @Autowired
    private ITestService testService;

    @Before
    public void beforeFunc(){
        System.out.println("================> 开始");
    }

    @After
    public void afterFunc(){
        System.out.println("================> 结束");
    }

    @Test
    public void test001(){
        System.out.println("11111");
        //exportService.doExportJob(ExportTypeEnum.OMS_MY_ORDER_PAGE, new MyOrderParam());
        System.out.println("22222");
    }

    @BeforeClass
    public static void init(){
        System.setProperty("env", "dev");
    }

    @Test
    public void doExportJobPro(){
        List<String> colName = new ArrayList<>();
        colName.add("orderNo");
        colName.add("orderTime");
        colName.add("orderStatus");
        colName.add("totalPrice");
        colName.add("DoublePride");

        ExportInfo exportInfo = new ExportInfo();
        exportInfo.setFieldList(colName);
        //exportInfo.setExportTypeEnum(ExportTypeEnum.OMS_MY_ORDER_PAGE);
        exportInfo.setExportTypeProEnum(ExportTypeProEnum.OMS_MY_ORDER_PAGE);
        exportService.doExportJobPro(exportInfo, null);

        //junit多线程测试的坑, 主线程休眠比较简单的解决方法
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test002(){
        System.out.println("11111");

        List<String> colName = new ArrayList<>();
        colName.add("orderNo");
        colName.add("orderTime");
        colName.add("orderStatus");
        colName.add("totalPrice");
        colName.add("DoublePride");

        ExportInfo exportInfo = new ExportInfo();
        exportInfo.setFieldList(colName);
        exportInfo.setExportTypeEnum(ExportTypeEnum.OMS_MY_ORDER_PAGE);
        exportService.doExportJob(exportInfo, null);
        System.out.println("22222");


    }

    @Test
    public void test003(){
        System.out.println("11111");

        List<String> colName = new ArrayList<>();
        colName.add("orderNo");
        colName.add("orderTime");
        colName.add("orderStatus");
        colName.add("totalPrice");
        colName.add("DoublePride");

        ExportInfo exportInfo = new ExportInfo();
        exportInfo.setFieldList(colName);
        exportInfo.setExportTypeEnum(ExportTypeEnum.OMS_MY_ORDER_PAGE);
        //exportService.doExportTestData(exportInfo);
        System.out.println("22222");


    }

    @Test
    public void test004(){
        ExportInfo exportInfo = new ExportInfo();
        exportInfo.setExportTypeProEnum(ExportTypeProEnum.OMS_MY_ORDER_PAGE);
        testService.testGetBeanAndGetData(exportInfo, null);
    }

    @Test
    public void test005(){
        List<String> colName = new ArrayList<>();
        colName.add("orderNo");
        colName.add("orderTime");
        colName.add("orderStatus");
        colName.add("totalPrice");
        colName.add("DoublePride");

        ExportInfo exportInfo = new ExportInfo();
        exportInfo.setFieldList(colName);
        //exportInfo.setExportTypeEnum(ExportTypeEnum.OMS_MY_ORDER_PAGE);
        exportInfo.setExportTypeProEnum(ExportTypeProEnum.OMS_MY_ORDER_PAGE);
        exportService.doExportJobPro(exportInfo, null);

        //junit多线程测试的坑, 主线程休眠比较简单的解决方法
        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test006(){
        ExportInfo exportInfo = new ExportInfo();
        exportInfo.setExportTypeProEnum(ExportTypeProEnum.OMS_MY_ORDER_PAGE);
        testService.testBuildExportInfo();
    }

}