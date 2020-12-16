/**
 * 
 */
package com.nec.asia.nic.dx.ws.impl;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.builder.ReflectionToStringBuilder;
//import org.apache.commons.lang.builder.ToStringStyle;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.nec.asia.nic.comp.trans.domain.NicTransaction;
//import com.nec.asia.nic.comp.trans.service.NicTransactionService;
//import com.nec.asia.nic.dx.validate.TransactionInfo;
//import com.nec.asia.nic.dx.validate.ValidateNic;
//import com.nec.asia.nic.dx.validate.ValidateNicResponse;
//import com.nec.asia.nic.dx.ws.StatusCode;
//import com.nec.asia.nic.dx.ws.ValidateWS;
//import com.nec.asia.nic.utils.DateUtil;

/**
 * The implementation class for main transaction online validation webservice, such as check Duplicate Conversion application.
 * 
 * @author chris_wong
 *
 */

/* 
 * Modification History:
 *  
 * 23 May 2014 (chris): init methods
 * 26 May 2014 (chris): set default transaction id if blank for validateNic()
 * 30 May 2014 (chris): update error code for MAX_LENGTH
 * 
 */
public class ValidateWSImpl {
//public class ValidateWSImpl implements ValidateWS {
//	private static Logger logger = LoggerFactory.getLogger(ValidateWSImpl.class);
//	@Autowired
//	private NicTransactionService nicTransactionService = null;
//	
//	@Override
//	public ValidateNicResponse validateNic(
//			ValidateNic input) {
//		ValidateNicResponse response = new ValidateNicResponse();
//		try {
//			logger.info("[ValidateWSImpl] validateNic - {}", ReflectionToStringBuilder.toString(input, ToStringStyle.MULTI_LINE_STYLE));
//			String nin=input.getNin();
//			String transactionId=input.getTransactionID();
//			String transactionType=input.getTransactionType();
//			String remarks=input.getRemarks();
//			String officerId=input.getOfficerID();
//			String wkstnId=input.getWorkstationID();
//			String siteCode=input.getSiteCode();
//			boolean valid = true;
//			if (StringUtils.isBlank(nin)) {
//				valid = false;
//				response.setStatusCode(StatusCode.VALWS_MANDATORY_FIELD_ERROR.getKey());
//				response.setStatusDescription(StatusCode.VALWS_MANDATORY_FIELD_ERROR.getDesc()+" -- nin");
//			} else if (StringUtils.isBlank(transactionType)) {
//				valid = false;
//				response.setStatusCode(StatusCode.VALWS_MANDATORY_FIELD_ERROR.getKey());
//				response.setStatusDescription(StatusCode.VALWS_MANDATORY_FIELD_ERROR.getDesc()+" -- transactionType");
//			} 
//			//MAX LENGTH VALIDATION
//			else if (StringUtils.length(nin)>14) {
//				valid = false;
//				response.setStatusCode(StatusCode.VALWS_FIELD_LENGTH_EXCEEDED.getKey());
//				response.setStatusDescription(StatusCode.VALWS_FIELD_LENGTH_EXCEEDED.getDesc()+" -- nin");
//			}
//			//set default transaction id for logging
//			if (StringUtils.isBlank(transactionId)) {
//				if (StringUtils.isNotBlank(siteCode)) {
//					transactionId = siteCode+"-"+DateUtil.parseDate(DateUtil.getSystemDate(), "yyyy-MMddHHmmss");
//				} else {
//					transactionId = nin;
//				}
//			} 
//			
//			if (valid) {
//				List<NicTransaction> hitTransactionList = nicTransactionService.checkDuplicateByNin(transactionId, nin, transactionType, remarks, officerId, wkstnId, siteCode);
//				if (CollectionUtils.isNotEmpty(hitTransactionList)) {
//					response.setStatusCode(StatusCode.VALWS_FAILED_WITH_ACTIVE_TXN.getKey());
//					response.setStatusDescription(StatusCode.VALWS_FAILED_WITH_ACTIVE_TXN.getDesc());
//					response.getTransactionInfos().addAll(parseTransactionInfoList(hitTransactionList));
//				} else {
//					response.setStatusCode(StatusCode.VALWS_NIC_CHECK_PASSED.getKey());
//					response.setStatusDescription(StatusCode.VALWS_NIC_CHECK_PASSED.getDesc());
//				}
//			}
//		} catch (Exception e) {
//			logger.error("[ValidateWSImpl] validateNic encountered unexpected error: ", e);
//			response.setStatusCode(StatusCode.VALWS_UNEXPECTED_ERROR.getKey());
//			response.setStatusDescription(StatusCode.VALWS_UNEXPECTED_ERROR.getDesc()+"["+e.getMessage()+"]");
//		}
//		return response;
//	}
//	
//	private List<TransactionInfo> parseTransactionInfoList(List<NicTransaction> nicTransactionList) {
//		List<TransactionInfo> resultList = new ArrayList<TransactionInfo>();
//		for (NicTransaction nicTransaction : nicTransactionList) {
//			TransactionInfo transactionInfoDTO = new TransactionInfo();
//			transactionInfoDTO.setNin(nicTransaction.getNin());
//			transactionInfoDTO.setTransactionID(nicTransaction.getTransactionId());
//			transactionInfoDTO.setTransactionType(nicTransaction.getTransactionType());
//			transactionInfoDTO.setTransactionSubtype(nicTransaction.getTransactionSubtype());
//			transactionInfoDTO.setTransactionStatus(nicTransaction.getTransactionStatus());
//			transactionInfoDTO.setDateOfApplication(nicTransaction.getDateOfApplication());
//			transactionInfoDTO.setRegSiteCode(nicTransaction.getRegSiteCode());
//			transactionInfoDTO.setIssSiteCode(nicTransaction.getIssSiteCode());
//			transactionInfoDTO.setDateOfIssue(nicTransaction.getDateOfIssue());
//			resultList.add(transactionInfoDTO);
//		}
//		return resultList;
//	}
}
