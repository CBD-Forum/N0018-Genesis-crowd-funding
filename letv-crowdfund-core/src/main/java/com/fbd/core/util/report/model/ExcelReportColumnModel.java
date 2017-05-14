package com.fbd.core.util.report.model;


public class ExcelReportColumnModel {


	private String name;
	private String dataType;
	private String format;
	private String mapping;
	private String dictpath;
	private String start;
	private String step;

	private int stepValue;
	private int currentValue;
	private boolean iSeqInit = false;

	/**
	 * @return the mapping
	 */
	public String getMapping() {
		return mapping;
	}

	/**
	 * @param mapping
	 *            the mapping to set
	 */
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the dictpath
	 */
	public String getDictpath() {
		return dictpath;
	}

	/**
	 * @param dictpath
	 *            the dictpath to set
	 */
	public void setDictpath(String dictpath) {
		this.dictpath = dictpath;
	}

	public void setStart(String start) {
		this.start = start;
		try {
			this.currentValue = Integer.parseInt(start);
		} catch (Exception e) {
		}
	}

	public String getStart() {
		return this.start;
	}

	public void setStep(String step) {
		this.step = step;
		try {
			this.stepValue = Integer.parseInt(step);
		} catch (Exception e) {
			stepValue = 1;
		}
	}

	public String getStep() {
		return this.step;
	}

	public int getCurrentValue() {
		return this.currentValue;
	}

	public int getNextSequenceValue() {
		int nextValue = 0;

		if (!"sequence".equals(this.getDataType())) {
			throw new RuntimeException("invoke error,datatype is not sequence!");
		}
		if (!iSeqInit) {
			this.currentValue = this.currentValue - this.stepValue;
			iSeqInit = true;
		}
		nextValue = this.currentValue + this.stepValue;
		this.currentValue = nextValue;
		return nextValue;
	}

}
