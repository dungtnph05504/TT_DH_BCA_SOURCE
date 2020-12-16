/**
 * 
 */
package com.nec.asia.nic.dx.ws.impl;

//import java.util.List;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.builder.ReflectionToStringBuilder;
//import org.apache.commons.lang.builder.ToStringStyle;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.nec.asia.nic.comp.trans.service.CardStockTrackingService;
//import com.nec.asia.nic.dx.cardstock.CardMovementRetrievalRecordType;
//import com.nec.asia.nic.dx.cardstock.CardStockTracking;
//import com.nec.asia.nic.dx.cardstock.RetrieveCardMovementHistoryRequest;
//import com.nec.asia.nic.dx.cardstock.RetrieveCardMovementHistoryResponse;
//import com.nec.asia.nic.dx.cardstock.UpdateCardMovementRequest;
//import com.nec.asia.nic.dx.cardstock.UpdateCardMovementResponse;
//import com.nec.asia.nic.dx.ws.CardstockWS;
//import com.nec.asia.nic.dx.ws.Constant;

/**
 * The implementation class for card stock tracking webservice, such card stock in, card stock out.
 * 
 * @author chris_wong
 *
 */

/* 
 * Modification History:
 *  
 * 06 Jan 2014 (chris): init methods
 */
public class CardstockWSImpl {
//public class CardstockWSImpl implements CardstockWS {
//	private static Logger logger = LoggerFactory.getLogger(CardstockWSImpl.class);
//	
//	@Autowired
//	private CardStockTrackingService cardStockTrackingService;
//	/* (non-Javadoc)
//	 * @see com.nec.asia.nic.dx.ws.CardstockWS#updateCardMovement(com.nec.asia.nic.dx.cardstock.UpdateCardMovementRequest)
//	 */
//	@Override
//	public UpdateCardMovementResponse updateCardMovement(
//			UpdateCardMovementRequest input) {
//		UpdateCardMovementResponse response = new UpdateCardMovementResponse();
//		try {
//			logger.info("[CardstockWSImpl] updateCardMovement - {}", ReflectionToStringBuilder.toString(input, ToStringStyle.MULTI_LINE_STYLE));
//			cardStockTrackingService.updateCardStockList(input.getCardStockTracking(), input.getSiteCode(), input.getUpdateBy(), input.getUpdateWkstnId(), input.getUpdateDate());
//			response.setStatusCode(Constant.TRANSACTION_SUCCESS);
//		} catch (Exception e) {
//			logger.error("[CardstockWSImpl] updateCardMovement encountered unexpected error: ", e);
//			response.setStatusCode(Constant.TRANSACTION_ERROR);
//			response.setDetailMessage(e.getMessage());
//		}
//		return response;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.nec.asia.nic.dx.ws.CardstockWS#retrieveCardMovementHistory(com.nec.asia.nic.dx.cardstock.RetrieveCardMovementHistoryRequest)
//	 */
//	@Override
//	public RetrieveCardMovementHistoryResponse retrieveCardMovementHistory(
//			RetrieveCardMovementHistoryRequest input) {
//		RetrieveCardMovementHistoryResponse response = new RetrieveCardMovementHistoryResponse();
//		try {
//			logger.info("[CardstockWSImpl] retrieveCardMovementHistory - {}", ReflectionToStringBuilder.toString(input, ToStringStyle.MULTI_LINE_STYLE));
//			if (input.getRecordType().equals(CardMovementRetrievalRecordType.ALL)) {
//				List<CardStockTracking> cardStockList = cardStockTrackingService.findAllCardStockListByCcn(input.getCcn(), input.getSiteCode(), input.getOfficerId(), input.getWorkstationId());
//				if (CollectionUtils.isNotEmpty(cardStockList))
//					response.getCardStockTracking().addAll(cardStockList);
//			} else if (input.getRecordType().equals(CardMovementRetrievalRecordType.LATEST)) {
//				CardStockTracking latestCardStock = cardStockTrackingService.findCardStockByCcn(input.getCcn(), input.getSiteCode(), input.getOfficerId(), input.getWorkstationId());
//				if (latestCardStock!=null)
//					response.getCardStockTracking().add(latestCardStock);
//			}
//			response.setStatusCode(Constant.TRANSACTION_SUCCESS);
//		} catch (Exception e) {
//			logger.error("[CardstockWSImpl] retrieveCardMovementHistory encountered unexpected error: ", e);
//			response.setStatusCode(Constant.TRANSACTION_ERROR);
//			response.setDetailMessage(e.getMessage());
//		}
//		return response;
//	}

}
