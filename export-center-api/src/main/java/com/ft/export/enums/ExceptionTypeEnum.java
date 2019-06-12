package com.ft.export.enums;

import java.io.Serializable;

/**
 * @author mask
 * @date 2019/6/12 20:15
 * @desc
 */
public enum ExceptionTypeEnum implements Serializable{
    FIELD_EMPTY(1, "导出列头为空"),
    DATA_EMPTY(2, "导出列头为空");

    private ExceptionTypeEnum(Integer code, String errorMsg){
        this.code = code;
        this.errorMsg = errorMsg;
    }
    private Integer code;
    private String errorMsg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
