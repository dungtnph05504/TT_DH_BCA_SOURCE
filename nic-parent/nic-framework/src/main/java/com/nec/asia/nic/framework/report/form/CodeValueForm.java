/**
 * 
 */
package com.nec.asia.nic.framework.report.form;

import java.util.List;
import java.util.Map;



/**
 * @author Prasad_Karnati
 *
 */

/* 
* Modification History:
*  
* 08 Oct 2013 (Chris Lai): Update to set/get the ParentCodeValue
*/

public class CodeValueForm {
	
	private String codeId;
	private String codeValue;
	private String codeValueDesc;
	private String expireDate;	
	private String codePriyority;
	private String codePriority;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private String deleteFlag;
	private String systemId;	
	private String txnType;
	private String txnSubType;
	private String txnStatus;
	private String  gender;
	private String ricOffice;
	private String [] selectedCols;
	private String [] unSelectedCols;
	private List<String> codeValues;	//no usage reference 
	private List<String> txnSubTypeValues;
	private List<String> txnStatusValues;
	private List<String> ricOfficeValues;
	private List<String> genderValues;
	private List<String> columnValues;
	private String tableName;
	private  String columnString;
	private Map<String,String> columnParamList;
	private String reportId;
	private String objName;
	private String transactionType;
	private String startDate;
	private String endDate;
	private String txnServedBy;
	private String reportFileName;
	private String codeName;
	private String displayTable;
	private String status;
	private String message;
	private String loginUser;
	private String mode;
	private String parentCodeValue;
	private String rptFrmt;

	/*
	 * Modification History:
	 * 
	 * 9 Jan 2014 (chris lai) : update with toString method
	 */
	
	
	public String getRptFrmt() {
		return rptFrmt;
	}
	public void setRptFrmt(String rptFrmt) {
		this.rptFrmt = rptFrmt;
	}
	/**
	 * @return the displayTable
	 */
	public String getDisplayTable() {
		return displayTable;
	}
	/**
	 * @param displayTable the displayTable to set
	 */
	public void setDisplayTable(String displayTable) {
		this.displayTable = displayTable;
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
	 * @return the txnSubTypeValues
	 */
	public List<String> getTxnSubTypeValues() {
		return txnSubTypeValues;
	}
	/**
	 * @param txnSubTypeValues the txnSubTypeValues to set
	 */
	public void setTxnSubTypeValues(List<String> txnSubTypeValues) {
		this.txnSubTypeValues = txnSubTypeValues;
	}
	/**
	 * @return the txnStatusValues
	 */
	public List<String> getTxnStatusValues() {
		return txnStatusValues;
	}
	/**
	 * @param txnStatusValues the txnStatusValues to set
	 */
	public void setTxnStatusValues(List<String> txnStatusValues) {
		this.txnStatusValues = txnStatusValues;
	}
	/**
	 * @return the ricOfficeValues
	 */
	public List<String> getRicOfficeValues() {
		return ricOfficeValues;
	}
	/**
	 * @param ricOfficeValues the ricOfficeValues to set
	 */
	public void setRicOfficeValues(List<String> ricOfficeValues) {
		this.ricOfficeValues = ricOfficeValues;
	}

	public List<String> getCodeValues() {
		return codeValues;
	}

	public void setCodeValues(List<String> codeValues) {
		this.codeValues = codeValues;
	}
	/**
	 * @return the codeId
	 */
	public String getCodeId() {
		return codeId;
	}
	/**
	 * @param codeId the codeId to set
	 */
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	/**
	 * @return the codeValue
	 */
	public String getCodeValue() {
		return codeValue;
	}
	/**
	 * @param codeValue the codeValue to set
	 */
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	/**
	 * @return the codeValueDesc
	 */
	public String getCodeValueDesc() {
		return codeValueDesc;
	}
	/**
	 * @param codeValueDesc the codeValueDesc to set
	 */
	public void setCodeValueDesc(String codeValueDesc) {
		this.codeValueDesc = codeValueDesc;
	}
	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return expireDate;
	}
	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	/**
	 * @return the codePriyority
	 */
	public String getCodePriyority() {
		return codePriyority;
	}
	/**
	 * @param codePriyority the codePriyority to set
	 */
	public void setCodePriyority(String codePriyority) {
		this.codePriyority = codePriyority;
	}
	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}
	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}
	/**
	 * @param txnType the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	/**
	 * @return the txnSubType
	 */
	public String getTxnSubType() {
		return txnSubType;
	}
	/**
	 * @param txnSubType the txnSubType to set
	 */
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	/**
	 * @return the txnStatus
	 */
	public String getTxnStatus() {
		return txnStatus;
	}
	/**
	 * @param txnStatus the txnStatus to set
	 */
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
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
	/**
	 * @return the genderValues
	 */
	public List<String> getGenderValues() {
		return genderValues;
	}
	/**
	 * @param genderValues the genderValues to set
	 */
	public void setGenderValues(List<String> genderValues) {
		this.genderValues = genderValues;
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
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the txnServedBy
	 */
	public String getTxnServedBy() {
		return txnServedBy;
	}
	/**
	 * @param txnServedBy the txnServedBy to set
	 */
	public void setTxnServedBy(String txnServedBy) {
		this.txnServedBy = txnServedBy;
	}
	public String getCodePriority() {
		return codePriority;
	}
	public void setCodePriority(String codePriority) {
		this.codePriority = codePriority;
	}
	public String getReportFileName() {
		return reportFileName;
	}
	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	/**
	 * @return the codeName
	 */
	public String getCodeName() {
		return codeName;
	}
	/**
	 * @param codeName the codeName to set
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	//08 Oct 2013 (Chris Lai): Update to set/get the ParentCodeValue
	/**
	 * @return the parentCodeValue
	 */
	public String getParentCodeValue() {
		return parentCodeValue;
	}
	/**
	 * @param parentCodeValue the parentCodeValue to set
	 */
	public void setParentCodeValue(String parentCodeValue) {
		this.parentCodeValue = parentCodeValue;
	}
	
	//9 Jan 2014 (chris lai) : update with toString method
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CodeValueForm [codeId=" + codeId + ", codeValue="
				+ codeValue + ", codeValueDesc=" + codeValueDesc + 
				", expireDate=" + expireDate + ", codePriyority="
				+ codePriyority + ", codePriority="+ codePriority + ", createBy=" + createBy + 
				", createDate=" + createDate + ", updateBy="
				+ updateBy + ", updateDate=" + updateDate + 
				", deleteFlag=" + deleteFlag + ", systemId="
				+ systemId + ", txnType=" + txnType + 
				", txnSubType=" + txnSubType + ", txnStatus=" + txnStatus + 
				", gender="+ gender + ", ricOffice=" + ricOffice + 
				", transactionType=" + transactionType + ", startDate="
				+ startDate + ", endDate="+ endDate + ", txnServedBy=" + txnServedBy + 
				", reportFileName=" + reportFileName + ", codeName="
				+ codeName + ", displayTable=" + displayTable + 
				", status=" + status + ", message="
				+ message + ", loginUser=" + loginUser + 
				", parentCodeValue=" + parentCodeValue + ", rptFrmt=" + rptFrmt + "]";
	}
	
}
