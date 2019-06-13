package com.ft.export.annotation;

import com.ft.export.enums.FieldTypeEnum;

import java.lang.annotation.*;

/**
 * @author mask
 * @date 2019/6/12 17:12
 * @desc
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExportField {
    /**
     * 列属性
     * @return
     */
    FieldTypeEnum fieldTypeEnum();

    /**
     * fieldTypeEnum为
     * @see FieldTypeEnum#Date 时间格式化模板
     * @return
     */
    String dateFormatPattern() default "yyyy-MM-dd HH:mm:SS";

    /**
     * fieldTypeEnum为
     * @see FieldTypeEnum#FormatStr 需要替换的文案模板
     * @return
     */
    String formatStr() default "";

    /**
     * 小数默认保存精度
     * @return
     */
    int precision() default 2;
}
