package com.nec.asia.nic.dx.ws.impl;

//import java.net.InetAddress;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.nec.asia.nic.comp.trans.domain.NicChangeStatusLog;
//import com.nec.asia.nic.comp.trans.domain.NicMain;
//import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
//import com.nec.asia.nic.comp.trans.service.CardStatusUpdateService;
//import com.nec.asia.nic.comp.trans.service.IssuanceDataService;
//import com.nec.asia.nic.comp.trans.service.NicMainService;
//import com.nec.asia.nic.dx.terminate.TerminateNic;
//import com.nec.asia.nic.dx.terminate.TerminateNicResponse;
//import com.nec.asia.nic.dx.ws.Constant;
//import com.nec.asia.nic.dx.ws.TerminationWS;
//import com.nec.asia.nic.framework.report.service.AuditAccessLogService;

/**
 * The Web Service Implementation to expose to external party to update card termination info.
 *  
 * @author chris_wong
 *
 */

/* 
 * Modification History:
 *  
 * 08 Sep 2013 (chris): fix to return error code if NIN no found in nic_main.
 * 29 Oct 2013 (chris): change create by / update by to SYSTEM_CPDV2
 * 06 Nov 2013 (chris): add termination status.
 * 07 Nov 2013 (chris): add logging
 * 28 Jan 2014 (chris): handle repeat updates.
 * 21 Jul 2014 (chris): add audit access log.
 */

public class TerminationWSImpl {
//public class TerminationWSImpl implements TerminationWS {
//	protected static final Logger logger = LoggerFactory.getLogger(TerminationWSImpl.class);
//	private final String serviceName = "TerminationWS"; //21 Jul 2014 (chris)
//	
//	@Autowired
//	NicMainService mainService;
//	@Autowired
//	CardStatusUpdateService cardStatusUpdateService;
//	@Autowired
//	IssuanceDataService issuanceDataService;
//	@Autowired
//	AuditAccessLogService auditAccessLogService; //21 Jul 2014 (chris)
//	
//	/* (non-Javadoc)
//	 * @see com.nec.asia.nic.dx.ws.impl.TerminationWS#terminateNic(com.nec.asia.nic.dx.ws.type.NicTermination)
//	 */
//	/*
//	 * E000001	Username / Password is invalid
//	 * I500000	OK
//	 * E500001	Field <field name> is mandatory
//	 * E500002	Max length of field <field name> is reached
//	 * E500003	No result
//	 * E999999	An unexpected error occurred
//	 */
//	@Override
//	public TerminateNicResponse terminateNic(TerminateNic terminateNicRequest) {
//		TerminateNicResponse statusCodeResult = new TerminateNicResponse();
//		logger.info("TerminationWSImpl.terminateNic(): begin");
//		//21 Jul 2014 (chris) - start
//		Date startTime = new Date();
//		Object[] inArgs = new Object[] { terminateNicRequest };
//		Object[] outResponses = new Object [] { statusCodeResult }; 
//		Throwable throwable = null;
//		//21 Jul 2014 (chris) - end
//		try {
//			//29 Oct 2013 (chris) - start
//			String officerId = Constant.SYSTEM_CPDV2; //"SYSTEM";
//			String wkstnId =  Constant.SYSTEM_CPDV2; //"SYSTEM";
//			try {
//				java.net.InetAddress localMachine = InetAddress.getLocalHost();
//				wkstnId = localMachine.getHostName();
//			} catch (Exception e) {			
//			}
//			//29 Oct 2013 (chris) - end
//			
//			if (terminateNicRequest!=null) {
//				logger.info("nin:{}", terminateNicRequest.getNin());
//				logger.debug("terminationType:{}", terminateNicRequest.getTerminationType());
//				logger.debug("terminationDate:{}", terminateNicRequest.getTerminationDate());
//				logger.debug("terminationRemarks:{}", terminateNicRequest.getTerminationRemarks());
//				if (StringUtils.isBlank(terminateNicRequest.getNin())) {
//					statusCodeResult.setStatusCode(Constant.TERWS_MANDATORY_FIELD_ERROR);
//					statusCodeResult.setFaultDescription("Field <nin> is mandatory.");
//				} else {
//					String nin = terminateNicRequest.getNin();
//					String cardStatus = NicChangeStatusLog.CARD_STATUS_TERMINATED;
//					String cardStatusChangeReason = NicChangeStatusLog.CSCR_TERMINATION_DUE_TO_DEATH;
//					String transactionStatus = NicTransactionLog.TRANSACTION_STATUS_EXT_CARD_TERMINATED;
//					
//					List<NicMain> resultList = mainService.findAllByNin(nin);
//					if (CollectionUtils.isEmpty(resultList)) {
//						statusCodeResult.setStatusCode(Constant.TERWS_NO_RESULT_ERROR);
//						statusCodeResult.setFaultDescription("No Citizen found.");
//						logger.info("[terminateNic] No Citizen found for nin:{}", nin);
//					} else {
//						boolean isCcnFound = false;
//						for (NicMain main: resultList) {
//							String transactionId = main.getTransactionId();
//							String ccn = main.getCcn();
//							if (StringUtils.isNotBlank(ccn)) {
//								//to check card_status, card_status_change_reason
//								String dbCardStatus = main.getCardStatus();
//								String dbCardStatusChangeReason = main.getCardStatusChangeReason();
//								if (StringUtils.equals(cardStatus, dbCardStatus) && StringUtils.equals(cardStatusChangeReason, dbCardStatusChangeReason)) {
//									logger.info("[terminateNic] ccn:{}, transactionId:{}, cardStatus:{} , DB is in sync, update not required.", new Object[]{ccn, transactionId, cardStatus});
//								} else {								
//									cardStatusUpdateService.updateCardStatus(transactionId, ccn, cardStatus, new Date(), cardStatusChangeReason, officerId, wkstnId);//"CPD", "CPDSYSTEM");
//									logger.info("[terminateNic] ccn:{}, transactionId:{} -- update cardStatus:{} ", new Object[]{ccn, transactionId, cardStatus});
//								}
//								issuanceDataService.updateTransactionByTransactionId(transactionId, transactionStatus, cardStatus, officerId, wkstnId);
//								isCcnFound = true;
//							}
//						}
//						if (isCcnFound) {
//							statusCodeResult.setStatusCode(Constant.TERWS_TRANSACTION_COMPLETED);
//							statusCodeResult.setFaultDescription("Transaction Completed.");
//						} else {
//							statusCodeResult.setStatusCode(Constant.TERWS_NO_RESULT_ERROR);
//							statusCodeResult.setFaultDescription("Citizen doesn't had a valid card.");
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("[terminateNic] An unexpected error occurred", e);
//			throwable = e; //21 Jul 2014 (chris)
//			statusCodeResult.setStatusCode(Constant.TERWS_UNEXPECTED_ERROR);//"E999999");
//			statusCodeResult.setFaultDescription("An unexpected error occurred");
//		} 
//		//21 Jul 2014 (chris) - start
//		finally {
//			Date endTime = new Date();
//			long timeMs = endTime.getTime()-startTime.getTime();
//			try {
//				auditAccessLogService.saveAuditAccessLogForWS(serviceName, "terminateNic", Constant.SYSTEM_CPDV2, Constant.SYSTEM_CPDV2, inArgs, outResponses, throwable, timeMs);
//			} catch (Exception e) {
//				logger.error("[terminateNic] unable to create audit access log: {}", e.getMessage());
//			}
//		}
//		//21 Jul 2014 (chris) - end
//		logger.info("TerminationWSImpl.terminateNic(): end");
//		return statusCodeResult;
//	}

}
