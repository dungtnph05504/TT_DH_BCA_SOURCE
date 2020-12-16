package com.nec.asia.nic.comp.trans.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.nic.comp.trans.utils.type.CardSection;
import com.nec.asia.nic.comp.trans.utils.type.FingerprintSection;
import com.nec.asia.nic.comp.trans.utils.type.FingerprintTemplate;
import com.nec.asia.nic.comp.trans.utils.type.FmsTemplate;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class FmsTemplateXmlConvertor {
	private Logger logger = LoggerFactory.getLogger(FmsTemplateXmlConvertor.class);
	private XStream xStream;
	
	public FmsTemplateXmlConvertor() {
		try {
			xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("_-", "_")));
			xStream.processAnnotations(FmsTemplate.class);
			xStream.processAnnotations(CardSection.class);
			xStream.processAnnotations(FingerprintSection.class);
			xStream.processAnnotations(FingerprintTemplate.class);
		} catch (Exception e) {
			logger.error("Error while init FmsTemplateXmlConvertor:" + e.getMessage(), e);
			throw e;
		}
	}
	
	public FmsTemplate getPayload(String tenprintCardXml) throws Exception {
		try {
			return (FmsTemplate) xStream.fromXML(tenprintCardXml);
		} catch (Exception e) {
			logger.error("Error while parsing the tenprint card data:" + e.getMessage(), e);
			throw e;
		}
	}
	
	public String getXml(FmsTemplate fmsTemplate) throws Exception {
		try {
			return (String) xStream.toXML(fmsTemplate);
		} catch (Exception e) {
			logger.error("Error while parsing the tenprint card data:" + e.getMessage(), e);
			throw e;
		}
	}
	
	public static void main(String[] args) {
		FmsTemplateXmlConvertor xmlConvertor = new FmsTemplateXmlConvertor();
		try {
			String tenprintCardXml = org.apache.commons.io.FileUtils.readFileToString(new java.io.File("D:/NIC-1.0/nic-parent/nic-ws/src/test/resources/candidate1/TPL.xml"));
			FmsTemplate fmsTemplate = xmlConvertor.getPayload(tenprintCardXml);
			System.out.println("fmsTemplate: "+fmsTemplate);
			System.out.println("fmsTemplate.cardSection: "+fmsTemplate.getCardSection());
			System.out.println("fmsTemplate.cardSection.cardTemplate: "+fmsTemplate.getCardSection().getCardTemplate());
			
			for (FingerprintSection fs : fmsTemplate.getFingerprintSections()) {
				System.out.println("fmsTemplate.FingerSection.fingerprintTemplate.minutia_format: "+fs.getMinutiaFormat());
				System.out.println("fmsTemplate.FingerSection: "+fs.getFingerprintTemplates());
				for (FingerprintTemplate ft : fs.getFingerprintTemplates())
					System.out.println("fmsTemplate.FingerSection.FingerprintTemplate: "+ft.getFingerprintNo() +" "+ft.getFingerprintQuality()+" "+ft.getMinutiaCount()+" "+ft.getFingerprintMinutia());
			}
			
			String outputXml = xmlConvertor.getXml(fmsTemplate);
			System.out.println("fmsTemplate: "+outputXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
