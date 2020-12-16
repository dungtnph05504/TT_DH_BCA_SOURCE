package com.nec.asia.nic.comp.job.command;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.idserver.agent.service.exception.IdserverAgentServiceException;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.NicAfisDataServiceException;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.util.MiscXmlConvertor;

/**
 * @author JohnPaul_Millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : July 16, 2013
 * <p></p>
 */
/* 
 * Modification History:
 * 
 * 07 Oct 2013 (jp): add status change to NIC_REJECTION 
 * 28 Oct 2013 (jp): change state DELETE to AFIS_DELETE
 * 09 Jan 2014 (jp): added perso in the investigation type
 * 28 Feb 2014 (jp): Skip creation of transaction log for CPD cases which does not require deletion from AFIS.
 * 28 Feb 2014 (jp): Change Deletion completed status (02) into not required (-1)
 * 12 Mar 2014 (jp): update status to D in NIC_AFIS_DATA table
 * 07 Apr 2014 (jp): handle cancellation cases (investigation status)
 */

public class NicDeleteCommand extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{
	
	//private static final Logger logger = Logger.getLogger(NicDeleteCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	public static final String JOB_STATE_DELETE = "AFIS_DELETE";
	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	public static final int AFIS_DELETE = 10;
	private String SYSTEM_NAME =  "NIC";
	public static final String PERSO= "PERSO";
	public static final String AFIS= "AFIS";
	/* 
	 * Modification History:
	 * 
	 * 07 Oct 2013 (jp): add status change to NIC_REJECTION 
	 * 28 Oct 2013 (jp): change state DELETE to AFIS_DELETE
	 * 09 Jan 2014 (jp): added perso in the investigation type
	 * 28 Feb 2014 (jp): Skip creation of transaction log for CPD cases which does not require deletion from AFIS.
	 * 28 Feb 2014 (jp): Change Deletion completed status (02) into not required (-1)
	 * 12 Mar 2014 (jp): update status to D in NIC_AFIS_DATA table
	 */
	public static final String JOB_STATE_REJECTION= "NIC_REJECTION";
	
	
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
	private IdserverAgentService idserverAgentService;
	
	private NicAfisDataService nicAfisDataService;
	
	String transactionData = "";
	
	@Override
	public void doSomething(NicUploadJob obj) {
		logger.info("\n inside deleteCommand:"+obj.getTransactionId());
		this.setState("DELETE");
				//for logging to transaction logs
				String logData = null;
				Date startTime = new Date();
				String transactionStatus = null;
				//
		
		try {
			logger.debug("\n flag :"+obj.getInvestigationType());
			updateStatus(obj.getWorkflowJobId(), JOB_STATE_REJECTION, JOB_STATE);
			
			if(StringUtils.equalsIgnoreCase(obj.getInvestigationType(),AFIS) || StringUtils.equalsIgnoreCase(obj.getInvestigationType(),PERSO) ){
				logger.debug("\n investigation type is "+obj.getInvestigationType());
				
				
				//do afis registration deletion
				idserverAgentService.deleteRegistration(SYSTEM_NAME, obj.getTransactionId());
				updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, AFIS_DELETE);
				
				//12 Mar 2014 (jp): update status to D in NIC_AFIS_DATA table
				updateAfisRefStatus(obj.getTransactionId());
				
				
				transactionStatus = JOB_STATE_DELETE+STAGE_COMPLETED;
				
				
			}else{
				//do nothing
				logger.debug("\n flag deletion not afis, no action:"+obj.getWorkflowJobId()+"-"+STATUS_COMPLETED+"-"+AFIS_DELETE);
				
				//28FEB2014 (jp) - change status completed to status no required as afis delete status if txn is CPD - remove transaction log
				updateStatus(obj.getWorkflowJobId(), STATUS_NO_REQUIRED, AFIS_DELETE);
				transactionData = "CPD case. AFIS deletion not required.";
				//transactionStatus = JOB_STATE_DELETE+STAGE_COMPLETED;
				//28FEB2014 (jp) - ends

				logger.debug("\n after update status"); 
			}

		} catch(IdserverAgentServiceException ie) {
			logger.error("flag IdserverAgentServiceException job id:"+obj.getWorkflowJobId());
			ie.printStackTrace();
			transactionStatus = JOB_STATE_DELETE+STAGE_ERROR;
			logData = MiscXmlConvertor.parseObjectToXml(ie);
			this.setState(GOTO_ERROR_CMD);
			updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, AFIS_DELETE);
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true, uploadJobService);
		} catch (Exception e) {
			logger.error("flag EXCEPTION job id:"+obj.getWorkflowJobId());
			e.printStackTrace();
			transactionStatus = JOB_STATE_DELETE+STAGE_ERROR;
			logData = MiscXmlConvertor.parseObjectToXml(e);
			this.setState(GOTO_ERROR_CMD);
			updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, AFIS_DELETE);
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true, uploadJobService);
		}finally {
			try {
				logger.debug("\n finally clause:"+transactionStatus+""+obj.getTransactionId()+""+startTime+""+logData);
				if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotBlank(transactionStatus)) {
					uploadJobService.updateJobStatus(obj.getWorkflowJobId(), transactionStatus);
					this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_DELETE, transactionStatus, startTime, new Date(), transactionData, logData);
				}
			} catch (Exception e) {
				logger.error("Exception in finally block (NicVerifyCpdCommand.doSomething)", e);
			}
		}
	}
	
	
		//update status to D in NIC_AFIS_DATA table
		private void updateAfisRefStatus(String transactionId) throws Exception{
			try{
				
				NicAfisData afisData = nicAfisDataService.findById(transactionId);
				
				if (afisData == null){
					throw new NicAfisDataServiceException("transaction id "+ transactionId +" is not found in NIC_AFIS_DATA table.");
				}else{
				
				afisData.setStatus(NicAfisData.STATUS_DELETED);
				afisData.setUpdateDatetime(new Date());
				
				nicAfisDataService.saveAfisRefId(afisData);
				
				}
				
			}catch(NicAfisDataServiceException aex){
				throw new NicAfisDataServiceException(aex);
			}catch(Exception ex){
				throw new Exception(ex);
			}
		}
	
	
	private void updateStatus(long objId, String state, int command) {
		
			try {
				uploadJobService.updateJobState(objId, state, command);
				
			} catch (NicUploadJobServiceException e) {
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

	public IdserverAgentService getIdserverAgentService() {
		return idserverAgentService;
	}

	public void setIdserverAgentService(IdserverAgentService idserverAgentService) {
		this.idserverAgentService = idserverAgentService;
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
	public NicAfisDataService getNicAfisDataService() {
		return nicAfisDataService;
	}
	public void setNicAfisDataService(NicAfisDataService nicAfisDataService) {
		this.nicAfisDataService = nicAfisDataService;
	}
}
