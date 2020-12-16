package com.nec.asia.nic.comp.job.command;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.job.command.exception.NicCommandException;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicMain;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.IssuanceDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.util.MiscXmlConvertor;

public class NicDeactivateCardCommand extends  BaseCommand<NicUploadJob> implements Command<NicUploadJob>{
	
	/* 
	 * Modification History:
	 *  
	 * 07 Nov 2013 (jp): to terminate only if card status is active, add updating with nicIssuanceDataService, status = NIC_OLD_CARD_DEACTIVATED
	 * 12 Nov 2013 (jp): add input of cardStatus in updateTransactionByTransactionId to sync with service changes
	 * 27 Feb 2014 (jp): skip creating transaction logs when deactivation is not done for transaction
	 * 10 Apr 2014 (jp): added updating of status after completion
	 * 28 Apr 2014 (jp): created termination job after deactivation for REP_LOST cases
	 * 14 May 2014 (jp): fixed bug transaction log created even if not REP_LOST
	 * 06 Jun 2014 (cw): code clean up, termination job will be created within CardStatusUpdateService 
	 * 20 Jun 2014 (jp): add null checking for issuancedata in REP_LOST clause
	 * 03 Sep 2014 (cw): add null checking for issuancedata.ccn, issuancedata.cardstatus
	 */
	
	private static final Logger logger = Logger.getLogger(NicDeactivateCardCommand.class);
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	//JOB TYPE
	public static final String NEW_REGISTRATION = "REG";
	public static final String REPLACEMENT = "REP";
	public static final String PARTICULARS_UPDATE = "PAR_UPD";
	public static final String CONVERSION = "CON";
	
	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	public static final int CARD_DEACTIVATE = 11;
	
	//JOB STATE
	public static final String JOB_STATE_DEACTIVATE_CARD= "NIC_OLD_CARD_DEACTIVATED";//"SYNC_CARD_CPD";
	public static final String NIC_OLD_CARD_DEACTIVATED = "NIC_OLD_CARD_DEACTIVATED";
	public static final String JOB_TYPE_CARD_TERMINATION = "CAR_TER";
	
	private NicTransactionService nicTransactionService;

	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
	
