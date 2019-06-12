package com.ft.export.enums;

import java.io.Serializable;

/**
 * @author mask
 * @date 2019/6/12 17:13
 * @desc
 */
public enum FieldTypeEnum implements Serializable{
    Str(1, "字符串"),
    Number(2, "数字"),
    Date(3, "日期"),
    FormatStr(4, "需格式化文本");

    private FieldTypeEnum(Integer value, String desc){
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
