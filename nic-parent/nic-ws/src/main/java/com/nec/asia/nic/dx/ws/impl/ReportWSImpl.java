package com.nec.asia.nic.dx.ws.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nec.asia.nic.comp.trans.domain.NicCardReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicCardReconRptId;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReconRptId;
import com.nec.asia.nic.comp.trans.service.ReconReportService;
import com.nec.asia.nic.dx.report.CardReconReportData;
import com.nec.asia.nic.dx.report.RetrieveCardReconReportDataRequest;
import com.nec.asia.nic.dx.report.RetrieveCardReconReportDataResponse;
import com.nec.asia.nic.dx.report.RetrieveTransactionReconReportDataRequest;
import com.nec.asia.nic.dx.report.RetrieveTransactionReconReportDataResponse;
import com.nec.asia.nic.dx.report.TransactionReconReportData;
import com.nec.asia.nic.dx.report.UploadCardReconReportDataRequest;
import com.nec.asia.nic.dx.report.UploadCardReconReportDataResponse;
import com.nec.asia.nic.dx.report.UploadTransactionReconReportDataRequest;
import com.nec.asia.nic.dx.report.UploadTransactionReconReportDataResponse;
import com.nec.asia.nic.dx.ws.Constant;
import com.nec.asia.nic.dx.ws.ReportWS;
/* 
 * Modification History:
 *  
 * 28 Nov 2013 (chris): init class
 * 02 Jun 2014 (chris): add field cciPendingStockedIn, reconRemarks
 */
public class ReportWSImpl implements ReportWS {

	protected static final Logger logger = LoggerFactory.getLogger(ReportWSImpl.class);
	@Autowired
	private ReconReportService reconReportService;

	public void setReconReportService(ReconReportService reconReportService) {
		this.reconReportService = reconReportService;
	}

	//TransactionReconReport
	@Override
	public RetrieveTransactionReconReportDataResponse retrieveTransactionReconReportData(
			RetrieveTransactionReconReportDataRequest input) {
		RetrieveTransactionReconReportDataResponse response = new RetrieveTransactionReconReportDataResponse();
		String statusCode = null;
		String detailMessage = null;
		logger.info("ReportWSImpl.retrieveTransactionReconReportData(): begin");
		try {			
			String subSystemId = input.getSourceSystemId();
			List<NicTransactionReconRpt> reconRptDBOList = reconReportService.findAllTransactionReconReport(input.getRegSiteCode(),
					input.getIssSiteCode(), input.getApplicationDate(), input.getCollectionDate(), subSystemId);
			if (reconRptDBOList!=null) {
				response.getTransactionReconReportData().addAll(parseTransactionReconReportDTO(reconRptDBOList));
			}
			statusCode = Constant.TRANSACTION_SUCCESS;
		} catch (Exception e) {
			logger.error("[Exception in ReportWS][retrieveTransactionReconReportData]", e);
			statusCode = Constant.TRANSACTION_FAILED;
			detailMessage = e.getMessage();
		}
		response.setStatusCode(statusCode);
		response.setDetailMessage(detailMessage);
		logger.info("ReportWSImpl.retrieveTransactionReconReportData(): end");
		return response;
	}

