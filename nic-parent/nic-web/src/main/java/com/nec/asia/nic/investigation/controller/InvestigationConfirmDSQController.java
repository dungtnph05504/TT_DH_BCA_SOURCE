package com.nec.asia.nic.investigation.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import bsh.StringUtil;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.asia.nic.admin.domain.User;
import com.nec.asia.nic.common.dto.RicRegistrationDTO;
import com.nec.asia.nic.comp.job.dto.SearchHitDto;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverDetail;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.trans.domain.DocumentInfo;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.workflowProcess.service.WorkflowProcessService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.investigation.controller.InvestigationController.DataJson;
import com.nec.asia.nic.investigation.controller.InvestigationController.RequestDocument;
import com.nec.asia.nic.util.BaseListResponse;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.PrintLocation;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.RequestBase;
import com.nec.asia.nic.util.RequestVerifyInfo;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/investigationConfirmDSQ")
public class InvestigationConfirmDSQController extends InvestigationController {

	private static final Logger logger = LoggerFactory
			.getLogger(InvestigationConfirmDSQController.class);

	public static TransLogInfoXmlConvertor logUtil = new TransLogInfoXmlConvertor();

	public static final String STATUS_COMPLETED = "02";

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private ListHandoverService listHandoverService;

	@Autowired
	private PersoLocationsService persoLocationsService;

	@Autowired
	private SiteRepositoryDao siteRepositoryDao;

	@Autowired
	private SiteService siteService;
	
	@Autowired
	private WorkflowProcessService workflowProcessService; 
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@ModelAttribute("userAssignment")
	public Map<String, String> userAssignment() {
		List<Users> codeList = userService.findAll();
		Map<String, String> userAssignment = new LinkedHashMap<String, String>();
		for (Users ricCode : codeList) {
			userAssignment.put(ricCode.getUserId(), ricCode.getUserId());
		}
		return userAssignment;
	}

	@ModelAttribute("transactionType")
	public Map<String, String> transactionType() {
		List<CodeValues> codeList = codesService.getAllCodeValues(
				"TRANSACTION_TYPE", new String[] { "NEW", "RENEWAL", "LOST",
						"REG", "REP" });
		Map<String, String> transactionType = new LinkedHashMap<String, String>();
		transactionType.put("", "--Chọn--");
		for (CodeValues ricCode : codeList) {
			transactionType.put(ricCode.getId().getCodeValue(),
					ricCode.getCodeValueDesc());
		}
		return transactionType;
	}

	@ModelAttribute("passportType")
	public Map<String, String> passportType() {
		List<CodeValues> codeList = codesService.getAllCodeValues(
				"PASSPORT_TYPE", new String[] { "P", "PD", "PO", "PE" });
		Map<String, String> passportType = new LinkedHashMap<String, String>();
		passportType.put("", "--Chọn--");
		for (CodeValues ricCode : codeList) {
			passportType.put(ricCode.getId().getCodeValue(),
					ricCode.getCodeValueDesc());
		}
		return passportType;
	}

	@ModelAttribute("priorityCode")
	public Map<String, String> priorityCode() {
		List<CodeValues> codeList = codesService.getAllCodeValues("PRIORITY",
				new String[] { "1", "2", "3" });
		Map<String, String> priorityCode = new LinkedHashMap<String, String>();
		priorityCode.put("", "--Chọn--");
		for (CodeValues ricCode : codeList) {
			priorityCode.put(ricCode.getId().getCodeValue(),
					ricCode.getCodeValueDesc());
		}
		return priorityCode;
	}
	
	@ModelAttribute("listSiteRepository")
	public Map<String, String> listSiteRepository() throws DaoException {
		Map<String, String> list = new HashMap<String, String>();
		list.put("", "--Chọn--");
		SiteGroups sg = siteService.getOnlyListByLevel("1");
		if(sg != null){
			List<SiteRepository> listSite = siteService.findAllByGroupId1(sg.getGroupId());	
			for(SiteRepository sr : listSite){
				list.put(sr.getSiteId(), sr.getSiteName());
			}
		}
		return list;
	}

	@ModelAttribute("investigationTypeCode")
	public Map<String, String> investigationTypeCode() {
		// List<CodeValues> codeList =
		// codesService.getAllCodeValues("SEARCH_TYPE", new String[] {"1", "2",
		// "3"});
		Map<String, String> investigationType = new LinkedHashMap<String, String>();
		investigationType.put("", "Không dính điều tra");
		investigationType.put("AFIS 1:N", "AFIS 1:N");
		investigationType.put("CPD", "CPD");
		investigationType.put("AFIS 1:1 % CPD", "CPD,AFIS 1:N");
		return investigationType;
	}

