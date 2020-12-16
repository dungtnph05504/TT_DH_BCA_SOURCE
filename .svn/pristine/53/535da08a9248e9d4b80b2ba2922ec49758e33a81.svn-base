package com.nec.asia.nic.comp.trans.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.integration.FileTransferService;
import com.nec.asia.nic.comp.integration.impl.FileTransferSshImpl;
import com.nec.asia.nic.comp.integration.impl.SecureFtpServer;
//import com.nec.asia.Persoservices;
import com.nec.asia.nic.comp.job.dto.CardDataDTO;
import com.nec.asia.nic.comp.job.dto.CcnInfoDTO;
import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.job.dto.NICRefTransactionStatusUpdateDTO;
import com.nec.asia.nic.comp.job.dto.NicPersoInfoDTO;
import com.nec.asia.nic.comp.job.dto.PackageInfoDTO;
import com.nec.asia.nic.comp.job.dto.PersoReferenceDataDTO;
import com.nec.asia.nic.comp.job.dto.TransactionInfoDTO;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
//import com.nec.asia.nic.comp.trans.service.DataPackService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.PersoService;
import com.nec.asia.nic.comp.trans.service.exception.PersoServiceException;
import com.nec.asia.nic.comp.trans.utils.TransDTOMapper;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.sshtools.j2ssh.sftp.SftpFile;

import junit.framework.TestCase;

public class PersoServiceTestCase extends TestCase {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static ApplicationContext context = null;
	//public static DataPackService dataPackservice = null;
	public static PersoService service = null;
	public static NicTransactionService transService = null;
	public static FileTransferService fileTransferService = null;
	public static NicTransactionAttachmentDao transactionDocumentDao;
	//public static com.nec.asia.jobcancellation.Jobcancellationservices jobCancellationWS = null;

	public PersoServiceTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			//dataPackservice = context.getBean("dataPackService", DataPackService.class);
			service = context.getBean("persoService", PersoService.class);
			service = context.getBean("persoServiceMBID60", PersoService.class);
			transService = context.getBean("nicTransactionService", NicTransactionService.class);
			transactionDocumentDao = context.getBean("transactionDocumentDao", NicTransactionAttachmentDao.class);
			//jobCancellationWS = context.getBean("jobCancellationWS", com.nec.asia.jobcancellation.Jobcancellationservices.class);
			
			
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
//	public void xtestJobCancellationWSCancelJobByTID() {
//		log("start jobCancellationWS - cancelJobByTransactionID");
//		try {
//			String transactionID = "RICHQ-2014-000001";
//			Holder<String> serviceMessage = new Holder<String>();
//			Holder<Integer> cancelJobByTransactionIDResult = new Holder<Integer>();
//			log("[cancelJobByTransactionID] transactionID:"+transactionID);
//			jobCancellationWS.cancelJobByTransactionID(transactionID, serviceMessage, cancelJobByTransactionIDResult);
//			log("[cancelJobByTransactionID] serviceMessage:"+serviceMessage.value+", cancelJobByTransactionIDResult:"+cancelJobByTransactionIDResult.value);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.assertNull(e);
//		}
//		log(" end  jobCancellationWS - cancelJobByTransactionID");
//	}
//	
//	public void xtestJobCancellationWSCancelJobByNIN() {
//		log("start jobCancellationWS - cancelJobByNIN");
//		try {
//			String nin = "A1234567891234";
//			Holder<String> serviceMessage = new Holder<String>();
//			Holder<Integer> cancelJobByNINResult = new Holder<Integer>();
//			log("[cancelJobByNIN] nin:"+nin);
//			jobCancellationWS.cancelJobByNIN(nin, serviceMessage, cancelJobByNINResult);
//			log("[cancelJobByNIN] serviceMessage:"+serviceMessage.value+", cancelJobByNINResult:"+cancelJobByNINResult.value);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.assertNull(e);
//		}
//		log(" end  jobCancellationWS - cancelJobByNIN");
//	}
	

//	public void xtestCancelJobByTID() {
//		log("start cancelJobByTransactionId");
//		try {
//			String transactionID = "CCM03-2013-000979";//"RICHQ-2014-000001";
//			String officerId = "CW";
//			String workstationId = "CW";
//			log("[cancelJobByTransactionID] transactionID:"+transactionID);
//			ResponseDTO response = service.cancelJobByTransactionId(transactionID, officerId, workstationId);
//			log("[cancelJobByTransactionID] code:"+response.getServiceCode()+", message:"+response.getServiceMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.assertNull(e);
//		}
//		log(" end  cancelJobByTransactionId");
//	}
	