	@Override
	public UploadTransactionReconReportDataResponse uploadTransactionReconReportData(
			UploadTransactionReconReportDataRequest input) {
		UploadTransactionReconReportDataResponse response = new UploadTransactionReconReportDataResponse();
		String statusCode = null;
		String detailMessage = null;
		logger.info("ReportWSImpl.uploadTransactionReconReportData(): begin");
		try {
			String subSystemId = input.getSourceSystemId();
			Date reportGenerationTime = input.getReportGenerationTime();
			if (CollectionUtils.isNotEmpty(input.getTransactionReconReportData())) {
				//to validate date format
				for (TransactionReconReportData rptDTO: input.getTransactionReconReportData()) {
					String applicationDate = rptDTO.getApplicationDate();
					String collectionDate = rptDTO.getCollectionDate();
					if (StringUtils.length(applicationDate)>8 || StringUtils.length(collectionDate)>8) {
						throw new Exception("ApplicationDate and CollectionDate should be in YYYYMMDD format.");
					}
				}
				logger.info("[uploadTransactionReconReportData] systemId-{}, reportGenerationTime-{}", new Object[] {subSystemId, input.getReportGenerationTime()});
				List<NicTransactionReconRpt> reconRptList = this.parseTransactionReconReportDBO(input.getTransactionReconReportData());
				reconReportService.updateTransactionReconReportBySubsystem(reconRptList, subSystemId, reportGenerationTime);
				//to regenerate nic report data
				for (NicTransactionReconRpt rpt: reconRptList) {
					String regSiteCode = rpt.getId().getRegSiteCode();
					String issSiteCode = rpt.getId().getIssSiteCode();
					String applicationDate = rpt.getId().getApplicationDate();
					String collectionDate = rpt.getId().getCollectionDate();
					reconReportService.updateNicTransactionReconReportData(regSiteCode, issSiteCode, applicationDate, collectionDate);
				}
			}
			statusCode = Constant.TRANSACTION_SUCCESS;
		} catch (Exception e) {
			logger.error("[Exception in ReportWS][uploadTransactionReconReportData]", e);
			statusCode = Constant.TRANSACTION_FAILED;
			detailMessage = e.getMessage();
		}
		response.setStatusCode(statusCode);
		response.setDetailMessage(detailMessage);
		logger.info("ReportWSImpl.uploadTransactionReconReportData(): end");
		return response;
	}

