package com.nec.asia.nic.comp.trans.service.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.eclipse.core.internal.preferences.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.job.dto.PersoReferenceDataDTO;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.service.DataPackService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.util.CitizenRefXmlConvertor;

public class DataPackServiceTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static DataPackService service = null;
	public static NicTransactionService transService = null;
	public DataPackServiceTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			service = context.getBean("dataPackService", DataPackService.class);
			transService = context.getBean("nicTransactionService", NicTransactionService.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void testPreparePersoData() {
		log("start DataPackService - preparePersoData");
		try {
			String transactionId = "RICHQ-2017-000006";//"RICHQ-2013-001122";

			NicTransaction nicTransaction = transService.findById(transactionId);
			nicTransaction = transService.getTransactionRegistrationData(nicTransaction);
			nicTransaction = transService.getTransactiondocuments(nicTransaction);
			service.preparePersoData(nicTransaction);
			log("[preparePersoData] nicTransaction: " + ReflectionToStringBuilder.toString(nicTransaction, ToStringStyle.MULTI_LINE_STYLE));
//			String packXML = FileUtils.readFileToString(new java.io.File("C:/worktool/workspace/NIC/nic-parent/nic-comp/test/main/resources/pack.orig.xml"));
			//log("[preparePersoData] packXML: [[[[[" + packXML+"]]]]]");
			
			nicTransaction = transService.getTransactiondocuments(nicTransaction);
			for (NicTransactionAttachment doc : nicTransaction.getNicTransactionAttachments()) {
				if ("PERSO".equals(doc.getDocData())) {
					byte[] data = doc.getDocData();
					byte[] decodedData = Base64.decode(data);
					String xml = new String(decodedData);
					
					log("[preparePersoData] xml: " + xml);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  DataPackService - preparePersoData");
	}
	
	public void xtestLogic() {
		Map<String, String> fpIndicatorMap = new HashMap<String, String>();
		String fpIndicators = "01-Y,02-N,03-Y,04-N,05-,06-,07-N,08";
		String[] fpIndicatorArray = fpIndicators.split(",");
		for (String fpIndicator : fpIndicatorArray) {
			if (StringUtils.contains(fpIndicator, "-") && fpIndicator.split("-").length==2) {
				fpIndicatorMap.put(fpIndicator.split("-")[0], fpIndicator.split("-")[1]);
			}
		}
		log("[fpIndicatorMap] :" + fpIndicatorMap+".");
	}
		
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}
//org.hibernate.search.event.FullTextIndexEventListener