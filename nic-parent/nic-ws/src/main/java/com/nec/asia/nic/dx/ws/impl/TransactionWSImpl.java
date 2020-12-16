package com.nec.asia.nic.dx.ws.impl;

import java.io.File;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.job.dto.InvestigationListInfoTargetDto;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.dao.NicSearchResultDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionPaymentDao;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.FamilyData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.trans.dto.NicHitTransaction;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPaymentDetaiService;
//import com.nec.asia.nic.comp.trans.service.CitizenRefService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.RejectionDataService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
//import com.nec.asia.nic.comp.trans.service.exception.CitizenRefServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.utils.TransDTOMapper;
import com.nec.asia.nic.dx.trans.BufEppDataPerson;
import com.nec.asia.nic.dx.trans.BufEppListRequest;
import com.nec.asia.nic.dx.trans.BufEppListResponse;
import com.nec.asia.nic.dx.trans.BufEppPerson;
import com.nec.asia.nic.dx.trans.BufEppPersonDoc;
import com.nec.asia.nic.dx.trans.BufEppPersonInvestigation;
import com.nec.asia.nic.dx.trans.BufEppRequest;
import com.nec.asia.nic.dx.trans.EditDataRegistration;
import com.nec.asia.nic.dx.trans.IssuanceData;
//import com.nec.asia.nic.dx.trans.CitizenRef;
//import com.nec.asia.nic.dx.trans.CitizenRefRetrievalFilter;
//import com.nec.asia.nic.dx.trans.CitizenRefRetrievalResult;
//import com.nec.asia.nic.dx.trans.LegacyNicInquiry;
//import com.nec.asia.nic.dx.trans.LegacyNicInquiryResponse;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.trans.NicPersoInfoDownloadFilter;
import com.nec.asia.nic.dx.trans.NicPersoInfoDownloadResult;
import com.nec.asia.nic.dx.trans.NicPersoInfoRef;
import com.nec.asia.nic.dx.trans.PaymentDetail;
import com.nec.asia.nic.dx.trans.TransactionLog;
import com.nec.asia.nic.dx.trans.TransactionLogUpload;
import com.nec.asia.nic.dx.trans.TransactionLogUploadResponse;
//import com.nec.asia.nic.dx.trans.TransactionLogUpload;
//import com.nec.asia.nic.dx.trans.TransactionLogUploadResponse;
import com.nec.asia.nic.dx.trans.TransactionRetrievalDataType;
import com.nec.asia.nic.dx.trans.TransactionRetrievalFilter;
import com.nec.asia.nic.dx.trans.TransactionRetrievalRecordType;
import com.nec.asia.nic.dx.trans.TransactionRetrievalResult;
import com.nec.asia.nic.dx.trans.UpdateReceivedNicPersoInfo;
import com.nec.asia.nic.dx.ws.Constant;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.StatusCode;
import com.nec.asia.nic.dx.ws.TransactionWS;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.util.MiscXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;

/* 
 * Modification History:
 * 
 * 19 Feb 2016 (chris): add debug for WS
 * 20 Feb 2016 (chris): add method retrieveTransactionStatus(), retrievePassportStatus()
 * 23 May 2017 (chris): add method downloadNicPersoInfo()
 */
public class TransactionWSImpl implements TransactionWS {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String PARAM_SCOPE_SYSTEM = "SYSTEM";
	private static final String PARAM_NAME_WS_DEBUG_FLAG = "WS_DEBUG_FLAG";
	private static final String PARAM_NAME_WS_DEBUG_OUTDIR = "WS_DEBUG_OUTDIR";
	private boolean wsDebugEnabled = false;
	private String  wsDebugOutDir = null;
	
	@Autowired
	private NicTransactionService nicTransactionService = null;
	
	@Autowired
	private NicRegistrationDataService nicRegistrationDataService = null;
	
	@Autowired
	private ParametersService parametersService = null;
	
	@Autowired
	private DocumentDataService documentDataService = null;
	
	@Autowired
	private RejectionDataService rejectionDataService = null;
	
	@Autowired
	private CodesService codesService = null;
	
	@Autowired
	private ListHandoverService listHandoverService = null;
	
	@Autowired
	private NicTransactionPackageService nicTransactionPackageService = null;
	
	@Autowired
	private NicTransactionPaymentDetaiService nicTransactionPaymentDetaiService = null;
	
	@Autowired
	private NicTransactionPaymentDao transactionPaymentDao = null;
	
	@Autowired
	private NicUploadJobService uploadJobService = null;
	
	@Autowired
	private TransDTOMapper mapper = null;
	
	@Autowired
	private NicSearchResultDao nicSearchResultDao = null;
	
	@Autowired
	private NicSearchHitResultService nicSearchHitResultService = null; 
	
	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService = null;
	
	@Autowired
	private BufPersonInvestigationService bufPersonInvestigationService = null;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RptStatisticsTransmitDataService rptStatisticsTransmitDataService = null;
	
	@Autowired
	private EppPersonService eppPersonService = null;
	
	@Autowired
	private SiteService siteService = null;
	
	public void initParameters() {
		try {
			Parameters wsDebugFlagParameters = parametersService.getParamDetails(PARAM_SCOPE_SYSTEM, PARAM_NAME_WS_DEBUG_FLAG);
			Parameters wsDebugOutDirParameters = parametersService.getParamDetails(PARAM_SCOPE_SYSTEM, PARAM_NAME_WS_DEBUG_OUTDIR);
			if (wsDebugFlagParameters!=null && wsDebugFlagParameters.getParaValue()!=null
					&& wsDebugOutDirParameters!=null && wsDebugOutDirParameters.getParaValue()!=null) {
				if (StringUtils.isNotBlank(wsDebugOutDirParameters.getParaValue().toString())) {
					wsDebugOutDir = wsDebugOutDirParameters.getParaValue().toString();
					if (StringUtils.equalsIgnoreCase("true", wsDebugFlagParameters.getParaValue().toString())) {
						wsDebugEnabled = true;
					}
				}
			}
			logger.info("TransactionWSImpl() set wsDebugEnabled: {}, wsDebugOutDir: {}", wsDebugEnabled, wsDebugOutDir );
		} catch (Exception e) {
			logger.error("Failed to init ws debug mode." , e);
			wsDebugOutDir = "";
			wsDebugEnabled = false;
		}
	}
	
	@Override
	public TransactionRetrievalResult retrieveTransaction(
			TransactionRetrievalFilter retrievalFilter) throws FaultException {
		if (wsDebugOutDir==null)	initParameters();
		logger.info("TransactionWSImpl.retrieveTransaction(): begin" );
		TransactionRetrievalResult result = new TransactionRetrievalResult();
		try {
			//need to retrieve separately
			boolean wantTransactionLog = false;
			//need to exclude from result.
			boolean wantRegistrationData = false;
			boolean wantTransactionDoc = false;
			boolean wantIssuanceData = false;
			boolean wantPayment = false;
			
			boolean wantLatestOnly = false;
			
			String nin = retrievalFilter.getNIN();
			String transactionId = retrievalFilter.getTransactionID();
			String passportNo = retrievalFilter.getPassportNo();
			
			//new fields with wildcard search
			String surname = retrievalFilter.getSurname();
			String firstName = retrievalFilter.getFirstName();
			String middleName = retrievalFilter.getMiddleName();
			String gender = retrievalFilter.getGender();
			String dob = null; 
			if (retrievalFilter.getBirthDate()!=null) {
				dob = DateUtil.parseDate(retrievalFilter.getBirthDate(), DateUtil.FORMAT_DDdashMMdashYYYY);
			} else if (StringUtils.isNotEmpty(retrievalFilter.getBirthDateApprox())) {
				dob = retrievalFilter.getBirthDateApprox();
			}

			boolean isSearchTxnByFields = StringUtils.isNotBlank(surname) 
					|| StringUtils.isNotBlank(firstName)
					|| StringUtils.isNotBlank(middleName)
					|| StringUtils.isNotBlank(gender)
					|| StringUtils.isNotBlank(dob);
			
//			if (!isSearchTxnByFields && StringUtils.isBlank(transactionId) && StringUtils.isBlank(nin) && StringUtils.isBlank(ccn)) {
//				throw new Exception("transaction id / nin / ccn cannot be empty");
//			}
			for (TransactionRetrievalDataType type : retrievalFilter.getDataType()) {
				switch (type) {
					case ALL:
						wantTransactionLog = true;
						wantRegistrationData = true;
						wantTransactionDoc = true;
						wantIssuanceData = true;
						wantPayment = true;
						break;
					case REGISTRATION_DATA:
						wantRegistrationData = true;
						//wantTransactionDoc = true;
						break;
					case TRANSACTION_DOC:
						wantTransactionDoc = true;
						break;
					case ISSUANCE_DATA:
						wantIssuanceData = true;
						break;
					case PAYMENT:
						wantPayment = true;
						break;
					case TRANSACTION_LOG:
						wantTransactionLog = true;
						break;
					default:
				}
			}
			if (TransactionRetrievalRecordType.LATEST.equals(retrievalFilter.getRecordType())) {
				wantLatestOnly = true;
			}
			
			if (isSearchTxnByFields || StringUtils.isNotBlank(transactionId) || StringUtils.isNotBlank(passportNo) || StringUtils.isNotBlank(nin)) {
				logger.info("TransactionWSImpl.retrieveTransaction() by transactionId: {}, nin: {}, wantRegistrationData: {}, wantIssuanceData: {}, wantPayment: {}, wantTransactionLog:{} ", new Object[]{ transactionId, nin, wantRegistrationData, wantIssuanceData, wantPayment, wantTransactionLog});
				if (isSearchTxnByFields) {
					logger.info("TransactionWSImpl.retrieveTransaction() by surname:{}, firstName:{}, gender:{}", new Object[]{ surname, firstName, gender});
				}
			} else {
				throw new Exception("transactionId / passportNo / nin cannot be null.");
			}
			List<MainTransaction> transactionList = null;
			if (isSearchTxnByFields) {
				List<NicTransaction> resultList = null; 
				resultList = nicTransactionService.findAllByFields(surname, firstName, middleName, gender, dob, wantIssuanceData, wantTransactionDoc, wantRegistrationData, wantPayment);
				if (CollectionUtils.isNotEmpty(resultList)) {
					transactionList = new ArrayList<MainTransaction>();
					int count = 0;
					for (NicTransaction t : resultList) {
						MainTransaction transactionDTO = mapper.parseMainTransactionDTO(t);
						transactionList.add(transactionDTO);
						if (++count >= Constant.MAX_RESULT_SIZE && wantTransactionDoc) {
							logger.info("TransactionWSImpl.retrieveTransaction() resultset size reach max: {} ", count);
							break;
						}
					}
				}
			} else {
				transactionList = nicTransactionService.findAllTransactionHistory(transactionId, passportNo, nin, wantRegistrationData, wantTransactionDoc, wantIssuanceData, wantPayment, wantTransactionLog, wantLatestOnly);
			}
			
			if (CollectionUtils.isNotEmpty(transactionList))
				result.getTransactions().addAll(transactionList);
			logger.info("retrieveTransaction() result.transactions.size={}", new Object[] { result.getTransactions().size() } );
		} catch (Exception e) {
			logger.error("Exception Encountered in TransactionWSImpl.retrieveTransaction()", e);
			throw new FaultException("Exception encountered in retrieveTransaction():"+e.getMessage(), e);
		}
		if (wsDebugEnabled) {
			try {
				String input = MiscXmlConvertor.parseObjectToXml(retrievalFilter);
				String output = MiscXmlConvertor.parseObjectToXml(result);
				
				String dateString = DateUtil.parseDate(new Date(), DateUtil.FORMAT_YYYYMMDD);
				String outDirPath = wsDebugOutDir + File.separator + "retrieveTransaction" + File.separator + dateString;
				File outDir = new File(outDirPath);
				logger.debug("[outDir] {}, exists:{}, isDirectory:{}, absolutePath:{} " , new Object[] {outDir, outDir.exists(), outDir.isDirectory(), outDir.getAbsolutePath()});
				if (!outDir.exists()) {
					outDir.mkdirs();
					logger.debug("[outDir] {} mkdirs(). " , new Object[] {outDir});
				}
				String id = ""+System.currentTimeMillis();
				FileUtils.writeStringToFile(new File(outDir, id+"-input.xml"), input, "UTF-8");
				FileUtils.writeStringToFile(new File(outDir, id+"-output.xml"), output, "UTF-8");
				logger.debug("[input] length:{}, [output] length:{} " , new Object[] {input.length() , output.length()});
			} catch (Exception e) {
				logger.error("Failed to write payload xml." , e);
			}
		}
		logger.info("TransactionWSImpl.retrieveTransaction(): end");
		return result;
	}

