package com.nec.asia.nic.comp.trans.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.CountTranStatus;
import com.nec.asia.nic.comp.trans.dto.Datadto;
import com.nec.asia.nic.comp.trans.dto.NicRegistrationDataDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.ReportRegProcessData;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.utils.MultiSeriesReport;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * 
 * @author chris_wong
 *
 */
/* 
 * Modification History:
 *  
 * 23 Sep 2013 (chris): add site_code code id for iss_site_code lookup.
 * 05 Oct 2013 (chris): to block transaction with duplicate nin in new registration 
 * 08 Oct 2013 (chris): to validate option_surname
 * 11 Nov 2013 (chris): to add constants
 * 18 Nov 2013 (chris): add error code for transactionUpload
 * 10 Dec 2013 (Peddi Swapna): Added new methods to support the NIC Statistics modules during the code merge. 
 * 12 Dec 2013 (chris): add constants for re-reg
 * 10 Jan 2014 (Sailaja): Added updateTransactionStatusOnReprint method to update transaction status on Re-print.
 * 15 Jan 2014 (Peddi Swapna): Added new methods: getCardBypassApprovedBySiteInfo(), getCardBypassApprovedTransIds(), getAllCardBypassApprovedTransIds()
 * 13 Feb 2014 (chris): add constants for suspend / card reject
 * 07 Apr 2014 (chris): add constants for void status
 * 23 May 2014 (chris): add methods checkDuplicateByNin for ELIGIBLITY_CHECK
 * 23 Jun 2014 (chris): add constants stage: PEND_EH, PEND_ISS_SUPERVISOR, CARD_REPACKAGE, CARD_TRANSFER, VOID, etc
 * 09 May 2016 (khang): add method updateStatusByTxnIdList
 * 31 May 2016 (chris): add constants PARA: MAX_LEN_FULLNAME, MAX_LEN_NAMEPAGE, MAX_LINES_PG3_LIMITATION, MAX_LEN_PG3_POSITION, etc
 * 03 Jun 2016 (chris): add constants PARA: MAX_LEN_PER_LINE_PG3_POSITION, MAX_LINES_PG3_AKA, MAX_LEN_PLACE_OF_BIRTH
 * 06 Jun 2016 (chris): add constants PARA: MAX_LEN_PG3_LIMITATION, MAX_LEN_PG3_FULLNAME, MAX_LEN_PG3_AKA
 * 29 Jul 2016 (chris): add constants PARA: APPLY_NAME_TRUNCATION
 * 05 Jun 2017 (chris): change constants from NEW to REG, RENEWAL to REP.
 */

public interface NicTransactionService extends BusinessService<NicTransaction, String>{
	
	//CORE TRANSACTION
	public static final String TRANSACTION_TYPE_REGISTRATION 		= "REG";//"NEW";
	public static final String TRANSACTION_TYPE_REPLACEMENT 		= "REP";//"RENEWAL";
	public static final String TRANSACTION_TYPE_LOST 				= "LOST";
	
	public static final String TRANSACTION_STATE_TX_UPLOAD 				= NicTransactionLog.TRANSACTION_STAGE_TX_UPLOAD;
	public static final String TRANSACTION_STATUS_TX_UPLOAD_COMPLETED	= NicTransactionLog.TRANSACTION_STATUS_TX_UPLOAD_COMPLETED;
	public static final String TRANSACTION_STATUS_TX_UPLOAD_ERROR		= NicTransactionLog.TRANSACTION_STATUS_TX_UPLOAD_ERROR;
	
