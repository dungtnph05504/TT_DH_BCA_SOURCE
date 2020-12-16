package com.nec.asia.nic.investigation.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.nec.asia.nic.comp.listHandover.dao.PersoLocationsDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
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
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/investigationBoss")
public class InvestigationBossController extends InvestigationController {

	private static final Logger logger = LoggerFactory
			.getLogger(InvestigationBossController.class);

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
	private PersoLocationsService persoLocationService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private NicTransactionPackageService packageService;

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

	@RequestMapping(value = "/investigationPendingBossList/{listCode}")
	public ModelAndView getInvestigationPendingBossList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData, @PathVariable String listCode,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationPendingBossList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		investigationAssignmentData.setListCode(listCode);	
		return this.getInvestigationPendingBossList(
				investigationAssignmentData, request, httpRequest, model,
				pageNo, null, null);
	}
	
	
	@RequestMapping(value = "/investigationPendingBossList1")
	public ModelAndView getInvestigationPendingBossList1(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationPendingBossList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getInvestigationPendingBossList1(
				investigationAssignmentData, request, httpRequest, model,
				pageNo, null, null);
	}
	
	
	public ModelAndView getInvestigationPendingBossList1(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationPendingBossList pageNo");
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
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			Date creDate = null;
			if (!StringUtils.isEmpty(investigationAssignmentData.getCreateDate())){				
				String pattern = "dd-MMM-yyyy";
			    SimpleDateFormat format = new SimpleDateFormat(pattern);
			    creDate = format.parse(investigationAssignmentData.getCreateDate());
			}

			// /TRUNG: Lấy dữ liệu danh sách chờ XÉT DUYỆT từ lãnh đạo
//			pr = uploadJobService.findAllForInvestigationPagination(
//					new String[] { NicUploadJob.RECORD_STATUS_PENDING_BOSS,
//							NicUploadJob.RECORD_STATUS_CONFIRMED },
//					userSession.getUserId(),
//					pageNo,
//					pageSize,
//					investigationAssignmentData.assignedOnly(),
//					new AssignmentFilterAll(creDate, investigationAssignmentData.getRegSiteCode(), investigationAssignmentData.getListCode()));
            pr = uploadJobService.findAllForInvestigationPagination2(new String[] { NicUploadJob.RECORD_STATUS_PENDING_BOSS,
							NicUploadJob.RECORD_STATUS_CONFIRMED }, userSession.getUserId(), pageNo, pageSize, investigationAssignmentData.assignedOnly(), new AssignmentFilterAll(creDate, investigationAssignmentData.getRegSiteCode(), investigationAssignmentData.getListCode()));
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();

			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
//					for (NicUploadJobDto record : list) {
//						String transactionId = record.getTransactionId();
//						if (transactionId != null) {
//							NicTransaction nicTransaction = nicTransactionService.findById(transactionId);
//							if (nicTransaction != null) {
//
//							}
//						}

//					}
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

				model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION_BOSS);
				ModelAndView modelAndView = new ModelAndView("investigation.investigationBoss.investigationPendingBoss1List",searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",Constants.HEADING_NIC_INVESTIGATION_BOSS);
				ModelAndView modelAndView = new ModelAndView("investigation.investigationBoss.investigationPendingBoss1List",null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public ModelAndView getInvestigationPendingBossList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationPendingBossList pageNo");
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
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			//Phúc thêm điều kiện lây chi tiết theo mã
			List<NicTransactionPackage> listPack = packageService.getListPackageByPackageId(investigationAssignmentData.getListCode());
			String[] arrPack = new String[listPack.size()];
			for(int i = 0; i < listPack.size(); i++){
				arrPack[i] = listPack.get(i).getTransactionId();
			}
			// /TRUNG: Lấy dữ liệu danh sách chờ XÉT DUYỆT từ lãnh đạo
			pr = uploadJobService.findAllForInvestigationPagination(
					new String[] { NicUploadJob.RECORD_STATUS_PENDING_BOSS,
							NicUploadJob.RECORD_STATUS_CONFIRMED },
					userSession.getUserId(),
					pageNo,
					pageSize,
					investigationAssignmentData.assignedOnly(),
					new AssignmentFilterAll(investigationAssignmentData.getSearchTransactionId(), investigationAssignmentData.getPriority(),investigationAssignmentData.getTransactionType(), 
							null, null,
							investigationAssignmentData.getRegSiteCode(), investigationAssignmentData.getPassportType(), null, investigationAssignmentData.getPackageCode(), arrPack, null));

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
				model.addAttribute("listCode", investigationAssignmentData.getListCode());	
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_BOSS);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationBoss.investigationPendingBossList",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_BOSS);
				model.addAttribute("listCode", investigationAssignmentData.getListCode());	
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationBoss.investigationPendingBossList",
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

	@RequestMapping(value = { "/startDetailInvestigation/{jobId}/{listCode}" })
	public ModelAndView startinvestigationConfirmcompare(
			@PathVariable long jobId, @PathVariable String listCode, HttpServletRequest httpRequest,
			Model model) throws Throwable {
		logger.info("NIC Start Investigation Pending Boss compare");

		return this.startinvestigationConfirmcompare(jobId, listCode, httpRequest, model,
				null);
	}

	public ModelAndView startinvestigationConfirmcompare(long jobId, String listCode,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Investigation Pending Boss compare, listOfMessages");

		ModelAndView modelAndView = new ModelAndView(
				"investigation.investigationBoss.startInvestigationPendingBoss.compare");
		model.addAttribute("listCode", listCode);
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
		if (nicTransaction.getNicRegistrationData().getDateOfBirth() != null) {
			String dob = HelperClass.convertDateToString1(nicTransaction
					.getNicRegistrationData().getDateOfBirth());
			nicTransaction.setDateOfBirthDesc(dob);
			// nicTransaction.getNicRegistrationData().setDateOfBirth(HelperClass.convertStringToDate1(dob));
		}
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
			nicTransaction.setReligionDesc(religion);
		}
		if (nicTransaction.getNicRegistrationData().getNation() != null) {
			String nation = codesService.getCodeValueDescByIdName(
					RegistrationConstants.CODE_NATION_CODE_ID, nicTransaction
							.getNicRegistrationData().getNation(), "");
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
	
	@RequestMapping(value = "/applyFilterPending")
	public ModelAndView applyFilterPending(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilterPending");

		return this.getInvestigationPendingBossList(
				investigationAssignmentData, request, httpRequest, model, 1,
				null, null);
	}
	
	@RequestMapping(value = "/applyFilterPending1")
	public ModelAndView applyFilterPending1(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilterPending");

		return this.getInvestigationPendingBossList1(
				investigationAssignmentData, request, httpRequest, model, 1,
				null, null);
	}

	// /Xử lý lãnhh đạo duyệt hồ sơ
	@RequestMapping(value = { "/approve_conf_boss/{id}" })
	public ModelAndView approve_conf_boss(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData, @PathVariable String id,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("pending_conf_bca");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			NicUploadJob nicUp = uploadJobService.findById(Long.valueOf(investigationHitData.getUploadJobId()));
			NicTransaction nicTrans = nicTransactionService.findById(nicUp.getTransactionId());
			List<NicPersoLocations> listPerso = persoLocationService.findAll();
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
			uploadJobService.approveJobStatus_Confirmed(
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRM_BOSS,
					NicUploadJob.RECORD_STATUS_APPROVE_PERSO); /// ĐỔI SANG TRẠNG THÁI IN NGAY SAU KHI ĐƯỢC DUYỆT

			nicTransactionService.saveOrUpdate(nicTrans);
			
			messages.add("Duyệt bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Duyệt bản ghi thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			messages.add("Duyệt bản ghi thất bại.");
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationBoss/investigationPendingBossList/" + id);
	}

	// /Xử lý từ chối bản ghi
	@RequestMapping(value = { "/reject_noHitP/{id}" })
	public ModelAndView reject_noHitP(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData, @PathVariable String id,
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
				"forward:/servlet/investigationBoss/investigationPendingBossList/" + id);
	}

	/*
	 * ==========================================================================
	 * ========================
	 * 
	 * DANH SÁCH LÃNH ĐẠO ĐÃ DUYỆT==================================
	 * 
	 * ==========================================================================
	 * ========================
	 */

	@RequestMapping(value = "/investigationApproveBossList")
	public ModelAndView getInvestigationApproveBossList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getnvestigationApproveBossList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getInvestigationApproveBossList(
				investigationAssignmentData, request, httpRequest, model,
				pageNo, null, null);
	}

	public ModelAndView getInvestigationApproveBossList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationApproveBossList pageNo");
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

			// /TRUNG: Lấy dữ liệu danh sách đã XÉT DUYỆT
			pr = uploadJobService.findAllForInvestigationPagination(
					new String[] { NicUploadJob.RECORD_STATUS_APPROVE_BOSS },
					userSession.getUserId(),
					pageNo,
					pageSize,
					investigationAssignmentData.assignedOnly(),
					new AssignmentFilterAll(investigationAssignmentData.getSearchTransactionId(), investigationAssignmentData.getPriority(),investigationAssignmentData.getTransactionType(), 
							null, null,
							investigationAssignmentData.getRegSiteCode(), investigationAssignmentData.getPassportType(), null));

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
					request.setAttribute("jobDetailsErrorMsg", "No Data found",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);

				model.addAttribute("jobList", list);

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_BOSS);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationBoss.investigationApproveBossList",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_BOSS);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationBoss.investigationApproveBossList",
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

	@RequestMapping(value = "/applyFilterApprove")
	public ModelAndView applyFilterApprove(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilterPending");

		return this.getInvestigationApproveBossList(
				investigationAssignmentData, request, httpRequest, model, 1,
				null, null);
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
	
	

}
