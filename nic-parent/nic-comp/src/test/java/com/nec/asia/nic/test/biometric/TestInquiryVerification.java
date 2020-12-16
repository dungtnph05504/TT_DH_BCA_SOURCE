package com.nec.asia.nic.test.biometric;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.idserver.agent.payload.model.AbstractBiometricData;
import com.nec.asia.idserver.agent.payload.model.IdserverImageFingerprint;
import com.nec.asia.idserver.agent.payload.model.result.IdserverScore;
import com.nec.asia.idserver.agent.payload.model.result.IdserverSubject;
import com.nec.asia.idserver.agent.payload.model.type.IdserverImageType;
import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.idserver.agent.service.exception.IdserverAgentServiceException;
import com.nec.asia.idserver.agent.util.FingerPosition;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

public class TestInquiryVerification extends TestBase {
    protected static final Logger logger = LoggerFactory.getLogger(TestInquiryVerification.class);
    
    //IMAGE to Key
    public void xtestInquiryFpVerification () throws Exception {
            String registrationId = "REGISTRATION_ID1";
            String personId = "PERSON_ID1";
            IdserverAgentService idsvrAgentService = (IdserverAgentService )SpringServiceManager.getBean("idserverAgentService");
            List<AbstractBiometricData<IdserverImageFingerprint>> biometricData = new ArrayList<AbstractBiometricData<IdserverImageFingerprint>>();
            String setNo = "set1/";
            this.buildMatchFingerprints(biometricData,setNo);        
            IdserverSubject subject = idsvrAgentService.inquiryFpVerification(biometricData,registrationId, personId);
            logger.info("RegistrationId = {}; PersonIdentifier = {}; DataSource = {}, HitDecision = {}, Scores = {}",
                    new Object[]{
                        subject.getRegistrationId(),
                        subject.getPersonIdentifier(),
                        subject.getDataSource(),
                        subject.getHitDecision(),
                        subject.getScoreList()
                        }
            );
    
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

    //TPL to TPL
    public void xtestInquiryComparision() {
        
        String setNo = "set4/";
        String transactionId = "T1";
        IdserverImageFingerprint fingerprint1 = null;
        IdserverImageFingerprint fingerprint2 = null;
        
        fingerprint1 = new IdserverImageFingerprint();
        fingerprint1.setFingerPosition(NicTransactionAttachment.TPL_POSITION);
        fingerprint1.setImageType(IdserverImageType.TPL);
        fingerprint1.setImageData(loadFingerprintTemplate(setNo + "99"));    
        
        fingerprint2 = new IdserverImageFingerprint();
        fingerprint2.setFingerPosition(NicTransactionAttachment.TPL_POSITION);
        fingerprint2.setImageType(IdserverImageType.TPL);
        fingerprint2.setImageData(loadFingerprintTemplate(setNo + "99"));    
        
        IdserverAgentService idserverAgentService = (IdserverAgentService )SpringServiceManager.getBean("idserverAgentService");
        try {
            IdserverSubject subject = idserverAgentService.inquiryFpComparison(transactionId, fingerprint1, fingerprint2);
            String hitInfo = "";
            for (IdserverScore score : subject.getScoreList()) {
                if (StringUtils.isNotBlank(hitInfo)) {
                    hitInfo += ",";
                }
                hitInfo += score.getFingerPosition() + "=" + score.getScore();
            }
            logger.info("RegistrationId = {}; PersonIdentifier = {}; DataSource = {}, HitDecision = {}, Scores = {}",
                    new Object[]{
                        subject.getRegistrationId(),
                        subject.getPersonIdentifier(),
                        subject.getDataSource(),
                        subject.getHitDecision(),
                        hitInfo
                        }
            );
        } catch (IdserverAgentServiceException e) {
            e.printStackTrace();
        }
    }

    //TPL to Key
    public void testInquiryFpVerificationByTPL () throws Exception {
        String setNo = "set4/";
        String transactionId = "004-2016-000004";
        String afisKeyNo = "004-2016-000004";
        
        IdserverAgentService idsvrAgentService = (IdserverAgentService )SpringServiceManager.getBean("idserverAgentService");
        List<AbstractBiometricData<IdserverImageFingerprint>> biometricData = new ArrayList<AbstractBiometricData<IdserverImageFingerprint>>();
        IdserverImageFingerprint fingerprint1 = null;
        fingerprint1 = new IdserverImageFingerprint();
        fingerprint1.setFingerPosition(NicTransactionAttachment.TPL_POSITION);
        fingerprint1.setImageType(IdserverImageType.TPL);
        fingerprint1.setImageData(loadFingerprintImage(setNo + "99"));    
        biometricData.add(fingerprint1);
        
        logger.info("before inquiryFpVerification() using tpl");
        IdserverSubject subject = idsvrAgentService.inquiryFpVerification(biometricData, transactionId, afisKeyNo);
        logger.info("after inquiryFpVerification() using tpl");
        String hitInfo = "";
        for (IdserverScore score : subject.getScoreList()) {
            if (StringUtils.isNotBlank(hitInfo)) {
                hitInfo += ",";
            }
            hitInfo += score.getFingerPosition() + "=" + score.getScore();
        }
        logger.info("RegistrationId = {}; PersonIdentifier = {}; DataSource = {}, HitDecision = {}, Scores = {}",
                new Object[]{
                    subject.getRegistrationId(),
                    subject.getPersonIdentifier(),
                    subject.getDataSource(),
                    subject.getHitDecision(),
                    hitInfo
                    }
        );

}
}