	private List<NicTransactionReconRpt> parseTransactionReconReportDBO(
			List<TransactionReconReportData> reconReportDataList) throws IllegalAccessException, InvocationTargetException {
		List<NicTransactionReconRpt> resultList = new ArrayList<NicTransactionReconRpt>();
		if (CollectionUtils.isNotEmpty(reconReportDataList)) {
			for (TransactionReconReportData reconReportData: reconReportDataList) {
				
				NicTransactionReconRptId id = new NicTransactionReconRptId(reconReportData.getRegSiteCode(),
						reconReportData.getIssSiteCode(), reconReportData.getApplicationDate(), reconReportData.getCollectionDate());
				NicTransactionReconRpt reconRptDBO = new NicTransactionReconRpt(id);
				BeanUtils.copyProperty(reconRptDBO, "reconStatus", reconReportData.getReconStatus());
				BeanUtils.copyProperty(reconRptDBO, "reconRemarks", reconReportData.getReconRemarks());
				if (reconReportData.getCcricRegistered()!=null)	BeanUtils.copyProperty(reconRptDBO, "ccricRegistered", reconReportData.getCcricRegistered());
				if (reconReportData.getCcricUploaded()!=null)	BeanUtils.copyProperty(reconRptDBO, "ccricUploaded", reconReportData.getCcricUploaded());
				if (reconReportData.getCcricCancelled()!=null)	BeanUtils.copyProperty(reconRptDBO, "ccricCancelled", reconReportData.getCcricCancelled());
				if (reconReportData.getCcricPending()!=null)	BeanUtils.copyProperty(reconRptDBO, "ccricPending", reconReportData.getCcricPending());
				
				if (reconReportData.getNicReceived()!=null)				BeanUtils.copyProperty(reconRptDBO, "nicReceived", reconReportData.getNicReceived());
				if (reconReportData.getNicRejected()!=null)				BeanUtils.copyProperty(reconRptDBO, "nicRejected", reconReportData.getNicRejected());
				if (reconReportData.getNicWip()!=null)					BeanUtils.copyProperty(reconRptDBO, "nicWip", reconReportData.getNicWip());
				if (reconReportData.getNicPersoSubmitted()!=null)		BeanUtils.copyProperty(reconRptDBO, "nicPersoSubmitted", reconReportData.getNicPersoSubmitted());
				if (reconReportData.getNicPersoRejected()!=null)		BeanUtils.copyProperty(reconRptDBO, "nicPersoRejected", reconReportData.getNicPersoRejected());
				if (reconReportData.getNicPersoPersonalized()!=null)	BeanUtils.copyProperty(reconRptDBO, "nicPersoPersonalized", reconReportData.getNicPersoPersonalized());
				if (reconReportData.getNicPersoCardDelivered()!=null)	BeanUtils.copyProperty(reconRptDBO, "nicPersoCardDelivered", reconReportData.getNicPersoCardDelivered());
				if (reconReportData.getNicPersoWip()!=null)				BeanUtils.copyProperty(reconRptDBO, "nicPersoWip", reconReportData.getNicPersoWip());
				if (reconReportData.getNicCcricCardReceived()!=null)	BeanUtils.copyProperty(reconRptDBO, "nicCcricCardReceived", reconReportData.getNicCcricCardReceived());
				if (reconReportData.getNicCcricCardRejected()!=null)	BeanUtils.copyProperty(reconRptDBO, "nicCcricCardRejected", reconReportData.getNicCcricCardRejected());
				if (reconReportData.getNicCcricCardCollected()!=null)	BeanUtils.copyProperty(reconRptDBO, "nicCcricCardCollected", reconReportData.getNicCcricCardCollected());
				if (reconReportData.getNicCcricWip()!=null)				BeanUtils.copyProperty(reconRptDBO, "nicCcricWip", reconReportData.getNicCcricWip());
				
				if (reconReportData.getPersoReceived()!=null)			BeanUtils.copyProperty(reconRptDBO, "persoReceived", reconReportData.getPersoReceived());
				if (reconReportData.getPersoRejected()!=null)			BeanUtils.copyProperty(reconRptDBO, "persoRejected", reconReportData.getPersoRejected());
				if (reconReportData.getPersoCardPacked()!=null)			BeanUtils.copyProperty(reconRptDBO, "persoCardPacked", reconReportData.getPersoCardPacked());
				if (reconReportData.getPersoCardDelivered()!=null)		BeanUtils.copyProperty(reconRptDBO, "persoCardDelivered", reconReportData.getPersoCardDelivered());
				if (reconReportData.getPersoWip()!=null)				BeanUtils.copyProperty(reconRptDBO, "persoWip", reconReportData.getPersoWip());
				
				if (reconReportData.getCciPendingStockedIn()!=null)		BeanUtils.copyProperty(reconRptDBO, "cciPendingStockedIn", reconReportData.getCciPendingStockedIn());
				if (reconReportData.getCciStockedIn()!=null)			BeanUtils.copyProperty(reconRptDBO, "cciStockedIn", reconReportData.getCciStockedIn());
				if (reconReportData.getCciRejected()!=null)				BeanUtils.copyProperty(reconRptDBO, "cciRejected", reconReportData.getCciRejected());
				if (reconReportData.getCciCollected()!=null)			BeanUtils.copyProperty(reconRptDBO, "cciCollected", reconReportData.getCciCollected());
				if (reconReportData.getCciCollectionFailed()!=null)		BeanUtils.copyProperty(reconRptDBO, "cciCollectionFailed", reconReportData.getCciCollectionFailed());
				if (reconReportData.getCciPendingCollection()!=null)	BeanUtils.copyProperty(reconRptDBO, "cciPendingCollection", reconReportData.getCciPendingCollection());
				
				BeanUtils.copyProperty(reconRptDBO, "createBy", reconReportData.getCreateBy());
				BeanUtils.copyProperty(reconRptDBO, "createDate", reconReportData.getCreateDate());
				BeanUtils.copyProperty(reconRptDBO, "updateBy", reconReportData.getUpdateBy());
				BeanUtils.copyProperty(reconRptDBO, "updateDate", reconReportData.getUpdateDate());
				resultList.add(reconRptDBO);
			}
		}
		return resultList;
	}
	
