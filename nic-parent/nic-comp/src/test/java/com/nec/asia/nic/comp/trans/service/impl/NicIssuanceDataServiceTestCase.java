package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceDataId;
import com.nec.asia.nic.comp.trans.service.IssuanceDataService;
import com.nec.asia.nic.comp.trans.service.exception.IssuanceServiceException;
import com.nec.asia.nic.utils.DateUtil;

public class NicIssuanceDataServiceTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static IssuanceDataService service = null;
	public NicIssuanceDataServiceTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			service = context.getBean("nicIssuanceDataService", IssuanceDataService.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	
	public void xtestSave() {
		log("start nicIssuanceDataService - save");
		try {
			NicIssuanceData issuanceData = new NicIssuanceData();
			NicIssuanceDataId dataId = new NicIssuanceDataId();
			dataId.setPersoRefId("PERSO_REF_ID_06");
			dataId.setTransactionId("RICHQ-2013-000002");
			issuanceData.setId(dataId);
			issuanceData.setPackageId("PACKID_01");
			issuanceData.setCcn("CCN_01");
			issuanceData.setSamKeyVersion(new Short("1"));
			issuanceData.setCardStatus("COMPLETED");
			issuanceData.setNin("1234567890");
			issuanceData.setIssuanceDecision("1");
					
			service.saveIssuanceData(issuanceData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  nicIssuanceDataService - save");
	}
	
	public void xtestFindByPeriod () {
		log("start nicIssuanceDataService - findByPeriod");
		Date start = DateUtil.toDate("11/06/2013", 0, 0, 0, 0);
		Date end = DateUtil.toDate("30/07/2013", 16, 0, 0, 0);
		String siteCode = "RICHQ";
		
		try {
			List<NicIssuanceData> resultList  = service.findByPeriod(siteCode,null, null);
			if (resultList!=null) {
				System.out.println("Size==" + resultList.size());
				for (NicIssuanceData data : resultList)
					System.out.println (ReflectionToStringBuilder.toString(data));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void xtestUpdateCardReceived () {
		log("start nicIssuanceDataService - UpdateCardReceived");
		
		
		try {
			List<NicIssuanceDataId> idList = new ArrayList<NicIssuanceDataId> ();
			idList.add(new NicIssuanceDataId("RICHQ-2013-000001","S2345678"));
			idList.add(new NicIssuanceDataId("RICHQ-2013-001419B","28"));
			idList.add(new NicIssuanceDataId("RICHQ-2013-001490","31"));
			String officerId = "RIC";
			String workstationId = "NIC";
			String siteCode = "RICHQ";			
			service.updateReceivedCardStatus(idList, officerId, workstationId, siteCode);
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void xtestUpdateStatusByCcn () throws Exception {
		String ccn = "222222222";
		String status = "ACTIVE";
		String cardStatusChangeReason = ""; 
		Date cardStatusChangeTime = null; 
		String officerId = "SYSTEM"; 
		String workstationId = "SYSTEM";
		service.updateStatusByCCn(ccn, status, cardStatusChangeReason, cardStatusChangeTime, officerId, workstationId);
	}
	
	public void xtestUpdateStatusByPackageId () throws Exception {
		List <String> packageList = new ArrayList<String>();
		String status = "REJECTED";
		packageList.add(new String("PACK_002"));
		packageList.add(new String("PACK_003"));
		packageList.add(new String("PACK_004"));
		
		service.updateStatusByPackageId(packageList, status);
	}
	
	public void testUpdateNicTransactionByTransactionId () {
		String transactionId = "CCMH-2013-000516";
		String transactionStatus = "RIC_TXN_SUSPENDED";
		String cardStatus = "";
		
		String officerId = "SYSTEM";
		String workstationId = "SYSTEM";
		try {
			//service.updateTransactionByTransactionId(transactionId, "TERMINATE_DAMAGE_CARD", cardStatus, officerId, workstationId);
			service.updateTransactionByTransactionId(transactionId, transactionStatus, cardStatus, officerId, workstationId);
		} catch (IssuanceServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void xtestFindByCcn() throws Exception {
		String ccn = "926782640";
		List<NicIssuanceData> resultList = service.findByCcn(ccn);
		if (resultList!=null) {
			System.out.println("[findByCcn] Size==" + resultList.size());
			for (NicIssuanceData data : resultList)
				System.out.println (ReflectionToStringBuilder.toString(data));
		}

		resultList = service.findIssuanceData(null, ccn, null);
		if (resultList!=null) {
			System.out.println("[findIssuanceData] Size==" + resultList.size());
			for (NicIssuanceData data : resultList)
				System.out.println (ReflectionToStringBuilder.toString(data));
		}
	}
	
	public void xtestFindByPackageIdCcnNin() throws Exception {
		String packageId = ""; //"PKG.RICHQ.1310230001";
		String ccn = ""; //"926782913";
		String nin = "R780101015072B";
		List<NicIssuanceData> resultList = service.findIssuanceData(packageId, ccn, nin);
		if (resultList!=null) {
			System.out.println("[findIssuanceData] Size==" + resultList.size());
			for (NicIssuanceData data : resultList)
				System.out.println (ReflectionToStringBuilder.toString(data));
		}
	}
	
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}
