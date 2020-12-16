package com.nec.asia.nic.comp.job.dto;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * XML Structure for Card Inventory Web Service.
 * 
 * @author chris_wong
 * 
    //<NICRefTransactionStatusUpdate>
    //      <Ccn>111111111</Ccn>
    //      <OfficerID>Officer001</OfficerID>
    //      <WkstnID>123456</WkstnID>
    //      <SiteCode>site001</SiteCode>
    //      <RefTransactionStatus>Active</RefTransactionStatus>
    //</NICRefTransactionStatusUpdate>
 */
/* 
 * Modification History:
 * 
 * 21 Oct 2013 (chris): init 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="NICRefTransactionStatusUpdate")
public class NICRefTransactionStatusUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "Ccn")
	protected String ccn;
	@XmlElement(name = "OfficerID")
	protected String officerId;
	@XmlElement(name = "WkstnID")
	protected String wkstnId;
	@XmlElement(name = "SiteCode")
	protected String siteCode;	
	@XmlElement(name = "RefTransactionStatus")
	protected String refTransactionStatus;
	
	public NICRefTransactionStatusUpdateDTO() {}

	public String getCcn() {
		return ccn;
	}

	public void setCcn(String ccn) {
		this.ccn = ccn;
	}

	public String getOfficerId() {
		return officerId;
	}

	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}

	public String getWkstnId() {
		return wkstnId;
	}

	public void setWkstnId(String wkstnId) {
		this.wkstnId = wkstnId;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getRefTransactionStatus() {
		return refTransactionStatus;
	}

	public void setRefTransactionStatus(String refTransactionStatus) {
		this.refTransactionStatus = refTransactionStatus;
	}

}
