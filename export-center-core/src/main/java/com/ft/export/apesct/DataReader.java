package com.ft.export.apesct;

import com.ft.business.resp.MyOrderPageResp;
import com.ft.export.annotation.ExportField;
import com.ft.export.enums.ExceptionTypeEnum;
import com.ft.export.exception.ExportException;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author mask
 * @date 2019/6/12 17:43
 * @desc
 */
public class DataReader {

    public <T>void createExcel(List<T> dataList,List<String> fieldList){
        if(CollectionUtils.isEmpty(fieldList)){
            throw new ExportException(ExceptionTypeEnum.FIELD_EMPTY);
        }

        if(CollectionUtils.isEmpty(dataList)){
            throw new ExportException(ExceptionTypeEnum.DATA_EMPTY);
        }

        //获取所有导出字段(ExportField)注解的字段
        Class clazz = dataList.get(BigDecimal.ZERO.intValue()).getClass();
        Map<String, ExportField> fieldMap = new HashMap<>();
        //可能继承了A类 然后A中也有注解 然后A又继承... 反复循环 所以这里循环,知道父类为Object为止 load出所有有ExportField的字段
        while (!Object.class.equals(clazz)){
            Field[] parentFields = clazz.getDeclaredFields();
            for (Field field : parentFields) {
                ExportField exportField = field.getAnnotation(ExportField.class);
                if(exportField != null){
                    fieldMap.put(field.getName(), exportField);
                }
            }
            clazz = clazz.getSuperclass();
        }

        Map<String, Method> methodMap = new HashMap<>();
        Method[] methods = MyOrderPageResp.class.getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().startsWith("get")){
                methodMap.put(method.getName(), method);
            }
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (T t : dataList) {

            for (Method method : methodMap.values()) {
                try {
                    Object object = method.invoke(t);
                    System.out.println(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
//            for (String s : fieldList) {
//                Object object =
//            }
        }
    }


    public <T>void dealDate(List<T> tList){

        Class clazz = tList.get(0).getClass();

        Map<String, ExportField> fieldMap = new HashMap<>();
        Field[] fields =clazz.getDeclaredFields();
        for (Field field : fields) {
            //
            ExportField exportField = field.getAnnotation(ExportField.class);
            if(exportField != null){
                fieldMap.put(field.getName(), exportField);
            }
        }

        for (String s : fieldMap.keySet()) {
            ExportField exportField = fieldMap.get(s);
            System.out.println("asd0:" + s + " asd1:" + exportField.fieldTypeEnum());
        }
    }

    public static void main(String[] args) {
//        Field[] fields =MyOrderPageResp.class.getDeclaredFields();
//        for (Field field : fields) {
//            System.out.println(field.getAnnotations()[0]);
//        }
//        System.out.println(1231);

        DataReader dataReader = new DataReader();
        dataReader.dealDate(Arrays.asList(new MyOrderPageResp()));
    }
}
