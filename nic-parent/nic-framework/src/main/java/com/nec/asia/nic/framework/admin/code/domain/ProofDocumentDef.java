package com.nec.asia.nic.framework.admin.code.domain;

// Generated May 13, 2013 10:20:54 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.List;
import java.util.Map;

@Deprecated 
/**
 * @deprecated this class is not supported by this version 
 */
public class ProofDocumentDef implements java.io.Serializable {

	private static final long serialVersionUID = 2933223057094138609L;

	private ProofDocumentDefId id;
	private String documentDesc;
	private String requireIndicator;
	private String systemId;
	private String createBy;
	private String createWkstnId;
	private Date createDate;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDate;
	private String deleteBy;
	private String deleteWkstnId;
	private Date deleteDate;
	private String deleteFlag;
	private Map<String, List<String>> transactionTypeMap;
	private List<String> newRegistration;
	private List<String> replacement;
	private List<String> updateParticulars;
	private List<String> documentIdList;
	private Map<String,String> txnTypeList;
	private Map<String,String> txnSubTypeLIst;
	private String transactionType;
	private String transactionSubtype;
	private String documentId;
	private String status;
	private  Map<String, Object> columnMap;
	

	public ProofDocumentDef() {
	}

	public ProofDocumentDef(ProofDocumentDefId id,
			String requireIndicator) {
		this.id = id;
		this.requireIndicator = requireIndicator;
	}

	public ProofDocumentDef(ProofDocumentDefId id, String documentDesc,
			String requireIndicator, String systemId, String createBy,
			String createWkstnId, Date createDate, String updateBy,
			String updateWkstnId, Date updateDate, String deleteBy,
			String deleteWkstnId, Date deleteDate, String deleteFlag) {
		this.id = id;
		this.documentDesc = documentDesc;
		this.requireIndicator = requireIndicator;
		this.systemId = systemId;
		this.createBy = createBy;
		this.createWkstnId = createWkstnId;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateWkstnId = updateWkstnId;
		this.updateDate = updateDate;
		this.deleteBy = deleteBy;
		this.deleteWkstnId = deleteWkstnId;
		this.deleteDate = deleteDate;
		this.deleteFlag = deleteFlag;
	}

	public ProofDocumentDefId getId() {
		return this.id;
	}

	public void setId(ProofDocumentDefId id) {
		this.id = id;
	}

	public String getDocumentDesc() {
		return this.documentDesc;
	}

	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}

	public String getRequireIndicator() {
		return this.requireIndicator;
	}

	public void setRequireIndicator(String requireIndicator) {
		this.requireIndicator = requireIndicator;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateWkstnId() {
		return this.createWkstnId;
	}

	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateWkstnId() {
		return this.updateWkstnId;
	}

	public void setUpdateWkstnId(String updateWkstnId) {
		this.updateWkstnId = updateWkstnId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDeleteBy() {
		return this.deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public String getDeleteWkstnId() {
		return this.deleteWkstnId;
	}

	public void setDeleteWkstnId(String deleteWkstnId) {
		this.deleteWkstnId = deleteWkstnId;
	}

	public Date getDeleteDate() {
		return this.deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Map<String, List<String>> getTransactionTypeMap() {
		return transactionTypeMap;
	}

	public void setTransactionTypeMap(Map<String, List<String>> transactionTypeMap) {
		this.transactionTypeMap = transactionTypeMap;
	}

	public List<String> getDocumentIdList() {
		return documentIdList;
	}

	public void setDocumentIdList(List<String> documentIdList) {
		this.documentIdList = documentIdList;
	}

	/**
	 * @return the newRegistration
	 */
	public List<String> getNewRegistration() {
		return newRegistration;
	}

	/**
	 * @param newRegistration the newRegistration to set
	 */
	public void setNewRegistration(List<String> newRegistration) {
		this.newRegistration = newRegistration;
	}

	/**
	 * @return the replacement
	 */
	public List<String> getReplacement() {
		return replacement;
	}

	/**
	 * @param replacement the replacement to set
	 */
	public void setReplacement(List<String> replacement) {
		this.replacement = replacement;
	}

	/**
	 * @return the updateParticulars
	 */
	public List<String> getUpdateParticulars() {
		return updateParticulars;
	}

	/**
	 * @param updateParticulars the updateParticulars to set
	 */
	public void setUpdateParticulars(List<String> updateParticulars) {
		this.updateParticulars = updateParticulars;
	}
	/**
	 * @return the txnTypeList
	 */
	public Map<String, String> getTxnTypeList() {
		return txnTypeList;
	}

	/**
	 * @param txnTypeList the txnTypeList to set
	 */
	public void setTxnTypeList(Map<String, String> txnTypeList) {
		this.txnTypeList = txnTypeList;
	}

	/**
	 * @return the txnSubTypeLIst
	 */
	public Map<String, String> getTxnSubTypeLIst() {
		return txnSubTypeLIst;
	}

	/**
	 * @param txnSubTypeLIst the txnSubTypeLIst to set
	 */
	public void setTxnSubTypeLIst(Map<String, String> txnSubTypeLIst) {
		this.txnSubTypeLIst = txnSubTypeLIst;
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
	 * @return the transactionSubtype
	 */
	public String getTransactionSubtype() {
		return transactionSubtype;
	}

	/**
	 * @param transactionSubtype the transactionSubtype to set
	 */
	public void setTransactionSubtype(String transactionSubtype) {
		this.transactionSubtype = transactionSubtype;
	}

	/**
	 * @return the documentId
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
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

	/**
	 * @return the columnMap
	 */
	public Map<String, Object> getColumnMap() {
		return columnMap;
	}

	/**
	 * @param columnMap the columnMap to set
	 */
	public void setColumnMap(Map<String, Object> columnMap) {
		this.columnMap = columnMap;
	}

}
