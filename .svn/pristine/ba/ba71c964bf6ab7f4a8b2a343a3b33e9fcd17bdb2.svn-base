package com.nec.asia.nic.comp.trans.utils.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 	<fingerprint_template fingerprint_no="0" fingerprint_quality="107" minutia_count="69" fingerprint_minutia="BASE64_STRING"/>
 */

@XStreamAlias("fingerprint_template")
@XmlAccessorType(XmlAccessType.FIELD)
public class FingerprintTemplate{
	@XStreamAlias("fingerprint_no")
	@XStreamAsAttribute
	private Integer fingerprintNo;
	@XStreamAlias("fingerprint_quality")
	@XStreamAsAttribute
	private Integer fingerprintQuality;
	@XStreamAlias("minutia_count")
	@XStreamAsAttribute
	private Integer minutiaCount;
	@XStreamAlias("fingerprint_minutia")
	@XStreamAsAttribute
	private String fingerprintMinutia;
	
	public FingerprintTemplate() {}
	public Integer getFingerprintNo() {
		return fingerprintNo;
	}
	public void setFingerprintNo(Integer fingerprintNo) {
		this.fingerprintNo = fingerprintNo;
	}
	public Integer getFingerprintQuality() {
		return fingerprintQuality;
	}
	public void setFingerprintQuality(Integer fingerprintQuality) {
		this.fingerprintQuality = fingerprintQuality;
	}
	public Integer getMinutiaCount() {
		return minutiaCount;
	}
	public void setMinutiaCount(Integer minutiaCount) {
		this.minutiaCount = minutiaCount;
	}
	public String getFingerprintMinutia() {
		return fingerprintMinutia;
	}
	public void setFingerprintMinutia(String fingerprintMinutia) {
		this.fingerprintMinutia = fingerprintMinutia;
	}			
}

