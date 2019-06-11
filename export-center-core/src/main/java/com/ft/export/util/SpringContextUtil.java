package com.ft.export.util;

import com.ft.export.api.IExportCommonService;
import com.ft.export.enums.ExportTypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //取值参考 https://blog.csdn.net/qq_23927391/article/details/80625578
    public <T>T getRealExportService(ExportTypeEnum exportTypeEnum){
        //(T)applicationContext.getBean(exportTypeEnum.getBeanId());
        return (T)applicationContext.getBean(exportTypeEnum.getBeanId());
    }

    //取值参考 https://blog.csdn.net/qq_23927391/article/details/80625578
    //突然觉得 这里直接返回IExportCommonService 就好了 无需泛型
    public IExportCommonService getExportCommonService(ExportTypeEnum exportTypeEnum){
        //(T)applicationContext.getBean(exportTypeEnum.getBeanId());
        return (IExportCommonService)applicationContext.getBean(exportTypeEnum.getBeanId(), exportTypeEnum.getDataSourceClass());
    }
}