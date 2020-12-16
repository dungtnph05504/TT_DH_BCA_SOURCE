package com.nec.asia.nic.common.dto;

import java.io.Serializable;

public class RicRegistrationFingerPrintDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fpIndicator;
	private String fp;
	private String fpQuality;
	private String verify;
	private String fpJpg;
	private String minutiaTemplate;
	
//	private String isDefault;
	private String serialNo;
	
	public String getFpIndicator() {
		return fpIndicator;
	}
	public void setFpIndicator(String fpIndicator) {
		this.fpIndicator = fpIndicator;
	}
	public String getFp() {
		return fp;
	}
	public void setFp(String fp) {
		this.fp = fp;
	}
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public String getFpQuality() {
		return fpQuality;
	}
	public void setFpQuality(String fpQuality) {
		this.fpQuality = fpQuality;
	}
	public String getFpJpg() {
		return fpJpg;
	}
	public void setFpJpg(String fpJpg) {
		this.fpJpg = fpJpg;
	}
//	public String getIsDefault() {
//		return isDefault;
//	}
//	public void setIsDefault(String isDefault) {
//		this.isDefault = isDefault;
//	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getMinutiaTemplate() {
		return minutiaTemplate;
	}
	public void setMinutiaTemplate(String minutiaTemplate) {
		this.minutiaTemplate = minutiaTemplate;
	}
	
	
	
	
}