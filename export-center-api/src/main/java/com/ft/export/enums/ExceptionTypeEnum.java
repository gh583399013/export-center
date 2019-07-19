package com.ft.export.enums;

import java.io.Serializable;

/**
 * @author mask
 * @date 2019/6/12 20:15
 * @desc
 */
public enum ExceptionTypeEnum implements Serializable{
    FILL_ERROR(1, "填充数据报错"),
    FIELD_EMPTY(2, "导出列头为空"),
    DATA_EMPTY(3, "导出数据为空"),
    BUILD_EXPORT_INFO_FAIL(4, "构建导出coreInfo失败请看日志"),;

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
