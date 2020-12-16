package com.nec.asia.nic.comp.trans.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class ReportDefinition.
 *
 * @author Peddi Swapna
 * @version 1.0
 * @since 20 Dec 2013
 */
public abstract class ReportDefinition {
	
	private String reportQuery;
	
	private String reportColumns;
	
	/** The report query parameter list. */
	private List<ReportParameter> reportParameterList = new ArrayList<ReportParameter>();
	
	
	/**
	 * @return the reportQuery
	 */
	public String getReportQuery() {
		return reportQuery;
	}

	/**
	 * @param reportQuery the reportQuery to set
	 */
	public void setReportQuery(String reportQuery) {
		this.reportQuery = reportQuery;
	}

	/**
	 * @return the reportColumns
	 */
	public String getReportColumns() {
		return reportColumns;
	}

	/**
	 * @param reportColumns the reportColumns to set
	 */
	public void setReportColumns(String reportColumns) {
		this.reportColumns = reportColumns;
	}

	/**
	 * @return the reportParameterList
	 */
	public List<ReportParameter> getReportParameterList() {
		return reportParameterList;
	}

	/**
	 * @param reportParameterList the reportParameterList to set
	 */
	public void setReportParameterList(List<ReportParameter> reportParameterList) {
		this.reportParameterList = reportParameterList;
	}

	
}
