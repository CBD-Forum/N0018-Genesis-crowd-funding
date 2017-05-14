package com.fbd.core.util.report.util;

import java.io.File;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.util.ExcelUtil;
import com.fbd.core.util.XMLHandler;
import com.fbd.core.util.XmlTools;
import com.fbd.core.util.report.model.ExcelReportColumnModel;
import com.fbd.core.util.report.model.ExcelReportModel;


/**
 * 
 * Description: Excel报表工具类
 * 
 * @author dekunzhao
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2013-12-02  zhaokevin       1.0        1.0 Version
 * </pre>
 */
public class ExcelReportUtil {

	public static final int HEADER_ROW_INDEX = 0;
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_MONEY_FORMAT = "#,##0.00";
	 private static final Logger logger = LoggerFactory
	            .getLogger(ExcelReportUtil.class);

	private static Map<String,String> cacheMap = new  HashMap<String,String>(100);
	
	
	
	public static HSSFWorkbook genExcelReport(File templateFile,
			List<? extends Object> datas) throws Exception {
		HSSFWorkbook workbook = null;
		try{
			String templateContent = FileUtils.readFileToString(templateFile,"UTF-8");
			
			if (logger.isDebugEnabled()) {
				logger.debug("解析模版－－－－－－－－－－start");
				logger.debug(templateContent);
				logger.debug("解析模版－－－－－－－－－－end");
			}
			
			XmlTools<ExcelReportModel> xmlTool = new XmlTools<ExcelReportModel>();
			XMLHandler<ExcelReportModel> xmlHandler = new ExcelReportXMLHandler();
			List<ExcelReportModel> reports = xmlTool.parseXML(templateContent,
					xmlHandler);

			ExcelReportModel report = reports.get(0);

			workbook = ExcelUtil.createWorkBook();
			String fileName = report.getReportName();
			HSSFSheet sheet = ExcelUtil.createSheet(workbook, fileName);
			List<ExcelReportColumnModel> columns = report.getReportColumns();

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			HSSFFont f  = workbook.createFont();     
			f.setFontName("宋体");
			f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
			headerCellStyle.setFont(f);
			headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			// 设置表头
			drawReportHeader(sheet, columns, headerCellStyle);
			// 构建表格内容
			HSSFCellStyle CellStyle = workbook.createCellStyle();
			HSSFFont fcell  = workbook.createFont();     
			fcell.setFontName("宋体");
			CellStyle.setFont(fcell);
			CellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			CellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			CellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			CellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			drawReportContent(sheet, columns, datas,CellStyle);

			int colNums = columns.size();
			ExcelUtil.autoSizeColumn(sheet, 0, colNums);			
		}finally{
			ExcelReportUtil.cacheMap.clear();
		}
		return workbook;
	}
	
	public static HSSFWorkbook genExcelReport(String templateFilePath,
			List<? extends Object> datas) throws Exception {
		File templateFile = ResourceUtils.getFile("classpath:"+FbdCoreConstants.EXPORT_TEMP_PATH+templateFilePath);
		return genExcelReport(templateFile, datas);
	}
	/**
	 * 构建报表内容
	 * 
	 * @author zhaokevin
	 * @param sheet
	 *            当前数据所在Sheet
	 * @param columns
	 *            报表表头实体
	 * @param datas
	 *            数据
	 * @throws Exception  
	 */
	private static void drawReportContent(HSSFSheet sheet,
			List<ExcelReportColumnModel> columns,
			List<? extends Object> datas,HSSFCellStyle CellStyle) throws Exception {
		int length = datas.size();
		for (int index = 1; index <= length; index++) {
			HSSFRow row = ExcelUtil.row(sheet, index);
			Object data = datas.get(index - 1);
			drawRowData(row, columns, data,CellStyle);
		}
	}

	/**
	 * 构建表头
	 * 
	 * @author zhaokevin
	 * @param sheet
	 *            当前数据所在Sheet
	 * @param columns
	 *            报表表头实体
	 * @param headerCellStyle
	 *            表头样式
	 */
	private static void drawReportHeader(HSSFSheet sheet,
			List<ExcelReportColumnModel> columns, CellStyle headerCellStyle) {
		HSSFRow headerRow = ExcelUtil.row(sheet, HEADER_ROW_INDEX);
		for (int index = 0; index < columns.size(); index++) {
			ExcelReportColumnModel column = columns.get(index);
			String name = column.getName();
			ExcelUtil.cell(headerRow, index, name, headerCellStyle);
		}
	}

	/**
	 * 构建行数据
	 * @author zhaokevin
	 * @param row Excel行对象
	 * @param columns 定义列模型
	 * @param data 行数据
	 * @throws Exception 
	 */
	private static void drawRowData(HSSFRow row,
			List<ExcelReportColumnModel> columns, Object data,HSSFCellStyle CellStyle)
			throws Exception {

		ExcelReportColumnModel reportColumnModel = null;
		for (int index = 0; index < columns.size(); index++) {
			reportColumnModel = columns.get(index);
			String dataType = reportColumnModel.getDataType();
			String format = reportColumnModel.getFormat();
			String mapping = reportColumnModel.getMapping();
			String start = reportColumnModel.getStart();
			Object value = null;
			if((!StringUtils.isEmpty(dataType))&&dataType.equalsIgnoreCase("sequence")){
				if(StringUtils.isEmpty(start)){
					int rowNum = row.getRowNum();
					reportColumnModel.setStart(String.valueOf(rowNum));
				}
				value = reportColumnModel.getNextSequenceValue();
			}else{
				value = genValueFromData(data, mapping);
				if(value!=null){
				    value = dealValue(dataType, format, value);        
				}
			}
			ExcelUtil.cell(row, index, value,CellStyle);
		}
	}


	/**
	 * 根据mapping映射属性从data实体中获取value
	 * 
	 * @author zhaokevin
	 * @param data
	 *            数据实体，可以为Map，也可为Bean
	 * @param mapping
	 *            映射字段值
	 * @return 数据实体对应mapping字段的值
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object genValueFromData(Object data, String mapping)
			throws Exception {
		Object value = null;
		Class clazz = data.getClass();
		try {
			if (clazz.isAssignableFrom(Map.class)) {
				value = ((Map) data).get(mapping);
			} else {
				value = PropertyUtils.getProperty(data, mapping);
			}			
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * 数据转换，当指定dataType与format后，将原始值转换成format之后得结果
	 * 
	 * @author zhaokevin
	 * @param dataType
	 *            数据类型，默认为string,可设置成date，bigdecimal
	 * @param format
	 *            当dataType指定成date｜bigdecimal时，需要提供数据格式
	 * @param value
	 *            数据原始值
	 * @return 转换后得数据
	 */
	private static Object dealValue(String dataType, String format, Object value) {
		if(logger.isDebugEnabled()){
			logger.info("转换数据值="+value);
		}
		Object result = value;
		Format d = null;
		if(dataType != null){
			if (dataType.toLowerCase().equals("date")) 
			{
				if(StringUtils.isEmpty(format)){
					format = DEFAULT_DATE_FORMAT;
				}
				d = new SimpleDateFormat(format);
			} else if (dataType.toLowerCase().equals("double")) {
				if(StringUtils.isEmpty(format)){
					format = DEFAULT_MONEY_FORMAT;
				}			
				d = new DecimalFormat(format);
			}
			if(d!=null){
				result = d.format(value);
			}
		}
		if(logger.isDebugEnabled()){
			logger.info("转换数据结果值="+result);
		}
		return result;
	}

}
