/**
 * 
 */
package com.nec.asia.nic.admin.signer.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.signerGovs.service.SignerGovsService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.web.session.UserSession;


/**
 * @author prasad_karnati
 *
 */

/* 
* Modification History:
*  
* 08 Oct 2013 (Chris Lai):Update to set the ParentCodeValue
* 
* 13 Dec 2013 (Chris Lai) : Add in Audit Log
* 
*/

@Controller
@RequestMapping(value = "/signerController")
public class SignerController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SignerController.class);
	
	@Autowired
	private SignerGovsService signerGovsService;
	
	@Autowired
	private CodesService codesService;
	
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	/* CHỮ KÝ CÔNG VĂN =========================== */

	@RequestMapping(value = "/signerGovs")
	@ExceptionHandler
	public ModelAndView signerGovsList(WebRequest request, ModelMap model)
			throws Throwable {
		logger.info("NIC Admin Console -- Signer Govs");
		PaginatedResult<SignerGovs> pr = null;
		Map searchResultMap = new HashMap();
		try {
			int paramPageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			int pageSize = 10;
			String paramListTableId = "row";
			/*
			 * try{ paramPageNo = Integer.parseInt(request.getParameter((new
			 * ParamEncoder
			 * (paramListTableId).encodeParameterName(TableTagParameters
			 * .PARAMETER_PAGE)))); } catch (Exception ex) {
			 * logger.error(ex.getMessage()); }
			 */
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			pr = signerGovsService.findAllSignerGovs("", paramPageNo, pageSize);

			List<SignerGovs> paymentList = new ArrayList<SignerGovs>();

			int totalRecords = 0;
			if (pr != null) {
				paymentList = pr.getRows();
				totalRecords = pr.getTotal();
				if (paymentList != null) {
					for (SignerGovs sig : paymentList) {
						CodeValues codeGOv = codesService
								.getCodeValueByIdName("CODE_IDGovernment",
										sig.getCodeGovernment());
						if (codeGOv != null) {
							sig.setNameGov(codeGOv.getCodeValueDesc());
						}
					}
				}
			}

			searchResultMap.put("totalRecords", totalRecords);
			searchResultMap.put("pageSize", pageSize);
			//model.addAttribute("jobList", paymentList);
			//phúc edit
			int firstResults = (paramPageNo - 1) < 0 ? 0 : (paramPageNo - 1) * pageSize;
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", paramPageNo);
			model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
			model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", pr.getTotal());
			model.addAttribute("endIndex", firstResults + pr.getRowSize());
			model.addAttribute("jobList", paymentList);
			//end

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in /signerGovs", e);
		}

		model.addAttribute("fnSelected", Constants.HEADING_NIC_SIGN_MATRIX);
		return new ModelAndView("signer-govs", searchResultMap);
	}

	@RequestMapping(value = "/addSignerGovs")
	@ExceptionHandler
	public ModelAndView addSignerGovs(WebRequest request, ModelMap model)
			throws Exception {
		logger.info("Add Signer Govs");
		SignerGovs form = new SignerGovs();
		List<CodeValues> codeGov = codesService
				.findAllByCodeId("CODE_IDGovernment");

		request.setAttribute("codeGov", codeGov, 1);

		ModelAndView mav = new ModelAndView("add-signer-govs");
		model.addAttribute("fnSelected", "ePassport Signer Gov");
		mav.addObject("paymentMatrixDefForm", form);
		return mav;
	}

	@RequestMapping(value = "/signerGovsCreate")
	@ResponseBody
	@ExceptionHandler
	public String signerGovsCreate(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute SignerGovs form,
			@RequestParam(value = "docString") String docString)
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
			SignerGovs sig = signerGovsService.findSignerByCode(form
					.getCodeSigner());
			if (sig != null) {
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

				form.setDocData(form.getDocDataF().getBytes());
				form.setCreateBy(userId);
				form.setActive("Y");
				form.setCreateDate(date);
				signerGovsService.saveOrUpdate(form);
				status = "success";

				List<String> messages = new ArrayList<String>();
				messages.add("Thêm mới chữ ký thành công");
				request.setAttribute("messages", messages, 1);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			List<String> messages = new ArrayList<String>();
			messages.add("Đã có lỗi xảy ra khi tạo chữ ký mới.");
			request.setAttribute("messages", messages, 1);
			ModelAndView mav = new ModelAndView("add-signer-govs");
			model.addAttribute("fnSelected", "ePassport Signer Gov");
			mav.addObject("paymentMatrixDefForm", form);
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Thêm mới chữ ký công văn";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}

	@RequestMapping(value = "/signerGovsEdit/{params}")
	@ExceptionHandler
	public ModelAndView editSignerGovs(WebRequest request, ModelMap model,@PathVariable long params)
			throws Exception {
		logger.info("Add Signer Govs");
		SignerGovs form = new SignerGovs();
		form = signerGovsService.findById(params);
		List<CodeValues> codeGov = codesService
				.findAllByCodeId("CODE_IDGovernment");

		request.setAttribute("codeGov", codeGov, 1);
		
		boolean act = form.getActive().equals("Y") ? true : false;
		form.setActiveT(act);
		String photoStrSigner = null;

		if (form.getDocData() != null) {
			photoStrSigner = new String(form.getDocData());
		}
		
		ModelAndView mav = new ModelAndView("signer-govs-edit");
		model.addAttribute("fnSelected", "ePassport Signer Gov");
		mav.addObject("paymentMatrixDefForm", form);
		mav.addObject("photoStr", photoStrSigner);
		return mav;
	}

	@RequestMapping(value = "/signerGovsUpdate")
	@ResponseBody
	@ExceptionHandler
	public String signerGovsEdit(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			@ModelAttribute SignerGovs form,
			@RequestParam(value = "docString") String docString)
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
			SignerGovs sig = signerGovsService.findById(form.getId());
			if (sig != null) {
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
				if (form.getDocDataF() != null)
					sig.setDocData(form.getDocDataF().getBytes());
				sig.setCreateBy(userId);
				
				sig.setCodeGovernment(form.getCodeGovernment());
				sig.setNameSigner(form.getNameSigner());
				sig.setDescription(form.getDescription());
				sig.setActive(form.getActive());
				sig.setUpdateDate(date);
				signerGovsService.saveOrUpdate(sig);
				status = "success";

				List<String> messages = new ArrayList<String>();
				messages.add("Cập nhật chữ ký thành công");
				request.setAttribute("messages", messages, 1);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			status = "fail";
			List<String> messages = new ArrayList<String>();
			messages.add("Đã có lỗi xảy ra khi cập nhật chữ ký.");
			request.setAttribute("messages", messages, 1);
			ModelAndView mav = new ModelAndView("signer-govs-edit");
			model.addAttribute("fnSelected", "ePassport Signer Gov");
			mav.addObject("paymentMatrixDefForm", form);
			// 30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Cập nhật chữ ký công văn";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest,
						functionName, args, throwable, timeMs);
			} catch (Exception exp) {
			}
		}

		return status;
	}
	
}
