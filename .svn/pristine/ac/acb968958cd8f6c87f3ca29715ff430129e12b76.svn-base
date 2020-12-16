package com.nec.asia.nic.dx.ws.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nec.asia.idserver.agent.payload.model.AbstractBiometricData;
import com.nec.asia.idserver.agent.payload.model.IdserverImageFingerprint;
import com.nec.asia.idserver.agent.payload.model.result.IdserverMatchResult;
import com.nec.asia.idserver.agent.payload.model.result.IdserverScore;
import com.nec.asia.idserver.agent.payload.model.result.IdserverSubject;
import com.nec.asia.idserver.agent.payload.model.type.IdserverImageType;
import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.idserver.agent.service.exception.IdserverAgentServiceException;
//import com.nec.asia.nic.comp.trans.domain.NicMain;
//import com.nec.asia.nic.comp.trans.service.NicMainService;
//import com.nec.asia.nic.comp.trans.service.exception.NicMainServiceException;
import com.nec.asia.nic.dx.common.FaultDetail;
import com.nec.asia.nic.dx.idserver.ImageFingerprintDto;
import com.nec.asia.nic.dx.idserver.MatchResultDto;
import com.nec.asia.nic.dx.idserver.ScoreDto;
import com.nec.asia.nic.dx.idserver.SubjectDto;
import com.nec.asia.nic.dx.ws.Constant;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.IdserverWS;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
/* 
 * Modification History:
 *  
 * 07 Nov 2013 (chris): add logging
 * 21 Jul 2014 (chris): add audit access log.
 * 26 Dec 2015 (chris): TODO replace nicID with afisKeyNo
 */
public class IdserverWSImpl implements IdserverWS  {
	
	protected static final Logger logger = LoggerFactory.getLogger(IdserverWSImpl.class);
	private final String serviceName = "IdserverWS"; //21 Jul 2014 (chris)
	
	@Autowired
	private IdserverAgentService idserverAgentService;
	
//	@Autowired
//	private NicMainService nicMainService;
	
	@Autowired
	AuditAccessLogService auditAccessLogService; //21 Jul 2014 (chris)
	
