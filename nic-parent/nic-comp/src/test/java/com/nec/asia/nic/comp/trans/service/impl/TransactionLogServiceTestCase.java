package com.nec.asia.nic.comp.trans.service.impl;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;

import junit.framework.TestCase;

public class TransactionLogServiceTestCase extends TestCase{

	
	public static ApplicationContext context = null;
	public static TransactionLogService service = null;
	
	public TransactionLogServiceTestCase (){
		init();
	}
	
	
	public void init() {
		try {
			log("-------------------------------------");
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			log("-------------------------------------context:"+context);
			service = context.getBean("nicTransactionLogService", TransactionLogService.class);
			log("-------------------------------------service:"+service);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void testSaveNicTransactionLog() {
		log("-------------------------------------save begins");
		String transactionId = "004-2016-000013";
		try {
			NicTransactionLog log = new NicTransactionLog();
			log.setRefId(transactionId);
			log.setLogCreateTime(new Date());
			log.setTransactionStage("AFIS_VERIFY");
			log.setTransactionStatus("AFIS_VERIFY_COMPLETED");
			log.setSiteCode("001");
			log.setStartTime(new Date());
			log.setEndTime(new Date());
			log.setLogInfo(null);
			log.setLogData(null);
			log.setWkstnId("CW");
			log.setOfficerId("EPP");
			//log.setRetryCount(0);
			log("-------------------------------------transactionId : "+transactionId);
			Long logId = service.save(log);
			log("-------------------------------------logId : "+logId);
			assertNotNull(logId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		log("save ends");
	}
	
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
	
}
