package com.nec.asia.nic.investigation.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.util.CitizenRefXmlConvertor;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
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
@RequestMapping(value = "/fraudCaseManagementAssigned")
public class FraudCaseManagementAssignedController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(FraudCaseManagementAssignedController.class);

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

	@RequestMapping(value = "/fraudCaseManagementAssignedList")
	public ModelAndView investigationAssignedList(WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		return this.investigationAssignedList(request, httpRequest, model, "", null);
	}

	public ModelAndView investigationAssignedList(WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			String userIdInput, List<String> listOfMessages) {
		logger.info("investigationAssignedList");
		PaginatedResult<NicUploadJobDto> pr = null;

		ModelAndView modelAndView = new ModelAndView("fraudCaseManagement.fraudCaseManagementAssigned.list");

		{
			List<String> messages = new ArrayList<String>();

			if (listOfMessages != null) {
				messages.addAll(listOfMessages);
			}

			if (CollectionUtils.isNotEmpty(messages)) {
				httpRequest.setAttribute("messages", messages);
			}
		}

		modelAndView.addObject("userIdInput", userIdInput);

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
			String order = FraudCaseManagementAssignedController.DECENDING_ORDER;

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
			if (order.equalsIgnoreCase(FraudCaseManagementAssignedController.DECENDING_ORDER)) {
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

			pr = uploadJobService.findAllForInvestigationPagination(
					new String[] { NicUploadJob.RECORD_STATUS_SUSPENDED }, userIdInput, pageNo, pageSize, true);
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
					request.setAttribute("jobDetailsErrorMsg", "No Data found", 0);
				}
				modelAndView.addObject("totalRecords", pr.getTotal());
				modelAndView.addObject("pageSize", pageSize);
				modelAndView.addObject("jobList", list);
				modelAndView.addObject("fnSelected", Constants.HEADING_NIC_INVESTIGATION_SUSPENDED_ASSIGNED);
				return modelAndView;
			} else {
				modelAndView.addObject("fnSelected", Constants.HEADING_NIC_INVESTIGATION_SUSPENDED_ASSIGNED);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/search/{searchUserIdInput}", method = RequestMethod.POST)
	public ModelAndView search(WebRequest request, HttpServletRequest httpRequest,
			@PathVariable("searchUserIdInput") String searchUserIdInput, ModelMap model) throws Throwable {
		logger.info("searchUserIdInput =========================>> (" + searchUserIdInput + ")");

		return this.investigationAssignedList(request, httpRequest, model, searchUserIdInput, null);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Throwable {
		logger.info("searchUserIdInput =========================>> none");

		return this.investigationAssignedList(request, httpRequest, model, "", null);
	}

	@RequestMapping(value = "/unassignJob/{jobId}", method = RequestMethod.POST)
	public ModelAndView unassignJob(WebRequest request, HttpServletRequest httpRequest,
			@PathVariable("jobId") long jobId, ModelMap model,
			@RequestParam("search_data") String currentSearchUserIdInput) throws Throwable {
		logger.info("unassignJob =========================>>jobId (" + jobId + ")");
		logger.info(
				"unassignJob =========================>>currentSearchUserIdInput (" + currentSearchUserIdInput + ")");

		try {
			UserSession userSession = (UserSession) httpRequest.getSession().getAttribute("userSession");
			uploadJobService.unassignSuspendedInvestigationJob(jobId, userSession.getUserName(),
					userSession.getWorkstationId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		List<String> messages = new LinkedList<String>();
		messages.add("The investigation record was successfully unassigned.");

		return this.investigationAssignedList(request, httpRequest, model, currentSearchUserIdInput, messages);
	}

	@RequestMapping(value = "/unassignAllJobsForUser/{unassignAllJobsForUserUserIdInput}", method = RequestMethod.POST)
	public ModelAndView unassignAllJobsForUser(WebRequest request, HttpServletRequest httpRequest,
			@PathVariable("unassignAllJobsForUserUserIdInput") String unassignAllJobsForUserUserIdInput, ModelMap model,
			@RequestParam("search_data") String currentSearchUserIdInput) throws Throwable {
		logger.info("unassignAllJobsForUser =========================>>unassignAllJobsForUserUserIdInput ("
				+ unassignAllJobsForUserUserIdInput + ")");
		logger.info("unassignAllJobsForUser =========================>>currentSearchUserIdInput ("
				+ currentSearchUserIdInput + ")");

		try {
			UserSession userSession = (UserSession) httpRequest.getSession().getAttribute("userSession");
			uploadJobService.unassignSuspendedInvestigationAllJobsForUser(unassignAllJobsForUserUserIdInput,
					userSession.getUserName(), userSession.getWorkstationId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		List<String> messages = new LinkedList<String>();
		messages.add("All investigation records for the user was successfully unassigned.");

		return this.investigationAssignedList(request, httpRequest, model, currentSearchUserIdInput, messages);
	}

	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}
}
