package com.ft.export.enums;

import com.ft.business.api.IOrderService;
import com.ft.business.param.MyOrderParam;
import com.ft.business.resp.MyOrderPageResp;

import java.io.Serializable;

/**
 * @author mask
 * @date 2019/6/10 17:31
 * @desc
 */
public enum ExportTypeEnum implements Serializable{
    OMS_MY_ORDER_PAGE(BusinessEnum.SYSTEM_OMS, "orderService", IOrderService.class, MyOrderParam.class, MyOrderPageResp.class, "OMS我的订单页导出");

    ExportTypeEnum(BusinessEnum businessEnum, String beanId, Class dataSourceClass, Class paramClz, Class respClz, String desc) {
        this.businessEnum = businessEnum;
        this.beanId = beanId;
        this.dataSourceClass = dataSourceClass;
        this.paramClz = paramClz;
        this.respClz = respClz;
        this.desc = desc;
    }

    private BusinessEnum businessEnum;
    private String beanId;
    private Class dataSourceClass;
    private Class paramClz;
    private Class respClz;
    private String desc;

    public BusinessEnum getBusinessEnum() {
        return businessEnum;
    }

    public void setBusinessEnum(BusinessEnum businessEnum) {
        this.businessEnum = businessEnum;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public Class getDataSourceClass() {
        return dataSourceClass;
    }

    public void setDataSourceClass(Class dataSourceClass) {
        this.dataSourceClass = dataSourceClass;
    }

    public Class getParamClz() {
        return paramClz;
    }

    public void setParamClz(Class paramClz) {
        this.paramClz = paramClz;
    }

    public Class getRespClz() {
        return respClz;
    }

    public void setRespClz(Class respClz) {
        this.respClz = respClz;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
