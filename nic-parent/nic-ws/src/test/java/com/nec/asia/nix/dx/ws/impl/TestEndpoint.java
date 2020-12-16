package com.nec.asia.nix.dx.ws.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.Endpoint;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.utils.TransDTOMapper;
import com.nec.asia.nic.dx.admin.ChangeUserPassword;
import com.nec.asia.nic.dx.admin.ChangeUserPasswordResponse;
import com.nec.asia.nic.dx.report.TransactionReconReportData;
import com.nec.asia.nic.dx.report.UploadTransactionReconReportDataRequest;
import com.nec.asia.nic.dx.report.UploadTransactionReconReportDataResponse;
import com.nec.asia.nic.dx.trans.FacialImage;
import com.nec.asia.nic.dx.trans.Fingerprint;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.trans.ReferenceDocument;
import com.nec.asia.nic.dx.trans.TransactionRetrievalDataType;
import com.nec.asia.nic.dx.trans.TransactionRetrievalFilter;
import com.nec.asia.nic.dx.trans.TransactionRetrievalRecordType;
import com.nec.asia.nic.dx.trans.TransactionRetrievalResult;
import com.nec.asia.nic.dx.ws.AdminWS;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.ReportWS;
import com.nec.asia.nic.dx.ws.TransactionWS;
import com.nec.asia.nic.dx.ws.impl.TransactionWSImpl;

public class TestEndpoint extends TestCase {
    
    private static final String ADDRESS =
        //"http://172.16.1.165:8280/nic/services/transaction";
        "http://localhost:8480/nic/services/transaction";
    //"http://localhost:9000/nic/services/report";
    //    	"http://localhost:8280/nic/services/admin"; 
    //    private static final String ADDRESS = "http://localhost:9000/test"; 
    
