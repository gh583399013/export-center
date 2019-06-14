package com.ft.export.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ft.business.resp.MyOrderPageResp;
import com.ft.export.annotation.ExportField;
import com.ft.export.dto.ExportCoreInfo;
import com.ft.export.dto.ExportFieldCoreInfo;
import com.ft.export.enums.FieldTypeEnum;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 导出报表工具类
 *
 * @author fengtao
 *
 */
public class ExcelUtil {
    public static final String VERSION_2003 = "2003";
	public static final String VERSION_2007 = "2007";
	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	/* 表头row单元格高 */
	private static final float HeadTextHeight = 40f;
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
		CellStyle headerStyle = workbook.createCellStyle();
		CellStyle propertyStyle = workbook.createCellStyle();
		CellStyle dataStyle1 = workbook.createCellStyle();
		CellStyle dataStyle2 = workbook.createCellStyle();
		styleMap.put("headerStyle", headerStyle);
		styleMap.put("propertyStyle", propertyStyle);
		styleMap.put("dataStyle1", dataStyle1);
		styleMap.put("dataStyle2", dataStyle2);

		// headerStyle
		// 指定单元格居中对齐

        headerStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

		// 指定当单元格内容显示不下时自动换行
		headerStyle.setWrapText(true);

		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);

		headerStyle.setBorderBottom(BorderStyle.THIN);// 下边框

		// 设置单元格字体
		Font font = workbook.createFont();
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		headerStyle.setFont(font);

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

	public static Workbook outputExcelResult1(int sheetNo, String sheetName, List<String> colName, List<String> colProperty,
			String headText, List<?> dataList, Class<?> clazz, String version) {
		Workbook workbook = createWorkbook(version, sheetName);

		try {
			initStyle(workbook);
			workbook = initTableHeader(workbook, sheetNo, colName, headText);
			workbook = fillData(workbook, sheetNo, dataList, clazz, colProperty);
			FileOutputStream fos = null;
			if (version.equals("2003")) {
				fos = new FileOutputStream(new File("d:\\test1.xls"));
			} else {
				fos = new FileOutputStream(new File("d:\\test2.xlsx"));
			}
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			// e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			// e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// e.printStackTrace();
		}
		return workbook;
	}


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

//	public static <T>Workbook outputExcelResult4(int sheetNo, String sheetName, List<String> colName, List<String> colProperty,
//											  String headText, List<T> dataList, String version) {
//		Workbook workbook = createWorkbook(version, sheetName);
//
//		try {
//			initStyle(workbook);
//			workbook = initTableHeader(workbook, sheetNo, colName, headText);
//
//			Map<String, ExportFieldCoreInfo> fieldDtoMap = null;
//			try {
//				fieldDtoMap = getExportFieldDtoMap(dataList.get(0).getClass() , null);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			ExportCoreInfo exportFieldDetail = new ExportCoreInfo();
//			exportFieldDetail.setDateFormatPattern("yyyy-MM-dd HH:mm:SS");
//			exportFieldDetail.setPrecision(1);
//			Map<String, String> valueDescMap = new HashMap<>();
//			valueDescMap.put("1","待支付");
//			valueDescMap.put("2","已支付");
//			valueDescMap.put("3","已发货");
//			exportFieldDetail.setValueDescMap(valueDescMap);
//
//			workbook = fillDataPro(workbook, sheetNo, dataList, colProperty, fieldDtoMap, exportFieldDetail);
//			FileOutputStream fos = null;
//			if (version.equals("2003")) {
//				fos = new FileOutputStream(new File("d:\\test1.xls"));
//			} else {
//				fos = new FileOutputStream(new File("d:\\test2.xlsx"));
//			}
//			workbook.write(fos);
//			fos.close();
//		} catch (FileNotFoundException e) {
//			logger.error(e.getMessage(), e);
//			// e.printStackTrace();
//		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
//			// e.printStackTrace();
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			// e.printStackTrace();
//		}
//		return workbook;
//	}

	/**
	 * 生成表单
	 *
	 * @param sheetNo
	 * @param sheetName
	 * @param colName
	 * @param colProperty
	 * @param headText
	 * @param dataList
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public Workbook outputExcelResult(int sheetNo, String sheetName, List<String> colName, List<String> colProperty,
			String headText, List<?> dataList, Class<?> clazz, String version) throws Exception {
		// HSSFWorkbook hssfWorkbook = createHSSFWorkbook(sheetName);
		Workbook workbook = createWorkbook(VERSION_2003,sheetName);
		// 初始化四种样式
		initStyle(workbook);
		workbook = initTableHeader(workbook, sheetNo, colName, headText);
		workbook = fillData(workbook, sheetNo, dataList, clazz, colProperty);
		System.out.println("我猜这里出错了");
		return workbook;
	}

	public static HSSFWorkbook createHSSFWorkbook(String... sheetNames) {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		for (String sheetName : sheetNames) {
			hssfWorkbook.createSheet(sheetName);
		}
		return hssfWorkbook;
	}

	public static Workbook createWorkbook(String version, String... sheetNames) {
		Workbook workbook = null;
		if (version.equals("2003")) {
			workbook = new HSSFWorkbook();
		} else if (version.equals("2007")) {
			workbook = new XSSFWorkbook();
		}
		for (String sheetName : sheetNames) {
			workbook.createSheet(sheetName);
		}

		System.out.println(workbook.getNumberOfSheets());

		return workbook;
	}

	/**
	 * 生成表头信息
	 * @param workbook
	 * @param sheetNo
	 * @param colName
	 * @param headText
	 * @return
	 */
	private static Workbook initTableHeader(Workbook workbook, int sheetNo, List<String> colName, String headText) {
		Sheet sheet = workbook.getSheetAt(sheetNo);
		// 计算表宽度
		int widthNum = colName.size();

		// 设置第一行
		Row row = sheet.createRow(0);
		row.setHeightInPoints(HeadTextHeight);
		Cell cell = row.createCell(0);
		// 定义单元格为字符串类型
		//cell.setCellType(HSSFCell.ENCODING_UTF_16);
		// 在这里使用底层接口，就不能使用hssf或者xssf的样式或者类型了，否则强制转换异常
		// 两种解决办法 在这里做一个if判断 两种不同逻辑；2是 在当前这种情况下 可以避免使用HSSFRichTextString
		// 就单存的文本就好了，没必要弄啥富文本没意义。
		cell.setCellValue(headText);
		cell.setCellStyle(styleMap.get("headerStyle"));

		// 指定合并区域
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, widthNum - 1));

		Row rowTwo = sheet.createRow(1);
		rowTwo.setHeightInPoints(PropertyHeight);
		for (int i = 0; i < colName.size(); i++) {
			Cell cellTemp = rowTwo.createCell(i);
			cellTemp.setCellStyle(styleMap.get("propertyStyle"));
			cellTemp.setCellValue(colName.get(i));
		}

		return workbook;
	}

