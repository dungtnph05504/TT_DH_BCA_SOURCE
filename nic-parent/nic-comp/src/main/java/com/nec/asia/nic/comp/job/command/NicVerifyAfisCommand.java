package com.nec.asia.nic.comp.job.command;

 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.nec.asia.idserver.agent.payload.model.AbstractBiometricData;
import com.nec.asia.idserver.agent.payload.model.IdserverImageFingerprint;
import com.nec.asia.idserver.agent.payload.model.result.IdserverMatchResult;
import com.nec.asia.idserver.agent.payload.model.result.IdserverScore;
import com.nec.asia.idserver.agent.payload.model.result.IdserverSubject;
import com.nec.asia.idserver.agent.payload.model.type.IdserverImageType;
import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.idserver.agent.service.exception.IdserverAgentServiceException;
import com.nec.asia.idserver.agent.util.FingerPosition;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.InquirySearchResultService;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.InquirySearchServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.util.MiscXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;


/* 
 * Modification History:
 *  
 * 13 FEB 2014 (jp): to save matchresult after 1:1
 * 19 MAY 2014 (jp): always set match result hashit to true to show in investigation
 * 05 JUN 2014 (jp): improved on buildfingerprints method. doctype comparison changed from substring(0, 2) to StringUtils.equalsignorecase
 * 12 JAN 2016 (chris): update for BAF upgrade, 1:1 using TPL
 * 22 FEB 2016 (chris): add logic for checking existing passport no for renewal. 
 * 21 MAR 2016 (chris): to use fingerprints instead of TPL if TPL doesn't exists.
 * 07 APR 2016 (chris): to set investigation reason as '07' when prev passport not declared, '10' when 1:1 not matching.
 * 18 APR 2016 (chris): [bugfix] to set reason, when hit_info is not empty.
 * 26 APR 2016 (chris): reset job reprocess count
 * 09 MAY 2016 (chris): [bugfix] to skip if FP ImageData is null.
 * 17 MAY 2016 (setia): to implement new logic to not flag to investigation module for MRP and green passport
 */
public class NicVerifyAfisCommand extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	//private static final Logger logger = Logger.getLogger(NicVerifyAfisCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	public static final int AFIS_VERIFY = 12;
	public static final int INVESTIGATION = 9;
	public static final int AFIS_DELETE = 10;
//	//JOB STATE
	public static final String JOB_STATE_AFIS_VERIFY = "AFIS_VERIFY";
