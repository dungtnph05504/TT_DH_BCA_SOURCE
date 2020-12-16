package com.nec.asia.nic.comp.job.command;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.compiler.ast.StringL;

import javax.transaction.Transaction;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.comp.trans.domain.ConfigurationWorkflow;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.SearchResultInA08Dto;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.ConfigurationWorkflowService;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.InquirySearchResultService;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.InquirySearchServiceException;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.workflowProcess.service.WorkflowProcessService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.util.MiscXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;

/*
 * 14 January 2016 (setia): revamp the logic of CPD 
 * 28 March 2016 (chris)  : add age check for fingerprint exempted.
 * 18 April 2016 (chris)  : add 09 reason (CPD check with hit).
 * 26 April 2016 (chris)  : reset job reprocess count when complete
 * 06 June 2016 (setia)   : to prevent duplicate record for those under age that has no fingerprint, but allow multiple paspport type
 */
public class NicVerifyCpdCommand extends BaseCommand<NicUploadJob> implements
		Command<NicUploadJob> {

	// private static final Logger logger =
	// Logger.getLogger(NicVerifyCpdCommand.class);

	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	// UPDATE STATUS CODE
	public static final int JOB_STATE = 1;
	public static final int CPD = 2;
	public static final int INVESTIGATION = 9;
	public static final String STATE_NAME = "CPD";
	public static final String STATE_ERROR = "ERROR";

	// Demographic Fields
	public static final String CHECK_SEARCH_NAME = "searchName";
	public static final String CHECK_FIRSTNAME = "firstnameFull";
	public static final String CHECK_MIDDLENAME = "middlenameFull";
	public static final String CHECK_SURNAME = "surnameFull";
	public static final String CHECK_BIRTH_DATE = "dateOfBirth";
	public static final String CHECK_GENDER = "gender";
	public static final String CHECK_NIN = "nin";

	// JOB STATE
	public static final String JOB_STATE_CPD = "CPD_CHECK";

	// REJECTED REASON
	public static final String MISSING_BIOMETRIC_DATA = "01";
	public static final String MISSING_PHOTO = "02";
	public static final String MISSING_PHOTO_CHIP = "03";
	public static final String BLACKLISTED = "04";

	public static final String DEMOGRAPHIC_CHECK_FAILED = "09"; // Applicant
																// flag by
																// system due to
																// matching
																// demographic
																// data.

	// VERIFICATION TYPE
	public static final String VERIFICATION_OLD_PASSPORT = "OLD PASSPORT";
	public static final String VERIFICATION_APPLICATION_IN_PROGRESS = "APPLICATION IN PROGRESS";
	public static final String VERIFICATION_REJECTED_APPLICATION = "REJECTED APPLICATION=";

	// parameters
	private static final String PARA_SCOPE_APPLICATION = "APPLICATION";
	private static final String PARA_NAME_AGE_FOR_FP_EXEMPTION = "AGE_FOR_FP_EXEMPTION";

	private static final String PARA_SCOPE_SYSTEM = "SYSTEM";
	private static final String PARA_NAME_ASSIGN_USER = "ASSIGN_USER";
	private static final String QUEUE_TYPE_BF_CPD = "BF_CPD";

	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
	private NicRegistrationDataService nicRegistrationDataService;
	private InquirySearchResultService inquirySearchResultService;
	private NicAfisDataService nicAfisDataService;
	private DocumentDataService documentDataService;
	private ParametersService parametersService;
	private WorkflowProcessService workflowProcessService;
	private PersoLocationsService persoLocationsService;
	private CodesService codesService;
	private NicTransactionAttachmentService transactionAttachmentService;
	private BufPersonInvestigationService bufPersonInvestigationService;
	private EppPersonService eppPersonService;
	private SyncQueueJobService queueJobService;
	private SiteService siteService;
	private ConfigurationWorkflowService configurationWorkflowService;
	private Map<String, String> demographicFields;

	// 28 Oct 2013 (jp): Added logInfo to add detailed reason for cpd comparison
	// failure
	String logInfo = "";
	String logInfoXml = "";

	@Override
	public void doSomething(NicUploadJob obj) {

		// Kiểm tra bảng WorkflowProcess
		NicWorkflowProcess objW = null;
		Boolean checkW = true;
		try {
			logger.info("Get data WorkflowProcess");
			List<NicWorkflowProcess> lstW = workflowProcessService
					.findWorkflowProcessByCriteria(NicWorkflowProcess.UPLOADED);
			if (lstW != null && lstW.size() > 0) {
				objW = new NicWorkflowProcess();
				objW = lstW.get(0);

				logger.info("Get data WorkflowProcess: WORKFLOW_END:"
						+ objW.getWorkflowEnd());
				if (!objW.getWorkflowEnd().equals(NicWorkflowProcess.CHECK_CPD)) {
					checkW = false;
				}
			} else {
				logger.info("Get data WorkflowProcess: NULL");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		 * if(StringUtils.equals(job.getAfisCheckStatus(), STATUS_COMPLETED) ||
		 * StringUtils.equals(job.getAfisCheckStatus(),
		 * STATUS_COMPLETED_WITH_HIT) ||
		 * StringUtils.equals(job.getAfisCheckStatus(), STATUS_NO_REQUIRED) ){
		 * 
		 * //AFIS CHECK has been completed before, skip step logger.info(
		 * "-----[{}] skipping AFIS CHECK, status: {}, continue to next step",
		 * transObj.getTransactionId(), job.getAfisCheckStatus()); }
		 */

		logger.info("inside NicVerifyCpdCommand job id:"
				+ obj.getWorkflowJobId() + ", reprocess count is:"
				+ obj.getJobReprocessCount());

		// for logging to transaction logs
		String logData = null;
		Date startTime = new Date();
		String jobStatus = null;
		this.setState(obj.getJobType());// set job type as child key

		try {
			if (!checkW && objW != null) {
				// skip check CPD with control by WORKFLOW_PROCESS
				logger.info("== skip check CPD with control by WORKFLOW_PROCESS");
			} else {

				NicTransaction transObj = null;

				updateStatus(obj.getWorkflowJobId(), JOB_STATE_CPD, JOB_STATE);// update
																				// job
																				// state
																				// to
																				// CPD_CHECK

				logger.info("flag after updating job current status:"
						+ obj.getJobCurrentStage());

				if (StringUtils.equals(obj.getCpdCheckStatus(),
						STATUS_COMPLETED)
						|| StringUtils.equals(obj.getCpdCheckStatus(),
								STATUS_NO_REQUIRED)
						|| StringUtils.equals(obj.getCpdCheckStatus(),
								STATUS_COMPLETED_WITH_HIT)
						|| (!obj.getNicTransaction().getRegSiteCode()
								.equals("AA")
								&& !obj.getNicTransaction().getRegSiteCode()
										.equals("BB")
								&& !obj.getNicTransaction().getRegSiteCode()
										.equals("CC") && (obj
								.getWorkflowJobRunAgainCount() == null || obj
								.getWorkflowJobRunAgainCount() < 2))) {

					// CPD has been completed before, skip step
					// NO ACTION, SKIP COMMAND
					// transactionStatus = JOB_STATE_CPD+STAGE_COMPLETED;
					logger.info("[" + JOB_STATE_CPD
							+ "] already completed, skipping step");

				} else if (StringUtils.equals(obj.getInvestigationStatus(),
						STATUS_COMPLETED)
						&& StringUtils.equals(obj.getInvestigationType(),
								STATE_NAME)) {
					// CPD has been approved thru investigation, skip step
					// transactionStatus = JOB_STATE_CPD+STAGE_COMPLETED;

				} else {
					// Thêm bước xóa CPD khi check lại
					// Kiểm tra trong bảng eppSearchResult
					/*
					 * NicSearchResult nicSearchResultExist =
					 * inquirySearchResultService
					 * .findLatestResultByJobId(obj.getWorkflowJobId(), null);
					 * if(nicSearchResultExist != null){ //Xóa dữ liệu trong
					 * bảng eppSearchHitResult List<NicSearchHitResult> listHit
					 * = inquirySearchResultService.findListHitResultByJobId(
					 * nicSearchResultExist.getSearchResultId()); if(listHit !=
					 * null && listHit.size() > 0){ for(NicSearchHitResult
					 * removeObj : listHit){
					 * inquirySearchResultService.removeNicSearchHitResultById
					 * (removeObj); } }
					 * 
					 * inquirySearchResultService.removeNicSearchResultById(
					 * nicSearchResultExist); }
					 */
					logger.info("[" + JOB_STATE_CPD + "]"
							+ obj.getTransactionId() + "\n");
					updateStatus(obj.getWorkflowJobId(), STATUS_INPROGRESS, CPD);// setCpdCheckStatus
																					// to
																					// in
																					// progress

					transObj = nicTransactionService.findById(
							obj.getTransactionId(), false, true, true, false);// has
																				// registration
																				// data
																				// and
																				// trans
																				// doc

					Map<String, Object> validationFieldsMap = this
							.constructDemographicCheckingFields(transObj
									.getNicRegistrationData());

					StringBuffer hitInfoBuffer = new StringBuffer();
					for (String fieldName : validationFieldsMap.keySet()) {
						hitInfoBuffer.append(fieldName).append(",");
					}

					List<NicRegistrationData> hitList = nicRegistrationDataService
							.findAllByFields(validationFieldsMap);

					List<NicTransaction> hitListNin = nicTransactionService
							.findAllByFields(transObj.getNin());// TRUNG thêm để
																// check cmnd

					// Tìm theo số hộ chiếu cũ
					List<NicDocumentData> hitListPassport = documentDataService
							.findAllByDocNumber(transObj.getPrevPassportNo());

					NicAfisData searchAfisData = nicAfisDataService
							.findByTransactionId(obj.getTransactionId());
					NicSearchResult searchResult = new NicSearchResult();
					searchResult.setWorkflowJobId(obj.getWorkflowJobId());
					if (searchAfisData != null)
						searchResult
								.setAfisKeyNo(searchAfisData.getAfisKeyNo());
					searchResult.setTransactionId(obj.getTransactionId());
					searchResult.setCreateDatetime(new Date());
					searchResult.setTypeSearch(STATE_NAME);
					searchResult.setHasHit(false);
					boolean isHit = false;
					boolean isError = false;
					Map<String, String> afisKeyCache = new HashMap<String, String>();
					String oldPassportNo = transObj.getPrevPassportNo();

					// 1) to check on lookout list.
					String lookoutMsg = this.checkRecordOnLookout(transObj
							.getNicRegistrationData());

					if (StringUtils.isNotBlank(lookoutMsg)) {
						searchResult.setTypeSearch(STATE_ERROR);
						searchResult.getHitList()
								.add(buildSearchHitResult(obj
										.getTransactionId(), afisKeyCache
										.get(obj.getTransactionId()),
										VERIFICATION_REJECTED_APPLICATION
												+ lookoutMsg, STATE_NAME));
						// logger.info("1_AA");
						isError = true;
					} else if (StringUtils.isBlank(lookoutMsg)) {
						boolean isFPExempted = this
								.isTransactionFPExempted(transObj);
						// logger.info("isFPExempted === " + isFPExempted);
						// 2) to check biometric exists
						String verificationMsg = this.checkBiometricExists(
								transObj.getNicTransactionAttachments(),
								isFPExempted);
						// logger.info("verificationMsg === " +
						// verificationMsg);
						if (StringUtils.isNotBlank(verificationMsg)) {
							searchResult.setTypeSearch(STATE_ERROR);
							searchResult.getHitList().add(
									buildSearchHitResult(
											obj.getTransactionId(),
											afisKeyCache.get(obj
													.getTransactionId()),
											VERIFICATION_REJECTED_APPLICATION
													+ verificationMsg,
											STATE_NAME));
							// logger.info("2_AA");
							isError = true;
						}
					}

					if (!isError) {
						// 3) to check demographic
						if (CollectionUtils.isNotEmpty(hitList)) {
							logger.info("EXIST_BY_INFO["
									+ hitList.size()
									+ "]. Preparing to save into search result for transaction ID["
									+ obj.getTransactionId() + "]\n");
							// construct search hit result
							for (NicRegistrationData candidate : hitList) {
								if (candidate.getTransactionId().equals(
										obj.getTransactionId())) {
									logger.info("NEXT_STEP_INFO"
											+ obj.getTransactionId());
									continue;
								}
								logger.info("CONT_STEP_INFO"
										+ obj.getTransactionId());
								NicAfisData candidateAfisData = nicAfisDataService
										.findByTransactionId(candidate
												.getTransactionId());
								/*
								 * if (candidateAfisData == null) continue; //
								 * chris handle npe [2016 Feb 17]
								 */

								// Thay đổi theo dữ liệu đồng bộ [2019 Oct 30]
								if (candidateAfisData == null) {
									candidateAfisData = new NicAfisData();
									candidateAfisData.setAfisKeyNo("");
								}
								afisKeyCache.put(candidate.getTransactionId(),
										candidateAfisData.getAfisKeyNo());
								searchResult
										.getHitList()
										.add(buildSearchHitResult(
												candidate.getTransactionId(),
												afisKeyCache.get(candidate
														.getTransactionId()),
												VERIFICATION_APPLICATION_IN_PROGRESS,
												STATE_NAME));
							}

						}
						// 1. Trung thêm check CMND
						if (CollectionUtils.isNotEmpty(hitListNin)) {
							logger.info("EXIST_NIN ["
									+ hitListNin.size()
									+ "]. Preparing to save into search result for transaction ID["
									+ obj.getTransactionId() + "]\n");
							// construct search hit result
							for (NicTransaction candidate : hitListNin) {
								if (candidate.getTransactionId().equals(
										obj.getTransactionId())) {
									logger.info("NEXT_STEP_NIN"
											+ obj.getTransactionId());
									continue;
								}
								logger.info("CONT_STEP_NIN"
										+ obj.getTransactionId());
								boolean checkExist = false;
								for (NicSearchHitResult nicSHR : searchResult
										.getHitList()) {
									if (candidate.getTransactionId().equals(
											nicSHR.getTransactionIdHit())) {
										checkExist = true;
										break;
									}
								}
								if (checkExist) {
									continue;
								}
								NicAfisData candidateAfisData = nicAfisDataService
										.findByTransactionId(candidate
												.getTransactionId());
								/*
								 * if (candidateAfisData == null) continue; //
								 * chris handle npe [2016 Feb 17]
								 */

								// Thay đổi theo dữ liệu đồng bộ [2019 Oct 30]
								if (candidateAfisData == null) {
									candidateAfisData = new NicAfisData();
									candidateAfisData.setAfisKeyNo("");
								}
								afisKeyCache.put(candidate.getTransactionId(),
										candidateAfisData.getAfisKeyNo());
								searchResult
										.getHitList()
										.add(buildSearchHitResult(
												candidate.getTransactionId(),
												afisKeyCache.get(candidate
														.getTransactionId()),
												VERIFICATION_APPLICATION_IN_PROGRESS,
												STATE_NAME));
							}
						}
						// End 1
						// Hoald thêm check theo số hộ chiếu cũ: prevPassportNo
						if (CollectionUtils.isNotEmpty(hitListPassport)) {
							logger.info("EXIST_PASSPORT_NO ["
									+ hitListPassport.size()
									+ "]. Preparing to save into search result for transaction ID["
									+ obj.getTransactionId() + "]\n");
							// construct search hit result
							for (NicDocumentData candidate : hitListPassport) {
								if (candidate.getId().getTransactionId()
										.equals(obj.getTransactionId())) {
									logger.info("NEXT_STEP_PASSPORT_NO"
											+ obj.getTransactionId());
									continue;
								}
								logger.info("CONT_STEP_PASSPORT_NO"
										+ obj.getTransactionId());
								boolean checkExist = false;
								for (NicSearchHitResult nicSHR : searchResult
										.getHitList()) {
									if (candidate
											.getId()
											.getTransactionId()
											.equals(nicSHR
													.getTransactionIdHit())) {
										checkExist = true;
										break;
									}
								}
								if (checkExist) {
									continue;
								}
								NicAfisData candidateAfisData = nicAfisDataService
										.findByTransactionId(candidate.getId()
												.getTransactionId());
								/*
								 * if (candidateAfisData == null) continue; //
								 * chris handle npe [2016 Feb 17]
								 */

								// Thay đổi theo dữ liệu đồng bộ [2019 Oct 30]
								if (candidateAfisData == null) {
									candidateAfisData = new NicAfisData();
									candidateAfisData.setAfisKeyNo("");
								}
								afisKeyCache.put(candidate.getId()
										.getTransactionId(), candidateAfisData
										.getAfisKeyNo());
								searchResult
										.getHitList()
										.add(buildSearchHitResult(
												candidate.getId()
														.getTransactionId(),
												afisKeyCache.get(candidate
														.getId()
														.getTransactionId()),
												VERIFICATION_APPLICATION_IN_PROGRESS,
												STATE_NAME));
							}
						}
						// end check prevPassportNo
					}
					logger.info(obj.getTransactionId() + " searchResult ==="
							+ searchResult.getHitList().size());
					if (searchResult.getHitList().size() > 0) {
						searchResult.setHasHit(true);
						isHit = true;
					}

					// Xu ly lay ket qua tu A08
					SearchResultInA08Dto request = new SearchResultInA08Dto();
					request.setpGTinh(obj.getNicTransaction()
							.getNicRegistrationData().getGender());
					request.setpHoTen(obj.getNicTransaction()
							.getNicRegistrationData().getSearchName());
					request.setpKieuNS(obj.getNicTransaction()
							.getNicRegistrationData().getDefDateOfBirth());
					request.setpMaCaNhan(obj.getNicTransaction()
							.getPersonCode());
					request.setpNgaySinh(DateUtil.parseDate(obj
							.getNicTransaction().getNicRegistrationData()
							.getDateOfBirth(), DateUtil.FORMAT_YYYYMMDD));
					request.setpQTich(obj.getNicTransaction()
							.getNicRegistrationData().getNationality());
					request.setpSoCMND(obj.getNicTransaction().getNin());
					request.setpSoHC(transObj.getPrevPassportNo());
					/*
					 * try { NicDocumentData doc = documentDataService
					 * .getDocumentDataById(obj.getTransactionId()) .getModel();
					 * if (doc != null)
					 * request.setpSoHC(doc.getId().getPassportNo()); } catch
					 * (Exception e) { }
					 */
					processResultInvestigationA08(request,
							searchResult.getHitList(), obj.getTransactionId());

					if (obj.getWorkflowJobRunAgainCount() == 1) {
						this.addObjToQueueJob(obj.getTransactionId(),
								QUEUE_TYPE_BF_CPD, transObj.getRegSiteCode(),
								null);// PA
					}
					if (obj.getWorkflowJobRunAgainCount() > 1) {
						String siteTTXL = this.responseSite(transObj
								.getRegSiteCode());
						logger.info("GetSiteCode: " + siteTTXL);
						if (StringUtils.isNotBlank(siteTTXL)) {
							this.addObjToQueueJob(obj.getTransactionId(),
									QUEUE_TYPE_BF_CPD, siteTTXL, null);
						}
					}

					if (isHit) {
						jobStatus = JOB_STATE_CPD + STAGE_COMPLETED_WITH_HIT;
						updateStatus(obj.getWorkflowJobId(),
								STATUS_COMPLETED_WITH_HIT, CPD);
					} else {
						jobStatus = JOB_STATE_CPD + STAGE_COMPLETED;
						updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED,
								CPD);
					}
					uploadJobService.resetReprocessCount(
							obj.getWorkflowJobId(), 0);// [cw] 2016 Apr 26
					// logger.info("3_SS");
					inquirySearchResultService.saveSearchResult(searchResult,
							searchResult.getHitList());
					logger.info("isError === " + isError + ", isHit === "
							+ isHit);
					if (!isError && isHit && searchAfisData != null) {
						logger.info("WorkflowJobId: " + obj.getWorkflowJobId());
						logger.info("TransactionId: " + obj.getTransactionId());
						logger.info("AfisKeyNo: "
								+ searchAfisData.getAfisKeyNo());

						saveValidationResult(obj.getWorkflowJobId(),
								obj.getTransactionId(),
								searchAfisData.getAfisKeyNo(),
								VERIFICATION_REJECTED_APPLICATION
										+ DEMOGRAPHIC_CHECK_FAILED);
					}

					if (objW != null) {
						logger.info("WorkflowProcess: "
								+ obj.getTransactionId());
						List<NicWorkflowProcess> lstW_CPD = workflowProcessService
								.findWorkflowProcessByCriteria(NicWorkflowProcess.CHECK_CPD);
						if (lstW_CPD != null && lstW_CPD.size() > 0) {
							String caseCPD = NicWorkflowProcess.WORKFLOW_START_RESULT_NO;
							if (isHit) {
								caseCPD = NicWorkflowProcess.WORKFLOW_START_RESULT_YES;
							}

							NicWorkflowProcess objWs = null;
							for (NicWorkflowProcess it : lstW_CPD) {
								if (it.getWorkflowStartResult().equals(caseCPD)) {
									objWs = new NicWorkflowProcess();
									objWs = it;
									break;
								}
							}
							if (objWs != null) {
								logger.info("check step next in CHECK_CPD === "
										+ objWs.getWorkflowEnd());
								String assignName = "SYSTEM";
								Parameters parameter = parametersService
										.findById(new ParametersId(
												PARA_SCOPE_SYSTEM,
												PARA_NAME_ASSIGN_USER));
								assignName = parameter.getParaShortValue();

								if (StringUtils.isNotBlank(objWs
										.getWorkflowEnd())) {
									logger.info("WorkflowEnd === "
											+ objWs.getWorkflowEnd());
									switch (objWs.getWorkflowEnd()) {
									case NicWorkflowProcess.PERSO:
										try {
											uploadJobService
													.assignInvestigationOnlyJob(
															obj.getWorkflowJobId(),
															assignName,
															"SYSTEM",
															"BAF-NIC-DEV");
											nicTransactionService
													.updateOnApprove(
															obj.getTransactionId(),
															"SYSTEM",
															"BAF-NIC-DEV");
											// Xử lý gán điểm in cho bản ghi
											/*
											 * NicTransaction nicTrans =
											 * nicTransactionService
											 * .findById(obj
											 * .getTransactionId());
											 * List<NicPersoLocations> listPerso
											 * =
											 * persoLocationsService.findAll();
											 * if(listPerso != null &&
											 * listPerso.size() > 0){
											 * for(NicPersoLocations l :
											 * listPerso){
											 * if(!StringUtils.isEmpty
											 * (l.getIssuePlace())){ String[]
											 * arraySite =
											 * l.getIssuePlace().split(",");
											 * boolean contains =
											 * java.util.Arrays
											 * .asList(arraySite)
											 * .contains(nicTrans
											 * .getIssSiteCode()); if(contains){
											 * nicTrans
											 * .setPrintPerso(l.getIdPerso());
											 * break; } else {
											 * nicTrans.setPrintPerso((long) 1);
											 * } } } } else {
											 * nicTrans.setPrintPerso((long) 1);
											 * }
											 * nicTransactionService.saveOrUpdate
											 * (nicTrans);
											 */

											// Xử lý duyệt sang IN
											uploadJobService
													.approveJobStatus_Confirmed(
															Long.valueOf(obj
																	.getWorkflowJobId()),
															"SYSTEM",
															"BAF-NIC-DEV",
															NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
															NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRM_BOSS,
															NicUploadJob.RECORD_STATUS_APPROVE_PERSO);

											logger.info("process send to step PERSO");
										} catch (NicUploadJobServiceException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (JobNoLongerAssignedToOfficerException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										break;
									case NicWorkflowProcess.INVESTIGATION:
										logger.info("process send to step INVESTIGATION");
										uploadJobService
												.assignInvestigationOnlyJob(
														obj.getWorkflowJobId(),
														assignName, "SYSTEM",
														"BAF-NIC-DEV");
										break;
									case NicWorkflowProcess.PRESENT_APPROVAL:
										logger.info("process send to step PRESENT_APPROVAL");
										uploadJobService
												.assignInvestigationOnlyJob(
														obj.getWorkflowJobId(),
														assignName, "SYSTEM",
														"BAF-NIC-DEV");
										uploadJobService
												.approveJob(
														"Approval by SYSTEM",
														obj.getWorkflowJobId(),
														"SYSTEM",
														"BAF-NIC-DEV",
														NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
														NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED);
										break;
									case NicWorkflowProcess.LEADERS_APPROVAL:
										logger.info("process send to step LEADERS_APPROVAL");
										uploadJobService
												.assignInvestigationOnlyJob(
														obj.getWorkflowJobId(),
														assignName, "SYSTEM",
														"BAF-NIC-DEV");
										uploadJobService
												.approveJobStatus_Confirmed(
														Long.valueOf(obj
																.getWorkflowJobId()),
														"SYSTEM",
														"BAF-NIC-DEV",
														NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
														NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
														NicUploadJob.RECORD_STATUS_CONFIRMED);
										break;
									default:
										break;
									}
								} else {
									logger.info("Get data objWs WORKFLOW_END: NULL. Record will process default");
								}
							} else {
								logger.info("Get data objWs: NULL. Record will process default");
							}
						} else {
							logger.info("Get data WorkflowProcess_lstW_CPD: NULL. Record will process default");
						}
					} else {
						logger.info("Get data WorkflowProcess: NULL. Record will process default");
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception is occured", ex);
			jobStatus = JOB_STATE_CPD + STAGE_ERROR;
			logData = MiscXmlConvertor.parseObjectToXml(ex);

			this.setState(GOTO_ERROR_CMD);
			updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, CPD);
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true,
					uploadJobService);
		} finally {
			try {
				if (!checkW && objW != null) {
					/*
					 * List<Long> jobIds = new ArrayList<Long>();
					 * jobIds.add(obj.getWorkflowJobId());
					 * logger.info("process approve for record-01. jobIds: " +
					 * jobIds.size());
					 * logger.info("CollectionUtils. jobIds Boolean: " +
					 * CollectionUtils.isEmpty(jobIds));
					 */
					String assignName = "SYSTEM";
					Parameters parameter = parametersService
							.findById(new ParametersId(PARA_SCOPE_SYSTEM,
									PARA_NAME_ASSIGN_USER));
					assignName = parameter.getParaShortValue();
					logger.info("WorkflowEnd === " + objW.getWorkflowEnd());
					switch (objW.getWorkflowEnd()) {
					case NicWorkflowProcess.PERSO:
						try {
							uploadJobService.assignInvestigationOnlyJob(
									obj.getWorkflowJobId(), assignName,
									"SYSTEM", "BAF-NIC-DEV");
							nicTransactionService.updateOnApprove(
									obj.getTransactionId(), "SYSTEM",
									"BAF-NIC-DEV");
							// Xử lý gán điểm in cho bản ghi
							/*
							 * NicTransaction nicTrans =
							 * nicTransactionService.findById
							 * (obj.getTransactionId()); List<NicPersoLocations>
							 * listPerso = persoLocationsService.findAll();
							 * if(listPerso != null && listPerso.size() > 0){
							 * for(NicPersoLocations l : listPerso){
							 * if(!StringUtils.isEmpty(l.getIssuePlace())){
							 * String[] arraySite =
							 * l.getIssuePlace().split(","); boolean contains =
							 * java
							 * .util.Arrays.asList(arraySite).contains(nicTrans
							 * .getIssSiteCode()); if(contains){
							 * nicTrans.setPrintPerso(l.getIdPerso()); break; }
							 * else { nicTrans.setPrintPerso((long) 1); } } } }
							 * else { nicTrans.setPrintPerso((long) 1); }
							 * nicTransactionService.saveOrUpdate(nicTrans);
							 */

							// Xử lý duyệt sang IN
							uploadJobService
									.approveJobStatus_Confirmed(
											Long.valueOf(obj.getWorkflowJobId()),
											"SYSTEM",
											"BAF-NIC-DEV",
											NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
											NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRM_BOSS,
											NicUploadJob.RECORD_STATUS_APPROVE_PERSO);

							logger.info("process send to step PERSO");
						} catch (NicUploadJobServiceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JobNoLongerAssignedToOfficerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case NicWorkflowProcess.INVESTIGATION:
						uploadJobService.assignInvestigationOnlyJob(
								obj.getWorkflowJobId(), assignName, "SYSTEM",
								"BAF-NIC-DEV");
						break;
					case NicWorkflowProcess.PRESENT_APPROVAL:
						uploadJobService.assignInvestigationOnlyJob(
								obj.getWorkflowJobId(), assignName, "SYSTEM",
								"BAF-NIC-DEV");
						uploadJobService
								.approveJob(
										"Approval by SYSTEM",
										obj.getWorkflowJobId(),
										"SYSTEM",
										"BAF-NIC-DEV",
										NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
										NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED);
						break;
					case NicWorkflowProcess.LEADERS_APPROVAL:
						uploadJobService.assignInvestigationOnlyJob(
								obj.getWorkflowJobId(), assignName, "SYSTEM",
								"BAF-NIC-DEV");
						uploadJobService
								.approveJobStatus_Confirmed(
										Long.valueOf(obj.getWorkflowJobId()),
										"SYSTEM",
										"BAF-NIC-DEV",
										NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
										NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
										NicUploadJob.RECORD_STATUS_CONFIRMED);
						break;
					default:
						break;
					}
				}
				if (obj.getJobReprocessCount() != null && obj.getJobReprocessCount() > INT_PASS_RECOUNT_MAX && obj.getCpdCheckStatus() != null
						&& obj.getCpdCheckStatus().equals(STATUS_ERROR)) {

					jobStatus = JOB_STATE_CPD + STAGE_PASS_BY_RECOUNT_MAX;
					updateStatus(obj.getWorkflowJobId(),
							STATUS_PASS_BY_RECOUNT_MAX, CPD);
					// transactionStatus = JOB_STATE_INVESTIGATION +
					// STAGE_PASS_BY_RECOUNT_MAX;
					uploadJobService.resetReprocessCount(
							obj.getWorkflowJobId(), 0);
				}

				logger.info("[jobStatus CPD]: " + jobStatus);
				if (StringUtils.isNotBlank(obj.getTransactionId())
						&& StringUtils.isNotEmpty(jobStatus)) {
					uploadJobService.updateJobStatus(obj.getWorkflowJobId(),
							jobStatus);
					this.saveTransactionLog(obj.getTransactionId(),
							JOB_STATE_CPD, jobStatus, startTime, new Date(),
							null, logData, obj.getJobReprocessCount());
				}

			} catch (Exception e) {
				logger.error(
						"Exception in finally block (NicSubmitPersoCommand.doSomething)",
						e);
			}
		}
	}

	// Xu ly ket qua tu a08
	private void processResultInvestigationA08(SearchResultInA08Dto request,
			Collection<NicSearchHitResult> listCPD, String masterId) {
		try {
			// Lay ket qua tu a08
			if (listCPD != null) {
				logger.info("searchResult local ===" + masterId + "=="
						+ listCPD.size());
			}
			List<BufEppPersonInvestigation> list = inquirySearchResultService
					.getResultA08(request);
			if (list != null) {
				logger.info("searchResult a08 ===" + masterId + "=="
						+ list.size());
			}
			List<BufEppPersonInvestigation> inveslist = new ArrayList<BufEppPersonInvestigation>();
			if (list != null && list.size() > 0) {
				for (BufEppPersonInvestigation i : list) {
					i.setOrgPerson(i.getMaCaNhan());
					i.setTransactionMasterId(masterId);
					inveslist.add(i);
				}
			}
			if (listCPD != null && listCPD.size() > 0) {
				for (NicSearchHitResult item : listCPD) {
					Boolean exist = false;
					String codePersonHit = "";
					String orgPersonHit = "";
					String macanhanHit = "";
					BaseModelSingle<NicTransaction> hisTran = nicTransactionService
							.findTransactionById(item.getTransactionIdHit());
					if (hisTran != null && hisTran.getModel() != null) {
						codePersonHit = hisTran.getModel().getPersonCode();
						EppPerson personHit = eppPersonService
								.findPersonByPersonCode(codePersonHit);
						if (personHit != null
								&& StringUtils.isNotBlank(personHit
										.getOrgPerson())) {
							orgPersonHit = personHit.getOrgPerson();
						} else {
							orgPersonHit = codePersonHit;
						}
						// Tim ma ca nhan
						// if (StringUtils.isNotBlank(codePersonHit)) {
						// BaseModelSingle<EppPerson> person = eppPersonService
						// .findByStringCode(codePersonHit);
						// if (person != null && person.getModel() != null) {
						// macanhanHit = person.getModel().getRefId();
						// }
						// }
					}
					logger.info("TRANMASTERID: " + masterId + "=="
							+ "TransactionIdHit: " + item.getTransactionIdHit());
					/*
					 * if (list != null && list.size() > 0) { for
					 * (BufEppPersonInvestigation i : list) {
					 * logger.info("MaCaNhan: " + macanhanHit + " : " +
					 * i.getMaCaNhan()); if (StringUtils.isNotEmpty(macanhanHit)
					 * && macanhanHit.equals(i.getMaCaNhan())) { exist = true;
					 * break; } } }
					 */
					if (inveslist != null && inveslist.size() > 0) {
						for (BufEppPersonInvestigation i : inveslist) {
							logger.info("MaCaNhan: " + codePersonHit + " : "
									+ i.getMaCaNhan());
							if (StringUtils.isNotEmpty(codePersonHit)
									&& codePersonHit.equals(i.getMaCaNhan())
									|| (i.getMaCaNhan() != null && i.getMaCaNhan().equals(orgPersonHit)) 
									|| (i.getOrgPerson() != null && i.getOrgPerson().equals(orgPersonHit))) {
								i.setPersonCode(codePersonHit);
								exist = true;
								break;
							}
						}
					}
					if (!exist) {
						logger.info("TRANMASTERID: " + masterId
								+ " Start exist");
						DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						BufEppPersonInvestigation buff = new BufEppPersonInvestigation();
						NicUploadJob upload = uploadJobService
								.getUploadJobByTransactionIdSinger(null,
										item.getTransactionIdHit()).getModel();
						NicRegistrationData regis = upload.getNicTransaction()
								.getNicRegistrationData();
						buff.setTransactionMasterId(masterId);
						buff.setType("CPD");
						buff.setTransactionId(item.getTransactionIdHit());
						buff.setSrc("LOCAL");
						buff.setMaCaNhan(macanhanHit);
						buff.setPersonCode(codePersonHit);
						buff.setOrgPerson(orgPersonHit);
						buff.setName(HelperClass.createFullName(
								regis.getSurnameFull(),
								regis.getMiddlenameFull(),
								regis.getFirstnameFull()));
						buff.setOtherName(regis.getAliasName());
						buff.setGender(regis.getGender());
						buff.setDateOfBirth(regis.getDateOfBirth() != null ? DateUtil.parseDate(regis.getDateOfBirth(), DateUtil.FORMAT_YYYYMMDD) : "");
						String px = codesService.getCodeValueDescByIdName(
								"CODE_BirthPlace", regis.getPlaceOfBirth(), "");
						buff.setPlaceOfBirthName(px);
						buff.setIdNumber(upload.getNicTransaction().getNin());
						if (regis.getDateNin() != null)
							buff.setDateOfIdIssue(sdf.format(regis.getDateNin()));
						String nc = codesService.getCodeValueDescByIdName(
								"CODE_IDPlace", regis.getAddressNin(), "");
						buff.setPlaceOfIdIssueName(nc);
						String dt = codesService.getCodeValueDescByIdName(
								"CODE_NATION", regis.getNation(), "");
						buff.setEthnic(dt);
						String tg = codesService.getCodeValueDescByIdName(
								"CODE_RELIGION", regis.getReligion(), "");
						buff.setReligion(tg);
						buff.setSearchName(regis.getSearchName());
						String na = codesService.getCodeValueDescByIdName(
								"COUNTRY", regis.getNationality(), "");
						buff.setNationalityName(na);
						buff.setResidentPlaceName("");
						String detail = "";
						if (StringUtils.isNotBlank(regis.getAddressLine1())) {
							detail = regis.getAddressLine1();
						}
						String xp = codesService.getCodeValueDescByIdName(
								"VILLAGE", regis.getAddressLine2(), "");
						String qh = codesService.getCodeValueDescByIdName(
								"TOWN_VILLAGE", regis.getAddressLine3(), "");
						String th = codesService.getCodeValueDescByIdName(
								"DISTRICT", regis.getAddressLine4(), "");
						if (StringUtils.isNotBlank(xp)) {
							detail += ", " + xp;
						}
						if (StringUtils.isNotBlank(qh)) {
							detail += ", " + qh;
						}
						if (StringUtils.isNotBlank(th)) {
							detail += ", " + th;
						}
						buff.setResidentAddress(detail);
						String detail_temp = "";
						if (StringUtils
								.isNotBlank(regis.getAddressTempDetail())) {
							detail_temp = regis.getAddressTempDetail();
						}
						String xp_temp = codesService.getCodeValueDescByIdName(
								"VILLAGE", regis.getAddressTempVillage(), "");
						String qh_temp = codesService.getCodeValueDescByIdName(
								"TOWN_VILLAGE", regis.getAddressTempDistrict(),
								"");
						String th_temp = codesService.getCodeValueDescByIdName(
								"DISTRICT", regis.getAddressTempProvince(), "");
						if (StringUtils.isNotBlank(xp_temp)) {
							detail_temp += ", " + xp_temp;
						}
						if (StringUtils.isNotBlank(qh_temp)) {
							detail_temp += ", " + qh_temp;
						}
						if (StringUtils.isNotBlank(th_temp)) {
							detail_temp += ", " + th_temp;
						}
						buff.setTmpAddress(detail_temp);
						buff.setOccupation(regis.getJob());
						buff.setOfficeInfo(regis.getOfficeAddress());
						buff.setFatherName(regis.getFatherFullname());
						buff.setFatherNationality(regis.getFatherCitizenship());
						buff.setFatherOccupation("");
						buff.setMotherName(regis.getMotherFullname());
						buff.setMotherNationality(regis.getMotherCitizenship());
						buff.setMotherOccupation("");

						List<EppPerson> listPerson = eppPersonService
								.findListPersonByOrgPersonCode(codePersonHit);

						if (listPerson != null && listPerson.size() > 0) {
							List<NicTransaction> listNicTran = nicTransactionService
									.findTranByListPersonCode(listPerson);
							if (listNicTran != null && listNicTran.size() > 0) {
								List<NicDocumentData> listDocData = new ArrayList<NicDocumentData>();
								for (NicTransaction nicTran : listNicTran) {
									listDocData.addAll(documentDataService
											.findListDocByTranId(nicTran
													.getTransactionId()));
								}
								if (listDocData != null
										&& listDocData.size() > 0) {
									String passportNo = "";
									int index = 0;
									for (NicDocumentData nicDocumentData : listDocData) {
										if (nicDocumentData.getCancelType() != null && nicDocumentData.getCancelType().equals(NicDocumentData.DOCUMENT_DATA_CANCEL_TYPE_IN_HONG)) {
											continue;
										}
										if (index == 0) {
											passportNo = nicDocumentData
													.getId().getPassportNo();
										} else {
											passportNo += ","
													+ nicDocumentData.getId()
															.getPassportNo();
										}
										index++;
									}
									buff.setPassportNo(passportNo);
								}
							}
						}

						try {
							BaseModelList<NicTransactionAttachment> photo = transactionAttachmentService
									.findNicTransactionAttachments(
											item.getTransactionIdHit(),
											"PH_CAP", null);
							buff.setPhoto((photo != null
									&& photo.getListModel() != null && photo
									.getListModel().size() > 0) ? photo
									.getListModel().get(0).getDocData() : null);
						} catch (Exception e) {
						}
						buff.setCreateBy("SYSTEM");
						buff.setCreateDatetime(new Date());
						buff.setDataType("HC");
						inveslist.add(buff);
						logger.info("TRANMASTERID: " + masterId
								+ " End exist: " + inveslist.size());
					}
				}
			}
			bufPersonInvestigationService
					.deleteBufPersonByTranMasterId(masterId);
			if (inveslist != null && inveslist.size() > 0) {
				logger.info("TRANMASTERID: " + masterId + "SaveBuff: "
						+ inveslist.size());
				for (BufEppPersonInvestigation add : inveslist) {
					bufPersonInvestigationService.updateAndCreate(add);
				}
			} else {
				logger.info("TRANMASTERID: " + masterId + " ListBuff NULL.");
			}
		} catch (Exception e) {
			logger.error("TRANMASTERID: " + masterId + " ExceptionSaveBuff: "
					+ CreateMessageException.createMessageException(e));
		}
	}

	// check transaction age is below 7 years old.
	private boolean isTransactionFPExempted(NicTransaction transObj)
			throws TransactionServiceException {
		boolean exempted = false;
		Parameters parameter = null;
		Date dateOfIssue = null;
		Date dateOfBirth = null;
		int ageForFPExemption = 7;

		try {
			parameter = parametersService.findById(new ParametersId(
					PARA_SCOPE_APPLICATION, PARA_NAME_AGE_FOR_FP_EXEMPTION));
		} catch (Exception e) {

		}

		if (parameter != null) {
			Integer defaultAgeForFPExemption = (Integer) parameter
					.getParaValue();
			logger.info("Parameter defaultAgeForFPExemption: "
					+ defaultAgeForFPExemption);
			if (defaultAgeForFPExemption != null) {
				ageForFPExemption = defaultAgeForFPExemption.intValue();
			}
		}

		if (transObj != null && transObj.getDateOfApplication() != null) {
			dateOfIssue = transObj.getDateOfApplication(); // assume dateOfIssue
															// is equal and
															// later than
															// application date.
		}
		if (transObj != null && transObj.getNicRegistrationData() != null) {
			if (transObj.getNicRegistrationData().getDateOfBirth() != null) { // assume
																				// dateOfBirth
																				// instead
																				// of
																				// approxDOB
				dateOfBirth = transObj.getNicRegistrationData()
						.getDateOfBirth();
			}
		}
		if (dateOfIssue != null && dateOfBirth != null) {
			int numOfDays = DateUtil.dateDiff(dateOfBirth, dateOfIssue);

			if (numOfDays < ageForFPExemption * 365) {
				exempted = true;
			}
			logger.info(
					"Transaction [{}], applicant [DOB:{}] 's age [{}] on Date of issue [{}]. exempted: {} ",
					new Object[] { transObj.getTransactionId(), dateOfBirth,
							numOfDays / 365, dateOfIssue, exempted });
		}
		return exempted;
	}

	private void saveValidationResult(Long jobId, String transactionId,
			String afisKeyNo, String hitInfo)
			throws InquirySearchServiceException {
		logger.info("VALIDATION_START: " + transactionId);
		NicSearchResult latestResult = inquirySearchResultService
				.findLatestResultByJobId(jobId, "ERROR");
		if (latestResult != null
				&& CollectionUtils.isNotEmpty(latestResult.getHitList())) {
			logger.info("START_VALI: " + transactionId);
			NicSearchHitResult hitCandidate = latestResult.getHitList()
					.iterator().next();
			String dbHitInfo = hitCandidate.getHitInfo();
			logger.info("CHECK_EXIST_SHR: " + transactionId);
			if (StringUtils.indexOf(dbHitInfo,
					VERIFICATION_REJECTED_APPLICATION) != -1
					&& StringUtils.indexOf(hitInfo,
							VERIFICATION_REJECTED_APPLICATION) != -1) {
				logger.info("CASE_1: " + transactionId);
				String dbHitCategories = StringUtils.substringAfter(dbHitInfo,
						VERIFICATION_REJECTED_APPLICATION);
				String curHitCategory = StringUtils.substringAfter(hitInfo,
						VERIFICATION_REJECTED_APPLICATION);
				List<String> hitCategoryList = new ArrayList<String>();
				if (StringUtils.indexOf(dbHitCategories, ",") != -1) {
					hitCategoryList.addAll(Arrays.asList(StringUtils.split(
							dbHitCategories, ",")));
				} else {
					hitCategoryList.add(dbHitCategories);
				}
				logger.info(
						"Transaction [{}], search result {} with hitInfo {} ",
						new Object[] { transactionId,
								latestResult.getSearchResultId(), dbHitInfo });
				boolean recordExists = false;
				for (String hitCategory : hitCategoryList) {
					if (StringUtils.equals(curHitCategory, hitCategory)) {
						recordExists = true;
						break;
					}
				}
				if (!recordExists) {
					hitCategoryList.add(curHitCategory);
				}
				String finalHitInfo = "";
				for (String hitCategory : hitCategoryList) {
					if (StringUtils.isNotEmpty(finalHitInfo))
						finalHitInfo += ",";
					finalHitInfo += hitCategory;
				}
				finalHitInfo = VERIFICATION_REJECTED_APPLICATION + finalHitInfo;
				hitCandidate.setHitInfo(finalHitInfo);
				hitCandidate.setCreateDatetime(new Date());
				inquirySearchResultService.saveOrUpdate(latestResult);
				logger.info(
						"Transaction [{}], updated search result {} with hitInfo {} ",
						new Object[] { transactionId,
								latestResult.getSearchResultId(), finalHitInfo });
			}
		} else {
			logger.info("CASE_2: " + transactionId);
			// logger.info("2_SS");
			NicSearchResult searchResult = new NicSearchResult();
			searchResult.setWorkflowJobId(jobId);
			searchResult.setAfisKeyNo(afisKeyNo);
			searchResult.setTransactionId(transactionId);
			searchResult.setCreateDatetime(new Date());
			searchResult.setTypeSearch("ERROR");
			searchResult.setHasHit(true);
			NicSearchHitResult hitCandidate = new NicSearchHitResult();
			hitCandidate.setTransactionIdHit(transactionId); // Search Side's
																// transaction
																// id
			// hitCandidate.setAfisKeyNoHit(afisKeyNo);
			hitCandidate.setHitInfo(hitInfo);
			hitCandidate.setDataSource("CPD");
			hitCandidate.setCreateDatetime(new Date());
			hitCandidate.setHitDecision(true);
			logger.info("CHECK_STEP_1: " + transactionId);
			searchResult.getHitList().add(hitCandidate);
			logger.info("CHECK_STEP_2: " + transactionId);
			inquirySearchResultService.saveSearchResult(searchResult,
					searchResult.getHitList());
			logger.info(
					"Transaction [{}], saved search result {} with hitInfo {} ",
					new Object[] { transactionId,
							searchResult.getSearchResultId(), hitInfo });
		}
	}

	private NicSearchHitResult buildSearchHitResult(String transactionId,
			String afisKeyNo, String hitInfo, String dataSource) {
		NicSearchHitResult hitCandidate = new NicSearchHitResult();
		hitCandidate.setTransactionIdHit(transactionId); // Search Side's
															// transaction id
		hitCandidate.setAfisKeyNoHit(afisKeyNo);
		hitCandidate.setHitInfo(hitInfo);
		hitCandidate.setDataSource(STATE_NAME);
		hitCandidate.setCreateDatetime(new Date());
		hitCandidate.setHitDecision(true);
		return hitCandidate;
	}

	private Map<String, Object> constructDemographicCheckingFields(
			NicRegistrationData regData) {

		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		if (new Boolean((String) this.demographicFields.get(CHECK_SEARCH_NAME))) {
			if (StringUtils.isNotBlank(regData.getSearchName())) {
				fieldsMap.put(CHECK_SEARCH_NAME, regData.getSearchName());
			}
		}
		if (new Boolean((String) this.demographicFields.get(CHECK_BIRTH_DATE)))
			if (regData.getDateOfBirth() != null)
				fieldsMap.put(CHECK_BIRTH_DATE, regData.getDateOfBirth());

		if (new Boolean((String) this.demographicFields.get(CHECK_GENDER)))
			if (StringUtils.isNotBlank(regData.getGender()))
				fieldsMap.put(CHECK_GENDER, regData.getGender());

		return fieldsMap;
	}

	private void updateStatus(long objId, String state, int command) {

		try {
			// logger.info("\n---- updateStatus:"+objId+"/"+state+"/"+command);
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			logger.error("update status exception" + e.getMessage());
			e.printStackTrace();
		}
	}

	private String checkBiometricExists(
			Set<NicTransactionAttachment> attachmentList, boolean isFPExempted) {
		boolean capturePhotoExist = false;
		boolean chipPhotoExist = false;
		boolean fingerprintExist = false;
		StringBuffer rejectedReason = new StringBuffer();
		if (CollectionUtils.isEmpty(attachmentList)) {
			rejectedReason.append(MISSING_BIOMETRIC_DATA).append(",")
					.append(MISSING_PHOTO);
		} else {
			for (NicTransactionAttachment attachment : attachmentList) {
				if (StringUtils.equalsIgnoreCase(
						NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
						attachment.getDocType()))
					if (attachment.getDocData() != null
							&& attachment.getDocData().length > 100)
						capturePhotoExist = true;

				if (StringUtils.equalsIgnoreCase(
						NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
						attachment.getDocType()))
					if (attachment.getDocData() != null
							&& attachment.getDocData().length > 100)
						chipPhotoExist = true;

				if (StringUtils.equalsIgnoreCase(
						NicTransactionAttachment.DOC_TYPE_TPL,
						attachment.getDocType()))
					if (attachment.getDocData() != null
							&& attachment.getDocData().length > 0)
						fingerprintExist = true;

				if (capturePhotoExist && chipPhotoExist && fingerprintExist)
					break;

			}

			if (!capturePhotoExist || !chipPhotoExist || !fingerprintExist) {
				if (!fingerprintExist && !isFPExempted)
					rejectedReason.append(MISSING_BIOMETRIC_DATA).append(",");

				if (!capturePhotoExist)
					rejectedReason.append(MISSING_PHOTO).append(",");

				if (!chipPhotoExist)
					rejectedReason.append(MISSING_PHOTO_CHIP).append(",");

			}
		}

		return rejectedReason.toString();

	}

	private String checkRecordOnLookout(NicRegistrationData applicant) {
		StringBuffer rejectedReason = new StringBuffer();

		try {
			boolean isLookoutDataExist = this.nicRegistrationDataService
					.isExistOnLookoutData(applicant.getFirstnameFull(),
							applicant.getSurnameFull(),
							applicant.getMiddlenameFull());

			if (isLookoutDataExist)
				rejectedReason.append(BLACKLISTED).append(",");

		} catch (Exception ex) {

		}
		return rejectedReason.toString();

	}

	private void saveTransactionLog(String transactionId,
			String transactionStage, String transactionStatus, Date startTime,
			Date endTime, String logInfo, String logData, Integer retryCount) {
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
			nicTransactionLogService.save(transactionLog);
		}
	}

	private Boolean addObjToQueueJob(String transactionId, String ObjType,
			String receiver, String status) throws Exception {
		try {
			SyncQueueJob queue = new SyncQueueJob();
			queue.setCreatedTs(Calendar.getInstance().getTime());
			queue.setObjectId(transactionId);
			queue.setObjectType(ObjType);
			queue.setReceiver(receiver);
			queue.setTranStatus(status);
			queue.setStatus(CODE_STATUS_QUEUE_PENDING);
			queue.setSyncRetry(1);
			BaseModelSingle<Boolean> baseSaveQueue = queueJobService
					.saveOrUpdateQueue(queue);
			if (!baseSaveQueue.isError() || baseSaveQueue.getMessage() != null) {
				throw new Exception(baseSaveQueue.getMessage());
			}
			return baseSaveQueue.getModel();
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - addObjToQueueJob: " + transactionId
							+ " - thất bại.");
		}
	}

	private String responseSite(String regSiteCode) throws DaoException {
		String siteHanA = "";
		BaseModelSingle<ConfigurationWorkflow> cf = configurationWorkflowService
				.findSiteRepositoryBySite(regSiteCode, new Date(), "XL", true);
		if (cf.isError()) {
			ConfigurationWorkflow cfwA = cf.getModel();
			if (cfwA != null) {
				siteHanA = cfwA.getSiteIdTo();
				logger.info("GetSiteCode: " + siteHanA);
			} else {
				for (int i = 0; i < 3; i++) {
					SiteRepository site = siteService
							.getSiteRepoById(regSiteCode);
					if (site != null) {
						siteHanA = site.getSiteGroups().getGroupId();
					}
					logger.info("GetSiteCode: " + (i + 1) + siteHanA);
					if (StringUtils.isNotBlank(siteHanA)) {
						break;
					}
				}
			}
		}
		return siteHanA;
	}

	public void setCommandUtil(NicCommandUtil commandUtil) {
		this.nicCommandUtil = commandUtil;
	}

	public void setNicTransactionService(
			NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}

	public void setUploadJobService(NicUploadJobService uploadJobService) {
		this.uploadJobService = uploadJobService;
	}

	public void setNicTransactionLogService(
			TransactionLogService nicTransactionLogService) {
		this.nicTransactionLogService = nicTransactionLogService;
	}

	public void setNicRegistrationDataService(
			NicRegistrationDataService nicRegistrationDataService) {
		this.nicRegistrationDataService = nicRegistrationDataService;
	}

	public void setInquirySearchResultService(
			InquirySearchResultService inquirySearchResultService) {
		this.inquirySearchResultService = inquirySearchResultService;
	}

	public void setNicAfisDataService(NicAfisDataService nicAfisDataService) {
		this.nicAfisDataService = nicAfisDataService;
	}

	public void setDemographicFields(Map<String, String> demographicFields) {
		this.demographicFields = demographicFields;
	}

	public void setDocumentDataService(DocumentDataService documentDataService) {
		this.documentDataService = documentDataService;
	}

	public void setParametersService(ParametersService parametersService) {
		this.parametersService = parametersService;
	}

	public void setWorkflowProcessService(
			WorkflowProcessService workflowProcessService) {
		this.workflowProcessService = workflowProcessService;
	}

	public void setPersoLocationsService(
			PersoLocationsService persoLocationsService) {
		this.persoLocationsService = persoLocationsService;
	}

	public void setCodesService(CodesService codesService) {
		this.codesService = codesService;
	}

	public void setTransactionAttachmentService(
			NicTransactionAttachmentService transactionAttachmentService) {
		this.transactionAttachmentService = transactionAttachmentService;
	}

	public void setBufPersonInvestigationService(
			BufPersonInvestigationService bufPersonInvestigationService) {
		this.bufPersonInvestigationService = bufPersonInvestigationService;
	}

	public void setEppPersonService(EppPersonService eppPersonService) {
		this.eppPersonService = eppPersonService;
	}

	public void setQueueJobService(SyncQueueJobService queueJobService) {
		this.queueJobService = queueJobService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public void setConfigurationWorkflowService(
			ConfigurationWorkflowService configurationWorkflowService) {
		this.configurationWorkflowService = configurationWorkflowService;
	}

}
