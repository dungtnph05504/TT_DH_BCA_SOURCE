package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.report.service.AfisDataSyncService;
import com.nec.asia.nic.util.MiscXmlConvertor;

import junit.framework.TestCase;

public class AfisDataSyncServiceTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static AfisDataSyncService service = null;
	public AfisDataSyncServiceTestCase() {
		init();
	}
	private static final String spring_config = "classpath:/com/nec/asia/nic/resources/spring/services/spring-services.xml";
	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			service = context.getBean("afisDataSyncService", AfisDataSyncService.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	@Ignore
	public void xtestGetSynchronizionDetails() {
		log("start afisDataSyncService - getSynchronizionDetails");
		try {
			String selectedMonth = "12-2015";
			List resultList = service.getSynchronizionDetails(selectedMonth);
			for (Object result: resultList) {
				log(MiscXmlConvertor.parseObjectToXml(result));
			}
			Assert.assertTrue("result shold not be empty.", CollectionUtils.isNotEmpty(resultList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		log(" end  afisDataSyncService - getSynchronizionDetails");
	}
	
	@Ignore
	public void xtestGetSynchronizionDetailsByDate() {
		log("start afisDataSyncService - getSynchronizionDetailsByDate");
		try {
			String selectedDate = "15-12-2015";
			log("[afisDataSyncService][getSynchronizionDetailsByDate] date: "+selectedDate);
			List resultList = service.getSynchronizionDetailsByDate(selectedDate);
			for (Object result: resultList) {
				log(MiscXmlConvertor.parseObjectToXml(result));
			}
			Assert.assertTrue("result shold not be empty.", CollectionUtils.isNotEmpty(resultList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		log(" end  afisDataSyncService - getSynchronizionDetailsByDate");
	}
	
	@Ignore
	public void xtestGetSynchronizionDetailsByMonth() {
		log("start afisDataSyncService - getSynchronizionDetailsByMonth");
		try {
			String selectedMonth = "12-2015";
			log("[afisDataSyncService][getSynchronizionDetailsByMonth] selectedMonth: "+selectedMonth);
			List resultList = service.getSynchronizionDetailsByMonth(selectedMonth);
			for (Object result: resultList) {
				log(MiscXmlConvertor.parseObjectToXml(result));
			}
			Assert.assertTrue("result shold not be empty.", CollectionUtils.isNotEmpty(resultList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		log(" end  afisDataSyncService - getSynchronizionDetailsByMonth");
	}
	
	public void testGetSyncRequestAndStatusDetails() {
		log("start afisDataSyncService - getSyncRequestAndStatusDetails");
		try {
			String transId = "004-2016-000004";
			log("[afisDataSyncService][getSyncRequestAndStatusDetails] transId: "+transId);
			List resultList = service.getSyncRequestAndStatusDetails(transId);
			for (Object result: resultList) {
				log(MiscXmlConvertor.parseObjectToXml(result));
			}
			Assert.assertTrue("result shold not be empty.", CollectionUtils.isNotEmpty(resultList));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		log(" end  afisDataSyncService - getSyncRequestAndStatusDetails");
	}
	
	public void testResetDataSyncRequest() {
		log("start afisDataSyncService - resetDataSyncRequest");
		try {
			service.resetDataSyncRequest(17L);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		log(" end  afisDataSyncService - resetDataSyncRequest");
	}
	
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}
//org.hibernate.search.event.FullTextIndexEventListener