package com.nec.asia.nic.comp.trans.domain;

import java.util.Date;

/**
 * NicCardReconRpt 
 */

/* 
 * Modification History:
 * 
 * 05 Dec 2013 (chris): Initial Class
 * 02 Jun 2014 (chris): add field cciPendingStockedIn, reconRemarks
 */
@Deprecated
public class NicCardReconRpt implements java.io.Serializable {

	private static final long serialVersionUID = -2519514103281332620L;
	
	private NicCardReconRptId id;
	private String reconStatus;
	private String reconRemarks;
	
	private Long   persoReceived;
	private Long   persoRejected;
	private Long   persoCardPacked;
	private Long   persoCardDelivered;
	private Long   persoWip;
	
	private Long   cciPendingStockedIn;
	private Long   cciStockedIn;
	private Long   cciRejected;
	private Long   cciCollected;
	private Long   cciCollectionFailed;
	private Long   cciPendingCollection;
	private Long   cciCardReturned;
	private Long   invCardReturned;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public NicCardReconRpt() {
	}

	public NicCardReconRpt(NicCardReconRptId id) {
		this.id = id;
	}
	
	public NicCardReconRpt(String siteCode, Date reportCreateDate) {
		NicCardReconRptId id = new NicCardReconRptId(siteCode, reportCreateDate);
		this.id = id;
	}
	public NicCardReconRpt(String siteCode, String reportCreateDate) {
		NicCardReconRptId id = new NicCardReconRptId(siteCode, reportCreateDate);
		this.id = id;
	}

	public NicCardReconRptId getId() {
		return id;
	}

	public void setId(NicCardReconRptId id) {
		this.id = id;
	}
	
	public String getReconStatus() {
		return reconStatus;
	}

	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}

	public String getReconRemarks() {
		return reconRemarks;
	}

	public void setReconRemarks(String reconRemarks) {
		this.reconRemarks = reconRemarks;
	}
	
	public Long getPersoReceived() {
		return persoReceived;
	}

	public void setPersoReceived(Long persoReceived) {
		this.persoReceived = persoReceived;
	}

	public Long getPersoRejected() {
		return persoRejected;
	}

	public void setPersoRejected(Long persoRejected) {
		this.persoRejected = persoRejected;
	}

	public Long getPersoCardPacked() {
		return persoCardPacked;
	}

	public void setPersoCardPacked(Long persoCardPacked) {
		this.persoCardPacked = persoCardPacked;
	}

	public Long getPersoCardDelivered() {
		return persoCardDelivered;
	}

	public void setPersoCardDelivered(Long persoCardDelivered) {
		this.persoCardDelivered = persoCardDelivered;
	}

	public Long getPersoWip() {
		return persoWip;
	}

	public void setPersoWip(Long persoWip) {
		this.persoWip = persoWip;
	}
	
	public Long getCciPendingStockedIn() {
		return cciPendingStockedIn;
	}

	public void setCciPendingStockedIn(Long cciPendingStockedIn) {
		this.cciPendingStockedIn = cciPendingStockedIn;
	}
	
	public Long getCciStockedIn() {
		return cciStockedIn;
	}

	public void setCciStockedIn(Long cciStockedIn) {
		this.cciStockedIn = cciStockedIn;
	}

	public Long getCciRejected() {
		return cciRejected;
	}

	public void setCciRejected(Long cciRejected) {
		this.cciRejected = cciRejected;
	}

	public Long getCciCollected() {
		return cciCollected;
	}

	public void setCciCollected(Long cciCollected) {
		this.cciCollected = cciCollected;
	}

	public Long getCciCollectionFailed() {
		return cciCollectionFailed;
	}

	public void setCciCollectionFailed(Long cciCollectionFailed) {
		this.cciCollectionFailed = cciCollectionFailed;
	}

	public Long getCciPendingCollection() {
		return cciPendingCollection;
	}

	public void setCciPendingCollection(Long cciPendingCollection) {
		this.cciPendingCollection = cciPendingCollection;
	}

	public Long getCciCardReturned() {
		return cciCardReturned;
	}

	public void setCciCardReturned(Long cciCardReturned) {
		this.cciCardReturned = cciCardReturned;
	}

	public Long getInvCardReturned() {
		return invCardReturned;
	}

	public void setInvCardReturned(Long invCardReturned) {
		this.invCardReturned = invCardReturned;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


}
