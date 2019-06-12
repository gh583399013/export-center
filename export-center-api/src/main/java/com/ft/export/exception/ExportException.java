package com.ft.export.exception;

import com.ft.export.enums.ExceptionTypeEnum;

/**
 * @author mask
 * @date 2019/6/12 20:13
 * @desc
 */
public class ExportException extends RuntimeException{

    public ExportException(ExceptionTypeEnum exceptionTypeEnum) {
        super(exceptionTypeEnum.getErrorMsg());
    }
}
