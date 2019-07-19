package com.ft.export.util;

import com.ft.export.annotation.ExportField;
import com.ft.export.dto.ExportCoreInfo;
import com.ft.export.dto.ExportFieldCoreInfo;
import com.ft.export.enums.ExceptionTypeEnum;
import com.ft.export.enums.FieldTypeEnum;
import com.ft.export.exception.ExportException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mask
 * @date 2019/7/19 18:41
 * @desc
 */
public class ExportCoreInfoBuildUtil {
    /**
     * 拿到要导出的类的一些信息 包括时间格式化pattern  小数保留精度 已经格式化文案匹配规则
     * @param clazz
     * @param colList
     * @return
     * @throws Exception
     */
    private static Map<String, ExportFieldCoreInfo> getExportFieldDtoMap(Class clazz, List<String> colList) throws Exception{
        Map<String, ExportFieldCoreInfo> resultMap = new HashMap<>();
        while (!Object.class.equals(clazz)){
            Field[] parentFields = clazz.getDeclaredFields();
            for (Field field : parentFields) {
                ExportField exportField = field.getAnnotation(ExportField.class);
                if(exportField != null){
                    ExportFieldCoreInfo exportFieldDto = new ExportFieldCoreInfo();
                    exportFieldDto.setFieldName(field.getName());
                    exportFieldDto.setExportField(exportField);
                    exportFieldDto.setFieldGetterMethod(clazz.getDeclaredMethod(getGetterName(field.getName())));
                    resultMap.put(field.getName(), exportFieldDto);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return resultMap;
    }

    /**
     * 生成导出核心信息类
     * 这里也可以改成在不需要dataList的时候就初始化, 我们获取dataList 也只是为了拿导出类的信息而已
     * 而这个信息完全可以
     * @param clazz
     * @param fieldList
     * @param filePath
     * @param fileName
     * @param version
     * @return
     */
    public static ExportCoreInfo getExportCoreInfo(Class clazz, List<String> fieldList, String filePath, String fileName, String version) throws Exception{
        Class originClazz = clazz;

        //查询字段的详细导出信息 丢入map
        Map<String, ExportFieldCoreInfo> exportFieldCoreInfoMap = new HashMap<>();
        while (!Object.class.equals(clazz)){
            Field[] parentFields = clazz.getDeclaredFields();
            for (Field field : parentFields) {
                ExportField exportField = field.getAnnotation(ExportField.class);
                if(exportField != null){
                    ExportFieldCoreInfo exportFieldDto = new ExportFieldCoreInfo();
                    exportFieldDto.setFieldName(field.getName());
                    exportFieldDto.setExportField(exportField);
                    if(FieldTypeEnum.FormatStr.equals(exportField.fieldTypeEnum())){
                        Map<String, String> valueDescMap = new HashMap<>();
                        String formatStr = exportField.formatStr();
                        String[] formatStrArr = formatStr.split("&");
                        for (String s : formatStrArr) {
                            String[] keyValue = s.split("=");
                            valueDescMap.put(keyValue[0], keyValue[1]);
                        }
                        exportFieldDto.setValueDescMap(valueDescMap);
                    }
                    try {
                        exportFieldDto.setFieldGetterMethod(clazz.getDeclaredMethod(getGetterName(field.getName())));
                    } catch (NoSuchMethodException e) {
                        throw new ExportException(field.getName() + "没有getter方法");
                    }
                    exportFieldCoreInfoMap.put(field.getName(), exportFieldDto);
                }
            }
            clazz = clazz.getSuperclass();
        }
        //查出列头名称
        List<String> headNameList = new ArrayList<>();
        for (String field : fieldList) {
            headNameList.add(exportFieldCoreInfoMap.get(field).getExportField().headName());
        }

        ExportCoreInfo exportCoreInfo = new ExportCoreInfo();
        exportCoreInfo.setFilePath(filePath);
        exportCoreInfo.setFileName(fileName + (ExcelCreator.VERSION_2007.equals(version) ? ExcelCreator.SUFFIX_2007:ExcelCreator.SUFFIX_2003));
        exportCoreInfo.setFileAbsolutePath(exportCoreInfo.getFilePath() + exportCoreInfo.getFileName());
        exportCoreInfo.setClazz(originClazz);
        exportCoreInfo.setFieldList(fieldList);
        exportCoreInfo.setHeadNameList(headNameList);
        exportCoreInfo.setFieldCoreInfoMap(exportFieldCoreInfoMap);
        exportCoreInfo.setVersion(version);
        checkExportCoreInfo(exportCoreInfo);
        return exportCoreInfo;
    }


    /**
     * 生成导出核心信息类
     * 这里也可以改成在不需要dataList的时候就初始化, 我们获取dataList 也只是为了拿导出类的信息而已
     * 而这个信息完全可以
     * @param dataList
     * @param fieldList
     * @param filePath
     * @param fileName
     * @param version
     * @param <T>
     * @return
     * @see ExportCoreInfoBuildUtil#getExportCoreInfo(java.lang.Class, java.util.List, java.lang.String, java.lang.String, java.lang.String)
     */
    @Deprecated
    public static <T>ExportCoreInfo getExportCoreInfo(List<T> dataList, List<String> fieldList, String filePath, String fileName, String version) throws Exception{
        return getExportCoreInfo(dataList.get(0).getClass(), fieldList, filePath, fileName, version);
    }

    /**
     * 检查导出参数是否合法
     * @param exportFieldInfo
     */
    private static void checkExportCoreInfo(ExportCoreInfo exportFieldInfo){
        if(StringUtils.isEmpty(exportFieldInfo.getFilePath())){
            throw new ExportException(ExceptionTypeEnum.FIELD_EMPTY);
        }
        if(CollectionUtils.isEmpty(exportFieldInfo.getFieldList())){
            throw new ExportException(ExceptionTypeEnum.FIELD_EMPTY);
        }
    }

    private static String getGetterName(String propertyName) {
        StringBuffer sb = new StringBuffer(propertyName);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return "get" + sb.toString();
    }
}