//	public static final String NIC_REJECTED = "NIC_REJECTED";
//	public static final String PAR_UPD = "PAR_UPD";
	
	//parameters
	private static final String PARA_SCOPE_APPLICATION = "APPLICATION";
	private static final String PARA_NAME_AGE_FOR_FP_EXEMPTION = "AGE_FOR_FP_EXEMPTION";
	
	private static final String VERIFICATION_REJECTED_APPLICATION = "REJECTED APPLICATION=";
	private static final String CODE_VALUE_ID_NO_PASSPORT_FOUND = "05"; //05 Declared Passport Num. doesn't exists.
	private static final String CODE_VALUE_ID_NO_FP_FOUND_ON_PREV_PASSPORT = "06"; //06 No Fingerprint found by Declared Passport No
	private static final String CODE_VALUE_ID_NO_PASSPORT_DECLARED = "07"; //Previous Passport number not declared/given for renewal application.
	private static final String CODE_VALUE_ID_FP_VERIFICATION_FAILED = "10"; //Applicant fingerprint does not match the fingerprint in previous record declared/given in system (FP 1:1).
	
	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private InquirySearchResultService inquirySearchResultService;
	private TransactionLogService nicTransactionLogService;
	private IdserverAgentService idserverAgentService;
	
	private DocumentDataService documentDataService;
	private NicAfisDataService nicAfisDataService;
	private NicTransactionAttachmentService transactionAttachmentService;
	private ParametersService parametersService; 
	
	private boolean skipVerify = false;
	private String  rejectMessage = null;
	
	@Override
	public void doSomething(NicUploadJob job) {
		logger.info("inside NicVerifyAfisCommand:{}", job.getTransactionId());
		//for logging to transaction logs
		String logData = null;
		Date startTime = new Date();
		String transactionStatus = null;
		//
		this.setState(job.getJobType());//set job type as child key
		
		try {
			
			NicTransaction transObj = new NicTransaction();
			
			updateStatus(job.getWorkflowJobId(), JOB_STATE_AFIS_VERIFY, JOB_STATE);

			if(StringUtils.equals(job.getAfisVerifyStatus(), STATUS_COMPLETED) || StringUtils.equals(job.getAfisVerifyStatus(), STATUS_COMPLETED_WITH_HIT) || StringUtils.equals(job.getAfisVerifyStatus(), STATUS_NO_REQUIRED) ){
			
				//AFIS VERIFY has been completed before, skip step
				logger.info("-----[{}] skipping AFIS VERIFY, status: {}, continue to next step", job.getTransactionId(), job.getAfisVerifyStatus());
				
			} else {
				
				//logger.info("flag2 idserverAgentService:"+idserverAgentService);
				updateStatus(job.getWorkflowJobId(), STATUS_INPROGRESS, AFIS_VERIFY);
				
				transObj = nicTransactionService.findById(job.getTransactionId(), false, true, true, false);//has trans doc and registration data
				
				boolean autoApproved = afisVerification(transObj, job);
				if(autoApproved){
					if (skipVerify) {
						//if transaction is NEW then FP 1:1 is optional
						//otherwise FP 1:1 is mandatory
						if (StringUtils.equalsIgnoreCase(NicTransactionService.TRANSACTION_TYPE_REGISTRATION, job.getJobType())) {
							updateStatus(job.getWorkflowJobId(), STATUS_NO_REQUIRED, AFIS_VERIFY);
						} else {
							updateStatus(job.getWorkflowJobId(), STATUS_COMPLETED_WITH_HIT, AFIS_VERIFY);
							transactionStatus = JOB_STATE_AFIS_VERIFY+STAGE_COMPLETED_WITH_HIT;
							String afisKeyNo = "";
							NicAfisData afisData = nicAfisDataService.findById(job.getTransactionId());
							if (afisData != null)  {
								afisKeyNo = afisData.getAfisKeyNo();
							}
							//saveValidationResult(job.getWorkflowJobId(), job.getTransactionId(), afisKeyNo, "REJECTED APPLICATION= No Declared Passport No Found.");
							saveValidationResult(job.getWorkflowJobId(), job.getTransactionId(), afisKeyNo, rejectMessage);
						}
					} else {
						updateStatus(job.getWorkflowJobId(), STATUS_COMPLETED, AFIS_VERIFY);
					}
					transactionStatus = JOB_STATE_AFIS_VERIFY+STAGE_COMPLETED;
					
					uploadJobService.resetReprocessCount(job.getWorkflowJobId(), 0);
				}else{
					updateStatus(job.getWorkflowJobId(), STATUS_COMPLETED_WITH_HIT, AFIS_VERIFY);
					uploadJobService.resetReprocessCount(job.getWorkflowJobId(), 0);
					//updateStatus(job.getWorkflowJobId(), STATUS_INITIAL, INVESTIGATION);
					//updateStatus(job.getWorkflowJobId(), null, AFIS_DELETE);
					transactionStatus = JOB_STATE_AFIS_VERIFY+STAGE_COMPLETED_WITH_HIT;
					//this.setState(GOTO_ERROR_CMD);

					//02SEP2013 - JP - update nic_transaction.transaction_status field
					//transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION);
					//nicTransactionService.saveOrUpdate(transObj);
					
					//07 APR 2016 - add Investigation Reason for FP Verification Failed 
					rejectMessage = VERIFICATION_REJECTED_APPLICATION+CODE_VALUE_ID_FP_VERIFICATION_FAILED;
					String afisKeyNo = "";
					NicAfisData afisData = nicAfisDataService.findById(job.getTransactionId());
					if (afisData != null)  {
						afisKeyNo = afisData.getAfisKeyNo();
					}
					saveValidationResult(job.getWorkflowJobId(), job.getTransactionId(), afisKeyNo, rejectMessage);
				}
			}

		} catch (Exception e) {
			logger.error("flag EXCEPTION");
			e.printStackTrace();
			transactionStatus = JOB_STATE_AFIS_VERIFY+STAGE_ERROR;
			logData = MiscXmlConvertor.parseObjectToXml(e);
			this.setState(GOTO_ERROR_CMD);
			updateStatus(job.getWorkflowJobId(), STATUS_ERROR, AFIS_VERIFY);
			nicCommandUtil.setErrorFlag(job.getWorkflowJobId(), true, uploadJobService);
		}finally {
			try {
				if (StringUtils.isNotBlank(job.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
					uploadJobService.updateJobStatus(job.getWorkflowJobId(), transactionStatus);
					this.saveTransactionLog(job.getTransactionId(), JOB_STATE_AFIS_VERIFY, transactionStatus, startTime, new Date(), null, logData, job.getJobReprocessCount());
				}
			} catch (Exception e) {
				logger.error("Exception in finally block (NicVerifyAfisCommand.doSomething)", e);
			}
		}
	}

	private boolean afisVerification(NicTransaction transObj, NicUploadJob job) throws Exception{
		//transaction object has trans docs and reg data
		IdserverSubject idSvrSubj = null;
		boolean hasHit = false;
		
		try {
			String transactionId = "";
			String afisKeyNo = "";
			String prevTransId = "";
			String prevAfisKeyNo = "";
			boolean isArchived = false;
			boolean isPrevTranFPExempted = false;
			
			transactionId = transObj.getTransactionId();
			NicAfisData afisData = nicAfisDataService.findById(transactionId);
			if (afisData != null)  {
				afisKeyNo = afisData.getAfisKeyNo();
			}
			
			//1) to check if transaction have declared previous passport No.
			String oldPassportNo = transObj.getPrevPassportNo();
			boolean isPassportExempted = true;
			if (StringUtils.isNotEmpty(oldPassportNo)) {
				
				NicDocumentData documentData = documentDataService.findByDocNumber(oldPassportNo).getModel();
				if (documentData != null) {
					prevTransId = documentData.getId().getTransactionId();
					NicAfisData prevAfisData = nicAfisDataService.findById(prevTransId);
					if (prevAfisData != null)  {
						prevAfisKeyNo = prevAfisData.getAfisKeyNo();
						isArchived = !StringUtils.equals(NicAfisData.STATUS_INSERTED, prevAfisData.getStatus());
					}
					isPrevTranFPExempted = validateTransaction(prevTransId, documentData);
				}
				
			}
			
			if (StringUtils.isBlank(prevTransId)) {

				if (StringUtils.isNotEmpty(oldPassportNo)) {
					// to check if green passport or MRP passport, beside that will route to investigation
					isPassportExempted = this.validatePassportNo(oldPassportNo);
					
					if (isPassportExempted) {
						logger.debug("skip afisVerification, the transaction for passportNo:{}, is valid passport number (Exemption Category) .", oldPassportNo);
						skipVerify = false;
						return true;
					} else {
						logger.info("skip afisVerification, there is no declared transaction found for passportNo:"+oldPassportNo);
						skipVerify = true;
						rejectMessage = "REJECTED APPLICATION="+CODE_VALUE_ID_NO_PASSPORT_FOUND;						
					}
				} else {
					logger.info("skip afisVerification, there is no declared passportNo for transactionId:"+transactionId);
					skipVerify = true;
					rejectMessage = "REJECTED APPLICATION="+CODE_VALUE_ID_NO_PASSPORT_DECLARED;
				}
				return true;
			} else if (isPrevTranFPExempted) {
				logger.info("skip afisVerification, the transaction for passportNo:{}, doesn't have fingerprints.", oldPassportNo);
				skipVerify = false;
				return true;
			}
			
			//2.1) image to image
			if (isArchived) {
				IdserverImageFingerprint currTPL = buildFingerprintTemplates(transObj.getNicTransactionAttachments());
				IdserverImageFingerprint prevTPL = buildFingerprintTemplatesByTransactionId(prevTransId);
				logger.info("[before] inquiryFpComparison - "+transactionId+","+afisKeyNo+" vs "+prevTransId+","+prevAfisKeyNo);
				if (currTPL!=null && prevTPL!=null) {
					idSvrSubj = idserverAgentService.inquiryFpComparison(transactionId, currTPL, prevTPL);
				} else {
					String docType = NicTransactionAttachment.DOC_TYPE_FINGERPRINT;
					List<NicTransactionAttachment> prevAttachmentList = transactionAttachmentService.findNicTransactionAttachments(prevTransId, docType, null).getListModel();
					if (CollectionUtils.isEmpty(prevAttachmentList)) {
						logger.info("skip afisVerification, there is no biometric data for prev transactionId:"+prevTransId);
						skipVerify = true;
						rejectMessage = VERIFICATION_REJECTED_APPLICATION+CODE_VALUE_ID_NO_FP_FOUND_ON_PREV_PASSPORT;
						return true;
					} 
					idSvrSubj = this.inquiryFpComparisonByValidFingers(transactionId, prevTransId, transObj.getNicTransactionAttachments(), prevAttachmentList);
				}
				logger.info("[after] inquiryFpComparison - "+transactionId+","+afisKeyNo+" vs "+prevTransId+","+prevAfisKeyNo+" : "+(idSvrSubj!=null?idSvrSubj.getHitDecision():"null")+", rejectMessage:"+rejectMessage);
				if (idSvrSubj!=null) idSvrSubj.setRegistrationId(prevTransId);
				if (idSvrSubj!=null) idSvrSubj.setPersonIdentifier(prevAfisKeyNo);
			//2.2) image to key
			} else {
				ArrayList<NicTransactionAttachment> attachmentList = new ArrayList<NicTransactionAttachment>();
				attachmentList.addAll(transObj.getNicTransactionAttachments());
				
				List<AbstractBiometricData<IdserverImageFingerprint>> biometricData = new ArrayList<AbstractBiometricData<IdserverImageFingerprint>>();
				logger.info("trans docs count:"+attachmentList.size());
				IdserverImageFingerprint tpl = buildFingerprintTemplates(transObj.getNicTransactionAttachments());
				if (tpl!=null) {
					biometricData.add(tpl);
				} else {
					biometricData = buildFingerPrints(biometricData, attachmentList);
				}
				logger.info("biometricData count:"+biometricData.size());
				
				if (CollectionUtils.isNotEmpty(biometricData)) {
					logger.info("[before] inquiryFpVerification - "+transactionId+","+afisKeyNo+" vs "+prevTransId+","+prevAfisKeyNo);
					idSvrSubj = idserverAgentService.inquiryFpVerification(biometricData, prevTransId, prevAfisKeyNo);
					logger.info("[after] inquiryFpVerification - "+transactionId+","+afisKeyNo+" vs "+prevTransId+","+prevAfisKeyNo+" : "+(idSvrSubj!=null?idSvrSubj.getHitDecision():"null"));
					if (idSvrSubj!=null) idSvrSubj.setPersonIdentifier(prevAfisKeyNo);
				} else {
					logger.info("skip afisVerification, there is no biometric data for transactionId:"+transactionId);
					skipVerify = true;
					rejectMessage = VERIFICATION_REJECTED_APPLICATION + NicVerifyCpdCommand.MISSING_BIOMETRIC_DATA;
					return true;
				}
			}
			//13FEB2014 - jp - save matchresult after 1:1 begins
			IdserverMatchResult matchResult = new IdserverMatchResult();
			List<IdserverSubject> idSvrSubjList = new ArrayList<IdserverSubject>();
			
			if(idSvrSubj!=null){
				idSvrSubjList.add(idSvrSubj);
				matchResult.setHasHit(true);
				matchResult.setSubjectList(idSvrSubjList);
			}else{
				logger.info("idSvrSubj is null");
			}
			
			long searchResultId = saveSearchResult(matchResult, transObj.getTransactionId(), afisKeyNo, job.getWorkflowJobId());
			logger.info("save search results output:"+searchResultId);
			
			// check Hit Decision, if there is no hit, then required investigation.
			if (idSvrSubj!=null) {
				hasHit = idSvrSubj.getHitDecision();

				if (logger.isDebugEnabled()) {
					logger.info("screening results:\n"+ReflectionToStringBuilder.toString(idSvrSubj, ToStringStyle.MULTI_LINE_STYLE));
					if (logger.isInfoEnabled()) {
						for(IdserverScore score : idSvrSubj.getScoreList()){
							logger.info("screening score:\n"+ReflectionToStringBuilder.toString(score, ToStringStyle.SHORT_PREFIX_STYLE));
						}
					}
				}
			}
			logger.info(">>>>> hasHit result:"+hasHit);
			return hasHit;
			
		} catch (IdserverAgentServiceException ie) {
			ie.printStackTrace();
			throw new IdserverAgentServiceException(ie);
			
		}catch (Exception e) {
			logger.info(">>>>> has exception:"+e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	private IdserverSubject inquiryFpComparisonByValidFingers(String transactionId, String prevTransId, Collection<NicTransactionAttachment> attachments, Collection<NicTransactionAttachment> prevAttachments) throws IdserverAgentServiceException {
		IdserverSubject idSvrSubj = null;
		
		for (NicTransactionAttachment attachment : attachments) {
			NicTransactionAttachment pervAttachment = null;
			if(!StringUtils.equalsIgnoreCase(attachment.getDocType(), NicTransactionAttachment.DOC_TYPE_FINGERPRINT)) 	continue;
			
			// loop to find matching FP.
			for (NicTransactionAttachment loopAttachment : prevAttachments) {
				if (StringUtils.isNumeric(attachment.getSerialNo()) && StringUtils.isNumeric(loopAttachment.getSerialNo())) {
					if (Integer.parseInt(attachment.getSerialNo())==Integer.parseInt(loopAttachment.getSerialNo())) {
						pervAttachment = loopAttachment;
						break;
					}
				}
			}
			if (attachment!=null && pervAttachment!=null) {
				IdserverImageFingerprint currFP = buildFingerprint(attachment);
				IdserverImageFingerprint prevFP = buildFingerprint(pervAttachment);
				if (currFP!=null && prevFP!=null) {
					idSvrSubj = idserverAgentService.inquiryFpComparison(transactionId, currFP, prevFP);
					String hitDecision = idSvrSubj!=null?""+idSvrSubj.getHitDecision():"";
					String hisScore = idSvrSubj!=null&&CollectionUtils.isNotEmpty(idSvrSubj.getScoreList())? ""+idSvrSubj.getScoreList().iterator().next().getScore():"";
					logger.info("1:1 for [{}] loop finger[{}] Current[{}] vs Previous [{}], idSvrSubj hitDecision: {}, hitScores: {} ", new Object[]{transactionId, attachment.getSerialNo(), attachment.getTransactionId(), pervAttachment.getTransactionId(), hitDecision, hisScore});
					if (idSvrSubj!=null && Boolean.TRUE.equals(idSvrSubj.getHitDecision())) {
						break;
					}
				}
			}
		}
		
		if (idSvrSubj!=null && Boolean.FALSE.equals(idSvrSubj.getHitDecision())) {
			logger.warn("completed afisVerification, not hit for prev transactionId:"+prevTransId+", required manual investigation.");
		}
		return idSvrSubj;
	}

	private long saveSearchResult(IdserverMatchResult matchResult, String transactionId, String afisKeyNo, Long jobId) throws Exception{
		long searchResultId = 0;
		
		try {
			logger.info("transactionId: "+ transactionId);
			//save inquiry search result
			searchResultId = inquirySearchResultService.saveSearchResult(InquirySearchResultService.VERIFICATION_FP_1_TO_1, matchResult, jobId, transactionId, afisKeyNo);
			
			logger.info("searchResultId:"+searchResultId);
			return searchResultId;
		} catch (InquirySearchServiceException ie) {
			throw new InquirySearchServiceException(ie);
		}catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private List<AbstractBiometricData<IdserverImageFingerprint>> buildFingerPrints(List<AbstractBiometricData<IdserverImageFingerprint>> biometricData, ArrayList<NicTransactionAttachment> transDocsList){
		
		try {
			
			for(NicTransactionAttachment tDoc : transDocsList){
			//fingerprint: RightIndex
				//logger.info("doctype:"+tDoc.getDocType());
//					if(tDoc.getDocType().substring(0, 2).equalsIgnoreCase("FP")){
				if(StringUtils.equalsIgnoreCase(tDoc.getDocType(), "FP")){
					//logger.info("flag FP");
					IdserverImageFingerprint fingerprint = new IdserverImageFingerprint();
					fingerprint.setFingerPosition(FingerPosition.getInstance(Short.valueOf(tDoc.getSerialNo())).getKey());

					logger.info("finger pos:"+fingerprint.getFingerPosition());

					fingerprint.setImageType(IdserverImageType.WSQ);
					fingerprint.setImageData(tDoc.getDocData());		
					biometricData.add(fingerprint);	
				}else{
					//logger.info("flag not FP : "+tDoc.getDocType());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return biometricData;
	}
	
	private IdserverImageFingerprint buildFingerprintTemplates(Collection<NicTransactionAttachment> transDocsList){
		IdserverImageFingerprint tpl = null;
		try {
			for(NicTransactionAttachment tDoc : transDocsList){
				if(StringUtils.equalsIgnoreCase(tDoc.getDocType(), "TPL")) {
					logger.info("setting TPL");
					tpl = new IdserverImageFingerprint();
					tpl.setFingerPosition((short) 99);
					tpl.setImageType(IdserverImageType.TPL);
					tpl.setImageData(tDoc.getDocData());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("tpl exists:"+(tpl!=null));
		return tpl;
	}
	
	private IdserverImageFingerprint buildFingerprint(NicTransactionAttachment transDoc){
		IdserverImageFingerprint fp = null;
		try {
			if(StringUtils.equalsIgnoreCase(transDoc.getDocType(), "FP") && transDoc.getDocData()!=null) {
				fp = new IdserverImageFingerprint();
				fp.setFingerPosition(FingerPosition.getInstance(Short.valueOf(transDoc.getSerialNo())).getKey());
				fp.setImageType(IdserverImageType.WSQ);
				fp.setImageData(transDoc.getDocData());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fp;
	}
	
	private IdserverImageFingerprint buildFingerprintTemplatesByTransactionId(String transactionId){
		IdserverImageFingerprint tpl = null;
		try {
			String docType = NicTransactionAttachment.DOC_TYPE_TPL;
			String serialNo = null; //NicTransactionAttachment.DEFAULT_SERIAL_NO;
			List<NicTransactionAttachment> transDocsList = transactionAttachmentService.findNicTransactionAttachments(transactionId, docType, serialNo).getListModel();
			
			logger.info("transaction - {} , docSize: {}", transactionId, transDocsList!=null?transDocsList.size():0);
			for(NicTransactionAttachment tDoc : transDocsList){
				if(StringUtils.equalsIgnoreCase(tDoc.getDocType(), "TPL")) {
					logger.info("setting TPL - {} ", transactionId);
					tpl = new IdserverImageFingerprint();
					tpl.setFingerPosition((short) 99);
					tpl.setImageType(IdserverImageType.TPL);
					tpl.setImageData(tDoc.getDocData());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("tpl exists:"+(tpl!=null));
		return tpl;
	}
	
	//1) validate previous transaction if doesn't have fingerprint templates and age is below 7 years old.
	private boolean validateTransaction(String prevTransId, NicDocumentData prevDocumentData) throws TransactionServiceException {
		boolean exempted = false;
		Date dateOfIssue = null;
		Date dateOfBirth = null;
		int ageForFPExemption = 7;
		
		Parameters parameter = parametersService.findById(new ParametersId(PARA_SCOPE_APPLICATION, PARA_NAME_AGE_FOR_FP_EXEMPTION));
		if (parameter!=null) {
			Integer defaultAgeForFPExemption = (Integer) parameter.getParaValue();
			logger.info("Parameter defaultAgeForFPExemption: "+defaultAgeForFPExemption);
			if (defaultAgeForFPExemption!=null) {
				ageForFPExemption = defaultAgeForFPExemption.intValue();
			}
		}
		
		if (prevDocumentData!=null) {
			dateOfIssue = prevDocumentData.getDateOfIssue();
		}
		NicTransaction prevTransObj = nicTransactionService.findById(prevTransId, false, false, true, false);//has registration data
		if (prevTransObj!=null && prevTransObj.getNicRegistrationData()!=null) {
			if (prevTransObj.getNicRegistrationData().getDateOfBirth()!=null) {
				dateOfBirth = prevTransObj.getNicRegistrationData().getDateOfBirth();
			}
		}
		if (dateOfIssue!=null && dateOfBirth!=null) {
			int numOfDays = DateUtil.dateDiff(dateOfBirth, dateOfIssue);
			
			if (numOfDays< ageForFPExemption * 365) {
				exempted = true;
			}
			logger.info("Transaction [{}], applicant [DOB:{}] 's age [{}] on Date of issue [{}]. exempted: {} ", new Object[] {prevTransId, dateOfBirth, numOfDays/365, dateOfIssue, exempted});
		}
		return exempted;
	}
	
	private boolean validatePassportNo(String passportNo) {
		boolean isMrpPassport  = false;
		boolean isGreenPassport = false;
		if (StringUtils.isNotBlank(passportNo)) {
			String firstTwoChar = StringUtils.substring(passportNo, 0, 2);
			String lastDigit  = StringUtils.substring(passportNo, 2);
			
			if ( firstTwoChar.length() == 2 && "XX".equalsIgnoreCase(firstTwoChar) ) {
				if (lastDigit.length() == 7  )
					if (StringUtils.isNumeric(lastDigit))
						isMrpPassport = true;				
			}
			
			if ( firstTwoChar.length() == 2  ) {
				String firstChar = StringUtils.substring(firstTwoChar, 0,1);
				String secondChar = StringUtils.substring(firstTwoChar, 1,2);
				if ( firstChar.equalsIgnoreCase(secondChar) )
					if ( lastDigit.length() == 6 || lastDigit.length() == 7  )
						if ( StringUtils.isNumeric(lastDigit) )
							isGreenPassport = true;
			}
			
			if (isMrpPassport || isGreenPassport)
				return true;
			
		}
		
		return false;
	}
	
	private void updateStatus(long objId, String state, int command){
		try {
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveTransactionLog(String transactionId, String transactionStage,String transactionStatus, Date startTime, Date endTime, String logInfo, String logData, Integer retryCount) {
		NicTransactionLog transactionLog = new NicTransactionLog();
		transactionLog.setRefId(transactionId);
		transactionLog.setLogCreateTime(new Date());
		transactionLog.setTransactionStage(transactionStage);//TRANSACTION_STATE_PERSONALIZATION);
		transactionLog.setTransactionStatus(transactionStatus);
		transactionLog.setSiteCode(nicCommandUtil.getSystemSiteCodeFromParameter()); //get from 'CURRENT_SITE_CODE'
		transactionLog.setStartTime(startTime);
		transactionLog.setEndTime(endTime);
		transactionLog.setLogInfo(logInfo);
		transactionLog.setLogData(logData);
		//8-OCT-2013 (jp) - added hostname and system
		transactionLog.setWkstnId(nicCommandUtil.getHostName());
		transactionLog.setOfficerId(nicCommandUtil.getSystemName());
		transactionLog.setRetryCount(retryCount);
		synchronized (NicTransactionLog.class) {
			nicTransactionLogService.save(transactionLog);
		}
	}
	
	private void saveValidationResult(Long jobId, String transactionId, String afisKeyNo, String hitInfo) throws InquirySearchServiceException {
		NicSearchResult latestResult = inquirySearchResultService.findLatestResultByJobId(jobId, "ERROR");
		if (latestResult!=null && CollectionUtils.isNotEmpty(latestResult.getHitList())) {
			NicSearchHitResult hitCandidate = latestResult.getHitList().iterator().next();
			String dbHitInfo = hitCandidate.getHitInfo();
			if (StringUtils.indexOf(dbHitInfo, VERIFICATION_REJECTED_APPLICATION)!=-1 && StringUtils.indexOf(hitInfo, VERIFICATION_REJECTED_APPLICATION)!=-1) {
				String dbHitCategories = StringUtils.substringAfter(dbHitInfo, VERIFICATION_REJECTED_APPLICATION);
				String curHitCategory = StringUtils.substringAfter(hitInfo, VERIFICATION_REJECTED_APPLICATION);
				List<String> hitCategoryList = new ArrayList<String>();
				if (StringUtils.indexOf(dbHitCategories, ",")!=-1) {
					hitCategoryList.addAll(Arrays.asList(StringUtils.split(dbHitCategories,",")));
				} else {
					hitCategoryList.add(dbHitCategories);
				}
				logger.info("Transaction [{}], search result {} with hitInfo {} ", new Object[] {transactionId, latestResult.getSearchResultId(), dbHitInfo});
				boolean recordExists = false;
				for (String hitCategory : hitCategoryList) {
					if (StringUtils.equals(curHitCategory, hitCategory)) {
						recordExists = true;
						break;
					}
				}
				if (!recordExists) {
					hitCategoryList.add(curHitCategory);
				}
				Collections.sort(hitCategoryList);
				
				String finalHitInfo = "";
				for (String hitCategory : hitCategoryList) {
					if (StringUtils.isNotEmpty(finalHitInfo))	finalHitInfo += ",";
					finalHitInfo += hitCategory;
				}
				finalHitInfo = VERIFICATION_REJECTED_APPLICATION+finalHitInfo;
				hitCandidate.setHitInfo(finalHitInfo);
				hitCandidate.setCreateDatetime(new Date());
				inquirySearchResultService.saveOrUpdate(latestResult);
				logger.info("Transaction [{}], updated search result {} with hitInfo {} ", new Object[] {transactionId, latestResult.getSearchResultId(), finalHitInfo});
			}
		} else {
			NicSearchResult searchResult = new NicSearchResult();
			searchResult.setWorkflowJobId(jobId);
			searchResult.setAfisKeyNo(afisKeyNo);
			searchResult.setTransactionId(transactionId);
			searchResult.setCreateDatetime(new Date());
			searchResult.setTypeSearch("ERROR");
			searchResult.setHasHit(true);
			
			NicSearchHitResult hitCandidate = new NicSearchHitResult();
			hitCandidate.setTransactionIdHit(transactionId); // Search Side's transaction id
			//hitCandidate.setAfisKeyNoHit(afisKeyNo);
			hitCandidate.setHitInfo(hitInfo);
			hitCandidate.setDataSource("ERROR");
			hitCandidate.setCreateDatetime(new Date());
			hitCandidate.setHitDecision(true);
			searchResult.getHitList().add(hitCandidate);
			
			inquirySearchResultService.saveSearchResult(searchResult, searchResult.getHitList());
			logger.info("Transaction [{}], saved search result {} with hitInfo {} ", new Object[] {transactionId, searchResult.getSearchResultId(), hitInfo});
		}
	}
	
	public void setCommandUtil (NicCommandUtil commandUtil) {
		this.nicCommandUtil = commandUtil;
	}

	public void setIdserverAgentService(IdserverAgentService idserverAgentService) {
		this.idserverAgentService = idserverAgentService;
	}
	
	public void setNicTransactionService(NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}

	public void setUploadJobService(NicUploadJobService uploadJobService) {
		this.uploadJobService = uploadJobService;
	}

	public void setInquirySearchResultService(InquirySearchResultService inquirySearchResultService) {
		this.inquirySearchResultService = inquirySearchResultService;
	}

	public void setNicTransactionLogService(TransactionLogService nicTransactionLogService) {
		this.nicTransactionLogService = nicTransactionLogService;
	}

	public void setDocumentDataService(DocumentDataService documentDataService) {
		this.documentDataService = documentDataService;
	}
	
	public void setNicAfisDataService(NicAfisDataService nicAfisDataService) {
		this.nicAfisDataService = nicAfisDataService;
	}

	public void setTransactionAttachmentService(NicTransactionAttachmentService transactionAttachmentService) {
		this.transactionAttachmentService = transactionAttachmentService;
	}
	
	public void setParametersService(ParametersService parametersService) {
		this.parametersService = parametersService;
	}
}


