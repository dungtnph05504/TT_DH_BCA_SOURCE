package com.nec.asia.nic.comp.job.command;

import java.util.Date;

import net.sf.jasperreports.web.commands.CommandException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
//import com.nec.asia.nic.comp.trans.service.DataPackService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.DataPackServiceException;
import com.nec.asia.nic.util.MiscXmlConvertor;

/**
 * @author johnpaul_millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Jun 24, 2013
 * <p></p>
 */


/* 
 * Modification History:
 * 
 * 02 SEP 2013 - JP - update nic_transaction.transaction_status field
 * 23 OCT 2013 - JP - throw exception when NIC ID is null
 */


public class NicPreparePersoCommand  extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{
	
	
	private static final Logger logger = Logger.getLogger(NicPreparePersoCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	public static final int PREPARE_PERSO = 5;
		
	//JOB STATE
	public static final String JOB_STATE_DATA_PREPARATION= "DATA_PREPARATION";
	

	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
	//private DataPackService dataPackService;
		
	@Override
	public void doSomething(NicUploadJob obj) {
		
		//for logging to transaction logs
		String logData = null;
		Date startTime = new Date();
		String transactionStatus = null;
		//
		this.setState(obj.getJobType());//set job type as child key
		
		try {
//			NicTransaction transObj = new NicTransaction();
//			
//			updateStatus(obj.getWorkflowJobId(), JOB_STATE_DATA_PREPARATION, JOB_STATE);
//			
//			if(StringUtils.equals(obj.getDataPreparationStatus(), STATUS_COMPLETED)){
//				
//			//--PREPARE PERSO has been completed before, skip step
//				
//			}else {
//				
//				updateStatus(obj.getWorkflowJobId(), STATUS_INPROGRESS, PREPARE_PERSO);
//				transObj.setTransactionId(obj.getTransactionId());
//				transObj = nicTransactionService.findById(transObj.getTransactionId(), false, true, true, false);//has trans doc and registration data
//
//				preparePersoData(transObj);
//
//				transactionStatus = JOB_STATE_DATA_PREPARATION+STAGE_COMPLETED;
//				updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, PREPARE_PERSO);
//
//				uploadJobService.resetReprocessCount(obj.getWorkflowJobId(), 0);
//
//				//02SEP2013 - JP - update nic_transaction.transaction_status field
//				//transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_PERSO_DATA_PREPARED);
//				//nicTransactionService.saveOrUpdate(transObj);
//				
//				if(nicCommandUtil.validateTransactionStatusUpdate(obj.getTransactionId(), transObj.getTransactionStatus(), NicTransactionService.TRANSACTION_STATUS_NIC_PERSO_DATA_PREPARED)){
//					transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_PERSO_DATA_PREPARED);
//					nicTransactionService.saveOrUpdate(transObj);
//				}
//
//			}
		
		} catch (Exception e) {
			logger.error("flag EXCEPTION",e);
			e.printStackTrace();
			transactionStatus = JOB_STATE_DATA_PREPARATION+STAGE_ERROR;
			logData = MiscXmlConvertor.parseObjectToXml(e);
			
			this.setState(GOTO_ERROR_CMD);
			updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, PREPARE_PERSO);
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true, uploadJobService);
			
		}finally {
			try {
				if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
					uploadJobService.updateJobStatus(obj.getWorkflowJobId(), transactionStatus);
					this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_DATA_PREPARATION, transactionStatus, startTime, new Date(), null, logData);
				}
			} catch (Exception e) {
				
			}
		}
	}
	
	private void preparePersoData(NicTransaction transObj) throws Exception{
		
//		try {
//			//23OCT2013 - JP - throw exception on null NIC ID
//			if(transObj.getNicRegistrationData() != null && ObjectUtils.equals(transObj.getNicRegistrationData().getNicId(), null)){
//				throw new CommandException("NIC ID cannot be null");
//			}else{
//				dataPackService.preparePersoData(transObj);
//			}
//		} catch (DataPackServiceException de) {
//			logger.error("[Error in NicPreparePersoCommand preparePersoData]", de);
//			throw de;
//		} catch (Exception e){
//			logger.error("[Exception in NicPreparePersoCommand preparePersoData]", e);
//			throw e;
//		}
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
		//
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		//
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
			nicTransactionLogService.saveOrUpdate(transactionLog);
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

//	public DataPackService getDataPackService() {
//		return dataPackService;
//	}
//
//	public void setDataPackService(DataPackService dataPackService) {
//		this.dataPackService = dataPackService;
//	}
}
