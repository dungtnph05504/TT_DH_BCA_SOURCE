package com.nec.asia.nic.comp.job.command;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.util.MiscXmlConvertor;

/**
 * @author JohnPaul_Millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * <p>This Command is the first command to pass thru for all Job Types, It handles deletion cases, Investigation cases and appends reprocess count</p>
 */
/* 
 * Modification History:
 * 
 * 07 Apr 2014 (jp) - add handling of cancelled tasks 05
 */
public class NicInitialCommand extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	//private static final Logger logger = Logger.getLogger(NicInitialCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
	
	public String DELETE = "DELETE";
	public static final int AFIS_DELETE = 10;
	public static final int CPD = 2;
	public static final int AFIS_SCREEN = 4;
	public static final int AFIS_VERIFY = 12;
	public static final String JOB_STATE_INIT = "INITIALIZE";
	
	@Override
	public void doSomething(NicUploadJob obj) {
		logger.info("inside init command, transactionId:{}, job type:{} ", obj.getTransactionId(), obj.getJobType());
		
		//for logging to transaction logs
		String logData = null;
		Date startTime = new Date();
		String transactionStatus = null;
		
		this.setState(obj.getJobType());
		
		try{
			//reset error flag to false
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), false, uploadJobService);
			
			if(checkIfError(obj)){
				//if transaction has error in one of the status, reprocess count will be incremented
				logger.info("has error. reprocess count will be appended.:{}", obj.getTransactionId());
				obj = updateRepProcessCount(obj.getWorkflowJobId());
				logger.info("after count has been appended {}:{}", obj.getJobReprocessCount(), obj.getTransactionId());
	
			}else if(StringUtils.equals(obj.getInvestigationStatus(), "02") || StringUtils.equals(obj.getInvestigationStatus(), "04") || StringUtils.equals(obj.getInvestigationStatus(), "05")){
				//if transaction is to be deleted,cancelled or after investigation
				logger.info("transaction after investigation decision:{}", obj.getTransactionId());
				if(StringUtils.equals(obj.getInvestigationStatus(), "02")){
					
					updateStatus(obj.getWorkflowJobId(), STATUS_NO_REQUIRED, AFIS_DELETE);
	
					//23-Aug-2013 added logic for after investigation to set complete to cpd
					if (StringUtils.equals(obj.getInvestigationType(), "CPD")){
						updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, CPD);
					}else if (StringUtils.equals(obj.getInvestigationType(), "AFIS") && StringUtils.equals(obj.getAfisCheckStatus(), "03") ){
						updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, AFIS_SCREEN);				
					}else if (StringUtils.equals(obj.getInvestigationType(), "AFIS") && StringUtils.equals(obj.getAfisVerifyStatus(), "03") ){
						updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, AFIS_VERIFY);				
					}
					//end
					
				}else if(StringUtils.equals(obj.getInvestigationStatus(), "04") || StringUtils.equals(obj.getInvestigationStatus(), "05")){
					this.setState(DELETE);
				}
				//updateStatus(obj.getWorkflowJobId(), "", INVESTIGATION);
			}

		} catch(Exception e) {
			logger.error("EXCEPTION occured in Init command", e);
			logData = MiscXmlConvertor.parseObjectToXml(e);
			transactionStatus = JOB_STATE_INIT+STAGE_ERROR;
			this.setState(GOTO_ERROR_CMD);
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true, uploadJobService);
		} finally {
			try {
				//create transaction log entry
				if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
					uploadJobService.updateJobStatus(obj.getWorkflowJobId(), transactionStatus);
					this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_INIT, transactionStatus, startTime, new Date(), null, logData);
				}

			} catch (Exception e) {
				logger.error("Exception in finally block (NicInitialCommand finally clause)", e);
			}
		}
	}
	
	//method to check if there are status with error within the transaction
	private boolean checkIfError(NicUploadJob job){
		
		boolean cpdCheckStatus = StringUtils.equals(job.getCpdCheckStatus(), "09");
		boolean afisRegisterStatus = StringUtils.equals(job.getAfisRegisterStatus(), "09");
		boolean afisCheckStatus = StringUtils.equals(job.getAfisCheckStatus(), "09");
		boolean dataPreparationStatus = false; //StringUtils.equals(job.getDataPreparationStatus(), "09");
		boolean persoRegisterStatus = StringUtils.equals(job.getPersoRegisterStatus(), "09");
		boolean syncTxCpdStatus = false; //StringUtils.equals(job.getSyncTxCpdStatus(), "09");
		boolean syncInventoryStatus = false; //StringUtils.equals(job.getSyncInventoryStatus(), "09");
		boolean syncCardCpdStatus = false; //StringUtils.equals(job.getSyncCardCpdStatus(), "09");
		boolean afisVerifyStatus =  StringUtils.equals(job.getAfisVerifyStatus(), "09");
		
		if(cpdCheckStatus || afisRegisterStatus  || afisCheckStatus || dataPreparationStatus ||
				persoRegisterStatus || syncTxCpdStatus || syncInventoryStatus || syncCardCpdStatus || afisVerifyStatus){
			
			return true;
			
		}else{
			return false;
		}
		
	}
	
	//method to call service to update reprocess count by 1
	private NicUploadJob updateRepProcessCount(long objId) throws NicUploadJobServiceException{
		try {
			return uploadJobService.updateProcessNum(objId);
		} catch (NicUploadJobServiceException e) {
			throw new NicUploadJobServiceException(e);
			
		}
	}
	
	private void updateStatus(long objId, String state, int command) throws Exception {
		try {
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	
	private void saveTransactionLog(String transactionId, String transactionStage,String transactionStatus, Date startTime, Date endTime, String logInfo, String logData) {
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
		synchronized (NicTransactionLog.class) {
			nicTransactionLogService.save(transactionLog);
		}
	}
	
	public void setCommandUtil (NicCommandUtil commandUtil) {
		this.nicCommandUtil = commandUtil;
	}
	public void setUploadJobService(NicUploadJobService uploadJobService) {
		this.uploadJobService = uploadJobService;
	}
	public void setNicTransactionLogService(TransactionLogService nicTransactionLogService) {
		this.nicTransactionLogService = nicTransactionLogService;
	}
	
	
}
