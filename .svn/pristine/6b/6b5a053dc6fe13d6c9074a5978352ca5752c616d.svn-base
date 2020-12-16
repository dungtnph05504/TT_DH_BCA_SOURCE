package com.nec.asia.nic.investigation.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jcifs.util.transport.Request;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.admin.domain.User;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.utils.HelperClass;
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
import com.nec.asia.nic.investigation.dto.BusinessListDto;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/investigationAssignment")
public class InvestigationAssignmentController extends AbstractController {

	private static final Logger logger = LoggerFactory
			.getLogger(InvestigationAssignmentController.class);

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
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private ListHandoverService handoverService;

	@Autowired
	private NicTransactionPackageService transactionPackageService;

	@Autowired
	private NicRegistrationDataService registrationDataService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@ModelAttribute("userAssignment")
	public Map<String, String> userAssignment() {
		List<Users> codeList = userService
				.findByRoles(RegistrationConstants.USERS_ID_BY_ROLES_CODE);
		Map<String, String> userAssignment = new LinkedHashMap<String, String>();
		List<Users> list = new ArrayList<>();
		// userAssignment.put("", "--Chọn--");
		if (codeList != null) {
			for (Users ricCode : codeList) {
				Users users = userService.findByUserId(ricCode.getUserId());
				if (users != null)
					list.add(users);
				// userAssignment.put(users.getUserId(), users.getUserName());
			}
		}
		Collections.sort(list, new Comparator<Users>() {
			@Override
			public int compare(Users o1, Users o2) {
				// TODO Auto-generated method stub
				return o1.getUserName().compareTo(o2.getUserName());
			}

		});
		for (Users ricCode : list) {
			userAssignment.put(ricCode.getUserId(), ricCode.getUserName());
		}
		return userAssignment;
	}

	/*
	 * @ModelAttribute("listSiteRepository") public Map<String, String>
	 * listSiteRepository() throws DaoException { Map<String, String> list = new
	 * HashMap<String, String>(); //list.put("", "--Chọn--"); SiteGroups sg =
	 * siteService.getOnlyListByLevel("1"); //if(sg != null){
	 * List<SiteRepository> listSite = siteService.findByAllGroup();
	 * for(SiteRepository sr : listSite){ list.put(sr.getSiteId(),
	 * sr.getSiteName()); } //} return list; }
	 */

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

	@RequestMapping(value = "/investigationAssignmentList1")
	public ModelAndView getInvestigationAssignmentList1(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationAssignmentList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		String ricId = !StringUtils.isEmpty(request.getParameter("ricId")) ? request
				.getParameter("ricId") : "";
		String ricSelect = !StringUtils.isEmpty(request
				.getParameter("ricSelect")) ? request.getParameter("ricSelect")
				: "";
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		investigationAssignmentData.setRegSiteCode(ricId);
		investigationAssignmentData.setPassportType(ricSelect);
		return this.getInvestigationAssignmentListPackage(
				investigationAssignmentData, request, httpRequest, model,
				pageNo, null, null);
	}