	@Override
	public String uploadTransaction(MainTransaction mainTransaction) throws FaultException {
		if (wsDebugOutDir==null)	initParameters();
		logger.info("TransactionWSImpl.uploadTransaction(): begin" );
		String result = ""; //Constant.TRANSACTION_SUCCESS;
		//Chia loại upload để trả mã lỗi
		//TypeUpload = 1: Handover / 2:Package
		Boolean isAdd = true;
		if(StringUtils.isNotEmpty(mainTransaction.getPackageID())){
			/*if(mainTransaction.getTypeUpload() == null){
				result = "Fail: is not null typeUpload";
			}
			else{
				if(mainTransaction.getTypeUpload() == 1){
					//Kiểm tra dữ liệu danh sách bàn giao
					List<NicListHandover> listH = listHandoverService.findAllByPackageId(mainTransaction.getPackageID());
					if(listH == null || listH.size() <= 0){
						try{
							NicListHandover newH = new NicListHandover();
							newH.setCreateBy(mainTransaction.getCreateByhA());
							newH.setCreateDate(new Date());
							newH.setDescription("Tạo mới qua đồng bộ dữ liệu");
							newH.setPackageName("Danh sách đồng bộ " + mainTransaction.getRegSiteCode() + " ngày "+ new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
							newH.setPackageId(mainTransaction.getPackageID());
							newH.setTransactionId(mainTransaction.getTransactionID());
							newH.setTypeList(7); //Dành cho danh sách đồng bộ từ Địa phương
							newH.setCountTransaction(mainTransaction.getCountTrans());
							newH.setSiteCode(mainTransaction.getRegSiteCode());
							listHandoverService.createRecordHandover(newH);
							result = Constant.TRANSACTION_SUCCESS;
							logger.info("Create handover success ==== " + mainTransaction.getPackageID());
						}
						catch (Exception e) {
							result =  Constant.TRANSACTION_FAILED + " - Create handover - ex: " + e.getMessage();
						}
					}
					else
					{
						try{
							NicListHandover updateH = listH.get(0);
							if(!updateH.getTransactionId().contains(mainTransaction.getTransactionID()))
							{
								updateH.setUpdateBy(mainTransaction.getCreateByhA());
								updateH.setUpdateDate(new Date());
								String text = updateH.getTransactionId();
								updateH.setTransactionId(text + "," + mainTransaction.getTransactionID());
								int num = updateH.getCountTransaction();
								updateH.setCountTransaction(num + 1);
								listHandoverService.createRecordHandover(updateH);
								result = Constant.TRANSACTION_SUCCESS;
								logger.info("Update handover success ==== " + mainTransaction.getPackageID());
							}
						}
						catch (Exception e) {
							result =  Constant.TRANSACTION_FAILED + " - Update handover - ex: " + e.getMessage();
						}
					}
					NicUploadJob nicUp = uploadJobService.getUploadJobByTransactionIdSinger(null, mainTransaction.getTransactionID());
					if(nicUp != null){
						//Cập nhật job xử lý check lại CPD/AFIS 
						nicUp.setIdentificationCheckStatus("");
						nicUp.setBlacklistCheckStatus("");
						nicUp.setCpdCheckStatus("");
						nicUp.setInvestigationStatus("");
						nicUp.setInvestigationType("");
						nicUp.setAfisCheckStatus("");
						nicUp.setJobCurrentStage("");
						nicUp.setAfisVerifyStatus("");
						nicUp.setJobCurrentStatus("");
						nicUp.setJobStartTime(null);
						nicUp.setJobEndTime(null);
						uploadJobService.saveOrUpdate(nicUp);
						logger.info("Reset workflow check data ==== ");
					}
				}
				
				else if(mainTransaction.getTypeUpload() == 2){
					if(isAdd){
						try{
							//Thêm vào bảng package trung gian
							NicTransactionPackage tran = new NicTransactionPackage();
							tran.setPackageId(mainTransaction.getPackageID());
							tran.setTransactionId(mainTransaction.getTransactionID());
							tran.setTypeList(7);
							nicTransactionPackageService.insertDataTable(tran);
							result = Constant.TRANSACTION_SUCCESS;
							
							try{
								NicTransaction nicTran = nicTransactionService.getNicTransactionById(mainTransaction.getTransactionID());
								if(nicTran != null){
									nicTran.setpackageID(mainTransaction.getPackageID());
									if(StringUtils.isNotEmpty(mainTransaction.getPassportStyle())){
										if(mainTransaction.getPassportStyle().equals("Y"))
											nicTran.setPassportStyle(true);
										else
											nicTran.setPassportStyle(false);
									}
									else
										nicTran.setPassportStyle(false);
				
									if(StringUtils.isNotEmpty(mainTransaction.getRegistrationData().getMotherLost())){
										if(mainTransaction.getRegistrationData().getMotherLost().equals("Y"))
											nicTran.getNicRegistrationData().setMotherLost(true);
										else
											nicTran.getNicRegistrationData().setMotherLost(false);
									}
									else
										nicTran.getNicRegistrationData().setMotherLost(false);
									
									if(StringUtils.isNotEmpty(mainTransaction.getRegistrationData().getFatherLost())){
										if(mainTransaction.getRegistrationData().getFatherLost().equals("Y"))
											nicTran.getNicRegistrationData().setFatherLost(true);
										else
											nicTran.getNicRegistrationData().setFatherLost(false);
									}
									else
										nicTran.getNicRegistrationData().setFatherLost(false);
									nicTransactionService.saveOrUpdate(nicTran);
								}
							
								List<PaymentDetail> list = mainTransaction.getRegistrationData().getPaymentInfo().getPaymentDetail();//nicTransactionPaymentDetaiService.findListDetailPaymentList(paymentId);
								if(list != null && list.size() > 0){
									for(PaymentDetail detail : list){
										nicTransactionPaymentDetaiService.saveOrUpdatePaymentDetail(mapper.parseTransactionPaymentDetailDataDTO(detail));
									}
								}
							}
							catch(Exception e){
								logger.error("[{}]ListPaymentDetail: " + e.getMessage(), mainTransaction.getTransactionID());
							}
						}
						catch (Exception e) {
							result = Constant.TRANSACTION_FAILED + " - Update handover - ex: " + e.getMessage();
						}
					}
				}
				else
				{
					result = Constant.TRANSACTION_FAILED + " - Wrong type upload: " + mainTransaction.getTypeUpload();
				}
			}*/
			
			int response = listHandoverService.createHandoverPackage(mainTransaction);
			switch (response) {
			case 1:
				result = Constant.TRANSACTION_SUCCESS;
				break;
			case -1:
				result = Constant.TRANSACTION_FAILED + " - Create Handover";
				break;
			case -2:
				result = Constant.TRANSACTION_FAILED + " - Update Handover";
				break;
			case -3:
				result = Constant.TRANSACTION_FAILED + " - Create Transaction Package";
				break;
			case -4:
				result = Constant.TRANSACTION_FAILED + " - Update Transaction";
				break;
			case -5:
				result = Constant.TRANSACTION_FAILED + " - Create List Payment Detail";
				break;
			default:
				break;
			}
		}
		else{
			try{
				nicTransactionService.saveMainTransaction(mainTransaction);
				result = Constant.TRANSACTION_SUCCESS;
			}
			catch (TransactionServiceException e) {
				logger.error("Exception Encountered in TransactionWSImpl.uploadTransaction()["+e.getErrorCode()+"]:"+e.getMessage());
				if (StringUtils.isNotBlank(e.getErrorCode())) {
					result = Constant.TRANSACTION_FAILED + "- transaction error" + e.getErrorCode()+":"+e.getMessage();
				} else {
					result = Constant.TRANSACTION_FAILED +  "- transaction error" + ":"+e.getMessage();
				}
				logger.info("uploadTransaction(): transactionId:{} ,result:{}", mainTransaction!=null?mainTransaction.getTransactionID():"", result);
				//throw new FaultException("Exception encountered in uploadTransaction:"+e.getMessage(), e);
			}
		}
		
		if (wsDebugEnabled) {
			try {
				String input = MiscXmlConvertor.parseObjectToXml(mainTransaction);
				String output = MiscXmlConvertor.parseObjectToXml(result);
				
				String dateString = DateUtil.parseDate(new Date(), DateUtil.FORMAT_YYYYMMDD);
				String outDirPath = wsDebugOutDir + File.separator + "uploadTransaction" + File.separator + dateString;
				File outDir = new File(outDirPath);
				logger.debug("[outDir] {}, exists:{}, isDirectory:{}, absolutePath:{} " , new Object[] {outDir, outDir.exists(), outDir.isDirectory(), outDir.getAbsolutePath()});
				if (!outDir.exists()) {
					outDir.mkdirs();
					logger.debug("[outDir] {} mkdirs(). " , new Object[] {outDir});
				}
				String id = ""+System.currentTimeMillis()+"-"+mainTransaction.getTransactionID();
				FileUtils.writeStringToFile(new File(outDir, id+"-input.xml"), input, "UTF-8");
				FileUtils.writeStringToFile(new File(outDir, id+"-output.xml"), output, "UTF-8");
				logger.debug("[input] length:{}, [output] length:{} " , new Object[] {input.length() , output.length()});
			} catch (Exception e) {
				logger.error("Failed to write payload xml." , e);
			}
		}
		logger.info("TransactionWSImpl.uploadTransaction(): end");
		return result;
	}
	
	@Override
	public String retrieveTransactionStatus(String transactionId) throws FaultException {
		String transactionStatus = null;
		logger.info("TransactionWSImpl.retrieveTransactionStatus(): begin" );
		if (StringUtils.isBlank(transactionId))
			throw new FaultException(StatusCode.TRANWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: TransactionID]", StatusCode.TRANWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
		try {
			logger.info("retrieveTransactionStatus(): transactionId:{}", transactionId);
			NicTransaction nicTransaction = nicTransactionService.findById(transactionId);
			if (nicTransaction==null)
				throw new FaultException(StatusCode.TRANWS_DATA_NOT_FOUND.getDesc(), StatusCode.TRANWS_DATA_NOT_FOUND.convertToFaultDetail());
			TransactionStatus transactionStatusInstance = TransactionStatus.getInstance(nicTransaction.getTransactionStatus());
			if (transactionStatusInstance!=null)
				transactionStatus = transactionStatusInstance.getName();
			else 
				transactionStatus = nicTransaction.getTransactionStatus();
			
			transactionStatus = codesService.getCodeValueDescByIdName(Codes.TRANSACTION_STATUS, nicTransaction.getTransactionStatus(), transactionStatus);
		} catch (Exception ex) {
			throw new FaultException(StatusCode.TRANWS_UNEXPECTED_ERROR.getDesc() + "[Exception: " + ex.getMessage() + "]", StatusCode.TRANWS_UNEXPECTED_ERROR.convertToFaultDetail());
		}
		logger.info("TransactionWSImpl.retrieveTransactionStatus(): transactionId:{} ,transactionStatus:{}", transactionId, transactionStatus);
		logger.info("TransactionWSImpl.retrieveTransactionStatus(): end" );
		return transactionStatus;
	}

