package com.nec.asia.nic.comp.trans.service;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicCardReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReconRptId;
import com.nec.asia.nic.comp.trans.service.exception.ReconReportServiceException;
import com.nec.asia.nic.framework.service.BusinessService;

/*
 * Modification History:
 * 
 * 27 Nov 2013 (chris) : init service
 * 15 May 2014 (chris) : add method exportTransaction()
 */
public interface ReconReportService extends BusinessService<NicTransactionReconRpt, NicTransactionReconRptId>{

	//constants
	public static final String RECON_STATUS_MISSING 	= "0";
	public static final String RECON_STATUS_UNMATCHED 	= "1";
	public static final String RECON_STATUS_PENDING 	= "2";
	public static final String RECON_STATUS_MATCHED 	= "3";
	
	public static final String SYSTEM_RIC				= "RIC";
	public static final String SYSTEM_CC				= "CC";
	public static final String SYSTEM_NIC				= "NIC";
	public static final String SYSTEM_PERSO				= "PERSO";
	public static final String SYSTEM_CCI				= "CCI";
	public static final String SYSTEM_INVENTORY			= "INVENTORY";
	
	public static final String DEFAULT_FILE_LOCATION	= "/syncroot/txn/nic/data";
	public static final String DEFAULT_ACK_LOCATION		= "/syncroot/txn/nic/ack";
	
	public void updateNicTransactionReconReportData(String regSiteCode, String issSiteCode, String applicationDate, String collectionDate) throws ReconReportServiceException;
	
	public void updateTransactionReconReportBySubsystem(List<NicTransactionReconRpt> reconRptList, String sourceSystemId, Date reportGenerationTime) throws ReconReportServiceException;
	public List<NicTransactionReconRpt> findAllTransactionReconReport(String regSiteCode, String issSiteCode, String applicationDate, String collectionDate, String sourceSystemId) throws ReconReportServiceException;
	
	public void updateCardReconReportBySubsystem(List<NicCardReconRpt> reconRptList, String sourceSystemId, Date reportGenerationTime) throws ReconReportServiceException;
	public List<NicCardReconRpt> findAllCardReconReport(String siteCode, String reportCreateDate, String sourceSystemId) throws ReconReportServiceException;

	boolean exportTransaction(String inputDate)	throws ReconReportServiceException;
	boolean isExported(String inputDate) throws ReconReportServiceException;
	
}

