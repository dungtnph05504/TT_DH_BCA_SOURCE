/**
 * 
 */
package com.nec.asia.nic.dx.ws.impl;

//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
//import com.nec.asia.nic.comp.job.dto.PersoLogDataDTO;
//import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
//import com.nec.asia.nic.comp.trans.domain.NicIssuanceDataId;
//import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
//import com.nec.asia.nic.comp.trans.service.IssuanceDataService;
//import com.nec.asia.nic.comp.trans.service.NicMainService;
//import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
//import com.nec.asia.nic.comp.trans.service.TransactionLogService;
//import com.nec.asia.nic.comp.trans.service.exception.IssuanceServiceException;
//import com.nec.asia.nic.comp.trans.service.exception.NicMainServiceException;
//import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
//import com.nec.asia.nic.dx.common.LogInfo;
//import com.nec.asia.nic.dx.nic.NicCardStatusUpdate;
//import com.nec.asia.nic.dx.nic.NicHistoryRetrievalDataType;
//import com.nec.asia.nic.dx.nic.NicHistoryRetrievalFilter;
//import com.nec.asia.nic.dx.nic.NicHistoryRetrievalResult;
//import com.nec.asia.nic.dx.nic.NicInfo;
//import com.nec.asia.nic.dx.nic.NicInfoRetrievalDataType;
//import com.nec.asia.nic.dx.nic.NicInfoRetrievalFilter;
//import com.nec.asia.nic.dx.nic.NicInfoRetrievalResult;
//import com.nec.asia.nic.dx.nic.NicPackageStatusUpdate;
//import com.nec.asia.nic.dx.nic.NicPersoInfoDetail;
//import com.nec.asia.nic.dx.nic.NicPersoInfoDownloadFilter;
//import com.nec.asia.nic.dx.nic.NicPersoInfoDownloadResult;
//import com.nec.asia.nic.dx.nic.NicPersoInfoUpload;
//import com.nec.asia.nic.dx.nic.NicTransStatusUpdate;
//import com.nec.asia.nic.dx.nic.ReceivedCardInfo;
//import com.nec.asia.nic.dx.nic.UpdateReceivedCardStatus;
//import com.nec.asia.nic.dx.trans.IssuanceData;
//import com.nec.asia.nic.dx.trans.RejectionData;
//import com.nec.asia.nic.dx.ws.Constant;
//import com.nec.asia.nic.dx.ws.FaultException;
//import com.nec.asia.nic.dx.ws.NicWS;
//import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
//import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
//import com.nec.asia.nic.utils.DateUtil;

/**
 * @author chris_wong
 *
 * Modification History :
 * 
 */

/* 
 * Modification History:
 *  
 * 04 Jul 2013 setia_budiyono : To implement method updateReceivedCardStatus
 * 12 Aug 2013 (chris): add rejection data in downloadNicPersoInfo()
 * 14 Sep 2013 (chris): add packageSequence, packageDate, dispatchID, dateOfIssue in IssuanceData
 * 17 Sep 2013 (chris): add comment to handle transaction_status = RIC_CARD_ISSUED, 
 * 10 Oct 2013 (WL): Addded echo operation
 * 22 Oct 2013 (chris): add input for nin/ccn in downloadNicPersoInfo()
 * 23 Oct 2013 (chris): add input validation for updateReceivedCardStatus()
 * 06 Nov 2013 (chris): add input of card status for updateNicStatus()
 * 07 Nov 2013 (chris): add logging
 * 07 Feb 2014 (chris): add suspend transaction logic in updateNicStatus()
 * 14 Feb 2014 (chris): add output of cscr csct in downloadNicPersoInfo()
 * 12 Mar 2014 (chris): to filter findIssuanceData by sitecode
 */
