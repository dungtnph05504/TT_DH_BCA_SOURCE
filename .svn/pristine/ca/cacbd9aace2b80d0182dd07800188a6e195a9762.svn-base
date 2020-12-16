package com.nec.asia.nix.dx.ws.client;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.job.dto.CardDataDTO;
import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.job.dto.NicPersoInfoDTO;
import com.nec.asia.nic.comp.job.dto.PersoReferenceDataDTO;
import com.nec.asia.nic.comp.job.dto.TransactionInfoDTO;
//import com.nec.asia.nic.dx.ws.DxWS;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.nec.asia.nic.util.JobXmlConvertor;

public class TestDxWSClient extends TestCase {
//	public static ApplicationContext context = null;
//	public static DxWS bean = null;
//	
//	public TestDxWSClient() {
//		init();
//	}
//
//	public void init() {
//		try {
//			context = new ClassPathXmlApplicationContext("spring-context-test.xml");
//			bean = context.getBean("dxWS", DxWS.class);
//		} catch (Error e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void testUploadPersoInfo () throws FaultException, Exception  {
//        //DxWS dxClient = (DxWSImpl)context.getBean("dxWS");
//	   	
//	    PersoReferenceDataDTO persoRefDto = new PersoReferenceDataDTO();
//	    NicPersoInfoDTO dto = new NicPersoInfoDTO();
//
//	    dto.setPackageID("PKG.RICHQ.1310230001");
//	    dto.getCardDataList().add(new CardDataDTO("1", "K2808584207352", "67", "CCPL-2013-000044", "1", "926782699", "0"));	    
//	    dto.getCardDataList().add(new CardDataDTO("2", "N0603650106215", "68", "CCPL-2013-000006", "1", "926782707", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("3", "R200654130542A", "69", "CCPL-2013-000230", "1", "926782749", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("4", "R780101015051B", "70", "RICHQ-2013-001419", "1", "926782731", "0"));
//	    
//	    dto.getCardDataList().add(new CardDataDTO("5", "R780101015052D", "71", "RICHQ-2013-001856", "1", "926782715", "0"));	    
//	    dto.getCardDataList().add(new CardDataDTO("6", "A111111111112A", "72", "RICHQ-2013-001851", "1", "926782723", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("7", "A050962090202G", "73", "CONV-0000009-20130818", "1", "926782756", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("8", "R780101015052B", "74", "RICHQ-2013-001930", "1", "926782764", "0"));
//	    
//	    dto.getCardDataList().add(new CardDataDTO("9", "R780101015052C", "75", "RICHQ-2013-001931", "1", "926782780", "0"));	    
//	    dto.getCardDataList().add(new CardDataDTO("10", "R780101015071F", "76", "RICHQ-2013-001945", "1", "926782772", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("11", "R780101015053D", "77", "RICHQ-2013-001933", "1", "926782871", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("12", "R780101015072B", "78", "RICHQ-2013-001954", "1", "926782913", "0"));
//    
//	    dto.getCardDataList().add(new CardDataDTO("13", "R780101015072A", "79", "RICHQ-2013-001955", "1", "926782889", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("14", "R780101015072D", "80", "RICHQ-2013-001952", "1", "926782897", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("15", "S0808739500254", "81", "CCEB-2013-000002", "1", "926782905", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("16", "R780101015052E", "82", "RICHQ-2013-001958", "1", "926782335", "0"));
//    
//	    dto.getCardDataList().add(new CardDataDTO("17", "R780101015054A", "83", "RICHQ-2013-001962", "1", "926782343", "0"));	    
//	    dto.getCardDataList().add(new CardDataDTO("18", "A090191340003A", "84", "CCBA-2013-000018", "1", "926782640", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("19", "L1912943400040", "85", "CCBA-2013-000173", "1", "926781741", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("20", "K0106950501092", "86", "CCBA-2013-000246", "1", "926781766", "0"));
//	    
//	    dto.getCardDataList().add(new CardDataDTO("21", "V1609942602059", "87", "CCCG-2013-000148", "1", "926781758", "0"));	    
//	    dto.getCardDataList().add(new CardDataDTO("22", "S150495320065A", "88", "CCEB-2013-000604", "1", "926781725", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("23", "S1702903011891", "89", "CCRH-2013-000929", "1", "926782376", "0"));
//	    dto.getCardDataList().add(new CardDataDTO("24", "B1802953001458", "90", "CCRH-2013-000210", "1", "926782392", "0"));
//	    persoRefDto.setNicPersoInfo(dto);
//	   
//	    JobXmlConvertor util = new JobXmlConvertor();
//	   
//	    String inputXml = util.marshal(persoRefDto);
//	    log("inputXml="+inputXml);
//		inputXml = util.encodeString(inputXml);
//		log("encodeString.inputXml="+inputXml);
//		String inputCheckSum = util.computeCheckSum(inputXml);
//	    System.out.println("CheckSum=" + inputCheckSum);
//	    
//        String result = bean.uploadNicPersoInfo(inputXml, inputCheckSum);
//        
//        System.out.println("result=" + result);
//   }
//	
//	public void xtestUpdatePersoError() throws JaxbXmlConvertorException, NoSuchAlgorithmException, UnsupportedEncodingException {
//		log("start uploadNicPersoInfo");
//		JobXmlConvertor util = new JobXmlConvertor();
//		PersoReferenceDataDTO dto = this.preparePersoErrorDto();
//		String inputXml = util.marshal(dto);
//		log("inputXml="+inputXml);
//		inputXml = util.encodeString(inputXml);
//		log("encodeString.inputXml="+inputXml);
//		String inputCheckSum = util.computeCheckSum(inputXml);
//		
//		String output = bean.uploadNicPersoInfo(inputXml, inputCheckSum);
//		log("start uploadNicPersoInfo: "+output);
//	}
//	
//	private PersoReferenceDataDTO preparePersoErrorDto() {
//		PersoReferenceDataDTO dto = new PersoReferenceDataDTO();
//		dto.setSource("com.nec.asia.perso.dataexchange.transactionstatusupdate");
//		dto.setTarget("com.nec.asia.nic.dataexchange.transactionstatusupdate");
//		dto.setVersion("1.0");
//		NicPersoInfoDTO nicPersoInfoDto = new NicPersoInfoDTO();
//		nicPersoInfoDto.setPersoRefID("54");
//		LogInfoDTO logInfo = new LogInfoDTO();
//		logInfo.setRemark("Unable to pass xml");
//		nicPersoInfoDto.setLogInfo(logInfo );
//		nicPersoInfoDto.setTransactionID("0");
//		dto.setNicPersoInfo(nicPersoInfoDto );
//		return dto;
//	}
//
//	public void xtestUpdateNicStatusForTransaction() {
//		log("start updateNicStatus");		
//	
//		try {
//			JobXmlConvertor util = new JobXmlConvertor();
//			PersoReferenceDataDTO dto = this.prepareTransactionStatusDTO();
//			String inputXml = util.marshal(dto);
//			log("inputXml="+inputXml);
//			inputXml = util.encodeString(inputXml);
//			log("encodeString.inputXml="+inputXml);
//			String inputCheckSum = util.computeCheckSum(inputXml);
//			
//			String output = bean.updateNicStatus(inputXml, inputCheckSum);
//			log("updateNicStatus: "+output);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		log(" end  updateNicStatus");
//	}
//	public PersoReferenceDataDTO prepareTransactionStatusDTO() throws Exception {
//		PersoReferenceDataDTO dto = new PersoReferenceDataDTO();
//		dto.setSource("com.nec.asia.perso.dataexchange.transactionstatusupdate");
//		dto.setTarget("com.nec.asia.nic.dataexchange.transactionstatusupdate");
//		dto.setVersion("1.0");
//		TransactionInfoDTO txnStatusInfo = new TransactionInfoDTO();
//		txnStatusInfo.setCcn("958513327");
//		txnStatusInfo.setStatus("INVENTORY_CARD_DESTROYED");
//		dto.setTransactionInfoDto(txnStatusInfo);
//		return dto;
//	}
	
	
	public static void log(String message) {
		System.out.println(message);
	}
}
