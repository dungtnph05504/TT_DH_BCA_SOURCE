package com.nec.asia.nic.comp.trans.utils.type;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * fingerprint template of dedicated FMS format 
 * 
 * <pre>
 * {@code
 * 
 * <?xml version="1.0" encoding="UTF-8"?>
 * <tenprint_card>
 * 	<card_section card_template="BASE64_STRING"/>
 *  <fingerprint_section>
 *  </fingerprint_section>
 *	<fingerprint_section minutia_format="FMP5">
 *		<fingerprint_template fingerprint_no="0" fingerprint_quality="107" minutia_count="69" fingerprint_minutia="BASE64_STRING"/>
 *		<fingerprint_template fingerprint_no="1" fingerprint_quality="78" minutia_count="64" fingerprint_minutia="BASE64_STRING"/>
 *		<fingerprint_template fingerprint_no="2" fingerprint_quality="77" minutia_count="56" fingerprint_minutia="BASE64_STRING"/>
 *		<fingerprint_template fingerprint_no="3" fingerprint_quality="60" minutia_count="36" fingerprint_minutia="BASE64_STRING"/>
 *		<fingerprint_template fingerprint_no="4" fingerprint_quality="96" minutia_count="67" fingerprint_minutia="BASE64_STRING"/>
 *		<fingerprint_template fingerprint_no="5" fingerprint_quality="71" minutia_count="50" fingerprint_minutia="BASE64_STRING"/>
 *		<fingerprint_template fingerprint_no="6" fingerprint_quality="80" minutia_count="67" fingerprint_minutia="BASE64_STRING"/>
 *		<fingerprint_template fingerprint_no="7" fingerprint_quality="81" minutia_count="70" fingerprint_minutia="BASE64_STRING"/>
 *		<fingerprint_template fingerprint_no="8" fingerprint_quality="65" minutia_count="60" fingerprint_minutia="BASE64_STRING"/>
 *		<fingerprint_template fingerprint_no="9" fingerprint_quality="82" minutia_count="54" fingerprint_minutia="BASE64_STRING"/>
 *	</fingerprint_section>
 * </tenprint_card>
 * 
 * }
 * </pre>
 * 
 * @author chris
 *
 */
@XStreamAlias("tenprint_card")
public class FmsTemplate {
	
	@XStreamAlias("card_section")
	private CardSection cardSection;
	@XStreamImplicit(itemFieldName="fingerprint_section")
	private List<FingerprintSection> fingerprintSections;
	
	public FmsTemplate() {
	}
	
	public CardSection getCardSection() {
		return cardSection;
	}
	public void setCardSection(CardSection cardSection) {
		this.cardSection = cardSection;
	}
	public List<FingerprintSection> getFingerprintSections() {
		return fingerprintSections;
	}
	public void setFingerprintSection(List<FingerprintSection> fingerprintSections) {
		this.fingerprintSections = fingerprintSections;
	}

}