package com.ft.export.enums;

import java.io.Serializable;

/**
 * @author mask
 * @date 2019/6/10 20:00
 * @desc
 */
public enum YesOrNoEnum implements Serializable{
    YES(1, "是"),
    NO(2, "否");

    private YesOrNoEnum(Integer value, String desc){
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