	/*@Override
	public BufEppDataPerson downloadBufEppPerson(String transactionId) throws FaultException {
		//Tìm hồ sơ khớp
		BufEppDataPerson result = new BufEppDataPerson();
		result.setResultCheck("N");
		try {
			if(StringUtils.isNotEmpty(transactionId)){
				//Kiểm tra kết quả trả về AFIS và CPD đã có chưa
				List<NicUploadJob> listUpload = uploadJobService.findAllByTransactionId(transactionId);
				if(listUpload != null && listUpload.size() > 0){
					NicUploadJob uploadJob = listUpload.get(0);
					if(uploadJob.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_INITIAL)){
						result.setResultCheck("Y");
						
						//Danh sách BMS / CPD nghi trùng
						Long idCPD = null;
						Long idBMS = null;
						
						List<String> listTranCPD = new ArrayList<String>();
						List<NicSearchResult> searchResults = nicSearchResultDao.findAllByJobId(uploadJob.getWorkflowJobId());
						if(searchResults != null && searchResults.size() > 0){
							for(NicSearchResult sR_ : searchResults){
								if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__CPD) && sR_.getHasHit()){
									idCPD = sR_.getSearchResultId();
								}
								else if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__FP_1_N) && sR_.getHasHit() != null && sR_.getHasHit()){
									idBMS = sR_.getSearchResultId();
								}
							}
						}
						
						if(idCPD == null && idBMS == null){
							logger.info("==== Null data check AFIS");
						}
						else{
							//Dữ liệu kiểm tra với CPD
							if(idCPD != null){
								//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
								List<NicSearchHitResult> listHitCPD = nicSearchHitResultService.findHitPositions(null, idCPD);
								if(listHitCPD != null && listHitCPD.size() > 0){
									for(NicSearchHitResult sHR : listHitCPD){
										if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_CPD)){
											//Lấy Id của đối tượng trùng
											String transactionId_CPD = sHR.getTransactionIdHit();
											BufEppPerson bufPeson = new BufEppPerson();
											if(StringUtils.isNotEmpty(transactionId_CPD)){
												//Lấy dữ liệu thông tin hồ sơ của transaction
												NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId_CPD);
												if(nicTran != null){
													if(!nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){//TH chưa phát hành
														
													}
													else{
														NicRegistrationData regOrg = nicTran.getNicRegistrationData();
														if(regOrg != null){
															bufPeson.setAddressResident(regOrg.getAddressLine1());
															bufPeson.setCreateBy("SYSTEM");
															bufPeson.setCreateDateTime(new Date());
															bufPeson.setCreateWkstnID("SYSTEM-WKST");
															bufPeson.setDob(regOrg.getDateOfBirth());
															bufPeson.setFullname(regOrg.getSurnameLine1());
															bufPeson.setGender(regOrg.getGender());
															bufPeson.setIssueDateNin(regOrg.getDateNin());
															bufPeson.setIssueDatePassport(regOrg.getDatePassport());
															bufPeson.setIssuePlaceNin(regOrg.getAddressNin());
															bufPeson.setNation(regOrg.getNation());
															bufPeson.setNin(nicTran.getNin());
															bufPeson.setNote("");
															
															//Thông tin hộ chiếu
															Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(transactionId_CPD);
															if(nicDocs != null && nicDocs.size() > 0){
																List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
																NicDocumentData nicDoc = nicDocs_.get(0);
																bufPeson.setPassportNo(nicDoc.getId().getPassportNo());
																bufPeson.setPassportStatus(nicDoc.getStatus()); //ISSUANCE: phát hành - NONE: tạm khóa
																
															}
															bufPeson.setPhone(regOrg.getContactNo());
															bufPeson.setPob(regOrg.getPlaceOfBirth());
															bufPeson.setReligion(regOrg.getReligion());
															bufPeson.setTransacionId(transactionId_CPD);
															bufPeson.setTransacionMasterId(transactionId);
															
															//Thông tin ng thân
															FamilyData family = new FamilyData();
															
															String fatherName = "";
															if(StringUtils.isNotEmpty(regOrg.getFatherFirstname()) && !regOrg.getFatherFirstname().equals("NULL")){
																fatherName = regOrg.getFatherFirstname();
															}
															if(StringUtils.isNotEmpty(regOrg.getFatherMiddlename()) && !regOrg.getFatherMiddlename().equals("NULL")){
																fatherName += " " + regOrg.getFatherMiddlename();
															}
															
															if(StringUtils.isNotEmpty(regOrg.getFatherSurname()) && !regOrg.getFatherSurname().equals("NULL")){
																fatherName += " " + regOrg.getFatherSurname();
															}
															
															String motherName = "";
															if(StringUtils.isNotEmpty(regOrg.getMotherFirstname()) && !regOrg.getMotherFirstname().equals("NULL")){
																motherName = regOrg.getMotherFirstname();
															}
															if(StringUtils.isNotEmpty(regOrg.getMotherMiddlename()) && !regOrg.getMotherMiddlename().equals("NULL")){
																motherName += " " + regOrg.getMotherMiddlename();
															}
															
															if(StringUtils.isNotEmpty(regOrg.getMotherSurname()) && !regOrg.getMotherSurname().equals("NULL")){
																motherName += " " + regOrg.getMotherSurname();
															}
															
															String spouseName = "";
															if(StringUtils.isNotEmpty(regOrg.getSpouseFirstname()) && !regOrg.getSpouseFirstname().equals("NULL")){
																spouseName = regOrg.getSpouseFirstname();
															}
															if(StringUtils.isNotEmpty(regOrg.getSpouseMiddlename()) && !regOrg.getSpouseMiddlename().equals("NULL")){
																spouseName += " " + regOrg.getSpouseMiddlename();
															}
															
															if(StringUtils.isNotEmpty(regOrg.getSpouseSurname()) && !regOrg.getSpouseSurname().equals("NULL")){
																spouseName += " " + regOrg.getSpouseSurname();
															}
															
															family.setFatherName(fatherName.trim());
															family.setMotherName(motherName.trim());
															family.setSpouseName(spouseName.trim());
															
															if(regOrg.getFatherDob() != null){
																family.setFatherDob(regOrg.getFatherDob());
															}
															if(regOrg.getMotherDob() != null){
																family.setMotherDob(regOrg.getMotherDob());
															}
															if(regOrg.getSpouseDob() != null){
																family.setSpouseDob(regOrg.getSpouseDob());
															}
															
															JAXBContext jaxbContext = JAXBContext.newInstance(FamilyData.class);
															Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
															StringWriter sw = new StringWriter();
															jaxbMarshaller.marshal(family, sw);
															String xmlFamily = sw.toString();
															
															bufPeson.setFamilyData(xmlFamily);
														}
													//}
												}
												
												//Lấy dữ liệu ảnh/tài liệu đính kèm/vân tay
												List<BufEppPersonDoc> bufDocList = new ArrayList<BufEppPersonDoc>();
												List<NicTransactionAttachment> docList = nicTransactionAttachmentService
														.findNicTransactionAttachments(
																transactionId_CPD, null, null);
												if(docList != null && docList.size() > 0){
													for(NicTransactionAttachment doc : docList){
														BufEppPersonDoc bufDoc = new BufEppPersonDoc();
														bufDoc.setCreateBy("SYSTEM");
														bufDoc.setCreateDateTime(new Date());
														bufDoc.setCreateWkstnID("SYSTEM-WKST");
														bufDoc.setDocData(doc.getDocData());
														bufDoc.setDocType(doc.getDocType());
														bufDoc.setSerial(doc.getSerialNo());
														bufDoc.setTransacionId(transactionId_CPD);
														bufDocList.add(bufDoc);
													}
												}
												
												BufEppPersonInvestigation bufInves = new BufEppPersonInvestigation();
												com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation bufInvesDomain = bufPersonInvestigationService.findByTranId(transactionId_CPD);
												if(bufInvesDomain != null){
													bufInves.setDataHistoryPassport(bufInvesDomain.getDataHistoryPassport());
													bufInves.setCreateBy(bufInvesDomain.getCreateBy());
													bufInves.setCreateDateTime(new Date());
													bufInves.setDataImmihistory(bufInvesDomain.getDataImmihistory());
													bufInves.setTransacionId(transactionId_CPD);
												}
												
												if(bufDocList != null && bufDocList.size() > 0){
													for(BufEppPersonDoc doc : bufDocList){
														bufPeson.getBufEppPersonDoc().add(doc);
													}	
												}
												
												bufPeson.setBufEppPersonInvestigation(bufInves);
												result.getBufEppPerson().add(bufPeson);
												//listTranCPD.add(transactionId_CPD);
											}
										}
									}
								}
							}
							
							logger.info("CHECKING WITH BMS=================");
							///Dữ liệu kiểm tra với BMS
							if(idBMS != null){
								//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
								List<NicSearchHitResult> listHitBMS = nicSearchHitResultService.findHitPositions(null, idBMS);
								if(listHitBMS != null && listHitBMS.size() > 0){
									for(NicSearchHitResult sHR : listHitBMS){
										if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_BMS)){
											//Lấy Id của đối tượng trùng
											String transactionId_BMS = sHR.getTransactionIdHit();
											if(StringUtils.isNotEmpty(transactionId_BMS)){
												//Kiểm tra trong ListCheckCPD có trùng transactionID không. Nếu có thì chỉ thêm dữ liệu BMS vào vị trí đối tượng
												if(listTranCPD.contains(transactionId_BMS) && result.getBufEppPerson() != null && result.getBufEppPerson().size() > 0){
													
												}
												else{
														BufEppPerson bufPeson = new BufEppPerson();
														
														int score = 0;
														//Tính số điểm trung bình cho vân tay
														//Kiểm tra chuỗi dữ liệu
														if(StringUtils.isNotEmpty(sHR.getHitInfo())){
															String[] listHit = sHR.getHitInfo().split(","); //Tách chuỗi theo dấu ","
															if(listHit.length > 0){
																for(int i = 0; i < listHit.length; i++){
																   if(Double.parseDouble((listHit[i].split("=")[1])) > score){
																	   double d = Double.parseDouble((listHit[i].split("=")[1]));
																   	   score = (int) d;
																   }
																}
															}
															
															bufPeson.setScoreBMS("" + score);
															bufPeson.setDetailScoreBMS(sHR.getHitInfo());
														}
														
														//Lấy dữ liệu thông tin hồ sơ của transaction
														NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId_BMS);
														if(nicTran != null){
															NicRegistrationData regOrg = nicTran.getNicRegistrationData();
															if(regOrg != null){
																bufPeson.setAddressResident(regOrg.getAddressLine1());
																bufPeson.setCreateBy("SYSTEM");
																bufPeson.setCreateDateTime(new Date());
																bufPeson.setCreateWkstnID("SYSTEM-WKST");
																bufPeson.setDob(regOrg.getDateOfBirth());
																bufPeson.setFullname(regOrg.getSurnameLine1());
																bufPeson.setGender(regOrg.getGender());
																bufPeson.setIssueDateNin(regOrg.getDateNin());
																bufPeson.setIssueDatePassport(regOrg.getDatePassport());
																bufPeson.setIssuePlaceNin(regOrg.getAddressNin());
																bufPeson.setNation(regOrg.getNation());
																bufPeson.setNin(nicTran.getNin());
																bufPeson.setNote("");
																
																//Thông tin hộ chiếu
																Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(transactionId_BMS);
																if(nicDocs != null && nicDocs.size() > 0){
																	List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
																	NicDocumentData nicDoc = nicDocs_.get(0);
																	bufPeson.setPassportNo(nicDoc.getId().getPassportNo());
																	bufPeson.setPassportStatus(nicDoc.getStatus()); //ISSUANCE: phát hành - NONE: tạm khóa
																	
																}
																bufPeson.setPhone(regOrg.getContactNo());
																bufPeson.setPob(regOrg.getPlaceOfBirth());
																bufPeson.setReligion(regOrg.getReligion());
																bufPeson.setTransacionId(transactionId_BMS);
																bufPeson.setTransacionMasterId(transactionId);
																
																//Thông tin ng thân
																FamilyData family = new FamilyData();
																
																String fatherName = "";
																if(StringUtils.isNotEmpty(regOrg.getFatherFirstname()) && !regOrg.getFatherFirstname().equals("NULL")){
																	fatherName = regOrg.getFatherFirstname();
																}
																if(StringUtils.isNotEmpty(regOrg.getFatherMiddlename()) && !regOrg.getFatherMiddlename().equals("NULL")){
																	fatherName += " " + regOrg.getFatherMiddlename();
																}
																
																if(StringUtils.isNotEmpty(regOrg.getFatherSurname()) && !regOrg.getFatherSurname().equals("NULL")){
																	fatherName += " " + regOrg.getFatherSurname();
																}
																
																String motherName = "";
																if(StringUtils.isNotEmpty(regOrg.getMotherFirstname()) && !regOrg.getMotherFirstname().equals("NULL")){
																	motherName = regOrg.getMotherFirstname();
																}
																if(StringUtils.isNotEmpty(regOrg.getMotherMiddlename()) && !regOrg.getMotherMiddlename().equals("NULL")){
																	motherName += " " + regOrg.getMotherMiddlename();
																}
																
																if(StringUtils.isNotEmpty(regOrg.getMotherSurname()) && !regOrg.getMotherSurname().equals("NULL")){
																	motherName += " " + regOrg.getMotherSurname();
																}
																
																String spouseName = "";
																if(StringUtils.isNotEmpty(regOrg.getSpouseFirstname()) && !regOrg.getSpouseFirstname().equals("NULL")){
																	spouseName = regOrg.getSpouseFirstname();
																}
																if(StringUtils.isNotEmpty(regOrg.getSpouseMiddlename()) && !regOrg.getSpouseMiddlename().equals("NULL")){
																	spouseName += " " + regOrg.getSpouseMiddlename();
																}
																
																if(StringUtils.isNotEmpty(regOrg.getSpouseSurname()) && !regOrg.getSpouseSurname().equals("NULL")){
																	spouseName += " " + regOrg.getSpouseSurname();
																}
																
																family.setFatherName(fatherName.trim());
																family.setMotherName(motherName.trim());
																family.setSpouseName(spouseName.trim());
																
																if(regOrg.getFatherDob() != null){
																	family.setFatherDob(regOrg.getFatherDob());
																}
																if(regOrg.getMotherDob() != null){
																	family.setMotherDob(regOrg.getMotherDob());
																}
																if(regOrg.getSpouseDob() != null){
																	family.setSpouseDob(regOrg.getSpouseDob());
																}
																
																JAXBContext jaxbContext = JAXBContext.newInstance(FamilyData.class);
																Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
																StringWriter sw = new StringWriter();
																jaxbMarshaller.marshal(family, sw);
																String xmlFamily = sw.toString();
																
																bufPeson.setFamilyData(xmlFamily);
															}
																//}
															//}
															
															//Lấy dữ liệu ảnh/tài liệu đính kèm/vân tay
															List<BufEppPersonDoc> bufDocList = new ArrayList<BufEppPersonDoc>();
															List<NicTransactionAttachment> docList = nicTransactionAttachmentService
																	.findNicTransactionAttachments(
																			transactionId_BMS, null, null);
															if(docList != null && docList.size() > 0){
																for(NicTransactionAttachment doc : docList){
																	BufEppPersonDoc bufDoc = new BufEppPersonDoc();
																	bufDoc.setCreateBy("SYSTEM");
																	bufDoc.setCreateDateTime(new Date());
																	bufDoc.setCreateWkstnID("SYSTEM-WKST");
																	bufDoc.setDocData(doc.getDocData());
																	bufDoc.setDocType(doc.getDocType());
																	bufDoc.setSerial(doc.getSerialNo());
																	bufDoc.setTransacionId(transactionId_BMS);
																	bufDocList.add(bufDoc);
																}
															}
															
															BufEppPersonInvestigation bufInves = new BufEppPersonInvestigation();
															com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation bufInvesDomain = bufPersonInvestigationService.findByTranId(transactionId_BMS);
															if(bufInvesDomain != null){
																bufInves.setDataHistoryPassport(bufInvesDomain.getDataHistoryPassport());
																bufInves.setCreateBy(bufInvesDomain.getCreateBy());
																bufInves.setCreateDateTime(new Date());
																bufInves.setDataImmihistory(bufInvesDomain.getDataImmihistory());
																bufInves.setTransacionId(transactionId_BMS);
															}
															
															if(bufDocList != null && bufDocList.size() > 0){
																for(BufEppPersonDoc doc : bufDocList){
																	bufPeson.getBufEppPersonDoc().add(doc);
																}	
															}
															
															bufPeson.setBufEppPersonInvestigation(bufInves);
															result.getBufEppPerson().add(bufPeson);
															
														}
														else
														{
															logger.info("Not found information Transaction in database TransactionId: " + transactionId_BMS);
														}
													}
												}	
											}
										}
									}
								}
								else
								{
									logger.info("Not found list hit BMS by idBMS: " + idBMS);
								}
							}
					}
					else{
						logger.info("Not found result BMS/CPD");
					}
					
					if(StringUtils.isNotEmpty(uploadJob.getOriginalyId())){
						//Lấy dữ liệu hồ sơ khớp
						BufEppPerson bufPeson = new BufEppPerson();
						//Tìm dữ liệu registration Data của orginalId
						try{
							NicRegistrationData regOrg = uploadJob.getNicTransaction().getNicRegistrationData();
							if(regOrg != null){
								bufPeson.setAddressResident(regOrg.getAddressLine1());
								bufPeson.setCreateBy("SYSTEM");
								bufPeson.setCreateDateTime(new Date());
								bufPeson.setCreateWkstnID("SYSTEM");
								bufPeson.setDob(regOrg.getDateOfBirth());
								bufPeson.setFullname(regOrg.getSurnameLine1());
								bufPeson.setGender(regOrg.getGender());
								bufPeson.setIssueDateNin(regOrg.getDateNin());
								bufPeson.setIssueDatePassport(regOrg.getDatePassport());
								bufPeson.setIssuePlaceNin(regOrg.getAddressNin());
								bufPeson.setNation(regOrg.getNation());
								bufPeson.setNin(uploadJob.getNicTransaction().getNin());
								bufPeson.setNote("");
							}
						}
						catch(Exception e){
							logger.info("RegistrationData-Error: " + e.getMessage());
						}
					}
				}
				else
				{
					logger.info("Not found transactionId" );
				}
			}
		} catch (Exception e) {
			logger.info("Message: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}*/
	
