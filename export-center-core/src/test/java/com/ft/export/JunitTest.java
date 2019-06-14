package com.ft.export;

import com.ft.business.param.MyOrderParam;
import com.ft.export.enums.ExportTypeEnum;
import com.ft.test.BaseTest;
import com.ft.export.api.IExportService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JunitTest extends BaseTest {

    @Autowired
    private IExportService exportService;

    @Test
    public void test001(){
        System.out.println("11111");
        //exportService.doExportJob(ExportTypeEnum.OMS_MY_ORDER_PAGE, new MyOrderParam());
        System.out.println("22222");
    }


    @Test
    public void test002(){
        System.out.println("11111");
        //exportService.doExportJob(ExportTypeEnum.OMS_MY_ORDER_PAGE, new MyOrderParam());
        System.out.println("22222");
    }


}