/**
 * 
 */
package com.nec.asia.nic.comp.trans.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.trans.dao.NicIssuanceDataDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceDataId;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.NicIssuanceDataDto;
import com.nec.asia.nic.comp.trans.dto.NicTransactionDto;
import com.nec.asia.nic.comp.trans.dto.ResponseDTO;

import com.nec.asia.nic.comp.trans.service.IssuanceDataService;
import com.nec.asia.nic.comp.trans.service.PersoService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.CardStatusUpdateServiceException;
import com.nec.asia.nic.comp.trans.service.exception.IssuanceServiceException;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.service.exception.PersoServiceException;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.nec.asia.nic.util.MiscXmlConvertor;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;

/**
 * @author setia_budiyono
 *
 */
/* 
 * Modification History:
 * 
 * 19 Sep 2013 (chris): to handle issuance officer id.
 *  1 Oct 2013 (chris): change constants from transaction_stage to transaction_status
 *  4 Oct 2013 (chris): to create card issuance job to sync card detail to CPD.
 * 22 Oct 2013 (chris): add findIssuanceData by packageId, ccn, nin
 * 22 Oct 2012 (chris): add transactionLog for updateReceivedCardStatus when batch_info downloaded by RIC/CC.
 * 30 Oct 2013 (chris): to rewrite code to create upload Job for card issuance, card termination, card suspension, card reactivation
 * 01 Nov 2013 (Swapna):added getLatestNicIssuanceData method.
 * 06 Nov 2013 (chris): to add input of cardStatus in updateTransactionByTransactionId
 * 06 Nov 2013 (jp): added findbyccn
 * 14 Nov 2013 (chris): to insert transaction log when update Card Status.
 * 15 Nov 2013 (chris): to integrate with persoWS
 * 10 Dec 2013 (Peddi Swapna): Added new methods to support the NIC Statistics modules during the code merge. 
 * 15 Jan 2014 (chris): add condition to create CAR_ISS job only when RIC_CARD_ISSUED.
 * 06 Feb 2014 (chris): add condition to prevent status override by earlier update.
 * 07 Feb 2014 (chris): add condition to handle suspend transaction.
 * 12 Feb 2014 (chris): fixed bug for status within same layer not updated, CAR_ISS duplicate job issue
 * 13 Feb 2014 (chris): to add ERROR_CODE for update status when transaction id not found
 * 24 Feb 2014 (chris): add condition to handle void (suspend & reject).
 * 12 Mar 2014 (chris): to filter findIssuanceData by sitecode
 * 04 Apr 2014 (chris): to add 'RIC_CARD_TRANSFERED' in status list
 * 02 Jun 2014 (chris): add method checkIfJobProcessing() for voidTransaction()
 * 06 Jun 2014 (chris): rename createJobForRepLost to createCardWorkflowJob, findByTransacionId to findByTransactionId
 * 18 Aug 2014 (chris): to update to save officer id, server id during update CardReceivedFlag
 * 05 Sep 2014 (chris): to insert log when error in updateTransactionByTransactionId()
 */
