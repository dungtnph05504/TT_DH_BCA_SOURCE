package com.nec.asia.nix.dx.ws.client;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.dx.trans.FacialImage;
import com.nec.asia.nic.dx.trans.FacialInfo;
import com.nec.asia.nic.dx.trans.Fingerprint;
import com.nec.asia.nic.dx.trans.FingerprintInfo;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.trans.PersonDetail;
import com.nec.asia.nic.dx.trans.ReferenceDocument;
import com.nec.asia.nic.dx.trans.RegistrationData;
import com.nec.asia.nic.dx.trans.TransactionLog;
import com.nec.asia.nic.dx.trans.TransactionRetrievalDataType;
import com.nec.asia.nic.dx.trans.TransactionRetrievalFilter;
import com.nec.asia.nic.dx.trans.TransactionRetrievalRecordType;
import com.nec.asia.nic.dx.trans.TransactionRetrievalResult;
import com.nec.asia.nic.dx.ws.TransactionWS;

import org.junit.Assert;
import junit.framework.TestCase;

public class TestTransWSClient extends TestCase {
	public static ApplicationContext context = null;
	public static TransactionWS bean = null;
	
	public TestTransWSClient() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context-test.xml");
			bean = context.getBean("transactionWS", TransactionWS.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void xtestUploadTransaction() {
		log("start uploadTransaction");		
	
		try {
			MainTransaction mainTransaction = this.prepareMainTransaction();
			log("mainTransaction [input]: "+mainTransaction+"\n"+ReflectionToStringBuilder.toString(mainTransaction, ToStringStyle.MULTI_LINE_STYLE));
			log("RegistrationData [input]: "+mainTransaction.getRegistrationData()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData(), ToStringStyle.MULTI_LINE_STYLE));
			log("PersonDetail [input]: "+mainTransaction.getRegistrationData().getPersonDetail()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData().getPersonDetail(), ToStringStyle.MULTI_LINE_STYLE));
			log("FacialInfo [input]: "+mainTransaction.getRegistrationData().getFacialInfo()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData().getFacialInfo(), ToStringStyle.MULTI_LINE_STYLE));
			log("FingerprintInfo [input]: "+mainTransaction.getRegistrationData().getFingerprintInfo()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData().getFingerprintInfo(), ToStringStyle.MULTI_LINE_STYLE));
			log("Signature  [input]: "+mainTransaction.getRegistrationData().getSignatureInfo()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData().getSignatureInfo(), ToStringStyle.MULTI_LINE_STYLE));
			log("ReferenceDocuments [input]: "+mainTransaction.getRegistrationData().getReferenceDocuments()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData().getReferenceDocuments(), ToStringStyle.MULTI_LINE_STYLE));
			log("TransactionLogs [input]: "+mainTransaction.getTransactionLogs()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getTransactionLogs(), ToStringStyle.MULTI_LINE_STYLE));
			String result = bean.uploadTransaction(mainTransaction);
			if (StringUtils.equals("success", result))  {
				log("uploadTransaction is succeed. - "+mainTransaction.getTransactionID()); 
			} else {
				Assert.fail("Upload is Failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		log(" end  uploadTransaction");
	}
	
	public void testRetrieveTransaction() {
		log("start retrieveTransaction");		
	
		try {
			String transactionId = "RICHQ-2017-000213";
			String documentNo = "V0000036A";
			TransactionRetrievalFilter input = new TransactionRetrievalFilter();
			input.setTransactionID(transactionId);
			//input.setPassportNo(documentNo);
			input.setRecordType(TransactionRetrievalRecordType.ALL);
			input.getDataType().add(TransactionRetrievalDataType.ALL);
			TransactionRetrievalResult output = bean.retrieveTransaction(input);
			log("TransactionRetrievalResult: "+output+"\n"+ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
			for (MainTransaction mainTransaction : output.getTransactions()) {
				log("mainTransaction [input]: "+mainTransaction+"\n"+ReflectionToStringBuilder.toString(mainTransaction, ToStringStyle.MULTI_LINE_STYLE));
				log("RegistrationData [input]: "+mainTransaction.getRegistrationData()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData(), ToStringStyle.MULTI_LINE_STYLE));
				log("FacialInfo [input]: "+mainTransaction.getRegistrationData().getFacialInfo()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData().getFacialInfo(), ToStringStyle.MULTI_LINE_STYLE));
				log("PersonDetail [input]: "+mainTransaction.getRegistrationData().getPersonDetail()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData().getPersonDetail(), ToStringStyle.MULTI_LINE_STYLE));
				log("FingerprintInfo [input]: "+mainTransaction.getRegistrationData().getFingerprintInfo()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData().getFingerprintInfo(), ToStringStyle.MULTI_LINE_STYLE));
				log("Signature  [input]: "+mainTransaction.getRegistrationData().getSignatureInfo()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData().getSignatureInfo(), ToStringStyle.MULTI_LINE_STYLE));
				log("ReferenceDocuments [input]: "+mainTransaction.getRegistrationData().getReferenceDocuments()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getRegistrationData().getReferenceDocuments(), ToStringStyle.MULTI_LINE_STYLE));
				log("TransactionLogs [input]: "+mainTransaction.getTransactionLogs()+"\n"+ReflectionToStringBuilder.toString(mainTransaction.getTransactionLogs(), ToStringStyle.MULTI_LINE_STYLE));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		log(" end  retrieveTransaction");
	}
	
	private MainTransaction prepareMainTransaction() {
		String transactionId = "THT_12345";
		String transactionType = "REG";
		String transactionStatus = "RIC-UPLOADING";
		String firstName = "THANG";
		String lastName = "VE";
		String middleName = "";
		
		
		MainTransaction mainTransaction = new MainTransaction();
		try {
			mainTransaction.setTransactionID(transactionId);
			mainTransaction.setDateOfApplication(DateUtils.parseDate("21/05/2017", new String[] {"dd/MM/yyyy"}));
			log("DOA: "+mainTransaction.getDateOfApplication());
			mainTransaction.setCreateBy("USER1");
			mainTransaction.setCreateDateTime(new Date());
			mainTransaction.setEstDateOfCollection(DateUtils.parseDate("27/06/2017", new String[] {"dd/MM/yyyy"}));
			mainTransaction.setIssSiteCode("RICHQ");
			mainTransaction.setRegSiteCode("RICHQ");
			mainTransaction.setIssuingAuthority("DFA HANOI");
			mainTransaction.setPassportType("P");
			mainTransaction.setPriority(NicUploadJob.JOB_PRIORITY_EXPRESS);

			mainTransaction.setTransactionStatus(transactionStatus);
			mainTransaction.setTransactionType(transactionType);
			//mainTransaction.setPrevDateOfIssue(DateUtils.parseDate("18/12/2015", new String[] {"dd/MM/yyyy"}));
			//mainTransaction.setPrevPassportNo("A00000006");
			mainTransaction.setUpdateBy("USER1");
			mainTransaction.setUpdateDateTime(new Date());
			mainTransaction.setValidatyPeriod(5);
			RegistrationData registrationData = new RegistrationData();
			registrationData.setTransactionID(transactionId);
			registrationData.setAddressLine1("Stanley Street");
			registrationData.setAddressLine2("Vietnam");
			//registrationData.setContactNo("");
			//registrationData.setEmail("");
			
			registrationData.setPersonDetail(new PersonDetail());
			PersonDetail person = registrationData.getPersonDetail();
			person.setDateOfBirth(DateUtils.parseDate("30/06/1960T12:00:00", new String[] {"dd/MM/yyyy'T'HH:mm:ss"}));
			person.setPrintDOB("19600630");
			person.setFatherCitizenship("VNM");
			person.setFatherFirstname("NGUYEN");
			person.setFatherSurname(lastName);
			person.setFirstnameFull(firstName);
			person.setMiddlenameFull(middleName);
			person.setSurnameFull(lastName);
			person.setFirstnameLenExceedFlag("N");
			person.setMiddlenameLenExceedFlag("N");
			person.setSurnameLenExceedFlag("N");
			person.setGender("M");
			person.setMaritalStatus("S");
			
			person.setMotherCitizenship("VNM");
			person.setMotherFirstname("VUOT");
			person.setMotherMiddlename("");
			person.setMotherSurname(lastName);
			person.setNationality("VNM");
			person.setPlaceOfBirth("USA");
			
			//person.setSpouseCitizenship("SGP");
			//person.setSpouseFirstname("WHITE");
			//person.setSpouseMiddlename("");
			//person.setSpouseSurname(lastName);
			
			
			/** photo */
			registrationData.setFacialIndicator("N");
			registrationData.setFacialInfo(new FacialInfo());
			FacialImage facialImageCapture = new FacialImage();
			facialImageCapture.setDocType("PH_CAP");
			facialImageCapture.setFacialData(this.loadImage("PH_CAP.jpg"));
			registrationData.getFacialInfo().getFacialImages().add(facialImageCapture);
			FacialImage facialImageChip = new FacialImage();
			facialImageChip.setDocType("PH_CHIP");
			facialImageChip.setFacialData(this.loadImage("PH_CHIP.jp2"));
			registrationData.getFacialInfo().getFacialImages().add(facialImageChip);
			
			/** fingerprint */
			registrationData.setFingerprintInfo(new FingerprintInfo());
			Fingerprint fingerprint01 = new Fingerprint();
			fingerprint01.setFingerprintPosition("01");
			fingerprint01.setFingerprintData(this.loadImage("FP_01.wsq"));
			registrationData.getFingerprintInfo().getFingerprints().add(fingerprint01);
			Fingerprint fingerprint02 = new Fingerprint();
			fingerprint02.setFingerprintPosition("02");
			fingerprint02.setFingerprintData(this.loadImage("FP_02.wsq"));
			registrationData.getFingerprintInfo().getFingerprints().add(fingerprint02);
			Fingerprint fingerprint06 = new Fingerprint();
			fingerprint06.setFingerprintPosition("06");
			fingerprint06.setFingerprintData(this.loadImage("FP_06.wsq"));
			registrationData.getFingerprintInfo().getFingerprints().add(fingerprint06);
			Fingerprint fingerprint07 = new Fingerprint();
			fingerprint07.setFingerprintPosition("07");
			fingerprint07.setFingerprintData(this.loadImage("FP_07.wsq"));
			registrationData.getFingerprintInfo().getFingerprints().add(fingerprint07);
			registrationData.getFingerprintInfo().setCmlafTemplate(this.loadImage("TPL.xml"));
			
			registrationData.setFpByPassDecision("N");
			//registrationData.setFpByPassDateTime(null);
			//registrationData.setFpByPassBy("");
			registrationData.setTotalFp(4);
			registrationData.setFpEncode("");
			registrationData.setFpIndicator("1-N,2-N,3-B,4-B,5-B,6-N,7-N,8-B,9-B,10-B");
			//registrationData.setFpPattern("W,W,W,W");
			registrationData.setFpQuality("1-63,2-58,3-0,4-0,5-0,6-85,7-73,8-0,9-0,10-0");

			registrationData.setPrintRemark1("");
			registrationData.setPrintRemark2("");
			//registrationData.setTravelPurpose("");
			//registrationData.setForeignPassportHolder("");
			//registrationData.setAcquiredCitizenship("");
			registrationData.setOccupationDesc("SOFTWARE ENGINEER");
			registrationData.setOfficeAddress("");
			registrationData.setOfficeContactNo("");
			
			/** signature */
			registrationData.setSignatureFlag("N");
//			registrationData.setSignatureInfo(new SignatureInfo());
//			Signature signature = new Signature();
//			signature.setDocType("SIGNATURE");
//			signature.setSignatureData(this.loadImage("SIGNATURE.jpg"));
//			registrationData.getSignatureInfo().getSignatures().add(signature);
			
			
			/** scanned document 
			PH	Photo
			AF	Application form
			BC	Birth certificate
			IS	Issuance signature
			AFF	Affidavit of Loss
			ID	Identification
			MC	Marriage Certificate
			NBI	National Bureau of Investigation
			OTH	Other Type of Document
			OPPT	Photocopy of Old passport
			*/
			ReferenceDocument scBCert = new ReferenceDocument();
//			scBCert.setDocType("BC");
//			scBCert.setSerialNo("01");
//			scBCert.setDocData(this.loadImage("BC.jpg"));
			ReferenceDocument scMCert = new ReferenceDocument();
//			scMCert.setDocType("MC");
//			scMCert.setSerialNo("01");
//			scMCert.setDocData(this.loadImage("MC.jpg"));
			ReferenceDocument scID = new ReferenceDocument();
//			scID.setDocType("ID");
//			scID.setSerialNo("01");
//			scID.setDocData(this.loadImage("IDCard.jp2"));
			if (scBCert.getDocData()!=null)
				registrationData.getReferenceDocuments().add(scBCert);
			if (scMCert.getDocData()!=null)
				registrationData.getReferenceDocuments().add(scMCert);
			if (scID.getDocData()!=null)
				registrationData.getReferenceDocuments().add(scID);
			
			/** audit transaction log */
			TransactionLog dcmTransLog = new TransactionLog();
			dcmTransLog.setOfficerID("RIC_SYS");
			//dcmTransLog.setWkstnID("");
			dcmTransLog.setSiteCode("004");
			dcmTransLog.setTransactionID(transactionId);
			dcmTransLog.setStartTime(DateUtils.parseDate("12/01/2016T09:34:22", new String[] {"dd/MM/yyyy'T'HH:mm:ss"}));
			dcmTransLog.setEndTime(DateUtils.parseDate("12/01/2016T11:55:15", new String[] {"dd/MM/yyyy'T'HH:mm:ss"}));
			dcmTransLog.setTransactionStage("DCM");
			dcmTransLog.setTransactionStatus("CA");
			
			TransactionLog dcmTransLog2 = new TransactionLog();
			dcmTransLog2.setOfficerID("RIC_SYS");
			dcmTransLog2.setSiteCode("004");
			dcmTransLog2.setTransactionID(transactionId);
			dcmTransLog2.setStartTime(DateUtils.parseDate("12/01/2016T11:56:00", new String[] {"dd/MM/yyyy'T'HH:mm:ss"}));
			dcmTransLog2.setEndTime(DateUtils.parseDate("12/01/2016T11:56:01", new String[] {"dd/MM/yyyy'T'HH:mm:ss"}));
			dcmTransLog2.setTransactionStage("DCM");
			dcmTransLog2.setTransactionStatus("SI");
			
			TransactionLog stagingTransLog = new TransactionLog();
			stagingTransLog.setOfficerID("STAGING_SYS");
			stagingTransLog.setSiteCode("001");
			stagingTransLog.setTransactionID(transactionId);
			stagingTransLog.setStartTime(DateUtils.parseDate("12/01/2016T12:08:00", new String[] {"dd/MM/yyyy'T'HH:mm:ss"}));
			stagingTransLog.setEndTime(DateUtils.parseDate("12/01/2016T12:08:02", new String[] {"dd/MM/yyyy'T'HH:mm:ss"}));
			stagingTransLog.setTransactionStage("STAGING");
			stagingTransLog.setTransactionStatus("RV");
			
			mainTransaction.setRegistrationData(registrationData);			
			mainTransaction.getTransactionLogs().add(dcmTransLog);
			mainTransaction.getTransactionLogs().add(dcmTransLog2);
			mainTransaction.getTransactionLogs().add(stagingTransLog);
			//mainTransaction.setIssuanceData(issuanceData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mainTransaction;
	}
	
	private String rootFolder = "E:/WORK/EPP/Code/NIC/nic-parent/nic-ws/src/test/resources/candidate1/";
	private byte[] loadImage(String simpleFileName){
		try {
			String fullFileName = rootFolder + simpleFileName;
			byte[] data = FileUtils.readFileToByteArray(new File(fullFileName));
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
}
