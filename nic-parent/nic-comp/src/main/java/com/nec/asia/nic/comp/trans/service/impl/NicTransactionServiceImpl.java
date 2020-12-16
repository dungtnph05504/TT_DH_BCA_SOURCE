package com.nec.asia.nic.comp.trans.service.impl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.dispatch.dao.DispatchDataDao;
import com.nec.asia.nic.comp.dispatch.dao.DispatchPackageDetailDao;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.dao.DocumentHistoryDao;
import com.nec.asia.nic.comp.trans.dao.NicAfisDataDao;
import com.nec.asia.nic.comp.trans.dao.NicFPDataDao;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryDao;
import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.dao.NicRejectionDataDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionPaymentDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionReprintDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicDocumentHistory;
import com.nec.asia.nic.comp.trans.domain.NicFPData;
import com.nec.asia.nic.comp.trans.domain.NicFPDataId;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReprint;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReprintId;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.CountTranStatus;
import com.nec.asia.nic.comp.trans.dto.Datadto;
import com.nec.asia.nic.comp.trans.dto.NicRegistrationDataDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.ReportRegProcessData;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.PersoService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.PersoServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.utils.FmsTemplateXmlConvertor;
import com.nec.asia.nic.comp.trans.utils.MSReportDefinition;
import com.nec.asia.nic.comp.trans.utils.MultiSeriesReport;
import com.nec.asia.nic.comp.trans.utils.RegistrationConstants;
import com.nec.asia.nic.comp.trans.utils.ReportDefinition;
import com.nec.asia.nic.comp.trans.utils.TransDTOMapper;
import com.nec.asia.nic.comp.trans.utils.type.FingerprintSection;
import com.nec.asia.nic.comp.trans.utils.type.FingerprintTemplate;
import com.nec.asia.nic.comp.trans.utils.type.FmsTemplate;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.dx.trans.Fingerprint;
import com.nec.asia.nic.dx.trans.FingerprintInfo;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.trans.PersonDetail;
import com.nec.asia.nic.dx.trans.RejectionData;
import com.nec.asia.nic.dx.trans.TransactionLog;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;

/**
 * Transaction Service Implementation class: To retrieve, save/update the
 * transaction.
 * 
 * @author chris_wong
 */

/*
 * Modification History:
 * 
 * 09 Aug 2013 (chris): update validation logic on fingerprint minutia template.
 * 12 Aug 2013 (chris): update validation logic on fingerprint minutia template
 * total size 15 Aug 2013 (chris): add method findAllByFields() 16 Aug 2013
 * (chris): update validation logic on collection site, date of birth. 18 Aug
 * 2013 (chris): update validation logic on address as Chip Storage increased to
 * 1K. 12 Sep 2013 (chris): add validation on site_code 05 Oct 2013 (chris): to
 * block duplicate of NIN [rollback]. 08 Oct 2013 (chris): to handle duplicate
 * logs 18 Oct 2013 (chris): to check surname, firstname not empty, signature is
 * not null. 25 Oct 2013 (chris): to check fpAsSignature is not null. 28 Oct
 * 2013 (chris): to add validate checksum logic 29 Oct 2013 (chris): to rewrite
 * code for create Upload Job. 05 Nov 2013 (priya): to add txnRptrecordList 08
 * Nov 2013 (chris): to add validate est collection date, option surname. 11 Nov
 * 2013 (chris): to check duplicate application from CC / RIC. 12 Nov 2013
 * (chris): to handle logging during transaction upload. 18 Nov 2013 (chris):
 * add error code for transactionUpload 25 Nov 2013 (chris): to remove
 * validation on firstname 01 Dec 2013 (chris): temporary remove checksum
 * validation 13 Dec 2013 (chris): update logic to handle re-registration 18 Dec
 * 2013 (chris): add logic to retrieve rejection data 06 Jan 2014 (Sailaja) :
 * add findAllForPagination method to view the fps issuance data 10 Jan 2014
 * (Sailaja): Added updateTransactionStatusOnReprint method to update
 * transaction status on Re-print. 15 Jan 2014 (Peddi Swapna): Added new
 * methods: getCardBypassApprovedBySiteInfo(), getCardBypassApprovedTransIds(),
 * getAllCardBypassApprovedTransIds() 16 Jan 2014 (chris): add logic to retrieve
 * transaction log by nin. 05 Feb 2014 (chris): fix unable to upload full
 * amputate cases without fp object. 13 Feb 2014 (chris): add condition for
 * RE-REG in saveMainTransaction() and checkDuplicateApplication() 19 Mar 2014
 * (chris): enable checksum validation 20 May 2014 (chris): update logic for
 * checkDuplicateApplication(), saveMainTransaction() 23 May 2014 (chris): add
 * methods checkDuplicateByNin for ELIGIBLITY_CHECK 23 Jun 2014 (chris): add ric
 * stage in findAllRicTransactionLogsByRefId(): PEND_EH, PEND_ISS_SUPERVISOR,
 * CARD_REPACKAGE, CARD_TRANSFER, VOID, etc 24 Jun 2014 (chris): fix bug on
 * saveMainTransaction() for null in nic_registration_data.nic_id 05 Jan 2016
 * (chris): update for uploadTransaction API. 15 Jan 2016 (chris): to generate
 * FPData when saveMainTransaction(). 22 Feb 2016 (chris): create DCM capture
 * txn log if it doesn't have. 25 Feb 2016 (chris): validate TPL position
 * against FP position (TPL = FP-1) 21 Mar 2016 (chris): validate Priority,
 * validityPeriod, issuingAuthority 31 Mar 2016 (chris): validate TPL missing
 * case 20 Apr 2016 (chris): to block TPL missing when it is empty string 26 Apr
 * 2016 (chris): to block invalid Passport (Temporary), to configure in
 * codetable when it is supported. 09 May 2016 (khang): add method
 * updateStatusByTxnIdList 13 May 2016 (chris): to block invalid DOB. 30 May
 * 2016 (chris): to block long name, long official positions. 03 Jun 2016
 * (chris): to block long alias. 29 Jul 2016 (chris): to generate full name :
 * first + middle + last, remove if exceed max. 16 Aug 2016 (chris): change full
 * name format: surname, firstname middlename 16 Aug 2016 (chris): change ARN
 * format for reprint to RE-01-xxxx instead of RE-001-xxxx 19 Aug 2016 (chris):
 * change trunction rules to cut to last space, keep chip name in line 2. 19 Aug
 * 2016 (chris): change ARN format for reprint to R-1-xxxx instead of
 * RE-001-xxxx 30 Jun 2017 (chris): remove duplicate transaction dto in method
 * nicTransactionService.findAllTransactionHistory(transactionId, passportNo,
 * nin, ...)
 */

