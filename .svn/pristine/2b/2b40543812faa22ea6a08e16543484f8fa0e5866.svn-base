package com.nec.asia.nic.investigation.controller;

import java.util.ArrayList;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/fraudCaseManagementAssignment")
public class FraudCaseManagementAssignmentController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(FraudCaseManagementAssignmentController.class);

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

	@RequestMapping(value = "/fraudCaseManagementAssignmentList")
	public ModelAndView getInvestigationAssignmentList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationAssignmentList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request
				.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber) && StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getInvestigationAssignmentList(investigationAssignmentData, request, httpRequest, model, pageNo,
				null, null);
	}

	public ModelAndView getInvestigationAssignmentList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model, int pageNo, List<String> listOfMessages,
			List<String> listOfErrors) {
		logger.info("getInvestigationAssignmentList pageNo");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		logger.info("The Officer Id or User Id is ===============>>>>>>>>>>" + userSession.getUserName());

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

			if (listOfErrors != null) {
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

			Parameters parameter = parametersService.findById(new ParametersId(Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}

			pr = uploadJobService.findAllForInvestigationPagination(
					new String[] { NicUploadJob.RECORD_STATUS_SUSPENDED },
					investigationAssignmentData.getCurrentlyAssignedToUserId(), pageNo, pageSize,
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
							NicTransaction nicTransaction = nicTransactionService.findById(transactionId);
							if (nicTransaction != null) {
								record.setDateOfApplication(nicTransaction.getDateOfApplication());
								record.setEstDateOfCollection(nicTransaction.getEstDateOfCollection());
								{
									try {
										CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,
												nicTransaction.getPriority().toString());
										if (codeValue != null && codeValue.getCodeValueDesc() != null) {
											record.setPriorityString(codeValue.getCodeValueDesc());
										} else {
											record.setPriorityString(nicTransaction.getPriority() == null ? null
													: nicTransaction.getPriority().toString());
										}
									} catch (Exception e) {
										record.setPriorityString(nicTransaction.getPriority() == null ? null
												: nicTransaction.getPriority().toString());
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

				model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION_SUSPENDED_ASSIGNMENT);
				ModelAndView modelAndView = new ModelAndView("fraudCaseManagement.fraudCaseManagementAssignment.list",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION_SUSPENDED_ASSIGNMENT);
				ModelAndView modelAndView = new ModelAndView("fraudCaseManagement.fraudCaseManagementAssignment.list",
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

	@RequestMapping(value = "/applyFilter")
	public ModelAndView applyFilter(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getInvestigationAssignmentList(investigationAssignmentData, request, httpRequest, model, 1, null,
				null);
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
			UserSession userSession = (UserSession) httpRequest.getSession().getAttribute("userSession");

			uploadJobService.unassignSuspendedInvestigationJobs(investigationAssignmentData.getSelectedJobIds_long(),
					userSession.getUserName(), userSession.getWorkstationId());

			messages.add("The unassignment was successful.");
		} catch (Exception e) {
			e.printStackTrace();
			Errors.add(
					"The unassignment was unsuccessful.  This may happen when a job has already changed.  Please try again.");
		}

		return this.getInvestigationAssignmentList(investigationAssignmentData, request, httpRequest, model, 1,
				messages, Errors);
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
			if (StringUtils.isBlank(investigationAssignmentData.getAssignToId())) {
				throw new Exception();
			}
			ADUser user = userService.findUserByUserId(investigationAssignmentData.getAssignToId());
			if (user == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			Errors.add("Invalid:  To User Id.");
			return this.getInvestigationAssignmentList(investigationAssignmentData, request, httpRequest, model, 1,
					messages, Errors);
		}

		try {
			UserSession userSession = (UserSession) httpRequest.getSession().getAttribute("userSession");
			uploadJobService.assignSuspendedInvestigationJobs(investigationAssignmentData.getSelectedJobIds_long(),
					investigationAssignmentData.getAssignToId(), userSession.getUserName(),
					userSession.getWorkstationId());
			messages.add("The assignment was successful.");
			return this.getInvestigationAssignmentList(investigationAssignmentData, request, httpRequest, model, 1,
					messages, Errors);
		} catch (Exception e) {
			e.printStackTrace();
			Errors.add(
					"The assignment was unsuccessful.  This may happen when a job has already changed.  Please try again.");
			return this.getInvestigationAssignmentList(investigationAssignmentData, request, httpRequest, model, 1,
					messages, Errors);
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
			if (StringUtils.isBlank(investigationAssignmentData.getUnassignAllForUserUserId())) {
				throw new Exception();
			}
			ADUser user = userService.findUserByUserId(investigationAssignmentData.getUnassignAllForUserUserId());
			if (user == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			Errors.add("Invalid:  Currently Assigned To User Id.");
			return this.getInvestigationAssignmentList(investigationAssignmentData, request, httpRequest, model, 1,
					messages, Errors);
		}

		try {
			UserSession userSession = (UserSession) httpRequest.getSession().getAttribute("userSession");
			uploadJobService.unassignSuspendedInvestigationAllJobsForUser(
					investigationAssignmentData.getUnassignAllForUserUserId(), userSession.getUserName(),
					userSession.getWorkstationId());
			messages.add("The unassignment was successful.");
			return this.getInvestigationAssignmentList(investigationAssignmentData, request, httpRequest, model, 1,
					messages, Errors);
		} catch (Exception e) {
			e.printStackTrace();
			Errors.add("The unassignment was unsuccessful.");
			return this.getInvestigationAssignmentList(investigationAssignmentData, request, httpRequest, model, 1,
					messages, Errors);
		}

	}

}