	public void xtestPersoData() {
		log("start PersoService - PersoData");
		JobXmlConvertor util = new JobXmlConvertor();
//		Persoservices persoWS = context.getBean(Persoservices.class);
		try {
			String transactionId = "RICHQ-2013-001530";
			String persoXml = "";//this.retrievePersoXml(transactionId);
			File inputFile = new File("C:/Users/chris_wong/Desktop/NIC/test.xml");
			persoXml = FileUtils.readFileToString(inputFile); 
			log("original:"+persoXml.length()+"\t"+persoXml);
			persoXml = util.encodeString(persoXml);
			log("base64:"+persoXml.length()+"\t"+persoXml);
			//persoXml = Base64.encodeBase64String(persoXml.getBytes());
			String persoXmlChecksum = util.computeCheckSum(persoXml);
			log("persoXmlChecksum="+ persoXmlChecksum+", persoXml="+ persoXml);
			log("before call");
//			String persoRefId = persoWS.downloadJob(persoXml, persoXmlChecksum);
//			log("after call:"+persoRefId);
//			log("persoXmlChecksum="+ persoXmlChecksum+", persoXml="+ persoXml);
//			persoXml = util.decodeString(persoXml);
//			log("reverse64:"+persoXml.length()+"\t"+persoXml);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertNull(e);
		}
		log(" end  PersoService - PersoData");
	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private static String retrievePersoXml(String transactionId) {
		String persoXml = null;
		List<NicTransactionAttachment> docList = transactionDocumentDao.findNicTransactionAttachments(transactionId, "PERSO", TransDTOMapper.DEFAULT_SERIAL_NO);
		if (CollectionUtils.isNotEmpty(docList)) {
			NicTransactionAttachment persoDoc = docList.get(0);
			byte[] persoXmlData = persoDoc.getDocData();
			//persoXml = new String(persoXmlData, "UTF-8");
			persoXml = new String(Base64.decodeBase64(persoXmlData));
			//log("[retrievePersoXml] persoXml:"+persoXml);
		}
		return persoXml;
	}
	
	
	public void testSubmitPersoData() {
		log("start PersoService - submitPersoData");
		try {
			String transactionId = "RICHQ-2017-000006";//"1222016081600001";//"RICHQ-2013-001530";
//			NicTransactionAttachmentDao transactionDocumentDao = context.getBean("transactionDocumentDao", NicTransactionAttachmentDao.class);
//			String persoXml = null;
//			List<NicTransactionAttachment> docList = transactionDocumentDao.findNicTransactionAttachments(transactionId, "PERSO", TransDTOMapper.DEFAULT_SERIAL_NO);
//			if (CollectionUtils.isNotEmpty(docList)) {
//				NicTransactionAttachment persoDoc = docList.get(0);
//				byte[] persoXmlData = persoDoc.getDocData();
//				persoXml = new String(persoXmlData);
//				log("[preparePersoData] persoXml:"+persoXml);
//			}
			
			NicTransaction nicTransaction = transService.findById(transactionId);
			//nicTransaction = transService.getTransactionRegistrationData(nicTransaction);
			//nicTransaction = transService.getTransactiondocuments(nicTransaction);
			
			log("[submitPersoData] nicTransaction: " + ReflectionToStringBuilder.toString(nicTransaction, ToStringStyle.MULTI_LINE_STYLE));
			service.submitPersoData(nicTransaction, "RE-01-"+ transactionId);
			log("[submitPersoData] nicTransaction: submitted."); 
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertNull(e);
		}
		log(" end  PersoService - submitPersoData");
	}
	
	public void xtestReceiveNicPersoInfo() throws Throwable {
		JobXmlConvertor util = new JobXmlConvertor();
		String xml = "PERhdGFFeGNoYW5nZSB4bWxuczpuczI9Imh0dHA6Ly90cmFucy5keC5uaWMuYXNpYS5uZWMuY29tLyIgc291cmNlPSJjb20ubmVjLmFzaWEucGVyc29zZXJ2aWNlcy51cGxvYWRqb2IiIHRhcmdldD0iY29tLm5lYy5hc2lhLm5pYyIgdmVyc2lvbj0iMS4wIj48TmljUGVyc29JbmZvIFBhY2thZ2VJRD0iUEtHLlJJQ0hRLjEzMDcxMzAwMDEiPjxDYXJkRGF0YSBOaW49IlI3ODAxMDEwMTUwNTFVICIgUGVyc29SZWZJRD0iNCIgVHJhbnNhY3Rpb25JRD0iUklDSFEtMjAxMy0wMDE2NDIiIFJlcHJpbnRDb3VudD0iMSIgQ2NuPSI2MzAwMTE0OTIiIFNBTUtleVZlcnNpb249IiIgLz48L05pY1BlcnNvSW5mbz48L0RhdGFFeGNoYW5nZT4=";
		//= "PERhdGFFeGNoYW5nZSBzb3VyY2U9IlBFUlNPX1NZU1RFTSIgdGFyZ2V0PSJOSUNfU1lTVEVNIiB2ZXJzaW9uPSIxLjAiIHhtbG5zOm5zMj0iaHR0cDovL3RyYW5zLmR4Lm5pYy5hc2lhLm5lYy5jb20vIj48TmljUGVyc29JbmZvPjxDYXJkRGF0YSBOaW49IkExMjM0NTY3ODkwMTJBIiBQZXJzb1JlZklEPSIxMCIgVHJhbnNhY3Rpb25JRD0iUklDSFEtMjAxMy0wMDAwMDEiIFJlcHJpbnRDb3VudD0iMCIgQ2NuPSIxMjM0NTY3ODkiIFNBTUtleVZlcnNpb249IjAiLz48Q2FyZERhdGEgTmluPSJBMTIzNDU2MTg5MDEyQSIgUGVyc29SZWZJRD0iMTEiIFRyYW5zYWN0aW9uSUQ9IlJJQ0hRLTIwMTMtMDAwMDAxIiBSZXByaW50Q291bnQ9IjAiIENjbj0iMTIzNDE2Nzg5IiBTQU1LZXlWZXJzaW9uPSIwIi8+PC9OaWNQZXJzb0luZm8+PC9EYXRhRXhjaGFuZ2U+";
		String origXml = util.decodeString(xml);//encodeString(xml);
		log("origXml[1]: " + origXml);
		
		String xmlCS = "13D1DA2CEDE1A1BABED6842B3D38318C";
		log("xmlCS[1]: " + xmlCS);
		service.receiveNicPersoInfo(xml, xmlCS);
	}
	
	private String preparePackageId(String siteCode, String sequence) {//PKG.CCQB.1401220028
		String dateString = DateUtil.parseDate(new Date(), "yyMMdd");
		return "PKG."+siteCode+"."+dateString+"X"+sequence;
	}
	//DSP.CCQB.1401240003
	private String prepareDispatchId(String siteCode, String sequence) {//PKG.CCQB.1401220028
		String dateString = DateUtil.parseDate(new Date(), "yyMMdd");
		return "DSP."+siteCode+"."+dateString+"X"+sequence;
	}
	private String prepareCcn() {
		String ccn = "";
		final int MAX = 100000000; 
		int number = (int) ((Math.random() * MAX) % MAX);
		ccn = String.format("%08d", number);
		ccn += computeCCNCheckSum(ccn);
		System.out.println(ccn);
		return ccn;
	}
	private String computeCCNCheckSum(String ccn8Digit) {
		String checkSum = "0";
		
		return checkSum;
	}
	
	/*
 	<NicPersoInfo PackageID="PKG.RICHQ.1307040002">
	<CardData Nin="A0123456789120" PersoRefID="1" TransactionID="X10000000A" ReprintCount="0" Ccn="123456780" SAMKeyVersion="1" />
	<CardData Nin="A0123456789121" PersoRefID="2" TransactionID="X10000000B" ReprintCount="0" Ccn="123456781" SAMKeyVersion="1" />
	<CardData Nin="A0123456789122" PersoRefID="3" TransactionID="X10000000C" ReprintCount="0" Ccn="123456782" SAMKeyVersion="1" />
	<CardData Nin="A0123456789123" PersoRefID="4" TransactionID="X10000000D" ReprintCount="0" Ccn="123456783" SAMKeyVersion="1" />
	<CardData Nin="A0123456789124" PersoRefID="5" TransactionID="X10000000E" ReprintCount="0" Ccn="123456784" SAMKeyVersion="1" />
	<CardData Nin="A0123456789125" PersoRefID="6" TransactionID="X10000000F" ReprintCount="0" Ccn="123456785" SAMKeyVersion="1" />
	</NicPersoInfo>
	 */
	public void xtestReceiveNicPersoInfoForReplacement() throws Throwable {
		JobXmlConvertor util = new JobXmlConvertor();
		PersoReferenceDataDTO dataExchangeDTO = new PersoReferenceDataDTO();
		NicPersoInfoDTO dto = new NicPersoInfoDTO();
		String packageId = preparePackageId("CCCG", "0001");
		String ccn = prepareCcn();
		dto.setPackageID(packageId);//"PKG.RICHQ.1307040002");
		List<CardDataDTO> cardDataList = new ArrayList<CardDataDTO>();
		CardDataDTO cardDataDTO = new CardDataDTO();
		cardDataDTO.setTransactionID("CCCG-2013-000154");//("RICHQ-2013-001908");//"CONV-0000008-20130818");
		cardDataDTO.setPersoRefID("207");
		cardDataDTO.setNin("B1609520133674");//("R780101015072M");//"A0507650802337");
		cardDataDTO.setReprintCount("1");
		cardDataDTO.setCcn(ccn);
		cardDataDTO.setSAMKeyVersion("0");	
		cardDataDTO.setPackageSequence("1");
		cardDataList.add(cardDataDTO);
		
		cardDataDTO = new CardDataDTO();
		cardDataDTO.setTransactionID("RICHQ-2014-000061");//("RICHQ-2013-001908");//"CONV-0000008-20130818");
		cardDataDTO.setPersoRefID("198");
		cardDataDTO.setNin("M0807950000233");//("R780101015072M");//"A0507650802337");
		cardDataDTO.setReprintCount("1");
		ccn = prepareCcn();
		cardDataDTO.setCcn(ccn);
		cardDataDTO.setSAMKeyVersion("0");	
		cardDataDTO.setPackageSequence("2");
		cardDataList.add(cardDataDTO);
		
		cardDataDTO = new CardDataDTO();
		cardDataDTO.setTransactionID("RICHQ-2014-000062");//("RICHQ-2013-001908");//"CONV-0000008-20130818");
		cardDataDTO.setPersoRefID("197");
		cardDataDTO.setNin("Z151080000046A");//("R780101015072M");//"A0507650802337");
		cardDataDTO.setReprintCount("1");
		ccn = prepareCcn();
		cardDataDTO.setCcn(ccn);
		cardDataDTO.setSAMKeyVersion("0");	
		cardDataDTO.setPackageSequence("3");
		cardDataList.add(cardDataDTO);
		dto.setCardDataList(cardDataList);
								
		dataExchangeDTO.setNicPersoInfo(dto);
		String origXml = null;
		origXml = util.marshal(dataExchangeDTO);
		log("origXml[1]: " + origXml);
		String xml = util.encodeString(origXml);
		
		log("encodeStringXml[1]: " + xml);
		String xmlCS = util.computeCheckSum(xml);
		
		log("xmlCS[1]: " + xmlCS);
		service.receiveNicPersoInfo(xml, xmlCS);
	}
	
	public void xtestReceivedDispatchInfo() throws JaxbXmlConvertorException, NoSuchAlgorithmException, UnsupportedEncodingException, PersoServiceException {
		JobXmlConvertor util = new JobXmlConvertor();
		PersoReferenceDataDTO dataExchangeDTO = new PersoReferenceDataDTO();
		PackageInfoDTO dto = new PackageInfoDTO();
		String dispatchId = this.prepareDispatchId("CCCG", "001");
		dto.setDispatchId(dispatchId);
		dto.setPackageIdList(new ArrayList<String>());
		//dto.setStatus("");
		dto.getPackageIdList().add("PKG.CCCG.140205X0001");
		dataExchangeDTO.setPackageInfoDto(dto);
		
		String origXml = null;
		origXml = util.marshal(dataExchangeDTO);
		log("origXml[1]: " + origXml);
		String xml = util.encodeString(origXml);
		
		log("encodeStringXml[1]: " + xml);
		String xmlCS = util.computeCheckSum(xml);
		
		log("xmlCS[1]: " + xmlCS);
		service.updateCardStatus(xml, xmlCS);
	}
	
	public void xtestReceiveNicPersoInfoForPersoRejection() throws Throwable {
		JobXmlConvertor util = new JobXmlConvertor();
		PersoReferenceDataDTO dataExchangeDTO = new PersoReferenceDataDTO();
		NicPersoInfoDTO dto = new NicPersoInfoDTO();
		dto.setTransactionID("RICRH-2013-000065");
		dto.setPersoRefID("114");
		dto.setNin("R210895130588C");
		dto.setReprintCount("1");
		dto.setLogInfo(new LogInfoDTO());
		dto.getLogInfo().setReason(PersoService.PERSO_ERROR_CODE_PERSO_QC_PERMANENT_REJECT);
		dto.getLogInfo().setRemark("Signature is no cleared, Perso Supervisor Suggest to reject the card.");
		dataExchangeDTO.setNicPersoInfo(dto);
		String origXml = null;
		origXml = util.marshal(dataExchangeDTO);
		log("origXml[1]: " + origXml);
		String xml = util.encodeString(origXml);
		
		log("encodeStringXml[1]: " + xml);
		String xmlCS = util.computeCheckSum(xml);
		
		log("xmlCS[1]: " + xmlCS);
		service.receiveNicPersoInfo(xml, xmlCS);
	}
	
	public void xtestReceiveNicPersoInfoForDataDeserial() throws Throwable {
		JobXmlConvertor util = new JobXmlConvertor();
		PersoReferenceDataDTO dataExchangeDTO = new PersoReferenceDataDTO();
		NicPersoInfoDTO dto = new NicPersoInfoDTO();
		dto.setTransactionID("RICHQ-2013-001529");
		dto.setPersoRefID("47");
		dto.setNin("");
		dto.setReprintCount("1");
		dto.setLogInfo(new LogInfoDTO());
		dto.getLogInfo().setReason(PersoService.PERSO_ERROR_CODE_DESERIALIZE_DATA_ERROR);
		dto.getLogInfo().setRemark("Perso remark: DATA RECEIVED ERROR");
		dataExchangeDTO.setNicPersoInfo(dto);
		String origXml = null;
		origXml = util.marshal(dataExchangeDTO);
		log("origXml[1]: " + origXml);
		String xml = util.encodeString(origXml);
		
		log("encodeStringXml[1]: " + xml);
		String xmlCS = util.computeCheckSum(xml);
		
		log("xmlCS[1]: " + xmlCS);
		service.receiveNicPersoInfo(xml, xmlCS);
	}
	
	public void xtestPackNSubmitPerso() {
		log("start Perso - PackNSubmitPerso");
		try {
			String transactionId = "RICHQ-2013-001530";
			NicTransaction nicTransaction = transService.findById(transactionId);
			nicTransaction = transService.getTransactionRegistrationData(nicTransaction);
			nicTransaction = transService.getTransactiondocuments(nicTransaction);
			log("[preparePersoData] nicTransaction: " + transactionId);
			//dataPackservice.preparePersoData(nicTransaction);
			log("[preparePersoData] nicTransaction: submitted."); 
			
//			NicTransaction nicTransaction = transService.findById(transactionId);
//			nicTransaction = transService.getTransactionRegistrationData(nicTransaction);
//			nicTransaction = transService.getTransactiondocuments(nicTransaction);
			
			log("[submitPersoData] nicTransaction: " + transactionId);
			service.submitPersoData(nicTransaction, transactionId);
			log("[submitPersoData] nicTransaction: submitted."); 
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertNull(e);
		}
		log(" end  Perso - PackNSubmitPerso");
	}
	
	public void xtestLogic() {
		Map<String, String> fpIndicatorMap = new HashMap<String, String>();
		String fpIndicators = "01-Y,02-N,03-Y,04-N,05-,06-,07-N,08";
		String[] fpIndicatorArray = fpIndicators.split(",");
		for (String fpIndicator : fpIndicatorArray) {
			if (StringUtils.contains(fpIndicator, "-") && fpIndicator.split("-").length==2) {
				fpIndicatorMap.put(fpIndicator.split("-")[0], fpIndicator.split("-")[1]);
			}
		}
		log("[fpIndicatorMap] :" + fpIndicatorMap+".");
	}
	
	public void xtestGenerateCardStatusXml() throws JaxbXmlConvertorException, NoSuchAlgorithmException, UnsupportedEncodingException {
		PersoReferenceDataDTO dto = new PersoReferenceDataDTO();
		dto.setSource("com.nec.asia.nic.dataexchange.persorequest");
		dto.setTarget("com.nec.asia.perso.dataexchange.persorequest");
		dto.setVersion("1.0");
		CcnInfoDTO cardInfoDto = new CcnInfoDTO();
		cardInfoDto.setCcnList(new ArrayList<String>());
		cardInfoDto.getCcnList().add("100000017");
		cardInfoDto.setStatus("DISPATCHED");
		dto.setCardInfoDto(cardInfoDto );
		JobXmlConvertor util = new JobXmlConvertor();
		String xml = util.marshal(dto);
		System.out.println("");
		System.out.println("");
		System.out.println(xml);
		System.out.println("");
		System.out.println("xml:");
		xml = util.encodeString(xml);
		System.out.println(xml);
		System.out.println("cs:");
		String cs = util.computeCheckSum(xml);
		System.out.println(cs);
		//<DataExchange source="com.nec.asia.nic.dataexchange.persorequest" target="com.nec.asia.perso.dataexchange.persorequest" version="1.0" xmlns:ns2="http://trans.dx.nic.asia.nec.com/"><NicCardStatusUpdate><Ccn>123456780</Ccn><Ccn>123456781</Ccn><Ccn>123456782</Ccn><Ccn>123456783</Ccn><Ccn>123456784</Ccn><Ccn>123456785</Ccn><Ccn>123456786</Ccn><Ccn>123456787</Ccn></NicCardStatusUpdate></DataExchange>
	}
	
	public void xtestUpdateTxnStatus() {
		String xml = "PERhdGFFeGNoYW5nZSBzb3VyY2U9ImNvbS5uZWMuYXNpYS5wZXJzby5kYXRhZXhjaGFuZ2UudHJhbnNhY3Rpb25zdGF0dXN1cGRhdGUiIHRhcmdldD0iY29tLm5lYy5hc2lhLm5pYy5kYXRhZXhjaGFuZ2UudHJhbnNhY3Rpb25zdGF0dXN1cGRhdGUiIHZlcnNpb249IjEuMCIgeG1sbnM6bnMyPSJodHRwOi8vdHJhbnMuZHgubmljLmFzaWEubmVjLmNvbS8iPjxOaWNUcmFuc2FjdGlvblN0YXR1c1VwZGF0ZSBjY249IjI3NjkwMTg1MCIgU3RhdHVzPSJJTlZFTlRPUllfQ0FSRF9ERVNUUk9ZRUQiLz48L0RhdGFFeGNoYW5nZT4=";
		String cs = "2BD7F0C6D5309CA905034FFADF3F07B0";
		
		try {
			service.updateCardStatus(xml, cs);
		} catch (PersoServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void xtestUpdateNicStatusForDestroy() {
		String xml = "";
		String cs = "";		
		try {
			PersoReferenceDataDTO dto = new PersoReferenceDataDTO();
			dto.setSource("INVENTORY");
			dto.setTarget("NIC");
			dto.setVersion("1.0");
			
			TransactionInfoDTO transactionInfoDto = new TransactionInfoDTO();
			transactionInfoDto.setCcn("015474720");
			transactionInfoDto.setStatus("INVENTORY_CARD_RECEIVED"); //INVENTORY_CARD_DESTROYED
//			dto.setTransactionInfoDto(transactionInfoDto);
			
			NICRefTransactionStatusUpdateDTO subDTO = new NICRefTransactionStatusUpdateDTO();
			subDTO.setCcn("015474720");
			subDTO.setRefTransactionStatus("INVENTORY_CARD_RECEIVED");
			subDTO.setOfficerId("CHRIS");
			subDTO.setWkstnId("PES-WKSTN-01");
			subDTO.setSiteCode("CPC");
			dto.setRefTransactionStatusUpdateDto(subDTO);
			JobXmlConvertor util = new JobXmlConvertor();
			xml = util.marshal(dto);
			System.out.println("xml:"+xml);
			xml = util.encodeString(xml);
			cs = util.computeCheckSum(xml);
			System.out.println("base64xml:"+xml);
			System.out.println("base64cs:"+cs);
			service.updateCardStatus(xml, cs);
		} catch (PersoServiceException e) {
			e.printStackTrace();
		} catch (JaxbXmlConvertorException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
	
	public static void xtestGetXML() {
//		List<String> transactionIdList = new ArrayList<String>();
//		NicTransaction e = new NicTransaction();
//		e.setTransactionStatus("NIC_TX_COMPLETD");
//		List<NicTransaction> resultList = transService.findAll(e);
//		for (NicTransaction t: resultList) {
//			String transactionId = t.getTransactionId();
//			transactionIdList.add(transactionId);
//		}
		
//		for (String transactionId : transactionIdList) {
//			//String transactionId = "CCEB-2013-000052";
//			String xml = retrievePersoXml(transactionId);
//			JobXmlConvertor util = new JobXmlConvertor();
//			boolean isPersoXml = false;
//			try {
//				PersoReferenceDataDTO dto = (PersoReferenceDataDTO) util.unmarshal(xml);
//				isPersoXml = true;
//			} catch (Exception ex) {
//				isPersoXml = false;
//			}
//			if (!isPersoXml)
//				System.out.println("transactionId: "+transactionId+", valid: "+ isPersoXml);
//		}
		String transactionId = "CCEB-2013-000052";
		transactionId = "RICHQ-2013-001723";
		String xml2 =retrievePersoXml(transactionId);
	}
}
//org.hibernate.search.event.FullTextIndexEventListener