	@RequestMapping(value = "/investigationAssignmentList")
	public ModelAndView getInvestigationAssignmentList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationAssignmentList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		return this.getInvestigationAssignmentList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);

	}

	@ResponseBody
	@RequestMapping(value = "/loadAssign")
	public String[] investigationAssignedList(WebRequest request,
			ModelMap model, @RequestParam String packageNo) {
		NicListHandover handover = handoverService.findListHandoverByOrther(
				packageNo, 7);
		if (handover != null) {
			String users = handover.getProcessUsers();
			if (StringUtils.isNotEmpty(users) && users.contains(",")) {
				String[] arrUsers = users.split(",");
				return arrUsers;
			}
		}
		return new String[0];
	}

	// Phúc thêm 4/5/2019
	@RequestMapping(value = "/investigationAssignmentDetail/{id}")
	public ModelAndView getInvestigationAssignmentDetail(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			@PathVariable String id) {
		logger.info("getInvestigationAssignmentList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		return this
				.getInvestigationAssignmentDetail(id, request, model, pageNo);
	}

	// Phúc thêm 4/5/2019
	public ModelAndView getInvestigationAssignmentDetail(String id,
			WebRequest request, ModelMap model, int pageNo) {
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
			// Phúc lấy all list khi thay bảng
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			pr = uploadJobService
					.findAllForInvestigationPaginationByPackageID(new String[] {
							NicUploadJob.RECORD_STATUS_INITIAL, null, "" }, id,
							pageNo, pageSize);
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();

			if (pr.getTotal() > 0 && pr.getRows().size() > 0) {
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
						String nameppt_ = "";
						nameppt_ = codesService.getCodeValueByIdName(
								"PASSPORT_TYPE", record.getPassportType())
								.getCodeValueDesc();
						record.setPassportType(nameppt_);
						if (record.getPriorityString().equals("0")) {
							String namep_ = "";
							namep_ = codesService.getCodeValueByIdName(
									"PRIORITY", "1").getCodeValueDesc();
							record.setPriorityString(namep_);
						}
					}
				} else {
					request.setAttribute("jobDetailsErrorMsg",
							"Không tìm thấy dữ liệu", 0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				// searchResultMap.put("pageSize", pageSize);

				// model.addAttribute("jobList", list);
				// phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
						* pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage",
						pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex",
						pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("jobList", list);
				model.addAttribute("packId", id);
				// end

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_ASSIGNMENT_DETAIL);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationAssignment.detail.list",
						searchResultMap);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_ASSIGNMENT_DETAIL);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationAssignment.detail.list",
						null);
				;
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Phúc thêm 4/5/2019
	public ModelAndView getInvestigationAssignmentListPackage(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationAssignmentList pageNo");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
				+ userSession.getUserName());

		{
			String messages = "";

			if (listOfMessages != null) {
				// messages.addAll(listOfMessages);
				for (String mess : listOfMessages) {
					messages += mess;
				}
			}

			if (StringUtils.isNotEmpty(messages)) {
				// httpRequest.setAttribute("messages", messages);
				model.addAttribute("thanhcong", messages);
			}
		}

		{
			String Errors = "";

			if (listOfErrors != null) {
				// Errors.addAll(listOfErrors);
				for (String err : listOfErrors) {
					Errors += err;
				}
			}

			if (StringUtils.isNotEmpty(Errors)) {
				// httpRequest.setAttribute("Errors", Errors);
				model.addAttribute("loi", Errors);
			}
		}

		if (investigationAssignmentData == null) {
			investigationAssignmentData = new InvestigationAssignmentData();
		}

		investigationAssignmentData.cleanUpStrings();
		String[] arraySite = null;
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			Map<String, String> listRIC = new HashMap<String, String>();
			String sAll = "";
			String sXnc = "";
			String sDp = "";
			String sBd = "";
			List<String> listS = new ArrayList<String>();

			List<SiteRepository> listSite = siteService.findByAllGroup();
			if (StringUtils.isNotEmpty(investigationAssignmentData
					.getPassportType())) {
				SiteGroups sg = siteService.getOnlyListByLevel("1");
				SiteGroups sg2 = siteService.getOnlyListByLevel("2");
				SiteGroups sg4 = siteService.getOnlyListByLevel("4");
				if (listSite != null && listSite.size() > 0) {
					switch (investigationAssignmentData.getPassportType()) {
					case "ALL":
						for (SiteRepository sr : listSite) {
							listRIC.put(sr.getSiteId(), sr.getSiteName());
						}
						sAll = "selected=\"selected\"";
						break;
					case "XNC":
						for (SiteRepository sr : listSite) {
							if ((sg != null && sg.getGroupId().equals(
									sr.getSiteGroups().getGroupId()))
									|| (sg2 != null && sg2.getGroupId().equals(
											sr.getSiteGroups().getGroupId()))) {
								listRIC.put(sr.getSiteId(), sr.getSiteName());
								listS.add(sr.getSiteId());
							}
						}
						arraySite = new String[listS.size()];
						for (int i = 0; i < listS.size(); i++) {
							arraySite[i] = listS.get(i);
						}
						sXnc = "selected=\"selected\"";
						break;
					case "DP":
						for (SiteRepository sr : listSite) {
							if (sg4 != null
									&& sg4.getGroupId().equals(
											sr.getSiteGroups().getGroupId())) {
								listRIC.put(sr.getSiteId(), sr.getSiteName());
								listS.add(sr.getSiteId());
							}
						}
						arraySite = new String[listS.size()];
						for (int i = 0; i < listS.size(); i++) {
							arraySite[i] = listS.get(i);
						}
						sDp = "selected=\"selected\"";
						break;
					case "BD":
						for (SiteRepository sr : listSite) {
							if (sg != null
									&& sg.getGroupId().equals(
											sr.getSiteGroups().getGroupId())) {
								listRIC.put(sr.getSiteId(), sr.getSiteName());
								listS.add(sr.getSiteId());
							}
						}
						arraySite = new String[listS.size()];
						for (int i = 0; i < listS.size(); i++) {
							arraySite[i] = listS.get(i);
						}
						sBd = "selected=\"selected\"";
						break;
					}
				}
			} else {
				if (listSite != null && listSite.size() > 0) {
					for (SiteRepository sr : listSite) {
						listRIC.put(sr.getSiteId(), sr.getSiteName());
					}
					sAll = "selected=\"selected\"";
				}
			}

			model.addAttribute("listSiteRepository", listRIC);
			model.addAttribute("sAll", sAll);
			model.addAttribute("sXnc", sXnc);
			model.addAttribute("sDp", sDp);
			model.addAttribute("sBd", sBd);
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
			// Phúc lấy all list khi thay bảng
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			String creDate = null;
			String endDate = null;
			// Date issDate = null;
			if (!StringUtils.isEmpty(investigationAssignmentData
					.getCreateDate())) {
				creDate = HelperClass
						.convertMonthNumberToMonthWord1(investigationAssignmentData
								.getCreateDate());
			} else {
				investigationAssignmentData
						.setCreateDate(HelperClass
								.convertCalendarToStringVersion2(Calendar
										.getInstance()));
				creDate = HelperClass
						.convertMonthNumberToMonthWord1(investigationAssignmentData
								.getCreateDate());
			}
			if (!StringUtils.isEmpty(investigationAssignmentData.getEndDate())) {
				endDate = HelperClass
						.convertMonthNumberToMonthWord1(investigationAssignmentData
								.getEndDate());
			} else {
				investigationAssignmentData
						.setEndDate(HelperClass
								.convertCalendarToStringVersion2(Calendar
										.getInstance()));
				endDate = HelperClass
						.convertMonthNumberToMonthWord1(investigationAssignmentData
								.getEndDate());
			}

			// pr =
			// uploadJobService.findAllForInvestigationPaginationByNullOfficerPackage(
			// new String[] { NicUploadJob.RECORD_STATUS_INITIAL,
			// NicUploadJob.RECORD_STATUS_IN_PROGRESS, null, "" },
			// investigationAssignmentData.getCurrentlyAssignedToUserId(),
			// pageNo, pageSize,
			// investigationAssignmentData.assignedOnly(),
			// new AssignmentFilterAll(creDate,
			// investigationAssignmentData.getRegSiteCode(),
			// investigationAssignmentData.getPackageCode()));
			pr = handoverService.findListHandoverNoAssign(arraySite,
					investigationAssignmentData.getTypeInvestigation(),
					investigationAssignmentData.getRegSiteCode(), 7, pageNo,
					pageSize, creDate, endDate);
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();

			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
				} else {
					request.setAttribute("jobDetailsErrorMsg",
							"Không tìm thấy dữ liệu", 0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				// searchResultMap.put("pageSize", pageSize);
				// phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
						* pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage",
						pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex",
						pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("jobList", list);
				// end
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_ASSIGNMENT);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationAssignment.list1",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_ASSIGNMENT);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationAssignment.list1", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ModelAndView getInvestigationAssignmentList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationAssignmentList pageNo");
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
			/* Phúc sửa lại hiển thị bảng */
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			// pr = uploadJobService.findAllForInvestigationPagination(
			// investigationAssignmentData.getCurrentlyAssignedToUserId(),
			// pageNo, pageSize,
			// investigationAssignmentData.assignedOnly(),
			// new
			// AssignmentFilter(investigationAssignmentData.getSearchTransactionId()));

			/*
			 * pr = uploadJobService.findAllForInvestigationPagination( new
			 * String[] { NicUploadJob.RECORD_STATUS_INITIAL,
			 * NicUploadJob.RECORD_STATUS_IN_PROGRESS },
			 * investigationAssignmentData.getCurrentlyAssignedToUserId(),
			 * pageNo, pageSize, investigationAssignmentData.assignedOnly(), new
			 * AssignmentFilter
			 * (investigationAssignmentData.getSearchTransactionId()));
			 */
			Date creDate = null;
			// Date issDate = null;
			if (!StringUtils.isEmpty(investigationAssignmentData
					.getCreateDate())) {

				String pattern = "dd-MMM-yyyy";
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				creDate = format.parse(investigationAssignmentData
						.getCreateDate());
			}

			pr = uploadJobService
					.findAllForInvestigationPaginationByNullOfficer(
							new String[] { NicUploadJob.RECORD_STATUS_INITIAL,
									NicUploadJob.RECORD_STATUS_IN_PROGRESS,
									null, "" },
							// new String[] {
							// NicUploadJob.RECORD_STATUS_INITIAL,
							// NicUploadJob.RECORD_STATUS_IN_PROGRESS},
							investigationAssignmentData
									.getCurrentlyAssignedToUserId(),
							pageNo,
							pageSize,
							investigationAssignmentData.assignedOnly(),
							new AssignmentFilterAll(investigationAssignmentData
									.getSearchTransactionId(),
									investigationAssignmentData.getPriority(),
									investigationAssignmentData
											.getTransactionType(), creDate,
									null, investigationAssignmentData
											.getRegSiteCode(),
									investigationAssignmentData
											.getPassportType(), null,
									investigationAssignmentData
											.getPackageCode()));

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
						String nameppt_ = "";
						nameppt_ = codesService.getCodeValueByIdName(
								"PASSPORT_TYPE", record.getPassportType())
								.getCodeValueDesc();
						record.setPassportType(nameppt_);
						if (record.getPriorityString().equals("0")) {
							String namep_ = "";
							namep_ = codesService.getCodeValueByIdName(
									"PRIORITY", "1").getCodeValueDesc();
							record.setPriorityString(namep_);
						}
					}
				} else {
					request.setAttribute("jobDetailsErrorMsg",
							"Không tìm thấy dữ liệu", 0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				// phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
						* pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage",
						pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex",
						pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("jobList", list);
				// end
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_ASSIGNMENT);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationAssignment.list",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_ASSIGNMENT);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationAssignment.list", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/applyFilter")
	public ModelAndView applyFilter(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getInvestigationAssignmentList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}

	@RequestMapping(value = "/applyFilter1")
	public ModelAndView applyFilter1(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getInvestigationAssignmentListPackage(
				investigationAssignmentData, request, httpRequest, model, 1,
				null, null);
	}

	@RequestMapping(value = "/unassign")
	public ModelAndView unassign(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("unassign");

		investigationAssignmentData.cleanUpStrings();

		List<String> messages = new LinkedList<String>();
		List<String> Errors = new LinkedList<String>();
		try {
			UserSession userSession = (UserSession) httpRequest.getSession()
					.getAttribute("userSession");
			uploadJobService.unassignInvestigationJobs(
					investigationAssignmentData.getSelectedJobIds_long(),
					userSession.getUserName(), userSession.getWorkstationId());
			messages.add("Phân công xử lý hồ sơ thành công.");
		} catch (Exception e) {
			e.printStackTrace();
			Errors.add("Phân công xử lý hồ sơ không thành công. Điều này có thể xảy ra khi một công việc đã thay đổi. Vui lòng thử lại.");
		}

		return this.getInvestigationAssignmentList(investigationAssignmentData,
				request, httpRequest, model, 1, messages, Errors);
	}

	@RequestMapping(value = "/assign1")
	public ModelAndView assign1(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("assign");

		investigationAssignmentData.cleanUpStrings();

		List<String> messages = new LinkedList<String>();
		List<String> Errors = new LinkedList<String>();
		List<String> assigns = new LinkedList<String>();

		try {
			if (investigationAssignmentData.getSelectedJobIds() == null
					|| investigationAssignmentData.getSelectedJobIds().length == 0) {
				throw new Exception();
			}
			for (int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++) {
				ADUser user = userService
						.findUserByUserId(investigationAssignmentData
								.getSelectedJobIds()[i]);
				if (user == null) {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			Errors.add("Phân công cán bộ không thành công.");
			// Phúc đóng 5/6/2019
			// return
			// this.getInvestigationAssignmentList(investigationAssignmentData,
			// request, httpRequest, model, 1,
			// messages, Errors);
			return this.getInvestigationAssignmentListPackage(
					investigationAssignmentData, request, httpRequest, model,
					1, messages, Errors);
		}

		try {
			List<String> listP = new ArrayList<String>();
			// Kiểm tra điều kiện phân công
			for (int i = 0; i < investigationAssignmentData.getPackageId().length; i++) {
				List<NicTransaction> listNic = nicTransactionService
						.getListTransactionByPackage(investigationAssignmentData
								.getPackageId()[i]);
				for (NicTransaction nic : listNic) {
					List<NicUploadJob> jobList = uploadJobService
							.findAllByTransactionId(nic.getTransactionId());
					// for(NicUploadJob job : jobList){
					if (jobList.get(0).getInvestigationStatus() == null) {
						listP.add(investigationAssignmentData.getPackageId()[i]);
						break;
					}
					// }
				}
			}
			// Phúc thêm code xử lý phân công theo phiếu bàn giao
			List<Long> jodId = new ArrayList<Long>();
			for (int i = 0; i < investigationAssignmentData.getPackageId().length; i++) {
				boolean check = this.checkPackage(listP,
						investigationAssignmentData.getPackageId()[i]);
				if (!check)
					continue;
				List<NicTransaction> listNic = nicTransactionService
						.getListTransactionByPackage(investigationAssignmentData
								.getPackageId()[i]);
				for (NicTransaction nic : listNic) {
					List<NicUploadJob> jobList = uploadJobService
							.findAllByTransactionId(nic.getTransactionId());
					for (NicUploadJob job : jobList) {
						// jodId.add(job.getUploadJobId());
					}
				}
			}
			UserSession userSession = (UserSession) httpRequest.getSession()
					.getAttribute("userSession");
			// uploadJobService.assignInvestigationJobs(investigationAssignmentData.getSelectedJobIds_long(),
			// investigationAssignmentData.getAssignToId(),
			// userSession.getUserName(),
			// userSession.getWorkstationId());
			uploadJobService.assignInvestigationJobs1(jodId,
					investigationAssignmentData.getSelectedJobIds(),
					userSession.getUserName(), userSession.getWorkstationId());
			if (listP.size() == 0) {
				messages.add("Phân công xử lý hồ sơ thành công.");
			} else {
				// String ass = "Hồ sơ";
				// for(String mes : listP){

				// }
			}
			handoverService.listHandoverUpdateAssignJob(
					investigationAssignmentData.getPackageId(), 7, null,
					investigationAssignmentData.getSelectedJobIds(),
					userSession.getUserName());
			// return
			// this.getInvestigationAssignmentList(investigationAssignmentData,
			// request, httpRequest, model, 1,
			// messages, Errors);
			// for(int i = 0; i <
			// investigationAssignmentData.getPackageId().length; i++){
			// List<NicTransaction> listTran =
			// nicTransactionService.getListTransactionByPackage(investigationAssignmentData.getPackageId()[i]);
			// for(NicTransaction tran : listTran){
			// NicTransactionPackage tp = new NicTransactionPackage();
			// tp.setTransactionId(tran.getTransactionId());
			// tp.setPackageId(investigationAssignmentData.getPackageId()[i]);
			// transactionPackageService.insertDataTable(tp);
			// }
			// }
			return this.getInvestigationAssignmentListPackage(
					investigationAssignmentData, request, httpRequest, model,
					1, messages, Errors);
		} catch (Exception e) {
			e.printStackTrace();
			Errors.add("Phân công xử lý hồ sơ không thành công.");
			// return
			// this.getInvestigationAssignmentList(investigationAssignmentData,
			// request, httpRequest, model, 1,
			// messages, Errors);
			return this.getInvestigationAssignmentListPackage(
					investigationAssignmentData, request, httpRequest, model,
					1, messages, Errors);
		}

	}

	public boolean checkPackage(List<String> list, String id) {
		for (String packageId : list) {
			if (packageId.equals(id)) {
				return false;
			}
		}
		return true;
	}

	@RequestMapping(value = "/assign")
	public ModelAndView assign(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("assign");

		investigationAssignmentData.cleanUpStrings();

		List<String> messages = new LinkedList<String>();
		List<String> Errors = new LinkedList<String>();

		try {
			if (StringUtils
					.isBlank(investigationAssignmentData.getAssignToId())) {
				throw new Exception();
			}
			ADUser user = userService
					.findUserByUserId(investigationAssignmentData
							.getAssignToId());
			if (user == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			Errors.add("Không hợp lệ");
			return this.getInvestigationAssignmentList(
					investigationAssignmentData, request, httpRequest, model,
					1, messages, Errors);
		}

		try {
			UserSession userSession = (UserSession) httpRequest.getSession()
					.getAttribute("userSession");
			uploadJobService.assignInvestigationJobs(
					investigationAssignmentData.getSelectedJobIds_long(),
					investigationAssignmentData.getAssignToId(),
					userSession.getUserName(), userSession.getWorkstationId());
			messages.add("Phân công xử lý hồ sơ thành công.");
			return this.getInvestigationAssignmentList(
					investigationAssignmentData, request, httpRequest, model,
					1, messages, Errors);
		} catch (Exception e) {
			e.printStackTrace();
			Errors.add("Phân công xử lý hồ sơ không thành công. Điều này có thể xảy ra khi một công việc đã thay đổi. Vui lòng thử lại");
			return this.getInvestigationAssignmentList(
					investigationAssignmentData, request, httpRequest, model,
					1, messages, Errors);
		}

	}

	@RequestMapping(value = "/unassignAllForUser")
	public ModelAndView unassignAllForUser(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("unassignAllForUser");

		investigationAssignmentData.cleanUpStrings();

		List<String> messages = new LinkedList<String>();
		List<String> Errors = new LinkedList<String>();

		try {
			if (StringUtils.isBlank(investigationAssignmentData
					.getUnassignAllForUserUserId())) {
				throw new Exception();
			}
			ADUser user = userService
					.findUserByUserId(investigationAssignmentData
							.getUnassignAllForUserUserId());
			if (user == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			Errors.add("Không hợp lệ, hồ sơ đã được phân công.");
			return this.getInvestigationAssignmentList(
					investigationAssignmentData, request, httpRequest, model,
					1, messages, Errors);
		}

		try {
			UserSession userSession = (UserSession) httpRequest.getSession()
					.getAttribute("userSession");
			uploadJobService.unassignInvestigationAllJobsForUser(
					investigationAssignmentData.getUnassignAllForUserUserId(),
					userSession.getUserName(), userSession.getWorkstationId());
			messages.add("Bỏ phân công xử lý hồ sơ thành công.");
			return this.getInvestigationAssignmentList(
					investigationAssignmentData, request, httpRequest, model,
					1, messages, Errors);
		} catch (Exception e) {
			e.printStackTrace();
			Errors.add("Bỏ phân công xử lý hồ sơ thành công.");
			return this.getInvestigationAssignmentList(
					investigationAssignmentData, request, httpRequest, model,
					1, messages, Errors);
		}

	}

	@ResponseBody
	@RequestMapping(value = "/getListRic/{keyB}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getListRic(@PathVariable String keyB) throws Exception {
		try {
			String result = "";
			SiteGroups sg = siteService.getOnlyListByLevel("1");
			SiteGroups sg2 = siteService.getOnlyListByLevel("2");
			SiteGroups sg4 = siteService.getOnlyListByLevel("4");
			List<SiteRepository> listSite = siteService.findByAllGroup();
			switch (keyB) {
			case "ALL":
				for (SiteRepository sr : listSite) {
					result += "<tr id=\"" + sr.getSiteId() + "\"><td>"
							+ sr.getSiteId() + "</td><td>" + sr.getSiteName()
							+ "</td></tr>";
				}
				return result;

			case "XNC":
				for (SiteRepository sr : listSite) {
					if (sg.getGroupId().equals(sr.getSiteGroups().getGroupId())
							|| (sg2 != null && sg2.getGroupId().equals(
									sr.getSiteGroups().getGroupId())))
						result += "<tr id=\"" + sr.getSiteId() + "\"><td>"
								+ sr.getSiteId() + "</td><td>"
								+ sr.getSiteName() + "</td></tr>";
				}
				return result;

			case "DP":
				for (SiteRepository sr : listSite) {
					if (sg4 != null
							&& sg4.getGroupId().equals(
									sr.getSiteGroups().getGroupId()))
						result += "<tr id=\"" + sr.getSiteId() + "\"><td>"
								+ sr.getSiteId() + "</td><td>"
								+ sr.getSiteName() + "</td></tr>";
				}
				return result;

			case "BD":
				for (SiteRepository sr : listSite) {
					if (sg.getGroupId().equals(sr.getSiteGroups().getGroupId()))
						result += "<tr id=\"" + sr.getSiteId() + "\"><td>"
								+ sr.getSiteId() + "</td><td>"
								+ sr.getSiteName() + "</td></tr>";
				}
				return result;
			}
		} catch (Exception e) {

		}
		return "error";
	}

	@ResponseBody
	@RequestMapping(value = "/showDetailPackage")
	public ModelAndView showDetailPackage(@RequestParam String packageNo,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("investigation-package");
		List<BusinessListDto> listDS = new ArrayList<BusinessListDto>();
		BusinessListDto dto = new BusinessListDto();
		try {
			List<NicListHandover> list = handoverService.findAllByPackageId(
					new NicListHandoverId(packageNo, null)).getListModel();
			if (CollectionUtils.isNotEmpty(list)) {
				// String[] arr = null;//
				// list.get(0).getTransactionId().split(",");
				// if (arr != null) {
				// for (int i = 0; i < arr.length; i++) {
				// BusinessListDto sub = new BusinessListDto();
				// sub.setTransactionId(arr[i]);
				// sub.setRecieptNo(packageNo);
				// NicRegistrationData regData = registrationDataService
				// .findById(arr[i]);
				// if (regData != null) {
				// sub.setFullName(HelperClass.createFullName(
				// regData.getSurnameFull(),
				// regData.getMiddlenameFull(),
				// regData.getFirstnameFull()));
				// sub.setGender("M".equals(regData.getGender()) ? "Nam"
				// : "Nữ");
				// String date = HelperClass
				// .convertDateToString(regData
				// .getDateOfBirth());
				// sub.setDob(com.nec.asia.nic.utils.HelperClass
				// .loadDateOfBirth(date,
				// regData.getDefDateOfBirth()));
				// //
				// sub.setDob(HelperClass.convertDateToString(regData.getDateOfBirth()));
				//
				// }
				// listDS.add(sub);
				// if (i == 0) {
				// NicTransaction txn = nicTransactionService
				// .findById(arr[0]);
				// // RicTransaction txn =
				// // transactionService.findById(arr[0]);
				// dto.setCreateDateDS(HelperClass
				// .convertDateToString(list.get(0)
				// .getCreateDate()));
				// dto.setNin(txn.getNin());
				// String noiSinh = codesService
				// .getCodeValueDescByIdName("DISTRICT",
				// regData.getPlaceOfBirth(), "");
				// if (StringUtils.isNotEmpty(noiSinh)) {
				// dto.setPob(noiSinh);
				// } else {
				// dto.setPob(regData.getPlaceOfBirth());
				// }
				// // dto.setAddress("");
				// List<NicTransactionAttachment> photoList =
				// nicTransactionAttachmentService
				// .findNicTransactionAttachments(
				// arr[0],
				// NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
				// NicTransactionAttachment.DEFAULT_SERIAL_NO)
				// .getListModel();
				// if (CollectionUtils.isNotEmpty(photoList)
				// && photoList.size() > 0) {
				// dto.setImgFacial(Base64
				// .encodeBase64String(photoList.get(0)
				// .getDocData()));
				// } else {
				// dto.setImgFacial("");
				// }
				// String[] excludedDocTypes = {
				// NicTransactionAttachment.KEY_SC_DSIGNER,
				// NicTransactionAttachment.KEY_SCAN_DOCUMENT, };
				//
				// List<NicTransactionAttachment> nicTransactionDocuments =
				// nicTransactionAttachmentService
				// .getNicTransactionAttachments(arr[0],
				// excludedDocTypes, null);
				// if (CollectionUtils
				// .isNotEmpty(nicTransactionDocuments)) {
				// for (NicTransactionAttachment nicTransProofDocument :
				// nicTransactionDocuments) {
				// // RicRegistrationDocumentDTO proofDoc = new
				// // RicRegistrationDocumentDTO();
				// String decodedDocString = Base64
				// .encodeBase64String(nicTransProofDocument
				// .getDocData());
				//
				// dto.setImgDoc(decodedDocString);
				// }
				// } else {
				// dto.setImgDoc("");
				// }
				// // String pob =
				// //
				// codeValueService.getCodeForCodeValueNCodeId(regData.getPlaceOfBirth(),
				// // RegistrationConstants.CODE_PLACE_ID).getDescription();
				// // if(StringUtils.isNotEmpty(pob)){
				// // dto.setPob(pob);
				// // }else{
				// // dto.setPob(regData.getPlaceOfBirth());
				// // }
				// String address = regData.getAddressLine1();
				// String ht = codesService.getCodeValueDescByIdName(
				// RegistrationConstants.CODE_TOWN,
				// regData.getAddressLine4(), "");
				// String tp = codesService.getCodeValueDescByIdName(
				// RegistrationConstants.CODE_DISTRICT,
				// regData.getAddressLine5(), "");
				// if (StringUtils.isNotEmpty(ht)) {
				// address += ", " + ht;
				// }
				// if (StringUtils.isNotEmpty(tp)) {
				// address += ", " + tp;
				// }
				// dto.setAddress(address);
				// }
				// }
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// mav.addObject("packageNo", packageNo);
		mav.addObject("dataRoot", dto);
		mav.addObject("dsChiTiet", listDS);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/clickTransaction/{transactionId}")
	public BusinessListDto clickTransaction(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		BusinessListDto dto = new BusinessListDto();
		try {
			NicTransaction txn = nicTransactionService.findById(transactionId);
			NicRegistrationData regData = registrationDataService
					.findById(transactionId);
			// dto.setCreateDateDS(HelperClass.convertDateToString(list.get(0).getCreateDate()));
			dto.setNin(txn.getNin());
			// dto.setPob(regData.getPlaceOfBirth());
			String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT",
					regData.getPlaceOfBirth(), "");
			if (StringUtils.isNotEmpty(noiSinh)) {
				dto.setPob(noiSinh);
			} else {
				dto.setPob(regData.getPlaceOfBirth());
			}
			// dto.setAddress("");
			List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
					.findNicTransactionAttachments(transactionId,
							NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
							NicTransactionAttachment.DEFAULT_SERIAL_NO)
					.getListModel();
			if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
				dto.setImgFacial("<img src=\'data:image/jpg;base64,"
						+ Base64.encodeBase64String(photoList.get(0)
								.getDocData())
						+ "' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />");
			} else {
				dto.setImgFacial("<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/eppcentral/resources/images/No_Image.jpg\">");
			}
			String[] excludedDocTypes = {
					NicTransactionAttachment.KEY_SC_DSIGNER,
					NicTransactionAttachment.KEY_SCAN_DOCUMENT, };

			List<NicTransactionAttachment> nicTransactionDocuments = nicTransactionAttachmentService
					.getNicTransactionAttachments(transactionId,
							excludedDocTypes, null);
			if (CollectionUtils.isNotEmpty(nicTransactionDocuments)) {
				for (NicTransactionAttachment nicTransProofDocument : nicTransactionDocuments) {
					// RicRegistrationDocumentDTO proofDoc = new
					// RicRegistrationDocumentDTO();
					String decodedDocString = Base64
							.encodeBase64String(nicTransProofDocument
									.getDocData());
					dto.setImgDoc("data:image/jpg;base64," + decodedDocString);
					// dto.setImgDoc("<img src=\'data:image/jpg;base64,"+
					// decodedDocString
					// +"' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />");
				}
			} else {
				dto.setImgDoc("/eppcentral/resources/images/No_Image.jpg");
				// dto.setImgDoc("<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/eppcentral/resources/images/No_Image.jpg\">");
			}
			// String pob =
			// codeValueService.getCodeForCodeValueNCodeId(regData.getPlaceOfBirth(),
			// RegistrationConstants.CODE_PLACE_ID).getDescription();
			// if(StringUtils.isNotEmpty(pob)){
			// dto.setPob(pob);
			// }else{
			// dto.setPob(regData.getPlaceOfBirth());
			// }
			String address = regData.getAddressLine1();
			String ht = codesService.getCodeValueDescByIdName(
					RegistrationConstants.CODE_TOWN, regData.getAddressLine4(),
					"");
			String tp = codesService.getCodeValueDescByIdName(
					RegistrationConstants.CODE_DISTRICT,
					regData.getAddressLine5(), "");
			if (StringUtils.isNotEmpty(ht)) {
				address += ", " + ht;
			}
			if (StringUtils.isNotEmpty(tp)) {
				address += ", " + tp;
			}
			dto.setAddress(address);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
}