	public static final String TRANSACTION_STAGE_RIC_REGISTRATION		= NicTransactionLog.TRANSACTION_STAGE_RIC_REGISTRATION;
	public static final String TRANSACTION_STAGE_RIC_PEND_SUPERVISOR	= NicTransactionLog.TRANSACTION_STAGE_RIC_PEND_SUPERVISOR;
	public static final String TRANSACTION_STAGE_RIC_VERIFICATION		= NicTransactionLog.TRANSACTION_STAGE_RIC_VERIFICATION;
	public static final String TRANSACTION_STAGE_RIC_EXC_HANDLING		= NicTransactionLog.TRANSACTION_STAGE_RIC_EXC_HANDLING;
	public static final String TRANSACTION_STAGE_RIC_PAYMENT			= NicTransactionLog.TRANSACTION_STAGE_RIC_PAYMENT;
	public static final String TRANSACTION_STAGE_RIC_ISSUANCE			= NicTransactionLog.TRANSACTION_STAGE_RIC_ISSUANCE;
	public static final String TRANSACTION_STAGE_RIC_CARD_ISSUED		= NicTransactionLog.TRANSACTION_STAGE_RIC_CARD_ISSUED;
	public static final String TRANSACTION_STAGE_RIC_CARD_REJECTED		= NicTransactionLog.TRANSACTION_STAGE_RIC_CARD_REJECTED;
	public static final String TRANSACTION_STAGE_RIC_REJECTED			= NicTransactionLog.TRANSACTION_STAGE_RIC_REJECTED;
	public static final String TRANSACTION_STAGE_RIC_REG_CANCELLED		= NicTransactionLog.TRANSACTION_STAGE_RIC_REG_CANCELLED;
	public static final String TRANSACTION_STAGE_WORKFLOW				= NicTransactionLog.TRANSACTION_STAGE_WORKFLOW;
	
	public final static String TRANSACTION_STAGE_RIC_PEND_EH		 		= NicTransactionLog.TRANSACTION_STAGE_RIC_PEND_EH; 				//23 Jun 2014 
	public final static String TRANSACTION_STAGE_RIC_PEND_ISS_SUPERVISOR	= NicTransactionLog.TRANSACTION_STAGE_RIC_PEND_ISS_SUPERVISOR; 	//23 Jun 2014 
	public final static String TRANSACTION_STAGE_RIC_CARD_RECEPTION		 	= NicTransactionLog.TRANSACTION_STAGE_RIC_CARD_RECEPTION; 		//23 Jun 2014 
	public final static String TRANSACTION_STAGE_RIC_CARD_REPACKAGE		 	= NicTransactionLog.TRANSACTION_STAGE_RIC_CARD_REPACKAGE; 		//23 Jun 2014 
	public final static String TRANSACTION_STAGE_RIC_CARD_TRANSFER		 	= NicTransactionLog.TRANSACTION_STAGE_RIC_CARD_TRANSFER; 		//23 Jun 2014 
	public final static String TRANSACTION_STAGE_RIC_CARD_DEACTIVATED		= NicTransactionLog.TRANSACTION_STAGE_RIC_CARD_DEACTIVATED; 	//23 Jun 2014 
	public final static String TRANSACTION_STAGE_DOC_PROCESS			 	= NicTransactionLog.TRANSACTION_STAGE_DOC_PROCESS; 				//23 Jun 2014 
	public final static String TRANSACTION_STAGE_VOID			 			= NicTransactionLog.TRANSACTION_STAGE_VOID; 					//23 Jun 2014 
	public final static String TRANSACTION_STAGE_RE_REGISTERED	 			= NicTransactionLog.TRANSACTION_STAGE_RE_REGISTERED; 			//23 Jun 2014 
	
	public static final String TRANSACTION_STAGE_NIC_CHECK			= NicTransactionLog.TRANSACTION_STAGE_NIC_CHECK;
	//CC NIC_CHECK TRANSACTION_STATUS
	public final static String TRANSACTION_STATUS_NIC_CHECK_COMPLETED 			= NicTransactionLog.TRANSACTION_STATUS_NIC_CHECK_COMPLETED;
	public final static String TRANSACTION_STATUS_NIC_CHECK_COMPLETED_WITH_HIT	= NicTransactionLog.TRANSACTION_STATUS_NIC_CHECK_COMPLETED_WITH_HIT;
	
