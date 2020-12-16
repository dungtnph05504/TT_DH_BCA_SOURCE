package com.nec.asia.nic.comp.job.command;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.nec.asia.idserver.agent.payload.model.AbstractBiometricData;
import com.nec.asia.idserver.agent.payload.model.IdserverImageFingerprint;
import com.nec.asia.idserver.agent.payload.model.result.IdserverMatchResult;
import com.nec.asia.idserver.agent.payload.model.result.IdserverScore;
import com.nec.asia.idserver.agent.payload.model.result.IdserverSubject;
import com.nec.asia.idserver.agent.payload.model.type.IdserverImageType;
import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.idserver.agent.service.exception.IdserverAgentServiceException;
import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
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
import com.nec.asia.nic.comp.trans.dto.SearchResultInA08Dto;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.InquirySearchResultService;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.InquirySearchServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.util.MiscXmlConvertor;
import com.nec.asia.nic.utils.HelperClass;

/* 
 * Modification History:
 *  
 * 31 Dec 2015 (chris): init AfisScreening.
 * 24 Feb 2016 (chris): set hasHit base on search result
 * 28 Mar 2016 (chris): ignore no FP case (handled in CPD stage). 
 * 07 Apr 2016 (chris): add investigation reason as '08' when there is a hit.
 * 14 Apr 2016 (chris): update hit_info instead of create new hit_info when error.
 * 19 Apr 2016 (chris): set update datetime and update by when update transaction status
 * 26 Apr 2016 (chris): reset job reprocess count
 * 13 May 2016 (chris): handle duplicate transaction not flag by system for renewal case, assume older passport should be archived in AFIS DB.
 * 19 May 2016 (chris): bug fix for 13 May, self-hit transaction should not be flagged.
 * 06 Jun 2016 (chris): handle fp is null without fingerprint count as 0
 */

