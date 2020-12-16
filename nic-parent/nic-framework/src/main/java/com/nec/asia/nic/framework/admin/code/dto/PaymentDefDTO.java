/**
 * 
 */
package com.nec.asia.nic.framework.admin.code.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 24, 2013
 */
public class PaymentDefDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String transactionType;
	private String transactionSubtype;
	private Integer noCardLost;
	private Double feeAmount;
	private Boolean reduceRateFlag;
	private Boolean waivableFlag;
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
	 * 
	 */
	
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
	 * @return the noCardLost
	 */
	public int getNoCardLost() {
		return noCardLost;
	}
	/**
	 * @param noCardLost the noCardLost to set
	 */
	public void setNoCardLost(int noCardLost) {
		this.noCardLost = noCardLost;
	}
	/**
	 * @return the feeAmount
	 */
	public Double getFeeAmount() {
		return feeAmount;
	}
	/**
	 * @param feeAmount the feeAmount to set
	 */
	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}
	/**
	 * @return the reduceRateFlag
	 */
	public Boolean getReduceRateFlag() {
		return reduceRateFlag;
	}
	/**
	 * @param reduceRateFlag the reduceRateFlag to set
	 */
	public void setReduceRateFlag(Boolean reduceRateFlag) {
		this.reduceRateFlag = reduceRateFlag;
	}
	/**
	 * @return the waivableFlag
	 */
	public Boolean getWaivableFlag() {
		return waivableFlag;
	}
	/**
	 * @param waivableFlag the waivableFlag to set
	 */
	public void setWaivableFlag(Boolean waivableFlag) {
		this.waivableFlag = waivableFlag;
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
