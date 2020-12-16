/**
 * 
 */
package com.nec.asia.nic.payment.controller;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Part;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;














import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.decisionManager.service.BusinessListService;
import com.nec.asia.nic.comp.decisionManager.service.DecisionManagerService;
import com.nec.asia.nic.comp.officalNation.service.OfficalNationService;
import com.nec.asia.nic.comp.officalVisa.service.OfficalVisaService;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.signerGovs.service.SignerGovsService;
import com.nec.asia.nic.comp.trans.domain.NicBusinessList;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicOfficalNation;
import com.nec.asia.nic.comp.trans.domain.NicOfficalVisa;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.ParaSignerCompares;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.investigation.controller.InvestigationAssignmentData;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;
import com.opensymphony.oscache.util.StringUtil;

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
@RequestMapping(value = "/decisionController")
public class DecisionController extends AbstractController  {

	@Autowired
	private OfficalVisaService officalVisaService;
	
	@Autowired
	private OfficalNationService officalNationService;
	
	@Autowired
	private BusinessListService businessListService;
	
	@Autowired
	private DecisionManagerService decisionManagerService;
	
	@Autowired
	private ParametersService parametersService;

	@Autowired
	private PaymentDefService paymentDefService;

	@Autowired
	private SignerGovsService signerGovsService;

	@Autowired
	private CodesService codesService;

	// 30 Dec 2013 (chris lai) : update with audit record
	@Autowired
	private AuditAccessLogService auditAccessLogService;

	private static final Logger logger = LoggerFactory
			.getLogger(DecisionController.class);

	//*** QUẢN LÝ QUYẾT ĐỊNH ***///
	@RequestMapping(value = "/applyFilter")
	public ModelAndView applyFilter(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getDecisionManagerList(investigationAssignmentData,
				request, httpRequest, model, 1);
	}
	