	private List<TransactionReconReportData> parseTransactionReconReportDTO(
			List<NicTransactionReconRpt> reconRptDBOList) throws IllegalAccessException, InvocationTargetException {
		List<TransactionReconReportData> resultList = new ArrayList<TransactionReconReportData>();
		if (CollectionUtils.isNotEmpty(reconRptDBOList)) {
			for (NicTransactionReconRpt reconRptDBO: reconRptDBOList) {
				TransactionReconReportData reconReportData = new TransactionReconReportData();
				BeanUtils.copyProperty(reconReportData, "regSiteCode", reconRptDBO.getId().getRegSiteCode());
				BeanUtils.copyProperty(reconReportData, "issSiteCode", reconRptDBO.getId().getIssSiteCode());
				BeanUtils.copyProperty(reconReportData, "applicationDate", reconRptDBO.getId().getApplicationDate());
				BeanUtils.copyProperty(reconReportData, "collectionDate", reconRptDBO.getId().getCollectionDate());
				BeanUtils.copyProperty(reconReportData, "reconStatus", reconRptDBO.getReconStatus());
				BeanUtils.copyProperty(reconReportData, "reconRemarks", reconRptDBO.getReconRemarks());
				if (reconRptDBO.getDcmRegistered()!=null)	BeanUtils.copyProperty(reconReportData, "ccricRegistered", reconRptDBO.getDcmRegistered());
				if (reconRptDBO.getDcmUploaded()!=null)	BeanUtils.copyProperty(reconReportData, "ccricUploaded", reconRptDBO.getDcmUploaded());
				if (reconRptDBO.getDcmCancelled()!=null)	BeanUtils.copyProperty(reconReportData, "ccricCancelled", reconRptDBO.getDcmCancelled());
				if (reconRptDBO.getDcmWip()!=null)	BeanUtils.copyProperty(reconReportData, "ccricPending", reconRptDBO.getDcmWip());
				
				if (reconRptDBO.getEppReceived()!=null)				BeanUtils.copyProperty(reconReportData, "nicReceived", reconRptDBO.getEppReceived());
				if (reconRptDBO.getEppRejected()!=null)				BeanUtils.copyProperty(reconReportData, "nicRejected", reconRptDBO.getEppRejected());
				if (reconRptDBO.getEppWip()!=null)					BeanUtils.copyProperty(reconReportData, "nicWip", reconRptDBO.getEppWip());
				if (reconRptDBO.getEppPersoSubmitted()!=null)		BeanUtils.copyProperty(reconReportData, "nicPersoSubmitted", reconRptDBO.getEppPersoSubmitted());
				if (reconRptDBO.getEppPersoRejected()!=null)		BeanUtils.copyProperty(reconReportData, "nicPersoRejected", reconRptDBO.getEppPersoRejected());
				if (reconRptDBO.getEppDocPrinted()!=null)			BeanUtils.copyProperty(reconReportData, "nicPersoPersonalized", reconRptDBO.getEppDocPrinted());
				if (reconRptDBO.getEppDocQcCompleted()!=null)	BeanUtils.copyProperty(reconReportData, "nicPersoCardDelivered", reconRptDBO.getEppDocQcCompleted());
				if (reconRptDBO.getEppPersoWip()!=null)				BeanUtils.copyProperty(reconReportData, "nicPersoWip", reconRptDBO.getEppPersoWip());
				if (reconRptDBO.getEppIssuanceDocReceived()!=null)	BeanUtils.copyProperty(reconReportData, "nicCcricCardReceived", reconRptDBO.getEppIssuanceDocReceived());
				if (reconRptDBO.getEppIssuanceDocRejected()!=null)	BeanUtils.copyProperty(reconReportData, "nicCcricCardRejected", reconRptDBO.getEppIssuanceDocRejected());
				if (reconRptDBO.getEppIssuanceDocIssued()!=null)	BeanUtils.copyProperty(reconReportData, "nicCcricCardCollected", reconRptDBO.getEppIssuanceDocIssued());
				if (reconRptDBO.getEppIssuanceWip()!=null)				BeanUtils.copyProperty(reconReportData, "nicCcricWip", reconRptDBO.getEppIssuanceWip());
				
				if (reconRptDBO.getPersoReceived()!=null)		BeanUtils.copyProperty(reconReportData, "persoReceived", reconRptDBO.getPersoReceived());
				if (reconRptDBO.getPersoRejected()!=null)		BeanUtils.copyProperty(reconReportData, "persoRejected", reconRptDBO.getPersoRejected());
				if (reconRptDBO.getPersoDocPrinted()!=null)		BeanUtils.copyProperty(reconReportData, "persoCardPacked", reconRptDBO.getPersoDocPrinted());
				if (reconRptDBO.getPersoDocQcCompleted()!=null)	BeanUtils.copyProperty(reconReportData, "persoCardDelivered", reconRptDBO.getPersoDocQcCompleted());
				if (reconRptDBO.getPersoWip()!=null)			BeanUtils.copyProperty(reconReportData, "persoWip", reconRptDBO.getPersoWip());
				
//				if (reconRptDBO.getCciPendingStockedIn()!=null)		BeanUtils.copyProperty(reconReportData, "cciPendingStockedIn", reconRptDBO.getCciPendingStockedIn());
//				if (reconRptDBO.getCciStockedIn()!=null)			BeanUtils.copyProperty(reconReportData, "cciStockedIn", reconRptDBO.getCciStockedIn());
//				if (reconRptDBO.getCciRejected()!=null)				BeanUtils.copyProperty(reconReportData, "cciRejected", reconRptDBO.getCciRejected());
//				if (reconRptDBO.getCciCollected()!=null)			BeanUtils.copyProperty(reconReportData, "cciCollected", reconRptDBO.getCciCollected());
//				if (reconRptDBO.getCciCollectionFailed()!=null)		BeanUtils.copyProperty(reconReportData, "cciCollectionFailed", reconRptDBO.getCciCollectionFailed());
//				if (reconRptDBO.getCciPendingCollection()!=null)	BeanUtils.copyProperty(reconReportData, "cciPendingCollection", reconRptDBO.getCciPendingCollection());
				
				BeanUtils.copyProperty(reconReportData, "createBy", reconRptDBO.getCreateBy());
				BeanUtils.copyProperty(reconReportData, "createDate", reconRptDBO.getCreateDatetime());
				BeanUtils.copyProperty(reconReportData, "updateBy", reconRptDBO.getUpdateBy());
				BeanUtils.copyProperty(reconReportData, "updateDate", reconRptDBO.getUpdateDatetime());
				resultList.add(reconReportData);
			}
		}
		return resultList;
	}

