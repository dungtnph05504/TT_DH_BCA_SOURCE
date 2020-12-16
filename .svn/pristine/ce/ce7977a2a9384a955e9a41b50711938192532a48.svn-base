/**
 * 
 */
package com.nec.asia.nic.queuesJobList.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverDetail;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.queuesJobSchedule.service.LogJobScheduleService;
import com.nec.asia.nic.comp.queuesJobSchedule.service.QueuesJobScheduleService;
import com.nec.asia.nic.comp.trans.domain.LogJobSchedule;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.investigation.controller.InvestigationAssignmentData;
/*import com.nec.asia.nic.pendingPassportNo.model.BaseResponseNIC;
import com.nec.asia.nic.pendingPassportNo.model.DataRegistrationLDS;
import com.nec.asia.nic.pendingPassportNo.model.PropertyModelNIC;*/
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.web.session.UserSession;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 27, 2013
 */

/*
 * Modification History:
 * 
 * 30 Dec 2013 (chris lai) : update with audit record
 */

@Controller
@RequestMapping(value = "/queuesJobListController")
public class QueuesJobListController extends AbstractController {

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;
	
	@Autowired
	private ParametersService parametersService;

	@Autowired
	private PaymentDefService paymentDefService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private PersoLocationsService persoLocationsService;

	@Autowired
	private QueuesJobScheduleService queuesJobScheduleService;
	
	@Autowired
	private LogJobScheduleService logJobScheduleService;

	@Autowired
	// @Qualifier(value="codesService")
	private CodesService codesService;

	// 30 Dec 2013 (chris lai) : update with audit record
	@Autowired
	private AuditAccessLogService auditAccessLogService;

	@Autowired
	private SiteRepositoryDao siteRepositoryDao;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(QueuesJobListController.class);

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

	// /======DANH SÁCH CHỜ Job xử lý========
	@RequestMapping(value = "/queuesJobList")
	public ModelAndView getQueuesJobList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getQueuesJobList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getQueuesJobList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getQueuesJobList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getQueuesJobList pageNo");
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

		PaginatedResult<QueuesJobSchedule> pr = null;
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
			pr = queuesJobScheduleService.findAllQueuesJobSchedule(null, "", pageNo, pageSize, null);

			List<QueuesJobSchedule> list = new ArrayList<QueuesJobSchedule>();

			if (pr != null && pr.getRowSize() > 0) {
				list = pr.getRows();
				if (list == null || list.size() <= 0) {
					request.setAttribute("jobDetailsErrorMsg", "Không có dữ liệu",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
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

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_PENDING_QUEUES_JOB_SCHEDULE);
				ModelAndView modelAndView = new ModelAndView(
						"pendingQueuesJobSchedule.list", searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_PENDING_QUEUES_JOB_SCHEDULE);
				ModelAndView modelAndView = new ModelAndView(
						"pendingQueuesJobSchedule.list", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/applyFilterQueuesJobList")
	public ModelAndView applyFilterQueuesJobList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getQueuesJobList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}

	public void initializeModelAndViewForms(ModelAndView mav) {
		mav.addObject("investigationHitData", new InvestigationHitData());
	}

	
	@RequestMapping(value = { "/startDetailQueuesJobList/{packId}" })
	public ModelAndView startDetailLogSchedulecompare(@PathVariable String packId,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start LogSchedule compare");
		int pageNo = !StringUtils.isEmpty(httpRequest.getParameter("pageNo")) ? Integer.parseInt(httpRequest.getParameter("pageNo")) : 1;
		return this.startLogSchedulecompare(packId, httpRequest, pageNo, model, null);
	}

	public ModelAndView startLogSchedulecompare(String packId,
			HttpServletRequest httpRequest, int pageNo, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start LogSchedule compare, listOfMessages");

		ModelAndView modelAndView = new ModelAndView("logSchedule.compare");
		this.initializeModelAndViewForms(modelAndView);
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		PaginatedResult<LogJobSchedule> jobDetails = logJobScheduleService.findListByCode1(packId, pageNo, pageSize);
		//phúc edit
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(jobDetails.getTotal(), pageSize));
		model.addAttribute("startIndex", jobDetails.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", jobDetails.getTotal());
		model.addAttribute("endIndex", firstResults + jobDetails.getRowSize());
		model.addAttribute("jobList", jobDetails.getRows());
		model.addAttribute("idPack", packId);
		//end	
		//model.addAttribute("jobList", jobDetails);
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}
	
	@RequestMapping(value = { "/listLogJobSchedule" })
	public ModelAndView listLogJobSchedule(@ModelAttribute(value = "formData") InvestigationAssignmentData log,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Throwable {
		logger.info("NIC Start LogSchedule List");
		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
										.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.listLogJobSchedule(log, httpRequest, request, model, pageNo, null);
	}
	
	@RequestMapping(value = "/applyFilter")
	public ModelAndView applyFilter(
			@ModelAttribute(value = "formData") InvestigationAssignmentData log,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		try {
			return this.listLogJobSchedule(log,
					httpRequest, request, model, 1, null);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ModelAndView listLogJobSchedule(@ModelAttribute(value = "formData") InvestigationAssignmentData log,
			HttpServletRequest httpRequest,WebRequest request, ModelMap model, int pageNo,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start LogSchedule List, listOfMessages");

		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
				+ userSession.getUserName());
		
		if (log == null) {
			log = new InvestigationAssignmentData();
		}
		
		log.cleanUpStrings();
		try{
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
			
			Date creDate = null;
			Date issDate = null;
			if (!StringUtils.isEmpty(log.getCreateDate())){
				
				String pattern = "dd-MMM-yyyy";
			    SimpleDateFormat format = new SimpleDateFormat(pattern);
			    creDate = format.parse(log.getCreateDate());
			}
			
			if (!StringUtils.isEmpty(log.getIssueDate())){
				
				String pattern = "dd-MMM-yyyy";
			    SimpleDateFormat format = new SimpleDateFormat(pattern);
			    issDate = format.parse(log.getIssueDate());
			}
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			PaginatedResult<LogJobSchedule> pr = 
					logJobScheduleService.findListByCriteria(pageNo, pageSize, 
							new AssignmentFilterAll(
							log.getSearchTransactionId(),
							null,
							log.getTransactionType(), creDate, issDate, log.getRegSiteCode(),
							log.getPassportType(),
							null
							));
			
			List<LogJobSchedule> list = new ArrayList<LogJobSchedule>();
			
			if (pr != null && pr.getRowSize() > 0) {
				list = pr.getRows();
				
				if (list == null || list.size() <= 0) {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",
							0);
				}
				else
				{
					for (LogJobSchedule logJobSchedule : list) {
						if(StringUtils.isNotEmpty(logJobSchedule.getDescription())){
							String des = logJobSchedule.getDescription().replace("'", "*").replace("\n", "").replace("\r", "").trim().replaceAll(" +", " ");
							logJobSchedule.setDescription(des);
						}
					}
				}
			}
			
			Map searchResultMap = new HashMap();
			searchResultMap.put("totalRecords", pr.getTotal());
			searchResultMap.put("pageSize", pageSize);
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
			List<String> messages = new ArrayList<String>();
	
			if (listOfMessages != null) {
				messages.addAll(listOfMessages);
			}
	
			if (CollectionUtils.isNotEmpty(messages)) {
				httpRequest.setAttribute("messages", messages);
			}
			
			ModelAndView modelAndView = new ModelAndView(
					"logSchedule.list",
					searchResultMap);
			log.cleanUpForNextPage();
			modelAndView.addObject("formData", log);
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
