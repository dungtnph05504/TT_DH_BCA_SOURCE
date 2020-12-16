package com.nec.asia.nic.comp.job.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author setia_budiyono
 *
 */

/* 
 * Modification History:
 * 
 * 23 May 2014 (chris): add constructor
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LogInfoDTO implements Serializable  {
	
	@XmlElement(name = "Reason")
	protected String reason;
	@XmlElement(name = "Remark")
	protected String remark;
	
	public LogInfoDTO() {
	}
	
	public LogInfoDTO(String reason, String remark) {
		this.reason=reason;
		this.remark=remark;
	}
	
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
}
