/**
 * 
 */
package com.nec.asia.nic.enquiry.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.common.DmsUtility;
import com.nec.asia.nic.common.dto.RicRegistrationDocumentDTO;
import com.nec.asia.nic.comp.job.dto.CpdReferenceDataDTO;
import com.nec.asia.nic.comp.job.dto.HitCandidateDTO;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicJobCpdCheckResult;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.NicEnquiryForm;
import com.nec.asia.nic.comp.trans.dto.NicRegistrationDataDto;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.dx.dto.DataExchangeDTO;
import com.nec.asia.nic.dx.dto.FpVerificationDTO;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.common.ImageUtil;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.investigation.AttachmentSource;
import com.nec.asia.nic.investigation.controller.AttachmentEntry;
import com.nec.asia.nic.investigation.controller.InvestigationController;
import com.nec.asia.nic.investigation.exception.InvalidParameterException;
import com.nec.asia.nic.security.workstationmanagement.WorkstationInfo;
import com.nec.asia.nic.util.CitizenRefXmlConvertor;
import com.nec.asia.nic.util.CodeValueDescComparator;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.LogDataXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.BaseDTOMapper;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;

/**
 * @author franco_conte
 * @Company: NEC Asia Pacific Ltd
 * @Since: Sep 2, 2013
 */

/*
 * Modification History: 11 Jul 2017 (chris): to show fingerprint as signature
 * based on transaction data.
 */

@Controller
@RequestMapping(value = "/transactionEnquiry")
public class TransactionEnquiryController extends AbstractController {

	private static final Logger logger = LoggerFactory
			.getLogger(TransactionEnquiryController.class);

	public static CitizenRefXmlConvertor util = new CitizenRefXmlConvertor();

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionDocumentService;

	@Autowired
	NicTransactionService nicTransactionService;

	@Autowired
	NicRegistrationDataService nicRegistrationDataService;

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private DocumentDataService documentDataService;

	@Autowired
	private DocumentDataDao documentDataDao = null;

	@Autowired
	private SiteService siteService;

	@Autowired
	private UserService userService;

	public static LogDataXmlConvertor issuanceUtil = new LogDataXmlConvertor();

	private static String ASCENDING_ORDER = "1";

	private static String DESCENDING_ORDER = "2";

	private static final String PARA_SCOPE_SYSTEM = "SYSTEM";
	private static final String PARA_NAME_SYSTEM_DMS_URL = "DMS_URL";

	private static final String PARA_TRANSACTION_ATTACHMENT_SOURCE_SCOPE = "SYSTEM";
	private static final String PARA_TRANSACTION_ATTACHMENT_SOURCE_VALUE = "TRANSACTION_ATTACHMENT_SOURCE";

	@ModelAttribute("genderList")
	public List<CodeValues> genderList() {
		List<CodeValues> genderList = null;
		try {
			genderList = getCodeValueList("GENDER");
		} catch (NicUploadJobServiceException e) {
			e.printStackTrace();
		}
		return genderList;
	}

