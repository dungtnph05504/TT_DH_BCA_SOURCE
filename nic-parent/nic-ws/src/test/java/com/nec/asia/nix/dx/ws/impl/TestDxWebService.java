package com.nec.asia.nix.dx.ws.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;

import junit.framework.TestCase;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.job.dto.CardDataDTO;
import com.nec.asia.nic.comp.job.dto.CcnInfoDTO;
import com.nec.asia.nic.comp.job.dto.NicPersoInfoDTO;
import com.nec.asia.nic.comp.job.dto.PackageInfoDTO;
import com.nec.asia.nic.comp.job.dto.PersoReferenceDataDTO;
import com.nec.asia.nic.comp.trans.service.PersoService;
//import com.nec.asia.nic.dx.ws.DxWS;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.impl.DxWSImpl;
import com.nec.asia.nic.util.JobXmlConvertor;


public class TestDxWebService extends TestCase {
//    
//    private static final String LOCAL_ADDRESS = "http://localhost:8082/admin"; 
//    // private static final String CLIENT_ADDRESS = "http://localhost:8080/nic-ws/services/dx"; 
//   // private static final String CLIENT_ADDRESS = "http://172.16.1.165:8280/nic-ws/services/dx"; 
//    
//    private String TRANSACTION_ID = "RICHQ-2013-000002";
//    private String PERSO_REF_ID = "PERSO_REF_ID_02";
//    private String NIN = "T121299012345N";
//    private String CCN = "1223"; //Control Card Number
//    private String PACKAGE_ID = "PACK_01";
//    private String SAMKEY_VERSION = "12";   //SAM KEY INSIDE IN CARD READER
//    
//    private JaxWsProxyFactoryBean factory ;
//    private DxWS client ;
//    ApplicationContext context;
//    
//    protected void setUp() throws Exception {
//        super.setUp();
//        URL currentPath = Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
//        File log4jConfigFile = new File(currentPath.getFile());
//        System.out.println("log4jConfigFile: "+log4jConfigFile+", exists? "+log4jConfigFile.exists());
//        System.out.println("Starting Server: "+currentPath);  
//        //AdminWSImpl demo = new AdminWSImpl();
//        //src/main/webapp/WEB-INF/
//        context = new ClassPathXmlApplicationContext("WEB-INF/nic-ws.xml");
//        PersoService persoService = context.getBean("persoService", PersoService.class);        
//        
//        DxWSImpl demo = new DxWSImpl();
//        demo.setPersoService(persoService);
//       
//        Endpoint.publish(LOCAL_ADDRESS, demo);       
//        
//        //construct client
//    	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//        factory.setServiceClass(DxWS.class);
//        factory.setAddress(LOCAL_ADDRESS);
//        client = (DxWS) factory.create();
//
//    }
//    
//   public void testUploadPersoInfo () throws FaultException, Exception  {
//        //DxWS dxClient = (DxWSImpl)context.getBean("dxWS");
//	   	
//	    PersoReferenceDataDTO persoRefDto = new PersoReferenceDataDTO();
//	    NicPersoInfoDTO dto = new NicPersoInfoDTO();
////	    dto.setNin("NIN_012345");
////	    dto.setPersoRefID("PERSO_REF_ID_02");
////	    dto.setReprintCount("0");
////	    dto.setTransactionID("RICHQ-2013-000002");
////	    
////	    //construct logDataDto
////	    PersoLogDataDTO persoLogDataDto = new PersoLogDataDTO ();
////	    persoLogDataDto.setCcn("23456");
////	    persoLogDataDto.setPackageID("PACK_002");
////	    persoLogDataDto.setSamKeyVersion("123");
////	    dto.setLogData(persoLogDataDto);
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
//	    String inputCheckSum = util.computeCheckSum(inputXml);
//	    
//	    System.out.println(inputXml);
//	    System.out.println("CheckSum=" + inputCheckSum);
//	    
//        String result = client.uploadNicPersoInfo(inputXml, inputCheckSum);
//        
//        System.out.println("result=" + result);
//       // Thread.currentThread().sleep(120000);
//   }
//   
//   public void xtestUpdateCardStatusByCcn () throws FaultException, Exception {
//	   PersoReferenceDataDTO persoRefDto = new PersoReferenceDataDTO();
//	   CcnInfoDTO cardInfoDto = new CcnInfoDTO(); 
//	   cardInfoDto.getCcnList().add("123456789");
//	   cardInfoDto.setStatus("DODOLL");
//	   persoRefDto.setCardInfoDto(cardInfoDto);
//	   
//	   JobXmlConvertor util = new JobXmlConvertor();
//		   
//	   String inputXml = util.marshal(persoRefDto);
//	   String inputCheckSum = util.computeCheckSum(inputXml);
//
//	   client.updateNicStatus(inputXml, inputCheckSum); 
//   }
//   
//   
//   public void testUpdateCardStatusByPackageList () throws FaultException, Exception {
//	   PersoReferenceDataDTO persoRefDto = new PersoReferenceDataDTO();
//	   
//	   PackageInfoDTO packInfoDto = new PackageInfoDTO();
//	   
//	   List <String> packageList = new ArrayList<String>();
//	   packageList.add(new String("PACK_002"));
//	   packageList.add(new String("PACK_003"));
//	   packageList.add(new String("PACK_004"));
//		
//	   packInfoDto.setPackageIdList(packageList);
//	   packInfoDto.setStatus("DISPATCHED");
//	   persoRefDto.setPackageInfoDto(packInfoDto);
//	   
//	   JobXmlConvertor util = new JobXmlConvertor();
//		   
//	   String inputXml = util.marshal(persoRefDto);
//	   String inputCheckSum = util.computeCheckSum(inputXml);
//
//	   client.updateNicStatus(inputXml, inputCheckSum); 
//   }
//   
//    
//   public void xtestDownloadPersoInfo () throws FaultException {
//
//   }
//    
//
//    
}
