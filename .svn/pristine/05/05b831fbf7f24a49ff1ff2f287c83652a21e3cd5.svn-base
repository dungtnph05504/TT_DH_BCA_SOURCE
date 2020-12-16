package com.nec.asia.nic.comp.job.dto;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author chris_wong
 * 
 * xml format:
 * <CardData Nin="A0123456789120" PersoRefID="1" TransactionID="X10000000A" ReprintCount="0" Ccn="123456780" SAMKeyVersion="1" />
 * new xml format:
 * <CardData PackageSequence="1" Nin="A999999999990A" PersoRefID="9999990" TransactionID="XXXXX-2013-001700" ReprintCount="2" Ccn="999999990" SAMKeyVersion="0" />
 */
/* 
 * Modification History:
 * 
 * 13 Sep 2013 (chris): add packageSequence 
 * 23 Oct 2013 (chris): add constructor
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CardDataDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "PackageSequence")
	protected String packageSequence;	
	@XmlAttribute(name = "Nin")
	protected String nin;
	@XmlAttribute(name = "PersoRefID")
	protected String persoRefID;
	@XmlAttribute(name = "TransactionID")
	protected String transactionID;
	@XmlAttribute(name = "ReprintCount")
	protected String reprintCount;
	@XmlAttribute(name = "Ccn")
	protected String ccn;
	@XmlAttribute(name = "SAMKeyVersion")
	protected String SAMKeyVersion;
	
	public CardDataDTO() {}
	public CardDataDTO(String packageSequence, String nin, String persoRefId, String transactionId, String reprintCount, String ccn, String sAMKeyVersion) {
		this.packageSequence = packageSequence;
		this.nin = nin;
		this.persoRefID = persoRefId;
		this.transactionID = transactionId;
		this.reprintCount = reprintCount;
		this.ccn = ccn;
		this.SAMKeyVersion = sAMKeyVersion;
	}
	
	//13 Sep 2013 (chris): add packageSequence - start
	/**
	 * @return the packageSequence
	 */
	public String getPackageSequence() {
		return packageSequence;
	}
	
	/**
	 * @param packageSequence the packageSequence to set
	 */
	public void setPackageSequence(String packageSequence) {
		this.packageSequence = packageSequence;
	}
	//13 Sep 2013 (chris): add packageSequence - end
	
	/**
	 * @return the nin
	 */
	public String getNin() {
		return nin;
	}
	/**
	 * @param nin the nin to set
	 */
	public void setNin(String nin) {
		this.nin = nin;
	}
	/**
	 * @return the persoRefID
	 */
	public String getPersoRefID() {
		return persoRefID;
	}
	/**
	 * @param persoRefID the persoRefID to set
	 */
	public void setPersoRefID(String persoRefID) {
		this.persoRefID = persoRefID;
	}
	/**
	 * @return the transactionID
	 */
	public String getTransactionID() {
		return transactionID;
	}
	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	/**
	 * @return the reprintCount
	 */
	public String getReprintCount() {
		return reprintCount;
	}
	/**
	 * @param reprintCount the reprintCount to set
	 */
	public void setReprintCount(String reprintCount) {
		this.reprintCount = reprintCount;
	}
	/**
	 * @return the ccn
	 */
	public String getCcn() {
		return ccn;
	}
	/**
	 * @param ccn the ccn to set
	 */
	public void setCcn(String ccn) {
		this.ccn = ccn;
	}
	/**
	 * @return the SAMKeyVersion
	 */
	public String getSAMKeyVersion() {
		return SAMKeyVersion;
	}
	/**
	 * @param SAMKeyVersion the sAMKeyVersion to set
	 */
	public void setSAMKeyVersion(String SAMKeyVersion) {
		this.SAMKeyVersion = SAMKeyVersion;
	}
}
