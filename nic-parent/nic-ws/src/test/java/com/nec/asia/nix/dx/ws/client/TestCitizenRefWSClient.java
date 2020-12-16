package com.nec.asia.nix.dx.ws.client;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.nec.asia.nic.dx.trans.CitizenRef;
//import com.nec.asia.nic.dx.trans.CitizenRefRetrievalFilter;
//import com.nec.asia.nic.dx.trans.CitizenRefRetrievalResult;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.TransactionWS;

public class TestCitizenRefWSClient extends TestCase {
//	public static ApplicationContext context = null;
//	public static TransactionWS bean = null;
//	
//	public TestCitizenRefWSClient() {
//		init();
//	}
//
//	public void init() {
//		try {
//			context = new ClassPathXmlApplicationContext("spring-context-test.xml");
//			bean = context.getBean("transactionWS", TransactionWS.class);
//		} catch (Error e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	String[] nins = new String[] {
//			"J0901750000014","S0605950000286","P151080000029E","B1510800000304","W0605450000316"};
//	public void testRetrieveCitizenRef() {
//		log("start retrieveCitizenRef");		
//		CitizenRefRetrievalResult output = null;
//		
//		try {			
//			int totalTS = 0;
//			double aveTS = 0;
//			int count = 1;
//			for (int i=0; i<1; i++) {
//				for (String nin: nins) {
//					Date start = new Date();
//					log("start retrieveCitizenRef ["+count+"]"+start);
//					CitizenRefRetrievalFilter input = new CitizenRefRetrievalFilter();
//					input.setNin(nin);
//					//input.setSex("M");
//					output = bean.retrieveCitizenRef(input);
//					Date end = new Date();
//					totalTS += end.getTime()-start.getTime();
//					aveTS = totalTS/count;
//					log("end retrieveCitizenRef ["+count+"] size:"+output.getCitizenRefs().size()+" "+end+" time consumed: "+(end.getTime()-start.getTime())+"\t aveTS:"+aveTS+"\t totalTS:"+totalTS);
//	//				log("CitizenRefRetrievalResult: "+output+"\n"+ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
//	//				if (output!=null&& CollectionUtils.isNotEmpty(output.getCitizenRefs())) {
//	//					for (CitizenRef citizenRef: output.getCitizenRefs()) {
//	//						log("CitizenRefRetrievalResult["+(count++)+"]: "+citizenRef.getNin());
//	//						//log("CitizenRefRetrievalResult["+(count++)+"]: "+citizenRef+"\n"+ReflectionToStringBuilder.toString(citizenRef, ToStringStyle.MULTI_LINE_STYLE));
//	//					}
//	//				}
//					count++;
//				}
//			}
//		} catch (FaultException e) {
//			e.printStackTrace();
//		}
//		
//		log(" end  retrieveCitizenRef");
//	}
//	
//	public void xtestSearchCitizenRef() {
//		CitizenRefRetrievalResult output = null;
//		
//		try {
//			CitizenRefRetrievalFilter input = new CitizenRefRetrievalFilter();
//			input.setSurname("Anderson Jerry");
//			output = bean.retrieveCitizenRef(input);
//			log("CitizenRefRetrievalResult: "+output+"\n"+ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
//			if (output!=null&& CollectionUtils.isNotEmpty(output.getCitizenRefs())) {
//				int count = 1;
//				for (CitizenRef citizenRef: output.getCitizenRefs()) {
//					log("CitizenRefRetrievalResult["+(count++)+"]: "+citizenRef+"\n"+ReflectionToStringBuilder.toString(citizenRef, ToStringStyle.MULTI_LINE_STYLE));
//				}
//			}
//		} catch (FaultException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void log(String message) {
//		System.out.println(message);
//	}
}
