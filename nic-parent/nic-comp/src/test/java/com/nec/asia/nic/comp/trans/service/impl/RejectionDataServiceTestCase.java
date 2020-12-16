package com.nec.asia.nic.comp.trans.service.impl;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.service.RejectionDataService;

public class RejectionDataServiceTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static RejectionDataService service = null;
	public RejectionDataServiceTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			service = context.getBean("rejectionDataService", RejectionDataService.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void testFindAllRejectionDataForSync() throws Exception {
		String siteCode = "RICHQ"; //"PKG.RICHQ.1310230001";
		List<NicRejectionData> resultList = service.findAllRejectionDataForSync(siteCode);
		if (resultList!=null) {
			log("[findAllRejectionDataForSync] Size==" + resultList.size());
			for (NicRejectionData data : resultList)
				log(ReflectionToStringBuilder.toString(data));
		}
	}
	
	public void testUpdateSyncStatus() throws Exception {
		String[] transactionIds = new String[] {"VA0000002","VA0000003"};
		List<String> transactionIdList = Arrays.asList(transactionIds);
		service.updateSyncStatus(transactionIdList);
	}
	
	public static void log(String message) {
		System.out.printf("--------- %s --------- %s%n", "DocumentDataServiceTestCase", message);
	}
}