	public static final String CODE_ID_SITE_CODE = "SITE_CODE";
	public static final String CODE_ID_OPTION_SURNAME = "OPTION_SURNAME";
	
	public static final String TRANSACTION_STATUS_NIC_SCREENING = TransactionStatus.Afis.getKey(); //NicTransactionLog.TRANSACTION_STATUS_NIC_SCREENING;
	public static final String TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION = TransactionStatus.PendingInvestigation.getKey(); //NicTransactionLog.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION;
	public static final String TRANSACTION_STATUS_NIC_APPROVED = TransactionStatus.Verified.getKey(); //NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED;
	public static final String TRANSACTION_STATUS_NIC_REJECTED = TransactionStatus.Rejected.getKey(); //NicTransactionLog.TRANSACTION_STATUS_NIC_REJECTED;
	//public static final String TRANSACTION_STATUS_NIC_APPROVED_FOR_REPRINT = NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED_FOR_REPRINT;
	//public static final String TRANSACTION_STATUS_NIC_OLD_CARD_DEACTIVATED = NicTransactionLog.TRANSACTION_STATUS_NIC_OLD_CARD_DEACTIVATED;
	//public static final String TRANSACTION_STATUS_NIC_CPDV2_UPDATED = NicTransactionLog.TRANSACTION_STATUS_NIC_CPDV2_UPDATED;
	//public static final String TRANSACTION_STATUS_NIC_PERSO_DATA_PREPARED = NicTransactionLog.TRANSACTION_STATUS_NIC_PERSO_DATA_PREPARED;
	//public static final String TRANSACTION_STATUS_NIC_PERSO_NO_REQUIRED = NicTransactionLog.TRANSACTION_STATUS_NIC_PERSO_NO_REQUIRED;
	public static final String TRANSACTION_STATUS_NIC_PERSO_REQUEST_SUBMITTED = TransactionStatus.Personalization.getKey(); //NicTransactionLog.TRANSACTION_STATUS_NIC_PERSO_REQUEST_SUBMITTED;
	//public static final String TRANSACTION_STATUS_NIC_TX_COMPLETD = TransactionStatus.Rejected.getKey(); //NicTransactionLog.TRANSACTION_STATUS_NIC_TX_COMPLETD;
	
	public static final String TRANSACTION_STATUS_PERSO_REJECTED = NicTransactionLog.TRANSACTION_STATUS_PERSO_REJECTED;
	public final static String TRANSACTION_STATUS_RIC_CARD_REJECTED = NicTransactionLog.TRANSACTION_STATUS_RIC_CARD_REJECTED; //"RIC_CARD_REJECTED";
	public final static String TRANSACTION_STATUS_RIC_CARD_EXPIRED = NicTransactionLog.TRANSACTION_STATUS_RIC_CARD_EXPIRED; ; //"RIC_CARD_EXPIRED";
	public final static String TRANSACTION_STATUS_RIC_CARD_RETURNED = NicTransactionLog.TRANSACTION_STATUS_RIC_CARD_RETURNED; //"RIC_CARD_RETURNED";
	public final static String TRANSACTION_STATUS_INVENTORY_CARD_RECEIVED = NicTransactionLog.TRANSACTION_STATUS_INVENTORY_CARD_RECEIVED;
	public final static String TRANSACTION_STATUS_INVENTORY_CARD_DESTROYED = NicTransactionLog.TRANSACTION_STATUS_INVENTORY_CARD_DESTROYED; //"INVENTORY_CARD_DESTROYED";
	public final static String TRANSACTION_STATUS_RIC_CARD_DEACTIVATED = NicTransactionLog.TRANSACTION_STATUS_RIC_CARD_DEACTIVATED; //"RIC_CARD_DEACTIVATED";
		
	public static final String TRANSACTION_STATUS_NIC_CANCELLED = NicTransactionLog.TRANSACTION_STATUS_NIC_CANCELLED;
	
