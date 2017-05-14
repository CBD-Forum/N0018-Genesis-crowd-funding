package com.fbd.core.util.report.model;

import java.util.ArrayList;
import java.util.List;

public class ExcelReportModel {

	
	private String reportName;
	private String encoding;
	private List<ExcelReportColumnModel> reportColumns;
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	
	public ExcelReportModel() {
		super();
		this.encoding = DEFAULT_ENCODING;
		this.reportColumns = new ArrayList<ExcelReportColumnModel>();
	}
	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}
	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	
	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	/**
	 * @return the reportColumns
	 */
	public List<ExcelReportColumnModel> getReportColumns() {
		return reportColumns;
	}
	/**
	 * @param reportColumns the reportColumns to set
	 */
	public void setReportColumns(List<ExcelReportColumnModel> reportColumns) {
		this.reportColumns = reportColumns;
	}
}