	//CardReconReport
	@Override
	public UploadCardReconReportDataResponse uploadCardReconReportData(
			UploadCardReconReportDataRequest input) {
		UploadCardReconReportDataResponse response = new UploadCardReconReportDataResponse();
		String statusCode = null;
		String detailMessage = null;
		logger.info("ReportWSImpl.uploadTransactionReconReportData(): begin");
		try {
			String subSystemId = input.getSourceSystemId();
			Date reportGenerationTime = input.getReportGenerationTime();
			if (CollectionUtils.isNotEmpty(input.getCardReconReportData())) {
				for (CardReconReportData rptDTO: input.getCardReconReportData()) {
					String reportCreateDate = rptDTO.getReportCreateDate();
					if (StringUtils.length(reportCreateDate)>8) {
						throw new Exception("ReportCreateDate should be in YYYYMMDD format.");
					}
				}
				logger.info("[uploadTransactionReconReportData] systemId-{}, reportGenerationTime-{}", new Object[] {subSystemId, input.getReportGenerationTime()});
				List<NicCardReconRpt> reconRptList = this.parseCardReconReportDBO(input.getCardReconReportData());
				reconReportService.updateCardReconReportBySubsystem(reconRptList , subSystemId, reportGenerationTime);
			}
			statusCode = Constant.TRANSACTION_SUCCESS;
		} catch (Exception e) {
			logger.error("[Exception in ReportWS][uploadTransactionReconReportData]", e);
			statusCode = Constant.TRANSACTION_FAILED;
			detailMessage = e.getMessage();
		}
		response.setStatusCode(statusCode);
		response.setDetailMessage(detailMessage);
		logger.info("ReportWSImpl.uploadTransactionReconReportData(): end");
		return response;
	}