	public static final String TRANSACTION_STATUS_VOID						= NicTransactionLog.TRANSACTION_STATUS_VOID; //"VOID";
	
	//to indicate update_by or create_by
	public final static String SYSTEM_EPP = "SYSTEM_EPP";
	
	//TransactionWS.uploadTransaction [M1]
	public static final String TX_UPLOAD_ERROR_CODE_DUPLICATE_TRANSACTION_ID			= "UL-E1001";
	//public static final String TX_UPLOAD_ERROR_CODE_DUPLICATE_TRANSACTION_ID_DIFF_NIN	= "UL-E1002";
	public static final String TX_UPLOAD_ERROR_CODE_DATA_VALIDATION_FAILED				= "UL-E2001";
	public static final String TX_UPLOAD_ERROR_CODE_CHECKSUM_VERIFICATION_FAILED		= "UL-E2002";
	public static final String TX_UPLOAD_ERROR_CODE_RE_REG_VALIDATION_FAILED			= "UL-E2003";

	// EPP PERSO 
	public final static String TRANSACTION_STATUS_PACKED                      = "PACKED";
    public final static String TRANSACTION_STAGE_PERSO                      = "PERSO";
    public final static String TRANSACTION_STAGE_DISPATCH                   = "DISPATCH";
    public final static String TRANSACTION_STATUS_PERSO_RECEIVED            = "PERSO_RECEIVED";
    public final static String TRANSACTION_STATUS_PERSO_QUEUED              = "PERSO_QUEUED";
    public final static String TRANSACTION_STATUS_PERSO_PRINTED             = "PERSO_PRINTED";
    public final static String TRANSACTION_STATUS_PERSO_QC_COMPLETED        = "PERSO_QC_COMPLETED";
    public final static String TRANSACTION_STATUS_PERSO_ERROR               = "PERSO_ERROR";
    public final static String TRANSACTION_STATUS_PERSONALIZED              = "PERSONALIZED";
    public final static String TRANSACTION_STATUS_PRIORITIZED               = "PRIORITIZED"; // 30-May-2016 (khang)
    public final static String TRANSACTION_STATUS_REPRINT                   = "REPRINT"; // 30-May-2016 (khang)
    public final static String TRANSACTION_STATUS_PASSPORT_CANCELLED        = "PASSPORT_CANCELLED"; // 30-May-2016 (khang)
    
    public static final String PARA_SCOPE_APPLICATION = "APPLICATION"; // 13 May 2016(chris)
    public static final String PARA_NAME_MAX_NUM_YEAR_FOR_VALID_DOB = "MAX_NUM_YEAR_FOR_VALID_DOB"; // 13 May 2016 (chris)
    
    public static final String PARA_SCOPE_PERSO = "PERSO"; // 13 May 2016(chris)
    public static final String PARA_NAME_MAX_LEN_FULLNAME = "MAX_LEN_FULLNAME"; // 30 May 2016 (chris) - Fullname size limit (in chip)
    public static final String PARA_NAME_MAX_LEN_NAMEPAGE = "MAX_LEN_NAMEPAGE"; // 30 May 2016 (chris) - name length limit (in name page)
    public static final String PARA_NAME_MAX_LEN_PLACE_OF_BIRTH = "MAX_LEN_PLACE_OF_BIRTH"; // 03 Jun 2016 (chris) - place of birth length limit (in limitation page)
    public static final String PARA_NAME_MAX_LEN_PER_LINE_PG3 = "MAX_LEN_PER_LINE_PG3"; // 30 May 2016 (chris) - printable text length limit (in limitation page, limitation, fullname, aka)
    public static final String PARA_NAME_MAX_LINES_PG3_LIMITATION = "MAX_LINES_PG3_LIMITATION"; // 30 May 2016 (chris) - printable no of lines limit for limitation (in limitation page)
    public static final String PARA_NAME_MAX_LINES_PG3_POSITION = "MAX_LINES_PG3_POSITION"; // 30 May 2016 (chris) - printable no of lines limit for position (in limitation page)
    public static final String PARA_NAME_MAX_LINES_PG3_FULLNAME = "MAX_LINES_PG3_FULLNAME"; // 30 May 2016 (chris) - printable no of lines limit for fullname (in limitation page)
    public static final String PARA_NAME_MAX_LINES_PG3_AKA = "MAX_LINES_PG3_AKA"; // 03 Jun 2016 (chris) - aka (Aliasname) length limit (in limitation page)
    public static final String PARA_NAME_MAX_LEN_PER_LINE_PG3_POSITION = "MAX_LEN_PER_LINE_PG3_POSITION"; // 03 Jun 2016 (chris) - position length limit (in limitation page)
    
