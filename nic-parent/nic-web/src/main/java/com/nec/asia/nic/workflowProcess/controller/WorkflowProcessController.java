/**
 * 
 */
package com.nec.asia.nic.workflowProcess.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jcifs.util.transport.Request;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ognl.InappropriateExpressionException;

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

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverDetail;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.workflowProcess.dao.WorkflowProcessDao;
import com.nec.asia.nic.comp.workflowProcess.service.WorkflowProcessService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.ParaSignerCompares;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.investigation.controller.InvestigationAssignmentData;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.utils.DateUtil;
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
@RequestMapping(value = "/workflowProcess")
public class WorkflowProcessController extends AbstractController {

	@Autowired
	private ParametersService parametersService;
	
	@Autowired
	private ListHandoverService listHandoverService;

	@Autowired
	private PaymentDefService paymentDefService;

	@Autowired
	private NicTransactionService nicTransactionService;
	
	@Autowired
	private NicUploadJobService uploadJobService;
	
	@Autowired
	private DocumentDataDao documentDataDao;
	
	@Autowired
	private WorkflowProcessService workflowProcessService;
	
	@Autowired
	//@Qualifier(value="codesService")
	private CodesService codesService;
	
	//30 Dec 2013 (chris lai) : update with audit record
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkflowProcessController.class);
	private static final String PARA_SCOPE_SYSTEM = "SYSTEM";
	private static final String PARA_NAME_ASSIGN_USER = "ASSIGN_USER";
	
	@RequestMapping(value = "/main")
	public ModelAndView getMainProcess(
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getMainProcess");

		List<NicWorkflowProcess> workflowList = null;
		ModelAndView modelAndView = new ModelAndView("mainProcess", null);
		
		NicWorkflowProcess checkCPD = new NicWorkflowProcess();
		NicWorkflowProcess assignment = new NicWorkflowProcess();
		NicWorkflowProcess investigation = new NicWorkflowProcess();
		NicWorkflowProcess presentapproval = new NicWorkflowProcess();
		NicWorkflowProcess leaderapproval = new NicWorkflowProcess();
		NicWorkflowProcess verification = new NicWorkflowProcess();
		
		Boolean invesVer = true;
		Boolean presentVer = true;
		Boolean endinvesVer = false;
		Boolean endpresentVer = true;
		Boolean endleaderVer = false;
		Boolean endpersoVer = false;
		
		Boolean assignment_b = true;
		Boolean investigation_b = true;
		Boolean presentapproval_b = true;
		Boolean leaderapproval_b = true;
		try{
			
			verification = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.VERIFICATION).get(0);
			if(verification.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF){
				invesVer = false;
				presentVer = false;
			}
			if(verification.getWorkflowEnd().equals(NicWorkflowProcess.INVESTIGATION)){
				endinvesVer = true;
				endpresentVer = false;
				endleaderVer = false;
				endpersoVer = false;
			}
			else if(verification.getWorkflowEnd().equals(NicWorkflowProcess.LEADERS_APPROVAL)){
				endinvesVer = false;
				endpresentVer = false;
				endleaderVer = true;
				endpersoVer = false;
			}
			else if(verification.getWorkflowEnd().equals(NicWorkflowProcess.PERSO)){
				endinvesVer = false;
				endpresentVer = false;
				endleaderVer = false;
				endpersoVer = true;
			}
			
			assignment = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.ASSIGNMENT).get(0);
			investigation = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.INVESTIGATION).get(0);
			presentapproval = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.PRESENT_APPROVAL).get(0);
			leaderapproval = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.LEADERS_APPROVAL).get(0);
			
			List<NicWorkflowProcess> listCPD = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.CHECK_CPD);
			for(NicWorkflowProcess i_ : listCPD){
				if(i_.getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_YES)){
					checkCPD = i_;
					break;
				}
			}
			
			workflowList = new ArrayList<NicWorkflowProcess>();
			workflowList = workflowProcessService.findAll();
			model.addAttribute("jobList", workflowList);
			
			if(assignment.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				assignment_b = false;
			if(investigation.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				investigation_b = false;
			if(presentapproval.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				presentapproval_b = false;
			if(leaderapproval.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				leaderapproval_b = false;
			
			if(checkCPD.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
			{	
				int k = CheckingKey(checkCPD.getWorkflowEnd());
				if(checkCPD.getWorkflowEnd().equals(assignment.getWorkflowStart()))
					assignment_b = true;
				else
				{
					int s = CheckingKey(assignment.getWorkflowStart());
					if(s < k){
						assignment_b = false;
					}
				}
				
				if(checkCPD.getWorkflowEnd().equals(investigation.getWorkflowStart()))
					investigation_b = true;
				else
				{
					int s = CheckingKey(investigation.getWorkflowStart());
					if(s < k){
						investigation_b = false;
					}
				}
				
				if(checkCPD.getWorkflowEnd().equals(presentapproval.getWorkflowStart()))
					presentapproval_b = true;
				else
				{
					int s = CheckingKey(presentapproval.getWorkflowStart());
					if(s < k){
						presentapproval_b = false;
					}
				}
				
				if(checkCPD.getWorkflowEnd().equals(leaderapproval.getWorkflowStart()))
					leaderapproval_b = true;
				else
				{
					int s = CheckingKey(leaderapproval.getWorkflowStart());
					if(s < k){
						leaderapproval_b = false;
					}
				}
			}
			
			modelAndView.addObject("assignment_b", assignment_b);
			modelAndView.addObject("investigation_b", investigation_b);
			modelAndView.addObject("presentapproval_b", presentapproval_b);
			modelAndView.addObject("leaderapproval_b", leaderapproval_b);
		}catch(Exception e){
			
		}
		
		modelAndView.addObject("checkCPD", checkCPD);
		modelAndView.addObject("assignment", assignment);
		modelAndView.addObject("investigation", investigation);
		modelAndView.addObject("presentapproval", presentapproval);
		modelAndView.addObject("leaderapproval", leaderapproval);
		modelAndView.addObject("verification", verification);
		
		modelAndView.addObject("invesVer", invesVer);
		modelAndView.addObject("presentVer", presentVer);
		modelAndView.addObject("endinvesVer", endinvesVer);
		modelAndView.addObject("endpresentVer", endpresentVer);
		modelAndView.addObject("endleaderVer", endleaderVer);
		modelAndView.addObject("endpersoVer", endpersoVer);

		String assignName = "NULL";
		Parameters parameter = parametersService.findById(new ParametersId(PARA_SCOPE_SYSTEM, PARA_NAME_ASSIGN_USER));
		assignName = parameter.getParaShortValue();
		
		modelAndView.addObject("assignName", assignName);
		return modelAndView;
	}
	
	@RequestMapping(value = "/mainNo")
	public ModelAndView getMainProcessNo(
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getMainProcessNo");

		List<NicWorkflowProcess> workflowList = null;
		ModelAndView modelAndView = new ModelAndView("mainProcessNo", null);
		
		NicWorkflowProcess checkCPD = new NicWorkflowProcess();
		NicWorkflowProcess assignment = new NicWorkflowProcess();
		NicWorkflowProcess investigation = new NicWorkflowProcess();
		NicWorkflowProcess presentapproval = new NicWorkflowProcess();
		NicWorkflowProcess leaderapproval = new NicWorkflowProcess();
		NicWorkflowProcess verification = new NicWorkflowProcess();
		
		Boolean invesVer = true;
		Boolean presentVer = true;
		Boolean endinvesVer = false;
		Boolean endpresentVer = true;
		Boolean endleaderVer = false;
		Boolean endpersoVer = false;
		
		Boolean assignment_b = true;
		Boolean investigation_b = true;
		Boolean presentapproval_b = true;
		Boolean leaderapproval_b = true;
		try{
			
			verification = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.VERIFICATION).get(0);
			if(verification.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF){
				invesVer = false;
				presentVer = false;
			}
			if(verification.getWorkflowEnd().equals(NicWorkflowProcess.INVESTIGATION)){
				endinvesVer = true;
				endpresentVer = false;
				endleaderVer = false;
				endpersoVer = false;
			}
			else if(verification.getWorkflowEnd().equals(NicWorkflowProcess.LEADERS_APPROVAL)){
				endinvesVer = false;
				endpresentVer = false;
				endleaderVer = true;
				endpersoVer = false;
			}
			else if(verification.getWorkflowEnd().equals(NicWorkflowProcess.PERSO)){
				endinvesVer = false;
				endpresentVer = false;
				endleaderVer = false;
				endpersoVer = true;
			}
			
			assignment = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.ASSIGNMENT).get(0);
			investigation = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.INVESTIGATION).get(0);
			presentapproval = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.PRESENT_APPROVAL).get(0);
			leaderapproval = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.LEADERS_APPROVAL).get(0);
			
			List<NicWorkflowProcess> listCPD = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.CHECK_CPD);
			for(NicWorkflowProcess i_ : listCPD){
				if(i_.getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_NO)){
					checkCPD = i_;
					break;
				}
			}
			
			workflowList = new ArrayList<NicWorkflowProcess>();
			workflowList = workflowProcessService.findAll();
			model.addAttribute("jobList", workflowList);
			
			if(assignment.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				assignment_b = false;
			if(investigation.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				investigation_b = false;
			if(presentapproval.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				presentapproval_b = false;
			if(leaderapproval.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				leaderapproval_b = false;
			
			if(checkCPD.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
				
				int k = CheckingKey(checkCPD.getWorkflowEnd());
				if(checkCPD.getWorkflowEnd().equals(assignment.getWorkflowStart()))
					assignment_b = true;
				else
				{
					int s = CheckingKey(assignment.getWorkflowStart());
					if(s < k){
						assignment_b = false;
					}
				}
				
				if(checkCPD.getWorkflowEnd().equals(investigation.getWorkflowStart()))
					investigation_b = true;
				else
				{
					int s = CheckingKey(investigation.getWorkflowStart());
					if(s < k){
						investigation_b = false;
					}
				}
				
				if(checkCPD.getWorkflowEnd().equals(presentapproval.getWorkflowStart()))
					presentapproval_b = true;
				else
				{
					int s = CheckingKey(presentapproval.getWorkflowStart());
					if(s < k){
						presentapproval_b = false;
					}
				}
				
				if(checkCPD.getWorkflowEnd().equals(leaderapproval.getWorkflowStart()))
					leaderapproval_b = true;
				else
				{
					int s = CheckingKey(leaderapproval.getWorkflowStart());
					if(s < k){
						leaderapproval_b = false;
					}
				}
			}
			
			modelAndView.addObject("assignment_b", assignment_b);
			modelAndView.addObject("investigation_b", investigation_b);
			modelAndView.addObject("presentapproval_b", presentapproval_b);
			modelAndView.addObject("leaderapproval_b", leaderapproval_b);
			
		}catch(Exception e){
			
		}
		
		modelAndView.addObject("checkCPD", checkCPD);
		modelAndView.addObject("assignment", assignment);
		modelAndView.addObject("investigation", investigation);
		modelAndView.addObject("presentapproval", presentapproval);
		modelAndView.addObject("leaderapproval", leaderapproval);
		modelAndView.addObject("verification", verification);
		
		modelAndView.addObject("invesVer", invesVer);
		modelAndView.addObject("presentVer", presentVer);
		modelAndView.addObject("endinvesVer", endinvesVer);
		modelAndView.addObject("endpresentVer", endpresentVer);
		modelAndView.addObject("endleaderVer", endleaderVer);
		modelAndView.addObject("endpersoVer", endpersoVer);

		String assignName = "NULL";
		Parameters parameter = parametersService.findById(new ParametersId(PARA_SCOPE_SYSTEM, PARA_NAME_ASSIGN_USER));
		assignName = parameter.getParaShortValue();
		
		modelAndView.addObject("assignName", assignName);
		return modelAndView;
	}

	@RequestMapping(value = "/reprocess", method = RequestMethod.POST)
	public @ResponseBody int Reprocess(HttpServletRequest httpRequest, @RequestBody String key){
		try{
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			if(StringUtils.isEmpty(key)){
				return -1;
			}
			else
				key = key.replace("=", "").replace("id", "").trim();
			
			int step = CheckingKey(key);
			if (step == 0)
				return -1; // Sai key

			NicWorkflowProcess wfl = new NicWorkflowProcess();
			List<NicWorkflowProcess> list = workflowProcessService
					.findWorkflowProcessByCriteria(key);
			
			if(list == null || list.size() <= 0)
				return -2; //Không có dữ liệu Key trong database
			else
			{
				if(key == NicWorkflowProcess.CHECK_CPD)
				{
					for(NicWorkflowProcess i_ : list){
						if(i_.getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_YES)){
							wfl = i_;
							break;
						}
					}
				}
				else
					wfl = list.get(0);
				int statusChange = NicWorkflowProcess.WORKFLOW_STATUS_ON;
				
				if (wfl.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
					statusChange = NicWorkflowProcess.WORKFLOW_STATUS_OFF;
					
					//Kiểm tra nếu WORKFLOW có VER
					int check_ = CheckVerifyWorkflow(key);
					if(check_ == 1){
						return 1; //Trùng key end với VER
					}
					else{
						String endWf = wfl.getWorkflowEnd();
						//Tìm Workflow có điểm cuối trùng với key
						List<NicWorkflowProcess> listBefore = workflowProcessService
								.findWorkflowProcessByCriteriaEnd(key);
						if(listBefore != null && listBefore.size() > 0){
							for(NicWorkflowProcess i_ : listBefore){
								//Cập nhật lại điểm cuối với những workflow đang chạy
								if(i_.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
									i_.setWorkflowEnd(endWf);
									i_.setUpdateBy(userSession.getUserName());
									workflowProcessService.editWorkflowProcess(i_);
									/*if(!i_.getWorkflowStart().equals(NicWorkflowProcess.VERIFICATION)){
										i_.setWorkflowEnd(endWf);
										i_.setUpdateBy(userSession.getUserName());
										workflowProcessService.editWorkflowProcess(i_);
									}
									else
									{
										if(i_.getWorkflowStartResult() != null && i_.getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_YES)){
											i_.setWorkflowEnd(endWf);
											i_.setUpdateBy(userSession.getUserName());
											workflowProcessService.editWorkflowProcess(i_);
										}
									}*/
								}
							}
						}
					}
				}
				else
				{
					if(!key.equals(NicWorkflowProcess.VERIFICATION)){
						//Kiểm tra bước trước đó của Workflow
						String wflBefore = CheckingBeforeWorkflow(key);
						if(StringUtils.isEmpty(wflBefore))
							return -1;
						List<NicWorkflowProcess> listBefore = workflowProcessService
								.findWorkflowProcessByCriteria(wflBefore);
						if(listBefore != null && listBefore.size() > 0){
							NicWorkflowProcess before = listBefore.get(0);
							if(before.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
								wfl.setWorkflowEnd(before.getWorkflowEnd());
								before.setWorkflowEnd(key);
								before.setUpdateBy(userSession.getUserName());
								workflowProcessService.editWorkflowProcess(before);
							}
							else
							{
								String a = before.getWorkflowStart();
								int i = 1;
								while(StringUtils.isNotEmpty(a)){
									String wflBefore2 = CheckingBeforeWorkflow(a);
									logger.info(i + ".KEY before2: " + wflBefore2);
									if(StringUtils.isEmpty(wflBefore2))
										return -1; //Sai key
									
									List<NicWorkflowProcess> listBefore2 = workflowProcessService
											.findWorkflowProcessByCriteria(wflBefore2);
									
									NicWorkflowProcess before2 = listBefore2.get(0);
									if(before2.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
										wfl.setWorkflowEnd(before2.getWorkflowEnd());
										before2.setWorkflowEnd(key);
										before2.setUpdateBy(userSession.getUserName());
										workflowProcessService.editWorkflowProcess(before2);
										a = "";
									}
									else
									{
										a = before2.getWorkflowStart();
										i++;
									}
								}
							}
						}
						else
						{
							String a = key;
							int i = 1;
							while(StringUtils.isNotEmpty(a)){
								String wflAfter = CheckingFirstWorkflow(a);
								logger.info(i + ".KEY AFTER: " + wflAfter);
								if(StringUtils.isEmpty(wflAfter))
									return -1; //Sai key
								
								List<NicWorkflowProcess> listAfter = workflowProcessService
										.findWorkflowProcessByCriteria(wflAfter);
								
								NicWorkflowProcess after = listAfter.get(0);
								if(after.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
									wfl.setWorkflowEnd(after.getWorkflowEnd());
									after.setWorkflowEnd(key);
									after.setUpdateBy(userSession.getUserName());
									workflowProcessService.editWorkflowProcess(after);
									a = "";
								}
								else
								{
									a = after.getWorkflowStart();
									i++;
								}
							}
						}
					}
				}
				//Cập nhật trạng thái của workflow key
				
				if(key.equals(NicWorkflowProcess.CHECK_CPD))
				{
					for(NicWorkflowProcess i_ : list){
						i_.setStatus(statusChange);
						i_.setUpdateBy(userSession.getUserName());
						workflowProcessService.editWorkflowProcess(i_);
					}
				}
				else{
					wfl.setStatus(statusChange);
					wfl.setUpdateBy(userSession.getUserName());
					workflowProcessService.editWorkflowProcess(wfl);
				}
				
				return 0; //Thành công	
			}
			
		}
		catch(Exception e){
			logger.error("Reprocess: Exception:" + e.getMessage());
		}
		return -99; //Cập nhật thất bại
	}
	
	@RequestMapping(value = "/reprocessNo", method = RequestMethod.POST)
	public @ResponseBody int ReprocessNo(HttpServletRequest httpRequest, @RequestBody String key){
		try{
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			if(StringUtils.isEmpty(key)){
				return -1;
			}
			else
				key = key.replace("=", "").replace("id", "").trim();
			
			int step = CheckingKey(key);
			if (step == 0)
				return -1; // Sai key

			NicWorkflowProcess wfl = new NicWorkflowProcess();
			List<NicWorkflowProcess> list = workflowProcessService
					.findWorkflowProcessByCriteria(key);
			
			if(list == null || list.size() <= 0)
				return -2; //Không có dữ liệu Key trong database
			else
			{
				if(key == NicWorkflowProcess.CHECK_CPD)
				{
					for(NicWorkflowProcess i_ : list){
						if(i_.getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_NO)){
							wfl = i_;
							break;
						}
					}
				}
				else
					wfl = list.get(0);
				int statusChange = NicWorkflowProcess.WORKFLOW_STATUS_ON;
				
				if (wfl.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
					statusChange = NicWorkflowProcess.WORKFLOW_STATUS_OFF;
					
					//Kiểm tra nếu WORKFLOW có VER
					int check_ = CheckVerifyWorkflow(key);
					if(check_ == 1){
						return 1; //Trùng key end với VER
					}
					else{
						String endWf = wfl.getWorkflowEnd();
						//Tìm Workflow có điểm cuối trùng với key
						List<NicWorkflowProcess> listBefore = workflowProcessService
								.findWorkflowProcessByCriteriaEnd(key);
						if(listBefore != null && listBefore.size() > 0){
							for(NicWorkflowProcess i_ : listBefore){
								//Cập nhật lại điểm cuối với những workflow đang chạy
								if(i_.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
									i_.setWorkflowEnd(endWf);
									i_.setUpdateBy(userSession.getUserName());
									workflowProcessService.editWorkflowProcess(i_);
									/*if(!i_.getWorkflowStart().equals(NicWorkflowProcess.VERIFICATION)){
										i_.setWorkflowEnd(endWf);
										i_.setUpdateBy(userSession.getUserName());
										workflowProcessService.editWorkflowProcess(i_);
									}
									else
									{
										if(i_.getWorkflowStartResult() != null && i_.getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_NO)){
											i_.setWorkflowEnd(endWf);
											i_.setUpdateBy(userSession.getUserName());
											workflowProcessService.editWorkflowProcess(i_);
										}
									}*/
								}
							}
						}
					}
				}
				else
				{
					if(!key.equals(NicWorkflowProcess.VERIFICATION)){
						//Kiểm tra bước trước đó của Workflow
						String wflBefore = CheckingBeforeWorkflow(key);
						if(StringUtils.isEmpty(wflBefore))
							return -1;
						List<NicWorkflowProcess> listBefore = workflowProcessService
								.findWorkflowProcessByCriteria(wflBefore);
						if(listBefore != null && listBefore.size() > 0){
							NicWorkflowProcess before = listBefore.get(0);
							if(listBefore.size() > 1)
								before = listBefore.get(1);
							
							if(before.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
								wfl.setWorkflowEnd(before.getWorkflowEnd());
								before.setWorkflowEnd(key);
								before.setUpdateBy(userSession.getUserName());
								workflowProcessService.editWorkflowProcess(before);
							}
							else
							{
								String a = before.getWorkflowStart();
								int i = 1;
								while(StringUtils.isNotEmpty(a)){
									
									String wflBefore2 = CheckingBeforeWorkflow(a);
									logger.info(i + ".KEY before2: " + wflBefore2);
									if(StringUtils.isEmpty(wflBefore2))
										return -1; //Sai key
									
									List<NicWorkflowProcess> listBefore2 = workflowProcessService
											.findWorkflowProcessByCriteria(wflBefore2);
									
									NicWorkflowProcess before2 = listBefore2.get(0);
									if(listBefore2.size() > 1)
										before2 = listBefore2.get(1);
									
									if(before2.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
										wfl.setWorkflowEnd(before2.getWorkflowEnd());
										before2.setWorkflowEnd(key);
										before2.setUpdateBy(userSession.getUserName());
										workflowProcessService.editWorkflowProcess(before2);
										a = "";
									}
									else
									{
										a = before2.getWorkflowStart();
										i++;
									}
								}
							}
						}
						else
						{
							String a = key;
							int i = 1;
							while(StringUtils.isNotEmpty(a)){
								String wflAfter = CheckingFirstWorkflow(a);
								logger.info(i + ".KEY AFTER: " + wflAfter);
								if(StringUtils.isEmpty(wflAfter))
									return -1; //Sai key
								
								List<NicWorkflowProcess> listAfter = workflowProcessService
										.findWorkflowProcessByCriteria(wflAfter);
								
								NicWorkflowProcess after = listAfter.get(0);
								if(after.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON){
									wfl.setWorkflowEnd(after.getWorkflowEnd());
									after.setWorkflowEnd(key);
									after.setUpdateBy(userSession.getUserName());
									workflowProcessService.editWorkflowProcess(after);
									a = "";
								}
								else
								{
									a = after.getWorkflowStart();
									i++;
								}
							}
						}
					}
				}
				//Cập nhật trạng thái của workflow key
				if(key.equals(NicWorkflowProcess.CHECK_CPD))
				{
					for(NicWorkflowProcess i_ : list){
						i_.setStatus(statusChange);
						i_.setUpdateBy(userSession.getUserName());
						workflowProcessService.editWorkflowProcess(i_);
					}
				}
				else{
					wfl.setStatus(statusChange);
					wfl.setUpdateBy(userSession.getUserName());
					workflowProcessService.editWorkflowProcess(wfl);
				}
				return 0; //Thành công	
			}
			
		}
		catch(Exception e){
			logger.error("Reprocess: Exception:" + e.getMessage());
		}
		return -99; //Cập nhật thất bại
	}
	
	@RequestMapping(value = "/changeWorkflowEnd", method = RequestMethod.POST)
	public @ResponseBody String changeWorkflowEnd(HttpServletRequest httpRequest, @RequestBody String key){
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			key = key.replace("=", "");
			String startKey = key.split("-")[0];
			int numb = Integer.parseInt(key.split("-")[1]);
			String endKey = key.split("-")[2];

			List<NicWorkflowProcess> listSkey = workflowProcessService
					.findWorkflowProcessByCriteria(startKey);
			if(listSkey == null || listSkey.size() <= 0)
				return "NULL";
			
			NicWorkflowProcess wfStart = new NicWorkflowProcess();
			wfStart = listSkey.get(0);
			if(wfStart.getWorkflowStart().equals(NicWorkflowProcess.CHECK_CPD) && !wfStart.getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_YES)){
				wfStart = listSkey.get(1);
			}
			
			if(wfStart.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				return "OFF"; //Đã tắt
			
			//Kiểm tra xem workflow đã đóng chưa
			if(!endKey.equals(NicWorkflowProcess.PERSO)){
				List<NicWorkflowProcess> listEkey = workflowProcessService
						.findWorkflowProcessByCriteria(endKey);
				if(listEkey == null || listEkey.size() <= 0)
					return "NULL";
				
				NicWorkflowProcess nwf = listEkey.get(0);
				if(nwf.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
					return "UNUSED"; //Đã đóng không dùng đc nữa
			}
			//Lấy danh sách các workflow ở giữa cần đóng
			String[] listWF = FindEndNewCase(numb);
			if(listWF == null && listWF.length <= 0)
				return "NULL";
			else
			{
				//Kiểm tra trước khi đóng có VER
				for(String i_ : listWF){
					if(!i_.equals(endKey)){
						List<NicWorkflowProcess> listVER = workflowProcessService
								.findWorkflowProcessByCriteria(NicWorkflowProcess.VERIFICATION);
						NicWorkflowProcess objVer = listVER.get(0);
						if(objVer.getWorkflowEnd().equals(i_))
							return i_; //Sửa lại luồng sang kết quả khác
					}
				}
				
				//Đóng tất cả các worflow đang mở 
				for(String i_ : listWF){	
					if(i_.equals(endKey))
						break;
					else
					{
						List<NicWorkflowProcess> listOn = workflowProcessService
								.findWorkflowProcessByCriteria(i_);
						NicWorkflowProcess objOn = listOn.get(0);
						if(!objOn.getWorkflowStart().equals(NicWorkflowProcess.CHECK_CPD))
						{
							objOn.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_OFF);
							objOn.setUpdateBy(userSession.getUserName());
							workflowProcessService.editWorkflowProcess(objOn);
						}
						else
						{
							if(objOn.getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_YES)){
								objOn.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_OFF);
								objOn.setUpdateBy(userSession.getUserName());
								workflowProcessService.editWorkflowProcess(objOn);
							}
						}
					}
				}
				
				wfStart.setWorkflowEnd(endKey);
				wfStart.setUpdateBy(userSession.getUserName());
				workflowProcessService.editWorkflowProcess(wfStart);
				return "OK";
			}
			
		} catch (Exception e) {
			
		}
		
		return "ERROR";
	}
	
	@RequestMapping(value = "/changeWorkflowEndNo", method = RequestMethod.POST)
	public @ResponseBody String changeWorkflowEndNo(HttpServletRequest httpRequest, @RequestBody String key){
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			key = key.replace("=", "");
			String startKey = key.split("-")[0];
			int numb = Integer.parseInt(key.split("-")[1]);
			String endKey = key.split("-")[2];

			List<NicWorkflowProcess> listSkey = workflowProcessService
					.findWorkflowProcessByCriteria(startKey);
			if(listSkey == null || listSkey.size() <= 0)
				return "NULL";
			
			NicWorkflowProcess wfStart = new NicWorkflowProcess();
			wfStart = listSkey.get(0);
			if(wfStart.getWorkflowStart().equals(NicWorkflowProcess.CHECK_CPD) && !wfStart.getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_NO)){
				wfStart = listSkey.get(1);
			}
			
			if(wfStart.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				return "OFF"; //Đã tắt
			
			//Kiểm tra xem workflow đã đóng chưa
			if(!endKey.equals(NicWorkflowProcess.PERSO)){
				List<NicWorkflowProcess> listEkey = workflowProcessService
						.findWorkflowProcessByCriteria(endKey);
				if(listEkey == null || listEkey.size() <= 0)
					return "NULL";
				
				NicWorkflowProcess nwf = listEkey.get(0);
				if(nwf.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
					return "UNUSED"; //Đã đóng không dùng đc nữa
			}
			//Lấy danh sách các workflow ở giữa cần đóng
			String[] listWF = FindEndNewCase(numb);
			if(listWF == null && listWF.length <= 0)
				return "NULL";
			else
			{
				//Kiểm tra trước khi đóng có VER
				for(String i_ : listWF){
					if(!i_.equals(endKey)){
						List<NicWorkflowProcess> listVER = workflowProcessService
								.findWorkflowProcessByCriteria(NicWorkflowProcess.VERIFICATION);
						NicWorkflowProcess objVer = listVER.get(0);
						if(objVer.getWorkflowEnd().equals(i_))
							return i_; //Sửa lại luồng sang kết quả khác
					}
				}
				
				//Đóng tất cả các worflow đang mở 
				for(String i_ : listWF){	
					if(i_.equals(endKey))
						break;
					else
					{
						List<NicWorkflowProcess> listOn = workflowProcessService
								.findWorkflowProcessByCriteria(i_);
						NicWorkflowProcess objOn = listOn.get(0);
						if(!objOn.getWorkflowStart().equals(NicWorkflowProcess.CHECK_CPD))
						{
							objOn.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_OFF);
							objOn.setUpdateBy(userSession.getUserName());
							workflowProcessService.editWorkflowProcess(objOn);
						}
						else
						{
							if(objOn.getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_NO)){
								objOn.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_OFF);
								objOn.setUpdateBy(userSession.getUserName());
								workflowProcessService.editWorkflowProcess(objOn);
							}
						}
					}
				}
				
				wfStart.setWorkflowEnd(endKey);
				wfStart.setUpdateBy(userSession.getUserName());
				workflowProcessService.editWorkflowProcess(wfStart);
				return "OK";
			}
			
		} catch (Exception e) {
			
		}
		
		return "ERROR";
	}
	
	public int CheckVerifyWorkflow(String key){
		try{
			int step = CheckingKey(key);
			if (step == 0)
				return -1; // Sai key

			NicWorkflowProcess wfl = new NicWorkflowProcess();
			List<NicWorkflowProcess> list = workflowProcessService
					.findWorkflowProcessByCriteria(key);
			
			if(list == null || list.size() <= 0)
				return -2; //Không có dữ liệu Key trong database
			else
			{	
				List<NicWorkflowProcess> listV = workflowProcessService
						.findWorkflowProcessByCriteria(NicWorkflowProcess.VERIFICATION);
				if (listV == null || listV.size() <= 0)
					return -3;// Không có dữ liệu Key trong database
				else {
					NicWorkflowProcess wflV = listV.get(0);
					// Kiểm tra đầu cuối của WORKFLOW VERIFICATION
					if (wflV.getWorkflowEnd().equals(key)) {
						return 1; // Có trùng key
					}
				}
			}
		}
		catch(Exception e){
			logger.error("Reprocess: Exception:" + e.getMessage());
			return -99;//Lỗi
		}
		return 0; //Không trùng
	}
	
	@RequestMapping(value = "/updateVerify", method = RequestMethod.POST)
	public @ResponseBody int UpdateVerify(HttpServletRequest httpRequest, @RequestBody String key)
	{
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		NicWorkflowProcess wflVER = new NicWorkflowProcess();
		try {
			if(StringUtils.isEmpty(key)){
				return -1;
			}
			else
				key = key.replace("=", "").replace("id", "").trim();
			
			List<NicWorkflowProcess> list = workflowProcessService
					.findWorkflowProcessByCriteria(key);

			if(!key.equals(NicWorkflowProcess.PERSO)){
				if(list == null || list.size() <= 0)
					return -2; //Không có dữ liệu Key trong database
			}
			
			List<NicWorkflowProcess> listVer = workflowProcessService
					.findWorkflowProcessByCriteria(NicWorkflowProcess.VERIFICATION);
			if (listVer != null && listVer.size() > 0) {
				wflVER = listVer.get(0);
				wflVER.setWorkflowEnd(key);
				wflVER.setUpdateBy(userSession.getUserName());
				workflowProcessService.editWorkflowProcess(wflVER);
				return 0; // Thành công
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -99;//Lỗi
	}
	@ResponseBody
	@RequestMapping(value = "/listWorkflow/{key}", method = RequestMethod.GET)
	@ExceptionHandler
	public List<String> listWorkflow(
			@PathVariable String key) throws Exception{
		List<String> result = new ArrayList<String>();
		
		//Kiểm tra trạng thái đóng mở
		NicWorkflowProcess presen = workflowProcessService
				.findWorkflowProcessByCriteria(NicWorkflowProcess.PRESENT_APPROVAL).get(0);
		NicWorkflowProcess leader = workflowProcessService
				.findWorkflowProcessByCriteria(NicWorkflowProcess.LEADERS_APPROVAL).get(0);
		NicWorkflowProcess inves = workflowProcessService
				.findWorkflowProcessByCriteria(NicWorkflowProcess.INVESTIGATION).get(0);
		switch (key) {
			case NicWorkflowProcess.INVESTIGATION:
				if(presen.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
					result.add("<option value=\"PRESENT_APPROVAL\">Đề xuất</option>");
				if(leader.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
					result.add("<option value=\"LEADERS_APPROVAL\">Lãnh đạo duyệt</option>");
				
				result.add("<option value=\"PERSO\">Cá thể hóa</option>");
				break;
			case NicWorkflowProcess.PRESENT_APPROVAL:
				if(inves.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
					result.add("<option value=\"INVESTIGATION\">Xử lý</option>");
				if(leader.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
					result.add("<option value=\"LEADERS_APPROVAL\">Lãnh đạo duyệt</option>");
				result.add("<option value=\"PERSO\">Cá thể hóa</option>");
				break;
			case NicWorkflowProcess.LEADERS_APPROVAL:
				if(inves.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
					result.add("<option value=\"INVESTIGATION\">Xử lý</option>");
				if(presen.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
					result.add("<option value=\"PRESENT_APPROVAL\">Đề xuất</option>");
				result.add("<option value=\"PERSO\">Cá thể hóa</option>");
				break;
			case NicWorkflowProcess.PERSO:
				if(inves.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
					result.add("<option value=\"INVESTIGATION\">Xử lý</option>");
				if(presen.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
					result.add("<option value=\"PRESENT_APPROVAL\">Đề xuất</option>");
				if(leader.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
					result.add("<option value=\"LEADERS_APPROVAL\">Lãnh đạo duyệt</option>");
				break;
			default:
				break;
		}
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/resetAll", method = RequestMethod.GET)
	@ExceptionHandler
	public int resetAll(HttpServletRequest httpRequest) throws Exception{
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		
		try{
			//Khởi tạo lại từng luồng Workflow 
			NicWorkflowProcess uploaded = workflowProcessService
					.findWorkflowProcessByCriteria(NicWorkflowProcess.UPLOADED).get(0);
			uploaded.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_ON);
			uploaded.setWorkflowEnd(NicWorkflowProcess.CHECK_CPD);
			uploaded.setUpdateBy(userSession.getUserName());
			workflowProcessService.editWorkflowProcess(uploaded);
			
			NicWorkflowProcess checkCPD = workflowProcessService
					.findWorkflowProcessByCriteria(NicWorkflowProcess.CHECK_CPD).get(0);
			checkCPD.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_ON);
			checkCPD.setWorkflowEnd(NicWorkflowProcess.ASSIGNMENT);
			checkCPD.setUpdateBy(userSession.getUserName());
			workflowProcessService.editWorkflowProcess(checkCPD);
			
			NicWorkflowProcess checkCPD_no = workflowProcessService
					.findWorkflowProcessByCriteria(NicWorkflowProcess.CHECK_CPD).get(1);
			checkCPD_no.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_ON);
			checkCPD_no.setWorkflowEnd(NicWorkflowProcess.ASSIGNMENT);
			checkCPD_no.setUpdateBy(userSession.getUserName());
			workflowProcessService.editWorkflowProcess(checkCPD_no);
			
			NicWorkflowProcess assigment = workflowProcessService
					.findWorkflowProcessByCriteria(NicWorkflowProcess.ASSIGNMENT).get(0);
			assigment.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_ON);
			assigment.setWorkflowEnd(NicWorkflowProcess.INVESTIGATION);
			assigment.setUpdateBy(userSession.getUserName());
			workflowProcessService.editWorkflowProcess(assigment);
			
			NicWorkflowProcess inves = workflowProcessService
					.findWorkflowProcessByCriteria(NicWorkflowProcess.INVESTIGATION).get(0);
			inves.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_ON);
			inves.setWorkflowEnd(NicWorkflowProcess.PRESENT_APPROVAL);
			inves.setUpdateBy(userSession.getUserName());
			workflowProcessService.editWorkflowProcess(inves);
			
			NicWorkflowProcess presen = workflowProcessService
					.findWorkflowProcessByCriteria(NicWorkflowProcess.PRESENT_APPROVAL).get(0);
			presen.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_ON);
			presen.setWorkflowEnd(NicWorkflowProcess.LEADERS_APPROVAL);
			presen.setUpdateBy(userSession.getUserName());
			workflowProcessService.editWorkflowProcess(presen);
			
			NicWorkflowProcess leader = workflowProcessService
					.findWorkflowProcessByCriteria(NicWorkflowProcess.LEADERS_APPROVAL).get(0);
			leader.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_ON);
			leader.setWorkflowEnd(NicWorkflowProcess.PERSO);
			leader.setUpdateBy(userSession.getUserName());
			workflowProcessService.editWorkflowProcess(leader);
			
			NicWorkflowProcess verify = workflowProcessService
					.findWorkflowProcessByCriteria(NicWorkflowProcess.VERIFICATION).get(0);
			verify.setStatus(NicWorkflowProcess.WORKFLOW_STATUS_ON);
			verify.setWorkflowEnd(NicWorkflowProcess.PRESENT_APPROVAL);
			verify.setUpdateBy(userSession.getUserName());
			workflowProcessService.editWorkflowProcess(verify);
			
			return 0;
		}
		catch(Exception e){
			
		}
		return -99;
	}
	
	@ResponseBody
	@RequestMapping(value = "/listUserEnd/{key}", method = RequestMethod.GET)
	@ExceptionHandler
	public List<String> listUserEnd(
			@PathVariable String key) throws Exception{
		List<String> result = new ArrayList<String>();
		List<Users> codeList = userService.findByRoles(RegistrationConstants.USERS_ID_BY_ROLES_CODE);
		if(codeList != null){
			for (Users ricCode : codeList) {
				if(!ricCode.getUserId().equals(key))
						result.add("<option value=\""+ ricCode.getUserId() +"\">"+ ricCode.getUserId() +"</option>");
			}			
		}
		return result;
	}
	
	@RequestMapping(value = "/changeUserEnd", method = RequestMethod.POST)
	public @ResponseBody int changeUserEnd(HttpServletRequest httpRequest, @RequestBody String user){
		try {
			user = user.replace("=", "").trim();
			Parameters parameter = parametersService.findById(new ParametersId(PARA_SCOPE_SYSTEM, PARA_NAME_ASSIGN_USER));
			parameter.setParaShortValue(user);
			parametersService.updateParam(parameter);
			return 0;
		}
		catch(Exception e){
			
		}
		return -99;//Lỗi
	}
	
	@RequestMapping(value = "/findEnWorkflow/{key}", method = RequestMethod.GET)
	public @ResponseBody String findEnWorkflow(
			@PathVariable String key){
		String result = "";
		
		List<NicWorkflowProcess> list;
		try {
			list = workflowProcessService
					.findWorkflowProcessByCriteria(key);
			
			if(list != null && list.size() > 0){
				if(list.size() < 2){
					NicWorkflowProcess wfl = list.get(0);
					result = wfl.getWorkflowEnd();
				}
				else
				{
					if(list.get(0).getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_YES)){
						NicWorkflowProcess wfl = list.get(0);
						result = wfl.getWorkflowEnd();
					}
					else
					{
						NicWorkflowProcess wfl = list.get(1);
						result = wfl.getWorkflowEnd();
					}
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/listAfterwf/{key}", method = RequestMethod.GET)
	@ExceptionHandler
	public List<String> listAfterwf(@PathVariable int key){
		//Kiểm tra key theo luồng
		String[] listWF = FindEndNewCase(key);
		if(listWF == null)
			return null;
		
		List<String> result = new ArrayList<String>();
		List<String> listWF_active = new ArrayList<String>();
		//Kiểm tra trạng thái từng Workflow list
		for(String i_ : listWF){
			try {
				if(!i_.equals(NicWorkflowProcess.PERSO)){
					List<NicWorkflowProcess> lcheckS = workflowProcessService.findWorkflowProcessByCriteria(i_);
					if(lcheckS != null && lcheckS.size() > 0){
						if(lcheckS.size() > 1){
							if (lcheckS.get(0).getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON) {
								listWF_active.add(i_);
							} else if (lcheckS.get(1).getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON) {
								listWF_active.add(i_);
							}
						}
						else{
							if(lcheckS.get(0).getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
								listWF_active.add(i_);
						}
					}
				}
				else
				{
					listWF_active.add(i_);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(listWF_active == null || listWF_active.size() < 0)
				return null;
		}
		
		for(String k_ : listWF_active){
			switch (k_) {
				case NicWorkflowProcess.CHECK_CPD:
					result.add("<option value=\"CHECK_CPD\">Kiểm tra CPD</option>");
					break;
				case NicWorkflowProcess.ASSIGNMENT:
					result.add("<option value=\"ASSIGNMENT\">Phân công</option>");
					break;
				case NicWorkflowProcess.INVESTIGATION:
					result.add("<option value=\"INVESTIGATION\">Xử lý</option>");
					break;
				case NicWorkflowProcess.PRESENT_APPROVAL:
					result.add("<option value=\"PRESENT_APPROVAL\">Đề xuất</option>");
					break;
				case NicWorkflowProcess.LEADERS_APPROVAL:
					result.add("<option value=\"LEADERS_APPROVAL\">Lãnh đạo duyệt</option>");
					break;
				case NicWorkflowProcess.PERSO:
					result.add("<option value=\"PERSO\">Cá thể hóa</option>");
					break;
				default:
					break;
			}
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/listAfterwfNo/{key}", method = RequestMethod.GET)
	@ExceptionHandler
	public List<String> listAfterwfNo(@PathVariable int key){
		//Kiểm tra key theo luồng
		String[] listWF = FindEndNewCase(key);
		if(listWF == null)
			return null;
		
		List<String> result = new ArrayList<String>();
		List<String> listWF_active = new ArrayList<String>();
		//Kiểm tra trạng thái từng Workflow list
		for(String i_ : listWF){
			try {
				if(!i_.equals(NicWorkflowProcess.PERSO)){
					List<NicWorkflowProcess> lcheckS = workflowProcessService.findWorkflowProcessByCriteria(i_);
					if(lcheckS != null && lcheckS.size() > 0){
						if(lcheckS.size() > 1){
							if(lcheckS.get(1).getWorkflowStart().equals(NicWorkflowProcess.VERIFICATION) && lcheckS.get(1).getWorkflowStartResult().equals(NicWorkflowProcess.WORKFLOW_START_RESULT_NO))
								if(lcheckS.get(1).getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
									listWF_active.add(i_);
						}
						else{
							if(lcheckS.get(0).getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_ON)
								listWF_active.add(i_);
						}
					}
				}
				else
				{
					listWF_active.add(i_);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(listWF_active == null || listWF_active.size() < 0)
				return null;
		}
		
		for(String k_ : listWF_active){
			switch (k_) {
				case NicWorkflowProcess.CHECK_CPD:
					result.add("<option value=\"CHECK_CPD\">Kiểm tra CPD</option>");
					break;
				case NicWorkflowProcess.ASSIGNMENT:
					result.add("<option value=\"ASSIGNMENT\">Phân công</option>");
					break;
				case NicWorkflowProcess.INVESTIGATION:
					result.add("<option value=\"INVESTIGATION\">Xử lý</option>");
					break;
				case NicWorkflowProcess.PRESENT_APPROVAL:
					result.add("<option value=\"PRESENT_APPROVAL\">Đề xuất</option>");
					break;
				case NicWorkflowProcess.LEADERS_APPROVAL:
					result.add("<option value=\"LEADERS_APPROVAL\">Lãnh đạo duyệt</option>");
					break;
				case NicWorkflowProcess.PERSO:
					result.add("<option value=\"PERSO\">Cá thể hóa</option>");
					break;
				default:
					break;
			}
		}
		
		return result;
	}
	
	private int CheckingKey(String key){
		
		switch (key) {
		case NicWorkflowProcess.UPLOADED:
			return 1;
		case NicWorkflowProcess.CHECK_CPD:
			return 2;
		case NicWorkflowProcess.ASSIGNMENT:
			return 3;
		case NicWorkflowProcess.INVESTIGATION:
			return 4;
		case NicWorkflowProcess.VERIFICATION:
			return 5;
		case NicWorkflowProcess.PRESENT_APPROVAL:
			return 6;
		case NicWorkflowProcess.LEADERS_APPROVAL:
			return 7;
		case NicWorkflowProcess.PERSO:
			return 8;
		default:
			return 0;
		}
	}
	
	private String CheckingBeforeWorkflow(String key){
		switch (key) {
			case NicWorkflowProcess.CHECK_CPD:
				return NicWorkflowProcess.UPLOADED;
			case NicWorkflowProcess.ASSIGNMENT:
				return NicWorkflowProcess.CHECK_CPD;
			case NicWorkflowProcess.INVESTIGATION:
				return NicWorkflowProcess.ASSIGNMENT;
			case NicWorkflowProcess.PRESENT_APPROVAL:
				return NicWorkflowProcess.INVESTIGATION;
			case NicWorkflowProcess.LEADERS_APPROVAL:
				return NicWorkflowProcess.PRESENT_APPROVAL;
			case NicWorkflowProcess.PERSO:
				return NicWorkflowProcess.LEADERS_APPROVAL;
			default:
				return "";
		}
	}
	
	private String CheckingFirstWorkflow(String key){
		switch (key) {
			case NicWorkflowProcess.UPLOADED:
			return NicWorkflowProcess.CHECK_CPD;
			case NicWorkflowProcess.CHECK_CPD:
				return NicWorkflowProcess.ASSIGNMENT;
			case NicWorkflowProcess.ASSIGNMENT:
				return NicWorkflowProcess.INVESTIGATION;
			case NicWorkflowProcess.INVESTIGATION:
				return NicWorkflowProcess.PRESENT_APPROVAL;
			case NicWorkflowProcess.PRESENT_APPROVAL:
				return NicWorkflowProcess.LEADERS_APPROVAL;
			case NicWorkflowProcess.LEADERS_APPROVAL:
				return NicWorkflowProcess.PERSO;
			default:
				return "";
		}
	}
	
	private String[] FindEndNewCase(int step){
		
		String [] result = null;
		
		switch (step) {
		case 1:
			result = new String[] {"CHECK_CPD", "ASSIGNMENT", "INVESTIGATION", "PRESENT_APPROVAL", "LEADERS_APPROVAL", "PERSO"};
			break;
		case 2:
			result = new String[] {"ASSIGNMENT", "INVESTIGATION", "PRESENT_APPROVAL", "LEADERS_APPROVAL", "PERSO"};
			break;
		case 3:
			result = new String[] {"INVESTIGATION", "PRESENT_APPROVAL", "LEADERS_APPROVAL", "PERSO"};
			break;
		case 4:
			result = new String[] {"PRESENT_APPROVAL", "LEADERS_APPROVAL", "PERSO"};
			break;
		case 5:
			result = new String[] {"LEADERS_APPROVAL", "PERSO"};
			break;
		case 6:
			result = new String[] {"PERSO"};
			break;
		default:
			break;
		}
		
		return result;
	}
	
	private String NameWorkflow(String key){
		switch (key) {
		case NicWorkflowProcess.VERIFICATION:
			return "Xác minh";
		case NicWorkflowProcess.UPLOADED:
			return "Đồng bộ";
		case NicWorkflowProcess.CHECK_CPD:
			return "Kiểm tra CPD";
		case NicWorkflowProcess.ASSIGNMENT:
			return "Phân công";
		case NicWorkflowProcess.INVESTIGATION:
			return "Xử lý";
		case NicWorkflowProcess.PRESENT_APPROVAL:
			return "Đề xuất";
		case NicWorkflowProcess.LEADERS_APPROVAL:
			return "Lãnh đạo duyệt";
		case NicWorkflowProcess.PERSO:
			return "Cá thể hóa";
		default:
			return "";
		}
	}
}
