package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.dto.CardStatusRptDTO;
import com.nec.asia.nic.comp.trans.dto.ExceptionHandlngRptDto;
import com.nec.asia.nic.comp.trans.dto.RICBatchCardInfoDto;
import com.nec.asia.nic.comp.trans.dto.RICPymtRptDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnRptDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnUpldRptDto;
import com.nec.asia.nic.comp.trans.dto.RICWaiverRptDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

/*
 * 16 jan 2014 (priya): added ricSiteCheck for getRicBatchCardInfoRptRecordList,getCardCollectedStatusRptRecordList,cardRejStatusRptCriteria,unColCardStatuSrchCriteria
 * 10 feb 2014 (priya): added ricReport Methods and Rpt_id and Name for the RIC REPORTS.
 */
public interface RICReportService extends
		BusinessService<NicTransaction, String> {
	public PaginatedResult<RICTxnRptDto> getRicTxnRptRecordList(
			RICTxnRptDto ricTxnRptCriteria, int pageNo, int pageSize);

	public PaginatedResult<RICPymtRptDto> getRicPymtRptRecordList(
			RICPymtRptDto ricPymtRptCriteria, int pageNo, int pageSize);

	public PaginatedResult<RICWaiverRptDto> getRicWaiverRptRecordList(
			RICWaiverRptDto ricWaiverRptCriteria, int pageNo, int pageSize);

	public PaginatedResult<RICTxnUpldRptDto> getRicTxnUploadRptRecordList(
			RICTxnUpldRptDto ricTxnUpldRptCriteria, int pageNo, int pageSize);

	public PaginatedResult<RICTxnRptDto> getRicRejTxnRptRecordList(
			RICTxnRptDto ricRejTxnRptCriteria, int pageNo, int pageSize);

	public PaginatedResult<RICBatchCardInfoDto> getRicBatchCardInfoRptRecordList(
			RICBatchCardInfoDto ricBatchCardInfoRptCriteria, int pageNo,
			int pageSize);

	public PaginatedResult<CardStatusRptDTO> getCardCollectedStatusRptRecordList(
			CardStatusRptDTO cardColStatusRptCriteria, int pageNo, int pageSize);

	public PaginatedResult<CardStatusRptDTO> getCardRejectedRptRecordList(
			CardStatusRptDTO cardRejStatusRptCriteria, int pageNo, int pageSize);

	public PaginatedResult<RICTxnRptDto> getUNCollectedStatusRptRecordList(
			RICTxnRptDto unColCardStatuSrchCriteria, int pageNo, int pageSize);

	public PaginatedResult<CardStatusRptDTO> getCardExpiredStatusRptRecordList(
			CardStatusRptDTO cardExpiredSrchCriteria, int pageNo, int pageSize);

	public PaginatedResult<CardStatusRptDTO> getCardReActRptRecordList(
			CardStatusRptDTO cardReActSrchCriteria, int pageNo, int pageSize);

	public PaginatedResult<CardStatusRptDTO> getCardDeActRptRecordList(
			CardStatusRptDTO cardDeActSrchCriteria, int pageNo, int pageSize);

	public List<CardStatusRptDTO> getCardRejectedRptRecordListForPDF(
			CardStatusRptDTO cardRejStatusRptCriteria);

	public PaginatedResult<RICTxnRptDto> getRicExprsPrintRptRecordList(
			RICTxnRptDto exprsPrintRptSrchCriteria, int pageNo, int pageSize);

	public PaginatedResult<ExceptionHandlngRptDto> getExceptionHandlingRptRecordList(
			ExceptionHandlngRptDto excepHandlingRptSrchCriteria, int pageNo,
			int pageSize) throws Exception;
	
	public PaginatedResult<RICTxnRptDto> getCardDeliveryStatusRptRecordList(
			RICTxnRptDto cardDelStatuSrchCriteria, int pageNo,
			int pageSize) throws Exception;
	
	
	public PaginatedResult<RICTxnRptDto> getRicLostNfoundRptRecordList(
			RICTxnRptDto lostNfoundSrchCriteria, int pageNo,
			int pageSize) throws Exception;

	public static final String NIC_RIC_TRANSACTION_RPT_ID = "NIC_RIC_RPT_001";
	public static final String NIC_RIC_PAYMENT_RPT_ID = "NIC_RIC_RPT_002";
	public static final String NIC_RIC_WAVIER_RPT_ID = "NIC_RIC_RPT_003";
	public static final String NIC_RIC_TRANSACTION_UPLOAD_RPT_ID = "NIC_RIC_RPT_004";
	public static final String NIC_RIC_REJECTED_TRANSACTION_RPT_ID = "NIC_RIC_RPT_005";
	public static final String NIC_RIC_BATCHCARD_INFO_DWNLD_RPT_ID = "NIC_RIC_RPT_006";
	public static final String NIC_RIC_TRANSACTION_RPT_NAME = "RIC Transaction Report";
	public static final String NIC_RIC_PAYMENT_RPT_NAME = "RIC Payment Report";
	public static final String NIC_RIC_WAVIER_RPT_NAME = "RIC Wavier Report";
	public static final String NIC_RIC_TRANSACTION_UPLOAD_RPT_NAME = "RIC Transaction Upload Report";
	public static final String NIC_RIC_REJECTED_TRANSACTION_RPT_NAME = "RIC_Rej_Transaction_Report";
	public static final String NIC_RIC_BATCHCARD_INFO_DWNLD_RPT_NAME = "RIC_Batch_Card_Info_Dwnload_RPT";
	public static final String NIC_RIC_CARD_COLL_STATUS_RPT_ID = "NIC_RIC_RPT_007";
	public static final String NIC_RIC_CARD_COLL_STATUS_RPT_NAME = "RIC Card Collected Status Report";
	public static final String NIC_RIC_UNCOLLECTED_CARD_STATUS_RPT_NAME = "RIC UnCollected Card Status Report";
	public static final String NIC_RIC_UNCOLLECTED_CARD_STATUS_RPT_ID = "NIC_RIC_RPT_015";
	public static final String NIC_RIC_UNCOLLECTED_CARD_STATUS_XLS_RPT_NAME = "RIC UnCollected Card Status Report";
	public static final String NIC_RIC_UNCOLLECTED_CARD_STATUS_XLS_RPT_ID = "NIC_RIC_RPT_016";
	public static final String NIC_RIC_CARD_REJ_STATUS_RPT_ID = "NIC_RIC_RPT_008";
	public static final String NIC_RIC_CARD_REJ_STATUS_RPT_NAME = "RIC Card Rejected Status Report";
	public static final String NIC_RIC_EXPPRNT_RPT_ID = "NIC_RIC_RPT_009";
	public static final String NIC_RIC_EXPPRNT_RPT_NAME = "RIC Express Printing Report";
	public static final String NIC_RIC_EXPHANDL_RPT_ID = "NIC_RIC_RPT_010";
	public static final String NIC_RIC_EXPHANDL_RPT_NAME = "RIC Exception Handling Report";
	public static final String NIC_RIC_CRDREACT_RPT_ID = "NIC_RIC_RPT_011";
	public static final String NIC_RIC_CRDREACT_RPT_NAME = "RIC Card Reactivation Report";
	public static final String NIC_RIC_CRDDEACT_RPT_ID = "NIC_RIC_RPT_012";
	public static final String NIC_RIC_CRDDEACT_RPT_NAME = "RIC Card Deactivation Report";
	public static final String NIC_RIC_CRDDELVRY_RPT_ID = "NIC_RIC_RPT_013";
	public static final String NIC_RIC_CRDDELVRY_RPT_NAME = "RIC Card Delivery Status Report";
	public static final String NIC_RIC_LSTNFND_RPT_ID = "NIC_RIC_RPT_014";
	public static final String NIC_RIC_LSTNFND_RPT_NAME = "RIC Lost And Found Report";
}
