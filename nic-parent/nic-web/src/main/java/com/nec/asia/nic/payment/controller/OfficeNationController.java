/**
 * 
 */
package com.nec.asia.nic.payment.controller;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@RequestMapping(value = "/officeNationController")
public class OfficeNationController extends AbstractController  {

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
			.getLogger(OfficeNationController.class);

	//*** QUẢN LÝ CÔNG HÀM ***///
	
	@RequestMapping(value = "/applyFilter")
	public ModelAndView applyFilter(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");
		
		return this.getOfficalNationList(investigationAssignmentData,
				request, httpRequest, model, 1);
	}
	
	@RequestMapping(value = "/officalNationList")
	public ModelAndView getOfficalNationList(
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
		return this.getOfficalNationList(investigationAssignmentData,
				request, httpRequest, model, pageNo);
	}
	
	
		
		public ModelAndView getOfficalNationList(
				@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
				WebRequest request, HttpServletRequest httpRequest, ModelMap model, int pageNo) {
			logger.info("NIC Admin Console -- NicOfficalNation");
			PaginatedResult<NicOfficalNation> pr = null;
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
				pageSize = Constants.PAGE_SIZE_DEFAULT;
				investigationAssignmentData.getCurrentlyAssignedToUserId().replace(",", "");
				pr = officalNationService.findAllOfficalNation("", pageNo, pageSize,
						new AssignmentFilterAll(investigationAssignmentData.getSearchTransactionId(),
										null, investigationAssignmentData.getCurrentlyAssignedToUserId(), 
										null, null,"","",""));
				int totalRecords = 0;
				List<NicOfficalNation> officalList = new ArrayList<NicOfficalNation>();
				if (pr != null) {
					officalList = pr.getRows();
					totalRecords = pr.getTotal();
					for (NicOfficalNation nicOfficalNation : officalList) {
						
						switch(nicOfficalNation.getStatus()){
							case "REGISTRATION":nicOfficalNation.setStatusS("Khởi tạo/ lưu tạm");break;
							case "PROCESSING":nicOfficalNation.setStatusS("Chờ xử lý (Giai đoạn lấy thị thực)");break;
							case "VERIFY":nicOfficalNation.setStatusS("Xác nhận");break;
							case "RECEIVED":nicOfficalNation.setStatusS("Đã nhận");break;
							case "REFUSE":nicOfficalNation.setStatusS("Từ chối của quản trị hệ thống");break;
							case "REFUSE_DSQ":nicOfficalNation.setStatusS("Từ chối từ đại sứ quán");break;
							case "REFUSE_CLS":nicOfficalNation.setStatusS("Từ chối từ cục lãnh sự");break;
							case "NATION_VISA":nicOfficalNation.setStatusS("Không cần cấp do đã có miễn thị thực Quốc gia");break;
						}
						
						List<NicBusinessList> aL  = businessListService.findAll();
						if(aL != null){
							for(NicBusinessList a : aL){
								if(a.getId() == nicOfficalNation.getBusinessID()){
									nicOfficalNation.setFullname(a.getFullname());
								}
							}
						}
						CodeValues codeN = codesService
								.getCodeValueByIdName("NATIONALITY",
										nicOfficalNation.getNationCode());
						if(codeN != null){
							nicOfficalNation.setNationCode(codeN.getCodeValueDesc());
						}
					}
				}
				investigationAssignmentData.cleanUpForNextPage();
				searchResultMap.put("totalRecords", totalRecords);
				searchResultMap.put("pageSize", pageSize);
				//model.addAttribute("jobList", officalList);
				//phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("jobList", pr.getRows());
				//end
				model.addAttribute("fnSelected", Constants.HEADING_NIC_OFFICALNATION_MATRIX);
				ModelAndView modelAndView = new ModelAndView("officalNation.list", searchResultMap);
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;

			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("fnSelected", Constants.HEADING_NIC_OFFICALNATION_MATRIX);
				logger.error("Error in /officalNationList", e);
				return null;
			}
		}
		
		@RequestMapping(value = "/addOfficalNation")
		@ExceptionHandler
		public ModelAndView addOfficalNation(WebRequest request, ModelMap model)
				throws Exception {
			logger.info("Add Offical Nation");
			NicOfficalNation form = new NicOfficalNation();
			List<NicDecisionManager> decisionList = decisionManagerService.findAll();
			
			List<CodeValues> codeNational = codesService
					.findAllByCodeId("NATIONALITY");
			List<CodeValues> issPlddl = codesService
					.findAllByCodeId("CODE_COQUANBNG");

			request.setAttribute("issPlddl", issPlddl, 1);
			request.setAttribute("decisionList", decisionList, 1);
			request.setAttribute("codeNational", codeNational, 1);

			ModelAndView mav = new ModelAndView("add-offical-nation");
			model.addAttribute("fnSelected", Constants.HEADING_NIC_OFFICALNATION_MATRIX);
			mav.addObject("officalNationForm", form);
			return mav;
		}
		
		@RequestMapping(value = "/createOfficalNation")
		@ResponseBody
		@ExceptionHandler
		public String createOfficalNation(WebRequest request,
				HttpServletRequest httpRequest, ModelMap model,
				@ModelAttribute NicOfficalNation form)
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
				NicOfficalNation deci = officalNationService.findOfficalNationByCode(form.getDecisionNumber());
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
					
					form.setStatus("REGISTRATION");
					form.setCreateBy(userId);
					form.setCreateDate(date);
					officalNationService.createOfficalNation(form);
					/*
					form.setDocData(Base64.decodeBase64(docString.getBytes()));
					form.setCreateBy(userId);
					form.setActive("Y");
					form.setCreateDate(date);
					signerGovsService.saveOrUpdate(form);*/
					status = "success";

					List<String> messages = new ArrayList<String>();
					messages.add("Thêm mới công hàm thành công");
					request.setAttribute("messages", messages, 1);
				}
			} catch (Exception exp) {
				exp.printStackTrace();
				status = "fail";
				List<String> messages = new ArrayList<String>();
				messages.add("Đã có lỗi xảy ra khi tạo công hàm mới.");
				request.setAttribute("messages", messages, 1);
				ModelAndView mav = new ModelAndView("add-offical-nation");
				model.addAttribute("fnSelected", Constants.HEADING_NIC_OFFICALNATION_MATRIX);
				mav.addObject("officalNationForm", form);
				// 30 Dec 2013 (chris lai) : update with audit record
				throwable = exp;

				// return mav;
			} finally {
				// 30 Dec 2013 (chris lai) : update with audit record
				try {
					String functionName = "Thêm mới công hàm";
					Object[] args = { form };
					long timeMs = System.currentTimeMillis() - startTimeMs;
					auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
							functionName, args, throwable, timeMs);
				} catch (Exception exp) {
				}
			}

			return status;
		}
		
		@RequestMapping(value = "/updateOfficalNation/{params}")
		@ExceptionHandler
		public ModelAndView updateNationVisa(WebRequest request, ModelMap model, @PathVariable String params)
				throws Exception {
			logger.info("Update Offical Nation");
			NicOfficalNation form = new NicOfficalNation();
			if(params != ""){
				form = officalNationService.findOfficalNationByCode(params);
				if(form == null)
				{
					ModelAndView mavD = new ModelAndView("officalNation.list");
					List<String> messages = new ArrayList<String>();
					messages.add("Công hàm không tồn tại.");
					request.setAttribute("messages", messages, 1);
					mavD.addObject("formData", new InvestigationAssignmentData());
					model.addAttribute("message", messages);
					return mavD;
				}
				List<NicDecisionManager> decisionList = decisionManagerService.findAll();
				List<NicBusinessList> businessList = businessListService.findListByDecisionNumber(form.getDecisionNumber());
				List<CodeValues> codeNational = codesService
						.findAllByCodeId("NATIONALITY");
				List<CodeValues> issPlddl = codesService
						.findAllByCodeId("CODE_COQUANBNG");
				request.setAttribute("decisionList", decisionList, 1);
				request.setAttribute("issPlddl", issPlddl, 1);
				request.setAttribute("codeNational", codeNational, 1);
				request.setAttribute("businessList", businessList, 1);
			}
			ModelAndView mav = new ModelAndView("update-offical-nation");
			model.addAttribute("fnSelected", Constants.HEADING_NIC_OFFICALNATION_MATRIX);
			mav.addObject("officalNationForm", form);
			return mav;
		}
		
		@RequestMapping(value = "/updateNationV")
		@ResponseBody
		@ExceptionHandler
		public String updateNationV(WebRequest request,
				HttpServletRequest httpRequest, ModelMap model,
				@ModelAttribute NicOfficalNation form)
				throws Exception {
			// 30 Dec 2013 (chris lai) : update with audit record
			long startTimeMs = System.currentTimeMillis();
			Throwable throwable = null;
			String status = "";
			Date date = new Date();
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			try {
					
					NicOfficalNation editO = new NicOfficalNation();
					editO = officalNationService.findOfficalNationByCode(form.getOfficalNationNo());
					
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
					if(editO.getPassportType() != null && form.getPassportType() == null)
					form.setPassportType(editO.getPassportType());
					form.setId(editO.getId());
					editO = form;
				/*	if(editO.getData() != null && form.getData() == null){
						editO.setData(editO.getData());
					}
					if(form.getData() == null)
						form.setData(editO.getData());*/
					
					editO.setModifyBy(userId);
					editO.setModifyDate(date);
					synchronized (NicDecisionManager.class) {
						officalNationService.saveOrUpdate(editO);
					}
					status = "success";

					List<String> messages = new ArrayList<String>();
					messages.add("Sửa thông tin công hàm thành công");
					request.setAttribute("messages", messages, 1);
				
			} catch (Exception exp) {
				exp.printStackTrace();
				status = "fail";
				List<String> messages = new ArrayList<String>();
				messages.add("Đã có lỗi xảy ra khi sửa công hàm.");
				request.setAttribute("messages", messages, 1);
				ModelAndView mav = new ModelAndView("update-offical-nation");
				model.addAttribute("fnSelected", Constants.HEADING_NIC_OFFICALNATION_MATRIX);
				mav.addObject("officalNationForm", form);
				// 30 Dec 2013 (chris lai) : update with audit record
				throwable = exp;

				// return mav;
			} finally {
				// 30 Dec 2013 (chris lai) : update with audit record
				try {
					String functionName = "Cập nhật công hàm";
					Object[] args = { form };
					long timeMs = System.currentTimeMillis() - startTimeMs;
					auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
							functionName, args, throwable, timeMs);
				} catch (Exception exp) {
				}
			}

			return status;
		}
		
		@RequestMapping(value = "/deleteOfficalNation/{params}")
		@ResponseBody
		@ExceptionHandler
		public String deleteOfficalNation(WebRequest request,
				HttpServletRequest httpRequest, ModelMap model, @PathVariable String params)
				throws Exception {
			// 30 Dec 2013 (chris lai) : update with audit record
			long startTimeMs = System.currentTimeMillis();
			Throwable throwable = null;
			String status = "";
			try {
				NicOfficalNation deleO = officalNationService.findOfficalNationByCode(params);
				
				if(deleO == null){
					status = "notfound";
				}
				else if(!deleO.getStatus().equals("REGISTRATION")){
					status = "duplicate";
				}
				else {
					officalNationService.delete(deleO);
					status = "success";
					List<String> messages = new ArrayList<String>();
					messages.add("Xóa thông tin công hàm thành công");
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
					String functionName = "Xóa công hàm";
					Object[] args = { null };
					long timeMs = System.currentTimeMillis() - startTimeMs;
					auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
							functionName, args, throwable, timeMs);
				} catch (Exception exp) {
				}
			}
			
			return status;
		}
		
		@RequestMapping(value = "/verifyOfficalNation/{params}")
		@ResponseBody
		@ExceptionHandler
		public String verifyOfficalNation(WebRequest request,
				HttpServletRequest httpRequest, ModelMap model, @PathVariable String params)
				throws Exception {
			// 30 Dec 2013 (chris lai) : update with audit record
			long startTimeMs = System.currentTimeMillis();
			Throwable throwable = null;
			String status = "";
			try {
				NicOfficalNation deleO = officalNationService.findOfficalNationByCode(params);
				if(deleO == null){
					status = "notfound";
				}
				else if(!deleO.getStatus().equals("REGISTRATION")){
					status = "duplicate";
				}
				else {
					deleO.setStatus("VERIFY");
					officalNationService.saveOrUpdate(deleO);
					status = "success";
					List<String> messages = new ArrayList<String>();
					messages.add("Xóa thông tin công hàm thành công");
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
					String functionName = "Xóa công hàm";
					Object[] args = { null };
					long timeMs = System.currentTimeMillis() - startTimeMs;
					auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
							functionName, args, throwable, timeMs);
				} catch (Exception exp) {
				}
			}
			
			return status;
		}
		@RequestMapping(value = "/sendingOfficalNation/{params}")
		@ResponseBody
		@ExceptionHandler
		public String sendingOfficalNation(WebRequest request,
				HttpServletRequest httpRequest, ModelMap model, @PathVariable String params)
				throws Exception {
			// 30 Dec 2013 (chris lai) : update with audit record
			long startTimeMs = System.currentTimeMillis();
			Throwable throwable = null;
			String status = "";
			try {
				NicOfficalNation deleO = officalNationService.findOfficalNationByCode(params);
				if(deleO == null){
					status = "notfound";
				}
				else if(!deleO.getStatus().equals("VERIFY")){
					status = "duplicate";
				}
				else {
					deleO.setStatus("PROCESSING");
					officalNationService.saveOrUpdate(deleO);
					status = "success";
					List<String> messages = new ArrayList<String>();
					messages.add("Xóa thông tin công hàm thành công");
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
					String functionName = "officalNationSending";
					Object[] args = { null };
					long timeMs = System.currentTimeMillis() - startTimeMs;
					auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
							functionName, args, throwable, timeMs);
				} catch (Exception exp) {
				}
			}
			
			return status;
		}
		@RequestMapping(value = "/nationTTOfficalNation/{params}")
		@ResponseBody
		@ExceptionHandler
		public String nationTTOfficalNation(WebRequest request,
				HttpServletRequest httpRequest, ModelMap model, @PathVariable String params)
				throws Exception {
			// 30 Dec 2013 (chris lai) : update with audit record
			long startTimeMs = System.currentTimeMillis();
			Throwable throwable = null;
			String status = "";
			try {
				NicOfficalNation deleO = officalNationService.findOfficalNationByCode(params);
				if(deleO == null){
					status = "notfound";
				}
				else if(!deleO.getStatus().equals("VERIFY")){
					status = "duplicate";
				}
				else {
					deleO.setStatus("NATION_VISA");
					officalNationService.saveOrUpdate(deleO);
					status = "success";
					List<String> messages = new ArrayList<String>();
					messages.add("Xóa thông tin công hàm thành công");
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
					String functionName = "officalNationNationTT";
					Object[] args = { null };
					long timeMs = System.currentTimeMillis() - startTimeMs;
					auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
							functionName, args, throwable, timeMs);
				} catch (Exception exp) {
				}
			}
			
			return status;
		}
		
		@RequestMapping(value = "/cancelOfficalNation/{params}")
		@ResponseBody
		@ExceptionHandler
		public String cancelOfficalNation(WebRequest request,
				HttpServletRequest httpRequest, ModelMap model, @PathVariable String params)
				throws Exception {
			// 30 Dec 2013 (chris lai) : update with audit record
			long startTimeMs = System.currentTimeMillis();
			Throwable throwable = null;
			String status = "";
			try {
				NicOfficalNation deleO = officalNationService.findOfficalNationByCode(params);
				if(deleO == null){
					status = "notfound";
				}
				else if(!deleO.getStatus().equals("VERIFY")){
					status = "duplicate";
				}
				else {
					deleO.setStatus("REFUSE");
					officalNationService.saveOrUpdate(deleO);
					status = "success";
					List<String> messages = new ArrayList<String>();
					messages.add("Xóa thông tin công hàm thành công");
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
					String functionName = "Cập nhật công hàm";
					Object[] args = { null };
					long timeMs = System.currentTimeMillis() - startTimeMs;
					auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
							functionName, args, throwable, timeMs);
				} catch (Exception exp) {
				}
			}
			
			return status;
		}
		
	@RequestMapping(value = "/personList", method = RequestMethod.GET)	
	public @ResponseBody List<NicBusinessList> personList(HttpServletRequest request, @RequestParam("id") String id){
		//String id = request.getParameter("id");
		if(id != null && id != ""){
			List<NicBusinessList> l = businessListService.findListByDecisionNumber(id);
			return l;
		}
		return null;
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
	
	//*** QUẢN LÝ QUỐC GIA MIỄN THỊ THỰC ***///
	@RequestMapping(value = "/officalVisaList")
	public ModelAndView getOfficalVisaList(
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Admin Console -- NicOfficalVisa");
		PaginatedResult<NicOfficalVisa> pr = null;
		Map searchResultMap = new HashMap();
		try {
			int paramPageNo = 1;
			int pageSize = 10;
			String paramListTableId = "row";
			/*
			 * try{ paramPageNo = Integer.parseInt(request.getParameter((new
			 * ParamEncoder
			 * (paramListTableId).encodeParameterName(TableTagParameters
			 * .PARAMETER_PAGE)))); } catch (Exception ex) {
			 * logger.error(ex.getMessage()); }
			 */
			pr = officalVisaService.findAllOfficalVisa("", paramPageNo, pageSize);
			int totalRecords = 0;
			List<NicOfficalVisa> officalList = new ArrayList<NicOfficalVisa>();
			if (pr != null) {
				officalList = pr.getRows();
				for (NicOfficalVisa nicOfficalVisa : officalList) {

					switch(nicOfficalVisa.getStatus()){
					case 1: nicOfficalVisa.setStatusS("Hoạt động");break;
					case 0: nicOfficalVisa.setStatusS("Tạm khóa");break;
					}

					switch(nicOfficalVisa.getPassportType()){
					case "P": nicOfficalVisa.setPassportType("Phổ thông");break;
					case "PD": nicOfficalVisa.setPassportType("Ngoại giao");break;
					case "PO": nicOfficalVisa.setPassportType("Công vụ");break;
					}

					CodeValues codeN = codesService
							.getCodeValueByIdName("NATIONALITY",
									nicOfficalVisa.getCountryCode());
					if(codeN != null){
						nicOfficalVisa.setCountryCode(codeN.getCodeValueDesc());
					}
				}
			}
			searchResultMap.put("totalRecords", totalRecords);
			model.addAttribute("jobList", officalList);
			searchResultMap.put("pageSize", pageSize);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in /officalVisaList", e);
		}

		model.addAttribute("fnSelected", Constants.HEADING_NIC_OFFICALVISA_MATRIX);
		return new ModelAndView("officalVisa.list", searchResultMap);
	}

	@RequestMapping(value = "/addOfficalVisa")
	@ExceptionHandler
	public ModelAndView addOfficalVisa(WebRequest request, ModelMap model)
			throws Exception {
		logger.info("Add Offical Visa");
		NicOfficalVisa form = new NicOfficalVisa();
		List<CodeValues> codeNational = codesService
				.findAllByCodeId("NATIONALITY");

		request.setAttribute("codeNational", codeNational, 1);

		ModelAndView mav = new ModelAndView("add-offical-visa");
		model.addAttribute("fnSelected", "ePassport Offical Visa");
		mav.addObject("officalVisaForm", form);
		return mav;
	}

	@RequestMapping(value = "/createOfficalVisa")
	@ResponseBody
	@ExceptionHandler
	public String createOfficalVisa(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute NicOfficalVisa form)
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
			NicOfficalVisa deci = officalVisaService.findOfficalVisaByCode(form.getCountryCode(), form.getPassportType());
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
				
				if(!form.getTypeS().equals("")){
					if(form.getTypeS().equals("1"))
						form.setType(1);
					else
						form.setType(2);
				}
				form.setStatus(1);
				form.setCreateBy(userId);
				form.setCreateDate(date);
				officalVisaService.createOfficalVisa(form);
				/*
						form.setDocData(Base64.decodeBase64(docString.getBytes()));
						form.setCreateBy(userId);
						form.setActive("Y");
						form.setCreateDate(date);
						signerGovsService.saveOrUpdate(form);*/
				status = "success";

				List<String> messages = new ArrayList<String>();
				messages.add("Thêm mới quốc gia miễn thi thực thành công");
				request.setAttribute("messages", messages, 1);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			List<String> messages = new ArrayList<String>();
			messages.add("Đã có lỗi xảy ra khi tạo quốc gia miễn thị thực mới.");
			request.setAttribute("messages", messages, 1);
			ModelAndView mav = new ModelAndView("add-offical-visa");
			model.addAttribute("fnSelected", "ePassport Offical Visa");
			mav.addObject("officalVisaForm", form);
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Thêm mới quốc gia miễn thị thực";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}

	@RequestMapping(value = "/updateOfficalVisa/{params}")
	@ExceptionHandler
	public ModelAndView updateOfficalVisa(WebRequest request, ModelMap model, @PathVariable Long params)
			throws Exception {
		logger.info("Update Offical Nation");
		NicOfficalVisa form = new NicOfficalVisa();
		if(params > 0){
			form = officalVisaService.findById(params);
			if(form == null)
			{
				ModelAndView mavD = new ModelAndView("officalVisa.list");
				List<String> messages = new ArrayList<String>();
				messages.add("Quốc gia miễn thị thực không tồn tại.");
				request.setAttribute("messages", messages, 1);
				return mavD;
			}

			if(form.getType() > 0){
				if(form.getType() == 1)
					form.setTypeS("1");
				else
					form.setTypeS("2");
			}
			List<CodeValues> codeNational = codesService
					.findAllByCodeId("NATIONALITY");

			request.setAttribute("codeNational", codeNational, 1);
		}
		ModelAndView mav = new ModelAndView("update-offical-visa");
		model.addAttribute("fnSelected", "ePassport Offical Visa");
		mav.addObject("officalVisaForm", form);
		return mav;
	}

	@RequestMapping(value = "/updateNationVisaA")
	@ResponseBody
	@ExceptionHandler
	public String updateNationVisaA(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute NicOfficalVisa form)
					throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";
		Date date = new Date();
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		try {

			NicOfficalVisa editO = new NicOfficalVisa();
			editO = officalVisaService.findById(form.getId());

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
			if(form.getTypeS() != ""){
				if(form.getTypeS().equals("1"))
					form.setType(1);
				else
					form.setType(2);
			}
			
			editO = form;
			/*	if(editO.getData() != null && form.getData() == null){
							editO.setData(editO.getData());
						}
						if(form.getData() == null)
							form.setData(editO.getData());*/

			editO.setLastModifiedBy(userId);
			editO.setLastModifiedTime(date);
			synchronized (NicDecisionManager.class) {
				officalVisaService.saveOrUpdate(editO);
			}
			status = "success";

			List<String> messages = new ArrayList<String>();
			messages.add("Sửa thông tin quốc gia miễn thị thực thành công");
			request.setAttribute("messages", messages, 1);

		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			List<String> messages = new ArrayList<String>();
			messages.add("Đã có lỗi xảy ra khi sửa quốc gia miễn thị thực.");
			request.setAttribute("messages", messages, 1);
			ModelAndView mav = new ModelAndView("update-offical-visa");
			model.addAttribute("fnSelected", "ePassport Offical Visa");
			mav.addObject("officalVisaForm", form);
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Cập nhật quốc gia miễn thị thực";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}

	@RequestMapping(value = "/deleteOfficalVisa/{params}")
	@ResponseBody
	@ExceptionHandler
	public String deleteOfficalVisa(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model, @PathVariable Long params)
					throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";
		try {
			NicOfficalVisa deleO = officalVisaService.findById(params);

			if(deleO == null){
				status = "notfound";
			}
			else {
				officalVisaService.delete(deleO);
				status = "success";
				List<String> messages = new ArrayList<String>();
				messages.add("Xóa thông tin QG miễn thị thực thành công");
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
				String functionName = "Xóa quốc gia miễn thị thực";
				Object[] args = { null };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}
	
	/*Kiểm tra dữ liệu quốc gia miễn thị thực có tồn tại không*/
	@RequestMapping(value = "/checkNationVisa", method = RequestMethod.GET)	
	public @ResponseBody NicOfficalVisa checkNationVisa(HttpServletRequest request){
		String countryCode = request.getParameter("id");
		String passportType = request.getParameter("code");
		if(countryCode != null && countryCode != "" && passportType != null && passportType != ""){
			NicOfficalVisa l = officalVisaService.findOfficalVisaByCode(countryCode, passportType);
			if(l != null && l.getStatus() == 1)
				return l;
		}
		return null;
	}
	
	
	/*Chọn cơ quan thẩm quyền xem có tiếng anh*/
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
