package com.nec.asia.nic.comp.job.command;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
import com.nec.asia.nic.comp.trans.domain.NicJobAfisCheckResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.InquirySearchResultService;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.InquirySearchServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.utils.TransDTOMapper;
import com.nec.asia.nic.util.MiscXmlConvertor;

public class NicScreenAfisCommand extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	//private static final Logger logger = Logger.getLogger(NicScreenAfisCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
		
	//JOB TYPE
//	public static final String NEW_REGISTRATION = "REG";
//	public static final String REPLACEMENT = "REP";
//	public static final String PARTICULARS_UPDATE = "PAR_UPD";
//	public static final String CONVERSION = "CON";
	
	//UPDATE STATUS CODE
	public static final int JOB_STATE = 1;
	//public static final int CPD = 2;
	//public static final int AFIS_REG = 3;
	public static final int AFIS_SCREEN = 4;
	public static final int INVESTIGATION = 9;
	public static final int AFIS_DELETE = 10;
	//JOB STATE
	public static final String JOB_STATE_AFIS_CHECK= "AFIS_CHECK";
	
	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private InquirySearchResultService inquirySearchResultService;
	private TransactionLogService nicTransactionLogService;
	
	private IdserverAgentService idserverAgentService;
	private NicAfisDataService nicAfisDataService;
	private NicTransactionAttachmentService transactionAttachmentService;
	
	//private TransDTOMapper transDTOMapperUtil = new TransDTOMapper();
	
	/* 
	 * Modification History:
	 * 02 SEP 2013 (jp) - update nic_transaction.transaction_status field
	 * 07 SEP 2013 (jp) - temp logic to handle full amputee - go to investigation
	 * 08 Oct 2013 (jp) - add fplist = 0, fp_indicator has 10 U's
	 * 12 Dec 2013 (chris) - to handle and bypass re-registration on ref_txn_id.
	 * 27 Feb 2014 (jp) - add category of counting of 10 "U" in fp indicator but N in full amputee flag for list of cases forwarded to Investigation
	 * 02 Jun 2014 (jp) - swap logic on checking if transaction is investigation approved and checking if first time to do screening
	 * 03 Jun 2014 (jp) - no longer skip first retry
	 */
	
	@Override
	public void doSomething(NicUploadJob obj) {
		
		//for logging to transaction logs
		String logData = null;
		String logInfo = null;
		Date startTime = new Date();
		String transactionStatus = null;
		//
		
		this.setState(obj.getJobType());//set job type as child key
		
		try {
			
			NicTransaction transObj = new NicTransaction();
			updateStatus(obj.getWorkflowJobId(), JOB_STATE_AFIS_CHECK, JOB_STATE);
			transObj = nicTransactionService.findById(obj.getTransactionId(), false, false, true, false);//has registration data
			logger.info("\n-----obj.getJobReprocessCount():"+obj.getJobReprocessCount());
			if(StringUtils.equals(obj.getAfisCheckStatus(), STATUS_COMPLETED) || StringUtils.equals(obj.getAfisCheckStatus(), STATUS_COMPLETED_WITH_HIT) || StringUtils.equals(obj.getAfisCheckStatus(), STATUS_NO_REQUIRED) ){
			
				//AFIS CHECK has been completed before, skip step
				logger.info("\n-----skipping AFIS CHECK, status: {}, continue to next step", obj.getAfisCheckStatus());
				
			} else if(obj.getJobReprocessCount() == null) {

				//AFIS CHECK will be forced queued on first attempt to await data sync in BMS.
				logger.info("\n-----AFIS CHECK will be forced queued on first attempt to await data sync in BMS. count is:"+obj.getJobReprocessCount());
				this.setState(GOTO_ERROR_CMD);
				updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, AFIS_SCREEN);
				nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true, uploadJobService);
			
				//07 sep 2013 (jp) - temp logic to handle full amputee - go to investigation
				//08 Oct 2013 (jp) - add fplist = 0, fp_indicator has 10 U's
				//27 Feb 2014 (jp) - add counting of U in fp indicator for those uncaptured fingers but not amputee			
//			}else if(transObj != null && transObj.getNicRegistrationData() != null  && 
//					( (transObj.getNicRegistrationData().getFullAmputatedFlag() !=null && transObj.getNicRegistrationData().getFullAmputatedFlag().booleanValue()==true) ||
//					  (transObj.getNicRegistrationData().getTotalFp() == null || transObj.getNicRegistrationData().getTotalFp() == 0 ) ||
//					StringUtils.countMatches(transObj.getNicRegistrationData().getFpIndicator(), "U") == 10)){
//				logger.warn("\n-----full amputee, transaction passed to investigation");
//				
//				String logContent = "";
//				
//				if(transObj.getNicRegistrationData().getFullAmputatedFlag() !=null && transObj.getNicRegistrationData().getFullAmputatedFlag().booleanValue()==true)
//					logContent = "Full Amputee";
//				else if (StringUtils.countMatches(transObj.getNicRegistrationData().getFpIndicator(), "U") == 10)
//					logContent = "All fingers are unable to capture";
//				else
//					logContent = "No Fingers captured";
//				
//				
//				transactionStatus = JOB_STATE_AFIS_CHECK+FULL_AMPUTEE;
//				
//				logInfo = "<logInfoDTO><Reason>No FP captured</Reason><Remark>System detected: "+logContent+"</Remark></logInfoDTO>";
//				updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED_WITH_HIT, AFIS_SCREEN);
//				updateStatus(obj.getWorkflowJobId(), STATUS_INITIAL, INVESTIGATION);
//				updateStatus(obj.getWorkflowJobId(), null, AFIS_DELETE);
//				this.setState(GOTO_ERROR_CMD);
//
//				//02SEP2013 - JP - update nic_transaction.transaction_status field
//				transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION);
//				nicTransactionService.saveOrUpdate(transObj);
//			
//			//07 sep 2013 (jp) -end	
//				
			} else {
				logger.info("idserverAgentService:"+idserverAgentService);
				updateStatus(obj.getWorkflowJobId(), STATUS_INPROGRESS, AFIS_SCREEN);
				
				transObj = nicTransactionService.findById(obj.getTransactionId(), false, true, true, false);//has trans doc and registration data
				logger.info("transObj:"+transObj);
				
				//02SEP2013 - JP - update nic_transaction.transaction_status field
				/*Tạm đóng cập nhật trạng thái hồ sơ 04.08.2020*/
//				if (transObj.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_UPLOADED)) {
//					transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_SCREENING);
//				}
//				nicTransactionService.saveOrUpdate(transObj);
				
				
				logger.info("before screening:"+transObj.getTransactionId());
				
				if(afis1NScreeningByGivenIndentifier(transObj, obj)){
					logger.info("screening is passed");
					
					updateStatus(obj.getWorkflowJobId(),STATUS_COMPLETED, AFIS_SCREEN);
					transactionStatus = JOB_STATE_AFIS_CHECK+STAGE_COMPLETED;
					//transObj.setDateOfIssue(new Date());
					nicTransactionService.saveOrUpdate(transObj);
					uploadJobService.resetReprocessCount(obj.getWorkflowJobId(), 0);
					logger.info("flag6");
					
				}else{
					logger.info("1:N got hitlist, passing to investigation");
					
					transactionStatus = JOB_STATE_AFIS_CHECK+STAGE_COMPLETED_WITH_HIT;
					updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED_WITH_HIT, AFIS_SCREEN);
					//updateStatus(obj.getWorkflowJobId(), STATUS_INITIAL, INVESTIGATION);
					//updateStatus(obj.getWorkflowJobId(), null, AFIS_DELETE);
					//this.setState(GOTO_ERROR_CMD);
					
					//02SEP2013 - JP - update nic_transaction.transaction_status field
					//transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION);
					//nicTransactionService.saveOrUpdate(transObj);

				}
			}
		
		}catch (Exception e) {
			logger.error("flag EXCEPTION:"+e.getMessage());
			e.printStackTrace();
			transactionStatus = JOB_STATE_AFIS_CHECK+STAGE_ERROR;
			logData = MiscXmlConvertor.parseObjectToXml(e);
			logInfo = e.getMessage();
			this.setState(GOTO_ERROR_CMD);
			updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, AFIS_SCREEN);
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true, uploadJobService);
			
		}finally {
			try {
				if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
					uploadJobService.updateJobStatus(obj.getWorkflowJobId(), transactionStatus);
					this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_AFIS_CHECK, transactionStatus, startTime, new Date(), logInfo, logData, obj.getJobReprocessCount());
				}
			} catch (Exception e) {
				logger.error("Exception in finally block (NicScreenAfisCommand.doSomething)", e);
			}
		}
	}
	
	private boolean afis1NScreeningByGivenIndentifier(NicTransaction transObj, NicUploadJob obj) throws Exception{
		logger.info("in afis1NScreeningByGivenIndentifier");
		boolean afisScreeningPassed = false;
		
		try {
				//test afis service
				IdserverMatchResult matchResult = idserverAgentService.inquiryFpScreeningByGivenIdentifier(obj.getTransactionId());
				
				boolean hasHit = matchResult.isHasHit();
				logger.info("hasHit:"+hasHit);
				long searchResultId = saveSearchResult(matchResult, transObj, obj);
				logger.info("save search results output:"+searchResultId);

				//load data after filtering.
				NicSearchResult searchResult = inquirySearchResultService.findById(searchResultId);
				boolean isRecordRegistered = false;
				
				if(searchResult!=null) {
					if (CollectionUtils.isNotEmpty(searchResult.getHitList())) {
						logger.info("Result size is:"+ searchResult.getHitList().size());
						afisScreeningPassed = false;
					} else {
						afisScreeningPassed = true;
					}
					if (CollectionUtils.isNotEmpty(matchResult.getSubjectList())){
						for(IdserverSubject results :matchResult.getSubjectList()){
							String transactionIdHit = results.getRegistrationId();//transaction id
							logger.info("transactionIdHit:"+ transactionIdHit);
							if(StringUtils.equals(transactionIdHit, transObj.getTransactionId())){
								logger.info("self hit validation passed:"+ transactionIdHit);
								isRecordRegistered = true;
							} 
						}
					} 
				}
				
				if (!isRecordRegistered) {
					throw new IdserverAgentServiceException("AFIS unable to hit the registered Record.");
				}
				return afisScreeningPassed;	
		} catch (IdserverAgentServiceException ie) {
			throw new IdserverAgentServiceException(ie);
		}catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private long saveSearchResult(IdserverMatchResult matchResult, NicTransaction transDocs, NicUploadJob job) throws Exception{
		long searchResultId = 0;
		
		try {
			logger.info("transid:"+ transDocs.getTransactionId());
			logger.info("transaction documents:"+transDocs.getNicTransactionAttachments().size());
				
			//test inquiry save service
			//String nicId = (transDocs.getNicRegistrationData() != null)?String.valueOf(transDocs.getNicRegistrationData().getNicId()): "";
			NicAfisData afisData = nicAfisDataService.findById(transDocs.getTransactionId());
			String afisKeyNo = afisData.getAfisKeyNo();
			
			matchResult = this.cleanUpMatchResult(matchResult, afisKeyNo);
			
			searchResultId = inquirySearchResultService.saveSearchResult(InquirySearchResultService.SCREENING_FP_1_TO_N, matchResult, job.getWorkflowJobId(), transDocs.getTransactionId(), afisKeyNo);
			
			logger.info("searchResultId:"+searchResultId);
			return searchResultId;
		} catch (InquirySearchServiceException ie) {
			throw new InquirySearchServiceException(ie);
		}catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private IdserverMatchResult cleanUpMatchResult(IdserverMatchResult matchResult, String afisKeyNo) throws TransactionServiceException {
		IdserverMatchResult updatedMatchResult = null;
		int hitCount = 0;
		if (matchResult!=null && CollectionUtils.isNotEmpty(matchResult.getSubjectList())) {
			updatedMatchResult = new IdserverMatchResult();
			for (IdserverSubject hit : matchResult.getSubjectList()) {
				String transactionIdHit = hit.getRegistrationId();
				String afisKeyNoHit = hit.getPersonIdentifier();
				boolean isSkip = false;
				if (StringUtils.equals(afisKeyNo, afisKeyNoHit)) {
					isSkip = true;
					logger.debug("transactionIdHit :{}, having same afisKeyNo {} with searchRecord.", transactionIdHit, afisKeyNo);
				}
				NicTransaction hitTransaction = nicTransactionService.findById(transactionIdHit, false, false, false, false);
				if (hitTransaction==null) {
					isSkip = true;
					logger.warn("transactionIdHit :{}, {} doesn't exist! removing from hit list.", transactionIdHit, afisKeyNoHit);
				}
				
				if (!isSkip) {
					updatedMatchResult.setHasHit(true);
					updatedMatchResult.getSubjectList().add(hit);
					hitCount++;
				}
			}
		}
		logger.info("hitCount:"+hitCount);
		return updatedMatchResult;
	}

	private List<AbstractBiometricData<IdserverImageFingerprint>> buildFingerPrints(List<AbstractBiometricData<IdserverImageFingerprint>> biometricData, ArrayList<NicTransactionAttachment> transDocsList){
			
			try {
				
				for(NicTransactionAttachment tDoc : transDocsList){
				
				//fingerprint: RightIndex
					logger.info("x:"+tDoc.getDocType().substring(0, 2));
					if(tDoc.getDocType().substring(0, 2).equalsIgnoreCase("FP")){
						//logger.info("flag FP");
						IdserverImageFingerprint fingerprint = new IdserverImageFingerprint();
						fingerprint.setFingerPosition(FingerPosition.getInstance(Short.valueOf(tDoc.getSerialNo())).getKey());
						
						logger.info("finger pos:"+fingerprint.getFingerPosition());
						
						fingerprint.setImageType(IdserverImageType.WSQ);
						fingerprint.setImageData(tDoc.getDocData());		
						biometricData.add(fingerprint);	
					}else{
						//logger.info("flag not FP");	
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
				
			return biometricData;
		}
	
	private void saveAfisCheckResult(NicUploadJob obj, Long searchResultId) throws Exception{

		try {
			
			NicJobAfisCheckResult afisCheckResult = new NicJobAfisCheckResult();
			
			afisCheckResult.setSearchResultId(searchResultId);
			
			afisCheckResult.setUploadJobId(obj.getWorkflowJobId());
			afisCheckResult.setCreateDate(new Date());
			
			afisCheckResult.setCreateWkstnId(obj.getCreateWkstnId());
			afisCheckResult.setAfisCheckTime(new Date());
			
			afisCheckResult.setCreateBy("SYSTEM");
			
			//uploadJobService.saveAfisCheckResult(obj,afisCheckResult);
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	
	private void updateStatus(long objId, String state, int command){
		try {
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			logger.info("exception in updateStatus:"+e.getMessage());	
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

	public void setInquirySearchResultService(
			InquirySearchResultService inquirySearchResultService) {
		this.inquirySearchResultService = inquirySearchResultService;
	}

	public void setNicTransactionLogService(
			TransactionLogService nicTransactionLogService) {
		this.nicTransactionLogService = nicTransactionLogService;
	}

	public void setNicAfisDataService(NicAfisDataService nicAfisDataService) {
		this.nicAfisDataService = nicAfisDataService;
	}

	public void setTransactionAttachmentService(NicTransactionAttachmentService transactionAttachmentService) {
		this.transactionAttachmentService = transactionAttachmentService;
	}
}