//@Service("nicIssuanceDataService")
//@Transactional
public class IssuanceDataServiceImp implements IssuanceDataService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NicIssuanceDataDao dao;

	@Autowired
	private NicUploadJobDao uploadJobDao = null; //4 Oct 2013 (chris)
	@Autowired
	private TransactionLogService nicTransactionLogService; //22 Oct 2013 (chris)
	@Autowired
	private NicTransactionDao transactionDao = null; //22 Oct 2013 (chris)
	@Autowired
	private PersoService persoService = null; //15 Nov 2013 (chris)
	
	public IssuanceDataServiceImp () {}
	
	
	@Override
	public NicIssuanceDataId saveIssuanceData(NicIssuanceData entity) throws IssuanceServiceException {
		entity.setCreateDate(new Date());
		try {
			return this.dao.save(entity);
			
		}catch (Exception dao){
			throw new IssuanceServiceException (dao);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.comp.trans.service.IssuanceDataService#updateIssuanceData(com.nec.asia.nic.comp.trans.domain.NicIssuanceData)
	 */
	@Override
	public NicIssuanceDataId updateIssuanceData(NicIssuanceData entity) throws IssuanceServiceException {
		entity.setUpdateDate(new Date());
	    try {
		    this.dao.saveOrUpdate(entity);
			return entity.getId();
	    }catch (Exception dao) {
	    	throw new IssuanceServiceException (dao);
	    }
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.comp.trans.service.IssuanceDataService#findByPeriod(java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override	
	public List<NicIssuanceData> findByPeriod(String siteCode, Date start, Date end) 
			throws IssuanceServiceException {
		
		try {
			return this.dao.findByPeriod(siteCode,start, end);			
		}catch (DaoException ex) {
			throw new IssuanceServiceException(ex);
		}
	}
	
	

	@Transactional(rollbackFor= IssuanceServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateStatusByCCn(String ccn, String cardStatus, String cardStatusChangeReason, Date cardStatusChangeTime, String officerId, String workstationId) throws IssuanceServiceException {
//		try {
//			List<NicIssuanceData> resultList =  this.dao.findDataByCCn(ccn);
//			if (CollectionUtils.isNotEmpty(resultList) ) {
//				String transactionId = resultList.get(0).getId().getTransactionId();
//				String currentCardStatus = resultList.get(0).getCardStatus();
//				if (StringUtils.equals(cardStatus, currentCardStatus)) {
//					logger.warn("Card[{}] status as {}, update is not required.", new Object[]{ccn, currentCardStatus});
//					return;
//				}
//				this.cardStatusUpdateService.updateCardStatus(transactionId, ccn, cardStatus, cardStatusChangeTime, cardStatusChangeReason, officerId, workstationId);
//			}
//		}catch (DaoException ex) {
//			throw new IssuanceServiceException (ex);
//		} catch (CardStatusUpdateServiceException e) {
//			throw new IssuanceServiceException (e);
//		}
	}
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public void updateStatusByPackageId(List<String> packageIdList, String status) throws IssuanceServiceException {
		try {
			this.dao.updateStatusByPackageId(packageIdList, status);
		}catch (DaoException ex) {
			throw new IssuanceServiceException (ex);
		}
	}
	
	@Transactional(rollbackFor= IssuanceServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateTransactionByCcn(String ccn, String transactionStatus, String officerId, String workstationId) throws IssuanceServiceException {
		try {
			String transactionId = null;
			List<NicIssuanceData> resultList =  this.dao.findDataByCCn(ccn);
			if (CollectionUtils.isNotEmpty(resultList) ) {
				transactionId = resultList.get(0).getId().getTransactionId();
				String currentTransactionStatus = transactionDao.getTransactionStatusById(transactionId);
				if (StringUtils.equals(transactionStatus, currentTransactionStatus)) {
					logger.warn("Transaction[{}] status as {}, update is not required.", new Object[]{transactionId, currentTransactionStatus});
					return;
				}
			}
			if (StringUtils.isNotBlank(transactionId)) {
				this.updateTransactionByTransactionId(transactionId, transactionStatus, null, officerId, workstationId);
				
				// 07 Apr 2013 (chris) to update destroy card status and delete afis records - start
				//if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_INVENTORY_CARD_RECEIVED) || StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_INVENTORY_CARD_DESTROYED)) {
				//	String jobType = JOB_TYPE_CARD_DESTROY;
				//	this.createJob(transactionId, jobType, ccn, null, transactionStatus, officerId, workstationId);
				//}
				// 07 Apr 2013 (chris) to update destroy card status and delete afis records - end
			}
			//this.dao.updateTransactionByCcn(ccn, transactionStatus, officerId, workstationId);
		}catch (DaoException ex) {
			throw new IssuanceServiceException (ex);
		}
	}
	
	@Transactional(rollbackFor= IssuanceServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateTransactionByTransactionId(String transactionId, String transactionStatus, String cardStatus, String officerId, String workstationId) throws IssuanceServiceException {
//		
//		Date startTime = new Date();
//		boolean error = false; //05 Sep 2014 (chris)
//		String logInfo = "";  //05 Sep 2014 (chris)
//		String logData = "";  //05 Sep 2014 (chris)
//		try {
//			String currentTransactionStatus = transactionDao.getTransactionStatusByIdForUpdate(transactionId);
//			if (StringUtils.equals(transactionStatus, currentTransactionStatus)) {
//				logger.warn("Transaction[{}] status as {}, update is not required.", new Object[]{transactionId, currentTransactionStatus});
//				return;
//			} else if (currentTransactionStatus==null) {
//				NicTransaction tempTransaction = transactionDao.findById(transactionId);
//				if (tempTransaction==null) {
//					logger.error("Transaction[{}] not found.", new Object[]{transactionId});
//					throw new IssuanceServiceException("Transaction Id not found.", UPDATE_TRANSACTION_STATUS_ERROR_CODE_TRANSACTION_ID_NOT_FOUND);
//				}
//			}
//			if (validateTransactionStatusUpdate(transactionId, currentTransactionStatus, transactionStatus)) {
//				this.transactionDao.updateStatusByTransactionId(transactionId, transactionStatus, officerId, workstationId);
//			}
//			//this.dao.updateStatusByTransactionId(transactionId, transactionStatus, officerId, workstationId);
//			
//			//19 Sep 2013 (chris): to handle issuance officer id - start
//			NicTransaction tempTransaction = new NicTransaction(transactionId);
//			//1 Oct 2013 (chris) - start
//			if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_CARD_ISSUED) 
//						|| StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_CARD_REJECTED)) {
//			//1 Oct 2013 (chris) - end
//				tempTransaction = this.dao.getTransactionIssuanceDatas(tempTransaction);
//				if (CollectionUtils.isNotEmpty(tempTransaction.getNicIssuanceDatas())) {
//					NicIssuanceData issDataToUpdate = null;
//					for (NicIssuanceData issData : tempTransaction.getNicIssuanceDatas()) {
//						if (issDataToUpdate==null) {
//							issDataToUpdate = issData;
//						} else if (issData.getCreateDate()!=null && issDataToUpdate.getCreateDate()!=null){
//							boolean isAfter = DateUtil.isAfterDate(issData.getCreateDate(), issDataToUpdate.getCreateDate());
//							if (isAfter) {
//								issDataToUpdate = issData;
//							}
//						}
//					}
//					//update the issucance officer and decision
//					if (issDataToUpdate!=null) {
//						Date collectionDate = DateUtil.strToDate(DateUtil.parseDate(DateUtil.getSystemDate(), DateUtil.FORMAT_DD_MM_YYYY), DateUtil.FORMAT_DD_MM_YYYY);
//						String issuanceDecision = StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_CARD_ISSUED) ? ISSUANCE_DECISION_ISSUED : ISSUANCE_DECISION_REJECTED;
//						issDataToUpdate.setCollectionDate(collectionDate);
//						issDataToUpdate.setIssuanceDecision(issuanceDecision);
//						issDataToUpdate.setIssuanceOfficerId(officerId);
//						//06 Feb 2014 (chris) : to update the issuance data as cancelled
//						if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_CARD_REJECTED)) {
//							issDataToUpdate.setCancelStatus(NicIssuanceData.CANCEL_STATUS_CANCELLED);
//							issDataToUpdate.setCancelReason(NicIssuanceData.CANCEL_REASON_REJECTED_BY_SITE);
//							issDataToUpdate.setUpdateBy(officerId);
//							issDataToUpdate.setUpdateWkstnId(workstationId);
//							issDataToUpdate.setUpdateDate(new Date());
//							logger.info("transactionId[{}], persoRefId[{}], ccn[{}] updating as cancelled : REJECTED_BY_SITE.", new Object[] {transactionId, issDataToUpdate.getId().getPersoRefId(), issDataToUpdate.getCcn()});
//						}
//						
//						this.dao.saveOrUpdate(issDataToUpdate);
//						
//						//4 Oct 2013 (chris) - start
//						//Create Nic Job for update cpd and update card inventory
//						if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_CARD_ISSUED)) {
//							String ccn = issDataToUpdate.getCcn();
//							//[chris][12 Feb 2014] To fix duplicate jobs created
//							String jobType = JOB_TYPE_CARD_ISSUANCE;
//							this.createJob(transactionId, jobType, ccn, null, transactionStatus, officerId, workstationId);
//							//[chris][12 Feb 2014] to fix duplicate jobs created 
//						}
//						//4 Oct 2013 (chris) - end
//					}
//				}
//			}
//			//30 Oct 2013 (chris): to rewrite code to create upload Job for card issuance, card termination, card suspension, card reactivation - start
//			else if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_EXT_CARD_TERMINATED) 
//					|| StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_CARD_DEACTIVATED) 
//					|| StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_CARD_REACTIVATED) ) {
//				String jobType = "";
//				// to handle termination
//				if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_EXT_CARD_TERMINATED) || StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_CARD_TERMINATED)) {
//					jobType = JOB_TYPE_CARD_TERMINATION;
//				} else if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_CARD_REACTIVATED)) {
//					jobType = JOB_TYPE_CARD_REACTIVATION;
//				} else if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_CARD_DEACTIVATED)) {
//					// to handle suspend and reactivation
//					if (StringUtils.equalsIgnoreCase(cardStatus, CardStatusUpdateService.CARD_STATUS_SUSPENDED)) {
//						jobType = JOB_TYPE_CARD_SUSPENSION;
//					} else if (StringUtils.equalsIgnoreCase(cardStatus, CardStatusUpdateService.CARD_STATUS_TERMINATED)) {
//						jobType = JOB_TYPE_CARD_TERMINATION;
//					}
//				}
//				
//				tempTransaction = this.dao.getTransactionIssuanceDatas(tempTransaction);
//				if (CollectionUtils.isNotEmpty(tempTransaction.getNicIssuanceDatas())) {
//					NicIssuanceData curIssData = null;
//					for (NicIssuanceData issData : tempTransaction.getNicIssuanceDatas()) {
//						if (curIssData==null) {
//							curIssData = issData;
//						} else if (issData.getCreateDate()!=null && curIssData.getCreateDate()!=null) {
//							boolean isAfter = DateUtil.isAfterDate(issData.getCreateDate(), curIssData.getCreateDate());
//							if (isAfter) {
//								curIssData = issData;
//							}
//						}
//					}
//					if (StringUtils.isNotBlank(jobType) && curIssData!=null) {
//						String ccn = curIssData.getCcn();
//						this.createJob(transactionId, jobType, ccn, curIssData, transactionStatus, officerId, workstationId);
//					}
//				}
//				
//			}
//			//30 Oct 2013 (chris): to rewrite code to create upload Job for card issuance, card termination, card suspension, card reactivation - end
//			//19 Sep 2013 (chris): to handle issuance officer id - end
//			//06 Feb 2014 (chris): to handle terminate on lost/damage card. - start
//			else if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_TERMINATE_LOST_CARD) 
//					|| StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_TERMINATE_DAMAGE_CARD) 
//					|| StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_VOID)) {				
//				tempTransaction = this.dao.getTransactionIssuanceDatas(tempTransaction);
//				if (CollectionUtils.isNotEmpty(tempTransaction.getNicIssuanceDatas())) {
//					NicIssuanceData curIssData = null;
//					for (NicIssuanceData issData : tempTransaction.getNicIssuanceDatas()) {
//						if (curIssData==null) {
//							curIssData = issData;
//						} else if (issData.getCreateDate()!=null && curIssData.getCreateDate()!=null) {
//							boolean isAfter = DateUtil.isAfterDate(issData.getCreateDate(), curIssData.getCreateDate());
//							if (isAfter) {
//								curIssData = issData;
//							}
//						}
//					}
//					if (curIssData!=null && StringUtils.isBlank(curIssData.getCancelStatus())) {
//						String cancelReason = "";
//						if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_TERMINATE_LOST_CARD))
//							cancelReason = NicIssuanceData.CANCEL_REASON_LOST;
//						else if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_TERMINATE_DAMAGE_CARD))
//							cancelReason = NicIssuanceData.CANCEL_REASON_DAMAGE;
//						
//						curIssData.setCancelStatus(NicIssuanceData.CANCEL_STATUS_CANCELLED);
//						curIssData.setCancelReason(cancelReason);
//						curIssData.setUpdateBy(officerId);
//						curIssData.setUpdateWkstnId(workstationId);
//						curIssData.setUpdateDate(new Date());
//						logger.info("transactionId[{}], persoRefId[{}], ccn[{}] updating as cancelled : {}.", new Object[] {transactionId, curIssData.getId().getPersoRefId(), curIssData.getCcn(), cancelReason});
//						this.dao.saveOrUpdate(curIssData);
//					}
//				}
//				//06 Feb 2014 (chris): to handle terminate on lost/damage card. - end
//			}
//			//07 Feb 2014 (chris): to handle suspend transaction / card. - start
//			else if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_RIC_TXN_SUSPENDED) ) {
//				this.voidTransaction(transactionId, officerId, workstationId);
//			}
//			//07 Feb 2014 (chris): to handle suspend transaction / card. - end
//			
//			//24 Feb 2014 (chris): add condition to handle void (suspend & reject) - start
//			if (StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_VOID)) {
//				this.voidTransaction(transactionId, officerId, workstationId);
//			}
//			//24 Feb 2014 (chris): add condition to handle void (suspend & reject) - end
//			
//			
//			//14 Nov 2013 (chris): start
//			//05 Sep 2014 (chris) commented
//			//if (StringUtils.isNotBlank(transactionStatus)) { //StringUtils.isNotBlank(cardStatus) && 		
//			//	String transactionStage = NicTransactionLog.TRANSACTION_STAGE_STATUS_UPDATE;
//			//	Date endTime = new Date(); //22 Oct 2012 (chris)
//			//	this.nicTransactionLogService.saveTransactionLog(transactionId, transactionStage, transactionStatus, officerId, workstationId, null, startTime, endTime, null, null);
//			//}
//			//14 Nov 2013 (chris): end
//		} catch (IssuanceServiceException e) {
//			//05 Sep 2014 (chris): start
//			error = true;
//			logInfo = this.getLogInfoXml (new LogInfoDTO(e.getErrorCode(), e.getMessage()));
//			//logData = MiscXmlConvertor.parseObjectToXml(e);
//			//05 Sep 2014 (chris): end
//			throw e;
//		} catch (Exception ex) {
//			//05 Sep 2014 (chris): start
//			error = true;
//			logInfo = this.getLogInfoXml (new LogInfoDTO("", ex.getMessage()));
//			logData = MiscXmlConvertor.parseObjectToXml(ex.getCause()==null?ex:ex.getCause());
//			//05 Sep 2014 (chris): end
//			throw new IssuanceServiceException (ex);
//		} finally {
//			//05 Sep 2014 (chris): start
//			if (StringUtils.isNotBlank(transactionStatus)) {	
//				String transactionStage = NicTransactionLog.TRANSACTION_STAGE_STATUS_UPDATE;
//				if (error) {
//					transactionStage += "_FAILED";
//				}
//				Date endTime = new Date(); 
//				this.nicTransactionLogService.saveTransactionLog(transactionId, transactionStage, transactionStatus, officerId, workstationId, null, startTime, endTime, logInfo, logData);
//			}
//			//05 Sep 2014 (chris): end
//		}
	}
	//05 Sep 2014 (chris)
	private String getLogInfoXml (LogInfoDTO logInfo) {
		String result = null;
		try {
			TransLogInfoXmlConvertor convertor = new TransLogInfoXmlConvertor();
			result = convertor.marshal(logInfo);	
		}catch (JaxbXmlConvertorException jaxb) {
			logger.error("Error Occured when getLogDataXML. caused:" + jaxb.getMessage(), jaxb);
		}
		return result;
	}
	
	//28 APR 2014 - jp - added creation of termination job for rep lost cases
	//06 Jun 2014 - chris - rename method to reuse for other scenarios: CARD_RETURN, REPLACEMENT, etc.
//	@Transactional(rollbackFor= IssuanceServiceException.class ,propagation = Propagation.REQUIRED)
//	public void createCardWorkflowJob(String transactionId, String jobType, String ccn, NicIssuanceData curIssData, String transactionStatus, String officerId, String workstationId) throws IssuanceServiceException{
//		try{
//			this.createJob(transactionId, jobType, ccn, curIssData, transactionStatus, officerId, workstationId);
//		} catch (Exception ex) {
//			throw new IssuanceServiceException (ex);
//		}
//	}
	
	
//	private void createJob(String transactionId, String jobType, String ccn, NicIssuanceData curIssData, String transactionStatus, String officerId, String workstationId) {
//		Long nicUploadJobId = null;
//		//String ccn = curIssData.getCcn();
//		//[chris][20 Dec 2013] To fix duplicate jobs created
//		boolean isJobCreated = false;
//		NicUploadJob sampleJob = new NicUploadJob();
//		sampleJob.setTransactionId(transactionId);
//		//sampleJob.setCcn(ccn);
//		sampleJob.setJobType(jobType);
//		List<NicUploadJob> resultJobList = uploadJobDao.findAll(sampleJob);
//		if (CollectionUtils.isNotEmpty(resultJobList)) {
//			for (NicUploadJob job: resultJobList) {
//				if (!StringUtils.equalsIgnoreCase(jobType, JOB_TYPE_CARD_REACTIVATION) && !StringUtils.equalsIgnoreCase(jobType, JOB_TYPE_CARD_SUSPENSION)) {
//					logger.error("[createJob] duplicate job created on {}, transactionId:{}, jobId:{}, jobType:{}, ccn: {} ", new Object[] {job.getJobCreatedTime(), transactionId, nicUploadJobId, jobType, ccn});
//					isJobCreated = true;
//				}
//				else if (DateUtils.isSameDay(new Date(), job.getJobCreatedTime())) {
//					logger.error("[createJob] duplicate job created on {}, transactionId:{}, jobId:{}, jobType:{}, ccn: {} ", new Object[] {job.getJobCreatedTime(), transactionId, nicUploadJobId, jobType, ccn});
//					isJobCreated = true;
//				}
//			}
//		}
//		if (!isJobCreated) {//[chris][20 Dec 2013] to fix duplicate jobs created 						
//			nicUploadJobId = uploadJobDao.createWorkflowJob(transactionId, ccn, jobType);
//			//reset flag for download
//			if (curIssData!=null && StringUtils.equalsIgnoreCase(transactionStatus, TRANSACTION_STATUS_EXT_CARD_TERMINATED)) {
//				curIssData.setReceivedCardFlag(false);
//				curIssData.setUpdateBy(officerId);
//				curIssData.setUpdateWkstnId(workstationId);
//				curIssData.setUpdateDate(new Date());
//				logger.info("transactionId[{}], persoRefId[{}], ccn[{}] reseting receivedCardFlag : {}.", new Object[] {transactionId, curIssData.getId().getPersoRefId(), curIssData.getCcn()});
//				this.dao.saveOrUpdate(curIssData);
//			}
//		}
//	}
	
	/**
	 * To compare the order of status sequence, if new status belong previous stage, then update is not required. 
	 * 
	 * @param oldStatus
	 * @param newStatus
	 * @return the boolean flag of update is required.
	 */
	private boolean validateTransactionStatusUpdate(String transactionId, String oldStatus, String newStatus) {
		boolean updateReq = true;
		
		if (StringUtils.contains(oldStatus, "RIC_UPLOAD_FAIL_")) {
			oldStatus = "RIC_UPLOAD_FAIL_%";
		}
		if (StringUtils.contains(newStatus, "RIC_UPLOAD_FAIL_")) {
			newStatus = "RIC_UPLOAD_FAIL_%";
		}
		
		Map<String, Integer> statusMap = new HashMap<String, Integer>();
		statusMap.put("RIC_UPLOADED", 1);//RIC UPLOAD
		statusMap.put("RIC_UPLOAD_FAIL_%", 1);
		
		statusMap.put("NIC_PENDING_INVESTIGATION", 2);//NIC WORKFLOW
		statusMap.put("NIC_SCREENING", 2);
		statusMap.put("NIC_APPROVED", 2);		
		statusMap.put("NIC_PERSO_DATA_PREPARED", 2);
		statusMap.put("NIC_PERSO_REQUEST_SUBMITTED", 2);
		statusMap.put("NIC_TX_COMPLETD", 2);
		statusMap.put("PERSO_ERROR", 2); //PERSO REJECTED
		
		statusMap.put("PERSO_PERSONALIZED", 3);//PERSO WORKFLOW
		statusMap.put("PERSO_COMPLETED", 3);
		
		statusMap.put("RIC_CARD_RECEIVED", 4);//RIC/CC UPDATE
		statusMap.put("RIC_CARD_TRANSFERRED", 4);
		
		statusMap.put("RIC_CARD_ISSUED", 5);
		statusMap.put("RIC_CARD_DEACTIVATED", 5);
		statusMap.put("RIC_CARD_TERMINATED", 5);
		statusMap.put("RIC_CARD_REJECTED", 5);
		statusMap.put("RIC_CARD_EXPIRED", 5);
		statusMap.put("RIC_CARD_RETURNED", 5);
		statusMap.put("NIC_CPDV2_UPDATED", 5);//NIC WORKFLOW
		statusMap.put("EXT_CARD_TERMINATED", 5);
		statusMap.put("TERMINATE_LOST_CARD", 5);//CC UPDATE
		statusMap.put("TERMINATE_DAMAGE_CARD", 5);
		
		statusMap.put("INVENTORY_CARD_RECEIVED", 6);
		statusMap.put("INVENTORY_CARD_DESTROYED", 6);
		
		statusMap.put("NIC_REJECTED", 9);
		statusMap.put("VOID", 9);
		
		Integer oldStatusId = statusMap.get(oldStatus);
		Integer newStatusId = statusMap.get(newStatus);		
		if (newStatusId!=null && oldStatusId!=null && newStatusId.compareTo(oldStatusId)<0) {
			updateReq = false;
		}
		logger.info("validateTransactionStatusUpdate[{}], oldStatus[{}], newStatus[{}] updateReq : {}.", new Object[] {transactionId, oldStatus, newStatus, updateReq});
		return updateReq;
	}
	
	/*
	 * to suspend transaction if
	 *         1) transaction is pending investigation
	 *         2) transaction is submitted to perso 
	 */
	private void voidTransaction(String transactionId, String officerId, String workstationId) throws IssuanceServiceException {
//		logger.info("[voidTransaction] - begin : transactionId[{}]", new Object[] {transactionId});
//		boolean cancelPerso = false;
//		boolean cancelNic = false;
//		boolean error = false;
//		boolean cancelDenied = false;
//		NicTransaction tempTransaction = new NicTransaction(transactionId);
//		NicIssuanceData nicIssuanceData = null;
//		tempTransaction = this.dao.getTransactionIssuanceDatas(tempTransaction);
//		if (CollectionUtils.isNotEmpty(tempTransaction.getNicIssuanceDatas())) {			
//			for (NicIssuanceData issData : tempTransaction.getNicIssuanceDatas()) {
//				if (nicIssuanceData==null) {
//					nicIssuanceData = issData;
//				} else if (issData.getCreateDate()!=null && nicIssuanceData.getCreateDate()!=null) {
//					boolean isAfter = DateUtil.isAfterDate(issData.getCreateDate(), nicIssuanceData.getCreateDate());
//					if (isAfter) {
//						nicIssuanceData = issData;
//					}
//				}
//			}			
//		}
//		
//		//If card has already printed, it cannot be suspended.
//		//[check ccn is not null]
//		if (nicIssuanceData!=null) {
//			if (StringUtils.isNotBlank(nicIssuanceData.getCcn())) {
//				logger.info("transactionId[{}], persoRefId[{}], ccn[{}] cannot be suspended as card is printed.", new Object[] {transactionId, nicIssuanceData.getId().getPersoRefId(), nicIssuanceData.getCcn()});
//				cancelDenied = true;
//			} else {
//		//If print job has been sent to CPC, call CPC services to try to cancel the print job. If failed, transaction cannot be suspended.
//		//[check issuance data is not empty, then cancel the job.]
//				try {
//					
//					int count=0;
//					do {
//						//cancel CPC job & insert transaction log
//						ResponseDTO responseDto = persoService.cancelJobByTransactionId(transactionId, officerId, workstationId);
//						
//						if (StringUtils.equals(Integer.toString(PersoService.PERSO_CANCEL_SUCCEED), responseDto.getServiceCode())) {
//							logger.info("transactionId[{}] suspended in perso.", new Object[] {transactionId});
//							cancelPerso = true;
//							
//							nicIssuanceData.setCancelStatus(NicIssuanceData.CANCEL_STATUS_CANCELLED);
//							nicIssuanceData.setCancelReason(NicIssuanceData.CANCEL_REASON_TXN_SUSPENDED);
//							nicIssuanceData.setUpdateBy(officerId);
//							nicIssuanceData.setUpdateWkstnId(workstationId);
//							nicIssuanceData.setUpdateDate(new Date());
//							logger.info("transactionId[{}], persoRefId[{}] updating as cancelled.", new Object[] {transactionId, nicIssuanceData.getId().getPersoRefId()});
//							this.dao.saveOrUpdate(nicIssuanceData);
//						} else if (StringUtils.equals(Integer.toString(PersoService.PERSO_CANCEL_FAILED), responseDto.getServiceCode())) {
//							logger.info("transactionId[{}] cannot suspended in perso, nic to try again", new Object[] {transactionId});
//							try {
//								Thread.sleep(10000);
//							} catch (InterruptedException e) {
//							}
//						} else if (StringUtils.equals(Integer.toString(PersoService.PERSO_CANCEL_DENIED), responseDto.getServiceCode())) {
//							logger.info("transactionId[{}] cannot suspended in perso, as it already reach PONR in Perso.", new Object[] {transactionId});
//							cancelDenied = true;
//						} 
//						count++;
//					} while(count<3 && !cancelPerso && !cancelDenied);
//					if (!cancelPerso && !cancelDenied) {
//						logger.info("transactionId[{}] failed to suspend in perso after {} tries.", new Object[] {transactionId, count});
//						error = true;
//					}
//				} catch (PersoServiceException e) {
//					logger.error("transactionId[{}] cannot be suspended as cancellation in perso failed.", new Object[] {transactionId}, e);
//					error = true;
//				}
//			}
//		}
//		
//		//If print job has not been sent to CPC, suspend the transaction. Set transaction stage to suspended.
//		//[check issuance data is empty, and job is not in progress]
//		if (nicIssuanceData==null ||(cancelPerso)) {
//			Date startTime = new Date();
//			String transactionStage = NicTransactionLog.TRANSACTION_STAGE_TXN_VOID;
//			String transactionStatus = NicTransactionLog.TRANSACTION_STATUS_TXN_VOID_COMPLETED;
//			NicUploadJob sampleJob = new NicUploadJob();
//			sampleJob.setTransactionId(transactionId);
//			List<NicUploadJob> resultJobList = uploadJobDao.findAll(sampleJob);
//			if (CollectionUtils.isNotEmpty(resultJobList)) {
//				for (NicUploadJob job: resultJobList) {
//					boolean notStarted = StringUtils.isBlank(job.getJobCurrentState());
//					boolean notProcessing = this.checkIfJobProcessing(job); //[chris][28 May 2014] to void job if it is not processing. 	
//					if (StringUtils.equals(NicTransaction.TRANSACTION_TYPE_REGISTRATION, job.getJobType()) || 
//							StringUtils.equals(NicTransaction.TRANSACTION_TYPE_REPLACEMENT, job.getJobType()) || 
//							StringUtils.equals(NicTransaction.TRANSACTION_TYPE_CONVERSION, job.getJobType())) {
//						if (notStarted || notProcessing || checkIfPendingInvestigation(job) || cancelPerso) { //[chris][28 May 2014] to void job if it is not processing. 
//							if (cancelPerso) {
//								job.setPersoRegisterStatus(NicUploadJob.RECORD_STATUS_CANCELLED);
//							} else if (checkIfPendingInvestigation(job)) {
//								job.setInvestigationOfficerId(SYSTEM_NIC);
//								job.setInvestigationStatus(NicUploadJob.RECORD_STATUS_REJECTED);
//								job.setDataPreparationStatus(NicUploadJob.RECORD_STATUS_NOT_REQUIRED);
//								job.setPersoRegisterStatus(NicUploadJob.RECORD_STATUS_NOT_REQUIRED);
//								job.setSyncTxCpdStatus(NicUploadJob.RECORD_STATUS_NOT_REQUIRED);
//							} 
//							//[chris][28 May 2014] to void job if it is not processing. - start
//							else if (notProcessing) {
//								if (!StringUtils.equals(job.getCpdCheckStatus(), NicUploadJob.RECORD_STATUS_COMPLETED) || !StringUtils.equals(job.getCpdCheckStatus(), NicUploadJob.RECORD_STATUS_COMPLETED_WITH_HIT)) {
//									job.setCpdCheckStatus(NicUploadJob.RECORD_STATUS_NOT_REQUIRED);
//								}
//								if (StringUtils.equals(NicTransaction.TRANSACTION_TYPE_REGISTRATION, job.getJobType()) || 
//										StringUtils.equals(NicTransaction.TRANSACTION_TYPE_CONVERSION, job.getJobType())) {
//									if (!StringUtils.equals(job.getAfisCheckStatus(), NicUploadJob.RECORD_STATUS_COMPLETED) || !StringUtils.equals(job.getAfisCheckStatus(), NicUploadJob.RECORD_STATUS_COMPLETED_WITH_HIT)) {
//										job.setAfisCheckStatus(NicUploadJob.RECORD_STATUS_NOT_REQUIRED);
//									}
//								} else {
//									if (!StringUtils.equals(job.getAfisVerifyStatus(), NicUploadJob.RECORD_STATUS_COMPLETED) || !StringUtils.equals(job.getAfisVerifyStatus(), NicUploadJob.RECORD_STATUS_COMPLETED_WITH_HIT)) {
//										job.setAfisVerifyStatus(NicUploadJob.RECORD_STATUS_NOT_REQUIRED);
//									}
//								}
//								if (!StringUtils.equals(job.getAfisRegisterStatus(), NicUploadJob.RECORD_STATUS_COMPLETED)) {
//									job.setAfisRegisterStatus(NicUploadJob.RECORD_STATUS_NOT_REQUIRED);
//								}
//								if (!StringUtils.equals(job.getDataPreparationStatus(), NicUploadJob.RECORD_STATUS_COMPLETED)) {
//									job.setDataPreparationStatus(NicUploadJob.RECORD_STATUS_NOT_REQUIRED);						
//								}
//								if (!StringUtils.equals(job.getPersoRegisterStatus(), NicUploadJob.RECORD_STATUS_COMPLETED)) {
//									job.setPersoRegisterStatus(NicUploadJob.RECORD_STATUS_NOT_REQUIRED);
//								}
//								if (!StringUtils.equals(job.getSyncTxCpdStatus(), NicUploadJob.RECORD_STATUS_COMPLETED)) {
//									job.setSyncTxCpdStatus(NicUploadJob.RECORD_STATUS_NOT_REQUIRED);
//								}
//								if (Boolean.TRUE.equals(job.getNicJobErrorFlag())) {
//									job.setNicJobErrorFlag(Boolean.FALSE);
//								}
//								if (Boolean.FALSE.equals(job.getNicJobCompletedFlag())) {
//									job.setNicJobCompletedFlag(Boolean.TRUE);
//								}
//							}
//							//[chris][28 May 2014] to void job if it is not processing. - end
//							
//							job.setJobCurrentState(NicUploadJob.JOB_STATE_VOID);
//							job.setJobCurrentStatus(NicUploadJob.JOB_STATE_VOID+"_COMPLETED");
//							job.setJobEndTime(new Date());
//							job.setUpdateBy(SYSTEM_NIC);
//							job.setUpdateDate(new Date());
//							uploadJobDao.saveOrUpdate(job);
//							logger.info("transactionId[{}] upload job is void (cancelled).", new Object[] {transactionId});
//							cancelNic = true;
//							//insert log
//							String siteCode = this.getCurrentSiteCodeFromParameter();
//							Long logId = nicTransactionLogService.saveTransactionLog(transactionId, transactionStage, transactionStatus, officerId, workstationId, siteCode, startTime, new Date(), null, null);
//							logger.info("[voidTransaction] save NicTransactionLog completed, transactionId:{}, logId:{} ", transactionId, logId);
//						} 
//					}
//				}
//			}
//		}
//		if (cancelDenied) {
//			logger.info("Failed to Void Transaction, Card maybe printed: {}", new Object[] {transactionId});
//			//throw new IssuanceServiceException("Failed to Void Transaction.", UPDATE_TRANSACTION_STATUS_ERROR_CODE_SUSPENSION_DENIED);
//		} else if (error) {
//			logger.info("Failed to Void Transaction in CPC: {}", new Object[] {transactionId});
//			//throw new IssuanceServiceException("Failed to Void Transaction in CPC.", UPDATE_TRANSACTION_STATUS_ERROR_CODE_SUSPENSION_FAILED);
//		} else if (!cancelNic) {
//			logger.info("Failed to Void Transaction in NIC: {}", new Object[] {transactionId});
//			//throw new IssuanceServiceException("Failed to Void Transaction in NIC.", UPDATE_TRANSACTION_STATUS_ERROR_CODE_SUSPENSION_FAILED);
//		}
	}
	
	private String getCurrentSiteCodeFromParameter() {
		String currentSiteCode = "NICDC";
//		Parameters parameter = parametersDao.findById(new ParametersId(PARA_SCOPE_SYSTEM, PARA_NAME_CURRENT_SITE_CODE));
//		if (parameter!=null) {
//			currentSiteCode = (String) parameter.getParaValue();
//		} else {
//			logger.warn("No matching Parameter for {} , {} ", PARA_SCOPE_SYSTEM, PARA_NAME_CURRENT_SITE_CODE);
//		}
		return currentSiteCode;
	}
	
	private boolean checkIfPendingInvestigation(NicUploadJob job){
		boolean investigationStatus = StringUtils.equals(job.getInvestigationStatus(), "00") || StringUtils.equals(job.getInvestigationStatus(), "01");

		if (investigationStatus) {
			return true;
		} else {
			return false;
		}
	}
	//to handle void case if the job is Not Processing.
	private boolean checkIfJobProcessing(NicUploadJob job){
		boolean processingStatus = 
			StringUtils.equals(job.getCpdCheckStatus(), NicUploadJob.RECORD_STATUS_IN_PROGRESS)
			|| StringUtils.equals(job.getAfisCheckStatus(), NicUploadJob.RECORD_STATUS_IN_PROGRESS) 
			|| StringUtils.equals(job.getAfisVerifyStatus(), NicUploadJob.RECORD_STATUS_IN_PROGRESS)
			|| StringUtils.equals(job.getAfisRegisterStatus(), NicUploadJob.RECORD_STATUS_IN_PROGRESS)
			|| StringUtils.equals(job.getAfisUpdateStatus(), NicUploadJob.RECORD_STATUS_IN_PROGRESS)
			//|| StringUtils.equals(job.getDataPreparationStatus(), NicUploadJob.RECORD_STATUS_IN_PROGRESS)
			|| StringUtils.equals(job.getPersoRegisterStatus(), NicUploadJob.RECORD_STATUS_IN_PROGRESS)
			//|| StringUtils.equals(job.getSyncTxCpdStatus(), NicUploadJob.RECORD_STATUS_IN_PROGRESS)
		;
		if (processingStatus) {
			return true;
		} else {
			return false;
		}
	}
	
	public NicIssuanceData findById(NicIssuanceDataId id) throws IssuanceServiceException {
		try {
			return this.dao.findById(id);
		}catch (Exception ex) {
			throw new IssuanceServiceException (ex);
		}
	}
	
	@Transactional(rollbackFor= IssuanceServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateReceivedCardStatus (List<NicIssuanceDataId> idList, String officerId, String workstationId, String siteCode) throws IssuanceServiceException {
		try {
			
			if (CollectionUtils.isEmpty(idList)) 
				throw new IssuanceServiceException ("List of issuance data cannot be empty");
			
			//23 Oct 2013 (chris): validate IssSiteCode with transactionId - start
			List<String> transactionIdList = new ArrayList<String>();
			for (NicIssuanceDataId data : idList) {	
				transactionIdList.add(data.getTransactionId());
			}
			boolean differentSiteCode = false;
			String errorTransactionId = "";
			List<NicTransaction> nicTransactionDBOList = this.transactionDao.findAllByTransIdList(transactionIdList);
			if (CollectionUtils.isNotEmpty(nicTransactionDBOList)) {
				for (NicTransaction nicTransaction: nicTransactionDBOList) {
					if (!StringUtils.equalsIgnoreCase(siteCode, nicTransaction.getIssSiteCode())) {
						differentSiteCode = true;
						errorTransactionId = nicTransaction.getTransactionId();
						logger.error("Transaction [{}] is belong to {} instead of {}, invalid update!!", new Object[]{ errorTransactionId, nicTransaction.getIssSiteCode(), siteCode});
						break;
					}
				}	
			}
			if (differentSiteCode) {
				throw new NicUploadJobServiceException("Invalid update download status on transactionId:"+errorTransactionId);
			}
			//23 Oct 2013 (chris): validate IssSiteCode with transactionId - end
			
			for (NicIssuanceDataId data : idList) {				
				Date startTime = new Date(); //22 Oct 2012 (chris)
				NicIssuanceData entity = this.dao.findById(data);
				if (entity==null)
					throw new IssuanceServiceException ("Data is not found for transactionId="+ data.getTransactionId() + "; persoRefId=" + data.getPersoRefId());				
			
				entity.setReceivedCardFlag(true);
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(officerId); //2014 Aug 18 (chris)
				entity.setUpdateWkstnId(workstationId); //2014 Aug 18 (chris)
				this.dao.saveOrUpdate(entity);
				
				//22 Oct 2012 (chris): add transactionLog for updateReceivedCardStatus - start
				String transactionId = entity.getId().getTransactionId();
				String transactionStage = NicTransactionLog.TRANSACTION_STAGE_RIC_TX_DOWNLOAD;
				String transactionStatus = NicTransactionLog.TRANSACTION_STATUS_RIC_CARD_DATA_RECEIVED;
				Date endTime = new Date(); //22 Oct 2012 (chris)
				String logData = ReflectionToStringBuilder.toString(data, ToStringStyle.SHORT_PREFIX_STYLE);
				this.nicTransactionLogService.saveTransactionLog(transactionId, transactionStage, transactionStatus, officerId, workstationId, siteCode, startTime, endTime, null, logData); //2014 Aug 18 (chris)
				logger.info("[updateReceivedCardStatus] save TransactionLog, transactionId:{}, logData:{} ", transactionId, logData);
				//22 Oct 2012 (chris): add transactionLog for updateReceivedCardStatus - end
			}
		}catch (Exception ex) {
			throw new IssuanceServiceException (ex);
		}
	}

	@Override
	@Transactional(rollbackFor= IssuanceServiceException.class ,propagation = Propagation.REQUIRED)
	public List<NicIssuanceData> findIssuanceData(String packageId, String ccn,	String nin) throws IssuanceServiceException {
		return this.findIssuanceData(packageId, ccn, nin, null, null, null);
	}

	@Override
	@Transactional(rollbackFor= IssuanceServiceException.class ,propagation = Propagation.REQUIRED)
	public List<NicIssuanceData> findIssuanceData(String packageId, String ccn,	String nin, String siteCode, String officerId, String workstationId) throws IssuanceServiceException {
		try {
			logger.debug("[findIssuanceData] packageId={}, ccn={}, nin={}", new Object[]{ packageId, ccn, nin});
			List<NicIssuanceData> nicIssuanceDataDBOList = new ArrayList<NicIssuanceData>();
			Date startTime = new Date();
			
			if (StringUtils.isNotBlank(packageId)) {
				//call perso webservice to find the issuance data.
				try {
					persoService.getPackageJobByPackageID(packageId);
				} catch(PersoServiceException e) {
					logger.error("[Exception][findIssuanceData] Error when find by packageId={}", new Object[]{packageId}, e);	
				}
				
				List<NicIssuanceData> resultList = this.dao.findByPackageId(packageId);
				if (CollectionUtils.isNotEmpty(resultList)) {
					nicIssuanceDataDBOList.addAll(resultList);
					logger.debug("[findIssuanceData] added result size={}, by packageId={}", new Object[]{ resultList.size(), packageId});
				} else {
					logger.warn("[findIssuanceData] data not found by packageId={}", new Object[]{packageId});					
				}
			} 
			if (StringUtils.isNotBlank(ccn)) {
				boolean ccnExists = false;
				for (NicIssuanceData nicIssuanceData : nicIssuanceDataDBOList) {
					if (StringUtils.equals(ccn, nicIssuanceData.getCcn())) {
						ccnExists = true;
						break;
					}
				}
				if (!ccnExists) {
					List<NicIssuanceData> resultList = this.dao.findDataByCCn(ccn);
					if (CollectionUtils.isNotEmpty(resultList)) {
						nicIssuanceDataDBOList.addAll(resultList);
						ccnExists = true;
						logger.debug("[findIssuanceData] added result size={}, by ccn={}", new Object[]{ resultList.size(), ccn});
					}
				}
				if (!ccnExists) {
					logger.warn("[findIssuanceData] data not found by ccn={}", new Object[]{ ccn});
					//TODO call perso webservice to find the issuance data.
					
				}
			} 
			if (StringUtils.isNotBlank(nin)) {
				boolean ninExists = false;
				String transactionId = null;
				for (NicIssuanceData nicIssuanceData : nicIssuanceDataDBOList) {
					if (StringUtils.equals(nin, nicIssuanceData.getNin())) {
						if (StringUtils.isNotBlank(nicIssuanceData.getCcn()) && StringUtils.isNotBlank(nicIssuanceData.getCardStatus())) {
							ninExists = true;
							break;
						}
					}
				}
				if (!ninExists) {
					NicIssuanceData sampleIssData = new NicIssuanceData();
					sampleIssData.setNin(nin);
					List<NicIssuanceData> resultList = this.dao.findAll(sampleIssData);
					if (CollectionUtils.isNotEmpty(resultList)) {
						boolean updated = false;
						for (NicIssuanceData nicIssuanceData : resultList) {
							if (StringUtils.isBlank(nicIssuanceData.getCcn()) && StringUtils.isBlank(nicIssuanceData.getCardStatus())) {
								transactionId = nicIssuanceData.getId().getTransactionId();
								//call perso webservice to find the issuance data.
								try {
									logger.info("[findIssuanceData] call perso to get by transactionId={}", new Object[]{ transactionId });
									persoService.getPackageJobByTransactionID(transactionId);
									updated = true;
								} catch(PersoServiceException e) {
									logger.error("[Exception][findIssuanceData] Error when find by transactionId={}", new Object[]{transactionId}, e);	
								}
							}
						}
						//refresh data from db 
						if (updated) {
							resultList = this.dao.findAll(sampleIssData);
						}
						nicIssuanceDataDBOList.addAll(resultList);
						ninExists = true;
						logger.debug("[findIssuanceData] added result size={}, by nin={}", new Object[]{ resultList.size(), nin});
					}
				}
			}
			//2014 Mar 12 [chris] filtered by sitecode
			if (StringUtils.isNotBlank(siteCode)) {
				List<String> transactionIdList = new ArrayList<String>();
				List<String> invalidTransIdList = new ArrayList<String>();
				for (NicIssuanceData nicIssuanceDataDBO : nicIssuanceDataDBOList) {
					transactionIdList.add(nicIssuanceDataDBO.getId().getTransactionId());
				}
				List<NicTransaction> nicTransactionDBOList = this.transactionDao.findAllByTransIdList(transactionIdList);
				for (NicTransaction nicTransactionDBO : nicTransactionDBOList) {
					if (!StringUtils.equals(nicTransactionDBO.getIssSiteCode(), siteCode)) {
						invalidTransIdList.add(nicTransactionDBO.getTransactionId());
					}
				}
				logger.info("[findIssuanceData] unmatch transaction id due to sitecode {}, by id List={}", new Object[]{ siteCode, invalidTransIdList});
				List<NicIssuanceData> tempIssDataList = new ArrayList<NicIssuanceData>();
				for (NicIssuanceData nicIssuanceDataDBO : nicIssuanceDataDBOList) {
					if (!invalidTransIdList.contains(nicIssuanceDataDBO.getId().getTransactionId())) {
						tempIssDataList.add(nicIssuanceDataDBO);
					}
				}
				logger.info("[findIssuanceData] remaining valid sitecode size={}", new Object[]{ tempIssDataList.size()});
				nicIssuanceDataDBOList = tempIssDataList;
				
				for (NicIssuanceData entity : nicIssuanceDataDBOList) {					
					String transactionId = entity.getId().getTransactionId();
					String transactionStage = NicTransactionLog.TRANSACTION_STAGE_RIC_TX_DOWNLOAD;
					String transactionStatus = NicTransactionLog.TRANSACTION_STATUS_RIC_CARD_DATA_RECEIVED;
					Date endTime = new Date(); //22 Oct 2012 (chris)
					String logData = "packageId="+packageId+", ccn="+ccn+", nin="+nin;
					this.nicTransactionLogService.saveTransactionLog(transactionId, transactionStage, transactionStatus, officerId, workstationId, siteCode, startTime, endTime, null, logData);
					logger.debug("[findIssuanceData][downloadMissingPackage] save TransactionLog, transactionId:{}, logData:{} ", transactionId, logData);
				}
			}
			//2014 Mar 12 [chris] filtered by sitecode - end
			
			logger.info("[findIssuanceData] result size={}", new Object[]{ nicIssuanceDataDBOList.size()});
			return nicIssuanceDataDBOList;
		}catch (Exception ex) {
			throw new IssuanceServiceException (ex);
		}
	}


	
	@Override
	@Transactional(rollbackFor= IssuanceServiceException.class ,propagation = Propagation.SUPPORTS)
	public List<NicIssuanceData> findByTransactionId(String transactionId)
			throws IssuanceServiceException {
		List<NicIssuanceData> nicIssuanceData = new ArrayList<NicIssuanceData>();
		try{
			nicIssuanceData = dao.findByTransactionId(transactionId);
			
		}catch (Exception ex) {
			throw new IssuanceServiceException (ex);
		}
		return nicIssuanceData;
	}


	
	@Override
	public NicIssuanceData getLatestNicIssuanceData(String transactionId)
			throws IssuanceServiceException {
		try{
			return dao.getLatestNicIssuanceData(transactionId);
		}catch (Exception ex) {
			throw new IssuanceServiceException (ex);
		}
	}

	@Override
	public List<NicIssuanceData> findByCcn(String ccn)
			throws IssuanceServiceException {
		try {
		//	return this.dao.findDataByCCn(ccn);
			
			//NicIssuanceData e =  new NicIssuanceData();
			//e.setCcn(ccn);
			return this.dao.findDataByCCn(ccn);
		}catch (Exception ex) {
			throw new IssuanceServiceException (ex);
		}
	}
	

	@Override
	public PaginatedResult<NicIssuanceDataDto> findAllForPagination(
			PageRequest pageRequest, NicIssuanceDataDto dto)
			throws IssuanceServiceException {
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = true;
			boolean enableLike = true;
			Order order = null;
			if(StringUtils.equals(pageRequest.getSortorder(),"asc")){
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}
			
			NicIssuanceData issuanceData = getDbo(dto);
			Date regDatefrom = DateUtil.strToDate(dto.getRegistrationDateFrom(), "dd-MMM-yyyy");
			Date regDateto = DateUtil.strToDate(dto.getRegistrationDateTo(), "dd-MMM-yyyy");
			Date collectDatefrom = DateUtil.strToDate(dto.getCollectionDateFrom(), "dd-MMM-yyyy");
			Date collectDateto = DateUtil.strToDate(dto.getCollectionDateTo(), "dd-MMM-yyyy");
			PaginatedResult<NicIssuanceData> prDbo = dao.findAllForPagination(issuanceData, 
					regDatefrom, regDateto, collectDatefrom, collectDateto, pageNo, pageSize, ignoreCase, enableLike, order);
			
			List<NicIssuanceData> dbos = prDbo.getRows();
			List<NicIssuanceDataDto> dtos = new ArrayList<NicIssuanceDataDto>();
			for(NicIssuanceData dbo : dbos) {
				dtos.add(getDto(dbo));
			}
			
			PaginatedResult<NicIssuanceDataDto> prDto = new PaginatedResult<NicIssuanceDataDto>(prDbo.getTotal(), prDbo.getPage(), dtos);
			
			return prDto;
		} catch (Exception e) {
			throw new IssuanceServiceException(e);
		}

	}

	private NicIssuanceData getDbo(NicIssuanceDataDto dto) {
		NicIssuanceData dbo = new NicIssuanceData();
		BeanUtils.copyProperties(dto, dbo);
		if(dto.getNicTransactionDto() != null) {
			NicTransaction nicTransaction = new NicTransaction();
			BeanUtils.copyProperties(dto.getNicTransactionDto(), nicTransaction);
			dbo.setNicTransaction(nicTransaction);
		}
		return dbo;
	}
	
	private NicIssuanceDataDto getDto(NicIssuanceData dbo) {
		NicIssuanceDataDto dto = new NicIssuanceDataDto();
		BeanUtils.copyProperties(dbo, dto);
		if(dbo.getNicTransaction() != null) {
			NicTransactionDto nicTransaction = new NicTransactionDto();
			BeanUtils.copyProperties(dbo.getNicTransaction(), nicTransaction);
			dto.setNicTransactionDto(nicTransaction);
		}
		return dto;
	}	

}