    public static final String PARA_NAME_MAX_LEN_PG3_LIMITATION = "MAX_LEN_PG3_LIMITATION"; // 06 Jun 2016 (chris) - limitation length limit (in limitation page, IAI auto wrap)
    public static final String PARA_NAME_MAX_LEN_PG3_FULLNAME = "MAX_LEN_PG3_FULLNAME"; // 06 Jun 2016 (chris) - fullname length limit (in limitation page, IAI auto wrap)
    public static final String PARA_NAME_MAX_LEN_PG3_AKA      = "MAX_LEN_PG3_AKA";      // 06 Jun 2016 (chris) - aka length limit (in limitation page, IAI auto wrap)
    public static final String PARA_NAME_MAX_LEN_PG3_POSITION = "MAX_LEN_PG3_POSITION"; // 31 May 2016 (chris) - position length limit (in limitation page, IAI auto wrap)

    public static final String PARA_NAME_APPLY_NAME_TRUNCATION = "APPLY_NAME_TRUNCATION"; // 29 Jul 2016 (chris) - to apply name truncation for chip, name page, 3 page.
    public static final String PARA_SCOPE_WS = "WS"; // 29 Jul 2016(chris) - change WS validation to WS scope
    public static final String PARA_NAME_TRUNCATE_TO_LAST_SPACE = "TRUNCATE_TO_LAST_SPACE"; // 19 Aug 2016 (chris) - to apply name truncation for chip, name page, 3 page, flag to indicate truncate to last space
    /*
     * WS - MAX_LEN_FULLNAME, MAX_LEN_NAMEPAGE, MAX_LEN_PG3_LIMITATION, MAX_LEN_PG3_FULLNAME, MAX_LEN_PG3_AKA, MAX_LEN_PG3_POSITION
     */
    
	//default methods from super class
//	public NicTransaction findById(String id);
//	public String save (NicTransaction e);
//	public void saveOrUpdate(NicTransaction e);
//	public NicTransaction merge(NicTransaction e);
//	public void delete(NicTransaction e);
//	public void delete(String id);
//	public List<NicTransaction> findAll ();
//	public List<NicTransaction> findAll (NicTransaction e);
//	public PaginatedResult<NicTransaction> findByPagination (NicTransaction e, int pageNo, int pageSize);
	
	public List<NicTransaction> findAllByFields(String surname,	String firstName, String middleName, String sex, String dob, boolean hasIssuanceData, boolean hasTransDocs, boolean hasRegData, boolean hasTransPayment) throws TransactionServiceException;
	public List<MainTransaction> findAllTransactionHistory(String transactionId, String documentNo, boolean wantRegistrationData, boolean wantTransactionDoc, boolean wantIssuanceData, boolean wantPayment, boolean wantTransactionLog, boolean wantLatestOnly) throws TransactionServiceException;
	public List<MainTransaction> findAllTransactionHistory(String transactionId, String documentNo, String nin, boolean wantRegistrationData, boolean wantTransactionDoc, boolean wantIssuanceData, boolean wantPayment, boolean wantTransactionLog, boolean wantLatestOnly) throws TransactionServiceException;
	
