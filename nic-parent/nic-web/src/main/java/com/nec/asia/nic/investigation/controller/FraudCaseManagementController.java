package com.nec.asia.nic.investigation.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;


import com.nec.asia.nic.comp.job.dto.SearchHitDto;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.exception.IssuanceServiceException;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.common.ImageUtil;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.investigation.Base64Jp2HeaderHelper;
import com.nec.asia.nic.util.CitizenRefXmlConvertor;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;

/**
 * investigation controller
 *
 * @author
 */
/*
 * Modification History: xx mm yyyy (who): remarks here
 */

@Controller
@RequestMapping(value = "/fraudCaseManagement")
public class FraudCaseManagementController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(FraudCaseManagementController.class);

	private static final String PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE = "SYSTEM";
	private static final String PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE = "TRANSACT2_ATTACHM_IGNORE_J2K";

	private static final String PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_SCOPE = "SYSTEM";
	private static final String PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_VALUE = "TX_ATTCH_J2K_CONVERT_AT_SERVER";

	public static JobXmlConvertor jobUtil = new JobXmlConvertor();

	public static CitizenRefXmlConvertor util = new CitizenRefXmlConvertor();

	public static TransLogInfoXmlConvertor logUtil = new TransLogInfoXmlConvertor();

	private static String ASSENDING_ORDER = "1";

	private static String DECENDING_ORDER = "2";

	public static final String STATUS_COMPLETED = "02";

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private FraudCaseManagementController_PrintProcessor printProcessor;

	@Autowired
	private DocumentDataDao documentDataDao = null;

	@Autowired
	private DocumentDataService documentDataService = null;

	@Autowired
	private SiteService eppSiteService;

	@RequestMapping(value = "/investigationSuspendedList")
	public ModelAndView investigationSuspendedList(WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC fraudCaseManagement List");
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageNo = 1;
			int pageSize = 20;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>" + userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = FraudCaseManagementController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request.getParameter(
						new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(FraudCaseManagementController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			Parameters parameter = parametersService.findById(id);

			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			String pageNumber = request
					.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (StringUtils.isNotBlank(pageNumber) && StringUtils.isNumeric(pageNumber)) {
				pageNo = Integer.parseInt(pageNumber);
			}
			httpRequest.setAttribute("suspendedCount", uploadJobService.getSuspendedInvestigationsCount());
			pr = uploadJobService.findAllForInvestigationPagination(
					new String[] { NicUploadJob.RECORD_STATUS_SUSPENDED }, userSession.getUserName(), pageNo, pageSize);
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
					for (NicUploadJobDto record : list) {
						String transactionId = record.getTransactionId();
						if (transactionId != null) {
							NicTransaction nicTransaction = nicTransactionService.findById(transactionId);
							if (nicTransaction != null) {
								record.setDateOfApplication(nicTransaction.getDateOfApplication());
								record.setEstDateOfCollection(nicTransaction.getEstDateOfCollection());
								// record.setPriority(nicTransaction.getPriority());
								{
									try {
										CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,
												nicTransaction.getPriority().toString());
										if (codeValue != null && codeValue.getCodeValueDesc() != null) {
											record.setPriorityString(codeValue.getCodeValueDesc());
										} else {
											record.setPriorityString(nicTransaction.getPriority()==null?null:nicTransaction.getPriority().toString());
										}
									} catch (Exception e) {
										record.setPriorityString(nicTransaction.getPriority()==null?null:nicTransaction.getPriority().toString());
									}
								}
							}
						}
					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "No Data found", 0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
				model.addAttribute("jobList", list);
				model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION_SUSPENDED);
				return new ModelAndView("investigation.fraudCaseManagement.list", searchResultMap);
			} else {
				model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION_SUSPENDED);
				return new ModelAndView("investigation.fraudCaseManagement.list", null);
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = { "/decide/{jobId}" })
	public ModelAndView decide(@PathVariable long jobId, HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start fraudCaseManagement job for decide");
		
		return this.decide(jobId, httpRequest, model, null);
	}

	public ModelAndView decide(long jobId, HttpServletRequest httpRequest, Model model, List<String> listOfMessages)
			throws Throwable {
		//logger.info("NIC Start Investigation compare");

		this.prepareModelStuff(model);

		ModelAndView modelAndView = new ModelAndView("investigation.fraudCaseManagement.decide");

		NicUploadJob jobDetails = uploadJobService.findById(jobId);

		String mainTransactionId = jobDetails.getTransactionId();
		Long searchResultId = this.getSearchResultId(jobDetails);
		logger.debug("searchResultId:" + searchResultId);

		this.initializeModelAndViewForms(modelAndView);

		this.initializeModelAndViewCommonPageDetails(modelAndView, jobDetails, mainTransactionId, searchResultId);

		List<InvestigationHit> mainCandidateDataContainer = this.getAllDataForModelAndView(modelAndView, jobDetails,
				mainTransactionId);
		modelAndView.addObject("mainCandidateDataContainer", mainCandidateDataContainer);
		modelAndView.addObject("mainCandidateDataContainerSize", mainCandidateDataContainer.size());
		logger.debug("mainCandidateDataContainer.size():" + mainCandidateDataContainer.size());

		this.processHitDocuments(modelAndView, jobDetails.getWorkflowJobId(), mainTransactionId);

		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}

	private void processHitDocuments(ModelAndView modelAndView, Long workflowId, String mainTransactionId)
			throws Exception {

		List<FraudCaseManagementDocumentData> invHitDocuments = new ArrayList<FraudCaseManagementDocumentData>();

		// get all true hits
		List<String> trueHits = this.getAllHitTransactionIds(workflowId, mainTransactionId);
		if (CollectionUtils.isEmpty(trueHits)) {
			modelAndView.addObject("invHitDocuments", invHitDocuments);
			return;
		}

		// sort true hits by transaction id
		Collections.sort(trueHits);

		// get document per tran id
		for (String transactionId : trueHits) {
			try {
				NicDocumentData doc = this.getDocumentForTransaction(transactionId);

				if (doc == null) {
					continue;
				}

				NicTransaction nicTransaction = nicTransactionService.findById(transactionId);

				invHitDocuments.add(new FraudCaseManagementDocumentData(transactionId, doc.getId().getPassportNo(),
						nicTransaction.getTransactionStatus(),
						TransactionStatus.getInstance(nicTransaction.getTransactionStatus()).getName(), this.codesService.getCodeValueDescByIdName(Codes.PASSPORT_TYPE, nicTransaction.getPassportType(), nicTransaction.getPassportType()))); 
			} catch (Exception e) {
			}
		}

		modelAndView.addObject("invHitDocuments", invHitDocuments);
	}

	private List<String> getAllHitTransactionIds(long jobId, String mainTransactionId) {

		try {
			List<SearchHitDto> trueHits = this.uploadJobService
					.getAllHitsForJobId_1PerHitTransactionId_anySearchResultId(jobId, true);

			if (CollectionUtils.isEmpty(trueHits)) {
				return null;
			}

			for (int i = trueHits.size() - 1; i >= 0; i--) {
				if (trueHits.get(i).getHitTransactionId().equals(mainTransactionId)) {
					trueHits.remove(i);
				}
			}

			List<String> transactionIds = new ArrayList<String>();
			for (SearchHitDto hit : trueHits) {
				transactionIds.add(hit.getHitTransactionId());
			}

			return transactionIds;
		} catch (Exception e) {
			return null;
		}

	}

	private NicDocumentData getDocumentForTransaction(String transactionId) {

		try {
			{
				List<NicDocumentData> docs = this.documentDataDao.findAllByTransactionId(transactionId,
						Arrays.asList(TransactionStatus.Active.getKey()));
				if (CollectionUtils.isNotEmpty(docs)) {
					return docs.get(0);
				}
			}

			{
				List<NicDocumentData> docs = this.documentDataDao.findAllByTransactionId_notInStatuses(transactionId,
						Arrays.asList(TransactionStatus.Active.getKey(), TransactionStatus.Cancelled.getKey()));
				if (CollectionUtils.isNotEmpty(docs)) {
					return docs.get(0);
				}
			}

			{
				List<NicDocumentData> docs = this.documentDataDao.findAllByTransactionId(transactionId,
						Arrays.asList(TransactionStatus.Cancelled.getKey()));
				if (CollectionUtils.isNotEmpty(docs)) {
					return docs.get(0);
				}
			}

			return null;
		} catch (Exception e) {
			return null;
		}
	}

	private InvestigationHit put1HitIntoModelAndView(ModelAndView modelAndView, NicUploadJob jobDetails,
			String mainTransactionId) throws Exception, TransactionServiceException, IssuanceServiceException {

		InvestigationHit invHit = new InvestigationHit();

		String mainCandidatePhoto = null;

		// Main Candidate Photo, Signature and Finger prints.
		List<NicTransactionAttachment> mainCanPh = nicTransactionAttachmentService.findNicTransactionAttachments(
				mainTransactionId, NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
				NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
		// Main Candidate Photo
		if (CollectionUtils.isNotEmpty(mainCanPh)) {
			byte[] photoBinary = this.handleJp2(mainCanPh.get(0).getDocData());
			mainCandidatePhoto = new Base64Jp2HeaderHelper().getBase64WithJp2Header(photoBinary);
		}
		invHit.addObject("mainCandidatePhoto", mainCandidatePhoto);

		// Main Candidate Details
		NicTransaction mainCandidateListNin = nicTransactionService.findById(mainTransactionId);
		if (mainCandidateListNin != null) {
			// mainCandidateListNin.getNin();
			mainCandidateListNin = nicTransactionService.getTransactionRegistrationData(mainCandidateListNin);

			NicRegistrationData nicRegistrationData = mainCandidateListNin.getNicRegistrationData();

			invHit.addObject("mainCandidateDateOfApplication",
					DateUtil.parseDate(mainCandidateListNin.getDateOfApplication(), "dd-MMM-yyyy"));
			invHit.addObject("mainCandidateIssuingAuthority", this.eppSiteService.getSiteRepoAuthority(
					mainCandidateListNin.getIssuingAuthority(), mainCandidateListNin.getIssuingAuthority()));

			this.processOneReferringToCodeTable(mainCandidateListNin.getTransactionType(), Codes.TRANSACTION_TYPE,
					"mainCandidateTransactionType", invHit);
			this.processOneReferringToCodeTable(mainCandidateListNin.getTransactionStatus(), Codes.TRANSACTION_STATUS,
					"mainCandidateApplicationPassportStatus", invHit);

			if (nicRegistrationData != null) {

				String mainCandidateFN = nicRegistrationData.getFirstnameFull();
				String mainCandidateFNShort = null;
				if (StringUtils.isNotBlank(mainCandidateFN)) {
					if (mainCandidateFN.length() > 15) {
						mainCandidateFNShort = mainCandidateFN.substring(0, 14) + " ...";
					} else {
						mainCandidateFNShort = mainCandidateFN;
					}
				}

				String mainCandidateSN = nicRegistrationData.getSurnameFull();

				String mainCandidateSNShort = null;
				if (StringUtils.isNotBlank(mainCandidateSN)) {
					if (mainCandidateSN.length() > 15) {
						mainCandidateSNShort = mainCandidateSN.substring(0, 14) + " ...";
					} else {
						mainCandidateSNShort = mainCandidateSN;
					}
				}

				String mainCandidateMN = nicRegistrationData.getMiddlenameFull();
				String mainCandidateMNShort = null;
				if (StringUtils.isNotBlank(mainCandidateMN)) {
					if (mainCandidateMN.length() > 15) {
						mainCandidateMNShort = mainCandidateMN.substring(0, 14) + " ...";
					} else {
						mainCandidateMNShort = mainCandidateMN;
					}
				}

				String mainCandidateGender = nicRegistrationData.getGender();
				Date mainCandidateDOB = nicRegistrationData.getDateOfBirth();
				String mainCandDOB = null;
				if (mainCandidateDOB != null) {
					mainCandDOB = DateUtil.parseDate(mainCandidateDOB, "dd-MMM-yyyy");
				}

				modelAndView.addObject("jobDetails", jobDetails);
				invHit.addObject("mainCandidateFN", mainCandidateFN);
				invHit.addObject("mainCandidateFNShort", mainCandidateFNShort);
				invHit.addObject("mainCandidateSN", mainCandidateSN);
				invHit.addObject("mainCandidateSNShort", mainCandidateSNShort);
				invHit.addObject("mainCandidateMNShort", mainCandidateMNShort);
				invHit.addObject("mainCandidateGender", mainCandidateGender);
				invHit.addObject("mainCandidateDOB", mainCandDOB);

			}
		}

		return invHit;
	}

	private byte[] handleJp2(byte[] image) {

		if (image == null) {
			return image;
		}

		// if (image != null) {
		// try {
		// return FileUtils.readFileToByteArray(new
		// File("C:/EPP-DEV/angeles/fileToConvert.xxx"));
		// } catch (Exception e) {
		// return null;
		// }
		// }

		boolean ignoreJ2k = false;
		{
			try {
				Parameters parameter = this.parametersService.getParamDetails(
						FraudCaseManagementController.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE,
						FraudCaseManagementController.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE);
				if (parameter != null) {
					ignoreJ2k = Boolean.valueOf(parameter.getParaShortValue());
				}
			} catch (Exception e) {
			}
		}

		// get type
		String imageType = null;
		{
			try {
				imageType = new ImageUtil().checkImageType(image);
			} catch (Exception e) {
				return null;
			}
		}

		if (StringUtils.isBlank(imageType)) {
			return null;
		}

		if (imageType.equalsIgnoreCase(ImageUtil.IMAGE_J2K)) {
			if (ignoreJ2k) {
				return null;
			} else {
				boolean j2kConvertAtServer = false;
				{
					try {
						Parameters parameter = this.parametersService.getParamDetails(
								FraudCaseManagementController.PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_SCOPE,
								FraudCaseManagementController.PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_VALUE);
						if (parameter != null) {
							j2kConvertAtServer = Boolean.valueOf(parameter.getParaShortValue());
						}
					} catch (Exception e) {
					}
				}

				if (j2kConvertAtServer) {
					try {
						//return SpidHelper.getInstance("Y").convertImageFormat(image, SpidHelper.IMAGE_J2K, SpidHelper.IMAGE_JPG);
						return null;
					} catch (Exception e) {
						return null;
					}
				} else {
					return image;
				}
			}
		}

		return image;
	}

	private void processOneReferringToCodeTable(String key, String codeTableKey, String modelItemName,
			InvestigationHit investigationHit) {
		if (key == null) {
			investigationHit.addObject(modelItemName, "");
		} else {
			CodeValues codeValue = codesService.getCodeValueByIdName(codeTableKey, key);
			if (codeValue != null) {
				investigationHit.addObject(modelItemName, codeValue.getCodeValueDesc());
			} else {
				investigationHit.addObject(modelItemName, key);
			}
		}
	}

	private Long getSearchResultId(NicUploadJob jobDetails) {

		if (jobDetails != null) {
			if (jobDetails.getNicSearchResults() != null) {
				Long searchResultId = null;
				// get the last search result.
				for (NicSearchResult sr : jobDetails.getNicSearchResults()) {
					if (searchResultId != null) {
						if (searchResultId.longValue() < sr.getSearchResultId().longValue())
							searchResultId = sr.getSearchResultId();
					} else {
						searchResultId = sr.getSearchResultId();
					}
				}
				return searchResultId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private List<InvestigationHit> getAllDataForModelAndView(ModelAndView modelAndView, NicUploadJob jobDetails,
			String mainTransactionId) {

		List<InvestigationHit> mainCandidateDataContainer = new ArrayList<InvestigationHit>();

		try {
			InvestigationHit invHit = this.put1HitIntoModelAndView(modelAndView, jobDetails, mainTransactionId);
			if (invHit != null) {
				mainCandidateDataContainer.add(invHit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mainCandidateDataContainer;
	}

	private void initializeModelAndViewCommonPageDetails(ModelAndView modelAndView, NicUploadJob jobDetails,
			String mainTransactionId, Long searchResultId) throws NicUploadJobServiceException {
		String jobUploadTime = null;

		// date of application
		NicTransaction nicTransaction = nicTransactionService.findById(jobDetails.getTransactionId());
		if (nicTransaction != null) {
			jobUploadTime = DateUtil.parseDate(nicTransaction.getDateOfApplication(), "dd-MMM-yyyy");
		}
		modelAndView.addObject("jobUploadTime", jobUploadTime);

		String jobType = null;
		if (jobDetails != null && StringUtils.isNotBlank(jobDetails.getJobType())) {
			CodeValues codeValue = codesService.getCodeValueByIdName("JOB_TYPE", jobDetails.getJobType());

			if (codeValue != null) {
				jobType = codeValue.getCodeValueDesc();
			} else {
				jobType = jobDetails.getJobType();
			}
		}
		modelAndView.addObject("jobType", jobType);

	}

	private void initializeModelAndViewForms(ModelAndView mav) {
		mav.addObject("fraudCaseManagementData", new FraudCaseManagementData());
		mav.addObject("cancelDocumentData", new CancelDocumentData());
	}

	private void prepareModelStuff(Model model) {
		model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION_SUSPENDED);
	}

	@RequestMapping(value = { "/submitDecision" })
	public ModelAndView submitHitDecision(
			@ModelAttribute(value = "fraudCaseManagementData") FraudCaseManagementData fraudCaseManagementData,
			BindingResult result, WebRequest request, HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("submitHitDecision, {}", (fraudCaseManagementData!=null?fraudCaseManagementData.getUploadJobId():"") );

		logger.debug("getDecision         [" + fraudCaseManagementData.getDecision() + "]");
		logger.debug("getRemarks          [" + fraudCaseManagementData.getRemarks() + "]");
		logger.debug("getUploadJobId      [" + fraudCaseManagementData.getUploadJobId() + "]");

		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		logger.debug("The Officer Id or User Id is ===============>>>>>>>>>>" + userSession.getUserName());

		List<String> messages = new LinkedList<String>();
		try {
			if (fraudCaseManagementData.getDecision()
					.equalsIgnoreCase(FraudCaseManagementData.FraudCaseManagementData_decision_APPROVE)) {
				this.uploadJobService.approveJob(fraudCaseManagementData.getRemarks(),
						Long.valueOf(fraudCaseManagementData.getUploadJobId()), userSession.getUserName(),
						userSession.getWorkstationId(), NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT,
						NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED);
			} else {
				this.uploadJobService.rejectJob(fraudCaseManagementData.getRemarks(),
						Long.valueOf(fraudCaseManagementData.getUploadJobId()), userSession.getUserName(),
						userSession.getWorkstationId(), NicRejectionData.rejectReason_faultCaseManagement, NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT,
						NicTransactionLog.TRANSACTION_STATUS_NIC_REJECTED);
			}
			
			logger.info("success XXX /submitDecision >>>>>>>>>>"); 
			messages.add("Investigation record successfully submitted.");
		} catch (JobNoLongerAssignedToOfficerException e) { 
			logger.info("submitDecision failed");
			messages.add("The submission failed.  The job is no longer assigned to you.");
		} catch (Throwable e) { 
			logger.info("submitDecision failed");
			messages.add("The submission failed.");
		}
 
		return this.continueAndGetNextRecord(httpRequest, model, messages);
	}

	private ModelAndView continueAndGetNextRecord(HttpServletRequest httpRequest, Model model, List<String> messages)
			throws Throwable {
		HttpSession session = httpRequest.getSession();
		String userName = ((UserSession) session.getAttribute("userSession")).getUserName();
		String workstationId = ((UserSession) session.getAttribute("userSession")).getWorkstationId();

		// any avialable assigned to open?
		{
			Long availableAssignedToDisplay = null;
			{
				try {
					PaginatedResult<NicUploadJobDto> pr = uploadJobService.findAllForInvestigationPagination(
							new String[] { NicUploadJob.RECORD_STATUS_SUSPENDED }, userName, 1, 1);
					if (pr != null) {
						List<NicUploadJobDto> list = pr.getRows();
						if (list != null) {
							availableAssignedToDisplay = list.get(0).getUploadJobId();
						}
					}
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}

			// yes
			// dispaly it with proper message
			if (availableAssignedToDisplay != null) {
				List<String> listOfMessages = new ArrayList<String>();
				if (messages != null) {
					listOfMessages.addAll(messages);
				}
				listOfMessages.add("Next record is displayed below.");
				return this.decide(availableAssignedToDisplay, httpRequest, model, listOfMessages);
			}
		}

		// here:no available assigned to open
		{
			Long newlyAssignedToDisplay = null;
			try {
				this.assignNewInvestigation(userName, userName, workstationId);
				newlyAssignedToDisplay = (this.uploadJobService.findAllForInvestigationPagination(
						new String[] { NicUploadJob.RECORD_STATUS_SUSPENDED }, userName, 1, 1)).getRows().get(0)
								.getUploadJobId();
			} catch (Exception ex) {
			}

			// any available unassigned?
			// yes
			// assign
			// display it with proper message
			if (newlyAssignedToDisplay != null) {
				List<String> listOfMessages = new ArrayList<String>();
				if (messages != null) {
					listOfMessages.addAll(messages);
				}
				listOfMessages.add("Next record displayed below.");
				return this.decide(newlyAssignedToDisplay, httpRequest, model, listOfMessages);
			}
			// no
			// message that there is no avaialble next
			{
				List<String> listOfMessages = new ArrayList<String>();
				if (messages != null) {
					listOfMessages.addAll(messages);
				}
				listOfMessages.add("No new records found.");
				httpRequest.setAttribute("messages", listOfMessages);
				return new ModelAndView("forward:/servlet/fraudCaseManagement/investigationSuspendedList");
			}
		}
	}

	@RequestMapping(value = { "/newInvestigation" })
	public ModelAndView NewInvestigation(HttpServletRequest request, Model model) throws Throwable {
		return this.NewInvestigation(request, model, null);
	}

	public ModelAndView NewInvestigation(HttpServletRequest request, Model model, List<String> messageItems)
			throws Throwable {
		List<String> messages = new LinkedList<String>();

		if (messageItems != null) {
			messages.addAll(messageItems);
		}

		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		try {
			this.assignNewInvestigation(userSession.getUserName(), userSession.getUserName(),
					userSession.getWorkstationId());
			messages.add("Suspended Investigation record successfully assigned to User: " + userSession.getUserName());
		} catch (Exception ex) {
			messages.add("No new suspended investigation record(s) found to assign.");
		}
		request.setAttribute("messages", messages);
		return new ModelAndView("forward:/servlet/fraudCaseManagement/investigationSuspendedList");
	}

	private void assignNewInvestigation(String user, String officerUserId, String officerWorkstationId)
			throws NoNewRecordException {
		try {
			uploadJobService.assignNewSuspendedJobList(user.trim(), officerUserId, officerWorkstationId);
		} catch (Exception ex) {
			throw new NoNewRecordException();
		}
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchJobList(WebRequest request, HttpServletRequest httpRequest,
			@RequestParam("search_data") String txnId, Model model) throws Throwable {
		logger.info("Retrieve Transaction Id =========================>> " + txnId);
		httpRequest.setAttribute("suspendedCount", uploadJobService.getSuspendedInvestigationsCount());
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageNo = 1;
			int pageSize = 10;
			int startIndex = 0;

			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = FraudCaseManagementController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request.getParameter(
						new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(FraudCaseManagementController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			Parameters parameter = parametersService.findById(id);

			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			String pageNumber = request
					.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (StringUtils.isNotBlank(pageNumber) && StringUtils.isNumeric(pageNumber)) {
				pageNo = Integer.parseInt(pageNumber);
			}
			if (StringUtils.isNotBlank(pageNumber)) {
				startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
			} else {
				startIndex = 10;
			}
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			pr = uploadJobService.findTransactions(txnId, userSession.getUserName(),
					Arrays.asList(new String[] { NicUploadJob.RECORD_STATUS_SUSPENDED }));
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
					for (NicUploadJobDto record : list) {
						String transactionId = record.getTransactionId();
						NicTransaction nicTransaction = nicTransactionService.findById(transactionId);
						record.setDateOfApplication(nicTransaction.getDateOfApplication());
						record.setEstDateOfCollection(nicTransaction.getEstDateOfCollection());
						// record.setPriority(nicTransaction.getPriority());
						{
							try {
								CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,
										nicTransaction.getPriority().toString());
								if (codeValue != null && codeValue.getCodeValueDesc() != null) {
									record.setPriorityString(codeValue.getCodeValueDesc());
								} else {
									record.setPriorityString(nicTransaction.getPriority()==null?null:nicTransaction.getPriority().toString());
								}
							} catch (Exception e) {
								record.setPriorityString(nicTransaction.getPriority()==null?null:nicTransaction.getPriority().toString());
							}
						}
					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "No Data found", 0);
				}
				model.addAttribute("nicUploadJobDto", new NicUploadJobDto());
				Map searchResultMap = new HashMap();
				searchResultMap.put("jobList", list);
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
				searchResultMap.put("txnId", txnId);
				model.addAttribute("jobList", list);
				prepareModelStuff(model);
				return new ModelAndView("investigation.fraudCaseManagement.list", searchResultMap);
			} else {
				return new ModelAndView("investigation.fraudCaseManagement.list", null);
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = { "/print/{jobId}" })
	public ModelAndView print(@PathVariable long jobId, HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("print");

		return printProcessor.print(jobId, httpRequest, model);
	}

	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}

	@RequestMapping(value = { "/cancelDocumentData" })
	public ModelAndView cancelDocumentData(
			@ModelAttribute(value = "cancelDocumentData") CancelDocumentData cancelDocumentData,
			HttpServletRequest request, Model model) throws Throwable {

		List<String> messages = new LinkedList<String>();

		try {
			this.nicTransactionService.updateDocByStatus(cancelDocumentData.getTransactionId(),
					cancelDocumentData.getPassportNo(), TransactionStatus.Cancelled,
					((UserSession) (request.getSession().getAttribute("userSession"))).getUserName(),
					((UserSession) (request.getSession().getAttribute("userSession"))).getWorkstationId(),NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT);
			logger.info("dummy cancelDocumentData call");
			messages.add("The passport was successfully cancelled.");
		} catch (Exception e) {
			messages.add("The passport was not successfully cancelled.");
		}

		return this.decide(Long.valueOf(cancelDocumentData.getUploadJobId()), request, model, messages);
	}
}
