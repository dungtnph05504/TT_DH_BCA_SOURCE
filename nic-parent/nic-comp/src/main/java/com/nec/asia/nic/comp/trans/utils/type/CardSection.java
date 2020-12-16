package com.nec.asia.nic.comp.trans.utils.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * <card_section card_template="BASE64_STRING"/>
 */
@XStreamAlias("card_section")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardSection{
	@XStreamAlias("card_template")
	@XStreamAsAttribute
	private String cardTemplate;
	
	public CardSection() {}
	public String getCardTemplate() {
		return cardTemplate;
	}
	public void setCardTemplate(String cardTemplate) {
		this.cardTemplate = cardTemplate;
	}			
}