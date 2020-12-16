/**
 * 
 */
package com.nec.asia.nic.framework.report.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author prasad_karnati
 *
 */
public class NicRejectedApplicationDto implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private Integer itemNumbe;
	private String  ninNumber;
	private String  appReferenceNumber;
	private String  firstName;
	private String  surName;
	private String  birthSurName;
	private String  gender;
	private Date    birthDate;
	private String	txnType;
	private String  txnStatus;
	private Date    txnDate;
	private String  officeName;
	private String  servedBy;
	private String  rejectedBy;
	private String  rejectComments;
	
	
	/**
	 * @return the itemNumbe
	 */
	public Integer getItemNumbe() {
		return itemNumbe;
	}
	/**
	 * @param itemNumbe the itemNumbe to set
	 */
	public void setItemNumbe(Integer itemNumbe) {
		this.itemNumbe = itemNumbe;
	}
	/**
	 * @return the ninNumber
	 */
	public String getNinNumber() {
		return ninNumber;
	}
	/**
	 * @param ninNumber the ninNumber to set
	 */
	public void setNinNumber(String ninNumber) {
		this.ninNumber = ninNumber;
	}
	/**
	 * @return the appReferenceNumber
	 */
	public String getAppReferenceNumber() {
		return appReferenceNumber;
	}
	/**
	 * @param appReferenceNumber the appReferenceNumber to set
	 */
	public void setAppReferenceNumber(String appReferenceNumber) {
		this.appReferenceNumber = appReferenceNumber;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the surName
	 */
	public String getSurName() {
		return surName;
	}
	/**
	 * @param surName the surName to set
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}
	/**
	 * @return the birthSurName
	 */
	public String getBirthSurName() {
		return birthSurName;
	}
	/**
	 * @param birthSurName the birthSurName to set
	 */
	public void setBirthSurName(String birthSurName) {
		this.birthSurName = birthSurName;
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
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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
	 * @return the txnDate
	 */
	public Date getTxnDate() {
		return txnDate;
	}
	/**
	 * @param txnDate the txnDate to set
	 */
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}
	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	/**
	 * @return the servedBy
	 */
	public String getServedBy() {
		return servedBy;
	}
	/**
	 * @param servedBy the servedBy to set
	 */
	public void setServedBy(String servedBy) {
		this.servedBy = servedBy;
	}
	/**
	 * @return the rejectedBy
	 */
	public String getRejectedBy() {
		return rejectedBy;
	}
	/**
	 * @param rejectedBy the rejectedBy to set
	 */
	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	/**
	 * @return the rejectComments
	 */
	public String getRejectComments() {
		return rejectComments;
	}
	/**
	 * @param rejectComments the rejectComments to set
	 */
	public void setRejectComments(String rejectComments) {
		this.rejectComments = rejectComments;
	}
	
	
}
