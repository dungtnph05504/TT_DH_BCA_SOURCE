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
import com.nec.asia.nic.util.Constants;
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
@RequestMapping(value = "/paymentMatrixController")
public class PaymentMatrixController extends AbstractController  {

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
			.getLogger(PaymentMatrixController.class);

	@RequestMapping(value = "/paymentMatrix")
	@ExceptionHandler
	public ModelAndView paymentDefList(WebRequest request, ModelMap model,
			@ModelAttribute PaymentDef form) throws Throwable {
		logger.info("NIC Admin Console -- Payment Matrix");
		PaginatedResult<Parameters> pr = null;
		PaginatedResult<PaymentDef> paymentPageResult = null;
		Map<String, Object> searchResultMap = new HashMap<String, Object>();
		try {
			int paramPageNo = 1;
			int paymentPageNo = 1;
			int pageSize = 10;
			String paramScope = "PAYMENT";
			String paramListTableId = "row";
			String paymentListTableId = "rowPaymentList";
			PaymentDef paymentDef = new PaymentDef();
			try {
				paramPageNo = Integer
						.parseInt(request.getParameter((new ParamEncoder(
								paramListTableId)
								.encodeParameterName(TableTagParameters.PARAMETER_PAGE))));
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			try {
				paymentPageNo = Integer
						.parseInt(request.getParameter((new ParamEncoder(
								paymentListTableId)
								.encodeParameterName(TableTagParameters.PARAMETER_PAGE))));
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
			pr = parametersService.findAllForPaginationByScope(paramScope,
					paramPageNo, pageSize);

			List<PaymentDef> paymentList = new ArrayList<PaymentDef>();
			List<Parameters> paramList = new ArrayList<Parameters>();

			int totalRecords = 0;
			if (pr != null) {
				paramList = pr.getRows();
				totalRecords = pr.getTotal();
			}

			paymentPageResult = paymentDefService.findAllForPagination(null,
					paymentPageNo, pageSize);
			int totalPaymentRecords = 0;
			if (paymentPageResult != null) {
				paymentList = paymentPageResult.getRows();
				totalPaymentRecords = paymentPageResult.getTotal();
			}

			if (request.getAttribute("status", 1) != null) {
				paymentDef
						.setStatus((String) request.getAttribute("status", 1));
				request.setAttribute("status", "empty", 1);
				paymentDef.setTransactionType(form.getTransactionType());
			}

			model.addAttribute("paymentParamForm", new Parameters());
			model.addAttribute("paymentMatrixDefForm", paymentDef);
			model.addAttribute("jobList1", paramList);
			model.addAttribute("jobList2", paymentList);
			searchResultMap.put("paramList", paramList);
			searchResultMap.put("totalRecords", totalRecords);
			searchResultMap.put("paymentList", paymentList);
			searchResultMap.put("totalPaymentRecords", totalPaymentRecords);
			searchResultMap.put("pageSize", pageSize);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in /paymentMatrix", e);
		}

		model.addAttribute("fnSelected", Constants.HEADING_NIC_PAYMENT_MATRIX);
		return new ModelAndView("payment-matrix", searchResultMap);
	}

	@RequestMapping(value = "/paymentMatrixUpdate")
	@ResponseBody
	@ExceptionHandler
	public String paymentMatrixUpdate(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute PaymentDef form) throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";

		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		try {
			String userId = "SYSTEM";
			String wkstnId = "";
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				userId = userSession.getUserName();
			}
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				wkstnId = userSession.getWorkstationId();
			} else {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName();
			}

			PaymentDefId paymentDefId = new PaymentDefId();
			paymentDefId.setNoOfTimeLost(form.getNoOfTimeLost());
			paymentDefId.setTransactionType(form.getTransactionType());
			paymentDefId.setTransactionSubtype(form.getTransactionSubtype());

			PaymentDef orgPaymentDef = paymentDefService.findById(paymentDefId);
			if (orgPaymentDef != null) {
				orgPaymentDef.setFeeAmount(form.getFeeAmount());
				orgPaymentDef.setReduceRateFlag(form.getReduceRateFlag());
				orgPaymentDef.setWaivableFlag(form.getWaivableFlag());
				orgPaymentDef.setDeleteFlag(form.getDeleteFlag());
				orgPaymentDef.setUpdateBy(userId);
				orgPaymentDef.setUpdateDateTime(DateUtil.getSystemTimestamp());
				orgPaymentDef.setUpdateWkstnId(wkstnId);
				paymentDefService.saveOrUpdate(orgPaymentDef);
				status = "success";
				form.setStatus(status);
				List<String> messages = new ArrayList<String>();
				messages.add("Payment Matrix:" + form.getTransactionType()
						+ " saved successfully");
				request.setAttribute("messages", messages, 1);
			} else {
				logger.info("Payment Matrix does not exists with Transaction Type: "
						+ form.getTransactionType()
						+ ", Transaction Subtype"
						+ form.getTransactionSubtype()
						+ ", No. Of Card Lost:"
						+ form.getNoOfTimeLost());
				status = "fail";
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "paymentMatrixUpdate";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}
		ModelAndView mav = new ModelAndView("payment-matrix-edit");
		mav.addObject("paymentMatrixDefForm", form);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_PAYMENT_MATRIX);

