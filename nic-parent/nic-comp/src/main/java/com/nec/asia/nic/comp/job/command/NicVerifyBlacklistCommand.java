package com.nec.asia.nic.comp.job.command;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
import com.nec.asia.nic.comp.trans.service.InquirySearchResultService;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicBlacklistService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.util.MiscXmlConvertor;


/*
 * 14 January 2016 (setia): revamp the logic of CPD 
 * 28 March 2016 (chris)  : add age check for fingerprint exempted.
 * 18 April 2016 (chris)  : add 09 reason (CPD check with hit).
 * 26 April 2016 (chris)  : reset job reprocess count when complete
 * 06 June 2016 (setia)   : to prevent duplicate record for those under age that has no fingerprint, but allow multiple paspport type
 */
public class NicVerifyBlacklistCommand extends BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	//private static final Logger logger = Logger.getLogger(NicVerifyBlacklistCommand.class);
	
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	public static final int BLACKLIST = 44;
	public static final int INVESTIGATION = 9;
	public static final String STATE_NAME = "BLACKLIST";
	public static final String STATE_ERROR = "ERROR";
	
	//Demographic Fields
	public static final String CHECK_FIRSTNAME = "firstnameFull";
	public static final String CHECK_MIDDLENAME = "middlenameFull";
	public static final String CHECK_SURNAME = "surnameFull";
	public static final String CHECK_BIRTH_DATE = "dateOfBirth";
	public static final String CHECK_GENDER = "gender";
	public static final String CHECK_NIN = "gender";
	
	
	//JOB STATE
	public static final String JOB_STATE_CPD = "CPD_CHECK";
	public static final String JOB_STATE_BLACKLIST = "BLACKLIST_CHECK";
	
	//REJECTED REASON
	public static final String MISSING_BIOMETRIC_DATA = "01";
	public static final String MISSING_PHOTO = "02";
	public static final String MISSING_PHOTO_CHIP = "03";
	public static final String BLACKLISTED = "04";
	
	public static final String DEMOGRAPHIC_CHECK_FAILED = "09"; //Applicant flag by system due to matching demographic data. 
	
	//VERIFICATION TYPE
	public static final String VERIFICATION_OLD_PASSPORT = "OLD PASSPORT";
	public static final String VERIFICATION_APPLICATION_IN_PROGRESS = "APPLICATION IN PROGRESS";
	public static final String VERIFICATION_REJECTED_APPLICATION = "REJECTED APPLICATION=";
	
	//parameters
	/*private static final String PARA_SCOPE_APPLICATION = "APPLICATION";
	private static final String PARA_NAME_AGE_FOR_FP_EXEMPTION = "AGE_FOR_FP_EXEMPTION";
	
	private static final String PARA_SCOPE_SYSTEM = "SYSTEM";
	private static final String PARA_NAME_ASSIGN_USER = "ASSIGN_USER";*/
	
	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
	private InquirySearchResultService inquirySearchResultService;
	private NicAfisDataService  nicAfisDataService;
	private NicBlacklistService nicBlacklistService;
	
	//28 Oct 2013 (jp): Added logInfo to add detailed reason for cpd comparison failure
	String logInfo = "";
	String logInfoXml = "";
	
	@Override
	public void doSomething(NicUploadJob obj) {
		
		//Kiểm tra bảng WorkflowProcess
		/*NicWorkflowProcess objW = null;
		Boolean checkW = true;
		try {
			logger.info("Get data WorkflowProcess");
			List<NicWorkflowProcess> lstW = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.UPLOADED);
			if(lstW != null && lstW.size() > 0){
				objW = new NicWorkflowProcess();
				objW = lstW.get(0);

				logger.info("Get data WorkflowProcess: WORKFLOW_END:" + objW.getWorkflowEnd());
				if(!objW.getWorkflowEnd().equals(NicWorkflowProcess.CHECK_CPD)){
					checkW = false;
				}
			}
			else
			{
				logger.info("Get data WorkflowProcess: NULL");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		/*if(StringUtils.equals(job.getAfisCheckStatus(), STATUS_COMPLETED) || StringUtils.equals(job.getAfisCheckStatus(), STATUS_COMPLETED_WITH_HIT) || StringUtils.equals(job.getAfisCheckStatus(), STATUS_NO_REQUIRED) ){
			
			//AFIS CHECK has been completed before, skip step
			logger.info("-----[{}] skipping AFIS CHECK, status: {}, continue to next step", transObj.getTransactionId(), job.getAfisCheckStatus());
		}*/
		
		
			
		logger.info("inside NicVerifyBlacklistCommand job id:" + obj.getWorkflowJobId()+", reprocess count is:"+obj.getJobReprocessCount());
		
		//for logging to transaction logs
		String logData = null;
		Date startTime = new Date();
		String jobStatus = null;			
		this.setState(obj.getJobType());//set job type as child key
		
		try {
			
				NicTransaction transObj = null;

				updateStatus(obj.getWorkflowJobId(), JOB_STATE_BLACKLIST, JOB_STATE);// update job state to CPD_CHECK

				logger.info("JOB_STATE_BLACKLIST :"
						+ obj.getTransactionId());
				
				logger.info("flag after updating job current status:"
						+ obj.getJobCurrentStage());

				if (StringUtils.equals(obj.getBlacklistCheckStatus(),
						STATUS_COMPLETED)
						|| StringUtils.equals(obj.getBlacklistCheckStatus(),
								STATUS_NO_REQUIRED)
						|| StringUtils.equals(obj.getBlacklistCheckStatus(),
								STATUS_COMPLETED_WITH_HIT)) {

					// CPD has been completed before, skip step
					// NO ACTION, SKIP COMMAND
					// transactionStatus = JOB_STATE_CPD+STAGE_COMPLETED;
					logger.info("[" + JOB_STATE_BLACKLIST
							+ "] already completed, skipping step");

				} else if (StringUtils.equals(obj.getInvestigationStatus(),
						STATUS_COMPLETED)
						&& StringUtils.equals(obj.getInvestigationType(),
								STATE_NAME)) {
					// CPD has been approved thru investigation, skip step
					// transactionStatus = JOB_STATE_CPD+STAGE_COMPLETED;

				} else {
					//Thêm bước xóa CPD khi check lại
					//Kiểm tra trong bảng eppSearchResult
					/*NicSearchResult nicSearchResultExist = inquirySearchResultService.findLatestResultByJobId(obj.getWorkflowJobId(), null);
					if(nicSearchResultExist != null){
						//Xóa dữ liệu trong bảng eppSearchHitResult
						List<NicSearchHitResult> listHit = inquirySearchResultService.findListHitResultByJobId(nicSearchResultExist.getSearchResultId());
						if(listHit != null && listHit.size() > 0){
							for(NicSearchHitResult removeObj : listHit){
								inquirySearchResultService.removeNicSearchHitResultById(removeObj);
							}
						}
						
						inquirySearchResultService.removeNicSearchResultById(nicSearchResultExist);
					}*/
					transObj = nicTransactionService.findById(
							obj.getTransactionId(), false, true, true, false);
					
					logger.info("[" + JOB_STATE_BLACKLIST + "]"
							+ obj.getTransactionId() + "\n");
					updateStatus(obj.getWorkflowJobId(), STATUS_INPROGRESS, BLACKLIST);// setCpdCheckStatus to in progress

					/*transObj = nicTransactionService.findById(
							obj.getTransactionId(), false, true, true, false);// has registration data and trans doc

					Map<String, Object> validationFieldsMap = this
							.constructDemographicCheckingFields(transObj
									.getNicRegistrationData());

					StringBuffer hitInfoBuffer = new StringBuffer();
					for (String fieldName : validationFieldsMap.keySet()) {
						hitInfoBuffer.append(fieldName).append(",");
					}

					List<NicRegistrationData> hitList = nicRegistrationDataService
							.findAllByFields(validationFieldsMap);*/
					String dob = new java.sql.Date(transObj
							.getNicRegistrationData()
							.getDateOfBirth().getTime()).toString();
					List<EppBlacklist> hitByCriterion = nicBlacklistService.findByMutilCriterion(transObj
							.getNicRegistrationData().getSurnameLine1(), dob, transObj.getNin());
					
					/*List<NicTransaction> hitListNin = nicTransactionService
							.findAllByFields(transObj.getNin());// TRUNG thêm để check cmnd*/					
					NicAfisData searchAfisData = nicAfisDataService
							.findByTransactionId(obj.getTransactionId());
					NicSearchResult searchResult = new NicSearchResult();
					Map<String, String> afisKeyCache = new HashMap<String, String>();
					searchResult.setWorkflowJobId(obj.getWorkflowJobId());
					if (searchAfisData != null)
						searchResult.setAfisKeyNo(searchAfisData.getAfisKeyNo());
					searchResult.setTransactionId(obj.getTransactionId());
					searchResult.setCreateDatetime(new Date());
					searchResult.setTypeSearch(STATE_NAME);
					searchResult.setHasHit(false);
					boolean isHit = false;

					if(hitByCriterion != null){
						for(EppBlacklist bl : hitByCriterion){
							searchResult
							.getHitList()
							.add(buildSearchHitResult(
									bl.getId().toString(),
									afisKeyCache.get(bl.getId().toString()),
									VERIFICATION_APPLICATION_IN_PROGRESS,
									STATE_NAME));
						}
					}
					if (searchResult.getHitList().size() > 0) {
						searchResult.setHasHit(true);
						isHit = true;
					}

					if (isHit) {
						jobStatus = JOB_STATE_BLACKLIST + STAGE_COMPLETED_WITH_HIT;
						updateStatus(obj.getWorkflowJobId(),
								STATUS_COMPLETED_WITH_HIT, BLACKLIST);
					} else {
						jobStatus = JOB_STATE_BLACKLIST + STAGE_COMPLETED;
						updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED,
								BLACKLIST);
					}
					uploadJobService.resetReprocessCount(
							obj.getWorkflowJobId(), 0);// [cw] 2016 Apr 26

					inquirySearchResultService.saveSearchResult(searchResult,
							searchResult.getHitList());
					
				}
		} catch (Exception ex) {
			logger.error("Exception is occured", ex);
			jobStatus = JOB_STATE_BLACKLIST + STAGE_ERROR;
			logData = MiscXmlConvertor.parseObjectToXml(ex);
			
			this.setState(GOTO_ERROR_CMD);
			updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, BLACKLIST);
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true,uploadJobService);
		} finally {
			try {
				if(obj.getJobReprocessCount() != null && obj.getJobReprocessCount() > INT_PASS_RECOUNT_MAX && obj.getBlacklistCheckStatus() != null && obj.getBlacklistCheckStatus().equals(STATUS_ERROR)){
					
					jobStatus = JOB_STATE_BLACKLIST + STAGE_PASS_BY_RECOUNT_MAX;
					updateStatus(obj.getWorkflowJobId(),
							STATUS_PASS_BY_RECOUNT_MAX, BLACKLIST);
					//transactionStatus = JOB_STATE_INVESTIGATION + STAGE_PASS_BY_RECOUNT_MAX;
					uploadJobService.resetReprocessCount(obj.getWorkflowJobId(), 0);
				}

				if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(jobStatus)) {
						uploadJobService.updateJobStatus(obj.getWorkflowJobId(), jobStatus);
						this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_BLACKLIST, jobStatus, startTime, new Date(), null, logData, obj.getJobReprocessCount());
				}
			} catch (Exception e) {
				logger.error("Exception in finally block (NicVerifyBlacklistCommand.doSomething)", e);
			}
		}
	}
	
	
	private NicSearchHitResult buildSearchHitResult (String transactionId, String afisKeyNo, String hitInfo, String dataSource) {
		NicSearchHitResult hitCandidate = new NicSearchHitResult();
		hitCandidate.setTransactionIdHit(transactionId); // Search Side's transaction id
		hitCandidate.setAfisKeyNoHit(afisKeyNo);
		hitCandidate.setHitInfo(hitInfo);
		hitCandidate.setDataSource(STATE_NAME);
		hitCandidate.setCreateDatetime(new Date());
		hitCandidate.setHitDecision(true);
	    return hitCandidate;
	}
	
	
	
	private void updateStatus(long objId, String state, int command) {
	
		try {
			//logger.info("\n---- updateStatus:"+objId+"/"+state+"/"+command);
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			logger.error("update status exception"+e.getMessage());
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
	
	public void setNicTransactionService(NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}
	
	public void setUploadJobService(NicUploadJobService uploadJobService) {
		this.uploadJobService = uploadJobService;
	}
	
	public void setNicTransactionLogService(TransactionLogService nicTransactionLogService) {
		this.nicTransactionLogService = nicTransactionLogService;
	}
	
	public void setInquirySearchResultService(InquirySearchResultService inquirySearchResultService) {
		this.inquirySearchResultService = inquirySearchResultService;
	}
	
	public void setNicAfisDataService(NicAfisDataService nicAfisDataService) {
		this.nicAfisDataService = nicAfisDataService;
	}
	
	public void setNicBlacklistService(NicBlacklistService nicBlacklistService) {
		this.nicBlacklistService = nicBlacklistService;
	}
}