public class NicAfisScreenAsyncCommand extends BaseCommand<NicUploadJob>
		implements Command<NicUploadJob> {

	private NicCommandUtil nicCommandUtil;

	// UPDATE STATUS CODE
	private static final int JOB_STATE = 1;
	private static final int AFIS_SCREEN = 4;

	// JOB STATE
	private static final String JOB_STATE_AFIS_CHECK = "AFIS_CHECK";
	private static final String STAGE_WIP = "_WIP";

	private static final String VERIFICATION_REJECTED_APPLICATION = "REJECTED APPLICATION=";
	private static final String CODE_VALUE_ID_FP_SCREENING_HAS_HIT = "08"; // Applicant
																			// and
																			// hit
																			// candidate
																			// flag
																			// as
																			// possible
																			// same
																			// person
																			// after
																			// fingerprint
																			// matching
																			// (FP
																			// 1:N).

	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private InquirySearchResultService inquirySearchResultService;
	private TransactionLogService nicTransactionLogService;

	private IdserverAgentService idserverAgentService;
	private NicAfisDataService nicAfisDataService;
	private CodesService codesService;
	private BufPersonInvestigationService bufPersonInvestigationService;
	private EppPersonService eppPersonService;
	private NicTransactionAttachmentService transactionAttachmentService;

	@Override
	public void doSomething(NicUploadJob job) {

		logger.info("inside NicAfisScreenAsyncCommand:{}",
				job.getTransactionId());

		String logData = null;
		String logInfo = null;
		Date startTime = new Date();
		String transactionStatus = null;

		Long jobId = job.getWorkflowJobId();

		this.setState(job.getJobType());// set job type as child key

		try {
			// logger.info("-----[{}] job.xml:{}", job.getTransactionId(),
			// MiscXmlConvertor.parseObjectToXml(job));
			// NicUploadJob jobDBO =
			// uploadJobService.findById(job.getWorkflowJobId());
			// logger.info("-----[{}] job.xml:{}", jobDBO.getTransactionId(),
			// MiscXmlConvertor.parseObjectToXml(jobDBO));

			updateStatus(jobId, JOB_STATE_AFIS_CHECK, JOB_STATE);
			NicTransaction transObj = nicTransactionService.findById(
					job.getTransactionId(), false, false, true, false);// has
																		// registration
																		// data
			logger.info("-----[{}] jobDBO.getJobReprocessCount():{}",
					job.getTransactionId(), job.getJobReprocessCount());
			if (StringUtils.equals(job.getAfisCheckStatus(), STATUS_COMPLETED)
					|| StringUtils.equals(job.getAfisCheckStatus(),
							STATUS_COMPLETED_WITH_HIT)
					|| StringUtils.equals(job.getAfisCheckStatus(),
							STATUS_NO_REQUIRED)) {

				// AFIS CHECK has been completed before, skip step
				logger.info(
						"-----[{}] skipping AFIS CHECK, status: {}, continue to next step",
						transObj.getTransactionId(), job.getAfisCheckStatus());
			} else if (StringUtils.equals(job.getAfisCheckStatus(),
					STATUS_PASS_BY_RECOUNT_MAX)) {

				// AFIS CHECK has been completed before, skip step
				logger.info(
						"-----[{}] pass by recount max, status: {}, continue to next step",
						transObj.getTransactionId(), job.getAfisCheckStatus());
			} else if (job.getJobReprocessCount() == null
					|| job.getJobReprocessCount().equals(0)) {
				// AFIS CHECK will be forced queued on first attempt to await
				// data sync in BMS.
				logger.info(
						"-----[{}] AFIS CHECK will be forced queued on first attempt to await data sync in BMS. count is: {}",
						transObj.getTransactionId(), job.getJobReprocessCount());
				this.setState(GOTO_ERROR_CMD);
				updateStatus(jobId, STATUS_ERROR, AFIS_SCREEN);
			} else if (transObj != null
					&& transObj.getNicRegistrationData() != null
					&& (transObj.getNicRegistrationData().getTotalFp() == null
							|| transObj.getNicRegistrationData().getTotalFp()
									.intValue() == 0 || this
							.buildFingerprintTemplatesByTransactionId(job
									.getTransactionId()) == null)) {
				logger.warn(
						"-----[{}] not finger captured, transaction passed to investigation if required.",
						transObj.getTransactionId());
				String logContent = "No Fingers captured";

				transactionStatus = JOB_STATE_AFIS_CHECK + WITHOUT_FP;

				logInfo = "<logInfoDTO><Reason>No FP captured</Reason><Remark>System detected: "
						+ logContent + "</Remark></logInfoDTO>";
				// updateStatus(jobId, STATUS_COMPLETED_WITH_HIT, AFIS_SCREEN);
				updateStatus(jobId, STATUS_COMPLETED, AFIS_SCREEN); // chris -
																	// 20160328
																	// - flag to
																	// investigation
																	// in CPD
																	// stage
																	// instead
																	// of AFIS
																	// stage.

				// update nic_transaction.transaction_status field
				// transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION);
				// nicTransactionService.saveOrUpdate(transObj);
			} else {
				updateStatus(jobId, STATUS_INPROGRESS, AFIS_SCREEN);
				// update nic_transaction.transaction_status field
				/* Tạm đóng cập nhật trạng thái hồ sơ 04.08.2020 */
				// transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_SCREENING);
				// nicTransactionService.saveOrUpdate(transObj);
				// [cw][2016 Apr 19] to update both update datetime, and status.
				// nicTransactionService.updateStatusByTransactionId(
				// job.getTransactionId(),
				// NicTransactionService.TRANSACTION_STATUS_NIC_SCREENING,
				// nicCommandUtil.getSystemName(),
				// nicCommandUtil.getHostName());

				logger.info("-----[{}] before screening:",
						transObj.getTransactionId());
				boolean hasHit = true;
				boolean isSearchWIP = false;
				// 1) retrieve and check search result status
				NicSearchResult latestResult = inquirySearchResultService
						.findLatestResultByJobId(jobId,
								InquirySearchResultService.SCREENING_FP_1_TO_N);
				if (latestResult != null) {
					if (StringUtils.isNotBlank(latestResult
							.getSearchReferenceId())) {
						if (StringUtils.equals(
								NicSearchResult.SEARCH_STATUS_SUBMITTED,
								latestResult.getSearchStatus())) {
							// 3) retrieve search result
							String receiptNo = latestResult
									.getSearchReferenceId();
							IdserverMatchResult matchResult = idserverAgentService
									.retrieveHitResult(receiptNo);
							logger.info("count tran bms return === "
									+ matchResult.getSubjectList().size());
							hasHit = this.updateSearchResult(latestResult,
									matchResult, transObj.getPrevPassportNo(),
									job.getTransactionId());
						} else if (StringUtils.equals(
								NicSearchResult.SEARCH_STATUS_COMPLETED,
								latestResult.getSearchStatus())
								&& job.getWorkflowJobRunAgainCount() == 2) {
							/* Phúc thêm trường hợp check lại BMS lần 2 7/1/2020 */
							logger.info("check bms again after save handoverA.");
							afis1NScreeningByAsync(transObj, job);
							isSearchWIP = true;
						} else if (latestResult.getHasHit() != null) {
							hasHit = latestResult.getHasHit().booleanValue();
						}
					}
				} else {
					// 2) submit search
					afis1NScreeningByAsync(transObj, job);
					isSearchWIP = true;
				}

				if (isSearchWIP) {
					this.setState(GOTO_ERROR_CMD);
					updateStatus(jobId, STATUS_ERROR, AFIS_SCREEN);
					transactionStatus = JOB_STATE_AFIS_CHECK + STAGE_WIP;
				} else {
					if (!hasHit) {
						logger.info("-----[{}] screening is passed",
								transObj.getTransactionId());

						updateStatus(jobId, STATUS_COMPLETED, AFIS_SCREEN);
						transactionStatus = JOB_STATE_AFIS_CHECK
								+ STAGE_COMPLETED;
						// nicTransactionService.saveOrUpdate(transObj);
						uploadJobService.resetReprocessCount(jobId, 0);
						logger.info("flag6");

					} else {
						logger.info(
								"-----[{}] screening is passed to investigation",
								transObj.getTransactionId());

						transactionStatus = JOB_STATE_AFIS_CHECK
								+ STAGE_COMPLETED_WITH_HIT;
						updateStatus(jobId, STATUS_COMPLETED_WITH_HIT,
								AFIS_SCREEN);
						uploadJobService.resetReprocessCount(jobId, 0); // [cw]
																		// 2016
																		// Apr
																		// 26
						// updateStatus(jobId, STATUS_INITIAL, INVESTIGATION);
						// updateStatus(jobId, null, AFIS_DELETE);
						// this.setState(GOTO_ERROR_CMD);

						// 02SEP2013 - JP - update
						// nic_transaction.transaction_status field
						// transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION);
						// nicTransactionService.saveOrUpdate(transObj);

						// [cw][2016 Apr 19] to update both update datetime, and
						// status.
						/* Tạm đóng cập nhật trạng thái. 04.08.2020 */
						// nicTransactionService.updateStatusByTransactionId(job.getTransactionId(),
						// NicTransactionService.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION,
						// nicCommandUtil.getSystemName(),
						// nicCommandUtil.getHostName());

					}
				}
			}

		} catch (Exception e) {
			logger.error("e === " + e.getMessage());
			logger.info("job_ReprocessCount === " + job.getJobReprocessCount());
			if (job.getJobReprocessCount() > INT_PASS_RECOUNT_MAX
					&& job.getAfisCheckStatus().equals(STATUS_ERROR)) {
				updateStatus(job.getWorkflowJobId(),
						STATUS_PASS_BY_RECOUNT_MAX, AFIS_SCREEN);
				transactionStatus = JOB_STATE_AFIS_CHECK
						+ STAGE_PASS_BY_RECOUNT_MAX;
				try {
					uploadJobService.resetReprocessCount(
							job.getWorkflowJobId(), 0);
				} catch (Exception ex) {
					logger.error("ex === " + ex.getMessage());

				}
			} else {
				logger.error("flag EXCEPTION:" + e.getMessage());
				e.printStackTrace();
				transactionStatus = JOB_STATE_AFIS_CHECK + STAGE_ERROR;
				logData = MiscXmlConvertor.parseObjectToXml(e);
				logInfo = e.getMessage();
				this.setState(GOTO_ERROR_CMD);
				updateStatus(jobId, STATUS_ERROR, AFIS_SCREEN);
				nicCommandUtil.setErrorFlag(jobId, true, uploadJobService);
			}
		} finally {
			try {
				if (job.getAfisCheckStatus().equals(STAGE_ERROR)) {
					if (job.getJobReprocessCount() != null
							&& job.getJobReprocessCount() > INT_PASS_RECOUNT_MAX) {
						updateStatus(job.getWorkflowJobId(),
								STATUS_PASS_BY_RECOUNT_MAX, AFIS_SCREEN);
						transactionStatus = JOB_STATE_AFIS_CHECK
								+ STAGE_PASS_BY_RECOUNT_MAX;
					}
					uploadJobService.resetReprocessCount(
							job.getWorkflowJobId(), 0);
				}

				if (StringUtils.isNotBlank(job.getTransactionId())
						&& StringUtils.isNotEmpty(transactionStatus)) {

					uploadJobService.updateJobStatus(jobId, transactionStatus);
					this.saveTransactionLog(job.getTransactionId(),
							JOB_STATE_AFIS_CHECK, transactionStatus, startTime,
							new Date(), logInfo, logData,
							job.getJobReprocessCount());
				}
			} catch (Exception e) {
				logger.error(
						"Exception in finally block (NicScreenAfisCommand.doSomething)",
						e);
			}
		}
	}

	private void afis1NScreeningByAsync(NicTransaction transObj,
			NicUploadJob obj) throws Exception {
		logger.debug("-----[{}] in afis1NScreeningByAsync",
				transObj.getTransactionId());
		try {
			String transactionId = transObj.getTransactionId();
			IdserverImageFingerprint tpl = this
					.buildFingerprintTemplatesByTransactionId(transactionId);
			List<AbstractBiometricData<IdserverImageFingerprint>> inputList = new ArrayList<AbstractBiometricData<IdserverImageFingerprint>>();
			inputList.add(tpl);

			String receiptNo = idserverAgentService.inquiryFpScreeningAsync(
					transactionId, inputList);
			long saveResult = createMatchResult(receiptNo, transObj, obj);
			logger.info("save search results output:" + saveResult);
		} catch (IdserverAgentServiceException ie) {
			throw new IdserverAgentServiceException(ie);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	private IdserverImageFingerprint buildFingerprintTemplatesByTransactionId(
			String transactionId) {
		IdserverImageFingerprint tpl = null;
		try {
			String docType = NicTransactionAttachment.DOC_TYPE_TPL;
			String serialNo = NicTransactionAttachment.DEFAULT_SERIAL_NO;
			List<NicTransactionAttachment> transDocsList = transactionAttachmentService
					.findNicTransactionAttachments(transactionId, docType,
							serialNo).getListModel();

			logger.info("transaction - {} , docSize: {}", transactionId,
					transDocsList != null ? transDocsList.size() : 0);
			for (NicTransactionAttachment tDoc : transDocsList) {
				if (StringUtils.equalsIgnoreCase(tDoc.getDocType(), "TPL")) {
					logger.info("setting TPL - {} ", transactionId);
					tpl = new IdserverImageFingerprint();
					tpl.setFingerPosition((short) 99);
					tpl.setImageType(IdserverImageType.TPL);
					tpl.setImageData(tDoc.getDocData());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("tpl exists:" + (tpl != null));
		return tpl;
	}

	private long createMatchResult(String receiptNo, NicTransaction transDocs,
			NicUploadJob job) throws Exception {
		long saveResult = 0;
		try {
			logger.info("transid:" + transDocs.getTransactionId());
			NicAfisData afisData = nicAfisDataService.findById(transDocs
					.getTransactionId());
			String afisKeyNo = afisData.getAfisKeyNo();

			NicSearchResult searchResult = new NicSearchResult();
			searchResult
					.setTypeSearch(InquirySearchResultService.SCREENING_FP_1_TO_N);
			searchResult.setAfisKeyNo(afisKeyNo);
			searchResult.setCreateDatetime(new Date());
			searchResult.setSearchReferenceId(receiptNo);
			searchResult
					.setSearchStatus(NicSearchResult.SEARCH_STATUS_SUBMITTED);
			searchResult.setTransactionId(transDocs.getTransactionId());
			searchResult.setWorkflowJobId(job.getWorkflowJobId());

			saveResult = inquirySearchResultService.save(searchResult);

			logger.info("result:" + saveResult);
			return saveResult;
		} catch (Exception ie) {
			throw new InquirySearchServiceException(ie);
		}
	}

	private void saveValidationResult(Long jobId, String transactionId,
			String afisKeyNo, String hitInfo)
			throws InquirySearchServiceException {
		NicSearchResult latestResult = inquirySearchResultService
				.findLatestResultByJobId(jobId, "ERROR");
		if (latestResult != null
				&& CollectionUtils.isNotEmpty(latestResult.getHitList())) {
			NicSearchHitResult hitCandidate = latestResult.getHitList()
					.iterator().next();
			String dbHitInfo = hitCandidate.getHitInfo();
			if (StringUtils.indexOf(dbHitInfo,
					VERIFICATION_REJECTED_APPLICATION) != -1
					&& StringUtils.indexOf(hitInfo,
							VERIFICATION_REJECTED_APPLICATION) != -1) {
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
				Collections.sort(hitCategoryList);

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
			NicSearchResult searchResult = new NicSearchResult();
			searchResult.setWorkflowJobId(jobId);
			searchResult.setAfisKeyNo(afisKeyNo);
			searchResult.setTransactionId(transactionId);
			searchResult.setCreateDatetime(new Date());
			searchResult.setTypeSearch("ERROR");// InquirySearchResultService.VERIFICATION_FP_1_TO_1);
			searchResult.setHasHit(true);

			NicSearchHitResult hitCandidate = new NicSearchHitResult();
			hitCandidate.setTransactionIdHit(transactionId); // Search Side's
																// transaction
																// id
			// hitCandidate.setAfisKeyNoHit(afisKeyNo);
			hitCandidate.setHitInfo(hitInfo);
			hitCandidate.setDataSource("ERROR");
			hitCandidate.setCreateDatetime(new Date());
			hitCandidate.setHitDecision(true);
			searchResult.getHitList().add(hitCandidate);

			inquirySearchResultService.saveSearchResult(searchResult,
					searchResult.getHitList());
			logger.info(
					"Transaction [{}], saved search result {} with hitInfo {} ",
					new Object[] { transactionId,
							searchResult.getSearchResultId(), hitInfo });
		}
	}

	private boolean updateSearchResult(NicSearchResult searchResultDBO,
			IdserverMatchResult matchResult, String prevPassportNo,
			String masterId) throws Exception {
		boolean hasHit = false;

		try {
			Long searchResultId = searchResultDBO.getSearchResultId();

			String afisKeyNo = searchResultDBO.getAfisKeyNo();
			String transactionId = searchResultDBO.getTransactionId(); // 19 May
																		// 2016
																		// (chris)
																		// -
																		// skip
																		// self
																		// transaction
																		// id
			matchResult = this.cleanUpMatchResult(matchResult, afisKeyNo,
					transactionId, prevPassportNo);

			searchResultDBO
					.setSearchStatus(NicSearchResult.SEARCH_STATUS_COMPLETED);

			List<NicSearchHitResult> hitList = new ArrayList<NicSearchHitResult>();
			if (matchResult != null
					&& CollectionUtils.isNotEmpty(matchResult.getSubjectList())) {
				NicSearchHitResult hitResult = null;
				for (IdserverSubject subject : matchResult.getSubjectList()) {
					hitResult = new NicSearchHitResult();
					hitResult.setTransactionIdHit(subject.getRegistrationId());
					hitResult.setAfisKeyNoHit(subject.getPersonIdentifier());
					hitResult.setDataSource(subject.getDataSource());
					hitResult.setHitDecision(subject.getHitDecision());
					// hitResult.setSearchResult(searchResultDBO);
					hitResult.setSearchResultId(searchResultDBO
							.getSearchResultId());
					hitResult.setCreateDatetime(new Date());
					String hitInfo = "";
					for (IdserverScore score : subject.getScoreList()) {
						if (StringUtils.isNotBlank(hitInfo)) {
							hitInfo += ",";
						}
						hitInfo += score.getFingerPosition() + "="
								+ score.getScore();
					}

					hitResult.setHitInfo(hitInfo);
					hitList.add(hitResult);
				}
			}
			searchResultDBO.setHasHit(hitList.size() > 0);
			searchResultDBO.getHitList().addAll(hitList);
			inquirySearchResultService.saveOrUpdate(searchResultDBO);
			hasHit = searchResultDBO.getHasHit();
			logger.info("searchResultId:{}, hitList.size:{}, return:{}",
					new Object[] { searchResultId, hitList.size(), hasHit });
			if (hasHit) {
				String hitInfo = VERIFICATION_REJECTED_APPLICATION
						+ CODE_VALUE_ID_FP_SCREENING_HAS_HIT;
				this.saveValidationResult(searchResultDBO.getWorkflowJobId(),
						searchResultDBO.getTransactionId(),
						searchResultDBO.getAfisKeyNo(), hitInfo);
			}
			processResultInvestigationA08(hitList, masterId);
			return hasHit;
		} catch (Exception e) {
			logger.warn("Error in updateSearchResult(): {}", e.getMessage());
			throw e;
		}
	}

	// Đưa kết quả AFIS check vào bảng BUFFER
	private void processResultInvestigationA08(
			Collection<NicSearchHitResult> listCPD, String masterId) {
		try {
			// Lay ket qua tu a08
			SearchResultInA08Dto request = new SearchResultInA08Dto();
			List<BufEppPersonInvestigation> inveslist = new ArrayList<BufEppPersonInvestigation>();

			if (listCPD != null && listCPD.size() > 0) {
				for (NicSearchHitResult item : listCPD) {
					Boolean exist = false;
					String codePersonHit = "";
					String macanhanHit = "";
					BaseModelSingle<NicTransaction> hisTran = nicTransactionService
							.findTransactionById(item.getTransactionIdHit());
					if (hisTran != null && hisTran.getModel() != null) {
						codePersonHit = hisTran.getModel().getPersonCode();
						// Tim ma ca nhan
						BaseModelSingle<EppPerson> person = eppPersonService
								.findByStringCode(codePersonHit);
						if (person != null && person.getModel() != null) {
							macanhanHit = person.getModel().getRefId();
						}
					}

					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					BufEppPersonInvestigation buff = new BufEppPersonInvestigation();
					NicUploadJob upload = uploadJobService
							.getUploadJobByTransactionIdSinger(null,
									item.getTransactionIdHit()).getModel();
					NicRegistrationData regis = upload.getNicTransaction()
							.getNicRegistrationData();
					buff.setTransactionMasterId(masterId);
					buff.setTransactionId(item.getTransactionIdHit());
					buff.setType("AFIS");
					buff.setSrc("LOCAL");
					buff.setMaCaNhan(macanhanHit);
					buff.setPersonCode(codePersonHit);
					buff.setName(HelperClass.createFullName(
							regis.getSurnameFull(), regis.getMiddlenameFull(),
							regis.getFirstnameFull()));
					buff.setOtherName(regis.getAliasName());
					buff.setGender(regis.getGender());
					buff.setDateOfBirth(sdf.format(regis.getDateOfBirth())
							.replace("-", ""));
					String px = codesService.getCodeValueDescByIdName(
							"CODE_BirthPlace", regis.getPlaceOfBirth(), "");
					buff.setPlaceOfBirthName(px);
					buff.setIdNumber(upload.getNicTransaction().getNin());
					if (regis.getDateNin() != null)
						buff.setDateOfIdIssue(sdf.format(regis.getDateNin())
								.replace("-", ""));
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
					String detail = regis.getAddressLine1() + ", ";
					String xp = codesService.getCodeValueDescByIdName(
							"VILLAGE", regis.getAddressLine2(), "") + ", ";
					String qh = codesService.getCodeValueDescByIdName(
							"TOWN_VILLAGE", regis.getAddressLine3(), "") + ", ";
					String th = codesService.getCodeValueDescByIdName(
							"DISTRICT", regis.getAddressLine4(), "") + ", ";
					buff.setResidentAddress(detail + xp + qh + th);
					String detail_temp = regis.getAddressTempDetail() + ", ";
					String xp_temp = codesService.getCodeValueDescByIdName(
							"VILLAGE", regis.getAddressTempVillage(), "")
							+ ", ";
					String qh_temp = codesService.getCodeValueDescByIdName(
							"TOWN_VILLAGE", regis.getAddressTempDistrict(), "")
							+ ", ";
					String th_temp = codesService.getCodeValueDescByIdName(
							"DISTRICT", regis.getAddressTempProvince(), "")
							+ ", ";
					buff.setTmpAddress(detail_temp + xp_temp + qh_temp
							+ th_temp);
					buff.setOccupation(regis.getJob());
					buff.setOfficeInfo(regis.getOfficeAddress());
					buff.setFatherName(regis.getFatherFullname());
					buff.setFatherNationality(regis.getFatherCitizenship());
					buff.setFatherOccupation("");
					buff.setMotherName(regis.getMotherFullname());
					buff.setMotherNationality(regis.getMotherCitizenship());
					buff.setMotherOccupation("");
					try {
						BaseModelList<NicTransactionAttachment> photo = transactionAttachmentService
								.findNicTransactionAttachments(
										item.getTransactionIdHit(), "PH_CAP",
										null);
						buff.setPhoto((photo != null
								&& photo.getListModel() != null && photo
								.getListModel().size() > 0) ? photo
								.getListModel().get(0).getDocData() : null);
					} catch (Exception e) {
					}
					buff.setCreateBy("SYSTEM");
					buff.setCreateDatetime(new Date());
					long score = 0;
					// Tính số điểm trung bình cho vân tay
					// Kiểm tra chuỗi dữ liệu
					if (StringUtils.isNotEmpty(item.getHitInfo())) {
						String[] listHit = item.getHitInfo().split(","); // Tách
																			// chuỗi
																			// theo
																			// dấu
																			// ","
						if (listHit.length > 0) {
							for (int i = 0; i < listHit.length; i++) {
								if (Double
										.parseDouble((listHit[i].split("=")[1])) > score) {
									double d = Double.parseDouble((listHit[i]
											.split("=")[1]));
									score = (long) d;
								}
							}
						}
						buff.setMatchPoint(score);
					}
					inveslist.add(buff);
				}
				if (inveslist != null && inveslist.size() > 0) {
					for (BufEppPersonInvestigation add : inveslist) {
						bufPersonInvestigationService.updateAndCreate(add);
					}
				}
			}
		} catch (Exception e) {

		}
	}

	private IdserverMatchResult cleanUpMatchResult(
			IdserverMatchResult matchResult, String afisKeyNo,
			String transactionId, String prevPassportNo)
			throws TransactionServiceException {
		IdserverMatchResult updatedMatchResult = null;
		int hitCount = 0;
		if (matchResult != null
				&& CollectionUtils.isNotEmpty(matchResult.getSubjectList())) {
			updatedMatchResult = new IdserverMatchResult();
			for (IdserverSubject hit : matchResult.getSubjectList()) {
				String transactionIdHit = hit.getRegistrationId();
				String afisKeyNoHit = hit.getPersonIdentifier();
				boolean isSkip = false;
				// 13 May 2016 (chris) - only skip the declared passport number
				// if (StringUtils.equals(afisKeyNo, afisKeyNoHit)) {
				// isSkip = true;
				// logger.debug("transactionIdHit :{}, having same afisKeyNo {} with searchRecord.",
				// transactionIdHit, afisKeyNo);
				// }
				// NicTransaction hitTransaction =
				// nicTransactionService.findById(transactionIdHit, false,
				// false, false, false);
				// 19 May 2016 (chris) - skip self transaction id
				if (StringUtils.equals(transactionId, transactionIdHit)) {
					isSkip = true;
				}

				NicTransaction hitTransaction = nicTransactionService.findById(
						transactionIdHit, true, false, false, false);
				if (hitTransaction == null) {
					isSkip = true;
					logger.warn(
							"transactionIdHit :{}, {} doesn't exist! removing from hit list.",
							transactionIdHit, afisKeyNoHit);
				} else {
					// 13 May 2016 (chris) - only skip the declared passport
					// number
					if (StringUtils.equals(afisKeyNo, afisKeyNoHit)) {
						logger.debug(
								"transactionIdHit :{}, having same afisKeyNo {} with searchRecord.",
								transactionIdHit, afisKeyNo);
						if (CollectionUtils.isNotEmpty(hitTransaction
								.getNicDocumentDatas())) {
							for (NicDocumentData documentData : hitTransaction
									.getNicDocumentDatas()) {
								if (StringUtils.equalsIgnoreCase(
										prevPassportNo, documentData.getId()
												.getPassportNo())) {
									logger.debug(
											"transactionIdHit :{}, having same passport {} with searchRecord's declared passport.",
											transactionIdHit, documentData
													.getId().getPassportNo());
									isSkip = true;
								}
							}
						}

					}
				}

				if (!isSkip) {
					updatedMatchResult.setHasHit(true);
					updatedMatchResult.getSubjectList().add(hit);
					hitCount++;
				}
			}
		}
		logger.info("hitCount:" + hitCount);
		return updatedMatchResult;
	}

	private void updateStatus(long objId, String state, int command) {
		try {
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			logger.info("exception in updateStatus:" + e.getMessage());
			e.printStackTrace();
		}
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

	public void setCommandUtil(NicCommandUtil commandUtil) {
		this.nicCommandUtil = commandUtil;
	}

	public void setIdserverAgentService(
			IdserverAgentService idserverAgentService) {
		this.idserverAgentService = idserverAgentService;
	}

	public void setNicTransactionService(
			NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}

	public void setUploadJobService(NicUploadJobService uploadJobService) {
		this.uploadJobService = uploadJobService;
	}

	public void setInquirySearchResultService(
			InquirySearchResultService inquirySearchResultService) {
		this.inquirySearchResultService = inquirySearchResultService;
	}

	public void setNicTransactionLogService(
			TransactionLogService nicTransactionLogService) {
		this.nicTransactionLogService = nicTransactionLogService;
	}

	public void setNicAfisDataService(NicAfisDataService nicAfisDataService) {
		this.nicAfisDataService = nicAfisDataService;
	}

	public void setTransactionAttachmentService(
			NicTransactionAttachmentService transactionAttachmentService) {
		this.transactionAttachmentService = transactionAttachmentService;
	}

	public void setCodesService(CodesService codesService) {
		this.codesService = codesService;
	}

	public void setBufPersonInvestigationService(
			BufPersonInvestigationService bufPersonInvestigationService) {
		this.bufPersonInvestigationService = bufPersonInvestigationService;
	}

	public void setEppPersonService(EppPersonService eppPersonService) {
		this.eppPersonService = eppPersonService;
	}
}
