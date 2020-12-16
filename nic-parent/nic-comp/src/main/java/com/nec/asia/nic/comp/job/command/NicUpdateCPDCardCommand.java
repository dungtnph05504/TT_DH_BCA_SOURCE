package com.nec.asia.nic.comp.job.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.job.command.exception.NicCommandException;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.IssuanceDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.util.MiscXmlConvertor;

/**
 * @author johnpaul_millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Aug 15, 2013
 * 
 */

/* 
 * Modification History:
 *  
 * 07 Feb 2014 (jp): add syncCardStatus
 * 
 */

public class NicUpdateCPDCardCommand extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	
	private static final Logger logger = Logger.getLogger(NicUpdateCPDCardCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	
	public static final int SYNC_CARD_CPD = 11;
	
	//JOB STATE
	public static final String JOB_STATE_SYNC_CARD_CPD= "SYNC_CARD_CPD";//SYNC_CARD_CPD
	
	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
//	private CitizenRefService citizenRefService;
	private IssuanceDataService nicIssuanceDataService;
	
	
	
	@Override
	public void doSomething(NicUploadJob obj) {
		logger.info("\n inside NicUpdateCPDCardCommand\n");
		
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
//			updateStatus(obj.getUploadJobId(), JOB_STATE_SYNC_CARD_CPD, JOB_STATE);
//
//			if(StringUtils.equals(obj.getSyncCardCpdStatus(), STATUS_COMPLETED)){
//				
//				logger.info("NicUpdateCPDCardCommand already completed. skipped.");
//
//			} else {
//				
//				updateStatus(obj.getUploadJobId(), STATUS_INPROGRESS, SYNC_CARD_CPD);
//				boolean hasIssuanceData = true;
//				boolean hasTransDocs = false;
//				boolean hasRegData = false;
//				boolean hasTransPayment = false;
//				transObj = nicTransactionService.findById(obj.getTransactionId(), hasIssuanceData, hasTransDocs, hasRegData, hasTransPayment);//has issuance data
//				
//				logger.info("before updating cpd card:"+obj.getCcn());
//				
//				//NicIssuanceData issuanceData = sortIssuanceList(transObj.getNicIssuanceDatas());
//				
//				
//				if(StringUtils.isEmpty(obj.getCcn())){
//					throw new NicCommandException("CCN is not found for:"+obj.getTransactionId()+"(job id:"+obj.getUploadJobId()+").");
//				}
//				
//				
//				//List<NicIssuanceData> issuanceDatas = nicIssuanceDataService.findByCcn(obj.getCcn());
//				List<NicIssuanceData> issuanceDatas = nicIssuanceDataService.findIssuanceData(null,obj.getCcn(),null);
//				NicIssuanceData issuanceData;
//
//				logger.info("issuanceDatas size:"+issuanceDatas.size());
//
//				if(issuanceDatas.size() == 0){
//					throw new NicCommandException("Issuance data for ccn:"+obj.getCcn()+" not found.");
//				}else{
//					issuanceData = issuanceDatas.get(0);
//				}
//
//				logger.info("issuanceData transaction id:"+issuanceData.getNicTransaction().getTransactionId());
//
//				updateCPDCard(issuanceData,issuanceData.getNicTransaction().getTransactionId());
//
//				//07 Feb 2014 (jp): add syncCardStatus
//				syncCardStatus(issuanceData.getNin(), issuanceData.getCcn(), getCardStatus(obj.getJobType()), issuanceData.getIssuanceOfficerId(), issuanceData.getCardStatusChangeReason(), obj.getTransactionId(), transObj.getTransactionType());
//
//				updateStatus(obj.getUploadJobId(), STATUS_COMPLETED, SYNC_CARD_CPD);
//				transactionStatus = JOB_STATE_SYNC_CARD_CPD+STAGE_COMPLETED;
//
//				//02SEP2013 - JP - update nic_transaction.transaction_status field
//				//transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_CPDV2_UPDATED);
//				//nicTransactionService.saveOrUpdate(transObj);
//
//			}
//
//		} catch (Exception e) {
//
//			logger.error("flag EXCEPTION:"+e.getMessage());
//			e.printStackTrace();
//			this.setState(GOTO_ERROR_CMD);
//			transactionStatus = JOB_STATE_SYNC_CARD_CPD+STAGE_ERROR;
//			logData = MiscXmlConvertor.parseObjectToXml(e);
//			updateStatus(obj.getUploadJobId(), STATUS_ERROR, SYNC_CARD_CPD);
//			nicCommandUtil.setErrorFlag(obj.getUploadJobId(), true,uploadJobService);
//
//		} finally {
//				try {
//				
//					if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
//						uploadJobService.updateJobStatus(obj.getUploadJobId(), transactionStatus);
//						this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_SYNC_CARD_CPD, transactionStatus, startTime, new Date(), null, logData);
//					}
//			} catch (Exception e) {
//				logger.error("Exception in finally block (doSomething)", e);
//			}
//		}
	}
	
	private void updateCPDCard(NicIssuanceData nicIssuanceData, String transactionId) throws Exception{

		try {
			
			//citizenRefService.updateCardInfo(nicIssuanceData, transactionId);
			
			//updateCardStatus
		} catch (Exception e) {
			
			throw new Exception(e);
		}
	}
	
	private void syncCardStatus(String nin, String ccn, String cardStatus, String officerId, String cardStatusChangeReason, String transactionId, String transactionType) throws Exception{

		try {
			
			logger.info("---syncCardStatus:"+nin+"-"+ccn+"-"+cardStatus+"-"+cardStatusChangeReason);
			
			//citizenRefService.updateCardStatus(nin, ccn, cardStatus, cardStatusChangeReason, officerId, transactionId, transactionType);
			
		} catch (Exception e) {
			
			throw new NicCommandException(e.getMessage());
		}
	}
	
	
	//(LOST, REPLACEMENT, TERMINATION)
	
	private String getCardStatus(String jobType){
		
		String status = "";
		
		if(jobType.equalsIgnoreCase(CAR_ISS)){
			status = ACTIVE;
		}else if(jobType.equalsIgnoreCase(CAR_REA)){
			status = ACTIVE;
		}else if(jobType.equalsIgnoreCase(CAR_SUP)){
			status = SUSPENDED;
		}else if(jobType.equalsIgnoreCase(CAR_TER)){
			status = TERMINATED;
		}else{
			
		}
		
		return status;
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
			nicTransactionLogService.saveOrUpdate(transactionLog);
		}
	}
	
	
	//sort issuance data to return the latest one based on update date
	private NicIssuanceData sortIssuanceList(Set<NicIssuanceData> issuanceSet) throws Exception{
		
		
		
		NicIssuanceData issuanceLatest = null;
		
		
		if (issuanceSet ==  null){
			throw new Exception("Nic Issuance Data is null");
		}else if(issuanceSet.size() == 0){
			throw new Exception("Nic Issuance Data is empty");
		}else{
		
			logger.info("issuance data size:"+issuanceSet.size());
			
			ArrayList<NicIssuanceData> issuanceList = new ArrayList<NicIssuanceData>(issuanceSet);
			
			Collections.sort(issuanceList, new Comparator<NicIssuanceData>() {
				  public int compare(NicIssuanceData o1, NicIssuanceData o2) {
				      if (o1.getUpdateDate() == null || o2.getUpdateDate() == null)
				        return -1;
				      return o2.getUpdateDate().compareTo(o1.getUpdateDate());
				  }
				});
			
		issuanceLatest = issuanceList.get(0);
		
			
		}	
			
		return issuanceLatest;
		
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
	
	public IssuanceDataService getNicIssuanceDataService() {
		return nicIssuanceDataService;
	}

	public void setNicIssuanceDataService(IssuanceDataService nicIssuanceDataService) {
		this.nicIssuanceDataService = nicIssuanceDataService;
	}


}
