/**
 * 
 */
package com.nec.asia.nic.framework.report.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author prasad_karnati
 *
 */
public class NicReportMenuForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String transactionType;
	private String transactionSubType;
	private String transactionStatus;
	private Date   transactionStartDate;
	private Date   transactionEndDate;
	private String  gender;
	private String  transactionServedBy;
	private String namedQuery;	
	private String  servedBy;
	private String	txnType;
	
	private String  ricOffice;
	private List<NicReportMenu> nicReportMenuList; //report parameter as input item (input type and values)
	private String reportId;
	private String [] selectedCols;
	private String [] unSelectedCols;
	
	private List<String> columnValues;
	private String tableName;
	private  String columnString;
	private Map<String,String> columnParamList;
	private String objName;	
	private Map<String,String> queryColumnList;
	
	private String reportFileName;
	private String loginUser;
    private String rptFrmt;
    private String reportType;
    private String reportCategory;
    private String reportDesc;

	
	public String getRptFrmt() {
		return rptFrmt;
	}
	public void setRptFrmt(String rptFrmt) {
		this.rptFrmt = rptFrmt;
	}
	
	
	public String getServedBy() {
		return servedBy;
	}
	public void setServedBy(String servedBy) {
		this.servedBy = servedBy;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	/**
	 * @return the selectedCols
	 */
	public String[] getSelectedCols() {
		return selectedCols;
	}
	/**
	 * @param selectedCols the selectedCols to set
	 */
	public void setSelectedCols(String[] selectedCols) {
		this.selectedCols = selectedCols;
	}
	/**
	 * @return the unSelectedCols
	 */
	public String[] getUnSelectedCols() {
		return unSelectedCols;
	}
	/**
	 * @param unSelectedCols the unSelectedCols to set
	 */
	public void setUnSelectedCols(String[] unSelectedCols) {
		this.unSelectedCols = unSelectedCols;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @return the transactionSubType
	 */
	public String getTransactionSubType() {
		return transactionSubType;
	}
	/**
	 * @param transactionSubType the transactionSubType to set
	 */
	public void setTransactionSubType(String transactionSubType) {
		this.transactionSubType = transactionSubType;
	}
	/**
	 * @return the transactionStatus
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}
	/**
	 * @param transactionStatus the transactionStatus to set
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	/**
	 * @return the transactionStartDate
	 */
	public Date getTransactionStartDate() {
		return transactionStartDate;
	}
	/**
	 * @param transactionStartDate the transactionStartDate to set
	 */
	public void setTransactionStartDate(Date transactionStartDate) {
		this.transactionStartDate = transactionStartDate;
	}
	/**
	 * @return the transactionEndDate
	 */
	public Date getTransactionEndDate() {
		return transactionEndDate;
	}
	/**
	 * @param transactionEndDate the transactionEndDate to set
	 */
	public void setTransactionEndDate(Date transactionEndDate) {
		this.transactionEndDate = transactionEndDate;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the transactionServedBy
	 */
	public String getTransactionServedBy() {
		return transactionServedBy;
	}
	/**
	 * @param transactionServedBy the transactionServedBy to set
	 */
	public void setTransactionServedBy(String transactionServedBy) {
		this.transactionServedBy = transactionServedBy;
	}
	/**
	 * @return the ricOffice
	 */
	public String getRicOffice() {
		return ricOffice;
	}
	/**
	 * @param ricOffice the ricOffice to set
	 */
	public void setRicOffice(String ricOffice) {
		this.ricOffice = ricOffice;
	}

	public List<NicReportMenu> getNicReportMenuList() {
		return nicReportMenuList;
	}

	public void setNicReportMenuList(List<NicReportMenu> nicReportMenuList) {
		this.nicReportMenuList = nicReportMenuList;
	}
	public String getNamedQuery() {
		return namedQuery;
	}
	public void setNamedQuery(String namedQuery) {
		this.namedQuery = namedQuery;
	}
	/**
	 * @return the queryColumnList
	 */
	public Map<String, String> getQueryColumnList() {
		return queryColumnList;
	}
	/**
	 * @param queryColumnList the queryColumnList to set
	 */
	public void setQueryColumnList(Map<String, String> queryColumnList) {
		this.queryColumnList = queryColumnList;
	}
	public String getReportFileName() {
		return reportFileName;
	}
	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}
	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	/**
	 * @return the reportCategory
	 */
	public String getReportCategory() {
		return reportCategory;
	}
	/**
	 * @param reportCategory the reportCategory to set
	 */
	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}
	/**
	 * @return the reportDesc
	 */
	public String getReportDesc() {
		return reportDesc;
	}
	/**
	 * @param reportDesc the reportDesc to set
	 */
	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}
		
		
		
		
	
		
}
