package com.nec.asia.nic.investigation.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.web.session.UserSession;



@Controller
@RequestMapping(value = "/supervisorController")
public class SupervisorController extends AbstractController {

	private static final Logger logger = LoggerFactory
			.getLogger(InvestigationController.class);
	
	private static String DECENDING_ORDER="2";
	
	@Autowired
	private NicUploadJobService uploadJobService;
	
	@Autowired
	private ParametersService parametersService;
	
	@Autowired
	private NicTransactionService nicTransactionService;
	
	
	@RequestMapping(value = "/investigationRejectedList")
	public ModelAndView getInvestionJob(WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		PaginatedResult<NicUploadJobDto> rejectedList = null;
		List<NicUploadJobDto> rejectedJobList = new ArrayList<NicUploadJobDto>();
		try {
			int pageNo = 1;
			int pageSize = 20;
			int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"+userSession.getUserName());
			String  userRole = uploadJobService.getUserRole(userSession.getUserId());
			
			//Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = SupervisorController.DECENDING_ORDER;
			  
			try {
				String sort = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}
				  
			try {
				String requestOrder = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}
				  
			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(SupervisorController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			Parameters parameter = parametersService.findById(id);

			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			String pageNumber = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (StringUtils.isNotBlank(pageNumber) && StringUtils.isNumeric(pageNumber)) {
				pageNo = Integer.parseInt(pageNumber);
			}
			if (StringUtils.isNotBlank(pageNumber)) {
				startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
			} else {
				startIndex = 10;
			}
			
			if (userRole != null && userRole.equals("SUPERVISOR")) {
				// Supervisor Login -- Rejected Job List
				rejectedList = uploadJobService.findRejectedJobsForSupervisor(userSession.getUserName(), pageNo, pageSize);
				if (rejectedList != null) {
					rejectedJobList = rejectedList.getRows();
					if (rejectedJobList != null) {
						for (NicUploadJobDto record : rejectedJobList) {
							String transactionId = record.getTransactionId();
							NicTransaction nicTransaction = nicTransactionService.findById(transactionId);
							if (nicTransaction != null) {
								record.setDateOfApplication(nicTransaction.getDateOfApplication());
							}
						}
					}
				}
				Map<String, Object> searchResultMap = new HashMap<String, Object>();
				searchResultMap.put("rejectedJobList", rejectedJobList);
				searchResultMap.put("totalRecords", rejectedList.getTotal());
				searchResultMap.put("pageSize", pageSize);
				
				model.addAttribute("userRole", userRole);
				model.addAttribute("fnSelected", Constants.HEADING_NIC_SUPERVISOR);
				return new ModelAndView("supervisor-rejectJob-List", searchResultMap);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  PrintStream ps = new PrintStream(baos);
		  ex.printStackTrace(ps);
		  logger.error(ex.getMessage()+" : \n" + new String(baos.toByteArray()));
	}
	

}