	@RequestMapping(value = "/decisionManagerList")
	public ModelAndView getDecisionManagerList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationConfirmList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getDecisionManagerList(investigationAssignmentData,
				request, httpRequest, model, pageNo);
	}
	
	
	public ModelAndView getDecisionManagerList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model, int page) {
		logger.info("NIC Admin Console -- NicDecisionManager");
		PaginatedResult<NicDecisionManager> pr = null;
		Map searchResultMap = new HashMap();
		
		if (investigationAssignmentData == null) {
			investigationAssignmentData = new InvestigationAssignmentData();
		}
		
		try {
			//int paramPageNo = 1;
			int pageSize = 10;
			//String paramListTableId = "row";
			/*
			 * try{ paramPageNo = Integer.parseInt(request.getParameter((new
			 * ParamEncoder
			 * (paramListTableId).encodeParameterName(TableTagParameters
			 * .PARAMETER_PAGE)))); } catch (Exception ex) {
			 * logger.error(ex.getMessage()); }
			 */
			List<SignerGovs> codeSigner = new ArrayList<SignerGovs>();
			SimpleDateFormat formatter2=new SimpleDateFormat("dd-MMM-yyyy");
			String pattern = "dd-MMM-yyyy";
			DateFormat df = new SimpleDateFormat(pattern);
			Date datefrom = formatter2.parse("01-Jan-1989");
			Date today = Calendar.getInstance().getTime();
			String todayAsString = df.format(today);
			Date dateto = formatter2.parse(todayAsString);
			if(!StringUtil.isEmpty(investigationAssignmentData.getCreateDate()) && investigationAssignmentData.getCreateDate() != null){
				datefrom = formatter2.parse(investigationAssignmentData.getCreateDate());
			}
			if(!StringUtil.isEmpty(investigationAssignmentData.getIssueDate()) && investigationAssignmentData.getIssueDate() != null){
				dateto = formatter2.parse(investigationAssignmentData.getIssueDate());
			}
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			pr = decisionManagerService.findAllDecisionManager("", page, pageSize,
					new AssignmentFilterAll(investigationAssignmentData
							.getSearchTransactionId(),
							investigationAssignmentData.getPriority(),
							investigationAssignmentData
									.getCurrentlyAssignedToUserId(), datefrom, dateto,
							investigationAssignmentData
									.getUnassignAllForUserUserId(),"","")
					);
			int totalRecords = 0;
			List<NicDecisionManager> decisionList = new ArrayList<NicDecisionManager>();
			if (pr != null) {
				decisionList = pr.getRows();
				totalRecords = pr.getTotal();
				int persoNo = 0;
				for (NicDecisionManager nicDecisionManager : decisionList) {
					List<NicBusinessList> a  = businessListService.findListByDecisionNumber(nicDecisionManager.getDecisionNumber());
					if(a != null)
						persoNo = a.size();
					nicDecisionManager.setNumberPerson(persoNo);
					CodeValues codeGOv = codesService
							.getCodeValueByIdName("CODE_IDGovernment",
									nicDecisionManager.getCompetentAuthorities());
					if (codeGOv != null) {
						nicDecisionManager.setCompetentAuthorities(codeGOv.getCodeValueDesc());
					}
					CodeValues codeV = codesService.getCodeValueByIdName(
							"CODE_SIGNER_GOV", nicDecisionManager.getSigner());
					if (codeV != null) {
						nicDecisionManager.setSigner(codeV.getCodeValueDesc());
					}
					
					if (nicDecisionManager.getCountryPlan() != "" && nicDecisionManager.getCountryPlan().split(",").length > 0) {
						String nameNational = "";
						int leg = nicDecisionManager.getCountryPlan().split(",").length;
						for(int i = 0; i < leg; i++){
							CodeValues codeN = codesService
									.getCodeValueByIdName("NATIONALITY",
											nicDecisionManager.getCountryPlan().split(",")[i]);
							if(codeN != null){
								nameNational += codeN.getCodeValueDesc() + ", ";
							}
						}
						nicDecisionManager.setCountryPlan(nameNational);
					}
				}
				
			}
			
			
			List<CodeValues> codeGov = codesService
					.findAllByCodeId("CODE_IDGovernment");
			request.setAttribute("codeGov", codeGov, 1);
			//investigationAssignmentData.cleanUpForNextPage();
			
			if(investigationAssignmentData
					.getCurrentlyAssignedToUserId() != null && investigationAssignmentData
							.getCurrentlyAssignedToUserId() != ""){
				String codeG = investigationAssignmentData.getCurrentlyAssignedToUserId();
				codeSigner = signerGovsService.findListSignerByCode(codeG);
				request.setAttribute("codeSigner", codeSigner, 1);
			}
			//phúc edit
			int firstResults = (page - 1) < 0 ? 0 : (page - 1) * pageSize;
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", page);
			model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
			model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", pr.getTotal());
			model.addAttribute("endIndex", firstResults + pr.getRowSize());
			model.addAttribute("jobList", decisionList);
			//end
			searchResultMap.put("totalRecords", totalRecords);
			searchResultMap.put("pageSize", pageSize);
			//model.addAttribute("jobList", decisionList);
			ModelAndView modelAndView = new ModelAndView("decisionManager.list",searchResultMap);
			modelAndView.addObject("formData", investigationAssignmentData);
			model.addAttribute("fnSelected", Constants.HEADING_NIC_DECISIONMANAGER_MATRIX);
			return modelAndView;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in /decisionManagerList", e);
			model.addAttribute("fnSelected", Constants.HEADING_NIC_DECISIONMANAGER_MATRIX);
			return null;
		}

	}
	
	@RequestMapping(value = "/addDecisionManager")
	@ExceptionHandler
	public ModelAndView addDecisionManager(WebRequest request, ModelMap model)
			throws Exception {
		logger.info("Add Decision Manager");
		NicDecisionManager form = new NicDecisionManager();
		
		List<CodeValues> codeGov = codesService
				.findAllByCodeId("CODE_IDGovernment");
		List<CodeValues> tripcostddl = codesService
				.findAllByCodeId("CODE_TripCost");
		List<CodeValues> purposeddl = codesService
				.findAllByCodeId("CODE_TripPurpose");
		
		List<CodeValues> codeNational = codesService
				.findAllByCodeId("NATIONALITY");

		request.setAttribute("codeGov", codeGov, 1);
		request.setAttribute("tripcostddl", tripcostddl, 1);
		request.setAttribute("purposeddl", purposeddl, 1);
		request.setAttribute("codeNational", codeNational, 1);

		ModelAndView mav = new ModelAndView("add-decision-manager");
		model.addAttribute("fnSelected", "ePassport Decision Manager");
		mav.addObject("decisionManagerForm", form);
		return mav;
	}
	
	@RequestMapping(value = "/createDecisionManager", method = RequestMethod.POST)
	@ResponseBody
	@ExceptionHandler
	public String createDecisionManager (WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute NicDecisionManager form)
			throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";
		Date date = new Date();
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		try {
			NicDecisionManager deci = decisionManagerService.findDecisionManagerByCode(form.getDecisionNumber());
			if (deci != null) {
				status = "duplicate";
			} else {

				String userId = "SYSTEM";
				String wkstnId = "";
				if (StringUtils.isNotBlank(userSession.getUserName())) {
					userId = userSession.getUserName();
				}
				if (StringUtils.isNotBlank(userSession.getUserName())) {
					wkstnId = userSession.getWorkstationId();
				} else {
					java.net.InetAddress localMachine = InetAddress
							.getLocalHost();
					wkstnId = localMachine.getHostName();
				}
				/*
				if (form.getFileUpload() != null) {
		            	form.setData(form.getFileUpload().getBytes());
				}*/
				/*List<MultipartFile> crunchifyFiles = form.getFileUpload();
				if (crunchifyFiles != null && crunchifyFiles.size() > 0) {
		            for (MultipartFile aFile : crunchifyFiles){
		            	form.setData(form.getFileUpload().getBytes());             
		            }
		        }*/
				
				form.setData(form.getFileUpload().getBytes());  
				form.setCreateBy(userId);
				form.setCreateDate(date);
				decisionManagerService.createDecisionManager(form);
				/*
				form.setDocData(Base64.decodeBase64(docString.getBytes()));
				form.setCreateBy(userId);
				form.setActive("Y");
				form.setCreateDate(date);
				signerGovsService.saveOrUpdate(form);*/
				status = "success";

				List<String> messages = new ArrayList<String>();
				messages.add("Thêm mới quyết định thành công");
				request.setAttribute("messages", messages, 1);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			List<String> messages = new ArrayList<String>();
			messages.add("Đã có lỗi xảy ra khi tạo quyết định mới.");
			request.setAttribute("messages", messages, 1);
			ModelAndView mav = new ModelAndView("add-decision-manager");
			model.addAttribute("fnSelected", "ePassport Decision Manager");
			mav.addObject("decisionManagerForm", form);
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Thêm mới quyết định";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}
	
	@RequestMapping(value = "/updateDecisionManager/{params}")
	@ExceptionHandler
	public ModelAndView updateDecisionManager(WebRequest request, ModelMap model, @PathVariable String params)
			throws Exception {
		logger.info("Update Decision Manager");
		NicDecisionManager form = new NicDecisionManager();
		if(params != ""){
			List<SignerGovs> codeSigner = new ArrayList<SignerGovs>();
			List<CodeValues> codeGov = codesService
					.findAllByCodeId("CODE_IDGovernment");
			List<CodeValues> tripcostddl = codesService
					.findAllByCodeId("CODE_TripCost");
			List<CodeValues> purposeddl = codesService
					.findAllByCodeId("CODE_TripPurpose");
			
			List<CodeValues> codeNational = codesService
					.findAllByCodeId("NATIONALITY");
	
			request.setAttribute("codeGov", codeGov, 1);
			request.setAttribute("tripcostddl", tripcostddl, 1);
			request.setAttribute("purposeddl", purposeddl, 1);
			request.setAttribute("codeNational", codeNational, 1);
			
			form = decisionManagerService.findDecisionManagerByCode(params);
			if(form == null)
			{
				ModelAndView mavD = new ModelAndView("decisionManager.list");
				List<String> messages = new ArrayList<String>();
				messages.add("Quyết định không tồn tại.");
				request.setAttribute("messages", messages, 1);
				return mavD;
			}
			else
			{
				if(form.getSigner() != ""){
					codeSigner = signerGovsService.findListSignerByCode(form.getCompetentAuthorities());
					request.setAttribute("codeSigner", codeSigner, 1);
				}
				
				if(form.getData() != null)
					form.setFileUpload(new String(form.getData()));
			}
		}
		ModelAndView mav = new ModelAndView("update-decision-manager");
		model.addAttribute("fnSelected", "ePassport Decision Manager");
		mav.addObject("decisionManagerForm", form);
		return mav;
	}
	
	@RequestMapping(value = "/updateDecision")
	@ResponseBody
	@ExceptionHandler
	public String updateDecision(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute NicDecisionManager form)
			throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";
		Date date = new Date();
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		try {
				
				NicDecisionManager editO = new NicDecisionManager();
				editO = decisionManagerService.findDecisionManagerByCode(form.getDecisionNumber());
				
				//TODO : Để sau check trạng thái công hàm đã duyệt/hủy thì ko cho cập nhật
				/*if(editO != null && status){
					status = "confirm";
				}else { }*/
				String userId = "SYSTEM";
				if (StringUtils.isNotBlank(userSession.getUserName())) {
					userId = userSession.getUserName();
				}
				form.setCreateBy(editO.getCreateBy());
				form.setCreateDate(editO.getCreateDate());
				form.setId(editO.getId());
				
				if(editO.getData() != null && form.getFileUpload() == null){
					form.setData(editO.getData());
				}
				else{
					form.setData(form.getFileUpload().getBytes());
				}
				editO = form;
				editO.setModifyBy(userId);
				editO.setModifyDate(date);
				synchronized (NicDecisionManager.class) {
					decisionManagerService.saveOrUpdate(editO);
				}
				status = "success";

				List<String> messages = new ArrayList<String>();
				messages.add("Sửa thông tin quyết định thành công");
				request.setAttribute("messages", messages, 1);
			
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			List<String> messages = new ArrayList<String>();
			messages.add("Đã có lỗi xảy ra khi sửa quyết định.");
			request.setAttribute("messages", messages, 1);
			ModelAndView mav = new ModelAndView("update-decision-manager");
			model.addAttribute("fnSelected", "ePassport Decision Manager");
			mav.addObject("decisionManagerForm", form);
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Cập nhật quyết định";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}
	
	@RequestMapping(value = "/deleteDecision/{params}")
	@ResponseBody
	@ExceptionHandler
	public String deleteDecision(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute String code, @PathVariable String params)
			throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";
		try {
			NicDecisionManager deleO = decisionManagerService.findDecisionManagerByCode(params);
			if (deleO != null) {
				//TODO : Để sau check trạng thái công hàm đã duyệt/hủy thì ko cho xóa
				/*if(editO != null && status){
					status = "confirm";
				}else { }*/
				List<NicBusinessList> a  = businessListService.findListByDecisionNumber(params);
				if(a != null && a.size() > 0){
					status = "duplicate";
				}
				else {
					decisionManagerService.delete(deleO);
					status = "success";
					List<String> messages = new ArrayList<String>();
					messages.add("Xóa thông tin quyết định thành công");
					request.setAttribute("messages", messages, 1);
				}
			} 
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Xóa quyết định";
				Object[] args = { null };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}
		
		return status;
	}
	
	@RequestMapping(value = "/addBusinessList/{params}")
	@ExceptionHandler
	public ModelAndView addBusinessList(WebRequest request, ModelMap model,@PathVariable String params)
			throws Exception {
		logger.info("Add BusinessList");
		List<NicBusinessList> formLs = new ArrayList<NicBusinessList>();
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		NicBusinessList form = new NicBusinessList();
		//Xử lý tìm bản ghi business theo số quyết định
		ModelAndView mav = new ModelAndView("add-business-list");
		mav.addObject("businessListForm", form);
		if(params != ""){
			NicDecisionManager deci = decisionManagerService.findDecisionManagerByCode(params);
			if(deci == null)
			{
				ModelAndView mavD = new ModelAndView("decisionManager.list");
				List<String> messages = new ArrayList<String>();
				messages.add("Quyết định không tồn tại. Vui lòng tạo mới quyết định trước");
				request.setAttribute("messages", messages, 1);
				return mavD;
			}
			PaginatedResult<NicBusinessList> result = businessListService.findListByDecisionNumber1(params, pageNo, pageSize);
			formLs = result.getRows();
			for (NicBusinessList obj : formLs) {
				CodeValues codeGOv = codesService.getCodeValueByIdName(
						"CODE_IDGovernment", obj.getAgency());
				if (codeGOv != null) {
					obj.setAgency(codeGOv.getCodeValueDesc());
				}
			}
			List<CodeValues> codeGov = codesService
					.findAllByCodeId("CODE_IDGovernment");
			
			List<CodeValues> pobddl = codesService
					.findAllByCodeId("CODE_BirthPlace");
			
			request.setAttribute("codeGov", codeGov, 1);
			request.setAttribute("pobddl", pobddl, 1);
			model.addAttribute("fnSelected", "ePassport Business List");
			model.addAttribute("decisionNo", params);
			model.addAttribute("listBusiness", formLs);
			//model.addAttribute("jobList", formLs);
			//phúc edit
			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("totalPage", pagingUtil.totalPage(result.getTotal(), pageSize));
			model.addAttribute("startIndex", result.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", result.getTotal());
			model.addAttribute("endIndex", firstResults + result.getRowSize());
			model.addAttribute("jobList", formLs);
			//end	
			model.addAttribute("totalRecords", formLs.size());
			
		}
		return mav;
	}
	
	@RequestMapping(value = "/createBusinessList")
	@ResponseBody
	@ExceptionHandler
	public String createBusinessList(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute NicBusinessList form)
			throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";
		Date date = new Date();
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		try {
			NicBusinessList busi = businessListService.findBySerial(form.getSerial(),form.getDecisionNumber());
			//TODO : Để sau check trạng thái công hàm đã duyệt/hủy thì ko cho xóa
			/*if(editO != null && status){
				status = "confirm";
			}else { }*/
			if (busi != null) {
				status = "duplicate";
			} else {

				String userId = "SYSTEM";
				String wkstnId = "";
				if (StringUtils.isNotBlank(userSession.getUserName())) {
					userId = userSession.getUserName();
				}
				if (StringUtils.isNotBlank(userSession.getUserName())) {
					wkstnId = userSession.getWorkstationId();
				} else {
					java.net.InetAddress localMachine = InetAddress
							.getLocalHost();
					wkstnId = localMachine.getHostName();
				}
				
				form.setCreateBy(userId);
				form.setCreateDate(date);
				businessListService.createBusinessList(form);
				status = "success";
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			List<String> messages = new ArrayList<String>();
			messages.add("Đã có lỗi xảy ra khi tạo người đi công tác.");
			request.setAttribute("messages", messages, 1);
			ModelAndView mav = new ModelAndView("add-business-list");
			model.addAttribute("fnSelected", "ePassport Business List");
			mav.addObject("businessListForm", form);
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Thêm mới người cử đi công tác";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}
	
	@RequestMapping(value = "/findBusinessList", method = RequestMethod.POST)
	public @ResponseBody NicBusinessList findBusinessList(@RequestParam("id") int id, @RequestParam(value = "num") String deNum){
		try{
			NicBusinessList busi = businessListService.findBySerial(id , deNum);
			if(busi != null){
				long timestamp = busi.getDob().getTime(); //Example -> in ms
				Date d = new Date(timestamp);
				busi.setDateF(new SimpleDateFormat("dd/MM/yyyy").format(d));
				return busi;
			}
		}catch(Exception e){
			
		}
		return null;
	}
	
	@RequestMapping(value = "/updateBusinessList")
	@ResponseBody
	@ExceptionHandler
	public String updateBusinessList(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute NicBusinessList form)
			throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";
		Date date = new Date();
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		try {
				//TODO : Để sau check trạng thái công hàm đã duyệt/hủy thì ko cho sửa
				/*if(editO != null && status){
					status = "confirm";
				}else { }*/
				NicBusinessList busi = new NicBusinessList();
				busi =	businessListService.findBySerial(form.getSerial(),form.getDecisionNumber());
				String userId = "SYSTEM";
				//String wkstnId = "";
				if (StringUtils.isNotBlank(userSession.getUserName())) {
					userId = userSession.getUserName();
				}
				form.setCreateBy(busi.getCreateBy());
				form.setCreateDate(busi.getCreateDate());
				form.setId(busi.getId());
				busi = form;
				busi.setModifyBy(userId);
				busi.setModifyDate(date);
				synchronized (NicBusinessList.class) {
					businessListService.saveOrUpdate(busi);
				}
				status = "success";
			
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			List<String> messages = new ArrayList<String>();
			messages.add("Đã có lỗi xảy ra khi sửa thông tin người đi công tác.");
			request.setAttribute("messages", messages, 1);
			ModelAndView mav = new ModelAndView("add-business-list");
			model.addAttribute("fnSelected", "ePassport Business List");
			mav.addObject("businessListForm", form);
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Cập nhật người cử đi công tác";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}
	
	@RequestMapping(value = "/deleteBusinessList/{params}/{codes}")
	@ResponseBody
	@ExceptionHandler
	public String deleteBusinessList(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model, @PathVariable int params,
			@PathVariable String codes)
			throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";
		try {
			NicBusinessList busi = businessListService.findBySerial(params,codes);
			if (busi != null) {
				//TODO : Để sau check trạng thái công hàm đã duyệt/hủy thì ko cho xóa
				/*if(editO != null && status){
					status = "confirm";
				}else { }*/
			
				businessListService.delete(busi);
				status = "success";
				List<String> messages = new ArrayList<String>();
				messages.add("Xóa thông tin Người cử đi thành công");
				request.setAttribute("messages", messages, 1);
			} 
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Xóa người cử đi công tác";
				Object[] args = { null };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}
		
		return status;
	}
	
	@RequestMapping(value = "/signerList", method = RequestMethod.GET)	
	public @ResponseBody List<SignerGovs> signerList(HttpServletRequest request){
		String id = request.getParameter("id");
		List<SignerGovs> l = new ArrayList<SignerGovs>();
		if(id != null && id != ""){
			id = id.replace("\"", "");
			l = signerGovsService.findListSignerByCode(id);
			if(l != null){
				for(SignerGovs ob : l){
					if(!ob.getActive().equals("Y")){
						l.remove(ob);
					}
				}
			}
		}
		return l;
	}
	
	//Chọn cơ quan thẩm quyền xem có tiếng anh
	@RequestMapping(value = "/writePositionEng", method = RequestMethod.GET)	
	public @ResponseBody String writePositionEng(HttpServletRequest request){
		String positionEngId = request.getParameter("id");
		//String positionEngCode = request.getParameter("code");
		if(positionEngId != null && positionEngId != ""){
			CodeValues idGover = codesService
					.getCodeValueByIdName("CODE_IDGovernment",
							positionEngId);
			if(idGover != null){
				if(idGover.getId().getCodeValue().equals(positionEngId)){
					CodeValues idGoverEng = codesService
							.getCodeValueByIdName("CODE_IDGovernment_Eng",
									idGover.getParentCodeValue());
					if(idGoverEng != null){

						return idGoverEng.getCodeValueDesc();
					}
				}

			}
		}
		return "";
	}
}
