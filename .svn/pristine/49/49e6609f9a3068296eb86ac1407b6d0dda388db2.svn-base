package com.nec.asia.nix.dx.ws.client;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.dx.report.RetrieveTransactionReconReportDataRequest;
import com.nec.asia.nic.dx.report.RetrieveTransactionReconReportDataResponse;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.ReportWS;

public class TestReportWSClient extends TestCase {
	protected static final Logger logger = LoggerFactory.getLogger(TestReportWSClient.class);
	
	public static ApplicationContext context = null;
	public static ReportWS bean = null;
	
	public TestReportWSClient() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context-test.xml");
			bean = context.getBean("reportWS", ReportWS.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void testRetrieveTransactionReconReportData() {
		log("start retrieveTransactionReconReportData");		
	
		try {
			RetrieveTransactionReconReportDataRequest input = new RetrieveTransactionReconReportDataRequest();
			input.setSourceSystemId("CC");
			input.setApplicationDate("20131203");
			input.setIssSiteCode("CCIPL");
			RetrieveTransactionReconReportDataResponse output = bean.retrieveTransactionReconReportData(input);
			System.out.println(ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log(" end  retrieveTransactionReconReportData");
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
}