	@Override
	public BufEppDataPerson downloadBufEppPerson(BufEppRequest request) throws FaultException {
		//Tìm hồ sơ khớp
		BufEppDataPerson result = new BufEppDataPerson();
		result.setResultCheck("N");
		
		Boolean resultCPD = false;
		try {
			if(StringUtils.isNotEmpty(request.getTransactionId()) && (request.getTransCPD() != null || request.getTransBMS() != null)
					&& request.getTransactionMasterId() != null){
				
				List<NicUploadJob> listUpload = uploadJobService.findAllByTransactionId(request.getTransactionMasterId());
				NicUploadJob uploadJob = listUpload.get(0);
				List<NicUploadJob> listUploadOb = uploadJobService.findAllByTransactionId(request.getTransactionId());	
				//EppPerson person = null;
				NicUploadJob uploadJobOb = listUploadOb.get(0);
				//List<EppPerson> lstPer = eppPersonService.findByGlobalId(uploadJobOb.getOriginalyPersonId());
				//if(lstPer != null && lstPer.size() > 0){
					//person = lstPer.get(0);
				//}
					result.setResultCheck("Y");
					BufEppPerson bufPeson = new BufEppPerson();
					if(request.getTransCPD() != null){
						//if(person != null){
							//bufPeson.setGlobalId(person.getGlobalId() != null ? person.getGlobalId().toString() :"");
							//bufPeson.setOriginId(person.getOriginId() != null ? person.getOriginId().toString() :"");
						//}
						logger.info("Info - TrasacitonID: " + request.getTransactionId() + " || idCPD: " + request.getTransCPD() + " || tranMaster: " + request.getTransactionMasterId());
							resultCPD = true;
							//Lấy dữ liệu thông tin hồ sơ của transaction
							NicTransaction nicTran = nicTransactionService.getNicTransactionById(request.getTransactionId()).getModel();
							String sfDob = "";
							String smDob = "";
							String ssDob = "";
							if(nicTran != null){
								NicRegistrationData regOrg = nicTran
										.getNicRegistrationData();
								if (regOrg != null) {
									sfDob = regOrg.getFatherDefDateOfBirth();
									smDob = regOrg.getMotherDefDateOfBirth();
									ssDob = regOrg.getSpouseDefDateOfBirth();
									String address = regOrg.getAddressLine1();
									String px = "";
									String tp = "";
									if(StringUtils.isNotEmpty(regOrg.getAddressLine4())){
										try {
											px =  codesService.getCodeValueDescByIdName("TOWN_VILLAGE", regOrg.getAddressLine4(), "");
										} catch (Exception e) {
											// TODO: handle exception
										}
										
										try {
											tp = codesService.getCodeValueDescByIdName("DISTRICT", regOrg.getAddressLine5(), "");
										} catch (Exception e) {
											// TODO: handle exception
										}
									}
									if(StringUtils.isNotEmpty(px)){
										address += ", " + px;
									}
									if(StringUtils.isNotEmpty(tp)){
										address += ", " + tp;
									}
									bufPeson.setAddressResident(address);
									bufPeson.setCreateBy("SYSTEM");
									bufPeson.setCreateDateTime(new Date());
									bufPeson.setCreateWkstnID("SYSTEM-WKST");
									bufPeson.setDob(regOrg.getDateOfBirth());
									bufPeson.setFullname(regOrg.getSurnameLine1());
									bufPeson.setGender(regOrg.getGender());
									bufPeson.setIssueDateNin(regOrg.getDateNin());
									/*bufPeson.setIssueDatePassport(regOrg
											.getDatePassport())*/;
									bufPeson.setIssuePlaceNin(regOrg.getAddressNin());
									bufPeson.setNation(regOrg.getNation());
									bufPeson.setNin(nicTran.getNin());
									bufPeson.setNote("");
									
									bufPeson.setStyleDob(regOrg.getDefDateOfBirth());
									
									String desPob = regOrg.getPlaceOfBirth();
									if(StringUtils.isNotEmpty(regOrg.getPlaceOfBirth())){
										CodeValues codeV = codesService.getCodeValueByIdName("CODE_BirthPlace", regOrg.getPlaceOfBirth());
										if(codeV != null){
											desPob = codeV.getCodeValueDesc();
										}
									}
									bufPeson.setPobDes(desPob);
									bufPeson.setHandoverA(nicTran.getRecieptNo());
									bufPeson.setInfoPerson(nicTran.getInfoPerson());
									NicListHandover listH = listHandoverService.findHandoverByCriteria(nicTran.getPackageId(), NicListHandover.TYPE_LIST_A, null).getModel();
									if(listH != null){
										try{
											Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
											Date dateS = new java.sql.Date(listH.getCreateDate().getTime());
											String s = formatter.format(dateS).split(" ")[0];
											bufPeson.setCreateDateHandover(s);
										}catch(Exception e){ }
										
										try{
											if(StringUtils.isNotEmpty(listH.getCreateBy())){
												Users user = userService.findByUserId(listH.getCreateBy());
												if(user != null){
													bufPeson.setCreateByHandover(user.getUserName());
												}
												else{
													bufPeson.setCreateByHandover(listH.getCreateBy());
												}
											}
										}catch(Exception e){ }
									}
									
									if(StringUtils.isNotEmpty(regOrg.getJob())){
										CodeValues codeV = codesService.getCodeValueByIdName("CODE_JOB", regOrg.getJob());
										if(codeV != null){
											bufPeson.setJobs(codeV.getCodeValueDesc());
										} else {
											bufPeson.setJobs(regOrg.getJob());
										}
									}
									// Thông tin hộ chiếu
									Collection<NicDocumentData> nicDocs = documentDataService
											.findByTransactionId(request.getTransactionId()).getModel();
									if (nicDocs != null && nicDocs.size() > 0) {
										List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(
												nicDocs);
										NicDocumentData nicDoc = nicDocs_.get(0);
										bufPeson.setPassportNo(nicDoc.getId()
												.getPassportNo());
										bufPeson.setPassportStatus(nicDoc.getStatus()); // ISSUANCE: phát hành - NONE: tạm khóa
										if(nicDoc.getStatus() != null && nicDoc.getStatus().equals("ISSUANCE"))
											bufPeson.setIssueFlag("Y");
										else
											bufPeson.setIssueFlag("N");
										
										Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
										if(nicDoc.getDateOfIssue() != null){
											Date dateS = new java.sql.Date(nicDoc.getDateOfIssue().getTime());
											String s = formatter.format(dateS).split(" ")[0];
											bufPeson.setIssuePassport(s);
										}
										
										if(nicDoc.getDateOfExpiry() != null){
											Date dateS = new java.sql.Date(nicDoc.getDateOfExpiry().getTime());
											String s = formatter.format(dateS).split(" ")[0];
											bufPeson.setExpiredPassport(s);
										}
										
										if(nicDoc.getCreateDatetime() != null){
											Date dateS = new java.sql.Date(nicDoc.getCreateDatetime().getTime());
											String t = formatter.format(dateS).split(" ")[0];
											bufPeson.setPrintDateC(t);
										}
										
										if(nicDoc.getPackageDatetime() != null){
											Date dateS = new java.sql.Date(nicDoc.getPackageDatetime().getTime());
											String t = formatter.format(dateS).split(" ")[0];
											bufPeson.setCreateDateC(t);
										}
										
										SiteRepository site = siteService.getSiteRepoById(nicTran.getIssSiteCode());
										if(site != null)
											bufPeson.setIssuePlacePassport(site.getSiteName());
										
										if(nicTran.getPassportType().equals("PO"))
											bufPeson.setPassportType("CÔNG VỤ");
										else if(nicTran.getPassportType().equals("PD"))
											bufPeson.setPassportType("NGOẠI GIAO");
										else
											bufPeson.setPassportType("PHỔ THÔNG");
										bufPeson.setSerial(nicDoc.getChipSerialNo());
										bufPeson.setIcaoLineOne(nicDoc.getIcaoLine1());
										bufPeson.setIcaoLineTwo(nicDoc.getIcaoLine2());
										bufPeson.setHandoverC(nicDoc.getPackageId());
									}
									bufPeson.setPhone(regOrg.getContactNo());
									bufPeson.setReligion(regOrg.getReligion());
									bufPeson.setTransacionId(request.getTransactionId());
									

									bufPeson.setFatherLost("N");
									bufPeson.setMotherLost("N");
									bufPeson.setFatherLost(regOrg.getFatherLost());
									bufPeson.setMotherLost(regOrg.getMotherLost());
									
									// Thông tin ng thân
									FamilyData family = new FamilyData();
		
									String fatherName = regOrg.getFatherFullname(); 
									String motherName = regOrg.getMotherFullname();
									String spouseName = regOrg.getSpouseFullname();
									
									family.setFatherName(fatherName.trim());
									family.setMotherName(motherName.trim());
									family.setSpouseName(spouseName.trim());
		
									if (regOrg.getFatherDateOfBirth() != null) {
										family.setFatherDob(regOrg.getFatherDateOfBirth());
									}
									if (regOrg.getMotherDateOfBirth() != null) {
										family.setMotherDob(regOrg.getMotherDateOfBirth());
									}
									if (regOrg.getSpouseDateOfBirth() != null) {
										family.setSpouseDob(regOrg.getSpouseDateOfBirth());
									}
		
									JAXBContext jaxbContext = JAXBContext
											.newInstance(FamilyData.class);
									Marshaller jaxbMarshaller = jaxbContext
											.createMarshaller();
									StringWriter sw = new StringWriter();
									jaxbMarshaller.marshal(family, sw);
									String xmlFamily = sw.toString();
		
									bufPeson.setFamilyData(xmlFamily);
								}
							}
							
							List<NicTransactionPackage> lstPack = nicTransactionPackageService.getPackageNameByTransactionId(request.getTransactionId());
							if(lstPack != null && lstPack.size() > 0){
								for(NicTransactionPackage pack : lstPack){
									if(pack.getTypeList() == 8 && pack.getStage().equals("D")){
										NicListHandover han = listHandoverService.findByPackageId(new NicListHandoverId(pack.getPackageId(), null)).getModel();						
										if(han != null){
											Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
											if (han.getCreateDate() != null){
												Date dateS = new java.sql.Date(han.getCreateDate().getTime());
												String s = formatter.format(dateS);
												bufPeson.setApproveDateB(s.split(" ")[0]);
											}
											if (han.getUpdateDate() != null){
												Date dateA = new java.sql.Date(han.getUpdateDate().getTime());
												String a = formatter.format(dateA);
												bufPeson.setRequestDateB(a.split(" ")[0]);
											}
										}
										bufPeson.setApproveNoteB(pack.getNoteLeaderApprove());
										bufPeson.setRequestNoteB(pack.getNoteApprove());
									}
								}
							}
							
							/*if (nicTran.getLeaderOfficerId() != null){
								Users user_ = userService.findByUserId(nicTran.getLeaderOfficerId());
								if(user_ != null){
									bufPeson.setApproveUserB(user_.getUserName());
								} else {
									bufPeson.setApproveUserB(nicTran.getLeaderOfficerId());
								}
							}*/
							if (uploadJobOb.getInvestigationOfficerId() != null){
								Users user_ = userService.findByUserId(uploadJobOb.getInvestigationOfficerId());
								if(user_ != null){
									bufPeson.setRequestUserB(user_.getUserName());
								} else {
									bufPeson.setRequestUserB(uploadJobOb.getInvestigationOfficerId());
								}
							}
							
							//Lấy dữ liệu ảnh/tài liệu đính kèm/vân tay
							List<BufEppPersonDoc> bufDocList = new ArrayList<BufEppPersonDoc>();
							List<NicTransactionAttachment> docList = nicTransactionAttachmentService
									.getNicTransactionAttachments(
											request.getTransactionId(),
											new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, 
												NicTransactionAttachment.DOC_TYPE_FINGERPRINT, 
												NicTransactionAttachment.KEY_SC_DSIGNER,
												NicTransactionAttachment.KEY_SC_NBERNIN,
												NicTransactionAttachment.KEY_SC_PASPORT,
												NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB},
											null);
							if (docList != null && docList.size() > 0){
								for(NicTransactionAttachment doc : docList){
									BufEppPersonDoc bufDoc = new BufEppPersonDoc();
									bufDoc.setCreateBy("SYSTEM");
									bufDoc.setCreateDateTime(new Date());
									bufDoc.setCreateWkstnID("SYSTEM-WKST");
									bufDoc.setDocData(doc.getDocData());
									bufDoc.setDocType(doc.getDocType());
									bufDoc.setSerial(doc.getSerialNo());
									bufDoc.setPersonIdDoc(doc.getRemarks());
									bufDoc.setTransacionId(request.getTransactionId());
									bufDocList.add(bufDoc);
								}
							}
							
							BufEppPersonInvestigation bufInves = new BufEppPersonInvestigation();
							com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation bufInvesDomain = bufPersonInvestigationService.findByTranId(request.getTransactionId());
							if (bufInvesDomain != null){
								//bufInves.setDataHistoryPassport(bufInvesDomain.getDataHistoryPassport());
								bufInves.setCreateBy(bufInvesDomain.getCreateBy());
								bufInves.setCreateDateTime(new Date());
								//bufInves.setDataImmihistory(bufInvesDomain.getDataImmihistory());
								//bufInves.setDataInfo(bufInvesDomain.getDataInfoDocument());
								//bufInves.setDataFamily(bufInvesDomain.getDataFamily());
								bufInves.setTransacionId(request.getTransactionId());
								bufInves.setSfDob(sfDob);
								bufInves.setSmDob(smDob);
								bufInves.setSsDob(ssDob);
							}
							
							if(bufDocList != null && bufDocList.size() > 0){
								for(BufEppPersonDoc doc : bufDocList){
									bufPeson.getBufEppPersonDoc().add(doc);
								}	
							}
							
							bufPeson.setBufEppPersonInvestigation(bufInves);
					}
					
					if(request.getTransBMS() != null){
						logger.info("Info - TrasacitonID: " + request.getTransactionId() + " || idBMS: " + request.getTransBMS() +  " || tranMaster: " + request.getTransactionMasterId());
						List<NicSearchHitResult> listHitBMS = nicSearchHitResultService
								.findHitPositionsHit(null, request.getTransBMS());
						if (listHitBMS != null && listHitBMS.size() > 0) {
							for (NicSearchHitResult sHR : listHitBMS) {
								if (sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_BMS)) {
									int score = 0;
									// Tính số điểm trung bình cho vân tay
									// Kiểm tra chuỗi dữ liệu
									if (StringUtils.isNotEmpty(sHR.getHitInfo())) {
										// Tách chuỗi theo dấu ","
										String[] listHit = sHR.getHitInfo().split(",");
										if (listHit.length > 0) {
											for (int i = 0; i < listHit.length; i++) {
												if (Double.parseDouble((listHit[i]
														.split("=")[1])) > score) {
													double d = Double
															.parseDouble((listHit[i]
																	.split("=")[1]));
													score = (int) d;
												}
											}
										}
		
										bufPeson.setScoreBMS("" + score);
										bufPeson.setDetailScoreBMS(sHR.getHitInfo());
										
										if(!resultCPD){
											//if(person != null){
												//bufPeson.setGlobalId(person.getGlobalId() != null ? person.getGlobalId().toString() :"");
												//bufPeson.setOriginId(person.getOriginId() != null ? person.getOriginId().toString() :"");
											//}
											//Lấy dữ liệu thông tin hồ sơ của transaction
											NicTransaction nicTran = nicTransactionService.getNicTransactionById(request.getTransactionId()).getModel();
											if(nicTran != null){
												NicRegistrationData regOrg = nicTran.getNicRegistrationData();
												if(regOrg != null){
													String address = regOrg.getAddressLine1();
													String px = codesService.getCodeValueDescByIdName("TOWN_VILLAGE", regOrg.getAddressLine4(), "");
													String tp = codesService.getCodeValueDescByIdName("DISTRICT", regOrg.getAddressLine5(), "");
													if(StringUtils.isNotEmpty(px)){
														address += ", " + px;
													}
													if(StringUtils.isNotEmpty(tp)){
														address += ", " + tp;
													}
													bufPeson.setAddressResident(address);
													bufPeson.setCreateBy("SYSTEM");
													bufPeson.setCreateDateTime(new Date());
													bufPeson.setCreateWkstnID("SYSTEM-WKST");
													bufPeson.setDob(regOrg.getDateOfBirth());
													bufPeson.setFullname(regOrg.getSurnameLine1());
													bufPeson.setGender(regOrg.getGender());
													bufPeson.setIssueDateNin(regOrg.getDateNin());
													//bufPeson.setIssueDatePassport(regOrg.getDatePassport());
													bufPeson.setIssuePlaceNin(regOrg.getAddressNin());
													bufPeson.setNation(regOrg.getNation());
													bufPeson.setNin(nicTran.getNin());
													bufPeson.setNote("");
													
													bufPeson.setStyleDob(regOrg.getDefDateOfBirth());
													
													String desPob = regOrg.getPlaceOfBirth();
													if(StringUtils.isNotEmpty(regOrg.getPlaceOfBirth())){
														CodeValues codeV = codesService.getCodeValueByIdName("CODE_BirthPlace", regOrg.getPlaceOfBirth());
														if(codeV != null){
															desPob = codeV.getCodeValueDesc();
														}
													}
													bufPeson.setPobDes(desPob);
													bufPeson.setHandoverA(nicTran.getRecieptNo());
													bufPeson.setInfoPerson(nicTran.getInfoPerson());
													
													NicListHandover listH = listHandoverService.findHandoverByCriteria(nicTran.getPackageId(), NicListHandover.TYPE_LIST_A, null).getModel();
													if(listH != null){
														try{
															Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
															Date dateS = new java.sql.Date(listH.getCreateDate().getTime());
															String s = formatter.format(dateS).split(" ")[0];
															bufPeson.setCreateDateHandover(s);
														}catch(Exception e){ }
														
														try{
															if(StringUtils.isNotEmpty(listH.getCreateBy())){
																Users user = userService.findByUserId(listH.getCreateBy());
																if(user != null){
																	bufPeson.setCreateByHandover(user.getUserName());
																}
																else{
																	bufPeson.setCreateByHandover(listH.getCreateBy());
																}
															}
														}catch(Exception e){ }
													}
													
													if(StringUtils.isNotEmpty(regOrg.getJob())){
														CodeValues codeV = codesService.getCodeValueByIdName("CODE_JOB", regOrg.getJob());
														if(codeV != null){
															bufPeson.setJobs(codeV.getCodeValueDesc());
														}
													}
													//Thông tin hộ chiếu
													Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(request.getTransactionId()).getModel();
													if(nicDocs != null && nicDocs.size() > 0){
														List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
														NicDocumentData nicDoc = nicDocs_.get(0);
														bufPeson.setPassportNo(nicDoc.getId().getPassportNo());
														bufPeson.setPassportStatus(nicDoc.getStatus()); //ISSUANCE: phát hành - NONE: tạm khóa
														if(nicDoc.getStatus() != null && nicDoc.getStatus().equals("ISSUANCE"))
															bufPeson.setIssueFlag("Y");
														else
															bufPeson.setIssueFlag("N");
														
														Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
														if(nicDoc.getDateOfIssue() != null){
															Date dateS = new java.sql.Date(nicDoc.getDateOfIssue().getTime());
															String s = formatter.format(dateS).split(" ")[0];
															bufPeson.setIssuePassport(s);
														}
														
														if(nicDoc.getDateOfExpiry() != null){
															
															Date dateS = new java.sql.Date(nicDoc.getDateOfExpiry().getTime());
															String s = formatter.format(dateS).split(" ")[0];
															bufPeson.setExpiredPassport(s);
														}
														
														if(nicDoc.getCreateDatetime() != null){
															Date dateS = new java.sql.Date(nicDoc.getCreateDatetime().getTime());
															String t = formatter.format(dateS).split(" ")[0];
															bufPeson.setPrintDateC(t);
														}
														
														if(nicDoc.getPackageDatetime() != null){
															Date dateS = new java.sql.Date(nicDoc.getPackageDatetime().getTime());
															String t = formatter.format(dateS).split(" ")[0];
															bufPeson.setCreateDateC(t);
														}
														
														SiteRepository site = siteService.getSiteRepoById(nicTran.getIssSiteCode());
														if(site != null)
															bufPeson.setIssuePlacePassport(site.getSiteName());
														
														if(nicTran.getPassportType().equals("PO"))
															bufPeson.setPassportType("CÔNG VỤ");
														else if(nicTran.getPassportType().equals("PD"))
															bufPeson.setPassportType("NGOẠI GIAO");
														else
															bufPeson.setPassportType("PHỔ THÔNG");
														bufPeson.setSerial(nicDoc.getChipSerialNo());
														bufPeson.setIcaoLineOne(nicDoc.getIcaoLine1());
														bufPeson.setIcaoLineTwo(nicDoc.getIcaoLine2());
														bufPeson.setHandoverC(nicDoc.getPackageId());
													}
													bufPeson.setPhone(regOrg.getContactNo());
													bufPeson.setReligion(regOrg.getReligion());
													bufPeson.setTransacionId(request.getTransactionId());
													
													bufPeson.setFatherLost("N");
													bufPeson.setMotherLost("N");
													bufPeson.setFatherLost(regOrg.getFatherLost());
													bufPeson.setMotherLost(regOrg.getMotherLost());
													
													//Thông tin ng thân
													FamilyData family = new FamilyData();
													
													String fatherName = regOrg.getFatherFullname();
													String motherName = regOrg.getMotherFullname();
													String spouseName = regOrg.getSpouseFullname();
													family.setFatherName(fatherName.trim());
													family.setMotherName(motherName.trim());
													family.setSpouseName(spouseName.trim());
													
													if(regOrg.getFatherDateOfBirth() != null){
														family.setFatherDob(regOrg.getFatherDateOfBirth());
													}
													if(regOrg.getMotherDateOfBirth() != null){
														family.setMotherDob(regOrg.getMotherDateOfBirth());
													}
													if(regOrg.getSpouseDateOfBirth() != null){
														family.setSpouseDob(regOrg.getSpouseDateOfBirth());
													}
													
													JAXBContext jaxbContext = JAXBContext.newInstance(FamilyData.class);
													Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
													StringWriter sw = new StringWriter();
													jaxbMarshaller.marshal(family, sw);
													String xmlFamily = sw.toString();
													
													bufPeson.setFamilyData(xmlFamily);
												}
													//}
												//}
												List<NicTransactionPackage> lstPack = nicTransactionPackageService.getPackageNameByTransactionId(request.getTransactionId());
												if(lstPack != null && lstPack.size() > 0){
													for(NicTransactionPackage pack : lstPack){
														if(pack.getTypeList() == 8 && pack.getStage().equals("D")){
															NicListHandover han = listHandoverService.findByPackageId(new NicListHandoverId(pack.getPackageId(), null)).getModel();						
															if(han != null){
																if(han.getCreateDate() != null){
																	Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
																	if (han.getCreateDate() != null){
																		Date dateS = new java.sql.Date(han.getCreateDate().getTime());
																		String s = formatter.format(dateS);
																		bufPeson.setApproveDateB(s.split(" ")[0]);
																	}
																	if (han.getUpdateDate() != null){
																		Date dateA = new java.sql.Date(han.getUpdateDate().getTime());
																		String a = formatter.format(dateA);
																		bufPeson.setRequestDateB(a.split(" ")[0]);
																	}
																}
															}
															bufPeson.setApproveNoteB(pack.getNoteLeaderApprove());
															bufPeson.setRequestNoteB(pack.getNoteApprove());
														}
													}
												}
												
												/*if (nicTran.getLeaderOfficerId() != null){
													Users user_ = userService.findByUserId(nicTran.getLeaderOfficerId());
													if(user_ != null){
														bufPeson.setApproveUserB(user_.getUserName());
													} else {
														bufPeson.setApproveUserB(nicTran.getLeaderOfficerId());
													}
												}*/
												if (uploadJobOb.getInvestigationOfficerId() != null){
													Users user_ = userService.findByUserId(uploadJobOb.getInvestigationOfficerId());
													if(user_ != null){
														bufPeson.setRequestUserB(user_.getUserName());
													} else {
														bufPeson.setRequestUserB(uploadJobOb.getInvestigationOfficerId());
													}
												}
												
												//Lấy dữ liệu ảnh/tài liệu đính kèm/vân tay
												List<BufEppPersonDoc> bufDocList = new ArrayList<BufEppPersonDoc>();
												List<NicTransactionAttachment> docList = nicTransactionAttachmentService
														.getNicTransactionAttachments(
																request.getTransactionId(),
																new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, 
																	NicTransactionAttachment.DOC_TYPE_FINGERPRINT, 
																	NicTransactionAttachment.KEY_SC_DSIGNER,
																	NicTransactionAttachment.KEY_SC_NBERNIN,
																	NicTransactionAttachment.KEY_SC_PASPORT,
																	NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB},
																null);
												if(docList != null && docList.size() > 0){
													for(NicTransactionAttachment doc : docList){
														BufEppPersonDoc bufDoc = new BufEppPersonDoc();
														bufDoc.setCreateBy("SYSTEM");
														bufDoc.setCreateDateTime(new Date());
														bufDoc.setCreateWkstnID("SYSTEM-WKST");
														bufDoc.setDocData(doc.getDocData());
														bufDoc.setDocType(doc.getDocType());
														bufDoc.setSerial(doc.getSerialNo());
														bufDoc.setPersonIdDoc(doc.getRemarks());
														bufDoc.setTransacionId(request.getTransactionId());
														bufDocList.add(bufDoc);
													}
												}
												
												BufEppPersonInvestigation bufInves = new BufEppPersonInvestigation();
												com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation bufInvesDomain = bufPersonInvestigationService.findByTranId(request.getTransactionId());
												if(bufInvesDomain != null){
													//bufInves.setDataHistoryPassport(bufInvesDomain.getDataHistoryPassport());
													bufInves.setCreateBy(bufInvesDomain.getCreateBy());
													bufInves.setCreateDateTime(new Date());
													//bufInves.setDataImmihistory(bufInvesDomain.getDataImmihistory());
													//bufInves.setDataFamily(bufInvesDomain.getDataFamily());
													//bufInves.setDataInfo(bufInvesDomain.getDataInfoDocument());
													bufInves.setTransacionId(request.getTransactionId());
													bufInves.setSfDob(regOrg.getFatherDefDateOfBirth());
													bufInves.setSmDob(regOrg.getMotherDefDateOfBirth());
													bufInves.setSsDob(regOrg.getSpouseDefDateOfBirth());
												}
												
												if(bufDocList != null && bufDocList.size() > 0){
													for(BufEppPersonDoc doc : bufDocList){
														bufPeson.getBufEppPersonDoc().add(doc);
													}	
												}
												
												bufPeson.setBufEppPersonInvestigation(bufInves);
												result.getBufEppPerson().add(bufPeson);
											}
										}
									}
								}
								break;
							}
						}
					}
					result.getBufEppPerson().add(bufPeson);
					
