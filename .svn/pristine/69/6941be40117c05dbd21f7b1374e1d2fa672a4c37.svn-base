package com.nec.asia.nic.comp.job.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author chris_wong
 * <p>DTO for Perso Data Preparation and Perso Submission</p>
 */
/**
 * Sample Transaction Info XML: for CI to update Status.
<NicTransactionStatusUpdate Ccn="958533325" Status="INVENTORY_CARD_DESTROYED" />
 *
 */

/*
 * Modification History:
 * 
 * 22 Jan 2013 (chris) : update ccn to Ccn
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="NicTransactionStatusUpdate")
public class TransactionInfoDTO extends AbstractCardInfoDTO {
	private static final long serialVersionUID = 827571471822340952L;
	
	@XmlAttribute(name = "Ccn")
	protected String ccn;	

	public String getCcn() {
		return ccn;
	}
	public void setCcn(String ccn) {
		this.ccn = ccn;
	}	
}