	@RequestMapping(value = "/investigationConfirmDSQList")
	public ModelAndView getInvestigationConfirmList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationConfirmDSQList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getInvestigationConfirmList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getInvestigationConfirmList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationConfirmDSQList pageNo");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
				+ userSession.getUserName());

		{
			List<String> messages = new ArrayList<String>();

			if (listOfMessages != null) {
				messages.addAll(listOfMessages);
			}

			if (CollectionUtils.isNotEmpty(messages)) {
				httpRequest.setAttribute("messages", messages);
			}
		}

		{
			List<String> Errors = new ArrayList<String>();

			if (listOfMessages != null) {
				Errors.addAll(listOfErrors);
			}

			if (CollectionUtils.isNotEmpty(Errors)) {
				httpRequest.setAttribute("Errors", Errors);
			}
		}

		if (investigationAssignmentData == null) {
			investigationAssignmentData = new InvestigationAssignmentData();
		}

		investigationAssignmentData.cleanUpStrings();

		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageSize = 20;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			pr = uploadJobService
					.findAllForInvestigationPaginationDSQ(
							new String[] { NicUploadJob.RECORD_STATUS_IN_PROGRESS, NicUploadJob.RECORD_STATUS_COMPLETED, NicUploadJob.RECORD_STATUS_CONFIRMED_BCA, NicUploadJob.RECORD_STATUS_PENDING_BCA, NicUploadJob.RECORD_STATUS_CONFIRMED_DSQ },
							userSession.getUserId(),
							pageNo,
							pageSize,
							investigationAssignmentData.assignedOnly(),
							new AssignmentFilterAll(investigationAssignmentData.getSearchTransactionId(), investigationAssignmentData.getPriority(),
									investigationAssignmentData.getTransactionType(), null, null,
									investigationAssignmentData.getRegSiteCode(),
									investigationAssignmentData.getPassportType(),
									investigationAssignmentData.getTypeInvestigation(), null, null, investigationAssignmentData.getValidateInfoBca()));

			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
					for (NicUploadJobDto record : list) {
						String transactionId = record.getTransactionId();
						if (transactionId != null) {
							NicTransaction nicTransaction = nicTransactionService
									.findById(transactionId);
							if (nicTransaction != null) {
								record.setPeriodValidate(nicTransaction.getValidityPeriod());
								record.setDateOfApplication(nicTransaction
										.getDateOfApplication());
								record.setEstDateOfCollection(nicTransaction
										.getEstDateOfCollection());
								record.setFullName(nicTransaction
										.getNicRegistrationData()
										.getSurnameLine1());
								record.setPassportType(nicTransaction
										.getPassportType());
								record.setRegSiteCode(nicTransaction
										.getRegSiteCode());
								{
									try {
										CodeValues codeValue = codesService
												.getCodeValueByIdName(
														Codes.PRIORITY,
														nicTransaction
																.getPriority()
																.toString());
										if (codeValue != null
												&& codeValue.getCodeValueDesc() != null) {
											record.setPriorityString(codeValue
													.getCodeValueDesc());
										} else {
											record.setPriorityString(nicTransaction
													.getPriority() == null ? null
													: nicTransaction
															.getPriority()
															.toString());
										}
									} catch (Exception e) {
										record.setPriorityString(nicTransaction
												.getPriority() == null ? null
												: nicTransaction.getPriority()
														.toString());
									}
								}
							}
						}
					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				//searchResultMap.put("pageSize", pageSize);

				//model.addAttribute("jobList", list);
				//phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("jobList", list);
				//end	

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_CONFIRM_DSQ);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationConfirmDSQ.investigationConfirmDSQList",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_CONFIRM_DSQ);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationConfirmDSQ.investigationConfirmDSQList",
						null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = { "/startDetailInvestigation/{jobId}" })
	public ModelAndView startinvestigationConfirmcompare(
			@PathVariable long jobId, HttpServletRequest httpRequest,
			Model model) throws Throwable {
		logger.info("NIC Start Investigation Confirm DSQ compare");

		return this.startinvestigationConfirmcompare(jobId, httpRequest, model,
				null);
	}

	public ModelAndView startinvestigationConfirmcompare(long jobId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Investigation Confirm DSQ compare, listOfMessages");
		String periodValid = "";
		ModelAndView modelAndView = new ModelAndView(
				"investigation.investigationConfirmDSQ.startInvestigationConfirmDSQ.compare");
		Boolean checkSendBCA = false;
		this.initializeModelAndViewForms(modelAndView);

		NicUploadJob jobDetails = uploadJobService.findById(jobId);
		String mainTransactionId = jobDetails.getTransactionId();

		// 05-02-2018: trung show img và thông tin cơ bản
		String photoStr = null;
		NicTransaction nicTransaction = null;

		nicTransaction = nicTransactionService.findById(jobDetails
				.getTransactionId());// TRung thêm
		if(nicTransaction != null){
			if (nicTransaction.getNicRegistrationData().getCreateDatetime() != null) {
				String crd = HelperClass.convertDateToString1(nicTransaction
						.getNicRegistrationData().getCreateDatetime());
				nicTransaction.setCreateDesc(crd);
				// nicTransaction.getNicRegistrationData().setCreateDatetime(HelperClass.convertStringToDate1(crd));
			}
			if (nicTransaction.getNicRegistrationData().getReligion() != null) {
				String religion = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_RELIGION_CODE_ID, nicTransaction
								.getNicRegistrationData().getReligion(), "");
				nicTransaction.getNicRegistrationData().setReligion(religion);
			}
			if (nicTransaction.getNicRegistrationData().getNation() != null) {
				String nation = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_NATION_CODE_ID, nicTransaction
								.getNicRegistrationData().getNation(), "");
				nicTransaction.getNicRegistrationData().setNation(nation);
			}
		}
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
				.findNicTransactionAttachments(jobDetails.getTransactionId(),
						NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
						NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
		}

		//Kiểm tra dữ liệu đã được gửi sang bộ công an hay chưa
		if(jobDetails.getValidateInfoBca() == null || jobDetails.getValidateInfoBca() == 0){
			checkSendBCA = true;
		}
		
		
		modelAndView.addObject("checkSendBca", checkSendBCA);
		modelAndView.addObject("photoStr", photoStr);
		modelAndView.addObject("nicData", nicTransaction);
		// end
		modelAndView.addObject("transID", mainTransactionId);
		modelAndView.addObject("jobsID", jobId);
		NicTransaction nic = nicTransactionService
				.getNicTransactionById(mainTransactionId).getModel();
		if (nic != null) {
			periodValid = nic.getValidityPeriod().toString();
		}
		modelAndView.addObject("periodValid", periodValid);
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		//Kiểm tra có được gửi VERIFY không
		Boolean checkVer = true;
		try {
			NicWorkflowProcess verification = workflowProcessService
					.findWorkflowProcessByCriteria(
							NicWorkflowProcess.VERIFICATION).get(0);
			if (verification.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				checkVer = false;
		} catch (Exception e) {
		}
		
		return modelAndView;
	}

	// /Lấy thông tin chi tiết của giao dịch (bao gồm cả dữ liệu đã kiểm tra CPD
	// và AFIS)
	@ResponseBody
	@RequestMapping(value = "/txnDetailTrans/{txnId}/{jobId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView detailTransactionMain(WebRequest request,
			@PathVariable String txnId, @PathVariable long jobId,
			ModelMap model, HttpServletRequest httpRequest) throws Exception {
		ModelAndView mav = new ModelAndView("nic-investigation-details");
		this.InitMainInfo(request, txnId, jobId, model, httpRequest, mav);
		return mav;
	}

	@RequestMapping(value = "/applyFilter")
	public ModelAndView applyFilter(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getInvestigationConfirmList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}

	// /Xử lý từ chối bản ghi
	@RequestMapping(value = { "/reject_noHit" })
	public ModelAndView reject_noHit(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {

		logger.info("reject_noHit");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.rejectJob(
					investigationHitData.getJobRejectRemarks(),
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicRejectionData.rejectReason_investigation,
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_REJECTED);

			messages.add("Đã từ chối bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			logger.info("rejection failed");
			messages.add("Từ chố bản ghi thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationConfirmDSQ/investigationConfirmDSQList");
	}

	// /Xử lý chấp nhận duyệt bản ghi
	@RequestMapping(value = { "/approve_noHit" })
	public ModelAndView approve_noHit(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("approve_noHit");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.approveJobStatus_Confirmed(
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
					NicUploadJob.RECORD_STATUS_CONFIRMED_DSQ);

			messages.add("Duyệt bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Duyệt bản ghi thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationConfirmDSQ/investigationConfirmDSQList");
	}

	
	///Xử lý gửi yêu cầu sang bộ công an duyệt thông tin
	@RequestMapping(value = { "/pending_bca" })
	public ModelAndView pending_bca(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("pending_bca");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			String tranId = "";
			NicUploadJob nicUpload = uploadJobService.findById(Long.valueOf(investigationHitData.getUploadJobId()));
			tranId = nicUpload.getTransactionId();
			NicTransaction nicTran = nicTransactionService.findById(tranId);
			
			do {

				if (tokenA98 == "" || tokenA98 == null) {
					Map<String, String> resultToken = GetTokenAPI("a98", "a98");
					if (resultToken != null) {
						tokenA98 = resultToken.get("access_token");
						/*expireTokenA98 = Integer.parseInt(resultToken
								.get("expires_in"));
						typeTokenA98 = resultToken.get("token_type");*/
					}
				}
				/*if (expireTokenA98 == 0 && tokenA98 != "") {
					ReAccessToken(tokenA98);
				}*/

			} while (tokenA98 == "");

			do {
				if (tokenBNG == ""|| tokenBNG == null) {
					Map<String, String> resultToken = GetTokenAPI("bng", "bng");
					if (resultToken != null) {
						tokenBNG = resultToken.get("access_token");
						/*expireTokenBNG = Integer.parseInt(resultToken
								.get("expires_in"));
						typeTokenBNG = resultToken.get("token_type");*/
					}
				}
				/*if (expireTokenBNG == 0 && tokenBNG != "") {
					ReAccessToken(tokenBNG);
				}*/

			} while (tokenBNG == "");
			
			RequestVerifyInfo requestDocument = new RequestVerifyInfo();
			requestDocument.setIdNumber(nicTran.getNin());
			requestDocument.setName(nicTran.getNicRegistrationData().getSurnameLine1());
			requestDocument.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").format(nicTran.getNicRegistrationData().getDateOfBirth()));
			requestDocument.setPlaceOfBirth(nicTran.getNicRegistrationData().getPlaceOfBirth());
			requestDocument.setPassportNo(nicTran.getPrevPassportNo());
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonModel = mapper.writeValueAsString(requestDocument);
			String requestUrl = "http://192.168.1.15:8044/app/rest/v2/queries/epp$Investigate/findBlackList";
			String resultSync = sendPostAuthentication(requestUrl, jsonModel,
								tokenBNG, "Bearer");
			if(resultSync.equals("441")){
				messages.add("Lỗi khi gửi thông tin sang Bộ công an - API");
			}
			else{
				
				/*JSONObject myResponse = new JSONObject(st);
				JSONObject bls_ = myResponse.getJSONObject("bl_lists");
				JSONArray arrayResp = bls_.getJSONArray("bl_list");
				for (int i = 0; i < arrayResp.length(); i++) {
					JSONObject bl_ = arrayResp.getJSONObject(i);
					bl_.ge
				}
				*/
				uploadJobService.approveJobStatus_Confirmed(
						Long.valueOf(investigationHitData.getUploadJobId()),
						userSession.getUserName(), userSession.getWorkstationId(),
						NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
						NicTransactionLog.TRANSACTION_STATUS_NIC_PENING_BCA,
						NicUploadJob.RECORD_STATUS_PENDING_BCA);
				
				NicUploadJob nicUp = new NicUploadJob();
				nicUp = uploadJobService.findById(Long.valueOf(investigationHitData.getUploadJobId()));
				if(nicUp != null){
					//Cập nhật trạng thái đã gửi dữ liệu xác thực từ BCA
					nicUp.setValidateInfoBca(1);
					uploadJobService.saveOrUpdate(nicUp);
				}
				/* TODO: XỬ LÝ GỌI API CHUYỂN DỮ LIỆU SANG BỘ CÔNG AN */
				messages.add("Đã gửi bản ghi sang bộ công an kiểm duyệt thông tin.");
			}
			
		
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Gửi thông tin sang bộ công an thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationConfirmDSQ/investigationConfirmDSQList");
	}

	@RequestMapping(value = "/passportNoEdit", method = RequestMethod.POST)
	@ExceptionHandler
	@ResponseBody
	public boolean saveDataPassport(
			@org.springframework.web.bind.annotation.RequestBody String response,
			HttpServletRequest httpRequest) throws JsonParseException,
			JsonMappingException, IOException {
		boolean result = false;
		try {

			DataJson jsonData = new DataJson();

			try {
				if (response.split("&")[0].split("=")[1] != null)
					jsonData.setPassportNo(response.split("&")[0].split("=")[1]);
			} catch (Exception ex) {
			}
			try {
				if (response.split("&")[1].split("=")[1] != null)
					jsonData.setPassportYear(response.split("&")[1].split("=")[1]);
			} catch (Exception ex) {
			}
			jsonData.setTransactionId(response.split("&")[2].split("=")[1]);

			// DataJson jsonData = new DataJson();
			String ppNo = jsonData.getPassportNo();
			String ppYear = jsonData.getPassportYear();
			String transactionId = jsonData.getTransactionId();
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			if (ppNo != null && ppNo != "") {
				uploadJobService.getSaveTempPassportNo(transactionId, ppNo);
				/*
				 * result = documentDataService.updatePassportNo(transactionId,
				 * userSession.getUserName(), userSession.getWorkstationId(),
				 * ppNo);
				 */
			}
			if (ppYear != null && ppYear != "") {
				int year = Integer.parseInt(ppYear);
				result = nicTransactionService.updateValidateTime(
						transactionId, userSession.getUserName(),
						userSession.getWorkstationId(), year);
			}

		} catch (Exception ex) {
		}
		return result;
	}

	/*// /====DÙNG TẠM CONTROLLER ĐỂ KHỞI TẠO DANH SÁCH BÀN GIAO
	@RequestMapping(value = "/listHandover")
	public ModelAndView getHandoverList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getHandoverList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getHandoverList(investigationAssignmentData, request,
				httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getHandoverList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getHandoverList pageNo");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
				+ userSession.getUserName());

		{
			List<String> messages = new ArrayList<String>();

			if (listOfMessages != null) {
				messages.addAll(listOfMessages);
			}

			if (CollectionUtils.isNotEmpty(messages)) {
				httpRequest.setAttribute("messages", messages);
			}
		}

		{
			List<String> Errors = new ArrayList<String>();

			if (listOfMessages != null) {
				Errors.addAll(listOfErrors);
			}

			if (CollectionUtils.isNotEmpty(Errors)) {
				httpRequest.setAttribute("Errors", Errors);
			}
		}

		if (investigationAssignmentData == null) {
			investigationAssignmentData = new InvestigationAssignmentData();
		}

		investigationAssignmentData.cleanUpStrings();

		PaginatedResult<NicListHandover> pr = null;
		try {
			int pageSize = 20;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}

			pr = listHandoverService.findAllHandoverList(pageNo, pageSize);

			List<NicListHandover> list = new ArrayList<NicListHandover>();

			if (pr != null) {
				list = pr.getRows();
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
				model.addAttribute("jobList", list);
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_LIST_HANDOVER);
				ModelAndView modelAndView = new ModelAndView(
						"listHandover.list", searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_LIST_HANDOVER);
				ModelAndView modelAndView = new ModelAndView(
						"listHandover.list", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = { "/startDetailHandover/{packId}" })
	public ModelAndView startDetailHandovercompare(@PathVariable String packId,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start DetailHandover compare");

		return this
				.startDetailHandovercompare(packId, httpRequest, model, null);
	}

	public ModelAndView startDetailHandovercompare(String packId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start DetailHandover compare, listOfMessages");

		ModelAndView modelAndView = new ModelAndView("listHandover.compare");
		List<NicListHandoverDetail> list = new ArrayList<NicListHandoverDetail>();
		this.initializeModelAndViewForms(modelAndView);

		NicListHandover jobDetails = listHandoverService.findById(packId);
		String mainTransactionId = jobDetails.getTransactionId();
		if (!StringUtils.isBlank(jobDetails.getTransactionId())) {
			String[] arrayTrans = jobDetails.getTransactionId().split(",");
			if (arrayTrans.length > 0) {
				for (String t : arrayTrans) {
					NicTransaction nicTrans = nicTransactionService.findById(t);
					if (nicTrans != null) {
						NicListHandoverDetail detail = new NicListHandoverDetail();
						detail.setCreateDate(nicTrans.getNicRegistrationData()
								.getCreateDatetime());
						detail.setCreateBy(nicTrans.getNicRegistrationData()
								.getCreateBy());
						long jobId = 0;
						List<NicUploadJob> lstUpload = uploadJobService
								.findAllByTransactionId(nicTrans
										.getTransactionId());
						if (!lstUpload.isEmpty()) {
							for (NicUploadJob n : lstUpload) {
								jobId = n.getWorkflowJobId();
							}
						}
						detail.setJobId(jobId);
						detail.setPackageId(packId);
						detail.setTransactionId(nicTrans.getTransactionId());
						detail.setTypeListName(jobDetails.getTypeListName());
						list.add(detail);
					}
				}
			}
		}
		model.addAttribute("jobList", list);
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}*/

	// Xử lý tạo danh sách bàn giao cho Xac minh so bo sang BCA / Lannh dao
	@RequestMapping(value = { "/createHandover" })
	public ModelAndView createHandover(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model,
			@RequestParam("selectedJobIds") String[] checkboxvalues)
			throws Throwable {
		List<String> messages = new LinkedList<String>();
		messages.add("Có lỗi khi tạo danh sách bàn giao.");
		logger.info("createHandover");
		String mess = "";
		try {
			HttpSession session = httpRequest.getSession();
			Date date = new Date();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			String listIdTrans = "";
			if (checkboxvalues != null) {
				for (String st : checkboxvalues) {
					List<NicUploadJob> lstUpload = uploadJobService
							.findAllByTransactionId(st);
					if (investigationHitData.getAssignToId().equals("1")) {

						// Chuyển trạng thái bản ghi sang trạng thái chờ thông
						// tin từ BCA
						uploadJobService
								.approveJobStatus_Confirmed(
										Long.valueOf(lstUpload.get(0)
												.getWorkflowJobId()),
										userSession.getUserName(),
										userSession.getWorkstationId(),
										NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
										NicTransactionLog.TRANSACTION_STATUS_NIC_PENING_BCA,
										NicUploadJob.RECORD_STATUS_PENDING_BCA);
						mess = "BCA";
					} else {

						// Chuyển danh sách sang cho lãnh đạo
						uploadJobService
								.approveJobStatus_Confirmed(
										Long.valueOf(lstUpload.get(0)
												.getWorkflowJobId()),
										userSession.getUserName(),
										userSession.getWorkstationId(),
										NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
										NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
										NicUploadJob.RECORD_STATUS_CONFIRMED);
						mess = "Xét duyệt";
					}

					// Lưu id sang bảng danh sách bàn giao
					listIdTrans += st + ",";
				}

				if (!StringUtils.isEmpty(listIdTrans)) {
					NicListHandover handover = new NicListHandover();
					handover.setCreateBy(userSession.getUserId());
					handover.setCreateDate(date);
					// 10/06/2020 thieu typeLis, khong khoi tao NicListHandoverId.
//					handover.setPackageId(investigationHitData
//							.getCurrentlyAssignedToUserId());
					//handover.setTransactionId(listIdTrans);
					handover.setPackageName(investigationHitData
							.getUnassignAllForUserUserId());
					/*handover.setTypeList(Integer.parseInt(investigationHitData
							.getAssignToId()));
					handover.setDescription("");*/
					listHandoverService.createRecordHandover(handover);

				}
			} else {
				messages.add("Chưa có bản ghi nào được chọn.");
				httpRequest.setAttribute("messages", messages);
				return new ModelAndView(
						"forward:/servlet/investigationConfirm/investigationConfirmList");
			}
			messages.add("Tạo danh sách bàn giao sang " + mess +" thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Tạo danh sách bàn giao sang " + mess +" thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
		}

		investigationHitData = new InvestigationAssignmentData();
		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationConfirm/investigationConfirmList");
	}

	/*// /====DÙNG TẠM CONTROLLER ĐỂ KHỞI TẠO DANH SÁCH ĐIỂM IN
	@RequestMapping(value = "/persoLocations")
	public ModelAndView getPersoLocations(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getPersoLocations");

		int pageNo = 1;

		RequestBase rq = new RequestBase();
		rq.setPageSize(20);
		rq.setPageIndex(1);
		rq.setStatus(-1);
		try {
			List<PrintLocation> list_ = GetDataAllPrintLocation(rq);
			List<NicPersoLocations> listPerso = persoLocationsService.findAll();
			if (list_ != null) {
				if (listPerso == null) {
					for (PrintLocation pr : list_) {
						NicPersoLocations nicPerso_ = new NicPersoLocations();
						nicPerso_.setAddress(pr.getAddress());
						nicPerso_.setLastModifiedBy(pr.getLastModifiedBy());
						nicPerso_.setLastModifiedDate(pr.getLastModifiedDate());
						nicPerso_.setCreateDate(pr.getCreatedDate());
						nicPerso_.setCreateBy(pr.getCreatedBy());
						nicPerso_.setName(pr.getName());
						nicPerso_.setCode(pr.getCode());
						nicPerso_.setIdPerso(pr.getID());
						nicPerso_.setDescription(pr.getDescription());
						nicPerso_.setStatus(pr.getStatus());

						persoLocationsService
								.createRecordPersoLocations(nicPerso_);
					}
				} else {
					for (PrintLocation pr : list_) {
						Boolean check_ = false;
						for (NicPersoLocations pl : listPerso) {
							if (pl.getIdPerso() == pr.getID()) {
								pl.setAddress(pr.getAddress());
								pl.setLastModifiedBy(pr.getLastModifiedBy());
								pl.setLastModifiedDate(pr.getLastModifiedDate());
								pl.setCreateDate(pr.getCreatedDate());
								pl.setCreateBy(pr.getCreatedBy());
								pl.setName(pr.getName());
								pl.setCode(pr.getCode());
								pl.setIdPerso(pr.getID());
								pl.setDescription(pr.getDescription());
								pl.setStatus(pr.getStatus());

								persoLocationsService
										.createRecordPersoLocations(pl);
								check_ = true;
								break;
							}
						}
						if (!check_) {
							NicPersoLocations nicPerso_ = new NicPersoLocations();
							nicPerso_.setAddress(pr.getAddress());
							nicPerso_.setLastModifiedBy(pr.getLastModifiedBy());
							nicPerso_.setLastModifiedDate(pr
									.getLastModifiedDate());
							nicPerso_.setCreateDate(pr.getCreatedDate());
							nicPerso_.setCreateBy(pr.getCreatedBy());
							nicPerso_.setName(pr.getName());
							nicPerso_.setCode(pr.getCode());
							nicPerso_.setIdPerso(pr.getID());
							nicPerso_.setDescription(pr.getDescription());
							nicPerso_.setStatus(pr.getStatus());
							persoLocationsService
									.createRecordPersoLocations(nicPerso_);
						}
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getPersoLocations(investigationAssignmentData, request,
				httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getPersoLocations(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getPersoLocations pageNo");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
				+ userSession.getUserName());

		{
			List<String> messages = new ArrayList<String>();

			if (listOfMessages != null) {
				messages.addAll(listOfMessages);
			}

			if (CollectionUtils.isNotEmpty(messages)) {
				httpRequest.setAttribute("messages", messages);
			}
		}

		{
			List<String> Errors = new ArrayList<String>();

			if (listOfMessages != null) {
				Errors.addAll(listOfErrors);
			}

			if (CollectionUtils.isNotEmpty(Errors)) {
				httpRequest.setAttribute("Errors", Errors);
			}
		}

		if (investigationAssignmentData == null) {
			investigationAssignmentData = new InvestigationAssignmentData();
		}

		investigationAssignmentData.cleanUpStrings();

		PaginatedResult<NicPersoLocations> pr = null;
		try {
			int pageSize = 20;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}

			pr = persoLocationsService.findAllPersoLocations(pageNo, pageSize);

			List<NicPersoLocations> list = new ArrayList<NicPersoLocations>();

			if (pr != null) {
				list = pr.getRows();
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
				model.addAttribute("jobList", list);
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_PERSO_LOCATIONS);
				ModelAndView modelAndView = new ModelAndView(
						"persoLocations.list", searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_PERSO_LOCATIONS);
				ModelAndView modelAndView = new ModelAndView(
						"persoLocations.list", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = { "/startDetailPersoLocations/{id}" })
	public ModelAndView startDetailPersoLocations(
			@ModelAttribute(value = "formData") InvestigationHitData investigationHitData,
			@PathVariable long id, HttpServletRequest httpRequest, Model model)
			throws Throwable {
		logger.info("NIC Start DetailHandover compare");

		return this.startDetailPersoLocations(id, httpRequest, model, null);
	}

	public ModelAndView startDetailPersoLocations(long id,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start DetailPersoLocations compare, listOfMessages");

		ModelAndView modelAndView = new ModelAndView("persoLocations.compare");
		this.initializeModelAndViewForms(modelAndView);

		List<SiteRepository> listSR = siteRepositoryDao.findAll();
		NicPersoLocations jobDetails = persoLocationsService.findById(id);
		Map<String, Boolean> listSite = new LinkedHashMap<String, Boolean>();

		if (listSR != null) {
			boolean contains = false;
			if (jobDetails != null) {
				String sites = jobDetails.getIssuePlace();
				if (!StringUtils.isEmpty(sites)) {

					String[] arraySite = sites.split(",");
					for (SiteRepository a : listSR) {
						contains = java.util.Arrays.asList(arraySite).contains(
								a.getSiteId());
						listSite.put(a.getSiteId(), contains);
					}
				} else {
					for (SiteRepository a : listSR) {
						listSite.put(a.getSiteId(), contains);
					}
				}
			} else {
				for (SiteRepository a : listSR) {
					listSite.put(a.getSiteId(), contains);
				}
			}
		}
		model.addAttribute("persoLocation", jobDetails.getName());
		model.addAttribute("jobList", listSite);
		model.addAttribute("idhidden", jobDetails.getId());
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}

	@RequestMapping(value = { "/updatePersoLocations" })
	public ModelAndView updatePersoLocations(
			@ModelAttribute(value = "formData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model,
			@RequestParam("chkSms") String[] checkboxvalues) throws Throwable {
		logger.info("updatePersoLocations");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			Date date = new Date();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			String listIdTrans = "";
			NicPersoLocations nicPerso = persoLocationsService.findById(Long
					.valueOf(investigationHitData.getUploadJobId()));
			if (checkboxvalues != null) {
				for (String st : checkboxvalues) {
					listIdTrans += st + ",";
					if (nicPerso != null) {
						nicPerso.setIssuePlace(listIdTrans);
						persoLocationsService.saveOrUpdate(nicPerso);
					}
				}
			} else {
				messages.add("Chưa có bản ghi nào được chọn.");
				httpRequest.setAttribute("messages", messages);
				return new ModelAndView(
						"forward:/servlet/investigationConfirm/persoLocations");
			}
			messages.add("Cập nhật điểm in thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Cập nhật điểm in không thành công. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		investigationHitData = new InvestigationHitData();
		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationConfirm/persoLocations");
	}

	// Gọi API từ PERSO lấy dữ liệu các điểm in
	public List<PrintLocation> GetDataAllPrintLocation(RequestBase urlParameters)
			throws JsonParseException, JsonMappingException, IOException {
		List<PrintLocation> listResult = new LinkedList<PrintLocation>();
		String urlP_ = "Keyword="
				+ (!StringUtils.isEmpty(urlParameters.getKeyword()) ? urlParameters
						.getKeyword() : "") + "&PageSize="
				+ urlParameters.getPageSize() + "&PageIndex="
				+ urlParameters.getPageIndex() + "&Status="
				+ urlParameters.getStatus();

		byte[] postData = urlP_.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		URL url = new URL(
				"http://192.168.1.16:1988/master/getAllPrintLocationByParam");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		connection.setRequestProperty("Content-Length",
				Integer.toString(postDataLength));
		connection.setUseCaches(false);
		try (DataOutputStream wr = new DataOutputStream(
				connection.getOutputStream())) {
			wr.write(postData);
		}
		connection.connect();

		int statusCode = connection.getResponseCode();
		if (statusCode == 200) {
			InputStream content = connection.getInputStream();
			Charset charset = Charset.forName("UTF8");
			InputStreamReader isr = new InputStreamReader(content, charset);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String st = sb.toString();
			JSONObject onb = new JSONObject(st);
			BaseListResponse responseB = new BaseListResponse();
			responseB.setIsSuccess(onb.getBoolean("IsSuccess"));
			if (responseB.getIsSuccess()) {
				JSONArray arrayResp = onb.getJSONArray("Data");
				for (int i = 0; i < arrayResp.length(); i++) {

					JSONObject jk = new JSONObject();
					jk = arrayResp.getJSONObject(i);
					PrintLocation pl_ = new PrintLocation();
					pl_.setID(jk.getLong("ID"));

					DateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss.SSS");
					Date datec = null;
					try {
						datec = formatter.parse(jk.getString("CreatedDate"));
					} catch (JSONException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					pl_.setCreatedDate(datec);

					DateFormat formatterm = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss.SSS");
					Date datem = null;
					try {
						datem = formatterm.parse(jk
								.getString("LastModifiedDate"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pl_.setLastModifiedDate(datem);
					pl_.setCreatedBy(jk.getLong("CreatedBy"));
					pl_.setLastModifiedBy(jk.getLong("LastModifiedBy"));
					pl_.setName(jk.getString("Name"));
					pl_.setCode(jk.getString("Code"));
					pl_.setAddress(jk.getString("Address"));
					pl_.setDescription(jk.getString("Description"));
					pl_.setStatus(jk.getInt("Status"));

					if (pl_ != null) {
						listResult.add(pl_);
					}
				}
				responseB.setData(listResult);
			}

		} else {
			// /Trả về log mã lỗi và thông tin lỗi

		}
		return listResult;
	}*/

	// /======DANH SÁCH IN THÀNH CÔNG========
	@RequestMapping(value = "/successPersoList")
	public ModelAndView getSuccessPersoList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getSuccessPersoList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getSuccessPersoList(investigationAssignmentData, request,
				httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getSuccessPersoList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getSuccessPersoList pageNo");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
				+ userSession.getUserName());

		{
			List<String> messages = new ArrayList<String>();

			if (listOfMessages != null) {
				messages.addAll(listOfMessages);
			}

			if (CollectionUtils.isNotEmpty(messages)) {
				httpRequest.setAttribute("messages", messages);
			}
		}

		{
			List<String> Errors = new ArrayList<String>();

			if (listOfMessages != null) {
				Errors.addAll(listOfErrors);
			}

			if (CollectionUtils.isNotEmpty(Errors)) {
				httpRequest.setAttribute("Errors", Errors);
			}
		}

		if (investigationAssignmentData == null) {
			investigationAssignmentData = new InvestigationAssignmentData();
		}

		investigationAssignmentData.cleanUpStrings();

		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageSize = 20;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}

			pr = uploadJobService
					.findAllForInvestigationPagination(
							new String[] { NicUploadJob.RECORD_STATUS_APPROVE_PERSO },
							userSession.getUserId(),
							pageNo,
							pageSize,
							investigationAssignmentData.assignedOnly(),
							new AssignmentFilterAll(investigationAssignmentData
									.getSearchTransactionId(),
									investigationAssignmentData.getPriority(),
									investigationAssignmentData
											.getTransactionType(), null, null,
									investigationAssignmentData
											.getRegSiteCode(),
									investigationAssignmentData
											.getPassportType(),
									investigationAssignmentData
											.getTypeInvestigation()));

			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();

			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
					for (NicUploadJobDto record : list) {
						String transactionId = record.getTransactionId();
						if (transactionId != null) {
							NicTransaction nicTransaction = nicTransactionService
									.findById(transactionId);
							if (nicTransaction != null) {
								record.setDateOfApplication(nicTransaction
										.getDateOfApplication());
								record.setEstDateOfCollection(nicTransaction
										.getEstDateOfCollection());
								record.setFullName(nicTransaction
										.getNicRegistrationData()
										.getSurnameLine1());
								record.setPassportType(nicTransaction
										.getPassportType());
								record.setRegSiteCode(nicTransaction
										.getRegSiteCode());
								{
									try {
										CodeValues codeValue = codesService
												.getCodeValueByIdName(
														Codes.PRIORITY,
														nicTransaction
																.getPriority()
																.toString());
										if (codeValue != null
												&& codeValue.getCodeValueDesc() != null) {
											record.setPriorityString(codeValue
													.getCodeValueDesc());
										} else {
											record.setPriorityString(nicTransaction
													.getPriority() == null ? null
													: nicTransaction
															.getPriority()
															.toString());
										}
									} catch (Exception e) {
										record.setPriorityString(nicTransaction
												.getPriority() == null ? null
												: nicTransaction.getPriority()
														.toString());
									}
								}
							}
						}

					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "No Data found",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);

				model.addAttribute("jobList", list);

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_APPROVE_PERSO);
				ModelAndView modelAndView = new ModelAndView(
						"successPerso.list",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_APPROVE_PERSO);
				ModelAndView modelAndView = new ModelAndView(
						"successPerso.list",
						null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// /Xử lý xét duyệt cho thông tin cho CQĐD
		@RequestMapping(value = { "/confirmDSQ" })
		public ModelAndView confirmDSQ(
				@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
				BindingResult result, WebRequest request,
				HttpServletRequest httpRequest, Model model) throws Throwable {

			logger.info("confirmDSQ action");

			List<String> messages = new LinkedList<String>();
			try {
				HttpSession session = httpRequest.getSession();
				UserSession userSession = (UserSession) session
						.getAttribute("userSession");

				uploadJobService.confirmDSQ(userSession.getUserName() + ": " +
						investigationHitData.getJobRejectRemarks(),
						Long.valueOf(investigationHitData.getUploadJobId()),
						userSession.getUserName(), userSession.getWorkstationId(),
						"",
						NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
						NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED_CONSULATE);

				messages.add("Đã xác nhận bản ghi thành công.");
			} catch (JobNoLongerAssignedToOfficerException e) {
				logger.info("confirmDSQ failed");
				messages.add("Xác nhận bản ghi thất bại. Công việc hiện tại không được phân công cho User này");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			httpRequest.setAttribute("messages", messages);
			return new ModelAndView(
					"forward:/servlet/investigationConfirmDSQ/investigationConfirmDSQList");
		}
	
	/*// /======DANH SÁCH CHỜ CÂP SỐ HỘ CHIẾU========
	@RequestMapping(value = "/pendingPassportNoList")
	public ModelAndView getPendingPassportNoList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getSuccessPersoList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getPendingPassportNoList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getPendingPassportNoList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getPendingPassportNoList pageNo");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
				+ userSession.getUserName());

		{
			List<String> messages = new ArrayList<String>();

			if (listOfMessages != null) {
				messages.addAll(listOfMessages);
			}

			if (CollectionUtils.isNotEmpty(messages)) {
				httpRequest.setAttribute("messages", messages);
			}
		}

		{
			List<String> Errors = new ArrayList<String>();

			if (listOfMessages != null) {
				Errors.addAll(listOfErrors);
			}

			if (CollectionUtils.isNotEmpty(Errors)) {
				httpRequest.setAttribute("Errors", Errors);
			}
		}

		if (investigationAssignmentData == null) {
			investigationAssignmentData = new InvestigationAssignmentData();
		}

		investigationAssignmentData.cleanUpStrings();

		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageSize = 20;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}

			pr = uploadJobService
					.findPendingPassportNo(
							new String[] { NicUploadJob.RECORD_STATUS_APPROVE_PERSO },
							userSession.getUserId(),
							pageNo,
							pageSize,
							investigationAssignmentData.assignedOnly(),
							new AssignmentFilterAll(investigationAssignmentData
									.getSearchTransactionId(),
									investigationAssignmentData.getPriority(),
									investigationAssignmentData
											.getTransactionType(), null, null,
									investigationAssignmentData
											.getRegSiteCode(),
									investigationAssignmentData
											.getPassportType(),
									investigationAssignmentData
											.getTypeInvestigation()));

			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();

			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
					for (NicUploadJobDto record : list) {
						String transactionId = record.getTransactionId();
						if (transactionId != null) {
							NicTransaction nicTransaction = nicTransactionService
									.findById(transactionId);
							if (nicTransaction != null) {
								record.setDateOfApplication(nicTransaction
										.getDateOfApplication());
								record.setEstDateOfCollection(nicTransaction
										.getEstDateOfCollection());
								record.setFullName(nicTransaction
										.getNicRegistrationData()
										.getSurnameLine1());
								record.setPassportType(nicTransaction
										.getPassportType());
								record.setRegSiteCode(nicTransaction
										.getRegSiteCode());
								{
									try {
										CodeValues codeValue = codesService
												.getCodeValueByIdName(
														Codes.PRIORITY,
														nicTransaction
																.getPriority()
																.toString());
										if (codeValue != null
												&& codeValue.getCodeValueDesc() != null) {
											record.setPriorityString(codeValue
													.getCodeValueDesc());
										} else {
											record.setPriorityString(nicTransaction
													.getPriority() == null ? null
													: nicTransaction
															.getPriority()
															.toString());
										}
									} catch (Exception e) {
										record.setPriorityString(nicTransaction
												.getPriority() == null ? null
												: nicTransaction.getPriority()
														.toString());
									}
								}
							}
						}

					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "No Data found",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);

				model.addAttribute("jobList", list);

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_PENDING_PASSPORTNO);
				ModelAndView modelAndView = new ModelAndView(
						"pendingPassportNo.list", searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_PENDING_PASSPORTNO);
				ModelAndView modelAndView = new ModelAndView(
						"pendingPassportNo.list", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/applyFilterPendingPassportNo")
	public ModelAndView applyFilterPendingPassportNo(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getPendingPassportNoList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}
	
	public class ResponseReport{
		
		private String sobiennhanF;
		private String nguoinopF;
		private String namsinhF;
		private String cmndF;
		private String hentraF;
		
		public ResponseReport(){
			
		}
		
		public ResponseReport(String sobiennhanF, String nguoinopF, String namsinhF, String cmndF, String hentraF){
			this.sobiennhanF = sobiennhanF;
			this.nguoinopF = nguoinopF;
			this.namsinhF = namsinhF;
			this.cmndF = cmndF;
			this.hentraF = hentraF;
		}
		
		public String getSobiennhanF(){
			return this.sobiennhanF;
		}
		public void setSobiennhanF(String sobiennhanF){
			this.sobiennhanF = sobiennhanF;
		}
		
		public String getNguoinopF(){
			return this.nguoinopF;
		}
		public void setNguoinopF(String nguoinopF){
			this.nguoinopF = nguoinopF;
		}
		
		public String getNamsinhF(){
			return this.namsinhF;
		}
		public void setNamsinhF(String namsinhF){
			this.namsinhF = namsinhF;
		}
		
		public String getCmndF(){
			return this.cmndF;
		}
		public void setCmndF(String cmndF){
			this.cmndF = cmndF;
		}
		
		public String getHentraF(){
			return this.hentraF;
		}
		public void setHentraF(String hentraF){
			this.hentraF = hentraF;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/showPdfResult")
	public void printPDF(WebRequest request, Model model, @RequestParam("packID") String packId , HttpServletResponse response) {
		try {
			String pachId_ = packId.split(",")[0];
			NicListHandover jobDetails = listHandoverService.findById(pachId_);
			if(jobDetails != null){
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("titleP", "DANH SÁCH " + jobDetails.getTypeListName().toUpperCase());
				String dateP_ = "Ngày " + jobDetails.getCreateDate().getDate() + " tháng " + jobDetails.getCreateDate().getMonth() + " năm " + (jobDetails.getCreateDate().getYear() + 1900);
				parameterMap.put("dateP", dateP_);
				parameterMap.put("codeHandoverP", "Số: " + jobDetails.getPackageId());
				
				String[] arrayTrans = jobDetails.getTransactionId().split(",");
				parameterMap.put("totalP", String.valueOf(arrayTrans.length));
				List<ResponseReport> list = new ArrayList<ResponseReport>();
				if(arrayTrans.length > 0){
					
					for (String t : arrayTrans) {
						NicTransaction nicTrans = nicTransactionService.findById(t);
						if (nicTrans != null) {
							ResponseReport detail = new ResponseReport();
							detail.setSobiennhanF(t);
							detail.setNamsinhF(new SimpleDateFormat("MM-dd-yyyy").format(nicTrans.getNicRegistrationData()
									.getDateOfBirth()));
							detail.setCmndF(nicTrans.getNin());
							detail.setNguoinopF(jobDetails.getCreateBy());
							detail.setHentraF(new SimpleDateFormat("MM-dd-yyyy").format(nicTrans.getEstDateOfCollection()));
							list.add(detail);
						}
					}
					
					FastReportBuilder drb = new FastReportBuilder();
					drb.addColumn("Số biên nhận", "sobiennhanF", String.class.getName(), 30);
					drb.addColumn("Người nộp", "nguoinopF", String.class.getName(), 30);
					drb.addColumn("Năm sinh", "namsinhF", String.class.getName(), 30);
					drb.addColumn("CMND/CCCD", "cmndF", String.class.getName(), 30);
					drb.addColumn("Hẹn trả", "hentraF", String.class.getName(), 30);
					for (String entry : ricPymtRptDto.getSelColumns()) {
						drb.addColumn(entry, entry, String.class.getName(), 30);
					}
					drb.setTemplateFile("resources/report/templates/list_base.jrxml");
					drb.setUseFullPageWidth(true);
					DynamicReport dr = drb.build();
					//PaginatedResult<RICPymtRptDto> result = paymentService.getRicPymtRptRecordList(ricPymtRptDto, 1, 3, true);
					JRDataSource dataSource = new JRBeanCollectionDataSource(list);
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					try {
						InputStream reportStream = this.getClass().getResourceAsStream("/report/templates/list_base.jrxml");
						JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
						JasperReportsUtils.renderAsPdf(jasperReport, parameterMap, dataSource, os);
						response.setContentType("application/pdf");
						response.getOutputStream().write(os.toByteArray());
					} catch (JRException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					 * InputStream reportStream = this.getClass().getResourceAsStream( "/RIC_ExceptionHandling_Report.jrxml").; JasperReport jasperReport = JasperCompileManager .compileReport(reportStream); 
					JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), dataSource, parameterMap);
					System.out.println("jasperPrint >>" + jasperPrint.getName());
					JRExporter exporter = new JRPdfExporter();
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
					
					 * FileOutputStream fileOuputStream=null; fileOuputStream = new FileOutputStream("c:\\pdf\\report1.pdf"); 
					// fileOuputStream.write(os.toByteArray());
					response.setContentType("application/pdf");
					response.getOutputStream().write(os.toByteArray());
					// fileOuputStream.close();
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
}