	@RequestMapping(value = "/init")
	@ExceptionHandler
	public ModelAndView nicEnquiryJobList(WebRequest request,
			HttpServletRequest httpServletRequest, ModelMap model)
			throws Exception {
		logger.info("Transaction Enquiry Page");
		try {
			String viewFPFalg = "Y";
			UserSession userSession = (UserSession) httpServletRequest
					.getSession().getAttribute("userSession");
			NicEnquiryForm nicEnqForm = new NicEnquiryForm();
			nicEnqForm.setGenderCodeList(getCodeValueList("GENDER"));
			if (!userSession.getFunctions().contains("VIEW_FP")) {
				viewFPFalg = "N";
			}
			// phúc edit
			model.addAttribute("pageSize", 10);
			model.addAttribute("pageNo", 1);
			model.addAttribute("totalPage", 1);
			model.addAttribute("startIndex", 0);
			model.addAttribute("totalRecord", 0);
			model.addAttribute("endIndex", 0);
			model.addAttribute("jobList", new ArrayList<NicUploadJobInfo>());
			// end
			model.addAttribute("fnSelected",
					Constants.HEADING_NIC_TRANS_ENQUIRY);
			// model.addAttribute("jobList", new
			// ArrayList<NicTransactionInfo>());
			ModelAndView mav = new ModelAndView("nic-enquiry-transaction");
			mav.addObject("nicEnqForm", nicEnqForm);
			mav.addObject("viewFPFalg", viewFPFalg);

			return mav;

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	private List<CodeValues> getCodeValueList(String codeId)
			throws NicUploadJobServiceException {
		List<CodeValues> codeValueList = codesService.findAllByCodeId(codeId);
		if (codeValueList != null) {
			Collections.sort(codeValueList, new CodeValueDescComparator());
		}

		return codeValueList;
	}

	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView searchTransaction(WebRequest request, ModelMap model,
			@ModelAttribute("nicEnqForm") NicEnquiryForm nicEnquiryForm)
			throws Exception {
		ModelAndView mav = new ModelAndView("nic-enquiry-transaction");
		mav.addObject("nicEnqForm", nicEnquiryForm);
		mav.addObject("viewFPFalg", "N");
		try {
			String pages = !StringUtils.isEmpty(request.getParameter("pageNo")) ? request
					.getParameter("pageNo") : "1";
			String sortname = request.getParameter("sortname") != null ? request
					.getParameter("sortname") : "transactionId";
			String sortorder = request.getParameter("sortorder") != null ? request
					.getParameter("sortorder") : "asc";
			String rp = request.getParameter("rp");
			String recordPerPage = request.getParameter("rp");
			if (!StringUtils.isNotBlank(recordPerPage)) {
				recordPerPage = "10";
			}

			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			;
			if (rp == null || "NaN".equals(rp)) {
				// pageSize =
				// Integer.parseInt(configurationService.getUserAdminConsoleJobQueueMaxRecordsPerPage(getUserSession(request).getUserId()).getSettings());
			} else {
				pageSize = Integer.parseInt(rp);
			}

			int startIndex = 0;
			String pageNumber = pages;
			if (StringUtils.isNotBlank(pageNumber)) {
				startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
			}
			PageRequest pageRequest = new PageRequest();
			pageRequest.setCalculateRecordCount(true);
			pageRequest.setFirstRowIndex(startIndex);
			pageRequest.setMaxRecords(pageSize);
			pageRequest.setSortname(sortname);
			pageRequest.setSortorder(sortorder);
			pageRequest.setPageNo(Integer.parseInt(pageNumber));

			if (StringUtils.isNotBlank(nicEnquiryForm.getTransactionId())) {
				NicTransaction nicTransaction = nicTransactionService
						.findById(nicEnquiryForm.getTransactionId());
				List<NicTransactionInfo> txnList = new ArrayList<NicTransactionInfo>();
				if (nicTransaction != null) {
					NicTransactionInfo nicTransactionInfo = getTransactionInfo(nicTransaction);
					txnList.add(nicTransactionInfo);
					// return new PaginatedResult<NicTransactionInfo>(1, 1,
					// txnList);

				} else {
					// return new PaginatedResult<NicTransactionInfo>(0, 0, new
					// ArrayList<NicTransactionInfo>());
					// return new NicTransactionInfo[0];
				}
				// phúc edit
				model.addAttribute("pageSize", 10);
				model.addAttribute("pageNo", 1);
				model.addAttribute("totalPage", 1);
				model.addAttribute("startIndex", txnList.size() != 0 ? 1 : 0);
				model.addAttribute("totalRecord", txnList.size());
				model.addAttribute("endIndex", txnList.size() != 0 ? 1 : 0);
				model.addAttribute("jobList", txnList);
				// end
				return mav;
			}

			if (StringUtils.isNotBlank(nicEnquiryForm.getPassportNo())) {
				NicDocumentData documentData = new NicDocumentData(
						new NicDocumentDataId("",
								nicEnquiryForm.getPassportNo()));
				PaginatedResult<NicDocumentData> pr = documentDataService
						.findAllForPagination(pageRequest, documentData);
				List<NicTransactionInfo> txnList = new ArrayList<NicTransactionInfo>();
				if (pr != null) {
					for (NicDocumentData docData : pr.getRows()) {
						NicTransaction nicTransaction = nicTransactionService
								.findById(docData.getId().getTransactionId());
						if (nicTransaction != null) {
							NicTransactionInfo nicTransactionInfo = getTransactionInfo(nicTransaction);
							txnList.add(nicTransactionInfo);
						}
					}

					if (CollectionUtils.isNotEmpty(txnList)) {
						// return new
						// PaginatedResult<NicTransactionInfo>(pr.getTotal(),
						// pr.getPage(), txnList);
						// NicTransactionInfo[] arr = new
						// NicTransactionInfo[txnList.size()];
						// for(int i = 0; i < txnList.size(); i++){
						// arr[i] = txnList.get(i);
						// }
						// return arr;
					} else {
						// return new PaginatedResult<NicTransactionInfo>(0, 0,
						// new ArrayList<NicTransactionInfo>());
						// return new NicTransactionInfo[0];
					}
				}
				// phúc edit
				int firstResults = (pageRequest.getPageNo() - 1) < 0 ? 0
						: (pageRequest.getPageNo() - 1) * pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageRequest.getPageNo());
				model.addAttribute("totalPage",
						pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex",
						pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("jobList", txnList);
				// end
				return mav;
				// return new NicTransactionInfo[0];
				// return new PaginatedResult<NicTransactionInfo>(0, 0, new
				// ArrayList<NicTransactionInfo>());
			}

			if (StringUtils.isNotBlank(nicEnquiryForm.getReceiptNo())) {
				NicTransaction nicTransaction = new NicTransaction();
				nicTransaction.setApplnRefNo(nicEnquiryForm.getReceiptNo());
				PaginatedResult<NicTransaction> pr = nicTransactionService
						.findAllForPagination(pageRequest, nicTransaction);
				List<NicTransactionInfo> txnList = new ArrayList<NicTransactionInfo>();
				for (NicTransaction transaction : pr.getRows()) {
					NicTransactionInfo nicTransactionInfo = getTransactionInfo(transaction);
					txnList.add(nicTransactionInfo);
				}

				if (CollectionUtils.isNotEmpty(txnList)) {
					// return new
					// PaginatedResult<NicTransactionInfo>(pr.getTotal(),
					// pr.getPage(), txnList);
					// NicTransactionInfo[] arr = new
					// NicTransactionInfo[txnList.size()];
					// for(int i = 0; i < txnList.size(); i++){
					// arr[i] = txnList.get(i);
					// }
					// return arr;
				} else {
					// return new PaginatedResult<NicTransactionInfo>(0, 0, new
					// ArrayList<NicTransactionInfo>());
					// return new NicTransactionInfo[0];
				}
				// phúc edit
				int firstResults = (pageRequest.getPageNo() - 1) < 0 ? 0
						: (pageRequest.getPageNo() - 1) * pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageRequest.getPageNo());
				model.addAttribute("totalPage",
						pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex",
						pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("jobList", txnList);
				// end
				return mav;
			}

			// NicTransaction nicTransaction = new NicTransaction();
			NicRegistrationData nicRegistrationData = new NicRegistrationData();

			if (StringUtils.isNotBlank(nicEnquiryForm.getFirstName())) {
				nicRegistrationData.setSearchName(HelperClass.removeAccent(
						nicEnquiryForm.getFirstName().trim()).toUpperCase());
			}

			// if (StringUtils.isNotBlank(nicEnquiryForm.getMiddleName())) {
			// nicRegistrationData.setMiddlenameFull(nicEnquiryForm.getMiddleName());
			// }
			//
			// if (StringUtils.isNotBlank(nicEnquiryForm.getLastName())) {
			// nicRegistrationData.setSurnameFull(nicEnquiryForm.getLastName());
			// }
			//
			if (StringUtils.isNotBlank(nicEnquiryForm.getGender())) {
				nicRegistrationData.setGender(nicEnquiryForm.getGender());
			}

			if (StringUtils.isNotBlank(nicEnquiryForm.getDateOfBirth())) {
				Date dateOfBirth = DateUtil.strToDate(
						nicEnquiryForm.getDateOfBirth(), "dd-MMM-yyyy");
				nicRegistrationData.setDateOfBirth(dateOfBirth);
			}

			PaginatedResult<NicRegistrationData> pr = nicRegistrationDataService
					.findAllForPagination(pageRequest, nicRegistrationData);
			List<NicTransactionInfo> txnList = new ArrayList<NicTransactionInfo>();
			for (NicRegistrationData regData : pr.getRows()) {
				NicTransaction nicTransaction = nicTransactionService
						.findById(regData.getTransactionId());
				if (nicTransaction != null) {
					NicTransactionInfo nicTransactionInfo = getTransactionInfo(nicTransaction);
					txnList.add(nicTransactionInfo);
				}
			}
			if (CollectionUtils.isNotEmpty(txnList)) {
				// return new PaginatedResult<NicTransactionInfo>(pr.getTotal(),
				// pr.getPage(), txnList);
				// NicTransactionInfo[] arr = new
				// NicTransactionInfo[txnList.size()];
				// for(int i = 0; i < txnList.size(); i++){
				// arr[i] = txnList.get(i);
				// }
				// return arr;
			} else {
				// return new PaginatedResult<NicTransactionInfo>(0, 0, new
				// ArrayList<NicTransactionInfo>());
				// return new NicTransactionInfo[0];
			}
			// phúc edit
			int firstResults = (pageRequest.getPageNo() - 1) < 0 ? 0
					: (pageRequest.getPageNo() - 1) * pageSize;
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", pageRequest.getPageNo());
			model.addAttribute("totalPage",
					pagingUtil.totalPage(pr.getTotal(), pageSize));
			model.addAttribute("startIndex",
					pr.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", pr.getTotal());
			model.addAttribute("endIndex", firstResults + pr.getRowSize());
			model.addAttribute("jobList", txnList);
			// end
			return mav;
		} catch (Exception e) {
			logger.error(e.getMessage());
			// return new PaginatedResult<NicTransactionInfo>(0, 0, new
			// ArrayList<NicTransactionInfo>());
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/jobEnqDgData/{jobId}", method = RequestMethod.GET)
	@ExceptionHandler
	public NicRegistrationDataDto getDGDForJobEnquery(WebRequest request,
			@PathVariable String jobId, ModelMap model) throws Exception {

		HitCandidateDTO candidateCPDData = new HitCandidateDTO();
		NicUploadJob nicUploadJob = uploadJobService.findById(Long
				.valueOf(jobId));
		String transactionId = nicUploadJob.getTransactionId();// this one is
																// null

		NicRegistrationData nicRegistrationData = uploadJobService
				.getNicRegistrationData(transactionId);
		NicRegistrationDataDto ricAndCpdData = new NicRegistrationDataDto(
				nicRegistrationData);
		NicJobCpdCheckResult cpdData = uploadJobService.findCpdData(Long
				.valueOf(jobId));
		String dataFromCpd = "";
		if (cpdData != null) {
			dataFromCpd = cpdData.getCpdXml();
		}
		CpdReferenceDataDTO convertedCpdData = new CpdReferenceDataDTO();
		if (!dataFromCpd.equals("")) {
			convertedCpdData = (CpdReferenceDataDTO) util
					.unmarshal(dataFromCpd);
		}
		if (convertedCpdData != null) {
			BaseDTOMapper.copyProperties(candidateCPDData, convertedCpdData);
		}
		ricAndCpdData.setCpdFirstnameFull(candidateCPDData.getFirstName());
		ricAndCpdData.setCpdSurnameFull(candidateCPDData.getSurname());
		ricAndCpdData.setCpdSurnameBirthFull(candidateCPDData
				.getSurnameAtBirth());
		ricAndCpdData.setCpdGender(candidateCPDData.getSex());
		ricAndCpdData.setCpdMaritalStatus(candidateCPDData.getMaritalStatus());
		ricAndCpdData.setCapdAddressLine4(candidateCPDData.getTownVillage());
		ricAndCpdData.setCpdDateOfBirth(candidateCPDData.getBirthDate());
		ricAndCpdData.setCpdFatherName(candidateCPDData.getFatherFirstName());
		ricAndCpdData.setCpdFatherSurname(candidateCPDData.getFatherSurname());
		ricAndCpdData.setCpdMotherName(candidateCPDData.getMotherFirstName());
		ricAndCpdData.setCpdMotherSurname(candidateCPDData.getMotherSurname());
		ricAndCpdData.setCpdNin(candidateCPDData.getNin());
		model.addAttribute("fnSelected", Constants.HEADING_NIC_TRANS_ENQUIRY);
		return ricAndCpdData;
	}

	@ResponseBody
	@RequestMapping(value = "/jobEnqHitList/{txnId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView jobEnqHitList(WebRequest request,
			@PathVariable String txnId, ModelMap model) throws Exception {
		List<HitCandidateDTO> hitCandidatesList = new ArrayList<HitCandidateDTO>();
		try {
			hitCandidatesList = uploadJobService
					.getAfisHitCandidateListByTxnId(txnId);
		} catch (Exception ex) {
			logger.info("Error occurred while getting the job enquiry hit list for Transaction:"
					+ txnId + ", Reason: " + ex.getMessage());
		}

		return new ModelAndView("nic-enquiry-hitList", "hitCandidatesList",
				hitCandidatesList);
	}

	@ResponseBody
	@RequestMapping(value = "/txnEnqTrans/{txnId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView jobEnqTrans(WebRequest request,
			@PathVariable String txnId, ModelMap model) throws Exception {
		NicTransactionInfo nicTransactionInfo = null;
		try {
			NicTransaction nicTransaction = uploadJobService
					.getNicTransaction(txnId);
			if (nicTransaction != null) {
				nicTransactionInfo = getTransactionInfo(nicTransaction);
			}
		} catch (Exception ex) {
			logger.info("Error occurred while getting the job enquiry transaction info for Transaction:"
					+ txnId + ", Reason: " + ex.getMessage());
		}

		return new ModelAndView("nic-enquiry-transInfo", "nicTransaction",
				nicTransactionInfo);
	}

	@ResponseBody
	@RequestMapping(value = "/getPassportInfo/{txnId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView getPassportInfo(WebRequest request,
			@PathVariable String txnId, ModelMap model) throws Exception {
		List<NicDocumentDataInfo> nicDocumentDataInfoList = null;
		try {
			List<NicDocumentData> nicDocumentDataList = (List<NicDocumentData>) documentDataService
					.findByTransactionId(txnId);
			if (CollectionUtils.isNotEmpty(nicDocumentDataList)) {
				nicDocumentDataInfoList = new ArrayList<>();
				for (NicDocumentData nicDocumentData : nicDocumentDataList) {
					NicDocumentDataInfo documentDataInfo = new NicDocumentDataInfo();
					BeanUtils.copyProperties(documentDataInfo, nicDocumentData);
					documentDataInfo.setTransactionId(nicDocumentData.getId()
							.getTransactionId());
					documentDataInfo.setPassportNo(nicDocumentData.getId()
							.getPassportNo());
					if (nicDocumentData.getDispatchDatetime() != null) {
						documentDataInfo.setDispatchDatetime(DateUtil
								.parseDate(
										nicDocumentData.getDispatchDatetime(),
										"dd/MM/yyyy"));
					}
					if (nicDocumentData.getPackageDatetime() != null) {
						documentDataInfo.setPackageDatetime(DateUtil.parseDate(
								nicDocumentData.getPackageDatetime(),
								"dd/MM/yyyy"));
					}
					if (nicDocumentData.getDateOfIssue() != null) {
						documentDataInfo
								.setDateOfIssue(DateUtil.parseDate(
										nicDocumentData.getDateOfIssue(),
										"dd/MM/yyyy"));
					}
					if (nicDocumentData.getDateOfExpiry() != null) {
						documentDataInfo
								.setDateOfExpiry(DateUtil.parseDate(
										nicDocumentData.getDateOfExpiry(),
										"dd/MM/yyyy"));
					}

					String status = null;
					if (StringUtils.isNotBlank(nicDocumentData.getStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("TRANSACTION_STATUS",
										nicDocumentData.getStatus());

						if (codeValue != null) {
							status = codeValue.getCodeValueDesc();
						} else {
							status = nicDocumentData.getStatus();
						}
					}
					documentDataInfo.setStatus(status);

					if (nicDocumentData.getStatusUpdateTime() != null) {
						documentDataInfo.setStatusUpdateTime(DateUtil
								.parseDate(
										nicDocumentData.getStatusUpdateTime(),
										"dd/MM/yyyy"));
					}
					if (nicDocumentData.getCreateDatetime() != null) {
						documentDataInfo.setCreateDatetime(DateUtil.parseDate(
								nicDocumentData.getCreateDatetime(),
								"dd/MM/yyyy"));
					}
					if (nicDocumentData.getUpdateDatetime() != null) {
						documentDataInfo.setUpdateDatetime(DateUtil.parseDate(
								nicDocumentData.getUpdateDatetime(),
								"dd/MM/yyyy"));
					}
					if (nicDocumentData.getLastSyncDatetime() != null) {
						documentDataInfo.setLastSyncDatetime(DateUtil
								.parseDate(
										nicDocumentData.getLastSyncDatetime(),
										"dd/MM/yyyy"));
					}

					String docStatus = nicDocumentData.getStatus();
					if (TransactionStatus.Printed.getKey().equalsIgnoreCase(
							docStatus)
							|| TransactionStatus.QcCompleted.getKey()
									.equalsIgnoreCase(docStatus)
							|| TransactionStatus.Dispatched.getKey()
									.equalsIgnoreCase(docStatus)
							|| TransactionStatus.Received.getKey()
									.equalsIgnoreCase(docStatus)
							|| TransactionStatus.Reroute.getKey()
									.equalsIgnoreCase(docStatus)
							|| TransactionStatus.Active.getKey()
									.equalsIgnoreCase(docStatus)) {
						documentDataInfo.setReadyForCancel(true);
					}
					nicDocumentDataInfoList.add(documentDataInfo);
				}
			}
		} catch (Exception ex) {
			logger.info("Error occurred while getting the passport info for Transaction:"
					+ txnId + ", Reason: " + ex.getMessage());
		}

		ModelAndView mav = new ModelAndView("nic-enquiry-passport-info");
		mav.addObject("passportInfolist", nicDocumentDataInfoList);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/jobEnqHistoryEvents/getContent")
	@ExceptionHandler
	public List<NicTransactionLogInfo> jobEnqHistoryEvents(WebRequest request,
			String txnId) throws Exception {

		// String txnId = request.getParameter("txnId");

		logger.info("jobEnqHistoryEvents txnId:" + txnId + ":");

		if (StringUtils.isBlank(txnId)) {
			return new ArrayList<NicTransactionLogInfo>();
		}

		// int pageNo = request.getParameter("page") != null ?
		// Integer.parseInt(request.getParameter("page")) : 1;
		// int pageSize = request.getParameter("rp") != null ?
		// Integer.parseInt(request.getParameter("rp")) :
		// com.nec.asia.nic.framework.Constants.PAGE_SIZE_DEFAULT;

		// String sortedElement = request.getParameter("sortname") != null ?
		// request.getParameter("sortname") : "logId";
		// String order = request.getParameter("sortorder") != null ?
		// request.getParameter("sortorder") : "asc";
		// Order hibernateOrder = Order.desc(sortedElement);
		// if ("asc".equalsIgnoreCase(order)) {
		// hibernateOrder = Order.asc(sortedElement);
		// }

		List<NicTransactionLogInfo> nicTransactionLogInfos = null;
		List<NicTransactionLog> nicTransactionLogs = null;
		{
			try {
				// nicTransactionLogs =
				// this.transactionLogService.findAllForPagination(new
				// NicTransactionLog(null, txnId),
				// pageNo, pageSize, hibernateOrder);
				nicTransactionLogs = this.transactionLogService
						.findForListLog(new NicTransactionLog(null, txnId));
				logger.info("nicTransactionLogs:" + nicTransactionLogs + ":");
				// logger.info("nicTransactionLogs size:" +
				// (nicTransactionLogs!=null?nicTransactionLogs.getRowSize():"null")
				// + ":");

				if (nicTransactionLogs != null) {
					nicTransactionLogInfos = new ArrayList<NicTransactionLogInfo>();
					for (NicTransactionLog log : nicTransactionLogs) {

						NicTransactionLogInfo nicTransactionLogInfo = new NicTransactionLogInfo();

						PropertyUtils
								.copyProperties(nicTransactionLogInfo, log);

						String transactionStatus = null;
						if (log != null
								&& StringUtils.isNotBlank(log
										.getTransactionStatus())) {
							CodeValues codeValue = codesService
									.getCodeValueByIdName("TRANSACTION_STATUS",
											log.getTransactionStatus());

							if (codeValue != null) {
								transactionStatus = codeValue
										.getCodeValueDesc();
							} else {
								transactionStatus = log.getTransactionStatus();
							}
						}

						nicTransactionLogInfo
								.setTransactionStatus(transactionStatus);

						String regSiteCode = null;
						if (log != null
								&& StringUtils.isNotBlank(log.getSiteCode())) {
							SiteRepository regSite = siteService
									.getSiteRepoById(log.getSiteCode());
							if (regSite != null) {
								regSiteCode = regSite.getSiteName();
							} else {
								regSiteCode = log.getSiteCode();
							}
						}

						nicTransactionLogInfo.setSiteCode(regSiteCode);
						nicTransactionLogInfo
								.setLogCreateTimeDesc(nicTransactionLogInfo
										.getLogCreateTime() != null ? HelperClass
										.convertDateToString1(nicTransactionLogInfo
												.getLogCreateTime())
										: "");
						nicTransactionLogInfo.setWkstnId("N");
						if (StringUtils.isNotEmpty(nicTransactionLogInfo
								.getLogInfo())) {
							nicTransactionLogInfo.setWkstnId("Y");
						}
						// [22 Feb 2016][Tue] - Add
						// Set transaction stage
						String transactionStage = null;
						if (log != null
								&& StringUtils.isNotBlank(log
										.getTransactionStage())) {
							CodeValues codeValue = codesService
									.getCodeValueByIdName("TRANSACTION_STAGE",
											log.getTransactionStage());

							if (codeValue == null) {
								codeValue = codesService.getCodeValueByIdName(
										"JOB_STAGE", log.getTransactionStage());
							}
							if (codeValue != null) {
								transactionStage = codeValue.getCodeValueDesc();
							} else {
								transactionStage = log.getTransactionStage();
							}
						}
						nicTransactionLogInfo
								.setTransactionStage(transactionStage);

						nicTransactionLogInfos.add(nicTransactionLogInfo);
					}
				}
			} catch (Exception ex) {
				logger.info("Error occurred while getting the job enquiry transaction history for Transaction:"
						+ txnId + ", Reason: " + ex.getMessage());
			}
		}

		// PaginatedResult<NicTransactionLogInfo> result = new
		// PaginatedResult<NicTransactionLogInfo>(
		// nicTransactionLogs.getTotal(), nicTransactionLogs.getPage(),
		// nicTransactionLogInfos);
		return nicTransactionLogInfos;
	}

	@ResponseBody
	@RequestMapping(value = "/jobEnqHistoryEvents/{txnId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView jobEnqHistoryEvents(WebRequest request,
			@PathVariable String txnId, ModelMap model) throws Exception {
		List<NicTransactionLogInfo> nicTransactionLogInfos = new ArrayList<NicTransactionLogInfo>();
		nicTransactionLogInfos = jobEnqHistoryEvents(request, txnId);
		ModelAndView mav = new ModelAndView("nic-enquiry-history-events");
		mav.addObject("nicLog", nicTransactionLogInfos);
		mav.addObject("txnId", txnId);
		return mav;
	}

	// @ResponseBody
	// @RequestMapping(value = "/jobEnqHistoryEvents/{txnId}", method =
	// RequestMethod.GET)
	// @ExceptionHandler
	// public ModelAndView jobEnqHistoryEvents(WebRequest request, @PathVariable
	// String txnId, ModelMap model) throws Exception {
	// List<NicTransactionLogInfo> nicTransactionLogInfos = new
	// ArrayList<NicTransactionLogInfo>();
	// try {
	// List<NicTransactionLog> nicTransactionLogs =
	// uploadJobService.getNicTransactionLogs(txnId);
	// if (nicTransactionLogs != null) {
	// for (NicTransactionLog log : nicTransactionLogs) {
	//
	// NicTransactionLogInfo nicTransactionLogInfo = new
	// NicTransactionLogInfo();
	//
	// PropertyUtils.copyProperties(nicTransactionLogInfo, log);
	//
	// String transactionStatus = null;
	// if (log != null && StringUtils.isNotBlank(log.getTransactionStatus())) {
	// CodeValues codeValue =
	// codesService.getCodeValueByIdName("TRANSACTION_STATUS",
	// log.getTransactionStatus());
	//
	// if (codeValue != null) {
	// transactionStatus = codeValue.getCodeValueDesc();
	// } else {
	// transactionStatus = log.getTransactionStatus();
	// }
	// }
	//
	// nicTransactionLogInfo.setTransactionStatus(transactionStatus);
	//
	// String regSiteCode = null;
	// if (log != null && StringUtils.isNotBlank(log.getSiteCode())) {
	// SiteRepository regSite = siteService.getSiteRepoById(log.getSiteCode());
	// if (regSite != null) {
	// regSiteCode = regSite.getSiteName();
	// } else {
	// regSiteCode = log.getSiteCode();
	// }
	// }
	//
	// nicTransactionLogInfo.setSiteCode(regSiteCode);
	//
	// // [22 Feb 2016][Tue] - Add
	// // Set transaction stage
	// String transactionStage = null;
	// if (log != null && StringUtils.isNotBlank(log.getTransactionStage())) {
	// CodeValues codeValue =
	// codesService.getCodeValueByIdName("TRANSACTION_STAGE",
	// log.getTransactionStage());
	//
	// if (codeValue == null) {
	// codeValue = codesService.getCodeValueByIdName("JOB_STAGE",
	// log.getTransactionStage());
	// }
	// if (codeValue != null) {
	// transactionStage = codeValue.getCodeValueDesc();
	// } else {
	// transactionStage = log.getTransactionStage();
	// }
	// }
	// nicTransactionLogInfo.setTransactionStage(transactionStage);
	//
	// nicTransactionLogInfos.add(nicTransactionLogInfo);
	// }
	// }
	// } catch (Exception ex) {
	// logger.info("Error occurred while getting the job enquiry transaction history for Transaction:"
	// + txnId + ", Reason: " + ex.getMessage());
	// }
	//
	// return new ModelAndView("nic-enquiry-history-events",
	// "nicTransactionLogs", nicTransactionLogInfos);
	// }
	// @ResponseBody
	// @RequestMapping(value = "/jobEnqHistoryEvents/{txnId}", method =
	// RequestMethod.GET)
	// @ExceptionHandler
	// public PaginatedResult<NicTransactionLogInfo>
	// searchTransactionLog(WebRequest request, ModelMap model, @PathVariable
	// String txnId) throws Exception {
	// List<NicTransactionLogInfo> nicTransactionLogInfos = new
	// ArrayList<NicTransactionLogInfo>();
	// try {
	// String pages = request.getParameter("page");
	// String sortname = request.getParameter("sortname");
	// String sortorder = request.getParameter("sortorder");
	// String rp = request.getParameter("rp");
	// String recordPerPage = request.getParameter("rp");
	// if (!StringUtils.isNotBlank(recordPerPage)) {
	// recordPerPage = "50";
	// }
	//
	// int pageSize = Constants.PAGE_SIZE_DEFAULT;
	// ;
	// if (rp == null || "NaN".equals(rp)) {
	// //pageSize =
	// Integer.parseInt(configurationService.getUserAdminConsoleJobQueueMaxRecordsPerPage(getUserSession(request).getUserId()).getSettings());
	// } else {
	// pageSize = Integer.parseInt(rp);
	// }
	//
	// int startIndex = 0;
	// String pageNumber = pages;
	// if (StringUtils.isNotBlank(pageNumber)) {
	// startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
	// }
	// PageRequest pageRequest = new PageRequest();
	// pageRequest.setCalculateRecordCount(true);
	// pageRequest.setFirstRowIndex(startIndex);
	// pageRequest.setMaxRecords(pageSize);
	// pageRequest.setSortname(sortname);
	// pageRequest.setSortorder(sortorder);
	// pageRequest.setPageNo(Integer.parseInt(pageNumber));
	// NicTransactionLog nicTransactionLog = new NicTransactionLog();
	// nicTransactionLog.setRefId(txnId);
	//
	// PaginatedResult<NicTransactionLog> pr =
	// transactionLogService.findAllForPagination(pageRequest,
	// nicTransactionLog);
	// if (pr != null) {
	// for (NicTransactionLog log : pr.getRows()) {
	//
	// NicTransactionLogInfo nicTransactionLogInfo = new
	// NicTransactionLogInfo();
	//
	// PropertyUtils.copyProperties(nicTransactionLogInfo, log);
	//
	// String transactionStatus = null;
	// if (log != null && StringUtils.isNotBlank(log.getTransactionStatus())) {
	// CodeValues codeValue =
	// codesService.getCodeValueByIdName("TRANSACTION_STATUS",
	// log.getTransactionStatus());
	//
	// if (codeValue != null) {
	// transactionStatus = codeValue.getCodeValueDesc();
	// } else {
	// transactionStatus = log.getTransactionStatus();
	// }
	// }
	//
	// nicTransactionLogInfo.setTransactionStatus(transactionStatus);
	//
	// String regSiteCode = null;
	// if (log != null && StringUtils.isNotBlank(log.getSiteCode())) {
	// SiteRepository regSite = siteService.getSiteRepoById(log.getSiteCode());
	// if (regSite != null) {
	// regSiteCode = regSite.getSiteName();
	// } else {
	// regSiteCode = log.getSiteCode();
	// }
	// }
	//
	// nicTransactionLogInfo.setSiteCode(regSiteCode);
	//
	// // [22 Feb 2016][Tue] - Add
	// // Set transaction stage
	// String transactionStage = null;
	// if (log != null && StringUtils.isNotBlank(log.getTransactionStage())) {
	// CodeValues codeValue =
	// codesService.getCodeValueByIdName("TRANSACTION_STAGE",
	// log.getTransactionStage());
	//
	// if (codeValue == null) {
	// codeValue = codesService.getCodeValueByIdName("JOB_STAGE",
	// log.getTransactionStage());
	// }
	// if (codeValue != null) {
	// transactionStage = codeValue.getCodeValueDesc();
	// } else {
	// transactionStage = log.getTransactionStage();
	// }
	// }
	// nicTransactionLogInfo.setTransactionStage(transactionStage);
	//
	// nicTransactionLogInfos.add(nicTransactionLogInfo);
	// }
	// }
	//
	//
	// if (CollectionUtils.isNotEmpty(nicTransactionLogInfos)) {
	// return new PaginatedResult<NicTransactionLogInfo>(pr.getTotal(),
	// pr.getPage(), nicTransactionLogInfos);
	// } else {
	// return new PaginatedResult<NicTransactionLogInfo>(0, 0, new
	// ArrayList<NicTransactionLogInfo>());
	// }
	// } catch (Exception ex) {
	// logger.info("Error occurred while getting the job enquiry transaction history for Transaction:"
	// + txnId + ", Reason: " + ex.getMessage());
	// return new PaginatedResult<NicTransactionLogInfo>(0, 0, new
	// ArrayList<NicTransactionLogInfo>());
	// }
	//
	// }

	@ResponseBody
	@RequestMapping(value = "/txnDetailInit/{txnId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView txnEnquiryDetailInit(WebRequest request,
			@PathVariable String txnId, ModelMap model) throws Exception {
		// 25-May-2016 (khang): Add logic to handle Prioritize, Reprint, Cancel
		// Passport
		boolean showPrioritizeBtn = false;
		boolean showReprintBtn = false;
		try {
			if (StringUtils.isNotEmpty(txnId)) {
				NicTransaction nicTransaction = nicTransactionService
						.findById(txnId);

				if (nicTransaction != null
						&& StringUtils.isNotBlank(nicTransaction
								.getTransactionStatus())) {
					String txnStatus = nicTransaction.getTransactionStatus();
					if (TransactionStatus.Afis.getKey().equalsIgnoreCase(
							txnStatus)
							|| TransactionStatus.PendingInvestigation.getKey()
									.equalsIgnoreCase(txnStatus)
							|| TransactionStatus.Staging.getKey()
									.equalsIgnoreCase(txnStatus)) {
						showPrioritizeBtn = true;
					}

					if (TransactionStatus.QcCompleted.getKey()
							.equalsIgnoreCase(txnStatus)
							|| TransactionStatus.Dispatched.getKey()
									.equalsIgnoreCase(txnStatus)
							|| TransactionStatus.Received.getKey()
									.equalsIgnoreCase(txnStatus)
							|| TransactionStatus.Reroute.getKey()
									.equalsIgnoreCase(txnStatus)
							|| TransactionStatus.Active.getKey()
									.equalsIgnoreCase(txnStatus)
							|| TransactionStatus.Rejected.getKey()
									.equalsIgnoreCase(txnStatus)) {
						showReprintBtn = true;
					} else if (TransactionStatus.Faulted.getKey()
							.equalsIgnoreCase(txnStatus)
							&& this.isFAAndHasAnFADocDoneInIssuance(txnId)) {
						showReprintBtn = true;
					}
				}

			}

		} catch (Exception ex) {
			logger.error(
					"Error occurred while getting the status for Transaction:"
							+ txnId + ", Reason: " + ex.getMessage(), ex);
		}

		ModelAndView mav = new ModelAndView("enquiry-details-init");
		NicEnquiryForm nicEnqForm = new NicEnquiryForm();
		mav.addObject("showPrioritizeBtn", showPrioritizeBtn);
		mav.addObject("showReprintBtn", showReprintBtn);
		mav.addObject("nicEnqDetailsForm", nicEnqForm);
		return mav;
	}

	private boolean isFAAndHasAnFADocDoneInIssuance(String transactionId)
			throws Exception {

		List<NicDocumentData> docs = this.documentDataDao
				.findAllByTransactionId(transactionId,
						Arrays.asList(TransactionStatus.Faulted.getKey()));

		if (docs == null) {
			return false;
		}

		if (docs.size() == 0) {
			return false;
		}

		for (NicDocumentData doc : docs) {
			if (doc.getRejectBy() != null
					&& doc.getRejectBy().trim().length() > 0) {
				return true;
			}
		}

		return false;
	}

	@ResponseBody
	@RequestMapping(value = "/jobEnqJobDetails/{jobId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView jobEnqJobDetails(WebRequest request,
			@PathVariable String jobId, ModelMap model) throws Exception {
		NicUploadJobInfo nicUploadJobInfo = null;
		boolean showErrorBtn = false;
		boolean showRetryBtn = false;
		try {
			if (StringUtils.isNumeric(jobId)) {
				NicUploadJob nicUploadJob = uploadJobService
						.getJobEnqueryDetails(new Long(jobId));
				if (nicUploadJob != null) {
					logger.info("jobId:" + jobId + ", CpdCheckStatus: "
							+ nicUploadJob.getCpdCheckStatus()
							+ ", AfisCheckStatus: "
							+ nicUploadJob.getAfisCheckStatus()
							+ ", AfisRegisterStatus: "
							+ nicUploadJob.getAfisRegisterStatus()
							+ ", AfisCheckStatus: "
							+ nicUploadJob.getAfisCheckStatus()
							+ ", AfisVerifyStatus: "
							+ nicUploadJob.getAfisVerifyStatus()
							+ ", PersoRegisterStatus: "
							+ nicUploadJob.getPersoRegisterStatus());
					nicUploadJobInfo = new NicUploadJobInfo();

					PropertyUtils
							.copyProperties(nicUploadJobInfo, nicUploadJob);
					// nicUploadJobInfo.setNicJobErrorFlag(nicUploadJob.getJobErrorFlag());
					nicUploadJobInfo.setNicJobCompletedFlag(nicUploadJob
							.getJobCompletedFlag());
					// logger.info("jobId:" + jobId +
					// ", nicUploadJob.getJobErrorFlag(): " +
					// nicUploadJob.getJobErrorFlag() +
					// ", nicUploadJob.getJobReprocessCount(): " +
					// nicUploadJob.getJobReprocessCount());
					logger.info("jobId:" + jobId
							+ ", nicUploadJobInfo.getNicJobErrorFlag(): "
							+ nicUploadJobInfo.getNicJobErrorFlag()
							+ ", nicUploadJobInfo.getJobReprocessCount(): "
							+ nicUploadJobInfo.getJobReprocessCount());

					if (nicUploadJob.getJobStartTime() != null) {
						nicUploadJobInfo.setJobStartDateTime(DateUtil
								.parseDate(nicUploadJob.getJobStartTime(),
										"dd-MMM-yyyy hh:mm:ss"));
					}

					if (nicUploadJob.getJobEndTime() != null) {
						nicUploadJobInfo.setJobEndDateTime(DateUtil.parseDate(
								nicUploadJob.getJobEndTime(),
								"dd-MMM-yyyy hh:mm:ss"));
					}

					if (nicUploadJob.getCreateDatetime() != null) {
						nicUploadJobInfo.setCreateDateTime(DateUtil.parseDate(
								nicUploadJob.getCreateDatetime(),
								"dd-MMM-yyyy hh:mm:ss"));
					}

					if (nicUploadJob.getUpdateDatetime() != null) {
						nicUploadJobInfo.setUpdateDateTime(DateUtil.parseDate(
								nicUploadJob.getUpdateDatetime(),
								"dd-MMM-yyyy hh:mm:ss"));
					}

					if (nicUploadJob.getJobCreatedTime() != null) {
						nicUploadJobInfo.setJobUploadDateTime(DateUtil
								.parseDate(nicUploadJob.getJobCreatedTime(),
										"dd-MMM-yyyy hh:mm:ss"));
					}

					String persoRegisterStatus = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getPersoRegisterStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("INVESTIGATION_STATUS",
										nicUploadJobInfo
												.getPersoRegisterStatus());

						if (codeValue != null) {
							persoRegisterStatus = codeValue.getCodeValueDesc();
						} else {
							persoRegisterStatus = nicUploadJobInfo
									.getPersoRegisterStatus();
						}
					}

					nicUploadJobInfo
							.setPersoRegisterStatus(persoRegisterStatus);

					String cpdCheckStatus = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getCpdCheckStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("INVESTIGATION_STATUS",
										nicUploadJobInfo.getCpdCheckStatus());

						if (codeValue != null) {
							cpdCheckStatus = codeValue.getCodeValueDesc();
						} else {
							cpdCheckStatus = nicUploadJobInfo
									.getCpdCheckStatus();
						}
					}

					nicUploadJobInfo.setCpdCheckStatus(cpdCheckStatus);

					String afisCheckStatus = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getAfisCheckStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("INVESTIGATION_STATUS",
										nicUploadJobInfo.getAfisCheckStatus());

						if (codeValue != null) {
							afisCheckStatus = codeValue.getCodeValueDesc();
						} else {
							afisCheckStatus = nicUploadJobInfo
									.getAfisCheckStatus();
						}
					}

					nicUploadJobInfo.setAfisCheckStatus(afisCheckStatus);

					String jobTypeDesc = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getJobType())) {
						// CodeValues codeValue =
						// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
						// nicUploadJobInfo.getJobType());
						CodeValues codeValue = codesService
								.getCodeValueByIdName("JOB_TYPE",
										nicUploadJobInfo.getJobType()); // 2014
																		// Aug
																		// 20

						if (codeValue != null) {
							jobTypeDesc = codeValue.getCodeValueDesc();
						} else {
							jobTypeDesc = nicUploadJobInfo.getJobType();
						}
					}

					nicUploadJobInfo.setJobType(jobTypeDesc);

					String status = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getInvestigationStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("INVESTIGATION_STATUS",
										nicUploadJobInfo
												.getInvestigationStatus());

						if (codeValue != null) {
							status = codeValue.getCodeValueDesc();
						} else {
							status = nicUploadJobInfo.getInvestigationStatus();
						}
					}

					nicUploadJobInfo.setInvestigationStatus(status);

					String currentStage = null;

					if (nicUploadJobInfo != null) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("JOB_STAGE",
										nicUploadJobInfo.getJobCurrentState());
						if (codeValue == null) {
							codeValue = codesService.getCodeValueByIdName(
									"JOB_STAGE",
									nicUploadJob.getJobCurrentStage());
						}

						if (codeValue != null) {
							currentStage = codeValue.getCodeValueDesc();
						}
					}

					nicUploadJobInfo.setJobCurrentState(currentStage);

					String afisRegStatus = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getAfisRegisterStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("INVESTIGATION_STATUS",
										nicUploadJobInfo
												.getAfisRegisterStatus());

						if (codeValue != null) {
							afisRegStatus = codeValue.getCodeValueDesc();
						} else {
							afisRegStatus = nicUploadJobInfo
									.getAfisRegisterStatus();
						}
					}

					nicUploadJobInfo.setAfisRegisterStatus(afisRegStatus);

					String afisVerifyStatus = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getAfisVerifyStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("INVESTIGATION_STATUS",
										nicUploadJobInfo.getAfisVerifyStatus());

						if (codeValue != null) {
							afisVerifyStatus = codeValue.getCodeValueDesc();
						} else {
							afisVerifyStatus = nicUploadJobInfo
									.getAfisVerifyStatus();
						}
					}

					nicUploadJobInfo.setAfisVerifyStatus(afisVerifyStatus);

					String syncTxCpdStatus = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getSyncTxCpdStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("INVESTIGATION_STATUS",
										nicUploadJobInfo.getSyncTxCpdStatus());

						if (codeValue != null) {
							syncTxCpdStatus = codeValue.getCodeValueDesc();
						} else {
							syncTxCpdStatus = nicUploadJobInfo
									.getSyncTxCpdStatus();
						}
					}

					nicUploadJobInfo.setSyncTxCpdStatus(syncTxCpdStatus);

					String syncCardCpdStatus = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getSyncCardCpdStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("INVESTIGATION_STATUS",
										nicUploadJobInfo.getSyncCardCpdStatus());

						if (codeValue != null) {
							syncCardCpdStatus = codeValue.getCodeValueDesc();
						} else {
							syncCardCpdStatus = nicUploadJobInfo
									.getSyncCardCpdStatus();
						}
					}

