package com.ft.export.dto;

import com.ft.export.annotation.ExportField;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author mask
 * @date 2019/6/13 19:50
 * @desc
 */
@Data
public class ExportFieldCoreInfo implements Serializable{
    /*导出列bean中字段*/
    private String fieldName;
    /*导出列bean中字段的getter方法*/
    private Method fieldGetterMethod;
    /*导出列bean中字段的导出注解*/
    private ExportField exportField;

    private Map<String, String> valueDescMap;
}
