package com.ft.export.enums;

import com.ft.business.api.IOrderService;

import java.lang.reflect.Method;

/**
 * @author mask
 * @date 2019/7/18 21:49
 * @desc
 */
public enum ExportTypeProEnum {

    OMS_MY_ORDER_PAGE(BusinessEnum.SYSTEM_OMS, "orderService", IOrderService.class, "OMS我的订单页导出");

    ExportTypeProEnum(BusinessEnum businessEnum, String beanId, Class dataSourceClass, String desc) {
        this.businessEnum = businessEnum;
        this.beanId = beanId;
        this.dataSourceClass = dataSourceClass;
        this.desc = desc;
        Method[] methods = dataSourceClass.getMethods();
        for (Method method : methods) {
            if(dataMethod.equals(method.getName())){
                this.getDataMethod = method;
            }
            if(countMethod.equals(method.getName())){
                this.getCountMethod = method;
            }
        }
    }

    private final String dataMethod = "findByCondition";
    private final String countMethod = "findCountByCondition";
    private BusinessEnum businessEnum;
    private String beanId;
    private Class dataSourceClass;
    private Method getDataMethod;
    private Method getCountMethod;
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

    public Method getGetDataMethod() {
        return getDataMethod;
    }

    public void setGetDataMethod(Method getDataMethod) {
        this.getDataMethod = getDataMethod;
    }

    public Method getGetCountMethod() {
        return getCountMethod;
    }

    public void setGetCountMethod(Method getCountMethod) {
        this.getCountMethod = getCountMethod;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
