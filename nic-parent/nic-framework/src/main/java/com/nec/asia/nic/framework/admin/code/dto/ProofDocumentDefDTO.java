/**
 * 
 */
package com.nec.asia.nic.framework.admin.code.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 26, 2013
 */
public class ProofDocumentDefDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String documentId;
	private String transactionType;
	private String transactionSubtype;
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
	private Boolean deleteFlag;

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
	 * @return the documentDesc
	 */
	public String getDocumentDesc() {
		return documentDesc;
	}
	/**
	 * @param documentDesc the documentDesc to set
	 */
	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}
	/**
	 * @return the requireIndicator
	 */
	public String getRequireIndicator() {
		return requireIndicator;
	}
	/**
	 * @param requireIndicator the requireIndicator to set
	 */
	public void setRequireIndicator(String requireIndicator) {
		this.requireIndicator = requireIndicator;
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
	 * @return the createWkstnId
	 */
	public String getCreateWkstnId() {
		return createWkstnId;
	}
	/**
	 * @param createWkstnId the createWkstnId to set
	 */
	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
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
	 * @return the updateWkstnId
	 */
	public String getUpdateWkstnId() {
		return updateWkstnId;
	}
	/**
	 * @param updateWkstnId the updateWkstnId to set
	 */
	public void setUpdateWkstnId(String updateWkstnId) {
		this.updateWkstnId = updateWkstnId;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the deleteBy
	 */
	public String getDeleteBy() {
		return deleteBy;
	}
	/**
	 * @param deleteBy the deleteBy to set
	 */
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}
	/**
	 * @return the deleteWkstnId
	 */
	public String getDeleteWkstnId() {
		return deleteWkstnId;
	}
	/**
	 * @param deleteWkstnId the deleteWkstnId to set
	 */
	public void setDeleteWkstnId(String deleteWkstnId) {
		this.deleteWkstnId = deleteWkstnId;
	}
	/**
	 * @return the deleteDate
	 */
	public Date getDeleteDate() {
		return deleteDate;
	}
	/**
	 * @param deleteDate the deleteDate to set
	 */
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	/**
	 * @return the deleteFlag
	 */
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
