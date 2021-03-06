package com.ft.export.util;

import com.ft.business.resp.MyOrderPageResp;
import com.ft.export.annotation.ExportField;
import com.ft.export.constant.ExportCenterCommonConfig;
import com.ft.export.dto.ExportCoreInfo;
import com.ft.export.dto.ExportFieldCoreInfo;
import com.ft.export.enums.ExceptionTypeEnum;
import com.ft.export.enums.FieldTypeEnum;
import com.ft.export.exception.ExportException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 导出报表工具类
 *
 * @author fengtao
 *
 */
public class ExcelCreator {
    public static final String VERSION_2003 = "2003";
    public static final String VERSION_2007 = "2007";
    public static final String SUFFIX_2003 = ".xls";
    public static final String SUFFIX_2007 = ".xlsx";


    private static Logger logger = LoggerFactory.getLogger(ExcelCreator.class);
    /* 列名row单元格高 */
    private static final float PropertyHeight = 30f;
    /* 数据域row单元格高 */
    private static final float DataHeight = 20f;
    /* 初始化四种单元格样式 */
    private static Map<String, CellStyle> styleMap;

    /**
     * 初始化四种单元格样式
     * @param workbook
     */
    private static void initStyle(Workbook workbook) {

        styleMap = new HashMap<String, CellStyle>();
        CellStyle propertyStyle = workbook.createCellStyle();
        CellStyle dataStyle1 = workbook.createCellStyle();
        CellStyle dataStyle2 = workbook.createCellStyle();
        styleMap.put("propertyStyle", propertyStyle);
        styleMap.put("dataStyle1", dataStyle1);
        styleMap.put("dataStyle2", dataStyle2);

        // 设置单元格字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeight((short) 200);

        // propertyStyle
        propertyStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        propertyStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        // 指定当单元格内容显示不下时自动换行
        propertyStyle.setWrapText(true);

        propertyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        propertyStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);

        propertyStyle.setBorderLeft(BorderStyle.THIN);// 左边框
        propertyStyle.setBorderRight(BorderStyle.THIN);// 右边框
        propertyStyle.setBorderBottom(BorderStyle.THIN);// 下边框
        propertyStyle.setBorderTop(BorderStyle.THIN);// 上边框
        propertyStyle.setFont(font);

        // dataStyle1
        dataStyle1.setAlignment(HorizontalAlignment.CENTER);//水平居中
        dataStyle1.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        // 指定当单元格内容显示不下时自动换行
        dataStyle1.setWrapText(true);
        // dataStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // dataStyle1.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        dataStyle1.setBorderLeft(BorderStyle.THIN);// 左边框
        dataStyle1.setBorderRight(BorderStyle.THIN);// 右边框
        dataStyle1.setBorderBottom(BorderStyle.THIN);// 下边框
        dataStyle1.setBorderTop(BorderStyle.THIN);// 上边框
        dataStyle1.setFont(font);