		return status;
	}

	@RequestMapping(value = "/updatePaymentMatrix/{params}")
	@ExceptionHandler
	public ModelAndView updatePaymentMatrix(WebRequest request, ModelMap model,
			@PathVariable String params,
			@ModelAttribute("paymentList") PaymentDef paymentDef,
			BindingResult result) throws Exception {
		logger.info("Update Payment Matrix");

		PaymentDefId paymentDefId = new PaymentDefId();
		String paramArr[] = null;
		if (params.contains(",")) {
			paramArr = params.split(",");
		}
		if (paramArr[0] != null) {
			paymentDefId.setTransactionType(paramArr[0]);
		}
		if (paramArr[1] != null) {
			paymentDefId.setTransactionSubtype(paramArr[1]);
		}
		if (paramArr[2] != null) {
			if (StringUtils.isNumeric(paramArr[2])) {
				paymentDefId.setNoOfTimeLost(Integer.parseInt(paramArr[2]));
			}
		}
		PaymentDef paymentDefResponse = paymentDefService
				.findById(paymentDefId);

		if (paymentDefResponse != null) {
			paymentDefResponse.setTransactionType(paymentDefResponse.getId()
					.getTransactionType());
			paymentDefResponse.setTransactionSubtype(paymentDefResponse.getId()
					.getTransactionSubtype());
			paymentDefResponse.setNoOfTimeLost(paymentDefResponse.getId()
					.getNoOfTimeLost());
		}

		model.addAttribute("fnSelected", Constants.HEADING_NIC_PAYMENT_MATRIX);
		ModelAndView mav = new ModelAndView("payment-matrix-edit");
		mav.addObject("paymentMatrixDefForm", paymentDefResponse);
		return mav;
	}

	@RequestMapping(value = "/addPaymentMatrix")
	@ExceptionHandler
	public ModelAndView addPaymentMatrix(WebRequest request, ModelMap model)
			throws Exception {
		logger.info("Add Payment Matrix");
		PaymentDef form = new PaymentDef();
		List<CodeValues> transactionTypes = codesService
				.findAllByCodeId("TRANSACTION_TYPE");
		List<CodeValues> transactionSubTypes = codesService
				.findAllByCodeId("TRANSACTION_SUBTYPE");

		request.setAttribute("transactionTypes", transactionTypes, 1);
		request.setAttribute("transactionSubTypes", transactionSubTypes, 1);

		ModelAndView mav = new ModelAndView("add-payment-matrix");
		model.addAttribute("fnSelected", Constants.HEADING_NIC_PAYMENT_MATRIX);
		mav.addObject("paymentMatrixDefForm", form);
		return mav;
	}

	@RequestMapping(value = "/paymentMatrixCreate")
	@ResponseBody
	@ExceptionHandler
	public String paymentMatrixCreate(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute PaymentDef form) throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";

		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		try {
			String userId = "SYSTEM";
			String wkstnId = "";
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				userId = userSession.getUserName();
			}
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				wkstnId = userSession.getWorkstationId();
			} else {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName();
			}

			PaymentDef obj = new PaymentDef();
			PaymentDefId paymentDefId = new PaymentDefId();
			paymentDefId.setNoOfTimeLost(form.getNoOfTimeLost());
			paymentDefId.setTransactionType(form.getTransactionType());
			paymentDefId.setTransactionSubtype(form.getTransactionSubtype());

			PaymentDef orgPaymentDef = paymentDefService.findById(paymentDefId);
			if (orgPaymentDef != null) {
				status = "alreadyExists";
				return status;
			}
			obj.setId(paymentDefId);
			obj.setNoOfTimeLost(form.getNoOfTimeLost());
			obj.setFeeAmount(form.getFeeAmount());
			obj.setReduceRateFlag(form.getReduceRateFlag());
			obj.setWaivableFlag(form.getWaivableFlag());
			obj.setDeleteFlag(form.getDeleteFlag());

			obj.setCreateBy(userId);
			obj.setCreateDateTime(DateUtil.getSystemTimestamp());
			obj.setCreateWkstnId(wkstnId);

			paymentDefService.saveOrUpdate(obj);
			status = "success";

			List<String> messages = new ArrayList<String>();
			messages.add("Payment Matrix:" + form.getTransactionType()
					+ " created successfully");
			request.setAttribute("messages", messages, 1);

		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			List<String> messages = new ArrayList<String>();
			messages.add("Error occurred while creating the Payment Matrix:"
					+ form.getTransactionType());
			request.setAttribute("messages", messages, 1);
			ModelAndView mav = new ModelAndView("add-payment-matrix");
			model.addAttribute("fnSelected",
					Constants.HEADING_NIC_PAYMENT_MATRIX);
			mav.addObject("paymentMatrixDefForm", form);
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "paymentMatrixCreate";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}

	/*
	 * @RequestMapping(value="/updateParameter/{paramFormData}") public
	 * ModelAndView updateParameter(WebRequest request, @PathVariable String
	 * paramFormData, ModelMap model){ logger.info("Update Parameter"); String
	 * paramName = ""; String paraScope = ""; String paraShortValue = "";
	 * if(paramFormData != null && paramFormData.contains("&&")) { String
	 * array[] = paramFormData.split("&&"); paramName = array[0]; paraShortValue
	 * = array[1]; paraScope = array[2]; } Parameters paramForm = new
	 * Parameters(); ParametersId parametersId = new ParametersId();
	 * paramForm.setParaShortValue(paraShortValue);
	 * parametersId.setParaName(paramName);
	 * parametersId.setParaScope(paraScope); paramForm.setId(parametersId);
	 * //ModelAndView mav = new ModelAndView("parameter-mgmt-edit");
	 * //mav.addObject("paramName",paramName); //return mav;
	 * model.addAttribute("fnSelected", Constants.HEADING_NIC_PAYMENT_MATRIX);
	 * return new ModelAndView("parameter-mgmt-edit", "paymentParamForm",
	 * paramForm);
	 * 
	 * }
	 */

	/*
	 * @RequestMapping(value="/addParameter") public String
	 * addParameter(WebRequest request){ logger.info("Add Parameter"); return
	 * "parameter-mgmt-add"; }
	 */

	@RequestMapping(value = "/paymentMatrixDelete")
	@ExceptionHandler
	public String paymentMatrixDelete(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute PaymentDef form) throws Exception {
		// 30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String status = "";

		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		try {
			String userId = "SYSTEM";
			String wkstnId = "";
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				userId = userSession.getUserName();
			}
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				wkstnId = userSession.getWorkstationId();
			} else {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName();
			}

			PaymentDef obj = new PaymentDef();
			PaymentDefId paymentDocId = new PaymentDefId();
			paymentDocId.setNoOfTimeLost(form.getNoOfTimeLost());
			paymentDocId.setTransactionType(form.getTransactionType());
			paymentDocId.setTransactionSubtype(form.getTransactionSubtype());
			obj.setId(paymentDocId);
			obj.setFeeAmount(form.getFeeAmount());
			obj.setReduceRateFlag(form.getReduceRateFlag());
			obj.setWaivableFlag(form.getWaivableFlag());

			obj.setDeleteBy(userId);
			obj.setDeleteDateTime(DateUtil.getSystemTimestamp());
			obj.setDeleteWkstnId(wkstnId);
			obj.setDeleteFlag(true);
			// paymentDefService.saveOrUpdate(obj);
			paymentDefService.deletePaymentMatrix(obj, userId, wkstnId);
			status = "success";

			form.setStatus(status);

			List<String> messages = new ArrayList<String>();
			messages.add("Payment Matrix:" + form.getTransactionType()
					+ " deleted successfully");
			request.setAttribute("messages", messages, 1);
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "paymentMatrixDelete";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}
		ModelAndView mav = new ModelAndView(
				"/servlet/paymentMatrixController/paymentMatrix");
		mav.addObject("paymentMatrixDefForm", form);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_PAYMENT_MATRIX);
		request.setAttribute("status", status, 1);
		return "forward:" + "paymentMatrix";
		// return status;
	}

	/*
	 * @RequestMapping(value="/paymentMatrixUpdate")
	 * 
	 * @ResponseBody
	 * 
	 * @ExceptionHandler public String paymentMatrixUpdate(WebRequest request,
	 * HttpServletRequest httpRequest, ModelMap model , @ModelAttribute
	 * PaymentDef form) throws Exception{ //30 Dec 2013 (chris lai) : update
	 * with audit record long startTimeMs = System.currentTimeMillis();
	 * Throwable throwable = null; String status="";
	 * 
	 * HttpSession session = httpRequest.getSession(); UserSession userSession =
	 * (UserSession) session.getAttribute("userSession"); try{ String userId =
	 * "SYSTEM"; String wkstnId = ""; if
	 * (StringUtils.isNotBlank(userSession.getUserName())) { userId =
	 * userSession.getUserName(); } if
	 * (StringUtils.isNotBlank(userSession.getUserName())) { wkstnId =
	 * userSession.getWorkstationId(); } else { java.net.InetAddress
	 * localMachine = InetAddress.getLocalHost(); wkstnId =
	 * localMachine.getHostName(); }
	 * 
	 * PaymentDefId paymentDefId= new PaymentDefId();
	 * paymentDefId.setNoOfTimeLost(form.getNoOfTimeLost());
	 * paymentDefId.setTransactionType(form.getTransactionType());
	 * paymentDefId.setTransactionSubtype(form.getTransactionSubtype());
	 * 
	 * PaymentDef orgPaymentDef = paymentDefService.findById(paymentDefId);
	 * if(orgPaymentDef !=null){
	 * orgPaymentDef.setFeeAmount(form.getFeeAmount());
	 * orgPaymentDef.setReduceRateFlag(form.getReduceRateFlag());
	 * orgPaymentDef.setWaivableFlag(form.getWaivableFlag());
	 * orgPaymentDef.setDeleteFlag(form.getDeleteFlag());
	 * orgPaymentDef.setUpdateBy(userId);
	 * orgPaymentDef.setUpdateDateTime(DateUtil.getSystemTimestamp());
	 * orgPaymentDef.setUpdateWkstnId(wkstnId);
	 * paymentDefService.saveOrUpdate(orgPaymentDef); status = "success";
	 * form.setStatus(status); List<String> messages = new ArrayList<String>();
	 * messages
	 * .add("Payment Matrix:"+form.getTransactionType()+" saved successfully");
	 * request.setAttribute("messages", messages,1); }else{
	 * logger.info("Payment Matrix does not exists with Transaction Type: "
	 * +form.
	 * getTransactionType()+", Transaction Subtype"+form.getTransactionSubtype
	 * ()+", No. Of Card Lost:"+form.getNoOfTimeLost()); status="fail"; }
	 * }catch(Exception exp){ exp.printStackTrace(); status="fail"; //30 Dec
	 * 2013 (chris lai) : update with audit record throwable = exp; }finally {
	 * //30 Dec 2013 (chris lai) : update with audit record try { String
	 * functionName = "paymentMatrixUpdate"; Object[] args = { form }; long
	 * timeMs = System.currentTimeMillis() - startTimeMs;
	 * auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
	 * functionName,args,throwable, timeMs); } catch(Exception exp){ } }
	 * ModelAndView mav = new ModelAndView("signer-govs-edit");
	 * mav.addObject("paymentMatrixDefForm", form);
	 * model.addAttribute("fnSelected", Constants.HEADING_NIC_PAYMENT_MATRIX);
	 * 
	 * return status; }
	 * 
	 * @RequestMapping(value="/updatePaymentMatrix/{params}")
	 * 
	 * @ExceptionHandler public ModelAndView updatePaymentMatrix(WebRequest
	 * request, ModelMap model, @PathVariable String params ,
	 * 
	 * @ModelAttribute("paymentList") PaymentDef paymentDef, BindingResult
	 * result) throws Exception{ logger.info("Update Payment Matrix");
	 * 
	 * PaymentDefId paymentDefId = new PaymentDefId(); String paramArr []=null;
	 * if(params.contains(",")){ paramArr = params.split(","); }
	 * if(paramArr[0]!=null){ paymentDefId.setTransactionType(paramArr[0]); }
	 * if(paramArr[1]!=null){ paymentDefId.setTransactionSubtype(paramArr[1]); }
	 * if(paramArr[2]!=null){ if(StringUtils.isNumeric(paramArr[2])){
	 * paymentDefId.setNoOfTimeLost(Integer.parseInt(paramArr[2])); } }
	 * PaymentDef paymentDefResponse = paymentDefService.findById(paymentDefId);
	 * 
	 * if(paymentDefResponse!=null){
	 * paymentDefResponse.setTransactionType(paymentDefResponse
	 * .getId().getTransactionType());
	 * paymentDefResponse.setTransactionSubtype(paymentDefResponse
	 * .getId().getTransactionSubtype());
	 * paymentDefResponse.setNoOfTimeLost(paymentDefResponse
	 * .getId().getNoOfTimeLost()); }
	 * 
	 * model.addAttribute("fnSelected", Constants.HEADING_NIC_PAYMENT_MATRIX);
	 * ModelAndView mav = new ModelAndView("payment-matrix-edit");
	 * mav.addObject("paymentMatrixDefForm", paymentDefResponse); return mav; }
	 * 
	 * @RequestMapping(value="/addPaymentMatrix")
	 * 
	 * @ExceptionHandler public ModelAndView addPaymentMatrix(WebRequest
	 * request, ModelMap model) throws Exception{
	 * logger.info("Add Payment Matrix"); PaymentDef form= new PaymentDef();
	 * List<CodeValues> transactionTypes =
	 * codesService.findAllByCodeId("TRANSACTION_TYPE"); List<CodeValues>
	 * transactionSubTypes =
	 * codesService.findAllByCodeId("TRANSACTION_SUBTYPE");
	 * 
	 * request.setAttribute("transactionTypes", transactionTypes,1);
	 * request.setAttribute("transactionSubTypes", transactionSubTypes,1);
	 * 
	 * 
	 * ModelAndView mav = new ModelAndView("add-payment-matrix");
	 * model.addAttribute("fnSelected", Constants.HEADING_NIC_PAYMENT_MATRIX);
	 * mav.addObject("paymentMatrixDefForm", form); return mav; }
	 * 
	 * @RequestMapping(value="/paymentMatrixCreate")
	 * 
	 * @ResponseBody
	 * 
	 * @ExceptionHandler public String paymentMatrixCreate(WebRequest
	 * request,HttpServletRequest httpRequest, ModelMap model , @ModelAttribute
	 * PaymentDef form) throws Exception{ //30 Dec 2013 (chris lai) : update
	 * with audit record long startTimeMs = System.currentTimeMillis();
	 * Throwable throwable = null; String status="";
	 * 
	 * HttpSession session = httpRequest.getSession(); UserSession userSession =
	 * (UserSession) session.getAttribute("userSession"); try{ String userId =
	 * "SYSTEM"; String wkstnId = ""; if
	 * (StringUtils.isNotBlank(userSession.getUserName())) { userId =
	 * userSession.getUserName(); } if
	 * (StringUtils.isNotBlank(userSession.getUserName())) { wkstnId =
	 * userSession.getWorkstationId(); } else { java.net.InetAddress
	 * localMachine = InetAddress.getLocalHost(); wkstnId =
	 * localMachine.getHostName(); }
	 * 
	 * PaymentDef obj = new PaymentDef(); PaymentDefId paymentDefId= new
	 * PaymentDefId(); paymentDefId.setNoOfTimeLost(form.getNoOfTimeLost());
	 * paymentDefId.setTransactionType(form.getTransactionType());
	 * paymentDefId.setTransactionSubtype(form.getTransactionSubtype());
	 * 
	 * PaymentDef orgPaymentDef = paymentDefService.findById(paymentDefId);
	 * if(orgPaymentDef !=null){ status="alreadyExists"; return status; }
	 * obj.setId(paymentDefId); obj.setNoOfTimeLost(form.getNoOfTimeLost());
	 * obj.setFeeAmount(form.getFeeAmount());
	 * obj.setReduceRateFlag(form.getReduceRateFlag());
	 * obj.setWaivableFlag(form.getWaivableFlag());
	 * obj.setDeleteFlag(form.getDeleteFlag());
	 * 
	 * obj.setCreateBy(userId);
	 * obj.setCreateDateTime(DateUtil.getSystemTimestamp());
	 * obj.setCreateWkstnId(wkstnId);
	 * 
	 * paymentDefService.saveOrUpdate(obj); status="success";
	 * 
	 * List<String> messages = new ArrayList<String>();
	 * messages.add("Payment Matrix:"
	 * +form.getTransactionType()+" created successfully");
	 * request.setAttribute("messages", messages,1);
	 * 
	 * }catch(Exception exp){ exp.printStackTrace(); status="fail"; List<String>
	 * messages = new ArrayList<String>();
	 * messages.add("Error occurred while creating the Payment Matrix:"
	 * +form.getTransactionType()); request.setAttribute("messages",
	 * messages,1); ModelAndView mav = new ModelAndView("add-payment-matrix");
	 * model.addAttribute("fnSelected", Constants.HEADING_NIC_PAYMENT_MATRIX);
	 * mav.addObject("paymentMatrixDefForm", form); //30 Dec 2013 (chris lai) :
	 * update with audit record throwable = exp;
	 * 
	 * // return mav; }finally { //30 Dec 2013 (chris lai) : update with audit
	 * record try { String functionName = "paymentMatrixCreate"; Object[] args =
	 * { form }; long timeMs = System.currentTimeMillis() - startTimeMs;
	 * auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
	 * functionName,args,throwable, timeMs); } catch(Exception exp){ } }
	 * 
	 * return status; }
	 */

	/*
	 * ==========================================================================
	 * ===============
	 */
	
	
	//*** QUẢN LÝ QUYẾT ĐỊNH ***///
	/*@RequestMapping(value = "/decisionManagerList")
	public ModelAndView getDecisionManagerList(
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Admin Console -- NicDecisionManager");
		PaginatedResult<NicDecisionManager> pr = null;
		Map searchResultMap = new HashMap();
		try {
			int paramPageNo = 1;
			int pageSize = 10;
			String paramListTableId = "row";
			
			 * try{ paramPageNo = Integer.parseInt(request.getParameter((new
			 * ParamEncoder
			 * (paramListTableId).encodeParameterName(TableTagParameters
			 * .PARAMETER_PAGE)))); } catch (Exception ex) {
			 * logger.error(ex.getMessage()); }
			 
			pr = decisionManagerService.findAllDecisionManager("", paramPageNo, pageSize);
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
			searchResultMap.put("totalRecords", totalRecords);
			model.addAttribute("jobList", decisionList);
			searchResultMap.put("pageSize", pageSize);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in /decisionManagerList", e);
		}

		model.addAttribute("fnSelected", Constants.HEADING_NIC_DECISIONMANAGER_MATRIX);
		return new ModelAndView("decisionManager.list", searchResultMap);
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
				
				if (form.getFileUpload() != null) {
		            	form.setData(form.getFileUpload().getBytes());
				}
				List<MultipartFile> crunchifyFiles = form.getFileUpload();
				if (crunchifyFiles != null && crunchifyFiles.size() > 0) {
		            for (MultipartFile aFile : crunchifyFiles){
		            	form.setData(form.getFileUpload().getBytes());             
		            }
		        }
				
				form.setData(form.getFileUpload().getBytes());  
				form.setCreateBy(userId);
				form.setCreateDate(date);
				decisionManagerService.createDecisionManager(form);
				
				form.setDocData(Base64.decodeBase64(docString.getBytes()));
				form.setCreateBy(userId);
				form.setActive("Y");
				form.setCreateDate(date);
				signerGovsService.saveOrUpdate(form);
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
				String functionName = "decisionManagerCreate";
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
				if(editO != null && status){
					status = "confirm";
				}else { }
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
				String functionName = "decisionManagerUpdate";
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
				if(editO != null && status){
					status = "confirm";
				}else { }
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
				String functionName = "decisionManagerDelete";
				Object[] args = { null };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}
		
		return status;
	}*/
	
	/*@RequestMapping(value = "/addBusinessList/{params}")
	@ExceptionHandler
	public ModelAndView addBusinessList(WebRequest request, ModelMap model,@PathVariable String params)
			throws Exception {
		logger.info("Add BusinessList");
		List<NicBusinessList> formLs = new ArrayList<NicBusinessList>();
		
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
		
			formLs = businessListService.findListByDecisionNumber(params);
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
			if(editO != null && status){
				status = "confirm";
			}else { }
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
				String functionName = "businessListCreate";
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
				if(editO != null && status){
					status = "confirm";
				}else { }
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
				String functionName = "businessListUpdate";
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
				if(editO != null && status){
					status = "confirm";
				}else { }
			
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
				String functionName = "businessListDelete";
				Object[] args = { null };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}
		
		return status;
	}*/
	
	//*** QUẢN LÝ CÔNG HÀM ***///
		/*@RequestMapping(value = "/officalNationList")
		public ModelAndView getOfficalNationList(
				WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
			logger.info("NIC Admin Console -- NicOfficalNation");
			PaginatedResult<NicOfficalNation> pr = null;
			Map searchResultMap = new HashMap();
			try {
				int paramPageNo = 1;
				int pageSize = 10;
				String paramListTableId = "row";
				
				 * try{ paramPageNo = Integer.parseInt(request.getParameter((new
				 * ParamEncoder
				 * (paramListTableId).encodeParameterName(TableTagParameters
				 * .PARAMETER_PAGE)))); } catch (Exception ex) {
				 * logger.error(ex.getMessage()); }
				 
				pr = officalNationService.findAllOfficalNation("", paramPageNo, pageSize);
				int totalRecords = 0;
				List<NicOfficalNation> officalList = new ArrayList<NicOfficalNation>();
				if (pr != null) {
					officalList = pr.getRows();
					totalRecords = pr.getTotal();
					for (NicOfficalNation nicOfficalNation : officalList) {
						
						switch(nicOfficalNation.getStatus()){
							case "REGISTRATION":nicOfficalNation.setStatusS("Khởi tạo/ lưu tạm");break;
							case "PROCESSING":nicOfficalNation.setStatusS("Xác nhận");break;
							case "VERIFY":nicOfficalNation.setStatusS("Đã nhận");break;
							case "RECEIVED":nicOfficalNation.setStatusS("Chờ xử lý (Giai đoạn lấy thị thực)");break;
							case "REFUSE":nicOfficalNation.setStatusS("Từ chối từ đại sứ quán");break;
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
				searchResultMap.put("totalRecords", totalRecords);
				model.addAttribute("jobList", officalList);
				searchResultMap.put("pageSize", pageSize);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error in /officalNationList", e);
			}

			model.addAttribute("fnSelected", Constants.HEADING_NIC_OFFICALNATION_MATRIX);
			return new ModelAndView("officalNation.list", searchResultMap);
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
			model.addAttribute("fnSelected", "ePassport Offical Nation");
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
					
					form.setDocData(Base64.decodeBase64(docString.getBytes()));
					form.setCreateBy(userId);
					form.setActive("Y");
					form.setCreateDate(date);
					signerGovsService.saveOrUpdate(form);
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
				model.addAttribute("fnSelected", "ePassport Offical Nation");
				mav.addObject("officalNationForm", form);
				// 30 Dec 2013 (chris lai) : update with audit record
				throwable = exp;

				// return mav;
			} finally {
				// 30 Dec 2013 (chris lai) : update with audit record
				try {
					String functionName = "officalNationCreate";
					Object[] args = { form };
					long timeMs = System.currentTimeMillis() - startTimeMs;
					auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
							functionName, args, throwable, timeMs);
				} catch (Exception exp) {
				}
			}

			return status;
		}*/
		
		/*@RequestMapping(value = "/updateOfficalNation/{params}")
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
			model.addAttribute("fnSelected", "ePassport Offical Nation");
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
					if(editO != null && status){
						status = "confirm";
					}else { }
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
					if(editO.getData() != null && form.getData() == null){
						editO.setData(editO.getData());
					}
					if(form.getData() == null)
						form.setData(editO.getData());
					
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
				model.addAttribute("fnSelected", "ePassport Offical Nation");
				mav.addObject("officalNationForm", form);
				// 30 Dec 2013 (chris lai) : update with audit record
				throwable = exp;

				// return mav;
			} finally {
				// 30 Dec 2013 (chris lai) : update with audit record
				try {
					String functionName = "officalNationUpdate";
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
					String functionName = "officalNationDelete";
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
					String functionName = "officalNationVerify";
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
					String functionName = "officalNationCancel";
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
	}*/
	
	/*@RequestMapping(value = "/signerList", method = RequestMethod.GET)	
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
	}*/
	
	//*** QUẢN LÝ QUỐC GIA MIỄN THỊ THỰC ***///
	/*@RequestMapping(value = "/officalVisaList")
	public ModelAndView getOfficalVisaList(
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Admin Console -- NicOfficalVisa");
		PaginatedResult<NicOfficalVisa> pr = null;
		Map searchResultMap = new HashMap();
		try {
			int paramPageNo = 1;
			int pageSize = 10;
			String paramListTableId = "row";
			
			 * try{ paramPageNo = Integer.parseInt(request.getParameter((new
			 * ParamEncoder
			 * (paramListTableId).encodeParameterName(TableTagParameters
			 * .PARAMETER_PAGE)))); } catch (Exception ex) {
			 * logger.error(ex.getMessage()); }
			 
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
				
						form.setDocData(Base64.decodeBase64(docString.getBytes()));
						form.setCreateBy(userId);
						form.setActive("Y");
						form.setCreateDate(date);
						signerGovsService.saveOrUpdate(form);
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
				String functionName = "officalVisaCreate";
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
			if(editO != null && status){
							status = "confirm";
						}else { }
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
				if(editO.getData() != null && form.getData() == null){
							editO.setData(editO.getData());
						}
						if(form.getData() == null)
							form.setData(editO.getData());

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
				String functionName = "officalVisaUpdate";
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
				String functionName = "officalVisaDelete";
				Object[] args = { null };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}
	
	Kiểm tra dữ liệu quốc gia miễn thị thực có tồn tại không
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
	
	
	Chọn cơ quan thẩm quyền xem có tiếng anh
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
	}*/
}
