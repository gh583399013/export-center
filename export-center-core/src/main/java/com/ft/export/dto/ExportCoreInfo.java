package com.ft.export.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author mask
 * @date 2019/6/13 20:12
 * @desc
 */
@Data
public class ExportCoreInfo implements Serializable{
    /*导出文件绝对路径*/
    private String fileAbsolutePath;

    /*导出文件目录*/
    private String filePath;

    /*导出文件名*/
    private String fileName;

    /*导出的类类型*/
    private Class clazz;

    /*导出字段 */
    private List<String> fieldList;

    /*导出字段列头描述 */
    private List<String> headNameList;

    /*导出列字段核心信息描述*/
    private Map<String, ExportFieldCoreInfo> fieldCoreInfoMap;

    /*导出文件是 xls 还是 xlsx*/
    /**
     * com.ft.export.util.ExcelCreator#VERSION_2003
     * com.ft.export.util.ExcelCreator#VERSION_2007
     */
    private String version;
}