	public void saveMainTransaction(MainTransaction mainTransactionDTO) throws TransactionServiceException;
	public void updateStatusByTransactionId(String transactionId, String status, String officerId, String workstationId) throws TransactionServiceException;
	
	/**
	 * To save the transactionLogList passed in. If any of the transactionLog unable to save to database, it will be returned.
	 * @param transactionLogList
	 * @return
	 */
	public List<NicTransactionLog> saveTransactionLogs(List<NicTransactionLog> transactionLogList);
	/* [used by batch job] To check duplicate application, to prevent applicant to come to different collection site to apply two card. */
	public String checkDuplicateApplication(String transactionId, String nin, String transactionType, String transactionSubtype, String refTxnId) throws TransactionServiceException;
	/* [used by online webservice] To check duplicate application, to prevent applicant to come to different collection site to apply two card. */
	//List<NicTransaction> checkDuplicateByNin(String transactionId, String nin,	String transactionType, String remarks, String officerId, String wkstnId, String siteCode) throws TransactionServiceException;
	
	public NicTransaction findById(String transactionId, String nin, String ccn) throws TransactionServiceException;
	//public List<NicTransaction> findAllByNin(String nin) throws TransactionServiceException;
	//public List<NicTransaction> findAllByCcn(String ccn) throws TransactionServiceException;

	//
	public BaseModelSingle<NicTransaction> findTransactionById(String transactionId) throws TransactionServiceException;
	
	public List<NicTransactionLog> findAllTransactionLogsByRefId(String refId) throws TransactionServiceException;
	public BaseModelList<NicTransactionLog> findAllRicTransactionLogsByRefId(String refId) throws TransactionServiceException;
	
	
	public NicTransaction getTransactionIssuanceDatas(NicTransaction transObj) throws TransactionServiceException;
	public NicTransaction getTransactiondocuments(NicTransaction transObj) throws TransactionServiceException;
	public NicTransaction getTransactionRegistrationData(NicTransaction transObj) throws TransactionServiceException;
//	public NicTransaction getTransactionPayment(NicTransaction transObj) throws TransactionServiceException;

	public NicTransaction findById(String transId, boolean hasIssuanceData, boolean hasTransDocs, boolean hasRegData, boolean hasTransPayment)
			throws TransactionServiceException;
	
	//public NicTransaction findById(String id, boolean wantPersonDetail, boolean wantImages, boolean wantIssuanceData, boolean wantPayment, boolean wantTransactionLog);
//    RicTransaction getTransactionByNin(String nin);
//    RicTransactionLog getLatestLog(String transactionId); 
//    RicTransaction getTransactionByQueueNumber(String QueueNum);
	
//	public PaginatedResult<NicMain> findAllForPagination(PageRequest pageRequest, NicMain nicMain) throws TransactionServiceException;

//	public List<NicHistory> getNicHistory(String nin) throws TransactionServiceException;

//	public PaginatedResult<NicIssuanceData> findAllForPagination(
//			PageRequest pageRequest, NicIssuanceData nicIssuanceData) throws TransactionServiceException;

	PaginatedResult<NicTransaction> findAllForPagination(PageRequest pageRequest, NicTransaction nicTransaction) throws TransactionServiceException;
	
	//moved to RICReportService.java
	//public PaginatedResult<RICTxnRptDto> getRicTxnRptRecordList(RICTxnRptDto ricTxnRptCriteria,int pageNo, int pageSize);
	//public PaginatedResult<RICPymtRptDto> getRicPymtRptRecordList(RICPymtRptDto ricPymtRptCriteria,int pageNo, int pageSize);
	 
 	public List<Object[]> getNicTransactionAttachmentByDocType(NicRegistrationDataDto nicRegData) throws TransactionServiceException;

	public List getCardConvChartData(Date fromDate, Date toDate, String[] sites) throws Exception;

