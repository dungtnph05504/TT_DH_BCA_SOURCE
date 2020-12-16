package com.nec.asia.nic.dx.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author chris_wong
 * <p>DTO for FP Verification Log Data</p>
 */
/**
 * Sample XML:
 * 
<DataExchange source="RIC" target="NIC" version="1.0">
  <IssuanceDetails />
</DataExchange>
 *
 */
/* 
 * Modification History:
 * 
 * 29 Nov 2013 (chris): init class 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="DataExchange")
public class DataExchangeDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
	
	/*
	 * DTO to keep fp verification log data
	 */
	@XmlElement(name = "IssuanceDetails")
    protected IssuanceDetailsDTO issuanceDetails;
	
	@XmlAttribute//(name = "source")
	protected String source;
	@XmlAttribute//(name = "target")
	protected String target;
	@XmlAttribute//(name = "version")
	protected String version;
	
	public DataExchangeDTO() {
		super();
	}

	public IssuanceDetailsDTO getIssuanceDetails() {
		return issuanceDetails;
	}

	public void setIssuanceDetails(IssuanceDetailsDTO issuanceDetails) {
		this.issuanceDetails = issuanceDetails;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