	@Override
	public MatchResultDto identification(List<ImageFingerprintDto> fingerprints)
			throws FaultException {
		MatchResultDto matchResultDto = null;
		logger.info("IdserverWSImpl.identification(): begin");
		//21 Jul 2014 (chris) - start
		Date startTime = new Date();
		Object[] inArgs = null;
		Object[] outResponses = null; 
		Throwable throwable = null;
		//21 Jul 2014 (chris) - end
		if (CollectionUtils.isEmpty(fingerprints)) 
			throw new FaultException("List of fingerprint image cannot be empty.");
		
		String transactionId = null;
		List<AbstractBiometricData<IdserverImageFingerprint>> biometricData = new ArrayList<AbstractBiometricData<IdserverImageFingerprint>> ();
		for (ImageFingerprintDto imgFingerprintDto : fingerprints) {
			IdserverImageFingerprint fingerprint = new IdserverImageFingerprint();
			fingerprint.setFingerPosition(imgFingerprintDto.getFingerPosition());
			fingerprint.setImageType( IdserverImageType.valueOf(imgFingerprintDto.getImageType().value()));
			fingerprint.setImageData(imgFingerprintDto.getImageData());	
			biometricData.add(fingerprint);
		}
		
		try {
			IdserverMatchResult matchResult = idserverAgentService.inquiryFpScreening(transactionId, biometricData);
			if (matchResult!=null) {
				matchResultDto = new MatchResultDto();
				SubjectDto subjectDto = null;
				if (CollectionUtils.isNotEmpty(matchResult.getSubjectList())) {
					matchResultDto.setHasHit(true);
					for(IdserverSubject subject : matchResult.getSubjectList()){
						subjectDto = this.getSubjectDto(subject);
						subjectDto.getScoreList().addAll(this.getScoreList(subject.getScoreList()));						
						matchResultDto.getSubjectList().add(subjectDto);				
					}					
				}
			}			
		}catch (IdserverAgentServiceException ex) {
			throwable = ex; //21 Jul 2014 (chris)
			throw new FaultException("Error Occured.caused:" + ex.getMessage(), ex);
//		}catch (NicMainServiceException mae) {
//			throwable = mae; //21 Jul 2014 (chris)
//			throw new FaultException("Error Occured.caused:" + mae.getMessage(), mae);			
		}
		//21 Jul 2014 (chris) - start
		finally {
			//to reduce the log data size in webservice.
			for (ImageFingerprintDto imgFingerprintDto : fingerprints) {
				imgFingerprintDto.setImageData(null);
			}
			
			inArgs = new Object[] { fingerprints };
			outResponses = new Object[] { matchResultDto };
			Date endTime = new Date();
			long timeMs = endTime.getTime()-startTime.getTime();
			try {
				auditAccessLogService.saveAuditAccessLogForWS(serviceName, "identification", Constant.SYSTEM_NIC, Constant.SYSTEM_NIC, inArgs, outResponses, throwable, timeMs);
			} catch (Exception e) {
				logger.error("[identification] unable to create audit access log: {}", e.getMessage());
			}
		}
		//21 Jul 2014 (chris) - end
		logger.info("IdserverWSImpl.identification(): end");
		return matchResultDto;
	}
	
	
	@Override
	public SubjectDto verification(String transactionId,String nin
			, List<ImageFingerprintDto> fingerprints)
			throws FaultException {
		SubjectDto subjectDto = null;
		logger.info("IdserverWSImpl.verification(): begin");
		//21 Jul 2014 (chris) - start
		Date startTime = new Date();
		Object[] inArgs = new Object[] { transactionId, nin };
		Object[] outResponses = null; 
		Throwable throwable = null;
		//21 Jul 2014 (chris) - end
		List<AbstractBiometricData<IdserverImageFingerprint>> biometricData = new ArrayList<AbstractBiometricData<IdserverImageFingerprint>> ();
		for (ImageFingerprintDto imgFingerprintDto : fingerprints) {
			IdserverImageFingerprint fingerprint = new IdserverImageFingerprint();
			fingerprint.setFingerPosition(imgFingerprintDto.getFingerPosition());
			fingerprint.setImageType( IdserverImageType.valueOf(imgFingerprintDto.getImageType().value()));
			fingerprint.setImageData(imgFingerprintDto.getImageData());	
			biometricData.add(fingerprint);
		}
		
		try {
			//TO DO
			//need to translate NIN into Person Identifier
//			List<NicMain> nicMainList = this.nicMainService.findAllByNin(nin);
			String nicId = null;

//			if (CollectionUtils.isEmpty(nicMainList)) {
//				FaultDetail faultDetail = new FaultDetail();
//				faultDetail.setCode(Constant.ERROR_CODE_NIN_NOT_EXISTS);
//				faultDetail.setDetailMessage("This NIN:"+nin+" is not registered yet into NIC DB.");
//				throw new FaultException("Invalid NIN="+ nin +". it doesn't exists in NIC DB."); 
//			}
//			
//			for (NicMain data : nicMainList) {
//				nicId = String.valueOf(data.getNicId());
//				break;
//			}
			
			IdserverSubject subject = idserverAgentService.inquiryFpVerification(biometricData,transactionId, nicId);
			
			subjectDto = this.getSubjectDto(subject);			
			subjectDto.getScoreList().addAll(this.getScoreList(subject.getScoreList()));
			logger.info("IdserverWSImpl.verification(): end");
			return subjectDto;
		}catch (IdserverAgentServiceException ex) {
			throwable = ex; //21 Jul 2014 (chris)
			throw new FaultException("Error Occured.caused:" + ex.getMessage(), ex);
//		}catch (NicMainServiceException nmex) {
//			throwable = nmex; //21 Jul 2014 (chris)
//			throw new FaultException("Error Occured.caused:" + nmex.getMessage(), nmex);			
//		}catch (FaultException fe) {
//			throwable = fe; //22 Jul 2014 (chris)
//			throw fe;	
		}
		//21 Jul 2014 (chris) - start
		finally {
			outResponses = new Object[] { subjectDto };
			Date endTime = new Date();
			long timeMs = endTime.getTime()-startTime.getTime();
			try {
				auditAccessLogService.saveAuditAccessLogForWS(serviceName, "verification", Constant.SYSTEM_NIC, Constant.SYSTEM_NIC, inArgs, outResponses, throwable, timeMs);
			} catch (Exception e) {
				logger.error("[verification] unable to create audit access log: {}", e.getMessage());
			}
		}
		//21 Jul 2014 (chris) - end
	}
	
	
	
	private SubjectDto getSubjectDto (IdserverSubject subject) /*throws NicMainServiceException*/ {
		if (subject==null)
			return null;
		
		SubjectDto subjectDto = new SubjectDto();
		subjectDto.setDataSource(subject.getDataSource());
		subjectDto.setHitDecision(subject.getHitDecision());
		subjectDto.setNicId(subject.getPersonIdentifier());
		subjectDto.setTransactionId(subject.getRegistrationId());
//		NicMain nicMain = this.nicMainService.findByTransactionId(subject.getRegistrationId());  //findById is personIdentifier
//		if (nicMain!=null)
//			subjectDto.setNin(nicMain.getNin());  //need to do translation from person identifer to NIN

		return subjectDto;
	}
	
	
	private List<ScoreDto> getScoreList (List<IdserverScore> idsvrScoreList) {
		List<ScoreDto> resultList = new ArrayList<ScoreDto> ();
		
		if (CollectionUtils.isEmpty(idsvrScoreList))
			return null;
		
		ScoreDto scoreDto = new ScoreDto();
		for (IdserverScore idsvrScore : idsvrScoreList ) {
			scoreDto = new ScoreDto();
			scoreDto.setFingerPosition(idsvrScore.getFingerPosition());
			scoreDto.setScore(idsvrScore.getScore());
			resultList.add(scoreDto);
		}
		
		return resultList;
	}

	/**
	 * @param idserverAgentService the idserverAgentService to set
	 */
	public void setIdserverAgentService(IdserverAgentService idserverAgentService) {
		this.idserverAgentService = idserverAgentService;
	}


//	/**
//	 * @param nicMainService the nicMainService to set
//	 */
//	public void setNicMainService(NicMainService nicMainService) {
//		this.nicMainService = nicMainService;
//	}


	
	
	
}
