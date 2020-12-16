package com.nec.asia.nic.comp.admin.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.admin.code.utils.CodeRetrievalHelper;

import junit.framework.TestCase;

public class CodeRetrievalHelperTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static CodeRetrievalHelper codeRetrievalUtil = null;
		
	public CodeRetrievalHelperTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			codeRetrievalUtil = context.getBean(CodeRetrievalHelper.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void testGetCode() {
		long startTime = System.currentTimeMillis();
		log("start codeRetrievalUtil - getCodeXml");
		try {
			String xml = codeRetrievalUtil.getCodeXml();
			log("code: "+xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log(" end  codeRetrievalUtil - getCodeXml: taken = "+(endTime - startTime));
	}
	
	/*public void testGetCodeDelta() {
		long startTime = System.currentTimeMillis();
		log("start codeRetrievalUtil - getCodeDeltaXml");
		try {
			String xml = codeRetrievalUtil.getCodeDeltaXml();
			log("code: "+xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log(" end  codeRetrievalUtil - getCodeDeltaXml: taken = "+(endTime - startTime));
	}*/
	
	public void testGetCodeValue() {
		long startTime = System.currentTimeMillis();
		log("start codeRetrievalUtil - getCodeValueXml");
		try {
			String xml = codeRetrievalUtil.getCodeValueXml();
			log("codeValue: "+xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log(" end  codeRetrievalUtil - getCodeValueXml: taken = "+(endTime - startTime));
	}
	
	public void testGetPara() {
		long startTime = System.currentTimeMillis();
		log("start codeRetrievalUtil - getParameterXml");
		try {
			String xml = codeRetrievalUtil.getParameterXml();
			log("para: "+xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log(" end  codeRetrievalUtil - getParameterXml: taken = "+(endTime - startTime));
	}
	
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}
//org.hibernate.search.event.FullTextIndexEventListener