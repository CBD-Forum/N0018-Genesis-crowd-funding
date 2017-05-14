package com.fbd.core.util.report.util;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import com.fbd.core.util.XMLHandler;
import com.fbd.core.util.report.model.ExcelReportColumnModel;
import com.fbd.core.util.report.model.ExcelReportModel;

public class ExcelReportXMLHandler implements XMLHandler<ExcelReportModel> {

	public static final String ROOT_PATH = "/report";
	public static final String REPORT_COLUMN = "report-header/column";
	
	@SuppressWarnings("unchecked")
	public List<ExcelReportModel> handler(Document document) {
		
		Element rootElement = (Element) document.selectSingleNode(ROOT_PATH);
		String fileName = rootElement.attributeValue("name");
		ExcelReportModel excelReport = new ExcelReportModel();
		excelReport.setReportName(fileName);
		List<Element> childrenEles = rootElement.selectNodes(REPORT_COLUMN);
		if(!childrenEles.isEmpty()){
			parseExcelReportColumn(childrenEles,excelReport);
		}
		List<ExcelReportModel> reports = new ArrayList<ExcelReportModel>(1);
		reports.add(excelReport);
		return reports;
	}

	/**
	 * 解析表头
	 * @param childrenEles
	 * @param excelReport
	 */
	private void parseExcelReportColumn(List<Element> childrenEles,
			ExcelReportModel excelReport) {
	
		ExcelReportColumnModel column = null;
		List<ExcelReportColumnModel> columns = excelReport.getReportColumns();
		for(Element ele:childrenEles){
			
			column = new ExcelReportColumnModel();
			String name = ele.attributeValue("name");
			String dataType = ele.attributeValue("datatype");
			String format = ele.attributeValue("format");
			String mapping = ele.attributeValue("mapping");
			String dictpath = ele.attributeValue("dictpath");
			String start = ele.attributeValue("start");
			String step = ele.attributeValue("step");
			column.setName(name);
			column.setDataType(dataType);
			column.setFormat(format);
			column.setMapping(mapping);
			column.setDictpath(dictpath);
			column.setStart(start);
			column.setStep(step);			
			columns.add(column);
		}
		
	}

	public String convert(Document document, ExcelReportModel targetObj) {
		return null;
	}

}