	@Override
	public RetrieveCardReconReportDataResponse retrieveCardReconReportData(
			RetrieveCardReconReportDataRequest input) {
		RetrieveCardReconReportDataResponse response = new RetrieveCardReconReportDataResponse();
		String statusCode = null;
		String detailMessage = null;
		logger.info("ReportWSImpl.retrieveCardReconReportData(): begin");
		try {			
			List<NicCardReconRpt> reconRptDBOList = reconReportService.findAllCardReconReport(input.getSiteCode(),
					input.getReportCreateDate(), input.getSourceSystemId());
			if (reconRptDBOList!=null) {
				response.getCardReconReportData().addAll(parseCardReconReportDTO(reconRptDBOList));
			}
			statusCode = Constant.TRANSACTION_SUCCESS;
		} catch (Exception e) {
			logger.error("[Exception in ReportWS][retrieveCardReconReportData]", e);
			statusCode = Constant.TRANSACTION_FAILED;
			detailMessage = e.getMessage();
		}
		response.setStatusCode(statusCode);
		response.setDetailMessage(detailMessage);
		logger.info("ReportWSImpl.retrieveCardReconReportData(): end");
		return response;
	}
	
	private List<NicCardReconRpt> parseCardReconReportDBO(
			List<CardReconReportData> reconReportDataList) throws IllegalAccessException, InvocationTargetException {
		List<NicCardReconRpt> resultList = new ArrayList<NicCardReconRpt>();
		if (CollectionUtils.isNotEmpty(reconReportDataList)) {
			for (CardReconReportData reconReportData: reconReportDataList) {
				
				NicCardReconRptId id = new NicCardReconRptId(reconReportData.getSiteCode(),	reconReportData.getReportCreateDate());
				NicCardReconRpt reconRptDBO = new NicCardReconRpt(id);
				BeanUtils.copyProperty(reconRptDBO, "reconStatus", reconReportData.getReconStatus());

				if (reconReportData.getPersoReceived()!=null)		BeanUtils.copyProperty(reconRptDBO, "persoReceived", reconReportData.getPersoReceived());
				if (reconReportData.getPersoRejected()!=null)		BeanUtils.copyProperty(reconRptDBO, "persoRejected", reconReportData.getPersoRejected());
				if (reconReportData.getPersoCardPacked()!=null)		BeanUtils.copyProperty(reconRptDBO, "persoCardPacked", reconReportData.getPersoCardPacked());
				if (reconReportData.getPersoCardDelivered()!=null)	BeanUtils.copyProperty(reconRptDBO, "persoCardDelivered", reconReportData.getPersoCardDelivered());
				if (reconReportData.getPersoWip()!=null)			BeanUtils.copyProperty(reconRptDBO, "persoWip", reconReportData.getPersoWip());
				
				if (reconReportData.getCciPendingStockedIn()!=null)		BeanUtils.copyProperty(reconRptDBO, "cciPendingStockedIn", reconReportData.getCciPendingStockedIn());
				if (reconReportData.getCciStockedIn()!=null)			BeanUtils.copyProperty(reconRptDBO, "cciStockedIn", reconReportData.getCciStockedIn());
				if (reconReportData.getCciRejected()!=null)				BeanUtils.copyProperty(reconRptDBO, "cciRejected", reconReportData.getCciRejected());
				if (reconReportData.getCciCollected()!=null)			BeanUtils.copyProperty(reconRptDBO, "cciCollected", reconReportData.getCciCollected());
				if (reconReportData.getCciCollectionFailed()!=null)		BeanUtils.copyProperty(reconRptDBO, "cciCollectionFailed", reconReportData.getCciCollectionFailed());
				if (reconReportData.getCciPendingCollection()!=null)	BeanUtils.copyProperty(reconRptDBO, "cciPendingCollection", reconReportData.getCciPendingCollection());
				if (reconReportData.getCciCardReturned()!=null)			BeanUtils.copyProperty(reconRptDBO, "cciCardReturned", reconReportData.getCciCardReturned());
				if (reconReportData.getInvCardReturned()!=null)			BeanUtils.copyProperty(reconRptDBO, "invCardReturned", reconReportData.getInvCardReturned());
				
				BeanUtils.copyProperty(reconRptDBO, "createBy", reconReportData.getCreateBy());
				BeanUtils.copyProperty(reconRptDBO, "createDate", reconReportData.getCreateDate());
				BeanUtils.copyProperty(reconRptDBO, "updateBy", reconReportData.getUpdateBy());
				BeanUtils.copyProperty(reconRptDBO, "updateDate", reconReportData.getUpdateDate());
				resultList.add(reconRptDBO);
			}
		}
		return resultList;
	}
	