					nicUploadJobInfo.setSyncCardCpdStatus(syncCardCpdStatus);

					String dataPreparationStatus = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getDataPreparationStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("INVESTIGATION_STATUS",
										nicUploadJobInfo
												.getDataPreparationStatus());

						if (codeValue != null) {
							dataPreparationStatus = codeValue
									.getCodeValueDesc();
						} else {
							dataPreparationStatus = nicUploadJobInfo
									.getDataPreparationStatus();
						}
					}

					nicUploadJobInfo
							.setDataPreparationStatus(dataPreparationStatus);

					String syncInventoryStatus = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getSyncInventoryStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("INVESTIGATION_STATUS",
										nicUploadJobInfo
												.getSyncInventoryStatus());

						if (codeValue != null) {
							syncInventoryStatus = codeValue.getCodeValueDesc();
						} else {
							syncInventoryStatus = nicUploadJobInfo
									.getSyncInventoryStatus();
						}
					}

					nicUploadJobInfo
							.setSyncInventoryStatus(syncInventoryStatus);

					String jobCurrentStatus = null;
					if (nicUploadJobInfo != null
							&& StringUtils.isNotBlank(nicUploadJobInfo
									.getJobCurrentStatus())) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("JOB_STATUS",
										nicUploadJobInfo.getJobCurrentStatus());

						if (codeValue != null) {
							jobCurrentStatus = codeValue.getCodeValueDesc();
						} else {
							jobCurrentStatus = nicUploadJobInfo
									.getJobCurrentStatus();
						}
					}

					nicUploadJobInfo.setJobCurrentStatus(jobCurrentStatus);
					// 2014 Aug 20 - simplify logic
					if (nicUploadJobInfo.getNicJobErrorFlag()) {

						NicTransactionLog nicTransactionLog = uploadJobService
								.getLatestErrorMessage(nicUploadJobInfo
										.getTransactionId());
						if (nicTransactionLog != null) {
							nicUploadJobInfo.setErrorDesc(nicTransactionLog
									.getLogData());
						}

						if (nicUploadJobInfo.getJobReprocessCount() != null
								&& nicUploadJobInfo.getJobReprocessCount()
										.intValue() >= 3) {
							showRetryBtn = true;
						}

						if (StringUtils.contains(
								nicUploadJobInfo.getJobCurrentStatus(),
								"_ERROR")) {
							showErrorBtn = true;
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(
					"Error occurred while getting the job deatils for Transaction:"
							+ jobId + ", Reason: " + ex.getMessage(), ex);
		}

		ModelAndView mav = new ModelAndView("nic-enquiry-jobInfo");
		mav.addObject("nicUploadJob", nicUploadJobInfo);
		mav.addObject("showErrorBtn", showErrorBtn);
		mav.addObject("showRetryBtn", showRetryBtn);

		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/jobEnqDemographicDetails/{jobId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView jobEnqDemographicDetails(WebRequest request,
			@PathVariable String jobId, ModelMap model) throws Exception {
		NicRegistrationDataDto ricAndCpdData = null;
		ModelAndView mav = new ModelAndView("nic-enquiry-demographicInfo");
		try {
			HitCandidateDTO candidateCPDData = new HitCandidateDTO();
			NicUploadJob nicUploadJob = uploadJobService.findById(Long
					.valueOf(jobId));
			String ricDOB = null;
			String cpdDOB = null;
			String townVillageDesc = "";
			String districtDesc = "";

			String cpdTownVillageDesc = "";
			String cpdDistrictDesc = "";
			// nicForm.setNicUploadJob(nicUploadJob);

			// 2) Get The Photo of the main candidate of the current job by
			// Transaction Id

			String transactionId = nicUploadJob.getTransactionId();// this one
																	// is null

			NicRegistrationData nicRegistrationData = uploadJobService
					.getNicRegistrationData(transactionId);
			ricAndCpdData = new NicRegistrationDataDto(nicRegistrationData);

			if (ricAndCpdData != null) {

				if (ricAndCpdData.getAddressLine4() != null
						&& StringUtils.isNotBlank(ricAndCpdData
								.getAddressLine4())) {
					CodeValues codeValue = codesService.getCodeValueByIdName(
							"TOWN_VILLAGE", ricAndCpdData.getAddressLine4());
					if (codeValue != null) {
						townVillageDesc = codeValue.getCodeValueDesc();
					} else {
						townVillageDesc = ricAndCpdData.getAddressLine4();
					}
				}
				String district = ricAndCpdData.getAddressLine5();
				if (district != null && StringUtils.isNotBlank(district)) {
					CodeValues codeValue = codesService.getCodeValueByIdName(
							"DISTRICT", district);

					if (codeValue != null) {
						districtDesc = codeValue.getCodeValueDesc();
					} else {
						districtDesc = district;
					}
				}

				if (ricAndCpdData.getCapdAddressLine4() != null
						&& StringUtils.isNotBlank(ricAndCpdData
								.getCapdAddressLine4())) {
					CodeValues codeValue = codesService
							.getCodeValueByIdName("TOWN_VILLAGE",
									ricAndCpdData.getCapdAddressLine4());
					if (codeValue != null) {
						cpdTownVillageDesc = codeValue.getCodeValueDesc();
					} else {
						cpdTownVillageDesc = ricAndCpdData
								.getCapdAddressLine4();
					}
				}
				String cpdDistrict = ricAndCpdData.getCpdAddressLine5();
				if (cpdDistrict != null && StringUtils.isNotBlank(cpdDistrict)) {
					CodeValues codeValue = codesService.getCodeValueByIdName(
							"DISTRICT", cpdDistrict);

					if (codeValue != null) {
						cpdDistrictDesc = codeValue.getCodeValueDesc();
					} else {
						cpdDistrictDesc = cpdDistrict;
					}
				}

				if (StringUtils.isNotBlank(ricAndCpdData.getGender())) {
					if (ricAndCpdData.getGender().equalsIgnoreCase("M")) {
						ricAndCpdData.setGender("Male");
					} else if (ricAndCpdData.getGender().equalsIgnoreCase("F")) {
						ricAndCpdData.setGender("Female");
					} else if (ricAndCpdData.getGender().equalsIgnoreCase("X")) {
						ricAndCpdData.setGender("Unknown");
					}
				}

				if (StringUtils.isNotBlank(ricAndCpdData.getMaritalStatus())) {
					if (ricAndCpdData.getMaritalStatus().equalsIgnoreCase("M")) {
						ricAndCpdData.setMaritalStatus("Married");
					} else if (ricAndCpdData.getMaritalStatus()
							.equalsIgnoreCase("S")) {
						ricAndCpdData.setMaritalStatus("Single");
					} else if (ricAndCpdData.getMaritalStatus()
							.equalsIgnoreCase("O")) {
						ricAndCpdData.setMaritalStatus("Others");
					}
				}

				if (ricAndCpdData.getDateOfBirth() != null) {
					ricDOB = DateUtil.parseDate(ricAndCpdData.getDateOfBirth(),
							"dd-MMM-yyyy");
				}
			}
			NicJobCpdCheckResult cpdData = uploadJobService.findCpdData(Long
					.valueOf(jobId));
			String dataFromCpd = "";
			if (cpdData != null) {
				dataFromCpd = cpdData.getCpdXml();
			}
			CpdReferenceDataDTO convertedCpdData = new CpdReferenceDataDTO();
			if (!dataFromCpd.equals("")) {
				convertedCpdData = (CpdReferenceDataDTO) util
						.unmarshal(dataFromCpd);
			}
			if (convertedCpdData != null) {
				BaseDTOMapper
						.copyProperties(candidateCPDData, convertedCpdData);

				ricAndCpdData.setCpdFirstnameFull(candidateCPDData
						.getFirstName());
				ricAndCpdData.setCpdSurnameFull(candidateCPDData.getSurname());
				ricAndCpdData.setCpdSurnameBirthFull(candidateCPDData
						.getSurnameAtBirth());
				// ricAndCpdData.setCpdGender(candidateCPDData.getSex());

				if (candidateCPDData != null) {
					if (StringUtils.isNotBlank(candidateCPDData.getSex())) {
						if (candidateCPDData.getSex().equalsIgnoreCase("M")) {
							ricAndCpdData.setCpdGender("Male");
						} else if (candidateCPDData.getSex().equalsIgnoreCase(
								"F")) {
							ricAndCpdData.setCpdGender("Female");
						} else if (candidateCPDData.getSex().equalsIgnoreCase(
								"X")) {
							ricAndCpdData.setCpdGender("Unknown");
						}
					}
				}

				if (StringUtils.isNotBlank(candidateCPDData.getMaritalStatus())) {
					if (candidateCPDData.getMaritalStatus().equalsIgnoreCase(
							"M")) {
						ricAndCpdData.setCpdMaritalStatus("Married");
					} else if (candidateCPDData.getMaritalStatus()
							.equalsIgnoreCase("S")) {
						ricAndCpdData.setCpdMaritalStatus("Single");
					} else if (candidateCPDData.getMaritalStatus()
							.equalsIgnoreCase("O")) {
						ricAndCpdData.setCpdMaritalStatus("Others");
					}
				}

				if (candidateCPDData.getBirthDate() != null) {
					cpdDOB = DateUtil.parseDate(
							candidateCPDData.getBirthDate(), "dd-MMM-yyyy");
				}

				// ricAndCpdData.setCpdMaritalStatus(candidateCPDData.getMaritalStatus());
				ricAndCpdData.setCapdAddressLine4(candidateCPDData
						.getTownVillage());
				ricAndCpdData
						.setCpdDateOfBirth(candidateCPDData.getBirthDate());
				ricAndCpdData.setCpdFatherName(candidateCPDData
						.getFatherFirstName());
				ricAndCpdData.setCpdFatherSurname(candidateCPDData
						.getFatherSurname());
				ricAndCpdData.setCpdMotherName(candidateCPDData
						.getMotherFirstName());
				ricAndCpdData.setCpdMotherSurname(candidateCPDData
						.getMotherSurname());
				ricAndCpdData.setCpdNin(candidateCPDData.getNin());
			}
			model.addAttribute("fnSelected",
					Constants.HEADING_NIC_TRANS_ENQUIRY);
			mav.addObject("ricAndCpdData", ricAndCpdData);
			mav.addObject("ricDOB", ricDOB);
			mav.addObject("cpdDOB", cpdDOB);

			mav.addObject("townVillageDesc", townVillageDesc);
			mav.addObject("districtDesc", districtDesc);
			mav.addObject("cpdTownVillageDesc", cpdTownVillageDesc);
			mav.addObject("cpdDistrictDesc", cpdDistrictDesc);

		} catch (Exception ex) {
			logger.info("Error occurred while getting the demographic info for :"
					+ jobId + ", Reason: " + ex.getMessage());
		}

		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/getPersonSummary/{txnId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView getPersonSummary(WebRequest request,
			@PathVariable String txnId, ModelMap model) throws Exception {
		NicRegistrationData nicRegistrationData = null;
		String photoStr = null;
		String signatureStr = null;
		String signatureFlag = "N";
		String fullAmpFlag = "N";
		String approxDOBFlag = "N";
		String dob = "";
		String townVillageDesc = "";
		String districtDesc = "";
		// [19Feb2016][Tue] - Add
		String passportNo = "";
		String passportStatus = "";
		List<NicDocumentData> docList = null;
		try {
			nicRegistrationData = uploadJobService
					.getNicRegistrationData(txnId);
			List<NicTransactionAttachment> photoList = nicTransactionDocumentService
					.findNicTransactionAttachments(txnId,
							NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
							NicTransactionAttachment.DEFAULT_SERIAL_NO)
					.getListModel();
			List<NicTransactionAttachment> signatureList = nicTransactionDocumentService
					.findNicTransactionAttachments(txnId,
							NicTransactionAttachment.DOC_TYPE_SIGNATURE,
							NicTransactionAttachment.DEFAULT_SERIAL_NO)
					.getListModel();
			if (CollectionUtils.isNotEmpty(signatureList)) {
				byte[] signBinary = signatureList.get(0).getDocData();
				signatureStr = Base64.encodeBase64String(signBinary);
				signatureFlag = "S";
			} else if (CollectionUtils.isEmpty(signatureList)) { // Thumbprint
																	// as
																	// Signature
																	// 'T'
				List<NicTransactionAttachment> fpSignList = nicTransactionDocumentService
						.findNicTransactionAttachments(txnId,
								NicTransactionAttachment.DOC_TYPE_SIGN_FP,
								NicTransactionAttachment.DEFAULT_SERIAL_NO)
						.getListModel();
				if (CollectionUtils.isNotEmpty(fpSignList)) {
					byte[] signBinary = fpSignList.get(0).getDocData();
					signatureStr = Base64.encodeBase64String(signBinary);
					signatureFlag = "T";
				}
			}
			// else {
			// fullAmpFlag = "Y";
			// }
			if (StringUtils.isBlank(nicRegistrationData.getFpQuality())) {
				fullAmpFlag = "Y";
				logger.info("[{}] FpQuality is empty, fullAmpFlag: {}", txnId,
						fullAmpFlag);
			}

			if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
				photoStr = Base64.encodeBase64String(photoList.get(0)
						.getDocData());
			}

			if (nicRegistrationData.getDateOfBirth() != null) {
				dob = DateUtil.parseDate(nicRegistrationData.getDateOfBirth(),
						"dd/MM/yyyy");

			} else {
				approxDOBFlag = "Y";
				dob = nicRegistrationData.getApproxDob();
			}
			townVillageDesc = nicRegistrationData.getAddressLine1();
			if (nicRegistrationData.getAddressLine4() != null
					&& StringUtils.isNotBlank(nicRegistrationData
							.getAddressLine4())) {
				CodeValues codeValue = codesService.getCodeValueByIdName(
						"TOWN_VILLAGE", nicRegistrationData.getAddressLine4());
				if (codeValue != null) {
					townVillageDesc += ", " + codeValue.getCodeValueDesc();
				} else {
					// townVillageDesc = nicRegistrationData.getAddressLine4();
				}
			}

			if (nicRegistrationData.getAddressLine5() != null
					&& StringUtils.isNotBlank(nicRegistrationData
							.getAddressLine5())) {
				CodeValues codeValue = codesService.getCodeValueByIdName(
						"DISTRICT", nicRegistrationData.getAddressLine5());
				if (codeValue != null) {
					townVillageDesc += ", " + codeValue.getCodeValueDesc();
				} else {
					// townVillageDesc = nicRegistrationData.getAddressLine5();
				}
			}
			nicRegistrationData.setAddressLine1(townVillageDesc);
			nicRegistrationData.setSurnameFull(nicRegistrationData
					.getSurnameLine1());
			docList = (List<NicDocumentData>) documentDataService
					.findByTransactionId(txnId);
			if (CollectionUtils.isNotEmpty(docList)
					&& null != docList.get(0).getId()) {
				passportNo = docList.get(0).getId().getPassportNo();
				if (StringUtils.isNotBlank(docList.get(0).getStatus())) {
					CodeValues codeValue = codesService.getCodeValueByIdName(
							"TRANSACTION_STATUS", docList.get(0).getStatus());
					if (codeValue != null) {
						passportStatus = codeValue.getCodeValueDesc();
					} else {
						passportStatus = docList.get(0).getStatus();
					}
				}

			}

		} catch (Exception ex) {
			logger.info("Error occurred while getting the person summary information for Transaction:"
					+ txnId + ", Reason: " + ex.getMessage());
		}

		ModelAndView mav = new ModelAndView("nic-enquiry-person-summary");

		mav.addObject("photoStr", photoStr);
		mav.addObject("signatureStr", signatureStr);
		mav.addObject("fullAmpFlag", fullAmpFlag);
		mav.addObject("signatureFlag", signatureFlag);
		mav.addObject("approxDOBFlag", approxDOBFlag);
		mav.addObject("townVillageDesc", townVillageDesc);
		mav.addObject("districtDesc", districtDesc);
		mav.addObject("dob", dob);
		mav.addObject("nicRegistrationData", nicRegistrationData);
		mav.addObject("passportNo", passportNo);
		mav.addObject("passportStatus", passportStatus);

		return mav;

	}

	@ResponseBody
	@RequestMapping(value = "/getTransDocuments/{txnId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView getTransDocuments(WebRequest request,
			@PathVariable String txnId, ModelMap model,
			HttpServletRequest httpServletRequest) throws Exception {
		List<NicTransactionAttachment> nicTransactionDocuments = null;
		List<RicRegistrationDocumentDTO> proofDocList = new ArrayList<RicRegistrationDocumentDTO>();
		// [20 Feb 2016][Tue] - Add
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		// int currentPage = 1;
		// String tableId = "proofDocList";
		// String sortedElement = "serialNo";
		// String order = DESCENDING_ORDER;
		// ParametersId id = new ParametersId();
		// id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
		// id.setParaScope("SYSTEM");
		try {

			// Order hibernateOrder = Order.desc(sortedElement);
			// if (order.equalsIgnoreCase(ASCENDING_ORDER)) {
			// hibernateOrder = Order.asc(sortedElement);
			// }

			// try {
			// if (null != request.getParameter((new
			// ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE))))
			// {
			// currentPage = Integer.parseInt(request.getParameter((new
			// ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE))));
			// }
			// // End
			// } catch (Exception ex) {
			// throw ex;
			// }

			// Parameters parameter = parametersService.findById(id);
			// if (parameter != null) {
			// String pageSizeDb = parameter.getParaShortValue();
			//
			// if (StringUtils.isNotBlank(pageSizeDb) &&
			// StringUtils.isNumeric(pageSizeDb)) {
			// pageSize = Integer.parseInt(pageSizeDb);
			// }
			// }

			httpServletRequest.setAttribute("txnId", txnId);
			ImageUtil imageUtil = new ImageUtil();

			String[] excludedDocTypes = {
					NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
					NicTransactionAttachment.DOC_TYPE_MINUTIA,
					NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
					NicTransactionAttachment.DOC_TYPE_ENCODING,
					NicTransactionAttachment.DOC_TYPE_PERSO,
					NicTransactionAttachment.DOC_TYPE_SIGNATURE,
					NicTransactionAttachment.DOC_TYPE_SIGN_FP,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
					NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE,
					NicTransactionAttachment.DOC_TYPE_PHOTO_GREY,
					NicTransactionAttachment.DOC_TYPE_THUMBNAIL_GREY,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
					NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CHIP,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CLI,
					NicTransactionAttachment.DOC_TYPE_TPL };

			nicTransactionDocuments = nicTransactionDocumentService
					.getTransactionProofDocuments(txnId, excludedDocTypes);
			if (CollectionUtils.isNotEmpty(nicTransactionDocuments)) {
				for (NicTransactionAttachment nicTransProofDocument : nicTransactionDocuments) {
					RicRegistrationDocumentDTO proofDoc = new RicRegistrationDocumentDTO();
					String decodedDocString = Base64
							.encodeBase64String(nicTransProofDocument
									.getDocData());
					String imageType = imageUtil
							.checkImageType(nicTransProofDocument.getDocData());
					String docType = nicTransProofDocument.getDocType();

					if (StringUtils.isNotBlank(imageType)
							&& imageType.equalsIgnoreCase(ImageUtil.IMAGE_JPG)) {
						proofDoc.setDocumentName("D"
								+ nicTransProofDocument.getTransactionDocId());
						proofDoc.setType("JPEG");
					} else {
						proofDoc.setDocumentName(docType);
						proofDoc.setType("PDF");
					}

					proofDoc.setDocumentId(String.valueOf(nicTransProofDocument
							.getTransactionDocId()));
					proofDoc.setTransactionNo(nicTransProofDocument
							.getTransactionId());
					proofDoc.setSerialNumber(nicTransProofDocument
							.getSerialNo());

					String docTypeDesc = null;
					// Document Description from Code Table
					if (docType != null && StringUtils.isNotBlank(docType)) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("DOC_TYPE", docType);
						if (codeValue != null) {
							docTypeDesc = codeValue.getCodeValueDesc();
						} else {
							docTypeDesc = docType;
						}
					}
					// proofDoc.setDocumentName(docType);

					proofDoc.setPurpose(docTypeDesc);

					proofDoc.setDmsLink(this.getDmsLink(
							nicTransProofDocument.getTransactionDocId(),
							nicTransProofDocument.getTransactionId(),
							nicTransProofDocument.getDocType()));

					List<String> documentList = new ArrayList<String>();
					documentList.add(decodedDocString);
					proofDoc.setDocument(documentList);
					proofDocList.add(proofDoc);
				}
			}
		} catch (Exception ex) {
			logger.info("Error occurred while getting the transaction documents for Transaction:"
					+ txnId + ", Reason: " + ex.getMessage());
		}
		httpServletRequest.setAttribute("pageSize", pageSize);
		httpServletRequest.setAttribute("proofDocList", proofDocList);
		return new ModelAndView("nic-enquiry-trans-docs");

	}

	private String getDmsLink(Long transactionDocId, String transactionId,
			String docType) throws Exception {

		String attachmentSource = null;
		try {
			Parameters parameter = this.parametersService
					.getParamDetails(
							TransactionEnquiryController.PARA_TRANSACTION_ATTACHMENT_SOURCE_SCOPE,
							TransactionEnquiryController.PARA_TRANSACTION_ATTACHMENT_SOURCE_VALUE);
			if (parameter != null
					&& StringUtils.isNotBlank(parameter.getParaShortValue())) {
				attachmentSource = parameter.getParaShortValue();
			}
		} catch (Exception e) {
		}

		if (attachmentSource == null) {
			InvalidParameterException exception = new InvalidParameterException(
					"Parameter TRANSACTION_ATTACHMENT_SOURCE is required.");
			exception.printStackTrace();
			throw exception;
		}

		if (!(attachmentSource.equalsIgnoreCase(AttachmentSource.DMS))) {
			return null;
		}

		String urlPrefix = null;
		try {
			Parameters parameter = this.parametersService.getParamDetails(
					TransactionEnquiryController.PARA_SCOPE_SYSTEM,
					TransactionEnquiryController.PARA_NAME_SYSTEM_DMS_URL);
			if (parameter != null && parameter.getParaLobValue() != null) {
				urlPrefix = parameter.getParaLobValue();
			}
		} catch (Exception e) {
		}

		if (StringUtils.isBlank(urlPrefix)) {
			InvalidParameterException exception = new InvalidParameterException(
					"Parameter DMS_URL is required.");
			exception.printStackTrace();
			throw exception;
		}

		return urlPrefix + DmsUtility.DMS__URL_DELIMITER_1 + transactionDocId
				+ DmsUtility.DMS__URL_DELIMITER_2 + transactionId
				+ DmsUtility.DMS__URL_DELIMITER_3 + docType;
	}

	@ResponseBody
	@RequestMapping(value = "/showJPEGProofDoc/{transId}/{docType}")
	public void showJPEGProofDoc(@PathVariable String transId,
			@PathVariable String docType, WebRequest request, ModelMap model,
			RicRegistrationDocumentDTO ricRegistrationDocumentDTO,
			HttpServletResponse response) throws Exception {
		ServletOutputStream ouputStream = null;
		try {
			byte[] doc = null;
			ouputStream = response.getOutputStream();
			List<NicTransactionAttachment> nicTransactionDocuments = nicTransactionDocumentService
					.findNicTransactionAttachments(transId, docType, null)
					.getListModel();

			if (CollectionUtils.isNotEmpty(nicTransactionDocuments)
					&& nicTransactionDocuments.size() > 0) {
				doc = nicTransactionDocuments.get(0).getDocData();

				response.setContentType("image/jpg");
				ouputStream.write(doc);
			} else {
				ouputStream.print("No Proof Document found to download.");
				return;
			}
		} catch (Exception e) {
			ouputStream
					.print("Error occurred while downloading the Proof Document");
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					throw new Exception(e);
				}
			}
		}

	}

	@ResponseBody
	@RequestMapping(value = "/showPDFProofDoc/{transId}/{docType}")
	public void showPDFProofDoc(@PathVariable String transId,
			@PathVariable String docType, WebRequest request, ModelMap model,
			RicRegistrationDocumentDTO ricRegistrationDocumentDTO,
			HttpServletResponse response) throws Exception {
		ServletOutputStream ouputStream = null;
		try {
			byte[] doc = null;
			ouputStream = response.getOutputStream();
			List<NicTransactionAttachment> nicTransactionDocuments = nicTransactionDocumentService
					.findNicTransactionAttachments(transId, docType, null)
					.getListModel();

			if (CollectionUtils.isNotEmpty(nicTransactionDocuments)
					&& nicTransactionDocuments.size() > 0) {
				doc = nicTransactionDocuments.get(0).getDocData();

				response.setContentType("application/pdf");
				ouputStream.write(doc);
			} else {
				ouputStream.print("No Proof Document found to download.");
				return;
			}
		} catch (Exception e) {
			ouputStream
					.print("Error occurred while downloading the Proof Document");
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					throw new Exception(e);
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/photosFullView", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView photosFullView(HttpServletRequest request,
			ModelMap model) throws Exception {
		try {
			PhotoEnquiryInfo photoEnquiryInfo = new PhotoEnquiryInfo();
			/*
			 * String[] docTypes = {
			 * NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
			 * NicTransactionAttachment.DOC_TYPE_PHOTO_GREY,
			 * NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
			 * NicTransactionAttachment.DOC_TYPE_PHOTO_CLI };
			 */
			String[] docTypes = {
					NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
					NicTransactionAttachment.DOC_TYPE_SIGNATURE };
			String[] serialNums = { NicTransactionAttachment.DEFAULT_SERIAL_NO };
			String transactionId = request.getParameter("transactionId");
			// String nin = request.getParameter("nin");
			String passportNo = request.getParameter("passportNo");

			photoEnquiryInfo.setTransactionId(transactionId);
			// [19Feb2016][Tue] - Edit
			// photoEnquiryInfo.setNin(nin);
			photoEnquiryInfo.setPassportNo(passportNo);

			List<NicTransactionAttachment> nicTransactionDocuments = nicTransactionDocumentService
					.getNicTransactionAttachments(transactionId, docTypes,
							serialNums);

			if (CollectionUtils.isNotEmpty(nicTransactionDocuments)) {
				for (NicTransactionAttachment nicTransactionDocument : nicTransactionDocuments) {
					if (nicTransactionDocument != null
							&& StringUtils.isNotBlank(nicTransactionDocument
									.getDocType())
							&& nicTransactionDocument
									.getDocType()
									.equalsIgnoreCase(
											NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE)) {
						photoEnquiryInfo.setCapturePhotoStr(Base64
								.encodeBase64String(nicTransactionDocument
										.getDocData()));
					} else if (nicTransactionDocument != null
							&& StringUtils.isNotBlank(nicTransactionDocument
									.getDocType())
							&& nicTransactionDocument
									.getDocType()
									.equalsIgnoreCase(
											NicTransactionAttachment.DOC_TYPE_SIGNATURE)) {
						photoEnquiryInfo.setSignatureStr(Base64
								.encodeBase64String(nicTransactionDocument
										.getDocData()));
					}
				}
			}

			request.setAttribute("photoEnquiryInfo", photoEnquiryInfo);
			request.setAttribute("passportNo", passportNo);
			return new ModelAndView("photo-full-view");
		} catch (Exception ex) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("errorWithoutMenu");
			modelAndView.addObject("exception", ex);
			modelAndView.addObject("message", ex.getLocalizedMessage());
			modelAndView.addObject("messageStack", ex.getMessage());
			return modelAndView;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/fpInfo", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView fpInfo(HttpServletRequest request, ModelMap model)
			throws Exception {
		try {

			UserSession userSession = (UserSession) request.getSession()
					.getAttribute("userSession");
			String transactionId = request.getParameter("transactionId");
			// [19Feb2016][Tue] - Add
			// String nin = request.getParameter("nin");
			String passportNo = request.getParameter("passportNo");

			String fpIndicator = request.getParameter("fpIndicator");
			String fpQuality = request.getParameter("fpQuality");
			String fpEncode = request.getParameter("fpEncode");
			String fpVerifyScore = request.getParameter("fpVerifyScore");
			String fpVerifyMatchScore = "";
			String viewFPFalg = "Y";
			String[] goodFpQuas = null;
			String[] fpIndicators = fpIndicator.split(",");
			String[] fpQualitys = fpQuality.split(",");
			String[] fpEncodes = fpEncode.split(",");
			String[] fpVerifyScores = fpVerifyScore.split(",");
			String defGoodFpQuas = "1-65,2-65,3-55,4-40,5-30,6-65,7-65,8-55,9-40,10-30";
			String defAcceptFpQuas = "1-40,2-40,3-40,4-25,5-15,6-40,7-40,8-40,9-25,10-15";
			String[] accFpQuas = null;
			ParametersId goodFpQuaParamId = new ParametersId();
			goodFpQuaParamId.setParaName(Parameters.FP_QUALITY_GOOD);
			goodFpQuaParamId.setParaScope(Parameters.SCOPE_SYSTEM);

			Parameters goodFpQuaParam = parametersService
					.findById(goodFpQuaParamId);

			if (goodFpQuaParam != null) {
				goodFpQuas = goodFpQuaParam.getParaShortValue().split(",");
			} else {
				goodFpQuas = defGoodFpQuas.split(",");
			}

			ParametersId accFpQuaParamId = new ParametersId();
			accFpQuaParamId.setParaName(Parameters.FP_QUALITY_ACCEPTABLE);
			accFpQuaParamId.setParaScope(Parameters.SCOPE_SYSTEM);

			Parameters accFpQuaParam = parametersService
					.findById(accFpQuaParamId);

			if (accFpQuaParam != null) {
				accFpQuas = accFpQuaParam.getParaShortValue().split(",");
			} else {
				accFpQuas = defAcceptFpQuas.split(",");
			}

			ParametersId paramId = new ParametersId();
			paramId.setParaName(Parameters.FP_VERIFY_MATCH_SCORE);
			paramId.setParaScope(Parameters.SCOPE_SYSTEM);

			Parameters parameter = parametersService.findById(paramId);
			if (parameter != null) {
				fpVerifyMatchScore = parameter.getParaShortValue();
			}

			List<Integer> fpEncodesArr = new ArrayList<Integer>();
			for (String fpEndVal : fpEncodes) {
				fpEncodesArr.add(Integer.parseInt(fpEndVal));
			}
			Map<Integer, FingerprintInfo> fpMap = new HashMap<Integer, FingerprintInfo>();

			if (userSession.getFunctions().contains("VIEW_FP")) {

				List<NicTransactionAttachment> fps = nicTransactionDocumentService
						.findNicTransactionAttachments(transactionId,
								NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
								null).getListModel();
				if (CollectionUtils.isNotEmpty(fps)) {
					for (NicTransactionAttachment fp : fps) {
						Integer fpPos = Integer.parseInt(fp.getSerialNo());

						FingerprintInfo info = new FingerprintInfo();
						info.setFpImage(Base64.encodeBase64String(HelperClass
								.ConvertWSQToJPG(fp.getDocData())));
						info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1]
								.split("-")[1]));
						// [19Feb2016][Tue] - Add
						// info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos
						// - 1].split("-")[1]));
						info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);
						info.setGoodFPQuaScr(Integer
								.parseInt(goodFpQuas[fpPos - 1].split("-")[1]));
						info.setAccetableFPQuaScr(Integer
								.parseInt(accFpQuas[fpPos - 1].split("-")[1]));

						if (fpEncodesArr.contains(fpPos)) {
							info.setEncodeFlag(true);
						}
						fpMap.put(fpPos, info);

					}
				}

				for (String fpIndi : fpIndicators) {
					Integer fpPos = Integer.parseInt(fpIndi.split("-")[0]);
					if (!fpMap.containsKey(fpPos)) {

						FingerprintInfo info = new FingerprintInfo();
						info.setFpImage("");
						info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1]
								.split("-")[1]));
						// [19Feb2016][Tue] - Add
						// info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos
						// - 1].split("-")[1]));
						info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);
						info.setGoodFPQuaScr(Integer
								.parseInt(goodFpQuas[fpPos - 1].split("-")[1]));
						info.setAccetableFPQuaScr(Integer
								.parseInt(accFpQuas[fpPos - 1].split("-")[1]));

						if (fpEncodesArr.contains(fpPos)) {
							info.setEncodeFlag(true);
						}
						fpMap.put(fpPos, info);
					}
				}

			} else {

				viewFPFalg = "N";

				List<NicTransactionAttachment> fps = nicTransactionDocumentService
						.findNicTransactionAttachments(transactionId,
								NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
								null).getListModel();
				if (CollectionUtils.isNotEmpty(fps)) {
					for (NicTransactionAttachment fp : fps) {
						Integer fpPos = Integer.parseInt(fp.getSerialNo());

						FingerprintInfo info = new FingerprintInfo();
						info.setFpImage(Base64.encodeBase64String(HelperClass
								.ConvertWSQToJPG(fp.getDocData())));
						info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1]
								.split("-")[1]));
						// [19Feb2016][Tue] - Add
						// info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos
						// - 1].split("-")[1]));
						info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);

						if (fpEncodesArr.contains(fpPos)) {
							info.setEncodeFlag(true);
						}
						fpMap.put(fpPos, info);
					}
				}

				for (String fpIndi : fpIndicators) {
					Integer fpPos = Integer.parseInt(fpIndi.split("-")[0]);
					if (!fpMap.containsKey(fpPos)) {
						FingerprintInfo info = new FingerprintInfo();
						info.setFpImage("");
						info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1]
								.split("-")[1]));
						// [19Feb2016][Tue] - Add
						// info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos
						// - 1].split("-")[1]));
						info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);

						if (fpEncodesArr.contains(fpPos)) {
							info.setEncodeFlag(true);
						}
						fpMap.put(fpPos, info);
					}
				}
			}

			ModelAndView mav = new ModelAndView("fp-info");

			mav.addObject("fpMap", fpMap);
			// [19Feb2016][Tue] - Add
			// mav.addObject("fpVerifyMatchScore",
			// Integer.parseInt(fpVerifyMatchScore));
			mav.addObject("fpVerifyMatchScore", 1);
			mav.addObject("viewFPFalg", viewFPFalg);

			return mav;
		} catch (Exception ex) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("errorWithoutMenu");
			modelAndView.addObject("exception", ex);
			modelAndView.addObject("message", ex.getLocalizedMessage());
			modelAndView.addObject("messageStack", ex.getMessage());
			return modelAndView;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getIssuanceData", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView getIssuanceData(HttpServletRequest request,
			ModelMap model) throws Exception {
		DataExchangeDTO convertedIssuanceData = null;
		List<FPEnquiryInfo> fpEnquiryInfoList = new ArrayList<FPEnquiryInfo>();
		String viewFPFalg = "Y";
		try {
			UserSession userSession = (UserSession) request.getSession()
					.getAttribute("userSession");
			if (!userSession.getFunctions().contains("VIEW_FP")) {
				viewFPFalg = "N";
			}
			String logId = request.getParameter("logId");

			NicTransactionLog nicTransactionLog = transactionLogService
					.findById(new Long(logId));
			if (nicTransactionLog.getLogData() != null) {
				convertedIssuanceData = (DataExchangeDTO) issuanceUtil
						.unmarshal(nicTransactionLog.getLogData());

				if (convertedIssuanceData != null) {
					if (convertedIssuanceData.getIssuanceDetails() != null) {
						if (convertedIssuanceData.getIssuanceDetails()
								.getFpVerifications() != null) {
							List<FpVerificationDTO> fpDataList = convertedIssuanceData
									.getIssuanceDetails().getFpVerifications()
									.getFpVerificationList();
							if (CollectionUtils.isNotEmpty(fpDataList)) {
								for (FpVerificationDTO fpVerificationDTO : fpDataList) {
									FPEnquiryInfo fpEnquiryInfo = new FPEnquiryInfo();
									if (fpVerificationDTO.getFingerprintData() != null) {
										fpEnquiryInfo
												.setFpDataStr(Base64
														.encodeBase64String(fpVerificationDTO
																.getFingerprintData()));
									}
									fpEnquiryInfo
											.setQualityScore(fpVerificationDTO
													.getFpQuality());
									fpEnquiryInfo
											.setFpPosition(fpVerificationDTO
													.getFingerprintPosition());
									fpEnquiryInfo.setFpSource(fpVerificationDTO
											.getFpSource());
									fpEnquiryInfo
											.setFpVerifyFlag(fpVerificationDTO
													.getFpVerifyFlag());
									fpEnquiryInfo
											.setMatchScore(fpVerificationDTO
													.getMatchScore());

									fpEnquiryInfoList.add(fpEnquiryInfo);
								}
							}
						}
					}
				}
			}

			ModelAndView mav = new ModelAndView("issuance-details");
			mav.addObject("fpEnquiryInfoList", fpEnquiryInfoList);
			mav.addObject("convertedIssuanceData", convertedIssuanceData);
			mav.addObject("fpSize", fpEnquiryInfoList.size());
			mav.addObject("viewFPFalg", viewFPFalg);

			return mav;
		} catch (Exception ex) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("errorWithoutMenu");
			modelAndView.addObject("exception", ex);
			modelAndView.addObject("message", ex.getLocalizedMessage());
			modelAndView.addObject("messageStack", ex.getMessage());
			return modelAndView;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/retry/{jobId}", method = RequestMethod.GET)
	@ExceptionHandler
	public String retry(HttpServletRequest request, @PathVariable String jobId,
			ModelMap model) throws Exception {
		try {
			logger.info("Retrying the job with Job Id:" + jobId);
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			if (StringUtils.isNotBlank(jobId)) {
				uploadJobService
						.retryTransaction(new Long(jobId),
								userSession.getUserId(),
								userSession.getWorkstationId());
				return "success";
			} else {
				logger.info("Job Id is null");
				return "fail";
			}

		} catch (Exception ex) {
			logger.error("Error occurred while Retrying the Job. Exception"
					+ ex.getMessage());

			return "fail";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/prioritize", method = RequestMethod.POST)
	@ExceptionHandler
	public String prioritize(HttpServletRequest request, ModelMap model,
			@ModelAttribute("nicEnqDetailsForm") NicEnquiryForm nicEnquiryForm)
			throws Exception {
		try {
			String transactionId = nicEnquiryForm.getTransactionId();
			String remarks = nicEnquiryForm.getRemarks();
			logger.info("Start - Prioritize the job with Transaction Id:"
					+ transactionId);
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			if (StringUtils.isNotBlank(transactionId)) {
				boolean isSuccess = nicTransactionService
						.prioritizeTransaction(transactionId, remarks,
								userSession.getUserId(),
								userSession.getWorkstationId());
				logger.info("End - Prioritize the job with Transaction Id:"
						+ transactionId + ", isSuccess:" + isSuccess);
				if (isSuccess == true)
					return "success";
				else
					return "fail";
			} else {
				logger.info("Transaction Id is null");
				return "fail";
			}
		} catch (Exception ex) {
			logger.error("Error occurred while Prioritize the Transaction. Exception"
					+ ex.getMessage());
			return "fail";
		}
	}

	@ResponseBody
	@ExceptionHandler
	@RequestMapping(value = "/reprint", method = RequestMethod.POST)
	public String reprint(HttpServletRequest request, ModelMap model,
			@ModelAttribute("nicEnqDetailsForm") NicEnquiryForm nicEnquiryForm)
			throws Exception {
		try {
			String transactionId = nicEnquiryForm.getTransactionId();
			String remarks = nicEnquiryForm.getRemarks();
			boolean cancelPptFlag = false;
			if ("Y".equalsIgnoreCase(nicEnquiryForm.getCancelPptFlag())) {
				cancelPptFlag = true;
			}

			logger.info("Start - Reprint the Transaction Id: " + transactionId
					+ ", cancelPptFlag: " + cancelPptFlag);
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			if (StringUtils.isNotBlank(transactionId)) {
				boolean isSuccess = nicTransactionService
						.reprintTransaction(transactionId, cancelPptFlag,
								remarks, userSession.getUserId(),
								userSession.getWorkstationId());
				logger.info("End - Reprint the Transaction Id:" + transactionId
						+ ", isSuccess:" + isSuccess);
				if (isSuccess) {
					return "success";
				} else {
					return "fail";
				}
			} else {
				logger.info("Transaction Id is null");
				return "fail";
			}

		} catch (Exception ex) {
			logger.error("Error occurred while Prioritize the Transaction. Exception"
					+ ex.getMessage());
			return "fail";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/cancelPassport", method = RequestMethod.POST)
	@ExceptionHandler
	public String cancelPassport(HttpServletRequest request, ModelMap model,
			@ModelAttribute("nicEnqDetailsForm") NicEnquiryForm nicEnquiryForm)
			throws Exception {
		try {
			String transactionId = nicEnquiryForm.getTransactionId();
			String passportNo = nicEnquiryForm.getPassportNo();
			String remarks = nicEnquiryForm.getRemarks();
			logger.info("Start - Cancel Passport with the Transaction Id:"
					+ transactionId + ", Passport No: " + passportNo);
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			if (StringUtils.isNotBlank(transactionId)
					&& StringUtils.isNotBlank(passportNo)) {
				boolean isSuccess = nicTransactionService
						.cancelPassport(transactionId, passportNo, remarks,
								userSession.getUserId(),
								userSession.getWorkstationId());
				logger.info("End - Cancel Passport with the Transaction Id:"
						+ transactionId + ", Passport No: " + passportNo
						+ ", isSuccess:" + isSuccess);
				if (isSuccess) {
					return "success";
				} else {
					return "fail";
				}
			} else {
				logger.info("Transaction Id or Passport No is null");
				return "fail";
			}

		} catch (Exception ex) {
			logger.error("Error occurred while Prioritize the Transaction. Exception"
					+ ex.getMessage());

			return "fail";
		}
	}

	public NicTransactionInfo getTransactionInfo(NicTransaction nicTransaction)
			throws Exception {
		NicTransactionInfo nicTransactionInfo = null;
		try {
			nicTransactionInfo = new NicTransactionInfo();

			PropertyUtils.copyProperties(nicTransactionInfo, nicTransaction);

			if (nicTransaction.getDateOfApplication() != null) {
				nicTransactionInfo.setDatTimeOfApplication(DateUtil.parseDate(
						nicTransaction.getDateOfApplication(), "dd/MM/yyyy"));
			}

			if (nicTransaction.getEstDateOfCollection() != null) {
				nicTransactionInfo.setEstCollectionDate(DateUtil.parseDate(
						nicTransaction.getEstDateOfCollection(), "dd/MM/yyyy"));
			}

			if (nicTransaction.getCreateDatetime() != null) {
				nicTransactionInfo.setCreateDateTime(DateUtil.parseDate(
						nicTransaction.getDateOfApplication(), "dd/MM/yyyy"));
			}

			if (nicTransaction.getUpdateDatetime() != null) {
				nicTransactionInfo.setUpdateDateTime(DateUtil.parseDate(
						nicTransaction.getUpdateDatetime(), "dd/MM/yyyy"));
			}

			if (nicTransaction.getPrevDateOfIssue() != null) {
				nicTransactionInfo.setPrevIssueDate(DateUtil.parseDate(
						nicTransaction.getPrevDateOfIssue(), "dd/MM/yyyy"));
			}

			String regSiteCode = null;
			if (StringUtils.isNotBlank(nicTransactionInfo.getRegSiteCode())) {
				SiteRepository regSite = siteService
						.getSiteRepoById(nicTransactionInfo.getRegSiteCode());
				if (regSite != null) {
					regSiteCode = regSite.getSiteName();
				} else {
					regSiteCode = nicTransactionInfo.getRegSiteCode();
				}
			}

			nicTransactionInfo.setRegSiteCode(regSiteCode);

			String issSiteCode = null;
			if (StringUtils.isNotBlank(nicTransactionInfo.getIssSiteCode())) {
				SiteRepository regSite = siteService
						.getSiteRepoById(nicTransactionInfo.getIssSiteCode());
				if (regSite != null) {
					issSiteCode = regSite.getSiteName();
				} else {
					issSiteCode = nicTransactionInfo.getRegSiteCode();
				}
			}

			nicTransactionInfo.setIssSiteCode(issSiteCode);

			String transactionStatus = null;
			if (nicTransactionInfo != null
					&& StringUtils.isNotBlank(nicTransactionInfo
							.getTransactionStatus())) {
				CodeValues codeValue = codesService.getCodeValueByIdName(
						"TRANSACTION_STATUS",
						nicTransactionInfo.getTransactionStatus());

				if (codeValue != null) {
					transactionStatus = codeValue.getCodeValueDesc();
				} else {
					transactionStatus = nicTransactionInfo
							.getTransactionStatus();
				}
			}
			nicTransactionInfo.setNin(nicTransaction.getNin());

			nicTransactionInfo.setTransactionStatus(transactionStatus);

			String transactionSubType = null;
			if (nicTransactionInfo != null
					&& StringUtils.isNotBlank(nicTransactionInfo
							.getTransactionSubtype())) {
				CodeValues codeValue = codesService.getCodeValueByIdName(
						"TRANSACTION_SUBTYPE",
						nicTransactionInfo.getTransactionSubtype());

				if (codeValue != null) {
					transactionSubType = codeValue.getCodeValueDesc();
				} else {
					transactionSubType = nicTransactionInfo
							.getTransactionSubtype();
				}
			}

			nicTransactionInfo.setTransactionSubtype(transactionSubType);

			String transactionType = null;
			if (nicTransactionInfo != null
					&& StringUtils.isNotBlank(nicTransactionInfo
							.getTransactionType())) {
				CodeValues codeValue = codesService.getCodeValueByIdName(
						"TRANSACTION_TYPE",
						nicTransactionInfo.getTransactionType());

				transactionType = nicTransactionInfo.getTransactionType();
				if (codeValue != null) {
					transactionType = codeValue.getCodeValueDesc();
				} else {
					codeValue = codesService.getCodeValueByIdName("JOB_TYPE",
							nicTransactionInfo.getTransactionType());
					if (codeValue != null) {
						transactionType = codeValue.getCodeValueDesc();
					}
				}
			}

			nicTransactionInfo.setTransactionType(transactionType);

			String passportType = null;
			if (nicTransactionInfo != null
					&& StringUtils.isNotBlank(nicTransactionInfo
							.getPassportType())) {
				CodeValues codeValue = codesService.getCodeValueByIdName(
						"PASSPORT_TYPE", nicTransactionInfo.getPassportType());

				if (codeValue != null) {
					passportType = codeValue.getCodeValueDesc();
				} else {
					passportType = nicTransactionInfo.getPassportType();
				}
			}

			nicTransactionInfo.setPassportType(passportType);

			// Thêm người tạo
			if (StringUtils.isNotEmpty(nicTransaction.getCreateBy())) {
				Users users = userService.findByUserId(nicTransaction
						.getCreateBy());
				if (users != null)
					nicTransactionInfo.setCreateBy(users.getUserName());
			}

			if (StringUtils.isNotEmpty(nicTransaction.getUpdateBy())) {
				Users users = userService.findByUserId(nicTransaction
						.getUpdateBy());
				if (users != null)
					nicTransactionInfo.setUpdateBy(users.getUserName());
			}

			// Get name
			NicRegistrationData registrationData = nicTransaction
					.getNicRegistrationData();
			if (registrationData != null) {
				nicTransactionInfo.setFirstName(registrationData.getSurnameFull());
				nicTransactionInfo.setMiddleName(registrationData.getMiddlenameFull());
				nicTransactionInfo.setLastName(registrationData.getFirstnameFull());
				nicTransactionInfo.setFullName(HelperClass.createFullName(
						registrationData.getSurnameFull(),
						registrationData.getMiddlenameFull(),
						registrationData.getFirstnameFull()));
			}
		} catch (Exception ex) {
			logger.info("Error occurred while getting the job enquiry transaction info for Transaction:"
					+ nicTransaction.getTransactionId()
					+ ", Reason: "
					+ ex.getMessage());
		}

		return nicTransactionInfo;
	}
}
