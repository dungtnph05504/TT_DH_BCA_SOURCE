package com.nec.asia.nic.investigation.controller;

import java.util.ArrayList;
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

import com.nec.asia.nic.admin.domain.User;
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
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/investigationReject")
public class InvestigationRejectController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(InvestigationRejectController.class);

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

	@ModelAttribute("userAssignment")
	public Map<String, String> userAssignment() {
		List<Users> codeList = userService.findAll();
		Map<String, String> userAssignment = new LinkedHashMap<String, String>();
		for (Users ricCode : codeList) {
			userAssignment.put(ricCode.getUserId(), ricCode.getUserId());
		}
		return userAssignment;
	}
	
	@RequestMapping(value = "/investigationRejectList")
	public ModelAndView getInvestigationRejectList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationRejectList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request
				.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber) && StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getInvestigationRejectList(investigationAssignmentData, request, httpRequest, model, pageNo,
				null, null);
	}

	public ModelAndView getInvestigationRejectList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model, int pageNo, List<String> listOfMessages,
			List<String> listOfErrors) {
		logger.info("getInvestigationPendingBossList pageNo");
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

			Parameters parameter = parametersService.findById(new ParametersId(Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			///TRUNG: Lấy dữ liệu danh sách bị từ chối
			pr = uploadJobService.findAllForInvestigationPagination(
					new String[] { NicUploadJob.RECORD_STATUS_REJECTED },
					userSession.getUserId(), pageNo, pageSize,
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
									record.setFullName(nicTransaction.getNicRegistrationData().getSurnameLine1());
									record.setPassportType(nicTransaction.getPassportType());
									record.setRegSiteCode(nicTransaction.getRegSiteCode());
									{
										try {
											CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY, nicTransaction.getPriority().toString());
											if (codeValue != null && codeValue.getCodeValueDesc() != null) {
												record.setPriorityString(codeValue.getCodeValueDesc());
											} else {
												record.setPriorityString(nicTransaction.getPriority() == null ? null
														: nicTransaction.getPriority().toString());
											}
											if(nicTransaction.getPriority() == 0)
												record.setPriorityString("Thông thường");
										} catch (Exception e) {
											record.setPriorityString(nicTransaction.getPriority() == null ? null
													: nicTransaction.getPriority().toString());
										}
									}
								}
							}
							
					}
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

				model.addAttribute("fnSelected", Constants.HEADING_NIC_REJECT);
				ModelAndView modelAndView = new ModelAndView("investigation.investigationReject.investigationRejectList",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected", Constants.HEADING_NIC_REJECT);
				ModelAndView modelAndView = new ModelAndView("investigation.investigationReject.investigationRejectList", null);
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
		logger.info("applyFilterPending");

		return this.getInvestigationRejectList(investigationAssignmentData, request, httpRequest, model, 1, null,
				null);
	}
}
