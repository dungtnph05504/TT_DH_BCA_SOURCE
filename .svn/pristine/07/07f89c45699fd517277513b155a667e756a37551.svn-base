package com.nec.asia.nic.comp.job.command;


import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
//import com.nec.asia.nic.comp.trans.service.NicMainService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.util.MiscXmlConvertor;

/**
 * @author johnpaul_millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Nov 21, 2013
 * <p></p>
 */
/**
 * @deprecated do not applicable to current project
 */
@Deprecated
public class NicMainAndHistoryCommand extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	
	private static final Logger logger = Logger.getLogger(NicUpdateCPDCardCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	
	//JOB STATE
	public static final String JOB_STATE_MAIN_AND_HISTORY= "UPD_MAIN_AND_HISTORY";//SYNC_CARD_CPD
	
	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
//	private NicMainService nicMainService;
	

	@Override
	public void doSomething(NicUploadJob obj) {
		logger.info("\n inside NicMainAndHistoryCommand:"+obj.getTransactionId()+"\n");
		
		//for logging to transaction logs
		String logData = null;
		Date startTime = new Date();
		String transactionStatus = null;
		
		this.setState(obj.getJobType());//set job type as child key
		
		try {

//			NicTransaction transObj = new NicTransaction();
//			updateStatus(obj.getWorkflowJobId(), JOB_STATE_MAIN_AND_HISTORY, JOB_STATE);
//
//			
//				//updateStatus(obj.getWorkflowJobId(), STATUS_INPROGRESS, INVENTORY_SYNC);
//				boolean hasIssuanceData = true;
//				boolean hasTransDocs = true;
//				boolean hasRegData = true;
//				boolean hasTransPayment = true;
//				transObj = nicTransactionService.findById(obj.getTransactionId(), hasIssuanceData, hasTransDocs, hasRegData, hasTransPayment);//has issuance data
//				
//				//NicIssuanceData issuanceData = sortIssuanceList(transObj.getNicIssuanceDatas());
//								
//				logger.info("before update to main and history:"+transObj.getTransactionId());
//					
//				//[EPP] commented -- TODO 
////					nicMainService.updateNicMainAndHistory(transObj, obj.getCcn());
//				
//				logger.info("after update to main and history:"+transObj.getTransactionId());
//				
//				//updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, INVENTORY_SYNC);
//				transactionStatus = JOB_STATE_MAIN_AND_HISTORY+STAGE_COMPLETED;
//				
		} catch (Exception e) {

			logger.error("flag EXCEPTION:"+e.getMessage());
			e.printStackTrace();
			this.setState(GOTO_ERROR_CMD);
			transactionStatus = JOB_STATE_MAIN_AND_HISTORY+STAGE_ERROR;
			logData = MiscXmlConvertor.parseObjectToXml(e);
			//updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, INVENTORY_SYNC);
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true,uploadJobService);

		} finally {
			try {
				if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
					uploadJobService.updateJobStatus(obj.getWorkflowJobId(), transactionStatus);
					this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_MAIN_AND_HISTORY, transactionStatus, startTime, new Date(), null, logData);
				}
			} catch (Exception e) {
				logger.error("Exception in finally block (doSomething)", e);
			}
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
//	public NicMainService getNicMainService() {
//		return nicMainService;
//	}
//
//	public void setNicMainService(NicMainService nicMainService) {
//		this.nicMainService = nicMainService;
//	}
}