public class NicWSImpl {
//public class NicWSImpl implements NicWS {
//	
//	protected static final Logger logger = LoggerFactory.getLogger(NicWSImpl.class);
//	
//	@Autowired
//	private TransactionLogService transactionLogService;
//	
//	@Autowired
//	private IssuanceDataService issuanceDataService;
//	
//	@Autowired
//	private NicUploadJobService uploadJobService;
//	
//	@Autowired
//	private NicMainService nicMainService;
//	
//	@Override
//	public String updateNicStatus(NicTransStatusUpdate transStatusInput,
//			NicCardStatusUpdate cardStatusInput,
//			NicPackageStatusUpdate packageStatusInput) throws FaultException  {
//		logger.info("NicWSImpl.updateNicStatus(): begin");
//		String result = Constant.TRANSACTION_FAILED;//"failed";
//		try {
//			if (transStatusInput!=null) {
//				if (StringUtils.isEmpty(transStatusInput.getTransactionID())) 
//					throw new FaultException ("Transaction Id cannot be empty");
//				String cardStatus = null;
//				if (cardStatusInput!=null && StringUtils.isNotEmpty(cardStatusInput.getCardStatus())) {
//					cardStatus = cardStatusInput.getCardStatus();
//				}
//				this.issuanceDataService.updateTransactionByTransactionId(
//						transStatusInput.getTransactionID(), 
//						transStatusInput.getTransactionStatus(),
//						cardStatus,
//						transStatusInput.getUpdateBy(),
//						transStatusInput.getUpdateWkstnID());
//			}
//			
//			if (packageStatusInput!=null) {
//				if (CollectionUtils.isEmpty(packageStatusInput.getPackageID())) {
//					throw new FaultException ("List of packageId cannot be empty");
//				}
//				
//				this.issuanceDataService.updateStatusByPackageId(packageStatusInput.getPackageID(), 
//						      packageStatusInput.getStatus());
//			}	
//			
//			if (cardStatusInput!=null) {
//				if (StringUtils.isEmpty(cardStatusInput.getCcn()) 
//						|| StringUtils.isEmpty(cardStatusInput.getCardStatus()) )
//					throw new FaultException("CCN and Status cannot be empty");
//				String ccn = cardStatusInput.getCcn();
//				String cardStatus = cardStatusInput.getCardStatus();
//				String cardStatusChangeReason = cardStatusInput.getCardStatusChangeReason();
//				Date cardStatusChangeTime = cardStatusInput.getCardStatusChangeTime();
//				String officerId = cardStatusInput.getUpdateBy();
//				String workstationId = cardStatusInput.getUpdateWkstnID();
//				
//				this.issuanceDataService.updateStatusByCCn(ccn, cardStatus, cardStatusChangeReason, cardStatusChangeTime, officerId, workstationId);
//			}
//			result = Constant.TRANSACTION_SUCCESS;//"Successfully";
//		} catch (IssuanceServiceException ex) {
//			if (StringUtils.equals(IssuanceDataService.UPDATE_TRANSACTION_STATUS_ERROR_CODE_SUSPENSION_DENIED, ex.getErrorCode())) {
//				result = Constant.TRANSACTION_ERROR;
//			} else if (StringUtils.equals(IssuanceDataService.UPDATE_TRANSACTION_STATUS_ERROR_CODE_SUSPENSION_FAILED, ex.getErrorCode())) {
//				result = Constant.TRANSACTION_FAILED;
//			} else 
//				throw new FaultException("Error occured in NicWSImpl.updateNicStatus:"+ex.getMessage(), ex);
//		}
//		logger.info("NicWSImpl.updateNicStatus(): end");
//		return result;
//	}
//
//	@Deprecated //please use dxWSImpl.uploadNicPersoInfo 
//	@Override
//	public String uploadNicPersoInfo(NicPersoInfoUpload input) throws FaultException {
//		
//		String result = Constant.TRANSACTION_FAILED;
//		if (input==null) return result;
//		if (true) {
//			return result;
//		}
//		logger.info("NicWSImpl.uploadNicPersoInfo() : Start");
//		boolean isDataValid = false;
//		NicTransactionLog transactionLog = new NicTransactionLog();
//
//		try {
//			//populate stub class of IssuanceData from input 
//			NicIssuanceDataId id = new NicIssuanceDataId();
//			id.setTransactionId(input.getTransactionID());
//			id.setPersoRefId(input.getPersoRefID());
//			NicIssuanceData nicIssuanceData = this.issuanceDataService.findById(id);
//			if (nicIssuanceData == null)
//				throw new FaultException("No existing card data found for transactionId:" + input.getTransactionID() +"; perso refID:" + input.getPersoRefID());
//
//			nicIssuanceData.setNin(input.getNin());
//						
//			//initialize for transaction log
//			transactionLog = new NicTransactionLog();
//			transactionLog.setRefId(input.getTransactionID());
//			transactionLog.setLogCreateTime(new Date());
//			
//			//from LogData
//			if (input.getLogData()!=null) {
//				nicIssuanceData.setCcn(input.getLogData().getCcn());
//				nicIssuanceData.setPackageId(input.getLogData().getPackageID());
//				nicIssuanceData.setSamKeyVersion(new Short(input.getLogData().getSAMKeyVersion()));
//				nicIssuanceData.setUpdateBy("SYSTEM_PERSO");
//				isDataValid = true;
//			}
//			
//			//Construct LogInfo that indicate Card is error or rejected
//			if (input.getLogInfo()!=null) {
//				transactionLog.setTransactionStatus(Constant.TRANSACTION_STATUS_PERSO_ERROR);
//				transactionLog.setLogInfo(this.getLogInfoXml(input.getLogInfo()));
//			}
//			
//			if (isDataValid ==true) {
//				
//				nicIssuanceData.setCardStatus(Constant.CARD_STATUS_DISPATCHED);				
//				this.issuanceDataService.updateIssuanceData(nicIssuanceData);						
//				transactionLog.setTransactionStatus(Constant.TRANSACTION_STATUS_PERSO_DISPATCHED);
//				
//				transactionLog.setLogData(this.getLogDataXml(input.getLogData()));
//				result = Constant.TRANSACTION_SUCCESS;
//			}			
//			this.transactionLogService.save(transactionLog);
//			
//		} catch (IssuanceServiceException ise) {
//			logger.error("Error occured in NicWSImpl.uploadNicPersoInfo()", ise);
//			throw new FaultException("Error occured in NicWSImpl.uploadNicPersoInfo:"+ise.getMessage(), ise);
//		}
//		return result;
//	}
//
//	@Override
//	public NicPersoInfoDownloadResult downloadNicPersoInfo(
//			NicPersoInfoDownloadFilter input) throws FaultException {
//		NicPersoInfoDownloadResult result = new NicPersoInfoDownloadResult();
//		logger.info("NicWSImpl.downloadNicPersoInfo(): begin");
//		try {
//			String siteCode = input.getSiteCode();
//			Date startDate = input.getStartDate();
//			Date endDate = input.getEndDate();
//			String officerId = input.getOfficerID();
//			String wkstnId = input.getWkstnID();
//
//			//22 Oct 2013 (chris): add input for nin/ccn in downloadNicPersoInfo() - start
//			boolean isAdhocRequest = false;
//			String ccn = input.getCcn();
//			String nin = input.getNin();
//			String packageId = input.getPackageID();
//			if (StringUtils.isNotBlank(ccn) || StringUtils.isNotBlank(nin) || StringUtils.isNotBlank(packageId)) {
//				isAdhocRequest = true;
//			}		
//			List<NicIssuanceData> entityList = null;
//			List<RejectionData> rejectionList = null;
//			if (isAdhocRequest) {
//				entityList = this.issuanceDataService.findIssuanceData(packageId, ccn, nin, siteCode, officerId, wkstnId);
//			} else {
//				logger.info("[downloadNicPersoInfo][issuanceData] siteCode:{}, startDate:{}, endDate:{} ", new Object[]{ siteCode, startDate, endDate});
//				entityList = this.issuanceDataService.findByPeriod(input.getSiteCode(), input.getStartDate(), input.getEndDate());
//				logger.info("[downloadNicPersoInfo][rejectionData] siteCode:{}, startDate:{}, endDate:{} ", new Object[]{ siteCode, startDate, endDate});
//				rejectionList = uploadJobService.getRejectionDataListBySiteCode(siteCode, startDate, endDate);
//			}
//			//22 Oct 2013 (chris): add input for nin/ccn in downloadNicPersoInfo() - end	
//			//	List<NicIssuanceData> entityList = this.issuanceDataService.findByPeriod(input.getSiteCode(), input.getStartDate(), input.getEndDate());
//	
//			IssuanceData issuanceData = null;
//			if (CollectionUtils.isNotEmpty(entityList)) {
//				for (NicIssuanceData data : entityList) {
//					issuanceData = new IssuanceData();
//					issuanceData.setTransactionID(data.getId().getTransactionId());
//					issuanceData.setPersoRefID(data.getId().getPersoRefId());
//					issuanceData.setPackageID(data.getPackageId());
//					issuanceData.setPackageSequence(data.getPackageSequence());//13 Sep 2013 (chris)
//					issuanceData.setPackageDate(data.getPackageDate());//13 Sep 2013 (chris)
//					issuanceData.setDispatchID(data.getDispatchId());//13 Sep 2013 (chris)
//					issuanceData.setCcn(data.getCcn());
//					if (data.getSamKeyVersion()!=null)
//						issuanceData.setSamKeyVersion(data.getSamKeyVersion().toString());
//					issuanceData.setNin(data.getNin());
//					issuanceData.setDateOfIssue(data.getDateOfIssue());//13 Sep 2013 (chris)
//					issuanceData.setCreateDate(data.getCreateDate());
//					issuanceData.setCreateBy(data.getCreateBy());
//					issuanceData.setUpdateBy(data.getUpdateBy());
//					issuanceData.setUpdateDate(data.getUpdateDate());
//					issuanceData.setCardStatus(data.getCardStatus());
//					issuanceData.setIssuanceDecision(data.getIssuanceDecision());
//					issuanceData.setIssuanceOfficerID(data.getIssuanceOfficerId());
//					issuanceData.setCollectionDate(data.getCollectionDate());
//					//14 Feb 2014 (chris): add output of cscr csct - start
//					issuanceData.setCardStatusChangeReason(data.getCardStatusChangeReason());
//					issuanceData.setCardStatusChangeTime(data.getCardStatusChangeTime());
//					//14 Feb 2014 (chris): add output of cscr csct - end
//					result.getIssuanceDatas().add(issuanceData);
//				}
//			}
//			//to get rejection data			
//			boolean hasRejectData = CollectionUtils.isNotEmpty(rejectionList);
//			if (hasRejectData) {
//				result.getRejectionDatas().addAll(rejectionList);
//			}
//			logger.info("[downloadNicPersoInfo] siteCode:{}, result.issueData.size:{}, result.rejectData.size:{}", new Object[]{ siteCode, result.getIssuanceDatas().size(), result.getRejectionDatas().size()});
//		}catch (IssuanceServiceException ex) {
//			logger.error("Error occured on downloadNicPersoInfo caused of:" + ex.getMessage(), ex);
//			throw new FaultException ("Error occured on downloadNicPersoInfo:"+ex.getMessage(),ex);
//		} catch (Exception ex) {
//			logger.error("Error occured on downloadNicPersoInfo caused of:" + ex.getMessage(), ex);
//			throw new FaultException ("Error occured on downloadNicPersoInfo:"+ex.getMessage(),ex);
//		}
//		logger.info("NicWSImpl.downloadNicPersoInfo(): end");
//		return result;
//	}
//
//	@Override
//	public NicInfoRetrievalResult inquiryNicInfo(NicInfoRetrievalFilter input)
//			throws FaultException {
//		NicInfoRetrievalResult result = new NicInfoRetrievalResult();
//		logger.info("NicWSImpl.inquiryNicInfo(): begin");
//		String transactionId = input.getTransactionID();
//		String nin = input.getNin();
//		String ccn = input.getCcn();
//		
//		String surname = input.getSurname();
//		String firstName = input.getFirstName();
//		String surnameAtBirth = input.getSurnameAtBirth();
//		String sex = input.getGender();
//		String dob = null;
//		if (input.getBirthDate()!=null) {
//			dob = DateUtil.parseDate(input.getBirthDate(), DateUtil.FORMAT_DDdashMMdashYYYY);
//		} 
//		
//		boolean getStatusFlag = false;
//		
//		for (NicInfoRetrievalDataType dataType: input.getDataType()) {
//			if (NicInfoRetrievalDataType.STATUS.equals(dataType)) {
//				getStatusFlag = true;
//			}
//			if (NicInfoRetrievalDataType.ALL.equals(dataType)) {
//				getStatusFlag = true;
//			}
//		}
//		try {
//			logger.info("[inquiryNicInfo] ccn:{}, surname:{}, firstName:{}, surnameAtBirth:{}, gender:{}, dob:{} ", new Object[]{ ccn, surname, firstName, surnameAtBirth, sex, dob});
//			List<NicInfo> nicInfoList = nicMainService.findNicInfoByFields(transactionId, nin, ccn, surname, firstName, surnameAtBirth, sex, dob, getStatusFlag);
//			if (CollectionUtils.isNotEmpty(nicInfoList)) {
//				NicInfo nicInfo = nicInfoList.get(0);
//				result.setNicInfo(nicInfo);
//			} 
//		} catch (NicMainServiceException e) {
//			logger.error("Error occured on inquiryNicInfo caused of:" + e.getMessage(), e);
//			throw new FaultException ("Error occured on inquiryNicInfo:" + e.getMessage(), e);
//		}
//		logger.info("NicWSImpl.inquiryNicInfo(): end");
//		return result;
//	}
//
//	//retrieve nic history transaction id
//	public NicHistoryRetrievalResult retrieveNicHistory(
//			NicHistoryRetrievalFilter input) throws FaultException {
//		NicHistoryRetrievalResult result = new NicHistoryRetrievalResult();
//		logger.info("NicWSImpl.retrieveNicHistory(): begin");
//		String nin = input.getNin();
//		for (NicHistoryRetrievalDataType dataType : input.getDataType()){
//			if (NicHistoryRetrievalDataType.BASIC.equals(dataType)) {
//				
//			}
//		}
//		String transactionId = null;
//		String ccn = null;
//		String surname = null;
//		String firstName = null;
//		String surnameAtBirth = null;
//		String sex = null;
//		String dob = null;
//		boolean getStatusFlag = true;
//		try {
//			logger.info("[inquiryNicInfo] nin:{} ", new Object[]{ nin});
//			List<NicInfo> nicInfoList = nicMainService.findNicInfoByFields(transactionId, nin, ccn, surname, firstName, surnameAtBirth, sex, dob, getStatusFlag);
//			for (NicInfo nicInfo : nicInfoList) {
//				result.getNicInfos().add(nicInfo);
//			}
//		} catch (NicMainServiceException e) {
//			logger.error("Error occured on retrieveNicHistory caused of:" + e.getMessage(), e);
//			throw new FaultException ("Error occured on retrieveNicHistory:" + e.getMessage(), e);
//		}
//		logger.info("NicWSImpl.retrieveNicHistory(): end");
//		return result;
//	}
//	
//	
//	private String getLogDataXml (NicPersoInfoDetail persoInfoDetail) {
//		String result = null;
//		if (persoInfoDetail==null)
//			return null;
//		
//		try {
//			TransLogInfoXmlConvertor convertor = new TransLogInfoXmlConvertor();
//			PersoLogDataDTO logDataDto = new PersoLogDataDTO();
//			try {
//				BeanUtils.copyProperties(logDataDto, persoInfoDetail);
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			}
//			
//			result = convertor.marshal(logDataDto);	
//			
//		}catch (JaxbXmlConvertorException jaxb) {
//			logger.error("Error Occured when getLogDataXML. caused:" + jaxb.getMessage(), jaxb);
//		}
//		return result;
//	}
//	
//	private String getLogInfoXml (LogInfo logInfo ) {
//		String result = null;
//		if (logInfo==null)
//			return null;
//		
//		try {
//			TransLogInfoXmlConvertor convertor = new TransLogInfoXmlConvertor();
//			LogInfoDTO logInfoDto = new LogInfoDTO();
//			try {
//				BeanUtils.copyProperties(logInfoDto, logInfo);
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			}
//			
//			result = convertor.marshal(logInfoDto);	
//			
//		}catch (JaxbXmlConvertorException jaxb) {
//			logger.error("Error Occured when getLogDataXML. caused:" + jaxb.getMessage(), jaxb);
//		}
//		return result;
//	}
//	
//
//	@Override
//	public String updateReceivedCardStatus(UpdateReceivedCardStatus input)
//			throws FaultException {
//		String result = Constant.TRANSACTION_FAILED;//"failed";
//		logger.info("NicWSImpl.updateReceivedCardStatus(): begin");
//		List<ReceivedCardInfo> cardInfoList = input.getReceivedCardInfos();
//		
//		//22 Oct 2013 (chris): add officerId and siteCode - start
//		String officerId = input.getOfficerID();
//		String workstationId = input.getWkstnID();
//		String siteCode = input.getSiteCode();
//		logger.debug("[updateReceivedCardStatus] OfficerID={}, WkstnID={}, SiteCode={}", new Object[]{ officerId, workstationId, siteCode});
//		//22 Oct 2013 (chris): add officerId and siteCode - end
//		
//		if (CollectionUtils.isEmpty(cardInfoList))
//			throw new FaultException ("List of ReceivedCardInfo cannot be empty");
//		if (StringUtils.isBlank(siteCode))
//			throw new FaultException ("Field siteCode is mandantory"); //23 Oct 2013 (chris): add siteCode validation
//		
//		List<NicIssuanceDataId> idList = new ArrayList<NicIssuanceDataId>();
//		List<String> rejectionTxnIdList = new ArrayList<String>();
//		NicIssuanceDataId id = null;
//		for (ReceivedCardInfo cardInfo : cardInfoList) {
//			if (StringUtils.isNotBlank(cardInfo.getPersoRefID())) {
//				id = new NicIssuanceDataId(cardInfo.getTransactionID(), cardInfo.getPersoRefID());
//				idList.add(id);
//			} else {
//				rejectionTxnIdList.add(cardInfo.getTransactionID());
//			}
//		}
//		try {
//			if (CollectionUtils.isNotEmpty(idList)) {
//				logger.debug("[updateReceivedCardStatus] updating receive_card for issuance_data, size={}", new Object[]{ idList.size() });
//				issuanceDataService.updateReceivedCardStatus(idList, officerId, workstationId, siteCode);
//			}
//			if (CollectionUtils.isNotEmpty(rejectionTxnIdList)) {
//				logger.debug("[updateReceivedCardStatus] updating receive_card for rejection_data, size={}", new Object[]{ rejectionTxnIdList.size() });
//				uploadJobService.updateRejectionDataDownloadedStatus(rejectionTxnIdList, officerId, workstationId, siteCode);
//			}
//			//result = "success";
//			result = Constant.TRANSACTION_SUCCESS;
//		}catch (IssuanceServiceException ex) {
//			throw new FaultException("Error occured."+ex.getMessage(), ex);
//		} catch (NicUploadJobServiceException e) {
//			throw new FaultException("Error occured."+e.getMessage(), e);
//		}
//		logger.info("NicWSImpl.updateReceivedCardStatus(): end");
//		return result;
//	}
//	
//	@Override
//	public String echo(String input) throws FaultException {
//		logger.info("NicWSImpl.echo(): end");
//		return "1";
//	}
//
//	/**
//	 * @param transactionLogService the transactionLogService to set
//	 */
//	public void setTransactionLogService(TransactionLogService transactionLogService) {
//		this.transactionLogService = transactionLogService;
//	}
//
//	/**
//	 * @param issuanceDataService the issuanceDataService to set
//	 */
//	public void setIssuanceDataService(IssuanceDataService issuanceDataService) {
//		this.issuanceDataService = issuanceDataService;
//	}
//
//		
}
