package service.transaction.bussiness;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import service.perso.bussiness.PersoService;
import service.perso.model.TransactionPerso;
import service.perso.model.UpdatePackageRequest;
import service.perso.util.Contants;
import service.transaction.dto.InfoFamilys;
import service.transaction.model.ArchiveCode;
import service.transaction.model.ArchiveDocument;
import service.transaction.model.DetailHandover;
import service.transaction.model.DetailHandoverB;
import service.transaction.model.DetailHandoverC;
import service.transaction.model.EppBufPerson;
import service.transaction.model.EppBufPersonDoc;
import service.transaction.model.FeeRecieptPaymentModel;
import service.transaction.model.FindPassportInput;
import service.transaction.model.FindPassportRestoreInput;
import service.transaction.model.FullTransactionDownloadModel;
import service.transaction.model.FullTransactionModel;
import service.transaction.model.HandoverA;
import service.transaction.model.HandoverAStatus;
import service.transaction.model.HandoverB;
import service.transaction.model.HandoverBCancelled;
import service.transaction.model.HandoverC;
import service.transaction.model.HandoverPA;
import service.transaction.model.HistoryPassportInfo;
import service.transaction.model.InfoDocRestoreToPrintMemo;
import service.transaction.model.InfoDocToPrintMemo;
import service.transaction.model.InfoDocumentCanceled;
import service.transaction.model.InfoFindCancelDocToPrintMemo;
import service.transaction.model.InfoJoinPerson;
import service.transaction.model.InfoPassportC;
import service.transaction.model.InfoPerson;
import service.transaction.model.InfoProcess;
import service.transaction.model.InfoValidPassport;
import service.transaction.model.ListDocument;
import service.transaction.model.OrgPersonDetailToHanding;
import service.transaction.model.OrgPersonOutput;
import service.transaction.model.PassportCancelDetail;
import service.transaction.model.PassportCancelInput;
import service.transaction.model.PassportLostInput;
import service.transaction.model.PassportLostOutput;
import service.transaction.model.PassportRestoreDetail;
import service.transaction.model.PassportStatusInfo;
import service.transaction.model.PaymentDetail;
import service.transaction.model.PaymentFeeInput;
import service.transaction.model.PersonFamily;
import service.transaction.model.PersonModel;
import service.transaction.model.PersonOutput;
import service.transaction.model.PersonToHandingDetail;
import service.transaction.model.PersonToMatchDetail;
import service.transaction.model.QueueInfo;
import service.transaction.model.ReceiptBill;
import service.transaction.model.ReceiptManager;
import service.transaction.model.RegistrationData;
import service.transaction.model.Response;
import service.transaction.model.ResponseBase;
import service.transaction.model.SeparatePersonalData;
import service.transaction.model.StatusInfo;
import service.transaction.model.StatusPassports;
import service.transaction.model.Transaction;
import service.transaction.model.TransactionDetailToHanding;
import service.transaction.model.TransactionDocument;
import service.transaction.model.TransactionIdInput;
import service.transaction.model.TransactionLost;
import service.transaction.model.TransactionStatusInfo;
import service.transaction.model.UpdatePassportModel;
import service.transaction.model.UpdateTranStatusProcess;
import service.transaction.model.UpdateTransactionDetail;
import sg.com.nec.spid.SPID_FMS_Tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.job.dto.ImmiHistoryData;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.dao.LogCheckConnectionDao;
import com.nec.asia.nic.comp.trans.dao.NicFPDataDao;
import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionPaymentDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.dao.SyncQueueJobDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.ConfigurationApi;
import com.nec.asia.nic.comp.trans.domain.ConfigurationWorkflow;
import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppArchive;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppIssuance;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.comp.trans.domain.EppMatchPersonHistory;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.FeeRecieptPayment;
import com.nec.asia.nic.comp.trans.domain.LogCheckConnection;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicDocumentHistory;
import com.nec.asia.nic.comp.trans.domain.NicFPData;
import com.nec.asia.nic.comp.trans.domain.NicFPDataId;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLost;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.domain.SynQueueJobXnc;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.LdsRequestWsDto;
import com.nec.asia.nic.comp.trans.dto.LdsResponseWsDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.ArchiveCodeService;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.ConfigurationApiService;
import com.nec.asia.nic.comp.trans.service.ConfigurationWorkflowService;
import com.nec.asia.nic.comp.trans.service.DataPackService;
import com.nec.asia.nic.comp.trans.service.DetailRecieptFeeService;
import com.nec.asia.nic.comp.trans.service.DetailRecieptService;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.DocumentHistoryService;
import com.nec.asia.nic.comp.trans.service.EppArchiveService;
import com.nec.asia.nic.comp.trans.service.EppIssuanceService;
import com.nec.asia.nic.comp.trans.service.EppListHandoverDetailService;
import com.nec.asia.nic.comp.trans.service.EppMatchPersonHistoryService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.FeeRecieptPaymentService;
import com.nec.asia.nic.comp.trans.service.LogCheckConnectionService;
import com.nec.asia.nic.comp.trans.service.NicImmiHistoryService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionLostService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPaymentDetaiService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.RecieptManagerService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.comp.trans.service.SynQueueJobXncService;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;
import com.nec.asia.nic.comp.trans.service.TempPaymentDetailService;
import com.nec.asia.nic.comp.trans.service.TempTranPackageService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.utils.FmsTemplateXmlConvertor;
import com.nec.asia.nic.comp.trans.utils.TransDTOMapper;
import com.nec.asia.nic.comp.trans.utils.type.FingerprintSection;
import com.nec.asia.nic.comp.trans.utils.type.FingerprintTemplate;
import com.nec.asia.nic.comp.trans.utils.type.FmsTemplate;
import com.nec.asia.nic.comp.ws.log.dao.EppWsLogDao;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.comp.ws.log.service.EppWsLogService;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.StatusCode;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.exception.ParametersServiceException;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.utils.ImageUtil;

@Path("/syncTran/")
@WebService
@Provider
public class TransactionService {

	public static final String TX_UPLOAD_ERROR_CODE_DUPLICATE_TRANSACTION_ID = "UL-E1001";
	public static final String OBJ_TYPE_QUEUE_XNC_PP = "PP";
	public static final String OBJ_TYPE_QUEUE_CREATE_PERSON = "PS";
	public static final String CONFIG_TYPE_XU_LY = "XL";
	public static final String CONFIG_TYPE_CA_THE_HOA = "IN";
	public static final String CONFIG_TYPE_PHAT_HANH = "PH";
	public static final String STR_FORMAT_DATE_yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String STR_FORMAT_DATE_yyyyMMdd = "yyyyMMdd";
	public static final String TRAN_STATUS_CREATED = "CREATED";
	public static final String TRAN_STATUS_SUBMIT_PA_A = "SUBMIT_PA_A";
	public static final String PARAMETERS_NAME_ISSUING_AUTHORITY = "ISSUING_AUTHORITY";
	public static final String PARAMETERS_SCOPE_SYSTEM = "SYSTEM";
	public static final String TRAN_STATUS_UPDATED = "UPDATED";

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private NicUploadJobDao nicUploadJobDao;

	@Autowired
	private NicFPDataDao nicFPDataDao;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicRegistrationDataService nicRegistrationDataService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private NicTransactionPaymentDao nicTransactionPaymentDao;

	@Autowired
	private TransDTOMapper mapper = null;

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private ParametersDao parametersDao;

	private FmsTemplateXmlConvertor xmlConvertor = new FmsTemplateXmlConvertor();

	@Autowired
	NicRegistrationDataDao registrationDataDao;

	@Autowired
	NicTransactionPaymentDao transactionPaymentDao;

	@Autowired
	private NicTransactionAttachmentDao transactionDocumentDao;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private TransactionLogService nicTransactionLogService = null;

	@Autowired
	private SyncQueueJobService queueJobService;

	@Autowired
	private SyncQueueJobDao queueJobDao;

	@Autowired
	private EppPersonService eppPersonService;

	@Autowired
	private ListHandoverService listHandoverService;

	@Autowired
	private UserService userService;

	@Autowired
	private DocumentDataService documentDataService;

	@Autowired
	private NicTransactionPackageService nicTransactionPackageService;

	@Autowired
	private BufPersonInvestigationService bufPersonInvestigationService;

	@Autowired
	private NicSearchHitResultService nicSearchHitResultService;

	@Autowired
	private RecieptManagerService rmService;

	@Autowired
	private DetailRecieptService drService;

	@Autowired
	private DetailRecieptFeeService drFeeService;

	@Autowired
	private NicTransactionPaymentDetaiService paymentDetailService;

	@Autowired
	private DocumentHistoryService documentHistoryService;

	@Autowired
	private NicTransactionLostService transactionLostService;

	@Autowired
	private NicImmiHistoryService immiHistoryService;

	@Autowired
	private SynQueueJobXncService queueJobXncService;

	@Autowired
	private BorderGateService borderGateService;

	@Autowired
	private TempTranPackageService tempTranPackageService;

	@Autowired
	private TempPaymentDetailService tempPaymentDetailService;

	@Autowired
	private ConfigurationWorkflowService configurationWorkflowService;

	@Autowired
	private ConfigurationApiService configurationApiService;

	@Autowired
	private EppListHandoverDetailService eppListHandoverDetailService;

	@Autowired
	private FeeRecieptPaymentService feeRecieptPaymentService;

	@Autowired
	private DataPackService dataPackService;

	@Autowired
	private DocumentDataDao documentDataDao;

	@Autowired
	private EppWsLogDao eppWsLogDao;

	@Autowired
	private EppWsLogService eppWsLogService;

	@Autowired
	private LogCheckConnectionDao logCheckConnectionDao;

	@Autowired
	private LogCheckConnectionService logCheckConnectionService;

	@Autowired
	private SiteRepositoryDao siteRepositoryDao;

	@Autowired
	private RptStatisticsTransmitDataService rptService;

	@Autowired
	private ArchiveCodeService archiveCodeService;

	@Autowired
	private EppArchiveService eppArchiveService;

	@Autowired
	private EppIssuanceService eppIssuanceService;

	@Autowired
	private EppMatchPersonHistoryService eppMatchPersonHistoryService;

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadTransaction")
	public ResponseBase uploadTransaction(Transaction transaction)
			throws JAXBException {

		ResponseBase rep = new ResponseBase();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "uploadTransaction";
		// if(!this.checkConnectApi(nameApi, transaction.getRegSiteCode())){
		// rep.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return rep;
		// }
		// logger.info("start upload Transaction, transactionId: " +
		// transaction.getTransactionId() + ", time: " +
		// HelperClass.convertDateToString1(new Date()));
		Date startTime = new Date();
		String logData = null;
		String refId = null;
		String txnStatus = Contants.TRANSACTION_STATUS_TX_UPLOAD_COMPLETED;
		String wkstnId = Contants.CREATE_BY_SYSTEM;
		String officerId = Contants.CREATE_BY_SYSTEM;
		String siteCode = "";
		Boolean stageUpdate = false;
		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_HS);
		eppWsLog.setUrlRequest(Contants.URL_UPLOAD_TRANSACTION);

		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(transaction));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + "convert object to json fail.");
			e1.printStackTrace();

		}

		// Kiểm tra Transaction đã đầy đủ thông tin hay chưa.
		/*
		 * ResponseBase error = checkValidateTransaction(transaction); if (error
		 * != null) { return error; }
		 */
		// set keyId for Ws_Log
		eppWsLog.setKeyId(transaction.getTransactionId());
		try {
			NicTransaction nicTransactionDBO = null;
			NicRegistrationData nicRegDataDBO = null;
			Collection<NicTransactionAttachment> nicTransDocDBOList = null;

			refId = transaction.getTransactionId();
			siteCode = transaction.getRegSiteCode();

			// Kiểm tra tồn tại Transaction.
			BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
					.findTransactionById(transaction.getTransactionId());
			if (!baseFindTran.isError() || baseFindTran.getMessage() != null) {
				return this.checkExistAndSaveException(baseFindTran, eppWsLog,
						rep, transaction);
			}
			NicTransaction transactionTemp = baseFindTran.getModel();
			String transactionId = transaction.getTransactionId();
			if (transactionTemp != null) {

				// Chuyển sang Update
				stageUpdate = true;
				rep.setCode(Contants.CODE_DATA_EXIST);
				rep.setMessage("Hồ sơ: " + transactionId
						+ Contants.MESSAGE_EXIST);

				return rep;
				/*
				 * String errorCode =
				 * TX_UPLOAD_ERROR_CODE_DUPLICATE_TRANSACTION_ID; logger.error(
				 * "Transaction cannot be uploaded as duplicate transaction Id found.[{}], errorCode:{}"
				 * , transactionId, errorCode); throw new
				 * TransactionServiceException(
				 * "Transaction cannot be uploaded as duplicate transaction Id found."
				 * , errorCode);
				 */
			}
			nicTransactionDBO = this.convertNicTransaction(transaction, false);
			BaseModelSingle<NicTransaction> baseApplyNT = nicTransactionService
					.applyNameTruncation(nicTransactionDBO);
			if (!baseApplyNT.isError() || baseApplyNT.getMessage() != null) {
				return this.checkExistAndSaveException(baseApplyNT, eppWsLog,
						rep, transaction);
			}
			// this.validateTransaction(transaction);
			/*
			 * transactionTemp = nicTransactionService.findById(transactionId);
			 * if (transactionTemp!=null) { String errorCode =
			 * TX_UPLOAD_ERROR_CODE_DUPLICATE_TRANSACTION_ID; logger.error(
			 * "Transaction cannot be uploaded as duplicate transaction Id found.[{}], errorCode:{}"
			 * , transactionId, errorCode); throw new
			 * TransactionServiceException
			 * ("Transaction cannot be uploaded as duplicate transaction Id found."
			 * , errorCode); }
			 */
			BaseModelSingle<String> baseSaveTran = nicTransactionService
					.saveTransaction(nicTransactionDBO);
			if (!baseSaveTran.isError() || baseSaveTran.getMessage() != null) {
				return this.checkExistAndSaveException(baseSaveTran, eppWsLog,
						rep, transaction);
			}
			// String transactionResult = baseSaveTran.getModel();
			// logger.debug("saving nicTransaction, transactionId:{}",
			// transactionResult);
			// nicTransactionService.saveOrUpdate(nicTransactionDBO);
			// Boolean checkTemp = true;
			// String packageA = "";
			if (nicTransactionDBO.getNicRegistrationData() != null) {

				nicRegDataDBO = nicTransactionDBO.getNicRegistrationData();// mapper.parseRegistrationDataDBO(transactionId,
																			// nin,
																			// regDataDTO);
				nicTransDocDBOList = nicTransactionDBO
						.getNicTransactionAttachments();// mapper.parseTransactionDocDBOList(transactionId,
														// regDataDTO);

				BaseModelSingle<String> baseSaveRegisData = registrationDataDao
						.saveNicRegistrationData(nicRegDataDBO);
				if (!baseSaveRegisData.isError()
						|| baseSaveRegisData.getMessage() != null) {
					return this.checkExistAndSaveException(baseSaveRegisData,
							eppWsLog, rep, nicRegDataDBO);
				}
				// String regisDataResult = baseSaveRegisData.getModel();
				// logger.debug("saving registrationData, transactionId:{}",
				// regisDataResult);
				// registrationDataDao.saveOrUpdate(nicRegDataDBO);

				if (!stageUpdate) {
					// [2017] save payment
					if (nicTransactionDBO.getNicTransactionPayment() != null
							&& StringUtils.isNotBlank(nicTransactionDBO
									.getNicTransactionPayment().getPaymentId())) {
						BaseModelSingle<String> baseSaveTP = transactionPaymentDao
								.saveTranPayment(nicTransactionDBO
										.getNicTransactionPayment());
						if (!baseSaveTP.isError()
								|| baseSaveTP.getMessage() != null) {
							return this.checkExistAndSaveException(baseSaveTP,
									eppWsLog, rep, nicTransactionDBO
											.getNicTransactionPayment());
						}
						// String paymentDataResult = baseSaveTP.getModel();
						// logger.debug("saving paymentData, transactionId:{}",
						// paymentDataResult);
						// transactionPaymentDao.saveOrUpdate(nicTransactionDBO.getNicTransactionPayment());
					}
				}
				for (NicTransactionAttachment nicTransDoc : nicTransDocDBOList) {
					if (StringUtils.isBlank(nicTransDoc.getTransactionId())) {
						// logger.warn("nicTransDoc.getTransactionId() is null");
						if (nicTransactionDBO != null) {
							nicTransDoc.setTransactionId(nicTransactionDBO
									.getTransactionId());
							// logger.warn("nicTransDoc.getTransactionId():{}",
							// nicTransactionDBO.getTransactionId());
						}
					}
					// logger.debug("saving transactionDoc, transactionId:{} docType:{}",
					// nicTransactionDBO.getTransactionId(),
					// nicTransDoc.getDocType());
					// transactionDocumentDao.saveOrUpdate(nicTransDoc);
					BaseModelSingle<Long> baseSaveTranDoc = transactionDocumentDao

					.saveTranAttachment(nicTransDoc);
					if (!baseSaveTranDoc.isError()
							|| baseSaveTranDoc.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveTranDoc,
								eppWsLog, rep, nicTransactionDBO);
					}
					// Long docId = baseSaveTranDoc.getModel();
					// nicTransDoc.set
					// logger.debug("saving transactionDoc, transactionId:{} docType:{} --> docId: {}/{}",
					// new Object[] {nicTransactionDBO.getTransactionId(),
					// nicTransDoc.getDocType(), docId,
					// nicTransDoc.getTransactionDocId()});
				}

				/* Thêm tạo person khi upload hồ sơ */
				// this.savePersonByUpload(transaction); Đóng 28-03-2020

				List<NicTransactionLog> transLogList = new ArrayList<NicTransactionLog>();
				NicTransactionLog dcmTransLog = new NicTransactionLog();
				dcmTransLog.setRefId(nicTransactionDBO.getTransactionId());
				dcmTransLog.setLogCreateTime(nicTransactionDBO
						.getCreateDatetime());
				dcmTransLog.setOfficerId(nicTransactionDBO.getCreateBy());
				dcmTransLog.setSiteCode(nicTransactionDBO.getRegSiteCode());
				dcmTransLog.setStartTime(nicTransactionDBO.getCreateDatetime());

				dcmTransLog
						.setTransactionStage(Contants.TRANSACTION_STATE_TX_UPLOAD);
				dcmTransLog.setTransactionStatus(TransactionStatus.Captured
						.getKey());
				transLogList.add(dcmTransLog);
				// List<NicTransactionLog> errorList = nicTransactionService
				// .saveTransactionLogs(transLogList);
				// for (NicTransactionLog errorLog : errorList) {
				// logger.warn("Failed to save log: {}, {}, {}, {}", new
				// Object[] {errorLog.getRefId(),
				// errorLog.getTransactionStage(),
				// errorLog.getTransactionStatus(),
				// errorLog.getSiteCode()});
				// }
				// Xử lý trường hợp danh sách vào trước hồ sơ vào sau
				/*
				 * try {
				 * //logger.info("Add list detail with the case list first.");
				 * TempTransactionPackage tempTran =
				 * tempTranPackageService.findTempByPackageIdOrTranId(null,
				 * transactionId, 7); if(tempTran != null){ packageA =
				 * tempTran.getPackageId(); EppListHandoverDetail tpLast = new
				 * EppListHandoverDetail();
				 * tpLast.setPackageId(tempTran.getPackageId());
				 * tpLast.setTransactionId(tempTran.getTransactionId());
				 * tpLast.setProposalStage(tempTran.getOfferStage());
				 * tpLast.setApproveStage(tempTran.getApproveStage());
				 * tpLast.setProposalNote(tempTran.getOfferNote());
				 * tpLast.setApproveNote(tempTran.getApproveNote());
				 * eppListHandoverDetailService.saveTranPackage(tpLast); }
				 * 
				 * if(StringUtils.isNotEmpty(packageA)){ NicListHandover hanA =
				 * listHandoverService.findByPackageId(packageA);
				 * List<EppListHandoverDetail> detailA =
				 * eppListHandoverDetailService
				 * .getListPackageByPackageId(packageA); if(hanA != null &&
				 * detailA != null && hanA.getCountTransaction() ==
				 * detailA.size()){ checkTemp = false; } } if(!stageUpdate){
				 * List<TempPaymentDetail> payList =
				 * tempPaymentDetailService.findTempByTransactionId
				 * (transactionId); if(payList != null && payList.size() > 0){
				 * for(TempPaymentDetail payment : payList){
				 * NicTransactionPaymentDetail pay = new
				 * NicTransactionPaymentDetail();
				 * pay.setPaymentId(nicTransactionDBO
				 * .getNicTransactionPayment().getPaymentId());
				 * pay.setTypePayment(payment.getTypePayment());
				 * pay.setSubTypePayment(payment.getSubtypePayment());
				 * pay.setPaymentAmount(payment.getPaymentAmount());
				 * pay.setNote(payment.getNote());
				 * pay.setStatusFee(payment.getStatusFee().equals("Y") ? true :
				 * false); pay.setCreateBy(Contants.CREATE_BY_SYSTEM);
				 * pay.setCreateDate(new Date());
				 * paymentDetailService.saveOrUpdatePaymentDetail(pay); } } }
				 * 
				 * } catch (Exception e) { logger.error(
				 * "Error when the add list detail with the case list first. mess: "
				 * + e.getMessage()); }
				 */
			}
			// Step 2: Create Nic Job
			Integer jobPriority = nicTransactionDBO.getPriority();
			if (jobPriority == null) {
				jobPriority = NicUploadJob.JOB_PRIORITY_NORMAL;
			}
			Long jobId = null;
			BaseModelSingle<Long> baseCreateWJ = nicUploadJobDao
					.createWorkflowJobCheck(transactionId,
							transaction.getTransactionType(), jobPriority, 1,
							transaction.getRegisData().getPersonCode());
			if (!baseCreateWJ.isError() || baseCreateWJ.getMessage() != null) {
				return this.checkExistAndSaveException(baseCreateWJ, eppWsLog,
						rep, transaction);
			}
			jobId = baseCreateWJ.getModel();
			// logger.info("[saveMainTransaction] save NicUploadJob, transactionId:{}, jobId:{} ",
			// transactionId, jobId);

			// Step 3: Update Nic Transaction
			nicTransactionDBO.getNicRegistrationData().setWorkflowJobId(jobId);
			BaseModelSingle<Void> baseSOURegisD = registrationDataDao
					.saveOrUpdateRegisData(nicTransactionDBO
							.getNicRegistrationData());
			if (!baseSOURegisD.isError() || baseSOURegisD.getMessage() != null) {
				return this.checkExistAndSaveException(baseSOURegisD, eppWsLog,
						rep, nicTransactionDBO.getNicRegistrationData());
			}
			// logger.info("[saveMainTransaction] Update Nic Registration Data, jobId:{} ",
			// jobId);

			List<NicFPData> fpDataList = this
					.convertFPDataList(nicTransactionDBO);
			for (NicFPData fpData : fpDataList) {
				BaseModelSingle<Boolean> baseSaveFPData = nicFPDataDao
						.saveNicFPData(fpData);
				if (!baseSaveFPData.isError()
						|| baseSaveFPData.getMessage() != null) {
					return this.checkExistAndSaveException(baseSaveFPData,
							eppWsLog, rep, nicTransactionDBO);
				}
			}

			// logger.debug("saveMainTransaction end, transactionId:{}",
			// transactionId);
			// 5/12/2019 update status upload no call service by ric
			BaseModelSingle<Boolean> baseCheckStatus = this.updateStatusLog(
					transactionId, Contants.TRANSACTION_STATUS_RIC_UPLOADED);
			if (!baseCheckStatus.isError()
					|| baseCheckStatus.getMessage() != null) {
				return this.checkExistAndSaveException(baseCheckStatus,
						eppWsLog, rep, transaction);
			}
			// logger.info("Result update status end, transactionId:{0}, result:{1}",
			// transactionId, checkStatus);
			rep.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Đưa vào hàng đợi chờ đồng bộ sang các trung tâm xử lý */
			/* Đưa vào hàng đợi nếu hồ sơ này từ A */
			/*
			 * SiteGroups groupSite = siteService
			 * .findSiteGroupsByGroupId(transaction.getRegSiteCode()); if
			 * (groupSite != null) { List<SiteGroups> list =
			 * siteService.findAll(); for (SiteGroups gs : list) { if
			 * (groupSite.getGroupId().equals(gs.getGroupId())) continue;
			 * BaseModelSingle<Boolean> baseAddOQ = this.addObjToQueueJob(
			 * transactionId, Contants.QUEUE_OBJ_TYPE_HS, gs.getGroupId(),
			 * TRAN_STATUS_CREATED, null); if (!baseAddOQ.isError() ||
			 * baseAddOQ.getMessage() != null) { return
			 * this.checkExistAndSaveException(baseAddOQ, eppWsLog, rep,
			 * nicTransactionDBO.getNicRegistrationData()); } } }
			 */
			/* Lưu bảng thống kê truyền nhận */
			this.saveOrUpRptData(Contants.URL_UPLOAD_TRANSACTION, 1,
					transaction.getRegSiteCode());
			/*
			 * Phúc tạm đóng 31/12/2019 không thực hiện điều phối luồng ở bước
			 * này String siteXl = ""; ConfigurationWorkflow cfw =
			 * configurationWorkflowService
			 * .findSiteRepositoryBySite(transaction.getRegSiteCode(), new
			 * Date(), CONFIG_TYPE_XU_LY, true); if(cfw != null){ siteXl =
			 * cfw.getSiteIdTo(); }else{ SiteRepository site =
			 * siteService.getSiteRepoById(transaction.getRegSiteCode());
			 * if(site != null){ siteXl = site.getSiteGroups().getGroupId(); } }
			 * if(StringUtils.isNotEmpty(siteXl)){
			 * this.addObjToQueueJob(transactionId, Contants.QUEUE_OBJ_TYPE_HS,
			 * siteXl); }
			 */
			/*
			 * Đưa danh sách A và hồ sơ lên sau vào hàng đợi với trường hợp danh
			 * sách lên trc, hồ sơ lên sau
			 */
			/*
			 * Đóng 31-03-2020 if(!checkTemp){
			 * 
			 * List<SiteGroups> list = siteService.findAll(); for(SiteGroups gs
			 * : list){ this.addObjToQueueJob(transactionId,
			 * Contants.QUEUE_OBJ_TYPE_HS, gs.getGroupId(),
			 * TRAN_STATUS_SUBMIT_PA_A); }
			 * 
			 * Dùng điều phối luồng String siteHanA = ""; ConfigurationWorkflow
			 * cfwA =
			 * configurationWorkflowService.findSiteRepositoryBySite(transaction
			 * .getRegSiteCode(), new Date(), CONFIG_TYPE_XU_LY, true); if(cfwA
			 * != null){ siteHanA = cfwA.getSiteIdTo(); }else{ SiteRepository
			 * site = siteService.getSiteRepoById(transaction.getRegSiteCode());
			 * if(site != null){ siteHanA = site.getSiteGroups().getGroupId(); }
			 * } if(StringUtils.isNotEmpty(siteHanA)){
			 * this.addObjToQueueJob(packageA, Contants.QUEUE_OBJ_TYPE_DA,
			 * siteHanA, null); } }
			 */
			/*-----*/

			// } catch (TransactionServiceException e) {
			// logData = e.getMessage();//MiscXmlConvertor.parseObjectToXml(e);
			// txnStatus = Contants.TRANSACTION_STATUS_TX_UPLOAD_ERROR;
			// //logger.info("[saveMainTransaction] Error, refId:{}, txnStatus:{}, logData:{} ",
			// new Object[] {refId, txnStatus, logData});
			// logger.error("[saveMainTransaction] Exception: {} ",
			// e.getMessage());
			// rep.setCode(Contants.CODE_THROW_EXCEPTION);
			// rep.setMessage(Contants.MESSAGE_EXCEPTION);
			// e.printStackTrace();
			// eppWsLog.setDataResponse(rep.toString());
			// eppWsLog.setMessageErrorLog("[saveMainTransaction] Exception: " +
			// e.getMessage());
			// eppWsLog.setCreatedDate(new Date());
			// BaseModelSingle<Boolean> baseCheck =
			// eppWsLogService.inserDataTable(eppWsLog);
			// if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
			// logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			// }
			// return rep;
		} catch (Exception e) {
			logger.error(e.getMessage() + " - uploadTransaction fail.");
			e.printStackTrace();
			rep.setCode(Contants.CODE_THROW_EXCEPTION);
			rep.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(rep.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - uploadTransaction fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}

			return rep;
		} finally {
			// Step 4: Create Nic Transaction Log
			// logger.info("[saveMainTransaction] save NicTransactionLog, transactionId:{}",
			// refId);
			try {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName().toUpperCase();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			Date endTime = new Date();
			NicTransactionLog nicTransLog = new NicTransactionLog();
			nicTransLog.setRefId(refId);
			nicTransLog.setLogCreateTime(new Date());
			nicTransLog.setStartTime(startTime);
			nicTransLog.setEndTime(endTime);
			nicTransLog
					.setTransactionStage(Contants.TRANSACTION_STATE_TX_UPLOAD);
			nicTransLog.setTransactionStatus(txnStatus);
			nicTransLog.setOfficerId(officerId);
			nicTransLog.setWkstnId(wkstnId);
			nicTransLog.setSiteCode(siteCode);
			nicTransLog.setLogInfo("");
			nicTransLog.setLogData(logData);
			// Long logId = transactionLogDao.save(nicTransLog);
			try {
				BaseModelSingle<Long> baseSaveTranLog = transactionLogService
						.saveTransactionLog(refId,
								Contants.TRANSACTION_STATE_TX_UPLOAD,
								txnStatus, officerId, wkstnId, siteCode,
								startTime, endTime, "", logData);
				if (!baseSaveTranLog.isError()
						|| baseSaveTranLog.getMessage() != null) {
					rep = this.checkExistAndSaveException(baseSaveTranLog,
							eppWsLog, rep, transaction);
				}
				// Long logId = baseSaveTranLog.getModel();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			// logger.info("[saveMainTransaction] save NicTransactionLog completed, transactionId:{}, logId:{} ",
			// refId, logId);

		}
		return rep;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateTransaction")
	public ResponseBase updateTransaction(UpdateTransactionDetail transaction)
			throws JAXBException {

		ResponseBase rep = new ResponseBase();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		// logger.info("start update Transaction, transactionId: " +
		// transaction.getTransactionId() + ", time: " +
		// HelperClass.convertDateToString1(new Date()));
		Date startTime = new Date();
		String logData = null;
		String refId = null;
		String txnStatus = Contants.TRANSACTION_STATUS_TX_UPDATE_COMPLETED;
		String wkstnId = Contants.CREATE_BY_SYSTEM;
		String officerId = Contants.CREATE_BY_SYSTEM;
		String siteCode = "";

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_UHS);
		eppWsLog.setUrlRequest(Contants.URL_UPDATE_TRANSACTION);

		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(transaction));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + "convert object to json fail.");
			e1.printStackTrace();

		}

		// Kiểm tra Transaction đã đầy đủ thông tin hay chưa.
		/*
		 * ResponseBase error = checkValidateTransaction(transaction); if (error
		 * != null) { return error; }
		 */
		// set keyId for Ws_Log
		eppWsLog.setKeyId(transaction.getTransactionId());

		try {
			NicTransaction nicTransactionDBO = null;
			NicRegistrationData nicRegDataDBO = null;
			refId = transaction.getTransactionId();
			siteCode = transaction.getRegSiteCode();
			BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
					.findTransactionById(transaction.getTransactionId());
			if (!baseFindTran.isError() || baseFindTran.getMessage() != null) {
				return this.checkExistAndSaveException(baseFindTran, eppWsLog,
						rep, transaction);
			}
			NicTransaction transactionTemp = baseFindTran.getModel();
			String transactionId = transaction.getTransactionId();
			if (transactionTemp != null) {
				nicTransactionDBO = this.convertUpdateNicTransactionDetail(
						transaction, true, transactionTemp);
				BaseModelSingle<NicTransaction> baseApplyNameTrunc = nicTransactionService
						.applyNameTruncation(nicTransactionDBO);
				if (!baseApplyNameTrunc.isError()
						|| baseApplyNameTrunc.getMessage() != null) {
					return this.checkExistAndSaveException(baseApplyNameTrunc,
							eppWsLog, rep, transaction);
				}
				this.validateUpdateTransaction(transaction);
				if (nicTransactionDBO.getNicRegistrationData() != null) {
					nicRegDataDBO = nicTransactionDBO.getNicRegistrationData();
					BaseModelSingle<Void> baseSOURegisData = registrationDataDao
							.saveOrUpdateRegisData(nicRegDataDBO);
					if (!baseSOURegisData.isError()
							|| baseSOURegisData.getMessage() != null) {
						return this.checkExistAndSaveException(
								baseSOURegisData, eppWsLog, rep, transaction);
					}
					// logger.debug("saving registrationData, transactionId:{}",
					// regisDataResult);
				}

				// Cập nhật ảnh (Nếu có)
				Collection<NicTransactionAttachment> nicTransDocDBOList = nicTransactionDBO
						.getNicTransactionAttachments();
				if (nicTransDocDBOList != null && nicTransDocDBOList.size() > 0) {
					BaseModelList<NicTransactionAttachment> baseFindTranAttach = transactionDocumentDao
							.findNicTransactionAttachmentsInTypes(
									transactionId,
									new String[] {
											Contants.DOC_TYPE_PHOTO_CAPTURE,
											Contants.DOC_TYPE_PHOTO_CHIP,
											Contants.DOC_TYPE_THUMBNAIL_CAPTURE,
											Contants.DOC_TYPE_SCAN_DOCUMENT,
											Contants.DOC_TYPE_TPL,
											Contants.DOC_TYPE_SCAN_OTHER });
					if (!baseFindTranAttach.isError()
							|| baseFindTranAttach.getMessage() != null) {
						return this.checkExistAndSaveExceptions(
								baseFindTranAttach, eppWsLog, rep, transaction);
					}
					List<NicTransactionAttachment> nicAttachments = baseFindTranAttach
							.getListModel();
					if (nicAttachments != null && nicAttachments.size() > 0) {
						// Xóa toàn bộ dữ liệu ảnh (Nếu có)
						for (NicTransactionAttachment attach : nicAttachments) {
							BaseModelSingle<Void> baseDelTranAttach = transactionDocumentDao
									.deleteTranAttachment(attach);
							if (!baseDelTranAttach.isError()
									|| baseDelTranAttach.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseDelTranAttach, eppWsLog, rep,
										transaction);
							}
						}
					}
					for (NicTransactionAttachment nicTransDoc : nicTransDocDBOList) {
						BaseModelSingle<Long> baseSaveTranAttach = transactionDocumentDao
								.saveTranAttachment(nicTransDoc);
						if (!baseSaveTranAttach.isError()
								|| baseSaveTranAttach.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseSaveTranAttach, eppWsLog, rep,
									transaction);
						}
					}
				}

				// logger.debug("saving nicTransaction, transactionId:{}",
				// transactionResult);
				/*
				 * String errorCode =
				 * TX_UPLOAD_ERROR_CODE_DUPLICATE_TRANSACTION_ID; logger.error(
				 * "Transaction cannot be uploaded as duplicate transaction Id found.[{}], errorCode:{}"
				 * , transactionId, errorCode); throw new
				 * TransactionServiceException(
				 * "Transaction cannot be uploaded as duplicate transaction Id found."
				 * , errorCode);
				 */

				// reset chạy lại job check lần nữa
				BaseModelSingle<NicUploadJob> baseNicUp = uploadJobService
						.findUploadJobByTransId(transaction.getTransactionId());
				if (!baseNicUp.isError() || baseNicUp.getMessage() != null) {
					return this.checkExistAndSaveException(baseNicUp, eppWsLog,
							rep, transaction);
				}
				NicUploadJob nicUp = baseNicUp.getModel();
				NicTransaction txn = nicUp.getNicTransaction();
				if (nicUp != null
						&& txn != null
						&& (transaction.getOfficerCodeUpdate().equals("AA")
								|| transaction.getOfficerCodeUpdate().equals(
										"BB") || transaction
								.getOfficerCodeUpdate().equals("CC"))) {
					this.resetWorkflowCheckData(nicUp);
				}

				BaseModelSingle<Boolean> baseSOUTran = nicTransactionService
						.saveOrUpdateTransaction(nicTransactionDBO);
				if (!baseSOUTran.isError() || baseSOUTran.getMessage() != null) {
					return this.checkExistAndSaveException(baseSOUTran,
							eppWsLog, rep, transaction);
				}

				try {
					BaseModelList<NicTransactionLog> baseFindAllRTranLog = nicTransactionService
							.findAllRicTransactionLogsByRefId(nicTransactionDBO
									.getTransactionId());
					if (!baseFindAllRTranLog.isError()
							|| baseFindAllRTranLog.getMessage() != null) {
						return this
								.checkExistAndSaveExceptions(
										baseFindAllRTranLog, eppWsLog, rep,
										transaction);
					}
					List<NicTransactionLog> transLog = baseFindAllRTranLog
							.getListModel();

					List<NicTransactionLog> transLogList = new ArrayList<NicTransactionLog>();
					NicTransactionLog dcmTransLog = new NicTransactionLog();
					dcmTransLog.setRefId(nicTransactionDBO.getTransactionId());
					dcmTransLog.setLogCreateTime(nicTransactionDBO
							.getCreateDatetime());
					dcmTransLog.setOfficerId(nicTransactionDBO.getCreateBy());
					dcmTransLog.setSiteCode(nicTransactionDBO.getRegSiteCode());
					dcmTransLog.setStartTime(nicTransactionDBO
							.getCreateDatetime());

					dcmTransLog
							.setTransactionStage(Contants.TRANSACTION_STATE_TX_UPDATE);
					dcmTransLog.setTransactionStatus(TransactionStatus.Captured
							.getKey());
					if (transLog.contains(transLogList)) {
						dcmTransLog
								.setRetryCount(dcmTransLog.getRetryCount() + 1);
						BaseModelSingle<Void> baseSOUTranLog = nicTransactionLogService
								.saveOrUpdateTranLog(dcmTransLog);
						if (!baseSOUTranLog.isError()
								|| baseSOUTranLog.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseSOUTranLog, eppWsLog, rep, transaction);
						}
					} else {
						dcmTransLog.setRetryCount(1);
						transLogList.add(dcmTransLog);
						List<NicTransactionLog> errorList = nicTransactionService
								.saveTransactionLogs(transLogList);
						for (NicTransactionLog errorLog : errorList) {
							logger.warn("Failed to save log: {}, {}, {}, {}",
									new Object[] { errorLog.getRefId(),
											errorLog.getTransactionStage(),
											errorLog.getTransactionStatus(),
											errorLog.getSiteCode() });
						}
					}
				} catch (Exception e) {
					rep.setCode(Contants.CODE_THROW_EXCEPTION);
					rep.setMessage(Contants.MESSAGE_EXCEPTION);
					eppWsLog.setDataResponse(rep.toString());
					eppWsLog.setMessageErrorLog(CreateMessageException
							.createMessageException(e)
							+ " - updateTransaction fail.");
					eppWsLog.setCreatedDate(new Date());
					BaseModelSingle<Boolean> baseCheck = eppWsLogService
							.inserDataTable(eppWsLog);
					if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
						logger.error(baseCheck.getMessage()
								+ " - save EppWsLog fail.");
					}
				}
			}

			// 5/12/2019 update status upload no call service by ric
			// Boolean checkStatus = this.updateStatusLog(transactionId,
			// Contants.TRANSACTION_STATUS_RIC_UPLOADED);
			// logger.info("Result update status end, transactionId:{0}, result:{1}",
			// transactionId, checkStatus);
			rep.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/*
			 * Đưa vào hàng đợi chờ đồng bộ sang các trung tâm xử lý Đưa vào
			 * hàng đợi nếu hồ sơ này từ A
			 */
			SiteGroups groupSite = siteService
					.findSiteGroupsByGroupId(transaction.getRegSiteCode());
			if (groupSite != null) {
				List<SiteGroups> list = siteService.findAll();
				for (SiteGroups gs : list) {
					if (groupSite.getGroupId().equals(gs.getGroupId()))
						continue;
					BaseModelSingle<Boolean> baseAddQJ = this.addObjToQueueJob(
							transactionId, Contants.QUEUE_OBJ_TYPE_HS,
							gs.getGroupId(), TRAN_STATUS_UPDATED, null);
					if (!baseAddQJ.isError() || baseAddQJ.getMessage() != null) {
						return this.checkExistAndSaveException(baseAddQJ,
								eppWsLog, rep, transaction);
					}
				}
			}
			/* Lưu bảng thống kê truyền nhận */
			this.saveOrUpRptData(Contants.URL_UPLOAD_TRANSACTION, 1,
					transaction.getRegSiteCode());
			// } catch (TransactionServiceException e) {
			// logData = e.getMessage();//MiscXmlConvertor.parseObjectToXml(e);
			// txnStatus = Contants.TRANSACTION_STATUS_TX_UPDATE_ERROR;
			// //logger.info("[saveMainTransaction] Error, refId:{}, txnStatus:{}, logData:{} ",
			// new Object[] {refId, txnStatus, logData});
			// logger.error("[saveMainTransaction] Exception: {} ",
			// e.getMessage());
			// rep.setMessage(e.getMessage());
			// e.printStackTrace();
		} catch (Throwable e) {
			logger.error(e.getMessage() + " - updateTransaction fail.");
			e.printStackTrace();
			rep.setCode(Contants.CODE_THROW_EXCEPTION);
			rep.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(rep.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(new Exception(e))
					+ " - updateTransaction fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}
		} finally {
			// Step 4: Create Nic Transaction Log
			// logger.info("[saveMainTransaction] save NicTransactionLog, transactionId:{}",
			// refId);
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
			nicTransLog
					.setTransactionStage(Contants.TRANSACTION_STATE_TX_UPDATE);
			nicTransLog.setTransactionStatus(txnStatus);
			nicTransLog.setOfficerId(officerId);
			nicTransLog.setWkstnId(wkstnId);
			nicTransLog.setSiteCode(siteCode);
			nicTransLog.setLogInfo("");
			nicTransLog.setLogData(logData);
			// Long logId = transactionLogDao.save(nicTransLog);
			try {
				BaseModelSingle<Long> baseSaveTranLog = transactionLogService
						.saveTransactionLog(refId,
								Contants.TRANSACTION_STATE_TX_UPDATE,
								txnStatus, officerId, wkstnId, siteCode,
								startTime, endTime, "", logData);
				if (!baseSaveTranLog.isError()
						|| baseSaveTranLog.getMessage() != null) {
					rep = this.checkExistAndSaveException(baseSaveTranLog,
							eppWsLog, rep, transaction);
				}
				// Long logId = baseSaveTranLog.getModel();
			} catch (Exception e2) {
			}

			// logger.info("[saveMainTransaction] save NicTransactionLog completed, transactionId:{}, logId:{} ",
			// refId, logId);

		}
		return rep;
	}

	@POST
	@Path("/updateStatusQueue")
	@Produces("application/json")
	@Consumes("application/json")
	public ResponseBase updateStatusQueue(QueueInfo queueInfo) {
		ResponseBase rep = new ResponseBase();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			queueJobService.updateQueueJob(queueInfo.getId(),
					queueInfo.getStatus());

			SyncQueueJob check = queueJobService.findQueueByInfo(
					queueInfo.getId(), null);

			if (check != null && check.getObjectType().equals("DA")
					&& "SUCCESS".equals(queueInfo.getStatus())) {
				NicListHandover listHan = listHandoverService
						.findListHandoverByOrther1(check.getObjectId(),
								NicListHandover.TYPE_LIST_A);
				BaseModelSingle<Boolean> baseSaveQ = this.addObjToQueueJob(
						check.getObjectId(),
						Contants.QUEUE_OBJ_TYPE_UP_DA_SUCCESS,
						listHan.getSiteCode(), "",
						Contants.URL_UPDATE_STATUS_QUEUE);
				if (!baseSaveQ.isError() || baseSaveQ.getMessage() != null) {
					throw new Exception(baseSaveQ.getMessage());
				}
			}
			rep.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
			// logger.info("success update status queue to TTDH");
		} catch (Exception e) {
			e.printStackTrace();
			rep.setCode(Contants.CODE_THROW_EXCEPTION);
			rep.setMessage(Contants.MESSAGE_EXCEPTION);
			EppWsLog eppWsLog = new EppWsLog();
			eppWsLog.setType(Contants.TYPE_USQ);
			eppWsLog.setUrlRequest(Contants.URL_UPDATE_STATUS_QUEUE);
			try {
				eppWsLog.setDataRequest(new ObjectMapper()
						.writeValueAsString(queueInfo));
			} catch (Exception e2) {
				logger.error("convert to json fail: " + e2.getMessage());
			}
			eppWsLog.setDataResponse(rep.toString());
			eppWsLog.setKeyId(queueInfo.getId() + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date()));

			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(new Exception(e))
					+ " "
					+ Contants.URL_UPDATE_STATUS_QUEUE + " fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rep;
	}

	/*
	 * lấy trạng thái danh sách gửi lên A
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadStatusHandoverASubmitToA/{site}")
	public Response<List<HandoverAStatus>> downloadStatusHandoverASubmitToA(
			@PathParam("site") String site) {
		Response<List<HandoverAStatus>> response = new Response<List<HandoverAStatus>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<HandoverAStatus> listHanStatus = null;
		try {
			/* Kiểm tra hàng đợi */
			List<SyncQueueJob> listQ = queueJobDao
					.findSyncQueueJobByObjIdOrReceiver(null,
							Contants.QUEUE_OBJ_TYPE_UP_DA_SUCCESS, site,
							Contants.CODE_STATUS_QUEUE_PENDING);

			if (listQ != null && listQ.size() > 0) {
				listHanStatus = new ArrayList<HandoverAStatus>();
				for (SyncQueueJob syncQueueJob : listQ) {
					HandoverAStatus hanStatus = new HandoverAStatus(
							syncQueueJob.getObjectId(), "SUCCESS",
							syncQueueJob.getId());
					listHanStatus.add(hanStatus);
					syncQueueJob.setStatus(Contants.CODE_STATUS_QUEUE_DOING);
					BaseModelSingle<Boolean> baseUpdateStatusQueue = queueJobDao
							.saveOrUpdateQueue(syncQueueJob);
					if (!baseUpdateStatusQueue.isError()
							|| baseUpdateStatusQueue.getMessage() != null) {
						throw new Exception(baseUpdateStatusQueue.getMessage());
					}
				}
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}
			response.setCode(Contants.CODE_SUCCESS);
			response.setData(listHanStatus);
			if (listHanStatus != null && listHanStatus.size() > 0) {
				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData("downloadStatusHandoverASubmitToA",
						listHanStatus.size(), site);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = "DSHS_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			EppWsLog eppWsLog = new EppWsLog();
			eppWsLog.setType("DSHS");
			eppWsLog.setUrlRequest("downloadStatusHandoverASubmitToA");
			eppWsLog.setDataRequest(site);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setKeyId(key);
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(new Exception(e))
					+ " "
					+ Contants.URL_UPDATE_STATUS_QUEUE + " fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/dowloadBufPerson/{site}")
	public Response<List<EppBufPerson>> dowloadBufPerson(
			@PathParam("site") String site) {
		Response<List<EppBufPerson>> response = new Response<List<EppBufPerson>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "dowloadBufPerson";
		// if(!this.checkConnectApi(nameApi, site)){
		// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return response;
		// }
		List<EppBufPerson> listBuf = new ArrayList<EppBufPerson>();
		// logger.info("start get buf in TTDH, site: "
		// + site
		// + ", time: "
		// + HelperClass.convertDateToString1(Calendar.getInstance()
		// .getTime()));
		ResponseBase rb = new ResponseBase();
		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_BPS);
		eppWsLog.setUrlRequest(Contants.URL_DOWNLOAD_BUF_PERSON);
		eppWsLog.setDataRequest(site);
		eppWsLog.setKeyId(site + "_"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()));
		try {
			int maxTotal = 10;
			Parameters pm = parametersService.getParamDetails(
					Contants.CREATE_BY_SYSTEM,
					Contants.PARA_NAME_MAX_COUNT_GET_BUF);
			if (pm != null) {
				maxTotal = Integer.valueOf(pm.getParaShortValue());
			}
			BaseModelList<SyncQueueJob> baseFindQByR = queueJobService
					.findQueueByReceiver(site,
							Contants.CODE_STATUS_QUEUE_PENDING,
							Contants.QUEUE_OBJ_TYPE_BF, maxTotal);
			if (!baseFindQByR.isError() || baseFindQByR.getMessage() != null) {
				rb = this.checkExistAndSaveExceptions(baseFindQByR, eppWsLog,
						rb, null);
				response.setCode(rb.getCode());
				response.setMessage(rb.getMessage());
				return response;
			}
			List<SyncQueueJob> dsBufQueue = baseFindQByR.getListModel();
			// logger.info("size buf: "
			// + (dsBufQueue != null ? dsBufQueue.size() : 0));
			if (dsBufQueue != null) {
				// Lấy thông tin hồ sơ theo đối tượng nghi trùng đã được đưa vào
				// hàng đợi
				for (SyncQueueJob queue : dsBufQueue) {
					EppBufPerson bufPeson = new EppBufPerson();
					if (StringUtils.isBlank(queue.getTransactionIdMaster())) {
						bufPeson.setIdQueue(queue.getId());
						bufPeson.setTransactionIdMaster(queue.getObjectId());
						bufPeson.setScoreBMS("-1");
						listBuf.add(bufPeson);
						continue;
					}
					bufPeson.setIdQueue(queue.getId());
					bufPeson.setTransactionIdMaster(queue
							.getTransactionIdMaster());

					BaseModelSingle<NicUploadJob> baseFindUJByTran = uploadJobService
							.findUploadJobByTransId(queue.getObjectId());
					if (!baseFindUJByTran.isError()
							|| baseFindUJByTran.getMessage() != null) {
						rb = this.checkExistAndSaveException(baseFindUJByTran,
								eppWsLog, rb, null);
						response.setCode(rb.getCode());
						response.setMessage(rb.getMessage());
						return response;
					}
					NicUploadJob uploadJobOb = baseFindUJByTran.getModel();

					String sfDob = "";
					String smDob = "";
					String ssDob = "";
					// Lấy dữ liệu thông tin hồ sơ của transaction
					BaseModelSingle<NicTransaction> baseGetTranById = nicTransactionService
							.getNicTransactionById(queue.getObjectId());
					if (!baseGetTranById.isError()
							|| baseGetTranById.getMessage() != null) {
						rb = this.checkExistAndSaveException(baseGetTranById,
								eppWsLog, rb, null);
						response.setCode(rb.getCode());
						response.setMessage(rb.getMessage());
						return response;
					}
					NicTransaction nicTran = baseGetTranById.getModel();
					EppPerson person = eppPersonService
							.findPersonByPersonCode(nicTran.getPersonCode());
					if (person != null) {
						bufPeson.setGlobalId(person.getPersonCode());
						bufPeson.setOriginId(person.getOrgPerson());
					}
					List<PersonFamily> listFamily = new ArrayList<PersonFamily>();
					if (nicTran != null) {
						NicRegistrationData regOrg = nicTran
								.getNicRegistrationData();
						if (regOrg != null) {
							sfDob = regOrg.getFatherDefDateOfBirth();
							smDob = regOrg.getMotherDefDateOfBirth();
							ssDob = regOrg.getSpouseDefDateOfBirth();
							String address = regOrg.getAddressLine1();
							String px = "";
							String tp = "";
							if (StringUtils
									.isNotEmpty(regOrg.getAddressLine4())) {
								try {
									px = codesService.getCodeValueDescByIdName(
											"TOWN_VILLAGE",
											regOrg.getAddressLine4(), "");
								} catch (Throwable e) {
									throw new Exception(e);
								}

								try {
									tp = codesService.getCodeValueDescByIdName(
											"DISTRICT",
											regOrg.getAddressLine5(), "");
								} catch (Exception e) {
									throw new Exception(e);
								}
							}
							if (StringUtils.isNotEmpty(px)) {
								address += ", " + px;
							}
							if (StringUtils.isNotEmpty(tp)) {
								address += ", " + tp;
							}
							bufPeson.setAddressResident(address);
							if (regOrg.getDateOfBirth() != null) {
								String dob = service.perso.util.HelperClass
										.convertDateToString(
												regOrg.getDateOfBirth(),
												STR_FORMAT_DATE_yyyyMMdd);
								bufPeson.setDob(HelperClass.loadDateOfBirths(
										dob, regOrg.getDefDateOfBirth()));
							}
							bufPeson.setFullname(regOrg.getSurnameLine1());
							bufPeson.setGender(regOrg.getGender().equals("M") ? "MALE"
									: "FEMALE");
							// bufPeson.setDateNin(regOrg.getDateNin());
							if (regOrg.getDateNin() != null) {
								String dateNin = service.perso.util.HelperClass
										.convertDateToString(
												regOrg.getDateNin(),
												STR_FORMAT_DATE_yyyyMMdd);
								bufPeson.setDateNin(dateNin);
							}
							bufPeson.setPlaceNin(regOrg.getAddressNin());
							bufPeson.setNation(regOrg.getNation());
							bufPeson.setNin(nicTran.getNin());
							bufPeson.setStyleDob(regOrg.getDefDateOfBirth());

							BaseModelSingle<EppListHandoverDetail> baseFindHanDetailByTranId = eppListHandoverDetailService
									.findTransactionByIdOrType(
											queue.getObjectId(),
											NicListHandover.TYPE_LIST_A);
							if (!baseFindHanDetailByTranId.isError()
									|| baseFindHanDetailByTranId.getMessage() != null) {
								rb = this.checkExistAndSaveException(
										baseFindHanDetailByTranId, eppWsLog,
										rb, null);
								response.setCode(rb.getCode());
								response.setMessage(rb.getMessage());
								return response;
							}
							EppListHandoverDetail tranp = baseFindHanDetailByTranId
									.getModel();
							if (tranp != null) {
								bufPeson.setHandoverA(tranp.getPackageId());
								BaseModelSingle<NicListHandover> baseFindHandover = listHandoverService
										.findHandoverByCriteria(
												tranp.getPackageId(),
												NicListHandover.TYPE_LIST_A,
												null);
								if (!baseFindHandover.isError()
										|| baseFindHandover.getMessage() != null) {
									rb = this.checkExistAndSaveException(
											baseFindHandover, eppWsLog, rb,
											null);
									response.setCode(rb.getCode());
									response.setMessage(rb.getMessage());
									return response;
								}
								NicListHandover listH = baseFindHandover
										.getModel();
								if (listH != null) {
									try {
										// bufPeson.setCreateDateHandoverA(listH.getProposalDate());
										if (listH.getProposalDate() != null) {
											String dateA = service.perso.util.HelperClass
													.convertDateToString(listH
															.getProposalDate(),
															STR_FORMAT_DATE_yyyyMMddHHmmss);
											bufPeson.setCreateDateHandoverA(dateA);
										}
									} catch (Throwable e) {
										throw new Exception(e);
									}

									try {
										bufPeson.setCreateByHandoverA(listH
												.getProcessUsers());
									} catch (Throwable e) {
										throw new Exception(e);
									}
								}
							}

							bufPeson.setJobs(regOrg.getJob());

							// Thông tin hộ chiếu
							BaseModelSingle<Collection<NicDocumentData>> baseFindListDocData = documentDataService
									.findByTransactionId(queue.getObjectId());
							if (!baseFindListDocData.isError()
									|| baseFindListDocData.getMessage() != null) {
								rb = this
										.checkExistAndSaveException(
												baseFindListDocData, eppWsLog,
												rb, null);
								response.setCode(rb.getCode());
								response.setMessage(rb.getMessage());
								return response;
							}
							Collection<NicDocumentData> nicDocs = baseFindListDocData
									.getModel();
							if (nicDocs != null && nicDocs.size() > 0) {
								List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(
										nicDocs);
								NicDocumentData nicDoc = nicDocs_.get(0);
								bufPeson.setPassportNo(nicDoc.getId()
										.getPassportNo());
								bufPeson.setPassportStatus(nicDoc.getStatus()); // ISSUANCE:
																				// phát
																				// hành
																				// -
																				// NONE:
																				// tạm
																				// khóa

								if (nicDoc.getDateOfIssue() != null) {
									// bufPeson.setIssuePassport(nicDoc.getDateOfIssue());
									String dateIssue = service.perso.util.HelperClass
											.convertDateToString(
													nicDoc.getDateOfIssue(),
													STR_FORMAT_DATE_yyyyMMddHHmmss);
									bufPeson.setIssuePassport(dateIssue);

								}

								if (nicDoc.getDateOfExpiry() != null) {
									// bufPeson.setExpiredPassport(nicDoc.getDateOfExpiry());
									String dateExp = service.perso.util.HelperClass
											.convertDateToString(
													nicDoc.getDateOfExpiry(),
													STR_FORMAT_DATE_yyyyMMddHHmmss);
									bufPeson.setExpiredPassport(dateExp);
								}

								if (nicDoc.getCreateDatetime() != null) {
									// bufPeson.setPrintDateC(nicDoc.getCreateDatetime());
									String datePrintC = service.perso.util.HelperClass
											.convertDateToString(
													nicDoc.getCreateDatetime(),
													STR_FORMAT_DATE_yyyyMMddHHmmss);
									bufPeson.setPrintDateC(datePrintC);
								}

								if (nicDoc.getPackageDatetime() != null) {
									// bufPeson.setCreateDateC(nicDoc.getPackageDatetime());
									String dateC = service.perso.util.HelperClass
											.convertDateToString(
													nicDoc.getPackageDatetime(),
													STR_FORMAT_DATE_yyyyMMddHHmmss);
									bufPeson.setCreateDateC(dateC);
								}

								SiteRepository siteS = siteService
										.getSiteRepoById(nicTran
												.getIssSiteCode());
								if (siteS != null)
									bufPeson.setPlaceIssuePassport(siteS
											.getSiteName());

								if (nicTran.getPassportType().equals("PO"))
									bufPeson.setPassportType("CÔNG VỤ");
								else if (nicTran.getPassportType().equals("PD"))
									bufPeson.setPassportType("NGOẠI GIAO");
								else
									bufPeson.setPassportType("PHỔ THÔNG");
								bufPeson.setSerial(nicDoc.getChipSerialNo());
								bufPeson.setIcaoLineOne(nicDoc.getIcaoLine1());
								bufPeson.setIcaoLineTwo(nicDoc.getIcaoLine2());
							}
							bufPeson.setPhone(regOrg.getContactNo());
							bufPeson.setPob(regOrg.getPlaceOfBirth());
							bufPeson.setReligion(regOrg.getReligion());
							bufPeson.setTransacionId(queue.getObjectId());
							// Thông tin ng thân

							PersonFamily fatherData = new PersonFamily();
							if (StringUtils.isNotEmpty(regOrg
									.getFatherFullname())) {
								fatherData.setName(regOrg.getFatherFullname());
								fatherData.setGender("M");
								fatherData
										.setRelationship(Contants.RELATIONSHIP_TYPE_FATHER);
								fatherData.setTypeDob(sfDob);
								if (regOrg.getFatherLost() != null) {
									fatherData.setLost(regOrg.getFatherLost());
								}
								// fatherData.setDateOfBirth(HelperClass.convertDateToString2(regOrg.getFatherDob()));
								if (regOrg.getFatherDateOfBirth() != null) {
									String dob = service.perso.util.HelperClass
											.convertDateToString(regOrg
													.getFatherDateOfBirth(),
													STR_FORMAT_DATE_yyyyMMdd);
									fatherData.setDateOfBirth(HelperClass
											.loadDateOfBirths(dob, sfDob));
								}
								fatherData
										.setTransactionId(queue.getObjectId());
								listFamily.add(fatherData);
							}
							PersonFamily motherData = new PersonFamily();
							if (StringUtils.isNotEmpty(regOrg
									.getMotherFullname())) {
								motherData.setName(regOrg.getMotherFullname());
								motherData.setGender("F");
								motherData
										.setRelationship(Contants.RELATIONSHIP_TYPE_MOTHER);
								motherData.setTypeDob(smDob);
								if (regOrg.getMotherLost() != null) {
									motherData.setLost(regOrg.getMotherLost());
								}
								// motherData.setDateOfBirth(HelperClass.convertDateToString2(regOrg.getMotherDob()));
								if (regOrg.getMotherDateOfBirth() != null) {
									String dob = service.perso.util.HelperClass
											.convertDateToString(regOrg
													.getMotherDateOfBirth(),
													STR_FORMAT_DATE_yyyyMMdd);
									motherData.setDateOfBirth(HelperClass
											.loadDateOfBirths(dob, smDob));
								}
								motherData
										.setTransactionId(queue.getObjectId());
								listFamily.add(motherData);
							}
							PersonFamily spouseData = new PersonFamily();
							if (StringUtils.isNotEmpty(regOrg
									.getSpouseFullname())) {
								spouseData.setName(regOrg.getSpouseFullname());
								spouseData.setGender(regOrg.getGender().equals(
										"M") ? "F" : "M");
								spouseData
										.setRelationship(Contants.RELATIONSHIP_TYPE_SPOUSE);
								spouseData.setTypeDob(ssDob);
								spouseData.setLost("N");
								// spouseData.setDateOfBirth(HelperClass.convertDateToString2(regOrg.getSpouseDob()));
								if (regOrg.getSpouseDateOfBirth() != null) {
									String dob = service.perso.util.HelperClass
											.convertDateToString(regOrg
													.getSpouseDateOfBirth(),
													STR_FORMAT_DATE_yyyyMMdd);
									spouseData.setDateOfBirth(HelperClass
											.loadDateOfBirths(dob, ssDob));
								}
								spouseData
										.setTransactionId(queue.getObjectId());
								listFamily.add(spouseData);
							}
							if (StringUtils.isNotEmpty(nicTran.getInfoPerson())) {
								JAXBContext jaxbContext = JAXBContext
										.newInstance(InfoFamilys.class);
								Unmarshaller unmarshaller = jaxbContext
										.createUnmarshaller();
								StringReader reader = new StringReader(
										nicTran.getInfoPerson());
								InfoFamilys sonFamily = (InfoFamilys) unmarshaller
										.unmarshal(reader);
								if (sonFamily != null
										&& sonFamily.getSonFamilies() != null) {
									for (PersonFamily fa : sonFamily
											.getSonFamilies()) {
										PersonFamily sonData = new PersonFamily();
										sonData.setName(fa.getName());
										sonData.setGender(fa.getGender());
										sonData.setRelationship(Contants.RELATIONSHIP_TYPE_CHILD);
										sonData.setTypeDob("D");
										sonData.setLost("N");
										sonData.setDateOfBirth(fa
												.getDateOfBirth());
										sonData.setTransactionId(queue
												.getObjectId());
										listFamily.add(sonData);
									}
								}
							}
						}
					}
					BaseModelList<NicListHandover> baseFindAllHanByTran = listHandoverService
							.findAllHandoverByTransactionId(
									queue.getObjectId(),
									NicListHandover.TYPE_LIST_B, null, true);
					if (!baseFindAllHanByTran.isError()
							|| baseFindAllHanByTran.getMessage() != null) {
						rb = this.checkExistAndSaveExceptions(
								baseFindAllHanByTran, eppWsLog, rb, null);
						response.setCode(rb.getCode());
						response.setMessage(rb.getMessage());
						return response;
					}
					List<NicListHandover> listHanG = baseFindAllHanByTran
							.getListModel();
					if (listHanG != null && listHanG.size() > 0) {
						NicListHandover handover = listHanG.get(0);
						EppListHandoverDetail tranPackage = eppListHandoverDetailService
								.getListPackageByPackageIdAndTranID(
										handover.getId().getPackageId(),
										queue.getObjectId(),
										NicListHandover.TYPE_LIST_A).getModel();
						if (tranPackage != null) {
							bufPeson.setApproveNoteB(tranPackage
									.getApproveNote());
							bufPeson.setRequestNoteB(tranPackage
									.getProposalNote());
						}
						// bufPeson.setRequestDateB(handover.getProposalDate());
						if (handover.getProposalDate() != null) {
							String dateB = service.perso.util.HelperClass
									.convertDateToString(
											handover.getProposalDate(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							bufPeson.setRequestDateB(dateB);
						}
						// bufPeson.setApproveDateB(handover.getApproveDate());
						if (handover.getApproveDate() != null) {
							String dateB = service.perso.util.HelperClass
									.convertDateToString(
											handover.getApproveDate(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							bufPeson.setApproveDateB(dateB);
						}
					}

					/*
					 * if (nicTran.getLeaderOfficerId() != null){ Users user_ =
					 * userService.findByUserId(nicTran.getLeaderOfficerId());
					 * if(user_ != null){
					 * bufPeson.setApproveUserB(user_.getUserName()); } else {
					 * bufPeson.setApproveUserB(nicTran.getLeaderOfficerId()); }
					 * }
					 */
					if (uploadJobOb != null
							&& uploadJobOb.getInvestigationOfficerId() != null) {
						Users user_ = userService.findByUserId(uploadJobOb
								.getInvestigationOfficerId());
						if (user_ != null) {
							bufPeson.setRequestUserB(user_.getUserName());
						} else {
							bufPeson.setRequestUserB(uploadJobOb
									.getInvestigationOfficerId());
						}
					}

					// Lấy dữ liệu ảnh/tài liệu đính kèm/vân tay
					List<EppBufPersonDoc> bufDocList = new ArrayList<EppBufPersonDoc>();
					List<NicTransactionAttachment> docList = nicTransactionAttachmentService
							.getNicTransactionAttachments(
									queue.getObjectId(),
									new String[] {
											NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
											NicTransactionAttachment.DOC_TYPE_FINGERPRINT },
									null);
					if (docList != null && docList.size() > 0) {
						for (NicTransactionAttachment doc : docList) {
							EppBufPersonDoc bufDoc = new EppBufPersonDoc();
							bufDoc.setDocData(doc.getDocData());
							bufDoc.setDocType(doc.getDocType());
							bufDoc.setSerial(doc.getSerialNo());
							bufDoc.setTransacionId(doc.getTransactionId());
							bufDocList.add(bufDoc);
						}
					}
					// Lấy lịch sử cấp phát hộ chiếu
					HistoryPassportInfo passportInfo = new HistoryPassportInfo();
					BaseModelSingle<NicDocumentData> baseGetDocData = documentDataService
							.getDocumentDataById(queue.getObjectId());
					NicDocumentData nicDoc = baseGetDocData.getModel();

					NicUploadJob nicUp = uploadJobService
							.findUploadJobByTransId(queue.getObjectId())
							.getModel();
					if (nicUp != null) {
						String status = "";
						if (nicDoc == null
								&& nicUp.getInvestigationStatus() != null
								&& nicUp.getInvestigationStatus()
										.equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)) {
							status = "Đã phê duyệt. Chờ in";
						} else if (nicDoc != null) {
							if (nicDoc
									.getStatus()
									.equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE)
									&& nicUp.getNicTransaction()
											.getTransactionStatus()
											.equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)) {
								status = "Đã trả hộ chiếu cho công dân";
							} else {
								status = "Đã in. Chờ trả hộ chiếu";
							}
						}
						passportInfo.setStatus(status);

					}
					if (nicDoc != null) {
						passportInfo.setPackageId(nicDoc.getPackageId());
						if (nicDoc.getDateOfIssue() != null) {
							// passportInfo.setIssueDate(nicDoc.getDateOfIssue());
							String dateIss = service.perso.util.HelperClass
									.convertDateToString(
											nicDoc.getDateOfIssue(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							passportInfo.setIssueDate(dateIss);
						}

						if (nicDoc.getDateOfExpiry() != null) {
							String dateExp = service.perso.util.HelperClass
									.convertDateToString(
											nicDoc.getDateOfExpiry(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							passportInfo.setExpiryDate(dateExp);
							// passportInfo.setExpiryDate(nicDoc.getDateOfExpiry());
						}

						if (nicDoc.getReceiveDatetime() != null) {
							// passportInfo.setReceiveDate(nicDoc.getReceiveDatetime());
							String dateExp = service.perso.util.HelperClass
									.convertDateToString(
											nicDoc.getReceiveDatetime(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							passportInfo.setReceiveDate(dateExp);
						}
						passportInfo.setIssueSiteCode(nicTran.getIssSiteCode());
						passportInfo.setPassportStage(nicDoc.getStatus());
						passportInfo.setTransactionId(queue.getObjectId());
						passportInfo.setReceiptNo(nicTran.getRecieptNo());
						if (nicTran.getTransactionType().equals("REP"))
							passportInfo.setTypeTransaction("Thay thế");
						else if (nicTran.getTransactionType().equals("LOS"))
							passportInfo
									.setTypeTransaction("Thay thế do mất hỏng");
						else
							passportInfo.setTypeTransaction("Cấp mới");
						passportInfo.setRegSiteCode(this.getSiteName(nicTran
								.getRegSiteCode()));
						BaseModelList<NicListHandover> baseFindAllHanByTranId = listHandoverService
								.findAllHandoverByTransactionId(
										queue.getObjectId(),
										NicListHandover.TYPE_LIST_B, null, true);
						if (!baseFindAllHanByTranId.isError()
								|| baseFindAllHanByTranId.getMessage() != null) {
							rb = this.checkExistAndSaveExceptions(
									baseFindAllHanByTranId, eppWsLog, rb, null);
							response.setCode(rb.getCode());
							response.setMessage(rb.getMessage());
							return response;
						}
						List<NicListHandover> listHandover = baseFindAllHanByTranId
								.getListModel();
						if (listHandover != null && listHandover.size() > 0) {
							NicListHandover handover = listHandover.get(0);
							BaseModelSingle<EppListHandoverDetail> baseGetHD = eppListHandoverDetailService
									.getListPackageByPackageIdAndTranID(
											handover.getId().getPackageId(),
											queue.getObjectId(),
											NicListHandover.TYPE_LIST_A);
							// if (!baseGetHD.isError() ||
							// baseGetHD.getMessage() != null) {
							// return this.checkExistAndSaveException(baseGetHD,
							// eppWsLog, response, object);
							// }
							EppListHandoverDetail tranPackage = baseGetHD
									.getModel();
							if (tranPackage != null) {
								passportInfo.setApproveNote(tranPackage
										.getApproveNote());
								passportInfo.setOfferNote(tranPackage
										.getProposalNote());
							}
						}

						String nameApprover = "";
						String levelApprover = "";
						/*
						 * if(StringUtils.isNotEmpty(nicTran.getLeaderOfficerId()
						 * )){ Users user =
						 * userService.findById(nicTran.getLeaderOfficerId());
						 * if(user != null){ nameApprover = user.getUserName();
						 * levelApprover = user.getPosition(); } else{
						 * nameApprover = nicTran.getLeaderOfficerId(); } }
						 */
						passportInfo.setApproveName(nameApprover);
						passportInfo.setApprovePosition(levelApprover);

						passportInfo.setPassportNo(nicDoc.getId()
								.getPassportNo());
						// passportInfo.setReleaseDate(nicDoc.getIssueDatetime());
						if (nicDoc.getIssueDatetime() != null) {
							String dateExp = service.perso.util.HelperClass
									.convertDateToString(
											nicDoc.getIssueDatetime(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							passportInfo.setReleaseDate(dateExp);
						}
						bufPeson.setPassportInfo(passportInfo);
						// Lấy lịch sử xuất nhập cảnh
						List<ImmiHistoryData> listImmi = immiHistoryService
								.findImmiByNinPPno(nicTran.getNin(), nicDoc
										.getId().getPassportNo());
						if (listImmi != null) {
							for (ImmiHistoryData immi : listImmi) {
								immi.setTransactionId(queue.getObjectId());
							}
						}
						bufPeson.setImmiHistoryDatas(listImmi);
						// end
					}

					// end
					if (bufDocList != null && bufDocList.size() > 0) {
						List<EppBufPersonDoc> bufDoc = new ArrayList<EppBufPersonDoc>();
						bufDoc.addAll(bufDocList);
						bufPeson.setEppBufPersonDoc(bufDoc);
					}
					if (listFamily.size() > 0) {
						bufPeson.setPersonFamilys(listFamily);
					}

					if (queue.getBmsId() != null) {
						List<NicSearchHitResult> listHitBMS = nicSearchHitResultService
								.findHitPositionsHit(null, queue.getBmsId());
						if (listHitBMS != null && listHitBMS.size() > 0) {
							for (NicSearchHitResult sHR : listHitBMS) {
								if (sHR.getDataSource()
										.equals(NicSearchHitResult.hitInfo_DataSource_BMS)) {
									int score = 0;
									// Tính số điểm trung bình cho vân tay
									// Kiểm tra chuỗi dữ liệu
									if (StringUtils
											.isNotEmpty(sHR.getHitInfo())) {
										// Tách chuỗi theo dấu ","
										String[] listHit = sHR.getHitInfo()
												.split(",");
										if (listHit.length > 0) {
											for (int i = 0; i < listHit.length; i++) {
												if (Double
														.parseDouble((listHit[i]
																.split("=")[1])) > score) {
													double d = Double
															.parseDouble((listHit[i]
																	.split("=")[1]));
													score = (int) d;
												}
											}
										}

										bufPeson.setScoreBMS("" + score);
										bufPeson.setDetailScoreBMS(sHR
												.getHitInfo());
									}
								}
							}
						}
					}
					listBuf.add(bufPeson);

					queueJobService.updateStatusQueueJob(queue.getId(),
							Contants.CODE_STATUS_QUEUE_DOING);

					/* Lưu bảng thống kê truyền nhận */
					if (listBuf != null && listBuf.size() > 0
							&& queue.getDateUpdateStatusHanding() == null) {
						this.saveOrUpRptData(Contants.URL_DOWNLOAD_BUF_PERSON,
								1, site);
					}
				}
				response.setData(listBuf);
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

				// logger.info("success get Buffer in TTDH, date now:"
				// + HelperClass.convertDateToString1(new Date()));
			} else {
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}

		} catch (Exception e) {
			logger.error("error get Buffer in TTDH: " + e.getMessage());
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	// 4.2.2. Service tra cứu đối tượng.

	// 4.2.3. Service tra cứu CMTND.

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadHandoverPA")
	public ResponseBase uploadHandoverPA(HandoverPA handover) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			if (handover != null) {
				if (handover.getHandovers() != null) {
					for (DetailHandover _dh : handover.getHandovers()) {
						/* Kiểm tra person */

						NicUploadJob nicU = uploadJobService
								.findUploadJobByTransId(_dh.getTransactionId())
								.getModel();
						if (_dh.getPersonStage().equals("KT")) {
							if (nicU != null) {
								nicU.getNicTransaction().setPersonCode(
										_dh.getPersonCode());
							}
							if (_dh.getApproveStage().equals("K")) {
								this.deletePerson(_dh.getTransactionId());
							}
						} else if (_dh.getPersonStage().equals("KM")) {
							if (nicU != null) {
								nicU.getNicTransaction().setPersonCode(
										_dh.getPersonCode());
							}

							EppPerson epp = eppPersonService
									.findPersonByPersonCode(nicU
											.getNicTransaction()
											.getPersonCode());
							epp.setOrgPerson(_dh.getPersonCode());
							eppPersonService.saveOrUpdateData(epp);
						}
						uploadJobService.saveOrUpdate(nicU);
					}
				}
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_UPLOAD_HANDOVER_PA, 1, null);
				// logger.info("success upload approve pa with update person from ttdh.");
			} else {
				logger.warn("Handover PA is not null");
				response.setMessage("Handover PA không được phép null");
			}
		} catch (Throwable e) {
			logger.error("error upload approve pa with update person from ttdh, err === "
					+ e.getMessage());
			response.setMessage("lỗi === " + e.getMessage());
		}
		return response;
	}

	/**
	 * @param handover
	 * @return
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadHandoverA")
	public ResponseBase uploadHandoverA(HandoverA handover) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_DSA);
		eppWsLog.setUrlRequest(Contants.URL_UPLOAD_HANDOVER_A);

		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(handover));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();

		}
		try {
			if (handover != null) {
				String nameApi = "uploadHandoverA";
				if (!this.checkConnectApi(nameApi, handover.getSiteCode())) {
					response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
					return response;
				}

				// Kiem tra ho so da day du chua?
				/*
				 * ResponseBase error = this.checkValidateHandover(handover);
				 * if(error != null){ return error; }
				 */
				// set KeyId Ws Log
				eppWsLog.setKeyId(handover.getPackageId());

				for (ReceiptManager rm : handover.getReceipts()) {
					if (rm.getHandovers() != null) {
						for (DetailHandover detailA : rm.getHandovers()) {
							BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
									.findTransactionById(detailA
											.getTransactionId());
							if (!baseFindTran.isError()
									|| baseFindTran.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseFindTran, eppWsLog, response,
										handover);
							}
							NicTransaction txn = baseFindTran.getModel();
							if (txn == null) {
								response.setCode(Contants.RESPONSE_CODE_FAIL_NOT_ENOUGH_HS);
								response.setMessage("Chưa upload đủ hồ sơ. Hồ sơ đang thiếu hiện tại: "
										+ detailA.getTransactionId());
								return response;
							}
						}
					}
				}

				// logger.info("start upload handover A to nic time: " +
				// HelperClass.convertDateToString1(new Date()));

				// Đã tồn tại hồ sơ trong danh sách
				Boolean checkTempGlobal = true;

				// Kiểm tra đã tồn tại của danh sách
				BaseModelSingle<NicListHandover> baseHan = listHandoverService
						.findByPackageId(new NicListHandoverId(handover
								.getPackageId(), NicListHandover.TYPE_LIST_A));
				if (!baseHan.isError() || baseHan.getMessage() != null) {
					return this.checkExistAndSaveException(baseHan, eppWsLog,
							response, handover);
				}
				NicListHandover hanCheck = baseHan.getModel();
				if (hanCheck == null) {
					// logger.info("start save handover A. packageId: " +
					// handover.getPackageId());
					NicListHandover nicHandover = new NicListHandover();
					nicHandover.setId(new NicListHandoverId(handover
							.getPackageId(), NicListHandover.TYPE_LIST_A));
					// nicHandover.setPackageId(handover.getPackageId());
					nicHandover.setSiteCode(handover.getSiteCode());

					if (StringUtils.isNotEmpty(handover.getApproveDate())) {
						Date date = HelperClass.convertStringToDate(handover
								.getApproveDate(), handover.getApproveDate()
								.length());
						nicHandover.setApproveDate(date);
					}

					if (StringUtils.isNotEmpty(handover.getOfferDate())) {
						Date date = HelperClass.convertStringToDate(handover
								.getOfferDate(), handover.getOfferDate()
								.length());
						nicHandover.setProposalDate(date);
					}
					nicHandover.setApproveUser(handover.getApproveUserName());
					nicHandover.setProposalUser(handover.getOfferUserName());
					nicHandover.setPackageName(Contants.CODE_PACKAGE_NAME_A);
					// nicHandover.setTypeList(NicListHandover.TYPE_LIST_A);
					nicHandover.setCreateDate(Calendar.getInstance().getTime());
					nicHandover.setCreateBy(handover.getOfferUserName());
					nicHandover.setHandoverStatus(0);
					// nicHandover.setStatusSyncXl(true);
					nicHandover.setCountTransaction(handover.getCount());

					nicHandover.setProposalName(handover.getProposalName());
					nicHandover.setApproveName(handover.getApproveName());
					nicHandover.setApprovePosition(handover
							.getApprovePosition());
					nicHandover.setCreatorName(handover.getCreatorName());
					nicHandover.setUpdateBy(handover.getUpdateBy());
					if (handover.getUpdateDate() != null) {
						Date date = HelperClass.convertStringToDate(handover
								.getUpdateDate(), handover.getUpdateDate()
								.length());
						nicHandover.setUpdateDate(date);
					}
					nicHandover.setUpdateByName(handover.getUpdateByName());
					nicHandover.setPlaceOfDelivery(handover
							.getPlaceOfDelivery());
					if (handover.getDateOfDelivery() != null) {
						Date date = HelperClass.convertStringToDate(handover
								.getDateOfDelivery(), handover
								.getDateOfDelivery().length());
						nicHandover.setDateOfDelivery(date);
					}
					// saveHandover
					BaseModelSingle<Void> baseSaveHan = listHandoverService
							.saveHandover(nicHandover);
					if (!baseSaveHan.isError()
							|| baseSaveHan.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveHan,
								eppWsLog, response, handover);
					}
				}
				for (ReceiptManager rm : handover.getReceipts()) {
					BaseModelList<EppRecieptManager> baseFindAllRM = rmService
							.findAllRecieptManager(rm.getReceiptNo());
					if (!baseFindAllRM.isError()
							|| baseFindAllRM.getMessage() != null) {
						return this.checkExistAndSaveExceptions(baseFindAllRM,
								eppWsLog, response, handover);
					}
					List<EppRecieptManager> list = baseFindAllRM.getListModel();
					if (list == null || list.size() == 0) {
						// logger.info("start save receipt manager, recieptNo: "
						// + rm.getReceiptNo());
						EppRecieptManager epp = new EppRecieptManager();
						epp.setRecieptNo(rm.getReceiptNo());
						epp.setOfficeName(rm.getOfficeName());
						epp.setFullname(rm.getName());
						if (StringUtils.isNotEmpty(rm.getDob())) {
							Date date = HelperClass.convertStringToDateTk(
									rm.getDob(), 0);
							epp.setDob(date);
							if (rm.getDob() != null) {
								switch (rm.getDob().length()) {
								case 4:
									epp.setDateDob("Y");
									break;
								case 6:
									epp.setDateDob("M");
									break;
								case 8:
									epp.setDateDob("D");
									break;
								default:
									break;
								}
							}
						}
						epp.setAddress(rm.getAddress());
						epp.setNinNumber(rm.getNin());
						epp.setPaymentAmount(rm.getPaymentAmount());
						epp.setCreateBy(rm.getCreateBy());
						epp.setCreateDate(Calendar.getInstance().getTime());
						epp.setRegSiteCode(rm.getRegSiteCode());
						/*
						 * if(StringUtils.isNotEmpty(rm.getDateOfIssue())){ Date
						 * date =
						 * HelperClass.convertStringToDate(rm.getDateOfIssue(),
						 * rm.getDateOfIssue().length());
						 * 
						 * }
						 */
						epp.setDateOfIssue(rm.getDateOfIssue());
						epp.setNote(rm.getNote());
						epp.setPlaceOfReceipt(rm.getPlaceOfReciept());
						if (StringUtils.isNotEmpty(rm.getDeliveryAtOffice()))
							epp.setDeliveryAtOffice(rm.getDeliveryAtOffice()
									.equals("Y") ? true : false);
						epp.setDeliveryOffice(rm.getDeliveryOffice());
						epp.setAmountOfPassport(rm.getAmountOfPassport());
						epp.setAmountOfRegistration(rm
								.getAmountOfRegistration());
						epp.setAmountOfPerson(rm.getAmountOfPerson());
						epp.setAmountOfImage(rm.getAmountOfImage());
						epp.setDocumentType(rm.getDocumentType());
						epp.setPrevPassportNo(rm.getPrevPassportNo());
						epp.setAddedDocuments(rm.getAddedDocuments());
						epp.setDocumentaryNo(rm.getDocumentaryNo());
						epp.setDocumentaryIssued(rm.getDocumentaryIssued());
						epp.setStatus(rm.getStatus());
						epp.setIsDelivered(rm.getIsDelivered());
						if (StringUtils.isNotEmpty(rm.getIsPostOffice()))
							epp.setIsPostOffice(rm.getIsPostOffice()
									.equals("Y") ? true : false);
						epp.setNoteOfDelivery(rm.getNoteOfDelivery());
						epp.setSignName(rm.getSignName());
						epp.setDocOfDelivery(rm.getDocOfDelivery());
						epp.setDocmentaryOffice(rm.getDocumentaryOffice());
						epp.setDocmentaryAddress(rm.getDocumentaryAddress());
						epp.setListCode(rm.getListCode());
						if (StringUtils.isNotEmpty(rm.getInputCompleted()))
							epp.setInputCompleted(rm.getInputCompleted()
									.equals("Y") ? true : false);
						epp.setDeletedBy(rm.getDeletedBy());
						if (rm.getDeletedDate() != null) {
							Date date = HelperClass.convertStringToDate(rm
									.getDeletedDate(), rm.getDeletedDate()
									.length());
							epp.setDeletedDate(date);
						}
						/* epp.setDeletedDate(rm.getDeletedDate()); */
						epp.setDeletedName(rm.getDeletedName());
						epp.setDeletedReason(rm.getDeletedReason());
						epp.setReceivedBy(rm.getReceivedBy());
						epp.setCreateByName(rm.getCreateByName());
						BaseModelSingle<Boolean> baseSaveRM = rmService
								.saveOrUpdateNew(epp);
						// rmService.save(epp);
						// check saveOrUpdate RecieptManager
						if (!baseSaveRM.isError()
								|| baseSaveRM.getMessage() != null) {
							return this.checkExistAndSaveException(baseSaveRM,
									eppWsLog, response, rm);
						}
					}
					if (rm.getHandovers() != null) {
						for (DetailHandover detailA : rm.getHandovers()) {
							/*
							 * Boolean checkTemp = true; EppListHandoverDetail
							 * tpCheck = eppListHandoverDetailService.
							 * getListPackageByPackageIdAndTranID
							 * (detailA.getPackageId(),
							 * detailA.getTransactionId()); if(tpCheck != null)
							 * continue;
							 */
							/*
							 * if(txn == null){ checkTemp = false;
							 * checkTempGlobal = false; //Kiểm tra trc bảng tạm
							 * TempTransactionPackage temk =
							 * tempTranPackageService
							 * .findTempByPackageIdOrTranId
							 * (detailA.getPackageId(),
							 * detailA.getTransactionId(), null); if(temk ==
							 * null){ //Đưa vào bảng tạm vì chưa có hồ sơ
							 * //logger
							 * .info("start save detail temp handoverA, packageId: "
							 * + detailA.getPackageId() + ", tranId: " +
							 * detailA.getTransactionId());
							 * TempTransactionPackage temp = new
							 * TempTransactionPackage();
							 * temp.setPackageId(detailA.getPackageId());
							 * temp.setTransactionId
							 * (detailA.getTransactionId());
							 * temp.setOfferStage(detailA.getOfferStage());
							 * temp.setApproveStage(detailA.getApproveStage());
							 * temp.setOfferNote(detailA.getNoteOffer());
							 * temp.setApproveNote(detailA.getNoteApprove());
							 * temp.setTypeList(7);
							 * tempTranPackageService.saveOrUpdateTemp(temp); }
							 * }
							 */
							// logger.info("start save detail handover A, packageId: "
							// + detailA.getPackageId() + ", tranId: " +
							// detailA.getTransactionId());
							BaseModelSingle<EppListHandoverDetail> baseGetHD = eppListHandoverDetailService
									.getListPackageByPackageIdAndTranID(
											detailA.getPackageId(),
											detailA.getTransactionId(),
											NicListHandover.TYPE_LIST_A);
							if (!baseGetHD.isError()
									|| baseGetHD.getMessage() != null) {

								return this
										.checkExistAndSaveException(baseGetHD,
												eppWsLog, response, handover);
							}
							EppListHandoverDetail tp = baseGetHD.getModel();
							if (tp == null) {
								tp = new EppListHandoverDetail();
								tp.setPackageId(detailA.getPackageId());
								tp.setTransactionId(detailA.getTransactionId());
								tp.setProposalStage(detailA.getOfferStage());
								tp.setApproveStage(detailA.getApproveStage());
								tp.setProposalNote(detailA.getNoteOffer());
								tp.setApproveNote(detailA.getNoteApprove());
								tp.setTypeList(NicListHandover.TYPE_LIST_A);
								BaseModelSingle<Boolean> baseHD = eppListHandoverDetailService
										.saveHandoverDetail(tp);
								if (!baseHD.isError()
										|| baseHD.getMessage() != null) {

									return this
											.checkExistAndSaveException(baseHD,
													eppWsLog, response, detailA);
								}

								// logger.info("===listHandoverDetail status: "
								// + success);

							}

							/* Đưa vào hàng đợi đồng bộ sang các ttxl */
							// Tam dong 20.07.2020
							// List<SiteGroups> sgList = siteService.findAll();
							// for (SiteGroups gs : sgList) {
							// this.addObjToQueueJob(
							// detailA.getTransactionId(),
							// Contants.QUEUE_OBJ_TYPE_HS,
							// gs.getGroupId(),
							// TRAN_STATUS_SUBMIT_PA_A,
							// Contants.URL_UPLOAD_HANDOVER_A);
							// }

							// reset chạy lại job check lần nữa
							BaseModelSingle<NicUploadJob> baseNicUp = uploadJobService
									.findUploadJobByTransId(detailA
											.getTransactionId());
							if (!baseNicUp.isError()
									|| baseNicUp.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseNicUp, eppWsLog, response, detailA);
							}
							NicUploadJob nicUp = baseNicUp.getModel();
							NicTransaction txn = nicUp.getNicTransaction();
							if (nicUp != null
									&& txn != null
									&& !txn.getRegSiteCode().equals(
											Contants.QUEUE_RECEIVER_MB)
									&& !txn.getRegSiteCode().equals(
											Contants.QUEUE_RECEIVER_MT)
									&& !txn.getRegSiteCode().equals(
											Contants.QUEUE_RECEIVER_MN)) {
								this.resetWorkflowCheckData(nicUp);

								txn.setPaBlacklistId(detailA.getPaBlacklistId());
								txn.setPaInqBllUser(detailA.getPaInqBllUser());
								txn.setPaArchiveCode(detailA.getPaArchiveCode());
								if (StringUtils.isNotEmpty(detailA
										.getPaSearchBio()))
									txn.setPaSearchBio(detailA.getPaSearchBio()
											.equals("Y") ? true : false);
								if (detailA.getPaJoinPersonDate() != null) {
									Date date = HelperClass
											.convertStringToDate(
													detailA.getPaJoinPersonDate(),
													detailA.getPaJoinPersonDate()
															.length());
									txn.setPaJoinPersonDate(date);
								}
								if (detailA.getPaSaveDocDate() != null) {
									Date date = HelperClass
											.convertStringToDate(detailA
													.getPaSaveDocDate(),
													detailA.getPaSaveDocDate()
															.length());
									txn.setPaSaveDocDate(date);
								}
								if (detailA.getPaSearchObjDate() != null) {
									Date date = HelperClass
											.convertStringToDate(
													detailA.getPaSearchObjDate(),
													detailA.getPaSearchObjDate()
															.length());
									txn.setPaSearchObjDate(date);
								}
								if (StringUtils.isNotBlank(detailA
										.getPersonStage())
										&& detailA.getPersonStage().length() == 2) {
									txn.setMatchedTypePerson(detailA
											.getPersonStage());
								}
								BaseModelSingle<Boolean> baseSaveOUTran = nicTransactionService
										.saveOrUpdateTransaction(txn);
								if (!baseSaveOUTran.isError()
										|| baseSaveOUTran.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseSaveOUTran, eppWsLog, response,
											handover);
								}
							}
							if (detailA.getPayments() != null) {
								for (PaymentDetail payment : detailA
										.getPayments()) {
									/*
									 * if(!checkTemp){ TempPaymentDetail temk =
									 * tempPaymentDetailService
									 * .findTempByTransactionId
									 * (payment.getTransactionId(),
									 * payment.getSubTypePayment()); if(temk ==
									 * null){ //đưa vào bảng tạm vì chưa có hồ
									 * sơ //logger.info(
									 * "start save temp detail payment, transactionId: "
									 * +payment.getTransactionId());
									 * TempPaymentDetail temp = new
									 * TempPaymentDetail();
									 * temp.setTransactionId
									 * (payment.getTransactionId());
									 * temp.setTypePayment
									 * (payment.getTypePayment());
									 * temp.setSubtypePayment
									 * (payment.getSubTypePayment());
									 * temp.setPaymentAmount
									 * (payment.getPaymentAmount());
									 * temp.setNote(payment.getNamePayment());
									 * temp
									 * .setStatusFee(payment.getStatusFee());
									 * tempPaymentDetailService
									 * .saveOrUpdateTemp(temp); } continue; }
									 */
									// check fail
									BaseModelSingle<NicTransactionPayment> baseTP = nicTransactionPaymentDao
											.findGetPaymentByTransaction(payment
													.getTransactionId());
									if (!baseTP.isError()
											|| baseTP.getMessage() != null) {

										return this.checkExistAndSaveException(
												baseTP, eppWsLog, response,
												payment);
									}

									NicTransactionPayment payments = baseTP
											.getModel();
									if (payments == null)
										continue;
									BaseModelSingle<NicTransactionPaymentDetail> baseFindDP = paymentDetailService
											.findDetailPaymentByTransactionId(
													payments.getPaymentId(),
													payment.getTypePayment(),
													payment.getSubTypePayment());
									if (!baseFindDP.isError()
											|| baseFindDP.getMessage() != null) {

										return this.checkExistAndSaveException(
												baseFindDP, eppWsLog, response,
												payment);
									}
									NicTransactionPaymentDetail payDetail = baseFindDP
											.getModel();
									if (payDetail != null)
										continue;// Đã tồn tại
									// logger.info("start save detail payment, paymentId: "
									// + payments.getPaymentId());
									NicTransactionPaymentDetail pay = new NicTransactionPaymentDetail();
									pay.setPaymentId(payments.getPaymentId());
									pay.setTypePayment(payment.getTypePayment());
									pay.setSubTypePayment(payment
											.getSubTypePayment());
									pay.setPaymentAmount(payment
											.getPaymentAmount());
									pay.setNote(payment.getNamePayment());
									pay.setStatusFee(payment.getStatusFee()
											.equals("Y") ? true : false);
									pay.setCreateBy(Contants.CREATE_BY_SYSTEM);
									pay.setCreateDate(Calendar.getInstance()
											.getTime());

									BaseModelSingle<Boolean> basePD = paymentDetailService
											.saveOrUpdatePaymentDetail(pay);
									// check fail
									if (!basePD.isError()
											|| basePD.getMessage() != null) {

										return this.checkExistAndSaveException(
												basePD, eppWsLog, response,
												payment);
									}
								}
							}

							/* Kiểm tra person */
							BaseModelSingle<NicUploadJob> baseNicU = uploadJobService
									.findUploadJobByTransId(detailA
											.getTransactionId());
							if (!baseNicU.isError()
									|| baseNicU.getMessage() != null) {

								return this.checkExistAndSaveException(
										baseNicU, eppWsLog, response, detailA);
							}
							NicUploadJob nicU = baseNicU.getModel();
							if (nicU != null) {
								NicTransaction nicTran = nicU
										.getNicTransaction();
								/*
								 * if(!_dh.getPersonStage().equals("KT")){
								 * this.savePersonByUploadTransaction
								 * (nicU,_dh.getPersonCode()); EppPerson epp =
								 * eppPersonService
								 * .findByTransactionId(_dh.getTransactionId());
								 * if(epp != null){
								 * if(_dh.getPersonStage().equals("KM")){
								 * epp.setOrgPerson(_dh.getPersonOrgCode());
								 * eppPersonService.saveOrUpdateData(epp); } }
								 * nicTran.setNote(_dh.getPersonOrgCode());
								 * //Luu tam du lieu OrgPersonCode }
								 */

								if (detailA.getOrgPerson() != null) {
									PersonModel person = detailA.getOrgPerson();
									BaseModelSingle<EppPerson> baseFindEppP = eppPersonService
											.findByStringCode(person
													.getPersonCode());
									if (!baseFindEppP.isError()
											|| baseFindEppP.getMessage() != null) {

										return this.checkExistAndSaveException(
												baseFindEppP, eppWsLog,
												response, detailA);
									}
									EppPerson epp = baseFindEppP.getModel();
									if (epp == null) {
										epp = new EppPerson();
										epp.setCreatedBy(person.getCreatedBy());
										if (person.getCreatedDate() != null) {
											Date date = HelperClass
													.convertStringToDate(
															person.getCreatedDate(),
															person.getCreatedDate()
																	.length());
											epp.setCreateTs(date);
										}
										epp.setDateOfBirth(person
												.getDateOfBirth());
										epp.setDateOfIdIssue(person
												.getDateOfIdIssue());
										epp.setEthnic(person.getEthNic());
										epp.setEthnicCode(person
												.getEthnicCode());
										epp.setFatherName(person
												.getFatherName());
										epp.setFatherSearchName(person
												.getFatherSearchName());
										epp.setGender(person.getGender());
										epp.setIdNumber(person.getIdNumber());
										epp.setMotherName(person
												.getMotherName());
										epp.setMotherSearchName(person
												.getMotherSearchName());
										epp.setName(person.getName());
										epp.setNationalityCode(person
												.getNationalityCode());
										epp.setNationalityName(person
												.getNationalityName());
										epp.setPersonCode(person
												.getPersonCode());
										if (!epp.getPersonCode().equals(
												person.getPersonOrgCode())) {
											epp.setOrgPerson(person
													.getPersonOrgCode());
										}
										epp.setPlaceOfBirthCode(person
												.getPlaceOfBirthCode());
										epp.setPlaceOfBirthName(person
												.getPlaceOfBirthName());
										epp.setPlaceOfIdIssueName(person
												.getPlaceOfIdIssueName());
										epp.setSrcDoc(detailA
												.getTransactionId());
										epp.setReligion(person.getReligion());
										epp.setReligionCode(person
												.getReligionCode());
										epp.setSearchName(person
												.getSearchName());
										epp.setUpdatedBy(person.getUpdatedBy());
										if (person.getUpdatedDate() != null) {
											Date date = HelperClass
													.convertStringToDate(
															person.getUpdatedDate(),
															person.getUpdatedDate()
																	.length());
											epp.setUpdateTs(date);
										}
										epp.setIsChecked((person.getIsChecked() != "" && person
												.getIsChecked().equals("Y")) ? true
												: false);
										epp.setDescription(person
												.getDescription());
										epp.setSrcOffice(person.getSrcOffice());
										epp.setStatus(person.getStatus());
										epp.setCreatedByName(person
												.getCreatedByName());
										epp.setUpdatedByName(person
												.getUpdatedByName());
										epp.setOtherName(person.getOtherName());
										epp.setCountryOfBirth(person
												.getCountryOfBirth());
										Long personId = eppPersonService
												.saveEppPerson(epp);

										// Kiểm tra thông tin gia đình
										if (person.getFamilies() != null
												&& person.getFamilies().size() > 0) {
											for (PersonFamily f : person
													.getFamilies()) {
												EppPersonFamily fmy = new EppPersonFamily();
												fmy.setName(f.getName());
												fmy.setDateOfBirth(f
														.getDateOfBirth());
												fmy.setLost(f.getLost());
												fmy.setRelationship(f
														.getRelationship());
												fmy.setGender(f.getGender());
												fmy.setSubjectPerson(personId);
												fmy.setCreatedBy(person
														.getCreatedBy());
												fmy.setCreateTs(new Date());
												BaseModelSingle<Boolean> baseSaveDF = eppPersonService
														.saveOrUpdateDataFamily(fmy);
												// check save EppPersonFamily
												if (!baseSaveDF.isError()
														|| baseSaveDF
																.getMessage() != null) {

													this.checkExistAndSaveException(
															baseSaveDF,
															eppWsLog, response,
															f);
												}
											}
										}
									}

								}

								nicTran.setPersonCode(detailA.getPersonCode());
								BaseModelSingle<Boolean> baseSaveOUTran = nicTransactionService
										.saveOrUpdateTransaction(nicTran);
								if (!baseSaveOUTran.isError()
										|| baseSaveOUTran.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseSaveOUTran, eppWsLog, response,
											handover);
								}
							}
						}
						if (rm.getBills() != null) {
							for (ReceiptBill rb : rm.getBills()) {
								BaseModelSingle<DetailRecieptFee> baseFindDR = drFeeService
										.findDetailRecieptFee(
												rb.getReceiptNo(),
												rb.getCode(), rb.getNumber());
								if (!baseFindDR.isError()
										|| baseFindDR.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseFindDR, eppWsLog, response,
											handover);
								}
								DetailRecieptFee fees = baseFindDR.getModel();
								if (fees != null)
									continue;
								// logger.info("start save receipt bill, code: "
								// + rb.getCode() + ", number: " +
								// rb.getNumber());
								DetailRecieptFee fee = new DetailRecieptFee();
								fee.setRecieptNo(rb.getReceiptNo());
								fee.setCodeBill(rb.getCode());
								fee.setNumberBill(rb.getNumber());
								fee.setPrice(rb.getPrice());
								fee.setPriceFlag(rb.getBillFlag());
								fee.setDescription(rb.getDescription());
								fee.setCreateBy(Contants.CREATE_BY_SYSTEM);
								fee.setCreateDate(Calendar.getInstance()
										.getTime());
								fee.setCashierName(rb.getCashierName());
								fee.setCreateByName(rb.getCreateByName());
								if (rb.getDateOfReceipt() != null) {
									Date date = HelperClass
											.convertStringToDate(rb
													.getDateOfReceipt(), rb
													.getDateOfReceipt()
													.length());
									fee.setDateOfReceipt(date);
								}
								fee.setCustomerName(rb.getCustomerName());
								BaseModelSingle<Boolean> baseSaveRB = drFeeService
										.saveOrUpdateNew(fee);
								// check save RecieptBill
								if (!baseSaveRB.isError()
										|| baseSaveRB.getMessage() != null) {

									return this.checkExistAndSaveException(
											baseSaveRB, eppWsLog, response, rb);
								}
							}
						}

						if (rm.getFeeRecieptPayment() != null) {
							for (FeeRecieptPaymentModel fr : rm
									.getFeeRecieptPayment()) {
								BaseModelList<FeeRecieptPayment> baseFindAllRP = feeRecieptPaymentService
										.findAllFeeRecieptPayment(fr
												.getRecieptNo());
								if (!baseFindAllRP.isError()
										|| baseFindAllRP.getMessage() != null) {
									return this.checkExistAndSaveExceptions(
											baseFindAllRP, eppWsLog, response,
											handover);
								}
								List<FeeRecieptPayment> listExist = baseFindAllRP
										.getListModel();
								// Xoa di neu da ton tai
								if (listExist != null && listExist.size() > 0) {
									BaseModelSingle<Boolean> baseDelRP = feeRecieptPaymentService
											.deleteObject(listExist);
									if (!baseDelRP.isError()
											|| baseDelRP.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseDelRP, eppWsLog, response,
												handover);
									}
								}
								// logger.info("start save receipt bill, code: "
								// + rb.getCode() + ", number: " +
								// rb.getNumber());
								FeeRecieptPayment fee = new FeeRecieptPayment();
								fee.setRecieptNo(fr.getRecieptNo());
								fee.setTypePayment(fr.getTypePayment());
								fee.setPrice(fr.getPrice());
								fee.setDescription(fr.getDescription());
								fee.setUnit(fr.getUnit());
								fee.setTotal(fr.getTotal());
								fee.setAmount(fr.getAmount());
								fee.setCreateBy(fr.getCreateBy());
								fee.setCreateDate(Calendar.getInstance()
										.getTime());
								fee.setCreateByName(fr.getCreateByName());
								BaseModelSingle<Boolean> baseSaveRP = feeRecieptPaymentService
										.saveOrUpdateNew(fee);
								// check save FeeRecieptPayment
								if (!baseSaveRP.isError()
										|| baseSaveRP.getMessage() != null) {

									return this.checkExistAndSaveException(
											baseSaveRP, eppWsLog, response, fr);
								}
							}
						}
					}
				}

				// Kiểm tra lại số lượng hồ sơ với số lượgn bản ghi trong bản
				// trung gian đã đủ chưa
				BaseModelList<EppListHandoverDetail> baseGetLP = eppListHandoverDetailService
						.getListPackageByPackageId(handover.getPackageId(),
								NicListHandover.TYPE_LIST_A);
				// check get List Handover Detail
				if (!baseGetLP.isError() || baseGetLP.getMessage() != null) {

					return this.checkExistAndSaveExceptions(baseGetLP,
							eppWsLog, response, handover);
				}
				List<EppListHandoverDetail> checklist = baseGetLP
						.getListModel();
				if (checklist == null
						|| checklist.size() != handover.getCount()) {
					response.setCode(Contants.RESPONSE_CODE_FAIL_NOT_ENOUGH_HS);
					response.setMessage("Chưa upload đủ hồ sơ.");
					return response;
				}

				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				// logger.info("success upload handoverA in TTDH, packageId: "+
				// handover.getPackageId() +", date now:" +
				// HelperClass.convertDateToString1(new Date()));

				String siteXl = "";
				BaseModelSingle<ConfigurationWorkflow> baseFindCW = configurationWorkflowService
						.findSiteRepositoryBySite(handover.getSiteCode(),
								new Date(), CONFIG_TYPE_XU_LY, true);
				// check find ConfigurationWorkflow
				if (!baseFindCW.isError() || baseFindCW.getMessage() != null) {

					return this.checkExistAndSaveException(baseFindCW,
							eppWsLog, response, handover);
				}
				ConfigurationWorkflow cfw = baseFindCW.getModel();
				if (cfw != null) {
					siteXl = cfw.getSiteIdTo();
				} else {
					SiteRepository site = siteService.getSiteRepoById(handover
							.getSiteCode());
					if (site != null) {
						siteXl = site.getSiteGroups().getGroupId();
					}
				}
				/* Đưa danh sách A vào hàng đợi chờ đồng bộ về ttxl */
				if (checkTempGlobal) {
					BaseModelList<EppListHandoverDetail> baseGetLH = eppListHandoverDetailService
							.getListPackageByPackageId(handover.getPackageId(),
									NicListHandover.TYPE_LIST_A);
					// check get List Handover Detail
					if (!baseGetLH.isError() || baseGetLH.getMessage() != null) {

						return this.checkExistAndSaveExceptions(baseGetLH,
								eppWsLog, response, handover);
					}
					List<EppListHandoverDetail> detail = baseGetLH
							.getListModel();
					if (detail != null && detail.size() > 0) {
						for (EppListHandoverDetail item : detail) {
							this.addObjToQueueJob(item.getTransactionId(),
									Contants.QUEUE_OBJ_TYPE_HS, siteXl,
									TRAN_STATUS_SUBMIT_PA_A, null);
						}
					}
				}

				if (StringUtils.isNotEmpty(siteXl)) {
					BaseModelSingle<Boolean> baseAddQJ = this.addObjToQueueJob(
							handover.getPackageId(),
							Contants.QUEUE_OBJ_TYPE_DA, siteXl, null, null);
					// check save Obj to Queue Job
					if (!baseAddQJ.isError() || baseAddQJ.getMessage() != null) {

						return this.checkExistAndSaveException(baseAddQJ,
								eppWsLog, response, handover);
					}
				}

				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_UPLOAD_HANDOVER_A, 1,
						handover.getSiteCode());
			} else {
				// logger.warn("HandoverA is not null");
				response.setMessage("HandoverA không được phép null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error(e.getMessage() + " - uploadHandoverA fail.");
			// save log
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - uploadHandoverA fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadHandoverC/{site}")
	public Response<List<HandoverC>> downloadHandoverC(
			@PathParam("site") String site) {
		Response<List<HandoverC>> response = new Response<List<HandoverC>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "downloadHandoverC";
		// if(!this.checkConnectApi(nameApi, site)){
		// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return response;
		// }
		List<HandoverC> listC = new ArrayList<HandoverC>();
		// logger.info("start download list C in ttdh:" +
		// HelperClass.convertDateToString1(new Date()));

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_DDSC);
		eppWsLog.setUrlRequest(Contants.URL_DOWNLOAD_HANDOVER_C);
		eppWsLog.setDataRequest(site);
		eppWsLog.setKeyId(site + "_"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()));

		ResponseBase rb = new ResponseBase();
		try {
			int maxTotal = 10;
			Parameters pm = parametersService.getParamDetails(
					Contants.CREATE_BY_SYSTEM,
					Contants.PARA_NAME_MAX_COUNT_GET_BUF);
			if (pm != null) {
				maxTotal = Integer.valueOf(pm.getParaShortValue());
			}
			BaseModelList<SyncQueueJob> baseFindQByR = queueJobService
					.findQueueByReceiver(site,
							Contants.CODE_STATUS_QUEUE_PENDING,
							Contants.QUEUE_OBJ_TYPE_DC, maxTotal);
			if (!baseFindQByR.isError() || baseFindQByR.getMessage() != null) {
				rb = this.checkExistAndSaveExceptions(baseFindQByR, eppWsLog,
						rb, new Object());
				response.setCode(rb.getCode());
				response.setMessage(rb.getMessage());
				return response;
			}
			List<SyncQueueJob> queueC = baseFindQByR.getListModel();
			// logger.info("total size list C in queue:" + queueC.size());
			for (SyncQueueJob queue : queueC) {
				BaseModelSingle<NicListHandover> baseFindHan = listHandoverService
						.findByPackageId(new NicListHandoverId(queue
								.getObjectId(), NicListHandover.TYPE_LIST_C));
				if (!baseFindHan.isError() || baseFindHan.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseFindHan, eppWsLog,
							rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				NicListHandover eppHan = baseFindHan.getModel();
				if (eppHan != null) {
					HandoverC handover = new HandoverC();
					handover.setPackageId(eppHan.getId().getPackageId());
					handover.setType("C");
					handover.setApproveName(eppHan.getApproveName());
					handover.setIdQueue(queue.getId());
					List<DetailHandoverC> detailCList = new ArrayList<DetailHandoverC>();
					BaseModelList<EppListHandoverDetail> baseGetHan = eppListHandoverDetailService
							.getListPackageByPackageId(eppHan.getId()
									.getPackageId(),
									NicListHandover.TYPE_LIST_C);
					if (!baseGetHan.isError()
							|| baseGetHan.getMessage() != null) {
						rb = this.checkExistAndSaveExceptions(baseGetHan,
								eppWsLog, rb, new Object());
						response.setCode(rb.getCode());
						response.setMessage(rb.getMessage());
						return response;
					}
					List<EppListHandoverDetail> detailList = baseGetHan
							.getListModel();
					if (detailList != null) {

						for (EppListHandoverDetail tp : detailList) {
							DetailHandoverC dtHanC = new DetailHandoverC();
							List<PaymentDetail> listPD = new ArrayList<PaymentDetail>();
							dtHanC.setTransactionId(tp.getTransactionId());
							BaseModelSingle<NicUploadJob> baseFindUJ = uploadJobService
									.findUploadJobByTransId(tp
											.getTransactionId());
							if (!baseFindUJ.isError()
									|| baseFindUJ.getMessage() != null) {
								rb = this.checkExistAndSaveException(
										baseFindUJ, eppWsLog, rb, new Object());
								response.setCode(rb.getCode());
								response.setMessage(rb.getMessage());
								return response;
							}
							NicUploadJob nicUp = baseFindUJ.getModel();
							BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
									.findTransactionById(tp.getTransactionId());
							if (!baseFindTran.isError()
									|| baseFindTran.getMessage() != null) {
								rb = this.checkExistAndSaveException(
										baseFindTran, eppWsLog, rb,
										new Object());
								response.setCode(rb.getCode());
								response.setMessage(rb.getMessage());
								return response;
							}
							NicTransaction txn = baseFindTran.getModel();
							if (txn != null) {
								dtHanC.setReceiptNo(txn.getRecieptNo());
								dtHanC.setRegistrationNo(txn
										.getRegistrationNo());
								dtHanC.setTransactionStatus(txn
										.getTransactionStatus());
							}
							if (nicUp != null) {
								// dtHanC.setPersonId(nicUp.getOriginalyPersonId());
								// List<EppPerson> lstPer =
								// eppPersonService.findByGlobalId(nicUp.getOriginalyPersonId());

								// if(lstPer != null && lstPer.size() > 0){
								// EppPerson person = lstPer.get(0);
								// dtHanC.setGlobalId(person.getGlobalId());
								// }
							}

							BaseModelSingle<NicTransactionPayment> baseFindPayment = nicTransactionPaymentDao
									.findGetPaymentByTransaction(tp
											.getTransactionId());
							if (!baseFindPayment.isError()
									|| baseFindPayment.getMessage() != null) {
								rb = this.checkExistAndSaveException(
										baseFindPayment, eppWsLog, rb,
										new Object());
								response.setCode(rb.getCode());
								response.setMessage(rb.getMessage());
								return response;
							}
							NicTransactionPayment payment = baseFindPayment
									.getModel();
							List<NicTransactionPaymentDetail> listPayD = null;
							if (payment != null) {
								BaseModelList<NicTransactionPaymentDetail> baseFindDP = paymentDetailService
										.findListDetailPaymentList(payment
												.getPaymentId());
								if (!baseFindDP.isError()
										|| baseFindDP.getMessage() != null) {
									rb = this.checkExistAndSaveExceptions(
											baseFindDP, eppWsLog, rb,
											new Object());
									response.setCode(rb.getCode());
									response.setMessage(rb.getMessage());
									return response;
								}
								listPayD = baseFindDP.getListModel();
							}

							if (listPayD != null && listPayD.size() > 0) {
								for (NicTransactionPaymentDetail td : listPayD) {
									PaymentDetail paymentDetail = new PaymentDetail();
									paymentDetail.setTransactionId(tp
											.getTransactionId());
									paymentDetail.setNamePayment(td.getNote());
									paymentDetail.setTypePayment(td
											.getTypePayment());
									paymentDetail.setSubTypePayment(td
											.getSubTypePayment());
									paymentDetail.setPaymentAmount(td
											.getPaymentAmount());
									paymentDetail
											.setStatusFee(td.isStatusFee() ? "Y"
													: "N");
									listPD.add(paymentDetail);
								}
							}
							dtHanC.setPayments(listPD);
							BaseModelList<NicDocumentData> baseFindAllDoc = documentDataDao
									.findAllByTransactionId(tp
											.getTransactionId());
							if (!baseFindAllDoc.isError()
									|| baseFindAllDoc.getMessage() != null) {
								rb = this.checkExistAndSaveExceptions(
										baseFindAllDoc, eppWsLog, rb,
										new Object());
								response.setCode(rb.getCode());
								response.setMessage(rb.getMessage());
								return response;
							}
							List<NicDocumentData> listDoc = baseFindAllDoc
									.getListModel();
							NicDocumentData docData = null;
							if (listDoc != null && listDoc.size() > 0) {
								for (NicDocumentData doc : listDoc) {
									if (doc.getStatus().equals("PERSONALIZED")) {
										docData = doc;
										break;
									} else if (doc.getStatus().equals(
											"ISSUANCE")) {
										docData = doc;
										break;
									}
								}
							}
							InfoPassportC info = null;
							// Lấy thông tin hộ chiếu
							if (docData != null) {
								info = new InfoPassportC();
								if (docData != null) {
									dtHanC.setPassportNo(docData.getId()
											.getPassportNo());
									info.setChipSerialNo(docData
											.getChipSerialNo());
									info.setPassportNo(docData.getId()
											.getPassportNo());
									// info.setDateOfExpiry(docData.getDateOfExpiry());
									if (docData.getDateOfExpiry() != null) {
										String date = service.perso.util.HelperClass
												.convertDateToString(docData
														.getDateOfExpiry(),
														STR_FORMAT_DATE_yyyyMMddHHmmss);
										info.setDateOfExpiry(date);
									}
									// info.setDateOfIssue(docData.getDateOfIssue());
									if (docData.getDateOfExpiry() != null) {
										String date = service.perso.util.HelperClass
												.convertDateToString(docData
														.getDateOfIssue(),
														STR_FORMAT_DATE_yyyyMMddHHmmss);
										info.setDateOfIssue(date);
									}
									info.setIcaoLine1(docData.getIcaoLine1());
									info.setIcaoLine2(docData.getIcaoLine2());
									info.setSignerName(docData.getSigner());
									info.setSignerPosition(docData
											.getPositionSigner());
									info.setStatus(docData.getStatus());
								}
								NicRegistrationData reg = txn
										.getNicRegistrationData();
								info.setFpEncode(reg != null ? reg
										.getFpEncode() : null);
								if (txn != null) {
									info.setPlaceOfIssueId(docData
											.getPlaceOfIssueCode());
									info.setPlaceOfIssueName(this
											.getSiteName(docData
													.getPlaceOfIssueCode()));
									info.setPassportType(txn.getPassportType());
								}
								info.setIsEpassport(txn.getIsEpassport()
										.equals("Y") ? true : false);
								info.setPersonId(nicUp != null ? nicUp
										.getNicTransaction().getPersonCode()
										+ "" : null);
							}

							dtHanC.setInfo(info);
							detailCList.add(dtHanC);

						}
						handover.setHandovers(detailCList);
						listC.add(handover);
						BaseModelSingle<Boolean> baseUpdateSQJ = queueJobService
								.updateStatusQueueJob(queue.getId(),
										Contants.CODE_STATUS_QUEUE_DOING);
						if (!baseUpdateSQJ.isError()
								|| baseUpdateSQJ.getMessage() != null) {
							rb = this.checkExistAndSaveException(baseUpdateSQJ,
									eppWsLog, rb, new Object());
							response.setCode(rb.getCode());
							response.setMessage(rb.getMessage());
							return response;
						}
						/* Lưu bảng thống kê truyền nhận */
						if (listC != null && listC.size() > 0
								&& queue.getDateUpdateStatusHanding() == null) {
							this.saveOrUpRptData(
									Contants.URL_DOWNLOAD_HANDOVER_C, 1, site);
						}
					}
				}
			}
			response.setData(listC);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

		} catch (Exception e) {
			logger.error(e.getMessage() + " - download HandoverC fail.");
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - downloadHandoverC fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}

			return response;
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateStatus")
	public ResponseBase updateStatus(StatusInfo statusInfo) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		logger.info("start update status receive time:"
				+ HelperClass.convertDateToString1(new Date()));

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_US);
		eppWsLog.setUrlRequest(Contants.URL_UPDATE_STATUS);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(statusInfo));
		} catch (IOException e1) {
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}

		try {
			if (statusInfo != null) {
				if (statusInfo.getTranStatus() != null) {
					for (TransactionStatusInfo tranStatus : statusInfo
							.getTranStatus()) {
						eppWsLog.setKeyId(tranStatus.getTransactionId());
						if (StringUtils.isBlank(tranStatus.getTransactionId())
								|| StringUtils.isBlank(tranStatus
										.getPassportNo())) {
							logger.error(
									StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
											.getDesc()
											+ "[FieldName: TransactionID or PassportNo]",
									StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
											.convertToFaultDetail());
							response.setMessage(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
									.getDesc()
									+ " [FieldName: TransactionID or PassportNo] "
									+ StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
											.getDesc());
							return response;
						}

						String transactionId = tranStatus.getTransactionId();
						BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
								.findTransactionById(transactionId);
						if (!baseFindTran.isError()
								|| baseFindTran.getMessage() != null) {
							throw new Exception(baseFindTran.getMessage());
						}
						NicTransaction transaction = baseFindTran.getModel();
						if (transaction == null) {
							logger.error(
									StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc()
											+ "[FieldName: Transaction Data]",
									StatusCode.SYNCWS_DATA_NOT_FOUND
											.convertToFaultDetail());
							throw new FaultException(
									StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc()
											+ "[FieldName: Transaction Data]",
									StatusCode.SYNCWS_DATA_NOT_FOUND
											.convertToFaultDetail());
						}

						String transactionStatus = tranStatus
								.getTransactionStatus();
						Date statusUpdateDatetime = new Date();
						if (StringUtils.isNotEmpty(tranStatus.getUpdateDate())) {

							statusUpdateDatetime = HelperClass
									.convertStringToDate(
											tranStatus.getUpdateDate(),
											tranStatus.getUpdateDate().length());
						}
						String userId = tranStatus.getUpdateBy();
						String workstationId = tranStatus.getUpdateWkstnID();
						if (!(transactionStatus != null
								&& transaction.getTransactionStatus() != null
								&& transactionStatus
										.equals(Contants.TRANSACTION_STATUS_RIC_RECEIVED) && transaction
								.getTransactionStatus().equals(
										Contants.TRANSACTION_STATUS_RIC_ISSUED))) {
							transaction.setTransactionStatus(transactionStatus);
						}
						transaction.setUpdateDatetime(statusUpdateDatetime);
						transaction.setUpdateBy(userId);
						transaction.setUpdateWkstnId(workstationId);

						BaseModelSingle<Boolean> baseUpdateTran = nicTransactionService
								.saveOrUpdateTransaction(transaction);
						if (!baseUpdateTran.isError()
								|| baseUpdateTran.getMessage() != null) {
							throw new Exception(baseUpdateTran.getMessage());
						}

						// Hoald thêm: cập nhật thông tin vào bảng hộ chiếu.
						NicDocumentData nicDocData = documentDataService
								.findDocDataById(tranStatus.getTransactionId(),
										tranStatus.getPassportNo());

						nicDocData.setReceiveBy(userId);
						nicDocData.setReceiveDatetime(statusUpdateDatetime);
						BaseModelSingle<Void> baseUpdatePassport = documentDataService
								.saveOrUpdateDocData(nicDocData);
						if (!baseUpdatePassport.isError()
								|| baseUpdatePassport.getMessage() != null) {
							throw new Exception(baseUpdatePassport.getMessage());
						}

						String transactionStage = getTransactionStage(tranStatus
								.getTransactionStatus());
						NicTransactionLog transactionLog = new NicTransactionLog();
						transactionLog.setRefId(transactionId);
						transactionLog.setLogCreateTime(new Date());
						// transactionLog.setTransactionStage(NicTransactionService.TRANSACTION_STAGE_DISPATCH);
						transactionLog.setTransactionStage(transactionStage);
						transactionLog.setTransactionStatus(tranStatus
								.getTransactionStatus());
						transactionLog.setStartTime(statusUpdateDatetime);
						transactionLog.setEndTime(statusUpdateDatetime);
						transactionLog.setOfficerId(tranStatus.getUpdateBy());
						transactionLog.setWkstnId(workstationId);

						nicTransactionLogService.save(transactionLog);
					}
				}
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			if (statusInfo.getTranStatus() != null
					&& statusInfo.getTranStatus().size() > 0) {
				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_UPDATE_STATUS, statusInfo
						.getTranStatus().size(), null);
			}

			// logger.info("success update status receive time:"
			// + HelperClass.convertDateToString1(new Date()));
		} catch (FaultException e) {
			logger.error(e.getMessage() + " - updateStatus fail.");
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - updateStatus fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(ex) + " - updateStatus fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/issuedPassport")
	public ResponseBase issuedPassport(StatusPassports statusPassports) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// logger.info("start update status passport issue time:"
		// + HelperClass.convertDateToString1(new Date()));
		String key = "";
		try {
			if (statusPassports != null) {
				if (statusPassports.getPassportsStatus() != null) {
					for (PassportStatusInfo statusInfo : statusPassports
							.getPassportsStatus()) {
						key = statusInfo.getTransactionId();
						if (StringUtils.isBlank(statusInfo.getPassportNo())) {
							logger.error(
									StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
											.getDesc()
											+ "[FieldName: PassportNo]",
									StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
											.convertToFaultDetail());
							throw new FaultException(
									StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc()
											+ "[FieldName: PassportNo]",
									StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
											.convertToFaultDetail());
						}
						if (StringUtils.isBlank(statusInfo.getTransactionId())) {
							logger.error(
									StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
											.getDesc()
											+ "[FieldName: TransactionId]",
									StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
											.convertToFaultDetail());
							throw new FaultException(
									StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc()
											+ "[FieldName: TransactionId]",
									StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
											.convertToFaultDetail());
						}

						// Đồng bộ trạng thái bảng transaction
						String transactionId = statusInfo.getTransactionId();
						String transactionStatus = Contants.TRANSACTION_STATUS_RIC_ISSUED;
						NicTransaction transaction = nicTransactionService
								.findById(transactionId);
						if (transaction == null) {
							logger.error(
									StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc()
											+ "[FieldName: Transaction Data]",
									StatusCode.SYNCWS_DATA_NOT_FOUND
											.convertToFaultDetail());
							throw new FaultException(
									StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc()
											+ "[FieldName: Transaction Data]",
									StatusCode.SYNCWS_DATA_NOT_FOUND
											.convertToFaultDetail());
						}

						Date updateDateTime = new Date();
						if (StringUtils.isNotEmpty(statusInfo.getUpdateDate())) {

							updateDateTime = HelperClass.convertStringToDate(
									statusInfo.getUpdateDate(), statusInfo
											.getUpdateDate().length());
						}
						String userId = statusInfo.getUpdateBy();
						String workstationId = statusInfo.getUpdateWkstnID();

						transaction.setTransactionStatus(transactionStatus);
						transaction.setUpdateDatetime(updateDateTime);
						transaction.setUpdateBy(userId);
						transaction.setUpdateWkstnId(workstationId);

						nicTransactionService
								.saveOrUpdateTransaction(transaction);

						// Hoald thêm: ghi dữ liệu bảng EPP_ISSUANCE
						EppIssuance eppIssuance = new EppIssuance();
						eppIssuance.setReceiptNo(statusInfo.getReceiptNo());
						eppIssuance.setDocumentCode(statusInfo
								.getDocumentCode());
						eppIssuance.setIoDocCode(statusInfo.getIoDocCode());
						eppIssuance.setIoDocType(statusInfo.getIoDocType());
						if (StringUtils.isNotBlank(statusInfo
								.getDateOfDelivery())) {
							eppIssuance.setDateOfDelivery(DateUtil.strToDate(
									statusInfo.getDateOfDelivery(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						eppIssuance.setDeliveryName(statusInfo
								.getDeliveryName());
						eppIssuance.setDeliveryNote(statusInfo
								.getDeliveryNote());
						eppIssuance.setDeliveryOffice(statusInfo
								.getDeliveryOffice());
						eppIssuance.setDeliveryUser(statusInfo
								.getDeliveryUser());
						eppIssuance.setRecipient(statusInfo.getRecipient());
						eppIssuance.setRecipientIdNumber(statusInfo
								.getRecipientIdNumber());
						eppIssuance.setRecipientOffice(statusInfo
								.getRecipientOffice());
						eppIssuance.setIssResult(statusInfo.getIssResult());
						eppIssuance.setTraKQId(statusInfo.getTraKQId());
						Long id = eppIssuanceService
								.saveEppIssuance(eppIssuance);
						this.addObjToQueueJob(String.valueOf(id),
								Contants.QUEUE_OBJ_TYPE_HS_ISSUANCE,
								Contants.QUEUE_RECEIVER_A08_HH, null, null);

						String transactionStage = getTransactionStage(transactionStatus);
						NicTransactionLog transactionLog = new NicTransactionLog();
						transactionLog.setRefId(transactionId);
						transactionLog.setLogCreateTime(new Date());
						transactionLog.setTransactionStage(transactionStage);
						transactionLog.setTransactionStatus(transactionStatus);
						transactionLog.setStartTime(updateDateTime);
						transactionLog.setEndTime(updateDateTime);
						transactionLog.setOfficerId(statusInfo.getUpdateBy());
						transactionLog.setWkstnId(workstationId);

						nicTransactionLogService.save(transactionLog);

						// đồng bộ trạng thái bảng passport
						String passportNo = statusInfo.getPassportNo();
						String passportS = Contants.TRANSACTION_STAGE_RIC_ISSUANCE;

						// BaseModelSingle<NicDocumentData> baseFindDocData =
						// documentDataService
						// .findByDocNumber(passportNo);
						// NicDocumentData documentData = baseFindDocData
						// .getModel();
						// if (documentData == null) {
						// throw new FaultException(
						// StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc()
						// + "[FieldName: Document Data]",
						// StatusCode.SYNCWS_DATA_NOT_FOUND
						// .convertToFaultDetail());
						// }
						//
						// if (StringUtils.equals(passportS, "OK")
						// || StringUtils.equals(passportS, "ISSUANCE")
						// || StringUtils.equals(passportS, "ACTIVE")
						// || StringUtils
						// .equals(passportS,
						// Contants.TRANSACTION_STATUS_RIC_CARD_ISSUED)) { //
						// ISSUED
						// if (documentData.getActiveFlag() == null)
						// documentData.setActiveFlag(Boolean.TRUE);
						// if (documentData.getIssuedFlag() == null)
						// documentData.setIssuedFlag(Boolean.TRUE);
						// documentData.setIssueBy(userId);
						// documentData.setIssueDatetime(updateDateTime);
						// }
						//
						// documentData.setStatus(passportS);
						// documentData.setStatusUpdateTime(updateDateTime);
						// documentData.setUpdateBy(userId);
						// documentData.setUpdateWkstnId(workstationId);
						//
						// documentDataService.saveOrUpdate(documentData);
						//
						// NicDocumentHistory documentHistory = new
						// NicDocumentHistory();
						// documentHistory.setPassportNo(passportNo);
						// documentHistory.setStatus(passportS);
						// documentHistory.setStatusUpdateBy(userId);
						// documentHistory.setStatusUpdateTime(updateDateTime);
						//
						// documentHistoryService.save(documentHistory);

						// Hoald tạm đóng điều hướng.
						/*
						 * String siteXl = ""; ConfigurationWorkflow cfw =
						 * configurationWorkflowService
						 * .findSiteRepositoryBySite(
						 * transaction.getRegSiteCode(), new Date(),
						 * CONFIG_TYPE_XU_LY, true) .getModel(); if (cfw !=
						 * null) { siteXl = cfw.getSiteIdTo(); } else {
						 * SiteRepository site = siteService
						 * .getSiteRepoById(transaction .getRegSiteCode()); if
						 * (site != null) { siteXl =
						 * site.getSiteGroups().getGroupId(); } }
						 * 
						 * if (StringUtils.isNotEmpty(siteXl)) {
						 * this.addObjToQueueJob(
						 * transaction.getTransactionId(),
						 * Contants.QUEUE_OBJ_TYPE_ISSUE_C, siteXl, null,
						 * Contants.URL_ISSUED_PASSPORT); }
						 */

						/*
						 * List<BorderGate> borderList = borderGateService
						 * .findBorderGateBySync(Contants.FLAG_Y); if
						 * (borderList != null) { for (BorderGate gate :
						 * borderList) {
						 * this.addObjToQueueXncJob(OBJ_TYPE_QUEUE_XNC_PP,
						 * gate.getCode(), passportNo); } }
						 */
					}
					response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					// logger.info("success update status issue, time:"
					// + HelperClass.convertDateToString1(new Date()));

					if (statusPassports.getPassportsStatus() != null
							&& statusPassports.getPassportsStatus().size() > 0) {
						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(Contants.URL_ISSUED_PASSPORT,
								statusPassports.getPassportsStatus().size(),
								null);
					}
				}
			}
		} catch (FaultException e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());

			EppWsLog eppWsLog = new EppWsLog();
			eppWsLog.setType(Contants.TYPE_IP);
			eppWsLog.setUrlRequest(Contants.URL_ISSUED_PASSPORT);
			try {
				eppWsLog.setDataRequest(new ObjectMapper()
						.writeValueAsString(statusPassports));
			} catch (IOException e1) {
				logger.error(e1.getMessage() + "convert object to json fail.");
				e1.printStackTrace();

			}
			eppWsLog.setKeyId(key);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - issuedPassport fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			logger.error(e.getMessage());

			EppWsLog eppWsLog = new EppWsLog();
			eppWsLog.setType(Contants.TYPE_IP);
			eppWsLog.setUrlRequest(Contants.URL_ISSUED_PASSPORT);
			try {
				eppWsLog.setDataRequest(new ObjectMapper()
						.writeValueAsString(statusPassports));
			} catch (IOException e1) {
				logger.error(e1.getMessage() + "convert object to json fail.");
				e1.printStackTrace();

			}
			eppWsLog.setKeyId(key);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - issuedPassport fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}

		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadPerson/{site}")
	public Response<InfoPerson> downloadPerson(@PathParam("site") String site) {
		Response<InfoPerson> response = new Response<InfoPerson>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "downloadPerson";
		// if(!this.checkConnectApi(nameApi, site)){
		// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return response;
		// }
		logger.info("start download perso to ttxl, time:"
				+ HelperClass.convertDateToString1(new Date()));
		try {
			BaseModelSingle<SyncQueueJob> baseFindQ = queueJobService
					.findQueueByStatus(site,
							Contants.CODE_STATUS_QUEUE_PENDING,
							OBJ_TYPE_QUEUE_CREATE_PERSON);
			SyncQueueJob queue = baseFindQ.getModel();
			if (queue != null) {
				Long idPerson = Long.parseLong(queue.getObjectId());
				EppPerson person = eppPersonService.getPersonById(idPerson);
				if (person != null) {
					InfoPerson personIf = new InfoPerson(person);
					List<EppPersonAttchmnt> attList = eppPersonService
							.findAllEppPersonAttchmnt1(null, idPerson);
					if (attList != null && attList.size() > 0) {
						personIf.setPhoto(attList.get(0).getBase64());
					}
					personIf.setIdQueue(queue.getId());
					response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					response.setData(personIf);

					// logger.info("success download person, personId: "
					// + idPerson);
				} else {
					logger.error("Not found person, by personId: " + idPerson);
					throw new Exception("Not found person.");
				}
				queueJobService.updateStatusQueueJob(queue.getId(),
						Contants.CODE_STATUS_QUEUE_DOING);

				/* Lưu bảng thống kê truyền nhận */
				if (response.getData().getPersonCode() != null
						&& queue.getDateUpdateStatusHanding() == null) {
					this.saveOrUpRptData(Contants.URL_DOWNLOAD_PERSON, 1, site);
				}
			} else {
				logger.info("No data to synchronize");
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}
		} catch (Exception e) {
			logger.error("error download person === " + e.getMessage());
			e.printStackTrace();
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadTransaction/{site}")
	public Response<Transaction> downloadTransaction(
			@PathParam("site") String site) {
		Response<Transaction> response = new Response<Transaction>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "downloadTransaction";
		// if(!this.checkConnectApi(nameApi, site)){
		// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return response;
		// }
		// logger.info("start download transaction to ttxl, time:" +
		// HelperClass.convertDateToString1(new Date()));
		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_DHS);
		eppWsLog.setUrlRequest(Contants.URL_DOWNLOAD_TRANSACTION);
		eppWsLog.setDataRequest(site);
		eppWsLog.setKeyId(site + "_"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()));

		ResponseBase rb = new ResponseBase();
		try {
			BaseModelSingle<SyncQueueJob> baseFindQ = queueJobService
					.findQueueByStatus(site,
							Contants.CODE_STATUS_QUEUE_PENDING,
							Contants.QUEUE_OBJ_TYPE_HS);
			if (!baseFindQ.isError() || baseFindQ.getMessage() != null) {
				rb = this.checkExistAndSaveException(baseFindQ, eppWsLog, rb,
						new Object());
				response.setCode(rb.getCode());
				response.setMessage(rb.getMessage());
				return response;
			}
			SyncQueueJob queue = baseFindQ.getModel();
			if (queue != null) {
				Transaction transaction = new Transaction();
				List<PersonFamily> listFamily = new ArrayList<PersonFamily>();
				BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
						.findTransactionById(queue.getObjectId());
				if (!baseFindTran.isError()
						|| baseFindTran.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseFindTran,
							eppWsLog, rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				NicTransaction txn = baseFindTran.getModel();
				if (txn != null) {
					transaction.setNote(txn.getNote()); // chi tiết nội dung đề
														// nghị nếu có
					transaction.setTransactionId(txn.getTransactionId());
					transaction.setNin(txn.getNin());
					transaction.setTransactionType(txn.getTransactionSubType());
					transaction.setTransactionStatus(queue.getTranStatus());
					if (txn.getDateOfApplication() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(
										txn.getDateOfApplication(),
										STR_FORMAT_DATE_yyyyMMdd);
						transaction.setDateOfApplication(date);
					}
					// transaction.setEstDateOfCollection(txn.getEstDateOfCollection());
					if (txn.getEstDateOfCollection() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(
										txn.getEstDateOfCollection(),
										STR_FORMAT_DATE_yyyyMMdd);
						transaction.setEstDateOfCollection(date);
					}
					if (txn.getIsPostOffice() != null) {
						transaction.setIsPostOffice(txn.getIsPostOffice() ? "Y"
								: "N");
					}
					transaction.setIssSiteCode(txn.getIssSiteCode());
					transaction.setRegSiteCode(txn.getRegSiteCode());
					if (txn.getIsEpassport() != null) {
						transaction.setPassportStyle(txn.getIsEpassport() ? "Y"
								: "N");
					}
					transaction.setPrevPassportNo(txn.getPrevPassportNo());
					// transaction.setPrevDateOfIssue(txn.getPrevDateOfIssue());
					if (txn.getPrevDateOfIssue() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(txn.getPrevDateOfIssue(),
										STR_FORMAT_DATE_yyyyMMdd);
						transaction.setPrevDateOfIssue(date);
					}
					transaction.setPriority(txn.getPriority());
					transaction.setRecieptNo(txn.getRecieptNo());
					transaction.setRegistrationNo(txn.getRegistrationNo());
					transaction.setPlaceIssuance(txn.getAppointmentPlace());
					transaction.setIdQueue(queue.getId());
					transaction.setApplicant(txn.getApplicant());
					transaction.setAppointmentPlace(txn.getAppointmentPlace());
					transaction.setPrevDateOfExpr(txn.getPrevDateOfExpr());
					transaction.setRegistrationType(txn.getRegistrationType());
					transaction.setPaBlacklistId(txn.getPaBlacklistId());
					transaction.setPaInqBllUser(txn.getPaInqBllUser());
					transaction.setPaArchiveCode(txn.getPaArchiveCode());
					if (txn.getPaJoinPersonDate() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(txn.getPaJoinPersonDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						transaction.setPaJoinPersonDate(date);
					}
					if (txn.getPaSaveDocDate() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(txn.getPaSaveDocDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						transaction.setPaSaveDocDate(date);
					}
					if (txn.getPaSearchObjDate() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(txn.getPaSearchObjDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						transaction.setPaSearchObjDate(date);
					}
					if (txn.getPaSearchBio() != null)
						transaction.setPaSearchBio(txn.getPaSearchBio() ? "Y"
								: "N");

					transaction.setDescription(txn.getDescription());
					if (StringUtils.isNotEmpty(txn.getInfoPerson())) {
						JAXBContext jaxbContext = JAXBContext
								.newInstance(InfoFamilys.class);
						Unmarshaller unmarshaller = jaxbContext
								.createUnmarshaller();
						StringReader reader = new StringReader(
								txn.getInfoPerson());
						InfoFamilys familys = (InfoFamilys) unmarshaller
								.unmarshal(reader);
						listFamily.addAll(familys.getSonFamilies());
					}
				}
				NicRegistrationData reg = txn.getNicRegistrationData();
				if (reg != null) {
					RegistrationData regis = new RegistrationData();
					regis.setResidentPlaceId(reg.getAddressLine4());
					regis.setResidentAreaId(reg.getAddressLine3());
					regis.setResidentAddress(reg.getAddressLine1());
					// regis.setTmpPlaceId(reg.getAddressTempCity());
					regis.setTmpAreaId(reg.getAddressTempDistrict());
					regis.setTmpAddress(reg.getAddressTempDetail());
					regis.setTmpPlaceId(reg.getAddressTempProvince());
					// regis.setTmpAddress(reg.getAddressTempStreet());
					regis.setTotalFp(reg.getTotalFp());
					regis.setContactNo(reg.getContactNo());
					regis.setFullName(HelperClass.createFullName(
							reg.getSurnameFull(), reg.getMiddlenameFull(),
							reg.getFirstnameFull()));
					regis.setNationality(reg.getNationality());
					// regis.setDateOfBirth(reg.getDateOfBirth());
					if (reg.getDateOfBirth() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(reg.getDateOfBirth(),
										STR_FORMAT_DATE_yyyyMMdd);
						regis.setDateOfBirth(HelperClass.loadDateOfBirths(date,
								reg.getDefDateOfBirth()));
					}
					regis.setPlaceOfBirth(reg.getPlaceOfBirth());
					// regis.setGender(reg.getGender().equals("M") ? "MALE" :
					// "FEMALE");
					regis.setGender(reg.getGender());
					regis.setReligion(reg.getReligion());
					regis.setNation(reg.getNation());
					regis.setAddressNin(reg.getAddressNin());
					// regis.setDateNin(reg.getDateNin());
					if (reg.getDateNin() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(reg.getDateNin(),
										STR_FORMAT_DATE_yyyyMMdd);
						regis.setDateNin(date);
					}
					regis.setAddressCompany(reg.getOfficeAddress());
					regis.setJob(reg.getJob());
					regis.setStyleDob(reg.getDefDateOfBirth());
					regis.setSurName(reg.getSurnameFull());
					regis.setMidName(reg.getMiddlenameFull());
					regis.setFirstName(reg.getFirstnameFull());
					regis.setNumDecision(reg.getNumDecision());
					regis.setNameDepartment(reg.getNameDepartment());
					regis.setPosition(reg.getPosition());
					regis.setOwnerType(reg.getOwnerType());
					regis.setSearchName(reg.getSearchName());
					regis.setCreateByName(reg.getCreateByName());
					regis.setCreateBy(reg.getCreateBy());
					BaseModelSingle<EppPerson> baseFindPerson = eppPersonService
							.findByStringCode(txn.getPersonCode());
					if (!baseFindPerson.isError()
							|| baseFindPerson.getMessage() != null) {
						rb = this.checkExistAndSaveException(baseFindPerson,
								eppWsLog, rb, new Object());
						response.setCode(rb.getCode());
						response.setMessage(rb.getMessage());
						return response;
					}
					EppPerson eppP = baseFindPerson.getModel();
					regis.setPersonCode(eppP != null ? eppP.getPersonCode()
							: null);
					transaction.setRegisData(regis);
					if (StringUtils.isNotEmpty(reg.getFatherFullname())) {
						PersonFamily father = new PersonFamily();
						father.setRelationship(Contants.RELATIONSHIP_TYPE_FATHER);
						father.setGender("M");
						father.setName(reg.getFatherFullname());
						if (reg.getFatherDateOfBirth() != null) {
							father.setDateOfBirth(DateUtil.parseDate(
									reg.getFatherDateOfBirth(),
									DateUtil.FORMAT_YYYYMMDD));
							father.setTypeDob(reg.getFatherDefDateOfBirth());
						}

						father.setLost(reg.getFatherLost());
						listFamily.add(father);
					}
					if (StringUtils.isNotEmpty(reg.getMotherFullname())) {
						PersonFamily mother = new PersonFamily();
						mother.setRelationship(Contants.RELATIONSHIP_TYPE_MOTHER);
						mother.setGender("F");
						mother.setName(reg.getMotherFullname());
						if (reg.getMotherDateOfBirth() != null) {
							mother.setDateOfBirth(DateUtil.parseDate(
									reg.getMotherDateOfBirth(),
									DateUtil.FORMAT_YYYYMMDD));
							mother.setTypeDob(reg.getMotherDefDateOfBirth());
						}

						mother.setLost(reg.getMotherLost());
						listFamily.add(mother);
					}
					if (StringUtils.isNotEmpty(reg.getSpouseFullname())) {
						PersonFamily spouse = new PersonFamily();
						spouse.setRelationship(Contants.RELATIONSHIP_TYPE_SPOUSE);
						spouse.setGender(reg.getGender().equals("M") ? "F"
								: "M");
						spouse.setName(reg.getSpouseFullname());
						if (reg.getSpouseDateOfBirth() != null) {
							spouse.setDateOfBirth(DateUtil.parseDate(
									reg.getSpouseDateOfBirth(),
									DateUtil.FORMAT_YYYYMMDD));
							spouse.setTypeDob(reg.getSpouseDefDateOfBirth());
						}
						spouse.setLost(reg.getSpouseLost());
						listFamily.add(spouse);
					}
				}
				BaseModelList<NicTransactionAttachment> baseGetTranAttach = nicTransactionAttachmentService
						.getNicTransactionAttachmentsOutTypes(
								queue.getObjectId(),
								new String[] {
										NicTransactionAttachment.DOC_TYPE_TPL,
										NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE,
										NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP });
				if (!baseGetTranAttach.isError()
						|| baseGetTranAttach.getMessage() != null) {
					rb = this.checkExistAndSaveExceptions(baseGetTranAttach,
							eppWsLog, rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				List<NicTransactionAttachment> listAttach = baseGetTranAttach
						.getListModel();
				List<TransactionDocument> listDoc = new ArrayList<TransactionDocument>();
				if (listAttach != null) {
					for (NicTransactionAttachment att : listAttach) {
						TransactionDocument document = new TransactionDocument();
						document.setDocType(att.getDocType());
						document.setSerialNo(att.getSerialNo());
						document.setDocData(Base64.encodeBase64String(att
								.getDocData()));
						document.setCreatedBy(att.getCreateBy());
						document.setCreatedByName(att.getCreateByName());
						if (att.getCreateDatetime() != null) {
							String date = service.perso.util.HelperClass
									.convertDateToString(
											att.getCreateDatetime(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							document.setCreatedDate(date);
						}
						if (att.getUpdateDatetime() != null) {
							String date = service.perso.util.HelperClass
									.convertDateToString(
											att.getUpdateDatetime(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							document.setUpdatedDate(date);
						}
						document.setUpdatedBy(att.getUpdateBy());
						document.setUpdatedByName(att.getUpdateByName());
						document.setFileName(att.getStorageRefId());
						listDoc.add(document);
					}
				}
				// Documents documents = new Documents(listDoc);
				transaction.setFamilies(listFamily);
				transaction.setDocuments(listDoc);
				response.setData(transaction);
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				// logger.info("success download transaction,time:" +
				// HelperClass.convertDateToString1(new Date()));

				// update lại trạng thái đang thực hiện đồng bộ trong hàng đợi
				BaseModelSingle<Boolean> baseUpdateSQJ = queueJobService
						.updateStatusQueueJob(queue.getId(),
								Contants.CODE_STATUS_QUEUE_DOING);
				if (!baseUpdateSQJ.isError()
						|| baseUpdateSQJ.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseUpdateSQJ,
							eppWsLog, rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				/* Lưu bảng thống kê truyền nhận */
				if (transaction.getTransactionId() != null
						&& queue.getDateUpdateStatusHanding() == null) {
					this.saveOrUpRptData(Contants.URL_DOWNLOAD_TRANSACTION, 1,
							site);
				}

			} else {
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - downloadTransaction fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadHandoverA/{site}")
	public Response<List<HandoverA>> downloadHandoverA(
			@PathParam("site") String site) {
		Response<List<HandoverA>> response = new Response<List<HandoverA>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "downloadHandoverA";
		// if(!this.checkConnectApi(nameApi, site)){
		// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return response;
		// }
		// logger.info("start download handoverA to ttxl, time:" +
		// HelperClass.convertDateToString1(new Date()));

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_DDSA);
		eppWsLog.setUrlRequest(Contants.URL_DOWNLOAD_HANDOVER_A);
		eppWsLog.setDataRequest(site);
		eppWsLog.setKeyId(site + "_"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()));

		ResponseBase rb = new ResponseBase();
		try {
			BaseModelSingle<SyncQueueJob> baseFindQ = queueJobService
					.findQueueByStatus(site,
							Contants.CODE_STATUS_QUEUE_PENDING,
							Contants.QUEUE_OBJ_TYPE_DA);
			if (!baseFindQ.isError() || baseFindQ.getMessage() != null) {
				rb = this.checkExistAndSaveException(baseFindQ, eppWsLog, rb,
						new Object());
				response.setCode(rb.getCode());
				response.setMessage(rb.getMessage());
				return response;
			}
			SyncQueueJob queue = baseFindQ.getModel();
			if (queue != null) {
				BaseModelSingle<NicListHandover> baseFindHan = listHandoverService
						.findByPackageId(new NicListHandoverId(queue
								.getObjectId(), NicListHandover.TYPE_LIST_A));
				if (!baseFindQ.isError() || baseFindQ.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseFindQ, eppWsLog,
							rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				NicListHandover handover = baseFindHan.getModel();
				HandoverA nicHandover = new HandoverA();
				nicHandover.setIdQueue(queue.getId());
				if (handover != null) {
					nicHandover.setPackageId(handover.getId().getPackageId());
					nicHandover.setSiteCode(handover.getSiteCode());
					if (handover.getApproveDate() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(handover.getApproveDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						nicHandover.setApproveDate(dateExp);
					}
					if (handover.getProposalDate() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(
										handover.getProposalDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						nicHandover.setOfferDate(dateExp);
					}
					nicHandover.setApproveUserName(handover.getApproveUser());
					nicHandover.setOfferUserName(handover.getProcessUsers());
					nicHandover.setType(NicListHandover.TYPE_LIST_A);
					nicHandover.setCount(handover.getCountTransaction());
					nicHandover.setCreatorName(handover.getCreatorName());
					nicHandover.setApproveName(handover.getApproveName());
					nicHandover.setApprovePosition(handover
							.getApprovePosition());
					nicHandover.setOfferUserName(handover.getProposalName());
					nicHandover.setCount(handover.getCountTransaction());
					nicHandover.setUpdateBy(handover.getUpdateBy());
					nicHandover.setUpdateByName(handover.getUpdateByName());
					if (handover.getUpdateDate() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(handover.getUpdateDate(),
										STR_FORMAT_DATE_yyyyMMdd);
						nicHandover.setUpdateDate(dateExp);
					}
					if (handover.getDateOfDelivery() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(
										handover.getDateOfDelivery(),
										STR_FORMAT_DATE_yyyyMMdd);
						nicHandover.setDateOfDelivery(dateExp);
					}
					nicHandover.setPlaceOfDelivery(handover
							.getPlaceOfDelivery());
					nicHandover.setProposalName(handover.getProposalName());
				}
				BaseModelList<EppListHandoverDetail> baseGetHan = eppListHandoverDetailService
						.getListPackageByPackageId(queue.getObjectId(),
								NicListHandover.TYPE_LIST_A);
				if (!baseGetHan.isError() || baseGetHan.getMessage() != null) {
					rb = this.checkExistAndSaveExceptions(baseGetHan, eppWsLog,
							rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				List<EppListHandoverDetail> listA = baseGetHan.getListModel();

				List<ReceiptManager> listRM = new ArrayList<ReceiptManager>();
				Set<String> receipts = new HashSet<String>();
				List<String> tranIds = new ArrayList<String>();
				if (listA != null) {
					for (EppListHandoverDetail detailA : listA) {
						BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
								.findTransactionById(detailA.getTransactionId());
						if (!baseFindTran.isError()
								|| baseFindTran.getMessage() != null) {
							rb = this.checkExistAndSaveException(baseFindTran,
									eppWsLog, rb, new Object());
							response.setCode(rb.getCode());
							response.setMessage(rb.getMessage());
							return response;
						}
						NicTransaction txn = baseFindTran.getModel();
						if (txn != null) {
							receipts.add(txn.getRecieptNo());
						}
						tranIds.add(detailA.getTransactionId());
					}
					for (String receiptNo : receipts) {
						BaseModelList<EppRecieptManager> baseFindAllRM = rmService
								.findAllRecieptManager(receiptNo);
						if (!baseFindAllRM.isError()
								|| baseFindAllRM.getMessage() != null) {
							rb = this.checkExistAndSaveExceptions(
									baseFindAllRM, eppWsLog, rb, new Object());
							response.setCode(rb.getCode());
							response.setMessage(rb.getMessage());
							return response;
						}
						List<EppRecieptManager> list = baseFindAllRM
								.getListModel();
						if (list == null || list.size() == 0)
							continue;
						EppRecieptManager eppR = list.get(0);
						ReceiptManager rm = new ReceiptManager();
						rm.setReceiptNo(eppR.getRecieptNo());
						rm.setOfficeName(eppR.getOfficeName());
						rm.setPaymentAmount(eppR.getPaymentAmount());
						rm.setName(eppR.getFullname());
						rm.setPhone(eppR.getPhone());
						rm.setPaymentFlag(eppR.getPaymentFlag());
						if (eppR.getDob() != null) {
							String dateExp = service.perso.util.HelperClass
									.convertDateToString(eppR.getDob(),
											STR_FORMAT_DATE_yyyyMMdd);
							rm.setDob(HelperClass.loadDateOfBirths(dateExp,
									eppR.getDateDob()));
						}
						rm.setAddress(eppR.getAddress());
						rm.setNin(eppR.getNinNumber());
						List<ReceiptBill> listRB = new ArrayList<ReceiptBill>();
						BaseModelList<DetailRecieptFee> baseFindAllDR = drFeeService
								.findAllDetailRecieptFee(receiptNo);
						if (!baseFindAllDR.isError()
								|| baseFindAllDR.getMessage() != null) {
							rb = this.checkExistAndSaveExceptions(
									baseFindAllDR, eppWsLog, rb, new Object());
							response.setCode(rb.getCode());
							response.setMessage(rb.getMessage());
							return response;
						}
						List<DetailRecieptFee> listRF = baseFindAllDR
								.getListModel();
						if (listRF != null && listRF.size() > 0) {
							for (DetailRecieptFee fee : listRF) {
								ReceiptBill bill = new ReceiptBill();
								bill.setReceiptNo(receiptNo);
								bill.setCode(fee.getCodeBill());
								bill.setNumber(fee.getNumberBill());
								bill.setBillFlag(fee.getPriceFlag());
								bill.setDescription(fee.getDescription());
								bill.setPrice(fee.getPrice());
								bill.setCashierName(fee.getCashierName());
								bill.setCreateByName(fee.getCreateByName());
								if (fee.getDateOfReceipt() != null) {
									String dateDelete = service.perso.util.HelperClass
											.convertDateToString(
													fee.getDateOfReceipt(),
													STR_FORMAT_DATE_yyyyMMdd);
									bill.setDateOfReceipt(dateDelete);
								}
								bill.setCustomerName(fee.getCustomerName());
								bill.setCreateBy(fee.getCreateBy());
								if (fee.getCreateDate() != null) {
									String dateDelete = service.perso.util.HelperClass
											.convertDateToString(
													fee.getCreateDate(),
													STR_FORMAT_DATE_yyyyMMddHHmmss);
									bill.setCreateDate(dateDelete);
								}
								listRB.add(bill);
							}
							rm.setBills(listRB);
						}

						List<FeeRecieptPaymentModel> listFrpModel = new ArrayList<FeeRecieptPaymentModel>();
						BaseModelList<FeeRecieptPayment> baseFindAllRP = feeRecieptPaymentService
								.findAllFeeRecieptPayment(receiptNo);
						if (!baseFindAllRP.isError()
								|| baseFindAllRP.getMessage() != null) {
							rb = this.checkExistAndSaveExceptions(
									baseFindAllRP, eppWsLog, rb, new Object());
							response.setCode(rb.getCode());
							response.setMessage(rb.getMessage());
							return response;
						}
						List<FeeRecieptPayment> listFrp = baseFindAllRP
								.getListModel();
						if (listFrp != null && listFrp.size() > 0) {
							for (FeeRecieptPayment fee : listFrp) {
								FeeRecieptPaymentModel bill = new FeeRecieptPaymentModel();
								bill.setRecieptNo(receiptNo);
								bill.setTypePayment(fee.getTypePayment());
								bill.setAmount(fee.getAmount());
								bill.setCreateBy(fee.getCreateBy());
								bill.setDescription(fee.getDescription());
								bill.setPrice(fee.getPrice());
								bill.setTotal(fee.getTotal());
								bill.setUnit(fee.getUnit());
								listFrpModel.add(bill);
							}
							rm.setFeeRecieptPayment(listFrpModel);
						}

						rm.setRegSiteCode(handover.getSiteCode());
						rm.setDateOfIssue(eppR.getDateOfIssue());
						rm.setDateInWeek(eppR.getDateInWeek());
						rm.setNote(eppR.getNote());
						rm.setPlaceOfReciept(eppR.getPlaceOfReceipt());
						if (eppR.getDeliveryAtOffice() != null)
							rm.setDeliveryAtOffice(eppR.getDeliveryAtOffice() ? "Y"
									: "N");
						rm.setDeliveryOffice(eppR.getDeliveryOffice());
						rm.setAmountOfPassport(eppR.getAmountOfPassport());
						rm.setAmountOfRegistration(eppR
								.getAmountOfRegistration());
						rm.setAmountOfPerson(eppR.getAmountOfPerson());
						rm.setAmountOfImage(eppR.getAmountOfImage());
						rm.setDocumentType(eppR.getDocumentType());
						rm.setPrevPassportNo(eppR.getPrevPassportNo());
						rm.setAddedDocuments(eppR.getAddedDocuments());
						rm.setDocumentaryNo(eppR.getDocumentaryNo());
						rm.setDocumentaryIssued(eppR.getDocumentaryIssued());
						rm.setStatus(eppR.getStatus());
						rm.setIsDelivered(eppR.getIsDelivered());
						rm.setIsPostOffice(eppR.getIsPostOffice() ? "Y" : "N");
						rm.setNoteOfDelivery(eppR.getNoteOfDelivery());
						rm.setSignName(eppR.getSignName());
						rm.setDocOfDelivery(eppR.getDocOfDelivery());
						rm.setDocumentaryOffice(eppR.getDocmentaryOffice());
						rm.setDocumentaryAddress(eppR.getDocmentaryAddress());
						rm.setListCode(eppR.getListCode());
						rm.setInputCompleted(eppR.getInputCompleted() ? "Y"
								: "N");
						if (eppR.getDeletedDate() != null) {
							String dateDelete = service.perso.util.HelperClass
									.convertDateToString(eppR.getDeletedDate(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							rm.setDeletedDate(dateDelete);
						}
						rm.setDeletedBy(eppR.getDeletedBy());
						rm.setDeletedName(eppR.getDeletedName());
						rm.setDeletedReason(eppR.getDeletedReason());
						rm.setCreateByName(eppR.getCreateByName());
						rm.setReceivedBy(eppR.getReceivedBy());
						rm.setCreateBy(eppR.getCreateBy());
						if (eppR.getCreateDate() != null) {
							String dateDelete = service.perso.util.HelperClass
									.convertDateToString(eppR.getCreateDate(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							rm.setCreateDate(dateDelete);
						}
						List<DetailHandover> listDH = new ArrayList<DetailHandover>();

						for (String transactionId : tranIds) {
							BaseModelSingle<NicUploadJob> baseFindUJ = uploadJobService
									.findNicUpByReceiptNo(handover.getId()
											.getPackageId(), receiptNo,
											transactionId);
							if (!baseFindUJ.isError()
									|| baseFindUJ.getMessage() != null) {
								rb = this.checkExistAndSaveException(
										baseFindUJ, eppWsLog, rb, new Object());
								response.setCode(rb.getCode());
								response.setMessage(rb.getMessage());
								return response;
							}
							NicUploadJob nicUp = baseFindUJ.getModel();

							if (nicUp != null) {
								BaseModelSingle<EppListHandoverDetail> baseGetHD = eppListHandoverDetailService
										.getListPackageByPackageIdAndTranID(
												handover.getId().getPackageId(),
												transactionId,
												NicListHandover.TYPE_LIST_A);
								if (!baseGetHD.isError()
										|| baseGetHD.getMessage() != null) {
									rb = this.checkExistAndSaveException(
											baseGetHD, eppWsLog, rb,
											new Object());
									response.setCode(rb.getCode());
									response.setMessage(rb.getMessage());
									return response;
								}
								EppListHandoverDetail detailA = baseGetHD
										.getModel();
								if (detailA != null) {
									DetailHandover tp = new DetailHandover();
									tp.setPackageId(detailA.getPackageId());
									tp.setTransactionId(detailA
											.getTransactionId());
									tp.setOfferStage(detailA.getProposalStage());
									tp.setApproveStage(detailA
											.getApproveStage());
									tp.setNoteOffer(detailA.getProposalNote());
									tp.setNoteApprove(detailA.getApproveNote());
									tp.setPersonCode(nicUp.getNicTransaction()
											.getPersonCode());

									tp.setPaBlacklistId(nicUp
											.getNicTransaction()
											.getPaBlacklistId());
									tp.setPaInqBllUser(nicUp
											.getNicTransaction()
											.getPaInqBllUser());
									tp.setPaArchiveCode(nicUp
											.getNicTransaction()
											.getPaArchiveCode());
									if (nicUp.getNicTransaction()
											.getPaJoinPersonDate() != null) {
										String date = service.perso.util.HelperClass
												.convertDateToString(nicUp
														.getNicTransaction()
														.getPaJoinPersonDate(),
														STR_FORMAT_DATE_yyyyMMddHHmmss);
										tp.setPaJoinPersonDate(date);
									}
									if (nicUp.getNicTransaction()
											.getPaSaveDocDate() != null) {
										String date = service.perso.util.HelperClass
												.convertDateToString(nicUp
														.getNicTransaction()
														.getPaSaveDocDate(),
														STR_FORMAT_DATE_yyyyMMddHHmmss);
										tp.setPaSaveDocDate(date);
									}
									if (nicUp.getNicTransaction()
											.getPaSearchObjDate() != null) {
										String date = service.perso.util.HelperClass
												.convertDateToString(nicUp
														.getNicTransaction()
														.getPaSearchObjDate(),
														STR_FORMAT_DATE_yyyyMMddHHmmss);
										tp.setPaSearchObjDate(date);
									}
									if (nicUp.getNicTransaction()
											.getPaSearchBio() != null)
										tp.setPaSearchBio(nicUp
												.getNicTransaction()
												.getPaSearchBio() ? "Y" : "N");
									// Tìm mã orgPersonCode
									BaseModelSingle<EppPerson> baseFindPersonByCode = eppPersonService
											.findByStringCode(nicUp
													.getNicTransaction()
													.getPersonCode());
									if (!baseFindPersonByCode.isError()
											|| baseFindPersonByCode
													.getMessage() != null) {
										rb = this.checkExistAndSaveException(
												baseFindPersonByCode, eppWsLog,
												rb, new Object());
										response.setCode(rb.getCode());
										response.setMessage(rb.getMessage());
										return response;
									}
									EppPerson eppPerson = baseFindPersonByCode
											.getModel();
									if (eppPerson != null) {
										tp.setPersonOrgCode(eppPerson
												.getOrgPerson() != null ? eppPerson
												.getOrgPerson() : eppPerson
												.getPersonCode());
										PersonModel person = new PersonModel();
										person.setDateOfBirth(eppPerson
												.getDateOfBirth());
										person.setDateOfIdIssue(eppPerson
												.getDateOfIdIssue());
										person.setEthNic(eppPerson.getEthnic());
										person.setEthnicCode(eppPerson
												.getEthnicCode());
										person.setFatherName(eppPerson
												.getFatherName());
										person.setFatherSearchName(eppPerson
												.getFatherSearchName());
										person.setGender(eppPerson.getGender());
										person.setIdNumber(eppPerson
												.getIdNumber());
										person.setMotherName(eppPerson
												.getMotherName());
										person.setMotherSearchName(eppPerson
												.getMotherSearchName());
										person.setName(eppPerson.getName());
										person.setNationalityCode(eppPerson
												.getNationalityCode());
										person.setNationalityName(eppPerson
												.getNationalityName());
										person.setPersonCode(eppPerson
												.getPersonCode());
										person.setPersonOrgCode(eppPerson
												.getOrgPerson() != null ? eppPerson
												.getOrgPerson() : eppPerson
												.getPersonCode());
										person.setPlaceOfBirthCode(eppPerson
												.getPlaceOfBirthCode());
										person.setPlaceOfBirthName(eppPerson
												.getPlaceOfBirthName());
										person.setPlaceOfIdIssueName(eppPerson
												.getPlaceOfIdIssueName());
										person.setRefId(eppPerson.getRefId());
										person.setReligion(eppPerson
												.getReligion());
										person.setReligionCode(eppPerson
												.getReligionCode());
										person.setSearchName(eppPerson
												.getSearchName());
										person.setCreatedBy(eppPerson
												.getCreatedBy());
										person.setCreatedByName(eppPerson
												.getCreatedByName());
										if (eppPerson.getCreateTs() != null) {
											String date = service.perso.util.HelperClass
													.convertDateToString(
															eppPerson
																	.getCreateTs(),
															STR_FORMAT_DATE_yyyyMMddHHmmss);
											person.setCreatedDate(date);
										}

										if (eppPerson.getUpdateTs() != null) {
											String date = service.perso.util.HelperClass
													.convertDateToString(
															eppPerson
																	.getUpdateTs(),
															STR_FORMAT_DATE_yyyyMMddHHmmss);
											person.setUpdatedDate(date);
										}
										person.setUpdatedBy(eppPerson
												.getUpdatedBy());
										person.setUpdatedByName(eppPerson
												.getUpdatedByName());
										person.setIsChecked((eppPerson
												.getIsChecked() != null && eppPerson
												.getIsChecked()) ? "Y" : "N");
										person.setDescription(eppPerson
												.getDescription());
										person.setSrcOffice(eppPerson
												.getSrcOffice());
										person.setStatus(eppPerson.getStatus());
										person.setOtherName(eppPerson
												.getOtherName());
										person.setCountryOfBirth(eppPerson
												.getCountryOfBirth());

										List<PersonFamily> families = new ArrayList<PersonFamily>();
										// Tìm danh sách gia đình
										BaseModelList<EppPersonFamily> baseFindPerson = eppPersonService
												.findAllEppPersonFamily1(eppPerson
														.getPersonId());
										if (!baseFindPerson.isError()
												|| baseFindPerson.getMessage() != null) {
											rb = this
													.checkExistAndSaveExceptions(
															baseFindPerson,
															eppWsLog, rb,
															new Object());
											response.setCode(rb.getCode());
											response.setMessage(rb.getMessage());
											return response;
										}
										List<EppPersonFamily> listf = baseFindPerson
												.getListModel();
										if (listf != null && listf.size() > 0) {
											for (EppPersonFamily f : listf) {
												PersonFamily family = new PersonFamily();
												family.setDateOfBirth(f
														.getDateOfBirth());
												family.setGender(f.getGender());
												family.setLost(f.getLost());
												family.setName(f.getName());
												family.setPlaceOfBirth(f
														.getPlaceOfBirthCode());
												family.setRelationship(f
														.getRelationship());
												families.add(family);
											}
										}
										person.setFamilies(families);
										tp.setOrgPerson(person);
									} else
										tp.setPersonOrgCode(nicUp
												.getNicTransaction().getNote()); // Luu
																					// tam
																					// neu
																					// ko
																					// co
																					// du
																					// lieu
																					// person

									BaseModelSingle<NicTransactionPayment> baseFindTranPay = nicTransactionPaymentDao
											.findGetPaymentByTransaction(detailA
													.getTransactionId());
									if (!baseFindTranPay.isError()
											|| baseFindTranPay.getMessage() != null) {
										rb = this.checkExistAndSaveException(
												baseFindTranPay, eppWsLog, rb,
												new Object());
										response.setCode(rb.getCode());
										response.setMessage(rb.getMessage());
										return response;
									}
									NicTransactionPayment payments = baseFindTranPay
											.getModel();
									if (payments == null)
										continue;
									BaseModelList<NicTransactionPaymentDetail> baseFindDPay = paymentDetailService
											.findListDetailPaymentList(
													payments.getPaymentId(),
													null);
									if (!baseFindDPay.isError()
											|| baseFindDPay.getMessage() != null) {
										rb = this.checkExistAndSaveExceptions(
												baseFindDPay, eppWsLog, rb,
												new Object());
										response.setCode(rb.getCode());
										response.setMessage(rb.getMessage());
										return response;
									}
									List<NicTransactionPaymentDetail> lsDetail = baseFindDPay
											.getListModel();
									if (lsDetail != null) {
										List<PaymentDetail> listPD = new ArrayList<PaymentDetail>();
										for (NicTransactionPaymentDetail pay : lsDetail) {
											PaymentDetail detail = new PaymentDetail();
											detail.setTransactionId(detailA
													.getTransactionId());
											detail.setNamePayment(pay.getNote());
											detail.setTypePayment(pay
													.getTypePayment());
											detail.setSubTypePayment(pay
													.getSubTypePayment());
											detail.setPaymentAmount(pay
													.getPaymentAmount());
											detail.setStatusFee(pay
													.isStatusFee() ? "Y" : "N");
											listPD.add(detail);
										}
										tp.setPayments(listPD);
									}
									listDH.add(tp);
								}

							}
						}
						rm.setHandovers(listDH);
						listRM.add(rm);
					}
				}
				nicHandover.setReceipts(listRM);
				List<HandoverA> lsHan = new ArrayList<HandoverA>();
				lsHan.add(nicHandover);
				response.setData(lsHan);
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

				// logger.info("success upload handoverA in TTDH, date now:"
				// + HelperClass.convertDateToString1(Calendar
				// .getInstance().getTime()));
				BaseModelSingle<Boolean> baseUpdateSQJ = queueJobService
						.updateStatusQueueJob(queue.getId(),
								Contants.CODE_STATUS_QUEUE_DOING);
				if (!baseUpdateSQJ.isError()
						|| baseUpdateSQJ.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseUpdateSQJ,
							eppWsLog, rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				/* Lưu bảng thống kê truyền nhận */
				if (nicHandover.getPackageId() != null
						&& queue.getDateUpdateStatusHanding() == null) {
					this.saveOrUpRptData(Contants.URL_DOWNLOAD_HANDOVER_A, 1,
							site);
				}
			} else {
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - downloadHandoverA fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}

			return response;
		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadHandoverB/{site}")
	public Response<HandoverB> downloadHandoverB(@PathParam("site") String site) {
		Response<HandoverB> response = new Response<HandoverB>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// logger.info("start download handoverB to pa, time:" +
		// HelperClass.convertDateToString1(new Date()));

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_DDSB);
		eppWsLog.setUrlRequest(Contants.URL_DOWNLOAD_HANDOVER_B);
		eppWsLog.setDataRequest(site);
		eppWsLog.setKeyId(site
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()));

		ResponseBase rb = new ResponseBase();

		try {
			BaseModelSingle<SyncQueueJob> baseFindQ = queueJobService
					.findQueueByStatus(site,
							Contants.CODE_STATUS_QUEUE_PENDING,
							Contants.QUEUE_OBJ_TYPE_DB);
			if (!baseFindQ.isError() || baseFindQ.getMessage() != null) {
				rb = this.checkExistAndSaveException(baseFindQ, eppWsLog, rb,
						new Object());
				response.setCode(rb.getCode());
				response.setMessage(rb.getMessage());
				return response;
			}
			SyncQueueJob queue = baseFindQ.getModel();
			if (queue != null) {
				BaseModelSingle<NicListHandover> baseFindHan = listHandoverService
						.findByPackageId(new NicListHandoverId(queue
								.getObjectId(), NicListHandover.TYPE_LIST_B));
				if (!baseFindHan.isError() || baseFindHan.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseFindHan, eppWsLog,
							rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				NicListHandover handover = baseFindHan.getModel();
				HandoverB nicHandover = new HandoverB();
				nicHandover.setIdQueue(queue.getId());
				if (handover != null) {
					nicHandover.setPackageId(handover.getId().getPackageId());
					nicHandover.setSiteCode(handover.getSiteCode());
					if (handover.getApproveDate() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(handover.getApproveDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						nicHandover.setApproveDate(dateExp);
					}
					if (handover.getProposalDate() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(
										handover.getProposalDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						nicHandover.setOfferDate(dateExp);
					}
					nicHandover.setApproveName(handover.getApproveName());
					nicHandover.setOfferUserName(handover.getProcessUsers());
					nicHandover.setType(8);
					nicHandover.setCount(handover.getCountTransaction());
				}
				BaseModelList<EppListHandoverDetail> baseGetHan = eppListHandoverDetailService
						.getListPackageByPackageId(queue.getObjectId(),
								NicListHandover.TYPE_LIST_B);
				if (!baseGetHan.isError() || baseGetHan.getMessage() != null) {
					rb = this.checkExistAndSaveExceptions(baseGetHan, eppWsLog,
							rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				List<EppListHandoverDetail> listB = baseGetHan.getListModel();
				if (listB != null) {
					List<DetailHandoverB> listDH = new ArrayList<DetailHandoverB>();
					for (EppListHandoverDetail detailB : listB) {
						DetailHandoverB tp = new DetailHandoverB();
						tp.setPackageId(detailB.getPackageId());
						tp.setTransactionId(detailB.getTransactionId());
						tp.setOfferStage(detailB.getProposalStage());
						tp.setApproveStage(detailB.getApproveStage());
						tp.setNoteOffer(detailB.getProposalNote());
						tp.setNoteApprove(detailB.getApproveNote());
						BaseModelSingle<NicTransactionPayment> baseFindPayment = nicTransactionPaymentDao
								.findGetPaymentByTransaction(detailB
										.getTransactionId());
						if (!baseFindPayment.isError()
								|| baseFindPayment.getMessage() != null) {
							rb = this
									.checkExistAndSaveException(
											baseFindPayment, eppWsLog, rb,
											new Object());
							response.setCode(rb.getCode());
							response.setMessage(rb.getMessage());
							return response;
						}
						NicTransactionPayment payments = baseFindPayment
								.getModel();
						if (payments == null)
							continue;
						BaseModelList<NicTransactionPaymentDetail> baseFindDPay = paymentDetailService
								.findListDetailPaymentList(
										payments.getPaymentId(), null);
						if (!baseFindDPay.isError()
								|| baseFindDPay.getMessage() != null) {
							rb = this.checkExistAndSaveExceptions(baseFindDPay,
									eppWsLog, rb, new Object());
							response.setCode(rb.getCode());
							response.setMessage(rb.getMessage());
							return response;
						}
						List<NicTransactionPaymentDetail> lsDetail = baseFindDPay
								.getListModel();
						if (lsDetail != null) {
							List<PaymentDetail> listPD = new ArrayList<PaymentDetail>();
							for (NicTransactionPaymentDetail pay : lsDetail) {
								PaymentDetail detail = new PaymentDetail();
								detail.setTransactionId(detailB
										.getTransactionId());
								detail.setNamePayment(pay.getNote());
								detail.setTypePayment(pay.getTypePayment());
								detail.setSubTypePayment(pay
										.getSubTypePayment());
								detail.setPaymentAmount(pay.getPaymentAmount());
								detail.setStatusFee(pay.isStatusFee() ? "Y"
										: "N");
								listPD.add(detail);
							}
							tp.setPayments(listPD);
						}
						listDH.add(tp);
					}
					nicHandover.setHandovers(listDH);
				}
				response.setData(nicHandover);
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

				// logger.info("success download handoverB in TTDH, date now:"
				// + HelperClass.convertDateToString1(Calendar
				// .getInstance().getTime()));
				BaseModelSingle<Boolean> baseUpdateSQJ = queueJobService
						.updateStatusQueueJob(queue.getId(),
								Contants.CODE_STATUS_QUEUE_DOING);
				if (!baseUpdateSQJ.isError()
						|| baseUpdateSQJ.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseUpdateSQJ,
							eppWsLog, rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				/* Lưu bảng thống kê truyền nhận */
				if (nicHandover.getPackageId() != null
						&& queue.getDateUpdateStatusHanding() == null) {
					this.saveOrUpRptData(Contants.URL_DOWNLOAD_HANDOVER_B, 1,
							site);
				}
			} else {
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - downloadHandoverB fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}

			return response;
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadHandoverB")
	public ResponseBase uploadHandoverB(HandoverB handover) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_DSB);
		eppWsLog.setUrlRequest(Contants.URL_UPLOAD_HANDOVER_B);

		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(handover));
		} catch (IOException e1) {
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}
		List<EppListHandoverDetail> listHanDetail = null;
		try {
			if (handover != null) {
				// String nameApi = "uploadHandoverB";
				// if(!this.checkConnectApi(nameApi, handover.getSiteCode())){
				// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
				// return response;
				// }
				/*
				 * ResponseBase error = this.checkValidateHandoverB(handover);
				 * if (error != null) { return error; }
				 */
				if (handover.getPassportCancelInfo() != null
						&& handover.getPassportCancelInfo().size() > 0) {
					for (PassportCancelInput ppInput : handover
							.getPassportCancelInfo()) {
						response = this.methodCancelPassport(ppInput, response);
						if (!response.getCode().equals(Contants.CODE_SUCCESS)
								&& !response.getCode().equals(
										Contants.CODE_DATA_EXIST)) {
							response.setMessage(response.getMessage()
									+ " - Hủy hộ chiếu thất bại.");
							return response;
						}
					}
				}
				eppWsLog.setKeyId(handover.getPackageId());
				BaseModelSingle<NicListHandover> baseFindH = listHandoverService
						.findByPackageId(new NicListHandoverId(handover
								.getPackageId(), NicListHandover.TYPE_LIST_B));
				if (!baseFindH.isError() || baseFindH.getMessage() != null) {
					return this.checkExistAndSaveException(baseFindH, eppWsLog,
							response, handover);
				}
				NicListHandover hanCheck = baseFindH.getModel();
				String sitePA = "";
				BaseModelSingle<NicListHandover> baseFindHandOld = null;
				if (StringUtils.isNotBlank(handover.getPackageOldId())) {
					baseFindHandOld = listHandoverService
							.findByPackageId(new NicListHandoverId(handover
									.getPackageOldId(),
									NicListHandover.TYPE_LIST_B));
					if (!baseFindHandOld.isError()
							|| baseFindHandOld.getMessage() != null) {
						throw new Exception(baseFindHandOld.getMessage());
					}
					NicListHandover oldListHandover = baseFindHandOld
							.getModel();
					if (oldListHandover != null) {
						oldListHandover.setHandoverStatus(2);
						BaseModelSingle<Boolean> saveOldHan = listHandoverService
								.saveOrUpdateHandover(oldListHandover);
						if (!saveOldHan.isError()
								|| saveOldHan.getMessage() != null) {
							throw new Exception(saveOldHan.getMessage());
						}
						BaseModelList<EppListHandoverDetail> getListHanDetail = eppListHandoverDetailService
								.getListPackageByPackageId(
										handover.getPackageOldId(),
										NicListHandover.TYPE_LIST_B);
						if (!getListHanDetail.isError()
								|| getListHanDetail.getMessage() != null) {
							throw new Exception(getListHanDetail.getMessage());
						}
						listHanDetail = getListHanDetail.getListModel();
						if (listHanDetail != null && listHanDetail.size() > 0) {
							for (EppListHandoverDetail hDetail : listHanDetail) {
								// Kiểm tra đủ hồ sơ hết chưa?
								BaseModelSingle<NicTransaction> baseFindT = nicTransactionService
										.findTransactionById(hDetail
												.getTransactionId());
								if (!baseFindT.isError()
										|| baseFindT.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseFindT, eppWsLog, response,
											handover);
								}
								NicTransaction txn = baseFindT.getModel();
								if (txn == null) {
									response.setCode(Contants.RESPONSE_CODE_FAIL_NOT_ENOUGH_HS);
									response.setMessage("Chưa upload đủ hồ sơ. Hồ sơ đang thiếu hiện tại: "
											+ hDetail.getTransactionId());
									return response;
								}
								/* update trạng thái hồ sơ */
								txn.setTransactionStatus(NicTransaction.TRANSACTION_STATUS_INVESTIGATION_SAVED);
								BaseModelSingle<Boolean> baseUpdateTranstatus = nicTransactionService
										.saveOrUpdateTransaction(txn);
								if (!baseUpdateTranstatus.isError()
										|| baseUpdateTranstatus.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseUpdateTranstatus, eppWsLog,
											response, handover);
								}
								// xóa thông tin
								eppListHandoverDetailService
										.deleteHandoverDetail(
												hDetail.getPackageId(),
												hDetail.getTransactionId(),
												hDetail.getTypeList());
							}
						}
					}
				}
				if (hanCheck == null
						&& StringUtils.isNotBlank(handover.getPackageId())) {
					NicListHandover nicHandover = new NicListHandover();
					nicHandover.setId(new NicListHandoverId(handover
							.getPackageId(), NicListHandover.TYPE_LIST_B));
					// nicHandover.setPackageId(handover.getPackageId());
					nicHandover.setSiteCode(handover.getSiteCode());
					if (StringUtils.isNotEmpty(handover.getApproveDate())) {
						Date date = HelperClass.convertStringToDate(handover
								.getApproveDate(), handover.getApproveDate()
								.length());
						nicHandover.setApproveDate(date);
					}
					if (StringUtils.isNotEmpty(handover.getOfferDate())) {
						Date date = HelperClass.convertStringToDate(handover
								.getOfferDate(), handover.getOfferDate()
								.length());
						nicHandover.setProposalDate(date);
					}
					nicHandover.setApproveUser(handover.getApproveUser());
					nicHandover.setProcessUsers(handover.getOfferUserName());
					nicHandover.setPackageName(Contants.CODE_PACKAGE_NAME_B);
					// nicHandover.setTypeList(NicListHandover.TYPE_LIST_B);
					nicHandover.setCreateDate(Calendar.getInstance().getTime());
					nicHandover.setCreateBy(handover.getOfferUserName());
					nicHandover.setHandoverStatus(1);
					// nicHandover.setStatusSyncXl(null);
					nicHandover.setProposalName(handover.getProposalName());
					nicHandover.setProposalUser(handover.getOfferUserName());
					nicHandover.setCreatorName(handover.getCreatorName());
					nicHandover.setCountTransaction(handover.getCount());
					BaseModelSingle<Void> baseSaveH = listHandoverService
							.saveHandover(nicHandover);
					if (!baseSaveH.isError() || baseSaveH.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveH,
								eppWsLog, response, handover);
					}
				} else if (hanCheck == null
						&& StringUtils.isBlank(handover.getPackageId())
						&& StringUtils.isNotBlank(handover.getPackageOldId())) {
					eppWsLog.setKeyId(handover.getPackageOldId());
					baseFindHandOld = listHandoverService
							.findByPackageId(new NicListHandoverId(handover
									.getPackageOldId(),
									NicListHandover.TYPE_LIST_B));
					if (!baseFindHandOld.isError()
							|| baseFindHandOld.getMessage() != null) {
						return this.checkExistAndSaveException(baseFindHandOld,
								eppWsLog, response, handover);
					}
					hanCheck = baseFindHandOld.getModel();
				}
				if (hanCheck != null) {
					if (StringUtils.isNotEmpty(handover.getApproveDate())) {
						Date date = HelperClass.convertStringToDate(handover
								.getApproveDate(), handover.getApproveDate()
								.length());
						hanCheck.setApproveDate(date);
					}
					hanCheck.setApproveName(handover.getApproveName());
					hanCheck.setApproveUser(handover.getApproveUser());
					hanCheck.setApprovePosition(handover.getApprovePosition());
					BaseModelSingle<Boolean> baseSaveOrUpH = listHandoverService
							.saveOrUpdateHandover(hanCheck);
					if (!baseSaveOrUpH.isError()
							|| baseSaveOrUpH.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveOrUpH,
								eppWsLog, response, handover);
					}
				}

				if (handover.getHandovers() != null) {
					for (DetailHandoverB detailB : handover.getHandovers()) {
						boolean check = true;
						// Kiểm tra đủ hồ sơ hết chưa?
						BaseModelSingle<NicTransaction> baseFindT = nicTransactionService
								.findTransactionById(detailB.getTransactionId());
						if (!baseFindT.isError()
								|| baseFindT.getMessage() != null) {
							return this.checkExistAndSaveException(baseFindT,
									eppWsLog, response, handover);
						}
						NicTransaction txn = baseFindT.getModel();
						if (txn == null) {
							response.setCode(Contants.RESPONSE_CODE_FAIL_NOT_ENOUGH_HS);
							response.setMessage("Chưa upload đủ hồ sơ. Hồ sơ đang thiếu hiện tại: "
									+ detailB.getTransactionId());
							return response;
						}
						BaseModelSingle<EppListHandoverDetail> baseGetHD = eppListHandoverDetailService
								.getListPackageByPackageIdAndTranID(
										detailB.getPackageId(),
										detailB.getTransactionId(),
										NicListHandover.TYPE_LIST_B);
						if (!baseGetHD.isError()
								|| baseGetHD.getMessage() != null) {
							return this.checkExistAndSaveException(baseGetHD,
									eppWsLog, response, handover);
						}
						EppListHandoverDetail tpCheck = baseGetHD.getModel();

						BaseModelSingle<NicUploadJob> baseFindUJ = uploadJobService
								.findUploadJobByTransId(detailB
										.getTransactionId());
						if (!baseFindUJ.isError()
								|| baseFindUJ.getMessage() != null) {
							return this.checkExistAndSaveException(baseGetHD,
									eppWsLog, response, handover);
						}
						NicUploadJob nicU = baseFindUJ.getModel();
						if (tpCheck == null
								&& StringUtils.isNotBlank(detailB
										.getPackageId())) {
							EppListHandoverDetail tp = new EppListHandoverDetail();
							tp.setPackageId(detailB.getPackageId());
							tp.setTransactionId(detailB.getTransactionId());
							tp.setProposalStage(detailB.getOfferStage());
							tp.setApproveStage(detailB.getApproveStage());
							tp.setProposalNote(detailB.getNoteOffer());
							tp.setApproveNote(detailB.getNoteApprove());
							tp.setTypeList(NicListHandover.TYPE_LIST_B);
							tp.setExchangeContent(detailB.getExchangeContent());
							BaseModelSingle<Boolean> bSaveHD = eppListHandoverDetailService
									.saveHandoverDetail(tp);
							if (!bSaveHD.isError()
									|| bSaveHD.getMessage() != null) {
								return this.checkExistAndSaveException(bSaveHD,
										eppWsLog, response, handover);
							}
							// BaseModelSingle<EppPerson> baseFindPerson =
							// eppPersonService
							// .findByTransactionId(detailB
							// .getTransactionId());
							// if (!baseFindPerson.isError()
							// || baseFindPerson.getMessage() != null) {
							// return this.checkExistAndSaveException(
							// baseFindPerson, eppWsLog, response,
							// handover);
							// }
							// EppPerson epp = baseFindPerson.getModel();
							// if (detailB.getPersonStage() != null) {
							// if (detailB.getPersonStage().equals("KT")) {
							// if (nicU != null) {
							// nicU.getNicTransaction().setPersonCode(
							// detailB.getPersonCode());
							// }
							// this.deletePerson(detailB
							// .getTransactionId());
							// } else if (detailB.getPersonStage()
							// .equals("KM")) {
							// if (nicU != null) {
							// nicU.getNicTransaction().setPersonCode(
							// detailB.getPersonCode());
							// }
							// epp.setOrgPerson(detailB.getPersonCode());
							// BaseModelSingle<Boolean> baseSavePerson =
							// eppPersonService
							// .saveOrUpdateData(epp);
							// if (!baseSavePerson.isError()
							// || baseSavePerson.getMessage() != null) {
							// return this.checkExistAndSaveException(
							// baseSavePerson, eppWsLog,
							// response, handover);
							// }
							// }
							// }
							// List<SiteGroups> groupList =
							// siteService.findAll();
							// if (groupList != null && epp != null) {
							// for (SiteGroups site : groupList) {
							// if (!site.getGroupId().equals(
							// handover.getSiteCode())) {
							// BaseModelSingle<Boolean> baseAddQJ = this
							// .addObjToQueueJob(
							// String.valueOf(epp
							// .getPersonId()),
							// OBJ_TYPE_QUEUE_CREATE_PERSON,
							// site.getGroupId(),
							// null,
							// Contants.URL_UPLOAD_HANDOVER_B);
							// if (!baseAddQJ.isError()
							// || baseAddQJ.getMessage() != null) {
							// return this
							// .checkExistAndSaveException(
							// baseAddQJ,
							// eppWsLog, response,
							// handover);
							// }
							// }
							// }
							// }
						} else if (tpCheck == null
								&& StringUtils.isBlank(detailB.getPackageId())
								&& StringUtils.isNotBlank(handover
										.getPackageOldId())
								&& StringUtils.isBlank(detailB
										.getApproveStage())) {
							check = false;
							eppListHandoverDetailService.deleteHandoverDetail(
									handover.getPackageOldId(),
									detailB.getTransactionId(),
									NicListHandover.TYPE_LIST_B);
						} else {
							tpCheck.setExchangeContent(detailB
									.getExchangeContent());
							tpCheck.setApproveStage(detailB.getApproveStage());
							tpCheck.setApproveNote(detailB.getNoteApprove());
							BaseModelSingle<Boolean> bSaveHD = eppListHandoverDetailService
									.insertDataTable(tpCheck);
							if (!bSaveHD.isError()
									|| bSaveHD.getMessage() != null) {
								return this.checkExistAndSaveException(bSaveHD,
										eppWsLog, response, handover);
							}
						}

						/* Update trạng thái duyệt vào hồ sơ */
						NicTransaction nicTran = txn;
						if (nicTran != null) {
							sitePA = nicTran.getRegSiteCode();
							NicRegistrationData reg = nicTran
									.getNicRegistrationData();
							if (StringUtils
									.isNotBlank(detailB.getArchiveCode())) {

								// cập nhật hoặc thêm mới bản ghi vào bảng
								// EppArchive
								EppArchive eppArchive = eppArchiveService
										.findByTransactionId(nicTran
												.getTransactionId());
								if (eppArchive != null) {
									eppArchive.setArchiveCode(detailB
											.getArchiveCode());
									eppArchive.setUpdatedBy(handover
											.getOfferUserName());
									eppArchive.setUpdatedByName(handover
											.getCreatorName());
									eppArchive.setUpdatedTs(new Date());
								} else {
									eppArchive = new EppArchive();
									eppArchive.setArchiveCode(detailB
											.getArchiveCode());
									eppArchive.setDocCode(nicTran
											.getTransactionId());
									eppArchive.setReceiptNo(nicTran
											.getRecieptNo());
									eppArchive.setAbridgment(HelperClass
											.createFullName(
													reg.getSurnameFull(),
													reg.getMiddlenameFull(),
													reg.getFirstnameFull()));
									eppArchive.setDesc(null);
									eppArchive.setType("HC");
									eppArchive.setStartPage(1L);
									eppArchive.setPageCount(3L);
									eppArchive.setRegOffice(nicTran
											.getIssuingAuthority());
									eppArchive.setSavedTime(null);
									eppArchive.setDocName(null);
									eppArchive.setCreatedTs(new Date());
									eppArchive.setCreatedBy(handover
											.getOfferUserName());
									eppArchive.setCreatedByName(handover
											.getCreatorName());
									eppArchive.setPackNo(null);
								}
								eppArchiveService
										.saveOrUpdateArchive(eppArchive);

								nicTran.setArchiveCode(detailB.getArchiveCode());
							}
							/* cập nhật thông tin nơi cấp hộ chiếu mới nếu có */
							if (StringUtils.isNotBlank(detailB
									.getIssueSiteCode())) {
								nicTran.setIssSiteCode(detailB
										.getIssueSiteCode());
							}
							/* update trạng thái hồ sơ */
							if (check) {
								nicTran.setTransactionStatus(Contants.TRANSACTION_STATUS_CREATED_B);
								BaseModelSingle<Boolean> baseSaveOUTran = nicTransactionService
										.saveOrUpdateTransaction(nicTran);
								if (!baseSaveOUTran.isError()
										|| baseSaveOUTran.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseSaveOUTran, eppWsLog, response,
											handover);
								}
							}
						}
						if (nicU != null) {
							String status = "";
							if (StringUtils.isNotBlank(detailB
									.getApproveStage())) {
								nicU.setJobCurrentStage(Contants.JOB_CURRENT_STAGE_HANDOVER);
								nicU.setJobCurrentStatus(Contants.JOB_CURRENT_STATUS_CREATED_B);
								switch (detailB.getApproveStage()) {
								case "D":
									status = Contants.TRANSACTION_STATUS_APPROVE_D;
									nicU.setJobCurrentStatus(Contants.JOB_CURRENT_STATUS_APPROVE_D);
									nicU.setInvestigationStatus("40");
									break;
								case "K":
									status = Contants.TRANSACTION_STATUS_APPROVE_K;
									nicU.setJobCurrentStatus(Contants.JOB_CURRENT_STATUS_APPROVE_K);
									nicU.setInvestigationStatus("04");
									break;
								case "C":
									status = Contants.TRANSACTION_STATUS_APPROVE_C;
									nicU.setJobCurrentStatus(Contants.JOB_CURRENT_STATUS_APPROVE_C);
									nicU.setInvestigationStatus("01");
									nicTran.setArchiveCode(null);
									break;
								default:
									break;
								}
								/* update trạng thái hồ sơ */
								nicTran.setTransactionStatus(status);
								BaseModelSingle<Boolean> baseUpdateTranstatus = nicTransactionService
										.saveOrUpdateTransaction(nicTran);
								if (!baseUpdateTranstatus.isError()
										|| baseUpdateTranstatus.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseUpdateTranstatus, eppWsLog,
											response, handover);
								}
								BaseModelSingle<Boolean> baseSOUUJ = uploadJobService
										.saveOrUpdateService(nicU);
								if (!baseSOUUJ.isError()
										|| baseSOUUJ.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseSOUUJ, eppWsLog, response,
											handover);
								}
							}
						}
						/*---end---*/
						/* Thêm chi tiết payment vào db */
						if (detailB.getPayments() != null
								&& StringUtils.isNotBlank(detailB
										.getPackageId())) {
							BaseModelSingle<NicTransactionPayment> baseFindTP = nicTransactionPaymentDao
									.findGetPaymentByTransaction(detailB
											.getTransactionId());
							if (!baseFindTP.isError()
									|| baseFindTP.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseFindTP, eppWsLog, response,
										handover);
							}
							NicTransactionPayment payments = baseFindTP
									.getModel();
							if (payments == null)
								continue;
							Double paymentAmount = 0.0;
							for (PaymentDetail payment : detailB.getPayments()) {

								BaseModelSingle<NicTransactionPaymentDetail> baseFindDP = paymentDetailService
										.findDetailPaymentByTransactionId(
												payments.getPaymentId(),
												payment.getTypePayment(),
												payment.getSubTypePayment());
								if (!baseFindDP.isError()
										|| baseFindDP.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseFindDP, eppWsLog, response,
											handover);
								}
								NicTransactionPaymentDetail payDetail = baseFindDP
										.getModel();
								BaseModelSingle<Boolean> baseSOUPD;
								if (payDetail != null) {
									payDetail.setStatusFee(payment
											.getStatusFee().equals("Y") ? true
											: false);
									baseSOUPD = paymentDetailService
											.saveOrUpdatePaymentDetail(payDetail);
									if (payment.getStatusFee().equals("Y")) {
										paymentAmount += payDetail
												.getPaymentAmount();
									}
								} else {
									NicTransactionPaymentDetail pay = new NicTransactionPaymentDetail();
									pay.setPaymentId(payments.getPaymentId());
									pay.setTypePayment(payment.getTypePayment());
									pay.setSubTypePayment(payment
											.getSubTypePayment());
									pay.setPaymentAmount(payment
											.getPaymentAmount());
									pay.setNote(payment.getNamePayment());
									pay.setStatusFee(payment.getStatusFee()
											.equals("Y") ? true : false);
									pay.setCreateBy(Contants.CREATE_BY_SYSTEM);
									pay.setCreateDate(Calendar.getInstance()
											.getTime());
									baseSOUPD = paymentDetailService
											.saveOrUpdatePaymentDetail(pay);
									if (payment.getStatusFee().equals("Y")) {
										paymentAmount += pay.getPaymentAmount();
									}
								}
								if (!baseSOUPD.isError()
										|| baseSOUPD.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseSOUPD, eppWsLog, response,
											handover);
								}
							}
							payments.setPaymentAmount(paymentAmount);
							nicTransactionPaymentDao
									.saveOrUpdatePaymentAmount(payments);
						}
					}
				}
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_UPLOAD_HANDOVER_B, 1,
						handover.getSiteCode());

				// logger.info("success upload handoverB in TTDH, date now:"
				// + HelperClass.convertDateToString1(new Date()));
				/*
				 * Kiểm tra tính hợp lệ của danh sách sau phê duyệt, đưa vào
				 * hàng đợi chờ đồng bộ sang trung tâm cá thê hóa
				 */
				if (StringUtils.isNotBlank(handover.getApproveName())
						&& StringUtils.isNotBlank(handover.getApproveDate())) {
					BaseModelSingle<NicUploadJobDto> baseLPC = nicTransactionService
							.listPackageIDConfig(handover.getPackageId(),
									new Date());
					if (!baseLPC.isError() || baseLPC.getMessage() != null) {
						return this.checkExistAndSaveException(baseLPC,
								eppWsLog, response, handover);
					}
					NicUploadJobDto checkConfig = baseLPC.getModel();
					if (checkConfig != null) {
						BaseModelSingle<Boolean> baseAddOTQJ = this
								.addObjToQueueJob(checkConfig.getPackageId(),
										CONFIG_TYPE_CA_THE_HOA,
										checkConfig.getRegSiteCode(), null,
										null);
						if (!baseAddOTQJ.isError()
								|| baseAddOTQJ.getMessage() != null) {
							return this.checkExistAndSaveException(baseAddOTQJ,
									eppWsLog, response, handover);
						}
					} else {
						NicUploadJobDto checkDefault = nicTransactionService
								.listPackageIDDefault(handover.getPackageId(),
										NicListHandover.TYPE_LIST_B);
						if (checkDefault != null) {
							BaseModelSingle<Boolean> baseAddOTQJ = this
									.addObjToQueueJob(
											checkDefault.getPackageId(),
											CONFIG_TYPE_CA_THE_HOA,
											checkDefault.getRegSiteCode(),
											null, null);
							if (!baseAddOTQJ.isError()
									|| baseAddOTQJ.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseAddOTQJ, eppWsLog, response,
										handover);
							}

						}
					}
					/*---end---*/
					/*
					 * Thêm vào hàng đợi chờ đồng bộ về PA đối với hồ sơ tiếp
					 * nhận ở PA
					 */
					if (StringUtils.isNotEmpty(sitePA)) {
						Boolean test = true;
						List<SiteGroups> groupList = siteService
								.findAllSiteGroubs();
						if (groupList != null) {
							for (SiteGroups site : groupList) {
								if (site.getGroupId().equals(sitePA)) {
									test = false;
									break;
								}
							}
							if (test) {
								BaseModelSingle<Boolean> baseAddOTQJ = this
										.addObjToQueueJob(
												handover.getPackageId(),
												Contants.QUEUE_OBJ_TYPE_DB,
												sitePA, null, null);
								if (!baseAddOTQJ.isError()
										|| baseAddOTQJ.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseAddOTQJ, eppWsLog, response,
											handover);
								}
							}
						} else {
							response.setCode(Contants.CODE_INPUT_FAILD);
							response.setMessage("Không tìm thấy mã đơn vị tiếp nhận.");
						}
					}
				}

				/*---end---*/
			} else {
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}
		} catch (Exception e) {
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error upload handover B:" + e.getMessage());
			// save log
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - uploadHandoverB fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		}
		return response;
	}

	// @POST
	// @Produces("application/json")
	// @Consumes("application/json")
	// @Path("/uploadProcessInfo")
	// public ResponseBase uploadProcessInfo(InfoProcess infoProcess) {
	// ResponseBase response = new ResponseBase();
	// response.setCode(Contants.RESPONSE_CODE_FAIL_API);
	// response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
	// logger.info("start upload info process to ttdh, time:"
	// + HelperClass.convertDateToString1(new Date()));
	// try {
	// if (infoProcess != null) {
	//
	// NicUploadJob nicUp = uploadJobService.findUploadJobByTransId(
	// infoProcess.getTransactionId()).getModel();
	// if (nicUp != null) {
	// nicUp.setInvestigationOfficerId(infoProcess
	// .getUserProcess());
	// nicUp.setInvestigationStatus(NicUploadJob.RECORD_STATUS_IN_PROGRESS);
	// nicUp.setUpdateBy(Contants.CREATE_BY_SYSTEM);
	// nicUp.setUpdateDatetime(new Date());
	// uploadJobService.saveOrUpdate(nicUp);
	// } else {
	// throw new Exception("no found transaction Id, "
	// + infoProcess.getTransactionId());
	// }
	// }
	// response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
	// response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
	//
	// /* Lưu bảng thống kê truyền nhận */
	// this.saveOrUpRptData(Contants.URL_UPLOAD_PROCESS_INFO, 1, null);
	//
	// // logger.info("success upload info process to ttdh, time:"
	// // + HelperClass.convertDateToString1(new Date()));
	// } catch (Exception e) {
	// e.printStackTrace();
	// response.setMessage(e.getMessage());
	// logger.error("error: " + e.getMessage());
	// }
	// return response;
	// }

	/* Cập nhật trạng thái xử lý hồ sơ */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadProcessInfo")
	public ResponseBase uploadProcessInfo(UpdateTranStatusProcess infoProcess) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			if (infoProcess != null) {
				if (infoProcess.getListTranId() != null
						&& infoProcess.getListTranId().size() > 0) {
					String processer = infoProcess.getUserName();
					String processerName = infoProcess.getFullName();
					for (TransactionIdInput tran : infoProcess.getListTranId()) {
						Date processDate = StringUtils.isNotBlank(infoProcess
								.getProcessDate()) ? DateUtil.strToDate(
								infoProcess.getProcessDate(),
								DateUtil.FORMAT_YYYYMMDDHHMMSS) : null;
						if ((StringUtils.isNotBlank(processer) || StringUtils
								.isNotBlank(processerName))
								&& processDate != null) {
							BaseModelSingle<NicUploadJob> findJob = uploadJobService
									.findUploadJobByTransId(tran
											.getTransactionId());
							if (!findJob.isError()
									|| findJob.getMessage() != null) {
								throw new Exception(findJob.getMessage());
							}
							NicUploadJob nicUp = findJob.getModel();
							if (nicUp != null) {
								if (StringUtils.isNotBlank(processerName)) {
									nicUp.setInvestigationOfficerId(processerName);
									nicUp.setInvestigationDate(processDate);
								}
								nicUp.setUpdateDatetime(new Date());

								BaseModelSingle<Boolean> baseSaveUJ = uploadJobService
										.saveOrUpdateService(nicUp);
								if (!baseSaveUJ.isError()
										|| baseSaveUJ.getMessage() != null) {
									throw new Exception(baseSaveUJ.getMessage());
								}
							} else {
								response.setCode(Contants.CODE_DATA_NOT_FOUND);
								response.setMessage(Contants.MESSAGE_TRANSACTION_NULL);
								return response;
							}
						}

						BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
								.findTransactionById(tran.getTransactionId());
						if (!baseFindTran.isError()
								&& baseFindTran.getMessage() != null) {
							throw new Exception(baseFindTran.getMessage());
						}
						NicTransaction nicTran = baseFindTran.getModel();
						nicTran.setTransactionStatus(NicTransaction.TRANSACTION_STATUS_INVESTIGATION_PROCESSING);
						BaseModelSingle<Boolean> baseSaveTran = nicTransactionService
								.saveOrUpdateTransaction(nicTran);
						if (!baseSaveTran.isError()
								&& baseSaveTran.getMessage() != null) {
							throw new Exception(baseSaveTran.getMessage());
						}
					}
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_UPLOAD_PROCESS_INFO, 1,
							null);
					response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				} else {
					response.setMessage("Kiểm tra lại dữ liệu đầu vào.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error: " + e.getMessage());
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			// save log
			EppWsLog eppWsLog = new EppWsLog();
			eppWsLog.setType(Contants.TYPE_UPI);
			eppWsLog.setKeyId(Contants.TYPE_UPI + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date()));
			eppWsLog.setUrlRequest(Contants.URL_UPLOAD_PROCESS_INFO);
			try {
				eppWsLog.setDataRequest(new ObjectMapper()
						.writeValueAsString(infoProcess));
			} catch (IOException e1) {
				logger.error(e1.getMessage()
						+ " - convert object to json fail.");
				e1.printStackTrace();

			}
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - uploadProcessInfo fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadSaveProcess")
	public ResponseBase uploadSaveProcess(InfoProcess infoProcess) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// logger.info("start upload save process to ttdh, time:"
		// + HelperClass.convertDateToString1(new Date()));

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_USP);
		eppWsLog.setUrlRequest(Contants.URL_UPLOAD_SAVE_PROCESS);

		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(infoProcess));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();

		}

		try {
			if (infoProcess != null) {

				eppWsLog.setKeyId(infoProcess.getTransactionId());
				BaseModelSingle<NicUploadJob> baseFindUJ = uploadJobService
						.findUploadJobByTransId(infoProcess.getTransactionId());
				if (!baseFindUJ.isError() || baseFindUJ.getMessage() != null) {
					return this.checkExistAndSaveException(baseFindUJ,
							eppWsLog, response, infoProcess);
				}
				NicUploadJob nicUp = baseFindUJ.getModel();
				if (nicUp != null) {
					nicUp.setInvestigationStatus(NicUploadJob.RECORD_STATUS_COMPLETED);
					if (StringUtils.isNotEmpty(infoProcess.getObjectDate())) {
						Date date = HelperClass.convertStringToDate(infoProcess
								.getObjectDate(), infoProcess.getObjectDate()
								.length());
						nicUp.setDateApproveInvestigation(date);
					}
					if (StringUtils.isNotEmpty(infoProcess.getNinDate())) {
						Date date = HelperClass.convertStringToDate(infoProcess
								.getNinDate(), infoProcess.getNinDate()
								.length());
						nicUp.setDateApproveNin(date);
					}

					if (StringUtils.isNotEmpty(infoProcess.getProcessDate())) {
						Date date = HelperClass.convertStringToDate(infoProcess
								.getProcessDate(), infoProcess.getProcessDate()
								.length());
						nicUp.setInvestigationDate(date);
					}
					if (infoProcess.getBlackListId() != null) {
						nicUp.setOriginalyBlacklistId(infoProcess
								.getBlackListId());
					}
					if (StringUtils.isNotBlank(infoProcess.getObjectNote())) {
						nicUp.setNoteApproveObj(infoProcess.getObjectNote());
					}
					if (StringUtils.isNotBlank(infoProcess.getNinNote())) {
						nicUp.setNoteApproveNin(infoProcess.getNinNote());
					}
					if (StringUtils.isNotBlank(infoProcess.getObjectName())) {
						nicUp.setUserApproverObj(infoProcess.getObjectName());
						nicUp.setInvestigationOfficerId(infoProcess
								.getObjectName());
					}
					nicUp.setUpdateBy(Contants.CREATE_BY_SYSTEM);
					nicUp.setUpdateDatetime(new Date());

					BaseModelSingle<Boolean> baseSaveUJ = uploadJobService
							.saveOrUpdateService(nicUp);
					if (!baseSaveUJ.isError()
							|| baseSaveUJ.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveUJ,
								eppWsLog, response, infoProcess);
					}

					// Tạo person khi lưu hồ sơ
					// this.createPersonInNic(infoProcess);
				} else {
					response.setMessage("no found transaction Id, "
							+ infoProcess.getTransactionId());
					return response;
				}
			}
			if (!"Y".equals(infoProcess.getIsSave())) {
				/* update trạng thái hồ sơ */
				BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
						.findTransactionById(infoProcess.getTransactionId());
				if (!baseFindTran.isError()
						|| baseFindTran.getMessage() != null) {
					return this.checkExistAndSaveException(baseFindTran,
							eppWsLog, response, infoProcess);
				}
				NicTransaction nicTran = baseFindTran.getModel();
				nicTran.setTransactionStatus(Contants.TRANSACTION_STATUS_UPLOAD_SAVE_PROCESS);
				BaseModelSingle<Boolean> baseUpdateTranstatus = nicTransactionService
						.saveOrUpdateTransaction(nicTran);
				if (!baseUpdateTranstatus.isError()
						|| baseUpdateTranstatus.getMessage() != null) {
					return this.checkExistAndSaveException(
							baseUpdateTranstatus, eppWsLog, response,
							infoProcess);
				}
			}

			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			this.saveOrUpRptData(Contants.URL_UPLOAD_SAVE_PROCESS, 1, null);

			// logger.info("success upload save process to ttdh, time:"
			// + HelperClass.convertDateToString1(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error: " + e.getMessage());

			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			// save log
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - uploadSaveProcess fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateDetailPassport")
	public ResponseBase updateDetailPassport(UpdatePassportModel update)
			throws JAXBException {
		ResponseBase resutl = new ResponseBase();
		resutl.setCode(Contants.RESPONSE_CODE_FAIL_API);
		resutl.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			BaseModelSingle<NicDocumentData> baseFindDocData = documentDataService
					.findByDocNumber(update.getPassportNo());
			NicDocumentData nicDoc = baseFindDocData.getModel();
			if (nicDoc != null) {
				nicDoc.setCancelBy(update.getCancelBy());
				if (update.getCancelDatetime() != null) {
					Date date = HelperClass.convertStringToDate(update
							.getCancelDatetime(), update.getCancelDatetime()
							.length());
					nicDoc.setCancelDatetime(date);
				}
				nicDoc.setChipSerialNo(update.getChipSerialNo());
				if (update.getDateOfExpiry() != null) {
					Date date = HelperClass.convertStringToDate(update
							.getDateOfExpiry(), update.getDateOfExpiry()
							.length());
					nicDoc.setDateOfExpiry(date);
				}
				if (update.getDateOfIssue() != null) {
					Date date = HelperClass
							.convertStringToDate(update.getDateOfIssue(),
									update.getDateOfIssue().length());
					nicDoc.setDateOfIssue(date);
				}
				nicDoc.setIcaoLine1(update.getIcaoLine1());
				nicDoc.setIcaoLine2(update.getIcaoLine2());
				nicDoc.setIssueBy(update.getIssueBy());
				if (update.getIssueDatetime() != null) {
					Date date = HelperClass.convertStringToDate(update
							.getIssueDatetime(), update.getIssueDatetime()
							.length());
					nicDoc.setIssueDatetime(date);
				}
				nicDoc.setPositionSigner(update.getPositionSigner());
				nicDoc.setSigner(update.getSigner());
				nicDoc.setPrintingSite(update.getPrintingSite());
				nicDoc.setReceiveBy(update.getReceiveBy());
				if (update.getReceiveDatetime() != null) {
					Date date = HelperClass.convertStringToDate(update
							.getReceiveDatetime(), update.getReceiveDatetime()
							.length());
					nicDoc.setReceiveDatetime(date);
				}
				nicDoc.setRejectBy(update.getRejectBy());
				if (update.getRejectDatetime() != null) {
					Date date = HelperClass.convertStringToDate(update
							.getRejectDatetime(), update.getRejectDatetime()
							.length());
					nicDoc.setRejectDatetime(date);
				}
				nicDoc.setActiveFlag(null);
				nicDoc.setIssuedFlag(null);
				if (update.getStatus().equals("ISSUANCE")) {
					nicDoc.setActiveFlag(true);
					nicDoc.setIssuedFlag(true);
				} else if (update.getStatus().equals("NONE")) {
					nicDoc.setActiveFlag(false);
					nicDoc.setIssuedFlag(true);
				}
				nicDoc.setStatus(update.getStatus());

				documentDataService.saveOrUpdate(nicDoc);
				BaseModelSingle<NicTransaction> baseGetNicTranById = nicTransactionService
						.getNicTransactionById(update.getTransactionId());
				NicTransaction nicTran = baseGetNicTranById.getModel();
				this.addObjToQueueJob(nicDoc.getId().getPassportNo(),
						Contants.QUEUE_OBJ_TYPE_UPDATE_PP,
						nicTran.getRegSiteCode(), "",
						Contants.URL_UPDATE_DETAIL_PASSPORT);
			}
			resutl.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			resutl.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
		} catch (Exception e) {
			e.printStackTrace();
			resutl.setMessage(e.getMessage());
			logger.error("error: " + e.getMessage());
		}

		return resutl;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/syncDetailPassport/{site}")
	public Response<UpdatePassportModel> syncDetailPassport(
			@PathParam("site") String site) {
		Response<UpdatePassportModel> response = new Response<UpdatePassportModel>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "downloadPerson";
		// if(!this.checkConnectApi(nameApi, site)){
		// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return response;
		// }
		try {
			BaseModelSingle<SyncQueueJob> baseFindQ = queueJobService
					.findQueueByStatus(site,
							Contants.CODE_STATUS_QUEUE_PENDING,
							Contants.QUEUE_OBJ_TYPE_UPDATE_PP);
			SyncQueueJob queue = baseFindQ.getModel();
			if (queue != null) {
				BaseModelSingle<NicDocumentData> baseFindDocData = documentDataService
						.findByDocNumber(queue.getObjectId());
				NicDocumentData nicDoc = baseFindDocData.getModel();
				if (nicDoc != null) {
					UpdatePassportModel update = new UpdatePassportModel();
					update.setIdQueue(queue.getId());
					update.setCancelBy(nicDoc.getCancelBy());
					if (nicDoc.getCancelDatetime() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(
										nicDoc.getCancelDatetime(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						update.setCancelDatetime(date);
					}
					update.setChipSerialNo(nicDoc.getChipSerialNo());
					if (nicDoc.getDateOfExpiry() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(nicDoc.getDateOfExpiry(),
										STR_FORMAT_DATE_yyyyMMdd);
						update.setDateOfExpiry(date);
					}
					if (nicDoc.getDateOfIssue() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(nicDoc.getDateOfIssue(),
										STR_FORMAT_DATE_yyyyMMdd);
						update.setDateOfIssue(date);
					}
					update.setIcaoLine1(nicDoc.getIcaoLine1());
					update.setIcaoLine2(nicDoc.getIcaoLine2());
					update.setIssueBy(nicDoc.getIssueBy());
					if (nicDoc.getIssueDatetime() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(nicDoc.getIssueDatetime(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						update.setIssueDatetime(date);
					}
					update.setPassportNo(nicDoc.getId().getPassportNo());
					update.setPositionSigner(nicDoc.getPositionSigner());
					update.setPrintingSite(nicDoc.getPrintingSite());
					update.setReceiveBy(nicDoc.getReceiveBy());
					if (nicDoc.getReceiveDatetime() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(
										nicDoc.getReceiveDatetime(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						update.setReceiveDatetime(date);
					}
					update.setRejectBy(nicDoc.getRejectBy());
					if (nicDoc.getRejectDatetime() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(
										nicDoc.getRejectDatetime(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						update.setRejectDatetime(date);
					}
					update.setSigner(nicDoc.getSigner());
					update.setStatus(nicDoc.getStatus());
					update.setTransactionId(nicDoc.getId().getTransactionId());
					BaseModelSingle<NicTransaction> baseGetNicTranById = nicTransactionService
							.getNicTransactionById(update.getTransactionId());
					NicTransaction nicTran = baseGetNicTranById.getModel();
					if (nicTran != null) {
						update.setPassportType(nicTran.getPassportType());
						update.setIsEpassport((nicTran.getIsEpassport() != null && nicTran
								.getIsEpassport()) ? "Y" : "N");
					}
					response.setData(update);
					response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

				}
				queueJobService.updateStatusQueueJob(queue.getId(),
						Contants.CODE_STATUS_QUEUE_DOING);
				/* Lưu bảng thống kê truyền nhận */
				if (response.getData() != null
						&& queue.getDateUpdateStatusHanding() == null) {
					this.saveOrUpRptData(Contants.URL_SYNC_DETAIL_PASSPORT, 1,
							site);
				}

			} else {
				logger.info("No data to synchronize");
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}
		} catch (Exception e) {
			logger.error("error download person === " + e.getMessage());
			e.printStackTrace();
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadFullTransaction")
	public ResponseBase uploadFullTransaction(FullTransactionModel request)
			throws JAXBException {
		ResponseBase resutl = new ResponseBase();
		resutl.setCode(Contants.RESPONSE_CODE_FAIL_API);
		resutl.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		String siteCode = request.getTransactionF().getRegSiteCode();

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_HS);
		eppWsLog.setUrlRequest(Contants.URL_UPLOAD_FULL_TRANSACTION);

		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + "convert object to json fail.");
			e1.printStackTrace();

		}

		// Kiểm tra FTransaction đã đầy đủ thông tin hay chưa.
		/*
		 * ResponseBase error = checkValidateFullTransaction(request); if (error
		 * != null) { return error; }
		 */
		// set keyId for Ws_Log
		eppWsLog.setKeyId(request.getTransactionF().getTransactionId());

		try {

			// Tạo HS
			Transaction transaction = request.getTransactionF();
			BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
					.findTransactionById(transaction.getTransactionId());
			if (!baseFindTran.isError() || baseFindTran.getMessage() != null) {
				return this.checkExistAndSaveException(baseFindTran, eppWsLog,
						resutl, request);
			}
			NicTransaction transactionTemp = baseFindTran.getModel();
			if (transactionTemp == null) {
				NicTransaction nicTransactionDBO = null;
				NicRegistrationData nicRegDataDBO = null;
				Collection<NicTransactionAttachment> nicTransDocDBOList = null;
				siteCode = transaction.getRegSiteCode();
				String transactionId = transaction.getTransactionId();
				nicTransactionDBO = this.convertNicTransaction(transaction,
						false);
				BaseModelSingle<NicTransaction> baseApplyNameTrun = nicTransactionService
						.applyNameTruncation(nicTransactionDBO);
				if (!baseApplyNameTrun.isError()
						|| baseApplyNameTrun.getMessage() != null) {
					return this.checkExistAndSaveException(baseApplyNameTrun,
							eppWsLog, resutl, request);
				}
				this.validateTransaction(transaction);
				/*
				 * transactionTemp =
				 * nicTransactionService.findById(transactionId); if
				 * (transactionTemp!=null) { String errorCode =
				 * TX_UPLOAD_ERROR_CODE_DUPLICATE_TRANSACTION_ID; logger.error(
				 * "Transaction cannot be uploaded as duplicate transaction Id found.[{}], errorCode:{}"
				 * , transactionId, errorCode); throw new
				 * TransactionServiceException(
				 * "Transaction cannot be uploaded as duplicate transaction Id found."
				 * , errorCode); }
				 */
				BaseModelSingle<String> baseSaveTran = nicTransactionService
						.saveTransaction(nicTransactionDBO);
				if (!baseSaveTran.isError()
						|| baseSaveTran.getMessage() != null) {
					return this.checkExistAndSaveException(baseSaveTran,
							eppWsLog, resutl, request);
				}
				String transactionResult = baseSaveTran.getModel();
				logger.debug("saving nicTransaction, transactionId:{}",
						transactionResult);
				// nicTransactionService.saveOrUpdateTransaction(nicTransactionDBO);
				if (nicTransactionDBO.getNicRegistrationData() != null) {

					nicRegDataDBO = nicTransactionDBO.getNicRegistrationData();// mapper.parseRegistrationDataDBO(transactionId,
																				// nin,
																				// regDataDTO);
					nicTransDocDBOList = nicTransactionDBO
							.getNicTransactionAttachments();// mapper.parseTransactionDocDBOList(transactionId,
															// regDataDTO);

					BaseModelSingle<String> baseSaveRegisData = registrationDataDao
							.saveNicRegistrationData(nicRegDataDBO);
					if (!baseSaveRegisData.isError()
							|| baseSaveRegisData.getMessage() != null) {
						return this.checkExistAndSaveException(
								baseSaveRegisData, eppWsLog, resutl, request);
					}
					// logger.debug("saving registrationData, transactionId:{}",
					// regisDataResult);
					// registrationDataDao.saveOrUpdate(nicRegDataDBO);

					// [2017] save payment
					if (nicTransactionDBO.getNicTransactionPayment() != null
							&& StringUtils.isNotBlank(nicTransactionDBO
									.getNicTransactionPayment().getPaymentId())) {
						BaseModelSingle<String> baseSaveTranPay = transactionPaymentDao
								.saveTranPayment(nicTransactionDBO
										.getNicTransactionPayment());
						if (!baseSaveTranPay.isError()
								|| baseSaveTranPay.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseSaveTranPay, eppWsLog, resutl, request);
						}
						// logger.debug("saving paymentData, transactionId:{}",
						// paymentDataResult);
						// transactionPaymentDao.saveOrUpdate(nicTransactionDBO.getNicTransactionPayment());
					}
					for (NicTransactionAttachment nicTransDoc : nicTransDocDBOList) {
						if (StringUtils.isBlank(nicTransDoc.getTransactionId())) {
							// logger.warn("nicTransDoc.getTransactionId() is null");
							if (nicTransactionDBO != null) {
								nicTransDoc.setTransactionId(nicTransactionDBO
										.getTransactionId());
								// logger.warn("nicTransDoc.getTransactionId():{}",
								// nicTransactionDBO.getTransactionId());
							}
						}
						// logger.debug("saving transactionDoc, transactionId:{} docType:{}",
						// nicTransactionDBO.getTransactionId(),
						// nicTransDoc.getDocType());
						// transactionDocumentDao.saveOrUpdate(nicTransDoc);
						BaseModelSingle<Long> baseSaveTranAttach = transactionDocumentDao
								.saveTranAttachment(nicTransDoc);
						if (!baseSaveTranAttach.isError()
								|| baseSaveTranAttach.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseSaveTranAttach, eppWsLog, resutl,
									request);
						}
						// nicTransDoc.set
						// logger.debug("saving transactionDoc, transactionId:{} docType:{} --> docId: {}/{}",
						// new Object[] {nicTransactionDBO.getTransactionId(),
						// nicTransDoc.getDocType(), docId,
						// nicTransDoc.getTransactionDocId()});
					}

					/* Thêm tạo person khi upload hồ sơ */
					// this.savePersonByUpload(transaction); Đóng 28-03-2020

					List<NicTransactionLog> transLogList = new ArrayList<NicTransactionLog>();
					NicTransactionLog dcmTransLog = new NicTransactionLog();
					dcmTransLog.setRefId(nicTransactionDBO.getTransactionId());
					dcmTransLog.setLogCreateTime(nicTransactionDBO
							.getCreateDatetime());
					dcmTransLog.setOfficerId(nicTransactionDBO.getCreateBy());
					dcmTransLog.setSiteCode(nicTransactionDBO.getRegSiteCode());
					dcmTransLog.setStartTime(nicTransactionDBO
							.getCreateDatetime());

					dcmTransLog
							.setTransactionStage(Contants.TRANSACTION_STATE_TX_UPLOAD);
					dcmTransLog.setTransactionStatus(TransactionStatus.Captured
							.getKey());
					transLogList.add(dcmTransLog);
					// List<NicTransactionLog> errorList = nicTransactionService
					// .saveTransactionLogs(transLogList);
					// for (NicTransactionLog errorLog: errorList) {
					// logger.warn("Failed to save log: {}, {}, {}, {}", new
					// Object[] {errorLog.getRefId(),
					// errorLog.getTransactionStage(),
					// errorLog.getTransactionStatus(),
					// errorLog.getSiteCode()});
					// }
				}
				// Step 2: Create Nic Job
				Integer jobPriority = nicTransactionDBO.getPriority();
				if (jobPriority == null) {
					jobPriority = NicUploadJob.JOB_PRIORITY_NORMAL;
				}
				Long jobId = null;
				BaseModelSingle<Long> baseCreateWJ = nicUploadJobDao
						.createWorkflowJobCheck(transactionId,
								transaction.getTransactionType(), jobPriority,
								1, transaction.getRegisData().getPersonCode());
				if (!baseCreateWJ.isError()
						|| baseCreateWJ.getMessage() != null) {
					return this.checkExistAndSaveException(baseCreateWJ,
							eppWsLog, resutl, request);
				}
				jobId = baseCreateWJ.getModel();
				// logger.info("[saveMainTransaction] save NicUploadJob, transactionId:{}, jobId:{} ",
				// transactionId, jobId);

				// Step 3: Update Nic Transaction
				nicTransactionDBO.getNicRegistrationData().setWorkflowJobId(
						jobId);
				BaseModelSingle<Void> baseSOURegisData = registrationDataDao
						.saveOrUpdateRegisData(nicTransactionDBO
								.getNicRegistrationData());
				if (!baseSOURegisData.isError()
						|| baseSOURegisData.getMessage() != null) {
					return this.checkExistAndSaveException(baseSOURegisData,
							eppWsLog, resutl, request);
				}
				// logger.info("[saveMainTransaction] Update Nic Registration Data, jobId:{} ",
				// jobId);

				List<NicFPData> fpDataList = this
						.convertFPDataList(nicTransactionDBO);
				for (NicFPData fpData : fpDataList) {
					BaseModelSingle<Boolean> baseSaveFPData = nicFPDataDao
							.saveNicFPData(fpData);
					if (!baseSaveFPData.isError()
							|| baseSaveFPData.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveFPData,
								eppWsLog, resutl, request);
					}
				}

				// logger.debug("saveMainTransaction end, transactionId:{}",
				// transactionId);
				// 5/12/2019 update status upload no call service by ric
				BaseModelSingle<Boolean> baseUpdateSL = this
						.updateStatusLog(transactionId,
								Contants.TRANSACTION_STATUS_RIC_UPLOADED);
				if (!baseUpdateSL.isError()
						|| baseUpdateSL.getMessage() != null) {
					return this.checkExistAndSaveException(baseUpdateSL,
							eppWsLog, resutl, request);
				}
				/*
				 * SyncQueueJob isExistTran =
				 * queueJobService.findQueueByObjectId
				 * (transaction.getTransactionId(), siteCode, "",
				 * Contants.QUEUE_OBJ_TYPE_UPDATE_PP); if(isExistTran == null)
				 * this.addObjToQueueJob(transaction.getTransactionId(),
				 * Contants.QUEUE_OBJ_TYPE_HS_FULL, siteCode, "");
				 */
			}
			// ==========================================================

			// Tạo danh sách A
			HandoverA handoverA = request.getMhandoverA();
			// check validate input
			if (handoverA != null) {
				this.checkValidateMHandoverA(handoverA);

				// Kiểm tra đã tồn tại của danh sách
				BaseModelSingle<NicListHandover> baseFindHan = listHandoverService
						.findByPackageId(new NicListHandoverId(handoverA
								.getPackageId(), NicListHandover.TYPE_LIST_A));
				if (!baseFindHan.isError() || baseFindHan.getMessage() != null) {
					return this.checkExistAndSaveException(baseFindHan,
							eppWsLog, resutl, request);
				}
				NicListHandover hanCheck = baseFindHan.getModel();
				if (hanCheck == null) {
					// logger.info("start save handover A. packageId: " +
					// handover.getPackageId());
					NicListHandover nicHandover = new NicListHandover();
					nicHandover.setId(new NicListHandoverId(handoverA
							.getPackageId(), NicListHandover.TYPE_LIST_A));
					// nicHandover.setPackageId(handoverA.getPackageId());
					nicHandover.setSiteCode(handoverA.getSiteCode());

					if (StringUtils.isNotEmpty(handoverA.getApproveDate())) {
						Date date = HelperClass.convertStringToDate(handoverA
								.getApproveDate(), handoverA.getApproveDate()
								.length());
						nicHandover.setApproveDate(date);
					}

					if (StringUtils.isNotEmpty(handoverA.getOfferDate())) {
						Date date = HelperClass.convertStringToDate(handoverA
								.getOfferDate(), handoverA.getOfferDate()
								.length());
						nicHandover.setProposalDate(date);
					}
					nicHandover.setApproveUser(handoverA.getApproveUserName());
					nicHandover.setProposalUser(handoverA.getOfferUserName());
					nicHandover.setPackageName(Contants.CODE_PACKAGE_NAME_A);
					// nicHandover.setTypeList(NicListHandover.TYPE_LIST_A);
					nicHandover.setCreateDate(Calendar.getInstance().getTime());
					nicHandover.setCreateBy(Contants.CREATE_BY_SYSTEM);
					nicHandover.setHandoverStatus(0);
					// nicHandover.setStatusSyncXl(true);
					nicHandover.setCountTransaction(handoverA.getCount());

					nicHandover.setProposalName(handoverA.getProposalName());
					nicHandover.setApproveName(handoverA.getApproveName());
					nicHandover.setApprovePosition(handoverA
							.getApprovePosition());
					nicHandover.setCreatorName(handoverA.getCreatorName());
					nicHandover.setUpdateBy(handoverA.getUpdateBy());
					if (handoverA.getUpdateDate() != null) {
						Date date = HelperClass.convertStringToDate(handoverA
								.getUpdateDate(), handoverA.getUpdateDate()
								.length());
						nicHandover.setUpdateDate(date);
					}
					nicHandover.setUpdateByName(handoverA.getUpdateByName());
					nicHandover.setPlaceOfDelivery(handoverA
							.getPlaceOfDelivery());
					if (handoverA.getDateOfDelivery() != null) {
						Date date = HelperClass.convertStringToDate(handoverA
								.getDateOfDelivery(), handoverA
								.getDateOfDelivery().length());
						nicHandover.setDateOfDelivery(date);
					}
					BaseModelSingle<Void> baseSaveHan = listHandoverService
							.saveHandover(nicHandover);
					if (!baseSaveHan.isError()
							|| baseSaveHan.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveHan,
								eppWsLog, resutl, request);
					}
				}
				// if(handoverA.getReceipts() != null &&
				// handoverA.getReceipts().size() > 0){
				if (handoverA.getReceipts() != null
						&& handoverA.getReceipts().size() > 0) {
					for (ReceiptManager rm : handoverA.getReceipts()) {
						if (rm.getReceiptNo() != "") {
							BaseModelList<EppRecieptManager> baseFindAllRM = rmService
									.findAllRecieptManager(rm.getReceiptNo());
							if (!baseFindAllRM.isError()
									|| baseFindAllRM.getMessage() != null) {
								return this.checkExistAndSaveExceptions(
										baseFindAllRM, eppWsLog, resutl,
										request);
							}
							List<EppRecieptManager> list = baseFindAllRM
									.getListModel();
							if (list == null || list.size() == 0) {
								// logger.info("start save receipt manager, recieptNo: "
								// + rm.getReceiptNo());
								EppRecieptManager epp = new EppRecieptManager();
								epp.setRecieptNo(rm.getReceiptNo());
								epp.setOfficeName(rm.getOfficeName());
								epp.setFullname(rm.getName());
								if (StringUtils.isNotEmpty(rm.getDob())) {
									Date date = HelperClass
											.convertStringToDateTk(rm.getDob(),
													0);
									epp.setDob(date);
									if (rm.getDob() != null) {
										switch (rm.getDob().length()) {
										case 4:
											epp.setDateDob("Y");
											break;
										case 6:
											epp.setDateDob("M");
											break;
										case 8:
											epp.setDateDob("D");
											break;
										default:
											break;
										}
									}
								}
								epp.setAddress(rm.getAddress());
								epp.setNinNumber(rm.getNin());
								epp.setPaymentAmount(rm.getPaymentAmount());
								epp.setCreateBy(rm.getCreateBy());
								epp.setCreateDate(Calendar.getInstance()
										.getTime());
								epp.setRegSiteCode(rm.getRegSiteCode());
								/*
								 * if(StringUtils.isNotEmpty(rm.getDateOfIssue())
								 * ){ Date date =
								 * HelperClass.convertStringToDate
								 * (rm.getDateOfIssue (),
								 * rm.getDateOfIssue().length());
								 * 
								 * }
								 */
								epp.setDateOfIssue(rm.getDateOfIssue());
								epp.setNote(rm.getNote());
								epp.setPlaceOfReceipt(rm.getPlaceOfReciept());
								if (StringUtils.isNotEmpty(rm
										.getDeliveryAtOffice()))
									epp.setDeliveryAtOffice(rm
											.getDeliveryAtOffice().equals("Y") ? true
											: false);
								epp.setDeliveryOffice(rm.getDeliveryOffice());
								epp.setAmountOfPassport(rm
										.getAmountOfPassport());
								epp.setAmountOfRegistration(rm
										.getAmountOfRegistration());
								epp.setAmountOfPerson(rm.getAmountOfPerson());
								epp.setAmountOfImage(rm.getAmountOfImage());
								epp.setDocumentType(rm.getDocumentType());
								epp.setPrevPassportNo(rm.getPrevPassportNo());
								epp.setAddedDocuments(rm.getAddedDocuments());
								epp.setDocumentaryNo(rm.getDocumentaryNo());
								epp.setDocumentaryIssued(rm
										.getDocumentaryIssued());
								epp.setStatus(rm.getStatus());
								epp.setIsDelivered(rm.getIsDelivered());
								if (StringUtils
										.isNotEmpty(rm.getIsPostOffice()))
									epp.setIsPostOffice(rm.getIsPostOffice()
											.equals("Y") ? true : false);
								epp.setNoteOfDelivery(rm.getNoteOfDelivery());
								epp.setSignName(rm.getSignName());
								epp.setDocOfDelivery(rm.getDocOfDelivery());
								epp.setDocmentaryOffice(rm
										.getDocumentaryOffice());
								epp.setDocmentaryAddress(rm
										.getDocumentaryAddress());
								epp.setListCode(rm.getListCode());
								if (StringUtils.isNotEmpty(rm
										.getInputCompleted()))
									epp.setInputCompleted(rm
											.getInputCompleted().equals("Y") ? true
											: false);
								epp.setDeletedBy(rm.getDeletedBy());
								if (rm.getDeletedDate() != null) {
									Date date = HelperClass
											.convertStringToDate(rm
													.getDeletedDate(), rm
													.getDeletedDate().length());
									epp.setDeletedDate(date);
								}
								/* epp.setDeletedDate(rm.getDeletedDate()); */
								epp.setDeletedName(rm.getDeletedName());
								epp.setDeletedReason(rm.getDeletedReason());
								epp.setReceivedBy(rm.getReceivedBy());
								epp.setCreateByName(rm.getCreateByName());
								BaseModelSingle<Boolean> baseSaveReciept = rmService
										.saveOrUpdateNew(epp);
								if (!baseSaveReciept.isError()
										|| baseSaveReciept.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseSaveReciept, eppWsLog, resutl,
											request);
								}
								// rmService.save(epp);
							}
						}

						if (rm.getHandovers() != null) {
							for (DetailHandover detailA : rm.getHandovers()) {
								BaseModelSingle<EppListHandoverDetail> baseGetHD = eppListHandoverDetailService
										.getListPackageByPackageIdAndTranID(
												detailA.getPackageId(),
												detailA.getTransactionId(),
												NicListHandover.TYPE_LIST_A);
								if (!baseGetHD.isError()
										|| baseGetHD.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseGetHD, eppWsLog, resutl,
											request);
								}
								EppListHandoverDetail tp = baseGetHD.getModel();
								if (tp == null) {
									tp = new EppListHandoverDetail();
									tp.setPackageId(detailA.getPackageId());
									tp.setTransactionId(detailA
											.getTransactionId());
									tp.setProposalStage(detailA.getOfferStage());
									tp.setApproveStage(detailA
											.getApproveStage());
									tp.setProposalNote(detailA.getNoteOffer());
									tp.setApproveNote(detailA.getNoteApprove());
									tp.setTypeList(NicListHandover.TYPE_LIST_A);
									BaseModelSingle<Boolean> baseSaveHD = eppListHandoverDetailService
											.saveHandoverDetail(tp);
									if (!baseSaveHD.isError()
											|| baseSaveHD.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseSaveHD, eppWsLog, resutl,
												request);
									}
									// Boolean success = baseSaveHD.getModel();
									// logger.info("===listHandoverDetail status: "
									// + success);

								}

								/*
								 * Đưa vào hàng đợi đồng bộ sang các ttxl
								 * List<SiteGroups> sgList =
								 * siteService.findAll(); for (SiteGroups gs :
								 * sgList) { this.addObjToQueueJob(
								 * detailA.getTransactionId(),
								 * Contants.QUEUE_OBJ_TYPE_HS, gs.getGroupId(),
								 * TRAN_STATUS_SUBMIT_PA_A); }
								 */

								// reset chạy lại job check lần nữa
								BaseModelSingle<NicUploadJob> baseFindUJ = uploadJobService
										.findUploadJobByTransId(detailA
												.getTransactionId());
								if (!baseFindUJ.isError()
										|| baseFindUJ.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseFindUJ, eppWsLog, resutl,
											request);
								}
								NicUploadJob nicUp = baseFindUJ.getModel();
								NicTransaction txn = nicUp.getNicTransaction();
								if (nicUp != null
										&& txn != null
										&& !txn.getRegSiteCode().equals(
												Contants.QUEUE_RECEIVER_MB)
										&& !txn.getRegSiteCode().equals(
												Contants.QUEUE_RECEIVER_MT)
										&& !txn.getRegSiteCode().equals(
												Contants.QUEUE_RECEIVER_MN)) {
									// this.resetWorkflowCheckData(nicUp);

									txn.setPaBlacklistId(detailA
											.getPaBlacklistId());
									txn.setPaInqBllUser(detailA
											.getPaInqBllUser());
									txn.setPaArchiveCode(detailA
											.getPaArchiveCode());
									if (StringUtils.isNotEmpty(detailA
											.getPaSearchBio()))
										txn.setPaSearchBio(detailA
												.getPaSearchBio().equals("Y") ? true
												: false);
									if (detailA.getPaJoinPersonDate() != null) {
										Date date = HelperClass
												.convertStringToDate(
														detailA.getPaJoinPersonDate(),
														detailA.getPaJoinPersonDate()
																.length());
										txn.setPaJoinPersonDate(date);
									}
									if (detailA.getPaSaveDocDate() != null) {
										Date date = HelperClass
												.convertStringToDate(
														detailA.getPaSaveDocDate(),
														detailA.getPaSaveDocDate()
																.length());
										txn.setPaSaveDocDate(date);
									}
									if (detailA.getPaSearchObjDate() != null) {
										Date date = HelperClass
												.convertStringToDate(
														detailA.getPaSearchObjDate(),
														detailA.getPaSearchObjDate()
																.length());
										txn.setPaSearchObjDate(date);
									}
									if (StringUtils.isNotBlank(detailA
											.getPersonStage())
											&& detailA.getPersonStage()
													.length() == 2) {
										txn.setMatchedTypePerson(detailA
												.getPersonStage());
									}
									BaseModelSingle<Boolean> baseSaveTran = nicTransactionService
											.saveOrUpdateTransaction(txn);
									if (!baseSaveTran.isError()
											|| baseSaveTran.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseSaveTran, eppWsLog, resutl,
												request);
									}
								}
								if (detailA.getPayments() != null) {
									for (PaymentDetail payment : detailA
											.getPayments()) {
										BaseModelSingle<NicTransactionPayment> baseFindTranPay = nicTransactionPaymentDao
												.findGetPaymentByTransaction(payment
														.getTransactionId());
										if (!baseFindTranPay.isError()
												|| baseFindTranPay.getMessage() != null) {
											return this
													.checkExistAndSaveException(
															baseFindTranPay,
															eppWsLog, resutl,
															request);
										}
										NicTransactionPayment payments = baseFindTranPay
												.getModel();
										if (payments == null)
											continue;
										BaseModelSingle<NicTransactionPaymentDetail> baseFindDP = paymentDetailService
												.findDetailPaymentByTransactionId(
														payments.getPaymentId(),
														payment.getTypePayment(),
														payment.getSubTypePayment());
										if (!baseFindDP.isError()
												|| baseFindDP.getMessage() != null) {
											return this
													.checkExistAndSaveException(
															baseFindDP,
															eppWsLog, resutl,
															request);
										}
										NicTransactionPaymentDetail payDetail = baseFindDP
												.getModel();
										if (payDetail != null)
											continue;// Đã tồn tại
										// logger.info("start save detail payment, paymentId: "
										// + payments.getPaymentId());
										NicTransactionPaymentDetail pay = new NicTransactionPaymentDetail();
										pay.setPaymentId(payments
												.getPaymentId());
										pay.setTypePayment(payment
												.getTypePayment());
										pay.setSubTypePayment(payment
												.getSubTypePayment());
										pay.setPaymentAmount(payment
												.getPaymentAmount());
										pay.setNote(payment.getNamePayment());
										pay.setStatusFee(payment.getStatusFee()
												.equals("Y") ? true : false);
										pay.setCreateBy(Contants.CREATE_BY_SYSTEM);
										pay.setCreateDate(Calendar
												.getInstance().getTime());
										BaseModelSingle<Boolean> baseSOUPD = paymentDetailService
												.saveOrUpdatePaymentDetail(pay);
										if (!baseSOUPD.isError()
												|| baseSOUPD.getMessage() != null) {
											return this
													.checkExistAndSaveException(
															baseSOUPD,
															eppWsLog, resutl,
															request);
										}
									}
								}
								BaseModelSingle<NicUploadJob> baseFindUploadJob = uploadJobService
										.findUploadJobByTransId(detailA
												.getTransactionId());
								if (!baseFindUploadJob.isError()
										|| baseFindUploadJob.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseFindUploadJob, eppWsLog,
											resutl, request);
								}
								NicUploadJob nicU = baseFindUJ.getModel();
								if (nicU != null) {
									NicTransaction nicTran = nicU
											.getNicTransaction();
									nicTran.setPersonCode(detailA
											.getPersonCode());
									BaseModelSingle<Boolean> baseSaveOUTran = nicTransactionService
											.saveOrUpdateTransaction(nicTran);
									if (!baseSaveOUTran.isError()
											|| baseSaveOUTran.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseSaveOUTran, eppWsLog,
												resutl, request);
									}
								}

							}

							if (rm.getBills() != null) {
								for (ReceiptBill rb : rm.getBills()) {
									BaseModelSingle<DetailRecieptFee> baseFindDR = drFeeService
											.findDetailRecieptFee(
													rb.getReceiptNo(),
													rb.getCode(),
													rb.getNumber());
									if (!baseFindDR.isError()
											|| baseFindDR.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseFindDR, eppWsLog, resutl,
												request);
									}
									DetailRecieptFee fees = baseFindDR
											.getModel();
									if (fees != null)
										continue;
									// logger.info("start save receipt bill, code: "
									// + rb.getCode() + ", number: " +
									// rb.getNumber());
									DetailRecieptFee fee = new DetailRecieptFee();
									fee.setRecieptNo(rb.getReceiptNo());
									fee.setCodeBill(rb.getCode());
									fee.setNumberBill(rb.getNumber());
									fee.setPrice(rb.getPrice());
									fee.setPriceFlag(rb.getBillFlag());
									fee.setDescription(rb.getDescription());
									fee.setCreateBy(Contants.CREATE_BY_SYSTEM);
									fee.setCreateDate(Calendar.getInstance()
											.getTime());
									fee.setCashierName(rb.getCashierName());
									fee.setCreateByName(rb.getCreateByName());
									if (rb.getDateOfReceipt() != null) {
										Date date = HelperClass
												.convertStringToDate(rb
														.getDateOfReceipt(), rb
														.getDateOfReceipt()
														.length());
										fee.setDateOfReceipt(date);
									}
									fee.setCustomerName(rb.getCustomerName());
									BaseModelSingle<Boolean> baseSaveDR = drFeeService
											.saveOrUpdateNew(fee);
									if (!baseSaveDR.isError()
											|| baseSaveDR.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseSaveDR, eppWsLog, resutl,
												request);
									}
								}
							}

							if (rm.getFeeRecieptPayment() != null) {
								for (FeeRecieptPaymentModel fr : rm
										.getFeeRecieptPayment()) {
									BaseModelList<FeeRecieptPayment> baseFindAllRP = feeRecieptPaymentService
											.findAllFeeRecieptPayment(rm
													.getReceiptNo());
									if (!baseFindAllRP.isError()
											|| baseFindAllRP.getMessage() != null) {
										return this
												.checkExistAndSaveExceptions(
														baseFindAllRP,
														eppWsLog, resutl,
														request);
									}
									List<FeeRecieptPayment> listExist = baseFindAllRP
											.getListModel();
									// Xoa di neu da ton tai
									if (listExist != null
											&& listExist.size() > 0) {
										BaseModelSingle<Boolean> baseDelRP = feeRecieptPaymentService
												.deleteObject(listExist);
										if (!baseDelRP.isError()
												|| baseDelRP.getMessage() != null) {
											return this
													.checkExistAndSaveException(
															baseDelRP,
															eppWsLog, resutl,
															request);
										}
									}

									// logger.info("start save receipt bill, code: "
									// + rb.getCode() + ", number: " +
									// rb.getNumber());
									FeeRecieptPayment fee = new FeeRecieptPayment();
									fee.setRecieptNo(fr.getRecieptNo());
									fee.setTypePayment(fr.getTypePayment());
									fee.setPrice(fr.getPrice());
									fee.setDescription(fr.getDescription());
									fee.setUnit(fr.getUnit());
									fee.setTotal(fr.getTotal());
									fee.setAmount(fr.getAmount());
									fee.setCreateBy(fr.getCreateBy());
									fee.setCreateDate(Calendar.getInstance()
											.getTime());
									fee.setCreateByName(fr.getCreateByName());
									BaseModelSingle<Boolean> baseSaveRP = feeRecieptPaymentService
											.saveOrUpdateNew(fee);
									if (!baseSaveRP.isError()
											|| baseSaveRP.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseSaveRP, eppWsLog, resutl,
												request);
									}
								}
							}
						}
					}
				} else {
					BaseModelSingle<EppListHandoverDetail> baseGetHD = eppListHandoverDetailService
							.getListPackageByPackageIdAndTranID(
									handoverA.getPackageId(),
									transaction.getTransactionId(),
									NicListHandover.TYPE_LIST_A);
					if (!baseGetHD.isError() || baseGetHD.getMessage() != null) {
						return this.checkExistAndSaveException(baseGetHD,
								eppWsLog, resutl, request);
					}
					EppListHandoverDetail tp = baseGetHD.getModel();

					if (tp == null) {
						tp = new EppListHandoverDetail();
						tp.setPackageId(handoverA.getPackageId());
						tp.setTransactionId(transaction.getTransactionId());
						tp.setTypeList(NicListHandover.TYPE_LIST_A);
						BaseModelSingle<Boolean> baseSaveHD = eppListHandoverDetailService
								.saveHandoverDetail(tp);
						if (!baseSaveHD.isError()
								|| baseSaveHD.getMessage() != null) {
							return this.checkExistAndSaveException(baseSaveHD,
									eppWsLog, resutl, request);
						}
						// Boolean success = baseSaveHD.getModel();
					}
				}
				/*
				 * String siteXl = ""; ConfigurationWorkflow cfw =
				 * configurationWorkflowService
				 * .findSiteRepositoryBySite(handoverA.getSiteCode(), new
				 * Date(), CONFIG_TYPE_XU_LY, true); if(cfw != null){ siteXl =
				 * cfw.getSiteIdTo(); }else{ SiteRepository site =
				 * siteService.getSiteRepoById(handoverA.getSiteCode()); if(site
				 * != null){ siteXl = site.getSiteGroups().getGroupId(); } }
				 * if(StringUtils.isNotEmpty(siteXl)){
				 * 
				 * }
				 */

				/*
				 * SyncQueueJob isExist =
				 * queueJobService.findQueueByObjectId(handoverA.getPackageId(),
				 * siteCode, "", Contants.QUEUE_OBJ_TYPE_DA); if(isExist ==
				 * null) this.addObjToQueueJob(handoverA.getPackageId(),
				 * Contants.QUEUE_OBJ_TYPE_DA, siteCode, null);
				 */
			}
			// ===============================================================================

			// Tạo danh sách B
			HandoverB handoverB = request.getMhandoverB();
			if (handoverB != null) {
				BaseModelSingle<NicListHandover> baseFindHan = listHandoverService
						.findByPackageId(new NicListHandoverId(handoverB
								.getPackageId(), NicListHandover.TYPE_LIST_B));
				if (!baseFindHan.isError() || baseFindHan.getMessage() != null) {
					return this.checkExistAndSaveException(baseFindHan,
							eppWsLog, resutl, request);
				}
				NicListHandover hanCheck = baseFindHan.getModel();
				String sitePA = "";
				if (hanCheck == null) {
					NicListHandover nicHandover = new NicListHandover();
					nicHandover.setId(new NicListHandoverId(handoverB
							.getPackageId(), NicListHandover.TYPE_LIST_B));
					// nicHandover.setPackageId(handoverB.getPackageId());
					nicHandover.setSiteCode(handoverB.getSiteCode());
					if (StringUtils.isNotEmpty(handoverB.getApproveDate())) {
						Date date = HelperClass.convertStringToDate(handoverB
								.getApproveDate(), handoverB.getApproveDate()
								.length());
						nicHandover.setApproveDate(date);
					}
					if (StringUtils.isNotEmpty(handoverB.getOfferDate())) {
						Date date = HelperClass.convertStringToDate(handoverB
								.getOfferDate(), handoverB.getOfferDate()
								.length());
						nicHandover.setProposalDate(date);
					}
					nicHandover.setApproveUser(handoverB.getApproveName());
					nicHandover.setProcessUsers(handoverB.getOfferUserName());
					nicHandover.setPackageName(Contants.CODE_PACKAGE_NAME_B);
					// nicHandover.setTypeList(NicListHandover.TYPE_LIST_B);
					nicHandover.setCreateDate(Calendar.getInstance().getTime());
					nicHandover.setCreateBy(Contants.CREATE_BY_SYSTEM);
					nicHandover.setHandoverStatus(1);
					// nicHandover.setStatusSyncXl(null);
					nicHandover.setCountTransaction(handoverB.getCount());
					BaseModelSingle<Void> baseSaveHan = listHandoverService
							.saveHandover(nicHandover);
					if (!baseSaveHan.isError()
							|| baseSaveHan.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveHan,
								eppWsLog, resutl, request);
					}
				}
				if (handoverB.getHandovers() != null) {
					for (DetailHandoverB detailB : handoverB.getHandovers()) {
						BaseModelSingle<EppListHandoverDetail> baseGetHD = eppListHandoverDetailService
								.getListPackageByPackageIdAndTranID(
										detailB.getPackageId(),
										detailB.getTransactionId(),
										NicListHandover.TYPE_LIST_B);
						if (!baseGetHD.isError()
								|| baseGetHD.getMessage() != null) {
							return this.checkExistAndSaveException(baseGetHD,
									eppWsLog, resutl, request);
						}
						EppListHandoverDetail tpCheck = baseGetHD.getModel();
						BaseModelSingle<NicUploadJob> baseFindUJ = uploadJobService
								.findUploadJobByTransId(detailB
										.getTransactionId());
						if (!baseFindUJ.isError()
								|| baseFindUJ.getMessage() != null) {
							return this.checkExistAndSaveException(baseFindUJ,
									eppWsLog, resutl, request);
						}
						NicUploadJob nicU = baseFindUJ.getModel();
						if (tpCheck == null) {
							EppListHandoverDetail tp = new EppListHandoverDetail();
							tp.setPackageId(detailB.getPackageId());
							tp.setTransactionId(detailB.getTransactionId());
							tp.setProposalStage(detailB.getOfferStage());
							tp.setApproveStage(detailB.getApproveStage());
							tp.setProposalNote(detailB.getNoteOffer());
							tp.setApproveNote(detailB.getNoteApprove());
							tp.setTypeList(NicListHandover.TYPE_LIST_B);
							BaseModelSingle<Boolean> baseSaveHD = eppListHandoverDetailService
									.saveHandoverDetail(tp);
							if (!baseSaveHD.isError()
									|| baseSaveHD.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseSaveHD, eppWsLog, resutl, request);
							}
							BaseModelSingle<EppPerson> baseFindPerson = eppPersonService
									.findByStringCode(nicU.getNicTransaction()
											.getPersonCode());
							if (!baseFindPerson.isError()
									|| baseFindPerson.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseFindPerson, eppWsLog, resutl,
										request);
							}
							EppPerson epp = baseFindPerson.getModel();
							if (detailB.getPersonStage().equals("KT")) {
								if (nicU != null) {
									nicU.getNicTransaction().setPersonCode(
											detailB.getPersonCode());
								}
								this.deletePerson(detailB.getTransactionId());
							} else if (detailB.getPersonStage().equals("KM")) {
								if (nicU != null) {
									nicU.getNicTransaction().setPersonCode(
											detailB.getPersonCode());
								}
								if (epp.getPersonCode().equals(
										detailB.getPersonCode())) {
									epp.setOrgPerson(detailB.getPersonCode());
								}
								BaseModelSingle<Boolean> baseSavePerson = eppPersonService
										.saveOrUpdateData(epp);
								if (!baseSavePerson.isError()
										|| baseSavePerson.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseSavePerson, eppWsLog, resutl,
											request);
								}
							}
							/*
							 * List<SiteGroups> groupList =
							 * siteService.findAll(); for(SiteGroups site :
							 * groupList){
							 * if(!site.getGroupId().equals(handoverB
							 * .getSiteCode())){
							 * this.addObjToQueueJob(String.valueOf
							 * (epp.getPersonId()),
							 * OBJ_TYPE_QUEUE_CREATE_PERSON, site.getGroupId(),
							 * null); } }
							 */
						}

						/* Update trạng thái duyệt vào hồ sơ */
						NicTransaction nicTran = nicTransactionService
								.findById(detailB.getTransactionId());
						if (nicTran != null) {
							sitePA = nicTran.getRegSiteCode();
							// nicTran.setLeaderOfficerId(handover.getApproveName());
							if (StringUtils
									.isNotBlank(detailB.getPersonStage())
									&& detailB.getPersonStage().length() == 2) {
								nicTran.setMatchedTypePerson(detailB
										.getPersonStage());
							}
							BaseModelSingle<Boolean> baseSaveTran = nicTransactionService
									.saveOrUpdateTransaction(nicTran);
							if (!baseSaveTran.isError()
									|| baseSaveTran.getMessage() != null) {
								return this
										.checkExistAndSaveException(
												baseSaveTran, eppWsLog, resutl,
												request);
							}
						}

						if (nicU != null) {
							if (detailB.getApproveStage() != null) {
								switch (detailB.getApproveStage()) {
								case "D":
									nicU.setInvestigationStatus("40");
									break;
								case "K":
									nicU.setInvestigationStatus("04");
									break;
								case "C":
									nicU.setInvestigationStatus("01");
									break;
								default:
									break;
								}
								BaseModelSingle<Boolean> baseSaveUJ = uploadJobService
										.saveOrUpdateService(nicU);
								if (!baseSaveUJ.isError()
										|| baseSaveUJ.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseSaveUJ, eppWsLog, resutl,
											request);
								}
							}
						}
						/*---end---*/
						/* Thêm chi tiết payment vào db */
						if (detailB.getPayments() != null) {
							for (PaymentDetail payment : detailB.getPayments()) {
								BaseModelSingle<NicTransactionPayment> baseFindTranP = nicTransactionPaymentDao
										.findGetPaymentByTransaction(payment
												.getTransactionId());
								if (!baseFindTranP.isError()
										|| baseFindTranP.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseFindTranP, eppWsLog, resutl,
											request);
								}
								NicTransactionPayment payments = baseFindTranP
										.getModel();
								if (payments == null)
									continue;
								BaseModelSingle<NicTransactionPaymentDetail> baseFindDP = paymentDetailService
										.findDetailPaymentByTransactionId(
												payments.getPaymentId(),
												payment.getTypePayment(),
												payment.getSubTypePayment());
								if (!baseFindDP.isError()
										|| baseFindDP.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseFindDP, eppWsLog, resutl,
											request);
								}
								NicTransactionPaymentDetail payDetail = baseFindDP
										.getModel();
								if (payDetail != null) {
									payDetail.setStatusFee(payment
											.getStatusFee().equals("Y") ? true
											: false);
									BaseModelSingle<Boolean> baseSavvePD = paymentDetailService
											.saveOrUpdatePaymentDetail(payDetail);
									if (!baseSavvePD.isError()
											|| baseSavvePD.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseSavvePD, eppWsLog, resutl,
												request);
									}
								} else {
									NicTransactionPaymentDetail pay = new NicTransactionPaymentDetail();
									pay.setPaymentId(payments.getPaymentId());
									pay.setTypePayment(payment.getTypePayment());
									pay.setSubTypePayment(payment
											.getSubTypePayment());
									pay.setPaymentAmount(payment
											.getPaymentAmount());
									pay.setNote(payment.getNamePayment());
									pay.setStatusFee(payment.getStatusFee()
											.equals("Y") ? true : false);
									pay.setCreateBy(Contants.CREATE_BY_SYSTEM);
									pay.setCreateDate(Calendar.getInstance()
											.getTime());
									BaseModelSingle<Boolean> baseSavvePD = paymentDetailService
											.saveOrUpdatePaymentDetail(pay);
									if (!baseSavvePD.isError()
											|| baseSavvePD.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseSavvePD, eppWsLog, resutl,
												request);
									}
								}
							}
						}
					}
				}

				/*
				 * Kiểm tra tính hợp lệ của danh sách sau phê duyệt, đưa vào
				 * hàng đợi chờ đồng bộ sang trung tâm cá thê hóa
				 */
				/*
				 * NicUploadJobDto checkConfig =
				 * nicTransactionService.listPackageIDConfig
				 * (handoverB.getPackageId(), new Date()); if(checkConfig !=
				 * null){ this.addObjToQueueJob(checkConfig.getPackageId(),
				 * CONFIG_TYPE_CA_THE_HOA, checkConfig.getRegSiteCode(), null);
				 * }else{ NicUploadJobDto checkDefault =
				 * nicTransactionService.listPackageIDDefault
				 * (handoverB.getPackageId()); if(checkDefault != null){
				 * this.addObjToQueueJob(checkDefault.getPackageId(),
				 * CONFIG_TYPE_CA_THE_HOA, checkDefault.getRegSiteCode(), null);
				 * } }
				 */
				/*---end---*/
				/*
				 * Thêm vào hàng đợi chờ đồng bộ về PA đối với hồ sơ tiếp nhận ở
				 * PA
				 */
				/*
				 * if(StringUtils.isNotEmpty(sitePA)){ Boolean test = true;
				 * List<SiteGroups> groupList = siteService.findAll();
				 * for(SiteGroups site : groupList){
				 * if(site.getGroupId().equals(sitePA)){ test = false; break; }
				 * } if(test){
				 * 
				 * } }
				 */
				/*
				 * SyncQueueJob isExist =
				 * queueJobService.findQueueByObjectId(handoverB.getPackageId(),
				 * siteCode, "", Contants.QUEUE_OBJ_TYPE_DB); if(isExist ==
				 * null) this.addObjToQueueJob(handoverB.getPackageId(),
				 * Contants.QUEUE_OBJ_TYPE_DB , siteCode, null);
				 */
			}
			// ============================================================================================

			// Tạo danh sách C
			UpdatePackageRequest listIssue = request.getMhandoverC();
			// Boolean WFDONE = false;
			if (listIssue != null
					&& StringUtils.isNotEmpty(listIssue.getPackageId())) {
				/* Thêm vào bảng danh sách handover */
				BaseModelSingle<NicListHandover> baseFindHan = listHandoverService
						.findByPackageId(new NicListHandoverId(listIssue
								.getPackageId(), NicListHandover.TYPE_LIST_C));
				if (!baseFindHan.isError() || baseFindHan.getMessage() != null) {
					return this.checkExistAndSaveException(baseFindHan,
							eppWsLog, resutl, request);
				}
				NicListHandover bHandover = baseFindHan.getModel();
				if (bHandover == null) {
					NicListHandover handoverC = new NicListHandover();
					handoverC.setId(new NicListHandoverId(listIssue
							.getPackageId(), NicListHandover.TYPE_LIST_C));
					// handoverC.setPackageId(listIssue.getPackageId());
					handoverC.setPackageName(Contants.CODE_PACKAGE_NAME_C);
					handoverC.setCountTransaction(listIssue.getAmount());
					// handoverC.setTypeList(NicListHandover.TYPE_LIST_C);
					handoverC.setHandoverStatus(0);
					;
					// handoverC.setStatusSyncXl(true);
					handoverC.setCreateDate(Calendar.getInstance().getTime());
					handoverC.setCreateBy(Contants.CREATE_BY_SYSTEM);
					BaseModelSingle<Void> baseSaveHan = listHandoverService
							.saveHandover(handoverC);
					if (!baseSaveHan.isError()
							|| baseSaveHan.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveHan,
								eppWsLog, resutl, request);
					}

				}
				BaseModelSingle<EppListHandoverDetail> baseGetHD = eppListHandoverDetailService
						.getListPackageByPackageIdAndTranID(
								listIssue.getPackageId(),
								transaction.getTransactionId(),
								NicListHandover.TYPE_LIST_C);
				if (!baseGetHD.isError() || baseGetHD.getMessage() != null) {
					return this.checkExistAndSaveException(baseGetHD, eppWsLog,
							resutl, request);
				}
				EppListHandoverDetail tp = baseGetHD.getModel();
				if (tp == null) {
					tp = new EppListHandoverDetail();
					tp.setPackageId(listIssue.getPackageId());
					tp.setTransactionId(transaction.getTransactionId());
					tp.setTypeList(NicListHandover.TYPE_LIST_C);
					BaseModelSingle<Boolean> baseSaveHD = eppListHandoverDetailService
							.saveHandoverDetail(tp);
					if (!baseSaveHD.isError()
							|| baseSaveHD.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveHD,
								eppWsLog, resutl, request);
					}
				}
				/*--end--*/

				/*
				 * if(WFDONE){ SyncQueueJob isExist =
				 * queueJobService.findQueueByObjectId(listIssue.getPackageId(),
				 * siteCode, "", Contants.QUEUE_OBJ_TYPE_DC); if(isExist ==
				 * null) this.addObjToQueueJob(listIssue.getPackageId(),
				 * Contants.QUEUE_OBJ_TYPE_DC, siteCode, null); }
				 */
			}

			// Tạo bản ghi hộ chiếu
			UpdatePassportModel passport = request.getMpassport();
			if (passport != null) {
				// Sinh bản ghi hộ chiếu nếu chưa có
				String trans = transaction.getTransactionId();
				BaseModelSingle<NicUploadJob> baseGetUJ = uploadJobService
						.getUploadJobByTransactionIdSinger(null, trans);
				if (!baseGetUJ.isError() || baseGetUJ.getMessage() != null) {
					return this.checkExistAndSaveException(baseGetUJ, eppWsLog,
							resutl, request);
				}
				NicUploadJob obj = baseGetUJ.getModel();
				siteCode = obj.getNicTransaction().getIssSiteCode();
				Boolean isCreate = true;// PersoService.CheckPersoRegister(trans,
										// obj.getTempPassportNo(), obj);
				if (isCreate) {
					Parameters para = parametersService.getParamDetails(
							"SYSTEM", "RECIEPT_NO_PARAM");
					if (para != null) {
						int number = Integer.parseInt(para.getParaShortValue());
						int numberN = number + 1;
						para.setParaShortValue("" + numberN);
						parametersService.saveOrUpdate(para);

						Calendar now = Calendar.getInstance();
						int year = now.get(Calendar.YEAR);
						int month = now.get(Calendar.MONTH) + 1; // Note: zero
																	// based!
						int day = now.get(Calendar.DAY_OF_MONTH);
						String date = "" + year + month + day;
						String receiptNo = String.format("%09d", number);
						obj.setReceiptNo(para.getParaLobValue() + date
								+ receiptNo);
						obj.setTempPassportNo(passport.getPassportNo());
						BaseModelSingle<Boolean> baseSaveUJ = uploadJobService
								.saveOrUpdateService(obj);
						if (!baseSaveUJ.isError()
								|| baseSaveUJ.getMessage() != null) {
							return this.checkExistAndSaveException(baseSaveUJ,
									eppWsLog, resutl, request);
						}
					}

					Date startTime = new Date();
					String transactionStatus = PersoService.JOB_STATE_PASSPORT_REGISTER
							+ PersoService.STAGE_COMPLETED;
					this.saveTransactionLog(trans,
							PersoService.JOB_STATE_PASSPORT_REGISTER,
							transactionStatus, startTime, new Date(), null, "",
							obj.getJobReprocessCount());
					BaseModelSingle<NicUploadJob> bGetUJ = uploadJobService
							.getUploadJobByTransactionIdSinger(null, trans);
					if (!bGetUJ.isError() || bGetUJ.getMessage() != null) {
						return this.checkExistAndSaveException(bGetUJ,
								eppWsLog, resutl, request);
					}
					obj = bGetUJ.getModel();
					Boolean result = true;
					if (obj.getPersoRegisterStatus() == null
							|| !obj.getPersoRegisterStatus().equals("02")) {
						BaseModelList<NicDocumentData> baseFindAllDoc = documentDataDao
								.findAllByTransactionId(obj.getTransactionId());
						if (!baseFindAllDoc.isError()
								|| baseFindAllDoc.getMessage() != null) {
							return this.checkExistAndSaveExceptions(
									baseFindAllDoc, eppWsLog, resutl, request);
						}
						List<NicDocumentData> nicdd_ = baseFindAllDoc
								.getListModel();
						if (!StringUtils.isBlank(obj.getTempPassportNo())
								&& nicdd_.isEmpty()) {
							// this.LDSPacketDocument(obj);
							String doi = DateUtil.parseDate(new Date(),
									DateUtil.FORMAT_DD_MM_YYYY);
							Date doE = DateUtils.addYears(new Date(), obj
									.getNicTransaction().getValidityPeriod());
							String doe = DateUtil.parseDate(doE,
									DateUtil.FORMAT_DD_MM_YYYY);
							BaseModelSingle<Boolean> baseSaveDocInfo = this
									.saveDocumentInfoNew(
											obj.getTransactionId(), doi, doe,
											obj.getTempPassportNo());
							if (!baseSaveDocInfo.isError()
									|| baseSaveDocInfo.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseSaveDocInfo, eppWsLog, resutl,
										request);
							}
							result = baseSaveDocInfo.getModel();
						}
					}

					if (result) {
						String statusPerso = PersoService.STATUS_COMPLETED;
						BaseModelSingle<NicUploadJob> baseUpdateJState = uploadJobService
								.updateJobState(obj.getWorkflowJobId(),
										statusPerso,
										PersoService.PERSO_REGISTER);
						if (!baseUpdateJState.isError()
								|| baseUpdateJState.getMessage() != null) {
							return this
									.checkExistAndSaveException(
											baseUpdateJState, eppWsLog, resutl,
											request);
						}
						BaseModelSingle<Void> baseUpdateJStatus = uploadJobService
								.updateJobStatus(obj.getWorkflowJobId(),
										transactionStatus);
						if (!baseUpdateJStatus.isError()
								|| baseUpdateJStatus.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseUpdateJStatus, eppWsLog, resutl,
									request);
						}
						this.saveTransactionLog(trans,
								PersoService.JOB_STATE_PERSO_REGISTER,
								transactionStatus, startTime, new Date(), null,
								"", obj.getJobReprocessCount());

						// Cập nhật hộ chiếu theo dữ liệu đẩy lên
						BaseModelSingle<NicDocumentData> baseFindDocData = documentDataService
								.findByDocNumber(passport.getPassportNo());
						if (!baseFindDocData.isError()
								|| baseFindDocData.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseFindDocData, eppWsLog, resutl, request);
						}
						NicDocumentData nicDoc = baseFindDocData.getModel();
						if (nicDoc != null) {
							nicDoc.setCancelBy(passport.getCancelBy());
							if (passport.getCancelDatetime() != null) {
								Date date = HelperClass.convertStringToDate(
										passport.getCancelDatetime(), passport
												.getCancelDatetime().length());
								nicDoc.setCancelDatetime(date);
							}
							nicDoc.setChipSerialNo(passport.getChipSerialNo());
							if (passport.getDateOfExpiry() != null) {
								Date date = HelperClass.convertStringToDate(
										passport.getDateOfExpiry(), passport
												.getDateOfExpiry().length());
								nicDoc.setDateOfExpiry(date);
							}
							if (passport.getDateOfIssue() != null) {
								Date date = HelperClass.convertStringToDate(
										passport.getDateOfIssue(), passport
												.getDateOfIssue().length());
								nicDoc.setDateOfIssue(date);
							}
							nicDoc.setIcaoLine1(passport.getIcaoLine1());
							nicDoc.setIcaoLine2(passport.getIcaoLine2());
							nicDoc.setIssueBy(passport.getIssueBy());
							if (passport.getIssueDatetime() != null) {
								Date date = HelperClass.convertStringToDate(
										passport.getIssueDatetime(), passport
												.getIssueDatetime().length());
								nicDoc.setIssueDatetime(date);
							}
							nicDoc.setPositionSigner(passport
									.getPositionSigner());
							nicDoc.setSigner(passport.getSigner());
							nicDoc.setPrintingSite(passport.getPrintingSite());
							nicDoc.setReceiveBy(passport.getReceiveBy());
							if (passport.getReceiveDatetime() != null) {
								Date date = HelperClass.convertStringToDate(
										passport.getReceiveDatetime(), passport
												.getReceiveDatetime().length());
								nicDoc.setReceiveDatetime(date);
							}
							nicDoc.setRejectBy(passport.getRejectBy());
							if (passport.getRejectDatetime() != null) {
								Date date = HelperClass.convertStringToDate(
										passport.getRejectDatetime(), passport
												.getRejectDatetime().length());
								nicDoc.setRejectDatetime(date);
							}
							nicDoc.setActiveFlag(null);
							nicDoc.setIssuedFlag(null);
							if (passport.getStatus().equals("ISSUANCE")) {
								nicDoc.setActiveFlag(true);
								nicDoc.setIssuedFlag(true);
							} else if (passport.getStatus().equals("NONE")) {
								nicDoc.setActiveFlag(false);
								nicDoc.setIssuedFlag(true);
							}
							nicDoc.setStatus(passport.getStatus());
							BaseModelSingle<Void> baseSOrUDocData = documentDataService
									.saveOrUpdateDocData(nicDoc);
							if (!baseSOrUDocData.isError()
									|| baseSOrUDocData.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseSOrUDocData, eppWsLog, resutl,
										request);
							}
							BaseModelSingle<NicTransaction> baseGetNicTranById = nicTransactionService
									.getNicTransactionById(passport
											.getTransactionId());
							if (!baseGetNicTranById.isError()
									|| baseGetNicTranById.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseGetNicTranById, eppWsLog, resutl,
										request);
							}
							// NicTransaction nicTran = baseGetNicTranById
							// .getModel();
							/*
							 * this.addObjToQueueJob(nicDoc.getId().getPassportNo
							 * (), Contants.QUEUE_OBJ_TYPE_UPDATE_PP,
							 * nicTran.getRegSiteCode(), "");
							 */
						}
					}
				}

				// Them ca nhan
				if (request.getOrgPerson() != null) {
					PersonModel person = request.getOrgPerson();

					BaseModelSingle<EppPerson> baseFindPersonByCode = eppPersonService
							.findByStringCode(person.getPersonCode());
					if (!baseFindPersonByCode.isError()
							|| baseFindPersonByCode.getMessage() != null) {
						return this
								.checkExistAndSaveException(
										baseFindPersonByCode, eppWsLog, resutl,
										request);
					}
					EppPerson epp = baseFindPersonByCode.getModel();

					if (epp == null)
						epp = new EppPerson();

					epp.setCreatedBy(person.getCreatedBy());
					if (person.getCreatedDate() != null) {
						Date date = HelperClass.convertStringToDate(person
								.getCreatedDate(), person.getCreatedDate()
								.length());
						epp.setCreateTs(date);
					}
					epp.setDateOfBirth(person.getDateOfBirth());
					epp.setDateOfIdIssue(person.getDateOfIdIssue());
					epp.setEthnic(person.getEthNic());
					epp.setEthnicCode(person.getEthnicCode());
					epp.setFatherName(person.getFatherName());
					epp.setFatherSearchName(person.getFatherSearchName());
					epp.setGender(person.getGender());
					epp.setIdNumber(person.getIdNumber());
					epp.setMotherName(person.getMotherName());
					epp.setMotherSearchName(person.getMotherSearchName());
					epp.setName(person.getName());
					epp.setNationalityCode(person.getNationalityCode());
					epp.setNationalityName(person.getNationalityName());
					epp.setPersonCode(person.getPersonCode());
					if (!epp.getPersonCode().equals(person.getPersonOrgCode())) {
						epp.setOrgPerson(person.getPersonOrgCode());
					}
					epp.setPlaceOfBirthCode(person.getPlaceOfBirthCode());
					epp.setPlaceOfBirthName(person.getPlaceOfBirthName());
					epp.setPlaceOfIdIssueName(person.getPlaceOfIdIssueName());
					if (StringUtils.isNotBlank(person.getRefId())) {
						epp.setRefId(person.getRefId());
					}
					epp.setSrcDoc(transaction.getTransactionId());
					epp.setReligion(person.getReligion());
					epp.setReligionCode(person.getReligionCode());
					epp.setSearchName(person.getSearchName());
					epp.setUpdatedBy(person.getUpdatedBy());
					if (person.getUpdatedDate() != null) {
						Date date = HelperClass.convertStringToDate(person
								.getUpdatedDate(), person.getUpdatedDate()
								.length());
						epp.setUpdateTs(date);
					}
					epp.setIsChecked((person.getIsChecked() != "" && person
							.getIsChecked().equals("Y")) ? true : false);
					epp.setDescription(person.getDescription());
					epp.setSrcOffice(person.getSrcOffice());
					epp.setStatus(person.getStatus());
					epp.setCreatedByName(person.getCreatedByName());
					epp.setUpdatedByName(person.getUpdatedByName());
					epp.setOtherName(person.getOtherName());
					epp.setCountryOfBirth(person.getCountryOfBirth());
					BaseModelSingle<Boolean> baseSOrUPerson = eppPersonService
							.saveOrUpdateData(epp);
					if (!baseSOrUPerson.isError()
							|| baseSOrUPerson.getMessage() != null) {
						this.checkExistAndSaveException(baseSOrUPerson,
								eppWsLog, resutl, request);
					}

					// Kiểm tra thông tin gia đình
					if (person.getFamilies() != null
							&& person.getFamilies().size() > 0) {
						for (PersonFamily f : person.getFamilies()) {
							EppPersonFamily fmy = new EppPersonFamily();
							fmy.setName(f.getName());
							fmy.setDateOfBirth(f.getDateOfBirth());
							fmy.setLost(f.getLost());
							fmy.setRelationship(f.getRelationship());
							fmy.setGender(f.getGender());
							fmy.setCreatedBy("SYSTEM");
							fmy.setCreateTs(new Date());
							BaseModelSingle<Boolean> baseSOrUDataF = eppPersonService
									.saveOrUpdateDataFamily(fmy);
							if (!baseSOrUDataF.isError()
									|| baseSOrUDataF.getMessage() != null) {
								this.checkExistAndSaveException(baseSOrUDataF,
										eppWsLog, resutl, request);
							}
						}
					}
					BaseModelSingle<NicUploadJob> baseFindUJ = uploadJobService
							.findUploadJobByTransId(request.getTransactionF()
									.getTransactionId());
					if (!baseFindUJ.isError()
							|| baseFindUJ.getMessage() != null) {
						return this.checkExistAndSaveException(baseFindUJ,
								eppWsLog, resutl, request);
					}
					NicUploadJob nicU = baseFindUJ.getModel();
					if (nicU != null) {
						NicTransaction nicTran = nicU.getNicTransaction();
						nicTran.setPersonCode(person.getPersonCode());
						BaseModelSingle<Boolean> baseSOrUTran = nicTransactionService
								.saveOrUpdateTransaction(nicTran);
						if (!baseSOrUTran.isError()
								|| baseSOrUTran.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseSOrUTran, eppWsLog, resutl, request);
						}
					}
				}
			}

			resutl.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			resutl.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			this.saveOrUpRptData(Contants.URL_UPLOAD_FULL_TRANSACTION, 1,
					request.getTransactionF().getRegSiteCode());

		} catch (Exception e) {
			e.printStackTrace();
			resutl.setCode(Contants.CODE_THROW_EXCEPTION);
			resutl.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error: " + e.getMessage());
			eppWsLog.setDataResponse(resutl.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e)
					+ " - uploadFullTransaction fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}

			return resutl;
		}

		return resutl;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadFullTransaction/{site}")
	public Response<FullTransactionDownloadModel> downloadFullTransaction(
			@PathParam("site") String site) {
		Response<FullTransactionDownloadModel> response = new Response<FullTransactionDownloadModel>();
		FullTransactionDownloadModel full = new FullTransactionDownloadModel();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "downloadPerson";
		// if(!this.checkConnectApi(nameApi, site)){
		// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return response;
		// }

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_DFHS);
		eppWsLog.setUrlRequest(Contants.URL_DOWNLOAD_FULL_TRANSACTION);
		eppWsLog.setDataRequest(site);
		eppWsLog.setKeyId(site + "_"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()));

		ResponseBase rb = new ResponseBase();

		try {
			BaseModelSingle<SyncQueueJob> baseFindQ = queueJobService
					.findQueueByStatus(site,
							Contants.CODE_STATUS_QUEUE_PENDING,
							Contants.QUEUE_OBJ_TYPE_HS_FULL);
			if (!baseFindQ.isError() || baseFindQ.getMessage() != null) {
				rb = this.checkExistAndSaveException(baseFindQ, eppWsLog, rb,
						new Object());
				response.setCode(rb.getCode());
				response.setMessage(rb.getMessage());
				return response;
			}
			SyncQueueJob queue = baseFindQ.getModel();
			if (queue != null) {
				BaseModelSingle<Transaction> baseGetTran = getTransaction(
						queue.getObjectId(), site, queue.getId(),
						queue.getTranStatus());
				if (!baseGetTran.isError() || baseGetTran.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseGetTran, eppWsLog,
							rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				Transaction transaction = baseGetTran.getModel();
				full.setTransactionF(transaction);

				BaseModelSingle<HandoverA> baseGetHanA = getHandoverA(
						transaction.getTransactionId(), site, queue.getId());
				if (!baseGetHanA.isError() || baseGetHanA.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseGetHanA, eppWsLog,
							rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				HandoverA hanA = baseGetHanA.getModel();
				full.setMhandoverA(hanA);

				BaseModelSingle<HandoverB> baseGetHanB = getHandoverB(
						transaction.getTransactionId(), site, queue.getId());
				if (!baseGetHanB.isError() || baseGetHanB.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseGetHanB, eppWsLog,
							rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				HandoverB hanB = baseGetHanB.getModel();
				full.setMhandoverB(hanB);

				BaseModelSingle<HandoverC> baseGetHanC = getHandoverC(
						transaction.getTransactionId(), site, queue.getId());
				if (!baseGetHanC.isError() || baseGetHanC.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseGetHanC, eppWsLog,
							rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				HandoverC hanC = baseGetHanC.getModel();
				full.setMhandoverDC(hanC);

				UpdatePassportModel pp = getDetailPassport(
						transaction.getTransactionId(), site, queue.getId());
				full.setMpassport(pp);
				BaseModelSingle<NicTransaction> baseGetTranById = nicTransactionService
						.getNicTransactionById(transaction.getTransactionId());
				if (!baseGetTranById.isError()
						|| baseGetTranById.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseGetTranById,
							eppWsLog, rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				NicTransaction nicTran = baseGetTranById.getModel();
				if (nicTran != null) {
					PersonModel ps = getPersonModel(nicTran.getPersonCode());
					full.setOrgPerson(ps);
				}

				BaseModelSingle<Boolean> baseUpdateSQJ = queueJobService
						.updateStatusQueueJob(queue.getId(),
								Contants.CODE_STATUS_QUEUE_DOING);
				if (!baseUpdateSQJ.isError()
						|| baseUpdateSQJ.getMessage() != null) {
					rb = this.checkExistAndSaveException(baseUpdateSQJ,
							eppWsLog, rb, new Object());
					response.setCode(rb.getCode());
					response.setMessage(rb.getMessage());
					return response;
				}
				response.setData(full);

				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

				/* Lưu bảng thống kê truyền nhận */
				if (full.getTransactionF().getTransactionId() != null
						&& queue.getDateUpdateStatusHanding() == null) {
					this.saveOrUpRptData(
							Contants.URL_DOWNLOAD_FULL_TRANSACTION, 1, site);
				}

			} else {
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}
		} catch (Exception e) {
			logger.error("error download person === " + e.getMessage());
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - downloadTransaction fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}

			return response;
		}
		return response;
	}

	/*
	 * @POST
	 * 
	 * @Produces("application/json")
	 * 
	 * @Consumes("application/json")
	 * 
	 * @Path("/uploadInfoApprove") public ResponseBase
	 * uploadInfoApprove(Handover handover){ ResponseBase response = new
	 * ResponseBase(); response.setCode(Contants.RESPONSE_CODE_FAIL_API);
	 * response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
	 * logger.info("start upload info approve to ttdh, time:" +
	 * HelperClass.convertDateToString1(new Date())); try { if(handover !=
	 * null){ //check validate input this.checkValidateHandover(handover);
	 * logger.info("start save handover approve."); NicListHandover nicHandover
	 * = new NicListHandover();
	 * nicHandover.setPackageId(handover.getPackageId());
	 * nicHandover.setSiteCode(handover.getSiteCode());
	 * //nicHandover.setApproveDate(handover.getApproveDate());
	 * if(StringUtils.isNotEmpty(handover.getApproveDate())){ Date date =
	 * HelperClass.convertStringToDate(handover.getApproveDate(),
	 * STR_FORMAT_DATE_yyyyMMddHHmmss); nicHandover.setApproveDate(date); }
	 * //nicHandover.setProposalDate(handover.getOfferDate());
	 * if(StringUtils.isNotEmpty(handover.getOfferDate())){ Date date =
	 * HelperClass.convertStringToDate(handover.getOfferDate(),
	 * STR_FORMAT_DATE_yyyyMMddHHmmss); nicHandover.setProposalDate(date); }
	 * nicHandover.setLeaderId(handover.getApproveName());
	 * nicHandover.setUserLeaderProcess(handover.getOfferName());
	 * nicHandover.setPackageName(Contants.CODE_PACKAGE_NAME_B);
	 * nicHandover.setTypeList(8);
	 * nicHandover.setUpdateDate(Calendar.getInstance().getTime());
	 * nicHandover.setUpdateBy(Contants.CREATE_BY_SYSTEM);
	 * nicHandover.setStatus(1); nicHandover.setStatusSyncXl(null);
	 * nicHandover.setCountTransaction(handover.getCount());
	 * listHandoverService.insertOrUpdateHandover(nicHandover);
	 * logger.info("start save detail handover approve."); for(DetailHandover
	 * detailA : handover.getDtHandover()){ NicTransactionPackage tp = new
	 * NicTransactionPackage(); tp.setPackageId(detailA.getPackageId());
	 * tp.setTransactionId(detailA.getTransactionId());
	 * tp.setOfferStage(detailA.getOfferStage());
	 * tp.setStage(detailA.getApproveStage());
	 * tp.setNoteApprove(detailA.getNoteOffer());
	 * tp.setNoteLeaderApprove(detailA.getNoteApprove()); tp.setTypeList(8);
	 * nicTransactionPackageService.saveTranPackage(tp);
	 * 
	 * //Thêm lãnh đạo duyệt vào chi tiết hồ sơ NicTransaction nicTran =
	 * nicTransactionService.findById(detailA.getTransactionId()); if(nicTran !=
	 * null){ nicTran.setLeaderOfficerId(handover.getApproveName());
	 * nicTransactionService.saveOrUpdate(nicTran); } }
	 * logger.info("start save detail payment approve."); for(PaymentDetail
	 * payment : handover.getPayments()){ NicTransactionPayment payments =
	 * nicTransactionPaymentDao
	 * .findGetPaymentByTransaction(payment.getTransactionId()); if(payments ==
	 * null) continue; NicTransactionPaymentDetail payDetail =
	 * paymentDetailService
	 * .findDetailPaymentByTransactionId(payments.getPaymentId(),
	 * payment.getSubTypePayment()); if(payDetail != null){
	 * payDetail.setStatusFee(payment.getStatusFee().equals("Y") ? true :
	 * false); paymentDetailService.saveOrUpdatePaymentDetail(payDetail); }else{
	 * NicTransactionPaymentDetail pay = new NicTransactionPaymentDetail();
	 * pay.setPaymentId(payments.getPaymentId());
	 * pay.setTypePayment(Contants.TYPE_PAYMENT_LP_A08);
	 * pay.setSubTypePayment(payment.getSubTypePayment());
	 * pay.setPaymentAmount(payment.getPaymentAmount());
	 * pay.setNote(payment.getNamePayment());
	 * pay.setStatusFee(payment.getStatusFee().equals("Y") ? true : false);
	 * pay.setCreateBy(Contants.CREATE_BY_SYSTEM);
	 * pay.setCreateDate(Calendar.getInstance().getTime());
	 * paymentDetailService.saveOrUpdatePaymentDetail(pay); } }
	 * response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
	 * response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
	 * logger.info("success upload handover approve in TTDH, date now:" +
	 * HelperClass.convertDateToString1(new Date()));
	 * 
	 * NicUploadJobDto checkConfig =
	 * nicTransactionService.listPackageIDConfig(handover.getPackageId(), new
	 * Date()); if(checkConfig != null){
	 * this.addObjToQueueJob(checkConfig.getPackageId(), CONFIG_TYPE_CA_THE_HOA,
	 * checkConfig.getRegSiteCode()); }else{ NicUploadJobDto checkDefault =
	 * nicTransactionService.listPackageIDDefault(handover.getPackageId());
	 * if(checkDefault != null){
	 * this.addObjToQueueJob(checkDefault.getPackageId(),
	 * CONFIG_TYPE_CA_THE_HOA, checkDefault.getRegSiteCode()); } }
	 * 
	 * } } catch (Exception e) { e.printStackTrace();
	 * response.setMessage(e.getMessage()); logger.error("error: " +
	 * e.getMessage()); } return response; }
	 */
	// Hoald close function because this dose not use
	// @POST
	// @Produces("application/json")
	// @Consumes("application/json")
	// @Path("/uploadTransactionLost")
	// public ResponseBase uploadTransactionLost(TransactionLost lost) {
	// ResponseBase response = new ResponseBase();
	// response.setCode(Contants.RESPONSE_CODE_FAIL_API);
	// response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
	// logger.info("start upload transaction lost to ttdh, time:"
	// + HelperClass.convertDateToString1(new Date()));
	// try {
	// if (lost != null) {
	// this.checkValidateLost(lost);
	// BaseModelSingle<NicDocumentData> baseFindDocData = documentDataService
	// .findByDocNumber(lost.getPassportNo());
	// NicDocumentData nicPassport = baseFindDocData.getModel();
	// if (nicPassport == null) {
	// logger.error("not found passport by passportNo: "
	// + lost.getPassportNo());
	// throw new Exception("not found passport by passportNo: "
	// + lost.getPassportNo());
	// }
	// nicPassport.setActiveFlag(false);
	// nicPassport.setStatus(Contants.STATUS_PASSPORT_NONE);
	// documentDataService.saveOrUpdate(nicPassport);
	//
	// // Lưu thông tin hồ sơ báo mất/hủy vào csdl ttdh
	// NicTransactionLost transactionLost = new NicTransactionLost();
	// transactionLost.setTransactionId(lost.getTransactionId());
	// transactionLost.setPassportNo(lost.getPassportNo());
	// transactionLost.setName(lost.getName());
	// transactionLost.setDob(lost.getDob());
	// transactionLost.setNin(lost.getNin());
	// transactionLost.setDateIssue(lost.getDateOfIssue());
	// transactionLost.setDateExrity(lost.getDateOfExpiry());
	// transactionLost.setPob(lost.getPob());
	// transactionLost.setPlaceIssue(lost.getPlaceIssue());
	// transactionLost.setSiteCode(lost.getSiteCode());
	// transactionLost.setReason(lost.getReason());
	// transactionLost.setReasonNote(lost.getNote());
	// transactionLostService.saveOrUpdateLost(transactionLost);
	// }
	// response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
	// response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
	//
	// /* Lưu bảng thống kê truyền nhận */
	// this.saveOrUpRptData(Contants.URL_UPLOAD_TRANSACTION_LOST, 1,
	// lost.getSiteCode());
	//
	// // logger.info("success upload transaction lost in TTDH, date now:"
	// // + HelperClass.convertDateToString1(new Date()));
	// } catch (Exception e) {
	// logger.error("error upload transaction lost:" + e.getMessage());
	// response.setMessage(e.getMessage());
	// }
	// return response;
	// }

	/*
	 * Kiểm tra kết nối từ đơn vị tới trung tâm.
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/logCheckConnection/{site}")
	public ResponseBase logCheckConnection(@PathParam("site") String site) {
		ResponseBase rb = new ResponseBase();
		rb.setCode(Contants.CODE_CONNECT_FAIL);
		rb.setMessage(Contants.MESS_CONNECT_FAIL);
		try {

			if (StringUtils.isNotBlank(site)) {
				String siteName = siteRepositoryDao.findBySiteId(site)
						.getSiteName();
				if (siteName == null) {
					rb.setCode(Contants.CODE_INPUT_FAILD);
					rb.setMessage(Contants.MESSAGE_SITENAME_NULL);
					return rb;
				}
				LogCheckConnection logC = logCheckConnectionService
						.findLogBySiteCode(site).getModel();
				if (logC == null) {
					logC = new LogCheckConnection();
					logC.setSiteCode(site);
					logC.setSiteName(siteName);
					logC.setCreateDatetime(new Date());
				}
				logC.setLastConnectedDatetime(new Date());
				logCheckConnectionService.saveOrUpdateLogCheckConnection(logC);

			} else {
				rb.setCode(Contants.CODE_INPUT_FAILD);
				rb.setMessage(Contants.MESSAGE_SITE_NULL);
				return rb;
			}

			rb.setCode(Contants.CODE_SUCCESS);
			rb.setMessage(Contants.MESSAGE_SUCCESS);

			/* Lưu bảng thống kê truyền nhận */
			// this.saveOrUpRptData(Contants.URL_LOG_CHECK_CONNECTION, 1, site);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return rb;
	}

	/*
	 * Gửi kết quả khớp cá nhân từ TTXL về TTĐH
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadJoinPerson")
	public ResponseBase uploadJoinPerson(InfoJoinPerson request) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// logger.info("start uploadJoinPerson to ttdh, time:" +
		// HelperClass.convertDateToString1(new Date()));

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_JPS);
		eppWsLog.setUrlRequest(Contants.URL_UPLOAD_JOIN_PERSON);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();

		}

		try {
			if (request != null) {
				eppWsLog.setKeyId(request.getTransactionId());
				/* Kiểm tra person */
				BaseModelSingle<NicUploadJob> baseFindUJByTran = uploadJobService
						.findUploadJobByTransId(request.getTransactionId());
				if (!baseFindUJByTran.isError()
						|| baseFindUJByTran.getMessage() != null) {
					return this.checkExistAndSaveException(baseFindUJByTran,
							eppWsLog, response, request);
				}
				NicUploadJob nicU = baseFindUJByTran.getModel();
				if (nicU != null) {
					nicU.setJobCurrentStage(NicUploadJob.JOB_CURRENT_STAGE_INVESTIGATION);
					nicU.setJobCurrentStatus(NicUploadJob.JOB_CURRENT_STAGE_INVESTIGATION_PROCESSING);
					nicU.setInvestigationOfficerId(request.getUserProcess());
					if (StringUtils.isNotEmpty(request.getDateProcess())) {
						Date date = HelperClass.convertStringToDate(request
								.getDateProcess(), request.getDateProcess()
								.length());
						nicU.setDateApprovePerson(date);
					}
					nicU.setNoteApprovePerson(request.getNote());
					nicU.setUpdateBy(Contants.CREATE_BY_SYSTEM);
					nicU.setUpdateDatetime(new Date());
					nicU.getNicTransaction().setPersonCode(
							request.getPersonCode());
					BaseModelSingle<Boolean> baseSaveUJ = uploadJobService
							.saveOrUpdateService(nicU);
					if (!baseSaveUJ.isError()
							|| baseSaveUJ.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveUJ,
								eppWsLog, response, request);
					}
					/*
					 * Lưu trạng thái khớp cá nhân.
					 */
					if (StringUtils.isNotBlank(request.getPersonStage())
							&& request.getPersonStage().length() == 2) {
						// BaseModelSingle<NicTransaction> baseFindTran =
						// nicTransactionService
						// .findTransactionById(request.getTransactionId());
						// if (!baseFindTran.isError()
						// || baseFindTran.getMessage() != null) {
						// return this.checkExistAndSaveException(
						// baseFindTran, eppWsLog, response, request);
						// }
						NicTransaction nicTran = nicU.getNicTransaction();
						if (nicTran != null) {
							nicTran.setPersonCode(request.getPersonCode());
							nicTran.setMatchedTypePerson(request
									.getPersonStage());
							nicTran.setTransactionStatus(NicTransaction.TRANSACTION_STATUS_INVESTIGATION_PROCESSING);
							BaseModelSingle<Boolean> baseSaveTran = nicTransactionService
									.saveOrUpdateTransaction(nicTran);
							if (!baseSaveTran.isError()
									|| baseSaveTran.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseSaveTran, eppWsLog, response,
										request);
							}
						}
					}

					// if (!request.getPersonStage().equals("KT")) {
					// BaseModelSingle<Boolean> baseSavePS = this
					// .savePersonByUploadTransaction(nicU,
					// request.getPersonCode());
					// if (!baseSavePS.isError()
					// || baseSavePS.getMessage() != null) {
					// return this.checkExistAndSaveException(baseSavePS,
					// eppWsLog, response, request);
					// }
					BaseModelSingle<EppPerson> baseFindPersonByTran = eppPersonService
							.findByStringCode(nicU.getNicTransaction()
									.getPersonCode());
					if (!baseFindPersonByTran.isError()
							|| baseFindPersonByTran.getMessage() != null) {
						return this.checkExistAndSaveException(
								baseFindPersonByTran, eppWsLog, response,
								request);
					}
					EppPerson epp = baseFindPersonByTran.getModel();
					if (request.getPersonStage().equals("KM")) {
						if (epp != null
								&& !epp.getPersonCode().equals(
										request.getPersonOrgCode())) {
							epp.setOrgPerson(request.getPersonOrgCode());
							BaseModelSingle<Boolean> baseSaveEppPerson = eppPersonService
									.saveOrUpdateData(epp);
							if (!baseSaveEppPerson.isError()
									|| baseSaveEppPerson.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseSaveEppPerson, eppWsLog, response,
										request);
							}
						}
					}
					// Hoald thêm: kiểm tra và lưu thông tin person nếu có.
					if (request.getPerson() != null) {
						PersonModel person = request.getPerson();
						epp = eppPersonService.findPersonByPersonCode(person
								.getPersonCode());
						if (epp == null) {
							epp = new EppPerson();
							epp.setCreatedBy(person.getCreatedBy());
							if (person.getCreatedDate() != null) {
								Date date = HelperClass.convertStringToDate(
										person.getCreatedDate(), person
												.getCreatedDate().length());
								epp.setCreateTs(date);
							}
							epp.setDateOfBirth(person.getDateOfBirth());
							epp.setDateOfIdIssue(person.getDateOfIdIssue());
							epp.setEthnic(person.getEthNic());
							epp.setEthnicCode(person.getEthnicCode());
							epp.setFatherName(person.getFatherName());
							epp.setFatherSearchName(person
									.getFatherSearchName());
							epp.setGender(person.getGender());
							epp.setIdNumber(person.getIdNumber());
							epp.setMotherName(person.getMotherName());
							epp.setMotherSearchName(person
									.getMotherSearchName());
							epp.setName(person.getName());
							epp.setNationalityCode(person.getNationalityCode());
							epp.setNationalityName(person.getNationalityName());
							epp.setPersonCode(person.getPersonCode());
							if (!person.getPersonCode().equals(
									person.getPersonOrgCode())) {
								epp.setOrgPerson(person.getPersonOrgCode());
							}
							epp.setPlaceOfBirthCode(person
									.getPlaceOfBirthCode());
							epp.setPlaceOfBirthName(person
									.getPlaceOfBirthName());
							epp.setPlaceOfIdIssueName(person
									.getPlaceOfIdIssueName());
							epp.setSrcDoc(request.getTransactionId());
							epp.setReligion(person.getReligion());
							epp.setReligionCode(person.getReligionCode());
							epp.setSearchName(person.getSearchName());
							epp.setUpdatedBy(person.getUpdatedBy());
							if (person.getUpdatedDate() != null) {
								Date date = HelperClass.convertStringToDate(
										person.getUpdatedDate(), person
												.getUpdatedDate().length());
								epp.setUpdateTs(date);
							}
							epp.setIsChecked((person.getIsChecked() != "" && person
									.getIsChecked().equals("Y")) ? true : false);
							epp.setDescription(person.getDescription());
							epp.setSrcOffice(person.getSrcOffice());
							epp.setStatus(person.getStatus());
							epp.setCreatedByName(person.getCreatedByName());
							epp.setUpdatedByName(person.getUpdatedByName());
							epp.setOtherName(person.getOtherName());
							epp.setCountryOfBirth(person.getCountryOfBirth());
							Long personId = eppPersonService.saveEppPerson(epp);
							// Kiểm tra thông tin gia đình
							if (person.getFamilies() != null
									&& person.getFamilies().size() > 0) {
								for (PersonFamily f : person.getFamilies()) {
									EppPersonFamily fmy = new EppPersonFamily();
									fmy.setName(f.getName());
									fmy.setDateOfBirth(f.getDateOfBirth());
									fmy.setLost(f.getLost());
									fmy.setRelationship(f.getRelationship());
									fmy.setGender(f.getGender());
									fmy.setSubjectPerson(personId);
									fmy.setCreatedBy(person.getCreatedBy());
									fmy.setCreateTs(new Date());
									BaseModelSingle<Boolean> baseSaveDataFamily = eppPersonService
											.saveOrUpdateDataFamily(fmy);
									if (!baseSaveDataFamily.isError()
											|| baseSaveDataFamily.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseSaveDataFamily, eppWsLog,
												response, request);
									}
								}
							}
						}
					}
					// }

					response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_UPLOAD_JOIN_PERSON, 1,
							null);
					// logger.info("success uploadJoinPerson to ttdh, time:" +
					// HelperClass.convertDateToString1(new Date()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - uploadJoinPerson: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - uploadJoinPerson fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/createHandoverB")
	public ResponseBase createHandoverB(HandoverB handover) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_CDSB);
		eppWsLog.setUrlRequest(Contants.URL_CREATE_HANDOVER_B);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(handover));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();

		}

		try {
			if (handover != null) {
				// String nameApi = "uploadHandoverB";
				// if(!this.checkConnectApi(nameApi, handover.getSiteCode())){
				// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
				// return response;
				// }

				// logger.info("start createHandoverB to ttdh, time:" +
				// HelperClass.convertDateToString1(new Date()));
				// if(StringUtils.isNotEmpty(handover.getPackageOldId())){
				// NicListHandover hanOldCheck =
				// listHandoverService.findByPackageId(handover.getPackageOldId());
				// //Đổi trạng thái danh sách cũ nếu có
				// if(hanOldCheck != null){
				// hanOldCheck.setHandoverStatus(0);
				// listHandoverService.insertOrUpdateHandover(hanOldCheck);
				// }
				// }
				eppWsLog.setKeyId(handover.getPackageId());
				BaseModelSingle<NicListHandover> baseFindHanById = listHandoverService
						.findByPackageId(new NicListHandoverId(handover
								.getPackageId(), NicListHandover.TYPE_LIST_B));
				if (!baseFindHanById.isError()
						|| baseFindHanById.getMessage() != null) {
					return this.checkExistAndSaveException(baseFindHanById,
							eppWsLog, response, handover);
				}
				NicListHandover hanCheck = baseFindHanById.getModel();
				String sitePA = "";
				if (hanCheck == null) {
					// logger.info("start createHandoverB, packageId: " +
					// handover.getPackageId());
					NicListHandover nicHandover = new NicListHandover();
					nicHandover.setId(new NicListHandoverId(handover
							.getPackageId(), NicListHandover.TYPE_LIST_B));
					nicHandover.setSiteCode(handover.getSiteCode());
					if (StringUtils.isNotEmpty(handover.getApproveDate())) {
						Date date = HelperClass.convertStringToDate(handover
								.getApproveDate(), handover.getApproveDate()
								.length());
						nicHandover.setApproveDate(date);
					}
					if (StringUtils.isNotEmpty(handover.getOfferDate())) {
						Date date = HelperClass.convertStringToDate(handover
								.getOfferDate(), handover.getOfferDate()
								.length());
						nicHandover.setProposalDate(date);
					}

					nicHandover.setApprovePosition(handover
							.getApprovePosition());
					nicHandover.setProposalName(handover.getProposalName());
					nicHandover.setCreatorName(handover.getCreatorName());
					nicHandover.setApproveName(handover.getApproveName());
					nicHandover.setProposalUser(handover.getOfferUserName());
					nicHandover.setPackageName(Contants.CODE_PACKAGE_NAME_B);
					nicHandover.setCreateDate(Calendar.getInstance().getTime());
					nicHandover.setCreateBy(Contants.CREATE_BY_SYSTEM);
					nicHandover.setHandoverStatus(1);

					nicHandover.setCountTransaction(handover.getCount());
					BaseModelSingle<Void> baseSaveHan = listHandoverService
							.saveHandover(nicHandover);
					if (!baseSaveHan.isError()
							|| baseFindHanById.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveHan,
								eppWsLog, response, handover);
					}
				}
				if (handover.getHandovers() != null) {
					for (DetailHandoverB detailB : handover.getHandovers()) {
						BaseModelSingle<EppListHandoverDetail> baseGetHanDetail = eppListHandoverDetailService
								.getListPackageByPackageIdAndTranID(
										detailB.getPackageId(),
										detailB.getTransactionId(),
										NicListHandover.TYPE_LIST_B);
						if (!baseGetHanDetail.isError()
								|| baseGetHanDetail.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseGetHanDetail, eppWsLog, response,
									handover);
						}
						EppListHandoverDetail tpCheck = baseGetHanDetail
								.getModel();
						BaseModelSingle<NicUploadJob> baseFindUJByTran = uploadJobService
								.findUploadJobByTransId(detailB
										.getTransactionId());
						if (!baseFindUJByTran.isError()
								|| baseFindUJByTran.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseFindUJByTran, eppWsLog, response,
									handover);
						}
						NicUploadJob nicU = baseFindUJByTran.getModel();
						if (tpCheck == null) {
							// logger.info("start upload detail handover B.");
							EppListHandoverDetail tp = new EppListHandoverDetail();
							tp.setPackageId(detailB.getPackageId());
							tp.setTransactionId(detailB.getTransactionId());
							tp.setApproveNote(detailB.getNoteApprove());
							tp.setApproveStage(detailB.getApproveStage());
							tp.setProposalNote(detailB.getNoteOffer());
							tp.setProposalStage(detailB.getOfferStage());
							tp.setTypeList(NicListHandover.TYPE_LIST_B);

							BaseModelSingle<Boolean> baseSaveHanDetail = eppListHandoverDetailService
									.saveHandoverDetail(tp);
							if (!baseSaveHanDetail.isError()
									|| baseSaveHanDetail.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseSaveHanDetail, eppWsLog, response,
										handover);
							}
							// BaseModelSingle<EppPerson> baseFindPersonByTranId
							// = eppPersonService
							// .findByTransactionId(detailB
							// .getTransactionId());
							// if (!baseFindPersonByTranId.isError() ||
							// baseFindPersonByTranId.getMessage() != null) {
							// return
							// this.checkExistAndSaveException(baseFindPersonByTranId,
							// eppWsLog, response, handover);
							// }
							// EppPerson epp =
							// baseFindPersonByTranId.getModel();
							// if (detailB.getPersonStage().equals("KT")) {
							// if (nicU != null) {
							// nicU.getNicTransaction().setPersonCode(
							// detailB.getPersonCode());
							// }
							// this.deletePerson(detailB.getTransactionId());
							// } else if (detailB.getPersonStage().equals("KM"))
							// {
							// if (nicU != null) {
							// nicU.getNicTransaction().setPersonCode(
							// detailB.getPersonCode());
							// }
							// epp.setOrgPerson(detailB.getPersonCode());
							// eppPersonService.saveOrUpdateData(epp);
							// }
							// List<SiteGroups> groupList =
							// siteService.findAll();
							// for (SiteGroups site : groupList) {
							// if (!site.getGroupId().equals(
							// handover.getSiteCode())) {
							// this.addObjToQueueJob(
							// String.valueOf(epp.getPersonId()),
							// OBJ_TYPE_QUEUE_CREATE_PERSON,
							// site.getGroupId(), null,
							// Contants.URL_CREATE_HANDOVER_B);
							// }
							// }
						}

						/* Update trạng thái duyệt vào hồ sơ */
						NicTransaction nicTran = nicTransactionService
								.findById(detailB.getTransactionId());
						if (nicTran != null) {
							sitePA = nicTran.getRegSiteCode();
							if (nicTran.getArchiveCode() == null) {
								nicTran.setArchiveCode(detailB.getArchiveCode());
							}
							BaseModelSingle<Boolean> baseSaveTran = nicTransactionService
									.saveOrUpdateTransaction(nicTran);
							if (!baseSaveTran.isError()
									|| baseSaveTran.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseSaveTran, eppWsLog, response,
										handover);
							}
						}

						if (nicU != null) {
							if (detailB.getApproveStage() != null) {
								switch (detailB.getApproveStage()) {
								case "D":
									nicU.setInvestigationStatus("40");
									break;
								case "K":
									nicU.setInvestigationStatus("04");
									break;
								case "C":
									nicU.setInvestigationStatus("01");
									break;
								default:
									break;
								}
								BaseModelSingle<Boolean> baseSaveUJ = uploadJobService
										.saveOrUpdateService(nicU);
								if (!baseSaveUJ.isError()
										|| baseSaveUJ.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseSaveUJ, eppWsLog, response,
											handover);
								}
							}
						}
						/*---end---*/
						/* Thêm chi tiết payment vào db */
						/*
						 * if(detailA.getPayments() != null){ for(PaymentDetail
						 * payment : detailA.getPayments()){
						 * NicTransactionPayment payments =
						 * nicTransactionPaymentDao
						 * .findGetPaymentByTransaction(payment
						 * .getTransactionId()); if(payments == null) continue;
						 * NicTransactionPaymentDetail payDetail =
						 * paymentDetailService
						 * .findDetailPaymentByTransactionId(
						 * payments.getPaymentId(),
						 * payment.getSubTypePayment()); if(payDetail != null){
						 * payDetail
						 * .setStatusFee(payment.getStatusFee().equals("Y") ?
						 * true : false);
						 * paymentDetailService.saveOrUpdatePaymentDetail
						 * (payDetail); }else{ NicTransactionPaymentDetail pay =
						 * new NicTransactionPaymentDetail();
						 * pay.setPaymentId(payments.getPaymentId());
						 * pay.setTypePayment(payment.getTypePayment());
						 * pay.setSubTypePayment(payment.getSubTypePayment());
						 * pay.setPaymentAmount(payment.getPaymentAmount());
						 * pay.setNote(payment.getNamePayment());
						 * pay.setStatusFee(payment.getStatusFee().equals("Y") ?
						 * true : false);
						 * pay.setCreateBy(Contants.CREATE_BY_SYSTEM);
						 * pay.setCreateDate(Calendar.getInstance().getTime());
						 * paymentDetailService.saveOrUpdatePaymentDetail(pay);
						 * } } }
						 */
					}
				}
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				// logger.info("success upload handoverB in TTDH, date now:"
				// + HelperClass.convertDateToString1(new Date()));
				/*
				 * Kiểm tra tính hợp lệ của danh sách sau phê duyệt, đưa vào
				 * hàng đợi chờ đồng bộ sang trung tâm cá thê hóa
				 */
				BaseModelSingle<NicUploadJobDto> baseListPackageIdConfig = nicTransactionService
						.listPackageIDConfig(handover.getPackageId(),
								new Date());
				if (!baseListPackageIdConfig.isError()
						|| baseListPackageIdConfig.getMessage() != null) {
					return this.checkExistAndSaveException(
							baseListPackageIdConfig, eppWsLog, response,
							handover);
				}
				NicUploadJobDto checkConfig = baseListPackageIdConfig
						.getModel();
				BaseModelSingle<Boolean> baseAddObjToJob = new BaseModelSingle<Boolean>();
				if (checkConfig != null) {
					baseAddObjToJob = this.addObjToQueueJob(
							checkConfig.getPackageId(), CONFIG_TYPE_CA_THE_HOA,
							checkConfig.getRegSiteCode(), null,
							Contants.URL_CREATE_HANDOVER_B);
				} else {
					NicUploadJobDto checkDefault = nicTransactionService
							.listPackageIDDefault(handover.getPackageId(),
									NicListHandover.TYPE_LIST_B);
					if (checkDefault != null) {
						baseAddObjToJob = this.addObjToQueueJob(
								checkDefault.getPackageId(),
								CONFIG_TYPE_CA_THE_HOA,
								checkDefault.getRegSiteCode(), null,
								Contants.URL_CREATE_HANDOVER_B);
					}
				}
				if (!baseAddObjToJob.isError()
						|| baseAddObjToJob.getMessage() != null) {
					return this.checkExistAndSaveException(baseAddObjToJob,
							eppWsLog, response, handover);
				}
				/*---end---*/
				/*
				 * Thêm vào hàng đợi chờ đồng bộ về PA đối với hồ sơ tiếp nhận ở
				 * PA
				 */
				if (StringUtils.isNotEmpty(sitePA)) {
					Boolean test = true;
					List<SiteGroups> groupList = siteService
							.findAllSiteGroubs();
					for (SiteGroups site : groupList) {
						if (site.getGroupId().equals(sitePA)) {
							test = false;
							break;
						}
					}
					if (test) {
						baseAddObjToJob = this.addObjToQueueJob(
								handover.getPackageId(),
								Contants.QUEUE_OBJ_TYPE_DB, sitePA, null,
								Contants.URL_CREATE_HANDOVER_B);
						if (!baseAddObjToJob.isError()
								|| baseAddObjToJob.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseAddObjToJob, eppWsLog, response,
									handover);
						}
					}
				}

				/*---end---*/
			} else {
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}
		} catch (Exception e) {
			logger.error("error create handover B:" + e.getMessage());
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - createHandoverB fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		}
		return response;
	}

	/*
	 * Lấy số hồ sơ lưu.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getArchiveCodeNumber")
	public Response<String> getArchiveCodeNumber(ArchiveCode archiveCode) {
		Response<String> rep = new Response<String>();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_GACN);
		eppWsLog.setUrlRequest(Contants.URL_GET_ARCHIVE_CODE_NUMBER);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(archiveCode));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + "convert object to json fail.");
			e1.printStackTrace();

		}
		ResponseBase rb = null;
		try {
			if (archiveCode != null) {
				if (StringUtils.isNotBlank(archiveCode.getArchiveCode())) {
					rb = this.validateArchiveCode(archiveCode.getArchiveCode());
					if (rb != null) {
						rep.setData("0");
						rep.setCode(rb.getCode());
						rep.setMessage(rb.getMessage());
						return rep;
					} else {
						rb = new ResponseBase();
					}
					eppWsLog.setKeyId(archiveCode.getArchiveCode()
							+ "_"
							+ new SimpleDateFormat("ddMMyyyy")
									.format(new Date()));
					BaseModelSingle<EppArchiveCode> baseFindArchiveCode = this
							.findArchive(archiveCode.getArchiveCode());
					if (!baseFindArchiveCode.isError()
							|| baseFindArchiveCode.getMessage() != null) {
						rb = this.checkExistAndSaveException(
								baseFindArchiveCode, eppWsLog, rb, archiveCode);
						rep.setData("0");
						rep.setCode(rb.getCode());
						rep.setMessage(rb.getMessage());
						return rep;
					}
					EppArchiveCode arc = baseFindArchiveCode.getModel();
					if (arc != null) {
						rep.setData(String.valueOf(arc.getCount()));
					} else {
						rep.setData("0");
					}

					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_ARCHIVE_CODE_NUMBER,
							1, null);

				} else {
					rep.setData("0");
					rep.setCode(Contants.CODE_INPUT_FAILD);
					rep.setMessage("archiveCode" + Contants.MESSAGE_INPUT_NULL);
					return rep;
				}
				rep.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
			} else {
				rep.setData("0");
				rep.setCode(Contants.CODE_INPUT_FAILD);
				rep.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			rep.setCode(Contants.CODE_THROW_EXCEPTION);
			rep.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(rep.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e)
					+ " - getArchiveCodeNumber fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rep;
	}

	/*
	 * Tìm kiếm danh sách hồ sơ hộ chiếu để hủy.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findPassportCancel")
	public Response<List<PassportLostOutput>> findPassportCancel(
			PassportLostInput tranLostInput) {
		Response<List<PassportLostOutput>> rep = new Response<List<PassportLostOutput>>();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_FPC);
		eppWsLog.setUrlRequest(Contants.URL_FIND_PASSPORT_CANCEL);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(tranLostInput));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + "convert object to json fail.");
			e1.printStackTrace();

		}
		eppWsLog.setKeyId("FPC_"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()));

		List<PassportLostOutput> listPassportLostOutput = null;
		PassportLostOutput pLostOut = null;
		ResponseBase rb = new ResponseBase();
		try {
			if (tranLostInput != null) {
				if (StringUtils.isNotBlank(tranLostInput.getPassportNo())
						|| StringUtils.isNotBlank(tranLostInput
								.getArchiveCode())) {
					List<NicDocumentData> listPassportLost = null;
					if (StringUtils.isNotBlank(tranLostInput.getPassportNo())) {
						BaseModelList<NicDocumentData> baseFindPassportLost = documentDataService
								.findAllPassportByCondition(
										tranLostInput.getPassportNo(), "");
						if (!baseFindPassportLost.isError()
								|| baseFindPassportLost.getMessage() != null) {
							rb = this.checkExistAndSaveExceptions(
									baseFindPassportLost, eppWsLog, rb,
									tranLostInput);
							rep.setCode(rb.getCode());
							rep.setMessage(rb.getMessage());
							return rep;
						}
						listPassportLost = baseFindPassportLost.getListModel();
					}
					if (StringUtils.isNotBlank(tranLostInput.getArchiveCode())) {
						List<NicTransaction> listNicTran = nicTransactionService
								.findTranByArchiveCode(tranLostInput
										.getArchiveCode());
						if (listNicTran != null && listNicTran.size() > 0) {
							if (listPassportLost == null) {
								listPassportLost = new ArrayList<NicDocumentData>();
							}
							for (NicTransaction nicTran : listNicTran) {
								NicDocumentData nicDoc = documentDataService
										.findDocDataByTranId(nicTran
												.getTransactionId());
								if (nicDoc != null
										&& (nicDoc.getStatus().equals(
												Contants.DOC_STATUS_ISSUANCE) || nicDoc
												.getStatus()
												.equals(Contants.DOC_STATUS_PERSONALIZED))) {
									listPassportLost.add(nicDoc);
								}
							}
						}
					}
					if (listPassportLost != null && listPassportLost.size() > 0) {
						listPassportLostOutput = new ArrayList<PassportLostOutput>();
						for (NicDocumentData nicDocumentData : listPassportLost) {
							if (!nicDocumentData.getStatus().equals(
									Contants.DOC_STATUS_ISSUANCE)
									&& !nicDocumentData.getStatus().equals(
											Contants.DOC_STATUS_PERSONALIZED)) {
								continue;
							}
							NicTransaction nicTran = nicDocumentData
									.getNicTransaction();
							if (nicTran != null) {
								boolean check = true;
								if (StringUtils.isNotBlank(tranLostInput
										.getArchiveCode())
										&& !tranLostInput.getArchiveCode()
												.equals(nicTran
														.getArchiveCode())) {
									check = false;
								}
								if (check) {
									pLostOut = new PassportLostOutput();
									String nationality = "";
									String fullName = "";
									String gender = "";
									String dateOfBirth = "";
									String officeName = "";
									NicRegistrationData regisData = nicTran
											.getNicRegistrationData();
									pLostOut.setPlaceOfIssueCode(nicDocumentData
											.getPlaceOfIssueCode());
									if (regisData != null) {
										fullName = HelperClass.createFullName(
												regisData.getSurnameFull(),
												regisData.getMiddlenameFull(),
												regisData.getFirstnameFull());
										gender = regisData.getGender();
										if (regisData.getDateOfBirth() != null) {
											dateOfBirth = DateUtil.parseDate(
													regisData.getDateOfBirth(),
													DateUtil.FORMAT_YYYYMMDD);
										}
										pLostOut.setPlaceOfBirthCode(regisData
												.getPlaceOfBirth());
										if (StringUtils.isNotBlank(regisData
												.getPlaceOfBirth())) {
											CodeValues code = codesService
													.findByStringCodeId(
															CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
															regisData
																	.getPlaceOfBirth());
											if (code != null) {
												pLostOut.setPlaceOfBirth(code
														.getCodeValueDesc());
											} else {
												pLostOut.setPlaceOfBirth(regisData
														.getPlaceOfBirth());
											}
										}
										pLostOut.setNationalityCode(regisData
												.getNationality());
										try {
											nationality = codesService
													.findByStringCodeId(
															CodeValues.CODE_ID_COUNTRY,
															regisData
																	.getNationality())
													.getCodeValueDesc();
										} catch (Exception e) {
											nationality = regisData
													.getNationality();
										}
									} else {
										BaseModelSingle<EppPerson> baseFindPersonByTran = eppPersonService
												.findByStringCode(nicTran
														.getPersonCode());
										if (!baseFindPersonByTran.isError()
												|| baseFindPersonByTran
														.getMessage() != null) {
											rb = this
													.checkExistAndSaveException(
															baseFindPersonByTran,
															eppWsLog, rb,
															tranLostInput);
											rep.setCode(rb.getCode());
											rep.setMessage(rb.getMessage());
											return rep;
										}
										EppPerson person = baseFindPersonByTran
												.getModel();
										if (person != null
												&& person.getPersonCode() != null) {
											fullName = person.getName();
											gender = person.getGender();
											dateOfBirth = person
													.getDateOfBirth();
											pLostOut.setNationalityCode(person
													.getNationalityCode());
											try {
												nationality = codesService
														.findByStringCodeId(
																CodeValues.CODE_ID_COUNTRY,
																person.getNationalityCode())
														.getCodeValueDesc();
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}
									try {
										officeName = siteService
												.getSiteRepoById(
														nicDocumentData
																.getPlaceOfIssueCode())
												.getSiteName();
									} catch (Exception e) {
										officeName = nicDocumentData
												.getPlaceOfIssueCode();
									}
									pLostOut.setName(fullName);
									pLostOut.setGender(gender);
									pLostOut.setDateOfBirth(dateOfBirth);
									pLostOut.setNationality(nationality);
									pLostOut.setPassportNo(nicDocumentData
											.getId().getPassportNo());
									pLostOut.setOfficeName(officeName);
									if (nicDocumentData.getDateOfIssue() != null) {
										pLostOut.setDateOfIssue(DateUtil.parseDate(
												nicDocumentData
														.getDateOfIssue(),
												DateUtil.FORMAT_YYYYMMDD));
									}
									if (nicDocumentData.getDateOfExpiry() != null) {
										pLostOut.setDateOfExpiry(DateUtil.parseDate(
												nicDocumentData
														.getDateOfExpiry(),
												DateUtil.FORMAT_YYYYMMDD));
									}
									pLostOut.setTransactionId(nicDocumentData
											.getId().getTransactionId());
									listPassportLostOutput.add(pLostOut);
								}
							}
						}
					}
					if (listPassportLostOutput != null
							&& listPassportLostOutput.size() > 0) {
						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(Contants.URL_FIND_PASSPORT_CANCEL,
								1, null);
						rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					} else {
						rep.setMessage(Contants.STRING_STATUS_CODE_HTTP_NOT_FOUND);
					}
					rep.setData(listPassportLostOutput);
					rep.setCode(Contants.RESPONSE_CODE_SUCCESS_API);

				} else {
					rep.setCode(Contants.CODE_INPUT_FAILD);
					rep.setMessage(Contants.MESSAGE_INPUT_NULL);
				}
			} else {
				rep.setCode(Contants.CODE_INPUT_FAILD);
				rep.setMessage("passportNo or archiveCode"
						+ Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			rep.setCode(Contants.CODE_THROW_EXCEPTION);
			rep.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(rep.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - findPassportCancel fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rep;
	}

	/*
	 * Hủy hộ chiếu.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/cancelPassport")
	public ResponseBase cancelPassport(PassportCancelInput passportCancel) {
		ResponseBase rb = new ResponseBase();
		rb.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rb.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_CP);
		eppWsLog.setUrlRequest(Contants.URL_CANCEL_PASSPORT);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(passportCancel));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + "convert object to json fail.");
			e1.printStackTrace();

		}
		eppWsLog.setKeyId("CP_"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()));
		String cancelledPassport = "";
		try {
			if (passportCancel != null
					&& passportCancel.getCancelDocuments() != null
					&& passportCancel.getCancelDocuments().size() > 0) {
				for (PassportCancelDetail passportCancelDetail : passportCancel
						.getCancelDocuments()) {
					passportCancel.setName(passportCancelDetail.getName());
					passportCancel.setGender(passportCancelDetail.getGender());
					passportCancel.setDateOfBirth(passportCancelDetail
							.getDateOfBirth());
					passportCancel.setPassportNo(passportCancelDetail
							.getPassportNo());
					passportCancel.setDateOfDocIssue(passportCancelDetail
							.getDateOfDocIssue());
					passportCancel.setDateOfDocExpiry(passportCancelDetail
							.getDateOfDocExpiry());
					passportCancel.setTransactionId(passportCancelDetail
							.getTransactionId());
					passportCancel.setPlaceOfBirthCode(passportCancelDetail
							.getPlaceOfBirth());

					rb = this.methodCancelPassport(passportCancel, rb);
					if (rb.getCode().equals(Contants.CODE_DATA_EXIST)) {
						cancelledPassport += rb.getMessage();
						continue;
					}
					if (!rb.getCode().equals(Contants.CODE_SUCCESS)) {
						break;
					}
				}
			} else {
				rb = this.methodCancelPassport(passportCancel, rb);
				if (rb.getCode().equals(Contants.CODE_DATA_EXIST)) {
					cancelledPassport += rb.getMessage();
				}
			}
			if (cancelledPassport.length() > 1) {
				rb.setCode(Contants.CODE_DATA_EXIST);
				rb.setMessage("Hộ chiếu đã hủy: " + cancelledPassport);
			}

		} catch (Exception e) {
			rb.setCode(Contants.CODE_THROW_EXCEPTION);
			rb.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(rb.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - cancelPassport fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rb;
	}

	/*
	 * Thanh toán lệ phí - thêm phiếu thu chi.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updatePaymentFee")
	public ResponseBase updatePaymentFee(PaymentFeeInput paymentFeeInput) {
		ResponseBase rb = new ResponseBase();
		rb.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rb.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_UPF);
		eppWsLog.setUrlRequest(Contants.URL_UPDATE_PAYMENT_FEE);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(paymentFeeInput));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + "convert object to json fail.");
			e1.printStackTrace();

		}
		eppWsLog.setKeyId("UPF_"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()));
		try {
			if (paymentFeeInput != null) {
				DetailRecieptFee detailRecieptFee = new DetailRecieptFee();
				detailRecieptFee.setRecieptNo(paymentFeeInput.getReceiptNo());
				// paymentFeeInput.getOfficeId()
				detailRecieptFee.setDescription(paymentFeeInput
						.getDescription());
				detailRecieptFee.setCustomerName(paymentFeeInput.getCustName());
				// paymentFeeInput.getIsChared()
				detailRecieptFee.setNumberBill(paymentFeeInput.getInvoiceNo());
				detailRecieptFee
						.setCodeBill(paymentFeeInput.getInvoiceSeries());
				detailRecieptFee.setPrice(paymentFeeInput.getAmount());
				// paymentFeeInput.getUnit()
				if (paymentFeeInput.getDocumentType().equals("T")
						|| paymentFeeInput.getDocumentType().equals("C")) {
					detailRecieptFee.setPriceFlag(paymentFeeInput
							.getDocumentType());
				}
				detailRecieptFee.setCashierName(paymentFeeInput
						.getCashierName());
				detailRecieptFee.setCreateBy(paymentFeeInput.getCreatedBy());
				detailRecieptFee.setCreateByName(paymentFeeInput
						.getCreatedName());
				detailRecieptFee.setCreateDate(new Date());
				if (StringUtils.isNotBlank(paymentFeeInput.getDateOfReceipt())
						&& paymentFeeInput.getDateOfReceipt().length() == 8) {
					Date date = DateUtil.strToDate(
							paymentFeeInput.getDateOfReceipt(),
							DateUtil.FORMAT_YYMMDD);
					detailRecieptFee.setDateOfReceipt(date);
				}
				BaseModelSingle<Boolean> baseSaveDetailRFee = drFeeService
						.saveOrUpdateNew(detailRecieptFee);
				if (!baseSaveDetailRFee.isError()
						|| baseSaveDetailRFee.getMessage() != null) {
					return this.checkExistAndSaveException(baseSaveDetailRFee,
							eppWsLog, rb, paymentFeeInput);
				}
				if (baseSaveDetailRFee.getModel()) {
					rb.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					rb.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_UPDATE_PAYMENT_FEE, 1,
							paymentFeeInput.getOfficeId());
				}
			} else {
				rb.setCode(Contants.CODE_INPUT_FAILD);
				rb.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			rb.setCode(Contants.CODE_THROW_EXCEPTION);
			rb.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(rb.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - updatePaymentFee fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rb;
	}

	/*
	 * Tìm kiếm hồ sơ cần tách nhân thân.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getPassportDocument")
	public Response<SeparatePersonalData> getPassportDocument(
			TransactionIdInput tranId) {
		Response<SeparatePersonalData> rep = new Response<SeparatePersonalData>();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_GPD);
		eppWsLog.setUrlRequest(Contants.URL_GET_PASSPORT_DOCUMENT);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(tranId));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + "convert object to json fail.");
			e1.printStackTrace();

		}
		eppWsLog.setKeyId("GPD_"
				+ new SimpleDateFormat("ddMMyyyy").format(new Date()));
		ResponseBase rb = new ResponseBase();
		SeparatePersonalData spData = null;
		try {
			if (tranId != null) {
				if (StringUtils.isNotBlank(tranId.getTransactionId())) {
					PersonOutput personOutput = new PersonOutput();
					OrgPersonOutput orgPersonOutput = new OrgPersonOutput();
					BaseModelSingle<NicTransaction> baseNicTran = nicTransactionService
							.findTransactionById(tranId.getTransactionId());
					if (!baseNicTran.isError()
							|| baseNicTran.getMessage() != null) {
						throw new Exception(baseNicTran.getMessage());
					}
					NicTransaction nicTran = baseNicTran.getModel();
					BaseModelSingle<EppPerson> baseFindPersonByTranId = eppPersonService
							.findByStringCode(nicTran != null ? nicTran
									.getPersonCode() : null);
					if (!baseFindPersonByTranId.isError()
							|| baseFindPersonByTranId.getMessage() != null) {
						rb = this.checkExistAndSaveException(
								baseFindPersonByTranId, eppWsLog, rb, tranId);
						rep.setCode(rb.getCode());
						rep.setMessage(rb.getMessage());
						return rep;
					}
					EppPerson person = baseFindPersonByTranId.getModel();
					if (person != null) {
						spData = new SeparatePersonalData();
						BaseModelList<NicTransactionAttachment> baseFindTranAttach = nicTransactionAttachmentService
								.findNicTransactionAttachments(
										person.getSrcDoc(),
										Contants.DOC_TYPE_PHOTO_CAPTURE, "01");
						if (!baseFindTranAttach.isError()
								|| baseFindTranAttach.getMessage() != null) {
							rb = this.checkExistAndSaveExceptions(
									baseFindTranAttach, eppWsLog, rb, tranId);
							rep.setCode(rb.getCode());
							rep.setMessage(rb.getMessage());
							return rep;
						}
						List<NicTransactionAttachment> listTranAttach = baseFindTranAttach
								.getListModel();
						NicTransactionAttachment tranAttach = null;
						if (listTranAttach != null && listTranAttach.size() > 0) {
							tranAttach = listTranAttach.get(0);
						}

						personOutput.setTransactionId(person.getSrcDoc());
						personOutput.setName(person.getName());
						personOutput.setGender(person.getGender());
						personOutput.setDateOfBirth(person.getDateOfBirth());
						personOutput.setPlaceOfBirth(person
								.getPlaceOfBirthName());
						personOutput.setIdNumber(person.getIdNumber());
						personOutput.setDateOfIssue(person.getDateOfIdIssue());
						personOutput.setPlaceOfIssue(person
								.getPlaceOfIdIssueName());
						if (tranAttach != null) {
							personOutput.setBase64(tranAttach.getDocData()
									.toString());
						}
						personOutput.setFather(person.getFatherName());
						personOutput.setMother(person.getMotherName());
						personOutput.setPersonCode(person.getPersonCode());
						// tạm để null do chưa rõ.
						personOutput.setMatchType(null);
						spData.setDocument(personOutput);
						if (StringUtils.isNotBlank(person.getOrgPerson())) {
							BaseModelSingle<EppPerson> baseFindOrgPersonByTranId = eppPersonService
									.findByStringCode(nicTran.getPersonCode());
							if (!baseFindOrgPersonByTranId.isError()
									|| baseFindOrgPersonByTranId.getMessage() != null) {
								rb = this.checkExistAndSaveException(
										baseFindOrgPersonByTranId, eppWsLog,
										rb, tranId);
								rep.setCode(rb.getCode());
								rep.setMessage(rb.getMessage());
								return rep;
							}
							EppPerson orgPerson = baseFindOrgPersonByTranId
									.getModel();
							if (orgPerson != null) {
								BaseModelList<NicTransactionAttachment> baseFindOrgTranAttach = nicTransactionAttachmentService
										.findNicTransactionAttachments(
												orgPerson.getSrcDoc(),
												Contants.DOC_TYPE_PHOTO_CAPTURE,
												"01");
								if (!baseFindOrgTranAttach.isError()
										|| baseFindOrgTranAttach.getMessage() != null) {
									rb = this.checkExistAndSaveExceptions(
											baseFindOrgTranAttach, eppWsLog,
											rb, tranId);
									rep.setCode(rb.getCode());
									rep.setMessage(rb.getMessage());
									return rep;
								}
								List<NicTransactionAttachment> listOrgTranAttach = baseFindTranAttach
										.getListModel();
								NicTransactionAttachment orgTranAttach = null;
								if (listOrgTranAttach != null
										&& listOrgTranAttach.size() > 0) {
									orgTranAttach = listTranAttach.get(0);
								}
								orgPersonOutput
										.setGender(orgPerson.getGender());
								orgPersonOutput.setDateOfBirth(orgPerson
										.getDateOfBirth());
								orgPersonOutput.setPlaceOfBirth(orgPerson
										.getPlaceOfBirthName());
								orgPersonOutput.setIdNumber(orgPerson
										.getIdNumber());
								orgPersonOutput.setFather(orgPerson
										.getFatherName());
								orgPersonOutput.setMother(orgPerson
										.getMotherName());
								orgPersonOutput.setPersonCode(orgPerson
										.getPersonCode());
								if (orgTranAttach != null) {
									orgPersonOutput.setBase64(orgTranAttach
											.getDocData().toString());
								}

								spData.setOrgDocument(orgPersonOutput);
							}
						}
					}
					rep.setData(spData);
					rep.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					if (spData != null) {
						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(
								Contants.URL_GET_PASSPORT_DOCUMENT, 1, null);
					}
				} else {
					rep.setCode(Contants.CODE_INPUT_FAILD);
					rep.setMessage("transactionId"
							+ Contants.MESSAGE_INPUT_NULL);
				}
			} else {
				rep.setCode(Contants.CODE_INPUT_FAILD);
				rep.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			rep.setCode(Contants.CODE_THROW_EXCEPTION);
			rep.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(rep.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - getPassportDocument fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rep;
	}

	/*
	 * Tách nhân thân.
	 */
	// @POST
	// @Produces("application/json")
	// @Consumes("application/json")
	// @Path("/separatePerson")
	// public ResponseBase separatePerson(PersonSeperationInfo personSepInfo) {
	// ResponseBase rb = new ResponseBase();
	// rb.setCode(Contants.RESPONSE_CODE_FAIL_API);
	// rb.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
	// EppWsLog eppWsLog = new EppWsLog();
	// eppWsLog.setType(Contants.TYPE_SP);
	// eppWsLog.setUrlRequest(Contants.URL_SEPARATE_PERSON);
	// try {
	// eppWsLog.setDataRequest(new ObjectMapper()
	// .writeValueAsString(personSepInfo));
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// logger.error(e1.getMessage() + "convert object to json fail.");
	// e1.printStackTrace();
	//
	// }
	// eppWsLog.setKeyId("SP_"
	// + new SimpleDateFormat("ddMMyyyy").format(new Date()));
	// try {
	// if (personSepInfo != null) {
	// EppPerson newPerson = null;
	// if (StringUtils.isNotBlank(personSepInfo.getPersonCode())) {
	// newPerson = new EppPerson();
	// }
	// BaseModelSingle<EppPerson> baseFindPersonByTranId = eppPersonService
	// .findByTransactionId(personSepInfo.getTransactionId());
	// if (!baseFindPersonByTranId.isError()
	// || baseFindPersonByTranId.getMessage() != null) {
	// rb = this
	// .checkExistAndSaveException(baseFindPersonByTranId,
	// eppWsLog, rb, personSepInfo);
	// rb.setCode(rb.getCode());
	// rb.setMessage(rb.getMessage());
	// return rb;
	// }
	// EppPerson person = baseFindPersonByTranId.getModel();
	//
	// } else {
	// rb.setCode(Contants.CODE_INPUT_FAILD);
	// rb.setMessage(Contants.MESSAGE_INPUT_NULL);
	// }
	// } catch (Exception e) {
	// rb.setCode(Contants.CODE_THROW_EXCEPTION);
	// rb.setMessage(Contants.MESSAGE_EXCEPTION);
	// eppWsLog.setDataResponse(rb.toString());
	// eppWsLog.setMessageErrorLog(CreateMessageException
	// .createMessageException(e) + " - separatePerson fail.");
	// eppWsLog.setCreatedDate(new Date());
	// BaseModelSingle<Boolean> baseCheck = eppWsLogService
	// .inserDataTable(eppWsLog);
	// if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
	// logger.error(e.getMessage() + " - save EppWsLog fail.");
	// }
	// }
	// return rb;
	// }

	/*
	 * Tách nhân thân với hộ chiếu đã cấp.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/splitPerson")
	public ResponseBase splitPerson(InfoJoinPerson request) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		Date startTime = new Date();
		String logData = null;
		String refId = null;
		String spStatus = Contants.PERSON_STATUS_SPLIT_PERSON_ERROR;
		String wkstnId = Contants.CREATE_BY_SYSTEM;
		String officerId = Contants.CREATE_BY_SYSTEM;
		String siteCode = "";

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_SP);
		eppWsLog.setUrlRequest(Contants.URL_SPLIT_PERSON);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}

		try {
			if (request != null) {
				eppWsLog.setKeyId(request.getTransactionId());

				BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
						.findTransactionById(request.getTransactionId());
				if (!baseFindTran.isError()
						|| baseFindTran.getMessage() != null) {
					return this.checkExistAndSaveException(baseFindTran,
							eppWsLog, response, request);
				}
				NicTransaction nicTran = baseFindTran.getModel();
				if (nicTran != null) {
					refId = nicTran.getTransactionId();
					if (nicTran.getMatchedTypePerson().equals("KM")) {
						/*
						 * Hoald tạm đóng 01.08.2020
						 */
						// BaseModelSingle<EppPerson> baseFindPersonByTran =
						// eppPersonService
						// .findByTransactionId(request.getTransactionId());
						// if (!baseFindPersonByTran.isError()
						// || baseFindPersonByTran.getMessage() != null) {
						// return this.checkExistAndSaveException(
						// baseFindPersonByTran, eppWsLog, response,
						// request);
						// }
						// EppPerson epp = baseFindPersonByTran.getModel();
						// epp.setOrgPerson(epp.getPersonCode());
						// BaseModelSingle<Boolean> baseSaveEppPerson =
						// eppPersonService
						// .saveOrUpdateData(epp);
						// if (!baseSaveEppPerson.isError()
						// || baseSaveEppPerson.getMessage() != null) {
						// return this.checkExistAndSaveException(
						// baseSaveEppPerson, eppWsLog, response,
						// request);
						// }
						//
						// officerId = request.getUserProcess();
						// spStatus =
						// Contants.PERSON_STATUS_SPLIT_PERSON_COMPLETED;
						// response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
						// response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
						//
						// /* Lưu bảng thống kê truyền nhận */
						// this.saveOrUpRptData(Contants.URL_SPLIT_PERSON, 1,
						// null);
					} else if (nicTran.getMatchedTypePerson().equals("KT")) {
						EppPerson epp = new EppPerson();
						if (request.getPerson() != null) {
							PersonModel person = request.getPerson();
							epp.setCreatedBy(person.getCreatedBy());
							if (person.getCreatedDate() != null) {
								Date date = HelperClass.convertStringToDate(
										person.getCreatedDate(), person
												.getCreatedDate().length());
								epp.setCreateTs(date);
							}
							epp.setDateOfBirth(person.getDateOfBirth());
							epp.setDateOfIdIssue(person.getDateOfIdIssue());
							epp.setEthnic(person.getEthNic());
							epp.setEthnicCode(person.getEthnicCode());
							epp.setFatherName(person.getFatherName());
							epp.setFatherSearchName(person
									.getFatherSearchName());
							epp.setGender(person.getGender());
							epp.setIdNumber(person.getIdNumber());
							epp.setMotherName(person.getMotherName());
							epp.setMotherSearchName(person
									.getMotherSearchName());
							epp.setName(person.getName());
							epp.setNationalityCode(person.getNationalityCode());
							epp.setNationalityName(person.getNationalityName());
							epp.setPersonCode(person.getPersonCode());
							if (!epp.getPersonCode().equals(
									person.getPersonOrgCode())) {
								epp.setOrgPerson(person.getPersonOrgCode());
							}
							epp.setPlaceOfBirthCode(person
									.getPlaceOfBirthCode());
							epp.setPlaceOfBirthName(person
									.getPlaceOfBirthName());
							epp.setPlaceOfIdIssueName(person
									.getPlaceOfIdIssueName());
							epp.setSrcDoc(refId);
							epp.setReligion(person.getReligion());
							epp.setReligionCode(person.getReligionCode());
							epp.setSearchName(person.getSearchName());
							epp.setUpdatedBy(person.getUpdatedBy());
							if (person.getUpdatedDate() != null) {
								Date date = HelperClass.convertStringToDate(
										person.getUpdatedDate(), person
												.getUpdatedDate().length());
								epp.setUpdateTs(date);
							}
							epp.setIsChecked((person.getIsChecked() != "" && person
									.getIsChecked().equals("Y")) ? true : false);
							epp.setDescription(person.getDescription());
							epp.setSrcOffice(person.getSrcOffice());
							epp.setStatus(person.getStatus());
							epp.setCreatedByName(person.getCreatedByName());
							epp.setUpdatedByName(person.getUpdatedByName());
							epp.setOtherName(person.getOtherName());
							epp.setCountryOfBirth(person.getCountryOfBirth());
							Long personId = eppPersonService.saveEppPerson(epp);
							// Kiểm tra thông tin gia đình
							if (person.getFamilies() != null
									&& person.getFamilies().size() > 0) {
								for (PersonFamily f : person.getFamilies()) {
									EppPersonFamily fmy = new EppPersonFamily();
									fmy.setName(f.getName());
									fmy.setDateOfBirth(f.getDateOfBirth());
									fmy.setLost(f.getLost());
									fmy.setRelationship(f.getRelationship());
									fmy.setGender(f.getGender());
									fmy.setSubjectPerson(personId);
									fmy.setCreatedBy(person.getCreatedBy());
									fmy.setCreateTs(new Date());
									BaseModelSingle<Boolean> baseSaveDataFamily = eppPersonService
											.saveOrUpdateDataFamily(fmy);
									if (!baseSaveDataFamily.isError()
											|| baseSaveDataFamily.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseSaveDataFamily, eppWsLog,
												response, request);
									}
								}
							}
						}
						if (StringUtils.isNotBlank(request.getApproveDate())
								&& StringUtils.isNotBlank(request
										.getUserProcess())
								&& StringUtils.isNotBlank(request
										.getDateProcess())
								&& StringUtils.isNotBlank(request
										.getApproverName())) {
							SimpleDateFormat fm = new SimpleDateFormat(
									DateUtil.FORMAT_YYYYMMDDHHMMSS);
							EppMatchPersonHistory matchHistory = new EppMatchPersonHistory();
							matchHistory.setTransactionId(request
									.getTransactionId());
							matchHistory.setApproveDate(fm.parse(request
									.getApproveDate()));
							matchHistory.setCreateDatetime(fm.parse(request
									.getDateProcess()));
							matchHistory.setApproverName(request
									.getApproverName());
							matchHistory.setCreateBy(request.getUserProcess());
							matchHistory.setOrgPerson(request
									.getPersonOrgCode());
							matchHistory.setPersonCode(request.getPersonCode());
							matchHistory.setReason(request.getNote());
							matchHistory
									.setType(EppMatchPersonHistory.TYPE_SPLIT);
							eppMatchPersonHistoryService
									.saveMatchPersonHistory(matchHistory);
						}

						officerId = request.getUserProcess();
						spStatus = Contants.PERSON_STATUS_SPLIT_PERSON_COMPLETED;
						response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
						response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(Contants.URL_SPLIT_PERSON, 1, null);
					}
				} else {
					response.setCode(Contants.CODE_INPUT_FAILD);
					response.setMessage(Contants.MESSAGE_TRANSACTION_NULL);
				}
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - splitPerson: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - splitPerson fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		} finally {
			try {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName().toUpperCase();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			Date endTime = new Date();
			BaseModelSingle<Long> baseSaveTranLog = transactionLogService
					.saveTransactionLog(refId,
							Contants.PERSON_STATE_SPLIT_PERSON, spStatus,
							officerId, wkstnId, siteCode, startTime, endTime,
							"", logData);
			if (!baseSaveTranLog.isError()
					|| baseSaveTranLog.getMessage() != null) {
				response = this.checkExistAndSaveException(baseSaveTranLog,
						eppWsLog, response, request);
			}
		}
		return response;
	}

	/*
	 * Khớp nhân thân với hộ chiếu đã cấp.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/matchPerson")
	public ResponseBase matchPerson(InfoJoinPerson request) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		Date startTime = new Date();
		String logData = null;
		String refId = null;
		String spStatus = Contants.PERSON_STATUS_MATCH_PERSON_ERROR;
		String wkstnId = Contants.CREATE_BY_SYSTEM;
		String officerId = Contants.CREATE_BY_SYSTEM;
		String siteCode = "";

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_MP);
		eppWsLog.setUrlRequest(Contants.URL_MATCH_PERSON);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();

		}

		try {
			if (request != null) {
				eppWsLog.setKeyId(request.getTransactionId());
				refId = request.getTransactionId();
				/* Kiểm tra person */
				BaseModelSingle<NicUploadJob> baseFindUJByTran = uploadJobService
						.findUploadJobByTransId(request.getTransactionId());
				if (!baseFindUJByTran.isError()
						|| baseFindUJByTran.getMessage() != null) {
					return this.checkExistAndSaveException(baseFindUJByTran,
							eppWsLog, response, request);
				}
				NicUploadJob nicU = baseFindUJByTran.getModel();
				if (nicU != null) {
					nicU.setInvestigationOfficerId(request.getUserProcess());
					// if (StringUtils.isNotEmpty(request.getDateProcess())) {
					// Date date = HelperClass.convertStringToDate(request
					// .getDateProcess(), request.getDateProcess()
					// .length());
					// nicU.setDateApprovePerson(date);
					// }
					nicU.setNoteApprovePerson(request.getNote());
					nicU.setUpdateBy(Contants.CREATE_BY_SYSTEM);
					nicU.setUpdateDatetime(new Date());
					nicU.getNicTransaction().setPersonCode(
							request.getPersonCode());
					BaseModelSingle<Boolean> baseSaveUJ = uploadJobService
							.saveOrUpdateService(nicU);
					if (!baseSaveUJ.isError()
							|| baseSaveUJ.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveUJ,
								eppWsLog, response, request);
					}
					/*
					 * Lưu trạng thái khớp cá nhân.
					 */
					if (StringUtils.isNotBlank(request.getPersonStage())
							&& request.getPersonOrgCode().length() == 2) {
						NicTransaction nicTran = nicU.getNicTransaction();
						nicTran.setMatchedTypePerson(request.getPersonStage());
						BaseModelSingle<Boolean> baseSaveTran = nicTransactionService
								.saveOrUpdateTransaction(nicTran);
						if (!baseSaveTran.isError()
								|| baseSaveTran.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseSaveTran, eppWsLog, response, request);
						}
					}

					if (!request.getPersonStage().equals("KT")) {
						// BaseModelSingle<Boolean> baseSavePS = this
						// .savePersonByUploadTransaction(nicU,
						// request.getPersonCode());
						// if (!baseSavePS.isError()
						// || baseSavePS.getMessage() != null) {
						// return this.checkExistAndSaveException(baseSavePS,
						// eppWsLog, response, request);
						// }
						BaseModelSingle<EppPerson> baseFindPersonByTran = eppPersonService
								.findByStringCode(nicU.getNicTransaction()
										.getPersonCode());
						if (!baseFindPersonByTran.isError()
								|| baseFindPersonByTran.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseFindPersonByTran, eppWsLog, response,
									request);
						}
						EppPerson epp = baseFindPersonByTran.getModel();
						if (request.getPersonStage().equals("KM")) {
							if (!epp.getPersonCode().equals(
									request.getPersonOrgCode())) {
								epp.setOrgPerson(request.getPersonOrgCode());
							}
							BaseModelSingle<Boolean> baseSaveEppPerson = eppPersonService
									.saveOrUpdateData(epp);
							if (!baseSaveEppPerson.isError()
									|| baseSaveEppPerson.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseSaveEppPerson, eppWsLog, response,
										request);
							}
						}
						// Hoald thêm: kiểm tra và lưu thông tin person nếu có.
						if (epp == null)
							epp = new EppPerson();

						if (request.getPerson() != null) {
							PersonModel person = request.getPerson();
							epp.setCreatedBy(person.getCreatedBy());
							if (person.getCreatedDate() != null) {
								Date date = HelperClass.convertStringToDate(
										person.getCreatedDate(), person
												.getCreatedDate().length());
								epp.setCreateTs(date);
							}
							epp.setDateOfBirth(person.getDateOfBirth());
							epp.setDateOfIdIssue(person.getDateOfIdIssue());
							epp.setEthnic(person.getEthNic());
							epp.setEthnicCode(person.getEthnicCode());
							epp.setFatherName(person.getFatherName());
							epp.setFatherSearchName(person
									.getFatherSearchName());
							epp.setGender(person.getGender());
							epp.setIdNumber(person.getIdNumber());
							epp.setMotherName(person.getMotherName());
							epp.setMotherSearchName(person
									.getMotherSearchName());
							epp.setName(person.getName());
							epp.setNationalityCode(person.getNationalityCode());
							epp.setNationalityName(person.getNationalityName());
							epp.setPersonCode(person.getPersonCode());
							if (!epp.getPersonCode().equals(
									person.getPersonOrgCode())) {
								epp.setOrgPerson(person.getPersonOrgCode());
							}
							epp.setPlaceOfBirthCode(person
									.getPlaceOfBirthCode());
							epp.setPlaceOfBirthName(person
									.getPlaceOfBirthName());
							epp.setPlaceOfIdIssueName(person
									.getPlaceOfIdIssueName());
							epp.setSrcDoc(request.getTransactionId());
							epp.setReligion(person.getReligion());
							epp.setReligionCode(person.getReligionCode());
							epp.setSearchName(person.getSearchName());
							epp.setUpdatedBy(person.getUpdatedBy());
							if (person.getUpdatedDate() != null) {
								Date date = HelperClass.convertStringToDate(
										person.getUpdatedDate(), person
												.getUpdatedDate().length());
								epp.setUpdateTs(date);
							}
							epp.setIsChecked((person.getIsChecked() != "" && person
									.getIsChecked().equals("Y")) ? true : false);
							epp.setDescription(person.getDescription());
							epp.setSrcOffice(person.getSrcOffice());
							epp.setStatus(person.getStatus());
							epp.setCreatedByName(person.getCreatedByName());
							epp.setUpdatedByName(person.getUpdatedByName());
							epp.setOtherName(person.getOtherName());
							epp.setCountryOfBirth(person.getCountryOfBirth());
							Long personId = eppPersonService.saveEppPerson(epp);
							// Kiểm tra thông tin gia đình
							if (person.getFamilies() != null
									&& person.getFamilies().size() > 0) {
								for (PersonFamily f : person.getFamilies()) {
									EppPersonFamily fmy = new EppPersonFamily();
									fmy.setName(f.getName());
									fmy.setDateOfBirth(f.getDateOfBirth());
									fmy.setLost(f.getLost());
									fmy.setRelationship(f.getRelationship());
									fmy.setGender(f.getGender());
									fmy.setSubjectPerson(personId);
									fmy.setCreatedBy(person.getCreatedBy());
									fmy.setCreateTs(new Date());
									BaseModelSingle<Boolean> baseSaveDataFamily = eppPersonService
											.saveOrUpdateDataFamily(fmy);
									if (!baseSaveDataFamily.isError()
											|| baseSaveDataFamily.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseSaveDataFamily, eppWsLog,
												response, request);
									}
								}
							}
						}
					}
					if (StringUtils.isNotBlank(request.getApproveDate())
							&& StringUtils.isNotBlank(request.getUserProcess())
							&& StringUtils.isNotBlank(request.getDateProcess())
							&& StringUtils
									.isNotBlank(request.getApproverName())) {
						SimpleDateFormat fm = new SimpleDateFormat(
								DateUtil.FORMAT_YYYYMMDDHHMMSS);
						EppMatchPersonHistory matchHistory = new EppMatchPersonHistory();
						matchHistory.setTransactionId(request
								.getTransactionId());
						matchHistory.setApproveDate(fm.parse(request
								.getApproveDate()));
						matchHistory.setCreateDatetime(fm.parse(request
								.getDateProcess()));
						matchHistory.setApproverName(request.getApproverName());
						matchHistory.setCreateBy(request.getUserProcess());
						matchHistory.setOrgPerson(request.getPersonOrgCode());
						matchHistory.setPersonCode(request.getPersonCode());
						matchHistory.setReason(request.getNote());
						matchHistory.setType(EppMatchPersonHistory.TYPE_MATCH);
						eppMatchPersonHistoryService
								.saveMatchPersonHistory(matchHistory);
					}
					officerId = request.getUserProcess();
					spStatus = Contants.PERSON_STATUS_MATCH_PERSON_COMPLETED;
					response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_MATCH_PERSON, 1, null);
					// logger.info("success uploadJoinPerson to ttdh, time:" +
					// HelperClass.convertDateToString1(new Date()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - matchPerson: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - matchPerson fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		} finally {
			try {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName().toUpperCase();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			Date endTime = new Date();
			BaseModelSingle<Long> baseSaveTranLog = transactionLogService
					.saveTransactionLog(refId,
							Contants.PERSON_STATE_MATCH_PERSON, spStatus,
							officerId, wkstnId, siteCode, startTime, endTime,
							"", logData);
			if (!baseSaveTranLog.isError()
					|| baseSaveTranLog.getMessage() != null) {
				response = this.checkExistAndSaveException(baseSaveTranLog,
						eppWsLog, response, request);
			}
		}
		return response;
	}

	/*
	 * cập nhật hồ sơ lưu
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateArchiveDocument")
	public Response<Long> updateArchiveDocument(ArchiveDocument request) {
		Response<Long> response = new Response<Long>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_UAD);
		eppWsLog.setUrlRequest(Contants.URL_UPDATE_ARCHIVE_DOCUMENT);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}
		try {
			if (request != null
					&& StringUtils.isNotBlank(request.getArchiveCode())) {
				String archiveCode = request.getArchiveCode();
				eppWsLog.setKeyId(archiveCode);
				// tìm kiếm theo số hồ sơ, đã tồn tại thì update thông tin, chưa
				// thì thêm mới.
				List<ListDocument> listDoc = request.getListDocument();
				if (listDoc != null && listDoc.size() > 0) {
					for (ListDocument doc : listDoc) {
						EppArchive eppArchive = null;
						eppArchive = eppArchiveService.findByTransactionId(doc
								.getDocCode());
						if (eppArchive == null) {
							eppArchive = new EppArchive();
						}
						eppArchive.setArchiveCode(archiveCode);
						eppArchive.setDocCode(doc.getDocCode());
						eppArchive.setReceiptNo(doc.getReceiptNo());
						eppArchive.setAbridgment(doc.getAbridgment());
						eppArchive.setDesc(doc.getDescription());
						eppArchive.setType(request.getType());
						eppArchive.setStartPage((long) doc.getStartPage());
						eppArchive.setPageCount((long) doc.getPageCount());
						eppArchive.setRegOffice(request.getRegOfficeCode());
						eppArchive.setSavedTime("");
						eppArchive.setDocName("");
						if (StringUtils.isNotBlank(request.getCreatedDate())) {
							eppArchive.setCreatedTs(DateUtil.strToDate(
									request.getCreatedDate(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						eppArchive.setCreatedBy(request.getCreatedUserName());
						eppArchive.setCreatedByName(request.getCreatedName());
						eppArchive.setUpdatedBy(null);
						eppArchive.setUpdatedTs(null);
						eppArchive.setPackNo(request.getPackNo());
						eppArchiveService.saveOrUpdateArchive(eppArchive);

						BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
								.findTransactionById(doc.getDocCode());
						if (!baseFindTran.isError()
								&& baseFindTran.getMessage() != null) {
							throw new Exception(baseFindTran.getMessage());
						}
						NicTransaction transaction = baseFindTran.getModel();
						if (transaction != null
								&& !archiveCode.equals(transaction
										.getArchiveCode())) {
							/*
							 * EppArchiveCode arcCode = null;
							 * BaseModelSingle<Boolean> baseSaveArcCode = null;
							 * BaseModelSingle<EppArchiveCode> baseFindArc =
							 * null; if (transaction.getArchiveCode() != null) {
							 * baseFindArc = this.findArchive(transaction
							 * .getArchiveCode()); if (!baseFindArc.isError() ||
							 * baseFindArc.getMessage() != null) { throw new
							 * Exception(baseFindArc.getMessage()); } arcCode =
							 * baseFindArc .getModel(); if (arcCode != null) {
							 * arcCode.setCount(arcCode.getCount() - 1);
							 * baseSaveArcCode = archiveCodeService
							 * .saveOrUpdateData(arcCode); if
							 * (!baseSaveArcCode.isError() ||
							 * baseSaveArcCode.getMessage() != null) { throw new
							 * Exception(baseSaveArcCode.getMessage()); } } }
							 * baseFindArc = this.findArchive(archiveCode); if
							 * (!baseFindArc.isError() ||
							 * baseFindArc.getMessage() != null) { throw new
							 * Exception(baseFindArc.getMessage()); } arcCode =
							 * baseFindArc .getModel(); if (arcCode != null) {
							 * arcCode .setCount(arcCode.getCount() + 1); }else
							 * { arcCode = this.createArcCode(archiveCode); } if
							 * (arcCode == null){
							 * response.setCode(Contants.CODE_INPUT_FAILD);
							 * response
							 * .setMessage("ArchiveCode - Sai định dạng dữ liệu."
							 * ); return response; } baseSaveArcCode =
							 * archiveCodeService .saveOrUpdateData(arcCode); if
							 * (!baseSaveArcCode.isError() ||
							 * baseSaveArcCode.getMessage() != null) { throw new
							 * Exception(baseSaveArcCode.getMessage()); }
							 */
							transaction.setArchiveCode(archiveCode);
							BaseModelSingle<Boolean> baseSaveTran = nicTransactionService
									.saveOrUpdateTransaction(transaction);
							if (!baseSaveTran.isError()
									&& baseSaveTran.getMessage() != null) {
								throw new Exception(baseSaveTran.getMessage());
							}
						}
						if (StringUtils.isNotBlank(doc.getCancelDocCode())
								&& StringUtils.isNotBlank(doc
										.getCancelPassportNo())) {
							BaseModelSingle<NicTransactionLost> findTranLost = transactionLostService
									.findTranLostByConditions(
											doc.getCancelDocCode(),
											doc.getCancelPassportNo(), null);
							if (!findTranLost.isError()
									|| findTranLost.getMessage() != null) {
								throw new Exception(findTranLost.getMessage());
							}
							NicTransactionLost nicLost = findTranLost
									.getModel();
							if (nicLost != null) {
								nicLost.setArchiveCode(request.getArchiveCode());
								BaseModelSingle<Boolean> saveLost = transactionLostService
										.saveOrUpdateLost(nicLost);
								if (!saveLost.isError()
										|| saveLost.getMessage() != null) {
									throw new Exception(saveLost.getMessage());
								}
							}
						}
					}
				}
				if (listDoc.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_UPDATE_ARCHIVE_DOCUMENT,
							1, request.getRegOfficeCode());
				}
				// Tu them
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage("Số hồ sơ lưu "
						+ Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - updateArchiveDocument: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e)
					+ " - updateArchiveDocument fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	/*
	 * Tìm kiếm hộ chiếu để khôi phục.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findPassportRestore")
	public Response<List<PassportRestoreDetail>> findPassportRestore(
			FindPassportInput request) {
		Response<List<PassportRestoreDetail>> response = new Response<List<PassportRestoreDetail>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		List<PassportRestoreDetail> listPassportDetail = null;

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_FPR);
		eppWsLog.setUrlRequest(Contants.URL_FIND_PASSPORT_RESTORE);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}
		try {
			if (StringUtils.isNotBlank(request.getPassportNo())) {
				List<NicTransactionLost> list = transactionLostService
						.findPassportCancelled(request.getPassportNo(),
								request.getReason(), request.getName(),
								request.getGender(), request.getDateOfBirth());

				if (list != null && list.size() > 0) {
					listPassportDetail = new ArrayList<PassportRestoreDetail>();
					for (NicTransactionLost tranLost : list) {
						PassportRestoreDetail passportDetail = new PassportRestoreDetail();
						passportDetail.setNote(tranLost.getNote());
						passportDetail.setReason(tranLost.getReason());
						passportDetail.setName(tranLost.getName());
						passportDetail.setGender(tranLost.getGender());
						passportDetail
								.setDateOfBirth(tranLost.getDateOfBirth());
						if (tranLost.getNationalityCode() != null) {
							CodeValues codeValues = codesService
									.findByStringCodeId("COUNTRY",
											tranLost.getNationalityCode());
							if (codeValues != null) {
								passportDetail.setNationalityName(codeValues
										.getCodeValueDesc());
							}
						}
						passportDetail.setPassportNo(tranLost.getPassportNo());
						if (tranLost.getDateOfDocIssue() != null) {
							passportDetail.setDateOfIssue(DateUtil.parseDate(
									tranLost.getDateOfDocIssue(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						if (StringUtils.isNotBlank(tranLost
								.getPlaceOfIssueCode())) {
							passportDetail
									.setPlaceOfIssue(this.getSiteName(tranLost
											.getPlaceOfIssueCode()));
						}
						if (tranLost.getDateOfDocExpiry() != null) {
							passportDetail.setDateOfExpiry(DateUtil.parseDate(
									tranLost.getDateOfDocExpiry(),
									DateUtil.FORMAT_YYYYMMDD));
						}

						NicDocumentData nicDoc = documentDataService
								.findDocDataById(tranLost.getTransactionId(),
										tranLost.getPassportNo());
						if (nicDoc != null) {
							passportDetail.setStatus(nicDoc.getStatus());
						}

						passportDetail
								.setDiplomaCode(tranLost.getDiplomaCode());
						passportDetail.setDateOfDiploma(tranLost
								.getDateOfBirth());
						if (tranLost.getDateOfRegister() != null) {
							passportDetail.setDateOfRegister(DateUtil
									.parseDate(tranLost.getDateOfRegister(),
											DateUtil.FORMAT_YYYYMMDD));
						}
						if (StringUtils.isNotBlank(tranLost.getOfficeCode())) {
							passportDetail.setOfficeName(this
									.getSiteName(tranLost.getOfficeCode()));
						}
						passportDetail.setApproverName(tranLost
								.getApproverName());
						passportDetail.setTransactionId(tranLost
								.getTransactionId());
						listPassportDetail.add(passportDetail);
					}

					if (listPassportDetail != null
							&& listPassportDetail.size() > 0) {
						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(
								Contants.URL_FIND_PASSPORT_RESTORE,
								listPassportDetail.size(), null);
					}

					response.setData(listPassportDetail);
					response.setCode(Contants.CODE_SUCCESS);
					response.setMessage(Contants.MESSAGE_SUCCESS);
				} else {
					response.setCode(Contants.CODE_INPUT_FAILD);
					response.setMessage("Không tìm thấy hộ chiếu đã hủy.");
				}
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage("passportNo" + Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - findPassportRestore: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - findPassportRestore fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	/*
	 * Khôi phục hộ chiếu.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/restorePassport")
	public ResponseBase restorePassport(FindPassportRestoreInput request) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_RP);
		eppWsLog.setUrlRequest(Contants.URL_RESTORE_PASSPORT);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}
		try {
			if (request != null) {
				if (request.getListPassportRestore() != null
						&& request.getListPassportRestore().size() > 0) {
					for (PassportCancelDetail detail : request
							.getListPassportRestore()) {
						if (StringUtils.isBlank(detail.getTransactionId())
								|| StringUtils.isBlank(detail.getPassportNo())) {
							response.setCode(Contants.CODE_INPUT_FAILD);
							response.setMessage("số hộ chiếu hoặc số hồ sơ"
									+ Contants.MESSAGE_INPUT_NULL);
							return response;
						}
						BaseModelSingle<NicTransactionLost> baseFindTranLost = transactionLostService
								.findTranLostByConditions(
										detail.getTransactionId(),
										detail.getPassportNo(), null);
						if (!baseFindTranLost.isError()
								|| baseFindTranLost.getMessage() != null) {
							throw new Exception(baseFindTranLost.getMessage());
						}
						NicTransactionLost tranLost = baseFindTranLost
								.getModel();
						if (tranLost != null) {
							if (StringUtils.isNotBlank(request
									.getApproverDate())) {
								tranLost.setRstApprovalDate(DateUtil.strToDate(
										request.getApproverDate(),
										DateUtil.FORMAT_YYYYMMDD));
							}
							// tranLost.setRstApproverLevel(request.getA);
							tranLost.setRstApproverName(request
									.getApproverName());
							// tranLost.setRstApproverPosition(rstApproverPosition);
							tranLost.setRstDiplomaCode(request.getDiplomaCode());
							if (StringUtils.isNotBlank(request
									.getDateOfDiploma())) {
								tranLost.setRstDiplomaDate(DateUtil.strToDate(
										request.getDateOfDiploma(),
										DateUtil.FORMAT_YYYYMMDD));
							}
							tranLost.setRstNote(request.getReason());
							BaseModelSingle<Boolean> baseSaveTranLost = transactionLostService
									.saveOrUpdateLost(tranLost);
							if (!baseSaveTranLost.isError()
									|| baseSaveTranLost.getMessage() != null) {
								throw new Exception(
										baseSaveTranLost.getMessage());
							}
							if (baseSaveTranLost.getModel()) {
								NicDocumentData nicDocData = documentDataService
										.findDocDataById(
												tranLost.getTransactionId(),
												tranLost.getPassportNo());
								if (nicDocData != null) {
									nicDocData
											.setStatus(Contants.DOC_STATUS_ISSUANCE);
									BaseModelSingle<Void> baseSaveDocData = documentDataService
											.saveOrUpdateDocData(nicDocData);
									if (!baseSaveDocData.isError()
											|| baseSaveDocData.getMessage() != null) {
										throw new Exception(
												baseSaveDocData.getMessage());
									}

									/* Lưu bảng thống kê truyền nhận */
									this.saveOrUpRptData(
											Contants.URL_RESTORE_PASSPORT, 1,
											Contants.QUEUE_RECEIVER_A08_HH);

									/* Save Passport Log */
									NicDocumentHistory docHis = new NicDocumentHistory();
									docHis.setPassportNo(tranLost
											.getPassportNo());
									docHis.setSiteCode(request.getSiteCode());
									docHis.setStatus(nicDocData.getStatus());
									docHis.setStatusUpdateBy(request
											.getApproverName());
									docHis.setStatusUpdateTime(new Date());
									BaseModelSingle<Boolean> baseSaveDocHis = documentHistoryService
											.saveDocumentHistory(docHis);
									if (!baseSaveDocHis.isError()
											|| baseSaveDocHis.getMessage() != null) {
										throw new Exception(
												baseSaveDocHis.getMessage());
									}
								}

								// Lưu hàng đợi đồng bộ sang hệ thống cũ
								this.addObjToQueueJob(
										String.valueOf(tranLost.getId()),
										Contants.QUEUE_OBJ_TYPE_DOC_RESTORE,
										Contants.QUEUE_RECEIVER_A08_HH, null,
										null);
							}
						} else {
							response.setCode(Contants.CODE_INPUT_FAILD);
							response.setMessage("Không có dữ liệu hủy hộ chiếu: "
									+ detail.getPassportNo());
							return response;
						}
					}
					response.setCode(Contants.CODE_SUCCESS);
					response.setMessage(Contants.MESSAGE_SUCCESS);

				} else {
					response.setCode(Contants.CODE_INPUT_FAILD);
					response.setMessage("Chi tiết hộ chiếu cần khôi phục trống.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - restorePassport: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - restorePassport fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	/*
	 * Tìm kiếm giấy tờ hủy để in thông báo.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findCancelDocumentToPrint")
	public Response<List<InfoDocToPrintMemo>> findCancelDocumentToPrint(
			InfoFindCancelDocToPrintMemo request) {
		Response<List<InfoDocToPrintMemo>> response = new Response<List<InfoDocToPrintMemo>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_FCDTP);
		eppWsLog.setUrlRequest(Contants.URL_FIND_CANCEL_DOCUMENT_TO_PRINT);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}
		List<InfoDocToPrintMemo> listInfo = null;
		try {
			if (request != null
					&& (StringUtils.isNotBlank(request.getPrintType()) && (request
							.getPrintType().equals("Y") || request
							.getPrintType().equals("N")))) {

				Date fromDate = null;
				Date toDate = null;
				Date dateOfBirth = null;
				Boolean printType = this
						.convertStrToBoo(request.getPrintType());
				if (StringUtils.isNotBlank(request.getFromDate())) {
					fromDate = DateUtil.strToDate(request.getFromDate(),
							DateUtil.FORMAT_YYYYMMDD);
				}
				if (StringUtils.isNotBlank(request.getToDate())) {
					toDate = new Date(DateUtil.strToDate(request.getToDate(),
							DateUtil.FORMAT_YYYYMMDD).getTime() + 86400000L);
				}
				if (StringUtils.isNotBlank(request.getDateOfBirth())) {
					dateOfBirth = DateUtil.strToDate(request.getDateOfBirth(),
							DateUtil.FORMAT_YYYYMMDD);
				}
				List<NicTransactionLost> listTranLost = transactionLostService
						.findDocCancelToPrint(request.getCreatedBy(),
								printType, request.getDiplomaCode(),
								dateOfBirth, fromDate, toDate,
								request.getGender(), request.getPassportNo(),
								request.getName());
				if (listTranLost != null && listTranLost.size() > 0) {
					listInfo = new ArrayList<InfoDocToPrintMemo>();
					for (NicTransactionLost tranLost : listTranLost) {
						if (tranLost.getRstApprovalDate() != null
								|| !tranLost.getIsSendNotification()) {
							continue;
						}
						InfoDocToPrintMemo info = new InfoDocToPrintMemo();
						info.setTransactionId(tranLost.getTransactionId());
						info.setPassportNo(tranLost.getPassportNo());
						if (tranLost.getDateOfDocIssue() != null) {
							info.setDateOfDocIssue(DateUtil.parseDate(
									tranLost.getDateOfDocIssue(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						if (tranLost.getDateOfDocExpiry() != null) {
							info.setDateOfDocExpiry(DateUtil.parseDate(
									tranLost.getDateOfDocExpiry(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						info.setName(tranLost.getName());
						info.setGender(tranLost.getGender());
						info.setDateOfBirth(tranLost.getDateOfBirth());
						info.setDiplomaCode(tranLost.getDiplomaCode());
						if (tranLost.getDateOfDiploma() != null) {
							info.setDateOfDiploma(DateUtil.parseDate(
									tranLost.getDateOfDiploma(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						if (StringUtils.isNotBlank(tranLost
								.getOfficeOfDiplomaCode())) {
							info.setOfficeOfDiploma(this.getSiteName(tranLost
									.getOfficeOfDiplomaCode()));
						}
						if (StringUtils.isNotBlank(tranLost
								.getPlaceOfIssueCode())) {
							info.setPlaceOfIssue(this.getSiteName(tranLost
									.getPlaceOfIssueCode()));

						}
						if (StringUtils.isNotBlank(tranLost.getOfficeCode())) {
							info.setOfficeName(this.getSiteName(tranLost
									.getOfficeCode()));
						}
						if (tranLost.getDateOfRegister() != null) {
							info.setDateOfRegister(DateUtil.parseDate(
									tranLost.getDateOfRegister(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						info.setCreateBy(tranLost.getCreatedBy());
						info.setReason(tranLost.getReason());
						info.setApproverName(tranLost.getApproverName());
						info.setApproverPosition(tranLost.getApproverPosition());
						info.setNote(tranLost.getNote());

						if (StringUtils.isNotBlank(tranLost
								.getPlaceOfBirthCode())) {
							CodeValues codeValues = codesService
									.findByStringCodeId(
											CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
											tranLost.getPlaceOfBirthCode());
							if (codeValues == null) {
								codeValues = codesService.findByStringCodeId(
										CodeValues.CODE_ID_COUNTRY,
										tranLost.getPlaceOfBirthCode());
							}
							if (codeValues != null) {
								info.setPlaceOfBirth(codeValues
										.getCodeValueDesc());
							} else {
								info.setPlaceOfBirth(tranLost
										.getPlaceOfBirthCode());
							}
						}

						// 09.09.2020 Hoald bổ sung thông tin trả về nếu in lại.
						if (printType) {
							info.setSendAbout(tranLost.getSendIssue());
							info.setSendDear(tranLost.getSendDear());
							info.setSendDiplomaCode(tranLost
									.getSendDiplomaCode());
							info.setSendDiplomaDate(tranLost
									.getSendDiplomaDate() != null ? DateUtil
									.parseDate(tranLost.getSendDiplomaDate(),
											DateUtil.FORMAT_YYYYMMDD) : null);
							info.setSendOfficeCode(tranLost.getSendOfficeCode());
							info.setSendSignLevel(tranLost.getSendSignLevel());
							info.setSendSignName(tranLost.getSendSignName());
							info.setSendSignPosition(tranLost
									.getSendSignPosition());
						}

						listInfo.add(info);
					}
					/* Lưu bảng thống kê truyền nhận */
					if (listInfo != null && listInfo.size() > 0) {
						this.saveOrUpRptData(
								Contants.URL_FIND_CANCEL_DOCUMENT_TO_PRINT,
								listInfo.size(), null);
					}
					response.setMessage(Contants.MESSAGE_SUCCESS);
				} else {
					response.setMessage("Không tìm thấy bản ghi nào.");
				}
				response.setCode(Contants.CODE_SUCCESS);
				response.setData(listInfo);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage("Kiểm tra lại dữ liệu đầu vào.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - findCancelDocumentToPrint: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e)
					+ " - findCancelDocumentToPrint fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	/*
	 * In thông báo hủy giấy tờ.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/printCancelDocument")
	public ResponseBase printCancelDocument(PassportCancelInput request) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_PCD);
		eppWsLog.setUrlRequest(Contants.URL_PRINT_CANCEL_DOCUMENT);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}
		try {
			if (request != null && request.getCancelDocuments() != null
					&& request.getCancelDocuments().size() > 0) {
				List<PassportCancelDetail> listPCDetail = request
						.getCancelDocuments();
				for (PassportCancelDetail pCDetail : listPCDetail) {
					BaseModelSingle<NicTransactionLost> baseFindTranLost = transactionLostService
							.findTranLostByConditions(
									pCDetail.getTransactionId(),
									pCDetail.getPassportNo(), null);
					if (!baseFindTranLost.isError()
							|| baseFindTranLost.getMessage() != null) {
						throw new Exception(baseFindTranLost.getMessage());
					}
					NicTransactionLost nicTranLost = baseFindTranLost
							.getModel();
					if (nicTranLost == null
							|| nicTranLost.getRstApprovalDate() != null) {
						continue;
					}
					nicTranLost.setPrinter(true);
					nicTranLost.setSendDiplomaCode(request.getDiplomaCode());
					if (StringUtils.isNotBlank(request.getDateOfDiploma())) {
						nicTranLost.setSendDiplomaDate(DateUtil.strToDate(
								request.getDateOfDiploma(),
								DateUtil.FORMAT_YYYYMMDD));
					}
					nicTranLost.setSendSignName(request.getSignName());
					nicTranLost.setSendSignPosition(request.getSignPosition());
					nicTranLost.setSendSignLevel(request.getSignLevel());
					nicTranLost.setSendOfficeCode(request.getOfficeCode());
					nicTranLost.setSendDear(request.getDear());
					nicTranLost.setSendIssue(request.getAbout());
					BaseModelSingle<Boolean> baseSaveTranLost = transactionLostService
							.saveOrUpdateLost(nicTranLost);
					if (!baseSaveTranLost.isError()
							|| baseSaveTranLost.getMessage() != null) {
						throw new Exception(baseSaveTranLost.getMessage());
					}
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_PRINT_CANCEL_DOCUMENT, 1,
							request.getOfficeCode());
				}
				response.setMessage(Contants.MESSAGE_SUCCESS);
				response.setCode(Contants.CODE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage("Kiểm tra lại dữ liệu đầu vào.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - printCancelDocument: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - printCancelDocument fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	/*
	 * tìm kiếm giấy tờ khôi phục để in thông báo.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findRestoreDocumentToPrint")
	public Response<List<InfoDocRestoreToPrintMemo>> findRestoreDocumentToPrint(
			InfoFindCancelDocToPrintMemo request) {
		Response<List<InfoDocRestoreToPrintMemo>> response = new Response<List<InfoDocRestoreToPrintMemo>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_FRDTP);
		eppWsLog.setUrlRequest(Contants.URL_FIND_RESTORE_DOCUMENT_TO_PRINT);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}
		List<InfoDocRestoreToPrintMemo> listInfo = null;
		try {
			if (request != null
					&& !(StringUtils.isBlank(request.getDiplomaCode())
							&& StringUtils.isBlank(request.getFromDate())
							&& StringUtils.isBlank(request.getGender())
							&& StringUtils.isBlank(request.getName())
							&& StringUtils.isBlank(request.getPassportNo())
							&& StringUtils.isBlank(request.getToDate()) && StringUtils
								.isBlank(request.getDateOfBirth()))) {

				Date fromDate = null;
				Date toDate = null;
				if (StringUtils.isNotBlank(request.getFromDate())) {
					fromDate = DateUtil.strToDate(request.getFromDate(),
							DateUtil.FORMAT_YYYYMMDD);
				}
				if (StringUtils.isNotBlank(request.getToDate())) {
					toDate = DateUtil.strToDate(request.getToDate(),
							DateUtil.FORMAT_YYYYMMDD);
				}
				List<NicTransactionLost> listTranLost = transactionLostService
						.findDocRestoreToPrint(request.getDiplomaCode(),
								request.getDateOfBirth(), fromDate, toDate,
								request.getGender(), request.getPassportNo(),
								request.getName());
				if (listTranLost != null && listTranLost.size() > 0) {
					listInfo = new ArrayList<InfoDocRestoreToPrintMemo>();
					for (NicTransactionLost tranLost : listTranLost) {
						if (tranLost.getRstApprovalDate() == null) {
							continue;
						}
						InfoDocRestoreToPrintMemo info = new InfoDocRestoreToPrintMemo();
						info.setPassportNo(tranLost.getPassportNo());
						if (tranLost.getDateOfDocIssue() != null) {
							info.setDateOfDocIssue(DateUtil.parseDate(
									tranLost.getDateOfDocIssue(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						if (tranLost.getDateOfDocExpiry() != null) {
							info.setDateOfDocExpiry(DateUtil.parseDate(
									tranLost.getDateOfDocExpiry(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						info.setName(tranLost.getName());
						info.setGender(tranLost.getGender());
						info.setDateOfBirth(tranLost.getDateOfBirth());
						info.setDiplomaCode(tranLost.getDiplomaCode());
						if (tranLost.getDateOfDiploma() != null) {
							info.setDateOfDiploma(DateUtil.parseDate(
									tranLost.getDateOfDiploma(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						if (StringUtils.isNotBlank(tranLost
								.getOfficeOfDiplomaCode())) {
							info.setOfficeOfDiploma(this.getSiteName(tranLost
									.getOfficeOfDiplomaCode()));
						}
						if (StringUtils.isNotBlank(tranLost.getOfficeCode())) {
							info.setOfficeName(this.getSiteName(tranLost
									.getOfficeCode()));
						}
						if (tranLost.getDateOfRegister() != null) {
							info.setDateOfRegister(DateUtil.parseDate(
									tranLost.getDateOfRegister(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						info.setReason(tranLost.getReason());
						info.setApproverName(tranLost.getApproverName());
						info.setApproverPosition(tranLost.getApproverPosition());
						info.setApproverLevel(tranLost.getApproverLevel());
						info.setNote(tranLost.getNote());
						info.setRstApproverName(tranLost.getRstApproverName());
						if (tranLost.getRstApprovalDate() != null) {
							info.setDateOfRstApprover(DateUtil.parseDate(
									tranLost.getRstApprovalDate(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						info.setRstDiplomaCode(tranLost.getRstDiplomaCode());
						if (tranLost.getRstDiplomaDate() != null) {
							info.setRstDateOfDiploma(DateUtil.parseDate(
									tranLost.getRstDiplomaDate(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						info.setRstReason(tranLost.getRstNote());
						listInfo.add(info);
					}
					/* Lưu bảng thống kê truyền nhận */
					if (listInfo != null && listInfo.size() > 0) {
						this.saveOrUpRptData(
								Contants.URL_FIND_RESTORE_DOCUMENT_TO_PRINT, 1,
								null);
					}
					response.setMessage(Contants.MESSAGE_SUCCESS);
				} else {
					response.setMessage("Không tìm thấy bản ghi nào.");
				}
				response.setCode(Contants.CODE_SUCCESS);
				response.setData(listInfo);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage("Kiểm tra lại dữ liệu đầu vào.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - findRestoreDocumentToPrint: "
					+ e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e)
					+ " - findRestoreDocumentToPrint fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	/*
	 * tra cứu giấy tờ bị hủy.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findDocumentCanceled")
	public Response<List<InfoDocumentCanceled>> findDocumentCanceled(
			InfoFindCancelDocToPrintMemo request) {
		Response<List<InfoDocumentCanceled>> response = new Response<List<InfoDocumentCanceled>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_FDC);
		eppWsLog.setUrlRequest(Contants.URL_FIND_DOCUMENT_CANCELED);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}
		List<InfoDocumentCanceled> listInfo = null;
		try {
			if (request != null
					&& !(StringUtils.isBlank(request.getDiplomaCode())
							&& StringUtils.isBlank(request.getFromDate())
							&& StringUtils.isBlank(request.getGender())
							&& StringUtils.isBlank(request.getName())
							&& StringUtils.isBlank(request.getPassportNo())
							&& StringUtils.isBlank(request.getToDate())
							&& StringUtils.isBlank(request.getReason())
							&& StringUtils
									.isBlank(request.getNationalityCode()) && StringUtils
								.isBlank(request.getDateOfBirth()))) {

				Date fromDate = null;
				Date toDate = null;
				if (StringUtils.isNotBlank(request.getFromDate())) {

					fromDate = DateUtil.strToDate(request.getFromDate(),
							DateUtil.FORMAT_YYYYMMDD);
				}
				if (StringUtils.isNotBlank(request.getToDate())) {
					String date = String.valueOf(Integer.parseInt(request
							.getToDate()) + 1);
					toDate = DateUtil.strToDate(date, DateUtil.FORMAT_YYYYMMDD);
				}
				List<NicTransactionLost> listTranLost = transactionLostService
						.findDocCanceled(request.getDiplomaCode(),
								request.getDateOfBirth(), fromDate, toDate,
								request.getGender(), request.getPassportNo(),
								request.getName(), request.getReason(),
								request.getNationalityCode());
				if (listTranLost != null && listTranLost.size() > 0) {
					listInfo = new ArrayList<InfoDocumentCanceled>();
					for (NicTransactionLost tranLost : listTranLost) {
						if (tranLost.getRstApprovalDate() != null) {
							continue;
						}
						InfoDocumentCanceled info = new InfoDocumentCanceled();
						info.setPassportNo(tranLost.getPassportNo());
						if (tranLost.getDateOfDocIssue() != null) {
							info.setDateOfDocIssue(DateUtil.parseDate(
									tranLost.getDateOfDocIssue(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						if (tranLost.getDateOfDocExpiry() != null) {
							info.setDateOfDocExpiry(DateUtil.parseDate(
									tranLost.getDateOfDocExpiry(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						if (StringUtils.isNotBlank(tranLost
								.getPlaceOfIssueCode())) {
							info.setPlaceOfDocIssue(this.getSiteName(tranLost
									.getPlaceOfIssueCode()));
						}
						info.setName(tranLost.getName());
						info.setGender(tranLost.getGender());
						info.setDateOfBirth(tranLost.getDateOfBirth());
						if (tranLost.getPlaceOfBirthCode() != null) {
							CodeValues codeValues = codesService
									.findByStringCodeId(
											CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
											tranLost.getPlaceOfBirthCode());
							if (codeValues == null) {
								codeValues = codesService.findByStringCodeId(
										CodeValues.CODE_ID_COUNTRY,
										tranLost.getPlaceOfBirthCode());
							}
							if (codeValues != null) {
								info.setPlaceOfBirth(codeValues
										.getCodeValueDesc());
							} else {
								info.setPlaceOfBirth(tranLost
										.getPlaceOfBirthCode());
							}
						}
						if (tranLost.getNationalityCode() != null) {
							CodeValues codeValues = codesService
									.findByStringCodeId(
											CodeValues.CODE_ID_COUNTRY,
											tranLost.getNationalityCode());
							if (codeValues != null) {
								info.setNationalityName(codeValues
										.getCodeValueDesc());
							} else {
								info.setNationalityName(tranLost
										.getNationalityCode());
							}
						}
						info.setDiplomaCode(tranLost.getDiplomaCode());
						if (tranLost.getDateOfDiploma() != null) {
							info.setDateOfDiploma(DateUtil.parseDate(
									tranLost.getDateOfDiploma(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						info.setDiplomaCodeSend(tranLost.getSendDiplomaCode());
						if (tranLost.getSendDiplomaDate() != null) {
							info.setDateOfDiplomaSend(DateUtil.parseDate(
									tranLost.getSendDiplomaDate(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						if (StringUtils.isNotBlank(tranLost.getOfficeCode())) {
							info.setOfficeName(this.getSiteName(tranLost
									.getOfficeCode()));
						}
						if (tranLost.getDateOfRegister() != null) {
							info.setDateOfRegister(DateUtil.parseDate(
									tranLost.getDateOfRegister(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						info.setReason(tranLost.getReason());
						info.setApproverName(tranLost.getApproverName());
						info.setApproverPosition(tranLost.getApproverPosition());
						info.setApproverLevel(tranLost.getApproverLevel());
						info.setNote(tranLost.getNote());
						NicDocumentData data = documentDataService
								.findDocDataById(tranLost.getTransactionId(),
										tranLost.getPassportNo());
						if (data != null) {
							info.setChipSerialNo(data.getChipSerialNo());
							info.setPhoiSerialNo(data.getSerialNo());
						}
						info.setCreateBy(tranLost.getCreatedBy());
						info.setCreateDate(DateUtil.parseDate(
								tranLost.getCreatedDate(),
								DateUtil.FORMAT_YYYYMMDD));
						listInfo.add(info);
					}
					/* Lưu bảng thống kê truyền nhận */
					if (listInfo != null && listInfo.size() > 0) {
						this.saveOrUpRptData(
								Contants.URL_FIND_DOCUMENT_CANCELED, 1, null);
					}
					response.setMessage(Contants.MESSAGE_SUCCESS);
				} else {
					response.setMessage("Không tìm thấy bản ghi nào.");
				}
				response.setCode(Contants.CODE_SUCCESS);
				response.setData(listInfo);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage("Kiểm tra lại dữ liệu đầu vào.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - findDocumentCanceled: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e)
					+ " - findDocumentCanceled fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	/*
	 * tìm kiếm hc còn hiệu lực và còn hạn
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findValidPassportToCancel")
	public Response<InfoValidPassport> findValidPassportToCancel(
			PassportCancelDetail request) {
		Response<InfoValidPassport> response = new Response<InfoValidPassport>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_FVPTC);
		eppWsLog.setUrlRequest(Contants.URL_FIND_VALID_PASSPORT_TO_CANCEL);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}
		InfoValidPassport info = null;
		try {
			if (request != null
					&& StringUtils.isNotBlank(request.getPassportNo())) {

				List<NicDocumentData> listNicDoc = documentDataService
						.findValidPassportById(request.getPassportNo());
				if (listNicDoc != null && listNicDoc.size() > 0) {
					if (listNicDoc.size() == 1) {
						NicDocumentData nicDoc = listNicDoc.get(0);
						info = new InfoValidPassport();
						info.setPassportNo(nicDoc.getId().getPassportNo());
						info.setTransactionId(nicDoc.getId().getTransactionId());
						if (nicDoc.getDateOfIssue() != null) {
							info.setDateOfDocIssue(DateUtil.parseDate(
									nicDoc.getDateOfIssue(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						if (nicDoc.getDateOfExpiry() != null) {
							info.setDateOfDocExpiry(DateUtil.parseDate(
									nicDoc.getDateOfExpiry(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						NicTransaction nicTran = nicDoc.getNicTransaction();
						if (nicTran != null) {
							info.setNin(nicTran.getNin());
							NicRegistrationData nicReg = nicTran
									.getNicRegistrationData();
							List<NicTransactionAttachment> docList = nicTransactionAttachmentService
									.getNicTransactionAttachments(
											nicTran.getTransactionId(),
											new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE },
											null);
							if (docList != null && docList.size() == 1) {
								NicTransactionAttachment tranAttach = docList
										.get(0);
								info.setBase64(jcifs.util.Base64
										.encode(tranAttach.getDocData()));
							}
							if (nicReg != null) {
								info.setName(HelperClass.createFullName(
										nicReg.getSurnameFull(),
										nicReg.getMiddlenameFull(),
										nicReg.getFirstnameFull()));
								info.setGender(nicReg.getGender());
								if (nicReg.getDateOfBirth() != null) {
									info.setDateOfBirth(DateUtil.parseDate(
											nicReg.getDateOfBirth(),
											DateUtil.FORMAT_YYYYMMDD));
								}
							}
							if (StringUtils.isNotBlank(nicDoc
									.getPlaceOfIssueCode())) {
								info.setPlaceOfDocIssue(this.getSiteName(nicDoc
										.getPlaceOfIssueCode()));
							}
						}
						response.setMessage(Contants.MESSAGE_SUCCESS);
					} else {
						response.setMessage("Tìm thấy nhiều hơn 1 hộ chiếu.");
					}
				} else {
					response.setMessage("Không tìm thấy bản ghi nào.");
				}
				response.setCode(Contants.CODE_SUCCESS);
				response.setData(info);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - findValidPassportToCancel: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e)
					+ " - findValidPassportToCancel fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	/*
	 * tìm kiếm list hc còn hiệu lực và còn hạn
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findValidListPassportToCancel")
	public Response<List<InfoValidPassport>> findValidListPassportToCancel(
			Response<List<FindPassportInput>> request) {
		Response<List<InfoValidPassport>> response = new Response<List<InfoValidPassport>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_FVPTC);
		eppWsLog.setUrlRequest(Contants.URL_FIND_VALID_PASSPORT_TO_CANCEL);
		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();
		}
		List<InfoValidPassport> listInfo = null;
		try {
			if (request != null && request.getData() != null) {
				List<FindPassportInput> listInput = request.getData();
				if (listInput.size() > 0) {
					listInfo = new ArrayList<InfoValidPassport>();
					for (FindPassportInput findPassportInput : listInput) {
						if (StringUtils.isNotBlank(findPassportInput
								.getPassportNo())) {
							List<NicDocumentData> listNicDoc = documentDataService
									.findValidPassportById(findPassportInput
											.getPassportNo());
							if (listNicDoc != null && listNicDoc.size() > 0) {
								if (listNicDoc.size() == 1) {
									NicDocumentData nicDoc = listNicDoc.get(0);

									InfoValidPassport info = new InfoValidPassport();
									info.setPlaceOfDocIssueCode(nicDoc
											.getPlaceOfIssueCode());
									info.setPassportNo(nicDoc.getId()
											.getPassportNo());
									info.setTransactionId(nicDoc.getId()
											.getTransactionId());
									if (nicDoc.getDateOfIssue() != null) {
										info.setDateOfDocIssue(DateUtil.parseDate(
												nicDoc.getDateOfIssue(),
												DateUtil.FORMAT_YYYYMMDD));
									}
									if (nicDoc.getDateOfExpiry() != null) {
										info.setDateOfDocExpiry(DateUtil.parseDate(
												nicDoc.getDateOfExpiry(),
												DateUtil.FORMAT_YYYYMMDD));
									}
									NicTransaction nicTran = nicDoc
											.getNicTransaction();
									if (nicTran != null) {
										info.setNin(nicTran.getNin());
										NicRegistrationData nicReg = nicTran
												.getNicRegistrationData();
										List<NicTransactionAttachment> docList = nicTransactionAttachmentService
												.getNicTransactionAttachments(
														nicTran.getTransactionId(),
														new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE },
														null);
										if (docList != null
												&& docList.size() == 1) {
											NicTransactionAttachment tranAttach = docList
													.get(0);
											info.setBase64(jcifs.util.Base64
													.encode(tranAttach
															.getDocData()));
										}
										if (nicReg != null) {
											info.setName(HelperClass.createFullName(
													nicReg.getSurnameFull(),
													nicReg.getMiddlenameFull(),
													nicReg.getFirstnameFull()));
											info.setGender(nicReg.getGender());
											if (nicReg.getDateOfBirth() != null) {
												info.setDateOfBirth(DateUtil.parseDate(
														nicReg.getDateOfBirth(),
														DateUtil.FORMAT_YYYYMMDD));
											}
										}
										info.setNationalityCode(nicReg
												.getNationality());
										info.setPlaceOfBirthCode(nicReg
												.getPlaceOfBirth());
										if (StringUtils.isNotBlank(nicDoc
												.getPlaceOfIssueCode())) {
											info.setPlaceOfDocIssue(this.getSiteName(nicDoc
													.getPlaceOfIssueCode()));
										}
									}
									response.setMessage(Contants.MESSAGE_SUCCESS);
									listInfo.add(info);
								}
							}
						}
					}
				}
				response.setCode(Contants.CODE_SUCCESS);
				response.setData(listInfo);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error("error - findValidPassportToCancel: " + e.getMessage());
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e)
					+ " - findValidPassportToCancel fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return response;
	}

	/**
	 * @param handover
	 * @return
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadHandoverAFromA")
	public ResponseBase uploadHandoverAFromA(HandoverA handover) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_DSA);
		eppWsLog.setUrlRequest(Contants.URL_UPLOAD_HANDOVER_A);

		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(handover));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();

		}
		try {
			if (handover != null) {
				String nameApi = "uploadHandoverAFromA";
				if (!this.checkConnectApi(nameApi, handover.getSiteCode())) {
					response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
					return response;
				}

				// Kiem tra ho so da day du chua?
				/*
				 * ResponseBase error = this.checkValidateHandover(handover);
				 * if(error != null){ return error; }
				 */
				// set KeyId Ws Log
				eppWsLog.setKeyId(handover.getPackageId());

				for (ReceiptManager rm : handover.getReceipts()) {
					if (rm.getHandovers() != null) {
						for (DetailHandover detailA : rm.getHandovers()) {
							BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
									.findTransactionById(detailA
											.getTransactionId());
							if (!baseFindTran.isError()
									|| baseFindTran.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseFindTran, eppWsLog, response,
										handover);
							}
							NicTransaction txn = baseFindTran.getModel();
							if (txn == null) {
								response.setCode(Contants.RESPONSE_CODE_FAIL_NOT_ENOUGH_HS);
								response.setMessage("Chưa upload đủ hồ sơ. Hồ sơ đang thiếu hiện tại: "
										+ detailA.getTransactionId());
								return response;
							}
						}
					}
				}

				// logger.info("start upload handover A to nic time: " +
				// HelperClass.convertDateToString1(new Date()));

				// Đã tồn tại hồ sơ trong danh sách
				Boolean checkTempGlobal = true;

				// Kiểm tra đã tồn tại của danh sách
				BaseModelSingle<NicListHandover> baseHan = listHandoverService
						.findByPackageId(new NicListHandoverId(handover
								.getPackageId(), NicListHandover.TYPE_LIST_A));
				if (!baseHan.isError() || baseHan.getMessage() != null) {
					return this.checkExistAndSaveException(baseHan, eppWsLog,
							response, handover);
				}
				NicListHandover hanCheck = baseHan.getModel();
				if (hanCheck == null) {
					// logger.info("start save handover A. packageId: " +
					// handover.getPackageId());
					NicListHandover nicHandover = new NicListHandover();
					nicHandover.setId(new NicListHandoverId(handover
							.getPackageId(), NicListHandover.TYPE_LIST_A));
					// nicHandover.setPackageId(handover.getPackageId());
					nicHandover.setSiteCode(handover.getSiteCode());

					if (StringUtils.isNotEmpty(handover.getApproveDate())) {
						Date date = HelperClass.convertStringToDate(handover
								.getApproveDate(), handover.getApproveDate()
								.length());
						nicHandover.setApproveDate(date);
					}

					if (StringUtils.isNotEmpty(handover.getOfferDate())) {
						Date date = HelperClass.convertStringToDate(handover
								.getOfferDate(), handover.getOfferDate()
								.length());
						nicHandover.setProposalDate(date);
					}
					nicHandover.setApproveUser(handover.getApproveUserName());
					nicHandover.setProposalUser(handover.getOfferUserName());
					nicHandover.setPackageName(Contants.CODE_PACKAGE_NAME_A);
					// nicHandover.setTypeList(NicListHandover.TYPE_LIST_A);
					nicHandover.setCreateDate(Calendar.getInstance().getTime());
					nicHandover.setCreateBy(Contants.CREATE_BY_SYSTEM);
					nicHandover.setHandoverStatus(0);
					// nicHandover.setStatusSyncXl(true);
					nicHandover.setCountTransaction(handover.getCount());

					nicHandover.setProposalName(handover.getProposalName());
					nicHandover.setApproveName(handover.getApproveName());
					nicHandover.setApprovePosition(handover
							.getApprovePosition());
					nicHandover.setCreatorName(handover.getCreatorName());
					nicHandover.setUpdateBy(handover.getUpdateBy());
					if (handover.getUpdateDate() != null) {
						Date date = HelperClass.convertStringToDate(handover
								.getUpdateDate(), handover.getUpdateDate()
								.length());
						nicHandover.setUpdateDate(date);
					}
					nicHandover.setUpdateByName(handover.getUpdateByName());
					nicHandover.setPlaceOfDelivery(handover
							.getPlaceOfDelivery());
					if (handover.getDateOfDelivery() != null) {
						Date date = HelperClass.convertStringToDate(handover
								.getDateOfDelivery(), handover
								.getDateOfDelivery().length());
						nicHandover.setDateOfDelivery(date);
					}
					// saveHandover
					BaseModelSingle<Void> baseSaveHan = listHandoverService
							.saveHandover(nicHandover);
					if (!baseSaveHan.isError()
							|| baseSaveHan.getMessage() != null) {
						return this.checkExistAndSaveException(baseSaveHan,
								eppWsLog, response, handover);
					}
				}
				for (ReceiptManager rm : handover.getReceipts()) {
					BaseModelList<EppRecieptManager> baseFindAllRM = rmService
							.findAllRecieptManager(rm.getReceiptNo());
					if (!baseFindAllRM.isError()
							|| baseFindAllRM.getMessage() != null) {
						return this.checkExistAndSaveExceptions(baseFindAllRM,
								eppWsLog, response, handover);
					}
					List<EppRecieptManager> list = baseFindAllRM.getListModel();
					if (list == null || list.size() == 0) {
						EppRecieptManager epp = new EppRecieptManager();
						epp.setRecieptNo(rm.getReceiptNo());
						epp.setOfficeName(rm.getOfficeName());
						epp.setFullname(rm.getName());
						if (StringUtils.isNotEmpty(rm.getDob())) {
							Date date = HelperClass.convertStringToDateTk(
									rm.getDob(), 0);
							epp.setDob(date);
							if (rm.getDob() != null) {
								switch (rm.getDob().length()) {
								case 4:
									epp.setDateDob("Y");
									break;
								case 6:
									epp.setDateDob("M");
									break;
								case 8:
									epp.setDateDob("D");
									break;
								default:
									break;
								}
							}
						}
						epp.setAddress(rm.getAddress());
						epp.setNinNumber(rm.getNin());
						epp.setPaymentAmount(rm.getPaymentAmount());
						epp.setCreateBy(rm.getCreateBy());
						epp.setCreateDate(Calendar.getInstance().getTime());
						epp.setRegSiteCode(rm.getRegSiteCode());
						epp.setDateOfIssue(rm.getDateOfIssue());
						epp.setNote(rm.getNote());
						epp.setPlaceOfReceipt(rm.getPlaceOfReciept());
						if (StringUtils.isNotEmpty(rm.getDeliveryAtOffice()))
							epp.setDeliveryAtOffice(rm.getDeliveryAtOffice()
									.equals("Y") ? true : false);
						epp.setDeliveryOffice(rm.getDeliveryOffice());
						epp.setAmountOfPassport(rm.getAmountOfPassport());
						epp.setAmountOfRegistration(rm
								.getAmountOfRegistration());
						epp.setAmountOfPerson(rm.getAmountOfPerson());
						epp.setAmountOfImage(rm.getAmountOfImage());
						epp.setDocumentType(rm.getDocumentType());
						epp.setPrevPassportNo(rm.getPrevPassportNo());
						epp.setAddedDocuments(rm.getAddedDocuments());
						epp.setDocumentaryNo(rm.getDocumentaryNo());
						epp.setDocumentaryIssued(rm.getDocumentaryIssued());
						epp.setStatus(rm.getStatus());
						epp.setIsDelivered(rm.getIsDelivered());
						if (StringUtils.isNotEmpty(rm.getIsPostOffice()))
							epp.setIsPostOffice(rm.getIsPostOffice()
									.equals("Y") ? true : false);
						epp.setNoteOfDelivery(rm.getNoteOfDelivery());
						epp.setSignName(rm.getSignName());
						epp.setDocOfDelivery(rm.getDocOfDelivery());
						epp.setDocmentaryOffice(rm.getDocumentaryOffice());
						epp.setDocmentaryAddress(rm.getDocumentaryAddress());
						epp.setListCode(rm.getListCode());
						if (StringUtils.isNotEmpty(rm.getInputCompleted()))
							epp.setInputCompleted(rm.getInputCompleted()
									.equals("Y") ? true : false);
						epp.setDeletedBy(rm.getDeletedBy());
						if (rm.getDeletedDate() != null) {
							Date date = HelperClass.convertStringToDate(rm
									.getDeletedDate(), rm.getDeletedDate()
									.length());
							epp.setDeletedDate(date);
						}
						/* epp.setDeletedDate(rm.getDeletedDate()); */
						epp.setDeletedName(rm.getDeletedName());
						epp.setDeletedReason(rm.getDeletedReason());
						epp.setReceivedBy(rm.getReceivedBy());
						epp.setCreateByName(rm.getCreateByName());
						BaseModelSingle<Boolean> baseSaveRM = rmService
								.saveOrUpdateNew(epp);
						if (!baseSaveRM.isError()
								|| baseSaveRM.getMessage() != null) {
							return this.checkExistAndSaveException(baseSaveRM,
									eppWsLog, response, rm);
						}
					}
					if (rm.getHandovers() != null) {
						for (DetailHandover detailA : rm.getHandovers()) {
							BaseModelSingle<EppListHandoverDetail> baseGetHD = eppListHandoverDetailService
									.getListPackageByPackageIdAndTranID(
											detailA.getPackageId(),
											detailA.getTransactionId(),
											NicListHandover.TYPE_LIST_A);
							if (!baseGetHD.isError()
									|| baseGetHD.getMessage() != null) {

								return this
										.checkExistAndSaveException(baseGetHD,
												eppWsLog, response, handover);
							}
							EppListHandoverDetail tp = baseGetHD.getModel();
							if (tp == null) {
								tp = new EppListHandoverDetail();
								tp.setPackageId(detailA.getPackageId());
								tp.setTransactionId(detailA.getTransactionId());
								tp.setProposalStage(detailA.getOfferStage());
								tp.setApproveStage(detailA.getApproveStage());
								tp.setProposalNote(detailA.getNoteOffer());
								tp.setApproveNote(detailA.getNoteApprove());
								tp.setTypeList(NicListHandover.TYPE_LIST_A);
								BaseModelSingle<Boolean> baseHD = eppListHandoverDetailService
										.saveHandoverDetail(tp);
								if (!baseHD.isError()
										|| baseHD.getMessage() != null) {

									return this
											.checkExistAndSaveException(baseHD,
													eppWsLog, response, detailA);
								}

								// logger.info("===listHandoverDetail status: "
								// + success);

							}

							/* Đưa vào hàng đợi đồng bộ sang các ttxl */

							// reset chạy lại job check lần nữa
							BaseModelSingle<NicUploadJob> baseNicUp = uploadJobService
									.findUploadJobByTransId(detailA
											.getTransactionId());
							if (!baseNicUp.isError()
									|| baseNicUp.getMessage() != null) {
								return this.checkExistAndSaveException(
										baseNicUp, eppWsLog, response, detailA);
							}
							NicUploadJob nicUp = baseNicUp.getModel();
							NicTransaction txn = nicUp.getNicTransaction();
							if (nicUp != null
									&& txn != null
									&& !txn.getRegSiteCode().equals(
											Contants.QUEUE_RECEIVER_MB)
									&& !txn.getRegSiteCode().equals(
											Contants.QUEUE_RECEIVER_MT)
									&& !txn.getRegSiteCode().equals(
											Contants.QUEUE_RECEIVER_MN)) {
								// this.resetWorkflowCheckData(nicUp);

								txn.setPaBlacklistId(detailA.getPaBlacklistId());
								txn.setPaInqBllUser(detailA.getPaInqBllUser());
								txn.setPaArchiveCode(detailA.getPaArchiveCode());
								if (StringUtils.isNotEmpty(detailA
										.getPaSearchBio()))
									txn.setPaSearchBio(detailA.getPaSearchBio()
											.equals("Y") ? true : false);
								if (detailA.getPaJoinPersonDate() != null) {
									Date date = HelperClass
											.convertStringToDate(
													detailA.getPaJoinPersonDate(),
													detailA.getPaJoinPersonDate()
															.length());
									txn.setPaJoinPersonDate(date);
								}
								if (detailA.getPaSaveDocDate() != null) {
									Date date = HelperClass
											.convertStringToDate(detailA
													.getPaSaveDocDate(),
													detailA.getPaSaveDocDate()
															.length());
									txn.setPaSaveDocDate(date);
								}
								if (detailA.getPaSearchObjDate() != null) {
									Date date = HelperClass
											.convertStringToDate(
													detailA.getPaSearchObjDate(),
													detailA.getPaSearchObjDate()
															.length());
									txn.setPaSearchObjDate(date);
								}
								if (StringUtils.isNotBlank(detailA
										.getPersonStage())
										&& detailA.getPersonStage().length() == 2) {
									txn.setMatchedTypePerson(detailA
											.getPersonStage());
								}
								BaseModelSingle<Boolean> baseSaveOUTran = nicTransactionService
										.saveOrUpdateTransaction(txn);
								if (!baseSaveOUTran.isError()
										|| baseSaveOUTran.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseSaveOUTran, eppWsLog, response,
											handover);
								}
							}
							if (detailA.getPayments() != null) {
								for (PaymentDetail payment : detailA
										.getPayments()) {

									// check fail
									BaseModelSingle<NicTransactionPayment> baseTP = nicTransactionPaymentDao
											.findGetPaymentByTransaction(payment
													.getTransactionId());
									if (!baseTP.isError()
											|| baseTP.getMessage() != null) {

										return this.checkExistAndSaveException(
												baseTP, eppWsLog, response,
												payment);
									}

									NicTransactionPayment payments = baseTP
											.getModel();
									if (payments == null)
										continue;
									BaseModelSingle<NicTransactionPaymentDetail> baseFindDP = paymentDetailService
											.findDetailPaymentByTransactionId(
													payments.getPaymentId(),
													payment.getTypePayment(),
													payment.getSubTypePayment());
									if (!baseFindDP.isError()
											|| baseFindDP.getMessage() != null) {

										return this.checkExistAndSaveException(
												baseFindDP, eppWsLog, response,
												payment);
									}
									NicTransactionPaymentDetail payDetail = baseFindDP
											.getModel();
									if (payDetail != null)
										continue;// Đã tồn tại
									// logger.info("start save detail payment, paymentId: "
									// + payments.getPaymentId());
									NicTransactionPaymentDetail pay = new NicTransactionPaymentDetail();
									pay.setPaymentId(payments.getPaymentId());
									pay.setTypePayment(payment.getTypePayment());
									pay.setSubTypePayment(payment
											.getSubTypePayment());
									pay.setPaymentAmount(payment
											.getPaymentAmount());
									pay.setNote(payment.getNamePayment());
									pay.setStatusFee(payment.getStatusFee()
											.equals("Y") ? true : false);
									pay.setCreateBy(Contants.CREATE_BY_SYSTEM);
									pay.setCreateDate(Calendar.getInstance()
											.getTime());

									BaseModelSingle<Boolean> basePD = paymentDetailService
											.saveOrUpdatePaymentDetail(pay);
									// check fail
									if (!basePD.isError()
											|| basePD.getMessage() != null) {

										return this.checkExistAndSaveException(
												basePD, eppWsLog, response,
												payment);
									}
								}
							}
						}
						if (rm.getBills() != null) {
							for (ReceiptBill rb : rm.getBills()) {
								BaseModelSingle<DetailRecieptFee> baseFindDR = drFeeService
										.findDetailRecieptFee(
												rb.getReceiptNo(),
												rb.getCode(), rb.getNumber());
								if (!baseFindDR.isError()
										|| baseFindDR.getMessage() != null) {
									return this.checkExistAndSaveException(
											baseFindDR, eppWsLog, response,
											handover);
								}
								DetailRecieptFee fees = baseFindDR.getModel();
								if (fees != null)
									continue;
								// logger.info("start save receipt bill, code: "
								// + rb.getCode() + ", number: " +
								// rb.getNumber());
								DetailRecieptFee fee = new DetailRecieptFee();
								fee.setRecieptNo(rb.getReceiptNo());
								fee.setCodeBill(rb.getCode());
								fee.setNumberBill(rb.getNumber());
								fee.setPrice(rb.getPrice());
								fee.setPriceFlag(rb.getBillFlag());
								fee.setDescription(rb.getDescription());
								fee.setCreateBy(Contants.CREATE_BY_SYSTEM);
								fee.setCreateDate(Calendar.getInstance()
										.getTime());
								fee.setCashierName(rb.getCashierName());
								fee.setCreateByName(rb.getCreateByName());
								if (rb.getDateOfReceipt() != null) {
									Date date = HelperClass
											.convertStringToDate(rb
													.getDateOfReceipt(), rb
													.getDateOfReceipt()
													.length());
									fee.setDateOfReceipt(date);
								}
								fee.setCustomerName(rb.getCustomerName());
								BaseModelSingle<Boolean> baseSaveRB = drFeeService
										.saveOrUpdateNew(fee);
								// check save RecieptBill
								if (!baseSaveRB.isError()
										|| baseSaveRB.getMessage() != null) {

									return this.checkExistAndSaveException(
											baseSaveRB, eppWsLog, response, rb);
								}
							}
						}

						if (rm.getFeeRecieptPayment() != null) {
							for (FeeRecieptPaymentModel fr : rm
									.getFeeRecieptPayment()) {
								BaseModelList<FeeRecieptPayment> baseFindAllRP = feeRecieptPaymentService
										.findAllFeeRecieptPayment(fr
												.getRecieptNo());
								if (!baseFindAllRP.isError()
										|| baseFindAllRP.getMessage() != null) {
									return this.checkExistAndSaveExceptions(
											baseFindAllRP, eppWsLog, response,
											handover);
								}
								List<FeeRecieptPayment> listExist = baseFindAllRP
										.getListModel();
								// Xoa di neu da ton tai
								if (listExist != null && listExist.size() > 0) {
									BaseModelSingle<Boolean> baseDelRP = feeRecieptPaymentService
											.deleteObject(listExist);
									if (!baseDelRP.isError()
											|| baseDelRP.getMessage() != null) {
										return this.checkExistAndSaveException(
												baseDelRP, eppWsLog, response,
												handover);
									}
								}
								// logger.info("start save receipt bill, code: "
								// + rb.getCode() + ", number: " +
								// rb.getNumber());
								FeeRecieptPayment fee = new FeeRecieptPayment();
								fee.setRecieptNo(fr.getRecieptNo());
								fee.setTypePayment(fr.getTypePayment());
								fee.setPrice(fr.getPrice());
								fee.setDescription(fr.getDescription());
								fee.setUnit(fr.getUnit());
								fee.setTotal(fr.getTotal());
								fee.setAmount(fr.getAmount());
								fee.setCreateBy(fr.getCreateBy());
								fee.setCreateDate(Calendar.getInstance()
										.getTime());
								fee.setCreateByName(fr.getCreateByName());
								BaseModelSingle<Boolean> baseSaveRP = feeRecieptPaymentService
										.saveOrUpdateNew(fee);
								// check save FeeRecieptPayment
								if (!baseSaveRP.isError()
										|| baseSaveRP.getMessage() != null) {

									return this.checkExistAndSaveException(
											baseSaveRP, eppWsLog, response, fr);
								}
							}
						}
					}
				}

				// Kiểm tra lại số lượng hồ sơ với số lượgn bản ghi trong bản
				// trung gian đã đủ chưa
				BaseModelList<EppListHandoverDetail> baseGetLP = eppListHandoverDetailService
						.getListPackageByPackageId(handover.getPackageId(),
								NicListHandover.TYPE_LIST_A);
				// check get List Handover Detail
				if (!baseGetLP.isError() || baseGetLP.getMessage() != null) {

					return this.checkExistAndSaveExceptions(baseGetLP,
							eppWsLog, response, handover);
				}
				List<EppListHandoverDetail> checklist = baseGetLP
						.getListModel();
				if (checklist == null
						|| checklist.size() != handover.getCount()) {
					response.setCode(Contants.RESPONSE_CODE_FAIL_NOT_ENOUGH_HS);
					response.setMessage("Chưa upload đủ hồ sơ.");
					return response;
				}

				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
			} else {
				// logger.warn("HandoverA is not null");
				response.setMessage("HandoverA không được phép null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			logger.error(e.getMessage() + " - uploadHandoverA fail.");
			// save log
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - uploadHandoverA fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		}
		return response;
	}

	/*
	 * tìm kiếm - tách cá nhân
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findPersonToSplit")
	public Response<PersonToHandingDetail> findPersonToSplit(
			TransactionIdInput tranId) {
		Response<PersonToHandingDetail> rep = new Response<PersonToHandingDetail>();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		PersonToHandingDetail personDetail = null;
		TransactionDetailToHanding tranDetail = null;
		OrgPersonDetailToHanding orgPersonDetail = null;
		List<NicTransaction> listTran = null;
		try {
			if (tranId != null) {
				if (StringUtils.isNotBlank(tranId.getTransactionId())) {
					personDetail = new PersonToHandingDetail();
					String[] listTranId = new String[1];
					listTranId[0] = tranId.getTransactionId();
					NicDocumentData docData = documentDataService
							.findValidDocByListTranId(listTranId);
					NicTransaction tran = null;
					NicRegistrationData reg = null;
					if (docData != null) {
						tran = docData.getNicTransaction();
					} else {
						BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
								.findTransactionById(tranId.getTransactionId());
						if (!baseFindTran.isError()
								|| baseFindTran.getMessage() != null) {
							throw new Exception(baseFindTran.getMessage());
						}
						tran = baseFindTran.getModel();
					}
					if (tran != null) {
						tranDetail = new TransactionDetailToHanding();
						tranDetail.setTransactionId(tran.getTransactionId());
						reg = tran.getNicRegistrationData();
						tranDetail.setName(HelperClass.createFullName(
								reg.getSurnameFull(), reg.getMiddlenameFull(),
								reg.getFirstnameFull()));
						tranDetail.setGender(reg.getGender());
						tranDetail
								.setDateOfBirth(reg.getDateOfBirth() != null ? DateUtil
										.parseDate(reg.getDateOfBirth(),
												DateUtil.FORMAT_YYYYMMDD)
										: null);
						if (StringUtils.isNotBlank(reg.getPlaceOfBirth())) {
							CodeValues code = codesService.findByStringCodeId(
									Contants.CODE_ID_CODE_BIRTH_PLACE,
									reg.getPlaceOfBirth());
							if (code == null) {
								code = codesService.findByStringCodeId(
										CodeValues.CODE_ID_COUNTRY,
										reg.getPlaceOfBirth());
							}
							tranDetail
									.setPlaceOfBirth(code != null ? code
											.getCodeValueDesc() : reg
											.getPlaceOfBirth());
						}
						tranDetail.setIdNumber(tran.getNin());
						tranDetail
								.setDateOfIdIssue(reg.getDateNin() != null ? DateUtil
										.parseDate(reg.getDateNin(),
												DateUtil.FORMAT_YYYYMMDD)
										: null);
						tranDetail.setReceiptNo(tran.getRecieptNo());
						tranDetail
								.setResidentAddress(createResidentAddress(reg));
						tranDetail.setMatchType(tran.getMatchedTypePerson());
						tranDetail.setMatchedPs(tran.getPersonCode());
						if (docData != null) {
							tranDetail.setPassportNo(docData.getId()
									.getPassportNo()); // tạm chưa xác định.
							tranDetail.setPassportStatus(docData.getStatus());
						}
						EppPerson person = eppPersonService
								.findPersonByPersonCode(tran.getPersonCode());
						if (person != null
								&& StringUtils
										.isNotBlank(person.getOrgPerson())) {
							person = eppPersonService
									.findPersonByPersonCode(person
											.getOrgPerson());
							listTran = nicTransactionService
									.findTranByPersonCode(person
											.getPersonCode());
							listTranId = new String[listTran.size()];
							int index = 0;
							for (NicTransaction txn : listTran) {
								listTranId[index] = txn.getTransactionId();
								index++;
							}
							NicDocumentData doc = documentDataService
									.findValidDocByListTranId(listTranId);
							if (doc != null) {
								docData = doc;
								tran = doc.getNicTransaction();
							}
						}
						if (person != null) {
							orgPersonDetail = new OrgPersonDetailToHanding();
							reg = tran.getNicRegistrationData();
							orgPersonDetail.setName(HelperClass.createFullName(
									reg.getSurnameFull(),
									reg.getMiddlenameFull(),
									reg.getFirstnameFull()));
							orgPersonDetail.setGender(reg.getGender());
							orgPersonDetail
									.setDateOfBirth(reg.getDateOfBirth() != null ? DateUtil
											.parseDate(reg.getDateOfBirth(),
													DateUtil.FORMAT_YYYYMMDD)
											: null);
							if (StringUtils.isNotBlank(reg.getPlaceOfBirth())) {
								CodeValues code = codesService
										.findByStringCodeId(
												Contants.CODE_ID_CODE_BIRTH_PLACE,
												reg.getPlaceOfBirth());
								if (code == null) {
									code = codesService.findByStringCodeId(
											CodeValues.CODE_ID_COUNTRY,
											reg.getPlaceOfBirth());
								}
								orgPersonDetail
										.setPlaceOfBirth(code != null ? code
												.getCodeValueDesc() : reg
												.getPlaceOfBirth());
							}
							orgPersonDetail.setIdNumber(tran.getNin());
							if (StringUtils.isNotBlank(reg.getAddressNin())) {
								CodeValues code = codesService
										.findByStringCodeId(
												Contants.CODE_ID_CODE_ID_PLACE,
												reg.getAddressNin());
								orgPersonDetail
										.setPlaceOfIdIssue(code != null ? code
												.getCodeValueDesc() : null);
							}
							orgPersonDetail
									.setDateOfIdIssue(reg.getDateNin() != null ? DateUtil
											.parseDate(reg.getDateNin(),
													DateUtil.FORMAT_YYYYMMDD)
											: null);
							orgPersonDetail.setReceiptNo(tran.getRecieptNo());
							orgPersonDetail
									.setResidentAddress(createResidentAddress(reg));
							orgPersonDetail.setPersonCode(person
									.getPersonCode());
							orgPersonDetail.setOrgPerson(person.getOrgPerson());
							// để các thông tin hộ chiếu chưa xác định.
							if (docData != null) {
								orgPersonDetail.setPassportNo(docData.getId()
										.getPassportNo());
								orgPersonDetail.setDateOfPPExpiry(docData
										.getDateOfExpiry() != null ? DateUtil
										.parseDate(docData.getDateOfExpiry(),
												DateUtil.FORMAT_YYYYMMDD)
										: null);
								orgPersonDetail.setDateOfIdIssue(docData
										.getDateOfIssue() != null ? DateUtil
										.parseDate(docData.getDateOfIssue(),
												DateUtil.FORMAT_YYYYMMDD)
										: null);
								if (StringUtils.isNotBlank(docData
										.getPlaceOfIssueCode())) {
									orgPersonDetail.setPlaceOfPPIssue(this
											.getSiteName(docData
													.getPlaceOfIssueCode()));
								}
								orgPersonDetail.setStatus(docData.getStatus());
							}
						}
					}
					personDetail.setOrgPerson(orgPersonDetail);
					personDetail.setTransaction(tranDetail);
					rep.setData(personDetail);
					rep.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					if (personDetail.getTransaction() != null) {
						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(Contants.URL_FIND_PERSON_TO_SPLIT,
								1, null);
					}
				} else {
					rep.setCode(Contants.CODE_INPUT_FAILD);
					rep.setMessage("transactionId"
							+ Contants.MESSAGE_INPUT_NULL);
				}
			} else {
				rep.setCode(Contants.CODE_INPUT_FAILD);
				rep.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			rep.setCode(Contants.CODE_THROW_EXCEPTION);
			rep.setMessage(Contants.MESSAGE_EXCEPTION);
			EppWsLog eppWsLog = new EppWsLog();
			eppWsLog.setType(Contants.TYPE_FPTS);
			eppWsLog.setUrlRequest(Contants.URL_FIND_PERSON_TO_SPLIT);
			try {
				eppWsLog.setDataRequest(new ObjectMapper()
						.writeValueAsString(tranId));
			} catch (IOException e1) {
				logger.error(e1.getMessage() + "convert object to json fail.");
				e1.printStackTrace();
			}
			eppWsLog.setKeyId(tranId.getTransactionId());
			eppWsLog.setDataResponse(rep.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - findPersonToSplit fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rep;
	}

	/*
	 * tìm kiếm - khớp cá nhân
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findPersonToMatch")
	public Response<PersonToMatchDetail> findPersonToMatch(
			TransactionDetailToHanding tranInput) {
		Response<PersonToMatchDetail> rep = new Response<PersonToMatchDetail>();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		PersonToMatchDetail personDetail = null;
		TransactionDetailToHanding tranDetail = null;
		List<OrgPersonDetailToHanding> listOrgPersonDetail = null;
		try {
			if (tranInput != null) {
				if (StringUtils.isNotBlank(tranInput.getTransactionId())
						&& !(StringUtils.isBlank(tranInput.getName())
								&& StringUtils.isBlank(tranInput.getGender())
								&& StringUtils.isBlank(tranInput
										.getDateOfBirth()) && StringUtils
									.isBlank(tranInput.getPassportNo()))) {
					personDetail = new PersonToMatchDetail();
					String[] listTranId = new String[1];
					listTranId[0] = tranInput.getTransactionId();
					NicDocumentData docData = documentDataService
							.findValidDocByListTranId(listTranId);
					NicTransaction tran = null;
					NicRegistrationData reg = null;
					List<NicTransaction> listTranRs = null;
					if (docData != null) {
						tran = docData.getNicTransaction();
					} else {
						BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
								.findTransactionById(tranInput
										.getTransactionId());
						if (!baseFindTran.isError()
								|| baseFindTran.getMessage() != null) {
							throw new Exception(baseFindTran.getMessage());
						}
						tran = baseFindTran.getModel();
					}
					if (tran != null) {
						tranDetail = new TransactionDetailToHanding();
						tranDetail.setTransactionId(tran.getTransactionId());
						reg = tran.getNicRegistrationData();
						tranDetail.setName(HelperClass.createFullName(
								reg.getSurnameFull(), reg.getMiddlenameFull(),
								reg.getFirstnameFull()));
						tranDetail.setGender(reg.getGender());
						tranDetail
								.setDateOfBirth(reg.getDateOfBirth() != null ? DateUtil
										.parseDate(reg.getDateOfBirth(),
												DateUtil.FORMAT_YYYYMMDD)
										: null);
						if (StringUtils.isNotBlank(reg.getPlaceOfBirth())) {
							CodeValues code = codesService.findByStringCodeId(
									Contants.CODE_ID_CODE_BIRTH_PLACE,
									reg.getPlaceOfBirth());
							if (code == null) {
								code = codesService.findByStringCodeId(
										CodeValues.CODE_ID_COUNTRY,
										reg.getPlaceOfBirth());
							}
							tranDetail
									.setPlaceOfBirth(code != null ? code
											.getCodeValueDesc() : reg
											.getPlaceOfBirth());
						}
						tranDetail.setIdNumber(tran.getNin());
						tranDetail
								.setDateOfIdIssue(reg.getDateNin() != null ? DateUtil
										.parseDate(reg.getDateNin(),
												DateUtil.FORMAT_YYYYMMDD)
										: null);
						tranDetail.setReceiptNo(tran.getRecieptNo());
						tranDetail
								.setResidentAddress(createResidentAddress(reg));
						tranDetail.setMatchType(tran.getMatchedTypePerson());
						tranDetail.setMatchedPs(tran.getPersonCode());
						if (docData != null) {
							tranDetail.setPassportNo(docData.getId()
									.getPassportNo()); // tạm chưa xác định.
							tranDetail.setPassportStatus(docData.getStatus());
						}

						if (StringUtils.isNotBlank(tranInput.getPassportNo())) {
							BaseModelList<NicDocumentData> baseFindDocData = documentDataService
									.findAllPassportByCondition(
											tranInput.getPassportNo(), null);
							if (!baseFindDocData.isError()
									|| baseFindDocData.getMessage() != null) {
								throw new Exception(
										baseFindDocData.getMessage());
							}
							List<NicDocumentData> listDoc = baseFindDocData
									.getListModel();
							if (listDoc != null && listDoc.size() > 0) {

								listTranRs = new ArrayList<NicTransaction>();
								for (NicDocumentData nicDocumentData : listDoc) {
									tran = nicDocumentData.getNicTransaction();
									reg = tran.getNicRegistrationData();
									if (!checkFieldInput(tranInput, reg)) {
										continue;
									}
									listTranRs.add(tran);
								}
							}
						} else {
							List<NicRegistrationData> listReg = nicRegistrationDataService
									.findRegByPersonInfo(tranInput.getName(),
											tranInput.getGender(),
											tranInput.getDateOfBirth());
							if (listReg != null && listReg.size() > 0) {
								listTranRs = new ArrayList<NicTransaction>();
								listTranId = new String[listReg.size()];
								int index = 0;
								for (NicRegistrationData nicReg : listReg) {
									listTranId[index] = nicReg
											.getTransactionId();
									index++;
								}
								if (listTranId.length > 0) {
									listTranRs.addAll(nicTransactionService
											.findAllByListTranId(listTranId));
								}
							}
						}
						if (listTranRs != null && listTranRs.size() > 0) {
							listOrgPersonDetail = new ArrayList<OrgPersonDetailToHanding>();
							for (NicTransaction nicTran : listTranRs) {
								tran = nicTran;
								EppPerson person = eppPersonService
										.findPersonByPersonCode(tran
												.getPersonCode());
								if (person != null) {
									OrgPersonDetailToHanding orgPersonDetail = new OrgPersonDetailToHanding();
									orgPersonDetail.setName(HelperClass
											.createFullName(
													reg.getSurnameFull(),
													reg.getMiddlenameFull(),
													reg.getFirstnameFull()));
									orgPersonDetail.setGender(reg.getGender());
									orgPersonDetail
											.setDateOfBirth(reg
													.getDateOfBirth() != null ? DateUtil.parseDate(
													reg.getDateOfBirth(),
													DateUtil.FORMAT_YYYYMMDD)
													: null);
									if (StringUtils.isNotBlank(reg
											.getPlaceOfBirth())) {
										CodeValues code = codesService
												.findByStringCodeId(
														CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
														reg.getPlaceOfBirth());
										if (code == null) {
											code = codesService
													.findByStringCodeId(
															CodeValues.CODE_ID_COUNTRY,
															reg.getPlaceOfBirth());
										}
										orgPersonDetail
												.setPlaceOfBirth(code != null ? code
														.getCodeValueDesc()
														: reg.getPlaceOfBirth());
									}
									orgPersonDetail.setIdNumber(tran.getNin());
									if (StringUtils.isNotBlank(reg
											.getAddressNin())) {
										CodeValues code = codesService
												.findByStringCodeId(
														Contants.CODE_ID_CODE_ID_PLACE,
														reg.getAddressNin());
										orgPersonDetail
												.setPlaceOfIdIssue(code != null ? code
														.getCodeValueDesc()
														: null);
									}
									orgPersonDetail.setDateOfIdIssue(reg
											.getDateNin() != null ? DateUtil
											.parseDate(reg.getDateNin(),
													DateUtil.FORMAT_YYYYMMDD)
											: null);
									orgPersonDetail.setReceiptNo(tran
											.getRecieptNo());
									orgPersonDetail
											.setResidentAddress(createResidentAddress(reg));
									orgPersonDetail.setPersonCode(person
											.getPersonCode());
									orgPersonDetail.setOrgPerson(person
											.getOrgPerson());
									// để các thông tin hộ chiếu chưa xác
									// định.
									if (docData != null) {
										orgPersonDetail.setPassportNo(docData
												.getId().getPassportNo());
										orgPersonDetail
												.setDateOfPPExpiry(docData
														.getDateOfExpiry() != null ? DateUtil.parseDate(
														docData.getDateOfExpiry(),
														DateUtil.FORMAT_YYYYMMDD)
														: null);
										orgPersonDetail
												.setDateOfIdIssue(docData
														.getDateOfIssue() != null ? DateUtil.parseDate(
														docData.getDateOfIssue(),
														DateUtil.FORMAT_YYYYMMDD)
														: null);
										if (StringUtils.isNotBlank(docData
												.getPlaceOfIssueCode())) {
											orgPersonDetail
													.setPlaceOfPPIssue(this
															.getSiteName(docData
																	.getPlaceOfIssueCode()));
										}
										orgPersonDetail.setStatus(docData
												.getStatus());
									}
									listOrgPersonDetail.add(orgPersonDetail);
								}
							}
						}
					}
					personDetail.setListOrgPerson(listOrgPersonDetail);
					personDetail.setTransaction(tranDetail);
					rep.setData(personDetail);
					rep.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					if (listOrgPersonDetail != null) {
						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(Contants.URL_FIND_PERSON_TO_MATCH,
								1, null);
					}
				} else {
					rep.setCode(Contants.CODE_INPUT_FAILD);
					rep.setMessage("Cần có ít nhất một giá trị tìm kiếm."
							+ Contants.MESSAGE_INPUT_NULL);
				}
			} else {
				rep.setCode(Contants.CODE_INPUT_FAILD);
				rep.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			rep.setCode(Contants.CODE_THROW_EXCEPTION);
			rep.setMessage(Contants.MESSAGE_EXCEPTION);
			EppWsLog eppWsLog = new EppWsLog();
			eppWsLog.setType(Contants.TYPE_FPTM);
			eppWsLog.setUrlRequest(Contants.URL_FIND_PERSON_TO_MATCH);
			try {
				eppWsLog.setDataRequest(new ObjectMapper()
						.writeValueAsString(tranInput));
			} catch (IOException e1) {
				logger.error(e1.getMessage() + "convert object to json fail.");
				e1.printStackTrace();

			}
			eppWsLog.setKeyId(tranInput.getTransactionId());
			eppWsLog.setDataResponse(rep.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - findPersonToMatch fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rep;
	}

	/*
	 * Nhập hộ chiếu chưa có trên hệ thống
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/createNewPassport")
	public ResponseBase createNewPassport(PassportCancelDetail input) {
		ResponseBase rep = new ResponseBase();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		try {
			if (input != null) {
				if (StringUtils.isNotBlank(input.getTransactionId())
						&& StringUtils.isNotBlank(input.getPassportNo())
						&& StringUtils.isNotBlank(input.getDateOfDocIssue())
						&& StringUtils.isNotBlank(input.getDateOfDocExpiry())
						&& StringUtils.isNotBlank(input.getPlaceOfIssue())) {
					BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
							.findTransactionById(input.getTransactionId());
					if (!baseFindTran.isError()
							|| baseFindTran.getMessage() != null) {
						throw new Exception(baseFindTran.getMessage());
					}
					NicTransaction tran = baseFindTran.getModel();
					if (tran != null) {
						boolean checkExist = false;
						List<NicDocumentData> listDoc = documentDataService
								.findListDocByTranId(tran.getTransactionId());
						if (listDoc != null && listDoc.size() > 0) {
							for (NicDocumentData nicDoc : listDoc) {
								if (nicDoc
										.getStatus()
										.equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE)
										|| nicDoc
												.getStatus()
												.equals(NicDocumentData.DOCUMENT_DATA_STATUS_PERSONALIZED)) {
									checkExist = true;
									break;
								}
							}
						}
						if (!checkExist) {

							String transactionId = tran.getTransactionId();
							String documentNumber = "";
							String printedSite = "IN_A08_MB";
							String dateFormat = DateUtil.FORMAT_YYYYMMDD;
							documentNumber = input.getPassportNo();
							String documentType = "P";

							ParametersId id = new ParametersId();
							id.setParaName(Parameters.WS_LDS_API);
							id.setParaScope("API");
							Parameters param = parametersDao.findById(id);
							LdsResponseWsDto res = null;
							NicRegistrationData reg = tran
									.getNicRegistrationData();
							String dob = DateUtil.parseDate(
									reg.getDateOfBirth(),
									DateUtil.FORMAT_YYMMDD);
							String doe = DateUtil.parseDate(DateUtil.strToDate(
									input.getDateOfDocExpiry(), dateFormat),
									DateUtil.FORMAT_YYMMDD);
							if (param != null
									&& StringUtils.isNotBlank(param
											.getParaLobValue())) {
								LdsRequestWsDto request = new LdsRequestWsDto();
								request.setDateOfBirth(dob);
								request.setDateOfExpiry(doe);
								request.setDocumentNumber(documentNumber);
								request.setDocumentType(documentType);
								String finger01Position = "";
								String finger02Position = "";
								byte[] finger01 = null;
								byte[] finger02 = null;
								String issueState = "VNM";
								if (StringUtils.isNotBlank(reg.getFpEncode())) {
									String[] fpEncodes = StringUtils.split(
											reg.getFpEncode(), ",");
									if (fpEncodes.length >= 1)
										finger01Position = fpEncodes[0];
									if (fpEncodes.length >= 2)
										finger02Position = fpEncodes[1];

									finger01 = dataPackService
											.getTransactionDocument(
													tran,
													NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
													finger01Position);
									finger02 = dataPackService
											.getTransactionDocument(
													tran,
													NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
													finger02Position);
								}
								if (StringUtils.isNotBlank(finger01Position)
										&& finger01 != null) {
									request.setFinger01Position(finger01Position);
									request.setFinger02Position(finger02Position);
									request.setFinger01(Base64
											.encodeBase64String(finger01));
									request.setFinger02(Base64
											.encodeBase64String(finger02));
								}
								request.setIsEPassport(tran.getIsEpassport());
								request.setIssueState(issueState);
								request.setNameOfHolder(tran
										.getNicRegistrationData()
										.getSearchName());
								request.setNationality(reg.getNationality());
								request.setOptionalData(tran.getNin());

								byte[] mugshotColour = null;
								// get mugshot from transaction data
								mugshotColour = dataPackService
										.getTransactionDocument(
												tran,
												NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
												NicTransactionAttachment.DEFAULT_SERIAL_NO);

								if (mugshotColour != null) {
									request.setPhoto(Base64
											.encodeBase64String(mugshotColour));
								}

								request.setSex(reg.getGender());
								res = dataPackService.GetLDS(request,
										param.getParaLobValue());

							}
							String icao1 = "";
							String icao2 = "";
							if (res != null && res.getStatus() == 200) {
								icao1 = res.getMrzLine1();
								icao2 = res.getMrzLine2();

								// String OFFICER_ID = "SYSTEM";
								String WKSTN_ID = "SYSTEM";
								String status = NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE;
								Date newDate = new Date();
								Date doiDate = DateUtil.strToDate(
										input.getDateOfDocIssue(), dateFormat);
								Date doeDate = DateUtil.strToDate(
										input.getDateOfDocExpiry(), dateFormat);
								NicDocumentDataId docId = new NicDocumentDataId(
										transactionId, documentNumber);
								NicDocumentData documentData = new NicDocumentData();
								documentData.setId(docId);
								documentData.setCreateBy(input.getCreateBy());
								documentData.setCreateDatetime(newDate);
								documentData.setCreateWkstnId(WKSTN_ID);

								documentData.setStatus(status);
								documentData.setStatusUpdateTime(newDate);
								documentData.setPrintingSite(printedSite);
								documentData.setDateOfIssue(doiDate);
								documentData.setDateOfExpiry(doeDate);

								// documentData.setUpdateBy(OFFICER_ID);
								// documentData.setUpdateDatetime(newDate);
								// documentData.setUpdateWkstnId(WKSTN_ID);

								String dispatchId = "DSP."
										+ DateUtil
												.parseDate(
														new Date(),
														DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
								documentData.setDispatchId(dispatchId);
								documentData.setDispatchDatetime(newDate);
								documentData.setIcaoLine1(icao1);
								documentData.setIcaoLine2(icao2);
								documentData.setPassportType(documentType);
								documentData.setPlaceOfIssueCode(input
										.getPlaceOfIssue());
								documentDataDao.saveOrUpdate(documentData);

								rep.setCode(Contants.CODE_SUCCESS);
								rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
								/* Lưu bảng thống kê truyền nhận */
								this.saveOrUpRptData(
										Contants.URL_CREATE_NEW_PASSPORT, 1,
										tran.getRegSiteCode());

							} else {
								rep.setCode(Contants.CODE_INPUT_FAILD);
								rep.setMessage("Tạo icao thất bại, kiểm tra lại đường truyền.");
							}
						} else {
							rep.setCode(Contants.CODE_INPUT_FAILD);
							rep.setMessage(Contants.MESSAGE_PASSPORT_EXIST);
						}

					} else {
						rep.setCode(Contants.CODE_INPUT_FAILD);
						rep.setMessage(Contants.MESSAGE_TRANSACTION_NULL);
					}

				} else {
					rep.setCode(Contants.CODE_INPUT_FAILD);
					rep.setMessage("Thiếu thông tin."
							+ Contants.MESSAGE_INPUT_NULL);
				}
			} else {
				rep.setCode(Contants.CODE_INPUT_FAILD);
				rep.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			rep.setCode(Contants.CODE_THROW_EXCEPTION);
			rep.setMessage(Contants.MESSAGE_EXCEPTION);
			EppWsLog eppWsLog = new EppWsLog();
			eppWsLog.setType(Contants.TYPE_CNP);
			eppWsLog.setUrlRequest(Contants.URL_CREATE_NEW_PASSPORT);
			try {
				eppWsLog.setDataRequest(new ObjectMapper()
						.writeValueAsString(input));
			} catch (IOException e1) {
				logger.error(e1.getMessage() + "convert object to json fail.");
				e1.printStackTrace();

			}
			eppWsLog.setKeyId(input.getTransactionId());
			eppWsLog.setDataResponse(rep.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - createNewPassport fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rep;
	}

	/*
	 * 
	 */

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadOutsideTransaction")
	public ResponseBase uploadOutsideTransaction(Transaction transaction)
			throws JAXBException {

		ResponseBase rep = new ResponseBase();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		Date startTime = new Date();
		String logData = null;
		String refId = null;
		String txnStatus = Contants.TRANSACTION_STATUS_TX_UPLOAD_COMPLETED;
		String wkstnId = Contants.CREATE_BY_SYSTEM;
		String officerId = Contants.CREATE_BY_SYSTEM;
		String siteCode = "";

		// Kiểm tra Transaction đã đầy đủ thông tin hay chưa.
		/*
		 * ResponseBase error = checkValidateTransaction(transaction); if (error
		 * != null) { return error; }
		 */
		if (transaction != null && transaction.getPerson() != null
				&& transaction.getPassportInfo() != null) {
			PassportCancelDetail input = transaction.getPassportInfo();
			if (StringUtils.isNotBlank(input.getTransactionId())
					&& StringUtils.isNotBlank(input.getPassportNo())
					&& StringUtils.isNotBlank(input.getDateOfDocIssue())
					&& StringUtils.isNotBlank(input.getDateOfDocExpiry())
					&& StringUtils.isNotBlank(input.getPlaceOfIssue())) {
				try {
					NicTransaction nicTransactionDBO = null;
					NicRegistrationData nicRegDataDBO = null;
					Collection<NicTransactionAttachment> nicTransDocDBOList = null;

					refId = transaction.getTransactionId();
					siteCode = transaction.getRegSiteCode();

					// Kiểm tra tồn tại Transaction.
					BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
							.findTransactionById(transaction.getTransactionId());
					if (!baseFindTran.isError()
							|| baseFindTran.getMessage() != null) {
						throw new Exception(baseFindTran.getMessage());
					}
					NicTransaction transactionTemp = baseFindTran.getModel();
					String transactionId = transaction.getTransactionId();
					List<NicDocumentData> listDocData = documentDataService
							.findAllByDocNumber(input.getPassportNo());
					if (transactionTemp != null
							|| (listDocData != null && listDocData.size() > 0)) {

						rep.setCode(Contants.CODE_DATA_EXIST);
						rep.setMessage("Hồ sơ:  " + transactionId
								+ " hoặc hộ chiếu : " + input.getPassportNo()
								+ Contants.MESSAGE_EXIST);

						return rep;
					}
					nicTransactionDBO = this.convertNicTransaction(transaction,
							false);
					BaseModelSingle<NicTransaction> baseApplyNT = nicTransactionService
							.applyNameTruncation(nicTransactionDBO);
					if (!baseApplyNT.isError()
							|| baseApplyNT.getMessage() != null) {
						throw new Exception(baseApplyNT.getMessage());
					}
					BaseModelSingle<String> baseSaveTran = nicTransactionService
							.saveTransaction(nicTransactionDBO);
					if (!baseSaveTran.isError()
							|| baseSaveTran.getMessage() != null) {
						throw new Exception(baseSaveTran.getMessage());
					}
					if (nicTransactionDBO.getNicRegistrationData() != null) {

						nicRegDataDBO = nicTransactionDBO
								.getNicRegistrationData();
						nicTransDocDBOList = nicTransactionDBO
								.getNicTransactionAttachments();
						BaseModelSingle<String> baseSaveRegisData = registrationDataDao
								.saveNicRegistrationData(nicRegDataDBO);
						if (!baseSaveRegisData.isError()
								|| baseSaveRegisData.getMessage() != null) {
							throw new Exception(baseSaveRegisData.getMessage());
						}
						// [2017] save payment
						if (nicTransactionDBO.getNicTransactionPayment() != null
								&& StringUtils.isNotBlank(nicTransactionDBO
										.getNicTransactionPayment()
										.getPaymentId())) {
							BaseModelSingle<String> baseSaveTP = transactionPaymentDao
									.saveTranPayment(nicTransactionDBO
											.getNicTransactionPayment());
							if (!baseSaveTP.isError()
									|| baseSaveTP.getMessage() != null) {
								throw new Exception(baseSaveTP.getMessage());
							}
						}
						for (NicTransactionAttachment nicTransDoc : nicTransDocDBOList) {
							if (StringUtils.isBlank(nicTransDoc
									.getTransactionId())) {
								if (nicTransactionDBO != null) {
									nicTransDoc
											.setTransactionId(nicTransactionDBO
													.getTransactionId());
								}
							}
							BaseModelSingle<Long> baseSaveTranDoc = transactionDocumentDao

							.saveTranAttachment(nicTransDoc);
							if (!baseSaveTranDoc.isError()
									|| baseSaveTranDoc.getMessage() != null) {
								throw new Exception(
										baseSaveTranDoc.getMessage());
							}
						}

						/* Thêm tạo person khi upload hồ sơ */
						// Hoald thêm: kiểm tra và lưu thông tin person nếu có.
						if (transaction.getPerson() != null) {
							PersonModel person = transaction.getPerson();
							EppPerson epp = eppPersonService
									.findPersonByPersonCode(person
											.getPersonCode());
							if (epp == null) {
								epp = new EppPerson();
								epp.setCreatedBy(transaction.getRegisData()
										.getCreateBy());
								epp.setCreateTs(new Date());
								epp.setDateOfBirth(person.getDateOfBirth());
								epp.setDateOfIdIssue(person.getDateOfIdIssue());
								epp.setEthnic(person.getEthNic());
								epp.setEthnicCode(person.getEthnicCode());
								epp.setFatherName(person.getFatherName());
								epp.setFatherSearchName(person
										.getFatherSearchName());
								epp.setGender(person.getGender());
								epp.setIdNumber(person.getIdNumber());
								epp.setMotherName(person.getMotherName());
								epp.setMotherSearchName(person
										.getMotherSearchName());
								epp.setName(person.getName());
								epp.setNationalityCode(person
										.getNationalityCode());
								epp.setNationalityName(person
										.getNationalityName());
								epp.setPersonCode(person.getPersonCode());
								if (!person.getPersonCode().equals(
										person.getPersonOrgCode())) {
									epp.setOrgPerson(person.getPersonOrgCode());
								}
								epp.setPlaceOfBirthCode(person
										.getPlaceOfBirthCode());
								epp.setPlaceOfBirthName(person
										.getPlaceOfBirthName());
								epp.setPlaceOfIdIssueName(person
										.getPlaceOfIdIssueName());
								epp.setSrcDoc(transactionId);
								epp.setReligion(person.getReligion());
								epp.setReligionCode(person.getReligionCode());
								epp.setSearchName(person.getSearchName());
								epp.setUpdatedBy(person.getUpdatedBy());
								if (person.getUpdatedDate() != null) {
									Date date = HelperClass
											.convertStringToDate(person
													.getUpdatedDate(), person
													.getUpdatedDate().length());
									epp.setUpdateTs(date);
								}
								epp.setIsChecked(false);
								epp.setDescription(person.getDescription());
								epp.setSrcOffice(person.getSrcOffice());
								epp.setStatus(person.getStatus());
								epp.setCreatedByName(person.getCreatedByName());
								epp.setUpdatedByName(person.getUpdatedByName());
								epp.setOtherName(person.getOtherName());
								epp.setCountryOfBirth(person
										.getCountryOfBirth());
								Long personId = eppPersonService
										.saveEppPerson(epp);
								// Kiểm tra thông tin gia đình
								if (person.getFamilies() != null
										&& person.getFamilies().size() > 0) {
									for (PersonFamily f : person.getFamilies()) {
										EppPersonFamily fmy = new EppPersonFamily();
										fmy.setName(f.getName());
										fmy.setDateOfBirth(f.getDateOfBirth());
										fmy.setLost(f.getLost());
										fmy.setRelationship(f.getRelationship());
										fmy.setGender(f.getGender());
										fmy.setSubjectPerson(personId);
										fmy.setCreatedBy(transaction
												.getRegisData().getCreateBy());
										fmy.setCreateTs(new Date());
										BaseModelSingle<Boolean> baseSaveDataFamily = eppPersonService
												.saveOrUpdateDataFamily(fmy);
										if (!baseSaveDataFamily.isError()
												|| baseSaveDataFamily
														.getMessage() != null) {
											throw new Exception(
													baseSaveDataFamily
															.getMessage());
										}
									}
								}
							}
							nicTransactionDBO
									.setPersonCode(epp.getPersonCode());
							BaseModelSingle<Boolean> baseSaveTrans = nicTransactionService
									.saveOrUpdateTransaction(nicTransactionDBO);
							if (!baseSaveTrans.isError()
									|| baseSaveTrans.getMessage() != null) {
								throw new Exception(baseSaveTrans.getMessage());
							}
						}

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
								.setTransactionStage(Contants.TRANSACTION_STATE_TX_UPLOAD);
						dcmTransLog
								.setTransactionStatus(TransactionStatus.Captured
										.getKey());
						BaseModelSingle<Boolean> saveLog = transactionLogService
								.saveTransactionLog(dcmTransLog);
						if (!saveLog.isError() || saveLog.getMessage() != null) {
							throw new Exception(saveLog.getMessage());
						}
					}
					// Step 2: Create Nic Job

					Integer jobPriority = nicTransactionDBO.getPriority();
					if (jobPriority == null) {
						jobPriority = NicUploadJob.JOB_PRIORITY_NORMAL;
					}
					Long jobId = nicUploadJobDao.createWorkflowJobNoRun(
							transactionId, transaction.getTransactionType(),
							jobPriority, 1, transaction.getRegisData()
									.getPersonCode());

					// Step 3: Update Nic Transaction
					nicTransactionDBO.getNicRegistrationData()
							.setWorkflowJobId(jobId);
					BaseModelSingle<Void> baseSOURegisD = registrationDataDao
							.saveOrUpdateRegisData(nicTransactionDBO
									.getNicRegistrationData());
					if (!baseSOURegisD.isError()
							|| baseSOURegisD.getMessage() != null) {
						throw new Exception(baseSOURegisD.getMessage());
					}

					List<NicFPData> fpDataList = this
							.convertFPDataList(nicTransactionDBO);
					for (NicFPData fpData : fpDataList) {
						BaseModelSingle<Boolean> baseSaveFPData = nicFPDataDao
								.saveNicFPData(fpData);
						if (!baseSaveFPData.isError()
								|| baseSaveFPData.getMessage() != null) {
							throw new Exception(baseSaveFPData.getMessage());
						}
					}

					// 5/12/2019 update status upload no call service by ric
					BaseModelSingle<Boolean> baseCheckStatus = this
							.updateStatusLog(transactionId,
									Contants.TRANSACTION_STATUS_RIC_UPLOADED);
					if (!baseCheckStatus.isError()
							|| baseCheckStatus.getMessage() != null) {
						throw new Exception(baseCheckStatus.getMessage());
					}
					/* Hoald: khởi tạo hộ chiếu mới theo dữ liệu đầu vào. */
					baseFindTran = nicTransactionService
							.findTransactionById(input.getTransactionId());
					if (!baseFindTran.isError()
							|| baseFindTran.getMessage() != null) {
						throw new Exception(baseFindTran.getMessage());
					}
					NicTransaction tran = baseFindTran.getModel();
					if (tran != null) {
						String documentNumber = "";
						String printedSite = "IN_A08_MB";
						String dateFormat = DateUtil.FORMAT_YYYYMMDD;
						documentNumber = input.getPassportNo();
						String documentType = "P";

						ParametersId id = new ParametersId();
						id.setParaName(Parameters.WS_LDS_API);
						id.setParaScope("API");
						Parameters param = parametersDao.findById(id);
						LdsResponseWsDto res = null;
						NicRegistrationData reg = tran.getNicRegistrationData();
						String dob = DateUtil.parseDate(reg.getDateOfBirth(),
								DateUtil.FORMAT_YYMMDD);
						String doe = DateUtil.parseDate(DateUtil.strToDate(
								input.getDateOfDocExpiry(), dateFormat),
								DateUtil.FORMAT_YYMMDD);
						if (param != null
								&& StringUtils.isNotBlank(param
										.getParaLobValue())) {
							LdsRequestWsDto request = new LdsRequestWsDto();
							request.setDateOfBirth(dob);
							request.setDateOfExpiry(doe);
							request.setDocumentNumber(documentNumber);
							request.setDocumentType(documentType);
							String finger01Position = "";
							String finger02Position = "";
							byte[] finger01 = null;
							byte[] finger02 = null;
							String issueState = "VNM";
							if (StringUtils.isNotBlank(reg.getFpEncode())) {
								String[] fpEncodes = StringUtils.split(
										reg.getFpEncode(), ",");
								if (fpEncodes.length >= 1)
									finger01Position = fpEncodes[0];
								if (fpEncodes.length >= 2)
									finger02Position = fpEncodes[1];

								finger01 = dataPackService
										.getTransactionDocument(
												tran,
												NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
												finger01Position);
								finger02 = dataPackService
										.getTransactionDocument(
												tran,
												NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
												finger02Position);
							}
							if (StringUtils.isNotBlank(finger01Position)
									&& finger01 != null) {
								request.setFinger01Position(finger01Position);
								request.setFinger02Position(finger02Position);
								request.setFinger01(Base64
										.encodeBase64String(finger01));
								request.setFinger02(Base64
										.encodeBase64String(finger02));
							}
							request.setIsEPassport(tran.getIsEpassport() != null ? tran
									.getIsEpassport() : false);
							request.setIssueState(issueState);
							request.setNameOfHolder(tran
									.getNicRegistrationData().getSearchName());
							request.setNationality(reg.getNationality());
							request.setOptionalData(tran.getNin());

							byte[] mugshotColour = null;
							// get mugshot from transaction data
							mugshotColour = dataPackService
									.getTransactionDocument(
											tran,
											NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
											NicTransactionAttachment.DEFAULT_SERIAL_NO);

							if (mugshotColour != null) {
								request.setPhoto(Base64
										.encodeBase64String(mugshotColour));
							}

							request.setSex(reg.getGender());
							res = dataPackService.GetLDS(request,
									param.getParaLobValue());

						}
						String icao1 = "";
						String icao2 = "";
						if (res != null && res.getStatus() == 200) {
							icao1 = res.getMrzLine1();
							icao2 = res.getMrzLine2();

							// String OFFICER_ID = "SYSTEM";
							String WKSTN_ID = "SYSTEM";
							String status = NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE;
							Date newDate = new Date();
							Date doiDate = DateUtil.strToDate(
									input.getDateOfDocIssue(), dateFormat);
							Date doeDate = DateUtil.strToDate(
									input.getDateOfDocExpiry(), dateFormat);
							NicDocumentDataId docId = new NicDocumentDataId(
									transactionId, documentNumber);
							NicDocumentData documentData = new NicDocumentData();
							documentData.setId(docId);
							documentData.setCreateBy(input.getCreateBy());
							documentData.setCreateDatetime(newDate);
							documentData.setCreateWkstnId(WKSTN_ID);

							documentData.setStatus(status);
							documentData.setStatusUpdateTime(newDate);
							documentData.setPrintingSite(printedSite);
							documentData.setDateOfIssue(doiDate);
							documentData.setDateOfExpiry(doeDate);

							// documentData.setUpdateBy(OFFICER_ID);
							// documentData.setUpdateDatetime(newDate);
							// documentData.setUpdateWkstnId(WKSTN_ID);

							String dispatchId = "DSP."
									+ DateUtil.parseDate(new Date(),
											DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
							documentData.setDispatchId(dispatchId);
							documentData.setDispatchDatetime(newDate);
							documentData.setIcaoLine1(icao1);
							documentData.setIcaoLine2(icao2);
							documentData.setPassportType(documentType);
							documentData.setPlaceOfIssueCode(input
									.getPlaceOfIssue());
							documentData.setPrintingSite(input
									.getPlaceOfIssue());
							documentDataDao.saveOrUpdate(documentData);

							// cập nhật trạng thái hồ sơ.
							tran.setIsEpassport(false);
							tran.setTransactionStatus(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED);
							nicTransactionService.saveOrUpdateTransaction(tran);

							rep.setCode(Contants.CODE_SUCCESS);
							rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
							/* Lưu bảng thống kê truyền nhận */
							this.saveOrUpRptData(
									Contants.URL_UPLOAD_OUTSIDE_TRANSACTION, 1,
									siteCode);

						} else {
							rep.setCode(Contants.CODE_INPUT_FAILD);
							rep.setMessage("Tạo icao thất bại, kiểm tra lại đường truyền.");
						}
					} else {
						rep.setCode(Contants.CODE_INPUT_FAILD);
						rep.setMessage(Contants.MESSAGE_TRANSACTION_NULL);
					}

				} catch (Exception e) {
					logger.error(e.getMessage()
							+ " - uploadOutsideTransaction fail.");
					e.printStackTrace();
					EppWsLog eppWsLog = new EppWsLog();
					eppWsLog.setType(Contants.TYPE_HS);
					eppWsLog.setUrlRequest(Contants.URL_UPLOAD_OUTSIDE_TRANSACTION);
					eppWsLog.setKeyId(transaction.getTransactionId());
					try {
						eppWsLog.setDataRequest(new ObjectMapper()
								.writeValueAsString(transaction));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						logger.error(e1.getMessage()
								+ "convert object to json fail.");
						e1.printStackTrace();

					}
					rep.setCode(Contants.CODE_THROW_EXCEPTION);
					rep.setMessage(Contants.MESSAGE_EXCEPTION);
					eppWsLog.setDataResponse(rep.toString());
					eppWsLog.setMessageErrorLog(CreateMessageException
							.createMessageException(e)
							+ " - uploadOutsideTransaction fail.");
					eppWsLog.setCreatedDate(new Date());
					BaseModelSingle<Boolean> baseCheck = eppWsLogService
							.inserDataTable(eppWsLog);
					if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
						logger.error(baseCheck.getMessage()
								+ " - save EppWsLog fail.");
					}

					return rep;
				} finally {
					// Step 4: Create Nic Transaction Log
					try {
						java.net.InetAddress localMachine = InetAddress
								.getLocalHost();
						wkstnId = localMachine.getHostName().toUpperCase();
					} catch (Exception e) {
						logger.error(e.getMessage());
					}

					Date endTime = new Date();
					try {
						transactionLogService.saveTransactionLog(refId,
								Contants.TRANSACTION_STATE_TX_UPLOAD,
								txnStatus, officerId, wkstnId, siteCode,
								startTime, endTime, "", logData);
					} catch (Exception e2) {
					}
				}
			} else {
				rep.setCode(Contants.CODE_INPUT_FAILD);
				rep.setMessage("Thiếu thông tin hồ sơ hộ chiếu.");
			}
		} else {
			rep.setCode(Contants.CODE_INPUT_FAILD);
			rep.setMessage("Thiếu thông tin hồ sơ hộ chiếu.");
		}
		return rep;
	}

	/*
	 * Khôi phục phôi
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/restoreDraft")
	public ResponseBase restoreDraft(PassportCancelDetail passportInfo) {
		ResponseBase rb = new ResponseBase();
		rb.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rb.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);

		Date startTime = new Date();
		try {
			if (passportInfo != null) {
				if (StringUtils.isNotBlank(passportInfo.getTransactionId())
						&& StringUtils.isNotBlank(passportInfo.getPassportNo())) {
					NicDocumentData nicDocData = documentDataService
							.findDocDataById(passportInfo.getTransactionId(),
									passportInfo.getPassportNo());
					if (nicDocData == null) {
						rb.setCode(Contants.CODE_INPUT_FAILD);
						rb.setMessage(Contants.MESSAGE_PASSPORT_NULL
								+ " passportNo: "
								+ passportInfo.getPassportNo());
						return rb;
					} else {
						NicTransaction tran = nicDocData.getNicTransaction();
						documentDataService.deletePassport(nicDocData);

						// save tranLog
						transactionLogService
								.saveTransactionLog(
										tran.getTransactionId(),
										NicDocumentData.DOCUMENT_DATA_CANCEL_TYPE_RESTORE_DRAFT,
										tran.getTransactionStatus(),
										passportInfo.getCreateBy(),
										Contants.CREATE_BY_SYSTEM,
										tran.getRegSiteCode(), startTime,
										new Date(), "passportNo: "
												+ passportInfo.getPassportNo(),
										null);
					}
				} else {
					rb.setCode(Contants.CODE_INPUT_FAILD);
					rb.setMessage("số hộ chiếu hoặc số hồ sơ "
							+ Contants.MESSAGE_INPUT_NULL);
					return rb;
				}
				rb.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				rb.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
			} else {
				rb.setCode(Contants.CODE_INPUT_FAILD);
				rb.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			rb.setCode(Contants.CODE_THROW_EXCEPTION);
			rb.setMessage(Contants.MESSAGE_EXCEPTION);
			EppWsLog eppWsLog = new EppWsLog();
			eppWsLog.setType(Contants.TYPE_RD);
			eppWsLog.setUrlRequest(Contants.URL_RESTORE_DRAFT);
			try {
				eppWsLog.setDataRequest(new ObjectMapper()
						.writeValueAsString(passportInfo));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error(e1.getMessage() + "convert object to json fail.");
				e1.printStackTrace();

			}
			eppWsLog.setKeyId("CP_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date()));
			eppWsLog.setDataResponse(rb.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - restoreDraft fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rb;
	}

	/*
	 * Hủy danh sách B
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/cancelHandoverB")
	public ResponseBase cancelHandoverB(HandoverBCancelled hanCanceled) {
		ResponseBase rb = new ResponseBase();
		rb.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rb.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			if (hanCanceled != null) {
				if (StringUtils.isNotBlank(hanCanceled.getPackageId())
						&& StringUtils.isNotBlank(hanCanceled.getCancelDate())
						&& StringUtils
								.isNotBlank(hanCanceled.getCancelReason())
						&& StringUtils.isNotBlank(hanCanceled.getCancelUser())
						&& StringUtils.isNotBlank(hanCanceled.getCancelName())) {
					BaseModelList<EppListHandoverDetail> findHanDetail = eppListHandoverDetailService
							.getListPackageByPackageId(
									hanCanceled.getPackageId(),
									NicListHandover.TYPE_LIST_B);
					if (!findHanDetail.isError()
							|| findHanDetail.getMessage() != null) {
						throw new Exception(findHanDetail.getMessage());
					}
					if (findHanDetail.getListModel() != null
							&& findHanDetail.getListModel().size() > 0) {
						NicListHandover handoverB = findHanDetail
								.getListModel().get(0).getPackageHandover();
						if (handoverB.getHandoverStatus() != 2) {
							handoverB.setHandoverStatus(2);
							handoverB.setCancelDatetime(hanCanceled
									.getCancelDate() != null ? DateUtil
									.strToDate(hanCanceled.getCancelDate(),
											DateUtil.FORMAT_YYYYMMDD)
									: new Date());
							handoverB.setCancelReason(hanCanceled
									.getCancelReason());
							handoverB.setCancelBy(hanCanceled.getCancelUser());
							handoverB.setCancelByName(hanCanceled
									.getCancelName());
							BaseModelSingle<Boolean> updateHanB = listHandoverService
									.saveOrUpdateHandover(handoverB);
							if (!updateHanB.isError()
									|| updateHanB.getMessage() != null) {
								throw new Exception(updateHanB.getMessage());
							}
							for (EppListHandoverDetail hanDetail : findHanDetail
									.getListModel()) {
								BaseModelSingle<NicTransaction> findTran = nicTransactionService
										.findTransactionById(hanDetail
												.getTransactionId());
								NicTransaction tran = findTran.getModel();
								if (tran != null) {
									tran.setTransactionStatus(NicTransaction.TRANSACTION_STATUS_INVESTIGATION_SAVED);
									nicTransactionService
											.saveOrUpdateTransaction(tran);
								}
							}
						}
					} else {
						rb.setCode(Contants.CODE_INPUT_FAILD);
						rb.setMessage(Contants.STRING_STATUS_CODE_HTTP_NOT_FOUND);
						return rb;
					}
				} else {
					rb.setCode(Contants.CODE_INPUT_FAILD);
					rb.setMessage("số hộ chiếu hoặc số hồ sơ "
							+ Contants.MESSAGE_INPUT_NULL);
					return rb;
				}
				rb.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				rb.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
			} else {
				rb.setCode(Contants.CODE_INPUT_FAILD);
				rb.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			rb.setCode(Contants.CODE_THROW_EXCEPTION);
			rb.setMessage(Contants.MESSAGE_EXCEPTION);
			EppWsLog eppWsLog = new EppWsLog();
			eppWsLog.setType("CHB");
			eppWsLog.setUrlRequest("cancelHandoverB");
			try {
				eppWsLog.setDataRequest(new ObjectMapper()
						.writeValueAsString(hanCanceled));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error(e1.getMessage() + "convert object to json fail.");
				e1.printStackTrace();

			}
			eppWsLog.setKeyId(hanCanceled.getPackageId());
			eppWsLog.setDataResponse(rb.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - cancelHandoverB fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
		}
		return rb;
	}

	/*
	 * Cập nhật thông tin phân công lại.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/redivisions")
	public ResponseBase redivisions(UpdateTranStatusProcess request) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// logger.info("start upload save process to ttdh, time:"
		// + HelperClass.convertDateToString1(new Date()));

		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_RD);
		eppWsLog.setUrlRequest(Contants.URL_REDIVISIONS);

		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(request));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();

		}
		Date updateDate = null;
		Date startTime = new Date();
		try {
			if (request != null && request.getListTranId() != null
					&& request.getListTranId().size() > 0) {
				if (StringUtils.isNotBlank(request.getProcessDate())) {
					updateDate = DateUtil.strToDate(request.getProcessDate(),
							DateUtil.FORMAT_YYYYMMDDHHMMSS);
				}
				for (TransactionIdInput transactionId : request.getListTranId()) {
					eppWsLog.setKeyId(transactionId.getTransactionId());
					BaseModelSingle<NicUploadJob> baseFindUJ = uploadJobService
							.findUploadJobByTransId(transactionId
									.getTransactionId());
					if (!baseFindUJ.isError()
							|| baseFindUJ.getMessage() != null) {
						return this.checkExistAndSaveException(baseFindUJ,
								eppWsLog, response, request);
					}
					NicUploadJob nicUp = baseFindUJ.getModel();
					if (nicUp != null) {
						nicUp.setInvestigationOfficerId(null);
						nicUp.setInvestigationStatus(NicUploadJob.RECORD_STATUS_INITIAL);
						nicUp.setDateApproveInvestigation(null);
						nicUp.setDateApproveNin(null);
						nicUp.setInvestigationDate(null);
						nicUp.setOriginalyBlacklistId(null);
						nicUp.setNoteApproveObj(null);
						nicUp.setNoteApproveNin(null);
						nicUp.setUserApproverObj(null);
						nicUp.setUpdateBy(request.getUserName());
						nicUp.setUpdateDatetime(updateDate);

						BaseModelSingle<Boolean> baseSaveUJ = uploadJobService
								.saveOrUpdateService(nicUp);
						if (!baseSaveUJ.isError()
								|| baseSaveUJ.getMessage() != null) {
							return this.checkExistAndSaveException(baseSaveUJ,
									eppWsLog, response, request);
						}

						/* update trạng thái hồ sơ */
						NicTransaction nicTran = nicUp.getNicTransaction();
						nicTran.setUpdateBy(request.getUserName());
						nicTran.setUpdateDatetime(updateDate);
						nicTran.setTransactionStatus(Contants.TRANSACTION_STATUS_RIC_UPLOADED);
						BaseModelSingle<Boolean> baseUpdateTranstatus = nicTransactionService
								.saveOrUpdateTransaction(nicTran);
						if (!baseUpdateTranstatus.isError()
								|| baseUpdateTranstatus.getMessage() != null) {
							return this.checkExistAndSaveException(
									baseUpdateTranstatus, eppWsLog, response,
									request);
						}

						/* Lưu log */
						NicTransactionLog dcmTransLog = new NicTransactionLog();
						dcmTransLog.setRefId(nicTran.getTransactionId());
						dcmTransLog.setLogCreateTime(updateDate);
						dcmTransLog.setOfficerId(request.getUserName());
						dcmTransLog
								.setTransactionStage(Contants.TRANSACTION_STATE_TX_REDIVISIONS);
						dcmTransLog
								.setTransactionStatus(TransactionStatus.Redivisions
										.getKey());
						dcmTransLog.setStartTime(startTime);
						dcmTransLog.setEndTime(new Date());
						dcmTransLog.setLogInfo(request.getFullName() + " "
								+ request.getProcessDate());
						BaseModelSingle<Boolean> saveLog = transactionLogService
								.saveTransactionLog(dcmTransLog);
						if (!saveLog.isError() || saveLog.getMessage() != null) {
							throw new Exception(saveLog.getMessage());
						}
					} else {
						response.setMessage("no found transaction Id, "
								+ transactionId.getTransactionId());
						return response;
					}
				}
			}

			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			this.saveOrUpRptData(Contants.URL_REDIVISIONS, 1, null);

			// logger.info("success upload save process to ttdh, time:"
			// + HelperClass.convertDateToString1(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error: " + e.getMessage());

			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			// save log
			eppWsLog.setDataResponse(response.toString());
			eppWsLog.setMessageErrorLog(CreateMessageException
					.createMessageException(e) + " - uploadSaveProcess fail.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(e.getMessage() + " - save EppWsLog fail.");
			}
			return response;
		}
		return response;
	}

	private boolean checkFieldInput(TransactionDetailToHanding tranInput,
			NicRegistrationData reg) {
		boolean check = true;

		if (StringUtils.isNotBlank(tranInput.getName())) {
			if (!tranInput.getName().equals(
					HelperClass.createFullName(reg.getSurnameFull(),
							reg.getMiddlenameFull(), reg.getFirstnameFull()))) {
				check = false;
			}
		}
		if (StringUtils.isNotBlank(tranInput.getGender())) {
			if (!tranInput.getGender().equals(reg.getGender())) {
				check = false;
			}
		}
		if (StringUtils.isNotBlank(tranInput.getDateOfBirth())) {
			if (!tranInput.getDateOfBirth().equals(
					DateUtil.parseDate(reg.getDateOfBirth(),
							DateUtil.FORMAT_YYYYMMDD))) {
				check = false;
			}
		}

		return check;
	}

	private Boolean convertStrToBoo(String input) {
		if (StringUtils.isNotBlank(input)) {
			if (input.length() == 1) {
				if (input.equals("N")) {
					return false;
				} else if (input.equals("Y")) {
					return true;
				}
			}
		}
		return null;
	}

	private void checkValidateLost(TransactionLost lost) throws Exception {
		if (StringUtils.isBlank(lost.getPassportNo())) {
			logger.error("error null passportNo by transaction lost.");
			throw new Exception("error null passportNo by transaction lost.");
		}
		if (StringUtils.isBlank(lost.getTransactionId())) {
			logger.error("error null transactionId by transaction lost.");
			throw new Exception("error null transactionId by transaction lost.");
		}
		if (StringUtils.isBlank(lost.getName())) {
			logger.error("error null fullname by transaction lost.");
			throw new Exception("error null fullname by transaction lost.");
		}
	}

	private void saveTransactionLog(String transactionId,
			String transactionStage, String transactionStatus, Date startTime,
			Date endTime, String logInfo, String logData, Integer retryCount) {
		NicCommandUtil nicCommandUtil = new NicCommandUtil();
		NicTransactionLog transactionLog = new NicTransactionLog();
		transactionLog.setRefId(transactionId);
		transactionLog.setLogCreateTime(new Date());
		transactionLog.setTransactionStage(transactionStage);// TRANSACTION_STATE_PERSONALIZATION);
		transactionLog.setTransactionStatus(transactionStatus);
		transactionLog.setSiteCode(nicCommandUtil
				.getSystemSiteCodeFromParameter()); // get from
													// 'CURRENT_SITE_CODE'
		transactionLog.setStartTime(startTime);
		transactionLog.setEndTime(endTime);
		transactionLog.setLogInfo(logInfo);
		transactionLog.setLogData(logData);
		// 8-OCT-2013 (jp) - added hostname and system
		transactionLog.setWkstnId(nicCommandUtil.getHostName());
		transactionLog.setOfficerId(nicCommandUtil.getSystemName());
		transactionLog.setRetryCount(retryCount);
		synchronized (NicTransactionLog.class) {
			nicTransactionLogService.saveOrUpdate(transactionLog);
		}
	}

	private int LDSPacketDocument(NicUploadJob obj) {
		NicCommandUtil nicCommandUtil = new NicCommandUtil();
		int res = -1;
		int JOB_STATE = 1;
		int PERSO_REGISTER = 6;
		String STATUS_INPROGRESS = "01";
		try {

			NicTransaction transObj = new NicTransaction();
			// updateStatus(obj.getWorkflowJobId(), JOB_STATE_PERSO_REGISTER,
			// JOB_STATE);
			uploadJobService.updateJobState(obj.getWorkflowJobId(),
					PersoService.JOB_STATE_PERSO_REGISTER, JOB_STATE);

			if (StringUtils.equals(obj.getPersoRegisterStatus(),
					PersoService.STATUS_COMPLETED)) {
				// SUBMIT PERSO has been completed before, skip step

			} else {

				// updateStatus(obj.getWorkflowJobId(), STATUS_INPROGRESS,
				// PERSO_REGISTER);
				uploadJobService.updateJobState(obj.getWorkflowJobId(),
						STATUS_INPROGRESS, PERSO_REGISTER);
				transObj.setTransactionId(obj.getTransactionId());
				transObj = nicTransactionService.findById(
						transObj.getTransactionId(), false, true, true, false);// has
																				// trans
																				// doc
																				// and
																				// registration
																				// data

				// preparePersoData(transObj);
				dataPackService.preparePersoData(transObj);

				// submitPersoData(transObj);

				res = 1;
				// transactionStatus = JOB_STATE_PERSO_REGISTER +
				// STAGE_COMPLETED;
				// updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED,
				// PERSO_REGISTER);
				uploadJobService.updateJobState(obj.getWorkflowJobId(),
						PersoService.STATUS_COMPLETED, PERSO_REGISTER);
				uploadJobService.resetReprocessCount(obj.getWorkflowJobId(), 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// transactionStatus = JOB_STATE_PERSO_REGISTER + STAGE_ERROR;
			// String logData = MiscXmlConvertor.parseObjectToXml(e);

			// updateStatus(obj.getWorkflowJobId(), STATUS_ERROR,
			// PERSO_REGISTER);
			try {
				uploadJobService.updateJobState(obj.getWorkflowJobId(),
						PersoService.STATUS_ERROR, PERSO_REGISTER);
			} catch (NicUploadJobServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true,
					uploadJobService);

		} finally {
			/*
			 * try { if (StringUtils.isNotBlank(obj.getTransactionId()) &&
			 * StringUtils.isNotEmpty(transactionStatus)) {
			 * uploadJobService.updateJobStatus(obj.getWorkflowJobId(),
			 * transactionStatus);
			 * this.saveTransactionLog(obj.getTransactionId(),
			 * JOB_STATE_PERSO_REGISTER, transactionStatus, startTime, new
			 * Date(), null, logData, obj.getJobReprocessCount());
			 * 
			 * } } catch (Exception e) { }
			 */
		}
		return res;

	}

	private UpdatePassportModel getDetailPassport(String transactionId,
			String site, long queueId) {
		UpdatePassportModel update = new UpdatePassportModel();
		BaseModelSingle<NicDocumentData> baseGetDocData = documentDataService
				.getDocumentDataById(transactionId);
		NicDocumentData nicDoc = baseGetDocData.getModel();
		if (nicDoc != null) {
			update.setIdQueue(queueId);
			update.setCancelBy(nicDoc.getCancelBy());
			if (nicDoc.getCancelDatetime() != null) {
				String date = service.perso.util.HelperClass
						.convertDateToString(nicDoc.getCancelDatetime(),
								STR_FORMAT_DATE_yyyyMMddHHmmss);
				update.setCancelDatetime(date);
			}
			update.setChipSerialNo(nicDoc.getChipSerialNo());
			if (nicDoc.getDateOfExpiry() != null) {
				String date = service.perso.util.HelperClass
						.convertDateToString(nicDoc.getDateOfExpiry(),
								STR_FORMAT_DATE_yyyyMMdd);
				update.setDateOfExpiry(date);
			}
			if (nicDoc.getDateOfIssue() != null) {
				String date = service.perso.util.HelperClass
						.convertDateToString(nicDoc.getDateOfIssue(),
								STR_FORMAT_DATE_yyyyMMdd);
				update.setDateOfIssue(date);
			}
			update.setIcaoLine1(nicDoc.getIcaoLine1());
			update.setIcaoLine2(nicDoc.getIcaoLine2());
			update.setIssueBy(nicDoc.getIssueBy());
			if (nicDoc.getIssueDatetime() != null) {
				String date = service.perso.util.HelperClass
						.convertDateToString(nicDoc.getIssueDatetime(),
								STR_FORMAT_DATE_yyyyMMddHHmmss);
				update.setIssueDatetime(date);
			}
			update.setPassportNo(nicDoc.getId().getPassportNo());
			update.setPositionSigner(nicDoc.getPositionSigner());
			update.setPrintingSite(nicDoc.getPrintingSite());
			update.setReceiveBy(nicDoc.getReceiveBy());
			if (nicDoc.getReceiveDatetime() != null) {
				String date = service.perso.util.HelperClass
						.convertDateToString(nicDoc.getReceiveDatetime(),
								STR_FORMAT_DATE_yyyyMMddHHmmss);
				update.setReceiveDatetime(date);
			}
			update.setRejectBy(nicDoc.getRejectBy());
			if (nicDoc.getRejectDatetime() != null) {
				String date = service.perso.util.HelperClass
						.convertDateToString(nicDoc.getRejectDatetime(),
								STR_FORMAT_DATE_yyyyMMddHHmmss);
				update.setRejectDatetime(date);
			}
			update.setSigner(nicDoc.getSigner());
			update.setStatus(nicDoc.getStatus());
			update.setTransactionId(nicDoc.getId().getTransactionId());
			NicTransaction nicTran;
			try {
				BaseModelSingle<NicTransaction> baseGetTranById = nicTransactionService
						.getNicTransactionById(nicDoc.getId()
								.getTransactionId());
				nicTran = baseGetTranById.getModel();
				if (nicTran != null) {
					update.setPassportType(nicTran.getPassportType());
					update.setIsEpassport((nicTran.getIsEpassport() != null && nicTran
							.getIsEpassport()) ? "Y" : "N");
				}
			} catch (TransactionServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return update;
	}

	private PersonModel getPersonModel(String personCode) {
		PersonModel person = new PersonModel();
		BaseModelSingle<EppPerson> baseFindPersonByCode = eppPersonService
				.findByStringCode(personCode);
		EppPerson eppPerson = baseFindPersonByCode.getModel();
		if (eppPerson != null) {
			person.setDateOfBirth(eppPerson.getDateOfBirth());
			person.setDateOfIdIssue(eppPerson.getDateOfIdIssue());
			person.setEthNic(eppPerson.getEthnic());
			person.setEthnicCode(eppPerson.getEthnicCode());
			person.setFatherName(eppPerson.getFatherName());
			person.setFatherSearchName(eppPerson.getFatherSearchName());
			person.setGender(eppPerson.getGender());
			person.setIdNumber(eppPerson.getIdNumber());
			person.setMotherName(eppPerson.getMotherName());
			person.setMotherSearchName(eppPerson.getMotherSearchName());
			person.setName(eppPerson.getName());
			person.setNationalityCode(eppPerson.getNationalityCode());
			person.setNationalityName(eppPerson.getNationalityName());
			person.setPersonCode(eppPerson.getPersonCode());
			person.setPersonOrgCode(eppPerson.getOrgPerson());
			person.setPlaceOfBirthCode(eppPerson.getPlaceOfBirthCode());
			person.setPlaceOfBirthName(eppPerson.getPlaceOfBirthName());
			person.setPlaceOfIdIssueName(eppPerson.getPlaceOfIdIssueName());
			person.setRefId(eppPerson.getRefId());
			person.setReligion(eppPerson.getReligion());
			person.setReligionCode(eppPerson.getReligionCode());
			person.setSearchName(eppPerson.getSearchName());
			person.setCreatedBy(eppPerson.getCreatedBy());
			person.setCreatedByName(eppPerson.getCreatedByName());
			if (eppPerson.getCreateTs() != null) {
				String date = service.perso.util.HelperClass
						.convertDateToString(eppPerson.getCreateTs(),
								STR_FORMAT_DATE_yyyyMMddHHmmss);
				person.setCreatedDate(date);
			}

			if (eppPerson.getUpdateTs() != null) {
				String date = service.perso.util.HelperClass
						.convertDateToString(eppPerson.getUpdateTs(),
								STR_FORMAT_DATE_yyyyMMddHHmmss);
				person.setUpdatedDate(date);
			}
			person.setUpdatedBy(eppPerson.getUpdatedBy());
			person.setUpdatedByName(eppPerson.getUpdatedByName());
			person.setIsChecked((eppPerson.getIsChecked() != null && eppPerson
					.getIsChecked()) ? "Y" : "N");
			person.setDescription(eppPerson.getDescription());
			person.setSrcOffice(eppPerson.getSrcOffice());
			person.setStatus(eppPerson.getStatus());
			person.setOtherName(eppPerson.getOtherName());
			person.setCountryOfBirth(eppPerson.getCountryOfBirth());

			List<PersonFamily> families = new ArrayList<PersonFamily>();
			// Tìm danh sách gia đình
			BaseModelList<EppPersonFamily> baseFindPerson = eppPersonService
					.findAllEppPersonFamily1(eppPerson.getPersonId());
			List<EppPersonFamily> listf = baseFindPerson.getListModel();
			if (listf != null && listf.size() > 0) {
				for (EppPersonFamily f : listf) {
					PersonFamily family = new PersonFamily();
					family.setDateOfBirth(f.getDateOfBirth());
					family.setGender(f.getGender());
					family.setLost(f.getLost());
					family.setName(f.getName());
					family.setPlaceOfBirth(f.getPlaceOfBirthCode());
					family.setRelationship(f.getRelationship());
					families.add(family);
				}
			}
			person.setFamilies(families);
		}
		return person;
	}

	private BaseModelSingle<Transaction> getTransaction(String transactionId,
			String site, long queueId, String statusQueue) {
		BaseModelSingle<Transaction> baseTran = new BaseModelSingle<Transaction>();
		Transaction transaction = new Transaction();
		try {
			List<PersonFamily> listFamily = new ArrayList<PersonFamily>();
			BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
					.findTransactionById(transactionId);
			if (!baseFindTran.isError() || baseFindTran.getMessage() != null) {
				baseTran.setError(baseFindTran.isError());
				baseTran.setMessage(baseFindTran.getMessage());
				baseTran.setModel(null);
				return baseTran;
			}
			NicTransaction txn = baseFindTran.getModel();
			if (txn != null) {
				transaction.setTransactionId(txn.getTransactionId());
				transaction.setNin(txn.getNin());
				transaction.setTransactionType(txn.getTransactionSubType());
				transaction.setTransactionStatus(statusQueue);
				if (txn.getDateOfApplication() != null) {
					String date = service.perso.util.HelperClass
							.convertDateToString(txn.getDateOfApplication(),
									STR_FORMAT_DATE_yyyyMMdd);
					transaction.setDateOfApplication(date);
				}
				// transaction.setEstDateOfCollection(txn.getEstDateOfCollection());
				if (txn.getEstDateOfCollection() != null) {
					String date = service.perso.util.HelperClass
							.convertDateToString(txn.getEstDateOfCollection(),
									STR_FORMAT_DATE_yyyyMMdd);
					transaction.setEstDateOfCollection(date);
				}
				if (txn.getIsPostOffice() != null) {
					transaction.setIsPostOffice(txn.getIsPostOffice() ? "Y"
							: "N");
				}
				transaction.setIssSiteCode(txn.getIssSiteCode());
				transaction.setRegSiteCode(txn.getRegSiteCode());
				if (txn.getIsEpassport() != null) {
					transaction.setPassportStyle(txn.getIsEpassport() ? "Y"
							: "N");
				}
				transaction.setPrevPassportNo(txn.getPrevPassportNo());
				// transaction.setPrevDateOfIssue(txn.getPrevDateOfIssue());
				if (txn.getPrevDateOfIssue() != null) {
					String date = service.perso.util.HelperClass
							.convertDateToString(txn.getPrevDateOfIssue(),
									STR_FORMAT_DATE_yyyyMMdd);
					transaction.setPrevDateOfIssue(date);
				}
				transaction.setPriority(txn.getPriority());
				transaction.setRecieptNo(txn.getRecieptNo());
				transaction.setRegistrationNo(txn.getRegistrationNo());
				transaction.setPlaceIssuance(txn.getAppointmentPlace());
				transaction.setIdQueue(queueId);
				transaction.setApplicant(txn.getApplicant());
				transaction.setAppointmentPlace(txn.getAppointmentPlace());
				transaction.setPrevDateOfExpr(txn.getPrevDateOfExpr());
				transaction.setRegistrationType(txn.getRegistrationType());
				transaction.setPaBlacklistId(txn.getPaBlacklistId());
				transaction.setPaInqBllUser(txn.getPaInqBllUser());
				transaction.setPaArchiveCode(txn.getPaArchiveCode());
				transaction.setDescription(txn.getDescription());
				if (txn.getPaJoinPersonDate() != null) {
					String date = service.perso.util.HelperClass
							.convertDateToString(txn.getPaJoinPersonDate(),
									STR_FORMAT_DATE_yyyyMMddHHmmss);
					transaction.setPaJoinPersonDate(date);
				}
				if (txn.getPaSaveDocDate() != null) {
					String date = service.perso.util.HelperClass
							.convertDateToString(txn.getPaSaveDocDate(),
									STR_FORMAT_DATE_yyyyMMddHHmmss);
					transaction.setPaSaveDocDate(date);
				}
				if (txn.getPaSearchObjDate() != null) {
					String date = service.perso.util.HelperClass
							.convertDateToString(txn.getPaSearchObjDate(),
									STR_FORMAT_DATE_yyyyMMddHHmmss);
					transaction.setPaSearchObjDate(date);
				}
				if (txn.getPaSearchBio() != null)
					transaction
							.setPaSearchBio(txn.getPaSearchBio() ? "Y" : "N");
				if (StringUtils.isNotEmpty(txn.getInfoPerson())) {
					JAXBContext jaxbContext = JAXBContext
							.newInstance(InfoFamilys.class);
					Unmarshaller unmarshaller = jaxbContext
							.createUnmarshaller();
					StringReader reader = new StringReader(txn.getInfoPerson());
					InfoFamilys familys = (InfoFamilys) unmarshaller
							.unmarshal(reader);
					listFamily.addAll(familys.getSonFamilies());
				}
			}
			NicRegistrationData reg = txn.getNicRegistrationData();
			if (reg != null) {
				RegistrationData regis = new RegistrationData();
				regis.setResidentPlaceId(reg.getAddressLine4());
				regis.setResidentAreaId(reg.getAddressLine3());
				regis.setResidentAddress(reg.getAddressLine1());
				// regis.setTmpPlaceId(reg.getAddressTempCity());
				regis.setTmpAreaId(reg.getAddressTempDistrict());
				regis.setTmpAddress(reg.getAddressTempDetail());
				regis.setTmpPlaceId(reg.getAddressTempProvince());
				// regis.setTmpAddress(reg.getAddressTempStreet());
				regis.setTotalFp(reg.getTotalFp());
				regis.setContactNo(reg.getContactNo());
				regis.setFullName(HelperClass.createFullName(
						reg.getSurnameFull(), reg.getMiddlenameFull(),
						reg.getFirstnameFull()));
				regis.setNationality(reg.getNationality());
				// regis.setDateOfBirth(reg.getDateOfBirth());
				if (reg.getDateOfBirth() != null) {
					String date = service.perso.util.HelperClass
							.convertDateToString(reg.getDateOfBirth(),
									STR_FORMAT_DATE_yyyyMMdd);
					regis.setDateOfBirth(HelperClass.loadDateOfBirths(date,
							reg.getDefDateOfBirth()));
				}
				regis.setPlaceOfBirth(reg.getPlaceOfBirth());
				// regis.setGender(reg.getGender().equals("M") ? "MALE" :
				// "FEMALE");
				regis.setGender(reg.getGender());
				regis.setReligion(reg.getReligion());
				regis.setNation(reg.getNation());
				regis.setAddressNin(reg.getAddressNin());
				// regis.setDateNin(reg.getDateNin());
				if (reg.getDateNin() != null) {
					String date = service.perso.util.HelperClass
							.convertDateToString(reg.getDateNin(),
									STR_FORMAT_DATE_yyyyMMdd);
					regis.setDateNin(date);
				}
				regis.setAddressCompany(reg.getOfficeAddress());
				regis.setJob(reg.getJob());
				regis.setStyleDob(reg.getDefDateOfBirth());
				regis.setSurName(reg.getSurnameFull());
				regis.setMidName(reg.getMiddlenameFull());
				regis.setFirstName(reg.getFirstnameFull());
				regis.setNumDecision(reg.getNumDecision());
				regis.setNameDepartment(reg.getNameDepartment());
				regis.setPosition(reg.getPosition());
				regis.setOwnerType(reg.getOwnerType());
				regis.setSearchName(reg.getSearchName());
				regis.setCreateByName(reg.getCreateByName());
				regis.setCreateBy(reg.getCreateBy());
				BaseModelSingle<EppPerson> baseFindPerson = eppPersonService
						.findByStringCode(txn.getPersonCode());
				if (!baseFindPerson.isError()
						|| baseFindPerson.getMessage() != null) {
					baseTran.setError(baseFindPerson.isError());
					baseTran.setMessage(baseFindPerson.getMessage());
					baseTran.setModel(null);
					return baseTran;
				}
				EppPerson eppP = baseFindPerson.getModel();
				regis.setPersonCode(eppP != null ? eppP.getPersonCode() : null);
				transaction.setRegisData(regis);
				if (StringUtils.isNotEmpty(reg.getFatherFullname())) {
					PersonFamily father = new PersonFamily();
					father.setRelationship(Contants.RELATIONSHIP_TYPE_FATHER);
					father.setGender("M");
					father.setName(reg.getFatherFullname());
					String dobFull = HelperClass.convertDateToString2(reg
							.getFatherDateOfBirth());
					if (dobFull != null && dobFull.contains("/")) {
						try {
							String[] arrDob = dobFull.split("/");
							if (reg.getFatherDateOfBirth().equals("Y")) {
								father.setDateOfBirth(arrDob[2]);
							} else if (reg.getFatherDateOfBirth().equals("M")) {
								father.setDateOfBirth(arrDob[2] + arrDob[1]);
							} else if (reg.getFatherDateOfBirth().equals("D")) {
								father.setDateOfBirth(arrDob[2] + arrDob[1]
										+ arrDob[0]);
							}
						} catch (Exception e) {
						}
					}

					father.setLost(reg.getFatherLost());
					listFamily.add(father);
				}
				if (StringUtils.isNotEmpty(reg.getMotherFullname())) {
					PersonFamily mother = new PersonFamily();
					mother.setRelationship(Contants.RELATIONSHIP_TYPE_MOTHER);
					mother.setGender("F");
					mother.setName(reg.getMotherFullname());
					String dobFull = HelperClass.convertDateToString2(reg
							.getMotherDateOfBirth());
					if (dobFull != null && dobFull.contains("/")) {
						try {
							String[] arrDob = dobFull.split("/");
							if (reg.getFatherDateOfBirth().equals("Y")) {
								mother.setDateOfBirth(arrDob[2]);
							} else if (reg.getFatherDateOfBirth().equals("M")) {
								mother.setDateOfBirth(arrDob[2] + arrDob[1]);
							} else if (reg.getFatherDateOfBirth().equals("D")) {
								mother.setDateOfBirth(arrDob[2] + arrDob[1]
										+ arrDob[0]);
							}
						} catch (Exception e) {
						}
					}

					mother.setLost(reg.getMotherLost());
					listFamily.add(mother);
				}
				if (StringUtils.isNotEmpty(reg.getSpouseFullname())) {
					PersonFamily spouse = new PersonFamily();
					spouse.setRelationship(Contants.RELATIONSHIP_TYPE_SPOUSE);
					spouse.setGender(reg.getGender().equals("M") ? "F" : "M");
					spouse.setName(reg.getSpouseFullname());
					String dobFull = HelperClass.convertDateToString2(reg
							.getSpouseDateOfBirth());
					if (dobFull != null && dobFull.contains("/")) {
						try {
							String[] arrDob = dobFull.split("/");
							if (reg.getSpouseDefDateOfBirth().equals("Y")) {
								spouse.setDateOfBirth(arrDob[2]);
							} else if (reg.getSpouseDefDateOfBirth()
									.equals("M")) {
								spouse.setDateOfBirth(arrDob[2] + arrDob[1]);
							} else if (reg.getSpouseDefDateOfBirth()
									.equals("D")) {
								spouse.setDateOfBirth(arrDob[2] + arrDob[1]
										+ arrDob[0]);
							}
						} catch (Exception e) {
						}
					}

					spouse.setLost(reg.getSpouseLost());
					listFamily.add(spouse);
				}
			}
			BaseModelList<NicTransactionAttachment> baseGetTranAttach = nicTransactionAttachmentService
					.getNicTransactionAttachmentsOutTypes(
							txn.getTransactionId(),
							new String[] {
									NicTransactionAttachment.DOC_TYPE_TPL,
									NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE,
									NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP });
			if (!baseGetTranAttach.isError()
					|| baseGetTranAttach.getMessage() != null) {
				baseTran.setError(baseGetTranAttach.isError());
				baseTran.setMessage(baseGetTranAttach.getMessage());
				baseTran.setModel(null);
				return baseTran;
			}
			List<NicTransactionAttachment> listAttach = baseGetTranAttach
					.getListModel();
			List<TransactionDocument> listDoc = new ArrayList<TransactionDocument>();
			if (listAttach != null) {
				for (NicTransactionAttachment att : listAttach) {
					TransactionDocument document = new TransactionDocument();
					document.setDocType(att.getDocType());
					document.setSerialNo(att.getSerialNo());
					document.setDocData(Base64.encodeBase64String(att
							.getDocData()));
					document.setCreatedBy(att.getCreateBy());
					document.setCreatedByName(att.getCreateByName());
					if (att.getCreateDatetime() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(att.getCreateDatetime(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						document.setCreatedDate(date);
					}
					if (att.getUpdateDatetime() != null) {
						String date = service.perso.util.HelperClass
								.convertDateToString(att.getUpdateDatetime(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						document.setUpdatedDate(date);
					}
					document.setUpdatedBy(att.getUpdateBy());
					document.setUpdatedByName(att.getUpdateByName());
					document.setFileName(att.getStorageRefId());
					listDoc.add(document);
				}
			}
			// Documents documents = new Documents(listDoc);
			transaction.setFamilies(listFamily);
			transaction.setDocuments(listDoc);
			baseTran.setError(true);
			baseTran.setMessage(null);
			baseTran.setModel(transaction);
		} catch (Exception e) {
			baseTran.setError(false);
			baseTran.setMessage(CreateMessageException
					.createMessageException(e)
					+ " - getTransaction - "
					+ transactionId + " - thất bại.");
			baseTran.setModel(null);
		}
		return baseTran;
	}

	private BaseModelSingle<HandoverA> getHandoverA(String transactionId,
			String site, long queueId) {
		BaseModelSingle<HandoverA> baseGetHan = new BaseModelSingle<HandoverA>();
		HandoverA nicHandover = new HandoverA();
		try {
			BaseModelList<EppListHandoverDetail> baseGetHanDetail = eppListHandoverDetailService
					.getPackageNameByTransactionId(transactionId);
			if (!baseGetHanDetail.isError()
					|| baseGetHanDetail.getMessage() != null) {
				baseGetHan.setError(baseGetHanDetail.isError());
				baseGetHan.setMessage(baseGetHanDetail.getMessage());
				baseGetHan.setModel(null);
				return baseGetHan;
			}
			List<EppListHandoverDetail> details = baseGetHanDetail
					.getListModel();
			NicListHandover handover = new NicListHandover();
			for (EppListHandoverDetail d : details) {
				BaseModelSingle<NicListHandover> baseFindHanById = listHandoverService
						.findByPackageId(new NicListHandoverId(
								d.getPackageId(), NicListHandover.TYPE_LIST_A));
				if (!baseFindHanById.isError()
						|| baseFindHanById.getMessage() != null) {
					baseGetHan.setError(baseFindHanById.isError());
					baseGetHan.setMessage(baseFindHanById.getMessage());
					baseGetHan.setModel(null);
					return baseGetHan;
				}
				handover = baseFindHanById.getModel();
				if (handover != null
						&& handover.getId().getTypeList().equals("A")) {
					break;
				}
			}
			if (handover != null) {
				// SyncQueueJob queue =
				// queueJobService.findQueueByObjectId(handover.getPackageId(),
				// site, Contants.CODE_STATUS_QUEUE_PENDING,
				// Contants.QUEUE_OBJ_TYPE_DA);
				nicHandover.setIdQueue(queueId);
				if (handover != null) {
					nicHandover.setPackageId(handover.getId().getPackageId());
					nicHandover.setSiteCode(handover.getSiteCode());
					if (handover.getApproveDate() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(handover.getApproveDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						nicHandover.setApproveDate(dateExp);
					}
					if (handover.getProposalDate() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(
										handover.getProposalDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						nicHandover.setOfferDate(dateExp);
					}
					nicHandover.setApproveUserName(handover.getApproveUser());
					nicHandover.setOfferUserName(handover.getProcessUsers());
					nicHandover.setType(NicListHandover.TYPE_LIST_A);
					nicHandover.setCount(handover.getCountTransaction());
					nicHandover.setCreatorName(handover.getCreatorName());
					nicHandover.setApproveName(handover.getApproveName());
					nicHandover.setApprovePosition(handover
							.getApprovePosition());
					nicHandover.setOfferUserName(handover.getProposalName());
					if (handover.getDateOfDelivery() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(
										handover.getDateOfDelivery(),
										STR_FORMAT_DATE_yyyyMMdd);
						nicHandover.setDateOfDelivery(dateExp);
					}
					nicHandover.setPlaceOfDelivery(handover
							.getPlaceOfDelivery());
				}
				BaseModelList<EppListHandoverDetail> baseGetHanDetailById = eppListHandoverDetailService
						.getListPackageByPackageId(handover.getId()
								.getPackageId(), NicListHandover.TYPE_LIST_A);
				if (!baseGetHanDetailById.isError()
						|| baseGetHanDetailById.getMessage() != null) {
					baseGetHan.setError(baseGetHanDetailById.isError());
					baseGetHan.setMessage(baseGetHanDetailById.getMessage());
					baseGetHan.setModel(null);
					return baseGetHan;
				}
				List<EppListHandoverDetail> listA = baseGetHanDetailById
						.getListModel();

				List<ReceiptManager> listRM = new ArrayList<ReceiptManager>();
				Set<String> receipts = new HashSet<String>();
				List<String> tranIds = new ArrayList<String>();
				if (listA != null) {
					for (EppListHandoverDetail detailA : listA) {
						BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
								.findTransactionById(detailA.getTransactionId());
						if (!baseFindTran.isError()
								|| baseFindTran.getMessage() != null) {
							baseGetHan.setError(baseFindTran.isError());
							baseGetHan.setMessage(baseFindTran.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						NicTransaction txn = baseFindTran.getModel();
						if (txn != null) {
							receipts.add(txn.getRecieptNo());
						}
						tranIds.add(detailA.getTransactionId());
					}
					for (String receiptNo : receipts) {
						BaseModelList<EppRecieptManager> baseFindRM = rmService
								.findAllRecieptManager(receiptNo);
						if (!baseFindRM.isError()
								|| baseFindRM.getMessage() != null) {
							baseGetHan.setError(baseFindRM.isError());
							baseGetHan.setMessage(baseFindRM.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						List<EppRecieptManager> list = baseFindRM
								.getListModel();
						if (list == null || list.size() == 0)
							continue;
						EppRecieptManager eppR = list.get(0);
						ReceiptManager rm = new ReceiptManager();
						rm.setReceiptNo(eppR.getRecieptNo());
						rm.setOfficeName(eppR.getOfficeName());
						rm.setPaymentAmount(eppR.getPaymentAmount());
						rm.setName(eppR.getFullname());
						rm.setPhone(eppR.getPhone());
						rm.setPaymentFlag(eppR.getPaymentFlag());
						if (eppR.getDob() != null) {
							String dateExp = service.perso.util.HelperClass
									.convertDateToString(eppR.getDob(),
											STR_FORMAT_DATE_yyyyMMdd);
							rm.setDob(HelperClass.loadDateOfBirths(dateExp,
									eppR.getDateDob()));
						}
						rm.setAddress(eppR.getAddress());
						rm.setNin(eppR.getNinNumber());
						List<ReceiptBill> listRB = new ArrayList<ReceiptBill>();
						BaseModelList<DetailRecieptFee> baseFindAllDR = drFeeService
								.findAllDetailRecieptFee(receiptNo);
						if (!baseFindAllDR.isError()
								|| baseFindAllDR.getMessage() != null) {
							baseGetHan.setError(baseFindAllDR.isError());
							baseGetHan.setMessage(baseFindAllDR.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						List<DetailRecieptFee> listRF = baseFindAllDR
								.getListModel();
						if (listRF != null && listRF.size() > 0) {
							for (DetailRecieptFee fee : listRF) {
								ReceiptBill bill = new ReceiptBill();
								bill.setReceiptNo(receiptNo);
								bill.setCode(fee.getCodeBill());
								bill.setNumber(fee.getNumberBill());
								bill.setBillFlag(fee.getPriceFlag());
								bill.setDescription(fee.getDescription());
								bill.setPrice(fee.getPrice());
								bill.setCashierName(fee.getCashierName());
								bill.setCreateByName(fee.getCreateByName());
								if (fee.getDateOfReceipt() != null) {
									String dateDelete = service.perso.util.HelperClass
											.convertDateToString(
													fee.getDateOfReceipt(),
													STR_FORMAT_DATE_yyyyMMdd);
									bill.setDateOfReceipt(dateDelete);
								}
								bill.setCustomerName(fee.getCustomerName());
								bill.setCreateBy(fee.getCreateBy());
								if (fee.getCreateDate() != null) {
									String dateDelete = service.perso.util.HelperClass
											.convertDateToString(
													fee.getCreateDate(),
													STR_FORMAT_DATE_yyyyMMddHHmmss);
									bill.setCreateDate(dateDelete);
								}
								listRB.add(bill);
							}
							rm.setBills(listRB);
						}

						List<FeeRecieptPaymentModel> listFrpModel = new ArrayList<FeeRecieptPaymentModel>();
						BaseModelList<FeeRecieptPayment> baseFindAllRP = feeRecieptPaymentService
								.findAllFeeRecieptPayment(receiptNo);
						if (!baseFindAllRP.isError()
								|| baseFindAllRP.getMessage() != null) {
							baseGetHan.setError(baseFindAllRP.isError());
							baseGetHan.setMessage(baseFindAllRP.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						List<FeeRecieptPayment> listFrp = baseFindAllRP
								.getListModel();
						if (listFrp != null && listFrp.size() > 0) {
							for (FeeRecieptPayment fee : listFrp) {
								FeeRecieptPaymentModel bill = new FeeRecieptPaymentModel();
								bill.setRecieptNo(receiptNo);
								bill.setTypePayment(fee.getTypePayment());
								bill.setAmount(fee.getAmount());
								bill.setCreateBy(fee.getCreateBy());
								bill.setDescription(fee.getDescription());
								bill.setPrice(fee.getPrice());
								bill.setTotal(fee.getTotal());
								bill.setUnit(fee.getUnit());
								listFrpModel.add(bill);
							}
							rm.setFeeRecieptPayment(listFrpModel);
						}

						rm.setRegSiteCode(handover.getSiteCode());
						rm.setDateOfIssue(eppR.getDateOfIssue());
						rm.setDateInWeek(eppR.getDateInWeek());
						rm.setNote(eppR.getNote());
						rm.setPlaceOfReciept(eppR.getPlaceOfReceipt());
						if (eppR.getDeliveryAtOffice() != null)
							rm.setDeliveryAtOffice(eppR.getDeliveryAtOffice() ? "Y"
									: "N");
						rm.setDeliveryOffice(eppR.getDeliveryOffice());
						rm.setAmountOfPassport(eppR.getAmountOfPassport());
						rm.setAmountOfRegistration(eppR
								.getAmountOfRegistration());
						rm.setAmountOfPerson(eppR.getAmountOfPerson());
						rm.setAmountOfImage(eppR.getAmountOfImage());
						rm.setDocumentType(eppR.getDocumentType());
						rm.setPrevPassportNo(eppR.getPrevPassportNo());
						rm.setAddedDocuments(eppR.getAddedDocuments());
						rm.setDocumentaryNo(eppR.getDocumentaryNo());
						rm.setDocumentaryIssued(eppR.getDocumentaryIssued());
						rm.setStatus(eppR.getStatus());
						rm.setIsDelivered(eppR.getIsDelivered());
						rm.setIsPostOffice(eppR.getIsPostOffice() ? "Y" : "N");
						rm.setNoteOfDelivery(eppR.getNoteOfDelivery());
						rm.setSignName(eppR.getSignName());
						rm.setDocOfDelivery(eppR.getDocOfDelivery());
						rm.setDocumentaryOffice(eppR.getDocmentaryOffice());
						rm.setDocumentaryAddress(eppR.getDocmentaryAddress());
						rm.setListCode(eppR.getListCode());
						if (eppR.getInputCompleted() != null) {
							rm.setInputCompleted(eppR.getInputCompleted() ? "Y"
									: "N");
						}

						if (eppR.getDeletedDate() != null) {
							String dateDelete = service.perso.util.HelperClass
									.convertDateToString(eppR.getDeletedDate(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							rm.setDeletedDate(dateDelete);
						}
						rm.setDeletedBy(eppR.getDeletedBy());
						rm.setDeletedName(eppR.getDeletedName());
						rm.setDeletedReason(eppR.getDeletedReason());
						rm.setCreateByName(eppR.getCreateByName());
						rm.setReceivedBy(eppR.getReceivedBy());
						rm.setCreateBy(eppR.getCreateBy());
						if (eppR.getCreateDate() != null) {
							String dateDelete = service.perso.util.HelperClass
									.convertDateToString(eppR.getCreateDate(),
											STR_FORMAT_DATE_yyyyMMddHHmmss);
							rm.setCreateDate(dateDelete);
						}
						List<DetailHandover> listDH = new ArrayList<DetailHandover>();
						BaseModelSingle<NicUploadJob> baseFindUJ = uploadJobService
								.findNicUpByReceiptNo(handover.getId()
										.getPackageId(), receiptNo,
										transactionId);
						if (!baseFindUJ.isError()
								|| baseFindUJ.getMessage() != null) {
							baseGetHan.setError(baseFindUJ.isError());
							baseGetHan.setMessage(baseFindUJ.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						NicUploadJob nicUp = baseFindUJ.getModel();

						if (nicUp != null) {
							BaseModelSingle<EppListHandoverDetail> baseGetHD = eppListHandoverDetailService
									.getListPackageByPackageIdAndTranID(
											handover.getId().getPackageId(),
											transactionId,
											NicListHandover.TYPE_LIST_A);
							if (!baseGetHD.isError()
									|| baseGetHD.getMessage() != null) {
								baseGetHan.setError(baseGetHD.isError());
								baseGetHan.setMessage(baseGetHD.getMessage());
								baseGetHan.setModel(null);
								return baseGetHan;
							}
							EppListHandoverDetail detailA = baseGetHD
									.getModel();
							if (detailA != null) {
								DetailHandover tp = new DetailHandover();
								tp.setPackageId(detailA.getPackageId());
								tp.setTransactionId(detailA.getTransactionId());
								tp.setOfferStage(detailA.getProposalStage());
								tp.setApproveStage(detailA.getApproveStage());
								tp.setNoteOffer(detailA.getProposalNote());
								tp.setNoteApprove(detailA.getApproveNote());
								tp.setPersonCode(nicUp.getNicTransaction()
										.getPersonCode());

								tp.setPaBlacklistId(nicUp.getNicTransaction()
										.getPaBlacklistId());
								tp.setPaInqBllUser(nicUp.getNicTransaction()
										.getPaInqBllUser());
								tp.setPaArchiveCode(nicUp.getNicTransaction()
										.getPaArchiveCode());
								if (nicUp.getNicTransaction()
										.getPaJoinPersonDate() != null) {
									String date = service.perso.util.HelperClass
											.convertDateToString(nicUp
													.getNicTransaction()
													.getPaJoinPersonDate(),
													STR_FORMAT_DATE_yyyyMMddHHmmss);
									tp.setPaJoinPersonDate(date);
								}
								if (nicUp.getNicTransaction()
										.getPaSaveDocDate() != null) {
									String date = service.perso.util.HelperClass
											.convertDateToString(nicUp
													.getNicTransaction()
													.getPaSaveDocDate(),
													STR_FORMAT_DATE_yyyyMMddHHmmss);
									tp.setPaSaveDocDate(date);
								}
								if (nicUp.getNicTransaction()
										.getPaSearchObjDate() != null) {
									String date = service.perso.util.HelperClass
											.convertDateToString(nicUp
													.getNicTransaction()
													.getPaSearchObjDate(),
													STR_FORMAT_DATE_yyyyMMddHHmmss);
									tp.setPaSearchObjDate(date);
								}
								if (nicUp.getNicTransaction().getPaSearchBio() != null)
									tp.setPaSearchBio(nicUp.getNicTransaction()
											.getPaSearchBio() ? "Y" : "N");

								// Tìm mã orgPersonCode
								BaseModelSingle<EppPerson> baseFindPersonByCode = eppPersonService
										.findByStringCode(nicUp
												.getNicTransaction()
												.getPersonCode());
								if (!baseFindPersonByCode.isError()
										|| baseFindPersonByCode.getMessage() != null) {
									baseGetHan.setError(baseFindPersonByCode
											.isError());
									baseGetHan.setMessage(baseFindPersonByCode
											.getMessage());
									baseGetHan.setModel(null);
									return baseGetHan;
								}
								EppPerson eppPerson = baseFindPersonByCode
										.getModel();
								if (eppPerson != null) {
									tp.setPersonOrgCode(eppPerson
											.getOrgPerson() != null ? eppPerson
											.getOrgPerson() : eppPerson
											.getPersonCode());
									PersonModel person = new PersonModel();
									person.setDateOfBirth(eppPerson
											.getDateOfBirth());
									person.setDateOfIdIssue(eppPerson
											.getDateOfIdIssue());
									person.setEthNic(eppPerson.getEthnic());
									person.setEthnicCode(eppPerson
											.getEthnicCode());
									person.setFatherName(eppPerson
											.getFatherName());
									person.setFatherSearchName(eppPerson
											.getFatherSearchName());
									person.setGender(eppPerson.getGender());
									person.setIdNumber(eppPerson.getIdNumber());
									person.setMotherName(eppPerson
											.getMotherName());
									person.setMotherSearchName(eppPerson
											.getMotherSearchName());
									person.setName(eppPerson.getName());
									person.setNationalityCode(eppPerson
											.getNationalityCode());
									person.setNationalityName(eppPerson
											.getNationalityName());
									person.setPersonCode(eppPerson
											.getPersonCode());
									person.setPersonOrgCode(eppPerson
											.getOrgPerson() != null ? eppPerson
											.getOrgPerson() : eppPerson
											.getPersonCode());
									person.setPlaceOfBirthCode(eppPerson
											.getPlaceOfBirthCode());
									person.setPlaceOfBirthName(eppPerson
											.getPlaceOfBirthName());
									person.setPlaceOfIdIssueName(eppPerson
											.getPlaceOfIdIssueName());
									person.setRefId(eppPerson.getRefId());
									person.setReligion(eppPerson.getReligion());
									person.setReligionCode(eppPerson
											.getReligionCode());
									person.setSearchName(eppPerson
											.getSearchName());
									person.setCreatedBy(eppPerson
											.getCreatedBy());
									person.setCreatedByName(eppPerson
											.getCreatedByName());
									if (eppPerson.getCreateTs() != null) {
										String date = service.perso.util.HelperClass
												.convertDateToString(
														eppPerson.getCreateTs(),
														STR_FORMAT_DATE_yyyyMMddHHmmss);
										person.setCreatedDate(date);
									}

									if (eppPerson.getUpdateTs() != null) {
										String date = service.perso.util.HelperClass
												.convertDateToString(
														eppPerson.getUpdateTs(),
														STR_FORMAT_DATE_yyyyMMddHHmmss);
										person.setUpdatedDate(date);
									}
									person.setUpdatedBy(eppPerson
											.getUpdatedBy());
									person.setUpdatedByName(eppPerson
											.getUpdatedByName());
									person.setIsChecked((eppPerson
											.getIsChecked() != null && eppPerson
											.getIsChecked()) ? "Y" : "N");
									person.setDescription(eppPerson
											.getDescription());
									person.setSrcOffice(eppPerson
											.getSrcOffice());
									person.setStatus(eppPerson.getStatus());
									person.setOtherName(eppPerson
											.getOtherName());
									person.setCountryOfBirth(eppPerson
											.getCountryOfBirth());

									List<PersonFamily> families = new ArrayList<PersonFamily>();
									// Tìm danh sách gia đình
									BaseModelList<EppPersonFamily> baseFindPerson = eppPersonService
											.findAllEppPersonFamily1(eppPerson
													.getPersonId());
									if (!baseFindPerson.isError()
											|| baseFindPerson.getMessage() != null) {
										baseGetHan.setError(baseFindPerson
												.isError());
										baseGetHan.setMessage(baseFindPerson
												.getMessage());
										baseGetHan.setModel(null);
										return baseGetHan;
									}
									List<EppPersonFamily> listf = baseFindPerson
											.getListModel();
									if (listf != null && listf.size() > 0) {
										for (EppPersonFamily f : listf) {
											PersonFamily family = new PersonFamily();
											family.setDateOfBirth(f
													.getDateOfBirth());
											family.setGender(f.getGender());
											family.setLost(f.getLost());
											family.setName(f.getName());
											family.setPlaceOfBirth(f
													.getPlaceOfBirthCode());
											family.setRelationship(f
													.getRelationship());
											families.add(family);
										}
									}
									person.setFamilies(families);
									tp.setOrgPerson(person);
								} else
									tp.setPersonOrgCode(nicUp
											.getNicTransaction().getNote()); // Luu
																				// tam
																				// neu
																				// ko
																				// co
																				// du
																				// lieu
																				// person

								BaseModelSingle<NicTransactionPayment> baseFindPayByTran = nicTransactionPaymentDao
										.findGetPaymentByTransaction(detailA
												.getTransactionId());
								if (!baseFindPayByTran.isError()
										|| baseFindPayByTran.getMessage() != null) {
									baseGetHan.setError(baseFindPayByTran
											.isError());
									baseGetHan.setMessage(baseFindPayByTran
											.getMessage());
									baseGetHan.setModel(null);
									return baseGetHan;
								}
								NicTransactionPayment payments = baseFindPayByTran
										.getModel();
								if (payments == null)
									continue;
								BaseModelList<NicTransactionPaymentDetail> baseFindDPay = paymentDetailService
										.findListDetailPaymentList(
												payments.getPaymentId(), null);
								if (!baseFindDPay.isError()
										|| baseFindDPay.getMessage() != null) {
									baseGetHan.setError(baseFindDPay.isError());
									baseGetHan.setMessage(baseFindDPay
											.getMessage());
									baseGetHan.setModel(null);
									return baseGetHan;
								}
								List<NicTransactionPaymentDetail> lsDetail = baseFindDPay
										.getListModel();
								if (lsDetail != null) {
									List<PaymentDetail> listPD = new ArrayList<PaymentDetail>();
									for (NicTransactionPaymentDetail pay : lsDetail) {
										PaymentDetail detail = new PaymentDetail();
										detail.setTransactionId(detailA
												.getTransactionId());
										detail.setNamePayment(pay.getNote());
										detail.setTypePayment(pay
												.getTypePayment());
										detail.setSubTypePayment(pay
												.getSubTypePayment());
										detail.setPaymentAmount(pay
												.getPaymentAmount());
										detail.setStatusFee(pay.isStatusFee() ? "Y"
												: "N");
										listPD.add(detail);
									}
									tp.setPayments(listPD);
								}
								listDH.add(tp);
							}

						}
						rm.setHandovers(listDH);
						listRM.add(rm);
					}
				}
				nicHandover.setReceipts(listRM);

				// queueJobService.updateStatusQueueJob(queue.getId(),
				// Contants.CODE_STATUS_QUEUE_DOING);

			}
			baseGetHan.setError(true);
			baseGetHan.setMessage(null);
			baseGetHan.setModel(nicHandover);
		} catch (Exception e) {
			baseGetHan.setError(false);
			baseGetHan.setMessage(CreateMessageException
					.createMessageException(e) + "getHandoverA - thất bại.");
			baseGetHan.setModel(null);

		}
		return baseGetHan;
	}

	private BaseModelSingle<HandoverB> getHandoverB(String transactionId,
			String site, long queueId) {
		BaseModelSingle<HandoverB> baseGetHan = new BaseModelSingle<HandoverB>();
		HandoverB nicHandover = new HandoverB();
		try {
			BaseModelList<EppListHandoverDetail> baseGetHanDetailByTranId = eppListHandoverDetailService
					.getPackageNameByTransactionId(transactionId);
			if (!baseGetHanDetailByTranId.isError()
					|| baseGetHanDetailByTranId.getMessage() != null) {
				baseGetHan.setError(baseGetHanDetailByTranId.isError());
				baseGetHan.setMessage(baseGetHanDetailByTranId.getMessage());
				baseGetHan.setModel(null);
				return baseGetHan;
			}
			List<EppListHandoverDetail> details = baseGetHanDetailByTranId
					.getListModel();
			NicListHandover handover = new NicListHandover();
			for (EppListHandoverDetail d : details) {
				BaseModelSingle<NicListHandover> baseFindHan = listHandoverService
						.findByPackageId(new NicListHandoverId(
								d.getPackageId(), NicListHandover.TYPE_LIST_B));
				if (!baseFindHan.isError() || baseFindHan.getMessage() != null) {
					baseGetHan.setError(baseFindHan.isError());
					baseGetHan.setMessage(baseFindHan.getMessage());
					baseGetHan.setModel(null);
					return baseGetHan;
				}
				handover = baseFindHan.getModel();
				if (handover != null
						&& handover.getId().getTypeList().equals("B")) {
					break;
				}
			}
			if (handover != null) {
				// SyncQueueJob queue =
				// queueJobService.findQueueByObjectId(handover.getPackageId(),
				// site, Contants.CODE_STATUS_QUEUE_PENDING,
				// Contants.QUEUE_OBJ_TYPE_DB);
				nicHandover.setIdQueue(queueId);
				if (handover != null) {
					nicHandover.setPackageId(handover.getId().getPackageId());
					nicHandover.setSiteCode(handover.getSiteCode());
					if (handover.getApproveDate() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(handover.getApproveDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						nicHandover.setApproveDate(dateExp);
					}
					if (handover.getProposalDate() != null) {
						String dateExp = service.perso.util.HelperClass
								.convertDateToString(
										handover.getProposalDate(),
										STR_FORMAT_DATE_yyyyMMddHHmmss);
						nicHandover.setOfferDate(dateExp);
					}
					nicHandover.setApproveName(handover.getApproveName());
					nicHandover.setOfferUserName(handover.getProcessUsers());
					nicHandover.setType(8);
					nicHandover.setCount(handover.getCountTransaction());
				}
				BaseModelList<EppListHandoverDetail> baseGetHDById = eppListHandoverDetailService
						.getListPackageByPackageId(handover.getId()
								.getPackageId(), NicListHandover.TYPE_LIST_B);
				if (!baseGetHDById.isError()
						|| baseGetHDById.getMessage() != null) {
					baseGetHan.setError(baseGetHDById.isError());
					baseGetHan.setMessage(baseGetHDById.getMessage());
					baseGetHan.setModel(null);
					return baseGetHan;
				}
				List<EppListHandoverDetail> listB = baseGetHDById
						.getListModel();
				if (listB != null) {
					List<DetailHandoverB> listDH = new ArrayList<DetailHandoverB>();
					for (EppListHandoverDetail detailA : listB) {
						DetailHandoverB tp = new DetailHandoverB();
						tp.setPackageId(detailA.getPackageId());
						tp.setTransactionId(detailA.getTransactionId());
						tp.setOfferStage(detailA.getProposalStage());
						tp.setApproveStage(detailA.getApproveStage());
						tp.setNoteOffer(detailA.getProposalNote());
						tp.setNoteApprove(detailA.getApproveNote());
						BaseModelSingle<NicTransactionPayment> baseFindTranPay = nicTransactionPaymentDao
								.findGetPaymentByTransaction(detailA
										.getTransactionId());
						if (!baseFindTranPay.isError()
								|| baseFindTranPay.getMessage() != null) {
							baseGetHan.setError(baseFindTranPay.isError());
							baseGetHan.setMessage(baseFindTranPay.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						NicTransactionPayment payments = baseFindTranPay
								.getModel();
						if (payments == null)
							continue;
						BaseModelList<NicTransactionPaymentDetail> baseFindDPay = paymentDetailService
								.findListDetailPaymentList(
										payments.getPaymentId(), null);
						if (!baseFindDPay.isError()
								|| baseFindDPay.getMessage() != null) {
							baseGetHan.setError(baseFindDPay.isError());
							baseGetHan.setMessage(baseFindDPay.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						List<NicTransactionPaymentDetail> lsDetail = baseFindDPay
								.getListModel();
						if (lsDetail != null) {
							List<PaymentDetail> listPD = new ArrayList<PaymentDetail>();
							for (NicTransactionPaymentDetail pay : lsDetail) {
								PaymentDetail detail = new PaymentDetail();
								detail.setTransactionId(detailA
										.getTransactionId());
								detail.setNamePayment(pay.getNote());
								detail.setTypePayment(pay.getTypePayment());
								detail.setSubTypePayment(pay
										.getSubTypePayment());
								detail.setPaymentAmount(pay.getPaymentAmount());
								detail.setStatusFee(pay.isStatusFee() ? "Y"
										: "N");
								listPD.add(detail);
							}
							tp.setPayments(listPD);
						}
						listDH.add(tp);
					}
					nicHandover.setHandovers(listDH);

					// queueJobService.updateStatusQueueJob(queue.getId(),
					// Contants.CODE_STATUS_QUEUE_DOING);

				}
			}
			baseGetHan.setError(true);
			baseGetHan.setMessage(null);
			baseGetHan.setModel(nicHandover);
		} catch (Exception e) {
			baseGetHan.setError(false);
			baseGetHan.setMessage(CreateMessageException
					.createMessageException(e) + "getHandoverB - thất bại.");
			baseGetHan.setModel(null);
		}
		return baseGetHan;
	}

	private BaseModelSingle<HandoverC> getHandoverC(String transactionId,
			String site, long queueId) {
		BaseModelSingle<HandoverC> baseGetHan = new BaseModelSingle<HandoverC>();
		HandoverC handoverC = new HandoverC();
		try {
			BaseModelList<EppListHandoverDetail> baseGetHDByTranId = eppListHandoverDetailService
					.getPackageNameByTransactionId(transactionId);
			if (!baseGetHDByTranId.isError()
					|| baseGetHDByTranId.getMessage() != null) {
				baseGetHan.setError(baseGetHDByTranId.isError());
				baseGetHan.setMessage(baseGetHDByTranId.getMessage());
				baseGetHan.setModel(null);
				return baseGetHan;
			}
			List<EppListHandoverDetail> details = baseGetHDByTranId
					.getListModel();
			NicListHandover handover = new NicListHandover();
			for (EppListHandoverDetail d : details) {
				BaseModelSingle<NicListHandover> baseFindHan = listHandoverService
						.findByPackageId(new NicListHandoverId(
								d.getPackageId(), NicListHandover.TYPE_LIST_C));
				if (!baseFindHan.isError() || baseFindHan.getMessage() != null) {
					baseGetHan.setError(baseFindHan.isError());
					baseGetHan.setMessage(baseFindHan.getMessage());
					baseGetHan.setModel(null);
					return baseGetHan;
				}
				handover = baseFindHan.getModel();
				if (handover != null
						&& handover.getId().getTypeList().equals("C")) {
					break;
				}
			}
			if (handover != null) {
				// SyncQueueJob queue =
				// queueJobService.findQueueByObjectId(handover.getPackageId(),
				// site, Contants.CODE_STATUS_QUEUE_PENDING,
				// Contants.QUEUE_OBJ_TYPE_DC);
				handoverC.setPackageId(handover.getId().getPackageId());
				handoverC.setType("C");
				handoverC.setApproveName(handover.getApproveName());
				handoverC.setIdQueue(queueId);
				List<DetailHandoverC> detailCList = new ArrayList<DetailHandoverC>();
				BaseModelList<EppListHandoverDetail> baseGetHDById = eppListHandoverDetailService
						.getListPackageByPackageId(handover.getId()
								.getPackageId(), NicListHandover.TYPE_LIST_C);
				if (!baseGetHDById.isError()
						|| baseGetHDById.getMessage() != null) {
					baseGetHan.setError(baseGetHDById.isError());
					baseGetHan.setMessage(baseGetHDById.getMessage());
					baseGetHan.setModel(null);
					return baseGetHan;
				}
				List<EppListHandoverDetail> detailList = baseGetHDById
						.getListModel();
				if (detailList != null) {
					for (EppListHandoverDetail tp : detailList) {
						DetailHandoverC dtHanC = new DetailHandoverC();
						List<PaymentDetail> listPD = new ArrayList<PaymentDetail>();
						dtHanC.setTransactionId(tp.getTransactionId());
						BaseModelSingle<NicUploadJob> baseFindUJ = uploadJobService
								.findUploadJobByTransId(tp.getTransactionId());
						if (!baseFindUJ.isError()
								|| baseFindUJ.getMessage() != null) {
							baseGetHan.setError(baseFindUJ.isError());
							baseGetHan.setMessage(baseFindUJ.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						NicUploadJob nicUp = baseFindUJ.getModel();
						BaseModelSingle<NicTransaction> baseFindTran = nicTransactionService
								.findTransactionById(tp.getTransactionId());
						if (!baseFindTran.isError()
								|| baseFindTran.getMessage() != null) {
							baseGetHan.setError(baseFindTran.isError());
							baseGetHan.setMessage(baseFindTran.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						NicTransaction txn = baseFindTran.getModel();
						if (txn != null) {
							dtHanC.setReceiptNo(txn.getRecieptNo());
							dtHanC.setRegistrationNo(txn.getRegistrationNo());
							dtHanC.setTransactionStatus(txn
									.getTransactionStatus());
						}
						if (nicUp != null) {
							// dtHanC.setPersonId(nicUp.getOriginalyPersonId());
							// List<EppPerson> lstPer =
							// eppPersonService.findByGlobalId(nicUp.getOriginalyPersonId());

							// if(lstPer != null && lstPer.size() > 0){
							// EppPerson person = lstPer.get(0);
							// dtHanC.setGlobalId(person.getGlobalId());
							// }
						}
						// Lấy thông tin hộ chiếu
						BaseModelSingle<NicDocumentData> baseGetDocData = documentDataService
								.getDocumentDataById(tp.getTransactionId());
						if (!baseGetDocData.isError()
								|| baseGetDocData.getMessage() != null) {
							baseGetHan.setError(baseGetDocData.isError());
							baseGetHan.setMessage(baseGetDocData.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						NicDocumentData docData = baseGetDocData.getModel();
						if (docData != null) {
							dtHanC.setPassportNo(docData.getId()
									.getPassportNo());
						}
						BaseModelSingle<NicTransactionPayment> baseFindPayByTran = nicTransactionPaymentDao
								.findGetPaymentByTransaction(tp
										.getTransactionId());
						if (!baseFindPayByTran.isError()
								|| baseFindPayByTran.getMessage() != null) {
							baseGetHan.setError(baseFindPayByTran.isError());
							baseGetHan.setMessage(baseFindPayByTran
									.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						NicTransactionPayment payment = baseFindPayByTran
								.getModel();
						List<NicTransactionPaymentDetail> listPayD = null;
						if (payment != null) {
							BaseModelList<NicTransactionPaymentDetail> baseFindDP = paymentDetailService
									.findListDetailPaymentList(payment
											.getPaymentId());
							if (!baseFindDP.isError()
									|| baseFindDP.getMessage() != null) {
								baseGetHan.setError(baseFindDP.isError());
								baseGetHan.setMessage(baseFindDP.getMessage());
								baseGetHan.setModel(null);
								return baseGetHan;
							}
							listPayD = baseFindDP.getListModel();
						}
						if (listPayD != null && listPayD.size() > 0) {
							for (NicTransactionPaymentDetail td : listPayD) {
								PaymentDetail paymentDetail = new PaymentDetail();
								paymentDetail.setTransactionId(tp
										.getTransactionId());
								paymentDetail.setNamePayment(td.getNote());
								paymentDetail.setTypePayment(td
										.getTypePayment());
								paymentDetail.setSubTypePayment(td
										.getSubTypePayment());
								paymentDetail.setPaymentAmount(td
										.getPaymentAmount());
								paymentDetail
										.setStatusFee(td.isStatusFee() ? "Y"
												: "N");
								listPD.add(paymentDetail);
							}
						}
						dtHanC.setPayments(listPD);

						InfoPassportC info = new InfoPassportC();
						if (docData != null) {
							info.setChipSerialNo(docData.getChipSerialNo());
							info.setPassportNo(docData.getId().getPassportNo());
							// info.setDateOfExpiry(docData.getDateOfExpiry());
							if (docData.getDateOfExpiry() != null) {
								String date = service.perso.util.HelperClass
										.convertDateToString(
												docData.getDateOfExpiry(),
												STR_FORMAT_DATE_yyyyMMddHHmmss);
								info.setDateOfExpiry(date);
							}
							// info.setDateOfIssue(docData.getDateOfIssue());
							if (docData.getDateOfExpiry() != null) {
								String date = service.perso.util.HelperClass
										.convertDateToString(
												docData.getDateOfIssue(),
												STR_FORMAT_DATE_yyyyMMddHHmmss);
								info.setDateOfIssue(date);
							}
							info.setIcaoLine1(docData.getIcaoLine1());
							info.setIcaoLine2(docData.getIcaoLine2());
							info.setSignerName(docData.getSigner());
							info.setSignerPosition(docData.getPositionSigner());
							info.setStatus(docData.getStatus());
						}
						BaseModelSingle<NicRegistrationData> baseFindRegData = nicRegistrationDataService
								.findRegistDataById(tp.getTransactionId());
						if (!baseFindRegData.isError()
								|| baseFindRegData.getMessage() != null) {
							baseGetHan.setError(baseFindRegData.isError());
							baseGetHan.setMessage(baseFindRegData.getMessage());
							baseGetHan.setModel(null);
							return baseGetHan;
						}
						NicRegistrationData reg = baseFindRegData.getModel();
						info.setFpEncode(reg != null ? reg.getFpEncode() : null);
						if (txn != null) {
							info.setPlaceOfIssueId(txn.getIssSiteCode());
							info.setPlaceOfIssueName(this.getSiteName(txn
									.getIssSiteCode()));
							info.setPassportType(txn.getPassportType());
						}
						info.setIsEpassport(txn.getIsEpassport().equals("Y") ? true
								: false);
						info.setPersonId(nicUp != null ? nicUp
								.getNicTransaction().getPersonCode() + ""
								: null);
						dtHanC.setInfo(info);
						detailCList.add(dtHanC);

					}
					handoverC.setHandovers(detailCList);

					// queueJobService.updateStatusQueueJob(queue.getId(),
					// Contants.CODE_STATUS_QUEUE_DOING);

				}
			}
			baseGetHan.setError(true);
			baseGetHan.setMessage(null);
			baseGetHan.setModel(handoverC);
		} catch (Exception e) {
			baseGetHan.setError(false);
			baseGetHan.setMessage(CreateMessageException
					.createMessageException(e) + " - getHandoverC - thất bại");
			baseGetHan.setModel(null);
		}
		return baseGetHan;
	}

	private String getTransactionStage(String transactionStatus) {
		String stage = "";

		if (StringUtils.equals(transactionStatus, "RD")
				|| StringUtils.equals(transactionStatus,
						Contants.TRANSACTION_STATUS_RIC_CARD_RECEIVED)) {
			stage = Contants.TRANSACTION_STAGE_RIC_CARD_RECEPTION;
		} else if (StringUtils.equals(transactionStatus, "OK")
				|| StringUtils.equals(transactionStatus,
						Contants.TRANSACTION_STATUS_RIC_CARD_ISSUED)) {
			stage = Contants.TRANSACTION_STAGE_RIC_ISSUANCE;
		} else if (StringUtils.equals(transactionStatus, "KO")
				|| StringUtils.equals(transactionStatus,
						Contants.TRANSACTION_STATUS_RIC_CARD_REJECTED)) {
			stage = Contants.TRANSACTION_STAGE_RIC_ISSUANCE;
		}

		return stage;
	}

	// check validate input getArchiveCodeNumber
	private ResponseBase validateArchiveCode(String archiveCode) {
		ResponseBase rb = null;
		if (archiveCode.length() < 7) {
			rb = new ResponseBase();
			rb.setCode(Contants.CODE_INPUT_FAILD);
			rb.setMessage("archiveCode" + Contants.MESSAGE_INPUT_FORMAT_ERROR);
		} else {
			try {
				Integer.parseInt(archiveCode.substring(2, 4));
				Integer.parseInt(archiveCode.substring(6, archiveCode.length()));
			} catch (Exception e) {
				rb = new ResponseBase();
				rb.setCode(Contants.CODE_INPUT_FAILD);
				rb.setMessage("archiveCode"
						+ Contants.MESSAGE_INPUT_FORMAT_ERROR);
			}
		}
		return rb;
	}

	// check validate full transaction
	private ResponseBase checkValidateFullTransaction(
			FullTransactionModel request) {
		if (request.getMhandoverA() != null) {
			ResponseBase rb;
			rb = this.checkValidateMHandoverA(request.getMhandoverA());

			if (rb != null) {
				rb.setMessage("HandoverA - " + rb.getMessage());
				return rb;
			}
		}
		if (request.getMhandoverB() != null) {
			ResponseBase rb;
			rb = this.checkValidateMHandoverB(request.getMhandoverB());

			if (rb != null) {
				rb.setMessage("HandoverB - " + rb.getMessage());
				return rb;
			}
		}
		if (request.getMhandoverC() != null) {
			ResponseBase rb;
			rb = this.checkValidateMHandoverC(request.getMhandoverC());

			if (rb != null) {
				rb.setMessage("HandoverC - " + rb.getMessage());
				return rb;
			}
		}
		if (request.getMpassport() == null) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Mpassport"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			ResponseBase rb;
			rb = this.checkValidateMpassport(request.getMpassport());

			if (rb != null) {
				rb.setMessage("Mpassport - " + rb.getMessage());
				return rb;
			}
		}
		if (request.getOrgPerson() == null) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OrgPerson"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			ResponseBase rb;
			rb = this.checkValidateOrgPerson(request.getOrgPerson());

			if (rb != null) {
				rb.setMessage("OrgPerson - " + rb.getMessage());
				return rb;
			}
		}
		if (request.getTransactionF() == null) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TransactionF"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			ResponseBase rb;
			rb = this.checkValidateTransactionF(request.getTransactionF());
			if (rb != null) {
				rb.setMessage("TransactionF - " + rb.getMessage());
				return rb;
			}
		}
		return null;
	}

	private ResponseBase checkValidateTransactionF(Transaction transaction) {
		if (StringUtils.isBlank(transaction.getTransactionId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TransactionId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Nin"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getDateOfApplication())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"DateOfApplication" + Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(transaction.getDateOfApplication())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"DateOfApplication" + Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(transaction.getDateOfApplication())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"DateOfApplication" + Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - length != 14");
		} else if (!checkFormatDatetime(transaction.getDateOfApplication())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"DateOfApplication" + Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - format != yyyyMMddHHmmss");
		}
		if (StringUtils.isBlank(transaction.getEstDateOfCollection())) {
			// return new ResponseBase(Contants.CODE_INPUT_FAILD,
			// "EstDateOfCollection" + Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(transaction.getEstDateOfCollection())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"EstDateOfCollection" + Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(transaction.getEstDateOfCollection())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"EstDateOfCollection" + Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - length != 8");
		} else if (!checkFormatDate(transaction.getEstDateOfCollection())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"EstDateOfCollection" + Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - format != yyyyMMdd");
		}
		if (StringUtils.isBlank(transaction.getPassportType())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PassportType"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getPriority().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Priority"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getRegSiteCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "RegSiteCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getIssSiteCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "IssSiteCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getTransactionType())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"TransactionType" + Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getTransactionStatus())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"TransactionStatus" + Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(transaction.getChecksum())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "Checksum" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isBlank(transaction.getIsPostOffice())) {
			// return new ResponseBase(Contants.CODE_INPUT_FAILD, "IsPostOffice"
			// + Contants.MESSAGE_INPUT_NULL);
		} else if (!transaction.getIsPostOffice().trim().equals("Y")
				&& !transaction.getIsPostOffice().trim().equals("N")) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "IsPostOffice"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - format: Y or N");
		}
		if (StringUtils.isBlank(transaction.getRecieptNo())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "RecieptNo"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(transaction.getRegistrationNo())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "RegistrationNo" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (transaction.getRegisData() == null) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "RegisData"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			ResponseBase rb = checkValidateRegistrationDataF(transaction
					.getRegisData());
			if (rb != null) {
				rb.setMessage("RegisData - " + rb.getMessage());
				return rb;
			}
		}
		if (transaction.getDocuments() == null
				|| transaction.getDocuments().size() == 0) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Documents"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			for (TransactionDocument tranDoc : transaction.getDocuments()) {
				ResponseBase rb = this
						.checkValidateTransactionDocumentF(tranDoc);
				if (rb != null) {
					rb.setMessage("Documents - " + rb.getMessage());
					return rb;
				}
			}
		}
		if (transaction.getFamilies() != null
				|| transaction.getFamilies().size() > 0) {
			for (PersonFamily pf : transaction.getFamilies()) {
				ResponseBase rb = this.checkValidatePersonFamilyF(pf);
				if (rb != null) {
					rb.setMessage("Families - " + rb.getMessage());
					return rb;
				}
			}
		}
		return null;
	}

	private ResponseBase checkValidatePersonFamilyF(PersonFamily pf) {
		if (StringUtils.isBlank(pf.getName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Name"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pf.getGender())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Gender"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pf.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(pf.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
			// }else if (!checkLengthDate(pf.getDateOfBirth())) {
			// return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
			// + Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 8");
			// }else if (!checkFormatDate(pf.getDateOfBirth())){
			// return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
			// + Contants.MESSAGE_INPUT_FORMAT_ERROR + " - format != yyyyMMdd");
		}
		if (StringUtils.isBlank(pf.getRelationship())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Relationship"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		return null;
	}

	private ResponseBase checkValidateTransactionDocumentF(
			TransactionDocument tDocument) {
		if (StringUtils.isBlank(tDocument.getDocType())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DocType"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tDocument.getSerialNo())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SerialNo"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tDocument.getDocData())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DocData"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		return null;
	}

	private ResponseBase checkValidateRegistrationDataF(
			RegistrationData regisData) {
		if (StringUtils.isBlank(regisData.getFirstName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "FirstName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getMidName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "MidName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getSurName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SurName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getFullName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "FullName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getGender())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Gender"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getPlaceOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PlaceOfBirth"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(regisData.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(regisData.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 8");
		} else if (!checkFormatDate(regisData.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMdd");
		}
		if (StringUtils.isBlank(regisData.getResidentPlaceId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"ResidentPlaceId" + Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(regisData.getResidentAreaId())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "ResidentAreaId" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		// if(StringUtils.isBlank(regisData.getResidentAddress())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "ResidentAddress"
		// + Contants.MESSAGE_INPUT_NULL);
		// }
		// if(StringUtils.isBlank(regisData.getReligion())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "Religion" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		// if(StringUtils.isBlank(regisData.getNation())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "Nation" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		// if(StringUtils.isBlank(regisData.getAddressNin())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "AddressNin" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isBlank(regisData.getDateNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateNin"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(regisData.getDateNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateNin"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(regisData.getDateNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateNin"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 8");
		} else if (!checkFormatDate(regisData.getDateNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateNin"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMdd");
		}
		if (StringUtils.isBlank(regisData.getTotalFp().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TotalFp"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getPersonCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PersonCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		return null;
	}

	private ResponseBase checkValidateOrgPerson(PersonModel p) {
		if (StringUtils.isEmpty(p.getPersonCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PersonCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(p.getName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Name"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(p.getSearchName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SearchName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(p.getGender())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Gender"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		return null;
	}

	private ResponseBase checkValidateMpassport(UpdatePassportModel mpassport) {
		if (StringUtils.isBlank(mpassport.getTransactionId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TransactionId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(mpassport.getPassportNo())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PassportNo"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(mpassport.getStatus())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Status"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(mpassport.getIcaoLine1())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "IcaoLine1"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(mpassport.getIcaoLine2())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "IcaoLine2"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(mpassport.getDateOfIssue())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfIssue"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(mpassport.getDateOfIssue())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfIssue"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(mpassport.getDateOfIssue())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfIssue"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 8");
		} else if (!checkFormatDate(mpassport.getDateOfIssue())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfIssue"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMdd");
		}
		if (StringUtils.isBlank(mpassport.getDateOfExpiry())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfExpiry"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(mpassport.getDateOfExpiry())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfExpiry"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(mpassport.getDateOfExpiry())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfExpiry"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 8");
		} else if (!checkFormatDate(mpassport.getDateOfExpiry())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfExpiry"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMdd");
		}
		return null;
	}

	private ResponseBase checkValidateMHandoverC(UpdatePackageRequest mHandoverC) {
		if (StringUtils.isBlank(mHandoverC.getPackageId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PackageId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(mHandoverC.getFullName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "FullName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(mHandoverC.getUserName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "UserName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(mHandoverC.getCreatedDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "CreatedDate"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(mHandoverC.getCreatedDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "CreatedDate"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(mHandoverC.getCreatedDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "CreatedDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 8");
		} else if (!checkFormatDate(mHandoverC.getCreatedDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "CreatedDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMdd");
		}
		if (mHandoverC.getTransactions() == null
				|| mHandoverC.getTransactions().size() == 0) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Transactions"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			for (TransactionPerso tp : mHandoverC.getTransactions()) {
				ResponseBase rb = this.checkValidateTransactions(tp);
				if (rb != null) {
					rb.setMessage("Transactions - " + rb.getMessage());
					return rb;
				}
			}
		}
		return null;
	}

	private ResponseBase checkValidateTransactions(TransactionPerso tran) {
		if (StringUtils.isBlank(tran.getTransactionId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - TransactionId"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tran.getPassportNo())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - PassportNo"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tran.getSerialPhoi())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - SerialPhoi"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tran.getIcaoLine1())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - IcaoLine1"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tran.getIcaoLine2())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - IcaoLine2"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tran.getSigner())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - Signer"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tran.getPositionSigner())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - PositionSigner"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tran.getPrintSite())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - PrintSite"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tran.getDateOfIssue())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - DateOfIssue"
							+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(tran.getDateOfIssue())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - DateOfIssue"
							+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(tran.getDateOfIssue())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - DateOfIssue"
							+ Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - length != 8");
		} else if (!checkFormatDate(tran.getDateOfIssue())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - DateOfIssue"
							+ Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - format != yyyyMMdd");
		}
		if (StringUtils.isBlank(tran.getDateOfExpire())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - DateOfExpire"
							+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(tran.getDateOfExpire())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - DateOfExpire"
							+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(tran.getDateOfExpire())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - DateOfExpire"
							+ Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - length != 8");
		} else if (!checkFormatDate(tran.getDateOfExpire())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					tran.getTransactionId() + " - DateOfExpire"
							+ Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - format != yyyyMMdd");
		}
		return null;
	}

	private ResponseBase checkValidateMHandoverB(HandoverB mhandoverB) {
		if (StringUtils.isBlank(mhandoverB.getPackageId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PackageId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(mhandoverB.getOfferName())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferName" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isBlank(mhandoverB.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(mhandoverB.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(mhandoverB.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 14");
		} else if (!checkFormatDatetime(mhandoverB.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMddHHmmss");
		}
		if (StringUtils.isBlank(mhandoverB.getApproveName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(mhandoverB.getApproveDate())) {
			// return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
			// + Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(mhandoverB.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(mhandoverB.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 14");
		} else if (!checkFormatDatetime(mhandoverB.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMddHHmmss");
		}
		if (StringUtils.isBlank(mhandoverB.getSiteCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SiteCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(String.valueOf(mhandoverB.getCount()))) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Count"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(String.valueOf(mhandoverB.getType()))) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Type"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (mhandoverB.getHandovers() == null
				|| mhandoverB.getHandovers().size() == 0) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Handovers"
					+ Contants.MESSAGE_INPUT_NULL);
			// }else if (mhandoverB.getCount() !=
			// mhandoverB.getHandovers().size()) {
			// return new
			// ResponseBase(Contants.RESPONSE_CODE_FAIL_NOT_ENOUGH_HS, "Count" +
			// Contants.MESSAGE_NOT_ENOUGH_RECIEPT);
		} else {
			for (DetailHandoverB dh : mhandoverB.getHandovers()) {
				ResponseBase rb = this.checkValidateDetailHandoverB(dh);
				if (rb != null) {
					rb.setMessage("Handovers - " + rb.getMessage());
					return rb;
				}
			}
		}
		return null;
	}

	private ResponseBase checkValidateMHandoverA(HandoverA mhandoverA) {
		if (StringUtils.isBlank(mhandoverA.getPackageId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PackageId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(mhandoverA.getOfferUserName())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferUserName" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isBlank(mhandoverA.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(mhandoverA.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(mhandoverA.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 14");
		} else if (!checkFormatDatetime(mhandoverA.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMddHHmmss");
		}
		// if(StringUtils.isBlank(mhandoverA.getApproveUserName())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveUserName"
		// + Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isBlank(mhandoverA.getApproveDate())) {
			// return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
			// + Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(mhandoverA.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(mhandoverA.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 14");
		} else if (!checkFormatDatetime(mhandoverA.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMddHHmmss");
		}
		if (StringUtils.isBlank(mhandoverA.getSiteCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SiteCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(String.valueOf(mhandoverA.getCount()))) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Count"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (mhandoverA.getReceipts() == null
				|| mhandoverA.getReceipts().size() == 0) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Receipts"
					+ Contants.MESSAGE_INPUT_NULL);
			// }else if (mhandoverA.getCount() !=
			// mhandoverA.getReceipts().size()) {
			// return new
			// ResponseBase(Contants.RESPONSE_CODE_FAIL_NOT_ENOUGH_HS, "Count" +
			// Contants.MESSAGE_NOT_ENOUGH_RECIEPT);
		} else {
			for (ReceiptManager rm : mhandoverA.getReceipts()) {
				ResponseBase rb = this.checkValidateRecieptManagerAF(rm);
				if (rb != null) {
					rb.setMessage("Receipts - " + rb.getMessage());
					return rb;
				}
			}
		}
		return null;
	}

	private ResponseBase checkValidateRecieptManagerAF(ReceiptManager rm) {
		if (rm.getHandovers() == null || rm.getHandovers().size() == 0) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Handovers"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			for (DetailHandover dh : rm.getHandovers()) {
				ResponseBase rb = this.checkValidateDetailHandoverF(dh);
				if (rb != null) {
					rb.setMessage("Handovers - " + rb.getMessage());
					return rb;
				}
			}
		}
		return null;
	}

	private ResponseBase checkValidateDetailHandoverF(
			DetailHandover detailHandover) {
		if (StringUtils.isBlank(detailHandover.getPackageId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PackageId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getTransactionId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TransactionId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(detailHandover.getApproveStage())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveStage" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		// if(StringUtils.isBlank(detailHandover.getNoteOffer())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "NoteOffer" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		// if(StringUtils.isBlank(detailHandover.getNoteApprove())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "NoteApprove" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isBlank(detailHandover.getPersonCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PersonCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getPersonStage())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PersonStage"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(detailHandover.getOfferStage())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferStage" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (detailHandover.getOrgPerson() != null) {
			ResponseBase rb = this.checkValidatePersonModel(detailHandover
					.getOrgPerson());
			if (rb != null) {
				rb.setMessage("OrgPerson - " + rb.getMessage());
				return rb;
			}
		}
		return null;
	}

	// check validate HandoverB
	private ResponseBase checkValidateHandoverB(HandoverB handover) {
		if (StringUtils.isBlank(handover.getPackageId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PackageId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(handover.getOfferName())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferName" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isBlank(handover.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(handover.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(handover.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 14");
		} else if (!checkFormatDatetime(handover.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMddHHmmss");
		}
		if (StringUtils.isBlank(handover.getApproveName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(handover.getApproveDate())) {
			// return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
			// + Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(handover.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(handover.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 14");
		} else if (!checkFormatDatetime(handover.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMddHHmmss");
		}
		if (StringUtils.isBlank(handover.getSiteCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SiteCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(String.valueOf(handover.getCount()))) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Count"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(String.valueOf(handover.getType()))) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Type"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (handover.getHandovers() == null
				|| handover.getHandovers().size() == 0) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Handovers"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (handover.getCount() != handover.getHandovers().size()) {
			return new ResponseBase(Contants.RESPONSE_CODE_FAIL_NOT_ENOUGH_HS,
					"Count" + Contants.MESSAGE_NOT_ENOUGH_RECIEPT);
		} else {
			for (DetailHandoverB dh : handover.getHandovers()) {
				ResponseBase rb = this.checkValidateDetailHandoverB(dh);
				if (rb != null) {
					rb.setMessage("Handovers - " + rb.getMessage());
					return rb;
				}
			}
		}
		return null;
	}

	// check validate DetailHandoverB
	private ResponseBase checkValidateDetailHandoverB(
			DetailHandoverB detailHandover) {
		if (StringUtils.isBlank(detailHandover.getPackageId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PackageId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getTransactionId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TransactionId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(detailHandover.getApproveStage())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveStage" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isBlank(detailHandover.getNoteOffer())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "NoteOffer"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getNoteApprove())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "NoteApprove"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getPersonCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PersonCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(detailHandover.getPersonStage())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "PersonStage" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isBlank(detailHandover.getOfferStage())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferStage"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		try {
			if (detailHandover.getPayments() != null
					|| detailHandover.getPayments().size() > 0) {
				for (PaymentDetail pDetail : detailHandover.getPayments()) {
					ResponseBase rb = this.checkValidatePaymentDetailB(pDetail);
					if (rb != null) {
						rb.setMessage("Payments - " + rb.getMessage());
						return rb;
					}
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	// check validate Payment DetailB
	private ResponseBase checkValidatePaymentDetailB(PaymentDetail pDetail) {
		if (StringUtils.isBlank(pDetail.getTransactionId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TransactionId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pDetail.getTypePayment())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TypePayment"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pDetail.getSubTypePayment())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SubTypePayment"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pDetail.getPaymentAmount().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PaymentAmount"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pDetail.getStatusFee())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "StatusFee"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!pDetail.getStatusFee().trim().equals("Y")
				&& !pDetail.getStatusFee().trim().equals("N")) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "StatusFee"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - format: Y or N");
		}
		if (StringUtils.isBlank(pDetail.getNamePayment())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "NamePayment"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		return null;
	}

	// check validate Transaction
	private ResponseBase checkValidateTransaction(Transaction transaction) {
		if (StringUtils.isBlank(transaction.getTransactionId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TransactionId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Nin"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getDateOfApplication())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"DateOfApplication" + Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(transaction.getDateOfApplication())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"DateOfApplication" + Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(transaction.getDateOfApplication())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"DateOfApplication" + Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - length != 14");
		} else if (!checkFormatDatetime(transaction.getDateOfApplication())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"DateOfApplication" + Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - format != yyyyMMddHHmmss");
		}
		if (StringUtils.isBlank(transaction.getEstDateOfCollection())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"EstDateOfCollection" + Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(transaction.getEstDateOfCollection())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"EstDateOfCollection" + Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(transaction.getEstDateOfCollection())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"EstDateOfCollection" + Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - length != 8");
		} else if (!checkFormatDate(transaction.getEstDateOfCollection())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"EstDateOfCollection" + Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - format != yyyyMMdd");
		}
		if (StringUtils.isBlank(transaction.getPassportType())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PassportType"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getPriority().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Priority"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getRegSiteCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "RegSiteCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getIssSiteCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "IssSiteCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getTransactionType())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"TransactionType" + Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getTransactionStatus())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"TransactionStatus" + Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isBlank(transaction.getChecksum())){
		// return new ResponseBase(Contants.CODE_INPUT_FAILD, "Checksum" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isBlank(transaction.getIsPostOffice())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "IsPostOffice"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!transaction.getIsPostOffice().trim().equals("Y")
				&& !transaction.getIsPostOffice().trim().equals("N")) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "IsPostOffice"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - format: Y or N");
		}
		if (StringUtils.isBlank(transaction.getRecieptNo())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "RecieptNo"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(transaction.getRegistrationNo())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "RegistrationNo"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (transaction.getRegisData() == null) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "RegisData"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			ResponseBase rb = checkValidateRegistrationData(transaction
					.getRegisData());
			if (rb != null) {
				return rb;
			}
		}
		if (transaction.getDocuments() == null
				|| transaction.getDocuments().size() == 0) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Documents"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			for (TransactionDocument tranDoc : transaction.getDocuments()) {
				ResponseBase rb = this
						.checkValidateTransactionDocument(tranDoc);
				if (rb != null) {
					rb.setMessage("Documents - " + rb.getMessage());
					return rb;
				}
			}
		}
		if (transaction.getFamilies() != null
				|| transaction.getFamilies().size() > 0) {
			for (PersonFamily pf : transaction.getFamilies()) {
				ResponseBase rb = this.checkValidatePersonFamily(pf);
				if (rb != null) {
					rb.setMessage("Families - " + rb.getMessage());
					return rb;
				}
			}
		}
		return null;
	}

	// check Validate PersonFamily
	private ResponseBase checkValidatePersonFamily(PersonFamily pf) {
		if (StringUtils.isBlank(pf.getName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Name"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pf.getGender())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Gender"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pf.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(pf.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(pf.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 8");
		} else if (!checkFormatDate(pf.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMdd");
		}
		if (StringUtils.isBlank(pf.getRelationship())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Relationship"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		return null;
	}

	// check Validate TransactionDocument
	private ResponseBase checkValidateTransactionDocument(
			TransactionDocument tDocument) {
		if (StringUtils.isBlank(tDocument.getDocType())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DocType"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tDocument.getSerialNo())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SerialNo"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(tDocument.getDocData())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DocData"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		return null;
	}

	// check Validate RegistrationData
	private ResponseBase checkValidateRegistrationData(
			RegistrationData regisData) {
		if (StringUtils.isBlank(regisData.getFirstName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "FirstName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getMidName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "MidName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getSurName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SurName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getFullName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "FullName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getGender())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Gender"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getPlaceOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PlaceOfBirth"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(regisData.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(regisData.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 8");
		} else if (!checkFormatDate(regisData.getDateOfBirth())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfBirth"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMdd");
		}
		if (StringUtils.isBlank(regisData.getResidentPlaceId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"ResidentPlaceId" + Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getResidentAreaId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ResidentAreaId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getResidentAddress())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"ResidentAddress" + Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getReligion())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Religion"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getNation())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Nation"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getAddressNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "AddressNin"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(regisData.getDateNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateNin"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(regisData.getDateNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateNin"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(regisData.getDateNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateNin"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 8");
		} else if (!checkFormatDate(regisData.getDateNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateNin"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMdd");
		}
		if (StringUtils.isBlank(regisData.getTotalFp().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TotalFp"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		/*
		 * if(StringUtils.isBlank(regisData.getPersonCode())){ return new
		 * ResponseBase(Contants.CODE_INPUT_FAILD, "PersonCode" +
		 * Contants.MESSAGE_INPUT_NULL); }
		 */
		return null;
	}

	// check Validate Handover
	private ResponseBase checkValidateHandover(HandoverA handover)
			throws Exception {
		if (StringUtils.isBlank(handover.getPackageId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PackageId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(handover.getOfferUserName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferUserName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(handover.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(handover.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(handover.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 14");
		} else if (!checkFormatDatetime(handover.getOfferDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMddHHmmss");
		}
		if (StringUtils.isBlank(handover.getApproveUserName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD,
					"ApproveUserName" + Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(handover.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(handover.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(handover.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 14");
		} else if (!checkFormatDatetime(handover.getApproveDate())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveDate"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMddHHmmss");
		}
		if (StringUtils.isBlank(handover.getSiteCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SiteCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(String.valueOf(handover.getCount()))) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Count"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (handover.getReceipts() == null
				|| handover.getReceipts().size() == 0) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Receipts"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (handover.getCount() != handover.getReceipts().size()) {
			return new ResponseBase(Contants.RESPONSE_CODE_FAIL_NOT_ENOUGH_HS,
					"Count" + Contants.MESSAGE_NOT_ENOUGH_RECIEPT);
		} else {
			for (ReceiptManager rm : handover.getReceipts()) {
				ResponseBase rb = this.checkValidateRecieptManagerA(rm);
				if (rb != null) {
					rb.setMessage("Receipts - " + rb.getMessage());
					return rb;
				}
			}
		}
		return null;
	}

	// check validate RecieptManager
	private ResponseBase checkValidateRecieptManagerA(ReceiptManager rm) {
		if (StringUtils.isBlank(rm.getReceiptNo())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "RecieptNo"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rm.getName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Name"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rm.getOfficeName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfficeName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rm.getDob())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Dob"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rm.getAddress())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Address"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rm.getNin())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Nin"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rm.getPaymentAmount().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PaymentAmount"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rm.getPaymentFlag())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PaymentFlag"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (rm.getHandovers() == null || rm.getHandovers().size() == 0) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Handovers"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			for (DetailHandover detailHandover : rm.getHandovers()) {
				ResponseBase rb = checkValidateHandoverDetailA(detailHandover);
				if (rb != null) {
					rb.setMessage("Handovers - " + rb.getMessage());
					return rb;
				}
			}
		}
		if (rm.getBills() != null || rm.getBills().size() > 0) {
			for (ReceiptBill rBill : rm.getBills()) {
				ResponseBase rb = this.checkValidateReceiptBillA(rBill);
				if (rb != null) {
					rb.setMessage("Bills - " + rb.getMessage());
					return rb;
				}
			}
		}
		if (rm.getFeeRecieptPayment() != null
				|| rm.getFeeRecieptPayment().size() > 0) {
			for (FeeRecieptPaymentModel fRPModel : rm.getFeeRecieptPayment()) {
				ResponseBase rb = this
						.checkValidateFeeRecieptPaymentModelA(fRPModel);
				if (rb != null) {
					rb.setMessage("FeeRecieptPayment - " + rb.getMessage());
					return rb;
				}
			}
		}
		return null;
	}

	// check Validate FeeRecieptPaymentModel
	private ResponseBase checkValidateFeeRecieptPaymentModelA(
			FeeRecieptPaymentModel fRPModel) {
		if (StringUtils.isBlank(fRPModel.getRecieptNo())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "RecieptNo"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(fRPModel.getTypePayment())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TypePayment"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(fRPModel.getPrice().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Price"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(fRPModel.getUnit())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Unit"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(fRPModel.getAmount().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Amount"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(fRPModel.getTotal().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Total"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(fRPModel.getCreateBy())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "CreateBy"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		return null;
	}

	// check Validate ReceiptBill
	private ResponseBase checkValidateReceiptBillA(ReceiptBill rBill) {
		if (StringUtils.isBlank(rBill.getReceiptNo())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TransactionId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rBill.getCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Code"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rBill.getNumber())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Number"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rBill.getPrice().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Price"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rBill.getBillFlag())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "BillFlag"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rBill.getDescription())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Description"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(rBill.getDateOfReceipt())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfReceipt"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(rBill.getDateOfReceipt())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfReceipt"
					+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDateTime(rBill.getDateOfReceipt())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfReceipt"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - length != 14");
		} else if (!checkFormatDatetime(rBill.getDateOfReceipt())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "DateOfReceipt"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR
					+ " - format != yyyyMMddHHmmss");
		}
		return null;
	}

	// check Validate HandoverDetail
	private ResponseBase checkValidateHandoverDetailA(
			DetailHandover detailHandover) {
		if (StringUtils.isBlank(detailHandover.getPackageId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PackageId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getTransactionId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TransactionId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getApproveStage())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "ApproveStage"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getNoteOffer())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "NoteOffer"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getNoteApprove())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "NoteApprove"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getPersonCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PersonCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getPersonStage())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PersonStage"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(detailHandover.getOfferStage())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "OfferStage"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (detailHandover.getPayments() == null
				|| detailHandover.getPayments().size() == 0) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Payments"
					+ Contants.MESSAGE_INPUT_NULL);
		} else {
			for (PaymentDetail pDetail : detailHandover.getPayments()) {
				ResponseBase rb = this.checkValidatePaymentDetailA(pDetail);
				if (rb != null) {
					rb.setMessage("Payments - " + rb.getMessage());
					return rb;
				}
			}
		}
		if (detailHandover.getOrgPerson() != null) {
			ResponseBase rb = this.checkValidatePersonModel(detailHandover
					.getOrgPerson());
			if (rb != null) {
				rb.setMessage("OrgPerson - " + rb.getMessage());
				return rb;
			}
		}
		return null;
	}

	// check Validate PersonModel
	private ResponseBase checkValidatePersonModel(PersonModel p) {
		if (StringUtils.isEmpty(p.getPersonCode())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PersonCode"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(p.getName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Name"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(p.getSearchName())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "SearchName"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(p.getGender())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "Gender"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		return null;
	}

	// check Validate PaymentDetail
	private ResponseBase checkValidatePaymentDetailA(PaymentDetail pDetail) {
		if (StringUtils.isBlank(pDetail.getTransactionId())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TransactionId"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pDetail.getTypePayment())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "TypePayment"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pDetail.getPaymentAmount().toString())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "PaymentAmount"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isBlank(pDetail.getStatusFee())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "StatusFee"
					+ Contants.MESSAGE_INPUT_NULL);
		} else if (!pDetail.getStatusFee().trim().equals("Y")
				&& !pDetail.getStatusFee().trim().equals("N")) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "StatusFee"
					+ Contants.MESSAGE_INPUT_FORMAT_ERROR + " - format: Y or N");
		}
		if (StringUtils.isBlank(pDetail.getNamePayment())) {
			return new ResponseBase(Contants.CODE_INPUT_FAILD, "NamePayment"
					+ Contants.MESSAGE_INPUT_NULL);
		}
		return null;
	}

	// check type int
	private boolean checkTypeLong(String input) {
		try {
			Long.parseLong(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// check length DATE yyyyMMdd
	private boolean checkLengthDate(String date) {
		if (date.length() == 8) {
			return true;
		} else {
			return false;
		}
	}

	// check length DATETIME yyyyMMddHHmmss
	private boolean checkLengthDateTime(String date) {
		if (date.length() == 14) {
			return true;
		} else {
			return false;
		}
	}

	// check format yyyyMMdd
	private SimpleDateFormat simpleDateFormat;

	private boolean checkFormatDate(String date) {
		simpleDateFormat = new SimpleDateFormat(
				HelperClass.STR_FORMAT_DATE_yyyyMMdd);
		try {
			Date d = simpleDateFormat.parse(date.trim());
			if (simpleDateFormat.format(d).equals(date.trim())) {
				return true;
			}
		} catch (java.text.ParseException e) {
			return false;
		}
		return false;
	}

	// check format yyyyMMddHHmmss
	private boolean checkFormatDatetime(String datetime) {
		simpleDateFormat = new SimpleDateFormat(
				HelperClass.STR_FORMAT_DATE_yyyyMMddHHmmss);
		try {
			Date d = simpleDateFormat.parse(datetime.trim());
			if (simpleDateFormat.format(d).equals(datetime.trim())) {
				return true;
			}
		} catch (java.text.ParseException e) {
			return false;
		}
		return false;
	}

	private BaseModelSingle<Boolean> addObjToQueueJob(String transactionId,
			String ObjType, String receiver, String status, String function) {
		try {
			// BaseModelSingle<SyncQueueJob> exist = queueJobService
			// .findQueueByObjectId(transactionId, receiver, "", ObjType);
			// if (!exist.isError() || exist.getMessage() != null) {
			// return new BaseModelSingle<Boolean>(false, exist.isError(),
			// exist.getMessage());
			// }
			// if (exist.getModel() == null) {
			SyncQueueJob queue = new SyncQueueJob();
			queue.setCreatedTs(Calendar.getInstance().getTime());
			queue.setObjectId(transactionId);
			queue.setObjectType(ObjType);
			queue.setReceiver(receiver);
			queue.setTranStatus(status);
			queue.setStatus(Contants.CODE_STATUS_QUEUE_PENDING);
			queue.setSyncRetry(1);
			BaseModelSingle<Boolean> baseSaveQueue = queueJobService
					.saveOrUpdateQueue(queue);
			if (!baseSaveQueue.isError() || baseSaveQueue.getMessage() != null) {
				throw new Exception(baseSaveQueue.getMessage());
			}
			boolean check = baseSaveQueue.getModel();
			if (StringUtils.isNotBlank(function) && check) {
				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(function, 1, receiver);
			}
			return new BaseModelSingle<Boolean>(check, true, null);
			// }
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException.createMessageException(e)
							+ " - addObjToQueueJob:" + transactionId
							+ " - thất bại.");
		}
		// return new BaseModelSingle<Boolean>(false, true, null);
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
									logger.error(e.getMessage());
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
									logger.error(e.getMessage());
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
					// logger.info("[convertFPDataList] error when getting FPData for transactionId:{}, FP-{}",
					// transactionId, doc.getSerialNo());
					if (logger.isDebugEnabled()) {
						logger.error("[convertFPDataList] Exception ", e);
					}
				}
			}
		}
		return fpDataList;
	}

	public NicTransaction convertNicTransaction(Transaction transactionDTO,
			Boolean isUpdate) throws JAXBException, ParametersServiceException {
		NicTransaction nicTransactionDBO = new NicTransaction();
		nicTransactionDBO.setTransactionId(transactionDTO.getTransactionId());
		nicTransactionDBO.setApplnRefNo(transactionDTO.getTransactionId());
		nicTransactionDBO.setNin(transactionDTO.getNin());
		// nicTransactionDBO.setDateOfApplication(transactionDTO.getDateOfApplication());
		if (StringUtils.isNotEmpty(transactionDTO.getDateOfApplication())) {
			Date date = HelperClass.convertStringToDate(transactionDTO
					.getDateOfApplication(), transactionDTO
					.getDateOfApplication().length());
			nicTransactionDBO.setDateOfApplication(date);
		}
		nicTransactionDBO.setNote(transactionDTO.getNote()); // chi tiết nội
																// dung đề nghị
																// nếu có
		nicTransactionDBO.setRegSiteCode(transactionDTO.getRegSiteCode());
		nicTransactionDBO.setIssSiteCode(transactionDTO.getIssSiteCode());
		/*
		 * 28-03-2020 Đóng
		 * nicTransactionDBO.setTransactionType(transactionDTO.getTransactionType
		 * ());
		 */

		nicTransactionDBO.setTransactionSubType(transactionDTO
				.getTransactionType());
		if (transactionDTO.getTransactionType().contains("RENEW"))
			nicTransactionDBO.setTransactionType("REP");
		else
			nicTransactionDBO.setTransactionType("REG");

		nicTransactionDBO
				.setTransactionStatus(NicTransaction.TRANSACTION_STATUS_RIC_UPLOADED);

		// additional fields
		// nicTransactionDBO.setEstDateOfCollection(transactionDTO.getEstDateOfCollection());
		if (StringUtils.isNotEmpty(transactionDTO.getEstDateOfCollection())) {
			Date date = HelperClass.convertStringToDate(transactionDTO
					.getEstDateOfCollection(), transactionDTO
					.getEstDateOfCollection().length());
			nicTransactionDBO.setEstDateOfCollection(date);
		}
		nicTransactionDBO.setPassportType(transactionDTO.getPassportType());
		Parameters pr = parametersService.getParamDetails(
				PARAMETERS_SCOPE_SYSTEM, PARAMETERS_NAME_ISSUING_AUTHORITY);
		// codesService.getCodeValueByIdName("ISSUING_AUTHORITY", codeName);
		try {
			nicTransactionDBO.setIssuingAuthority(parametersService.findById(
					new ParametersId("SYSTEM", "ISSUING_AUTHORITY"))
					.getParaShortValue()); // Tạm để mã 18A sửa lại
		} catch (Exception e) {
			// TODO: handle exception
		}
		nicTransactionDBO.setValidityPeriod(10); // Tạm thời để thời hạn hộ
													// chiếu là 10 năm
		nicTransactionDBO.setPriority(transactionDTO.getPriority());
		// nicTransactionDBO.setPrevDateOfIssue(transactionDTO.getPrevDateOfIssue());
		if (StringUtils.isNotEmpty(transactionDTO.getPrevDateOfIssue())) {
			Date date = HelperClass.convertStringToDate(transactionDTO
					.getPrevDateOfIssue(), transactionDTO.getPrevDateOfIssue()
					.length());
			nicTransactionDBO.setPrevDateOfIssue(date);
		}
		nicTransactionDBO.setPrevPassportNo(transactionDTO.getPrevPassportNo());

		// if (!isUpdate) {
		nicTransactionDBO.setCreateBy(transactionDTO.getRegisData()
				.getCreateBy());
		nicTransactionDBO.setCreateDatetime(new Date());
		nicTransactionDBO.setCreateWkstnId(null);
		// }
		nicTransactionDBO.setUpdateBy(null);
		nicTransactionDBO.setUpdateDatetime(null);
		nicTransactionDBO.setUpdateWkstnId(null);
		nicTransactionDBO.setRecieptNo(transactionDTO.getRecieptNo());
		nicTransactionDBO.setRegistrationNo(transactionDTO.getRegistrationNo());
		// nicTransactionDBO.setReceiver(transactionDTO.getPlaceIssuance());
		// Lấy thông tin nhân thân, con cái
		List<PersonFamily> dsFamily = transactionDTO.getFamilies();
		if (dsFamily != null && dsFamily.size() > 0) {
			InfoFamilys infoFamily = new InfoFamilys();
			List<PersonFamily> dsFamilySon = new ArrayList<PersonFamily>();
			for (PersonFamily pf : dsFamily) {
				if (pf.getRelationship().equals("CHILD")) {
					dsFamilySon.add(pf);
				}
			}
			if (dsFamilySon.size() > 0) {
				infoFamily.setSonFamilies(dsFamilySon);
				String infoPerson = this.convertObjToXml(infoFamily);
				nicTransactionDBO.setInfoPerson(infoPerson);
			}
		}

		// 28-03-2020
		nicTransactionDBO.setPrevDateOfExpr(transactionDTO.getPrevDateOfExpr());
		nicTransactionDBO.setAppointmentPlace(transactionDTO
				.getAppointmentPlace());
		// nicTransactionDBO.setTransactionSubType(transactionDTO.getTransactionSubType());
		nicTransactionDBO.setApplicant(transactionDTO.getApplicant());
		nicTransactionDBO.setRegistrationType(transactionDTO
				.getRegistrationType());
		nicTransactionDBO.setPaBlacklistId(transactionDTO.getPaBlacklistId());
		nicTransactionDBO.setPaInqBllUser(transactionDTO.getPaInqBllUser());
		nicTransactionDBO.setPaArchiveCode(transactionDTO.getPaArchiveCode());
		if (StringUtils.isNotEmpty(transactionDTO.getPaSearchBio()))
			nicTransactionDBO.setPaSearchBio(transactionDTO.getPaSearchBio()
					.equals("Y") ? true : false);

		NicRegistrationData nicRegDataDBO = this.parseRegistrationDataDBO(
				transactionDTO, null, transactionDTO.getRegisData(), isUpdate);
		nicTransactionDBO.setNicRegistrationData(nicRegDataDBO);
		// photo and document_scan
		List<NicTransactionAttachment> nicFacialDocDBOList = this
				.parseNicTransDocDBOForFacial(
						transactionDTO.getTransactionId(),
						transactionDTO.getDocuments(), nicTransactionDBO,
						isUpdate);
		nicTransactionDBO.getNicTransactionAttachments().addAll(
				nicFacialDocDBOList);
		// finger print wsq and tpl
		List<NicTransactionAttachment> nicFPDocDBOList = this
				.parseNicTransDocDBOForFingerprint(
						transactionDTO.getTransactionId(),
						transactionDTO.getDocuments(), nicTransactionDBO,
						isUpdate);

		nicTransactionDBO.getNicTransactionAttachments()
				.addAll(nicFPDocDBOList);
		for (NicTransactionAttachment nicTranAttach : nicTransactionDBO
				.getNicTransactionAttachments()) {
			nicTranAttach.setCreateBy(nicRegDataDBO.getCreateBy());
			nicTranAttach.setCreateByName(nicRegDataDBO.getCreateByName());
		}
		// payment
		NicTransactionPayment nicTransPaymentDBO = this
				.parseNicTransPaymentDBO(transactionDTO, isUpdate);
		nicTransactionDBO.setNicTransactionPayment(nicTransPaymentDBO);

		if (StringUtils.isNotEmpty(transactionDTO.getIsPostOffice()))
			nicTransactionDBO.setIsPostOffice(transactionDTO.getIsPostOffice()
					.equals("Y") ? true : false);

		if (StringUtils.isNotEmpty(transactionDTO.getPassportStyle()))
			nicTransactionDBO.setIsEpassport(transactionDTO.getPassportStyle()
					.equals("Y") ? true : false);

		nicTransactionDBO.setDescription(transactionDTO.getDescription());
		if (transactionDTO.getPaJoinPersonDate() != null) {
			Date date = HelperClass.convertStringToDate(transactionDTO
					.getPaJoinPersonDate(), transactionDTO
					.getPaJoinPersonDate().length());
			nicTransactionDBO.setPaJoinPersonDate(date);
		}
		if (transactionDTO.getPaSaveDocDate() != null) {
			Date date = HelperClass.convertStringToDate(transactionDTO
					.getPaSaveDocDate(), transactionDTO.getPaSaveDocDate()
					.length());
			nicTransactionDBO.setPaSaveDocDate(date);
		}
		if (transactionDTO.getPaSearchObjDate() != null) {
			Date date = HelperClass.convertStringToDate(transactionDTO
					.getPaSearchObjDate(), transactionDTO.getPaSearchObjDate()
					.length());
			nicTransactionDBO.setPaSearchObjDate(date);
		}
		return nicTransactionDBO;
	}

	public NicTransaction convertUpdateNicTransactionDetail(
			UpdateTransactionDetail transactionDTO, Boolean isUpdate,
			NicTransaction nicTransactionDBO) throws JAXBException,
			ParametersServiceException {
		if (nicTransactionDBO != null) {
			nicTransactionDBO.setTransactionId(transactionDTO
					.getTransactionId());
			nicTransactionDBO.setApplnRefNo(transactionDTO.getTransactionId());
			nicTransactionDBO.setNin(transactionDTO.getNin());
			// nicTransactionDBO.setDateOfApplication(transactionDTO.getDateOfApplication());
			if (StringUtils.isNotEmpty(transactionDTO.getDateOfApplication())) {
				Date date = HelperClass.convertStringToDate(transactionDTO
						.getDateOfApplication(), transactionDTO
						.getDateOfApplication().length());
				nicTransactionDBO.setDateOfApplication(date);
			}
			nicTransactionDBO.setNote(transactionDTO.getNote()); // chi tiết nội
																	// dung đề
																	// nghị nếu
																	// có
			nicTransactionDBO.setRegSiteCode(transactionDTO.getRegSiteCode());
			nicTransactionDBO.setIssSiteCode(transactionDTO.getIssSiteCode());
			/*
			 * 28-03-2020 Đóng
			 * nicTransactionDBO.setTransactionType(transactionDTO
			 * .getTransactionType ());
			 */

			nicTransactionDBO.setTransactionSubType(transactionDTO
					.getTransactionType());
			if (transactionDTO.getTransactionType().contains("RENEW"))
				nicTransactionDBO.setTransactionType("REP");
			else
				nicTransactionDBO.setTransactionType("REG");

			/* đóng cập nhật trạng thái khi update Hồ sơ */
			// nicTransactionDBO.setTransactionStatus(transactionDTO
			// .getTransactionStatus());

			// additional fields
			// nicTransactionDBO.setEstDateOfCollection(transactionDTO.getEstDateOfCollection());
			if (StringUtils.isNotEmpty(transactionDTO.getEstDateOfCollection())) {
				Date date = HelperClass.convertStringToDate(transactionDTO
						.getEstDateOfCollection(), transactionDTO
						.getEstDateOfCollection().length());
				nicTransactionDBO.setEstDateOfCollection(date);
			}
			nicTransactionDBO.setPassportType(transactionDTO.getPassportType());
			Parameters pr = parametersService.getParamDetails(
					PARAMETERS_SCOPE_SYSTEM, PARAMETERS_NAME_ISSUING_AUTHORITY);
			// codesService.getCodeValueByIdName("ISSUING_AUTHORITY", codeName);
			try {
				nicTransactionDBO
						.setIssuingAuthority(parametersService
								.findById(
										new ParametersId("SYSTEM",
												"ISSUING_AUTHORITY"))
								.getParaShortValue()); // Tạm để mã 18A sửa lại
			} catch (Exception e) {
				// TODO: handle exception
			}
			nicTransactionDBO.setValidityPeriod(10); // Tạm thời để thời hạn hộ
														// chiếu là 10 năm
			nicTransactionDBO.setPriority(transactionDTO.getPriority());
			// nicTransactionDBO.setPrevDateOfIssue(transactionDTO.getPrevDateOfIssue());
			if (StringUtils.isNotEmpty(transactionDTO.getPrevDateOfIssue())) {
				Date date = HelperClass.convertStringToDate(transactionDTO
						.getPrevDateOfIssue(), transactionDTO
						.getPrevDateOfIssue().length());
				nicTransactionDBO.setPrevDateOfIssue(date);
			}
			nicTransactionDBO.setPrevPassportNo(transactionDTO
					.getPrevPassportNo());

			// if (!isUpdate) {
			// nicTransactionDBO.setCreateBy(Contants.CREATE_BY_SYSTEM);
			// nicTransactionDBO.setCreateDatetime(Calendar.getInstance()
			// .getTime());
			// nicTransactionDBO.setCreateWkstnId(null);
			// }
			nicTransactionDBO.setUpdateBy(transactionDTO.getRegisData()
					.getCreateBy());
			nicTransactionDBO.setUpdateDatetime(new Date());
			nicTransactionDBO.setUpdateWkstnId(null);
			nicTransactionDBO.setRecieptNo(transactionDTO.getRecieptNo());
			nicTransactionDBO.setRegistrationNo(transactionDTO
					.getRegistrationNo());
			// nicTransactionDBO.setReceiver(transactionDTO.getPlaceIssuance());
			// Lấy thông tin nhân thân, con cái
			List<PersonFamily> dsFamily = transactionDTO.getFamilies();
			if (dsFamily != null && dsFamily.size() > 0) {
				InfoFamilys infoFamily = new InfoFamilys();
				List<PersonFamily> dsFamilySon = new ArrayList<PersonFamily>();
				for (PersonFamily pf : dsFamily) {
					if (pf.getRelationship().equals("CHILD")) {
						dsFamilySon.add(pf);
					}
				}
				if (dsFamilySon.size() > 0) {
					infoFamily.setSonFamilies(dsFamilySon);
					String infoPerson = this.convertObjToXml(infoFamily);
					nicTransactionDBO.setInfoPerson(infoPerson);
				}
			}

			// 28-03-2020
			nicTransactionDBO.setPrevDateOfExpr(transactionDTO
					.getPrevDateOfExpr());
			nicTransactionDBO.setAppointmentPlace(transactionDTO
					.getAppointmentPlace());
			// nicTransactionDBO.setTransactionSubType(transactionDTO.getTransactionSubType());
			nicTransactionDBO.setApplicant(transactionDTO.getApplicant());
			nicTransactionDBO.setRegistrationType(transactionDTO
					.getRegistrationType());
			nicTransactionDBO.setPaBlacklistId(transactionDTO
					.getPaBlacklistId());
			nicTransactionDBO.setPaInqBllUser(transactionDTO.getPaInqBllUser());
			nicTransactionDBO.setPaArchiveCode(transactionDTO
					.getPaArchiveCode());
			if (StringUtils.isNotEmpty(transactionDTO.getPaSearchBio()))
				nicTransactionDBO.setPaSearchBio(transactionDTO
						.getPaSearchBio().equals("Y") ? true : false);

			NicRegistrationData nicRegDataDBO = this
					.parseUpdateRegistrationDataDBO(transactionDTO, null,
							transactionDTO.getRegisData(), isUpdate,
							nicTransactionDBO.getNicRegistrationData());
			nicTransactionDBO.setNicRegistrationData(nicRegDataDBO);
			// photo and document_scan
			List<NicTransactionAttachment> nicFacialDocDBOList = this
					.parseNicTransDocDBOForFacial(
							transactionDTO.getTransactionId(),
							transactionDTO.getDocuments(), nicTransactionDBO,
							isUpdate);
			nicTransactionDBO.getNicTransactionAttachments().addAll(
					nicFacialDocDBOList);
			// finger print wsq and tpl
			List<NicTransactionAttachment> nicFPDocDBOList = this
					.parseNicTransDocDBOForFingerprint(
							transactionDTO.getTransactionId(),
							transactionDTO.getDocuments(), nicTransactionDBO,
							isUpdate);
			nicTransactionDBO.getNicTransactionAttachments().addAll(
					nicFPDocDBOList);

			// payment
			NicTransactionPayment nicTransPaymentDBO = this
					.parseUpdateNicTransPaymentDBO(transactionDTO, isUpdate);
			nicTransactionDBO.setNicTransactionPayment(nicTransPaymentDBO);

			if (StringUtils.isNotEmpty(transactionDTO.getIsPostOffice()))
				nicTransactionDBO.setIsPostOffice(transactionDTO
						.getIsPostOffice().equals("Y") ? true : false);

			if (StringUtils.isNotEmpty(transactionDTO.getPassportStyle()))
				nicTransactionDBO.setIsEpassport(transactionDTO
						.getPassportStyle().equals("Y") ? true : false);

			nicTransactionDBO.setDescription(transactionDTO.getDescription());
			if (transactionDTO.getPaJoinPersonDate() != null) {
				Date date = HelperClass.convertStringToDate(transactionDTO
						.getPaJoinPersonDate(), transactionDTO
						.getPaJoinPersonDate().length());
				nicTransactionDBO.setPaJoinPersonDate(date);
			}
			if (transactionDTO.getPaSaveDocDate() != null) {
				Date date = HelperClass.convertStringToDate(transactionDTO
						.getPaSaveDocDate(), transactionDTO.getPaSaveDocDate()
						.length());
				nicTransactionDBO.setPaSaveDocDate(date);
			}
			if (transactionDTO.getPaSearchObjDate() != null) {
				Date date = HelperClass.convertStringToDate(transactionDTO
						.getPaSearchObjDate(), transactionDTO
						.getPaSearchObjDate().length());
				nicTransactionDBO.setPaSearchObjDate(date);
			}
		}
		return nicTransactionDBO;
	}

	public NicTransactionPayment parseNicTransPaymentDBO(
			Transaction Transaction, Boolean isUpdate) {
		NicTransactionPayment nicTransPaymentDBO = null;
		if (Transaction != null) {
			nicTransPaymentDBO = new NicTransactionPayment();
			nicTransPaymentDBO.setTransactionId(Transaction.getTransactionId());
			nicTransPaymentDBO.setNicTransaction(new NicTransaction(Transaction
					.getTransactionId()));
			nicTransPaymentDBO.setPaymentId("P-"
					+ Transaction.getTransactionId());
			nicTransPaymentDBO.setBalance(0.0);
			nicTransPaymentDBO.setCashReceived(0.0);
			nicTransPaymentDBO.setFeeAmount(0.0);
			nicTransPaymentDBO.setNoOfTimeLost(0);
			nicTransPaymentDBO.setPaymentAmount(0.0);
			nicTransPaymentDBO
					.setPaymentStatus(Contants.STATUS_PAYMENT_MAIN_PAID);
			nicTransPaymentDBO.setPaymentDatetime(null);
			nicTransPaymentDBO.setReceiptId(null);
			nicTransPaymentDBO.setReduceRateAmount(0.0);
			nicTransPaymentDBO.setReduceRateFlag(false);

			nicTransPaymentDBO.setCollectionOfficerId(null);

			nicTransPaymentDBO.setCreateBy(Transaction.getRegisData()
					.getCreateBy());
			nicTransPaymentDBO.setCreateDatetime(Calendar.getInstance()
					.getTime());
			nicTransPaymentDBO.setCreateWkstnId(null);
		}
		return nicTransPaymentDBO;
	}

	public NicTransactionPayment parseUpdateNicTransPaymentDBO(
			UpdateTransactionDetail Transaction, Boolean isUpdate) {
		NicTransactionPayment nicTransPaymentDBO = null;
		if (Transaction != null) {
			nicTransPaymentDBO = new NicTransactionPayment();
			nicTransPaymentDBO.setTransactionId(Transaction.getTransactionId());
			nicTransPaymentDBO.setNicTransaction(new NicTransaction(Transaction
					.getTransactionId()));
			nicTransPaymentDBO.setPaymentId("P-"
					+ Transaction.getTransactionId());
			nicTransPaymentDBO.setBalance(0.0);
			nicTransPaymentDBO.setCashReceived(0.0);
			nicTransPaymentDBO.setFeeAmount(0.0);
			nicTransPaymentDBO.setNoOfTimeLost(0);
			nicTransPaymentDBO.setPaymentAmount(0.0);
			nicTransPaymentDBO
					.setPaymentStatus(Contants.STATUS_PAYMENT_MAIN_PAID);
			nicTransPaymentDBO.setPaymentDatetime(null);
			nicTransPaymentDBO.setReceiptId(null);
			nicTransPaymentDBO.setReduceRateAmount(0.0);
			nicTransPaymentDBO.setReduceRateFlag(false);

			nicTransPaymentDBO.setCollectionOfficerId(null);

			// if (!isUpdate) {
			// nicTransPaymentDBO.setCreateBy(Transaction.getRegisData().getCreateBy());
			// nicTransPaymentDBO.setCreateDatetime(Calendar.getInstance()
			// .getTime());
			// nicTransPaymentDBO.setCreateWkstnId(null);
			// }
		}
		return nicTransPaymentDBO;
	}

	// Finger prints DTO to DBO
	public List<NicTransactionAttachment> parseNicTransDocDBOForFingerprint(
			String transactionId, List<TransactionDocument> fps,
			NicTransaction nicTransaction, Boolean isUpdate) {
		List<NicTransactionAttachment> fpDocDBOList = new ArrayList<NicTransactionAttachment>();
		if (fps != null && fps.size() > 0) {
			// Boolean checkFp = false;
			for (TransactionDocument fp : fps) {
				// Fingerprint FP
				if (fp.getDocType().equals(Contants.DOC_TYPE_FINGERPRINT_WSQ)
						&& StringUtils.isNotEmpty(fp.getDocData())) {
					NicTransactionAttachment fpDocDBO = new NicTransactionAttachment();
					fpDocDBO.setTransactionId(transactionId);
					fpDocDBO.setDocType(Contants.DOC_TYPE_FINGERPRINT);
					fpDocDBO.setSerialNo(fp.getSerialNo().length() == 1 ? ("0" + fp
							.getSerialNo()) : fp.getSerialNo());
					fpDocDBO.setDocData(StringUtils.isNotEmpty(fp.getDocData()) ? Base64
							.decodeBase64(fp.getDocData()) : null);
					if (!isUpdate) {
						fpDocDBO.setCreateBy(nicTransaction.getCreateBy());
						fpDocDBO.setCreateDatetime(nicTransaction
								.getCreateDatetime());
						fpDocDBO.setCreateWkstnId(nicTransaction
								.getCreateWkstnId());
					}
					fpDocDBOList.add(fpDocDBO);
					// checkFp = true;
					// Lấy file MNU
					/*
					 * Phúc đóng lấy file mnu theo cách cũ
					 * if(StringUtils.isNotEmpty(fp.getMinutiaData()) &&
					 * Contants
					 * .DOC_TYPE_MINUTIAETEMPLATE.equals(fp.getMinutiaFormat
					 * ())){ NicTransactionAttachment mnuDocDBO = new
					 * NicTransactionAttachment();
					 * mnuDocDBO.setTransactionId(transactionId);
					 * mnuDocDBO.setDocType(fp.getMinutiaFormat() + "_CBEFF");
					 * mnuDocDBO.setSerialNo(fp.getSerialNo());
					 * mnuDocDBO.setDocData
					 * (Base64.decodeBase64(fp.getMinutiaData()));
					 * mnuDocDBO.setCreateBy(nicTransaction.getCreateBy());
					 * mnuDocDBO
					 * .setCreateDatetime(nicTransaction.getCreateDatetime());
					 * mnuDocDBO
					 * .setCreateWkstnId(nicTransaction.getCreateWkstnId());
					 * fpDocDBOList.add(mnuDocDBO); }
					 */
				}
				// Fingerprint WSQ//Tạm đóng 9/12/2019 nhận document mnu theo
				// cách khác
				/* Mở lại 24/12/2019 Phúc */
				if (fp.getDocType()
						.equals(Contants.DOC_TYPE_FINGERPRINT_FP_MNU)
						&& StringUtils.isNotEmpty(fp.getDocData())) {
					NicTransactionAttachment mnuDocDBO = new NicTransactionAttachment();
					mnuDocDBO.setTransactionId(transactionId);
					mnuDocDBO.setDocType(Contants.DOC_TYPE_MINUTIAETEMPLATE
							+ "_CBEFF");
					mnuDocDBO
							.setSerialNo(fp.getSerialNo().length() == 1 ? ("0" + fp
									.getSerialNo()) : fp.getSerialNo());
					mnuDocDBO.setDocData(Base64.decodeBase64(fp.getDocData()));
					if (!isUpdate) {
						mnuDocDBO.setCreateBy(nicTransaction.getCreateBy());
						mnuDocDBO.setCreateDatetime(nicTransaction
								.getCreateDatetime());
						mnuDocDBO.setCreateWkstnId(nicTransaction
								.getCreateWkstnId());
					}
					fpDocDBOList.add(mnuDocDBO);
				}
				// Fingerprint TPL
				/*
				 * if(fp.getDocType().equals(Contants.DOC_TYPE_TPL) &&
				 * StringUtils.isNotEmpty(fp.getDocData())){
				 * NicTransactionAttachment fpTplDBO = new
				 * NicTransactionAttachment();
				 * fpTplDBO.setTransactionId(transactionId);
				 * fpTplDBO.setDocType(Contants.DOC_TYPE_TPL);
				 * fpTplDBO.setSerialNo(Contants.DEFAULT_SERIAL_NO);
				 * fpTplDBO.setDocData(Base64.decodeBase64(fp.getDocData()));
				 * fpTplDBO.setCreateBy(nicTransaction.getCreateBy());
				 * fpTplDBO.setCreateDatetime
				 * (nicTransaction.getCreateDatetime());
				 * fpTplDBO.setCreateWkstnId(nicTransaction.getCreateWkstnId());
				 * fpDocDBOList.add(fpTplDBO); }
				 */
			}
			// if(checkFp){
			NicTransactionAttachment attFms = this.saveFmsTemplate(fps,
					nicTransaction);
			fpDocDBOList.add(attFms);
			// }
		}
		return fpDocDBOList;
	}

	public NicTransactionAttachment saveFmsTemplate(
			List<TransactionDocument> docList, NicTransaction nicTransaction) {

		try {
			byte[] _wsqimage1 = null, _wsqimage2 = null, _wsqimage3 = null, _wsqimage4 = null, _wsqimage5 = null, _wsqimage6 = null, _wsqimage7 = null, _wsqimage8 = null, _wsqimage9 = null, _wsqimage10 = null;

			List<TransactionDocument> fpDocList = new ArrayList<TransactionDocument>();
			for (TransactionDocument doc : docList) {
				if (doc.getDocType().equals(Contants.DOC_TYPE_FINGERPRINT_WSQ)) {
					fpDocList.add(doc);
				}
			}
			// String user=null;

			for (TransactionDocument doc : fpDocList) {
				// user=Contants.CREATE_BY_SYSTEM;
				switch (Integer.parseInt(doc.getSerialNo())) {

				case 1:
					_wsqimage1 = Base64.decodeBase64(doc.getDocData());
					break;
				case 2:
					_wsqimage2 = Base64.decodeBase64(doc.getDocData());
					break;
				case 3:
					_wsqimage3 = Base64.decodeBase64(doc.getDocData());
					break;
				case 4:
					_wsqimage4 = Base64.decodeBase64(doc.getDocData());
					break;
				case 5:
					_wsqimage5 = Base64.decodeBase64(doc.getDocData());
					break;
				case 6:
					_wsqimage6 = Base64.decodeBase64(doc.getDocData());
					break;
				case 7:
					_wsqimage7 = Base64.decodeBase64(doc.getDocData());
					break;
				case 8:
					_wsqimage8 = Base64.decodeBase64(doc.getDocData());
					break;
				case 9:
					_wsqimage9 = Base64.decodeBase64(doc.getDocData());
					break;
				case 10:
					_wsqimage10 = Base64.decodeBase64(doc.getDocData());
					break;
				}
			}

			SPID_FMS_Tool fms_tool = new SPID_FMS_Tool();
			String templateStr = fms_tool.CreateFMSTemplate(_wsqimage1,
					_wsqimage2, _wsqimage3, _wsqimage4, _wsqimage5, _wsqimage6,
					_wsqimage7, _wsqimage8, _wsqimage9, _wsqimage10, 9, '0', 0);
			if (templateStr != null) {
				byte templateBytes[] = new byte[templateStr.length()];
				templateBytes = templateStr.getBytes();

				NicTransactionAttachment fmsTemplate = new NicTransactionAttachment();
				fmsTemplate.setTransactionId(nicTransaction.getTransactionId());
				fmsTemplate.setDocType(Contants.DOC_TYPE_TPL);
				fmsTemplate.setSerialNo(Contants.DEFAULT_SERIAL_NO);
				fmsTemplate.setDocData(templateBytes);
				fmsTemplate.setCreateBy(nicTransaction.getCreateBy());
				fmsTemplate.setCreateDatetime(nicTransaction
						.getCreateDatetime());
				fmsTemplate.setCreateWkstnId(nicTransaction.getCreateWkstnId());
				return fmsTemplate;

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error null fms_template fp document");
		}
		return null;
	}

	public List<NicTransactionAttachment> parseNicTransDocDBOForFacial(
			String transactionId, List<TransactionDocument> facialInfo,
			NicTransaction nicTransaction, Boolean isUpdate) {
		List<NicTransactionAttachment> facialDocDBOList = new ArrayList<NicTransactionAttachment>();
		if (facialInfo != null && facialInfo.size() > 0) {
			byte[] photo = null;
			for (TransactionDocument facialImage : facialInfo) {
				if (facialImage.getDocType().equals(
						Contants.DOC_TYPE_PHOTO_CAPTURE)) {
					NicTransactionAttachment facialDocDBO = new NicTransactionAttachment();
					facialDocDBO.setTransactionId(transactionId);
					facialDocDBO.setDocType(facialImage.getDocType());
					facialDocDBO.setSerialNo(Contants.DEFAULT_SERIAL_NO);
					facialDocDBO.setDocData(StringUtils.isNotEmpty(facialImage
							.getDocData()) ? Base64.decodeBase64(facialImage
							.getDocData()) : null);
					facialDocDBO.setStorageRefId(facialImage.getFileName());

					facialDocDBO.setCreateBy(facialImage.getCreatedBy());
					facialDocDBO
							.setCreateByName(facialDocDBO.getCreateByName());
					if (StringUtils.isNotEmpty(facialImage.getCreatedDate())) {
						Date date = HelperClass.convertStringToDate(facialImage
								.getCreatedDate(), facialImage.getCreatedDate()
								.length());
						facialDocDBO.setCreateDatetime(date);
					}
					facialDocDBO.setCreateWkstnId(nicTransaction
							.getCreateWkstnId());

					facialDocDBO.setUpdateBy(facialDocDBO.getUpdateBy());
					facialDocDBO
							.setUpdateByName(facialDocDBO.getUpdateByName());
					if (StringUtils.isNotEmpty(facialImage.getUpdatedDate())) {
						Date date = HelperClass.convertStringToDate(facialImage
								.getUpdatedDate(), facialImage.getUpdatedDate()
								.length());
						facialDocDBO.setUpdateDatetime(date);
					}
					facialDocDBOList.add(facialDocDBO);

					photo = facialDocDBO.getDocData();

					// Thêm ảnh chip
					NicTransactionAttachment facialChipDBO = new NicTransactionAttachment();
					facialChipDBO.setTransactionId(transactionId);
					facialChipDBO.setDocType(Contants.DOC_TYPE_PHOTO_CHIP);
					facialChipDBO.setSerialNo(Contants.DEFAULT_SERIAL_NO);
					facialChipDBO.setDocData(StringUtils.isNotEmpty(facialImage
							.getDocData()) ? Base64.decodeBase64(facialImage
							.getDocData()) : null);
					facialDocDBO.setStorageRefId(facialImage.getFileName());

					facialDocDBO.setCreateBy(facialImage.getCreatedBy());
					facialDocDBO
							.setCreateByName(facialDocDBO.getCreateByName());
					if (StringUtils.isNotEmpty(facialImage.getCreatedDate())) {
						Date date = HelperClass.convertStringToDate(facialImage
								.getCreatedDate(), facialImage.getCreatedDate()
								.length());
						facialDocDBO.setCreateDatetime(date);
					}
					facialDocDBO.setCreateWkstnId(nicTransaction
							.getCreateWkstnId());
					facialDocDBO.setUpdateBy(facialDocDBO.getUpdateBy());
					facialDocDBO
							.setUpdateByName(facialDocDBO.getUpdateByName());
					if (StringUtils.isNotEmpty(facialImage.getUpdatedDate())) {
						Date date = HelperClass.convertStringToDate(facialImage
								.getUpdatedDate(), facialImage.getUpdatedDate()
								.length());
						facialDocDBO.setUpdateDatetime(date);
					}
					facialDocDBOList.add(facialChipDBO);
				}
				if (facialImage.getDocType().equals(
						Contants.DOC_TYPE_SCAN_DOCUMENT)) {
					NicTransactionAttachment facialDocDBO = new NicTransactionAttachment();
					facialDocDBO.setTransactionId(transactionId);
					facialDocDBO.setDocType(facialImage.getDocType());
					facialDocDBO.setSerialNo(facialImage.getSerialNo());
					facialDocDBO.setDocData(StringUtils.isNotEmpty(facialImage
							.getDocData()) ? Base64.decodeBase64(facialImage
							.getDocData()) : null);
					facialDocDBO.setStorageRefId(facialImage.getFileName());

					facialDocDBO.setCreateBy(facialImage.getCreatedBy());
					facialDocDBO
							.setCreateByName(facialDocDBO.getCreateByName());
					if (StringUtils.isNotEmpty(facialImage.getCreatedDate())) {
						Date date = HelperClass.convertStringToDate(facialImage
								.getCreatedDate(), facialImage.getCreatedDate()
								.length());
						facialDocDBO.setCreateDatetime(date);
					}
					facialDocDBO.setCreateWkstnId(nicTransaction
							.getCreateWkstnId());

					facialDocDBO.setUpdateBy(facialDocDBO.getUpdateBy());
					facialDocDBO
							.setUpdateByName(facialDocDBO.getUpdateByName());
					if (StringUtils.isNotEmpty(facialImage.getUpdatedDate())) {
						Date date = HelperClass.convertStringToDate(facialImage
								.getUpdatedDate(), facialImage.getUpdatedDate()
								.length());
						facialDocDBO.setUpdateDatetime(date);
					}
					facialDocDBOList.add(facialDocDBO);
				}
				// anh tu them
				if (facialImage.getDocType().equals(
						Contants.DOC_TYPE_SCAN_OTHER)) {
					NicTransactionAttachment facialDocDBO = new NicTransactionAttachment();
					facialDocDBO.setTransactionId(transactionId);
					facialDocDBO.setDocType(facialImage.getDocType());
					facialDocDBO.setSerialNo(facialImage.getSerialNo());
					facialDocDBO.setDocData(StringUtils.isNotEmpty(facialImage
							.getDocData()) ? Base64.decodeBase64(facialImage
							.getDocData()) : null);
					facialDocDBO.setStorageRefId(facialImage.getFileName());

					facialDocDBO.setCreateBy(facialImage.getCreatedBy());
					facialDocDBO
							.setCreateByName(facialDocDBO.getCreateByName());
					if (StringUtils.isNotEmpty(facialImage.getCreatedDate())) {
						Date date = HelperClass.convertStringToDate(facialImage
								.getCreatedDate(), facialImage.getCreatedDate()
								.length());
						facialDocDBO.setCreateDatetime(date);
					}
					facialDocDBO.setCreateWkstnId(nicTransaction
							.getCreateWkstnId());

					facialDocDBO.setUpdateBy(facialDocDBO.getUpdateBy());
					facialDocDBO
							.setUpdateByName(facialDocDBO.getUpdateByName());
					if (StringUtils.isNotEmpty(facialImage.getUpdatedDate())) {
						Date date = HelperClass.convertStringToDate(facialImage
								.getUpdatedDate(), facialImage.getUpdatedDate()
								.length());
						facialDocDBO.setUpdateDatetime(date);
					}
					facialDocDBOList.add(facialDocDBO);
				}
			}
			if (photo != null) {
				byte[] thumbnail = null;
				try {
					thumbnail = ImageUtil.resizeImageByWidthHeight(photo,
							Contants.THUMBNAIL_FACE_WIDTH_DEFAULT,
							Contants.THUMBNAIL_FACE_HEIGHT_DEFAULT,
							Contants.THUMBNAIL_FACE_QUALITY_DEFAULT);
				} catch (IOException e) {

				}
				if (thumbnail != null) {
					NicTransactionAttachment facialDocDBO = new NicTransactionAttachment();
					facialDocDBO.setTransactionId(transactionId);
					facialDocDBO
							.setDocType(Contants.DOC_TYPE_THUMBNAIL_CAPTURE);
					facialDocDBO.setSerialNo(Contants.DEFAULT_SERIAL_NO);
					facialDocDBO.setDocData(thumbnail);

					facialDocDBO.setCreateBy(nicTransaction.getCreateBy());
					facialDocDBO.setCreateDatetime(nicTransaction
							.getCreateDatetime());
					facialDocDBO.setCreateWkstnId(nicTransaction
							.getCreateWkstnId());

					facialDocDBOList.add(facialDocDBO);
				}
			}
		}
		return facialDocDBOList;
	}

	// Registration Data DTO to Data DBO
	public NicRegistrationData parseRegistrationDataDBO(
			Transaction transaction, String nin, RegistrationData regDataDTO,
			Boolean isUpdate) {
		NicRegistrationData nicRegDataDBO = null;
		String transactionId = transaction.getTransactionId();
		if (regDataDTO != null) {
			nicRegDataDBO = new NicRegistrationData();
			nicRegDataDBO.setTransactionId(transactionId);
			// if (!isUpdate) {
//			nicRegDataDBO.setCreateBy(regDataDTO.getCreateBy());
//			nicRegDataDBO.setCreateByName(regDataDTO.getCreateByName());
//			nicRegDataDBO.setCreateWkstnId(null);
			nicRegDataDBO.setCreateDatetime(new Date());
			// }
			// nicRegDataDBO.setUpdateBy(Contants.CREATE_BY_SYSTEM);
			// nicRegDataDBO.setUpdateWkstnId(null);
			// nicRegDataDBO.setUpdateDatetime(Calendar.getInstance().getTime());

			if (StringUtils.isNotBlank(regDataDTO.getSurName()))
				nicRegDataDBO.setSurnameFull(regDataDTO.getSurName());
			else
				nicRegDataDBO.setSurnameFull(StringUtils.EMPTY);
			/* nicRegDataDBO.setSurnameLine1(regDataDTO.getFullName()); */

			nicRegDataDBO.setSurnameLenExceedFlag(false);// Mặc định là false:
															// cần xem lại

			if (StringUtils.isNotBlank(regDataDTO.getFirstName()))
				nicRegDataDBO.setFirstnameFull(regDataDTO.getFirstName());
			else
				nicRegDataDBO.setFirstnameFull(StringUtils.EMPTY);
			nicRegDataDBO.setFirstnameLenExceedFlag(false);

			if (StringUtils.isNotBlank(regDataDTO.getMidName()))
				nicRegDataDBO.setMiddlenameFull(regDataDTO.getMidName());
			else
				nicRegDataDBO.setMiddlenameFull(StringUtils.EMPTY);
			nicRegDataDBO.setMiddlenameLenExceedFlag(false);

			if (StringUtils.isNotEmpty(regDataDTO.getDateOfBirth())) {
				nicRegDataDBO.setDateOfBirth(HelperClass.convertStringToDateTk(
						regDataDTO.getDateOfBirth(), 0));
				switch (regDataDTO.getDateOfBirth().length()) {
				case 4:
					nicRegDataDBO.setDefDateOfBirth("Y");
					break;
				case 6:
					nicRegDataDBO.setDefDateOfBirth("M");
					break;
				case 8:
					nicRegDataDBO.setDefDateOfBirth("D");
					break;

				default:
					break;
				}
			}
			nicRegDataDBO.setApproxDob(null);
			// to set PrintDob if it is null
			if (StringUtils.isBlank(nicRegDataDBO.getPrintDob())) {
				if (nicRegDataDBO.getDateOfBirth() != null) {
					nicRegDataDBO.setPrintDob(DateUtil.parseDate(
							nicRegDataDBO.getDateOfBirth(),
							DateUtil.FORMAT_YYYYMMDD));
				}
			}
			/*
			 * switch (regDataDTO.getGender()) { case "MALE":
			 * nicRegDataDBO.setGender("M"); break; case "FEMALE":
			 * nicRegDataDBO.setGender("F"); break; default:
			 * nicRegDataDBO.setGender("N"); break; }
			 */

			nicRegDataDBO.setGender(regDataDTO.getGender());

			// EPP additional fields
			nicRegDataDBO.setNationality(regDataDTO.getNationality());
			nicRegDataDBO.setPlaceOfBirth(regDataDTO.getPlaceOfBirth());

			// get Address
			nicRegDataDBO.setAddressLine1(regDataDTO.getResidentAddress());
			// nicRegDataDBO.setAddressLine3(regDataDTO.getResidentAddress());
			nicRegDataDBO.setAddressLine3(regDataDTO.getResidentAreaId());
			nicRegDataDBO.setAddressLine4(regDataDTO.getResidentPlaceId());

			// get Relationship
			List<PersonFamily> dsFamily = transaction.getFamilies();
			if (dsFamily != null && dsFamily.size() > 0) {
				for (PersonFamily pf : dsFamily) {
					if (pf.getRelationship().equals(
							Contants.RELATIONSHIP_TYPE_FATHER)) {
						nicRegDataDBO.setFatherFullname(pf.getName());
						nicRegDataDBO
								.setFatherDateOfBirth(service.perso.util.HelperClass
										.convertStringToDateTk(
												pf.getDateOfBirth(), 0));
						if (pf.getDateOfBirth() != null) {
							switch (pf.getDateOfBirth().length()) {
							case 4:
								nicRegDataDBO.setFatherDefDateOfBirth("Y");
								break;
							case 6:
								nicRegDataDBO.setFatherDefDateOfBirth("M");
								break;
							case 8:
								nicRegDataDBO.setFatherDefDateOfBirth("D");
								break;

							default:
								break;
							}
						}
						nicRegDataDBO.setFatherLost(pf.getLost());
					}

					if (pf.getRelationship().equals(
							Contants.RELATIONSHIP_TYPE_MOTHER)) {
						nicRegDataDBO.setMotherFullname(pf.getName());
						nicRegDataDBO
								.setMotherDateOfBirth(service.perso.util.HelperClass
										.convertStringToDateTk(
												pf.getDateOfBirth(), 0));
						if (pf.getDateOfBirth() != null) {
							switch (pf.getDateOfBirth().length()) {
							case 4:
								nicRegDataDBO.setMotherDefDateOfBirth("Y");
								break;
							case 6:
								nicRegDataDBO.setMotherDefDateOfBirth("M");
								break;
							case 8:
								nicRegDataDBO.setMotherDefDateOfBirth("D");
								break;

							default:
								break;
							}
						}
						nicRegDataDBO.setMotherLost(pf.getLost());
					}

					if (pf.getRelationship().equals(
							Contants.RELATIONSHIP_TYPE_SPOUSE)) {
						nicRegDataDBO.setSpouseFullname(pf.getName());
						nicRegDataDBO
								.setSpouseDateOfBirth(service.perso.util.HelperClass
										.convertStringToDateTk(
												pf.getDateOfBirth(), 0));
						if (pf.getDateOfBirth() != null) {
							switch (pf.getDateOfBirth().length()) {
							case 4:
								nicRegDataDBO.setSpouseDefDateOfBirth("Y");
								break;
							case 6:
								nicRegDataDBO.setSpouseDefDateOfBirth("M");
								break;
							case 8:
								nicRegDataDBO.setSpouseDefDateOfBirth("D");
								break;

							default:
								break;
							}
						}
						nicRegDataDBO.setSpouseLost(pf.getLost());
					}
				}
			}
			Map<String, String> mapFp = new HashMap<String, String>();
			Map<String, String> mapFpQua = new HashMap<String, String>();
			Map<String, String> mapFpScore = new HashMap<String, String>();

			if (transaction.getDocuments() != null) {
				for (TransactionDocument doc : transaction.getDocuments()) {
					if (doc.getDocType().equals("FP_WSQ")) {
						mapFp.put(
								(doc.getSerialNo().length() == 1 ? "0"
										+ doc.getSerialNo() : doc.getSerialNo()),
								"N");
						mapFpQua.put((doc.getSerialNo().length() == 1 ? "0"
								+ doc.getSerialNo() : doc.getSerialNo()), "70");
						mapFpScore
								.put((doc.getSerialNo().length() == 1 ? "0"
										+ doc.getSerialNo() : doc.getSerialNo()),
										"700");
					}
				}
			}
			nicRegDataDBO.setTotalFp(regDataDTO.getTotalFp());
			nicRegDataDBO.setFpIndicator(this.formFPIndicator(mapFp));

			nicRegDataDBO.setFpQuality(this.formFPQuality(mapFpQua));
			try {
				Parameters pr = parametersService.getParamDetails(
						Contants.CREATE_BY_SYSTEM,
						Contants.PRIORITY_FP_ENCODE_SYS);

				if (pr != null && pr.getParaShortValue().contains(",")) {
					String[] arr = pr.getParaShortValue().split(",");
					nicRegDataDBO.setFpEncode(this.formFPEncode(mapFp, arr));
				} else {
					String[] arr = { "1", "6", "2", "7", "3", "8", "4", "9",
							"5", "10" };
					nicRegDataDBO.setFpEncode(this.formFPEncode(mapFp, arr));
				}

			} catch (Exception e) {
			}
			nicRegDataDBO.setFpPattern(null);
			nicRegDataDBO.setSignatureFlag(null);
			nicRegDataDBO.setFacialIndicator(false);

			nicRegDataDBO.setOfficeAddress(regDataDTO.getAddressCompany());
			nicRegDataDBO.setContactNo(regDataDTO.getContactNo());
			nicRegDataDBO.setJob(regDataDTO.getJob()); // 03 Jun 2016 - add
														// aliasname for aka
			// trung editor 20/11
			nicRegDataDBO.setReligion(regDataDTO.getReligion());
			nicRegDataDBO.setNation(regDataDTO.getNation());
			nicRegDataDBO.setAddressNin(regDataDTO.getAddressNin());
			// nicRegDataDBO.setDateNin(regDataDTO.getDateNin());
			nicRegDataDBO.setDateNin(service.perso.util.HelperClass
					.convertStringToDateTk(regDataDTO.getDateNin(), 0));
			// nicRegDataDBO.setAddressCompany(regDataDTO.getAddressCompany());
			nicRegDataDBO.setAddressTempDetail(regDataDTO.getTmpAddress());
			nicRegDataDBO.setAddressTempDistrict(regDataDTO.getTmpAreaId());
			nicRegDataDBO.setAddressTempProvince(regDataDTO.getTmpPlaceId());
			// nicRegDataDBO.setAddressTempCity(regDataDTO.getTmpPlaceId());

			/* Tạm đóng 4/12/2019 Kiểm tra lại nếu cần */
			// checkFpData(nicRegDataDBO, regDataDTO);
			// nicRegDataDBO.setStyleDob(regDataDTO.getStyleDob());

			// 28-03-2020
			nicRegDataDBO.setNumDecision(regDataDTO.getNumDecision());
			nicRegDataDBO.setNameDepartment(regDataDTO.getNameDepartment());
			nicRegDataDBO.setPosition(regDataDTO.getPosition());
			nicRegDataDBO.setOwnerType(regDataDTO.getOwnerType());
			// nicRegDataDBO.setAliasName();
			nicRegDataDBO.setSearchName(regDataDTO.getSearchName());
			nicRegDataDBO.setCreateBy(regDataDTO.getCreateBy());
			nicRegDataDBO.setCreateByName(regDataDTO.getCreateByName());
		}
		return nicRegDataDBO;
	}

	public NicRegistrationData parseUpdateRegistrationDataDBO(
			UpdateTransactionDetail transaction, String nin,
			RegistrationData regDataDTO, Boolean isUpdate,
			NicRegistrationData nicRegDataDBO) {
		String transactionId = transaction.getTransactionId();
		if (regDataDTO != null) {
			if (nicRegDataDBO == null) {
				nicRegDataDBO = new NicRegistrationData();
			}
			nicRegDataDBO.setTransactionId(transactionId);
			// if (!isUpdate) {
			// nicRegDataDBO.setCreateBy(Contants.CREATE_BY_SYSTEM);
			// nicRegDataDBO.setCreateWkstnId(null);
			// nicRegDataDBO.setCreateDatetime(Calendar.getInstance()
			// .getTime());
			// }
			nicRegDataDBO.setUpdateBy(regDataDTO.getCreateBy());
			nicRegDataDBO.setUpdateWkstnId(null);
			nicRegDataDBO.setUpdateDatetime(new Date());

			if (StringUtils.isNotBlank(regDataDTO.getSurName()))
				nicRegDataDBO.setSurnameFull(regDataDTO.getSurName());
			else
				nicRegDataDBO.setSurnameFull(StringUtils.EMPTY);
			/* nicRegDataDBO.setSurnameLine1(regDataDTO.getFullName()); */

			nicRegDataDBO.setSurnameLenExceedFlag(false);// Mặc định là false:
															// cần xem lại

			if (StringUtils.isNotBlank(regDataDTO.getFirstName()))
				nicRegDataDBO.setFirstnameFull(regDataDTO.getFirstName());
			else
				nicRegDataDBO.setFirstnameFull(StringUtils.EMPTY);
			nicRegDataDBO.setFirstnameLenExceedFlag(false);

			if (StringUtils.isNotBlank(regDataDTO.getMidName()))
				nicRegDataDBO.setMiddlenameFull(regDataDTO.getMidName());
			else
				nicRegDataDBO.setMiddlenameFull(StringUtils.EMPTY);
			nicRegDataDBO.setMiddlenameLenExceedFlag(false);

			if (StringUtils.isNotEmpty(regDataDTO.getDateOfBirth())) {
				nicRegDataDBO.setDateOfBirth(HelperClass.convertStringToDateTk(
						regDataDTO.getDateOfBirth(), 0));
				switch (regDataDTO.getDateOfBirth().length()) {
				case 4:
					nicRegDataDBO.setDefDateOfBirth("Y");
					break;
				case 6:
					nicRegDataDBO.setDefDateOfBirth("M");
					break;
				case 8:
					nicRegDataDBO.setDefDateOfBirth("D");
					break;

				default:
					break;
				}
			}
			nicRegDataDBO.setApproxDob(null);
			// to set PrintDob if it is null
			if (StringUtils.isBlank(nicRegDataDBO.getPrintDob())) {
				if (nicRegDataDBO.getDateOfBirth() != null) {
					nicRegDataDBO.setPrintDob(DateUtil.parseDate(
							nicRegDataDBO.getDateOfBirth(),
							DateUtil.FORMAT_YYYYMMDD));
				}
			}
			/*
			 * switch (regDataDTO.getGender()) { case "MALE":
			 * nicRegDataDBO.setGender("M"); break; case "FEMALE":
			 * nicRegDataDBO.setGender("F"); break; default:
			 * nicRegDataDBO.setGender("N"); break; }
			 */

			nicRegDataDBO.setGender(regDataDTO.getGender());

			// EPP additional fields
			nicRegDataDBO.setNationality(regDataDTO.getNationality());
			nicRegDataDBO.setPlaceOfBirth(regDataDTO.getPlaceOfBirth());

			// get Address
			nicRegDataDBO.setAddressLine1(regDataDTO.getResidentAddress());
			// nicRegDataDBO.setAddressLine3(regDataDTO.getResidentAddress());
			nicRegDataDBO.setAddressLine3(regDataDTO.getResidentAreaId());
			nicRegDataDBO.setAddressLine4(regDataDTO.getResidentPlaceId());

			// get Relationship
			List<PersonFamily> dsFamily = transaction.getFamilies();
			if (dsFamily != null && dsFamily.size() > 0) {
				for (PersonFamily pf : dsFamily) {
					if (pf.getRelationship().equals(
							Contants.RELATIONSHIP_TYPE_FATHER)) {
						nicRegDataDBO.setFatherFullname(pf.getName());
						nicRegDataDBO
								.setFatherDateOfBirth(service.perso.util.HelperClass
										.convertStringToDateTk(
												pf.getDateOfBirth(), 0));
						if (pf.getDateOfBirth() != null) {
							switch (pf.getDateOfBirth().length()) {
							case 4:
								nicRegDataDBO.setFatherDefDateOfBirth("Y");
								break;
							case 6:
								nicRegDataDBO.setFatherDefDateOfBirth("M");
								break;
							case 8:
								nicRegDataDBO.setFatherDefDateOfBirth("D");
								break;

							default:
								break;
							}
						}
						nicRegDataDBO.setFatherLost(pf.getLost());
					}

					if (pf.getRelationship().equals(
							Contants.RELATIONSHIP_TYPE_MOTHER)) {
						nicRegDataDBO.setMotherFullname(pf.getName());
						nicRegDataDBO
								.setMotherDateOfBirth(service.perso.util.HelperClass
										.convertStringToDateTk(
												pf.getDateOfBirth(), 0));
						if (pf.getDateOfBirth() != null) {
							switch (pf.getDateOfBirth().length()) {
							case 4:
								nicRegDataDBO.setMotherDefDateOfBirth("Y");
								break;
							case 6:
								nicRegDataDBO.setMotherDefDateOfBirth("M");
								break;
							case 8:
								nicRegDataDBO.setMotherDefDateOfBirth("D");
								break;

							default:
								break;
							}
						}
						nicRegDataDBO.setMotherLost(pf.getLost());
					}

					if (pf.getRelationship().equals(
							Contants.RELATIONSHIP_TYPE_SPOUSE)) {
						nicRegDataDBO.setSpouseFullname(pf.getName());
						nicRegDataDBO
								.setSpouseDateOfBirth(service.perso.util.HelperClass
										.convertStringToDateTk(
												pf.getDateOfBirth(), 0));
						if (pf.getDateOfBirth() != null) {
							switch (pf.getDateOfBirth().length()) {
							case 4:
								nicRegDataDBO.setSpouseDefDateOfBirth("Y");
								break;
							case 6:
								nicRegDataDBO.setSpouseDefDateOfBirth("M");
								break;
							case 8:
								nicRegDataDBO.setSpouseDefDateOfBirth("D");
								break;

							default:
								break;
							}
						}
						nicRegDataDBO.setSpouseLost(pf.getLost());
					}
				}
			}
			Map<String, String> mapFp = new HashMap<String, String>();
			Map<String, String> mapFpQua = new HashMap<String, String>();
			Map<String, String> mapFpScore = new HashMap<String, String>();

			if (transaction.getDocuments() != null) {
				for (TransactionDocument doc : transaction.getDocuments()) {
					if (doc.getDocType().equals("FP_WSQ")) {
						mapFp.put(
								(doc.getSerialNo().length() == 1 ? "0"
										+ doc.getSerialNo() : doc.getSerialNo()),
								"N");
						mapFpQua.put((doc.getSerialNo().length() == 1 ? "0"
								+ doc.getSerialNo() : doc.getSerialNo()), "70");
						mapFpScore
								.put((doc.getSerialNo().length() == 1 ? "0"
										+ doc.getSerialNo() : doc.getSerialNo()),
										"700");
					}
				}
			}
			nicRegDataDBO.setTotalFp(regDataDTO.getTotalFp());
			nicRegDataDBO.setFpIndicator(this.formFPIndicator(mapFp));

			nicRegDataDBO.setFpQuality(this.formFPQuality(mapFpQua));
			try {
				Parameters pr = parametersService.getParamDetails(
						Contants.CREATE_BY_SYSTEM,
						Contants.PRIORITY_FP_ENCODE_SYS);

				if (pr != null && pr.getParaShortValue().contains(",")) {
					String[] arr = pr.getParaShortValue().split(",");
					nicRegDataDBO.setFpEncode(this.formFPEncode(mapFp, arr));
				} else {
					String[] arr = { "1", "6", "2", "7", "3", "8", "4", "9",
							"5", "10" };
					nicRegDataDBO.setFpEncode(this.formFPEncode(mapFp, arr));
				}

			} catch (Exception e) {
			}
			nicRegDataDBO.setFpPattern(null);
			nicRegDataDBO.setSignatureFlag(null);
			nicRegDataDBO.setFacialIndicator(false);

			nicRegDataDBO.setOfficeAddress(regDataDTO.getAddressCompany());
			nicRegDataDBO.setContactNo(regDataDTO.getContactNo());
			nicRegDataDBO.setJob(regDataDTO.getJob()); // 03 Jun 2016 - add
														// aliasname for aka
			// trung editor 20/11
			nicRegDataDBO.setReligion(regDataDTO.getReligion());
			nicRegDataDBO.setNation(regDataDTO.getNation());
			nicRegDataDBO.setAddressNin(regDataDTO.getAddressNin());
			// nicRegDataDBO.setDateNin(regDataDTO.getDateNin());
			nicRegDataDBO.setDateNin(service.perso.util.HelperClass
					.convertStringToDateTk(regDataDTO.getDateNin(), 0));
			// nicRegDataDBO.setAddressCompany(regDataDTO.getAddressCompany());
			nicRegDataDBO.setAddressTempDetail(regDataDTO.getTmpAddress());
			nicRegDataDBO.setAddressTempDistrict(regDataDTO.getTmpAreaId());
			nicRegDataDBO.setAddressTempProvince(regDataDTO.getTmpPlaceId());
			// nicRegDataDBO.setAddressTempCity(regDataDTO.getTmpPlaceId());

			/* Tạm đóng 4/12/2019 Kiểm tra lại nếu cần */
			// checkFpData(nicRegDataDBO, regDataDTO);
			// nicRegDataDBO.setStyleDob(regDataDTO.getStyleDob());

			// 28-03-2020
			nicRegDataDBO.setNumDecision(regDataDTO.getNumDecision());
			nicRegDataDBO.setNameDepartment(regDataDTO.getNameDepartment());
			nicRegDataDBO.setPosition(regDataDTO.getPosition());
			nicRegDataDBO.setOwnerType(regDataDTO.getOwnerType());
			// nicRegDataDBO.setAliasName(regDataDTO.getSearchName());
			nicRegDataDBO.setSearchName(regDataDTO.getSearchName());
		}
		return nicRegDataDBO;
	}

	public String convertObjToXml(InfoFamilys Obj) throws JAXBException {
		JAXBContext contextObj = JAXBContext.newInstance(InfoFamilys.class);
		StringWriter sw = new StringWriter();
		Marshaller marshallerObj = contextObj.createMarshaller();
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshallerObj.marshal(Obj, sw);
		return sw.toString();
		// System.out.println(infoPersons);
	}

	private String formFPIndicator(Map<String, String> mapFp) {
		StringBuffer indicator = new StringBuffer();
		for (int i = 1; i <= 10; i++) {
			indicator.append(String.valueOf(i) + "-");
			String yn = mapFp.get(String.valueOf(i).length() == 1 ? "0"
					+ String.valueOf(i) : String.valueOf(i));
			if (yn != null) {
				indicator.append(yn);
			} else {
				indicator.append("");
			}
			if (i < 10) {
				indicator.append(",");
			}
		}
		return indicator.toString();
	}

	private String formFPQuality(Map<String, String> mapFp) {
		StringBuffer indicator = new StringBuffer();
		for (int i = 1; i <= 10; i++) {
			indicator.append(String.valueOf(i) + "-");
			String yn = mapFp.get(String.valueOf(i).length() == 1 ? "0"
					+ String.valueOf(i) : String.valueOf(i));
			if (yn != null) {
				indicator.append(yn);
			} else {
				indicator.append("0");
			}
			if (i < 10) {
				indicator.append(",");
			}
		}
		return indicator.toString();
	}

	private String formFPEncode(Map<String, String> mapFp, String[] arr) {
		String fpEncode = "";
		int counter = 0;
		for (int i = 0; i < arr.length; i++) {
			if (mapFp.get(arr[i].length() == 1 ? "0" + arr[i] : arr[i]) != null) {
				counter++;
				if (counter == 2) {
					fpEncode += arr[i];

					return fpEncode;
				} else {
					fpEncode += arr[i] + ",";

				}
			}
		}
		return fpEncode;
	}

	/*
	 * private boolean validateTransaction(Transaction dto) throws
	 * TransactionServiceException { StringBuffer message = new StringBuffer();
	 * boolean valid = true; String errorCode =
	 * Contants.TX_UPLOAD_ERROR_CODE_DATA_VALIDATION_FAILED; // to check
	 * checksum if (StringUtils.isBlank(dto.getChecksum())) {
	 * logger.info("[validateTransaction] [{}] checksum is null", new Object[] {
	 * dto.getTransactionId() }); errorCode =
	 * Contants.TX_UPLOAD_ERROR_CODE_CHECKSUM_VERIFICATION_FAILED;
	 * message.append("checksum cannot be null, "); } else { //String
	 * inputChecksum = dto.getChecksum(); //String computedChecksum = ""; }
	 * 
	 * if (StringUtils.isBlank(dto.getRegSiteCode())) { valid = false;
	 * message.append("registration site code cannot be null, "); } else { try {
	 * SiteRepository site = siteService.getSiteRepoById(dto.getRegSiteCode());
	 * logger.info("[{}] site: {}, active: [{}] " , new Object[]
	 * {dto.getTransactionId(), site,
	 * (site!=null?site.getActiveIndicator():"")}); if (site == null) { valid =
	 * false; message.append("invalid reg site code : " +
	 * dto.getRegSiteCode()+", "); } else { if
	 * (!"Y".equalsIgnoreCase(site.getActiveIndicator())) { valid = false;
	 * message.append("registration site code : " +
	 * dto.getRegSiteCode()+" is not allowed, "); } } } catch (DaoException e) {
	 * e.printStackTrace(); } }
	 * 
	 * if (StringUtils.isBlank(dto.getIssSiteCode())) { valid = false;
	 * message.append("issuance site code cannot be null, "); } else { try {
	 * SiteRepository site = siteService.getSiteRepoById(dto.getIssSiteCode());
	 * if (site == null) { valid = false;
	 * message.append("invalid iss site code : " + dto.getIssSiteCode()+", "); }
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * 
	 * if (StringUtils.isBlank(dto.getPassportType())) { valid = false;
	 * message.append("passportType cannot be null, "); } else { //[cw] 26 Apr
	 * 2016 - block non - regular passport. CodeValues codeValue =
	 * codesService.getCodeValueByIdName(Codes.PASSPORT_TYPE,
	 * dto.getPassportType()); if (codeValue == null ||
	 * Boolean.TRUE.equals(codeValue.getDeleteFlag())) { valid = false;
	 * message.append("invalid passportType : " + dto.getPassportType()+", "); }
	 * } if (dto.getPriority()==null) { valid = false;
	 * message.append("priority cannot be null, "); }
	 * 
	 * Tạm đóng 4/12/2019
	 * 
	 * // if (dto.getValidatyPeriod()==null ||
	 * dto.getValidatyPeriod().intValue()==0) { // valid = false; //
	 * message.append("validityPeriod cannot be null or zero, "); // } // // if
	 * (StringUtils.isBlank(dto.getIssuingAuthority())) { // valid = false; //
	 * message.append("issuingAuthority cannot be null, "); // }
	 * 
	 * // validate estDateOfCollection if
	 * (StringUtils.equalsIgnoreCase(Contants.TRANSACTION_TYPE_REGISTRATION,
	 * dto.getTransactionType()) ||
	 * StringUtils.equalsIgnoreCase(Contants.TRANSACTION_TYPE_REPLACEMENT,
	 * dto.getTransactionType()) ||
	 * StringUtils.equalsIgnoreCase(Contants.TRANSACTION_TYPE_LOST,
	 * dto.getTransactionType())) { if (dto.getEstDateOfCollection()==null) {
	 * valid = false; message.append("estDateOfCollection cannot be null, "); }
	 * }
	 * 
	 * if (dto.getRegisData() == null) { valid = false;
	 * message.append("registration data dto cannot be null, "); } else {
	 * 
	 * if (dto.getRegisData() == null) { valid = false;
	 * message.append("registration data dto cannot be null, "); } else {
	 * RegistrationData reg = dto.getRegisData(); { int maxLenForFullname = 96;
	 * Parameters paramMaxLenForFullNames = parametersDao.findById(new
	 * ParametersId(Contants.PARA_SCOPE_WS,
	 * Contants.PARA_NAME_MAX_LEN_FULLNAME)); //29 Jul 2016 (chris): change
	 * scope //CHIP LIMIT FOR DB, allow truncation if (paramMaxLenForFullNames
	 * != null &&
	 * StringUtils.isNumeric(paramMaxLenForFullNames.getParaShortValue())) {
	 * maxLenForFullname =
	 * Integer.parseInt(paramMaxLenForFullNames.getParaShortValue()); } int
	 * maxLenForNamepage = 50; Parameters paramMaxLenForNamepage =
	 * parametersDao.findById(new ParametersId(Contants.PARA_SCOPE_WS,
	 * Contants.PARA_NAME_MAX_LEN_NAMEPAGE)); //29 Jul 2016 (chris): change
	 * scope //PAGE LIMIT FOR DB, allow truncation if (paramMaxLenForNamepage !=
	 * null &&
	 * StringUtils.isNumeric(paramMaxLenForNamepage.getParaShortValue())) {
	 * maxLenForNamepage =
	 * Integer.parseInt(paramMaxLenForNamepage.getParaShortValue()); } int
	 * surnameLen = StringUtils.length(reg.getSurName()); int firstnameLen =
	 * StringUtils.length(reg.getFirstName()); int middlenameLen =
	 * StringUtils.length(reg.getMidName());
	 * 
	 * if (surnameLen > maxLenForNamepage) { valid = false;
	 * message.append("surname line exceeded the limits, "); } if (firstnameLen
	 * > maxLenForNamepage) { valid = false;
	 * message.append("firstname line exceeded the limits, "); } if
	 * (middlenameLen > maxLenForNamepage) { valid = false;
	 * message.append("middlename line exceeded the limits, "); }
	 * 
	 * if (surnameLen+firstnameLen+middlenameLen+2 > maxLenForFullname) { //29
	 * Jul 2016 (chris): add length for space valid = false;
	 * message.append("full name exceeded the limits, "); }
	 * logger.info("[{}] full name length: {} " , new Object[]
	 * {dto.getTransactionId(), surnameLen+firstnameLen+middlenameLen}); }
	 * //[cw] 2016 May 30 - validate max length of names - end
	 * 
	 * if (StringUtils.isNotBlank(reg.getPlaceOfBirth())) { int
	 * maxLenForPlaceOfBirth = 42; Parameters param = parametersDao.findById(new
	 * ParametersId(Contants.PARA_SCOPE_PERSO,
	 * Contants.PARA_NAME_MAX_LEN_PLACE_OF_BIRTH)); if (param != null &&
	 * StringUtils.isNumeric(param.getParaShortValue())) { maxLenForPlaceOfBirth
	 * = Integer.parseInt(param.getParaShortValue()); } int pobLen =
	 * StringUtils.length(reg.getPlaceOfBirth());
	 * 
	 * if (pobLen > maxLenForPlaceOfBirth) { valid = false;
	 * message.append("place of birth exceeded the limits, "); } } //[cw] 2016
	 * Jun 03 - validate aka - aliasname, place of birth - end
	 * 
	 * // validate dob cannot be null if (reg.getDateOfBirth() == null) { valid
	 * = false; message.append("date of birth cannot be null, "); } else {
	 * //[cw] 2016 May 13 - validate max number of days range for Date of Birth
	 * - start int maxNumYearForDOB = 150; Parameters paramMaxNumDaysForPrinting
	 * = parametersDao.findById(new
	 * ParametersId(Contants.PARA_SCOPE_APPLICATION,
	 * Contants.PARA_NAME_MAX_NUM_YEAR_FOR_VALID_DOB)); if
	 * (paramMaxNumDaysForPrinting != null &&
	 * StringUtils.isNumeric(paramMaxNumDaysForPrinting.getParaShortValue())) {
	 * maxNumYearForDOB =
	 * Integer.parseInt(paramMaxNumDaysForPrinting.getParaShortValue()); }
	 * Calendar cal = Calendar.getInstance(); //int systemYear =
	 * cal.get(Calendar.YEAR); Date systemDate = cal.getTime(); if
	 * (reg.getDateOfBirth() != null) { int diff =
	 * DateUtil.dateDiff(HelperClass.convertStringToDateTk(reg.getDateOfBirth(),
	 * 0), systemDate); logger.info("[{}] DateOfBirth: {} - diff/365: {} " , new
	 * Object[] {dto.getTransactionId(), reg.getDateOfBirth(), diff/365}); if
	 * (diff > maxNumYearForDOB*365) {
	 * logger.warn("Invalid DateOfBirth: {} for transaction: {}" ,
	 * reg.getDateOfBirth(), dto.getTransactionId()); valid = false;
	 * message.append("date of birth is invalid date, "); } } } // validate
	 * gender cannot be null ///Tạm đóng để đưa dữ liệu LOST/DAMEGE if
	 * (StringUtils.isBlank(reg.getGender())) { valid = false;
	 * message.append("gender cannot be null, "); }
	 * 
	 * 
	 * //[cw] 2016 May 30 - validate nationality for nat and natFull - start if
	 * (StringUtils.isBlank(reg.getNationality())) { valid = false;
	 * message.append("nationality cannot be null, "); } else { CodeValues
	 * codeValue = codesService.getCodeValueByIdName(Codes.NATIONALITY,
	 * reg.getNationality()); if (codeValue == null ||
	 * Boolean.TRUE.equals(codeValue.getDeleteFlag())) { valid = false;
	 * message.append("invalid nationality : " + reg.getNationality()+", "); } }
	 * //[cw] 2016 May 30 - validate nationality for nat and natFull - end }
	 * 
	 * // validate fingerprint position List<TransactionDocument> fpInfo =
	 * dto.getDocuments(); TransactionDocument docTpl = null; if(fpInfo !=
	 * null){ for(TransactionDocument doc : fpInfo){
	 * if(doc.getDocType().equals(Contants.DOC_TYPE_TPL)){ docTpl = doc; break;
	 * } } } if (docTpl!=null) { List<Integer> tplFingerPositionList = new
	 * ArrayList<Integer>(); if (docTpl.getDocData()!=null &&
	 * StringUtils.isNotBlank(new String(docTpl.getDocData()))) { //[CW] 2016
	 * Apr 20 logger.debug(
	 * "[{}] in validateTransaction is failed, fingerprintInfo.getCmlafTemplate()!=null"
	 * , new Object[] { dto.getTransactionId() } ); try { String xml = new
	 * String(docTpl.getDocData()); FmsTemplate fmsTemplate =
	 * xmlConvertor.getPayload(xml); if (fmsTemplate!=null) { if
	 * (CollectionUtils.isNotEmpty(fmsTemplate.getFingerprintSections())) {
	 * //check first fingerprint sections only. FingerprintSection fs =
	 * fmsTemplate.getFingerprintSections().get(0); if
	 * (CollectionUtils.isNotEmpty(fs.getFingerprintTemplates())) { for
	 * (FingerprintTemplate fpTemplate: fs.getFingerprintTemplates()) {
	 * if(fpTemplate.getFingerprintNo()!=null &&
	 * StringUtils.isNotBlank(fpTemplate.getFingerprintMinutia())) {
	 * tplFingerPositionList.add(fpTemplate.getFingerprintNo()); } } } } } }
	 * catch (Exception e) {
	 * logger.error("Error when getting payload from xml."); } } } } if (!valid)
	 * { logger.warn("[{}] validateTransaction is failed, reason:{}", new
	 * Object[] { dto.getTransactionId(), message.toString()} ); throw new
	 * TransactionServiceException(
	 * "Transaction Object validation failed, reason: "+
	 * message.toString()+"["+dto.getTransactionId()+"]", errorCode); } return
	 * valid; }
	 */

	private boolean validateUpdateTransaction(UpdateTransactionDetail dto)
			throws TransactionServiceException {
		StringBuffer message = new StringBuffer();
		boolean valid = true;
		String errorCode = Contants.TX_UPLOAD_ERROR_CODE_DATA_VALIDATION_FAILED;
		// to check checksum
		if (StringUtils.isBlank(dto.getChecksum())) {
			logger.info("[validateTransaction] [{}] checksum is null",
					new Object[] { dto.getTransactionId() });
			errorCode = Contants.TX_UPLOAD_ERROR_CODE_CHECKSUM_VERIFICATION_FAILED;
			message.append("checksum cannot be null, ");
		} else {
			// String inputChecksum = dto.getChecksum();
			// String computedChecksum = "";
		}

		if (StringUtils.isBlank(dto.getRegSiteCode())) {
			valid = false;
			message.append("registration site code cannot be null, ");
		} else {
			try {
				SiteRepository site = siteService.getSiteRepoById(dto
						.getRegSiteCode());
				logger.info(
						"[{}] site: {}, active: [{}] ",
						new Object[] { dto.getTransactionId(), site,
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

		/* Tạm đóng 4/12/2019 */

		// if (dto.getValidatyPeriod()==null ||
		// dto.getValidatyPeriod().intValue()==0) {
		// valid = false;
		// message.append("validityPeriod cannot be null or zero, ");
		// }
		//
		// if (StringUtils.isBlank(dto.getIssuingAuthority())) {
		// valid = false;
		// message.append("issuingAuthority cannot be null, ");
		// }

		// validate estDateOfCollection
		if (StringUtils.equalsIgnoreCase(
				Contants.TRANSACTION_TYPE_REGISTRATION,
				dto.getTransactionType())
				|| StringUtils.equalsIgnoreCase(
						Contants.TRANSACTION_TYPE_REPLACEMENT,
						dto.getTransactionType())
				|| StringUtils.equalsIgnoreCase(Contants.TRANSACTION_TYPE_LOST,
						dto.getTransactionType())) {
			if (dto.getEstDateOfCollection() == null) {
				valid = false;
				message.append("estDateOfCollection cannot be null, ");
			}
		}

		if (dto.getRegisData() == null) {
			valid = false;
			message.append("registration data dto cannot be null, ");
		} else {

			if (dto.getRegisData() == null) {
				valid = false;
				message.append("registration data dto cannot be null, ");
			} else {
				RegistrationData reg = dto.getRegisData();
				{
					int maxLenForFullname = 96;
					Parameters paramMaxLenForFullNames = parametersDao
							.findById(new ParametersId(Contants.PARA_SCOPE_WS,
									Contants.PARA_NAME_MAX_LEN_FULLNAME)); // 29
																			// Jul
																			// 2016
																			// (chris):
																			// change
																			// scope
																			// //CHIP
																			// LIMIT
																			// FOR
																			// DB,
																			// allow
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
							.findById(new ParametersId(Contants.PARA_SCOPE_WS,
									Contants.PARA_NAME_MAX_LEN_NAMEPAGE)); // 29
																			// Jul
																			// 2016
																			// (chris):
																			// change
																			// scope
																			// //PAGE
																			// LIMIT
																			// FOR
																			// DB,
																			// allow
																			// truncation
					if (paramMaxLenForNamepage != null
							&& StringUtils.isNumeric(paramMaxLenForNamepage
									.getParaShortValue())) {
						maxLenForNamepage = Integer
								.parseInt(paramMaxLenForNamepage
										.getParaShortValue());
					}
					int surnameLen = StringUtils.length(reg.getSurName());
					int firstnameLen = StringUtils.length(reg.getFirstName());
					int middlenameLen = StringUtils.length(reg.getMidName());

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
							dto.getTransactionId(),
							surnameLen + firstnameLen + middlenameLen });
				}
				// [cw] 2016 May 30 - validate max length of names - end

				if (StringUtils.isNotBlank(reg.getPlaceOfBirth())) {
					int maxLenForPlaceOfBirth = 42;
					Parameters param = parametersDao.findById(new ParametersId(
							Contants.PARA_SCOPE_PERSO,
							Contants.PARA_NAME_MAX_LEN_PLACE_OF_BIRTH));
					if (param != null
							&& StringUtils.isNumeric(param.getParaShortValue())) {
						maxLenForPlaceOfBirth = Integer.parseInt(param
								.getParaShortValue());
					}
					int pobLen = StringUtils.length(reg.getPlaceOfBirth());

					if (pobLen > maxLenForPlaceOfBirth) {
						valid = false;
						message.append("place of birth exceeded the limits, ");
					}
				}
				// [cw] 2016 Jun 03 - validate aka - aliasname, place of birth -
				// end

				// validate dob cannot be null
				if (reg.getDateOfBirth() == null) {
					valid = false;
					message.append("date of birth cannot be null, ");
				} else {
					// [cw] 2016 May 13 - validate max number of days range for
					// Date of Birth - start
					int maxNumYearForDOB = 150;
					Parameters paramMaxNumDaysForPrinting = parametersDao
							.findById(new ParametersId(
									Contants.PARA_SCOPE_APPLICATION,
									Contants.PARA_NAME_MAX_NUM_YEAR_FOR_VALID_DOB));
					if (paramMaxNumDaysForPrinting != null
							&& StringUtils.isNumeric(paramMaxNumDaysForPrinting
									.getParaShortValue())) {
						maxNumYearForDOB = Integer
								.parseInt(paramMaxNumDaysForPrinting
										.getParaShortValue());
					}
					Calendar cal = Calendar.getInstance();
					// int systemYear = cal.get(Calendar.YEAR);
					Date systemDate = cal.getTime();
					if (reg.getDateOfBirth() != null) {
						int diff = DateUtil.dateDiff(
								HelperClass.convertStringToDateTk(
										reg.getDateOfBirth(), 0), systemDate);
						logger.info(
								"[{}] DateOfBirth: {} - diff/365: {} ",
								new Object[] { dto.getTransactionId(),
										reg.getDateOfBirth(), diff / 365 });
						if (diff > maxNumYearForDOB * 365) {
							logger.warn(
									"Invalid DateOfBirth: {} for transaction: {}",
									reg.getDateOfBirth(),
									dto.getTransactionId());
							valid = false;
							message.append("date of birth is invalid date, ");
						}
					}
				}
				// validate gender cannot be null
				// /Tạm đóng để đưa dữ liệu LOST/DAMEGE
				if (StringUtils.isBlank(reg.getGender())) {
					valid = false;
					message.append("gender cannot be null, ");
				}

				// [cw] 2016 May 30 - validate nationality for nat and natFull -
				// start
				if (StringUtils.isBlank(reg.getNationality())) {
					valid = false;
					message.append("nationality cannot be null, ");
				} else {
					CodeValues codeValue = codesService.getCodeValueByIdName(
							Codes.NATIONALITY, reg.getNationality());
					if (codeValue == null
							|| Boolean.TRUE.equals(codeValue.getDeleteFlag())) {
						valid = false;
						message.append("invalid nationality : "
								+ reg.getNationality() + ", ");
					}
				}
				// [cw] 2016 May 30 - validate nationality for nat and natFull -
				// end
			}

			// validate fingerprint position
			List<TransactionDocument> fpInfo = dto.getDocuments();
			TransactionDocument docTpl = null;
			if (fpInfo != null) {
				for (TransactionDocument doc : fpInfo) {
					if (doc.getDocType().equals(Contants.DOC_TYPE_TPL)) {
						docTpl = doc;
						break;
					}
				}
			}
			if (docTpl != null) {
				List<Integer> tplFingerPositionList = new ArrayList<Integer>();
				if (docTpl.getDocData() != null
						&& StringUtils.isNotBlank(new String(docTpl
								.getDocData()))) { // [CW] 2016 Apr 20
					logger.debug(
							"[{}] in validateTransaction is failed, fingerprintInfo.getCmlafTemplate()!=null",
							new Object[] { dto.getTransactionId() });
					try {
						String xml = new String(docTpl.getDocData());
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
				}
			}
		}
		if (!valid) {
			logger.warn("[{}] validateTransaction is failed, reason:{}",
					new Object[] { dto.getTransactionId(), message.toString() });
			throw new TransactionServiceException(
					"Transaction Object validation failed, reason: "
							+ message.toString() + "[" + dto.getTransactionId()
							+ "]", errorCode);
		}
		return valid;
	}

	private boolean validateTransaction(Transaction dto)
			throws TransactionServiceException {
		StringBuffer message = new StringBuffer();
		boolean valid = true;
		String errorCode = Contants.TX_UPLOAD_ERROR_CODE_DATA_VALIDATION_FAILED;
		// to check checksum
		if (StringUtils.isBlank(dto.getChecksum())) {
			logger.info("[validateTransaction] [{}] checksum is null",
					new Object[] { dto.getTransactionId() });
			errorCode = Contants.TX_UPLOAD_ERROR_CODE_CHECKSUM_VERIFICATION_FAILED;
			message.append("checksum cannot be null, ");
		} else {
			// String inputChecksum = dto.getChecksum();
			// String computedChecksum = "";
		}

		if (StringUtils.isBlank(dto.getRegSiteCode())) {
			valid = false;
			message.append("registration site code cannot be null, ");
		} else {
			try {
				SiteRepository site = siteService.getSiteRepoById(dto
						.getRegSiteCode());
				logger.info(
						"[{}] site: {}, active: [{}] ",
						new Object[] { dto.getTransactionId(), site,
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

		/* Tạm đóng 4/12/2019 */

		// if (dto.getValidatyPeriod()==null ||
		// dto.getValidatyPeriod().intValue()==0) {
		// valid = false;
		// message.append("validityPeriod cannot be null or zero, ");
		// }
		//
		// if (StringUtils.isBlank(dto.getIssuingAuthority())) {
		// valid = false;
		// message.append("issuingAuthority cannot be null, ");
		// }

		// validate estDateOfCollection
		if (StringUtils.equalsIgnoreCase(
				Contants.TRANSACTION_TYPE_REGISTRATION,
				dto.getTransactionType())
				|| StringUtils.equalsIgnoreCase(
						Contants.TRANSACTION_TYPE_REPLACEMENT,
						dto.getTransactionType())
				|| StringUtils.equalsIgnoreCase(Contants.TRANSACTION_TYPE_LOST,
						dto.getTransactionType())) {
			if (dto.getEstDateOfCollection() == null) {
				valid = false;
				message.append("estDateOfCollection cannot be null, ");
			}
		}

		if (dto.getRegisData() == null) {
			valid = false;
			message.append("registration data dto cannot be null, ");
		} else {

			if (dto.getRegisData() == null) {
				valid = false;
				message.append("registration data dto cannot be null, ");
			} else {
				RegistrationData reg = dto.getRegisData();
				{
					int maxLenForFullname = 96;
					Parameters paramMaxLenForFullNames = parametersDao
							.findById(new ParametersId(Contants.PARA_SCOPE_WS,
									Contants.PARA_NAME_MAX_LEN_FULLNAME)); // 29
																			// Jul
																			// 2016
																			// (chris):
																			// change
																			// scope
																			// //CHIP
																			// LIMIT
																			// FOR
																			// DB,
																			// allow
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
							.findById(new ParametersId(Contants.PARA_SCOPE_WS,
									Contants.PARA_NAME_MAX_LEN_NAMEPAGE)); // 29
																			// Jul
																			// 2016
																			// (chris):
																			// change
																			// scope
																			// //PAGE
																			// LIMIT
																			// FOR
																			// DB,
																			// allow
																			// truncation
					if (paramMaxLenForNamepage != null
							&& StringUtils.isNumeric(paramMaxLenForNamepage
									.getParaShortValue())) {
						maxLenForNamepage = Integer
								.parseInt(paramMaxLenForNamepage
										.getParaShortValue());
					}
					int surnameLen = StringUtils.length(reg.getSurName());
					int firstnameLen = StringUtils.length(reg.getFirstName());
					int middlenameLen = StringUtils.length(reg.getMidName());

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
							dto.getTransactionId(),
							surnameLen + firstnameLen + middlenameLen });
				}
				// [cw] 2016 May 30 - validate max length of names - end

				if (StringUtils.isNotBlank(reg.getPlaceOfBirth())) {
					int maxLenForPlaceOfBirth = 42;
					Parameters param = parametersDao.findById(new ParametersId(
							Contants.PARA_SCOPE_PERSO,
							Contants.PARA_NAME_MAX_LEN_PLACE_OF_BIRTH));
					if (param != null
							&& StringUtils.isNumeric(param.getParaShortValue())) {
						maxLenForPlaceOfBirth = Integer.parseInt(param
								.getParaShortValue());
					}
					int pobLen = StringUtils.length(reg.getPlaceOfBirth());

					if (pobLen > maxLenForPlaceOfBirth) {
						valid = false;
						message.append("place of birth exceeded the limits, ");
					}
				}
				// [cw] 2016 Jun 03 - validate aka - aliasname, place of birth -
				// end

				// validate dob cannot be null
				if (reg.getDateOfBirth() == null) {
					valid = false;
					message.append("date of birth cannot be null, ");
				} else {
					// [cw] 2016 May 13 - validate max number of days range for
					// Date of Birth - start
					int maxNumYearForDOB = 150;
					Parameters paramMaxNumDaysForPrinting = parametersDao
							.findById(new ParametersId(
									Contants.PARA_SCOPE_APPLICATION,
									Contants.PARA_NAME_MAX_NUM_YEAR_FOR_VALID_DOB));
					if (paramMaxNumDaysForPrinting != null
							&& StringUtils.isNumeric(paramMaxNumDaysForPrinting
									.getParaShortValue())) {
						maxNumYearForDOB = Integer
								.parseInt(paramMaxNumDaysForPrinting
										.getParaShortValue());
					}
					Calendar cal = Calendar.getInstance();
					// int systemYear = cal.get(Calendar.YEAR);
					Date systemDate = cal.getTime();
					if (reg.getDateOfBirth() != null) {
						int diff = DateUtil.dateDiff(
								HelperClass.convertStringToDateTk(
										reg.getDateOfBirth(), 0), systemDate);
						logger.info(
								"[{}] DateOfBirth: {} - diff/365: {} ",
								new Object[] { dto.getTransactionId(),
										reg.getDateOfBirth(), diff / 365 });
						if (diff > maxNumYearForDOB * 365) {
							logger.warn(
									"Invalid DateOfBirth: {} for transaction: {}",
									reg.getDateOfBirth(),
									dto.getTransactionId());
							valid = false;
							message.append("date of birth is invalid date, ");
						}
					}
				}
				// validate gender cannot be null
				// /Tạm đóng để đưa dữ liệu LOST/DAMEGE
				if (StringUtils.isBlank(reg.getGender())) {
					valid = false;
					message.append("gender cannot be null, ");
				}

				// [cw] 2016 May 30 - validate nationality for nat and natFull -
				// start
				if (StringUtils.isBlank(reg.getNationality())) {
					valid = false;
					message.append("nationality cannot be null, ");
				} else {
					CodeValues codeValue = codesService.getCodeValueByIdName(
							Codes.NATIONALITY, reg.getNationality());
					if (codeValue == null
							|| Boolean.TRUE.equals(codeValue.getDeleteFlag())) {
						valid = false;
						message.append("invalid nationality : "
								+ reg.getNationality() + ", ");
					}
				}
				// [cw] 2016 May 30 - validate nationality for nat and natFull -
				// end
			}

			// validate fingerprint position
			List<TransactionDocument> fpInfo = dto.getDocuments();
			TransactionDocument docTpl = null;
			if (fpInfo != null) {
				for (TransactionDocument doc : fpInfo) {
					if (doc.getDocType().equals(Contants.DOC_TYPE_TPL)) {
						docTpl = doc;
						break;
					}
				}
			}
			if (docTpl != null) {
				List<Integer> tplFingerPositionList = new ArrayList<Integer>();
				if (docTpl.getDocData() != null
						&& StringUtils.isNotBlank(new String(docTpl
								.getDocData()))) { // [CW] 2016 Apr 20
					logger.debug(
							"[{}] in validateTransaction is failed, fingerprintInfo.getCmlafTemplate()!=null",
							new Object[] { dto.getTransactionId() });
					try {
						String xml = new String(docTpl.getDocData());
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
				}
			}
		}
		if (!valid) {
			logger.warn("[{}] validateTransaction is failed, reason:{}",
					new Object[] { dto.getTransactionId(), message.toString() });
			throw new TransactionServiceException(
					"Transaction Object validation failed, reason: "
							+ message.toString() + "[" + dto.getTransactionId()
							+ "]", errorCode);
		}
		return valid;
	}

	private BaseModelSingle<Boolean> updateStatusLog(String transactionId,
			String transactionStatus) {
		try {
			if (StringUtils.isBlank(transactionId)) {
				return new BaseModelSingle<Boolean>(false, false,
						new FaultException(
								StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
										.getDesc()
										+ "[FieldName: TransactionID]",
								StatusCode.SYNCWS_MANDATORY_FIELD_ERROR
										.convertToFaultDetail()).getMessage());
			}

			NicTransaction transaction = nicTransactionService
					.findById(transactionId);
			if (transaction == null) {
				return new BaseModelSingle<Boolean>(false, false,
						new FaultException(
								StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc()
										+ "[FieldName: Transaction Data]",
								StatusCode.SYNCWS_DATA_NOT_FOUND
										.convertToFaultDetail()).getMessage());
			}

			transaction.setTransactionStatus(transactionStatus);
			transaction.setUpdateDatetime(Calendar.getInstance().getTime());
			transaction.setUpdateBy(Contants.CREATE_BY_SYSTEM);

			nicTransactionService.saveOrUpdateTransaction(transaction);

			NicTransactionLog transactionLog = new NicTransactionLog();
			transactionLog.setRefId(transactionId);
			transactionLog.setLogCreateTime(new Date());
			transactionLog.setTransactionStage("");
			transactionLog.setTransactionStatus(transactionStatus);
			transactionLog.setStartTime(new Date());
			transactionLog.setEndTime(new Date());
			transactionLog.setOfficerId(Contants.CREATE_BY_SYSTEM);

			nicTransactionLogService.save(transactionLog);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException.createMessageException(e)
							+ " - updateStatusLog - " + transactionId
							+ " - thất bại.");
		}
	}

	private void addObjToQueueXncJob(String ObjType, String site,
			String passportNo) {
		try {
			SynQueueJobXnc queue = new SynQueueJobXnc();
			queue.setCreatedTs(Calendar.getInstance().getTime());
			queue.setObjectId(passportNo);
			queue.setObjectType(ObjType);
			queue.setSiteCode(site);
			queue.setStatus(Contants.CODE_STATUS_QUEUE_PENDING);
			queue.setSyncRetry(1);
			queueJobXncService.saveOrUpdateQueueXnc(queue);
		} catch (Exception e) {

		}
	}

	/*
	 * private void savePersonByUpload(Transaction transaction, String
	 * personCode) { try { EppPerson eppPerson = new EppPerson();
	 * eppPerson.setPersonCode(personCode);
	 * eppPerson.setRefId(transaction.getTransactionId());
	 * eppPerson.setSrcDoc(transaction.getTransactionId());
	 * eppPerson.setName(transaction.getRegisData().getFullName());
	 * eppPerson.setSearchName(HelperClass.removeAccent(transaction
	 * .getRegisData().getFullName()));
	 * eppPerson.setGender(transaction.getRegisData().getGender());
	 * eppPerson.setDateOfBirth(transaction.getRegisData() .getDateOfBirth());
	 * String pob = transaction.getRegisData().getPlaceOfBirth(); if
	 * (StringUtils.isNotEmpty(pob)) { eppPerson.setPlaceOfBirthCode(pob);
	 * eppPerson.setPlaceOfBirthName(codesService
	 * .getCodeValueDescByIdName("CODE_BirthPlace", pob, "")); }
	 * eppPerson.setIdNumber(transaction.getNin());
	 * eppPerson.setDateOfIdIssue(transaction.getRegisData().getDateNin());
	 * eppPerson.setPlaceOfIdIssueName(transaction.getRegisData()
	 * .getAddressNin()); String nation =
	 * transaction.getRegisData().getNation(); if
	 * (StringUtils.isNotEmpty(nation)) { eppPerson.setEthnicCode(nation);
	 * eppPerson.setEthnic(codesService.getCodeValueDescByIdName( "CODE_NATION",
	 * nation, "")); } String religion =
	 * transaction.getRegisData().getReligion(); if
	 * (StringUtils.isNotEmpty(religion)) { eppPerson.setReligionCode(religion);
	 * eppPerson.setReligion(codesService.getCodeValueDescByIdName(
	 * "CODE_RELIGION", religion, "")); } String nationality =
	 * transaction.getRegisData().getNationality(); if
	 * (StringUtils.isNotEmpty(nationality)) {
	 * eppPerson.setNationalityCode(nationality);
	 * eppPerson.setNationalityName(codesService
	 * .getCodeValueDescByIdName("NATIONALITY", nationality, "")); }
	 * List<EppPersonFamily> fmList = new ArrayList<EppPersonFamily>(); if
	 * (transaction.getFamilies() != null) { for (PersonFamily fm :
	 * transaction.getFamilies()) { EppPersonFamily fmy = new EppPersonFamily();
	 * if (fm.getRelationship().equals( Contants.RELATIONSHIP_TYPE_FATHER)) {
	 * eppPerson.setFatherName(fm.getName());
	 * eppPerson.setFatherSearchName(HelperClass .removeAccent(fm.getName()));
	 * fmy.setName(fm.getName()); fmy.setDateOfBirth(fm.getDateOfBirth());
	 * fmy.setGender(fm.getGender()); fmy.setLost(fm.getLost());
	 * fmy.setPlaceOfBirthCode(fm.getPlaceOfBirth());
	 * fmy.setRelationship(Contants.RELATIONSHIP_TYPE_FATHER);
	 * fmy.setCreatedBy("SYSTEM"); fmy.setCreateTs(new Date()); fmList.add(fmy);
	 * } if (fm.getRelationship().equals( Contants.RELATIONSHIP_TYPE_MOTHER)) {
	 * eppPerson.setMotherName(fm.getName());
	 * eppPerson.setMotherSearchName(HelperClass .removeAccent(fm.getName()));
	 * fmy.setName(fm.getName()); fmy.setDateOfBirth(fm.getDateOfBirth());
	 * fmy.setGender(fm.getGender()); fmy.setLost(fm.getLost());
	 * fmy.setPlaceOfBirthCode(fm.getPlaceOfBirth());
	 * fmy.setRelationship(Contants.RELATIONSHIP_TYPE_MOTHER);
	 * fmy.setCreatedBy("SYSTEM"); fmy.setCreateTs(new Date()); fmList.add(fmy);
	 * } if (fm.getRelationship().equals( Contants.RELATIONSHIP_TYPE_SPOUSE)) {
	 * fmy.setName(fm.getName()); fmy.setDateOfBirth(fm.getDateOfBirth());
	 * fmy.setGender(fm.getGender()); fmy.setLost(fm.getLost());
	 * fmy.setPlaceOfBirthCode(fm.getPlaceOfBirth());
	 * fmy.setRelationship(Contants.RELATIONSHIP_TYPE_SPOUSE);
	 * fmy.setCreatedBy("SYSTEM"); fmy.setCreateTs(new Date()); fmList.add(fmy);
	 * } if (fm.getRelationship().equals( Contants.RELATIONSHIP_TYPE_CHILD)) {
	 * fmy.setName(fm.getName()); fmy.setDateOfBirth(fm.getDateOfBirth());
	 * fmy.setGender(fm.getGender()); fmy.setLost(fm.getLost());
	 * fmy.setPlaceOfBirthCode(fm.getPlaceOfBirth());
	 * fmy.setRelationship(Contants.RELATIONSHIP_TYPE_CHILD);
	 * fmy.setCreatedBy("SYSTEM"); fmy.setCreateTs(new Date()); fmList.add(fmy);
	 * } } } EppPersonAttchmnt eppAtt = new EppPersonAttchmnt(); if
	 * (transaction.getDocuments() != null) { for (TransactionDocument doc :
	 * transaction.getDocuments()) { if (doc.getDocType().equals("PH_CAP")) {
	 * eppAtt.setFileName("PHOTO"); eppAtt.setSerialNo(1);
	 * eppAtt.setType_(doc.getDocType()); eppAtt.setBase64(doc.getDocData());
	 * break; } } } eppPerson.setCreatedBy("SYSTEM"); eppPerson.setCreateTs(new
	 * Date()); Boolean result = eppPersonService.saveOrUpdateData(eppPerson)
	 * .getModel(); logger.warn("result when the save person === " + result); if
	 * (result) { EppPerson epp = eppPersonService.findPersonByPersonCode(
	 * transaction.getp); for (EppPersonFamily fl : fmList) {
	 * fl.setSubjectPerson(epp.getPersonId());
	 * eppPersonService.saveOrUpdateDataFamily(fl); }
	 * eppAtt.setPersonId(epp.getPersonId());
	 * eppPersonService.saveOrUpdateDataAttchmnt(eppAtt); } } catch (Exception
	 * e) { e.printStackTrace();
	 * logger.error("error save person when the upload transaction, transactionId: "
	 * + transaction.getTransactionId()); } }
	 */

	private BaseModelSingle<Boolean> savePersonByUploadTransaction(
			NicUploadJob upload, String personCode) {
		try {
			NicTransaction transaction = upload.getNicTransaction();
			NicRegistrationData reg = transaction.getNicRegistrationData();
			EppPerson eppPerson = eppPersonService
					.findPersonByPersonCode(personCode);
			if (eppPerson == null) {
				eppPerson = new EppPerson();
				eppPerson.setPersonCode(personCode);
			}
			String name = HelperClass.createFullName(reg.getSurnameFull(),
					reg.getMiddlenameFull(), reg.getFirstnameFull());
			eppPerson.setSrcDoc(transaction.getTransactionId());
			eppPerson.setName(name);
			eppPerson.setSearchName(HelperClass.removeAccent(name));
			eppPerson.setGender(reg.getGender());
			eppPerson.setDateOfBirth(HelperClass
					.convertStringToString_ForPerson(HelperClass
							.convertDateToString2(reg.getDateOfBirth())));
			String pob = reg.getPlaceOfBirth();
			if (StringUtils.isNotEmpty(pob)) {
				eppPerson.setPlaceOfBirthCode(pob);
				eppPerson.setPlaceOfBirthName(codesService
						.getCodeValueDescByIdName("CODE_BirthPlace", pob, ""));
			}
			eppPerson.setIdNumber(transaction.getNin());
			eppPerson.setDateOfIdIssue(HelperClass
					.convertStringToString_ForPerson(HelperClass
							.convertDateToString2(reg.getDateNin())));
			eppPerson.setPlaceOfIdIssueName(reg.getAddressNin());
			String nation = reg.getNation();
			if (StringUtils.isNotEmpty(nation)) {
				eppPerson.setEthnicCode(nation);
				eppPerson.setEthnic(codesService.getCodeValueDescByIdName(
						"CODE_NATION", nation, ""));
			}
			String religion = reg.getReligion();
			if (StringUtils.isNotEmpty(religion)) {
				eppPerson.setReligionCode(religion);
				eppPerson.setReligion(codesService.getCodeValueDescByIdName(
						"CODE_RELIGION", religion, ""));
			}
			String nationality = reg.getNationality();
			if (StringUtils.isNotEmpty(nationality)) {
				eppPerson.setNationalityCode(nationality);
				eppPerson.setNationalityName(codesService
						.getCodeValueDescByIdName("NATIONALITY", nationality,
								""));
			}
			List<EppPersonFamily> fmList = new ArrayList<EppPersonFamily>();

			if (StringUtils.isNotEmpty(reg.getFatherFullname())) {
				EppPersonFamily fmy = new EppPersonFamily();
				eppPerson.setFatherName(reg.getFatherFullname());
				eppPerson.setFatherSearchName(HelperClass.removeAccent(reg
						.getFatherFullname()));
				fmy.setName(reg.getFatherFullname());
				fmy.setDateOfBirth(HelperClass
						.convertStringToString_ForPerson(HelperClass
								.convertDateToString2(reg
										.getFatherDateOfBirth())));
				fmy.setLost(reg.getFatherLost());
				fmy.setRelationship(Contants.RELATIONSHIP_TYPE_FATHER);
				fmy.setCreatedBy("SYSTEM");
				fmy.setCreateTs(new Date());
				fmList.add(fmy);
			}

			if (StringUtils.isNotEmpty(reg.getMotherFullname())) {
				EppPersonFamily fmy = new EppPersonFamily();
				eppPerson.setMotherName(reg.getMotherFullname());
				eppPerson.setMotherSearchName(HelperClass.removeAccent(reg
						.getMotherFullname()));
				fmy.setName(reg.getMotherFullname());
				fmy.setDateOfBirth(HelperClass
						.convertStringToString_ForPerson(HelperClass
								.convertDateToString2(reg
										.getMotherDateOfBirth())));
				fmy.setLost(reg.getMotherLost());
				fmy.setRelationship(Contants.RELATIONSHIP_TYPE_MOTHER);
				fmy.setCreatedBy("SYSTEM");
				fmy.setCreateTs(new Date());
				fmList.add(fmy);
			}

			if (StringUtils.isNotEmpty(reg.getSpouseFullname())) {
				EppPersonFamily fmy = new EppPersonFamily();
				fmy.setName(reg.getSpouseFullname());
				fmy.setDateOfBirth(HelperClass
						.convertStringToString_ForPerson(HelperClass
								.convertDateToString2(reg
										.getSpouseDateOfBirth())));
				fmy.setLost(reg.getSpouseLost());
				fmy.setRelationship(Contants.RELATIONSHIP_TYPE_SPOUSE);
				fmy.setCreatedBy("SYSTEM");
				fmy.setCreateTs(new Date());
				fmList.add(fmy);
			}

			// Lấy danh sách trẻ em *TODO:* Đang xem lại

			/*
			 * EppPersonAttchmnt eppAtt = new EppPersonAttchmnt();
			 * if(transaction.getNicTransactionAttachments() != null){
			 * for(NicTransactionAttachment doc :
			 * transaction.getNicTransactionAttachments()){
			 * if(doc.getDocType().equals("PH_CAP")){
			 * eppAtt.setFileName("PHOTO"); eppAtt.setSerialNo(1);
			 * eppAtt.setType_(doc.getDocType());
			 * eppAtt.setBase64(Base64.encodeBase64String(doc.getDocData()));
			 * break; } } }
			 */

			eppPerson.setCreatedBy("SYSTEM");
			eppPerson.setCreateTs(new Date());
			BaseModelSingle<Boolean> baseSaveEppPerson = eppPersonService
					.saveOrUpdateData(eppPerson);
			if (!baseSaveEppPerson.isError()
					|| baseSaveEppPerson.getMessage() != null) {
				return baseSaveEppPerson;
			}
			Boolean result = baseSaveEppPerson.getModel();
			// logger.warn("result when the save person === " + result);
			if (result) {
				BaseModelSingle<EppPerson> baseFindEppPerson = eppPersonService
						.findByStringCode(transaction.getPersonCode());
				if (!baseFindEppPerson.isError()
						|| baseFindEppPerson.getMessage() != null) {
					return new BaseModelSingle<Boolean>(false, false,
							baseFindEppPerson.getMessage());
				}
				EppPerson epp = baseFindEppPerson.getModel();
				if (fmList != null && fmList.size() > 0) {
					for (EppPersonFamily fl : fmList) {
						fl.setSubjectPerson(epp.getPersonId());
						BaseModelSingle<Boolean> baseSaveFamily = eppPersonService
								.saveOrUpdateDataFamily(fl);
						if (!baseSaveFamily.isError()
								|| baseSaveFamily.getMessage() != null) {
							return baseSaveFamily;
						}
					}
				}
				/*
				 * eppAtt.setPersonId(epp.getPersonId());
				 * eppPersonService.saveOrUpdateDataAttchmnt(eppAtt);
				 */
			}
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error save person when the upload transaction, transactionId: "
					+ upload.getTransactionId());
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException.createMessageException(e)
							+ " - savePersonByUploadTransaction thất bại.");
		}
	}

	public void resetWorkflowCheckData(NicUploadJob nicUploadJob) {
		try {
			nicUploadJob.setIdentificationCheckStatus(null);
			// nicUploadJob.setBlacklistCheckStatus("");
			nicUploadJob.setCpdCheckStatus("");
			nicUploadJob.setInvestigationStatus(null);
			nicUploadJob.setInvestigationType("");
			// nicUploadJob.setAfisCheckStatus("");
			nicUploadJob.setJobCurrentStage("");
			// nicUploadJob.setAfisVerifyStatus("");
			nicUploadJob.setJobCurrentStatus(null);
			nicUploadJob.setJobStartTime(null);
			nicUploadJob.setJobEndTime(null);
			nicUploadJob.setWorkflowJobRunAgainCount(2);
			// nicUploadJob.setJobReprocessCount(0);
			uploadJobService.saveOrUpdate(nicUploadJob);
			// logger.info("Reset workflow check data, workflow id: " +
			// nicUploadJob.getWorkflowJobId());
		} catch (Throwable e) {
			logger.error("Error reset workflow check data, workflow id: "
					+ nicUploadJob.getWorkflowJobId());
		}
	}

	public static String removeAccent(String s) {

		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D")
				.replaceAll("đ", "d");
	}

	public Boolean checkConnectApi(String nameApi, String siteCode) {
		Boolean result = true;
		try {
			ConfigurationApi cfg = configurationApiService.findConfigApiByName(
					nameApi, null);
			if (cfg != null) {
				if (cfg.getCloseAll()) {
					result = false;
				} else if (StringUtils.isNotEmpty(cfg.getCloseMember())
						&& StringUtils.isNotEmpty(siteCode)) {
					if (cfg.getCloseMember().contains(siteCode))
						result = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void deletePerson(String transactionId) {
		try {
			NicTransaction nicTran = nicTransactionService.findTransactionById(
					transactionId).getModel();
			EppPerson person = eppPersonService
					.findPersonByPersonCode(nicTran != null ? nicTran
							.getPersonCode() : null);
			if (person != null) {
				List<EppPersonAttchmnt> attList = eppPersonService
						.findAllEppPersonAttchmnt1(null, person.getPersonId());
				if (attList != null) {
					for (EppPersonAttchmnt att : attList) {
						eppPersonService.deletePersonAttach(att);
					}
				}
				BaseModelList<EppPersonFamily> baseFindPerson = eppPersonService
						.findAllEppPersonFamily1(person.getPersonId());
				List<EppPersonFamily> fmList = baseFindPerson.getListModel();
				if (fmList != null) {
					for (EppPersonFamily fm : fmList) {
						eppPersonService.deletePersonFamily(fm);
					}
				}
				eppPersonService.deletePerson(person);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BaseModelSingle<Boolean> saveDocumentInfoNew(String transactionId,
			String doi, String doe, String ppNo) {
		try {
			String documentNumber = "";
			// String printedSite = "HANOI";
			String printedSite = "IN_A08_MB";
			// String doi = "";
			// String doe = "";
			String dateFormat = com.nec.asia.nic.comp.trans.service.PersoService.DATE_FORMAT_FOR_PERSO;
			documentNumber = ppNo;
			/*
			 * final String DOC_NUM_START_TAG =
			 * "<DataField Name=\"DocumentNumber\">"; final String
			 * DOC_NUM_END_TAG = "</DataField>"; final String VALUE_START_TAG =
			 * "<Value InputFormat=\"Text\">"; final String VALUE_END_TAG =
			 * "</Value>";
			 * 
			 * final String DOI_START_TAG = "<DataField Name=\"DateOfIssue\">";
			 * //YYYYMMDD final String DOE_START_TAG =
			 * "<DataField Name=\"DateOfExpiry\">"; //YYYYMMDD
			 * 
			 * int docNumTagStart = StringUtils.indexOf(persoXml,
			 * DOC_NUM_START_TAG); if (docNumTagStart > 0) { int valueTagStart =
			 * StringUtils.indexOf(persoXml, VALUE_START_TAG , docNumTagStart);
			 * int valueTagEnd = StringUtils.indexOf(persoXml, VALUE_END_TAG ,
			 * docNumTagStart);
			 * 
			 * documentNumber = StringUtils.substring(persoXml,
			 * valueTagStart+VALUE_START_TAG.length(), valueTagEnd); }
			 * 
			 * int doiTagStart = StringUtils.indexOf(persoXml, DOI_START_TAG);
			 * if (doiTagStart > 0) { int valueTagStart =
			 * StringUtils.indexOf(persoXml, VALUE_START_TAG , doiTagStart); int
			 * valueTagEnd = StringUtils.indexOf(persoXml, VALUE_END_TAG ,
			 * doiTagStart);
			 * 
			 * doi = StringUtils.substring(persoXml,
			 * valueTagStart+VALUE_START_TAG.length(), valueTagEnd); }
			 * 
			 * int doeTagStart = StringUtils.indexOf(persoXml, DOE_START_TAG);
			 * if (doeTagStart > 0) { int valueTagStart =
			 * StringUtils.indexOf(persoXml, VALUE_START_TAG , doeTagStart); int
			 * valueTagEnd = StringUtils.indexOf(persoXml, VALUE_END_TAG ,
			 * doeTagStart);
			 * 
			 * doe = StringUtils.substring(persoXml,
			 * valueTagStart+VALUE_START_TAG.length(), valueTagEnd); }
			 */

			// logger.info("[saveDocumentInfo]transactionId:{} documentNumber: {}, doi: {}, doe: {}",
			// new Object[] { transactionId, documentNumber, doi, doe} );
			String OFFICER_ID = "SYSTEM";
			String WKSTN_ID = "SYSTEM";
			String status = NicTransactionService.TRANSACTION_STATUS_PERSONALIZED;
			Date newDate = new Date();
			Date doiDate = DateUtil.strToDate(doi, dateFormat);
			Date doeDate = DateUtil.strToDate(doe, dateFormat);

			NicDocumentDataId id = new NicDocumentDataId(transactionId,
					documentNumber);
			NicDocumentData documentData = documentDataDao.findById(id);
			if (documentData == null) {
				documentData = new NicDocumentData();
				documentData.setId(id);
				documentData.setCreateBy(OFFICER_ID);
				documentData.setCreateDatetime(newDate);
				documentData.setCreateWkstnId(WKSTN_ID);
			}

			documentData.setStatus(status);
			documentData.setStatusUpdateTime(newDate);
			// documentData.setChipSerialNo();
			documentData.setPrintingSite(printedSite);
			documentData.setDateOfIssue(doiDate);
			documentData.setDateOfExpiry(doeDate);

			documentData.setUpdateBy(OFFICER_ID);
			documentData.setUpdateDatetime(newDate);
			documentData.setUpdateWkstnId(WKSTN_ID);

			// TRUNG TẠM ĐÓNG ĐỂ CHO SYSTEM PERSO XỬ LÝ SINH MÃ GÓI
			// String packageId = "PKG."+DateUtil.parseDate(new Date(),
			// DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
			String dispatchId = "DSP."
					+ DateUtil.parseDate(new Date(),
							DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
			// documentData.setPackageId(packageId);
			// documentData.setPackageDatetime(newDate);
			documentData.setDispatchId(dispatchId);
			documentData.setDispatchDatetime(newDate);

			documentDataDao.saveOrUpdateDocumentData(documentData);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(false, false, e.getMessage()
					+ " - saveDocumentInfoNew - " + transactionId
					+ " - thất bại.");
		}
	}

	// check exist and save Exception - single
	private ResponseBase checkExistAndSaveException(BaseModelSingle base,
			EppWsLog eWL, ResponseBase responseBase, Object object) {
		responseBase.setCode(Contants.CODE_THROW_EXCEPTION);
		responseBase.setMessage(Contants.MESSAGE_EXCEPTION);

		// try {
		// eWL.setDataRequest(new ObjectMapper().writeValueAsString(object));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// logger.error(e.getMessage() + " - convert object to json fail.");
		// e.printStackTrace();
		// }
		eWL.setDataResponse(responseBase.toString());
		eWL.setMessageErrorLog(base.getMessage());
		eWL.setCreatedDate(new Date());
		BaseModelSingle<Boolean> baseCheck = eppWsLogService
				.inserDataTable(eWL);
		if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
			logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
		}
		return responseBase;
	}

	// check exist and save Exception - list
	private ResponseBase checkExistAndSaveExceptions(BaseModelList base,
			EppWsLog eWL, ResponseBase responseBase, Object object) {
		responseBase.setCode(Contants.CODE_THROW_EXCEPTION);
		responseBase.setMessage(Contants.MESSAGE_EXCEPTION);

		// try {
		// eWL.setDataRequest(new ObjectMapper().writeValueAsString(object));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// logger.error(e.getMessage() + " - convert object to json fail.");
		// e.printStackTrace();
		// }
		eWL.setDataResponse(responseBase.toString());
		eWL.setMessageErrorLog(base.getMessage());
		eWL.setCreatedDate(new Date());
		BaseModelSingle<Boolean> baseCheck = eppWsLogService
				.inserDataTable(eWL);
		if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
			logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
		}
		return responseBase;
	}

	// save or update rptData
	private void saveOrUpRptData(String type, int count, String siteCode) {
		try {
			RptStatisticsTransmitData rptData = rptService
					.findSingerByConditions(type, siteCode, DateUtil.strToDate(
							HelperClass.convertDateType3(new Date()),
							DateUtil.FORMAT_YYYYMMDD));
			if (rptData != null) {
				rptData.setTotal(rptData.getTotal() + count);
			} else {
				rptData = new RptStatisticsTransmitData();
				rptData.setRptDate(DateUtil.strToDate(
						HelperClass.convertDateType3(new Date()),
						DateUtil.FORMAT_YYYYMMDD));
				rptData.setTotal(count);
				rptData.setType(type);
				rptData.setSiteCode(siteCode);
			}
			rptData.setUpdateDatetime(new Date());
			rptService.saveOrUpdateData(rptData);
		} catch (Exception e) {
			throw e;
		}
	}

	// find ArchiveCode by EPP_TRANSACTION.ARCHIVECODE
	private BaseModelSingle<EppArchiveCode> findArchive(String archiveCode) {
		boolean check = false;
		String docType = archiveCode.substring(0, 2);
		int nYear = Integer.parseInt(archiveCode.substring(2, 4));
		String officeCode = null;
		int incNo = 0;
		try {
			incNo = Integer.parseInt(archiveCode.substring(6,
					archiveCode.length()));
			officeCode = archiveCode.substring(4, 6);
			check = true;
		} catch (Exception e) {

		}
		if (!check) {
			try {
				incNo = Integer.parseInt(archiveCode.substring(7,
						archiveCode.length()));
				officeCode = archiveCode.substring(4, 7);
			} catch (Exception e) {
				return new BaseModelSingle<EppArchiveCode>(null, false,
						"ArchiveCode - Sai định dạng dữ liệu.");
			}
		}
		BaseModelSingle<EppArchiveCode> baseFindArc = archiveCodeService
				.findArchiveCodeByAll(docType, nYear, officeCode, incNo);
		return baseFindArc;
	}

	// create new ArchiveCode
	private EppArchiveCode createArcCode(String archiveCode) {
		EppArchiveCode arc = new EppArchiveCode();
		boolean check = false;
		arc.setDocType(archiveCode.substring(0, 2));
		arc.setnYear(Integer.parseInt(archiveCode.substring(2, 4)));
		String officeCode = null;
		int incNo = 0;
		try {
			incNo = Integer.parseInt(archiveCode.substring(6,
					archiveCode.length()));
			officeCode = archiveCode.substring(4, 6);
			check = true;
		} catch (Exception e) {

		}
		if (!check) {
			try {
				incNo = Integer.parseInt(archiveCode.substring(7,
						archiveCode.length()));
				officeCode = archiveCode.substring(4, 7);
			} catch (Exception e) {
				return null;
			}
		}
		arc.setOfficeCode(officeCode);
		arc.setIncNo(incNo);
		arc.setCount(1);
		arc.setClosed("N");
		return arc;
	}

	// create Address
	private String createResidentAddress(NicRegistrationData reg)
			throws Exception {
		String address = reg.getAddressLine1();
		String px = "";
		String tp = "";
		if (StringUtils.isNotEmpty(reg.getAddressLine4())) {
			try {
				px = codesService.getCodeValueDescByIdName("TOWN_VILLAGE",
						reg.getAddressLine4(), "");
			} catch (Throwable e) {
				throw new Exception(e);
			}

			try {
				tp = codesService.getCodeValueDescByIdName("DISTRICT",
						reg.getAddressLine5(), "");
			} catch (Exception e) {
				throw new Exception(e);
			}
		}
		if (StringUtils.isNotEmpty(px)) {
			address += ", " + px;
		}
		if (StringUtils.isNotEmpty(tp)) {
			address += ", " + tp;
		}
		return address;
	}

	// getSiteName
	private String getSiteName(String placeOfIssueCode) {
		try {
			SiteRepository site = siteService.getSiteRepoById(placeOfIssueCode);
			String siteId = "DSQ_" + placeOfIssueCode;
			if (site == null) {
				site = siteService.getSiteRepoById(siteId);
			}
			if (site != null) {
				placeOfIssueCode = site.getSiteName();
			}
		} catch (Exception e) {
		}
		return placeOfIssueCode;
	}

	/*
	 * Hủy hộ chiếu.
	 */
	private ResponseBase methodCancelPassport(
			PassportCancelInput passportCancel, ResponseBase rb)
			throws Exception {
		String siteCode = "";
		String officerId = "";
		String cancelledPassportNo = "";
		boolean check = true;
		try {
			if (passportCancel != null) {
				NicTransactionLost nicTranLost = null;
				if (StringUtils.isNotBlank(passportCancel.getPassportNo())) {
					officerId = passportCancel.getCreateBy();
					NicTransaction nicTran = null;
					NicDocumentData nicDocData = null;
					if (StringUtils.isNotBlank(passportCancel
							.getTransactionId())) {
						nicDocData = documentDataService.findDocDataById(
								passportCancel.getTransactionId(),
								passportCancel.getPassportNo());
						if (nicDocData != null) {
							if (nicDocData.getStatus().equals(
									Contants.DOC_STATUS_CANCELLED)) {
								cancelledPassportNo += passportCancel
										.getPassportNo() + "//";
								check = false;
							} else {
								nicTran = nicDocData.getNicTransaction();
								// Cập nhật trạng thái của Passport.
								if (StringUtils.isNotBlank(passportCancel
										.getIsCancelPassportStatus())) {
									if (passportCancel
											.getIsCancelPassportStatus()
											.equals("Y")) {
										nicDocData.setCancelBy(passportCancel
												.getCreateBy());
										nicDocData
												.setCancelDatetime(new Date());
										nicDocData
												.setCancelReason(passportCancel
														.getNote());
										nicDocData.setCancelType(passportCancel
												.getReason());
										nicDocData
												.setStatus(Contants.DOC_STATUS_CANCELLED);
										nicDocData
												.setStatusUpdateTime(new Date());
										BaseModelSingle<Void> baseSaveDocData = documentDataService
												.saveOrUpdateDocData(nicDocData);
										if (!baseSaveDocData.isError()
												|| baseSaveDocData.getMessage() != null) {
											throw new Exception(
													baseSaveDocData
															.getMessage());
										}
									}
								}
							}
						} else {
							rb.setCode(Contants.CODE_INPUT_FAILD);
							rb.setMessage("Hồ sơ hộ chiếu không tồn tại");
							return rb;
						}
					} else {
						nicTranLost = transactionLostService
								.findDocCanByPassportNo(passportCancel
										.getPassportNo());
						if (nicTranLost != null) {
							cancelledPassportNo += passportCancel
									.getPassportNo() + "//";
							check = false;
						}
					}

					if (check) {
						nicTranLost = new NicTransactionLost();
						nicTranLost.setPlaceOfBirthCode(passportCancel
								.getPlaceOfBirthCode());
						nicTranLost.setArchiveCode(passportCancel
								.getArchiveCode());
						nicTranLost.setCreatedBy(passportCancel.getCreateBy());
						nicTranLost.setCreatedDate(new Date());
						nicTranLost.setOfficeCode(passportCancel
								.getOfficeCode());
						if (StringUtils.isNotBlank(passportCancel
								.getDateOfRegister())) {
							nicTranLost.setDateOfRegister(DateUtil.strToDate(
									passportCancel.getDateOfRegister(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						nicTranLost.setDiplomaCode(passportCancel
								.getDiplomaCode());
						if (StringUtils.isNotBlank(passportCancel
								.getDateOfDiploma())) {
							nicTranLost.setDateOfDiploma(DateUtil.strToDate(
									passportCancel.getDateOfDiploma(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						nicTranLost.setOfficeOfDiplomaCode(passportCancel
								.getOfficeOfDiploma());
						nicTranLost.setReason(passportCancel.getReason());
						nicTranLost.setApproverName(passportCancel
								.getApproverName());
						nicTranLost.setApproverLevel(passportCancel
								.getApproverLevel());
						nicTranLost.setApproverPosition(passportCancel
								.getApproverPosition());
						nicTranLost.setNote(passportCancel.getNote());
						nicTranLost.setTransactionId(passportCancel
								.getTransactionId());
						nicTranLost.setPassportNo(passportCancel
								.getPassportNo());
						nicTranLost.setIsCancelPassportStatus(this
								.convertStrToBoo(passportCancel
										.getIsCancelPassportStatus()));
						nicTranLost.setIsSendNotification(this
								.convertStrToBoo(passportCancel
										.getIsSendNotification()));
						nicTranLost.setPlaceOfIssueCode(passportCancel
								.getPlaceOfIssueCode());
						nicTranLost.setName(passportCancel.getName());
						nicTranLost.setSearchName(HelperClass.removeAccent(
								passportCancel.getName()).toUpperCase());
						nicTranLost.setGender(passportCancel.getGender());
						nicTranLost.setDateOfBirth(passportCancel
								.getDateOfBirth());
						nicTranLost.setNationalityCode(passportCancel
								.getNationalityCode());
						nicTranLost
								.setDateOfDocIssue(StringUtils
										.isNotBlank(passportCancel
												.getDateOfDocIssue()) ? DateUtil
										.strToDate(passportCancel
												.getDateOfDocIssue(),
												DateUtil.FORMAT_YYYYMMDD)
										: null);
						nicTranLost
								.setDateOfDocExpiry(StringUtils
										.isNotBlank(passportCancel
												.getDateOfDocExpiry()) ? DateUtil
										.strToDate(passportCancel
												.getDateOfDocExpiry(),
												DateUtil.FORMAT_YYYYMMDD)
										: null);

						siteCode = passportCancel.getOfficeCode();

						// IsSendNotification = Y luu vao bang hang
						// doi
						// if
						// (passportCancel.getIsSendNotification()
						// .equals("Y")) {
						// this.addObjToQueueJob(
						// nicDocData.getNicTransaction()
						// .getTransactionId(),
						// Contants.TYPE_CP, siteCode, null,
						// Contants.URL_CANCEL_PASSPORT);
						// }
						nicTranLost.setIsCancelPhysical(this
								.convertStrToBoo(passportCancel
										.getIsCancelPhysical()));
						nicTranLost.setPrinter(false);
						if (nicTran != null) {
							nicTranLost.setPersonCode(nicTran.getPersonCode());
						}
						Long tranLostId = transactionLostService
								.saveTranLost(nicTranLost);

						if (!Long.toString(tranLostId).isEmpty()) {
							/* Lưu bảng thống kê truyền nhận */
							this.saveOrUpRptData(Contants.URL_CANCEL_PASSPORT,
									1, siteCode);
						}

						// Lưu hàng đợi đồng bộ sang hệ thống cũ
						this.addObjToQueueJob(String.valueOf(tranLostId),
								Contants.QUEUE_OBJ_TYPE_DOC_LOST,
								Contants.QUEUE_RECEIVER_A08_HH, null, null);

						if (nicDocData != null) {
							/* Save Passport Log */
							NicDocumentHistory docHis = new NicDocumentHistory();
							docHis.setPassportNo(nicTranLost.getPassportNo());
							docHis.setSiteCode(siteCode);
							docHis.setStatus(nicDocData.getStatus());
							docHis.setStatusUpdateBy(officerId);
							docHis.setStatusUpdateTime(new Date());
							BaseModelSingle<Boolean> baseSaveDocHis = documentHistoryService
									.saveDocumentHistory(docHis);
							if (!baseSaveDocHis.isError()
									|| baseSaveDocHis.getMessage() != null) {
								throw new Exception(baseSaveDocHis.getMessage());
							}
						}
					}
				} else {
					rb.setCode(Contants.CODE_INPUT_FAILD);
					rb.setMessage("số hộ chiếu" + Contants.MESSAGE_INPUT_NULL);
					return rb;
				}
				rb.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				rb.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				if (cancelledPassportNo.length() > 1) {
					rb.setCode(Contants.CODE_DATA_EXIST);
					rb.setMessage(cancelledPassportNo);
				}
			} else {
				rb.setCode(Contants.CODE_INPUT_FAILD);
				rb.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}
	/*
	 * public void createPersonInNic(InfoProcess info){ Long idPerson = null;
	 * Boolean createPerson = false; Long idOrginal = null; NicUploadJob upJob_
	 * = uploadJobService.getUploadJobByTransactionIdSinger(null,
	 * info.getTransactionId()); //NicUploadJob upjob =
	 * mapperNicUploadJob(upJob_, request.getNicUp());
	 * if(StringUtils.isNotEmpty(info.getTranId())){ //Nếu có kiểm tra PersonId
	 * đối tượng NicUploadJob uploadJob =
	 * uploadJobService.getUploadJobByTransactionIdSinger(null,
	 * info.getTranId()); if(uploadJob != null){ NicRegistrationData regis =
	 * nicRegistrationDataService.getNicDataById(uploadJob.getTransactionId());
	 * String fullname = removeAccent(regis.getSurnameLine1().toUpperCase());
	 * String gender = regis.getGender().equals("M") ? "MALE" : "FEMALE"; String
	 * patternJ = "dd-MMM-yyyy"; DateFormat df = new SimpleDateFormat(patternJ);
	 * String dob = regis.getPrintDob(); String pob = regis.getPlaceOfBirth();
	 * //Kiểm tra đối tượng có PersonId ko, Nếu không có tạo một PersonID cho
	 * đối tượng if(uploadJob.getOriginalyPersonId() == null){ EppPerson newPer
	 * = new EppPerson(); newPer.setDateOfBirth(dob); newPer.setCreateTs(new
	 * Date()); String issueDateNin = ""; if(regis.getDateNin() != null)
	 * issueDateNin= df.format(regis.getDateNin());
	 * newPer.setDateOfIdIssue(HelperClass
	 * .convertMonthWordToMonthNumber1(issueDateNin));
	 * newPer.setEthnic(regis.getNation()); newPer.setGender(gender);
	 * newPer.setIdNumber(uploadJob.getNicTransaction().getNin());
	 * newPer.setName(regis.getSurnameLine1());
	 * newPer.setNationalityId(regis.getNationality());
	 * newPer.setOccupation(regis.getJob());
	 * newPer.setOfficeInfo(regis.getAddressCompany());
	 * newPer.setPlaceOfBirthId(pob); newPer.setReligion(regis.getReligion());
	 * String qh = ""; String tt = "";
	 * if(StringUtils.isNotEmpty(regis.getAddressLine4())){ CodeValues codeV =
	 * codesService.getCodeValueByIdName(Codes.TOWN_VILLAGE,
	 * regis.getAddressLine4()); if(codeV != null){ qh = ", " +
	 * codeV.getCodeValueDesc(); } }
	 * if(StringUtils.isNotEmpty(regis.getAddressLine4())){ CodeValues codeV =
	 * codesService.getCodeValueByIdName(Codes.DISTRICT,
	 * regis.getAddressLine4()); if(codeV != null){ tt = ", " +
	 * codeV.getCodeValueDesc(); } } String address = regis.getAddressLine1() +
	 * qh + tt; newPer.setPlaceIfIdIssueId(regis.getAddressNin());
	 * newPer.setResidentAddress(address);
	 * newPer.setResidentAreaId(regis.getAddressLine5());
	 * newPer.setResidentPlaceId(regis.getAddressLine4());
	 * newPer.setSearchName(removeAccent
	 * (regis.getSurnameLine1().toUpperCase()));
	 * newPer.setTmpAddress(regis.getAddressTempStreet());
	 * newPer.setTmpPlaceId(regis.getAddressTempDistrict());
	 * newPer.setTmpAreaId(regis.getAddressTempCity()); newPer.setFpFlag("N");
	 * Collection<NicDocumentData> nicDocs; try { nicDocs =
	 * documentDataService.findByTransactionId(uploadJob.getTransactionId());
	 * if(nicDocs != null && nicDocs.size() > 0){ List<NicDocumentData> nicDocs_
	 * = new ArrayList<NicDocumentData>(nicDocs); NicDocumentData nicDoc =
	 * nicDocs_.get(0); newPer.setPassportNo(nicDoc.getId().getPassportNo()); }
	 * List<NicTransactionAttachment> docFp =
	 * nicTransactionAttachmentService.getNicTransactionAttachments(
	 * uploadJob.getTransactionId(), new String[]
	 * {NicTransactionAttachment.DOC_TYPE_FINGERPRINT}, null); if(docFp != null
	 * && docFp.size() > 0){ newPer.setFpFlag("Y"); }
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } eppPersonService.saveOrUpdateData(newPer);
	 * 
	 * List<EppPerson> lstP = eppPersonService.findAllEppPerson2(fullname,
	 * gender, dob, pob); if(lstP != null && lstP.size() > 0){ idPerson =
	 * lstP.get(0).getId();
	 * 
	 * //Cập nhật lại GlobalID EppPerson pGlobal = lstP.get(0);
	 * pGlobal.setGlobalId(idPerson);
	 * eppPersonService.saveOrUpdateData(pGlobal); }
	 * 
	 * try { List<NicTransactionAttachment> docList =
	 * nicTransactionAttachmentService .getNicTransactionAttachments(
	 * uploadJob.getTransactionId(), new String[] {
	 * NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
	 * NicTransactionAttachment.KEY_SCAN_DOCUMENT }, null); if(docList != null
	 * && docList.size() > 0){ for(NicTransactionAttachment doc : docList){
	 * //Kiểm tra EppPersonAttchmnt attchmnt = new EppPersonAttchmnt();
	 * attchmnt.setFileName(doc.getDocType()); attchmnt.setPersonId(idPerson);
	 * attchmnt.setSerialNo(Integer.parseInt(doc.getSerialNo()));
	 * attchmnt.setType_(doc.getDocType());
	 * attchmnt.setBase64(Base64.encodeBase64String(doc.getDocData()));
	 * eppPersonService.saveOrUpdateDataAttchmnt(attchmnt); } } } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * try { //Thong tin nguoi than //Thông tin bố
	 * if(StringUtils.isNotEmpty(regis.getFatherSurname())){ EppPersonFamily
	 * family = new EppPersonFamily(); family.setCreatedBy("SYSTEM");
	 * family.setCreateTs(new Date()); family.setName(regis.getFatherSurname());
	 * family.setGender("M"); family.setRelationship("1"); //Bố
	 * family.setSubjectPerson(idPerson);
	 * if(StringUtils.isNotEmpty(regis.getSfDob())){ if(regis.getFatherDob() !=
	 * null){ SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); String
	 * dobf = sdf.format(regis.getFatherDob()); family.setDateOfBirth(dobf); } }
	 * if(regis.getFatherLost() != null && regis.getFatherLost()){
	 * family.setLost("Y"); } family.setPlaceOfBirthCode("Không xác định");
	 * eppPersonService.saveOrUpdateDataFamily(family); } //Thông tin mẹ
	 * if(StringUtils.isNotEmpty(regis.getMotherSurname())){ EppPersonFamily
	 * family = new EppPersonFamily(); family.setCreatedBy("SYSTEM");
	 * family.setCreateTs(new Date()); family.setName(regis.getMotherSurname());
	 * family.setGender("F"); family.setRelationship("2"); //Mẹ
	 * family.setSubjectPerson(idPerson);
	 * if(StringUtils.isNotEmpty(regis.getSmDob())){ if(regis.getMotherDob() !=
	 * null){ SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); String
	 * dobm = sdf.format(regis.getMotherDob()); family.setDateOfBirth(dobm); } }
	 * if(regis.getMotherLost() != null && regis.getMotherLost()){
	 * family.setLost("Y"); } family.setPlaceOfBirthCode("Không xác định");
	 * eppPersonService.saveOrUpdateDataFamily(family); }
	 * 
	 * //Thông tin vợ/chồng
	 * if(StringUtils.isNotEmpty(regis.getSpouseSurname())){ EppPersonFamily
	 * family = new EppPersonFamily(); family.setCreatedBy("SYSTEM");
	 * family.setCreateTs(new Date()); family.setName(regis.getSpouseSurname());
	 * if(regis.getGender().equals("M")){ family.setGender("F");
	 * family.setRelationship("3"); //Vợ } else{ family.setGender("M");
	 * family.setRelationship("4"); //Chồng } family.setSubjectPerson(idPerson);
	 * if(StringUtils.isNotEmpty(regis.getSsDob())){ if(regis.getSpouseDob() !=
	 * null){ SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); String
	 * dobs = sdf.format(regis.getSpouseDob()); family.setDateOfBirth(dobs); } }
	 * family.setPlaceOfBirthCode("Không xác định");
	 * eppPersonService.saveOrUpdateDataFamily(family); }
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * uploadJob.setOriginalyPersonId(idPerson);
	 * uploadJobService.saveOrUpdate(uploadJob); } else { idPerson =
	 * uploadJob.getOriginalyPersonId(); }
	 * 
	 * //Kiểm tra person có tồn tại EppPerson person =
	 * eppPersonService.getPersonById(idPerson); if(person != null){
	 * NicRegistrationData regisMain =
	 * upJob_.getNicTransaction().getNicRegistrationData(); String fullnameMain
	 * = removeAccent(regisMain.getSurnameLine1().toUpperCase()); String
	 * genderMain = regisMain.getGender().equals("M") ? "MALE" : "FEMALE";
	 * String dobMain = regisMain.getPrintDob(); String pobMain =
	 * regisMain.getPlaceOfBirth();
	 * if(!fullnameMain.equals(person.getSearchName().toUpperCase()) ||
	 * !genderMain.equals(person.getGender()) ||
	 * !dobMain.equals(person.getDateOfBirth()) ||
	 * !pobMain.equals(person.getPlaceOfBirthId())){ idOrginal =
	 * uploadJob.getOriginalyPersonId(); idPerson = null; createPerson = true; }
	 * else{ idOrginal = null; idPerson = uploadJob.getOriginalyPersonId(); } }
	 * else createPerson = true; } else { createPerson = true; } } else {
	 * createPerson = true; }
	 * 
	 * if(createPerson){ NicRegistrationData regis =
	 * upJob_.getNicTransaction().getNicRegistrationData(); String fullname =
	 * removeAccent(regis.getSurnameLine1().toUpperCase()); String gender =
	 * regis.getGender().equals("M") ? "MALE" : "FEMALE"; String pattern =
	 * "dd-MMM-yyyy"; DateFormat df = new SimpleDateFormat(pattern); // Using
	 * DateFormat format method we can create a string // representation of a
	 * date with the defined format. String dob = regis.getPrintDob(); String
	 * pob = regis.getPlaceOfBirth(); EppPerson newPer = new EppPerson();
	 * newPer.setDateOfBirth(dob); newPer.setCreateTs(new Date()); String
	 * issueDateNin = ""; if(regis.getDateNin() != null) issueDateNin=
	 * df.format(regis.getDateNin());
	 * newPer.setDateOfIdIssue(HelperClass.convertMonthWordToMonthNumber1
	 * (issueDateNin)); newPer.setEthnic(regis.getNation());
	 * newPer.setGender(gender);
	 * newPer.setIdNumber(upJob_.getNicTransaction().getNin());
	 * newPer.setName(regis.getSurnameLine1());
	 * newPer.setNationalityId(regis.getNationality());
	 * newPer.setOccupation(regis.getJob());
	 * newPer.setOfficeInfo(regis.getAddressCompany());
	 * newPer.setPlaceOfBirthId(pob); newPer.setReligion(regis.getReligion());
	 * String qh = ""; String tt = "";
	 * if(StringUtils.isNotEmpty(regis.getAddressLine4())){ CodeValues codeV =
	 * codesService.getCodeValueByIdName(Codes.TOWN_VILLAGE,
	 * regis.getAddressLine4()); if(codeV != null){ qh = ", " +
	 * codeV.getCodeValueDesc(); } }
	 * if(StringUtils.isNotEmpty(regis.getAddressLine4())){ CodeValues codeV =
	 * codesService.getCodeValueByIdName(Codes.DISTRICT,
	 * regis.getAddressLine4()); if(codeV != null){ tt = ", " +
	 * codeV.getCodeValueDesc(); } } String address = regis.getAddressLine1() +
	 * qh + tt; newPer.setPlaceIfIdIssueId(regis.getAddressNin());
	 * newPer.setResidentAddress(address);
	 * newPer.setResidentAreaId(regis.getAddressLine5());
	 * newPer.setResidentPlaceId(regis.getAddressLine4());
	 * newPer.setSearchName(removeAccent
	 * (regis.getSurnameLine1().toUpperCase()));
	 * newPer.setTmpAddress(regis.getAddressTempStreet());
	 * newPer.setTmpPlaceId(regis.getAddressTempDistrict());
	 * newPer.setTmpAreaId(regis.getAddressTempCity()); newPer.setFpFlag("N");
	 * newPer.setOriginId(idOrginal); Collection<NicDocumentData> nicDocs; try {
	 * nicDocs =
	 * documentDataService.findByTransactionId(upJob_.getTransactionId());
	 * if(nicDocs != null && nicDocs.size() > 0){ List<NicDocumentData> nicDocs_
	 * = new ArrayList<NicDocumentData>(nicDocs); NicDocumentData nicDoc =
	 * nicDocs_.get(0); newPer.setPassportNo(nicDoc.getId().getPassportNo()); }
	 * List<NicTransactionAttachment> docFp =
	 * nicTransactionAttachmentService.getNicTransactionAttachments(
	 * upJob_.getTransactionId(), new String[]
	 * {NicTransactionAttachment.DOC_TYPE_FINGERPRINT}, null); if(docFp != null
	 * && docFp.size() > 0){ newPer.setFpFlag("Y"); }
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } eppPersonService.saveOrUpdateData(newPer);
	 * 
	 * //Đưa vào hàng đợi chờ đồng bộ sang các trung tâm xử lý List<SiteGroups>
	 * siteList = siteService.findAll(); for(SiteGroups sg : siteList){
	 * this.addObjToQueueJob(String.valueOf(newPer.getId()),
	 * OBJ_TYPE_QUEUE_CREATE_PERSON, sg.getGroupId(), null); } //---end---
	 * List<EppPerson> lstP = eppPersonService.findAllEppPerson2(fullname,
	 * gender, dob, pob); if(lstP != null && lstP.size() > 0){ idPerson =
	 * lstP.get(0).getId();
	 * 
	 * //Cập nhật lại GlobalID EppPerson pGlobal = lstP.get(0);
	 * pGlobal.setGlobalId(idPerson);
	 * eppPersonService.saveOrUpdateData(pGlobal); }
	 * 
	 * try { List<NicTransactionAttachment> docList =
	 * nicTransactionAttachmentService .getNicTransactionAttachments(
	 * upJob_.getTransactionId(), new String[] {
	 * NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
	 * NicTransactionAttachment.KEY_SCAN_DOCUMENT}, null); if(docList != null &&
	 * docList.size() > 0){ for(NicTransactionAttachment doc : docList){
	 * EppPersonAttchmnt attchmnt = new EppPersonAttchmnt();
	 * attchmnt.setFileName(doc.getDocType()); attchmnt.setPersonId(idPerson);
	 * attchmnt.setSerialNo(Integer.parseInt(doc.getSerialNo()));
	 * attchmnt.setType_(doc.getDocType());
	 * attchmnt.setBase64(Base64.encodeBase64String(doc.getDocData()));
	 * eppPersonService.saveOrUpdateDataAttchmnt(attchmnt); } }
	 * 
	 * //Thong tin nguoi than //Thông tin bố
	 * if(StringUtils.isNotEmpty(regis.getFatherSurname())){ EppPersonFamily
	 * family = new EppPersonFamily(); family.setCreatedBy("SYSTEM");
	 * family.setCreateTs(new Date()); family.setName(regis.getFatherSurname());
	 * family.setGender("M"); family.setRelationship("1"); //Bố
	 * family.setSubjectPerson(idPerson);
	 * if(StringUtils.isNotEmpty(regis.getSfDob())){ if(regis.getFatherDob() !=
	 * null){ SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); String
	 * dobf = sdf.format(regis.getFatherDob()); family.setDateOfBirth(dobf); } }
	 * if(regis.getFatherLost() != null && regis.getFatherLost()){
	 * family.setLost("Y"); } family.setPlaceOfBirthCode("Không xác định");
	 * eppPersonService.saveOrUpdateDataFamily(family); } //Thông tin mẹ
	 * if(StringUtils.isNotEmpty(regis.getMotherSurname())){ EppPersonFamily
	 * family = new EppPersonFamily(); family.setCreatedBy("SYSTEM");
	 * family.setCreateTs(new Date()); family.setName(regis.getMotherSurname());
	 * family.setGender("F"); family.setRelationship("2"); //Mẹ
	 * family.setSubjectPerson(idPerson);
	 * if(StringUtils.isNotEmpty(regis.getSmDob())){ if(regis.getMotherDob() !=
	 * null){ SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); String
	 * dobm = sdf.format(regis.getMotherDob()); family.setDateOfBirth(dobm); } }
	 * if(regis.getMotherLost() != null && regis.getMotherLost()){
	 * family.setLost("Y"); } family.setPlaceOfBirthCode("Không xác định");
	 * eppPersonService.saveOrUpdateDataFamily(family); }
	 * 
	 * //Thông tin vợ/chồng
	 * if(StringUtils.isNotEmpty(regis.getSpouseSurname())){ EppPersonFamily
	 * family = new EppPersonFamily(); family.setCreatedBy("SYSTEM");
	 * family.setCreateTs(new Date()); family.setName(regis.getSpouseSurname());
	 * if(regis.getGender().equals("M")){ family.setGender("F");
	 * family.setRelationship("3"); //Vợ } else{ family.setGender("M");
	 * family.setRelationship("4"); //Chồng } family.setSubjectPerson(idPerson);
	 * if(StringUtils.isNotEmpty(regis.getSsDob())){ if(regis.getSpouseDob() !=
	 * null){ SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); String
	 * dobs = sdf.format(regis.getSpouseDob()); family.setDateOfBirth(dobs); } }
	 * family.setPlaceOfBirthCode("Không xác định");
	 * eppPersonService.saveOrUpdateDataFamily(family); }
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } }
	 */
}
