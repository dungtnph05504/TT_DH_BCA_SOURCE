package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;

public class DocumentDataServiceTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static DocumentDataService service = null;
	public DocumentDataServiceTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			service = context.getBean("documentDataService", DocumentDataService.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void xtestFindAllDocumentsForSync() throws Exception {
		String siteCode = "RICHQ"; //"PKG.RICHQ.1310230001";
		List<NicDocumentData> resultList = service.findAllDocumentsForSync(siteCode);
		if (resultList!=null) {
			log("[findAllDispatchedDocuments] Size==" + resultList.size());
			for (NicDocumentData data : resultList)
				log(ReflectionToStringBuilder.toString(data));
		}
	}
	
	public void testUpdateSyncStatus() throws Exception {
		String[] passportNos = new String[] {"VA0000002","VA0000003"};
		List<String> passportNoList = new ArrayList<String>();
		passportNoList = Arrays.asList(passportNos);
		String officerId = "RECEPTION_OFF";
		String wkstnId = "RECEIPTION_WKSTN";
		String siteCode = "RICHQ";
		service.updateSyncStatus(passportNoList, officerId, wkstnId, siteCode);
	}
	
	public static void log(String message) {
		System.out.printf("--------- %s --------- %s%n", "DocumentDataServiceTestCase", message);
	}
}
