package com.nec.asia.nic.comp.trans.utils.type;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * <fingerprint_section minutia_format="FMP5">
 * 	<fingerprint_template />
 *	<fingerprint_template />
 * </fingerprint_section>
 */
@XStreamAlias("fingerprint_section")
@XmlAccessorType(XmlAccessType.FIELD)
public class FingerprintSection{
	@XStreamAlias("minutia_format")
	@XStreamAsAttribute
	private String minutiaFormat;
	@XStreamImplicit(itemFieldName="fingerprint_template")
	private List<FingerprintTemplate> fingerprintTemplates;
	
	public FingerprintSection(){}
	public String getMinutiaFormat() {
		return minutiaFormat;
	}
	
	public void setMinutiaFormat(String minutiaFormat) {
		this.minutiaFormat = minutiaFormat;
	}

	public List<FingerprintTemplate> getFingerprintTemplates() {
		return fingerprintTemplates;
	}

	public void setFingerprintTemplates(List<FingerprintTemplate> fingerprintTemplates) {
		this.fingerprintTemplates = fingerprintTemplates;
	}	
}
