package com.nec.asia.nic.investigation.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.utils.HelperClass;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.util.CitizenRefXmlConvertor;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
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
@RequestMapping(value = "/investigationAssigned")
public class InvestigationAssignedController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(InvestigationAssignedController.class);

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
	private UserService userService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private ListHandoverService handoverService;

	@ModelAttribute("userAssignment")
	public Map<String, String> userAssignment() {
		List<Users> codeList = userService.findByRoles(RegistrationConstants.USERS_ID_BY_ROLES_CODE);
		Map<String, String> userAssignment = new LinkedHashMap<String, String>();
		List<Users> list = new ArrayList<>();
		//userAssignment.put("", "--Chọn--");
		if(codeList != null){
			for (Users ricCode : codeList) {
				Users users = userService.findByUserId(ricCode.getUserId());
				if(users != null)
					//userAssignment.put(users.getUserId(), users.getUserName());
					list.add(users);
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
	
	@ModelAttribute("listSiteRepository")
	public Map<String, String> listSiteRepository() {
		Map<String, String> list = new HashMap<String, String>();
		//list.put("", "--Chọn--");
		List<SiteRepository> listSite = siteService.findByAllGroup();
		for(SiteRepository sr : listSite){
			list.put(sr.getSiteId(), sr.getSiteName());
		}
		return list;
	}
	
	@ModelAttribute("transactionType")
	public Map<String, String> transactionType() {
		List<CodeValues> codeList = codesService.getAllCodeValues("TRANSACTION_TYPE", new String[] {"NEW", "RENEWAL", "LOST", "REG", "REP"});
		Map<String, String> transactionType = new LinkedHashMap<String, String>();
		transactionType.put("", "--Chọn--");
		for (CodeValues ricCode : codeList) {
			transactionType.put(ricCode.getId().getCodeValue(), ricCode.getCodeValueDesc());
		}
		return transactionType;
	}
	
	@ModelAttribute("passportType")
	public Map<String, String> passportType() {
		List<CodeValues> codeList = codesService.getAllCodeValues("PASSPORT_TYPE", new String[] {"P", "PD", "PO", "PE"});
		Map<String, String> passportType = new LinkedHashMap<String, String>();
		passportType.put("", "--Chọn--");
		for (CodeValues ricCode : codeList) {
			passportType.put(ricCode.getId().getCodeValue(), ricCode.getCodeValueDesc());
		}
		return passportType;
	}
	
	@ModelAttribute("priorityCode")
	public Map<String, String> priorityCode() {
		List<CodeValues> codeList = codesService.getAllCodeValues("PRIORITY", new String[] {"1", "2", "3"});
		Map<String, String> priorityCode = new LinkedHashMap<String, String>();
		priorityCode.put("", "--Chọn--");
		for (CodeValues ricCode : codeList) {
			priorityCode.put(ricCode.getId().getCodeValue(), ricCode.getCodeValueDesc());
		}
		return priorityCode;
	}
	
	
	@RequestMapping(value = "/investigationAssignedList")
	public ModelAndView investigationAssignedList(@ModelAttribute(value = "form") InvestigationAssignmentData investigationAssignmentData, WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		String ricId = !StringUtils.isEmpty(request.getParameter("ricId")) ? request.getParameter("ricId") : "";
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		investigationAssignmentData.setRegSiteCode(ricId);
		return this.investigationAssignedList(investigationAssignmentData, request, pageNo, httpRequest, model, "", null, null);
	}

	public ModelAndView investigationAssignedList(@ModelAttribute(value = "form") InvestigationAssignmentData investigationAssignmentData,WebRequest request, int pageNo, HttpServletRequest httpRequest, ModelMap model,
			String userIdInput, List<String> listOfMessages, List<String> listErrors) {
		logger.info("investigationAssignedList");
		PaginatedResult<NicUploadJobDto> pr = null;

		ModelAndView modelAndView = new ModelAndView("investigation.investigationAssigned.list");

		{
			String messages = "";

			if (listOfMessages != null) {
				for(String mess : listOfMessages){
					messages += mess;
				}
			}

			if (StringUtils.isNotEmpty(messages)) {
				model.addAttribute("thanhcong", messages);
			}
		}
		{
			String Errors = "";

			if (listErrors != null) {
				for(String err : listErrors){
					Errors += err;
				}
			}

			if (StringUtils.isNotEmpty(Errors)) {
				model.addAttribute("loi", Errors);
			}
		}

		if (investigationAssignmentData == null) {
			investigationAssignmentData = new InvestigationAssignmentData();
		}

		investigationAssignmentData.cleanUpStrings();
		
		modelAndView.addObject("userIdInput", userIdInput);

		try {
			//int pageNo = 1;
			int pageSize = 10;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>" + userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationAssignedController.DECENDING_ORDER;

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
			if (order.equalsIgnoreCase(InvestigationAssignedController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			Parameters parameter = parametersService.findById(id);

			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			//phúc thêm sử dụng bảng mới
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			String creDate = null;
			String endDate = null;
			//Date issDate = null;
			if (!StringUtils.isEmpty(investigationAssignmentData.getCreateDate())){
				creDate = HelperClass.convertMonthNumberToMonthWord1(investigationAssignmentData.getCreateDate());
			}else{
				investigationAssignmentData.setCreateDate(HelperClass.convertCalendarToStringVersion2(Calendar.getInstance()));
				creDate = HelperClass.convertMonthNumberToMonthWord1(investigationAssignmentData.getCreateDate());
			}
			if (!StringUtils.isEmpty(investigationAssignmentData.getEndDate())){
				endDate = HelperClass.convertMonthNumberToMonthWord1(investigationAssignmentData.getEndDate());
			}else{
				investigationAssignmentData.setEndDate(HelperClass.convertCalendarToStringVersion2(Calendar.getInstance()));
				endDate = HelperClass.convertMonthNumberToMonthWord1(investigationAssignmentData.getEndDate());
			}
//			String pageNumber = request
//					.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
//
//			if (StringUtils.isNotBlank(pageNumber) && StringUtils.isNumeric(pageNumber)) {
//				pageNo = Integer.parseInt(pageNumber);
//			}

			//pr = uploadJobService.findAllForInvestigationPagination(userIdInput, pageNo, pageSize, true);
//			pr   = uploadJobService.findAllForInvestigationPagination(
//			   new String[] { NicUploadJob.RECORD_STATUS_IN_PROGRESS }, investigationAssignmentData
//				.getTypeInvestigation(), pageNo, pageSize, true,
//				   new AssignmentFilterAll(investigationAssignmentData
//							.getSearchTransactionId(),
//							investigationAssignmentData.getPriority(),
//							investigationAssignmentData
//									.getTransactionType(), null, null,
//							investigationAssignmentData
//									.getRegSiteCode(),
//							investigationAssignmentData
//									.getPassportType(),
//							""));
			pr = handoverService.findListHandoverNoAssign(null, "1", investigationAssignmentData.getRegSiteCode(), 7, pageNo, pageSize, creDate, endDate);
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
							}
						}
					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu", 0);
				}
				modelAndView.addObject("form", investigationAssignmentData);
				modelAndView.addObject("totalRecords", pr.getTotal());
				//modelAndView.addObject("pageSize", pageSize);
				//modelAndView.addObject("jobList", list);
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
				modelAndView.addObject("fnSelected", Constants.HEADING_NIC_INVESTIGATION_ASSIGNED);
				return modelAndView;
			} else {
				modelAndView.addObject("form", new InvestigationAssignmentData());
				modelAndView.addObject("fnSelected", Constants.HEADING_NIC_INVESTIGATION_ASSIGNED);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/loadAssign")
	public String[] investigationAssignedList(WebRequest request, ModelMap model, @RequestParam String packageNo) {
		NicListHandover handover = handoverService.findListHandoverByOrther(packageNo, 7);
		if(handover != null){
			String users = handover.getProcessUsers();
			if(StringUtils.isNotEmpty(users) && users.contains(",")){
				String[] arrUsers = users.split(",");
				return arrUsers;
			}
		}
		return new String[0];
	}

	@RequestMapping(value = "/search/{searchUserIdInput}", method = RequestMethod.POST)
	public ModelAndView search(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest,
			@PathVariable("searchUserIdInput") String searchUserIdInput, ModelMap model) throws Throwable {
		logger.info("searchUserIdInput =========================>> (" + searchUserIdInput + ")");

		return this.investigationAssignedList(investigationAssignmentData, request, 1,httpRequest, model, searchUserIdInput, null, null);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Throwable {
		logger.info("searchUserIdInput =========================>> none");

		return this.investigationAssignedList(investigationAssignmentData, request, 1,httpRequest, model, "", null, null);
	}

	@RequestMapping(value = "/unassignJob/{jobId}", method = RequestMethod.POST)
	public ModelAndView unassignJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest,
			@PathVariable("jobId") long jobId, ModelMap model) throws Throwable {
		logger.info("unassignJob =========================>>jobId (" + jobId + ")");
		//logger.info("unassignJob =========================>>currentSearchUserIdInput (" + currentSearchUserIdInput + ")");

		try {
			UserSession userSession = (UserSession) httpRequest.getSession().getAttribute("userSession");
			uploadJobService.unassignInvestigationJob(jobId, userSession.getUserName(), userSession.getWorkstationId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		List<String> messages = new LinkedList<String>();
		messages.add("Bỏ giao công việc thành công.");

		return this.investigationAssignedList(investigationAssignmentData, request, 1,httpRequest, model, "",messages, null);
	}

	@RequestMapping(value = "/unassignAllJobsForUser/{unassignAllJobsForUserUserIdInput}", method = RequestMethod.POST)
	public ModelAndView unassignAllJobsForUser(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest,
			@PathVariable("unassignAllJobsForUserUserIdInput") String unassignAllJobsForUserUserIdInput, ModelMap model) throws Throwable {
		logger.info("unassignAllJobsForUser =========================>>unassignAllJobsForUserUserIdInput ("
				+ unassignAllJobsForUserUserIdInput + ")");
		//logger.info("unassignAllJobsForUser =========================>>currentSearchUserIdInput ("+ currentSearchUserIdInput + ")");

		try {
			UserSession userSession = (UserSession) httpRequest.getSession().getAttribute("userSession");
			uploadJobService.unassignInvestigationAllJobsForUser(unassignAllJobsForUserUserIdInput,
					userSession.getUserName(), userSession.getWorkstationId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		List<String> messages = new LinkedList<String>();
		messages.add("Bỏ tất cả các công việc được giao thành công.");

		return this.investigationAssignedList(investigationAssignmentData, request, 1,httpRequest, model, "", messages, null);
	}
	
	@RequestMapping(value = "/applyFilter1")
	public ModelAndView applyFilter1(
			@ModelAttribute(value = "form") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.investigationAssignedList(investigationAssignmentData, request, 1, httpRequest, model, null, null, null);
	}

	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}
	
	@RequestMapping(value = "/addAssign", method = RequestMethod.POST)
	public ModelAndView addingAssign(@ModelAttribute(value = "form") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Throwable {
		List<String> messages = new LinkedList<String>();
		List<String> errors = new LinkedList<String>();
		try {
			if (investigationAssignmentData.getSelectedJobIds() == null || investigationAssignmentData.getSelectedJobIds().length == 0) {
				throw new Exception();
			}
			for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
				ADUser user = userService.findUserByUserId(investigationAssignmentData.getSelectedJobIds()[i]);
				if (user == null) {
					throw new Exception();
				}				
			}
		} catch (Exception e) {
			errors.add("Thêm cán bộ không thành công.");			
			return this.investigationAssignedList(investigationAssignmentData, request, 1, httpRequest, model, "", messages, errors);
		}
		try {
			UserSession userSession = (UserSession) httpRequest.getSession().getAttribute("userSession");
//			List<Long> jodId = new ArrayList<Long>();
//			for(int i = 0; i < investigationAssignmentData.getPackageId().length; i++){
//				List<NicTransaction> listNic = nicTransactionService.getListTransactionByPackage(investigationAssignmentData.getPackageId()[i]);
//				for(NicTransaction nic : listNic){
//					List<NicUploadJob> jobList = uploadJobService.findAllByTransactionId(nic.getTransactionId());
//					for(NicUploadJob job : jobList){
//						jodId.add(job.getUploadJobId());
//					}
//				}
//			}
			//uploadJobService.assignInvestigationJobsUpdate(jodId, investigationAssignmentData.getSelectedJobIds(), userSession.getUserName(), userSession.getWorkstationId());
			handoverService.listHandoverUpdateAssignJob(investigationAssignmentData.getPackageId(), 7, null, investigationAssignmentData.getSelectedJobIds(), userSession.getUserName());
			messages.add("Phân công xử lý hồ sơ thành công.");
			return this.investigationAssignedList(investigationAssignmentData, request, 1, httpRequest, model, "", messages, errors);
			
		} catch (Exception e) {
			e.printStackTrace();
			errors.add("Phân công xử lý hồ sơ không thành công.");
			return this.investigationAssignedList(investigationAssignmentData, request, 1, httpRequest, model, "", messages, errors);
		}
		
	}
}
