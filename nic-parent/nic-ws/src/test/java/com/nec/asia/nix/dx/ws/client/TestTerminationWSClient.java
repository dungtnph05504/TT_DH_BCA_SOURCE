package com.nec.asia.nix.dx.ws.client;

import java.util.Calendar;
import java.util.Date;

//import gov.mnis.signserver.ws.ResponseObject;
//import gov.mnis.signserver.ws.SigningService;
import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.nec.asia.nic.dx.terminate.TerminateNic;
//import com.nec.asia.nic.dx.terminate.TerminateNicResponse;
//import com.nec.asia.nic.dx.ws.TerminationWS;

public class TestTerminationWSClient extends TestCase {
//	public static ApplicationContext context = null;
//	public static TerminationWS bean = null;
//	
//	public TestTerminationWSClient() {
//		init();
//	}
//
//	public void init() {
//		try {
//			context = new ClassPathXmlApplicationContext("spring-context-test.xml");
//			bean = context.getBean("terminateWS", TerminationWS.class);
//		} catch (Error e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	
//	public void testTerminate() {
//		log("start terminateNic");
//		TerminateNic terminateNicRequest = new TerminateNic();
//		String nin = "";
//		Calendar cal = Calendar.getInstance();
//		Date terminationDate = cal.getTime();
//		//TODO EMIGRATION, DEATH, MISSING
//		String terminationType = "DEATH";
//		String terminationRemarks = "Police report sent to CSD Department.";
//		terminateNicRequest.setNin(nin);
//		terminateNicRequest.setTerminationDate(terminationDate);		
//		terminateNicRequest.setTerminationType(terminationType);
//		terminateNicRequest.setTerminationRemarks(terminationRemarks);
//		TerminateNicResponse output = bean.terminateNic(terminateNicRequest);
//		log("TerminateNicResponse: "+output+"\n"+ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
//		log(" end  terminateNic");
//	}
	
	public static void log(String message) {
		System.out.println(message);
	}
}
