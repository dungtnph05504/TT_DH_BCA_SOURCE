package com.nec.asia.nic.comp.trans.service.impl;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;

public class NicAfisDataServiceTestCase extends TestCase{

	
	public static ApplicationContext context = null;
	
	public static NicAfisDataService service = null;
	
	public NicAfisDataServiceTestCase (){
		
		init();
	}
	
	
	public void init() {
		try {
			log("-------------------------------------");
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			log("-------------------------------------context:"+context);
			service = context.getBean("nicAfisDataService", NicAfisDataService.class);
			log("-------------------------------------service:"+service);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	
	public void xtestSaveNicAfisData(){
		log("-------------------------------------testSaveNicAfisData begins");
		
		try {
			String result = "";
			NicAfisData data = service.findById("RICHQ-2013-000405");
			data.setUpdateDatetime(new Date());
			data.setStatus("X");
			service.saveAfisRefId(data);
			
			assertNotNull(result);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		log("testSaveNicAfisData ends");
		
	}
	
	public void xtestFindById(){
		log("-------------------------------------testFindById begins");
		
		try {
			String result = "";
			NicAfisData data = service.findById("RICHQ-2013-000405");
			
			if(data != null){
				log("[NicAfisData] data: " + ReflectionToStringBuilder.toString(data, ToStringStyle.MULTI_LINE_STYLE));
			}else{
				log("[NicAfisData] data: transaction not found");
			}
			assertNotNull(data);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		log("testSaveNicAfisData ends");
	}
	
	public void testFindAfisRefIdByTransactionId() {
		log("-------------------------------------testFindAfisRefIdByTransactionId begins");
		
		try {
			String transactionId = "004-2016-000003";
			log("-------------------------------------transactionId : "+transactionId);
			String afisRefId = service.findAfisRefIdByTransactionId(transactionId);
			log("-------------------------------------afisRefId : "+afisRefId);
			assertNotNull(afisRefId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		log("testFindAfisRefIdByTransactionId ends");
	}
	
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
	
}