//	/**
//		填充数据
//	 **/
//	private static <T>Workbook fillDataPro(Workbook workbook, int sheetNo, List<T> dataList, List<String> colProperty, Map<String, ExportFieldCoreInfo> fieldDtoMap, ExportCoreInfo exportFieldDetail) {
//		SimpleDateFormat sdf = new SimpleDateFormat(exportFieldDetail.getDateFormatPattern());
//		Map<String, String> valueDescMap = exportFieldDetail.getValueDescMap();
//
//		Sheet sheet = workbook.getSheetAt(sheetNo);
//		Row row = null;
//		Cell cell = null;
//		for (int i = 0; i < dataList.size(); i++) {
//			row = sheet.createRow(sheet.getLastRowNum() + 1);
//			row.setHeightInPoints(DataHeight);
//			T rowData = dataList.get(i);
//			//List<Object> rowData = getRowData(object, clazz, colName);
//			for (int j = 0; j < colProperty.size(); j++) {
//				cell = row.createCell(j);
//				cell.setCellStyle(i % 2 == 0 ? styleMap.get("dataStyle1") : styleMap.get("dataStyle2"));
//
//				String fieldName = colProperty.get(j);
//				Method method = fieldDtoMap.get(fieldName).getFieldGetterMethod();
//				ExportField exportField = fieldDtoMap.get(fieldName).getExportField();
//				Object obj = null;
//				try {
//					obj = method.invoke(rowData);
//
//					if (FieldTypeEnum.Str.equals(exportField.fieldTypeEnum())) {
//						cell.setCellValue(String.valueOf(obj));
//					} else if (FieldTypeEnum.Number.equals(exportField.fieldTypeEnum())) {
//						if (obj instanceof Integer) {
//							cell.setCellValue(Integer.valueOf(String.valueOf(obj)));
//						} else {
//							BigDecimal value = new BigDecimal(String.valueOf(obj));
//							value = value.setScale(exportFieldDetail.getPrecision(), RoundingMode.HALF_UP);
//							cell.setCellValue(value.doubleValue());
//						}
//					} else if (FieldTypeEnum.Date.equals(exportField.fieldTypeEnum())) {
//						cell.setCellValue(sdf.format((Date) obj));
//					} else if (FieldTypeEnum.FormatStr.equals(exportField.fieldTypeEnum())) {
//						cell.setCellValue(valueDescMap.get(String.valueOf(obj)));
//					} else {
//
//					}
//
////					if (obj instanceof String) {
////						cell.setCellValue(String.valueOf(obj));
////					} else if (obj instanceof Integer) {
////						cell.setCellValue((int) obj);
////					} else if (obj instanceof Double) {
////						cell.setCellValue((double) obj);
////					} else if (obj instanceof Float) {
////						cell.setCellValue((double) obj);
////					} else if (obj instanceof Boolean) {
////						cell.setCellValue((boolean) obj);
////					} else if (obj instanceof Date) {
////						cell.setCellValue((Date) obj);
////					} else {
////					/*
////					 * 这里代码会出问题，类型强制转换异常 要么是直接抛出异常，要么是打印日志 break；
////					 * 我猜大部分情况都应该是前者吧。
////					 */
////						cell.setCellValue((String) obj);
////					}
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//				// 设置每列宽度自适应(不能放)
//				// hssfSheet.autoSizeColumn(j, true);
//			}
//		}
//		// 设置每列宽度自适应
//		/**
//		 * 不能放上面的理由 因为当行数m和和列数n都很大时
//		 * 这是一个m*n的循环，而autoSizeColumn这个调整自适应宽度的函数比较耗时（500行*80列情况下）
//		 * 到了后期不断的往表里面写数据是 这个时间会越来越大 （500行*80列情况下 我反正是到了300-400ms） 这样就导致 我们执行了
//		 * m*n*500ms 这里严重耗时。当m n越大 那么耗时也就越大，所以应该在表数据写完之后
//		 * 最后一次性调整这个宽度（这也是多重循环要注意的位置，内层循环不要放耗时严重的方法，应该想其他办法解决）
//		 */
//		for (int j = 0; j < colProperty.size(); j++) {
//			sheet.autoSizeColumn(j, true);
//		}
//		return workbook;
//	}


	/**
	 * 填充数据
	 * @param workbook
	 * @param sheetNo
	 * @param dataList
	 * @param clazz
	 * @param colName
	 * @return
	 */
	private static Workbook fillData(Workbook workbook, int sheetNo, List<?> dataList, Class<?> clazz, List<String> colName) {
		Sheet sheet = workbook.getSheetAt(sheetNo);
		Row row = null;
		Cell cell = null;
		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(sheet.getLastRowNum() + 1);
			row.setHeightInPoints(DataHeight);
			Object object = dataList.get(i);;
			List<Object> rowData = getRowData(object, clazz, colName);
			for (int j = 0; j < colName.size(); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(i % 2 == 0 ? styleMap.get("dataStyle1") : styleMap.get("dataStyle2"));
				Object obj = rowData.get(j);
				if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Integer) {
					cell.setCellValue((int) obj);
				} else if (obj instanceof Double) {
					cell.setCellValue((double) obj);
				} else if (obj instanceof Float) {
					cell.setCellValue((double) obj);
				} else if (obj instanceof Boolean) {
					cell.setCellValue((boolean) obj);
				} else if (obj instanceof Date) {
					cell.setCellValue((Date) obj);
				} else {
					/*
					 * 这里代码会出问题，类型强制转换异常 要么是直接抛出异常，要么是打印日志 break；
					 * 我猜大部分情况都应该是前者吧。
					 */
					cell.setCellValue((String) obj);
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
		for (int j = 0; j < colName.size(); j++) {
			sheet.autoSizeColumn(j, true);
		}
		return workbook;
	}

	private static String getGetterName(String propertyName) {
		StringBuffer sb = new StringBuffer(propertyName);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return "get" + sb.toString();
	}

	private static List<Object> getRowData(Object object, Class<?> clazz, List<String> colName) {
		List<Object> rowData = new ArrayList<Object>();
		for (String propertyName : colName) {
			try {
				Method method = clazz.getDeclaredMethod("get" + getGetterName(propertyName));
				Object result = method.invoke(object);
				if (result != null) {
					rowData.add(result);
				} else {
					rowData.add("");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rowData;
	}

	public static void main(String[] args) {

		MyOrderPageResp myOrderPageResp = new MyOrderPageResp();
		myOrderPageResp.setOrderNo("SO19921226");
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

//		try {
//			ExcelUtil.outputExcelResult4(0, "studentTable", colName, colName, "学生基本信息表", dataList,
//					ExcelUtil.VERSION_2007);
//		} catch (Exception e) {
//			System.out.println("@@@@");
//			e.printStackTrace();
//		}
//		System.out.println("@@@@@@@@@@@@@");
	}
}