    protected void setUp() throws Exception {
        super.setUp();
        URL currentPath = Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
        File log4jConfigFile = new File(currentPath.getFile());
        System.out.println("log4jConfigFile: " + log4jConfigFile + ", exists? " + log4jConfigFile.exists());
        System.out.println("Starting Server: " + currentPath);
        //AdminWSImpl demo = new AdminWSImpl();
        //src/main/webapp/WEB-INF/
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
            "WEB-INF/spring-local-datasource.xml", "WEB-INF/nic-ws.xml"
        });
        NicTransactionService nicTransactionService = context.getBean("nicTransactionService", NicTransactionService.class);
        //        CitizenRefService citizenRefService = context.getBean("citizenRefService", CitizenRefService.class);
        TransDTOMapper mapper = context.getBean(TransDTOMapper.class);
        //        
        //        PersoService persoService = context.getBean("persoService", PersoService.class);
        //        NicTabCitizensService nicTabCitizensService = context.getBean("nicTabCitizensService", NicTabCitizensService.class);
        //        
        TransactionWSImpl demo = new TransactionWSImpl();
        demo.setMapper(mapper);
        demo.setNicTransactionService(nicTransactionService);
        //        demo.setCitizenRefService(citizenRefService);
        //        demo.setNicTabCitizensService(nicTabCitizensService);
        //        
        ////        DxWSImpl demo = new DxWSImpl();
        ////        demo.setPersoService(persoService);
        //        
        //        ReconReportService reconReportService = context.getBean("reconReportService", ReconReportService.class);
        //        ReportWSImpl demo = new ReportWSImpl();
        //        demo.setReconReportService(reconReportService);
        
        
        Endpoint.publish(ADDRESS, demo);
        System.out.println("Start success");
    }
    
        public void testAdminWS(){
            
            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
            factory.setServiceClass(AdminWS.class);
            factory.setAddress("http://localhost:8280/nic/services/admin");
            AdminWS client = (AdminWS)factory.create();
    //        GetProofDocumentMatrix pdm = new GetProofDocumentMatrix();
    //        pdm.setTransactionType("REP");
    //        pdm.setTransactionSubtype("REP_LOST");
    //        GetProofDocumentMatrixResponse result;
    		try {
    
    //			result = client.getProofDocumentMatrix(pdm);
    //	        Assert.assertNotNull(result);
    //	        System.out.println("Receive:"+result+"\n"+result.getProofDocuments());
    			
    //			GetAuthorizedFunctions userInfoInput = new GetAuthorizedFunctions();
    //			userInfoInput.setUserID("REG_OFFICER_A");
    //			userInfoInput.setWkstnID("WKSTN_NO2");
    //			GetAuthorizedFunctionsResponse authorizedFunctionResult = client.getAuthorizedFunctions(userInfoInput);
    //			System.out.println("Function:"+authorizedFunctionResult+"\n"+authorizedFunctionResult.getFunctions());
    			
    			ChangeUserPassword changeUserPasswordInput = new ChangeUserPassword();
    			changeUserPasswordInput.setUserId("ric_supervisor");
    			changeUserPasswordInput.setCurrentPwd("test1234");
    			changeUserPasswordInput.setNewPwd("Test1234");
    			changeUserPasswordInput.setOfficerId("ric_supervisor");
    			changeUserPasswordInput.setWorkstationId("NIC-PC");
    			changeUserPasswordInput.setSiteCode("RICRG");
    			ChangeUserPasswordResponse result = client.changeUserPassword(changeUserPasswordInput );
    	        Assert.assertNotNull(result);
    	        System.out.println("changeUserPassword.result: " + result);
    	        System.out.println("[changeUserPassword] changeUserPassword.result: " + ReflectionToStringBuilder.toString(result, ToStringStyle.MULTI_LINE_STYLE));
    		} catch (FaultException e) {
    			e.printStackTrace();
    		}
    
        }
    
    //    public void xtestDxWS(){        
    //        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    //        factory.setServiceClass(DxWS.class);
    //        factory.setAddress(ADDRESS);
    //        DxWS client = (DxWS) factory.create();
    //        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><DataExchange source=\"PERSO_SYSTEM\" target=\"NIC_SYSTEM\" version=\"1.0\" xmlns:ns2=\"http://trans.dx.nic.asia.nec.com/\"><NicPersoInfo Nin=\"A123456789012C\" PersoRefID=\"12\" TransactionID=\"RICHQ-2013-000001\" ReprintCount=\"0\"><LogData><PackageID>RICHQ-2013-000001</PackageID><CCN>123456789</CCN><SAMKeyVersion>0</SAMKeyVersion></LogData></NicPersoInfo></DataExchange>";
    //        String inputCheckSum = "19762aa40e8ef88e08b212d4f83b4cdfdc73dc096758ffe4b703ee99c8706f77";
    //        System.out.println("Call [DxWS.uploadNicPersoInfo][1]");
    //        String result = client.uploadNicPersoInfo(inputXml, inputCheckSum);
    //        Assert.assertNotNull(result);
    //        System.out.println("Receive[1]:"+result);
    //        inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><DataExchange source=\"PERSO_SYSTEM\" target=\"NIC_SYSTEM\" version=\"1.0\" xmlns:ns2=\"http://trans.dx.nic.asia.nec.com/\"><NicPersoInfo Nin=\"A123456789012C\" PersoRefID=\"12\" TransactionID=\"RICHQ-2013-000001\" ReprintCount=\"1\"><LogInfo><Reason>The Reason</Reason><Remark>The Remarks</Remark></LogInfo></NicPersoInfo></DataExchange>";
    //        inputCheckSum = "7c44f0dc1601c0d77c1099a94760e9a7060de638f1b27b71e78bb3a76a419bd0";
    //        System.out.println("Call [DxWS.uploadNicPersoInfo][2]");
    //        result = client.uploadNicPersoInfo(inputXml, inputCheckSum);
    //        Assert.assertNotNull(result);
    //        System.out.println("Receive[2]:"+result);
    //    }
    
    @Test
    public void testTransWS() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(TransactionWS.class);
        factory.setAddress(ADDRESS);
        TransactionWS client = (TransactionWS) factory.create();
        
        TransactionRetrievalFilter filter = new TransactionRetrievalFilter();
        filter.getDataType().add(TransactionRetrievalDataType.REGISTRATION_DATA);
        filter.getDataType().add(TransactionRetrievalDataType.PAYMENT);
        filter.getDataType().add(TransactionRetrievalDataType.ISSUANCE_DATA);
        filter.setRecordType(TransactionRetrievalRecordType.ALL);
        filter.setTransactionID("RICHQ-2013-000011");
        filter.setNIN("A000000000008P");

        TransactionRetrievalResult result;
        try {
            result = client.retrieveTransaction(filter);
            List<MainTransaction> transDTOList = result.getTransactions();
            System.out.println("[retrieveTransaction] Response: " + transDTOList);
            for (MainTransaction tranDto: transDTOList) {
            	System.out.println("[retrieveTransaction] mainTran: " + ReflectionToStringBuilder.toString(tranDto, ToStringStyle.MULTI_LINE_STYLE));
            	System.out.println("[retrieveTransaction] mainTran.registrationData: " + ReflectionToStringBuilder.toString(tranDto.getRegistrationData(), ToStringStyle.MULTI_LINE_STYLE));

	        	System.out.println("[retrieveTransaction] mainTran.registrationData.fingerprintInfo: " + ReflectionToStringBuilder.toString(tranDto.getRegistrationData().getFingerprintInfo(), ToStringStyle.MULTI_LINE_STYLE));
	        	if (tranDto.getRegistrationData().getFingerprintInfo()!=null) {
		        	for (Fingerprint fp : tranDto.getRegistrationData().getFingerprintInfo().getFingerprints()) {
		        		System.out.println("[retrieveTransaction] fingerprint: " + fp.getFingerprintPosition()+", "+ fp.getFingerprintData());
		        	}
	        	}
	        	tranDto.getRegistrationData().getSignatureInfo().getSignatures().get(0).getSignatureData();
	        	System.out.println("[retrieveTransaction] mainTran.registrationData.signatureInfo: " + ReflectionToStringBuilder.toString(tranDto.getRegistrationData().getSignatureInfo(), ToStringStyle.MULTI_LINE_STYLE));
	        	if (tranDto.getRegistrationData().getFacialInfo()!=null) {
	        		System.out.println("[retrieveTransaction] mainTran.registrationData.facialInfo: " + ReflectionToStringBuilder.toString(tranDto.getRegistrationData().getFacialInfo(), ToStringStyle.MULTI_LINE_STYLE));
	        		for (FacialImage image : tranDto.getRegistrationData().getFacialInfo().getFacialImages()) {
	        			System.out.println("[retrieveTransaction] facialImage: " + image.getDocType()+"," +image.getFacialData());
	        		}
	        	}
	        	for (ReferenceDocument doc : tranDto.getRegistrationData().getReferenceDocuments()) {
	        		System.out.println("[retrieveTransaction] mainTran.registrationData.referenceDocuments: " + doc.getDocData());
	        	}
            }
        } catch (FaultException e) {
            e.printStackTrace();
        }
        
    }
    
    //    public void xtestTransWSLegacyNicInquiry() throws Exception{        
    //        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    //        factory.setServiceClass(TransactionWS.class);
    //        factory.setAddress(ADDRESS);
    //        TransactionWS client = (TransactionWS) factory.create();
    //
    //       
    //		LegacyNicInquiry legacyNicInquiry = new LegacyNicInquiry();
    //		legacyNicInquiry.setSurname("KOH");
    //		LegacyNicInquiryResponse result = client.legacyNicInquiry(legacyNicInquiry);
    //        Assert.assertNotNull(result);
    //        System.out.println("[legacyNicInquiry] Response: " + result);
    //        for (NicTabCitizens legacyNicCitizen: result.getNicTabCitizens()) {
    //        	System.out.println("[legacyNicInquiry] legacyNicCitizen: " + ReflectionToStringBuilder.toString(legacyNicCitizen, ToStringStyle.MULTI_LINE_STYLE));
    //        }
    //    }
    
    public void xtestTransWSUploadTransaction() throws Exception {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(TransactionWS.class);
        factory.setAddress(ADDRESS);
        TransactionWS client = (TransactionWS) factory.create();
        
        String xml = FileUtils.readFileToString(new File("C:\\worktool\\workspace\\NIC\\nic-parent\\nic-comp\\test\\main\\resources\\upload.xml"));
        MainTransaction mainTran = parseTransactionXML(xml);
        mainTran.setTransactionID("RICHQ-ERROR-001566");
		String result = client.uploadTransaction(mainTran);

        System.out.println("[uploadTransaction] Response: " + result);
    }
    
    public void xtestReportWSUploadTransactionReconData() throws Exception {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(ReportWS.class);
        factory.setAddress(ADDRESS);
        ReportWS client = (ReportWS) factory.create();
        
        UploadTransactionReconReportDataRequest tranReconDataInput = new UploadTransactionReconReportDataRequest();
        TransactionReconReportData tranReconReportData = new TransactionReconReportData();
        tranReconReportData.setRegSiteCode("RICHQ");
        tranReconReportData.setIssSiteCode("RICHQ");
        tranReconReportData.setApplicationDate("20140601");
        tranReconReportData.setCollectionDate("20140615");
        
        tranReconReportData.setCciPendingStockedIn(10L);
        tranReconReportData.setCciPendingCollection(0L);
        tranReconDataInput.getTransactionReconReportData().add(tranReconReportData);
        tranReconDataInput.setSourceSystemId("CC");
        tranReconDataInput.setReportGenerationTime(new Date());
        UploadTransactionReconReportDataResponse result = client.uploadTransactionReconReportData(tranReconDataInput);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getStatusCode(), "success");
        System.out.println("[uploadTransaction] Response: " + result);
    }
    
    private MainTransaction parseTransactionXML(String xml) throws Exception {			
		JAXBContext jc = JAXBContext.newInstance(com.nec.asia.nic.dx.trans.ObjectFactory.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		MainTransaction transDTO = ((JAXBElement<MainTransaction>) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()))).getValue();
		
		System.out.println(ReflectionToStringBuilder.toString(transDTO, ToStringStyle.MULTI_LINE_STYLE));
		return transDTO;
    }
    
    //    public void xtestTransWSCitizenAPI() throws FaultException{        
    //        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    //        factory.setServiceClass(TransactionWS.class);
    //        factory.setAddress(ADDRESS);
    //        TransactionWS client = (TransactionWS) factory.create();
    //
    //        CitizenRefRetrievalFilter input = new CitizenRefRetrievalFilter();
    ////        input.setNin("G121290778910C");
    //        input.setSurname("Goh");
    //        CitizenRefRetrievalResult result = client.retrieveCitizenRef(input);
    //        Assert.assertNotNull(result);
    //        List<CitizenRef> citizenRefList = result.getCitizenRefs();
    //        System.out.println("[retrieveCitizenRef] Response: " + citizenRefList);
    //        for (CitizenRef citizen: citizenRefList) {
    //        	System.out.println("[retrieveCitizenRef] citizen: " + ReflectionToStringBuilder.toString(citizen, ToStringStyle.MULTI_LINE_STYLE));
    //        }
    //    }
}
