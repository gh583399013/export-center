package com.ft.export.dto;

import com.ft.export.annotation.ExportField;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author mask
 * @date 2019/6/13 19:50
 * @desc
 */
@Data
public class ExportFieldDto implements Serializable{
    private String fieldName;
    private Method fieldGetterMethod;
    private ExportField exportField;
}
