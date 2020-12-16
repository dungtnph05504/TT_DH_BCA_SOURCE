package com.nec.asia.nix.dx.ws.client;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.nec.asia.nic.dx.nic.NicCardStatusUpdate;
//import com.nec.asia.nic.dx.nic.NicHistoryRetrievalFilter;
//import com.nec.asia.nic.dx.nic.NicHistoryRetrievalResult;
//import com.nec.asia.nic.dx.nic.NicInfoRetrievalDataType;
//import com.nec.asia.nic.dx.nic.NicInfoRetrievalFilter;
//import com.nec.asia.nic.dx.nic.NicInfoRetrievalResult;
//import com.nec.asia.nic.dx.nic.NicPersoInfoDownloadFilter;
//import com.nec.asia.nic.dx.nic.NicPersoInfoDownloadResult;
//import com.nec.asia.nic.dx.nic.NicTransStatusUpdate;
//import com.nec.asia.nic.dx.nic.ReceivedCardInfo;
//import com.nec.asia.nic.dx.nic.UpdateReceivedCardStatus;
//import com.nec.asia.nic.dx.trans.IssuanceData;
//import com.nec.asia.nic.dx.trans.RejectionData;
//import com.nec.asia.nic.dx.ws.FaultException;
//import com.nec.asia.nic.dx.ws.NicWS;
import com.nec.asia.nic.utils.DateUtil;

public class TestNicWSClient extends TestCase {
//	public static ApplicationContext context = null;
//	public static NicWS bean = null;
//	
//	public TestNicWSClient() {
//		init();
//	}
//
//	public void init() {
//		try {
//			context = new ClassPathXmlApplicationContext("spring-context-test.xml");
//			bean = context.getBean("nicWS", NicWS.class);
//		} catch (Error e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void testDownloadNicPersoInfo() {
//		try {
//			Date start = null;// DateUtil.strToSqlDate("01082013", DateUtil.FORMAT_DDMMYYYY);
//			Date end = null;//DateUtil.strToSqlDate("30082013", DateUtil.FORMAT_DDMMYYYY);
//			NicPersoInfoDownloadFilter input = new NicPersoInfoDownloadFilter();
//			input.setSiteCode("RICHQ");
//			input.setStartDate(start);
//			input.setEndDate(end);
//			//input.setCcn("915361920");//"90090099");
//			input.setPackageID("PKG.RICHQ2013/09/14");
//			NicPersoInfoDownloadResult output = bean.downloadNicPersoInfo(input);
//			log("NicPersoInfoDownloadResult: "+output+"\n"+ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
//			for (IssuanceData issData: output.getIssuanceDatas()) {
//				log("IssuanceData: "+issData+"\n"+ReflectionToStringBuilder.toString(issData, ToStringStyle.MULTI_LINE_STYLE));
//			}
//			for (RejectionData rejData: output.getRejectionDatas()) {
//				log("RejectionData: "+rejData+"\n"+ReflectionToStringBuilder.toString(rejData, ToStringStyle.MULTI_LINE_STYLE));
//			}
//		} catch (FaultException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void xtestUpdateReceivedCardStatus() {
//		try {
//			
//			UpdateReceivedCardStatus input = new UpdateReceivedCardStatus();
//			ReceivedCardInfo receiveInfo = new ReceivedCardInfo();
//			receiveInfo.setTransactionID("RICHQ-2013-001714");
//			receiveInfo.setPersoRefID("2");
//			input.getReceivedCardInfos().add(receiveInfo);
//			//rejetectionInfo
//			receiveInfo = new ReceivedCardInfo();
//			receiveInfo.setTransactionID("RICHQ-2013-000008");
//			input.getReceivedCardInfos().add(receiveInfo);
//			String output = bean.updateReceivedCardStatus(input);
//			log("String: "+output+"\n"+ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
//		} catch (FaultException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void xtestRetrieveNicHistory() {
//		try {
//			NicHistoryRetrievalFilter input = new NicHistoryRetrievalFilter();
//			input.setNin("R780101015051B");
//			NicHistoryRetrievalResult output = bean.retrieveNicHistory(input);
//			log("String: "+output+"\n"+ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
//		} catch (FaultException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void xtestInquiryNicInfo() {
//		try {
//			NicInfoRetrievalFilter input = new NicInfoRetrievalFilter();
//			input.setNin("R780101015051B");
//			input.setCcn("");
//			input.setTransactionID("");
//			input.getDataType().add(NicInfoRetrievalDataType.STATUS);
//			input.getDataType().add(NicInfoRetrievalDataType.BASIC_HOLDER_INFO);
//			input.getDataType().add(NicInfoRetrievalDataType.ADDRESS);
//			input.getDataType().add(NicInfoRetrievalDataType.EXTENDED_HOLDER_INFO);
//			NicInfoRetrievalResult output = bean.inquiryNicInfo(input);
//			log("NicInfoRetrievalResult: "+ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
//			log("BasicHolderInfo: "+ReflectionToStringBuilder.toString(output.getBasicHolderInfo(), ToStringStyle.MULTI_LINE_STYLE));
//		} catch (FaultException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void xtestEcho() {
//		log("start echo");		
//	
//		try {
//			String output = bean.echo("");
//			log("String: "+output);
//		} catch (FaultException e) {
//			e.printStackTrace();
//		}
//		
//		log(" end  echo");
//	}
//	
//	public void xtestUpdateNicCardStatus() {
//		log("start updateNicStatus");		
//	
//		try {
//			
//			NicCardStatusUpdate cardStatusInput = new NicCardStatusUpdate();
//			cardStatusInput.setCardStatus("ACTIVE");
//			//cardStatusInput.setCardStatus("SUSPEND");
//			//cardStatusInput.setCardStatusChangeReason("SUSPENSION_LOST");
//			//cardStatusInput.setCardStatusChangeTime(new Date());
//			cardStatusInput.setCcn("152918880");
//			cardStatusInput.setUpdateBy("RIC");
//			cardStatusInput.setUpdateWkstnID("RIC");
//			String output = bean.updateNicStatus(null, cardStatusInput, null);
//			log("String: "+output+"\n"+ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
//		} catch (FaultException e) {
//			e.printStackTrace();
//		}
//		
//		log(" end  updateNicStatus");
//	}
//	public void xtestUpdateNicTransStatus() {
//		log("start updateNicStatus");		
//	
//		try {
//			NicTransStatusUpdate transInput = new NicTransStatusUpdate();
//			transInput.setTransactionID("RICHQ-2013-000011");
//			transInput.setTransactionStatus("NIC_COMPLETED");
//			String output = bean.updateNicStatus(transInput, null, null);
//			log("output: "+output);
//		} catch (FaultException e) {
//			e.printStackTrace();
//		}
//		
//		log(" end  updateNicStatus");
//	}
//	
//	
//	private String rootFolder = "C:/worktool/workspace/NIC/nic-parent/nic-comp/test/main/resources";
//	public static final String FINGERPRINT_DATA_ROOT = "/fingerprints/";
//	private byte[] loadFingerprintImage(String index){
//		try {
//			String fileName = rootFolder + FINGERPRINT_DATA_ROOT + index + ".wsq";
//			byte[] data = FileUtils.readFileToByteArray(new File(fileName));
//			return data;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	public static void log(String message) {
		System.out.println(message);
	}
}