					if(StringUtils.isNotEmpty(uploadJob.getNicTransaction().getPersonCode())){
						//Lấy dữ liệu hồ sơ khớp
						//Tìm dữ liệu registration Data của orginalId
						try{
							NicRegistrationData regOrg = uploadJob.getNicTransaction().getNicRegistrationData();
							if(regOrg != null){
								bufPeson.setAddressResident(regOrg.getAddressLine1());
								bufPeson.setCreateBy("SYSTEM");
								bufPeson.setCreateDateTime(new Date());
								bufPeson.setCreateWkstnID("SYSTEM");
								bufPeson.setDob(regOrg.getDateOfBirth());
								bufPeson.setFullname(regOrg.getSurnameLine1());
								bufPeson.setGender(regOrg.getGender());
								bufPeson.setIssueDateNin(regOrg.getDateNin());
								//bufPeson.setIssueDatePassport(regOrg.getDatePassport());
								bufPeson.setIssuePlaceNin(regOrg.getAddressNin());
								bufPeson.setNation(regOrg.getNation());
								bufPeson.setNin(uploadJob.getNicTransaction().getNin());
								bufPeson.setNote("");
							}
						}
						catch(Exception e){
							logger.error("RegistrationData-Error: " + e.getMessage());
						}
					}
			}
		} catch (Exception e) {
			logger.error("Message: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public BufEppListRequest downloadListBufIdPerson(String transactionId) throws FaultException {
		//Tìm hồ sơ khớp
		BufEppListRequest result = new BufEppListRequest();
				result.setResultCheck("N");
				try {
					if(StringUtils.isNotEmpty(transactionId)){
						//Kiểm tra kết quả trả về AFIS và CPD đã có chưa
						List<NicUploadJob> listUpload = uploadJobService.findAllByTransactionId(transactionId);
						if(listUpload != null && listUpload.size() > 0){
							NicUploadJob uploadJob = listUpload.get(0);
							if(uploadJob.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_INITIAL)){
								result.setResultCheck("Y");
								List<RptStatisticsTransmitData> lstRptKQ = rptStatisticsTransmitDataService.findBySiteCode("A08");
								if(lstRptKQ != null && lstRptKQ.size() > 0){
									RptStatisticsTransmitData data = lstRptKQ.get(0);
									int num = data.getTotal();
									data.setTotal(num + 1);
									data.setUpdateDatetime(new Date());
									rptStatisticsTransmitDataService.saveOrUpdateData(data);
								}
								else
								{
									RptStatisticsTransmitData data = new RptStatisticsTransmitData();
									data.setRptDate(new Date());
									data.setSiteCode("A08");
									data.setTotal(1);
									data.setType("TRA_KQTC");
									data.setUpdateDatetime(new Date());
									rptStatisticsTransmitDataService.saveOrUpdateData(data);
								}
								
								//Danh sách BMS / CPD nghi trùng
								Long idCPD = null;
								Long idBMS = null;
								
								List<NicSearchResult> searchResults = nicSearchResultDao.findAllByJobId(uploadJob.getWorkflowJobId());
								if(searchResults != null && searchResults.size() > 0){
									for(NicSearchResult sR_ : searchResults){
										if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__CPD) && sR_.getHasHit()){
											idCPD = sR_.getSearchResultId();
										}
										else if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__FP_1_N) && sR_.getHasHit() != null && sR_.getHasHit()){
											idBMS = sR_.getSearchResultId();
										}
									}
								}
								
								if(idCPD == null && idBMS == null){
									logger.info("==== Null data check AFIS");
								}
								else{
									//Dữ liệu kiểm tra với CPD
									
									if(idCPD != null){
										/*Map<String, Long> mappingPerson = new HashMap<String, Long>();
										Map<String, Long> mappingCPD = new HashMap<String, Long>();
										//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
										List<NicSearchHitResult> listHitCPD = nicSearchHitResultService.findHitPositions(null, idCPD);
										if(listHitCPD != null && listHitCPD.size() > 0){
											for(NicSearchHitResult sHR : listHitCPD){
												if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_CPD)){
													NicUploadJob nicHit = uploadJobService.getUploadJobByTransactionIdSinger(null, sHR.getTransactionIdHit());
													if(nicHit != null && nicHit.getNicTransaction().getRegSiteCode().equals(uploadJob.getNicTransaction().getRegSiteCode())){
														mappingCPD.put(nicHit.getTransactionId(), sHR.getHitResultId());
														if(mappingPerson.size() > 0){
															if(!mappingPerson.containsValue(nicHit.getOriginalyPersonId())){
																mappingPerson.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
															}
															else
															{
																String sameValue = "";
																for (Entry<String, Long> entry : mappingPerson.entrySet()) {
														            if (entry.getValue().equals(nicHit.getOriginalyPersonId())) {
														            	sameValue = entry.getKey();
														            }
														        }
																
																if(StringUtils.isNotEmpty(sameValue)){
																	NicUploadJob nicSame = uploadJobService.getUploadJobByTransactionIdSinger(null, sameValue);
																	if(nicHit.getNicTransaction().getDateOfApplication().before(nicSame.getNicTransaction().getDateOfApplication())){
																		mappingPerson.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
																		mappingPerson.remove(sameValue);
																	}
																}
																else
																{
																	mappingPerson.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
																}
															}	
														}
														else
														{
															mappingPerson.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
														}
													}
												}
											}
										}
										
										if(mappingPerson.size() > 0){
											Map<String, Long> mapOldPerson = new HashMap<String, Long>();
											//Xử lý lấy hồ sơ tới Person gốc
											for (Entry<String, Long> entry : mappingPerson.entrySet()) {
												if(entry.getValue() != null){
													List<EppPerson> lstP = eppPersonService.findByGlobalId(entry.getValue());
													if(lstP != null && lstP.size() > 0){
														EppPerson person = lstP.get(0);
														if(person.getOriginId() != null && person.getOriginId() > 0){
															Long perId = person.getOriginId();
															do {
																List<NicUploadJob> lUploadO1 = uploadJobService.findAllByPersonID(person.getOriginId());
																if(lUploadO1 != null && lUploadO1.size() > 0){
																	for(NicUploadJob up : lUploadO1){
																		if(mapOldPerson.size() > 0){
																			String sameValue = "";
																			for (Entry<String, Long> entry1 : mapOldPerson.entrySet()) {
																	            if (entry1.getValue().equals(up.getOriginalyPersonId())) {
																	            	sameValue = entry1.getKey();
																	            }
																	        }
																			
																			if(StringUtils.isNotEmpty(sameValue)){
																				NicUploadJob nicSame = uploadJobService.getUploadJobByTransactionIdSinger(null, sameValue);
																				if(up.getNicTransaction().getDateOfApplication().before(nicSame.getNicTransaction().getDateOfApplication())){
																					mapOldPerson.put(up.getTransactionId(), up.getOriginalyPersonId());
																					mapOldPerson.remove(sameValue);
																				}
																			}
																			else {
																				mapOldPerson.put(up.getTransactionId(), up.getOriginalyPersonId());
																			}
																		}
																		else {
																			mapOldPerson.put(up.getTransactionId(), up.getOriginalyPersonId());
																		}
																	}
																	
																	List<EppPerson> lPersonO1 = eppPersonService.findByGlobalId(person.getOriginId());
																	if(lPersonO1 != null && lPersonO1.size() > 0){
																		if(lPersonO1.get(0).getOriginId() != null && lPersonO1.get(0).getOriginId() > 0){
																			perId = lPersonO1.get(0).getOriginId();
																		}
																		else
																		{
																			perId = null;
																		}
																	}
																}
																else
																{
																	perId = null;
																}
															} while(perId != null);
														}
													}
												}
											}
											for (Entry<String, Long> entry : mapOldPerson.entrySet()) {
												if(!mappingPerson.containsKey(entry.getKey())){
													mappingPerson.put(entry.getKey(), entry.getValue());
												}
											}
											
											for (Entry<String, Long> entry : mappingCPD.entrySet()) {
									            if(mappingPerson.containsKey(entry.getKey())){
									            	//Lấy Id của đối tượng trùng
													BufEppListResponse res = new BufEppListResponse();
													res.setIdHitSearchCPD(entry.getValue());
													res.setTranId(entry.getKey());
													result.getBufEppListResponse().add(res);
									            }
									        }
										}*/
										

										List<NicHitTransaction> mappingCPD = new ArrayList<NicHitTransaction>();
										List<NicHitTransaction> listHitCPD = nicSearchHitResultService.findHitBySource(uploadJob.getTransactionId(), "CPD", uploadJob.getNicTransaction().getRegSiteCode(), idCPD);
										if(listHitCPD != null && listHitCPD.size() > 0){
											for(NicHitTransaction hits : listHitCPD){
												if(mappingCPD == null || mappingCPD.size() < 1){
													mappingCPD.add(hits);
												}
												else
												{
													Boolean exist = false;
													Boolean samePersonId = true;
													for(NicHitTransaction map : mappingCPD){
														if(map.getTranid().equals(hits.getTranid())){
															exist = true;
															break;
														} else {
															if(map.getPersonId() != null && hits.getPersonId() != null){
																EppPerson personHit = eppPersonService.getPersonById(hits.getPersonId());
																EppPerson personMap =  eppPersonService.getPersonById(map.getPersonId());
																/*if(personHit != null && personMap != null && personHit.getOriginId() != null && personMap.getOriginId() != null && personMap.getOriginId().equals(personHit.getOriginId())){
																	NicUploadJob nicHit = uploadJobService.getUploadJobByTransactionIdSinger(null, hits.getTranid());
																	NicUploadJob nicSame = uploadJobService.getUploadJobByTransactionIdSinger(null, map.getTranid());
																	exist = true;
																	if(nicHit.getNicTransaction().getDateOfApplication().after(nicSame.getNicTransaction().getDateOfApplication())) {
																		mappingCPD.add(hits);
																		mappingCPD.remove(map);
																		break;
																	}
																}*/
															}
														}
													}
													if(!exist || !samePersonId)
														mappingCPD.add(hits);
												}
											}
										}
									
										List<NicHitTransaction> mapHit = new ArrayList<NicHitTransaction>();
										for(NicHitTransaction map : mappingCPD){
											if(map.getPersonId() != null){
												mapHit = findPersonOrginal(map.getPersonId());
											}
										}
										
										if(mapHit != null && mapHit.size() > 0){
											for(NicHitTransaction map : mapHit){
												for(NicHitTransaction mapCPD : mappingCPD){
													if(!map.getTranid().equals(mapCPD.getTranid())){
														mappingCPD.add(map);
													}
												}
											}
										}
										if(mappingCPD.size() > 0){
											for (NicHitTransaction entry : mappingCPD) {
												//Lấy Id của đối tượng trùng
												BufEppListResponse res = new BufEppListResponse();
												res.setIdHitSearchCPD(entry.getHitId());
												res.setTranId(entry.getTranid());
												result.getBufEppListResponse().add(res); 	
									        }
										}
									}
									
									
									
									
									
									logger.info("CHECKING WITH BMS=================");
									///Dữ liệu kiểm tra với BMS
									if(idBMS != null){
										/*//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
										Map<String, Long> mappingPersonBMS = new HashMap<String, Long>();
										Map<String, Long> mappingBMS = new HashMap<String, Long>();
										List<NicSearchHitResult> listHitBMS = nicSearchHitResultService.findHitPositions(null, idBMS);
										if(listHitBMS != null && listHitBMS.size() > 0){
											for(NicSearchHitResult sHR : listHitBMS){
												if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_BMS)){
													NicUploadJob nicHit = uploadJobService.getUploadJobByTransactionIdSinger(null, sHR.getTransactionIdHit());
													mappingBMS.put(sHR.getTransactionIdHit(), sHR.getHitResultId());
													if(mappingPersonBMS.size() > 0){
														if(!mappingPersonBMS.containsValue(nicHit.getOriginalyPersonId())){
															mappingPersonBMS.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
														}
														else
														{
															String sameValue = "";
															for (Entry<String, Long> entry : mappingPersonBMS.entrySet()) {
													            if (entry.getValue() != null && entry.getValue().equals(nicHit.getOriginalyPersonId())) {
													            	sameValue = entry.getKey();
													            }
													        }
															
															if(StringUtils.isNotEmpty(sameValue)){
																NicUploadJob nicSame = uploadJobService.getUploadJobByTransactionIdSinger(null, sameValue);
																if(nicHit.getNicTransaction().getDateOfApplication().before(nicSame.getNicTransaction().getDateOfApplication())){
																	mappingPersonBMS.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
																	mappingPersonBMS.remove(sameValue);
																}
															}
															else
															{
																mappingPersonBMS.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
															}
														}
													}
													else
													{
														mappingPersonBMS.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
													}
													//Lấy Id của đối tượng trùng
													String transactionId_BMS = sHR.getTransactionIdHit();
													if(StringUtils.isNotEmpty(transactionId_BMS)){
														Boolean existId = false;
														//Kiểm tra trong ListCheckCPD có trùng transactionID không. Nếu có thì chỉ thêm dữ liệu BMS vào vị trí đối tượng
														if(result != null && result.getBufEppListResponse().size() > 0){
															for(BufEppListResponse res : result.getBufEppListResponse()){
																if(res.getTranId().equals(transactionId_BMS))
																{
																	existId = true;
																	res.setIdHitSearchBMS(sHR.getHitResultId());
																}
															}
														}
														if(!existId){
															BufEppListResponse res = new BufEppListResponse();
															res.setIdHitSearchBMS(sHR.getHitResultId());
															res.setTranId(sHR.getTransactionIdHit());
															result.getBufEppListResponse().add(res);
														}	
													}
												}
											}
										}
										
										if(mappingPersonBMS.size() > 0){
											Map<String, Long> mapOldPerson = new HashMap<String, Long>();
											//Xử lý lấy hồ sơ tới Person gốc
											for (Entry<String, Long> entry : mappingPersonBMS.entrySet()) {
												if(entry.getValue() != null){
													List<EppPerson> lstP = eppPersonService.findByGlobalId(entry.getValue());
													if(lstP != null && lstP.size() > 0){
														EppPerson person = lstP.get(0);
														if(person.getOriginId() != null && person.getOriginId() > 0){
															Long perId = person.getOriginId();
															do {
																List<NicUploadJob> lUploadO1 = uploadJobService.findAllByPersonID(person.getOriginId());
																if(lUploadO1 != null && lUploadO1.size() > 0){
																	for(NicUploadJob up : lUploadO1){
																		if(mapOldPerson.size() > 0){
																			String sameValue = "";
																			for (Entry<String, Long> entry1 : mapOldPerson.entrySet()) {
																	            if (entry1.getValue() != null && entry1.getValue().equals(up.getOriginalyPersonId())) {
																	            	sameValue = entry1.getKey();
																	            }
																	        }
																			
																			if(StringUtils.isNotEmpty(sameValue)){
																				NicUploadJob nicSame = uploadJobService.getUploadJobByTransactionIdSinger(null, sameValue);
																				if(up.getNicTransaction().getDateOfApplication().before(nicSame.getNicTransaction().getDateOfApplication())){
																					mapOldPerson.put(up.getTransactionId(), up.getOriginalyPersonId());
																					mapOldPerson.remove(sameValue);
																				}
																			}
																			else {
																				mapOldPerson.put(up.getTransactionId(), up.getOriginalyPersonId());
																			}
																		}
																		else {
																			mapOldPerson.put(up.getTransactionId(), up.getOriginalyPersonId());
																		}
																	}
																	
																	List<EppPerson> lPersonO1 = eppPersonService.findByGlobalId(person.getOriginId());
																	if(lPersonO1 != null && lPersonO1.size() > 0){
																		if(lPersonO1.get(0).getOriginId() != null && lPersonO1.get(0).getOriginId() > 0){
																			perId = lPersonO1.get(0).getOriginId();
																		}
																		else
																		{
																			perId = null;
																		}
																	}
																}
																else
																{
																	perId = null;
																}
															} while (perId != null);
														}
													}
												}
											}
											for (Entry<String, Long> entry : mapOldPerson.entrySet()) {
												if(!mappingPersonBMS.containsKey(entry.getKey())){
													mappingPersonBMS.put(entry.getKey(), entry.getValue());
												}
											}
										}
										if(mappingPersonBMS.size() > 0){
											for (Entry<String, Long> entry : mappingBMS.entrySet()) {
									            if(mappingPersonBMS.containsKey(entry.getKey())){
									            	//Lấy Id của đối tượng trùng
									            	Boolean existId = false;
													//Kiểm tra trong ListCheckCPD có trùng transactionID không. Nếu có thì chỉ thêm dữ liệu BMS vào vị trí đối tượng
													if(result != null && result.getBufEppListResponse().size() > 0){
														for(BufEppListResponse res : result.getBufEppListResponse()){
															if(res.getTranId().equals(entry.getKey()))
															{
																existId = true;
																res.setIdHitSearchBMS(entry.getValue());
															}
														}
													}
													if(!existId){
														BufEppListResponse res = new BufEppListResponse();
														res.setIdHitSearchBMS(entry.getValue());
														res.setTranId(entry.getKey());
														result.getBufEppListResponse().add(res);
													}
									            }
									        }
										}*/
										
										List<NicHitTransaction> mappingBMS = new ArrayList<NicHitTransaction>();
										List<NicHitTransaction> listHitBMS = nicSearchHitResultService.findHitBySource(uploadJob.getTransactionId(), "BMS", uploadJob.getNicTransaction().getRegSiteCode(), idBMS);
										if(listHitBMS != null && listHitBMS.size() > 0){
											for(NicHitTransaction hits : listHitBMS){
												if(mappingBMS == null || mappingBMS.size() < 1){
													mappingBMS.add(hits);
												}
												else
												{
													Boolean exist = false;
													Boolean samePersonId = true;
													for(NicHitTransaction map : mappingBMS){
														if(map.getTranid().equals(hits.getTranid())){
															exist = true;
															break;
														} else {
															if(map.getPersonId() != null && hits.getPersonId() != null){
																EppPerson personHit = eppPersonService.getPersonById(hits.getPersonId());
																EppPerson personMap =  eppPersonService.getPersonById(map.getPersonId());
																/*if(personHit != null && personMap != null && personHit.getOriginId() != null && personMap.getOriginId() != null && personMap.getOriginId().equals(personHit.getOriginId())){
																	NicUploadJob nicHit = uploadJobService.getUploadJobByTransactionIdSinger(null, hits.getTranid());
																	NicUploadJob nicSame = uploadJobService.getUploadJobByTransactionIdSinger(null, map.getTranid());
																	exist = true;
																	if(nicHit.getNicTransaction().getDateOfApplication().after(nicSame.getNicTransaction().getDateOfApplication())) {
																		mappingBMS.add(hits);
																		mappingBMS.remove(map);
																		break;
																	}
																}*/
															}
														}
													}
													if(!exist || !samePersonId)
														mappingBMS.add(hits);
												}
											}
										}
									
										List<NicHitTransaction> mapHit = new ArrayList<NicHitTransaction>();
										for(NicHitTransaction map : mappingBMS){
											if(map.getPersonId() != null){
												mapHit = findPersonOrginal(map.getPersonId());
											}
										}
										
										if(mapHit != null && mapHit.size() > 0){
											for(NicHitTransaction map : mapHit){
												for(NicHitTransaction mapBMS : mappingBMS){
													if(!map.getTranid().equals(mapBMS.getTranid())){
															mappingBMS.add(map);
													}
												}
											}
										}
										
										if(mappingBMS.size() > 0){
											for (NicHitTransaction entry : mappingBMS) {
												//Lấy Id của đối tượng trùng
								            	Boolean existId = false;
												//Kiểm tra trong ListCheckCPD có trùng transactionID không. Nếu có thì chỉ thêm dữ liệu BMS vào vị trí đối tượng
												if(result != null && result.getBufEppListResponse().size() > 0){
													for(BufEppListResponse res : result.getBufEppListResponse()){
														if(res.getTranId().equals(entry.getTranid())) {
															existId = true;
															res.setIdHitSearchBMS(entry.getHitId());
														}
													}
												}
												if(!existId){
													BufEppListResponse res = new BufEppListResponse();
													res.setIdHitSearchBMS(entry.getHitId());
													res.setTranId(entry.getTranid());
													result.getBufEppListResponse().add(res);
												}  	
									        }
										}
									}
									else
									{
										logger.info("Not found list hit BMS by idBMS: " + idBMS);
									}
								}
							}
							else{
								logger.info("Not found result BMS/CPD");
							}
						}
						else
						{
							logger.info("Not found transactionId" );
						}
					}
				} catch (Exception e) {
					logger.info("Message: " + e.getMessage());
					e.printStackTrace();
				}
				return result;
	}

	/*@Override
	public MoreInformation retrieveMoreInformation(String transactionId) throws FaultException {
		
	}*/
	
	@Override
	public String retrievePassportStatus(String passportNo) throws FaultException {
		String passportStatus = null;
		logger.info("TransactionWSImpl.retrievePassportStatus(): begin" );
		if (StringUtils.isBlank(passportNo))
			throw new FaultException(StatusCode.TRANWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: PassportNo]", StatusCode.TRANWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
		try {
			logger.info("retrievePassportStatus(): passportNo:{}", passportNo);
			NicDocumentData documentData = documentDataService.findByDocNumber(passportNo).getModel();
			if (documentData==null)
				throw new FaultException(StatusCode.TRANWS_DATA_NOT_FOUND.getDesc(), StatusCode.TRANWS_DATA_NOT_FOUND.convertToFaultDetail());
			logger.debug("TransactionWSImpl.retrievePassportStatus(): passportNo:{} , status:{} , active: {}", new Object[] {passportNo, documentData.getStatus(), documentData.getActiveFlag()});
			
			boolean active = Boolean.TRUE.equals(documentData.getActiveFlag());
			if (active)
				passportStatus = "Active";
			else 
				passportStatus = "Inactive";
		} catch (Exception ex) {
			throw new FaultException(StatusCode.TRANWS_UNEXPECTED_ERROR.getDesc() + "[Exception: " + ex.getMessage() + "]", StatusCode.TRANWS_UNEXPECTED_ERROR.convertToFaultDetail());
		}
		logger.info("TransactionWSImpl.retrievePassportStatus(): passportNo:{} ,passportStatus:{}", passportNo, passportStatus);
		logger.info("TransactionWSImpl.retrievePassportStatus(): end" );
		return passportStatus;
	}
	
	@Override
	public NicPersoInfoDownloadResult downloadNicPersoInfo(
			NicPersoInfoDownloadFilter input) throws FaultException {
		NicPersoInfoDownloadResult result = new NicPersoInfoDownloadResult();
		logger.info("TransactionWSImpl.downloadNicPersoInfo(): begin");
		try {
			String siteCode = input.getSiteCode();
			Date startDate = input.getStartDate();
			Date endDate = input.getEndDate();
			String officerId = input.getOfficerID();
			String wkstnId = input.getWkstnID();

			boolean isAdhocRequest = false;
			String passportNo = input.getPassportNo();
			String nin = input.getNIN();
			String packageId = input.getPackageID();
			String dispatchId = input.getDispatchID();
			if (StringUtils.isNotBlank(passportNo) || StringUtils.isNotBlank(nin) || StringUtils.isNotBlank(packageId) || StringUtils.isNotBlank(dispatchId)) {
				isAdhocRequest = true;
			}		
			List<NicDocumentData> documentDataList = null;
			List<NicRejectionData> rejectionDataList = null;
			List<IssuanceData> issuanceDataList = null;
			if (isAdhocRequest) {
				//entityList = this.issuanceDataService.findIssuanceData(packageId, ccn, nin, siteCode, officerId, wkstnId);
			} else {
				logger.info("[downloadNicPersoInfo][issuanceData] siteCode:{}, startDate:{}, endDate:{} ", new Object[]{ siteCode, startDate, endDate});
				//entityList = this.issuanceDataService.findByPeriod(input.getSiteCode(), input.getStartDate(), input.getEndDate());
				documentDataList = documentDataService.findAllDocumentsForSync(siteCode);
				
				logger.info("[downloadNicPersoInfo][rejectionData] siteCode:{}, startDate:{}, endDate:{} ", new Object[]{ siteCode, startDate, endDate});
				//rejectionList = uploadJobService.getRejectionDataListBySiteCode(siteCode, startDate, endDate);
				rejectionDataList = rejectionDataService.findAllRejectionDataForSync(siteCode);
			}
			if (CollectionUtils.isNotEmpty(documentDataList)) {
				
				int count = 0;
				
				//Lọc bản ghi theo siteCode
				if(documentDataList.size() > 0){
					for(NicDocumentData data : documentDataList){
						String transactionId = data.getId().getTransactionId();
						NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId).getModel();
						if(nicTran == null || !nicTran.getIssSiteCode().equals(siteCode)){
							documentDataList.remove(data);
						}
					}
				}
				
				issuanceDataList = mapper.parseIssuanceDataDTO(documentDataList);
				
				if(documentDataList.size() > 0 && issuanceDataList.size() > 0){
					for(IssuanceData data : issuanceDataList){
						List<PaymentDetail> retrievePaymentDetail = new ArrayList<PaymentDetail>();
						logger.info("update data payment detail");
						//Re-update payment detail
						String transactionId = data.getTransactionID();
						NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId).getModel();
						
						String nameApprover = "";
					/*	if(StringUtils.isNotEmpty(nicTran.getLeaderOfficerId())){
							Users user = userService.findById(nicTran.getLeaderOfficerId());
							if(user != null){
								nameApprover = user.getUserName();
							}
							else{
								nameApprover = nicTran.getLeaderOfficerId();
							}
						}
						*/
						data.setNameApprover(nameApprover);
						NicListHandover listH = listHandoverService.findHandoverByTransactionId(transactionId, 8, 1, true);						
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
						String strDate= formatter.format(listH.getUpdateDate());
						data.setDateApprove(strDate);
						List<NicTransactionPackage> lstP = nicTransactionPackageService.getPackageNameByTransactionId(transactionId);
						if(lstP != null && lstP.size() > 0){
							for(NicTransactionPackage pack : lstP){
								if(pack.getTypeList() == 8 && pack.getStage().equals("D")){
									data.setNoteApprove(pack.getNoteApprove());
								}
							}
						}
						
						if(nicTran != null && nicTran.getIssSiteCode().equals(siteCode)){
							//find payment Id
							String paymentId = "";
							NicTransactionPayment payment = transactionPaymentDao.findGetPaymentByTransaction(transactionId).getModel();
							paymentId = payment.getPaymentId();
							try{
								List<NicTransactionPaymentDetail> list = nicTransactionPaymentDetaiService.findListDetailPaymentList(paymentId).getListModel();
								if(list != null && list.size() > 0){
									retrievePaymentDetail = mapper.parsePaymentDetailDataDTO(list);
								}
							}catch(Exception e){
								
							}
							if(retrievePaymentDetail != null && retrievePaymentDetail.size() > 0){
								for(PaymentDetail paymentD : retrievePaymentDetail){
									issuanceDataList.get(count).getPaymentDetail().add(paymentD);
								}
							}
							count++;
						}
					}
				}
				
				result.getIssuanceDatas().addAll(issuanceDataList);
			}
			if (CollectionUtils.isNotEmpty(rejectionDataList)) {
				result.getRejectionDatas().addAll(mapper.parseRejectionDataDTO(rejectionDataList));
			}

			logger.info("[downloadNicPersoInfo] siteCode:{}, result.issueData.size:{}, result.rejectData.size:{}", new Object[]{ siteCode, result.getIssuanceDatas().size(), result.getRejectionDatas().size()});
		
		} catch (Exception ex) {
			logger.error("Error occured on downloadNicPersoInfo caused of:" + ex.getMessage(), ex);
			throw new FaultException ("Error occured on downloadNicPersoInfo:"+ex.getMessage(),ex);
		}
		logger.info("TransactionWSImpl.downloadNicPersoInfo(): end");
		return result;
	}

	@Override
	public TransactionLogUploadResponse uploadTransactionLog(TransactionLogUpload input) {
		TransactionLogUploadResponse result = new TransactionLogUploadResponse();
		try {
			logger.info("TransactionWSImpl.uploadTransactionLog(): begin" );			
			if (CollectionUtils.isNotEmpty(input.getTransactionLogs())) {
				List<NicTransactionLog> nicTransLogDBOList = mapper.parseNicTransactionLogDBOList(input.getTransactionLogs());
				List<NicTransactionLog> failureLogDBOList = nicTransactionService.saveTransactionLogs(nicTransLogDBOList);
				if (CollectionUtils.isNotEmpty(failureLogDBOList)) {
					List<TransactionLog> failureLogDTOList = mapper.parseTransactionLogDTOList(failureLogDBOList);
					result.getRejectedTransactionLogs().addAll(failureLogDTOList);
					result.setCode(Constant.TRANSACTION_FAILED);
					result.setDetailMessage(Constant.TRANSACTION_FAILED);
				} else {
					result.setCode(Constant.TRANSACTION_SUCCESS);
					result.setDetailMessage(Constant.TRANSACTION_SUCCESS);
				}
			}
			logger.info("[uploadTransactionLog] result.code:{}, result.detailMessage:{}, result.rejectTxnLog.size:{}", new Object[]{ result.getCode(), result.getDetailMessage(), result.getRejectedTransactionLogs().size()});
			logger.info("TransactionWSImpl.uploadTransactionLog(): end");
		} catch (Exception e) {
			result.setCode(Constant.TRANSACTION_ERROR);
			result.setDetailMessage("Exception Encountered in TransactionWSImpl.uploadTransactionLog(): "+e.getMessage());
			logger.error("Exception Encountered in TransactionWSImpl.uploadTransactionLog():"+e.getMessage(), e);
		}
		return result;
	}

	@Override
	public String editDataRegistration(EditDataRegistration data)
			throws FaultException {
		
		try{
		    NicRegistrationData regis =	nicRegistrationDataService.getNicDataById(data.getTransactionId());
		    if(StringUtils.isNotEmpty(data.getAddressline1())){
		    	regis.setAddressLine1(data.getAddressline1());
		    }
		    if(StringUtils.isNotEmpty(data.getAddressline4())){
		    	regis.setAddressLine4(data.getAddressline4());
		    }
		    if(StringUtils.isNotEmpty(data.getAddressline5())){
		    	regis.setAddressLine5(data.getAddressline5());
		    }
		    if(StringUtils.isNotEmpty(data.getFullname())){
		    	regis.setSurnameLine1(data.getFullname());
		    }
		    if(StringUtils.isNotEmpty(data.getSurname())){
		    	regis.setSurnameFull(data.getSurname());
		    }
		    if(StringUtils.isNotEmpty(data.getMiddlename())){
		    	regis.setMiddlenameFull(data.getMiddlename());
		    }
		    if(StringUtils.isNotEmpty(data.getLastname())){
		    	regis.setFirstnameFull(data.getLastname());
		    }
		    if(StringUtils.isNotEmpty(data.getPrintDob())){
		    	regis.setPrintDob(data.getPrintDob());
		    }
		    if(StringUtils.isNotEmpty(data.getStyleDob())){
		    	regis.setDefDateOfBirth(data.getStyleDob());
		    }
		    if(StringUtils.isNotEmpty(data.getDob())){
		    	try{
		    		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(data.getDob());
		    		regis.setDateOfBirth(date1);
		    	}
		    	catch(Exception e){ }
		    }
		    nicRegistrationDataService.saveOrUpdate(regis);
		    return "success";
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@Override
	public String updateReceivedNicPersoInfo(UpdateReceivedNicPersoInfo input)
			throws FaultException {
		String result = Constant.TRANSACTION_FAILED;//"failed";
		logger.info("TransactionWSImpl.updateReceivedNicPersoInfo(): begin");
		List<NicPersoInfoRef> cardInfoList = input.getNicPersoInfoRefs();
		
		//22 Oct 2013 (chris): add officerId and siteCode - start
		String officerId = input.getOfficerID();
		String workstationId = input.getWkstnID();
		String siteCode = input.getSiteCode();
		logger.debug("[updateReceivedCardStatus] OfficerID={}, WkstnID={}, SiteCode={}", new Object[]{ officerId, workstationId, siteCode});
		//22 Oct 2013 (chris): add officerId and siteCode - end
		
		if (CollectionUtils.isEmpty(cardInfoList))
			throw new FaultException ("List of NicPersoInfoRef cannot be empty");
		if (StringUtils.isBlank(siteCode))
			throw new FaultException ("Field siteCode is mandantory"); //23 Oct 2013 (chris): add siteCode validation
		
		List<String> passportNoList = new ArrayList<String>();
		List<String> rejectionTxnIdList = new ArrayList<String>();
		NicDocumentDataId id = null;
		for (NicPersoInfoRef personInfo : cardInfoList) {
			if (StringUtils.isNotBlank(personInfo.getPersoRefID())) {
				passportNoList.add(personInfo.getPersoRefID());
			} else {
				rejectionTxnIdList.add(personInfo.getTransactionID());
			}
		}
		try {
			if (CollectionUtils.isNotEmpty(passportNoList)) {
				logger.debug("[updateReceivedNicPersoInfo] updating received for issuance_data, size={}", new Object[]{ passportNoList.size() });
				documentDataService.updateSyncStatus(passportNoList, officerId, workstationId, siteCode);//
			}
			if (CollectionUtils.isNotEmpty(rejectionTxnIdList)) {
				logger.debug("[updateReceivedNicPersoInfo] updating received for rejection_data, size={}", new Object[]{ rejectionTxnIdList.size() });
				rejectionDataService.updateSyncStatus(rejectionTxnIdList);
			}
			result = Constant.TRANSACTION_SUCCESS;
		} catch (Exception e) {
			logger.error("Exception Encountered in TransactionWSImpl.updateReceivedNicPersoInfo():"+e.getMessage(), e);
		}
		logger.info("TransactionWSImpl.updateReceivedNicPersoInfo(): end");
		return result;
	}
	
	public List<NicHitTransaction> findPersonOrginal(Long personId){
		List<NicHitTransaction> result = null;
		try {
			
			do{
				EppPerson person =  eppPersonService.getPersonById(personId);
				personId = null;
				/*if(person != null && person.getOriginId() != null && person.getOriginId() > 0){
					result = new ArrayList<NicHitTransaction>();
					List<NicUploadJob> nicUp = uploadJobService.findAllByPersonID(person.getOriginId());
					if(nicUp != null && nicUp.size() > 0){
						NicHitTransaction add = new NicHitTransaction();
						add.setTranid(nicUp.get(0).getTransactionId());
						add.setPersonId(person.getOriginId());
						add.setHitId(99999999l);
						result.add(add);
						EppPerson personExist =  eppPersonService.getPersonById(person.getOriginId());
						if(personExist != null && personExist.getOriginId() != null && personExist.getOriginId() > 0){
							personId = personExist.getOriginId();
						} 
					} 
				}*/
			} while (personId != null);
			
		} catch (Exception e){
			
		}
		return result;
	}

	public void setNicTransactionService(NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}

	public void setMapper(TransDTOMapper mapper) {
		this.mapper = mapper;
	}
	
	public void setParametersService(ParametersService parametersService) {
		this.parametersService = parametersService;
	}

	public void setDocumentDataService(DocumentDataService documentDataService) {
		this.documentDataService = documentDataService;
	}

	public void setCodesService(CodesService codesService) {
		this.codesService = codesService;
	}
	
}
