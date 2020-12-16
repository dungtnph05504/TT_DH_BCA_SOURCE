package com.nec.asia.nic.comp.trans.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;

import junit.framework.TestCase;

public class NicTransactionAttachmentServiceTestCase extends TestCase{

	
	public static ApplicationContext context = null;
	public static NicTransactionAttachmentService service = null;
	
	public NicTransactionAttachmentServiceTestCase (){
		init();
	}
	
	private void checkParameters() {
		String logDir = System.getProperty("jboss.server.log.dir");
		if (logDir==null || logDir.length()==0) {
			log(" jboss.server.log.dir is empty, set it as logs\\");
			System.setProperty("jboss.server.log.dir", "logs\\");
		} else {
			log(" jboss.server.log.dir is "+logDir);
		}
	}
	
	public void init() {
		checkParameters();
		
		try {
			log("-------------------------------------");
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			//log("-------------------------------------context:"+context);
			service = context.getBean("nicTransactionDocumentService", NicTransactionAttachmentService.class);
			//log("-------------------------------------service:"+service);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void testFindNicTransactionAttachments_PHOTO() {
		log("------------------------------------- findNicTransactionAttachments begins");
		
		try {
			String transactionId = "0042016031643525W";
			String docType =  NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE;
			String serialNo = NicTransactionAttachment.DEFAULT_SERIAL_NO;
			log("-------------------------------------transactionId : "+transactionId);
			List<NicTransactionAttachment> attachmentList = service.findNicTransactionAttachments(transactionId, docType, serialNo);
			log("-------------------------------------attachmentList : "+attachmentList);
			for (NicTransactionAttachment attm : attachmentList) {
				log("-------------------------------------docType : "+attm.getDocType());
				log("-------------------------------------docData : "+attm.getDocData());
			}
			
			assertNotNull(attachmentList);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		log("------------------------------------- findNicTransactionAttachments ends");
	}
	
	
	public void testFindNicTransactionAttachments_FP() {
		log("------------------------------------- getNicTransactionAttachments begins");
		
		try {
			String transactionId = "0042016031643525W";
			String[] docTypes =  { NicTransactionAttachment.DOC_TYPE_FINGERPRINT};
			String[] serialNo = null;
			log("-------------------------------------transactionId : "+transactionId);
			List<NicTransactionAttachment> attachmentList = service.getNicTransactionAttachments(transactionId, docTypes, serialNo);
			log("-------------------------------------attachmentList : "+attachmentList);
			for (NicTransactionAttachment attm : attachmentList) {
				log("-------------------------------------docType : "+attm.getDocType());
				log("-------------------------------------docData : "+attm.getDocData());
			}
			
			assertNotNull(attachmentList);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		log("------------------------------------- getNicTransactionAttachments ends");
	}
	
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
	
}
