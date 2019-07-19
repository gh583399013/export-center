package com.ft.export.entity;

import com.ft.export.enums.ExceptionTypeEnum;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author mask
 * @date 2019/6/12 20:13
 * @desc
 */
@Data
public class ExportResult{
    public static final Integer SUCCESS = 0;
    public static final Integer FAIL = -1;
    private Integer code;
    private String msg;

    public ExportResult(ExceptionTypeEnum exceptionTypeEnum) {
        this.code = exceptionTypeEnum.getCode();
        this.msg = exceptionTypeEnum.getErrorMsg();
    }

    public ExportResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ExportResult(String msg) {
        this(FAIL, msg);
    }

    public static ExportResult exportSuccess() {
        return new ExportResult(SUCCESS,"oK");
    }

    public static ExportResult exportFail(ExceptionTypeEnum exceptionTypeEnum) {
        return new ExportResult(exceptionTypeEnum.getCode(), exceptionTypeEnum.getErrorMsg());
    }

    public static ExportResult exportFail(Class clazz, Method method) {
        return exportFail(clazz.getSimpleName(), method.getName());
    }

    public static ExportResult exportFail(String className, String methodName) {
        String msg = className + "服务异常,调用" + methodName + "失败";
        return new ExportResult(msg);
    }
}
