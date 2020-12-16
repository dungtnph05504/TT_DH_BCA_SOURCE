package com.nec.asia.nic.comp.trans.service.impl;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.service.InventoryRefService;

public class InventoryRefServiceTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static InventoryRefService service = null;
	public InventoryRefServiceTestCase() {
		init();
	}
	private static final String spring_config = "classpath:/com/nec/asia/nic/resources/spring/services/spring-services.xml";
	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");//spring_config);//
			service = context.getBean("inventoryRefService", InventoryRefService.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void xtestUpdateTransactionStatus() {
		log("start InventoryRefService - updateTransactionInfo");
		try {
			String ccn = "926782707";
			String transactionStatus = "RIC_CARD_ISSUED";
			String officerId = "RIC_ISS_OFFICER";
			String workstationId = "";
			String siteCode = "";
			log("[Inventory][updateTransactionStatus] "+ccn);
			//service.updateTransactionStatus(ccn, transactionStatus, officerId, workstationId, siteCode);			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  InventoryRefService - updateTransactionInfo");
	}
	
	public void testUpdateCardStatus() {
		log("start InventoryRefService - updateCardStatus");
		try {
			String ccn = "926782707";
			String cardStatus = "ACTIVE";
			String officerId = "RIC_OFFICER";
			String workstationId = "";
			String siteCode = "";
			log("[Inventory][updateCardStatus] "+ccn);
			//service.updateCardStatus(ccn, cardStatus, officerId, workstationId, siteCode);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  InventoryRefService - updateCardStatus");
	}
	
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}
//org.hibernate.search.event.FullTextIndexEventListener