	public List<Object[]> getTotTransVolBySiteReportInfo(Date fromDate, Date toDate, String[] sites) throws Exception;
	
	public MultiSeriesReport getMSSeriesReport(String reportBeanName, HashMap<String, Object> parameters) throws Exception;

	public List<Object[]> getCardBypassApprovedBySiteInfo(Date fromDate, Date toDate, String[] sites) throws Exception;

	public PaginatedResult<String> getCardBypassApprovedTransIds(Date fromDate, Date toDate, String[] siteCodes, int fromRow, int toRow) throws Exception;

	public List<String> getAllCardBypassApprovedTransIds(Date fromDate, Date toDate, String[] sites ) throws Exception;

	public PaginatedResult<NicTransactionLog> findAllForPagination(
			int currentPage, int pageSize, String siteCode, String officerId, String transactionStage, String transactionStatus) throws Exception;

	public void updateTransactionStatusOnReprint(String transactionId, String userId, String wkstnId) throws Exception;

	public BaseModelSingle<NicTransaction> getNicTransactionById(String transactionId)throws TransactionServiceException;
	
	public NicTransaction getNicTransactionByPrevPPno(String prevPassportNo, String typePassport, String nin)throws TransactionServiceException;
	
	public void updateDocByStatus(String transactionId, String passportNo, TransactionStatus cancelled,
			String userName, String wkstnId) throws Exception;
	
	public int updateStatusByTxnIdList(List<String> txnIdList, String status, String officerId, String workstationId) throws TransactionServiceException;
	public List<NicTransaction> findByApplnRefId(String applnRefNo) throws TransactionServiceException;
	public boolean prioritizeTransaction(String transactionId, String remarks, String userId, String wkstnId) throws Exception;
	public boolean reprintTransaction(String transactionId, boolean cancelPptFlag, String remarks ,String userId, String wkstnId) throws Exception;
	public boolean cancelPassport(String transactionId, String passportNo, String remarks, String userId, String wkstnId) throws Exception;
	
	public void updateDocByStatus(String transactionId, String passportNo, TransactionStatus cancelled,
			String userName, String wkstnId, String transactionStage) throws Exception;

	public List<NicTransaction> findAllByFields (String nin) throws Exception;
	
	//Phúc thêm 5/5/2019 lấy danh sách theo mã phiêu
	public List<NicTransaction> getListTransactionByPackage(String packageId);
	
	public List<NicTransaction> getListTransactionBySiteCode(String siteCode, Date date);
	
	//[09-Mar] Trung thêm số năm hết hạn hộ chiếu 
	public boolean updateValidateTime(String transactionId, String userId, String wkstnId, Integer year);
	
	public void updateOnApprove(String transactionId, String userId, String wkstnId);
	public boolean searchTransactionByLog(String refId,String transactionStatus);

	public List<CountPassport> issuePassportList() throws Exception;
	
	public List<CountPassport> noneIssuePassportList() throws Exception;
	
	public List<CountPassport> printedPassport() throws Exception;
	
	public List<CountPassport> nonePrintPassport() throws Exception;
	
	public List<NicUploadJobDto> allPassportList(String handoverId, String regSite, String fromDate, String toDate, int pageNumber, int pageSize) throws Exception;
	
	public List<NicUploadJobDto> newTransactionProcess() throws Exception;
	public List<NicUploadJobDto> newTransactionProcessDDXL() throws Exception;
	
	public List<CountPassport> totalHosoTrongNgay() throws Exception;
	public List<CountPassport> slhosoDuyetCap() throws Exception;
	public List<CountPassport> slhosoTuChoi() throws Exception;
	public List<CountPassport> slhosoSapHetHan() throws Exception;
	public List<CountPassport> slhosoQuaHan() throws Exception;
	
	public long getRowCountallPassportList(String handoverId, String regSite, String fromDate, String toDate) throws Exception;

