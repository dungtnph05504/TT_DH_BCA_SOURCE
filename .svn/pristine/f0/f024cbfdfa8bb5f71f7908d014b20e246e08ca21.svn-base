package com.nec.asia.nic.comp.trans.service;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceDataId;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.dto.NicIssuanceDataDto;
import com.nec.asia.nic.comp.trans.service.exception.IssuanceServiceException;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;

/**
 * This interface to manage all issuance data of CARD.
 * 
 * @author setia_budiyono
 *
 */


/* 
 * Modification History:
 *  
 * 23 Sep 2013 (chris): add issuance_decision_constants.
 * 1 Oct 2013 (chris): change constants from transaction_stage to transaction_status
 * 22 Oct 2013 (chris): add findIssuanceData by packageId, ccn, nin
 * 30 Oct 2013 (chris) : Added the constants on TRANSACTION_TYPE.
 * 01 Nov 2013 (Swapna):added getLatestNicIssuanceData method.
 * 06 Nov 2013 (chris): to add input of cardStatus in updateTransactionByTransactionId
 * 06 Nov 2013 (jp) : addded findByCcn method
 * 04 Dec 2013 (chris): to add constants RIC_CARD_TERMINATED
 * 06 Feb 2014 (chris): to add constants TERMINATE_LOST_CARD, TERMINATE_DAMAGE_CARD
 * 13 Feb 2014 (chris): to add constants ERROR_CODE for update status when transaction id not found
 * 12 Mar 2014 (chris): to filter findIssuanceData by sitecode
 * 08 Apr 2014 (chris): to refactor job type
 * 28 Apr 2014 (jp): added createJobForRepLost
 * 06 Jun 2014 (chris): rename createJobForRepLost to createCardWorkflowJob
 */
public interface IssuanceDataService {
	
	public static final String TRANSACTION_STAGE_RIC_CARD_ISSUED	= NicTransactionLog.TRANSACTION_STAGE_RIC_CARD_ISSUED;
	public static final String TRANSACTION_STAGE_RIC_CARD_REJECTED	= NicTransactionLog.TRANSACTION_STAGE_RIC_CARD_REJECTED;
	
	public static final String ISSUANCE_DECISION_ISSUED				= "I";
	public static final String ISSUANCE_DECISION_REJECTED			= "R";
	
	//to indicate update_by or create_by
	
	//NicWS.updateNicStatus [M1]
	public static final String UPDATE_TRANSACTION_STATUS_ERROR_CODE_SUSPENSION_FAILED	= "UT-E1001"; //Can Retry
	public static final String UPDATE_TRANSACTION_STATUS_ERROR_CODE_SUSPENSION_DENIED	= "UT-E1002"; //Cannot Retry
	public static final String UPDATE_TRANSACTION_STATUS_ERROR_CODE_TRANSACTION_ID_NOT_FOUND	= "UT-E2001"; //Can Retry
	
	public NicIssuanceDataId saveIssuanceData(NicIssuanceData e) throws IssuanceServiceException;
	
	public NicIssuanceDataId updateIssuanceData (NicIssuanceData e) throws IssuanceServiceException;
	
	public List<NicIssuanceData> findByPeriod(String siteCode, Date start, Date end) throws IssuanceServiceException;

	public void updateStatusByCCn(String ccn, String cardStatus, String cardStatusChangeReason, Date cardStatusChangeTime, String officerId, String workstationId) throws IssuanceServiceException;

	public void updateStatusByPackageId(List<String> packageIdList, String status) throws IssuanceServiceException;

	public void updateTransactionByCcn(String ccn, String transactionStatus, String officerId, String workstationId) throws IssuanceServiceException;
	public void updateTransactionByTransactionId(String transactionId, String transactionStatus, String cardStatus, String officerId, String workstationId) throws IssuanceServiceException;
	
	//common method
	public NicIssuanceData findById(NicIssuanceDataId id ) throws IssuanceServiceException;
	
	public void updateReceivedCardStatus (List<NicIssuanceDataId> idList, String officerId, String workstationId, String siteCode) throws IssuanceServiceException;

	public List<NicIssuanceData> findIssuanceData(String packageId, String ccn, String nin) throws IssuanceServiceException;
	public List<NicIssuanceData> findIssuanceData(String packageId, String ccn, String nin, String siteCode, String officerId, String workstationId) throws IssuanceServiceException;
	
	public List<NicIssuanceData> findByTransactionId (String transactionId) throws IssuanceServiceException;

	/**
	 * @param hitTransactionId
	 * @return
	 */
	public NicIssuanceData getLatestNicIssuanceData(String transactionId) throws IssuanceServiceException;
	public List<NicIssuanceData> findByCcn(String ccn) throws IssuanceServiceException;
	public PaginatedResult<NicIssuanceDataDto> findAllForPagination(PageRequest pageRequest, NicIssuanceDataDto nicIssuanceData) throws IssuanceServiceException;
	
	//28APR2014
	//public void createCardWorkflowJob(String transactionId, String jobType, String ccn, NicIssuanceData curIssData, String transactionStatus, String officerId, String workstationId) throws IssuanceServiceException;
}