@Service("nicTransactionService")
@Transactional
public class NicTransactionServiceImpl extends
		DefaultBusinessServiceImpl<NicTransaction, String, NicTransactionDao>
		implements NicTransactionService {

	@Autowired
	private TransDTOMapper mapper = null;
	@Autowired
	private DocumentDataDao documentDataDao = null;
	@Autowired
	private NicRegistrationDataDao registrationDataDao = null;
	@Autowired
	private NicTransactionPaymentDao transactionPaymentDao = null;
	@Autowired
	private NicTransactionAttachmentDao transactionDocumentDao = null;
	@Autowired
	private NicFPDataDao nicFPDataDao = null;
	@Autowired
	private NicTransactionLogDao transactionLogDao = null;
	@Autowired
	private TransactionLogService transactionLogService = null;
	@Autowired
	private NicUploadJobDao uploadJobDao = null;
	@Autowired
	private CodesService codesService = null;
	@Autowired
	private SiteService siteService = null;
	@Autowired
	private NicRejectionDataDao nicRejectionDataDao = null;
	@Autowired
	private ParametersDao parametersDao; // 13 May 2015

	@Autowired
	private NicCommandUtil nicCommandUtil;

	@Autowired
	private DocumentDataService documentDataService = null;

	@Autowired
	private PersoService persoService = null;

	@Autowired
	private NicTransactionReprintDao transactionReprintDao = null;

	@Autowired
	private DispatchPackageDetailDao dispatchPackageDetailDao = null;

	@Autowired
	private DispatchDataDao dispatchDataDao = null;

	@Autowired
	private DocumentHistoryDao documentHistoryDao = null;

	@Autowired
	private NicRejectionDataDao rejectionDataDao = null;

	@Autowired
	private NicAfisDataDao afisDataDao = null;

	@Autowired
	private ListHandoverService listHandoverService;

	@Autowired
	private NicImmiHistoryDao immiHistoryDao;

	public static TransLogInfoXmlConvertor logUtil = new TransLogInfoXmlConvertor();

	private FmsTemplateXmlConvertor xmlConvertor = new FmsTemplateXmlConvertor();

	// private TransactionChecksumGenerator checksumGenerator = new
	// TransactionChecksumGenerator();

	/** must define the non-argument constructor because we use CGLib proxy */
	public NicTransactionServiceImpl() {
	}

	@Autowired
	public NicTransactionServiceImpl(NicTransactionDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public void saveMainTransaction(MainTransaction transactionDTO)
			throws TransactionServiceException {
		/*
		 * Step 1: Save NicTransaction, RegistrationData, TransactionDocument
		 * Step 2: Create Nic Job Step 3: Update Nic Transaction Step 4: Create
		 * Nic Transaction Log
		 */
		Date startTime = new Date();
		String refId = null;
		String officerId = SYSTEM_EPP;
		String wkstnId = SYSTEM_EPP;
		String siteCode = "";
		String txnStatus = TRANSACTION_STATUS_TX_UPLOAD_COMPLETED;
		String logData = null;
		try {
			officerId = nicCommandUtil.getSystemName();
			wkstnId = nicCommandUtil.getHostName();
			// Step 1:Save NicTransaction, RegistrationData, TransactionDocument
			NicTransaction nicTransactionDBO = null;
			NicRegistrationData nicRegDataDBO = null;
			Collection<NicTransactionAttachment> nicTransDocDBOList = null;

			String transactionId = transactionDTO.getTransactionID();
			String transactionType = transactionDTO.getTransactionType();
			logger.debug("saveMainTransaction, transactionId:{}", transactionId);

			refId = transactionId;
			siteCode = transactionDTO.getRegSiteCode();

			// if (StringUtils.equals(TRANSACTION_TYPE_REGISTRATION,
			// transactionType)
			// || StringUtils.equals(TRANSACTION_TYPE_REPLACEMENT,
			// transactionType)
			// || StringUtils.equals(TRANSACTION_TYPE_LOST, transactionType)) {
			{
				// Duplication Check.
				NicTransaction tempTransactionDBO = this
						.findById(transactionId);
				if (tempTransactionDBO != null) {
					String errorCode = TX_UPLOAD_ERROR_CODE_DUPLICATE_TRANSACTION_ID;
					// if (!StringUtils.equals(tempTransactionDBO.getNin(),
					// transactionDTO.getNin())) {
					// errorCode =
					// TX_UPLOAD_ERROR_CODE_DUPLICATE_TRANSACTION_ID_DIFF_NIN;
					// }
					logger.error(
							"Transaction cannot be uploaded as duplicate transaction Id found.[{}], errorCode:{}",
							transactionId, errorCode);
					throw new TransactionServiceException(
							"Transaction cannot be uploaded as duplicate transaction Id found.",
							errorCode);
				}
				// 05 Oct 2013 (chris): to block duplicate of NIN.
				/*
				 * if (StringUtils.equals(TRANSACTION_TYPE_REGISTRATION,
				 * transactionType) ||
				 * StringUtils.equals(TRANSACTION_TYPE_CONVERSION,
				 * transactionType)) { String nin = transactionDTO.getNin();
				 * String transactionIdHit = null; boolean transactionExists =
				 * false; List<NicTransaction> resultList = findAllByNin(nin);
				 * if (CollectionUtils.isNotEmpty(resultList)) { for
				 * (NicTransaction tempTransaction: resultList) { if
				 * (StringUtils.equals(tempTransaction.getTransactionType(),
				 * transactionType)) { //to check transaction status if
				 * (!StringUtils.equals(tempTransaction.getTransactionStatus(),
				 * TRANSACTION_STATUS_RIC_CARD_REJECTED) &&
				 * !StringUtils.equals(tempTransaction.getTransactionStatus(),
				 * TRANSACTION_STATUS_INVENTORY_CARD_DESTROYED) &&
				 * !StringUtils.equals(tempTransaction.getTransactionStatus(),
				 * TRANSACTION_STATUS_NIC_REJECTED)) { transactionExists = true;
				 * } } } if (transactionExists) { throw new
				 * TransactionServiceException
				 * ("Transaction cannot be uploaded as transaction Id ["
				 * +transactionId
				 * +"] found for nin ["+nin+"] on previous transaction Id ["
				 * +transactionIdHit+"]."); } } }
				 */
				// 05 Oct 2013 (chris): to block duplicate of NIN.

				// String transactionSubtype =
				// transactionDTO.getTransactionSubtype();
				// if (StringUtils.equalsIgnoreCase(TRANSACTION_SUBTYPE_RE_REG,
				// transactionSubtype)) {
				// String refTxnId =
				// mapper.getReferenceTransactionId(transactionDTO);
				// boolean validReReg = false;
				// if (StringUtils.isNotBlank(refTxnId)) {
				// String prevTransactionStatus =
				// dao.getTransactionStatusById(refTxnId);
				// if (StringUtils.equals(prevTransactionStatus,
				// TRANSACTION_STATUS_NIC_REJECTED) ||
				// StringUtils.equals(prevTransactionStatus,
				// TRANSACTION_STATUS_NIC_CANCELLED) ||
				// StringUtils.equals(prevTransactionStatus,
				// TRANSACTION_STATUS_VOID) || //07 Apr 2014 (chris) to support
				// void
				// StringUtils.equals(prevTransactionStatus,
				// TRANSACTION_STATUS_RIC_CARD_RETURNED) || //23 May 2014
				// (chris) to support return for destroy
				// StringUtils.equals(prevTransactionStatus,
				// TRANSACTION_STATUS_INVENTORY_CARD_RECEIVED) || //23 May 2014
				// (chris) to support return for destroy
				// StringUtils.equals(prevTransactionStatus,
				// TRANSACTION_STATUS_INVENTORY_CARD_DESTROYED) || //23 May 2014
				// (chris) to support return for destroy
				// StringUtils.equals(prevTransactionStatus,
				// TRANSACTION_STATUS_RIC_CARD_REJECTED)
				// ) {
				// validReReg = true;
				// logger.info("valid for RE_REG transaction, prev transaction [{}] is [{}]",
				// refTxnId, prevTransactionStatus);
				// }
				// //23 May 2014 (chris) to support terminated card to re-reg.
				// else if (StringUtils.equals(prevTransactionStatus,
				// TRANSACTION_STATUS_RIC_CARD_DEACTIVATED)) {
				// List<NicIssuanceData> nicIssuanceDataList =
				// nicIssuanceDataDao.findByTransactionId(refTxnId);
				// for (NicIssuanceData nicIssuanceData: nicIssuanceDataList) {
				// if (StringUtils.isNotBlank(nicIssuanceData.getCcn())) {
				// if (StringUtils.equals(nicIssuanceData.getCardStatus(),
				// NicChangeStatusLog.CARD_STATUS_TERMINATED)) {
				// validReReg = true;
				// logger.info("valid for RE_REG transaction, prev transaction [{}] is [{}], card [{}] is [{}]",
				// new String[] {refTxnId, prevTransactionStatus,
				// nicIssuanceData.getCcn(), nicIssuanceData.getCardStatus()});
				// }
				// }
				// }
				// }
				// if (!validReReg){
				// String errorCode =
				// TX_UPLOAD_ERROR_CODE_RE_REG_VALIDATION_FAILED;
				// throw new
				// TransactionServiceException("Link Transaction must be NIC_CANCELLED / NIC_REJECTED.",
				// errorCode);
				// }
				// } else {
				// logger.error("REF_TXN_ID is mandatory for RE_REG transaction.");
				// }
				// }

				// RegistrationData regDataDTO =
				// transactionDTO.getRegistrationData();
				nicTransactionDBO = mapper
						.parseNicTransactionDBO(transactionDTO);
				// 29 Jul 2016 (chris): first + middle + last, remove if exceed
				// max length.
				nicTransactionDBO = this.applyNameTruncation(nicTransactionDBO)
						.getModel();

				// to throw exception with the validation reason
				validateTransaction(transactionDTO);

				// [2014 May 15] Duplication Check again before save to DB.
				tempTransactionDBO = this.findById(transactionId);
				if (tempTransactionDBO != null) {
					String errorCode = TX_UPLOAD_ERROR_CODE_DUPLICATE_TRANSACTION_ID;
					logger.error(
							"Transaction cannot be uploaded as duplicate transaction Id found.[{}], errorCode:{}",
							transactionId, errorCode);
					throw new TransactionServiceException(
							"Transaction cannot be uploaded as duplicate transaction Id found.",
							errorCode);
				}

				String transactionResult = dao.save(nicTransactionDBO);
				logger.debug("saving nicTransaction, transactionId:{}",
						transactionResult);
				if (nicTransactionDBO.getNicRegistrationData() != null) {// regDataDTO!=null)
																			// {
					// String nin = transactionDTO.getNin();
					nicRegDataDBO = nicTransactionDBO.getNicRegistrationData();// mapper.parseRegistrationDataDBO(transactionId,
																				// nin,
																				// regDataDTO);
					nicTransDocDBOList = nicTransactionDBO
							.getNicTransactionAttachments();// mapper.parseTransactionDocDBOList(transactionId,
															// regDataDTO);

					String regisDataResult = registrationDataDao
							.save(nicRegDataDBO);
					logger.debug("saving registrationData, transactionId:{}",
							regisDataResult);

					// [2017] save payment
					if (nicTransactionDBO.getNicTransactionPayment() != null
							&& StringUtils.isNotBlank(nicTransactionDBO
									.getNicTransactionPayment().getPaymentId())) {
						String paymentDataResult = transactionPaymentDao
								.save(nicTransactionDBO
										.getNicTransactionPayment());
						logger.debug("saving paymentData, transactionId:{}",
								paymentDataResult);
					}

					for (NicTransactionAttachment nicTransDoc : nicTransDocDBOList) {
						// if (nicTransDoc.getNicTransaction()==null) {
						// logger.warn("nicTransDoc.getTransactionId() is null");
						// if (nicTransactionDBO!=null) {
						// nicTransDoc.setNicTransaction(nicTransactionDBO);
						// logger.warn("nicTransDoc.getTransactionId():{}",
						// nicTransactionDBO.getTransactionId());
						// }
						// }
						if (StringUtils.isBlank(nicTransDoc.getTransactionId())) {
							logger.warn("nicTransDoc.getTransactionId() is null");
							if (nicTransactionDBO != null) {
								nicTransDoc.setTransactionId(nicTransactionDBO
										.getTransactionId());
								logger.warn(
										"nicTransDoc.getTransactionId():{}",
										nicTransactionDBO.getTransactionId());
							}
						}
						logger.debug(
								"saving transactionDoc, transactionId:{} docType:{}",
								nicTransactionDBO.getTransactionId(),
								nicTransDoc.getDocType());
						Long docId = transactionDocumentDao.save(nicTransDoc);
						// nicTransDoc.set
						logger.debug(
								"saving transactionDoc, transactionId:{} docType:{} --> docId: {}/{}",
								new Object[] {
										nicTransactionDBO.getTransactionId(),
										nicTransDoc.getDocType(), docId,
										nicTransDoc.getTransactionDocId() });
					}

					// Logs
					if (CollectionUtils.isNotEmpty(transactionDTO
							.getTransactionLogs())) {
						List<NicTransactionLog> transLogList = mapper
								.parseNicTransactionLogDBOList(transactionDTO
										.getTransactionLogs());
						List<NicTransactionLog> errorList = this
								.saveTransactionLogs(transLogList);
						for (NicTransactionLog errorLog : errorList) {
							logger.warn("Failed to save log: {}, {}, {}, {}",
									new Object[] { errorLog.getRefId(),
											errorLog.getTransactionStage(),
											errorLog.getTransactionStatus(),
											errorLog.getSiteCode() });
						}
					} else { // generate a CA Captured log for application
								// report
						List<NicTransactionLog> transLogList = new ArrayList<NicTransactionLog>();
						NicTransactionLog dcmTransLog = new NicTransactionLog();
						dcmTransLog.setRefId(nicTransactionDBO
								.getTransactionId());
						dcmTransLog.setLogCreateTime(nicTransactionDBO
								.getCreateDatetime());
						dcmTransLog.setOfficerId(nicTransactionDBO
								.getCreateBy());
						dcmTransLog.setSiteCode(nicTransactionDBO
								.getRegSiteCode());
						dcmTransLog.setStartTime(nicTransactionDBO
								.getCreateDatetime());

						dcmTransLog
								.setTransactionStage(TRANSACTION_STATE_TX_UPLOAD);
						dcmTransLog
								.setTransactionStatus(TransactionStatus.Captured
										.getKey());
						transLogList.add(dcmTransLog);
						List<NicTransactionLog> errorList = this
								.saveTransactionLogs(transLogList);
						for (NicTransactionLog errorLog : errorList) {
							logger.warn("Failed to save log: {}, {}, {}, {}",
									new Object[] { errorLog.getRefId(),
											errorLog.getTransactionStage(),
											errorLog.getTransactionStatus(),
											errorLog.getSiteCode() });
						}
					}
				}
				// Step 2: Create Nic Job
				Integer jobPriority = nicTransactionDBO.getPriority();
				if (jobPriority == null) {
					jobPriority = NicUploadJob.JOB_PRIORITY_NORMAL;
				}
				Long jobId = null;
				jobId = uploadJobDao.createWorkflowJob(transactionId,
						transactionType, jobPriority);
				logger.info(
						"[saveMainTransaction] save NicUploadJob, transactionId:{}, jobId:{} ",
						transactionId, jobId);

				// Step 3: Update Nic Transaction
				nicTransactionDBO.getNicRegistrationData().setWorkflowJobId(
						jobId);
				registrationDataDao.saveOrUpdate(nicTransactionDBO
						.getNicRegistrationData());
				logger.info(
						"[saveMainTransaction] Update Nic Registration Data, jobId:{} ",
						jobId);

				List<NicFPData> fpDataList = this
						.convertFPDataList(nicTransactionDBO);
				for (NicFPData fpData : fpDataList) {
					nicFPDataDao.save(fpData);
				}

				logger.debug("saveMainTransaction end, transactionId:{}",
						transactionId);
			}
		} catch (Throwable e) {
			logData = e.getMessage();// MiscXmlConvertor.parseObjectToXml(e);
			txnStatus = TRANSACTION_STATUS_TX_UPLOAD_ERROR;
			logger.info(
					"[saveMainTransaction] Error, refId:{}, txnStatus:{}, logData:{} ",
					new Object[] { refId, txnStatus, logData });
			logger.error("[saveMainTransaction] Exception: {} ", e.getMessage());
			if (e instanceof TransactionServiceException) {
				throw (TransactionServiceException) e;
			} else {
				throw new TransactionServiceException(e);
			}
		} finally {
			// Step 4: Create Nic Transaction Log
			logger.info(
					"[saveMainTransaction] save NicTransactionLog, transactionId:{}",
					refId);
			try {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName().toUpperCase();
			} catch (Exception e) {
			}

			Date endTime = new Date();
			NicTransactionLog nicTransLog = new NicTransactionLog();
			nicTransLog.setRefId(refId);
			nicTransLog.setLogCreateTime(new Date());
			nicTransLog.setStartTime(startTime);
			nicTransLog.setEndTime(endTime);
			nicTransLog.setTransactionStage(TRANSACTION_STATE_TX_UPLOAD);
			nicTransLog.setTransactionStatus(txnStatus);
			nicTransLog.setOfficerId(officerId);
			nicTransLog.setWkstnId(wkstnId);
			nicTransLog.setSiteCode(siteCode);
			nicTransLog.setLogInfo("");
			nicTransLog.setLogData(logData);
			// Long logId = transactionLogDao.save(nicTransLog);
			Long logId = transactionLogService.saveTransactionLog(refId,
					TRANSACTION_STATE_TX_UPLOAD, txnStatus, officerId, wkstnId,
					siteCode, startTime, endTime, "", logData).getModel();
			logger.info(
					"[saveMainTransaction] save NicTransactionLog completed, transactionId:{}, logId:{} ",
					refId, logId);

			if (transactionDTO.getTransactionType().equals(
					TRANSACTION_TYPE_REPLACEMENT)) {
				NicDocumentData documentData = documentDataService
						.findByDocNumber(transactionDTO.getPrevPassportNo())
						.getModel();
				if (documentData != null) {
					documentData.setActiveFlag(false);
					documentDataService.saveOrUpdate(documentData);
				}
			}
		}
	}

	// 29 Jul 2016 (chris): first + middle + last, remove if exceed max length.
	// 16 Aug 2016 (chris): change to new format: last, first middle
	// 19 Aug 2016 (chris): change logic to truncate to last space instead of
	// last valid character.
	// to set line1 for printing, line2 for chip encoding.
	/*
	 * To set Names line 1 when length exceed. Follow ICAO Standard for
	 * truncation: truncate until the last valid character.
	 */

	@Override
	public BaseModelSingle<NicTransaction> applyNameTruncation(
			NicTransaction nicTransactionDBO) {
		boolean autoNameTruncationFlag = this.getParamAsBool(PARA_SCOPE_PERSO,
				PARA_NAME_APPLY_NAME_TRUNCATION, false);
		if (autoNameTruncationFlag) {
			try {
				boolean cutToLastSpace = this.getParamAsBool(PARA_SCOPE_PERSO,
						PARA_NAME_TRUNCATE_TO_LAST_SPACE, true);

				boolean exceeded = false;
				int maxLenForNamepage = this.getParamAsInt(PARA_SCOPE_PERSO,
						PARA_NAME_MAX_LEN_NAMEPAGE, 50);
				int maxLenForChipFullname = this.getParamAsInt(
						PARA_SCOPE_PERSO, PARA_NAME_MAX_LEN_FULLNAME, 96);
				int maxLenForPage3Fullname = this.getParamAsInt(
						PARA_SCOPE_PERSO, PARA_NAME_MAX_LEN_PG3_FULLNAME, 224);

				String surname = nicTransactionDBO.getNicRegistrationData()
						.getSurnameFull();
				String firstname = nicTransactionDBO.getNicRegistrationData()
						.getFirstnameFull();
				String middlename = nicTransactionDBO.getNicRegistrationData()
						.getMiddlenameFull();

				surname = StringUtils.trim(surname);
				firstname = StringUtils.trim(firstname);
				middlename = StringUtils.trim(middlename);

				// 16 Aug 2016 (chris): change to new format: last, first middle
				// String fullname =
				// StringUtils.trim(firstname)+" "+StringUtils.trim(middlename)+" "+StringUtils.trim(surname);
				String fullname = surname + ", " + firstname + " " + middlename;

				int surnameLen = StringUtils.length(surname);
				int firstnameLen = StringUtils.length(firstname);
				int middlenameLen = StringUtils.length(middlename);

				if (surnameLen + firstnameLen + middlenameLen + 2 > maxLenForPage3Fullname) {
					/*
					 * -- format: first + middle + last if
					 * (surnameLen+firstnameLen+1 > maxLenForPage3Fullname) { if
					 * (firstnameLen > maxLenForPage3Fullname) { fullname =
					 * truncateNameToLength(firstname, maxLenForPage3Fullname);
					 * } else { int balance = maxLenForPage3Fullname -
					 * firstnameLen; fullname = firstname +
					 * " "+truncateNameToLength(surname, balance); } } else {
					 * int balance = maxLenForPage3Fullname - firstnameLen -
					 * surnameLen -1; fullname = firstname +
					 * " "+truncateNameToLength(middlename, balance) +
					 * " "+surname; }
					 */
					// change to new format: last, first middle
					if (surnameLen + firstnameLen + 2 > maxLenForPage3Fullname) {
						if (surnameLen > maxLenForPage3Fullname) {
							fullname = truncateNameToLength(surname,
									maxLenForPage3Fullname, cutToLastSpace);
						} else {
							int balance = maxLenForPage3Fullname - surnameLen
									- 2;
							fullname = surname
									+ ", "
									+ truncateNameToLength(firstname, balance,
											cutToLastSpace);
						}
					} else {
						int balance = maxLenForPage3Fullname - surnameLen - 2
								- firstnameLen - 1;
						fullname = surname
								+ ", "
								+ firstname
								+ " "
								+ truncateNameToLength(middlename, balance,
										cutToLastSpace);
					}
				}

				// For Chip Encoding limit
				if (surnameLen + firstnameLen + middlenameLen > maxLenForChipFullname) {
					exceeded = true;
					// 19 Aug 2016 (chris) commented code - Chip and printed is
					// different length limit
					// if (surnameLen > maxLenForNamepage) {
					// surname = truncateNameToLength(surname,
					// maxLenForNamepage, cutToLastSpace);
					// surnameLen = StringUtils.length(surname);
					// }
					// if (firstnameLen > maxLenForNamepage) {
					// firstname = truncateNameToLength(firstname,
					// maxLenForNamepage, cutToLastSpace);
					// firstnameLen = StringUtils.length(firstname);
					// }
					// if (middlenameLen > maxLenForNamepage) {
					// middlename = truncateNameToLength(middlename,
					// maxLenForNamepage, cutToLastSpace);
					// middlenameLen = StringUtils.length(middlename);
					// }

					String surnameChip = surname;
					String firstnameChip = firstname;
					String middlenameChip = middlename;
					if (surnameLen + firstnameLen > maxLenForChipFullname) { // if
																				// surname+firstname
																				// >
																				// 96
																				// then
																				// set
																				// middlename
																				// to
																				// empty
						middlenameChip = "";
						if (surnameLen > maxLenForChipFullname) {
							firstnameChip = "";
							surnameChip = truncateNameToLength(surname,
									maxLenForChipFullname, cutToLastSpace);
						} else {
							int balance = maxLenForChipFullname - surnameLen;
							firstnameChip = truncateNameToLength(firstname,
									balance, cutToLastSpace);
						}
					} else { // else then keep surname, firstname and cut
								// middlename
						int balance = maxLenForChipFullname - firstnameLen
								- surnameLen;
						middlenameChip = truncateNameToLength(middlename,
								balance, cutToLastSpace);
					}
					nicTransactionDBO.getNicRegistrationData().setSurnameLine2(
							surnameChip);
					nicTransactionDBO.getNicRegistrationData()
							.setFirstnameLine2(firstnameChip);
					nicTransactionDBO.getNicRegistrationData()
							.setMiddlenameLine2(middlenameChip);
				} else { // within chip limit just keep it as it is.
					nicTransactionDBO.getNicRegistrationData().setSurnameLine2(
							surname);
					nicTransactionDBO.getNicRegistrationData()
							.setFirstnameLine2(firstname);
					nicTransactionDBO.getNicRegistrationData()
							.setMiddlenameLine2(middlename);
				}

				if (surnameLen > maxLenForNamepage) { // exceed page 2 limit -
														// surname
					exceeded = true;
					surname = truncateNameToLength(surname, maxLenForNamepage,
							cutToLastSpace);
					surnameLen = StringUtils.length(surname);
				}
				if (firstnameLen > maxLenForNamepage) { // exceed page 2 limit -
														// firstname
					exceeded = true;
					firstname = truncateNameToLength(firstname,
							maxLenForNamepage, cutToLastSpace);
					firstnameLen = StringUtils.length(firstname);
				}
				if (middlenameLen > maxLenForNamepage) { // exceed page 2 limit
															// - middlename
					exceeded = true;
					middlename = truncateNameToLength(middlename,
							maxLenForNamepage, cutToLastSpace);
					middlenameLen = StringUtils.length(middlename);
				}

				if (exceeded) {
					if (!StringUtils.equals(surname, nicTransactionDBO
							.getNicRegistrationData().getSurnameFull())) {
						nicTransactionDBO.getNicRegistrationData()
								.setSurnameLenExceedFlag(true);
						nicTransactionDBO.getNicRegistrationData()
								.setSurnameLine1(surname);
					}
					if (!StringUtils.equals(firstname, nicTransactionDBO
							.getNicRegistrationData().getFirstnameFull())) {
						nicTransactionDBO.getNicRegistrationData()
								.setFirstnameLenExceedFlag(true);
						nicTransactionDBO.getNicRegistrationData()
								.setFirstnameLine1(firstname);
					}
					if (!StringUtils.equals(middlename, nicTransactionDBO
							.getNicRegistrationData().getMiddlenameFull())) {
						nicTransactionDBO.getNicRegistrationData()
								.setMiddlenameLenExceedFlag(true);
						nicTransactionDBO.getNicRegistrationData()
								.setMiddlenameLine1(middlename);
					}
					nicTransactionDBO.getNicRegistrationData()
							.setPrintRemarks2(fullname);
					// logger.debug("[applyNameTruncation] original  - transactionId:{}, firstname:{}, middlename:{}, surname:{}",
					// new Object[] {nicTransactionDBO.getTransactionId(),
					// nicTransactionDBO.getNicRegistrationData().getFirstnameFull(),
					// nicTransactionDBO.getNicRegistrationData().getMiddlenameFull(),
					// nicTransactionDBO.getNicRegistrationData().getSurnameFull()});
					// logger.debug("[applyNameTruncation] truncated - transactionId:{}, firstname:{}, middlename:{}, surname:{}, fullname:{}",
					// new Object[] {nicTransactionDBO.getTransactionId(),
					// firstname, middlename, surname, fullname});
				}
			} catch (Exception e) {
				logger.error("Exception in applyNameTruncation()", e);
				return new BaseModelSingle<NicTransaction>(null, false,
						CreateMessageException.createMessageException(e)
								+ " - applyNameTruncation - "
								+ nicTransactionDBO.getTransactionId()
								+ " - thất bại.");
			}
		}
		return new BaseModelSingle<NicTransaction>(nicTransactionDBO, true,
				null);
	}

	private String truncateNameToLength(String name, int maxLen,
			boolean cutToLastSpace) {
		String result = StringUtils.substring(name, 0, maxLen);
		String next = StringUtils.substring(name, maxLen, maxLen + 1);

		if (cutToLastSpace && !StringUtils.isWhitespace(next)
				&& StringUtils.indexOf(result, " ") != -1) {
			int len = StringUtils.length(result);
			do {
				String last = StringUtils.substring(result, len - 1, len);
				boolean endWithSpace = StringUtils.isWhitespace(last);
				result = StringUtils.substring(result, 0, len - 1);
				len = StringUtils.length(result);
				String last2 = result.substring(len - 1, len);
				if (endWithSpace && !StringUtils.isWhitespace(last2)) {
					break;
				}
			} while (len > 0);
		} else { // to cut the last letter if it is a space.
			String last = StringUtils.substring(result, maxLen - 1, maxLen);
			if (StringUtils.isWhitespace(last)) {
				result = StringUtils.substring(result, 0, maxLen - 1);
				return truncateNameToLength(result, maxLen - 1, false);
			}
		}
		return result;
	}

	private boolean getParamAsBool(String scope, String name,
			boolean defaultValue) {
		boolean value = defaultValue;
		Parameters param = parametersDao
				.findById(new ParametersId(scope, name));
		if (param != null) {
			if (StringUtils.equalsIgnoreCase(param.getParaShortValue(), "Y")
					|| StringUtils.equalsIgnoreCase(param.getParaShortValue(),
							"true")) {
				value = true;
			} else if (StringUtils.equalsIgnoreCase(param.getParaShortValue(),
					"N")
					|| StringUtils.equalsIgnoreCase(param.getParaShortValue(),
							"false")) {
				value = false;
			}
		}
		return value;
	}

	private int getParamAsInt(String scope, String name, int defaultValue) {
		int value = defaultValue;
		Parameters param = parametersDao
				.findById(new ParametersId(scope, name));
		if (param != null && StringUtils.isNumeric(param.getParaShortValue())) {
			value = Integer.parseInt(param.getParaShortValue());
		}
		return value;
	}

	private List<NicFPData> convertFPDataList(NicTransaction tranDBO) {
		List<NicFPData> fpDataList = new ArrayList<NicFPData>();
		Map<Integer, Integer> fpqMap = new HashMap<Integer, Integer>();
		Map<Integer, String> fpIndMap = new HashMap<Integer, String>();

		if (tranDBO != null
				&& CollectionUtils.isNotEmpty(tranDBO
						.getNicTransactionAttachments())) {
			String transactionId = tranDBO.getTransactionId();
			NicRegistrationData nicRegDataDBO = tranDBO
					.getNicRegistrationData();
			if (nicRegDataDBO != null) {
				// check fp quality and fp indicator
				String fpQuality = nicRegDataDBO.getFpQuality();
				String fpIndicator = nicRegDataDBO.getFpIndicator();

				// check fp quality
				// 1-99,2-99,3-99,4-99,5-99,6-99,7-99,8-99,9-99,10-99
				String[] fpQualityArray = StringUtils.split(fpQuality, ",");
				if (fpQualityArray != null) {
					for (String fpq : fpQualityArray) {
						if (StringUtils.contains(fpq, "-")
								&& StringUtils.split(fpq, "-").length == 2) {
							String fingerPosition = StringUtils.split(fpq, "-")[0];
							String quality = StringUtils.split(fpq, "-")[1];
							if (StringUtils.isNumeric(fingerPosition)
									&& StringUtils.isNumeric(quality)) {
								try {
									fpqMap.put(
											Integer.parseInt(fingerPosition),
											Integer.parseInt(quality));
								} catch (Exception e) {
								}
							}
						}
					}
				}

				// check fp indicator 1-N,2-N,3-N,4-N,5-N,6-N,7-N,8-N,9-A,10-N
				String[] fpIndArray = StringUtils.split(fpIndicator, ",");
				if (fpIndArray != null) { // 24 Jan 2014 (chris) - start
					for (String fpInd : fpIndArray) {
						if (StringUtils.contains(fpInd, "-")
								&& StringUtils.split(fpInd, "-").length == 2) {
							String fingerPosition = StringUtils.split(fpInd,
									"-")[0];
							String indicator = StringUtils.trim(StringUtils
									.split(fpInd, "-")[1]);
							if (StringUtils.isNumeric(fingerPosition)
									&& StringUtils.length(indicator) == 1) {
								try {
									fpIndMap.put(
											Integer.parseInt(fingerPosition),
											indicator);
								} catch (Exception e) {
								}
							}
						}
					}
				}
			}

			for (NicTransactionAttachment doc : tranDBO
					.getNicTransactionAttachments()) {
				try {
					if (StringUtils.equals(
							NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
							doc.getDocType())) {
						int position = Integer.parseInt(doc.getSerialNo());
						NicFPData fpData = new NicFPData(new NicFPDataId(
								transactionId, position));
						fpData.setSiteCode(tranDBO.getRegSiteCode());
						fpData.setDateOfApplication(tranDBO
								.getDateOfApplication());
						fpData.setCreateBy(tranDBO.getCreateBy());
						fpData.setCreateDatetime(tranDBO.getCreateDatetime());
						fpData.setFpIndicator(fpIndMap.get(position));
						fpData.setQuality(fpqMap.get(position));
						fpData.setDocId(doc.getTransactionDocId());
						fpDataList.add(fpData);
					}
				} catch (Exception e) {
					logger.info(
							"[convertFPDataList] error when getting FPData for transactionId:{}, FP-{}",
							transactionId, doc.getSerialNo());
					if (logger.isDebugEnabled()) {
						logger.error("[convertFPDataList] Exception ", e);
					}
				}
			}
		}
		return fpDataList;
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public String checkDuplicateApplication(String transactionId, String nin,
			String transactionType, String transactionSubtype, String refTxnId)
			throws TransactionServiceException {
		String result = "";
		// try {
		// if (StringUtils.equals(TRANSACTION_TYPE_REGISTRATION,
		// transactionType) ||
		// StringUtils.equals(TRANSACTION_TYPE_CONVERSION, transactionType)) {
		// String transactionIdHit = null;
		// boolean duplicate = false;
		// List<NicTransaction> resultList = findAllByNin(nin);
		// if (CollectionUtils.isNotEmpty(resultList)) {
		// for (NicTransaction tempTransaction: resultList) {
		// String tempTransactionStatus =
		// tempTransaction.getTransactionStatus();
		// if ( (StringUtils.equals(tempTransaction.getTransactionType(),
		// TRANSACTION_TYPE_REGISTRATION)
		// || StringUtils.equals(tempTransaction.getTransactionType(),
		// TRANSACTION_TYPE_CONVERSION))
		// && !StringUtils.equals(tempTransaction.getTransactionId(),
		// transactionId)) {
		// //to check transaction status
		// if
		// (!StringUtils.equals(tempTransactionStatus,TRANSACTION_STATUS_RIC_CARD_REJECTED)
		// &&
		// !StringUtils.equals(tempTransactionStatus,TRANSACTION_STATUS_RIC_CARD_DEACTIVATED)
		// &&
		// !StringUtils.equals(tempTransactionStatus,
		// TRANSACTION_STATUS_INVENTORY_CARD_RECEIVED) &&
		// !StringUtils.equals(tempTransactionStatus,
		// TRANSACTION_STATUS_INVENTORY_CARD_DESTROYED) &&
		// !StringUtils.equals(tempTransactionStatus, TRANSACTION_STATUS_VOID)
		// && //07 Apr 2014 (chris) to support void
		// !StringUtils.equals(tempTransactionStatus,
		// TRANSACTION_STATUS_NIC_REJECTED) &&
		// !StringUtils.equals(tempTransactionStatus,
		// TRANSACTION_STATUS_NIC_CANCELLED) &&
		// !StringUtils.equals(tempTransactionStatus,
		// TRANSACTION_STATUS_PERSO_REJECTED)) {
		// if (StringUtils.equalsIgnoreCase(TRANSACTION_SUBTYPE_RE_REG,
		// transactionSubtype)) {
		// if (StringUtils.equalsIgnoreCase(refTxnId, transactionIdHit)) {
		// logger.warn("[checkDuplicateApplication]: Re-reg for {}, non duplication case.",
		// transactionIdHit);
		// continue;
		// }
		// }
		// duplicate = true;
		// if (StringUtils.isNotBlank(transactionIdHit))
		// transactionIdHit += ","+tempTransaction.getTransactionId();
		// else
		// transactionIdHit = tempTransaction.getTransactionId();
		// }
		// //23 May 2014 (chris) to support terminated card to re-reg.
		// else {
		// List<NicIssuanceData> nicIssuanceDataList =
		// nicIssuanceDataDao.findByTransactionId(tempTransaction.getTransactionId());
		// for (NicIssuanceData nicIssuanceData: nicIssuanceDataList) {
		// if (StringUtils.isNotBlank(nicIssuanceData.getCcn())) {
		// if (StringUtils.equals(nicIssuanceData.getCardStatus(),
		// NicChangeStatusLog.CARD_STATUS_ACTIVE)) {
		// duplicate = true;
		// if (StringUtils.isNotBlank(transactionIdHit))
		// transactionIdHit += ","+tempTransaction.getTransactionId();
		// else
		// transactionIdHit = tempTransaction.getTransactionId();
		// logger.info("[checkDuplicateApplication]: invalid for RE_REG transaction, prev transaction [{}] is [{}], card [{}] is [{}]",
		// new String[] {tempTransaction.getTransactionId(),
		// tempTransactionStatus, nicIssuanceData.getCcn(),
		// nicIssuanceData.getCardStatus()});
		// }
		// }
		// }
		// }
		// //23 May 2014 (chris) to support terminated card to re-reg - end.
		// }
		// }
		// if (duplicate) {
		// result =
		// "Active Application(s) found in NIC System (Transaction ID: "+transactionIdHit+") with NIN: "+nin+". Please report to Command Centre.";
		// }
		// }
		// }
		// } catch (DaoException e) {
		// throw new TransactionServiceException(e);
		// }
		return result;
	}

	private boolean validateTransaction(MainTransaction dto)
			throws TransactionServiceException {
		StringBuffer message = new StringBuffer();
		boolean valid = true;
		String errorCode = TX_UPLOAD_ERROR_CODE_DATA_VALIDATION_FAILED;
		// to check checksum
		if (StringUtils.isBlank(dto.getChecksum())) {
			logger.info("[validateTransaction] [{}] checksum is null",
					new Object[] { dto.getTransactionID() });
			// enforce checksum, allow only when RIC_SUPPORT validated the
			// transaction.
			if (!StringUtils.equals(dto.getUpdateBy(), "RIC_SUPPORT")) {
				// valid = false;
			}
			errorCode = TX_UPLOAD_ERROR_CODE_CHECKSUM_VERIFICATION_FAILED;
			message.append("checksum cannot be null, ");
		} else {
			// String inputChecksum = dto.getChecksum();
			// String computedChecksum = "";
			// try {
			// computedChecksum =
			// checksumGenerator.computeCheckSum(transactionDTO);
			// boolean matchChecksum = StringUtils.equalsIgnoreCase(
			// inputChecksum, computedChecksum);
			// logger.info(
			// "[validateTransaction] [{}] inputChecksum[{}] computedChecksum[{}] match:{}",
			// new Object[] { transactionDTO.getTransactionID(),
			// inputChecksum, computedChecksum, matchChecksum });
			// if (!matchChecksum) {
			// //enforce checksum, allow only hen RIC_SUPPORT validated the
			// transaction.
			// if (!StringUtils.equals(transactionDTO.getUpdateBy(),
			// "RIC_SUPPORT")) {
			// valid = false;
			// }
			// errorCode = TX_UPLOAD_ERROR_CODE_CHECKSUM_VERIFICATION_FAILED;
			// message.append("invalid checksum received, ");
			// }
			// } catch (Exception e) {
			// //throw new TransactionServiceException(e);
			// logger.error("[validateTransaction] [{}] unable to generate checksum:{}",
			// new Object[] { transactionDTO.getTransactionID(), e.getMessage()
			// }, e);
			// }
		}

		// validate collection site & registration site
		if (StringUtils.isBlank(dto.getRegSiteCode())) {
			valid = false;
			message.append("registration site code cannot be null, ");
		} else {
			try {
				SiteRepository site = siteService.getSiteRepoById(dto
						.getRegSiteCode());
				logger.info(
						"[{}] site: {}, active: [{}] ",
						new Object[] { dto.getTransactionID(), site,
								(site != null ? site.getActiveIndicator() : "") });
				if (site == null) {
					valid = false;
					message.append("invalid reg site code : "
							+ dto.getRegSiteCode() + ", ");
				} else {
					if (!"Y".equalsIgnoreCase(site.getActiveIndicator())) {
						valid = false;
						message.append("registration site code : "
								+ dto.getRegSiteCode() + " is not allowed, ");
					}
				}
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isBlank(dto.getIssSiteCode())) {
			valid = false;
			message.append("issuance site code cannot be null, ");
		} else {
			// CodeValues codeValue =
			// codesService.getCodeValueByIdName(CODE_ID_SITE_CODE,
			// dto.getIssSiteCode());
			// if (codeValue == null) {
			// valid = false;
			// message.append("invalid iss site code : " +
			// dto.getIssSiteCode()+", ");
			// }
			try {
				SiteRepository site = siteService.getSiteRepoById(dto
						.getIssSiteCode());
				if (site == null) {
					valid = false;
					message.append("invalid iss site code : "
							+ dto.getIssSiteCode() + ", ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// validate passportType, priority, validityPeriod, issuingAuthority
		if (StringUtils.isBlank(dto.getPassportType())) {
			valid = false;
			message.append("passportType cannot be null, ");
		} else { // [cw] 26 Apr 2016 - block non - regular passport.
			CodeValues codeValue = codesService.getCodeValueByIdName(
					Codes.PASSPORT_TYPE, dto.getPassportType());
			if (codeValue == null
					|| Boolean.TRUE.equals(codeValue.getDeleteFlag())) {
				valid = false;
				message.append("invalid passportType : "
						+ dto.getPassportType() + ", ");
			}
		}
		if (dto.getPriority() == null) {
			valid = false;
			message.append("priority cannot be null, ");
		}

		if (dto.getValidatyPeriod() == null
				|| dto.getValidatyPeriod().intValue() == 0) {
			valid = false;
			message.append("validityPeriod cannot be null or zero, ");
		}

		if (StringUtils.isBlank(dto.getIssuingAuthority())) {
			valid = false;
			message.append("issuingAuthority cannot be null, ");
		}

		// validate estDateOfCollection
		if (StringUtils.equalsIgnoreCase(TRANSACTION_TYPE_REGISTRATION,
				dto.getTransactionType())
				|| StringUtils.equalsIgnoreCase(TRANSACTION_TYPE_REPLACEMENT,
						dto.getTransactionType())
				|| StringUtils.equalsIgnoreCase(TRANSACTION_TYPE_LOST,
						dto.getTransactionType())) {
			if (dto.getEstDateOfCollection() == null) {
				valid = false;
				message.append("estDateOfCollection cannot be null, ");
			}
		}

		if (dto.getRegistrationData() == null) {
			valid = false;
			message.append("registration data dto cannot be null, ");
		} else {
			// [cw] 2016 May 30 - validate max length for page 3 - start
			if (StringUtils.isNotBlank(dto.getRegistrationData()
					.getPrintRemark1())
					|| StringUtils.isNotBlank(dto.getRegistrationData()
							.getPrintRemark2())
					|| StringUtils.isNotBlank(dto.getRegistrationData()
							.getOccupationDesc())) {
				int maxLenPerLineForPage3 = 45;
				Parameters paramMaxLenForPage3 = parametersDao
						.findById(new ParametersId(PARA_SCOPE_PERSO,
								PARA_NAME_MAX_LEN_PER_LINE_PG3));
				if (paramMaxLenForPage3 != null
						&& StringUtils.isNumeric(paramMaxLenForPage3
								.getParaShortValue())) {
					maxLenPerLineForPage3 = Integer
							.parseInt(paramMaxLenForPage3.getParaShortValue());
				}
				// limitation
				if (StringUtils.isNotBlank(dto.getRegistrationData()
						.getPrintRemark1())) {
					int maxLinesForLimitation = 0;
					Parameters param = parametersDao.findById(new ParametersId(
							PARA_SCOPE_PERSO,
							PARA_NAME_MAX_LINES_PG3_LIMITATION));
					if (param != null
							&& StringUtils.isNumeric(param.getParaShortValue())) {
						maxLinesForLimitation = Integer.parseInt(param
								.getParaShortValue());
					}
					if (maxLinesForLimitation >= 1) {
						String[] limitations = StringUtils.split(dto
								.getRegistrationData().getPrintRemark1(),
								"\r\n");
						if (limitations != null) {
							if (limitations.length > maxLinesForLimitation) {
								valid = false;
								message.append("limitations (PrintRemark1) exceeded the maximum number of lines, ");
							}
							for (int i = 0; i < limitations.length; i++) {
								String line = limitations[i];
								if (StringUtils.length(line) > maxLenPerLineForPage3) {
									valid = false;
									message.append("limitations (PrintRemark1) line ["
											+ (i + 1)
											+ "] exceeded the limits,  ");
								}
							}
						}
					} else {
						int maxLenForLimitation = 168; // 56 * 3
						Parameters param2 = parametersDao
								.findById(new ParametersId(PARA_SCOPE_WS,
										PARA_NAME_MAX_LEN_PG3_LIMITATION)); // 29
																			// Jul
																			// 2016
																			// (chris):
																			// change
																			// scope
						if (param2 != null
								&& StringUtils.isNumeric(param2
										.getParaShortValue())) {
							maxLenForLimitation = Integer.parseInt(param2
									.getParaShortValue());
						}
						if (StringUtils.length(dto.getRegistrationData()
								.getPrintRemark1()) > maxLenForLimitation) {
							valid = false;
							message.append("limitations (PrintRemark1) exceeded the limits,  ");
						}
					}
				}

				// position
				if (StringUtils.isNotBlank(dto.getRegistrationData()
						.getOccupationDesc())) {
					int maxLinesForPosition = 0;
					Parameters param = parametersDao
							.findById(new ParametersId(PARA_SCOPE_PERSO,
									PARA_NAME_MAX_LINES_PG3_POSITION));
					if (param != null
							&& StringUtils.isNumeric(param.getParaShortValue())) {
						maxLinesForPosition = Integer.parseInt(param
								.getParaShortValue());
					}
					if (maxLinesForPosition >= 1) {
						int maxLenPerLineForPosition = 32;
						Parameters paramLen = parametersDao
								.findById(new ParametersId(PARA_SCOPE_PERSO,
										PARA_NAME_MAX_LEN_PER_LINE_PG3_POSITION));
						if (paramLen != null
								&& StringUtils.isNumeric(paramLen
										.getParaShortValue())) {
							maxLenPerLineForPosition = Integer
									.parseInt(paramLen.getParaShortValue());
						}
						String[] positions = StringUtils.split(dto
								.getRegistrationData().getOccupationDesc(),
								"\r\n");
						if (positions != null) {
							if (positions.length > maxLinesForPosition) {
								valid = false;
								message.append("position (OccupationDesc) exceeded the maximum number of lines, ");
							}
							for (int i = 0; i < positions.length; i++) {
								String line = positions[i];
								if (StringUtils.length(line) > maxLenPerLineForPosition) {
									valid = false;
									message.append("position (OccupationDesc) line ["
											+ (i + 1)
											+ "] exceeded the limits,  ");
								}
							}
						}
					} else {
						int maxLenForPosition = 288;
						Parameters param2 = parametersDao
								.findById(new ParametersId(PARA_SCOPE_WS,
										PARA_NAME_MAX_LEN_PG3_POSITION)); // 29
																			// Jul
																			// 2016
																			// (chris):
																			// change
																			// scope
						if (param2 != null
								&& StringUtils.isNumeric(param2
										.getParaShortValue())) {
							maxLenForPosition = Integer.parseInt(param2
									.getParaShortValue());
						}
						if (StringUtils.length(dto.getRegistrationData()
								.getOccupationDesc()) > maxLenForPosition) {
							valid = false;
							message.append("position (OccupationDesc) exceeded the limits,  ");
						}
					}
				}

				// fullname
				if (StringUtils.isNotBlank(dto.getRegistrationData()
						.getPrintRemark2())) {
					int maxLinesForFullname = 0;
					Parameters param = parametersDao
							.findById(new ParametersId(PARA_SCOPE_PERSO,
									PARA_NAME_MAX_LINES_PG3_FULLNAME));
					if (param != null
							&& StringUtils.isNumeric(param.getParaShortValue())) {
						maxLinesForFullname = Integer.parseInt(param
								.getParaShortValue());
					}
					if (maxLinesForFullname >= 1) {
						String[] fullnames = StringUtils.split(dto
								.getRegistrationData().getPrintRemark2(),
								"\r\n");
						if (fullnames != null) {
							if (fullnames.length > maxLinesForFullname) {
								valid = false;
								message.append("fullname (PrintRemark2) exceeded the maximum number of lines, ");
							}
							for (int i = 0; i < fullnames.length; i++) {
								String line = fullnames[i];
								if (StringUtils.length(line) > maxLenPerLineForPage3) {
									valid = false;
									message.append("fullname (PrintRemark2) line ["
											+ (i + 1)
											+ "] exceeded the limits,  ");
								}
							}
						}
					} else {
						// [cw] 2016 Jun 06 - validate max length for page 3
						// full name auto wrap
						int maxLenForFullname = 224; // 56*4;
						Parameters param2 = parametersDao
								.findById(new ParametersId(PARA_SCOPE_WS,
										PARA_NAME_MAX_LEN_PG3_FULLNAME)); // 29
																			// Jul
																			// 2016
																			// (chris):
																			// change
																			// scope
						if (param2 != null
								&& StringUtils.isNumeric(param2
										.getParaShortValue())) {
							maxLenForFullname = Integer.parseInt(param2
									.getParaShortValue());
						}
						if (StringUtils.length(dto.getRegistrationData()
								.getPrintRemark2()) > maxLenForFullname) {// 29
																			// Jul
																			// 2016
																			// (chris):
																			// bugfix
																			// for
																			// wrong
																			// field
																			// checking
							valid = false;
							message.append("fullname (PrintRemark2) exceeded the limits,  ");
						}
					}
				}
			}
			// [cw] 2016 May 30 - validate max length for page 3 - end

			// 08 Nov 2013 (chris): start
			if (dto.getRegistrationData().getPersonDetail() == null) {
				valid = false;
				message.append("person detail cannot be null, ");
			} else {
				PersonDetail personDetail = dto.getRegistrationData()
						.getPersonDetail();
				// validate surname cannot be null
				// if (StringUtils.isBlank(personDetail.getSurnameFull())) {
				// valid = false;
				// message.append("surname cannot be null, ");
				// }
				// else
				// [cw] 2016 May 30 - validate max length of names - start
				{
					int maxLenForFullname = 96;
					Parameters paramMaxLenForFullNames = parametersDao
							.findById(new ParametersId(PARA_SCOPE_WS,
									PARA_NAME_MAX_LEN_FULLNAME)); // 29 Jul 2016
																	// (chris):
																	// change
																	// scope
																	// //CHIP
																	// LIMIT FOR
																	// DB, allow
																	// truncation
					if (paramMaxLenForFullNames != null
							&& StringUtils.isNumeric(paramMaxLenForFullNames
									.getParaShortValue())) {
						maxLenForFullname = Integer
								.parseInt(paramMaxLenForFullNames
										.getParaShortValue());
					}
					int maxLenForNamepage = 50;
					Parameters paramMaxLenForNamepage = parametersDao
							.findById(new ParametersId(PARA_SCOPE_WS,
									PARA_NAME_MAX_LEN_NAMEPAGE)); // 29 Jul 2016
																	// (chris):
																	// change
																	// scope
																	// //PAGE
																	// LIMIT FOR
																	// DB, allow
																	// truncation
					if (paramMaxLenForNamepage != null
							&& StringUtils.isNumeric(paramMaxLenForNamepage
									.getParaShortValue())) {
						maxLenForNamepage = Integer
								.parseInt(paramMaxLenForNamepage
										.getParaShortValue());
					}
					int surnameLen = StringUtils.length(personDetail
							.getSurnameFull());
					int firstnameLen = StringUtils.length(personDetail
							.getFirstnameFull());
					int middlenameLen = StringUtils.length(personDetail
							.getMiddlenameFull());

					if (surnameLen > maxLenForNamepage) {
						valid = false;
						message.append("surname line exceeded the limits, ");
					}
					if (firstnameLen > maxLenForNamepage) {
						valid = false;
						message.append("firstname line exceeded the limits, ");
					}
					if (middlenameLen > maxLenForNamepage) {
						valid = false;
						message.append("middlename line exceeded the limits, ");
					}

					if (surnameLen + firstnameLen + middlenameLen + 2 > maxLenForFullname) { // 29
																								// Jul
																								// 2016
																								// (chris):
																								// add
																								// length
																								// for
																								// space
						valid = false;
						message.append("full name exceeded the limits, ");
					}
					logger.info("[{}] full name length: {} ", new Object[] {
							dto.getTransactionID(),
							surnameLen + firstnameLen + middlenameLen });
				}
				// [cw] 2016 May 30 - validate max length of names - end

				// [cw] 2016 Jun 03 - validate aka - aliasname, place of birth -
				// start
				if (StringUtils.isNotBlank(dto.getRegistrationData()
						.getPersonDetail().getAliasname())) {
					int maxLinesForAka = 0;
					int maxLenForPage3 = 56;
					Parameters param = parametersDao.findById(new ParametersId(
							PARA_SCOPE_PERSO, PARA_NAME_MAX_LINES_PG3_AKA));
					if (param != null
							&& StringUtils.isNumeric(param.getParaShortValue())) {
						maxLinesForAka = Integer.parseInt(param
								.getParaShortValue());
					}
					if (maxLinesForAka >= 1) {
						Parameters paramMaxLenForPage3 = parametersDao
								.findById(new ParametersId(PARA_SCOPE_PERSO,
										PARA_NAME_MAX_LEN_PER_LINE_PG3));
						if (paramMaxLenForPage3 != null
								&& StringUtils.isNumeric(paramMaxLenForPage3
										.getParaShortValue())) {
							maxLenForPage3 = Integer
									.parseInt(paramMaxLenForPage3
											.getParaShortValue());
						}
						String[] akaLine = StringUtils.split(dto
								.getRegistrationData().getPersonDetail()
								.getAliasname(), "\r\n");
						if (akaLine != null) {
							if (akaLine.length > maxLinesForAka) {
								valid = false;
								message.append("aka (Aliasname) exceeded the maximum number of lines, ");
							}
							for (int i = 0; i < akaLine.length; i++) {
								String line = akaLine[i];
								if (StringUtils.length(line) > maxLenForPage3) {
									valid = false;
									message.append("aka (Aliasname) line ["
											+ (i + 1)
											+ "] exceeded the limits,  ");
								}
							}
						}
					} else {
						// [cw] 2016 Jun 06 - validate max length for page 3 aka
						// auto wrap
						int maxLenForAka = 112; // 56*2;
						Parameters param2 = parametersDao
								.findById(new ParametersId(PARA_SCOPE_WS,
										PARA_NAME_MAX_LEN_PG3_AKA)); // 29 Jul
																		// 2016
																		// (chris):
																		// change
																		// scope
						if (param2 != null
								&& StringUtils.isNumeric(param2
										.getParaShortValue())) {
							maxLenForAka = Integer.parseInt(param2
									.getParaShortValue());
						}
						if (StringUtils.length(dto.getRegistrationData()
								.getPersonDetail().getAliasname()) > maxLenForAka) {
							valid = false;
							message.append("aka (Aliasname) exceeded the limits,  ");
						}
					}
				}

				if (StringUtils.isNotBlank(dto.getRegistrationData()
						.getPersonDetail().getPlaceOfBirth())) {
					int maxLenForPlaceOfBirth = 42;
					Parameters param = parametersDao
							.findById(new ParametersId(PARA_SCOPE_PERSO,
									PARA_NAME_MAX_LEN_PLACE_OF_BIRTH));
					if (param != null
							&& StringUtils.isNumeric(param.getParaShortValue())) {
						maxLenForPlaceOfBirth = Integer.parseInt(param
								.getParaShortValue());
					}
					int pobLen = StringUtils.length(personDetail
							.getPlaceOfBirth());

					if (pobLen > maxLenForPlaceOfBirth) {
						valid = false;
						message.append("place of birth exceeded the limits, ");
					}
				}
				// [cw] 2016 Jun 03 - validate aka - aliasname, place of birth -
				// end

				// validate dob cannot be null
				if (personDetail.getDateOfBirth() == null
						&& StringUtils.isBlank(personDetail.getApproxDOB())) {
					valid = false;
					message.append("date of birth cannot be null, ");
				} else {
					// [cw] 2016 May 13 - validate max number of days range for
					// Date of Birth - start
					int maxNumYearForDOB = 150;
					Parameters paramMaxNumDaysForPrinting = parametersDao
							.findById(new ParametersId(PARA_SCOPE_APPLICATION,
									PARA_NAME_MAX_NUM_YEAR_FOR_VALID_DOB));
					if (paramMaxNumDaysForPrinting != null
							&& StringUtils.isNumeric(paramMaxNumDaysForPrinting
									.getParaShortValue())) {
						maxNumYearForDOB = Integer
								.parseInt(paramMaxNumDaysForPrinting
										.getParaShortValue());
					}
					Calendar cal = Calendar.getInstance();
					int systemYear = cal.get(Calendar.YEAR);
					Date systemDate = cal.getTime();
					if (personDetail.getDateOfBirth() != null) {
						int diff = DateUtil.dateDiff(
								personDetail.getDateOfBirth(), systemDate);
						logger.info("[{}] DateOfBirth: {} - diff/365: {} ",
								new Object[] { dto.getTransactionID(),
										personDetail.getDateOfBirth(),
										diff / 365 });
						if (diff > maxNumYearForDOB * 365) {
							logger.warn(
									"Invalid DateOfBirth: {} for transaction: {}",
									personDetail.getDateOfBirth(),
									dto.getTransactionID());
							valid = false;
							message.append("date of birth is invalid date, ");
						}
					} else if (StringUtils.isNotBlank(personDetail
							.getApproxDOB())) {
						String YOB = StringUtils.substring(
								personDetail.getApproxDOB(), 0, 4);
						if (StringUtils.isNumeric(YOB)
								&& !StringUtils.equals("0000", YOB)) {
							int yearOfBirth = Integer.parseInt(YOB);
							int yearDiff = systemYear - yearOfBirth;
							logger.info(
									"[{}] ApproxDOB: {}, YOB: {} - yearDiff: {} ",
									new Object[] { dto.getTransactionID(),
											personDetail.getApproxDOB(),
											yearOfBirth, yearDiff });
							if (yearDiff > maxNumYearForDOB) {
								logger.warn(
										"Invalid DateOfBirth: {} for transaction: {}",
										personDetail.getApproxDOB(),
										dto.getTransactionID());
								valid = false;
								message.append("date of birth is invalid date, ");
							}
						}
					}
					// [cw] 2016 May 13 - validate max number of days range for
					// Date of Birth - end
				}
				// validate gender cannot be null
				// /Tạm đóng để đưa dữ liệu LOST/DAMEGE
				if (StringUtils.isBlank(personDetail.getGender())) {
					/*
					 * valid = false; message.append("gender cannot be null, ");
					 */
				}
				// validate dob cannot be null
				if (StringUtils.isBlank(personDetail.getPrintDOB())) {
					valid = false;
					message.append("dob cannot be null, ");
				}

				// [cw] 2016 May 30 - validate nationality for nat and natFull -
				// start
				if (StringUtils.isBlank(personDetail.getNationality())) {
					// /Tạm đóng để đưa dữ liệu LOST/DAMEGE
					/*
					 * valid = false;
					 * message.append("nationality cannot be null, ");
					 */
				} else {
					CodeValues codeValue = codesService.getCodeValueByIdName(
							Codes.NATIONALITY, personDetail.getNationality());
					if (codeValue == null
							|| Boolean.TRUE.equals(codeValue.getDeleteFlag())) {
						valid = false;
						message.append("invalid nationality : "
								+ personDetail.getNationality() + ", ");
					}
				}
				// [cw] 2016 May 30 - validate nationality for nat and natFull -
				// end
			}

			// validate fingerprint position
			FingerprintInfo fingerprintInfo = dto.getRegistrationData()
					.getFingerprintInfo();
			if (fingerprintInfo != null) {
				List<Integer> tplFingerPositionList = new ArrayList<Integer>();
				if (fingerprintInfo.getCmlafTemplate() != null
						&& StringUtils.isNotBlank(new String(fingerprintInfo
								.getCmlafTemplate()))) { // [CW] 2016 Apr 20
					logger.debug(
							"[{}] in validateTransaction is failed, fingerprintInfo.getCmlafTemplate()!=null",
							new Object[] { dto.getTransactionID() });
					try {
						String xml = new String(
								fingerprintInfo.getCmlafTemplate());
						FmsTemplate fmsTemplate = xmlConvertor.getPayload(xml);
						if (fmsTemplate != null) {
							if (CollectionUtils.isNotEmpty(fmsTemplate
									.getFingerprintSections())) {
								// check first fingerprint sections only.
								FingerprintSection fs = fmsTemplate
										.getFingerprintSections().get(0);
								if (CollectionUtils.isNotEmpty(fs
										.getFingerprintTemplates())) {
									for (FingerprintTemplate fpTemplate : fs
											.getFingerprintTemplates()) {
										if (fpTemplate.getFingerprintNo() != null
												&& StringUtils
														.isNotBlank(fpTemplate
																.getFingerprintMinutia())) {
											tplFingerPositionList
													.add(fpTemplate
															.getFingerprintNo());
										}
									}
								}
							}
						}
					} catch (Exception e) {
						logger.error("Error when getting payload from xml.");
					}
				} else {
					logger.info(
							"[{}] in validateTransaction is failed, fingerprintInfo.getCmlafTemplate()==null",
							new Object[] { dto.getTransactionID() });
					boolean hasFPwithoutTPL = false;
					if (CollectionUtils.isNotEmpty(fingerprintInfo
							.getFingerprints())) {
						for (Fingerprint fp : fingerprintInfo.getFingerprints()) {
							if (StringUtils.isNumeric(fp
									.getFingerprintPosition())
									&& fp.getFingerprintData() != null) {
								hasFPwithoutTPL = true;
							}
						}
					}
					if (hasFPwithoutTPL) {
						valid = false;
						message.append("Fingerprint Templates is missing - TPL, ");
					}
				}
				List<Integer> fpFingerPositionList = new ArrayList<Integer>();
				if (CollectionUtils.isNotEmpty(fingerprintInfo
						.getFingerprints())) {
					for (Fingerprint fp : fingerprintInfo.getFingerprints()) {
						if (StringUtils.isNumeric(fp.getFingerprintPosition())) {
							int pos = Integer.parseInt(fp
									.getFingerprintPosition());
							fpFingerPositionList.add(new Integer(pos));
						}
					}
				}
				boolean positionsMatched = true;
				for (Integer tplPos : tplFingerPositionList) {
					boolean hasMatch = false;
					for (Integer fpPos : fpFingerPositionList) {
						if (tplPos.intValue() + 1 == fpPos.intValue()) {
							hasMatch = true;
							break;
						}
					}
					if (!hasMatch) {
						positionsMatched = false;
					}
				}
				if (!positionsMatched) {
					valid = false;
					message.append("Fingerprint Position conflicted - TPL : "
							+ tplFingerPositionList + ", ");
				}
			}

			// validate finger print image size

			// validate address length

			// validate SC logo

		}
		if (!valid) {
			logger.warn("[{}] validateTransaction is failed, reason:{}",
					new Object[] { dto.getTransactionID(), message.toString() });
			throw new TransactionServiceException(
					"Transaction Object validation failed, reason: "
							+ message.toString() + "[" + dto.getTransactionID()
							+ "]", errorCode);
		}
		return valid;
	}

	// @Override
	// @Transactional(rollbackFor = java.lang.Throwable.class, propagation =
	// Propagation.REQUIRED)
	// public List<NicTransaction> findAllByCcn(String ccn)
	// throws TransactionServiceException {
	// try {
	// List<NicTransaction> nicTransactionDBOList = new
	// ArrayList<NicTransaction>();
	//
	// List<String> transIds = nicIssuanceDataDao.findByCCn(ccn);
	// logger.info("[findAllByCcn][{}] transIds:{}", new Object[] { ccn,
	// transIds});
	// List<NicTransaction> resultList = dao.findAllByTransIdList(transIds);
	//
	// for (NicTransaction nicTransObj : resultList) {
	// nicTransactionDBOList.add(nicIssuanceDataDao.getTransactionIssuanceDatas(nicTransObj));
	// }
	// for (NicTransaction nicTransDBO : nicTransactionDBOList) {
	// logger.info("findAllByCcn({}) result:{}-{}", new Object[] { ccn,
	// nicTransDBO.getTransactionId(), nicTransDBO.getTransactionStatus()});
	// }
	// // nicTransactionDBOList.addAll(resultList);
	// return nicTransactionDBOList;
	// } catch (Exception e) {
	// throw new TransactionServiceException(e);
	// }
	// }

	// @Transactional(rollbackFor = java.lang.Throwable.class, propagation =
	// Propagation.REQUIRED)
	// public List<NicTransaction> findAllByNin(String nin)
	// throws TransactionServiceException {
	// try {
	// List<NicTransaction> nicTransactionDBOList = new
	// ArrayList<NicTransaction>();
	// NicTransaction exampleTrans = new NicTransaction();
	// // exampleTrans.setNin(nin);
	// List<NicTransaction> resultList = dao.findAll(exampleTrans);
	// nicTransactionDBOList.addAll(resultList);
	// for (NicTransaction nicTransDBO : nicTransactionDBOList) {
	// logger.info("findAllByNin({}) result:{}-{}", new Object[] { nin,
	// nicTransDBO.getTransactionId(), nicTransDBO.getTransactionStatus()});
	// }
	// return nicTransactionDBOList;
	// } catch (Exception e) {
	// throw new TransactionServiceException(e);
	// }
	// }

	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public List<NicTransaction> findAllByFields(String surname,
			String firstName, String middleName, String sex, String dob,
			boolean hasIssuanceData, boolean hasTransDocs, boolean hasRegData,
			boolean hasTransPayment) throws TransactionServiceException {
		logger.debug(
				"findAllByFields, surname:{}, firstName:{}, middleName:{}",
				new Object[] { surname, firstName, middleName, sex, dob });
		try {
			List<NicTransaction> nicTransactionDBOList = new ArrayList<NicTransaction>();

			List<NicTransaction> resultList = dao.findAllByFields(surname,
					firstName, middleName, sex, dob);
			for (NicTransaction nicTransDBO : resultList) {
				if (hasRegData) {
					nicTransDBO = this
							.getTransactionRegistrationData(nicTransDBO);
				}
				if (hasTransPayment) {
					nicTransDBO = this.getTransactionPayment(nicTransDBO);
				}
				if (hasTransDocs) {
					nicTransDBO = this.getTransactiondocuments(nicTransDBO);
				}
				if (hasIssuanceData) {
					nicTransDBO = this.getTransactionIssuanceDatas(nicTransDBO);
				}

				nicTransactionDBOList.add(nicTransDBO);
			}
			return nicTransactionDBOList;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public NicTransaction findById(String transId, boolean hasIssuanceData,
			boolean hasTransDocs, boolean hasRegData, boolean hasTransPayment)
			throws TransactionServiceException {

		try {
			NicTransaction nicTransDBO = null;

			if (StringUtils.isBlank(transId)) {
				throw new TransactionServiceException(
						"transaction Id cannot be empty.");
			}
			nicTransDBO = this.findById(transId);
			if (nicTransDBO != null) {
				if (hasRegData) {
					nicTransDBO = this
							.getTransactionRegistrationData(nicTransDBO);
				}
				if (hasTransPayment) {
					nicTransDBO = this.getTransactionPayment(nicTransDBO);
				}
				if (hasTransDocs) {
					nicTransDBO = this.getTransactiondocuments(nicTransDBO);
				}
				if (hasIssuanceData) {
					nicTransDBO = this.getTransactionIssuanceDatas(nicTransDBO);
				}
			}
			return nicTransDBO;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public void updateStatusByTransactionId(String transactionId,
			String status, String officerId, String workstationId)
			throws TransactionServiceException {
		try {
			logger.debug(
					"updateStatusByTransactionId, transactionId:{}, status:{} ",
					transactionId, status);
			this.dao.updateStatusByTransactionId(transactionId, status,
					officerId, workstationId);
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public List<NicTransactionLog> findAllLogsByNinWithExcludedTransactions(
			String nin, List<String> transactionIdList)
			throws TransactionServiceException {
		try {
			List<NicTransactionLog> nicTransactionLogDBOList = new ArrayList<NicTransactionLog>();
			nicTransactionLogDBOList = transactionLogDao
					.findAllByNinWithExcludedTransactions(nin,
							transactionIdList);
			return nicTransactionLogDBOList;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public List<NicTransactionLog> findAllTransactionLogsByRefId(String refId)
			throws TransactionServiceException {
		try {
			List<NicTransactionLog> nicTransactionLogDBOList = new ArrayList<NicTransactionLog>();
			NicTransactionLog exampleTransLog = new NicTransactionLog();
			exampleTransLog.setRefId(refId);
			List<NicTransactionLog> resultList = transactionLogDao
					.findAll(exampleTransLog);
			nicTransactionLogDBOList.addAll(resultList);
			return nicTransactionLogDBOList;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public BaseModelList<NicTransactionLog> findAllRicTransactionLogsByRefId(
			String refId) throws TransactionServiceException {
		try {
			String[] ricTransStages = new String[] {
					TRANSACTION_STAGE_RIC_REGISTRATION,
					TRANSACTION_STAGE_RIC_PEND_SUPERVISOR,
					TRANSACTION_STAGE_RIC_VERIFICATION,
					TRANSACTION_STAGE_RIC_EXC_HANDLING,
					TRANSACTION_STAGE_RIC_PAYMENT,
					TRANSACTION_STAGE_RIC_ISSUANCE,
					TRANSACTION_STAGE_RIC_CARD_ISSUED,
					TRANSACTION_STAGE_RIC_CARD_REJECTED,
					TRANSACTION_STAGE_RIC_REJECTED,
					TRANSACTION_STAGE_RIC_REG_CANCELLED

					, TRANSACTION_STAGE_RIC_PEND_EH,
					TRANSACTION_STAGE_RIC_PEND_ISS_SUPERVISOR,
					TRANSACTION_STAGE_RIC_CARD_RECEPTION,
					TRANSACTION_STAGE_RIC_CARD_REPACKAGE,
					TRANSACTION_STAGE_RIC_CARD_TRANSFER,
					TRANSACTION_STAGE_RIC_CARD_DEACTIVATED,
					TRANSACTION_STAGE_DOC_PROCESS, TRANSACTION_STAGE_VOID,
					TRANSACTION_STAGE_RE_REGISTERED };

			List<NicTransactionLog> nicTransactionLogDBOList = new ArrayList<NicTransactionLog>();

			List<String> ricTransactionStates = new ArrayList<String>();
			// comment to retrieve all logs without filtering.
			ricTransactionStates.addAll(Arrays.asList(ricTransStages));
			BaseModelList<NicTransactionLog> resultList = transactionLogDao
					.findAllByRefIdAndTransStateList(refId,
							ricTransactionStates);
			nicTransactionLogDBOList.addAll(resultList.getListModel());
			return new BaseModelList<NicTransactionLog>(
					nicTransactionLogDBOList, resultList.isError(),
					resultList.getMessage());
		} catch (Exception e) {
			return new BaseModelList<NicTransactionLog>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findAllRicTransactionLogsByRefId - " + refId
							+ " - thất bại.");
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public NicTransaction findById(String transactionId, String nin, String ccn)
			throws TransactionServiceException {
		try {
			if (StringUtils.isBlank(transactionId)) {
				throw new TransactionServiceException(
						"transaction Id cannot be empty.");
			}
			NicTransaction nicTransDBO = this.findById(transactionId);
			if (nicTransDBO != null) {
				// remove transaction with different ccn and nin
				boolean isValid = true;
				// if (StringUtils.isNotBlank(nin)
				// && !StringUtils.equals(nicTransDBO.getNin(), nin)) {
				// isValid = false;
				// }
				// if (StringUtils.isNotBlank(ccn)
				// && CollectionUtils.isNotEmpty(nicTransDBO
				// .getNicIssuanceDatas())) {
				// boolean ccnExists = false;
				// for (NicIssuanceData nicIssDataDBO : nicTransDBO
				// .getNicIssuanceDatas()) {
				// if (StringUtils.equals(nicIssDataDBO.getCcn(), ccn)) {
				// ccnExists = true;
				// }
				// }
				// isValid &= ccnExists;
				// }
				if (!isValid) {
					nicTransDBO = null;
				}
			}
			return nicTransDBO;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public NicTransaction getTransactionIssuanceDatas(NicTransaction transObj)
			throws TransactionServiceException {
		try {
			if (StringUtils.isBlank(transObj.getTransactionId())) {
				throw new TransactionServiceException(
						"transaction Id cannot be empty.");
			}
			// NicTransaction nicTransDBO =
			// nicIssuanceDataDao.getTransactionIssuanceDatas(transObj);
			List<NicDocumentData> documentDataList = documentDataDao
					.findAllByTransactionId(transObj.getTransactionId())
					.getListModel();
			if (CollectionUtils.isNotEmpty(documentDataList)) {
				transObj.getNicDocumentDatas().addAll(documentDataList);
			}
			return transObj;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public NicTransaction getTransactiondocuments(NicTransaction transObj)
			throws TransactionServiceException {
		try {
			if (StringUtils.isBlank(transObj.getTransactionId())) {
				throw new TransactionServiceException(
						"transaction Id cannot be empty.");
			}
			NicTransaction nicTransDBO = transactionDocumentDao
					.getTransactiondocuments(transObj);

			return nicTransDBO;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public NicTransaction getTransactionRegistrationData(NicTransaction transObj)
			throws TransactionServiceException {
		try {
			if (StringUtils.isBlank(transObj.getTransactionId())) {
				throw new TransactionServiceException(
						"transaction Id cannot be empty.");
			}
			NicTransaction nicTransDBO = registrationDataDao
					.getTransactionRegistrationData(transObj);

			return nicTransDBO;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public NicTransaction getTransactionPayment(NicTransaction transObj)
			throws TransactionServiceException {
		try {
			if (StringUtils.isBlank(transObj.getTransactionId())) {
				throw new TransactionServiceException(
						"transaction Id cannot be empty.");
			}
			NicTransaction nicTransDBO = transactionPaymentDao
					.getTransactionPayment(transObj);

			return nicTransDBO;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public List<MainTransaction> findAllTransactionHistory(
			String transactionId, String documentNo,
			boolean wantRegistrationData, boolean wantTransactionDoc,
			boolean wantIssuanceData, boolean wantPayment,
			boolean wantTransactionLog, boolean wantLatestOnly)
			throws TransactionServiceException {
		return this.findAllTransactionHistory(transactionId, documentNo, null,
				wantRegistrationData, wantTransactionDoc, wantIssuanceData,
				wantPayment, wantTransactionLog, wantLatestOnly);
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public List<MainTransaction> findAllTransactionHistory(
			String transactionId, String documentNo, String nin,
			boolean wantRegistrationData, boolean wantTransactionDoc,
			boolean wantIssuanceData, boolean wantPayment,
			boolean wantTransactionLog, boolean wantLatestOnly)
			throws TransactionServiceException {
		List<MainTransaction> transactionDTOList = new ArrayList<MainTransaction>();

		try {
			List<NicTransaction> nicTransDBOResultList = new ArrayList<NicTransaction>();
			if (StringUtils.isNotBlank(transactionId)) {
				NicTransaction nicTransDBO = this.findById(transactionId);
				if (nicTransDBO != null) {
					nicTransDBOResultList.add(nicTransDBO);
					logger.info(
							"[findAllTransactionHistory] findById({}) result:{}-{}",
							new Object[] { transactionId,
									nicTransDBO.getTransactionId(),
									nicTransDBO.getTransactionStatus() });
				}
			}
			if (StringUtils.isNotBlank(documentNo)) {
				NicDocumentData documentData = this.documentDataDao
						.findByDocNumber(documentNo).getModel();
				if (documentData != null) {
					// remove duplicate records
					boolean isDuplicate = false;
					for (NicTransaction nicTransDBO : nicTransDBOResultList) {
						if (StringUtils.equals(nicTransDBO.getTransactionId(),
								documentData.getId().getTransactionId())) {
							isDuplicate = true;
						}
					}
					if (!isDuplicate) {
						NicTransaction nicTransDBO = this.findById(documentData
								.getId().getTransactionId());
						if (nicTransDBO != null) {
							nicTransDBOResultList.add(nicTransDBO);
							logger.info(
									"[findAllTransactionHistory] findById({}) result:{}-{}",
									new Object[] { documentNo,
											nicTransDBO.getTransactionId(),
											nicTransDBO.getTransactionStatus() });
						}
					} else {
						logger.info(
								"[findAllTransactionHistory] findById({}) skipped.",
								documentNo);
					}
				}
			}
			if (StringUtils.isNotBlank(nin)) {
				List<NicTransaction> nicTransDBOList = this.dao
						.findAllByNin(nin);
				if (CollectionUtils.isNotEmpty(nicTransDBOList)) {
					logger.info(
							"[findAllTransactionHistory] findById({}) result.size:{}",
							new Object[] { nin, nicTransDBOList.size() });
					// remove duplicate records
					for (NicTransaction temp : nicTransDBOList) {
						boolean isDuplicate = false;
						for (NicTransaction nicTransDBO : nicTransDBOResultList) {
							if (StringUtils.equals(
									nicTransDBO.getTransactionId(),
									temp.getTransactionId())) {
								isDuplicate = true;
								break;
							}
						}
						if (!isDuplicate) {
							nicTransDBOResultList.add(temp);
						}
					}
				}
			}

			// to check all related records
			List<String> tranIdList = new ArrayList<String>();
			for (NicTransaction tran : nicTransDBOResultList) {
				if (!tranIdList.contains(tran.getTransactionId())) {
					tranIdList.add(tran.getTransactionId());
				}
			}
			if (CollectionUtils.isNotEmpty(tranIdList)) {
				List<String> otherTranIdList = afisDataDao
						.findOtherAfisHistoryByTransIdList(tranIdList);
				if (CollectionUtils.isNotEmpty(otherTranIdList)) {
					List<NicTransaction> otherTransDBOList = this.dao
							.findAllByTransIdList(otherTranIdList);
					if (CollectionUtils.isNotEmpty(otherTransDBOList)) {
						nicTransDBOResultList.addAll(otherTransDBOList);
						logger.info(
								"[findAllTransactionHistory] findOtherAfisHistory({}) result.size:{}",
								new Object[] { otherTranIdList,
										otherTransDBOList.size() });
					}
				}
			}

			if (wantLatestOnly) {
				NicTransaction latestNicTransaction = null;
				Date latestDateOfApplication = null;
				for (NicTransaction nicTransaction : nicTransDBOResultList) {
					Date loopDateOfApplication = nicTransaction
							.getDateOfApplication();
					if ((latestDateOfApplication == null)
							|| (loopDateOfApplication != null
									&& latestDateOfApplication != null && DateUtil
										.isAfterDate(loopDateOfApplication,
												latestDateOfApplication))) {
						latestNicTransaction = nicTransaction;
						latestDateOfApplication = nicTransaction
								.getDateOfApplication();
					}
				}
				if (latestNicTransaction != null) {
					nicTransDBOResultList.clear();
					nicTransDBOResultList.add(latestNicTransaction);
				}
			}
			List<String> transactionIdList = new ArrayList<String>();
			for (NicTransaction nicTransDBO : nicTransDBOResultList) {
				if (nicTransDBO != null) { // convert the DBO to DTO, add to
											// return list
					String loopTransactionId = nicTransDBO.getTransactionId();
					transactionIdList.add(loopTransactionId);
					if (wantRegistrationData) {
						nicTransDBO = this
								.getTransactionRegistrationData(nicTransDBO);
					}
					if (wantPayment) {
						nicTransDBO = this.getTransactionPayment(nicTransDBO);
					}
					if (wantTransactionDoc) {
						nicTransDBO = this.getTransactiondocuments(nicTransDBO);
					}
					if (wantIssuanceData) {
						nicTransDBO = this
								.getTransactionIssuanceDatas(nicTransDBO);
					}
					MainTransaction transactionDTO = mapper
							.parseMainTransactionDTO(nicTransDBO);
					NicRejectionData nicRejectionDataDBO = nicRejectionDataDao
							.findByTransactionId(loopTransactionId);
					if (nicRejectionDataDBO != null) {
						String transactionStatus = nicTransDBO
								.getTransactionStatus();
						RejectionData rejectionDataDTO = mapper
								.parseRejectionDataDTO(nicRejectionDataDBO,
										transactionStatus);
						if (rejectionDataDTO != null) {
							transactionDTO.setRejectionData(rejectionDataDTO);
						}
					}
					if (wantTransactionLog) {
						List<NicTransactionLog> nicTransactionLogDBOList = new ArrayList<NicTransactionLog>();
						List<NicTransactionLog> resultList = this
								.findAllRicTransactionLogsByRefId(
										loopTransactionId).getListModel();
						if (CollectionUtils.isNotEmpty(resultList)) {
							nicTransactionLogDBOList.addAll(resultList);
						}
						List<TransactionLog> transactionLogDTOList = mapper
								.parseTransactionLogDTOList(nicTransactionLogDBOList);
						if (CollectionUtils.isNotEmpty(transactionLogDTOList))
							transactionDTO.getTransactionLogs().addAll(
									transactionLogDTOList);
					}
					transactionDTOList.add(transactionDTO);
				}
			}
		} catch (TransactionServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
		return transactionDTOList;
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public List<NicTransactionLog> saveTransactionLogs(
			List<NicTransactionLog> transactionLogList) {
		List<NicTransactionLog> failureLogList = new ArrayList<NicTransactionLog>();
		if (CollectionUtils.isNotEmpty(transactionLogList)) {
			for (NicTransactionLog logDBO : transactionLogList) {
				try {
					// 08 Oct 2013 (chris): to handle duplicate logs - start
					NicTransactionLog tempLog = new NicTransactionLog();
					tempLog.setRefId(logDBO.getRefId());
					if (logDBO.getLogCreateTime() != null)
						tempLog.setLogCreateTime(logDBO.getLogCreateTime());
					tempLog.setTransactionStage(logDBO.getTransactionStage());
					List<NicTransactionLog> matchLogList = transactionLogDao
							.findAll(tempLog);
					if (CollectionUtils.isNotEmpty(matchLogList)) {
						logger.info(
								"save log(refId={}, createTime={}, stage={}, status={})",
								new Object[] { logDBO.getRefId(),
										logDBO.getLogCreateTime(),
										logDBO.getTransactionStage(),
										logDBO.getTransactionStatus() });
						long logId = matchLogList.get(0).getLogId();
						logDBO.setLogId(logId);
						transactionLogDao.saveOrUpdate(logDBO);
					} else {
						// 08 Oct 2013 (chris): to handle duplicate logs - end
						if (logDBO.getLogCreateTime() == null)
							logDBO.setLogCreateTime(new Date());
						transactionLogDao.save(logDBO);
					}
				} catch (Exception e) {
					logger.error(
							"Add to failured log List (refId={}, createTime={}, stage={}, status={}, errorMessage={})",
							new Object[] { logDBO.getRefId(),
									logDBO.getLogCreateTime(),
									logDBO.getTransactionStage(),
									logDBO.getTransactionStatus(),
									e.getMessage() });
					failureLogList.add(logDBO);
				}
			}
		}
		logger.debug("rejected log size={}", failureLogList.size());
		return failureLogList;
	}

	// @Override
	// @Transactional(rollbackFor = java.lang.Throwable.class, propagation =
	// Propagation.REQUIRED)
	// public List<NicTransaction> checkDuplicateByNin(String transactionId,
	// String nin, String transactionType, String remarks, String officerId,
	// String wkstnId, String siteCode) throws TransactionServiceException {
	// List<NicTransaction> hitTransactionList = new
	// ArrayList<NicTransaction>();
	// Date startTime = new Date();
	// String[] allowTransactionStatuses = new String[] {
	// TRANSACTION_STATUS_RIC_CARD_REJECTED,
	// TRANSACTION_STATUS_RIC_CARD_DEACTIVATED,
	// TRANSACTION_STATUS_RIC_CARD_RETURNED,
	// TRANSACTION_STATUS_INVENTORY_CARD_RECEIVED,
	// TRANSACTION_STATUS_INVENTORY_CARD_DESTROYED,
	// TRANSACTION_STATUS_VOID,
	// TRANSACTION_STATUS_NIC_REJECTED};
	// List<String> allowStatusList = Arrays.asList(allowTransactionStatuses);
	// if (StringUtils.equals(TRANSACTION_TYPE_REGISTRATION, transactionType)) {
	// List<NicTransaction> resultList = findAllByNin(nin);
	// if (CollectionUtils.isNotEmpty(resultList)) {
	// for (NicTransaction tempTransaction: resultList) {
	// String tempTransactionStatus = tempTransaction.getTransactionStatus();
	// //to check transaction status
	// if (!allowStatusList.contains(tempTransactionStatus)) {
	// logger.info("[checkDuplicateByNin]: transaction {} found with {}.",
	// tempTransaction.getTransactionId(), nin);
	// hitTransactionList.add(tempTransaction);
	// }
	// }
	// }
	// }
	// try {
	// if (StringUtils.isBlank(officerId)) officerId = SYSTEM_NIC;
	// if (StringUtils.isBlank(wkstnId)) {
	// wkstnId = SYSTEM_NIC;
	// java.net.InetAddress localMachine = InetAddress.getLocalHost();
	// wkstnId = localMachine.getHostName();
	// }
	// } catch (Exception e) {
	// }
	// String txnStatus = CollectionUtils.isNotEmpty(hitTransactionList) ?
	// TRANSACTION_STATUS_NIC_CHECK_COMPLETED_WITH_HIT :
	// TRANSACTION_STATUS_NIC_CHECK_COMPLETED;
	// Date endTime = new Date();
	// String logInfo = mapper.parseLogInfoXml(new LogInfoDTO(null, remarks));
	// Long logId =
	// transactionLogService.saveTransactionLogWithNin(transactionId,
	// TRANSACTION_STAGE_NIC_CHECK, txnStatus, officerId, wkstnId, siteCode,
	// startTime, endTime, logInfo, null, nin);
	// logger.debug("[checkDuplicateByNin] save NicTransactionLog completed, transactionId:{}, logId:{} ",
	// transactionId, logId);
	// return hitTransactionList;
	// }

	// @Transactional(rollbackFor = java.lang.Throwable.class, propagation =
	// Propagation.REQUIRED)
	// public PaginatedResult<NicMain> findAllForPagination(
	// PageRequest pageRequest, NicMain nicMain) {
	// int pageNo = pageRequest.getPageNo();
	// int pageSize = pageRequest.getMaxRecords();
	// boolean ignoreCase = true;
	// boolean enableLike = true;
	// Order order = null;
	// if (StringUtils.equals(pageRequest.getSortorder(), "asc")) {
	// order = Order.asc(pageRequest.getSortname());
	// } else {
	// order = Order.desc(pageRequest.getSortname());
	// }
	//
	// PaginatedResult<NicMain> pr = nicMainDao.findAllForPaginationByFilter(
	// nicMain, pageNo, pageSize, ignoreCase, enableLike, order);
	//
	// for (NicMain nic : pr.getRows()) {
	// List<NicHistory> hist = nicHistoryDao.findByNin(nic.getNin());
	// if (CollectionUtils.isNotEmpty(hist)) {
	// nic.setNicHistories(new HashSet<NicHistory>(hist));
	// }
	// }
	//
	// return pr;
	// }

	// @Override
	// public List<NicHistory> getNicHistory(String nin)
	// throws TransactionServiceException {
	// return nicHistoryDao.findByNin(nin);
	// }

	// @Override
	// public PaginatedResult<NicIssuanceData> findAllForPagination(
	// PageRequest pageRequest, NicIssuanceData nicIssuanceData)
	// throws TransactionServiceException {
	// PaginatedResult<NicIssuanceData> pr = null;
	// try {
	// int pageNo = pageRequest.getPageNo();
	// int pageSize = pageRequest.getMaxRecords();
	// boolean ignoreCase = true;
	// boolean enableLike = true;
	// Order order = null;
	// if (StringUtils.equals(pageRequest.getSortorder(), "asc")) {
	// order = Order.asc(pageRequest.getSortname());
	// } else {
	// order = Order.desc(pageRequest.getSortname());
	// }
	// // pr = nicIssuanceDataDao.findAllForPaginationByFilter(
	// // nicIssuanceData, pageNo, pageSize, ignoreCase, enableLike,
	// // order);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return pr;
	// }

	@Override
	public PaginatedResult<NicTransaction> findAllForPagination(
			PageRequest pageRequest, NicTransaction nicTransaction)
			throws TransactionServiceException {
		PaginatedResult<NicTransaction> pr = null;
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = true;
			boolean enableLike = true;
			Order order = null;
			if (StringUtils.equals(pageRequest.getSortorder(), "asc")) {
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}
			pr = dao.findAllForPagination(nicTransaction, pageNo, pageSize,
					ignoreCase, enableLike, order);
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}

		return pr;
	}

	@Override
	public List<Object[]> getNicTransactionAttachmentByDocType(
			NicRegistrationDataDto nicRegistrationData) {

		String surnameFull = nicRegistrationData.getSurnameFull();
		String surnameAtBirth = nicRegistrationData.getSurnameBirthFull();
		String otherName = nicRegistrationData.getOptionSurname();
		String gender = nicRegistrationData.getGender();
		Date dateOfBirth = nicRegistrationData.getDateOfBirth();

		String regSiteCode = null;
		String transactionId = null;
		String status = null;
		if (nicRegistrationData.getNicTransaction() != null) {
			regSiteCode = nicRegistrationData.getNicTransaction()
					.getRegSiteCode();
			transactionId = nicRegistrationData.getNicTransaction()
					.getTransactionId();
			status = nicRegistrationData.getNicTransaction()
					.getTransactionStatus();
		}

		String doctype = "PH_CAP";

		return transactionDocumentDao.getNicTransactionAttachmentByDocType(
				doctype, regSiteCode, transactionId, surnameFull, status,
				surnameAtBirth, otherName, gender, dateOfBirth);
	}

	@Override
	public List getCardConvChartData(Date fromDate, Date toDate,
			String[] siteCodes) throws Exception {
		return dao.getCardConvChartData(fromDate, toDate, siteCodes);
	}

	@Override
	public List<Object[]> getTotTransVolBySiteReportInfo(Date fromDate,
			Date toDate, String[] sites) throws Exception {
		return dao.getTotTransVolBySiteReportInfo(fromDate, toDate, sites);
	}

	@Override
	public MultiSeriesReport getMSSeriesReport(String reportBeanName,
			HashMap<String, Object> parameters) throws Exception {
		try {
			MSReportDefinition msReportDef = (MSReportDefinition) getReportDefination(reportBeanName);
			MultiSeriesReport report = new MultiSeriesReport();
			BeanUtils.copyProperties(report, msReportDef);
			dao.loadMSLineReportData(report, msReportDef, parameters);
			return report;
		} catch (DaoException ex) {
			throw new Exception(ex);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	/**
	 * Gets the chart defination.
	 *
	 * @param chartBeanName
	 *            the chart bean name
	 * @return the chart defination
	 * @throws ChartDataException
	 *             the chart data exception
	 */
	private ReportDefinition getReportDefination(String reportBeanName)
			throws Exception {
		ReportDefinition reportDef = (ReportDefinition) SpringServiceManager
				.getBean(reportBeanName);
		if (reportDef == null) {
			throw new Exception("Report defination not configured for "
					+ reportBeanName);
		}
		return reportDef;
	}

	@Override
	public PaginatedResult<NicTransactionLog> findAllForPagination(
			int currentPage, int pageSize, String siteCode, String officerId,
			String transactionStage, String transactionStatus) throws Exception {
		return transactionLogDao.findAllForPagination(currentPage, pageSize,
				siteCode, officerId, transactionStage, transactionStatus);
	}

	@Override
	public void updateTransactionStatusOnReprint(String transactionId,
			String userId, String wkstnId) throws Exception {
		logger.info("Inside the service layer to update transaction status upon reprint");
		NicTransaction transDataUpdateObj = null;
		try {
			transDataUpdateObj = dao.findById(transactionId);
			transDataUpdateObj
					.setTransactionStatus(NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED_FOR_REPRINT);
			transDataUpdateObj.setUpdateBy(userId);
			transDataUpdateObj.setUpdateWkstnId(wkstnId);
			transDataUpdateObj.setUpdateDatetime(new Date());
			dao.saveOrUpdate(transDataUpdateObj);
		} catch (Exception ex) {
			logger.error(
					"Error occurred while updating transaction status upon reprint. Exception: "
							+ ex.getMessage(), ex);
			throw new Exception(ex);
		}

	}

	@Override
	public List<Object[]> getCardBypassApprovedBySiteInfo(Date fromDate,
			Date toDate, String[] sites) throws Exception {
		return dao.getCardBypassApprovedBySiteInfo(fromDate, toDate, sites);
	}

	@Override
	public PaginatedResult<String> getCardBypassApprovedTransIds(Date fromDate,
			Date toDate, String[] siteCodes, int fromRow, int toRow)
			throws Exception {
		return dao.getCardBypassApprovedTransIds(fromDate, toDate, siteCodes,
				fromRow, toRow);
	}

	@Override
	public List<String> getAllCardBypassApprovedTransIds(Date fromDate,
			Date toDate, String[] sites) throws Exception {
		return dao.getAllCardBypassApprovedTransIds(fromDate, toDate, sites);
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public BaseModelSingle<NicTransaction> getNicTransactionById(
			String transactionId) throws TransactionServiceException {
		try {
			if (StringUtils.isBlank(transactionId)) {
				throw new TransactionServiceException(
						"Transaction Id cannot be empty.");
			}
			return this.dao.getNicTransactionById(transactionId);
		} catch (Exception e) {
			return new BaseModelSingle<NicTransaction>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - getNicTransactionById - " + transactionId
							+ " - thất bại.");
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public NicTransaction getNicTransactionByPrevPPno(String prevPassport,
			String typePassport, String nin) throws TransactionServiceException {
		try {
			if (StringUtils.isBlank(prevPassport)
					&& StringUtils.isBlank(typePassport)
					&& StringUtils.isBlank(nin)) {
				throw new TransactionServiceException(
						"One in all cannot be empty.");
			}
			NicTransaction nicTransaction = dao.getNicTransactionByPrevPPno(
					prevPassport, typePassport, nin);
			return nicTransaction;
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public void updateDocByStatus(String transactionId, String passportNo,
			TransactionStatus status, String userName, String wkstnId)
			throws Exception {
		logger.info("updateDocByStatus");

		this.updateDocByStatus(transactionId, passportNo, status, userName,
				wkstnId, null);

	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public void updateDocByStatus(String transactionId, String passportNo,
			TransactionStatus status, String userName, String wkstnId,
			String transactionStage) throws Exception {
		logger.info("updateDocByStatus, with transactionStage");

		{
			NicTransaction transDataUpdateObj = null;
			try {
				transDataUpdateObj = dao.findById(transactionId);
				transDataUpdateObj.setTransactionStatus(status.getKey());
				transDataUpdateObj.setUpdateBy(userName);
				transDataUpdateObj.setUpdateWkstnId(wkstnId);
				transDataUpdateObj.setUpdateDatetime(new Date());
				dao.saveOrUpdate(transDataUpdateObj);
				this.logTransaction(
						transactionId,
						transactionStage == null ? NicTransactionLog.TRANSACTION_STAGE_CANCEL_PASSPORT
								: transactionStage, status.getKey(), userName,
						wkstnId,
						NicTransactionLog.TRANSACTION_STAGE_CANCEL_PASSPORT,
						NicTransactionLog.TRANSACTION_STAGE_CANCEL_PASSPORT
								+ ":  Passport Number=" + passportNo
								+ ",  Transaction=" + transactionId);
			} catch (Exception ex) {
				logger.error(
						"Error updateDocByStatus dao. Exception: "
								+ ex.getMessage(), ex);
				throw new Exception(ex);
			}
		}

		{
			try {
				this.documentDataService.updateDocByStatus(transactionId,
						passportNo, TransactionStatus.Cancelled, userName);
			} catch (Exception e) {
				logger.error(
						"Error updateDocByStatus documentDataService. Exception: "
								+ e.getMessage(), e);
				throw new Exception(e);
			}
		}

	}

	private void logTransaction(String referenceId, String stage,
			String status, String officerUserId, String officerWorkstationId,
			String reason, String remarks) {
		{
			NicTransactionLog nicTransactionLog = new NicTransactionLog();
			nicTransactionLog.setRefId(referenceId);
			nicTransactionLog.setTransactionStage(stage);
			nicTransactionLog.setTransactionStatus(status);
			nicTransactionLog.setOfficerId(officerUserId);
			nicTransactionLog.setWkstnId(officerWorkstationId);
			nicTransactionLog.setLogCreateTime(new Date());
			String remarksDataMarshal = null;
			{
				LogInfoDTO logInfoDto = new LogInfoDTO();
				logInfoDto.setReason(reason);
				logInfoDto.setRemark(remarks);
				try {
					remarksDataMarshal = logUtil.marshal(logInfoDto);
				} catch (JaxbXmlConvertorException e) {
					e.printStackTrace();
				}
			}
			nicTransactionLog.setLogInfo(remarksDataMarshal);
			this.transactionLogDao.save(nicTransactionLog);
		}
	}

	@Override
	public int updateStatusByTxnIdList(List<String> txnIdList, String status,
			String officerId, String workstationId)
			throws TransactionServiceException {
		try {
			return this.dao.updateStatusByTxnIdList(txnIdList, status,
					officerId, workstationId);
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Override
	public List<NicTransaction> findByApplnRefId(String applnRefNo)
			throws TransactionServiceException {
		try {
			return this.dao.findByApplnRefId(applnRefNo);
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}
	}

	@Override
	public boolean prioritizeTransaction(String transactionId, String remarks,
			String userId, String wkstnId) throws Exception {
		logger.info("Inside the service layer to prioritize transaction: "
				+ transactionId);
		Date currentDate = new Date();
		Date originalEstDateOfCollection;
		Integer originalPriority;
		try {
			NicTransaction nicTransaction = this.dao.findById(transactionId);
			if (nicTransaction != null) {
				originalEstDateOfCollection = nicTransaction
						.getEstDateOfCollection();
				originalPriority = nicTransaction.getPriority();
				nicTransaction.setPriority(2);
				nicTransaction.setEstDateOfCollection(DateUtil.formatDate(
						currentDate, DateUtil.FORMAT_MM_DD_YYYY));
				nicTransaction.setUpdateBy(userId);
				nicTransaction.setUpdateDatetime(new Date());
				nicTransaction.setUpdateWkstnId(wkstnId);
				this.dao.saveOrUpdate(nicTransaction);
			} else {
				logger.info("Transaction not found");
				return false;
			}

			BaseModelList<NicUploadJob> baseFindAllTran = uploadJobDao
					.findAllByTransactionId(transactionId);
			List<NicUploadJob> nicUploadJobList = baseFindAllTran
					.getListModel();
			if (CollectionUtils.isNotEmpty(nicUploadJobList)) {
				for (NicUploadJob nicUploadJob : nicUploadJobList) {
					nicUploadJob.setJobPriority(2);
					nicUploadJob.setJobReprocessCount(1);
					uploadJobDao.saveOrUpdate(nicUploadJob);
				}
			}

			NicTransactionLog nicTransactionLog = new NicTransactionLog();
			nicTransactionLog.setRefId(transactionId);
			nicTransactionLog.setLogCreateTime(currentDate);
			nicTransactionLog
					.setTransactionStage(NicTransactionService.TRANSACTION_STAGE_WORKFLOW);
			nicTransactionLog
					.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_PRIORITIZED);
			nicTransactionLog.setStartTime(currentDate);
			nicTransactionLog.setEndTime(new Date());
			nicTransactionLog
					.setLogData("Prioritize Transaction [Original] EST_DATE_OF_COLLECTION: "
							+ DateUtil.parseDate(originalEstDateOfCollection,
									DateUtil.FORMAT_DD_MM_YYYY_HH24_MM_SS)
							+ ", PRIORITY: " + originalPriority);
			nicTransactionLog.setLogInfo(remarks);
			nicTransactionLog.setOfficerId(userId);
			nicTransactionLog.setWkstnId(wkstnId);
			nicTransactionLog.setSiteCode("001");
			transactionLogDao.saveOrUpdate(nicTransactionLog);

			return true;
		} catch (Exception ex) {
			logger.error(
					"Error occurred while prioritize transaction: "
							+ ex.getMessage(), ex);
			throw new Exception(ex);
		}
	}

	@Override
	public boolean reprintTransaction(String transactionId,
			boolean cancelPptFlag, String remarks, String userId, String wkstnId)
			throws Exception {
		logger.info("Inside the service layer to reprint transaction: "
				+ transactionId);
		try {
			final String systemSiteCode = nicCommandUtil
					.getSystemSiteCodeFromParameter(); // "001"

			NicTransaction nicTransaction = this.dao.findById(transactionId);
			if (nicTransaction != null) {

				// Get latest Transaction Reprint order by Reprint Count desc
				NicTransactionReprint latestTransactionReprint = transactionReprintDao
						.getLatestReprintByTransactionId(transactionId);
				NicTransactionReprint transactionRepprint = new NicTransactionReprint();
				NicTransactionReprintId reprintId = new NicTransactionReprintId();

				// Generate new Reference ARN: R-1-TXNID
				String refArn = StringUtils.EMPTY;
				int printCount = 1;
				if (latestTransactionReprint != null) {
					printCount += latestTransactionReprint.getReprintCount();
					refArn = "R-"
							+ StringUtils.leftPad(String.valueOf(printCount),
									1, '0') + "-" + transactionId;

				} else {
					refArn = "R-1-" + transactionId;
				}
				if (printCount > 9) {
					logger.warn("Transaction exceed the reprint limit! - "
							+ transactionId);
					throw new PersoServiceException(
							"Transaction exceed the reprint limit!");
				}
				Date startTime = new Date();
				try {
					logger.info("Re-submit transaction: " + transactionId
							+ ", RefARN: " + refArn);
					persoService.submitPersoData(nicTransaction, refArn);// 26-01:
																			// TRUNG
																			// doan
																			// nay
																			// co
																			// the
																			// bi
																			// anh
																			// huong
																			// khi
																			// sua
																			// doan
																			// xml
																			// utf-8
				} catch (PersoServiceException e) {
					throw new PersoServiceException(e);
				}
				Date endTime = new Date();

				// Reset transaction status to RP
				nicTransaction
						.setTransactionStatus(TransactionStatus.Personalization
								.getKey());
				nicTransaction.setUpdateDatetime(new Date());
				nicTransaction.setUpdateBy(userId);
				nicTransaction.setUpdateWkstnId(wkstnId);
				this.dao.saveOrUpdate(nicTransaction);

				// Add new log for perso register
				NicTransactionLog nicTransactionLog = new NicTransactionLog();
				nicTransactionLog.setRefId(transactionId);
				nicTransactionLog.setLogCreateTime(new Date());
				nicTransactionLog
						.setTransactionStage(NicTransactionService.TRANSACTION_STAGE_WORKFLOW);
				nicTransactionLog
						.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_REPRINT);
				nicTransactionLog.setStartTime(startTime);
				nicTransactionLog.setEndTime(endTime);
				nicTransactionLog.setLogData("Reprint transaction: "
						+ transactionId + ", RefArn: " + refArn);
				nicTransactionLog.setLogInfo(remarks);
				nicTransactionLog.setOfficerId(userId);
				nicTransactionLog.setWkstnId(wkstnId);
				nicTransactionLog.setSiteCode(systemSiteCode);
				transactionLogDao.saveOrUpdate(nicTransactionLog);

				reprintId.setTransactionId(transactionId);
				reprintId.setRefArn(refArn);
				transactionRepprint.setId(reprintId);
				transactionRepprint
						.setReprintCount(Integer.valueOf(printCount));
				transactionRepprint.setCancelPptFlag(cancelPptFlag);
				transactionRepprint.setCreateBy(userId);
				transactionRepprint.setCreateWkstnId(wkstnId);
				transactionRepprint.setCreateDatetime(new Date());
				transactionReprintDao.saveOrUpdate(transactionRepprint);

				// remove transaction from rejection data
				NicRejectionData rejectionData = rejectionDataDao
						.findByTransactionId(transactionId);
				if (rejectionData != null)
					rejectionDataDao.delete(rejectionData.getWorkflowJobId());

				// Remove passport from the dispatch queue when passport had
				// been set as dispatch in system
				/*
				 * List<String> docStatus = new ArrayList<>();
				 * docStatus.add(TransactionStatus.Dispatched.getKey());
				 * docStatus.add(TransactionStatus.QcCompleted.getKey());
				 * List<NicDocumentData> documentDataList =
				 * documentDataDao.findAllByTransactionId(transactionId,
				 * docStatus); if (CollectionUtils.isNotEmpty(documentDataList))
				 * { for (NicDocumentData nicDocumentData : documentDataList) {
				 * DispatchQueueData queuData =
				 * dispatchDataDao.findQueueData(transactionId,
				 * nicDocumentData.getId().getPassportNo()); if (queuData !=
				 * null) { queuData.setStatus('C');
				 * dispatchDataDao.saveOrUpdate(queuData); }
				 * 
				 * DispatchPackageDetail packageDetail =
				 * dispatchPackageDetailDao.findPackageDetail(transactionId,
				 * nicDocumentData.getId().getPassportNo()); if (packageDetail
				 * != null) { packageDetail.setStatus("CANCELLED");
				 * dispatchPackageDetailDao.saveOrUpdate(packageDetail); } } }
				 */

				// Cancel all active passport if existed
				if (cancelPptFlag == true) {
					List<String> docActiveStatus = new ArrayList<>();
					docActiveStatus.add(TransactionStatus.QcCompleted.getKey());
					docActiveStatus.add(TransactionStatus.Dispatched.getKey());
					docActiveStatus.add(TransactionStatus.Received.getKey());
					docActiveStatus.add(TransactionStatus.Reroute.getKey());
					docActiveStatus.add(TransactionStatus.Active.getKey());

					List<NicDocumentData> documentDataActiveList = documentDataDao
							.findAllByTransactionId(transactionId,
									docActiveStatus);
					if (CollectionUtils.isNotEmpty(documentDataActiveList)) {
						for (NicDocumentData nicDocumentData : documentDataActiveList) {
							nicDocumentData
									.setStatus(TransactionStatus.Rejected
											.getKey());
							nicDocumentData.setUpdateBy(userId);
							nicDocumentData.setUpdateDatetime(new Date());
							nicDocumentData.setStatusUpdateTime(new Date());
							nicDocumentData.setUpdateWkstnId(wkstnId);
							documentDataDao.saveOrUpdate(nicDocumentData);

							NicDocumentHistory documentHistory = new NicDocumentHistory();
							documentHistory.setPassportNo(nicDocumentData
									.getId().getPassportNo());
							documentHistory.setSiteCode(systemSiteCode);
							documentHistory
									.setStatus(TransactionStatus.Rejected
											.getKey());
							documentHistory.setStatusUpdateBy(userId);
							documentHistory.setStatusUpdateTime(new Date());

							documentHistoryDao.saveOrUpdate(documentHistory);

							documentDataDao.cancelPassport(transactionId,
									nicDocumentData.getId().getPassportNo());
						}

					}
				}
			} else {
				logger.info("Transaction not found");
				return false;
			}
			return true;
		} catch (Exception ex) {
			logger.error(
					"Error occurred while reprint transaction: "
							+ ex.getMessage(), ex);
			throw new Exception(ex);
		}
	}

	@Override
	public boolean cancelPassport(String transactionId, String passportNo,
			String remarks, String userId, String wkstnId) throws Exception {
		logger.info("Inside the service layer to reprint transaction: "
				+ transactionId);
		try {
			NicTransaction nicTransaction = this.dao.findById(transactionId);
			if (nicTransaction != null) {
				// Add new log for perso register
				NicTransactionLog nicTransactionLog = new NicTransactionLog();
				nicTransactionLog.setRefId(transactionId);
				nicTransactionLog.setLogCreateTime(new Date());
				nicTransactionLog
						.setTransactionStage(NicTransactionService.TRANSACTION_STAGE_WORKFLOW);
				nicTransactionLog
						.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_PASSPORT_CANCELLED);
				nicTransactionLog
						.setLogData("Cancelled Passport with transactionId: "
								+ transactionId + ", passportNo: " + passportNo);
				nicTransactionLog.setLogInfo(remarks);
				nicTransactionLog.setStartTime(new Date());
				nicTransactionLog.setEndTime(new Date());
				nicTransactionLog.setOfficerId(userId);
				nicTransactionLog.setWkstnId(wkstnId);
				nicTransactionLog.setSiteCode("001");
				transactionLogDao.saveOrUpdate(nicTransactionLog);

				// Cancel an active passport if exists
				NicDocumentDataId docId = new NicDocumentDataId(transactionId,
						passportNo);
				NicDocumentData documentData = documentDataDao.findById(docId);
				if (documentData != null) {
					documentData.setStatus(TransactionStatus.Rejected.getKey());
					documentData.setUpdateBy(userId);
					documentData.setUpdateDatetime(new Date());
					documentData.setStatusUpdateTime(new Date());
					documentData.setUpdateWkstnId(wkstnId);
					documentDataDao.saveOrUpdate(documentData);

					NicDocumentHistory documentHistory = new NicDocumentHistory();
					documentHistory.setPassportNo(passportNo);
					documentHistory.setSiteCode("001");
					documentHistory.setStatus(TransactionStatus.Rejected
							.getKey());
					documentHistory.setStatusUpdateBy(userId);
					documentHistory.setStatusUpdateTime(new Date());

					documentHistoryDao.saveOrUpdate(documentHistory);
					documentDataDao.cancelPassport(transactionId, passportNo);

				}
			} else {
				logger.info("Transaction not found");
				return false;
			}
			return true;
		} catch (Exception ex) {
			logger.error(
					"Error occurred while cancel passport: " + ex.getMessage(),
					ex);
			throw new Exception(ex);
		}
	}

	// Thêm hàm check CMND cho phần kiểm tra CPD
	public List<NicTransaction> findAllByFields(String nin) throws Exception {

		try {
			List<NicTransaction> nicTransDBOResultList = new ArrayList<NicTransaction>();

			if (StringUtils.isNotBlank(nin)) {
				List<NicTransaction> nicTransDBOList = this.dao
						.findAllByNin(nin);
				if (CollectionUtils.isNotEmpty(nicTransDBOList)) {
					logger.info(
							"[findAllTransactionHistory] findById({}) result.size:{}",
							new Object[] { nin, nicTransDBOList.size() });
					// remove duplicate records
					for (NicTransaction temp : nicTransDBOList) {
						boolean isDuplicate = false;
						for (NicTransaction nicTransDBO : nicTransDBOResultList) {
							if (StringUtils.equals(
									nicTransDBO.getTransactionId(),
									temp.getTransactionId())) {
								isDuplicate = true;
								break;
							}
						}
						if (!isDuplicate) {
							nicTransDBOResultList.add(temp);
						}
					}
				}
				return nicTransDBOResultList;
			}

			return null;

			/*
			 * String hql =
			 * "select a.nicId from NicRegistrationData a where a.transactionId = ? "
			 * ;
			 * 
			 * Object[] parameters = { transactionId } ; List<String> resultList
			 * = (List<String>) this.getHibernateTemplate().find(hql,
			 * parameters);
			 * 
			 * 
			 * if (CollectionUtils.isNotEmpty(resultList)) { nicId =
			 * Long.parseLong(resultList.get(0)); }
			 */
			// return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}

	// [09-Mar] Trung thêm số năm hết hạn hộ chiếu
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean updateValidateTime(String transactionId, String userId,
			String wkstnId, Integer year) {
		try {
			dao.updateValidateTime(transactionId, userId, wkstnId, year);
			NicTransaction nic = dao.getNicTransactionById(transactionId)
					.getModel();
			if (nic.getValidityPeriod() == year
					&& nic.getTransactionId() == transactionId)
				return true;
		} catch (Exception ex) {
			logger.error("Error occurred while approving the card. Exception: "
					+ ex.getMessage(), ex);

		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateOnApprove(String transactionId, String userId,
			String wkstnId) {
		try {
			dao.updateOnApprove(transactionId, userId, wkstnId);
		} catch (Exception ex) {
			logger.error("Error occurred while updateOnApprove. Exception: "
					+ ex.getMessage(), ex);

		}
	}

	@Override
	public List<NicTransaction> getListTransactionByPackage(String packageId) {
		try {
			List<NicTransaction> listNic = this.dao
					.getListTransactionByPackage(packageId);
			if (listNic != null)
				return listNic;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return new ArrayList<NicTransaction>();
	}

	@Override
	public List<NicTransaction> getListTransactionBySiteCode(String siteCode,
			Date date) {
		try {
			List<NicTransaction> listNic = this.dao
					.getListTransactionBySiteCode(siteCode, date);
			if (listNic != null)
				return listNic;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return new ArrayList<NicTransaction>();
	}

	@Override
	public boolean searchTransactionByLog(String refId, String transactionStatus) {
		try {
			List<NicTransactionLog> logList = transactionLogDao
					.findAllLogByStatus(refId, transactionStatus);
			if (logList != null && logList.size() > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public List<CountPassport> noneIssuePassportList() {

		List<CountPassport> result = null;
		try {
			result = dao.noneIssuePassportList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<CountPassport> issuePassportList() {

		List<CountPassport> result = null;
		try {
			result = dao.issuePassportList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<CountPassport> nonePrintPassport() {

		List<CountPassport> result = null;
		try {
			result = dao.nonePrintPassport();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<CountPassport> printedPassport() {

		List<CountPassport> result = null;
		try {
			result = dao.printedPassport();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<NicUploadJobDto> allPassportList(String handoverId,
			String regSite, String fromDate, String toDate, int pageNumber,
			int pageSize) {

		List<NicUploadJobDto> result = null;
		try {
			result = dao.allPassportList(handoverId, regSite, fromDate, toDate,
					pageNumber, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<NicUploadJobDto> newTransactionProcess() {

		List<NicUploadJobDto> result = null;
		try {
			result = dao.newTransactionProcess();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<NicUploadJobDto> newTransactionProcessDDXL() {

		List<NicUploadJobDto> result = null;
		try {
			result = dao.newTransactionProcessDDXL();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<CountPassport> totalHosoTrongNgay() {

		List<CountPassport> result = null;
		try {
			result = dao.totalHosoTrongNgay();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<CountPassport> slhosoDuyetCap() {

		List<CountPassport> result = null;
		try {
			result = dao.slhosoDuyetCap();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<CountPassport> slhosoTuChoi() {

		List<CountPassport> result = null;
		try {
			result = dao.slhosoTuChoi();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<CountPassport> slhosoSapHetHan() {

		List<CountPassport> result = null;
		try {
			result = dao.slhosoSapHetHan();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<CountPassport> slhosoQuaHan() {

		List<CountPassport> result = null;
		try {
			result = dao.slhosoQuaHan();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public long getRowCountallPassportList(String handoverId, String regSite,
			String fromDate, String toDate) throws Exception {
		try {
			return dao.getRowCountallPassportList(handoverId, regSite,
					fromDate, toDate);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return 0;
	}

	@Override
	public long getRowCountallPersoList(String handoverId, Integer persoId,
			String fromDate, String toDate) throws Exception {
		try {
			return dao.getRowCountallPersoList(handoverId, persoId, fromDate,
					toDate);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return 0;
	}

	@Override
	public List<NicUploadJobDto> allPersoList(String handoverId,
			Integer persoId, String fromDate, String toDate, int pageNumber,
			int pageSize) {

		List<NicUploadJobDto> result = null;
		try {
			result = dao.allPersoList(handoverId, persoId, fromDate, toDate,
					pageNumber, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<CountPassport> sohschuaxl() {

		List<CountPassport> result = null;
		try {
			result = dao.sohschuaxl();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<CountPassport> sohsdaxl() {

		List<CountPassport> result = null;
		try {
			result = dao.sohsdaxl();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	// add
	@Override
	public PaginatedResult<Datadto> allPassportStorageT(String name,
			String dateOfBirth, String idNumber, String gender,
			String listCode, String passportNo, String passportType,
			String receipNo, int page, int pageSize) throws Exception {
		try {
			PaginatedResult<Datadto> res = dao.allListBProcessPerso(name,
					dateOfBirth, idNumber, gender, listCode, passportNo,
					passportType, receipNo, page, pageSize);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("allPassportStorageT Error");
		}
	}

	@Override
	public PaginatedResult<NicUploadJobDto> allPassportStorage(
			String passportType, String passportNo, String status,
			String startDate, String endDate, String fullName, int pageNo,
			int pageSize) throws Exception {
		try {
			if (StringUtils.isNotEmpty(startDate)) {
				// startDate = HelperClass.getDateStringForRptQuery((startDate
				// != null) ? startDate : "");
			}
			if (StringUtils.isNotEmpty(endDate)) {
				// endDate = HelperClass.getDateStringForRptQuery((endDate !=
				// null) ? endDate : "");
			}

			PaginatedResult<NicUploadJobDto> res = dao
					.getResultPassportInStore(passportType, passportNo, status,
							startDate, endDate, fullName, pageNo, pageSize);
			/*
			 * PaginatedResult<NicUploadJobDto> res = dao.allPassportStorage(
			 * passportType, passportNo, status, startDate, endDate, fullName,
			 * pageNo, pageSize);
			 */
			if (res != null) {
				if (res.getRows().size() > 0) {
					List<NicUploadJobDto> list = res.getRows();
					for (NicUploadJobDto dto : list) {
						dto.setPriority(1);
						if (dto.getPassportStyle() != null && dto.getPassportStyle().equals("Y")) {
							dto.setPriority(0);
						}
						dto.setPassportType(codesService
								.getCodeValueDescByIdName(
										RegistrationConstants.CODE_ID_PASSPORT_TYPE,
										dto.getPassportType(), ""));
						SiteRepository site = siteService.getSiteRepoById(dto
								.getNicSiteCode());
						if (site != null)
							dto.setRicName(site.getSiteName());
						dto.setFullName(HelperClass.createFullName(
								dto.getSubName(), dto.getMidName(),
								dto.getFirstName()));
						// Date dateIss =
						// HelperClass.convertStringToDate2(dto.getDateIssuce());
						// dto.setDateIssuce(HelperClass.convertStringToString(dto.getDateIssuce().split(" ")[0]));
						// dto.setDateEprity(HelperClass.convertStringToString(dto.getDateEprity().split(" ")[0]));
						// Date dateEpr =
						// HelperClass.convertStringToDate2(dto.getDateEprity());
						// dto.setEsColectionDate(HelperClass
						// .convertStringToString(dto.getEsColectionDate()
						// .split(" ")[0]));
						/*
						 * if(dto.getStatus().equals("NONE")){
						 * dto.setStageList("Hủy/Hỏng/Mất"); }else
						 * if(dto.getStatus().equals("ISSUANCE")){
						 * dto.setStageList("Phát hành"); }else
						 * if(dateEpr.before(Calendar.getInstance().getTime())){
						 * dto.setStageList("Hết hạn/Không hiệu lực"); }else
						 * if(!
						 * dateEpr.before(Calendar.getInstance().getTime())){
						 * dto.setStageList("Có hiệu lực"); }
						 */

						dto.setPassportNo(StringUtils.isNotEmpty(dto
								.getPassportNo()) ? dto.getPassportNo() : dto
								.getTempPassportNo());
						/*
						 * NicDocumentData nicData = documentDataService
						 * .getDocumentDataById(dto.getTransactionId())
						 * .getModel(); if (nicData != null) {
						 * dto.setDateIssuce(HelperClass
						 * .convertDateToString2(nicData .getDateOfIssue()));
						 * dto.setDateEprity(HelperClass
						 * .convertDateToString2(nicData .getDateOfExpiry())); }
						 * if (nicData != null &&
						 * nicData.getStatus().equals("PERSONALIZED") &&
						 * StringUtils.isNotEmpty(nicData .getPackageId())) {
						 * dto.setStageList("Có hiệu lực"); } else if (nicData
						 * != null && nicData.getStatus().equals("NONE")) {
						 * dto.setStageList("Hủy/Hỏng/Mất"); } else if (nicData
						 * != null && nicData.getDateOfExpiry().before(
						 * Calendar.getInstance().getTime())) {
						 * dto.setStageList("Hết hạn"); } else if (nicData ==
						 * null && (StringUtils.isEmpty(dto
						 * .getPersonRegStatus()) || dto
						 * .getPersonRegStatus().equals("09")) &&
						 * dto.getInvestigationStatus().equals("40")) {
						 * dto.setStageList("Khởi tạo"); } else if (nicData !=
						 * null && nicData.getStatus().equals("ISSUANCE")) {
						 * dto.setStageList("Phát hành"); } else if (nicData !=
						 * null && nicData.getStatus().equals("PERSONALIZED") &&
						 * StringUtils.isEmpty(nicData.getPackageId()) &&
						 * (StringUtils.isEmpty(dto .getPersonRegStatus()) ||
						 * dto .getPersonRegStatus().equals("09"))) {
						 * dto.setStageList("Đã đóng gói"); } else if (nicData
						 * != null && nicData.getStatus().equals("PERSONALIZED")
						 * && StringUtils.isEmpty(nicData.getPackageId()) &&
						 * dto.getPersonRegStatus().equals("02")) {
						 * dto.setStageList("Hoàn thành in"); }
						 */

						if (dto.getStatus().equals("ISSUANCE") && dto.getDateIssuance().compareTo(new Date())>0) {
							dto.setStageList("Có hiệu lực");
						} else if (dto.getStatus().equals("CANCELLED")) {
							dto.setStageList("Hủy");
						} else if (dto.getStatus().equals("DAMAGED")) {
							dto.setStageList("Hỏng");
						} else if (dto.getStatus().equals("ISSUANCE")) {
							dto.setStageList("Khởi tạo");
						} else if (dto.getStatus().equals("ISSUANCE")&& dto.getDateIssuance().compareTo(new Date())<0) {
							dto.setStageList("Hết hạn");
						} else if (dto.getStatus().equals("INIT")) {
							dto.setStageList("Khởi tạo");
						} else if (dto.getStatus().equals("ISSUANCE")) {
							dto.setStageList("Phát hành");
						}else if (dto.getStatus().equals("LOST")) {
							dto.setStageList("Mất");
						}else if (dto.getStatus().equals("PACKED")) {
							dto.setStageList("Đã đóng gói");
						}else if (dto.getStatus().equals("PERSONALIZED")) {
							dto.setStageList("Hoàn thành in");
						}
					}
					res.setRows(list);
				}
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PaginatedResult<>(0, pageNo,
				new ArrayList<NicUploadJobDto>());
	}

	@Override
	public PaginatedResult<NicUploadJobDto> allTransactionStorage(
			String transactionId, String recieptNo, String passportNo,
			String nin, String fullName, String passportStyle, String ppStage,
			String typeInves, int pageNo, int pageSize) throws Exception {
		try {

			/*
			 * PaginatedResult<NicUploadJobDto> res = dao.allTransactionStorage(
			 * transactionId, recieptNo, passportNo, nin, fullName,
			 * passportStyle, ppStage, typeInves, pageNo, pageSize);
			 */
			PaginatedResult<NicUploadJobDto> res = dao
					.getResultTransactionInStore(transactionId, recieptNo,
							passportNo, nin, fullName, passportStyle, ppStage,
							typeInves, pageNo, pageSize);
			if (res != null) {
				if (res.getRows().size() > 0) {
					List<NicUploadJobDto> list = res.getRows();
					int i = 1;
					for (NicUploadJobDto dto : list) {

						NicDocumentData docData = documentDataDao
								.getNicDocumentDataById(dto.getTransactionId())
								.getModel();
						NicListHandover handover = listHandoverService
								.findHandoverByTransactionId(
										dto.getTransactionId(), 8, 1, true);
						if (docData != null)
							dto.setPassportNo(docData.getId().getPassportNo());
						dto.setPriority(0);
						if (dto.getPassportStyle().equals("N")) {
							dto.setPriority(1);
						}
						// NicTransactionPackage tp =
						// nicTranPackageService.findTransactionByIdOrType(dto.getTransactionId(),
						// 7);
						dto.setPassportType(codesService
								.getCodeValueDescByIdName(
										RegistrationConstants.CODE_ID_PASSPORT_TYPE,
										dto.getPassportType(), ""));
						SiteRepository site = siteService.getSiteRepoById(dto
								.getNicSiteCode());
//						if (site != null)
//							dto.setRicName(site.getSiteName());
//						dto.setFullName(HelperClass.createFullName(
//								dto.getSubName(), dto.getMidName(),
//								dto.getFirstName()));
						// dto.setDateIssuce(HelperClass.convertStringToString(dto.getDateIssuce().split(" ")[0]));
						// dto.setDateEprity(HelperClass.convertStringToString(dto.getDateEprity().split(" ")[0]));
//						dto.setEsColectionDate(HelperClass
//								.convertStringToString(dto.getEsColectionDate()
//										.split(" ")[0]));
						if (docData != null && dto.getTranStatus().equals("PERSO_PRINTED")) {
							dto.setTransactionStatus("Đã in");
						} else if (dto.getTranStatus().equals("RIC_UPLOADED")) {
							dto.setTransactionStatus("Khởi tạo");
						} else if (dto.getTranStatus().equals("RIC_ISSUED")) {
							dto.setTransactionStatus("Đã trả hộ chiếu cho CD");
						} else if (dto.getTranStatus().equals("INVESTIGATION_ASSIGNED")) {
							dto.setTransactionStatus("Chưa xử lý");
						} else if (dto.getTranStatus().equals("APPROVE_K")) {
							dto.setTransactionStatus("Đã từ chối");
						} else if (dto.getTranStatus().equals("APPROVE_D")) {
							dto.setTransactionStatus("Đã phê duyệt");
						} else if (dto.getTranStatus().equals("CREATED_B")) {
							dto.setTransactionStatus("Đã lập đề xuất");
						} else if (dto.getTranStatus().equals("APPROVE_C")) {
							dto.setTransactionStatus("Đợi bổ sung");
						} else if (dto.getTranStatus().equals("INVESTIGATION_SAVED")) {
							dto.setTransactionStatus("Đã xử lý");
						} else if (dto.getTranStatus().equals("INVESTIGATION_PROCESSING")) {
							dto.setTransactionStatus("Đang xử lý");
						}
						
						//lấy trạng thái hc
						if (dto.getPersoCheckStatus().equals("ISSUANCE")&& dto.getDateIssuance().compareTo(new Date()) > 0) {
							dto.setStatus("Có hiệu lực");
						} else if (dto.getPersoCheckStatus().equals("CANCELLED")) {
							dto.setStatus("Hủy");
						} else if (dto.getPersoCheckStatus().equals("DAMAGED")) {
							dto.setStatus("Hỏng");
						} else if (dto.getPersoCheckStatus().equals("ISSUANCE")&& dto.getDateIssuance().compareTo(new Date()) < 0) {
							dto.setStatus("Hết hạn");
						} else if (dto.getPersoCheckStatus().equals("INIT")) {
							dto.setStatus("Khởi tạo");
						} else if (dto.getPersoCheckStatus().equals("ISSUANCE")) {
							dto.setStatus("Phát hành");
						} else if (dto.getPersoCheckStatus().equals("LOST")) {
							dto.setStatus("Mất");
						} else if (dto.getPersoCheckStatus().equals("PACKED")) {
							dto.setStatus("Đã đóng gói");
						} else if (dto.getPersoCheckStatus().equals("PERSONALIZED")) {
							dto.setStatus("Hoàn thành in");
						}
						dto.setStt(i);
						i++;
					}
					res.setRows(list);
				}
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PaginatedResult<NicUploadJobDto>(0, pageNo,
				new ArrayList<NicUploadJobDto>());
	}

	@Override
	public long getTracuuhosohcCount(NicUploadJobDto model) throws Exception {
		try {
			return dao.getTracuuhosohcCount(model);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return 0;
	}

	@Override
	public List<NicUploadJobDto> allTracuuhosohc(NicUploadJobDto model,
			int pageNumber, int pageSize) {

		List<NicUploadJobDto> result = null;
		try {
			result = dao.allTracuuhosohc(model, pageNumber, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public PaginatedResult<NicUploadJobDto> allPassportDestroy(
			String passportNo, String[] status, String toDay, int pageNo,
			int pageSize) throws Exception {
		PaginatedResult<NicUploadJobDto> res = dao.allPassportDestroy(
				passportNo, status, toDay, pageNo, pageSize);
		if (res != null) {
			if (res.getRows().size() > 0) {
				List<NicUploadJobDto> list = res.getRows();
				for (NicUploadJobDto dto : list) {
					dto.setFullName(HelperClass.createFullName(
							dto.getSubName(), dto.getMidName(),
							dto.getFirstName()));
					if (StringUtils.isNotEmpty(dto.getDob())) {
						String dob = HelperClass.convertStringToString(dto
								.getDob().split(" ")[0]);
						dto.setDob(HelperClass.loadDateOfBirth(dob,
								dto.getKindDob()));
					}
					dto.setGender(dto.getGender().equals("M") ? "Nam" : "Nữ");
					String pob = codesService.getCodeValueDescByIdName(
							"DISTRICT", dto.getPlaceOfBirth(), "");
					if (StringUtils.isNotEmpty(pob))
						dto.setPlaceOfBirth(pob);
				}
			}
		}
		return res;
	}

	@Override
	public List<NicUploadJobDto> searchUpdateB(String packageId,
			Date createDate, Date endDate) throws Exception {
		try {
			String cDate = HelperClass.convertDateToString2(createDate);
			String eDate = HelperClass.convertDateToString2(endDate);
			return dao.searchUpdateB(packageId,
					HelperClass.convertStringToString3(cDate),
					HelperClass.convertStringToString3(eDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<NicUploadJobDto> listPackageIDPerso(String code, String packId) {

		List<NicUploadJobDto> result = null;
		try {
			result = dao.listPackageIDPerso(code, packId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public PaginatedResult<NicUploadJobDto> allListBProcessPerso(
			String packageId, String dateApprove, String siteCode, int pageNo,
			int pageSize) {
		PaginatedResult<NicUploadJobDto> result = null;
		try {
			result = dao.allListBProcessPerso(packageId, dateApprove, siteCode,
					pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<NicUploadJobDto> listPackageIDPerso(String code)
			throws Exception {
		List<NicUploadJobDto> result = null;
		try {
			result = dao.listPackageIDPerso(code);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public BaseModelSingle<NicUploadJobDto> listPackageIDConfig(
			String packageId, Date todayTime) throws Exception {
		try {
			String strToday = HelperClass.convertDateToString1(todayTime);
			BaseModelList<NicUploadJobDto> list = dao.listPackageIDConfig(
					packageId, strToday);
			if (list.getListModel() != null && list.getListModel().size() > 0)
				return new BaseModelSingle<NicUploadJobDto>(list.getListModel()
						.get(0), true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<NicUploadJobDto>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - listPackageIDConfig - " + packageId
							+ " - thất bại.");
		}
		return new BaseModelSingle<NicUploadJobDto>(null, true, null);
	}

	@Override
	public NicUploadJobDto listPackageIDDefault(String packageId, String type)
			throws Exception {
		try {
			List<NicUploadJobDto> list = dao.listPackageIDDefault(packageId,
					type);
			if (list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PaginatedResult<ImmiHistory> getAllImmihistory(String fullName,
			Date dob, String gender, String passportNo, String visaNo,
			String nin, String nationality, int pageNo, int pageSize) {
		// return immiHistoryDao.getAllImmihistory(fullName, null, gender,
		// passportNo, visaNo, nin, nationality, pageNo, pageSize);
		return null;
	}

	@Override
	public String getSitePerso(String transactionId) throws Exception {
		return dao.getSitePerso(transactionId);
	}

	public BaseModelSingle<String> saveTransaction(NicTransaction tran)
			throws Exception {
		try {
			return this.dao.saveTransaction(tran);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<String>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - updateOrSaveTransaction - "
							+ tran.getTransactionId() + " - thất bại.");

		}
	}

	@Override
	public BaseModelSingle<NicTransaction> findTransactionById(
			String transactionId) {
		try {
			return this.dao.findNicTransactionById(transactionId);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<NicTransaction>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findByTransactionId - " + transactionId
							+ " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateTransaction(NicTransaction tran)
			throws Exception {
		try {
			this.dao.saveOrUpdateTransaction(tran);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException.createMessageException(e)
							+ " - updateOrSaveTransaction - "
							+ tran.getTransactionId() + " - thất bại.");
		}
	}

	@Override
	public List<NicTransaction> findTranByPersonCode(String personCode)
			throws Exception {
		List<NicTransaction> list = null;
		try {
			list = this.dao.findTranByPersonCode(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findTranByPersonCode thất bại.");
		}
		return list;
	}

	@Override
	public List<NicTransaction> findTranByListPersonCode(
			List<EppPerson> listPerson) throws Exception {
		List<NicTransaction> list = null;
		try {
			String[] listPersonCode = new String[listPerson.size()];
			int index = 0;
			for (EppPerson eppPerson : listPerson) {
				listPersonCode[index] = eppPerson.getPersonCode();
				index++;
			}
			if (listPersonCode.length > 0) {
				list = this.dao.findTranByPersonCode(listPersonCode);
			}
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findTranByListPersonCode thất bại.");
		}
		return list;
	}

	@Override
	public List<NicTransaction> findTranByArchiveCode(String archiveCode)
			throws Exception {
		List<NicTransaction> listTran = null;
		try {
			listTran = this.dao.findTranByArchiveCode(archiveCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findTranByArchiveCode" + archiveCode
							+ "- thất bại.");
		}
		return listTran;
	}

	@Override
	public List<NicTransaction> findTranByNinAndTypePassport(String nin,
			String passportType) throws Exception {
		List<NicTransaction> listTran = null;
		try {
			listTran = this.dao.findTranByNinAndTypePassport(nin, passportType);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findTranByNinAndTypePassport" + nin
							+ "- thất bại.");
		}
		return listTran;
	}

	@Override
	public List<NicTransaction> findAllByListTranId(String[] listTranId)
			throws Exception {
		List<NicTransaction> listRs = null;
		try {
			listRs = this.dao.findAllByListTranId(listTranId);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findAllByListTranId thất bại.");
		}
		return listRs;
	}

	@Override
	public PaginatedResult<NicTransaction> getPageByRegDateOrRegSite(
			String fromDate, String toDate, String regSiteCode, int pageNo,
			int pageSize) throws Exception {
		PaginatedResult<NicTransaction> pagRs = null;
		try {
			Date fDate = null;
			Date tDate = null;
			if (StringUtils.isNotBlank(fromDate)) {
				fDate = DateUtil.strToDate(fromDate, DateUtil.FORMAT_YYYYMMDD);
			}
			if (StringUtils.isNotBlank(toDate)) {
				tDate = new Date(DateUtil.strToDate(toDate,
						DateUtil.FORMAT_YYYYMMDD).getTime() + 86400000L);
			}
			pagRs = this.dao.getPageByRegDateOrRegSite(fDate, tDate,
					regSiteCode, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagRs;
	}

	@Override
	public List<CountTranStatus> countTranByStatusAndCoditions(String fromDate,
			String toDate, String regSiteCode) {
		List<CountTranStatus> listCount = null;
		try {
			Date fDate = null;
			Date tDate = null;
			if (StringUtils.isNotBlank(fromDate)) {
				fDate = DateUtil.strToDate(fromDate, DateUtil.FORMAT_YYYYMMDD);
			}
			if (StringUtils.isNotBlank(toDate)) {
				tDate = new Date(DateUtil.strToDate(toDate,
						DateUtil.FORMAT_YYYYMMDD).getTime() + 86400000L);
			}
			List<NicTransaction> list = this.dao.findByCoditions(fDate, tDate,
					regSiteCode);
			if (list != null && list.size() > 0) {
				listCount = new ArrayList<CountTranStatus>();
				for (NicTransaction nicTransaction : list) {
					boolean check = true;
					for (CountTranStatus count : listCount) {
						if (nicTransaction.getTransactionStatus().equals(
								count.getTransactionStatus())) {
							count.setCount(count.getCount() + 1);
							check = false;
							break;
						}
					}
					if (check) {
						CountTranStatus count = new CountTranStatus();
						count.setCount(1);
						count.setTransactionStatus(nicTransaction
								.getTransactionStatus());
						listCount.add(count);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Override
	public List<NicTransaction> getByRegDateOrRegSite(String fromDate,
			String toDate, String regSiteCode) {
		List<NicTransaction> listTran = null;
		try {
			Date fDate = null;
			Date tDate = null;
			if (StringUtils.isNotBlank(fromDate)) {
				fDate = DateUtil.strToDate(fromDate, DateUtil.FORMAT_YYYYMMDD);
			}
			if (StringUtils.isNotBlank(toDate)) {
				tDate = new Date(DateUtil.strToDate(toDate,
						DateUtil.FORMAT_YYYYMMDD).getTime() + 86400000L);
			}
			listTran = this.dao
					.getByRegDateOrRegSite(fDate, tDate, regSiteCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listTran;
	}

	@Override
	public PaginatedResult<ReportRegProcessData> getPageByCodition(
			String fromDate, String toDate, String regSiteCode,
			String printSiteCode, int pageNo, int pageSize) {
		PaginatedResult<ReportRegProcessData> result = null;
		try {
			result = dao.getPageByCodition(fromDate, toDate, regSiteCode,
					printSiteCode, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<ReportRegProcessData> getAllByCodition(String fromDate,
			String toDate, String regSiteCode, String printSiteCode) {
		List<ReportRegProcessData> result = null;
		try {
			result = dao.getAllByCodition(fromDate, toDate, regSiteCode,
					printSiteCode);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	@Override
	public List<Integer> getCountTransmissFromA08() throws Exception {
		return this.dao.getCountTransmissFromA08();
	}
}