	private IssuanceDataService nicIssuanceDataService;
	
	
	@Override
	public void doSomething(NicUploadJob obj) {
		logger.info("\n inside NicDeactivateCardCommand:"+obj.getTransactionId());
		//for logging to transaction logs
		String logData = null;
		Date startTime = new Date();
		String transactionStatus = null;
		//
		String userId;
		String workstationId;
		NicMain mainObj = null;
			
		this.setState(obj.getJobType());//set job type as child key
//
//		try {
//			NicTransaction transObj = new NicTransaction();
//			updateStatus(obj.getUploadJobId(), JOB_STATE_DEACTIVATE_CARD, JOB_STATE);
//			
//			if(StringUtils.equals(obj.getSyncCardCpdStatus(), STATUS_COMPLETED)){
//				//transactionStatus = JOB_STATE_DEACTIVATE_CARD+STAGE_COMPLETED;
//				//skipped and not need to insert transaction log.
//			} else {
//				updateStatus(obj.getUploadJobId(), STATUS_INPROGRESS, CARD_DEACTIVATE);
//				transObj = nicTransactionService.findById(obj.getTransactionId(), false, false, true, false);
//				if(transObj == null || transObj.getNicRegistrationData() == null){
//					throw new NicCommandException("No Registration Data found for "+obj.getTransactionId());
//				}
//				logger.info("transObj is:"+transObj);
//				mainObj = nicMainService.findById(transObj.getNicRegistrationData().getNicId());
//				logger.info("after getting mainObj:"+mainObj);
//				
//				if(mainObj == null || transObj==null){
//					logger.info("transObj is:"+transObj);
//					logger.info("mainObj is:"+mainObj);
//				} else {
//					String subtype = transObj.getTransactionSubtype();
//					logger.info("subtype is :"+subtype);
//					//if lost - starts
//					if(StringUtils.equalsIgnoreCase(subtype, "REP_LOST")){
//						logger.info("Transaction is REP_LOST");
//						String csUpdate = NicChangeStatusLog.CARD_STATUS_TERMINATED;
//						String cscrUpdate = NicChangeStatusLog.CSCR_TERMINATION_DUE_TO_REPLACEMENT_OF_LOST_CARD;
//						Date csctUpdate = new Date();
//						userId = obj.getUpdateBy();
//						workstationId = obj.getUpdateWkstnId();
//						boolean hasActiveCard = false;
//						List<NicTransaction> transByNin = nicTransactionService.findAllByNin(transObj.getNin());
//						
//						for(NicTransaction nicTrans:transByNin ){
//							logger.info("transaction id:"+nicTrans.getTransactionId());
//							//skip itself.
//							if (StringUtils.equals(nicTrans.getTransactionId(), obj.getTransactionId()))	
//								continue;
//							else {
//								//get Issuance data for prev transaction
//								NicIssuanceData issData = this.nicIssuanceDataService.getLatestNicIssuanceData(nicTrans.getTransactionId());
//								//to deactivate the current card if it is still not terminated
//								//if (issData !=null && !StringUtils.equalsIgnoreCase(issData.getCardStatus(), NicChangeStatusLog.CARD_STATUS_TERMINATED)) {
//								if (issData !=null && StringUtils.isNotBlank(issData.getCcn()) && StringUtils.isNotBlank(issData.getCardStatus()) && !StringUtils.equalsIgnoreCase(issData.getCardStatus(), NicChangeStatusLog.CARD_STATUS_TERMINATED)) { //2014 Sep 03
//									logger.info("transactionId:{"+issData.getId().getTransactionId()+"}, old cardstatus: {"+issData.getCardStatus()+"}, new cardstatus: {"+csUpdate+"}");
//									//update card status to TERMINATED
//									cardStatusUpdateService.updateCardStatus(issData.getId().getTransactionId(), issData.getCcn(), csUpdate, csctUpdate, cscrUpdate, userId, workstationId);
//									logger.info("before calling updateTransactionByTransactionId");
//									//update transaction status to NIC_OLD_CARD_DEACTIVATED
//									nicIssuanceDataService.updateTransactionByTransactionId(issData.getId().getTransactionId(), NIC_OLD_CARD_DEACTIVATED, csUpdate, userId, workstationId);
//									hasActiveCard = true;
//									if (StringUtils.isBlank(logData)) {
//										logData = "Deactivated (transactionId="+issData.getId().getTransactionId()+", ccn="+issData.getCcn()+")";
//									} else {
//										logData += ", (transactionId="+issData.getId().getTransactionId()+", ccn="+issData.getCcn()+")";
//									}
//								}
//							}
//						}//
//						//14 May 2014(jp) - moved creation of transaction log to inside the rep lost clause
//						if (hasActiveCard) {
//							transactionStatus = JOB_STATE_DEACTIVATE_CARD+STAGE_COMPLETED;
//							logger.info("active cards For nin:{"+transObj.getNin()+"} -- "+logData);
//						} else {
//							logger.info("not active card to deactivate for nin:{"+transObj.getNin()+"}");
//						}
//					}
//					//if lost - ends
//				
//					updateStatus(obj.getUploadJobId(), STATUS_COMPLETED, CARD_DEACTIVATE); 
//				}
//			}
//
//		} catch (Exception e) {
//			logger.error("flag EXCEPTION:"+e.getMessage(), e);
//			transactionStatus = JOB_STATE_DEACTIVATE_CARD+STAGE_ERROR;
//			logData = MiscXmlConvertor.parseObjectToXml(e);
//			
//			this.setState(GOTO_ERROR_CMD);
//			nicCommandUtil.setErrorFlag(obj.getUploadJobId(), true, uploadJobService);
//			
//			updateStatus(obj.getUploadJobId(), STATUS_ERROR, CARD_DEACTIVATE);
//		} finally {
//			try {
//				if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
//					uploadJobService.updateJobStatus(obj.getUploadJobId(), transactionStatus);
//					this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_DEACTIVATE_CARD, transactionStatus, startTime, new Date(), null, logData);
//				}
//			} catch (Exception e) {
//				logger.error("Exception in finally block (NicDeactivateCardCommand.doSomething)", e);
//			}
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

	public void setNicTransactionLogService(TransactionLogService nicTransactionLogService) {
		this.nicTransactionLogService = nicTransactionLogService;
	}
	
	public IssuanceDataService getNicIssuanceDataService() {
		return nicIssuanceDataService;
	}

	public void setNicIssuanceDataService(IssuanceDataService nicIssuanceDataService) {
		this.nicIssuanceDataService = nicIssuanceDataService;
	}

}