	public List<NicUploadJobDto> allPersoList(String handoverId, Integer persoId, String fromDate, String toDate, int pageNumber, int pageSize) throws Exception;
	public long getRowCountallPersoList(String handoverId, Integer persoId, String fromDate, String toDate) throws Exception;
	public PaginatedResult<NicUploadJobDto> allListBProcessPerso(String packageId, String dateApprove, String siteCode, int pageNo, int pageSize);

	public List<CountPassport> sohschuaxl() throws Exception;
	public List<CountPassport> sohsdaxl() throws Exception;
	
	public PaginatedResult<NicUploadJobDto> allPassportStorage(String passportType, String passportNo, String status, String startDate, String endDate, String fullName,int pageNo, int pageSize) throws Exception;
	public PaginatedResult<Datadto> allPassportStorageT(String name,String dateOfBirth, String idNumber ,String gender,String listCode,String passportNo ,String passportType,String receipNo , int page, int pageSize) throws Exception;
	public PaginatedResult<NicUploadJobDto> allTransactionStorage(String transactionId, String recieptNo, String passportNo, String nin, String fullName, String passportStyle, String ppStage, String typeInves, int pageNo, int pageSize) throws Exception;

	public List<NicUploadJobDto> allTracuuhosohc(NicUploadJobDto model, int pageNumber, int pageSize) throws Exception;
	public long getTracuuhosohcCount(NicUploadJobDto model) throws Exception;
	public PaginatedResult<NicUploadJobDto> allPassportDestroy(String passportNo, String[] status, String toDay, int pageNo, int pageSize) throws Exception;


	public List<NicUploadJobDto> searchUpdateB(String packageId, Date createDate, Date endDate) throws Exception;

	
	public List<NicUploadJobDto> listPackageIDPerso(String code, String packId) throws Exception;
	
	public List<NicUploadJobDto> listPackageIDPerso(String code) throws Exception;
	
	public BaseModelSingle<NicTransaction> applyNameTruncation(NicTransaction nicTransactionDBO);
	public BaseModelSingle<NicUploadJobDto> listPackageIDConfig(String packageId, Date todayTime) throws Exception;
	public NicUploadJobDto listPackageIDDefault(String packageId, String type) throws Exception;
	public PaginatedResult<ImmiHistory> getAllImmihistory(String fullName, Date dob, String gender, String passportNo, String visaNo, String nin, String nationality, int pageNo, int pageSize);
	public String getSitePerso(String transactionId) throws Exception; 
	
	public BaseModelSingle<String> saveTransaction(NicTransaction tran) throws Exception; 
	public BaseModelSingle<Boolean> saveOrUpdateTransaction(NicTransaction tran) throws Exception;
	public List<NicTransaction> findTranByPersonCode(String personCode) throws Exception;
	public List<NicTransaction> findTranByListPersonCode(List<EppPerson> listPerson) throws Exception;
	public List<NicTransaction> findTranByArchiveCode(String archiveCode) throws Exception;
	public List<NicTransaction> findTranByNinAndTypePassport(String nin,
			String passportType) throws Exception;
	public List<NicTransaction> findAllByListTranId(
			String[] listTranId)throws Exception;
	public PaginatedResult<NicTransaction> getPageByRegDateOrRegSite(
			String fromDate, String toDate, String regSiteCode, int pageNo, int pageSize)throws Exception;
	public List<CountTranStatus> countTranByStatusAndCoditions(String fromDate,
			String toDate, String regSiteCode);
	public List<NicTransaction> getByRegDateOrRegSite(String fromDate,
			String toDate, String regSiteCode);
	public PaginatedResult<ReportRegProcessData> getPageByCodition(
			String fromDate, String toDate, String regSiteCode,
			String printSiteCode, int pageNo, int pageSize);
	public List<ReportRegProcessData> getAllByCodition(String fromDate,
			String toDate, String regSiteCode, String printSiteCode);
	public List<Integer> getCountTransmissFromA08()throws Exception;
}
