package com.nec.asia.nic.test.biometric;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.idserver.agent.payload.model.AbstractBiometricData;
import com.nec.asia.idserver.agent.payload.model.IdserverImageFingerprint;
import com.nec.asia.idserver.agent.payload.model.result.IdserverMatchResult;
import com.nec.asia.idserver.agent.payload.model.result.IdserverScore;
import com.nec.asia.idserver.agent.payload.model.result.IdserverSubject;
import com.nec.asia.idserver.agent.payload.model.type.IdserverImageType;
import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.idserver.agent.util.FingerPosition;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

public class TestInquiryFingerprint extends TestBase {
	protected static final Logger logger = LoggerFactory.getLogger(TestInquiryFingerprint.class);
	
	//M1 1:N by transaction Id
	public void testInquiryByIdentifier() {
		System.err.println("inquiryFpScreeningByGivenIdentifier() start.");
		logger.warn("In inquiryFpScreeningByGivenIdentifier() ----------------------------------");
		try {
			IdserverAgentService idsvrAgentService = (IdserverAgentService) SpringServiceManager.getBean("idserverAgentService");
			String transactionId = "004-2016-000003";
			IdserverMatchResult matchResult = idsvrAgentService.inquiryFpScreeningByGivenIdentifier(transactionId);
			
			for(IdserverSubject subject : matchResult.getSubjectList()){
				String hitInfo = "";
				for (IdserverScore score : subject.getScoreList()) {
					if (StringUtils.isNotBlank(hitInfo)) {
						hitInfo += ",";
					}
					hitInfo += score.getFingerPosition() + "=" + score.getScore();
				}
				logger.info("RegistrationId = {}; PersonIdentifier = {}; DataSource = {}, HitDecision = {}, HitInfo = {}",
						new Object[]{
							subject.getRegistrationId(),
							subject.getPersonIdentifier(),
							subject.getDataSource(),
							subject.getHitDecision(),
							hitInfo 
							}
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
        logger.warn("End of inquiryFpScreeningByGivenIdentifier() ----------------------------------");
        System.err.println("inquiryFpScreeningByGivenIdentifier() end.");
	}
	
	//M2 1:N by WSQ
	public void xtestInquiryFpScreening () {
		System.err.println("testInquiryFpScreening() start.");
		logger.warn("In testInquiryFpScreening() ----------------------------------");
		try {
			String transactionId = "REGISTRATION_ID1";
			IdserverAgentService idsvrAgentService = (IdserverAgentService )SpringServiceManager.getBean("idserverAgentService");
	        List<AbstractBiometricData<IdserverImageFingerprint>> biometricData = new ArrayList<AbstractBiometricData<IdserverImageFingerprint>>();
	        String setNo = "set1/";
	        this.buildMatchFingerprints(biometricData,setNo);		
	        IdserverMatchResult matchResult = idsvrAgentService.inquiryFpScreening(transactionId, biometricData);
	        
	        for(IdserverSubject subject : matchResult.getSubjectList()){
				logger.info("RegistrationId = {}; PersonIdentifier = {}; DataSource = {}, HitDecision = {}, Scores = {}",
						new Object[]{
							subject.getRegistrationId(),
							subject.getPersonIdentifier(),
							subject.getDataSource(),
							subject.getHitDecision(),
							ReflectionToStringBuilder.toString(subject.getScoreList()) 
							}
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
        logger.warn("End of testInquiryFpScreening() ----------------------------------");
        System.err.println("testInquiryFpScreening() end.");
    }
	 
	private void buildMatchFingerprints (List<AbstractBiometricData<IdserverImageFingerprint>> biometricData, String setNo) throws Exception {
    	//fingerprint: RightThumb
		IdserverImageFingerprint fingerprint1 = new IdserverImageFingerprint();
		fingerprint1.setFingerPosition(FingerPosition.RightThumb.getKey());
		fingerprint1.setImageType(IdserverImageType.WSQ);
		fingerprint1.setImageData(loadFingerprintImage(setNo + "1"));	
		
		biometricData.add(fingerprint1);
		
		//fingerprint: RightIndex
		IdserverImageFingerprint fingerprint2 = new IdserverImageFingerprint();
		fingerprint2.setFingerPosition(FingerPosition.RightIndex.getKey());
		fingerprint2.setImageType(IdserverImageType.WSQ);
		fingerprint2.setImageData(loadFingerprintImage(setNo + "2"));		
		biometricData.add(fingerprint2);
		
		//fingerprint: LeftThumb
		IdserverImageFingerprint fingerprint6 = new IdserverImageFingerprint();
		fingerprint6.setFingerPosition(FingerPosition.LeftThumb.getKey());
		fingerprint6.setImageType(IdserverImageType.WSQ);
		fingerprint6.setImageData(loadFingerprintImage(setNo + "6"));		
		biometricData.add(fingerprint6);
		
		//fingerprint: LeftIndex
		IdserverImageFingerprint fingerprint7 = new IdserverImageFingerprint();
		fingerprint7.setFingerPosition(FingerPosition.LeftIndex.getKey());
		fingerprint7.setImageType(IdserverImageType.WSQ);
		fingerprint7.setImageData(loadFingerprintImage(setNo + "7"));		
		biometricData.add(fingerprint7);
	}

	//M3 1:N async
	public void xtestAsyncInquiryByTpl() {
		System.err.println("inquiryFpScreeningAsync() start.");
		logger.warn("In inquiryFpScreeningAsync() ----------------------------------");
		String setNo = "set4/";
		
		List<AbstractBiometricData<IdserverImageFingerprint>> inputList = new ArrayList<AbstractBiometricData<IdserverImageFingerprint>>();
		IdserverImageFingerprint tplData = new IdserverImageFingerprint();
		tplData.setFingerPosition((short) 0);
		tplData.setImageType(IdserverImageType.TPL);
		tplData.setImageData(loadFingerprintTemplate(setNo + "99"));
		inputList.add(tplData );
		try {
			IdserverAgentService idserverAgentService = (IdserverAgentService )SpringServiceManager.getBean("idserverAgentService");
			String transactionId = "REGISTRATION_ID1";
			String receiptNo = idserverAgentService.inquiryFpScreeningAsync(transactionId, inputList);
			System.err.println("receiptNo: "+receiptNo);
			logger.warn("receiptNo: "+receiptNo);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		System.err.println("inquiryFpScreeningAsync() end.");
		logger.warn("Complete In inquiryFpScreeningAsync() ----------------------------------");
	}
	
	//M4
	public void xtestGetAsyncResult() {
		String receiptNo = "408";
		
		System.err.println("inquiryFpScreeningAsync() start.");
		logger.warn("In inquiryFpScreeningAsync() ----------------------------------");
		try {
			IdserverAgentService idserverAgentService = (IdserverAgentService )SpringServiceManager.getBean("idserverAgentService");
			IdserverMatchResult hitResult = idserverAgentService.retrieveHitResult(receiptNo);
			System.err.println("IdserverMatchResult: "+hitResult);
			if (hitResult!=null) {
				for (IdserverSubject subject: hitResult.getSubjectList()) {
					logger.info("RegistrationId = {}; PersonIdentifier = {}; DataSource = {}, HitDecision = {}, Scores = {}",
							new Object[]{
								subject.getRegistrationId(),
								subject.getPersonIdentifier(),
								subject.getDataSource(),
								subject.getHitDecision(),
								ReflectionToStringBuilder.toString(subject.getScoreList()) 
								}
					);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		System.err.println("inquiryFpScreeningAsync() end.");
		logger.warn("Complete In inquiryFpScreeningAsync() ----------------------------------");
	}
}
