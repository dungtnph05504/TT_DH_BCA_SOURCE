package com.nec.asia.nic.comp.trans.utils;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


public class ReportDataSet {

	/** The series name. */
	@XStreamAsAttribute
	private String seriesName;
	
	/** The color. */
	@XStreamAsAttribute
	private String color;
	
	/** The parent y axis. */
	@XStreamAsAttribute
	private String parentYAxis;
	
	
	/** The record list. */
	@XStreamImplicit(itemFieldName="set")
	private List<ReportDataRecord> recordList=new ArrayList<ReportDataRecord>();

	/**
	 * Instantiates a new report data set.
	 */
	public ReportDataSet(){
		
	}
	
	/**
	 * Instantiates a new report data set.
	 *
	 * @param seriesName the series name
	 * @param color the color
	 * @param parentYAxis the parent y axis
	 */
	public ReportDataSet(String seriesName, String color,String parentYAxis) {
		this.seriesName=seriesName;
		this.color=color;
		this.parentYAxis=parentYAxis;
	}
	
	/**
	 * Adds the.
	 *
	 * @param value the value
	 * @param hoverText the hover text
	 * @param link the link
	 */
	public void add(String value,String link) {
		recordList.add(new ReportDataRecord(null, value,  null,link));
	}
	
	/**
	 * Adds the.
	 *
	 * @param value the value
	 * @param hoverText the hover text
	 * @param color the color
	 * @param link the link
	 */
	public void add(String value, String color,String link) {
		recordList.add(new ReportDataRecord(null, value,  color,link));
	}
	
	/**
	 * Gets the record list.
	 *
	 * @return the record list
	 */
	public List<ReportDataRecord> getRecordList() {
		return recordList;
	}

	/**
	 * Sets the record list.
	 *
	 * @param recordList the new record list
	 */
	public void setRecordList(List<ReportDataRecord> recordList) {
		this.recordList = recordList;
	}

	/**
	 * Gets the series name.
	 *
	 * @return the series name
	 */
	public String getSeriesName() {
		return seriesName;
	}

	/**
	 * Sets the series name.
	 *
	 * @param seriesName the new series name
	 */
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Gets the parent y axis.
	 *
	 * @return the parent y axis
	 */
	public String getParentYAxis() {
		return parentYAxis;
	}

	/**
	 * Sets the parent y axis.
	 *
	 * @param parentYAxis the new parent y axis
	 */
	public void setParentYAxis(String parentYAxis) {
		this.parentYAxis = parentYAxis;
	}



	
}