	private List<CardReconReportData> parseCardReconReportDTO(
			List<NicCardReconRpt> reconRptDBOList) throws IllegalAccessException, InvocationTargetException {
		List<CardReconReportData> resultList = new ArrayList<CardReconReportData>();
		if (CollectionUtils.isNotEmpty(reconRptDBOList)) {
			for (NicCardReconRpt reconRptDBO: reconRptDBOList) {
				CardReconReportData reconReportData = new CardReconReportData();
				BeanUtils.copyProperty(reconReportData, "siteCode", reconRptDBO.getId().getSiteCode());
				BeanUtils.copyProperty(reconReportData, "reportCreateDate", reconRptDBO.getId().getReportCreateDate());
				BeanUtils.copyProperty(reconReportData, "reconStatus", reconRptDBO.getReconStatus());
				
				if (reconRptDBO.getPersoReceived()!=null)			BeanUtils.copyProperty(reconReportData, "persoReceived", reconRptDBO.getPersoReceived());
				if (reconRptDBO.getPersoRejected()!=null)			BeanUtils.copyProperty(reconReportData, "persoRejected", reconRptDBO.getPersoRejected());
				if (reconRptDBO.getPersoCardPacked()!=null)			BeanUtils.copyProperty(reconReportData, "persoCardPacked", reconRptDBO.getPersoCardPacked());
				if (reconRptDBO.getPersoCardDelivered()!=null)		BeanUtils.copyProperty(reconReportData, "persoCardDelivered", reconRptDBO.getPersoCardDelivered());
				if (reconRptDBO.getPersoWip()!=null)				BeanUtils.copyProperty(reconReportData, "persoWip", reconRptDBO.getPersoWip());
				
				if (reconRptDBO.getCciPendingStockedIn()!=null)		BeanUtils.copyProperty(reconReportData, "cciPendingStockedIn", reconRptDBO.getCciPendingStockedIn());
				if (reconRptDBO.getCciStockedIn()!=null)			BeanUtils.copyProperty(reconReportData, "cciStockedIn", reconRptDBO.getCciStockedIn());
				if (reconRptDBO.getCciRejected()!=null)				BeanUtils.copyProperty(reconReportData, "cciRejected", reconRptDBO.getCciRejected());
				if (reconRptDBO.getCciCollected()!=null)			BeanUtils.copyProperty(reconReportData, "cciCollected", reconRptDBO.getCciCollected());
				if (reconRptDBO.getCciCollectionFailed()!=null)		BeanUtils.copyProperty(reconReportData, "cciCollectionFailed", reconRptDBO.getCciCollectionFailed());
				if (reconRptDBO.getCciPendingCollection()!=null)	BeanUtils.copyProperty(reconReportData, "cciPendingCollection", reconRptDBO.getCciPendingCollection());
				if (reconRptDBO.getCciCardReturned()!=null)			BeanUtils.copyProperty(reconReportData, "cciCardReturned", reconRptDBO.getCciCardReturned());
				
				if (reconRptDBO.getInvCardReturned()!=null)			BeanUtils.copyProperty(reconReportData, "invCardReturned", reconRptDBO.getInvCardReturned());
				
				BeanUtils.copyProperty(reconReportData, "createBy", reconRptDBO.getCreateBy());
				BeanUtils.copyProperty(reconReportData, "createDate", reconRptDBO.getCreateDate());
				BeanUtils.copyProperty(reconReportData, "updateBy", reconRptDBO.getUpdateBy());
				BeanUtils.copyProperty(reconReportData, "updateDate", reconRptDBO.getUpdateDate());
				resultList.add(reconReportData);
			}
		}
		return resultList;
	}
}