        // dataStyle2
        // 指定单元格居中对齐
        dataStyle2.setAlignment(HorizontalAlignment.CENTER);//水平居中
        dataStyle2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        // 指定当单元格内容显示不下时自动换行
        dataStyle2.setWrapText(true);
        dataStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        dataStyle2.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
        dataStyle2.setBorderLeft(BorderStyle.THIN);// 左边框
        dataStyle2.setBorderRight(BorderStyle.THIN);// 右边框
        dataStyle2.setBorderBottom(BorderStyle.THIN);// 下边框
        dataStyle2.setBorderTop(BorderStyle.THIN);// 上边框
        dataStyle2.setFont(font);
    }

    /**
     * 输入EXCEL文件
     *
     * @param fileName
     *            文件名
     */
    public static void outputExcel(HSSFWorkbook hssfWorkbook, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(fileName));
            hssfWorkbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void outputExcel(List<T> dataList, ExportCoreInfo exportFieldInfo, Integer sheetNo) {
        try {
            Workbook workbook = createWorkBook(exportFieldInfo, sheetNo);
            workbook = fillData(workbook, dataList, exportFieldInfo, sheetNo);
            FileOutputStream fos = null;

            File exportFile = new File(exportFieldInfo.getFileAbsolutePath());
            fos = new FileOutputStream(exportFile);
            workbook.write(fos);
            fos.close();
            QiNiuYunFileUtil.uploadFile(exportFile.getAbsolutePath(), exportFile.getName());
			//TODO 删除开关
            exportFile.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } catch (ExportException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

//	/**
//	 * 生成表单
//	 *
//	 * @param sheetNo
//	 * @param sheetName
//	 * @param colName
//	 * @param colProperty
//	 * @param headText
//	 * @param dataList
//	 * @param clazz
//	 * @return
//	 * @throws Exception
//	 */
//	public Workbook outputExcelResult(int sheetNo, String sheetName, List<String> colName, List<String> colProperty,
//			String headText, List<?> dataList, Class<?> clazz, String version) throws Exception {
//		// HSSFWorkbook hssfWorkbook = createHSSFWorkbook(sheetName);
//		Workbook workbook = createWorkbook(VERSION_2003,sheetName);
//		// 初始化四种样式
//		initStyle(workbook);
//		workbook = initTableHeader(workbook, sheetNo, colName, headText);
//		workbook = fillData(workbook, sheetNo, dataList, clazz, colProperty);
//		System.out.println("我猜这里出错了");
//		return workbook;
//	}

    public static HSSFWorkbook createHSSFWorkbook(String... sheetNames) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        for (String sheetName : sheetNames) {
            hssfWorkbook.createSheet(sheetName);
        }
        return hssfWorkbook;
    }

    //读取excel
    private static Workbook readExcel(ExportCoreInfo exportFieldInfo){
        String absolutePath = exportFieldInfo.getFileAbsolutePath();
        Workbook wb = null;
        try {
            File exportFile = new File(absolutePath);
            if(!exportFile.exists()){
                return null;
            }
            InputStream is = new FileInputStream(exportFile);
            if(ExcelUtil.VERSION_2003.equals(exportFieldInfo.getVersion())){
                wb = new HSSFWorkbook(is);
            }else if(ExcelUtil.VERSION_2007.equals(exportFieldInfo.getVersion())){
                wb = new XSSFWorkbook(is);
            }else{
                wb = null;
            }
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }


    public static Workbook createWorkBook(ExportCoreInfo exportFieldInfo, Integer sheetNo) {
        Workbook workbook = readExcel(exportFieldInfo);
        if(workbook == null){
            if(VERSION_2003.equals(exportFieldInfo.getVersion())){
                workbook = new HSSFWorkbook();
            }else{
                workbook = new XSSFWorkbook();
            }
        }
        initStyle(workbook);

        workbook.createSheet("编号" + sheetNo);
        workbook = initTableHeader(workbook, exportFieldInfo.getHeadNameList(), sheetNo);
        return workbook;
    }

    /**
     * 生成表头信息
     * @param workbook
     * @param headNameList
     * @return
     */
    private static Workbook initTableHeader(Workbook workbook, List<String> headNameList, Integer sheetNo) {
        Sheet sheet = workbook.getSheetAt(sheetNo);
        // 设置第一行
        Row headRow = sheet.createRow(0);
        headRow.setHeightInPoints(PropertyHeight);
        for (int i = 0; i < headNameList.size(); i++) {
            Cell cellTemp = headRow.createCell(i);
            //cellTemp.setCellStyle(styleMap.get("propertyStyle"));
            cellTemp.setCellValue(headNameList.get(i));
        }
        return workbook;
    }

    /**
     填充数据
     **/
    private static <T>Workbook fillData(Workbook workbook, List<T> dataList, ExportCoreInfo exportFieldInfo, Integer sheetNo) {
        Sheet sheet = workbook.getSheetAt(sheetNo);
        for (int i = 0; i < dataList.size(); i++) {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.setHeightInPoints(DataHeight);

            T rowData = dataList.get(i);
            List<String> fieldList = exportFieldInfo.getFieldList();
            for (int j = 0; j < fieldList.size(); j++) {
                Cell cell = row.createCell(j);
                //cell.setCellStyle(i % 2 == 0 ? styleMap.get("dataStyle1") : styleMap.get("dataStyle2"));

                String fieldName = fieldList.get(j);

                ExportFieldCoreInfo exportFieldCoreInfo = exportFieldInfo.getFieldCoreInfoMap().get(fieldName);
                Method method = exportFieldCoreInfo.getFieldGetterMethod();
                ExportField exportField = exportFieldCoreInfo.getExportField();
                try {
                    Object obj = method.invoke(rowData);
                    if (FieldTypeEnum.Str.equals(exportField.fieldTypeEnum())) {
                        cell.setCellValue(String.valueOf(obj));
                    } else if (FieldTypeEnum.Number.equals(exportField.fieldTypeEnum())) {
                        if (obj instanceof Integer) {
                            cell.setCellValue(Integer.valueOf(String.valueOf(obj)));
                        } else {
                            BigDecimal value = new BigDecimal(String.valueOf(obj));
                            value = value.setScale(exportField.precision(), RoundingMode.HALF_UP);
                            cell.setCellValue(value.doubleValue());
                        }
                    } else if (FieldTypeEnum.Date.equals(exportField.fieldTypeEnum())) {
                        SimpleDateFormat sdf = new SimpleDateFormat(exportField.dateFormatPattern());
                        cell.setCellValue(sdf.format((Date) obj));
                    } else if (FieldTypeEnum.FormatStr.equals(exportField.fieldTypeEnum())) {
                        Map<String, String> valueDescMap = exportFieldCoreInfo.getValueDescMap();
                        cell.setCellValue(valueDescMap.get(String.valueOf(obj)));
                    } else {
                        cell.setCellValue("");
                    }
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                    throw new ExportException(ExceptionTypeEnum.FILL_ERROR);
                } catch (InvocationTargetException e) {
                    logger.error(e.getMessage(), e);
                    throw new ExportException(ExceptionTypeEnum.FILL_ERROR);
                }
                // 设置每列宽度自适应(不能放)
                // hssfSheet.autoSizeColumn(j, true);
            }
        }
        // 设置每列宽度自适应
        /**
         * 不能放上面的理由 因为当行数m和和列数n都很大时
         * 这是一个m*n的循环，而autoSizeColumn这个调整自适应宽度的函数比较耗时（500行*80列情况下）
         * 到了后期不断的往表里面写数据是 这个时间会越来越大 （500行*80列情况下 我反正是到了300-400ms） 这样就导致 我们执行了
         * m*n*500ms 这里严重耗时。当m n越大 那么耗时也就越大，所以应该在表数据写完之后
         * 最后一次性调整这个宽度（这也是多重循环要注意的位置，内层循环不要放耗时严重的方法，应该想其他办法解决）
         */
        for (int j = 0; j < exportFieldInfo.getFieldList().size(); j++) {
            sheet.autoSizeColumn(j, true);
        }
        return workbook;
    }

    public static void main(String[] args) {

        MyOrderPageResp myOrderPageResp = new MyOrderPageResp();
        myOrderPageResp.setOrderNo("SO19921226");
        myOrderPageResp.setOrderTime(new Date());
        myOrderPageResp.setOrderStatus(2);
        myOrderPageResp.setTotalPrice(new BigDecimal(100.22));
        myOrderPageResp.setDoublePride(399.12);

        MyOrderPageResp myOrderPageResp2 = new MyOrderPageResp();
        myOrderPageResp.setOrderNo("212312312");
        myOrderPageResp.setOrderTime(new Date());
        myOrderPageResp.setOrderStatus(2);
        myOrderPageResp.setTotalPrice(new BigDecimal(100.22));
        myOrderPageResp.setDoublePride(399.12);

        List<String> colName = new ArrayList<>();
        colName.add("orderNo");
        colName.add("orderTime");
        colName.add("orderStatus");
        colName.add("totalPrice");
        colName.add("DoublePride");

        List<MyOrderPageResp> dataList = new ArrayList<>();
        dataList.add(myOrderPageResp);
        //dataList.add(myOrderPageResp2);


        Integer totalCount = 2;

        int totalPageCount = totalCount / ExportCenterCommonConfig.pageCount;

        String fileName = "测试分sheet";

        ExportCoreInfo exportCoreInfo = null;

        try {
            exportCoreInfo = ExportCoreInfoBuildUtil.getExportCoreInfo(dataList, colName, "D:/tmp/exportFiles", fileName, ExcelUtil.VERSION_2007);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<MyOrderPageResp> allList = new ArrayList<>();

        int sheetNo = 0;
        for (int i = 1; i <= totalPageCount; i++) {
            //如果查询次数 > 50次 sheetNo要变成1了 然后dataList要清空
            int nextSheetNo = (i - 1) / ExportCenterCommonConfig.sheetMaxQueryTimes;
            if(sheetNo != nextSheetNo){
                System.out.println("@@@@@@@@@@@ sheetNo" + sheetNo);
                ExcelCreator.outputExcel(allList, exportCoreInfo, sheetNo);
                sheetNo = nextSheetNo;
                allList.clear();
            }
            if(exportCoreInfo == null){
                try {
                    exportCoreInfo = ExportCoreInfoBuildUtil.getExportCoreInfo(dataList, colName, "D:\\tmp\\exportFiles\\qweqwe", fileName, ExcelUtil.VERSION_2007);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            allList.addAll(dataList);
        }
        System.out.println("@@@@@@@@@@@ sheetNo" + sheetNo);
        //如果只有一个sheet, 或者到了最后一个sheet 因为没有触发sheetNo != nextSheetNo 所以在这里手动生成
        ExcelCreator.outputExcel(allList, exportCoreInfo, sheetNo);



        System.out.println("@@@@@@@@@@@@@");
    }
}