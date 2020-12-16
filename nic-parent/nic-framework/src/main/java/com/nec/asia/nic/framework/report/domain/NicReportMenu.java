/**
 * 
 */
package com.nec.asia.nic.framework.report.domain;

import java.util.List;
import java.util.Map;

/**
 * @author prasad_karnati
 *
 */
public class NicReportMenu {
	
	private String propertyType;
	private String propertyName;
	private String propId;	
	private Map<String,String> multiMenu;
	
	private List<String> columnValues;
	private String tableName;
	private  String columnString;
	private Map<String,String> columnParamList;
	private String reportId;
	private String objName;
	private String mandatory;

	
	/**
	 * @return the mandatory
	 */
	public String getMandatory() {
		return mandatory;
	}
	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public Map<String,String> getMultiMenu() {
		return multiMenu;
	}
	public void setMultiMenu(Map<String,String> multiMenu) {
		this.multiMenu = multiMenu;
	}
	public String getPropId() {
		return propId;
	}
	public void setPropId(String propId) {
		this.propId = propId;
	}
	
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	/**
	 * @return the columnValues
	 */
	public List<String> getColumnValues() {
		return columnValues;
	}
	/**
	 * @param columnValues the columnValues to set
	 */
	public void setColumnValues(List<String> columnValues) {
		this.columnValues = columnValues;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the columnString
	 */
	public String getColumnString() {
		return columnString;
	}
	/**
	 * @param columnString the columnString to set
	 */
	public void setColumnString(String columnString) {
		this.columnString = columnString;
	}
	/**
	 * @return the columnParamList
	 */
	public Map<String, String> getColumnParamList() {
		return columnParamList;
	}
	/**
	 * @param columnParamList the columnParamList to set
	 */
	public void setColumnParamList(Map<String, String> columnParamList) {
		this.columnParamList = columnParamList;
	}
	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}
	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	/**
	 * @return the objName
	 */
	public String getObjName() {
		return objName;
	}
	/**
	 * @param objName the objName to set
	 */
	public void setObjName(String objName) {
		this.objName = objName;
	}
	

}
