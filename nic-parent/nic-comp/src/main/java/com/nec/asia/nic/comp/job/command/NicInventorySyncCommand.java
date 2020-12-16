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
import com.nec.asia.nic.comp.trans.service.InventoryRefService;
import com.nec.asia.nic.comp.trans.service.IssuanceDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.util.MiscXmlConvertor;

/**
 * @author johnpaul_millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Nov 5, 2013
 * <p></p>
 */
public class NicInventorySyncCommand extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	
	private static final Logger logger = Logger.getLogger(NicUpdateCPDCardCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	public static final int INVENTORY_SYNC = 8;
	
	//JOB STATE
	public static final String JOB_STATE_INVENTORY_SYNC= "INVENTORY_SYNC";//SYNC_CARD_CPD
	public static final String STATUS_INVENTORY_SYNC= "NIC_INVENTORY_SYNC_COMPLETED";//SYNC_CARD_CPD
	
//	public static final String CAR_ISS = "CAR_ISS";
//	public static final String CAR_TER = "CAR_TER";
//	public static final String CAR_REA = "CAR_REA";
//	public static final String CAR_SUP = "CAR_SUP";
//	
//	public static final String ACTIVE = "ACTIVE";
//	public static final String TERMINATED = "TERMINATED";
//	public static final String SUSPENDED = "SUSPENDED";
	//ACTIVE
	//TERMINATED
	//SUSPENDED
	
	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
	private InventoryRefService inventoryRefService;
	private IssuanceDataService nicIssuanceDataService;
	

	@Override
	public void doSomething(NicUploadJob obj) {
		logger.info("inside NicInventorySyncCommand");
		
		//for logging to transaction logs
		String logData = null;
		Date startTime = new Date();
		String transactionStatus = null;
		
		this.setState(obj.getJobType());//set job type as child key
		
		try {
//
//			NicTransaction transObj = new NicTransaction();
//			updateStatus(obj.getWorkflowJobId(), JOB_STATE_INVENTORY_SYNC, JOB_STATE);
//
//			if(StringUtils.equals(obj.getSyncInventoryStatus(), STATUS_COMPLETED)){
//				
//				logger.info("NicInventorySyncCommand already completed. skipped.");
//
//			} else {
//				
//				updateStatus(obj.getWorkflowJobId(), STATUS_INPROGRESS, INVENTORY_SYNC);
//				boolean hasIssuanceData = true;
//				boolean hasTransDocs = false;
//				boolean hasRegData = true;
//				boolean hasTransPayment = false;
//				transObj = nicTransactionService.findById(obj.getTransactionId(), hasIssuanceData, hasTransDocs, hasRegData, hasTransPayment);//has issuance data
//				
//				//NicIssuanceData issuanceData = sortIssuanceList(transObj.getNicIssuanceDatas());
//				
//				List<NicIssuanceData> issuanceDatas = nicIssuanceDataService.findByCcn(obj.getCcn());
//				NicIssuanceData issuanceData; 
//				
//				logger.info("issuanceDatas:"+((issuanceDatas==null)?0:issuanceDatas.size()));
//					
//				
//				
//				if(issuanceDatas.size() == 0){
//					throw new NicCommandException("Issuance data for ccn:"+obj.getCcn()+" not found.");
//				}else{
//					issuanceData = issuanceDatas.get(0);
//				}
//				
//				logger.info("before sync inventory:"+transObj.getTransactionId());
//				syncInventory(issuanceData.getCcn(), getCardStatus(obj.getJobType()), issuanceData.getIssuanceOfficerId(), issuanceData.getCreateWkstnId(), transObj.getIssSiteCode(), transObj.getTransactionStatus());
//				//logger.info("after sync inventory:"+transObj.getTransactionId());
//				//syncInventory(String ccn, String cardStatus, String officerId, String workstationId, String siteCode)
//				
//				updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, INVENTORY_SYNC);
//				transactionStatus = JOB_STATE_INVENTORY_SYNC+STAGE_COMPLETED;
//				
//				//02SEP2013 - JP - update nic_transaction.transaction_status field
//				//transObj.setTransactionStatus(STATUS_INVENTORY_SYNC);
//				//nicTransactionService.saveOrUpdate(transObj);
//
//			}
//		
		} catch (Exception e) {

			logger.error("flag NicInventorySyncCommand EXCEPTION:"+e.getMessage());
			e.printStackTrace();
			this.setState(GOTO_ERROR_CMD);
			transactionStatus = JOB_STATE_INVENTORY_SYNC+STAGE_ERROR;
			logData = MiscXmlConvertor.parseObjectToXml(e);
			updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, INVENTORY_SYNC);
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true,uploadJobService);

		} finally {
				try {
				
					if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
						uploadJobService.updateJobStatus(obj.getWorkflowJobId(), transactionStatus);
						this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_INVENTORY_SYNC, transactionStatus, startTime, new Date(), null, logData);
					}
			} catch (Exception e) {
				logger.error("Exception in finally block (doSomething)", e);
			}
		}
	}
	
	private void syncInventory(String ccn, String cardStatus, String officerId, String workstationId, String siteCode, String transactionStatus) throws Exception{

		try {
			
			
			//logger.info("x[Inventory][updateCardStatus]:"+ccn);
			//updateCardStatus(String ccn, String cardStatus, String officerId, String workstationId, String siteCode)
			//inventoryRefService.updateCardStatus(ccn, cardStatus, officerId, workstationId, siteCode);	
			
			//logger.info("x[Inventory][updateTransactionStatus]:"+transactionStatus);
			//updateTransactionStatus(String ccn, String transactionStatus, String officerId, String workstationId, String siteCode)
			//inventoryRefService.updateTransactionStatus(ccn,transactionStatus,officerId,workstationId, siteCode);
			
		} catch (Exception e) {
			
			throw new NicCommandException(e.getMessage());
		}
	}
	
	private void updateStatus(long objId, String state, int command){
		try {
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	
	public InventoryRefService getInventoryRefService() {
		return inventoryRefService;
	}

	public void setInventoryRefService(InventoryRefService inventoryRefService) {
		this.inventoryRefService = inventoryRefService;
	}
	
	public IssuanceDataService getNicIssuanceDataService() {
		return nicIssuanceDataService;
	}

	public void setNicIssuanceDataService(IssuanceDataService nicIssuanceDataService) {
		this.nicIssuanceDataService = nicIssuanceDataService;
	}

}
