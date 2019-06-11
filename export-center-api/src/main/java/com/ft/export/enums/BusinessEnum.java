package com.ft.export.enums;

/**
 * @author mask
 * @date 2019/6/10 20:00
 * @desc
 */
public enum BusinessEnum {
    SYSTEM_OMS(1, "订单系统"),
    SYSTEM_WMS(2, "仓储系统"),
    SYSTEM_FMS(3, "财务系统");

    private BusinessEnum(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

    private Integer value;
    private String desc;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
