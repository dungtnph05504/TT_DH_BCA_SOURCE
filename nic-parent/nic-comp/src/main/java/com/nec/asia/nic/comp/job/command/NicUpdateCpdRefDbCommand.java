package com.nec.asia.nic.comp.job.command;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.util.MiscXmlConvertor;

public class NicUpdateCpdRefDbCommand extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	
	private static final Logger logger = Logger.getLogger(NicUpdateCpdRefDbCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	
	public static final int SYNC_TX_CPD = 7;
	
	//JOB STATE
	public static final String JOB_STATE_SYNC_TX_CPD= "SYNC_TX_CPD";
	
	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
//	private CitizenRefService citizenRefService;
	
	@Override
	public void doSomething(NicUploadJob obj) {
		logger.info("\n inside NicUpdateCpdRefDbCommand\n");
		
//		//for logging to transaction logs
//		String logData = null;
//		Date startTime = new Date();
//		String transactionStatus = null;
//		
//		this.setState(obj.getJobType());//set job type as child key
//		
//		try {
//			
//			NicTransaction transObj = new NicTransaction();
//			updateStatus(obj.getUploadJobId(), JOB_STATE_SYNC_TX_CPD, JOB_STATE);
//			
//			if(StringUtils.equals(obj.getSyncTxCpdStatus(), STATUS_COMPLETED)){
//				logger.info("NicUpdateCpdRefDbCommand already completed. skipped.");
//
////			} else if(StringUtils.equalsIgnoreCase(obj.getJobType(), "PAR_UPD") && (transObj != null && StringUtils.equalsIgnoreCase(transObj.getTransactionSubtype(), "PAR_UPD_FP"))){
////				updateStatus(obj.getUploadJobId(), STATUS_COMPLETED, SYNC_TX_CPD);
////				logger.info("\n-----PAR_UPD_FP, skipping step");
////				
//			}else {
//				
//				updateStatus(obj.getUploadJobId(), STATUS_INPROGRESS, SYNC_TX_CPD);
//				transObj = nicTransactionService.findById(obj.getTransactionId(), false, true, true, false);//has transaction doc and registration data
//				
//				
//				logger.info("before updating cpd:"+transObj);
//				logger.info("before updating cpd trans doc size:"+transObj.getNicTransactionAttachments().size());
//				logger.info("before updating cpd reg data:"+transObj.getNicRegistrationData());
//				
//				updateCPD(transObj);
//				
//				updateStatus(obj.getUploadJobId(), STATUS_COMPLETED, SYNC_TX_CPD);
//				transactionStatus = JOB_STATE_SYNC_TX_CPD+STAGE_COMPLETED;
//				
//				//02SEP2013 - JP - update nic_transaction.transaction_status field
//				//NIC_CPDV2_UPDATED no longer used
////				if(nicCommandUtil.validateTransactionStatusUpdate(obj.getTransactionId(), transObj.getTransactionStatus(), NicTransactionService.TRANSACTION_STATUS_NIC_CPDV2_UPDATED)){
////					transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_CPDV2_UPDATED);
////					nicTransactionService.saveOrUpdate(transObj);
////				}
//				
//			}
//		
//		} catch (Exception e) {
//			
//			logger.error("flag EXCEPTION:"+e.getMessage());
//			e.printStackTrace();
//			this.setState(GOTO_ERROR_CMD);
//			transactionStatus = JOB_STATE_SYNC_TX_CPD+STAGE_ERROR;
//			logData = MiscXmlConvertor.parseObjectToXml(e);
//			updateStatus(obj.getUploadJobId(), STATUS_ERROR, SYNC_TX_CPD);
//			nicCommandUtil.setErrorFlag(obj.getUploadJobId(), true, uploadJobService);
//
//		} finally {
//
//			try {
//			
//				if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
//					uploadJobService.updateJobStatus(obj.getUploadJobId(), transactionStatus);
//					this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_SYNC_TX_CPD, transactionStatus, startTime, new Date(), null, logData);
//				}
//
//			} catch (Exception e) {
//				logger.error("Exception in finally block (doSomething)", e);
//			}
//		}
	}
	
	private void updateCPD(NicTransaction transObj) throws Exception{

		try {
			
			//citizenRefService.updateTransactionInfo(transObj);
			
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
