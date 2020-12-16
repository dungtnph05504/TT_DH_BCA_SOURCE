package com.nec.asia.nic.comp.trans.service.impl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.utils.TransDTOMapper;
import com.nec.asia.nic.dx.dto.DataExchangeDTO;
import com.nec.asia.nic.dx.dto.FpVerificationDTO;
//import com.nec.asia.nic.dx.nic.Address;
import com.nec.asia.nic.dx.trans.Fingerprint;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.trans.ObjectFactory;
import com.nec.asia.nic.dx.trans.PersonDetail;
import com.nec.asia.nic.dx.trans.RegistrationData;
//import com.nec.asia.nic.dx.trans.ScannedDocument;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.util.LogDataXmlConvertor;

public class NicTransactionServiceTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static NicTransactionService service = null;
	public NicTransactionServiceTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			service = context.getBean("nicTransactionService", NicTransactionService.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void xtestInitDataXML() {
		String transactionId = "RICHQ-2017-000000";
		MainTransaction transactionDTO = new MainTransaction();
		transactionDTO.setTransactionID(transactionId);
		transactionDTO.setApplnRefNo(transactionId);
		transactionDTO.setNIN("A123456789B");
		transactionDTO.setDateOfApplication(new Date());
		transactionDTO.setRegSiteCode("RICHQ");
		transactionDTO.setIssSiteCode("RICHQ");
		transactionDTO.setTransactionType("NEW");
		transactionDTO.setTransactionStatus("RV");	
		
		transactionDTO.setCreateBy("ADMIN");
		transactionDTO.setCreateDateTime(new Date());
		transactionDTO.setCreateWkstnID("SYSTEM");
		transactionDTO.setUpdateBy("ADMIN");
		transactionDTO.setUpdateDateTime(new Date());
		transactionDTO.setUpdateWkstnID("SYSTEM");
		
		try {
			JAXBContext jc = JAXBContext.newInstance(MainTransaction.class);
			Marshaller marshaller = jc.createMarshaller();
			ObjectFactory of = new ObjectFactory();
			JAXBElement<MainTransaction> jb = of.createMainTransaction(transactionDTO);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			marshaller.marshal(jb, baos);
			String xml3 = new String( baos.toByteArray());
			System.out.println(xml3);
			
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns4:MainTransaction xmlns=\"http://dx.nec.com/\" xmlns:ns2=\"http://nic.dx.nic.asia.nec.com/\" xmlns:ns3=\"http://common.dx.nic.asia.nec.com/\" xmlns:ns4=\"http://trans.dx.nic.asia.nec.com/\"><ns4:TransactionID>RICHQ-2013-000001</ns4:TransactionID><ns4:QueueNumber>R0001</ns4:QueueNumber><ns4:ApplnRefNo>RICHQ-2013-000001</ns4:ApplnRefNo><ns4:Nin>T123456789012A</ns4:Nin><ns4:DateOfApplication>2013-05-21T17:26:32.142+08:00</ns4:DateOfApplication><ns4:RegSiteCode>RICHQ</ns4:RegSiteCode><ns4:IssSiteCode>RICHQ</ns4:IssSiteCode><ns4:TransactionType>REG</ns4:TransactionType><ns4:TransactionSubtype>NEW_CITIZEN</ns4:TransactionSubtype><ns4:TransactionStatus>COMPLETED</ns4:TransactionStatus><ns4:CreateBy>SYSTEM</ns4:CreateBy><ns4:CreateDate>2013-05-21T17:26:32.143+08:00</ns4:CreateDate><ns4:CreateWkstnID>SYSTEM</ns4:CreateWkstnID><ns4:UpdateBy>SYSTEM</ns4:UpdateBy><ns4:UpdateDate>2013-05-21T17:26:32.143+08:00</ns4:UpdateDate><ns4:UpdateWkstnID>SYSTEM</ns4:UpdateWkstnID></ns4:MainTransaction>";
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			JAXBElement<MainTransaction> jb2  = (JAXBElement<MainTransaction>) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
			System.out.println(ReflectionToStringBuilder.toString(jb2.getValue(), ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void xtestSave() {
		log("start nicTransactionService - save");
		try {
			//String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns4:MainTransaction xmlns=\"http://dx.nec.com/\" xmlns:ns2=\"http://nic.dx.nic.asia.nec.com/\" xmlns:ns3=\"http://common.dx.nic.asia.nec.com/\" xmlns:ns4=\"http://trans.dx.nic.asia.nec.com/\"><ns4:TransactionID>RICHQ-2013-000002</ns4:TransactionID><ns4:QueueNumber>R0001</ns4:QueueNumber><ns4:ApplnRefNo>RICHQ-2013-000002</ns4:ApplnRefNo><ns4:Nin>T123456789012A</ns4:Nin><ns4:DateOfApplication>2013-05-21T17:26:32.142+08:00</ns4:DateOfApplication><ns4:RegSiteCode>RICHQ</ns4:RegSiteCode><ns4:IssSiteCode>RICHQ</ns4:IssSiteCode><ns4:TransactionType>REG</ns4:TransactionType><ns4:TransactionSubtype>NEW_CITIZEN</ns4:TransactionSubtype><ns4:TransactionStatus>COMPLETED</ns4:TransactionStatus><ns4:CreateBy>SYSTEM</ns4:CreateBy><ns4:CreateDate>2013-05-21T17:26:32.143+08:00</ns4:CreateDate><ns4:CreateWkstnID>SYSTEM</ns4:CreateWkstnID><ns4:UpdateBy>SYSTEM</ns4:UpdateBy><ns4:UpdateDate>2013-05-21T17:26:32.143+08:00</ns4:UpdateDate><ns4:UpdateWkstnID>SYSTEM</ns4:UpdateWkstnID></ns4:MainTransaction>";
			String xml = "";
	    	xml = FileUtils.readFileToString(new File("src/test/resources/upload.xml"));
	    	
			JAXBContext jc = JAXBContext.newInstance(com.nec.asia.nic.dx.trans.ObjectFactory.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			JAXBElement<MainTransaction> mainTransactionJE = (JAXBElement<MainTransaction>) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
			MainTransaction mainTransaction = mainTransactionJE.getValue();
			System.out.println(ReflectionToStringBuilder.toString(mainTransaction, ToStringStyle.MULTI_LINE_STYLE));
			service.saveMainTransaction(mainTransaction);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertNull("Exception should be null", e);
		}
		log(" end  nicTransactionService - save");
	}
	
	public void xtestDelete() {
		log("start nicTransactionService - delete");
		try {
			service.delete("RICHQ-2013-001099");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertNull("Exception should be null", e);
		}
		log(" end  nicTransactionService - delete");
	}
	
	public void xtestGetMainTransaction() {
		log("start nicTransactionService - findAllTransactionHistory");
		try {
			String transactionId = "004-2016-000008";
			String documentNo = "";
			String nin = "";
			List<MainTransaction> transactionList = null;
			transactionList = service.findAllTransactionHistory(transactionId, documentNo, nin, true, true, true, false, false, false);
			log("[findAllTransactionHistory] transDBO.size: "+transactionList.size());
			for (MainTransaction mt: transactionList) {
				log("[T]: "+mt+", "+ReflectionToStringBuilder.toString(mt, ToStringStyle.MULTI_LINE_STYLE));
				if (mt.getRegistrationData()!=null && mt.getRegistrationData().getFingerprintInfo()!=null) {
					for (Fingerprint fp: mt.getRegistrationData().getFingerprintInfo().getFingerprints()) {
						log("[fp]: "+fp.getFingerprintPosition()+", "+ReflectionToStringBuilder.toString(fp, ToStringStyle.MULTI_LINE_STYLE));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  nicTransactionService - findAllTransactionHistory");
	}
	
	public void xtestFindAllLogs() {
		log("start nicTransactionService - findAllLogs");
		try {
			String transactionId = "RICHQ-2013-000002";

			List<NicTransactionLog> nicTransLogDBOList = service.findAllTransactionLogsByRefId(transactionId);
			for (NicTransactionLog logDBO: nicTransLogDBOList) {
				log("[findTransactionLogsByRefId] logDBO: " + ReflectionToStringBuilder.toString(logDBO, ToStringStyle.MULTI_LINE_STYLE));
		    }
			log("[findTransactionLogsByRefId] transDBO.size: "+nicTransLogDBOList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  nicTransactionService - findAllLogs");
	}
	
	public void testFindAllTransactionHistory() {
		log("start nicTransactionService - findAllTransactionHistory");
		try {
			String transactionId =""; //= "004-2016-000004";//"RICHQ-2013-001095";
			String documentNo =""; //= "958514326";//"x012222222222X";
			String nin = "A123456789B";
			boolean wantTransactionLog = true;
			//need to exclude from result.
			boolean wantRegistrationData = true;
			boolean wantTransactionDoc = true;
			boolean wantIssuanceData = true;
			boolean wantPayment = true;
			boolean wantLatestOnly = false;

			List<MainTransaction> transDTOList = service.findAllTransactionHistory(transactionId, documentNo, nin, wantRegistrationData, wantTransactionDoc, wantIssuanceData, wantPayment, wantTransactionLog, wantLatestOnly);
			for (MainTransaction transDTO: transDTOList) {
				log("[findAllTransactionHistory] transDTO: " + ReflectionToStringBuilder.toString(transDTO, ToStringStyle.MULTI_LINE_STYLE));
//				System.out.println("[retrieveTransaction] trans.registrationData.scannedDocuments: " + transDTO.getRegistrationData().getScannedDocuments());
//				for (ScannedDocument doc : transDTO.getRegistrationData().getScannedDocuments()) {
//	        		System.out.println("[retrieveTransaction] trans.registrationData.scannedDocuments: " + ReflectionToStringBuilder.toString(doc, ToStringStyle.MULTI_LINE_STYLE));
//	        	}
		    }
			log("[findAllTransactionHistory] transDTO.size: "+transDTOList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  nicTransactionService - findAllTransactionHistory");
	}
	
	public void xtestFindAllRicLogs() {
		log("start nicTransactionService - findAllRicLogs");
		try {
			String transactionId = "RICHQ-2013-000002";

			List<NicTransactionLog> nicTransLogDBOList = service.findAllRicTransactionLogsByRefId(transactionId);
			for (NicTransactionLog logDBO: nicTransLogDBOList) {
				log("[findAllRicTransactionLogsByRefId] logDBO: " + ReflectionToStringBuilder.toString(logDBO, ToStringStyle.MULTI_LINE_STYLE));
		    }
			log("[findAllRicTransactionLogsByRefId] transDBO.size: "+nicTransLogDBOList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  nicTransactionService - findAllRicLogs");
	}
	
	public void xtestFindAllByFields() {
		log("start nicTransactionService - FindAllByFields");
		try {
			String surname = null;
			String firstName = "%wa%";
			String middleName = null;
			String sex = null;
			String dob = "09-01-1990";

			boolean hasIssuanceData = true;
			boolean hasTransDocs = true;
			boolean hasRegData = true;
			boolean hasTransPayment = true;
			List<NicTransaction> nicTransDBOList = service.findAllByFields(surname, firstName, middleName, sex, dob, hasIssuanceData, hasTransDocs, hasRegData, hasTransPayment);
			for (NicTransaction transDBO: nicTransDBOList) {
				log("[findAllByFields] transDBO: " + ReflectionToStringBuilder.toString(transDBO, ToStringStyle.MULTI_LINE_STYLE));
		    }
			log("[findAllByFields] transDBO.size: "+nicTransDBOList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  nicTransactionService - FindAllByFields");
	}
	
	public void xtestMapper() {
		
		TransDTOMapper util = new TransDTOMapper();
		
		String transactionId = "";
		String nin = "";
		RegistrationData regDataDTO = new RegistrationData();
		regDataDTO.setPersonDetail(new PersonDetail());
//		com.nec.asia.nic.dx.trans.Address add = new com.nec.asia.nic.dx.trans.Address();
//		regDataDTO.setAddress(add);
		regDataDTO.getPersonDetail().setApproxDOB("  -  -1995");
		NicRegistrationData regDataDBO = util.parseRegistrationDataDBO(transactionId, nin, regDataDTO);
		log("[mapper] regDataDBO: " + ReflectionToStringBuilder.toString(regDataDBO, ToStringStyle.MULTI_LINE_STYLE));
		
	}
	
	public void xtestValidateTransaction() {
		log("start nicTransactionService - checkDuplicateApplication");
		try {
			String transactionId = "RICRG-2013-000844";
			String nin = "R780101015072M";
			String transactionType = "CON";
			String transactionSubType = "RE-REG";
			String refTxnId = "RICRH-2013-000844";
			
			String checkResult = service.checkDuplicateApplication(
					transactionId, nin, transactionType, transactionSubType, refTxnId);
			log("[checkDuplicateApplication] checkResult: "+checkResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  nicTransactionService - checkDuplicateApplication");
	}
	
//	public void xtestCheckDuplicateByNin() {
//		log("start nicTransactionService - checkDuplicateByNin");
//		try {
//			String transactionId = "CCPL-2013-013127";
//			String nin = "E1505884401064";
//			String transactionType = "CON";
//			String remarks = "";
//			String officerId = "CW";
//			String wkstnId = "CW";
//			String siteCode = "CCPL";
//			
//			List<NicTransaction> checkResult = service.checkDuplicateByNin(transactionId, nin, transactionType, remarks, officerId, wkstnId, siteCode);
//			log("[checkDuplicateByNin] checkResult: "+checkResult);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log(" end  nicTransactionService - checkDuplicateByNin");
//	}
	
	public void xtestIssuanceLogEnquiry() {
		log("start transService - findAllForPagination");
		try {
			LogDataXmlConvertor issuanceUtil = new LogDataXmlConvertor();
			int currentPage = 1;
			int pageSize = 96;
			String siteCode = "";
			String officerId = "";
			String transactionStage = NicTransactionLog.TRANSACTION_STAGE_RIC_ISSUANCE;//"ISSUANCE";
			String transactionStatus = "";
			PaginatedResult<NicTransactionLog> fpIssuDataList = service.findAllForPagination(currentPage, pageSize, siteCode, officerId, transactionStage, transactionStatus );
			
			if(fpIssuDataList!=null){
				for(NicTransactionLog nicTransactionLog :fpIssuDataList.getRows()){
					if (StringUtils.isNotBlank(nicTransactionLog.getLogData())) {
						String logData = nicTransactionLog.getLogData();
						int startIndex = StringUtils.indexOf(nicTransactionLog.getLogData(), "<DataExchange>");
						if (startIndex!=-1) {
							if (startIndex!=0) {
								log("invalid header["+startIndex+"]: "+StringUtils.substring(logData, 0, startIndex));
								logData = StringUtils.substring(logData, startIndex);
								log("updated xml: "+logData);
							}
						} else {
							log("skip non DataExchange xml: "+logData);
							continue;
						}
						
						try {
							DataExchangeDTO issLogDataExchangeDTO = (DataExchangeDTO) issuanceUtil.unmarshal(logData);
							log("[FPIssuanceEnquiryController].txnEnquirySearch() - IssLog exists, ID:"+nicTransactionLog.getRefId()+", STAGE:"+nicTransactionLog.getTransactionStage()+", LOG_ID:"+nicTransactionLog.getLogId());
							if (issLogDataExchangeDTO!=null) {
								if (issLogDataExchangeDTO.getIssuanceDetails()!=null) {
									if (issLogDataExchangeDTO.getIssuanceDetails().getFpVerifications()!=null) {
										if (issLogDataExchangeDTO.getIssuanceDetails().getFpVerifications().getFpVerificationList().size()>0) {
											FpVerificationDTO fpVerification = issLogDataExchangeDTO.getIssuanceDetails().getFpVerifications().getFpVerificationList().get(0);
											if (fpVerification.getFingerprintData()==null) {
												log("ERROR: not fingerprint Data at all."+nicTransactionLog.getLogId());
											} else {
												log("Valid FP: size:"+fpVerification.getFingerprintData().length);
											}
										}
									}
								}
							}
						} catch (Exception e) {
							log("[FPIssuanceEnquiryController].txnEnquirySearch() - IssLog not exists, ID:"+nicTransactionLog.getRefId()+", STAGE:"+nicTransactionLog.getTransactionStage()+", LOG_ID:"+nicTransactionLog.getLogId());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertNull(e);
		}
		log(" end  transService - findAllForPagination");
	}
			
	public void xtestLog() {
		log("start transService - findAllTransactionLogsByRefId");
		try {
			LogDataXmlConvertor issuanceUtil = new LogDataXmlConvertor();
			List<NicTransactionLog> resultLogList = service.findAllTransactionLogsByRefId("CCRH-2014-033155");
			for(NicTransactionLog nicTransactionLog :resultLogList){
				if (StringUtils.isNotBlank(nicTransactionLog.getLogData())) {
					String logData = nicTransactionLog.getLogData();
					int startIndex = StringUtils.indexOf(nicTransactionLog.getLogData(), "<DataExchange>");
					if (startIndex!=-1) {
						log("invalid header["+startIndex+"]: "+StringUtils.substring(logData, 0, startIndex));
						logData = StringUtils.substring(logData, startIndex);
						log("updated xml: "+logData);
					} else {
						log("skip non DataExchange xml: "+logData);
						continue;
					}
					
					try {
						DataExchangeDTO issLogDataExchangeDTO = (DataExchangeDTO) issuanceUtil.unmarshal(logData);
						log("[FPIssuanceEnquiryController].txnEnquirySearch() - IssLog exists, ID:"+nicTransactionLog.getRefId()+", STAGE:"+nicTransactionLog.getTransactionStage()+", LOG_ID:"+nicTransactionLog.getLogId());
						if (issLogDataExchangeDTO!=null) {
							if (issLogDataExchangeDTO.getIssuanceDetails()!=null) {
								if (issLogDataExchangeDTO.getIssuanceDetails().getFpVerifications()!=null) {
									if (issLogDataExchangeDTO.getIssuanceDetails().getFpVerifications().getFpVerificationList().size()>0) {
										FpVerificationDTO fpVerification = issLogDataExchangeDTO.getIssuanceDetails().getFpVerifications().getFpVerificationList().get(0);
										if (fpVerification.getFingerprintData()==null) {
											log("ERROR: not fingerprint Data at all."+nicTransactionLog.getLogId());
										}
									}
								}
							}
						}
					} catch (Exception e) {
						log("[FPIssuanceEnquiryController].txnEnquirySearch() - IssLog not exists, ID:"+nicTransactionLog.getRefId()+", STAGE:"+nicTransactionLog.getTransactionStage()+", LOG_ID:"+nicTransactionLog.getLogId());
					}
				}
			}
		} catch (TransactionServiceException e) {
			e.printStackTrace();
		}
		log("start transService - findAllTransactionLogsByRefId");
	}
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}
