package com.nec.asia.nic.comp.trans.dao;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.utils.DateUtil;

import junit.framework.TestCase;


public class DocumentDataDaoTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static DocumentDataDao documentDataDao = null;
	public DocumentDataDaoTestCase() {
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
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			documentDataDao = context.getBean("documentDataDao", DocumentDataDao.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void testDao_updatePackageDispatchInfoByPassportNoList() {
		log("start updatePackageDispatchInfoByPassportNoList dao");

		try {
			List<String> passportNoList = Arrays.asList(new String[]{"AA0010003" , "EC0000002"}); 
			String packageId= "2000000010040";
			Date packageDateTime = DateUtil.strToDate("20160421", DateUtil.FORMAT_YYYYMMDD);
			String dispatchId= "DSP04201604250001";
			Date dispatchDateTime = DateUtil.strToDate("20160422", DateUtil.FORMAT_YYYYMMDD);
			String status= "ST";
			Date statusUpdateTime = DateUtil.strToDate("20160422", DateUtil.FORMAT_YYYYMMDD);
			String officerId= "EPP";
			String workstationId = "SYSTEM";
			
			documentDataDao.updatePackageDispatchInfoByPassportNoList(passportNoList, packageId, packageDateTime, dispatchId, dispatchDateTime, status, statusUpdateTime, officerId, workstationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  updatePackageDispatchInfoByPassportNoList dao");
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
}
