package com.nec.asia.nic.comp.job.command;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.job.command.exception.NicCommandException;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
@Deprecated
public class NicCardIssuanceCommand extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{
	
	/* 
	 * Modification History:
	 *  
	 * 07 Feb 2014 (jp): Deprecated - Command is no longer being used due to change in issuance flow
	 * 
	 */
	
	private static final Logger logger = Logger.getLogger(NicUpdateCpdRefDbCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	//UPDATE STATUS CODE
	public static final int JOB_STATE = 1;
	
	public static final int CARD_ISSUANCE = 11;//SYNC_CARD_CPD
	
	//JOB STATE
	public static final String JOB_STATE_CARD_ISSUANCE= "CARD_ISSUANCE";//SYNC_CARD_CPD
	
	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
//	private CitizenRefService citizenRefService;
	
	@Deprecated
	@Override
	public void doSomething(NicUploadJob obj) {
		logger.info("\n inside NicCardIssuanceCommand\n");
		
//		//for logging to transaction logs
//		String logData = null;
//		Date startTime = new Date();
//		String transactionStatus = null;
//		
//		
//		this.setState(obj.getJobType());//set job type as child key
//		
//		try {
//			
//			NicTransaction transObj = new NicTransaction();
//			updateStatus(obj.getWorkflowJobId(), JOB_STATE_CARD_ISSUANCE, JOB_STATE);
//			
//			if(StringUtils.equals(obj.getSyncCardCpdStatus(), STATUS_COMPLETED)){
//				logger.info("already completed. skipped.\n---\n---");
//			}else {
//				
//				updateStatus(obj.getWorkflowJobId(), STATUS_INPROGRESS, CARD_ISSUANCE);
//				transObj = nicTransactionService.findById(obj.getTransactionId(), true, false, true, false);//has issuance and reg data
//				
//				//TODO update card issuance
//				
//				
////				updateCardInfo(transObj.getNicIssuanceDatas(), obj.getTransactionId());
//				
//				
//				updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, CARD_ISSUANCE);
//				transactionStatus = JOB_STATE_CARD_ISSUANCE+STAGE_COMPLETED;
//				
//			}
//		
//		} catch (Exception e) {
//			logger.error("flag EXCEPTION:"+e.getMessage());
//			e.printStackTrace();
//			this.setState(GOTO_ERROR_CMD);
//			transactionStatus = JOB_STATE_CARD_ISSUANCE+STAGE_ERROR;
//			updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, CARD_ISSUANCE);
//			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true, uploadJobService);
//		} finally {
//			
//			try {
//			
//				if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
//					this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_CARD_ISSUANCE, transactionStatus, startTime, new Date(), null, logData);
//				}
//				
//			} catch (Exception e) {
//				logger.error("Exception in finally block (NicCardIssuanceCommand.doSomething)", e);
//			}
//		}
//		
		
	}
	
	@Deprecated
	private void updateCardInfo(Set<NicIssuanceData> issuanceDatas, String transId) throws Exception{
		//TODO stub method
		try {
			
			if(issuanceDatas != null){
			
				for(NicIssuanceData nicObj: issuanceDatas){
//					citizenRefService.updateCardInfo(nicObj, transId);
				}
				
			
			}else{
				throw new NicCommandException("Issuance Data is null");
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private void updateStatus(long objId, String state, int command){
		try {
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			e.printStackTrace();
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
	
	
	public NicTransactionService getNicTransactionService() {
		return nicTransactionService;
	}

	public void setNicTransactionService(NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}

	public NicUploadJobService getUploadJobService() {
		return uploadJobService;
	}

	public void setUploadJobService(NicUploadJobService uploadJobService) {
		this.uploadJobService = uploadJobService;
	}

	public TransactionLogService getNicTransactionLogService() {
		return nicTransactionLogService;
	}

	public void setNicTransactionLogService(
			TransactionLogService nicTransactionLogService) {
		this.nicTransactionLogService = nicTransactionLogService;
	}
//	public CitizenRefService getCitizenRefService() {
//		return citizenRefService;
//	}
//
//	public void setCitizenRefService(CitizenRefService citizenRefService) {
//		this.citizenRefService = citizenRefService;
//	}



}
