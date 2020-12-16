package com.nec.asia.nic.investigation.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.admin.domain.User;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.queuesJobSchedule.service.QueuesJobScheduleService;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
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
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/investigationBca")
public class InvestigationBcaController extends InvestigationController {

	private static final Logger logger = LoggerFactory
			.getLogger(InvestigationBcaController.class);

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
	private SiteService siteService;
	
	@Autowired
	private QueuesJobScheduleService queuesJobScheduleService;
	
	@Autowired
	private NicTransactionPackageService transactionPackageService;

	@Autowired
	private WorkflowProcessService workflowProcessService; 

	@Autowired
	private PersoLocationsService persoLocationsService;
	
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
	/*
	 * ==========================================================================
	 * =======================================
	 */
	/* ===================================================================== */
	/*
	 * 
	 * DANH SÁCH CHỜ XÁC NHẬN TỪ BỘ CÔNG AN
	 */

	@RequestMapping(value = "/investigationPendingBcaList")
	public ModelAndView getInvestigationPendingBcaList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationPendingBcaList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		investigationAssignmentData.setValidateInfoBca("0");
		return this.getInvestigationPendingBcaList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}
	
	@RequestMapping(value = "/investigationLoadBcaDetail/{packageId}")
	public ModelAndView investigationLoadBcaDetail(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData, @PathVariable String packageId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationPendingBcaList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
        investigationAssignmentData.setListCode(packageId); 
		return this.getInvestigationPendingBcaList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}
	
	@RequestMapping(value = "/investigationConfirmBcaDetail/{packageId}")
	public ModelAndView investigationConfirmBcaDetail(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData, @PathVariable String packageId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationPendingBcaList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
        investigationAssignmentData.setListCode(packageId); 
		return this.getInvestigationConfirmedBcaList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}
	
	@RequestMapping(value = "/investigationLoadBcaList")
	public ModelAndView getInvestigationLoadBcaList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationPendingBcaList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;	
		return this.getInvestigationLoadBcaList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}
	
	@RequestMapping(value = "/investigationLoadBcaListConfirm")
	public ModelAndView getInvestigationLoadBcaListConfirm(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationPendingBcaList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;	
		return this.getInvestigationLoadBcaListConfirm(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}
	
	public ModelAndView getInvestigationLoadBcaListConfirm(@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationPendingBcaList pageNo");
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
			int pageSize = 10;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			Date creDate = null;
			if (!StringUtils.isEmpty(investigationAssignmentData.getCreateDate())){				
				String pattern = "dd-MMM-yyyy";
			    SimpleDateFormat format = new SimpleDateFormat(pattern);
			    creDate = format.parse(investigationAssignmentData.getCreateDate());
			}
			pr = uploadJobService.findAllForInvestigationPagination3(new String[] { NicUploadJob.RECORD_STATUS_CONFIRMED_BCA }, userSession.getUserId(), Constants.INVESTIGATION_STAGE_CONFIRM,pageNo, pageSize, investigationAssignmentData.assignedOnly(), new AssignmentFilterAll(creDate, null, investigationAssignmentData.getPackageCode()));
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();

			if (pr != null) {
				list = pr.getRows();
				if (list != null) {					
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu", 0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				//searchResultMap.put("pageSize", pageSize);
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
				//model.addAttribute("jobList", list);
				

				model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION_BCA);
				ModelAndView modelAndView = new ModelAndView("investigation.investigationBca.investigationConfirmedBcaList1", searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION_BCA);
				ModelAndView modelAndView = new ModelAndView("investigation.investigationBca.investigationConfirmedBcaList1", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ModelAndView getInvestigationLoadBcaList(@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationPendingBcaList pageNo");
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
			int pageSize = 10;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			//Phúc thêm 29/5/2019 lấy full size khi thay bảng mới
			pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
			Date creDate = null;
			if (!StringUtils.isEmpty(investigationAssignmentData.getCreateDate())){				
				String pattern = "dd-MMM-yyyy";
			    SimpleDateFormat format = new SimpleDateFormat(pattern);
			    creDate = format.parse(investigationAssignmentData.getCreateDate());
			}
			pr = uploadJobService.findAllForInvestigationPagination3(new String[] { NicUploadJob.RECORD_STATUS_PENDING_BCA }, userSession.getUserId(), Constants.INVESTIGATION_STAGE_PENDING, pageNo, pageSize, investigationAssignmentData.assignedOnly(), new AssignmentFilterAll(creDate, null, investigationAssignmentData.getPackageCode()));
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();

			if (pr != null) {
				list = pr.getRows();
				if (list != null) {					
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu", 0);
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
						Constants.HEADING_NIC_INVESTIGATION_BCA);
				ModelAndView modelAndView = new ModelAndView("investigation.investigationBca.investigationLoadBcaList", searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_BCA);
				ModelAndView modelAndView = new ModelAndView("investigation.investigationBca.investigationLoadBcaList", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ModelAndView getInvestigationPendingBcaList(@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationPendingBcaList pageNo");
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
			int pageSize = 10;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
				
			}
			pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
			//Phúc thêm lấy chi tiết danh sách theo mã
			List<NicTransactionPackage> listPack = transactionPackageService.getListPackageByPackageId(investigationAssignmentData.getListCode());
			String[] arrPack = new String[listPack.size()];
			for(int i = 0; i < listPack.size(); i++){
				arrPack[i] = listPack.get(i).getTransactionId();
			}

			///TRUNG: Lấy dữ liệu danh sách chờ xác thực từ bộ công an
			pr = uploadJobService.findAllForInvestigationPaginationAll(
					new String[] { NicUploadJob.RECORD_STATUS_PENDING_BCA },
					userSession.getUserId(),
					pageNo,
					pageSize,
					investigationAssignmentData.assignedOnly(),
					new AssignmentFilterAll(investigationAssignmentData.getSearchTransactionId(), investigationAssignmentData.getPriority(),investigationAssignmentData.getTransactionType(), 
							null, null,
							investigationAssignmentData.getRegSiteCode(), investigationAssignmentData.getPassportType(), null, investigationAssignmentData.getPackageCode(), arrPack, investigationAssignmentData.getValidateInfoBca()));

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
											record.setPriorityString(codeValue.getCodeValueDesc());
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
				ModelAndView modelAndView = null;
				if(StringUtils.isNotEmpty(investigationAssignmentData.getListCode())){
					model.addAttribute("fnSelected",Constants.HEADING_NIC_INVESTIGATION_BCA_DETAIl);
					model.addAttribute("listCode", investigationAssignmentData.getListCode());
					modelAndView = new ModelAndView("investigation.investigationBca.investigationLoadBcaCompare",searchResultMap);					
				}else{
					model.addAttribute("fnSelected",Constants.HEADING_NIC_INVESTIGATION_BCA);
					modelAndView = new ModelAndView("investigation.investigationBca.investigationPendingBcaList",searchResultMap);					
				}
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				ModelAndView modelAndView = null;
				if(StringUtils.isNotEmpty(investigationAssignmentData.getListCode())){
					model.addAttribute("fnSelected",Constants.HEADING_NIC_INVESTIGATION_BCA_DETAIl);
					model.addAttribute("listCode", investigationAssignmentData.getListCode());
					modelAndView = new ModelAndView("investigation.investigationBca.investigationLoadBcaCompare",null);					
				}else{
					model.addAttribute("fnSelected",Constants.HEADING_NIC_INVESTIGATION_BCA);
					modelAndView = new ModelAndView("investigation.investigationBca.investigationPendingBcaList",null);					
				}
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = { "/startDetailInvestigationP/{jobId}" })
	public ModelAndView startDetailInvestigationP(@PathVariable long jobId,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start Investigation Pending BCA compare");

		return this.startDetailInvestigationP(jobId, httpRequest, model, null);
	}

	public ModelAndView startDetailInvestigationP(long jobId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Investigation Pending BCA compare, listOfMessages");

		ModelAndView modelAndView = new ModelAndView(
				"investigationBca.startInvestigationPendingBca.compare");

		this.initializeModelAndViewForms(modelAndView);

		NicUploadJob jobDetails = uploadJobService.findById(jobId);
		String mainTransactionId = jobDetails.getTransactionId();

		// 05-02-2018: trung show img và thông tin cơ bản
		String photoStr = null;
		NicTransaction nicTransaction = null;

		nicTransaction = nicTransactionService.findById(jobDetails
				.getTransactionId());// TRung thêm
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
				.findNicTransactionAttachments(jobDetails.getTransactionId(),
						NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
						NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
		}
		//Phúc chỉnh format ngày, dân tộc...
		if(nicTransaction.getNicRegistrationData().getDateOfBirth() != null){
			String dob = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getDateOfBirth());
			nicTransaction.setDateOfBirthDesc(dob);
			//nicTransaction.getNicRegistrationData().setDateOfBirth(HelperClass.convertStringToDate1(dob));			
		}
		if(nicTransaction.getNicRegistrationData().getCreateDatetime() != null){
			String crd = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getCreateDatetime());
			nicTransaction.setCreateDesc(crd);
			//nicTransaction.getNicRegistrationData().setCreateDatetime(HelperClass.convertStringToDate1(crd));			
		}
		if(nicTransaction.getNicRegistrationData().getReligion() != null){
			String religion = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_RELIGION_CODE_ID, nicTransaction.getNicRegistrationData().getReligion(), "");
			nicTransaction.setReligionDesc(religion);
		}
		if(nicTransaction.getNicRegistrationData().getNation()!= null){
			String nation = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_NATION_CODE_ID, nicTransaction.getNicRegistrationData().getNation(), "");
			nicTransaction.setNationDesc(nation);
		}
		modelAndView.addObject("photoStr", photoStr);
		modelAndView.addObject("nicData", nicTransaction);
		// end
		modelAndView.addObject("transID", mainTransactionId);
		modelAndView.addObject("jobsID", jobId);
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}
	
	@RequestMapping(value = { "/startDetailInvestigationL/{jobId}/{listCode}" })
	public ModelAndView startDetailInvestigationL(@PathVariable long jobId, @PathVariable String listCode,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start Investigation Pending BCA compare");

		return this.startDetailInvestigationL(jobId, listCode, httpRequest, model, null);
	}

	public ModelAndView startDetailInvestigationL(long jobId, String listCode,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Investigation Pending BCA compare, listOfMessages");
		model.addAttribute("listCode", listCode);
		ModelAndView modelAndView = new ModelAndView("investigation.investigationBca.investigationLoadBcaDetail");

		this.initializeModelAndViewForms(modelAndView);

		NicUploadJob jobDetails = uploadJobService.findById(jobId);
		String mainTransactionId = jobDetails.getTransactionId();

		// 05-02-2018: trung show img và thông tin cơ bản
		String photoStr = null;
		NicTransaction nicTransaction = null;

		nicTransaction = nicTransactionService.findById(jobDetails
				.getTransactionId());// TRung thêm
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
				.findNicTransactionAttachments(jobDetails.getTransactionId(),
						NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
						NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
		}
		//Phúc chỉnh format ngày, dân tộc...
		if(nicTransaction.getNicRegistrationData().getDateOfBirth() != null){
			String dob = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getDateOfBirth());
			nicTransaction.setDateOfBirthDesc(dob);
			//nicTransaction.getNicRegistrationData().setDateOfBirth(HelperClass.convertStringToDate1(dob));			
		}
		if(nicTransaction.getNicRegistrationData().getCreateDatetime() != null){
			String crd = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getCreateDatetime());
			nicTransaction.setCreateDesc(crd);
			//nicTransaction.getNicRegistrationData().setCreateDatetime(HelperClass.convertStringToDate1(crd));			
		}
		if(nicTransaction.getNicRegistrationData().getReligion() != null){
			String religion = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_RELIGION_CODE_ID, nicTransaction.getNicRegistrationData().getReligion(), "");
			nicTransaction.setReligionDesc(religion);
		}
		if(nicTransaction.getNicRegistrationData().getNation()!= null){
			String nation = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_NATION_CODE_ID, nicTransaction.getNicRegistrationData().getNation(), "");
			nicTransaction.setNationDesc(nation);
		}
		modelAndView.addObject("photoStr", photoStr);
		modelAndView.addObject("nicData", nicTransaction);
		// end
		modelAndView.addObject("transID", mainTransactionId);
		modelAndView.addObject("jobsID", jobId);
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}

	// /Xử lý gửi lại yêu cầu sang bộ công an duyệt thông tin
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

			NicUploadJob nicUp = new NicUploadJob();
			nicUp = uploadJobService.findById(Long.valueOf(investigationHitData.getUploadJobId()));
			/*=== Kiểm tra xem có phải dữ liệu */
			NicTransaction nicTran = nicTransactionService.getNicTransactionById(nicUp.getTransactionId()).getModel();
			if(nicUp != null){
				//Cập nhật trạng thái đã có dữ liệu xác thực từ BCA
				nicUp.setValidateInfoBca(2);
				uploadJobService.saveOrUpdate(nicUp);
			}
			
			if(nicTran != null && StringUtils.isEmpty(nicTran.getNicSiteCode())){

				/*======= TẠM THỜI FAKE DỮ LIỆU CÓ KẾT QUẢ TRẢ VỀ TỪ BỘ CÔNG AN*/
				uploadJobService.approveJobStatus_Confirmed(Long.valueOf(investigationHitData.getUploadJobId()),
						 userSession.getUserName(), userSession.getWorkstationId(),
						 NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
						 NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
						 NicUploadJob.RECORD_STATUS_CONFIRMED_BCA);
			}
			else{
				/*======= TẠM THỜI FAKE DỮ LIỆU CÓ KẾT QUẢ TRẢ VỀ TỪ BỘ CÔNG AN (Cho bản ghi Cơ quan đại diện nước ngoài) ======*/
				uploadJobService.approveJobStatus_Confirmed(Long.valueOf(investigationHitData.getUploadJobId()),
						 userSession.getUserName(), userSession.getWorkstationId(),
						 NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
						 NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
						 NicUploadJob.RECORD_STATUS_CONFIRMED_DSQ);
			}
			
			/**TODO: CHỜ SERVICE XÁC MINH BỘ CÔNG AN BÊN TECA XỬ LÝ */
			/* TODO: XỬ LÝ GỌI LẠI API CHUYỂN DỮ LIỆU SANG BỘ CÔNG AN YÊU CẦU XỬ LÝ */

			messages.add("Đã gửi lại yêu cầu xác minh lại sang bộ công an.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Yêu cầu gửi thông tin sang bộ công an xác minh thất bại.\\n Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationBca/investigationPendingBcaList");
	}

	///Xử lý gửi yêu cầu hủy xác minh thông tin
	@RequestMapping(value = { "/cancel_bca" })
	public ModelAndView cancel_bca(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("cancel_bca");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			///Chuyển đên danh sách Kiểm duyệt sơ bộ
			uploadJobService.approveJobStatus_Confirmed(
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_CANCEL_BCA,
					NicUploadJob.RECORD_STATUS_APPROVED);

			/* TODO: XỬ LÝ GỌI API CHUYỂN DỮ LIỆU SANG BỘ CÔNG AN */

			messages.add("Đã HỦY yêu cầu xác minh lại sang bộ công an.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Yêu cầu HỦY thông tin sang bộ công an xác minh thất bại.\\n Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationBca/investigationPendingBcaList");
	}
	
	@RequestMapping(value = "/applyFilterLoading")
	public ModelAndView applyFilterLoading(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilterPending");

		return this.getInvestigationLoadBcaList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}
	
	@RequestMapping(value = "/applyFilterPending")
	public ModelAndView applyFilterPending(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilterPending");

		return this.getInvestigationPendingBcaList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}
	
	@RequestMapping(value = "/applyFilterConfirm1")
	public ModelAndView applyFilterConfirm1(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilterPending");

		return this.getInvestigationLoadBcaListConfirm(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}

	/*
	 * ==========================================================================
	 * =======================================
	 */
	/* ===================================================================== */
	/*
	 * 
	 * DANH SÁCH ĐÃ XÁC NHẬN TỪ BỘ CÔNG AN
	 */

	@RequestMapping(value = "/investigationConfirmedBcaList")
	public ModelAndView getInvestigationConfirmedBcaList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationConfirmedBcaList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getInvestigationConfirmedBcaList(
				investigationAssignmentData, request, httpRequest, model,
				pageNo, null, null);
	}

	public ModelAndView getInvestigationConfirmedBcaList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationConfirmedBcaList pageNo");
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
			pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
			//Phúc thêm lấy chi tiết danh sách theo mã
			List<NicTransactionPackage> listPack = transactionPackageService.getListPackageByPackageId(investigationAssignmentData.getListCode());
			String[] arrPack = new String[listPack.size()];
			for(int i = 0; i < listPack.size(); i++){
				arrPack[i] = listPack.get(i).getTransactionId();
			}
			// /TRUNG: Lấy dữ liệu danh sách đã xác thực từ bộ công an
			pr = uploadJobService.findAllForInvestigationPagination(
					new String[] { NicUploadJob.RECORD_STATUS_CONFIRMED_BCA },
					userSession.getUserId(),
					pageNo,
					pageSize,
					investigationAssignmentData.assignedOnly(),
					new AssignmentFilterAll(investigationAssignmentData
							.getSearchTransactionId(), investigationAssignmentData.getPriority(),investigationAssignmentData.getTransactionType(), 
							null, null,
							investigationAssignmentData.getRegSiteCode(), investigationAssignmentData.getPassportType(), investigationAssignmentData.getTypeInvestigation(), investigationAssignmentData.getPackageCode(), arrPack, null));

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
										if(nicTransaction.getPriority() == 0)
											record.setPriorityString("Thông thường");
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
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",0);
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

				model.addAttribute("fnSelected",Constants.HEADING_NIC_INVESTIGATION_BCA_DETAIl);
				model.addAttribute("listCode", investigationAssignmentData.getListCode());
				ModelAndView modelAndView = new ModelAndView("investigation.investigationBca.investigationConfirmedBcaList",searchResultMap);					
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",Constants.HEADING_NIC_INVESTIGATION_BCA_DETAIl);
				model.addAttribute("listCode", investigationAssignmentData.getListCode());
				ModelAndView modelAndView = new ModelAndView("investigation.investigationBca.investigationConfirmedBcaList",null);					
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/applyFilterConfirmed")
	public ModelAndView applyFilterConfirmed(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilterConfirmed");

		return this.getInvestigationConfirmedBcaList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}

	@RequestMapping(value = { "/startDetailInvestigationC/{jobId}/{listCode}" })
	public ModelAndView startDetailInvestigationC(@PathVariable long jobId, @PathVariable String listCode,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start Investigation Confirm BCA compare");

		return this.startDetailInvestigationC(jobId, listCode,httpRequest, model, null);
	}

	public ModelAndView startDetailInvestigationC(long jobId, String listCode,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Investigation Confirm BCA compare, listOfMessages");
		model.addAttribute("listCode", listCode);
		ModelAndView modelAndView = new ModelAndView(
				"investigationBca.startInvestigationConfirmedBca.compare");

		this.initializeModelAndViewForms(modelAndView);

		NicUploadJob jobDetails = uploadJobService.findById(jobId);
		String mainTransactionId = jobDetails.getTransactionId();

		// 05-02-2018: trung show img và thông tin cơ bản
		String photoStr = null;
		NicTransaction nicTransaction = null;

		nicTransaction = nicTransactionService.findById(jobDetails
				.getTransactionId());// TRung thêm
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
				.findNicTransactionAttachments(jobDetails.getTransactionId(),
						NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
						NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
		}
		//Phúc format lại ...
		if(nicTransaction.getNicRegistrationData().getDateOfBirth() != null){
			String dob = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getDateOfBirth());
			nicTransaction.setDateOfBirthDesc(dob);
			//nicTransaction.getNicRegistrationData().setDateOfBirth(HelperClass.convertStringToDate1(dob));			
		}
		if(nicTransaction.getNicRegistrationData().getCreateDatetime() != null){
			String crd = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getCreateDatetime());
			nicTransaction.setCreateDesc(crd);
			//nicTransaction.getNicRegistrationData().setCreateDatetime(HelperClass.convertStringToDate1(crd));			
		}
		if(nicTransaction.getNicRegistrationData().getReligion() != null){
			String religion = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_RELIGION_CODE_ID, nicTransaction.getNicRegistrationData().getReligion(), "");
			nicTransaction.setReligionDesc(religion);
		}
		if(nicTransaction.getNicRegistrationData().getNation()!= null){
			String nation = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_NATION_CODE_ID, nicTransaction.getNicRegistrationData().getNation(), "");
			nicTransaction.setNationDesc(nation);
		}
		modelAndView.addObject("photoStr", photoStr);
		modelAndView.addObject("nicData", nicTransaction);
		// end
		modelAndView.addObject("transID", mainTransactionId);
		modelAndView.addObject("jobsID", jobId);
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}

	// /Xử lý gửi yêu cầu sang bộ công an duyệt thông tin
	@RequestMapping(value = { "/approve_conf_bca" })
	public ModelAndView approve_conf_bca(
			@ModelAttribute(value = "investigationHitData") InvestigationAssignmentData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("approve_conf_bca");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			
			///Chuyển về danh sách Kiểm duyệt sơ bộ
			/*uploadJobService.approveJobStatus_Confirmed(
					Long.valueOf(investigationHitData.getCurrentlyAssignedToUserId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRM_BCA,
					NicUploadJob.RECORD_STATUS_APPROVED);*/
			NicWorkflowProcess objW = null;
			logger.info("Get data WorkflowProcess");
			List<NicWorkflowProcess> lstW = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.VERIFICATION);
			if(lstW != null && lstW.size() > 0){
				objW = new NicWorkflowProcess();
				objW = lstW.get(0);
				
					NicUploadJob obj = uploadJobService.findById(Long.valueOf(investigationHitData.getCurrentlyAssignedToUserId()));
					//Kiểm tra bước kế tiếp sau khi phân công
					switch (objW.getWorkflowEnd()) {
					case NicWorkflowProcess.PERSO:
						try {
							nicTransactionService.updateOnApprove(obj.getTransactionId(), "SYSTEM", "BAF-NIC-DEV");
							//Xử lý gán điểm in cho bản ghi
							NicTransaction nicTrans = nicTransactionService.findById(obj.getTransactionId());
							List<NicPersoLocations> listPerso = persoLocationsService.findAll();
							/*if(listPerso != null && listPerso.size() > 0){
								for(NicPersoLocations l : listPerso){
									if(!StringUtils.isEmpty(l.getIssuePlace())){
										String[] arraySite = l.getIssuePlace().split(",");
										boolean contains = java.util.Arrays.asList(arraySite).contains(nicTrans.getIssSiteCode());
										if(contains){
											nicTrans.setPrintPerso(l.getIdPerso());
											break;
										}
										else
										{
											nicTrans.setPrintPerso((long) 1);
										}
									}
								}
							}
							else
							{
								nicTrans.setPrintPerso((long) 1);
							}*/
							
							//Xử lý duyệt sang IN
							uploadJobService
									.approveJobStatus_Confirmed(
											Long.valueOf(obj.getWorkflowJobId()),
											"SYSTEM",
											"BAF-NIC-DEV",
											NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
											NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRM_BOSS,
											NicUploadJob.RECORD_STATUS_APPROVE_PERSO);
							
							nicTransactionService.saveOrUpdate(nicTrans);
							
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
						uploadJobService.updateJobState(Long.valueOf(investigationHitData.getCurrentlyAssignedToUserId()), NicUploadJob.RECORD_STATUS_IN_PROGRESS, 9);
						break;
					case NicWorkflowProcess.LEADERS_APPROVAL:
						uploadJobService.approveJobStatus_Confirmed(
								Long.valueOf(obj.getWorkflowJobId()),
								"SYSTEM",
								"BAF-NIC-DEV",
								NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
								NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
								NicUploadJob.RECORD_STATUS_CONFIRMED);
						break;
					default:
						logger.info("Not found case. Record will process default");
						uploadJobService.approveJobStatus_Confirmed(
								Long.valueOf(investigationHitData.getCurrentlyAssignedToUserId()),
								userSession.getUserName(), userSession.getWorkstationId(),
								NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
								NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRM_BCA,
								NicUploadJob.RECORD_STATUS_APPROVED);
						break;
					}
			
			}
			else
			{
				logger.info("Get data WorkflowProcess: NULL. Record will process default");
			}
			

			messages.add("Duyệt bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Duyệt bản ghi thất bại. \\nCông việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationBca/investigationConfirmedBcaList");
	}

	// /Xử lý từ chối bản ghi
	@RequestMapping(value = { "/reject_noHit_bca" })
	public ModelAndView reject_noHit_bca(
			@ModelAttribute(value = "investigationHitData") InvestigationAssignmentData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {

		logger.info("reject_noHit_bca");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.rejectJob(
					investigationHitData.getUnassignAllForUserUserId(),
					Long.valueOf(investigationHitData.getCurrentlyAssignedToUserId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicRejectionData.rejectReason_investigation,
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_REJECTED);

			messages.add("Đã từ chối bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			logger.info("rejection failed");
			messages.add("Từ chố bản ghi thất bại. \\nCông việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationBca/investigationConfirmedBcaList");
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

	// Xử lý tạo danh sách bàn giao
	@RequestMapping(value = { "/createHandover" })
	public ModelAndView createHandover(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model,
			@RequestParam("selectedJobIds") String[] checkboxvalues)
			throws Throwable {
		logger.info("createHandover");

		List<String> messages = new LinkedList<String>();
		List<String> listTranId = new ArrayList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			Date date = new Date();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			String listIdTrans = "";
			if (checkboxvalues != null) {
				for (String st : checkboxvalues) {
					listTranId.add(st);
					List<NicUploadJob> lstUpload = uploadJobService.findAllByTransactionId(st);
 
					uploadJobService
							.approveJobStatus_Confirmed(
									Long.valueOf(lstUpload.get(0)
											.getWorkflowJobId()),
									userSession.getUserName(),
									userSession.getWorkstationId(),
									NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
									NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRM_BCA,
									NicUploadJob.RECORD_STATUS_PENDING_BOSS);

					
					NicUploadJob nicU = new NicUploadJob();
					nicU = uploadJobService.findById(Long.valueOf(lstUpload.get(0)
							.getWorkflowJobId()));
					// Chuyển vào hàng đợi để gửi sang BNG chạy JOb
					QueuesJobSchedule obj = new QueuesJobSchedule();
					obj.setCode(nicU.getTransactionId());
					if (nicU.getNicTransaction() != null) {
						obj.setPassportType(nicU.getNicTransaction()
								.getPassportType());
						if (nicU.getNicTransaction().getNicRegistrationData() != null) {
							obj.setName(nicU.getNicTransaction()
									.getNicRegistrationData().getSurnameLine1());
						}
					}
					obj.setTypeTransaction(QueuesJobSchedule.TYPE_TRANSACTION_AUTHENTICATION);
					obj.setTypeLogJob(nicU.getJobType());
					obj.setStatus(QueuesJobSchedule.STATUS_JOB_NEW);
					obj.setDescription("Đang xử lý gửi dữ liệu Xác minh sang Bộ công an..");
					queuesJobScheduleService.createQueuesJobSchedule(obj);
					
					
					nicU.setValidateInfoBca(1);
					uploadJobService.saveOrUpdate(nicU);

					// Lưu id sang bảng danh sách bàn giao
					listIdTrans += st + ",";
				}

				if (!StringUtils.isEmpty(listIdTrans)) {
					String packId = "";
					Random ran = new Random();
					while(StringUtils.isBlank(packId)){
						String p = "RC-CQDD-" + ran.nextInt(99999999); 
						List<NicListHandover> check = listHandoverService.findAllByPackageId(new NicListHandoverId(p, null)).getListModel();
						if(check == null || check.size() <= 0){
							packId = p;
						}
					}
					
					NicListHandover handover = new NicListHandover();
					handover.setCreateBy(userSession.getUserId());
					handover.setCreateDate(date);
					handover.setId(new NicListHandoverId(packId, NicListHandover.TYPE_LIST_C));
//					handover.setPackageId(packId);
					//handover.setTransactionId(listIdTrans);
					handover.setPackageName(investigationHitData
							.getUnassignAllForUserUserId());
//					handover.setTypeList(NicListHandover.TYPE_LIST_C);
					//handover.setDescription("");
					listHandoverService.createRecordHandover(handover);
					// Thêm dữ liệu danh sách vào bảng trung gian phúc thêm 10/5
					for(String tranId : listTranId){
						NicTransactionPackage tranPackage = new NicTransactionPackage();
						tranPackage.setPackageId(packId);
						tranPackage.setTransactionId(tranId);
						transactionPackageService.insertDataTable(tranPackage);
					}
					messages.add("Tạo danh sách bàn giao sang Bộ công an thành công.");
					ModelAndView modelV = new ModelAndView("investigation.investigationBca.success");
					model.addAttribute("code", packId);
					return modelV;
				}
			} else {
				messages.add("Chưa có bản ghi nào được chọn.");
				httpRequest.setAttribute("messages", messages);
				return new ModelAndView(
						"forward:/servlet/investigationBca/investigationPendingBcaList");
			}
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Tạo danh sách bàn giao sang Bộ công an thất bại.\\n Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		investigationHitData = new InvestigationAssignmentData();
		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationBca/investigationPendingBcaList");
	}
	
	
}
