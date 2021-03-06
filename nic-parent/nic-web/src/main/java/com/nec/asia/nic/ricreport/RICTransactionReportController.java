package com.nec.asia.nic.ricreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.nec.asia.nic.comp.trans.dto.ExceptionHandlngRptDto;
import com.nec.asia.nic.comp.trans.dto.RICBatchCardInfoDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnRptDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnUpldRptDto;
import com.nec.asia.nic.comp.trans.service.RICReportService;
import com.nec.asia.nic.framework.Constants;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.report.domain.ReportTemplate;
import com.nec.asia.nic.framework.report.service.ReportManagementService;
//import com.nec.asia.nic.framework.systemparameter.exception.ParameterException;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.ReportUtil;
import com.nec.asia.nic.web.session.UserSession;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

@Controller
@RequestMapping(value = "/txnReport")
public class RICTransactionReportController extends AbstractController {

	@Autowired
	private CodesService codesService;

	@Autowired
	private RICReportService ricReportService;

	@Autowired
	private ReportManagementService reportService;

	Map<String, String> ricSiteCodesMap;

	@RequestMapping(value = "/showricTxnRpt")
	public String showRicTxnRpt(WebRequest request, Model model)
			throws DaoException {
		model.addAttribute("fnSelected", "RIC_RPT_001 :RIC Transaction Report");
		Map<String, String> txnTypeCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_TYPE);
		if(txnTypeCodesMap.containsKey("CON")){
			txnTypeCodesMap.remove("CON");			
		}
		Map<String, String> txnSubTypeCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE);
		if(txnSubTypeCodesMap.containsKey("CON")){
			txnSubTypeCodesMap.remove("CON");			
		}
		Map<String, String> txnStatusCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_STATUS);
		Map<String, String> genderCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_GENDER);
		Map<String, String> siteCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_SITE_CODE);
		Iterator<Entry<String, String>> sitecodes = siteCodesMap.entrySet()
				.iterator();
		ricSiteCodesMap = new TreeMap<String, String>();
		while (sitecodes.hasNext()) {
			Map.Entry<String, String> entry = sitecodes.next();
			String key = entry.getKey().substring(0, 3);
			if (key.equalsIgnoreCase("RIC")) {
				ricSiteCodesMap.put(entry.getKey(), entry.getValue());
			}
		}
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		model.addAttribute("txnTypeList", txnTypeCodesMap);
		model.addAttribute("txnSubTypeList", txnSubTypeCodesMap);
		model.addAttribute("txnStatus", txnStatusCodesMap);
		model.addAttribute("gender", genderCodesMap);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("siteCodes", ricSiteCodesMap);
		model.addAttribute("ricTxnRptDto", new RICTxnRptDto());
		return "show.ricTxnRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showTabluarResult")
	public PaginatedResult<RICTxnRptDto> showTabluarResult(WebRequest request,
			Model model, RICTxnRptDto ricTxnRptDto) throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getRicTxnRptRecordList(ricTxnRptDto, page, pageSize);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showPdfResult")
	public void showPdfResult1(WebRequest request, Model model,
			HttpServletRequest httpRequest, RICTxnRptDto ricTxnRptDto,
			HttpServletResponse response) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		try {
			parameterMap.put("startDate", ricTxnRptDto.getStartDate());
			parameterMap.put("endDate", ricTxnRptDto.getEndDate());
			parameterMap.put("txnTyp", ricTxnRptDto.getTxnType());
			parameterMap
					.put("txnTypDesc",
							(ricTxnRptDto.getTxnType() != null) ? codesService
									.getCodeValueByIdName(
											RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
											ricTxnRptDto.getTxnType())
									.getCodeValueDesc()
									: "");
			parameterMap.put("txnSubType", ricTxnRptDto.getTxnSubType());
			parameterMap
					.put("txnSubTypeDesc",
							(ricTxnRptDto.getTxnSubType() != null) ? codesService
									.getCodeValueByIdName(
											RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
											ricTxnRptDto.getTxnSubType())
									.getCodeValueDesc()
									: "");
			parameterMap.put("txnStatus", ricTxnRptDto.getTxnStatus());
			parameterMap
					.put("txnStatusDesc",
							(ricTxnRptDto.getTxnStatus() != null) ? codesService
									.getCodeValueByIdName(
											RegistrationConstants.CODE_ID_TRANSACTION_STATUS,
											ricTxnRptDto.getTxnStatus())
									.getCodeValueDesc()
									: "");
			parameterMap.put("gender", ricTxnRptDto.getGender());
			parameterMap.put(
					"genderDesc",
					(ricTxnRptDto.getGender() != null) ? codesService
							.getCodeValueByIdName(
									RegistrationConstants.CODE_ID_GENDER,
									ricTxnRptDto.getGender())
							.getCodeValueDesc() : "");
			parameterMap.put("estColStDate", ricTxnRptDto.getEstColStartDt());
			parameterMap.put("estColEndDate", ricTxnRptDto.getEstColEndDt());
			parameterMap.put("reportId", "RIC_RPT_001");
			parameterMap.put("ricSite", (!ricTxnRptDto.getRicOffice()
					.equalsIgnoreCase("ALL")) ? ricTxnRptDto.getRicOffice()
					: "RIC");
			parameterMap
					.put("ricSiteDesc",
							(!ricTxnRptDto.getRicOffice().equalsIgnoreCase(
									"ALL")) ? codesService
									.getCodeValueByIdName(
											RegistrationConstants.CODE_ID_SITE_CODE,
											ricTxnRptDto.getRicOffice())
									.getCodeValueDesc()
									: "");
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			parameterMap.put("userID", userSession.getUserName());

			// System.out.println("Generating report");
//			ReportTemplate template = reportService
//					.getReportTemplateById(
//							RICReportService.NIC_RIC_TRANSACTION_RPT_ID,
//							RICReportService.NIC_RIC_TRANSACTION_RPT_NAME);
			ReportTemplate template = reportService
					.getReportTemplateById(
							"TDR3",
							"Passport Collection Status Report");
			System.out.println("Generating report >> " + template);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			response.setContentType("application/pdf; charset=UTF-8");
			System.out.println("Generating report Done");
			response.getOutputStream().write(
					ReportUtil.getPdfReport(parameterMap, reportStream,
							reportService.getConnection()));
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/showTxnRejRpt")
	public String showTransRejectReport(WebRequest request, Model model)
			throws DaoException {
		model.addAttribute("fnSelected",
				"RIC_RPT_005 :Rejected Transaction Report");
		Map<String, String> txnTypeCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_TYPE);
		if(txnTypeCodesMap.containsKey("CON")){
			txnTypeCodesMap.remove("CON");			
		}
		Map<String, String> txnSubTypeCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE);
		if(txnSubTypeCodesMap.containsKey("CON")){
			txnSubTypeCodesMap.remove("CON");			
		}
		Map<String, String> rejRsnMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_REASON_NIC_INV_REJECT);
		Map<String, String> siteCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_SITE_CODE);
		Iterator<Entry<String, String>> sitecodes = siteCodesMap.entrySet()
				.iterator();
		ricSiteCodesMap = new TreeMap<String, String>();
		while (sitecodes.hasNext()) {
			Map.Entry<String, String> entry = sitecodes.next();
			String key = entry.getKey().substring(0, 3);
			if (key.equalsIgnoreCase("RIC")) {
				ricSiteCodesMap.put(entry.getKey(), entry.getValue());
			}
		}
		model.addAttribute("siteCodes", ricSiteCodesMap);
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		model.addAttribute("txnTypeList", txnTypeCodesMap);
		model.addAttribute("txnSubTypeList", txnSubTypeCodesMap);
		model.addAttribute("reasonList", rejRsnMap);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("ricTxnRptDto", new RICTxnRptDto());
		return "show.txnRejRpt";
	}

	@RequestMapping(value = "/showricTxnUpldRpt")
	public String showricTxnUpldRpt(WebRequest request, Model model) {
		model.addAttribute("fnSelected",
				"RIC_RPT_004 :RIC Transaction Uploaded Report");
		Map<String, String> txnTypeCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_TYPE);
		if(txnTypeCodesMap.containsKey("CON")){
			txnTypeCodesMap.remove("CON");			
		}
		Map<String, String> txnSubTypeCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE);
		if(txnSubTypeCodesMap.containsKey("CON")){
			txnSubTypeCodesMap.remove("CON");			
		}
		Map<String, String> genderCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_GENDER);
		Map<String, String> siteCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_SITE_CODE);
		Iterator<Entry<String, String>> sitecodes = siteCodesMap.entrySet().iterator();
		ricSiteCodesMap = new TreeMap<String, String>();
		while (sitecodes.hasNext()) {
			Map.Entry<String, String> entry = sitecodes.next();
			String key = entry.getKey().substring(0, 3);
			if (key.equalsIgnoreCase("RIC")) {
				ricSiteCodesMap.put(entry.getKey(), entry.getValue());
			}
		}
		model.addAttribute("siteCodes", ricSiteCodesMap);
		model.addAttribute("txnTypeList", txnTypeCodesMap);
		model.addAttribute("txnSubTypeList", txnSubTypeCodesMap);
		model.addAttribute("gender", genderCodesMap);
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();/*
												 * new
												 * java.text.SimpleDateFormat
												 * ("d-MMM-yyyy").format(new
												 * java.util.Date(System.
												 * currentTimeMillis() - 7 * 24
												 * * 60 * 60 *
												 * 1000)).toString();
												 */
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("ricTxnUpldRptDto", new RICTxnUpldRptDto());
		return "show.ricTxnUpldRpt";
	}

	@RequestMapping(value = "/showricBatchCardInfoRpt")
	public String showTransCardStatus(WebRequest request, Model model) {
		model.addAttribute("fnSelected",
				"RIC_RPT_006 :RIC Batch Card Info Download Report");
		Map<String, String> siteCodesmap = new TreeMap<String, String>();
		Map<String, String> txnTypeCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_TYPE);
		if(txnTypeCodesMap.containsKey("CON")){
			txnTypeCodesMap.remove("CON");			
		}
		
		Map<String, String> txnSubTypeCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE);
		if(txnSubTypeCodesMap.containsKey("CON")){
			txnSubTypeCodesMap.remove("CON");			
		}
		model.addAttribute("txnTypeList", txnTypeCodesMap);
		model.addAttribute("txnSubTypeList", txnSubTypeCodesMap);
		Map<String, String> siteCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_SITE_CODE);
		Iterator<Entry<String, String>> sitecodes = siteCodesMap.entrySet()
				.iterator();
		ricSiteCodesMap = new TreeMap<String, String>();
		while (sitecodes.hasNext()) {
			Map.Entry<String, String> entry = sitecodes.next();
			String key = entry.getKey().substring(0, 3);
			if (key.equalsIgnoreCase("RIC")) {
				ricSiteCodesMap.put(entry.getKey(), entry.getValue());
			}
		}
		model.addAttribute("siteCodes", ricSiteCodesMap);
		model.addAttribute("ricBatchCardInfoRptDto", new RICBatchCardInfoDto());
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		Map<String, String> cardStatusMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_CARD_STATUS);
		model.addAttribute("cardStatusVal", cardStatusMap);
		return "show.ricBatchCardInfoRpt";
	}

	@RequestMapping(value = "/showExceptionHandlingRpt")
	public String showExceptionHandlingRpt(WebRequest request, Model model) {
		model.addAttribute("fnSelected",
				"RIC_RPT_013 :Exception Handling Report");
		Map<String, String> exceptionReasonMap = codesService
				.getCodeValues(RegistrationConstants.EXCPTION_HANDLING_REASON_CODE);
		model.addAttribute("exceptionReasonList", exceptionReasonMap);
		Map<String, String> siteCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_SITE_CODE);
		Iterator<Entry<String, String>> sitecodes = siteCodesMap.entrySet()
				.iterator();
		ricSiteCodesMap = new TreeMap<String, String>();
		while (sitecodes.hasNext()) {
			Map.Entry<String, String> entry = sitecodes.next();
			String key = entry.getKey().substring(0, 3);
			if (key.equalsIgnoreCase("RIC")) {
				ricSiteCodesMap.put(entry.getKey(), entry.getValue());
			}
		}
		model.addAttribute("siteCodes", ricSiteCodesMap);
		ExceptionHandlngRptDto dto = new ExceptionHandlngRptDto();
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		dto.setForwardStartDt(startDate);
		dto.setForwardEndDt(endDate);
		model.addAttribute("excptnHandlngRptDto", dto);
		return "show.exceptionHandlingRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showExcelResult")
	public void showExcelResult(WebRequest request, Model model,
			HttpServletRequest httpRequest, RICTxnRptDto ricTxnRptDto,
			HttpServletResponse response) throws Exception {
		System.out.println("Transaction Type" + ricTxnRptDto.getTxnType());
		System.out.println("Transaction SUB Type"
				+ ricTxnRptDto.getTxnSubType());
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("startDate", ricTxnRptDto.getStartDate());
		parameterMap.put("endDate", ricTxnRptDto.getEndDate());
		parameterMap.put("txnTyp", ricTxnRptDto.getTxnType());
		parameterMap
				.put("txnTypDesc",
						(ricTxnRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricTxnRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("txnSubType", ricTxnRptDto.getTxnSubType());
		parameterMap
				.put("txnSubTypeDesc",
						(ricTxnRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricTxnRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("txnStatus", ricTxnRptDto.getTxnStatus());
		parameterMap
				.put("txnStatusDesc",
						(ricTxnRptDto.getTxnStatus() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_STATUS,
										ricTxnRptDto.getTxnStatus())
								.getCodeValueDesc()
								: "");
		parameterMap.put("gender", ricTxnRptDto.getGender());
		parameterMap.put(
				"genderDesc",
				(ricTxnRptDto.getGender() != null) ? codesService
						.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_GENDER,
								ricTxnRptDto.getGender()).getCodeValueDesc()
						: "");
		parameterMap.put("estColStDate", ricTxnRptDto.getEstColStartDt());
		parameterMap.put("estColEndDate", ricTxnRptDto.getEstColEndDt());
		parameterMap.put("reportId", "RIC_RPT_001");
		parameterMap
				.put("ricSite",
						(!ricTxnRptDto.getRicOffice().equalsIgnoreCase("ALL")) ? ricTxnRptDto
								.getRicOffice() : "RIC");
		parameterMap
				.put("ricSiteDesc",
						(!ricTxnRptDto.getRicOffice().equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										ricTxnRptDto.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put(
				"JRXlsExporterParameter.IS _REMOVE_EMPTY_SPACE_BETWEEN_ROWS",
				Boolean.TRUE);
		parameterMap.put("JRXlsExporterParameter.IS _ONE_PAGE_PER_SHEET",
				Boolean.FALSE);
		parameterMap.put("JRXlsExporterParameter.IS _AUTO_DETECT_CELL_TYPE",
				Boolean.TRUE);
		parameterMap.put("JRXlsExporterParameter.IS _WHITE_PAGE_BACKGROUND",
				Boolean.FALSE);
		parameterMap.put("IS_IGNORE_PAGINATION", true);
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_TRANSACTION_RPT_ID,
							ricReportService.NIC_RIC_TRANSACTION_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameterMap, reportService.getConnection());
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS
					.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporterXLS
					.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
							Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			response.setContentType("application/vnd.ms-excel");
			exporterXLS.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/showTxnUploadTabluarResult")
	public PaginatedResult<RICTxnUpldRptDto> showTxnUploadTabluarResult(
			WebRequest request, Model model, RICTxnUpldRptDto ricTxnUpldRptDto)
			throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		PaginatedResult<RICTxnUpldRptDto> result = ricReportService
				.getRicTxnUploadRptRecordList(ricTxnUpldRptDto, page, pageSize);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showTxnUploadPdfResult")
	public void showTxnUploadPdfResult(WebRequest request, Model model,
			HttpServletRequest httpRequest, RICTxnUpldRptDto ricTxnUpldRptDto,
			HttpServletResponse response) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("startDate", ricTxnUpldRptDto.getStartDate());
		parameterMap.put("endDate", ricTxnUpldRptDto.getEndDate());
		parameterMap.put("txnType", ricTxnUpldRptDto.getTxnType());
		parameterMap
				.put("txnTypeDesc",
						(ricTxnUpldRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricTxnUpldRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("txnSubType", ricTxnUpldRptDto.getTxnSubType());
		parameterMap
				.put("txnSubTypeDesc",
						(ricTxnUpldRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricTxnUpldRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("txnStatus", ricTxnUpldRptDto.getTxnStatus());
		parameterMap
				.put("txnStatusDesc",
						(ricTxnUpldRptDto.getTxnStatus() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_STATUS,
										ricTxnUpldRptDto.getTxnStatus())
								.getCodeValueDesc()
								: "");
		parameterMap.put("reportId", "RIC_RPT_004");
		parameterMap.put("ricSite", (!ricTxnUpldRptDto.getRicOffice()
				.equalsIgnoreCase("ALL")) ? ricTxnUpldRptDto.getRicOffice()
				: "RIC");
		parameterMap
				.put("ricSiteDesc",
						(!ricTxnUpldRptDto.getRicOffice().equalsIgnoreCase(
								"ALL")) ? codesService.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								ricTxnUpldRptDto.getRicOffice())
								.getCodeValueDesc() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_TRANSACTION_UPLOAD_RPT_ID,
							ricReportService.NIC_RIC_TRANSACTION_UPLOAD_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			response.setContentType("application/pdf");
			response.getOutputStream().write(
					ReportUtil.getPdfReport(parameterMap, reportStream,
							reportService.getConnection()));
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping(value = "/showTxnUploadXlsResult")
	public void showTxnUploadXlsResult(WebRequest request, Model model,
			HttpServletRequest httpRequest, RICTxnUpldRptDto ricTxnUpldRptDto,
			HttpServletResponse response) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("startDate", ricTxnUpldRptDto.getStartDate());
		parameterMap.put("endDate", ricTxnUpldRptDto.getEndDate());
		parameterMap.put("txnType", ricTxnUpldRptDto.getTxnType());
		parameterMap
				.put("txnTypeDesc",
						(ricTxnUpldRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricTxnUpldRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("txnSubType", ricTxnUpldRptDto.getTxnSubType());
		parameterMap
				.put("txnSubTypeDesc",
						(ricTxnUpldRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricTxnUpldRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("txnStatus", ricTxnUpldRptDto.getTxnStatus());
		parameterMap
				.put("txnStatusDesc",
						(ricTxnUpldRptDto.getTxnStatus() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_STATUS,
										ricTxnUpldRptDto.getTxnStatus())
								.getCodeValueDesc()
								: "");
		parameterMap.put("reportId", "RIC_RPT_004");
		parameterMap.put("ricSite", (!ricTxnUpldRptDto.getRicOffice()
				.equalsIgnoreCase("ALL")) ? ricTxnUpldRptDto.getRicOffice()
				: "RIC");
		parameterMap
				.put("ricSiteDesc",
						(!ricTxnUpldRptDto.getRicOffice().equalsIgnoreCase(
								"ALL")) ? codesService.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								ricTxnUpldRptDto.getRicOffice())
								.getCodeValueDesc() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_TRANSACTION_UPLOAD_RPT_ID,
							ricReportService.NIC_RIC_TRANSACTION_UPLOAD_RPT_NAME);
			/*InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			response.setContentType("application/pdf");
			response.getOutputStream().write(
					ReportUtil.getPdfReport(parameterMap, reportStream,
							reportService.getConnection()));*/
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameterMap, reportService.getConnection());
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS
					.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporterXLS
					.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
							Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			response.setContentType("application/vnd.ms-excel");
			exporterXLS.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/showRejTxnTabluarResult")
	public PaginatedResult<RICTxnRptDto> showRejTxnTabluarResult(
			WebRequest request, Model model, RICTxnRptDto ricRejTxnRptDto)
			throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println("Entering the controller");
		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getRicRejTxnRptRecordList(ricRejTxnRptDto, page, pageSize);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showRejTxnPdfResult")
	public void showRejTxnPdfResult(WebRequest request, Model model,
			HttpServletRequest httpRequest, RICTxnRptDto ricRejTxnRptDto,
			HttpServletResponse response) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("startDate", ricRejTxnRptDto.getStartDate());
		parameterMap.put("endDate", ricRejTxnRptDto.getEndDate());
		parameterMap.put("txnType", ricRejTxnRptDto.getTxnType());
		parameterMap
				.put("txnTypeDesc",
						(ricRejTxnRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricRejTxnRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("txnSubType", ricRejTxnRptDto.getTxnSubType());
		parameterMap
				.put("txnSubTypeDesc",
						(ricRejTxnRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricRejTxnRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("rejRsn", ricRejTxnRptDto.getReason());
		parameterMap
				.put("rejRsnDesc",
						(ricRejTxnRptDto.getReason() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_REASON_NIC_INV_REJECT,
										ricRejTxnRptDto.getReason())
								.getCodeValueDesc()
								: "");
		parameterMap.put("rejStdt", ricRejTxnRptDto.getRejStartDate());
		parameterMap.put("rejEndDt", ricRejTxnRptDto.getRejEndDate());
		parameterMap.put("reportId", "RIC_RPT_005");
		parameterMap.put("siteId", (!ricRejTxnRptDto.getRicOffice()
				.equalsIgnoreCase("ALL")) ? ricRejTxnRptDto.getRicOffice()
				: "RIC");
		parameterMap
				.put("siteIdDesc",
						(!ricRejTxnRptDto.getRicOffice()
								.equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										ricRejTxnRptDto.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_REJECTED_TRANSACTION_RPT_ID,
							ricReportService.NIC_RIC_REJECTED_TRANSACTION_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			response.setContentType("application/pdf");
			response.getOutputStream().write(
					ReportUtil.getPdfReport(parameterMap, reportStream,
							reportService.getConnection()));
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping(value = "/showRejTxnXlsResult")
	public void showRejTxnXlsResult(WebRequest request, Model model,
			HttpServletRequest httpRequest, RICTxnRptDto ricRejTxnRptDto,
			HttpServletResponse response) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("startDate", ricRejTxnRptDto.getStartDate());
		parameterMap.put("endDate", ricRejTxnRptDto.getEndDate());
		parameterMap.put("txnType", ricRejTxnRptDto.getTxnType());
		parameterMap
				.put("txnTypeDesc",
						(ricRejTxnRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricRejTxnRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("txnSubType", ricRejTxnRptDto.getTxnSubType());
		parameterMap
				.put("txnSubTypeDesc",
						(ricRejTxnRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricRejTxnRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("rejRsn", ricRejTxnRptDto.getReason());
		parameterMap
				.put("rejRsnDesc",
						(ricRejTxnRptDto.getReason() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_REASON_NIC_INV_REJECT,
										ricRejTxnRptDto.getReason())
								.getCodeValueDesc()
								: "");
		parameterMap.put("rejStdt", ricRejTxnRptDto.getRejStartDate());
		parameterMap.put("rejEndDt", ricRejTxnRptDto.getRejEndDate());
		parameterMap.put("reportId", "RIC_RPT_005");
		parameterMap.put("siteId", (!ricRejTxnRptDto.getRicOffice()
				.equalsIgnoreCase("ALL")) ? ricRejTxnRptDto.getRicOffice()
				: "RIC");
		parameterMap
				.put("siteIdDesc",
						(!ricRejTxnRptDto.getRicOffice()
								.equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										ricRejTxnRptDto.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_REJECTED_TRANSACTION_RPT_ID,
							ricReportService.NIC_RIC_REJECTED_TRANSACTION_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameterMap, reportService.getConnection());
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS
					.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporterXLS
					.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
							Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			response.setContentType("application/vnd.ms-excel");
			exporterXLS.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@ResponseBody
	@RequestMapping(value = "/showBatchCardInfoTabluarResult")
	public PaginatedResult<RICBatchCardInfoDto> showBatchCardInfoTabluarResult(
			WebRequest request, Model model,
			RICBatchCardInfoDto ricBatchCardInfoRptDto) throws Exception {
		System.out.println("Txn Start Dt >>"
				+ ricBatchCardInfoRptDto.getTxnStartDate());
		System.out.println("Txn End Dt >>"
				+ ricBatchCardInfoRptDto.getTxnEndDate());
		System.out.println("Issue Dt >>"
				+ ricBatchCardInfoRptDto.getCardIssueDate());
		System.out.println("Issue End Dt >>"
				+ ricBatchCardInfoRptDto.getCardIssueEndDate());
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;

		PaginatedResult<RICBatchCardInfoDto> result = ricReportService
				.getRicBatchCardInfoRptRecordList(ricBatchCardInfoRptDto, page,
						pageSize);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showBatchCardInfoPdfResult")
	public void showBatchCardInfoPdfResult(WebRequest request, Model model,
			RICBatchCardInfoDto ricBatchCardInfoRptDto,
			HttpServletRequest httpRequest, HttpServletResponse response)
			throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap
				.put("startDate",
						(ricBatchCardInfoRptDto.getCardPkgStartDate() != null) ? ricBatchCardInfoRptDto
								.getCardPkgStartDate() : "");
		parameterMap
				.put("endDate",
						(ricBatchCardInfoRptDto.getCardPkgEndDate() != null) ? ricBatchCardInfoRptDto
								.getCardPkgEndDate() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_006");
		parameterMap
				.put("siteId",
						(!ricBatchCardInfoRptDto.getRicOffice()
								.equalsIgnoreCase("ALL")) ? ricBatchCardInfoRptDto
								.getRicOffice() : "RIC");
		parameterMap
				.put("siteIdDesc",
						(!ricBatchCardInfoRptDto.getRicOffice()
								.equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										ricBatchCardInfoRptDto.getRicOffice(),
										RegistrationConstants.CODE_ID_SITE_CODE)
								.getCodeValueDesc()
								: "");
		parameterMap
				.put("txnType",
						(ricBatchCardInfoRptDto.getTxnType() != null) ? ricBatchCardInfoRptDto
								.getTxnType() : "");
		parameterMap
				.put("txnSubtype",
						(ricBatchCardInfoRptDto.getTxnSubType() != null) ? ricBatchCardInfoRptDto
								.getTxnSubType() : "");
		parameterMap
				.put("cardStatus",
						(ricBatchCardInfoRptDto.getCardStatus() != null) ? ricBatchCardInfoRptDto
								.getCardStatus() : "");
		parameterMap
				.put("txnStartDt",
						(ricBatchCardInfoRptDto.getTxnStartDate() != null) ? ricBatchCardInfoRptDto
								.getTxnStartDate() : "");
		parameterMap
				.put("txnEndDt",
						(ricBatchCardInfoRptDto.getTxnEndDate() != null) ? ricBatchCardInfoRptDto
								.getTxnEndDate() : "");
		parameterMap
				.put("issueStartDt",
						(ricBatchCardInfoRptDto.getCardIssueDate() != null) ? ricBatchCardInfoRptDto
								.getCardIssueDate() : "");
		parameterMap
				.put("issueEndDt",
						(ricBatchCardInfoRptDto.getCardIssueEndDate() != null) ? ricBatchCardInfoRptDto
								.getCardIssueEndDate() : "");
		parameterMap
				.put("expiryStartDt",
						(ricBatchCardInfoRptDto.getCardExpiryDate() != null) ? ricBatchCardInfoRptDto
								.getCardExpiryDate() : "");
		parameterMap
				.put("expiryEndDt",
						(ricBatchCardInfoRptDto.getCardExpiryEndDate() != null) ? ricBatchCardInfoRptDto
								.getCardExpiryEndDate() : "");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_BATCHCARD_INFO_DWNLD_RPT_ID,
							ricReportService.NIC_RIC_BATCHCARD_INFO_DWNLD_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			response.setContentType("application/pdf");
			response.getOutputStream().write(
					ReportUtil.getPdfReport(parameterMap, reportStream,
							reportService.getConnection()));
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping(value = "/showBatchCardInfoXlsfResult")
	public void showBatchCardInfoXlsfResult(WebRequest request, Model model,
			RICBatchCardInfoDto ricBatchCardInfoRptDto,
			HttpServletRequest httpRequest, HttpServletResponse response)
			throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap
				.put("startDate",
						(ricBatchCardInfoRptDto.getCardPkgStartDate() != null) ? ricBatchCardInfoRptDto
								.getCardPkgStartDate() : "");
		parameterMap
				.put("endDate",
						(ricBatchCardInfoRptDto.getCardPkgEndDate() != null) ? ricBatchCardInfoRptDto
								.getCardPkgEndDate() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_006");
		parameterMap
				.put("siteId",
						(!ricBatchCardInfoRptDto.getRicOffice()
								.equalsIgnoreCase("ALL")) ? ricBatchCardInfoRptDto
								.getRicOffice() : "RIC");
		parameterMap
				.put("siteIdDesc",
						(!ricBatchCardInfoRptDto.getRicOffice()
								.equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										ricBatchCardInfoRptDto.getRicOffice(),
										RegistrationConstants.CODE_ID_SITE_CODE)
								.getCodeValueDesc()
								: "");
		parameterMap
				.put("txnType",
						(ricBatchCardInfoRptDto.getTxnType() != null) ? ricBatchCardInfoRptDto
								.getTxnType() : "");
		parameterMap
				.put("txnSubtype",
						(ricBatchCardInfoRptDto.getTxnSubType() != null) ? ricBatchCardInfoRptDto
								.getTxnSubType() : "");
		parameterMap
				.put("cardStatus",
						(ricBatchCardInfoRptDto.getCardStatus() != null) ? ricBatchCardInfoRptDto
								.getCardStatus() : "");
		parameterMap
				.put("txnStartDt",
						(ricBatchCardInfoRptDto.getTxnStartDate() != null) ? ricBatchCardInfoRptDto
								.getTxnStartDate() : "");
		parameterMap
				.put("txnEndDt",
						(ricBatchCardInfoRptDto.getTxnEndDate() != null) ? ricBatchCardInfoRptDto
								.getTxnEndDate() : "");
		parameterMap
				.put("issueStartDt",
						(ricBatchCardInfoRptDto.getCardIssueDate() != null) ? ricBatchCardInfoRptDto
								.getCardIssueDate() : "");
		parameterMap
				.put("issueEndDt",
						(ricBatchCardInfoRptDto.getCardIssueEndDate() != null) ? ricBatchCardInfoRptDto
								.getCardIssueEndDate() : "");
		parameterMap
				.put("expiryStartDt",
						(ricBatchCardInfoRptDto.getCardExpiryDate() != null) ? ricBatchCardInfoRptDto
								.getCardExpiryDate() : "");
		parameterMap
				.put("expiryEndDt",
						(ricBatchCardInfoRptDto.getCardExpiryEndDate() != null) ? ricBatchCardInfoRptDto
								.getCardExpiryEndDate() : "");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_BATCHCARD_INFO_DWNLD_RPT_ID,
							ricReportService.NIC_RIC_BATCHCARD_INFO_DWNLD_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameterMap, reportService.getConnection());
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS
					.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporterXLS
					.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
							Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			response.setContentType("application/vnd.ms-excel");
			exporterXLS.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/showExprsPrintRpt")
	public String showExprsPrintRpt(WebRequest request, Model model)
			throws DaoException {
		model.addAttribute("fnSelected", "RIC_RPT_016 :Express Printing Report");
		Map<String, String> txnTypeCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_TYPE);
		if(txnTypeCodesMap.containsKey("CON")){
			txnTypeCodesMap.remove("CON");			
		}
		Map<String, String> txnSubTypeCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE);
		if(txnSubTypeCodesMap.containsKey("CON")){
			txnSubTypeCodesMap.remove("CON");			
		}
		Map<String, String> txnStatusCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_STATUS);
		Map<String, String> siteCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_SITE_CODE);
		Map<String, String> genderCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_GENDER);
		Iterator<Entry<String, String>> sitecodes = siteCodesMap.entrySet()
				.iterator();
		ricSiteCodesMap = new TreeMap<String, String>();
		while (sitecodes.hasNext()) {
			Map.Entry<String, String> entry = sitecodes.next();
			String key = entry.getKey().substring(0, 3);
			if (key.equalsIgnoreCase("RIC")) {
				ricSiteCodesMap.put(entry.getKey(), entry.getValue());
			}
		}
		model.addAttribute("siteCodes", ricSiteCodesMap);
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		model.addAttribute("txnTypeList", txnTypeCodesMap);
		model.addAttribute("txnSubTypeList", txnSubTypeCodesMap);
		model.addAttribute("txnStatus", txnStatusCodesMap);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("gender", genderCodesMap);
		/*
		 * model.addAttribute("estColStDate", estColStDate);
		 * model.addAttribute("estColEndDate", estColEndDate);
		 */
		model.addAttribute("ricExprsPrntRptDto", new RICTxnRptDto());
		return "show.exprsPrintRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showExprsPrntTabluarResult")
	public PaginatedResult<RICTxnRptDto> showExprsPrntTabluarResult(
			WebRequest request, Model model, RICTxnRptDto ricTxnRptDto)
			throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getRicExprsPrintRptRecordList(ricTxnRptDto, page, pageSize);
		return result;

	}

	@ResponseBody
	@RequestMapping(value = "/showExprsPrntPdfResult")
	public void showExprsPrntPdfResult(WebRequest request, Model model,
			RICTxnRptDto ricTxnRptDto, HttpServletResponse response,
			HttpServletRequest httpRequest) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(
				"startDate",
				(ricTxnRptDto.getStartDate() != null) ? ricTxnRptDto
						.getStartDate() : "");
		parameterMap.put("endDate",
				(ricTxnRptDto.getEndDate() != null) ? ricTxnRptDto.getEndDate()
						: "");
		parameterMap
				.put("txnType",
						(ricTxnRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricTxnRptDto.getTxnType())
								.getCodeValueDesc()
								: "");

		parameterMap
				.put("txnSubType",
						(ricTxnRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricTxnRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");

		parameterMap
				.put("txnStatus",
						(ricTxnRptDto.getTxnStatus() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_STATUS,
										ricTxnRptDto.getTxnStatus())
								.getCodeValueDesc()
								: "");

		parameterMap.put(
				"gender",
				(ricTxnRptDto.getGender() != null) ? codesService
						.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_GENDER,
								ricTxnRptDto.getGender()).getCodeValueDesc()
						: "");

		parameterMap.put(
				"estColStDate",
				(ricTxnRptDto.getEstColStartDt() != null) ? ricTxnRptDto
						.getEstColStartDt() : "");
		parameterMap.put(
				"estColEndDate",
				(ricTxnRptDto.getEstColEndDt() != null) ? ricTxnRptDto
						.getEstColEndDt() : "");
		parameterMap.put("reportId", "RIC_RPT_016");
		parameterMap
				.put("regOfficerId",
						(!ricTxnRptDto.getRicOffice().equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										ricTxnRptDto.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getRicExprsPrintRptRecordList(ricTxnRptDto, 1,
						Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		System.out.println("In controller >>" + result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_EXPPRNT_RPT_ID,
							ricReportService.NIC_RIC_EXPPRNT_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperReportsUtils.renderAsPdf(jasperReport, parameterMap,
					JRdataSource, os);
			response.setContentType("application/pdf");
			response.getOutputStream().write(os.toByteArray());

		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping(value = "/showExprsPrntXlsResult")
	public void showExprsPrntxlsResult(WebRequest request, Model model,
			RICTxnRptDto ricTxnRptDto, HttpServletResponse response,
			HttpServletRequest httpRequest) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(
				"startDate",
				(ricTxnRptDto.getStartDate() != null) ? ricTxnRptDto
						.getStartDate() : "");
		parameterMap.put("endDate",
				(ricTxnRptDto.getEndDate() != null) ? ricTxnRptDto.getEndDate()
						: "");
		parameterMap
				.put("txnType",
						(ricTxnRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricTxnRptDto.getTxnType())
								.getCodeValueDesc()
								: "");

		parameterMap
				.put("txnSubType",
						(ricTxnRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricTxnRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");

		parameterMap
				.put("txnStatus",
						(ricTxnRptDto.getTxnStatus() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_STATUS,
										ricTxnRptDto.getTxnStatus())
								.getCodeValueDesc()
								: "");

		parameterMap.put(
				"gender",
				(ricTxnRptDto.getGender() != null) ? codesService
						.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_GENDER,
								ricTxnRptDto.getGender()).getCodeValueDesc()
						: "");

		parameterMap.put(
				"estColStDate",
				(ricTxnRptDto.getEstColStartDt() != null) ? ricTxnRptDto
						.getEstColStartDt() : "");
		parameterMap.put(
				"estColEndDate",
				(ricTxnRptDto.getEstColEndDt() != null) ? ricTxnRptDto
						.getEstColEndDt() : "");
		parameterMap.put("reportId", "RIC_RPT_016");
		parameterMap
				.put("regOfficerId",
						(!ricTxnRptDto.getRicOffice().equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										ricTxnRptDto.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getRicExprsPrintRptRecordList(ricTxnRptDto, 1,
						Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_EXPPRNT_RPT_ID,
							ricReportService.NIC_RIC_EXPPRNT_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperReportsUtils.renderAsXls(jasperReport, parameterMap,
					JRdataSource, os);
			response.setContentType("application/vnd.ms-excel");
			response.getOutputStream().write(os.toByteArray());
	/*	ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_EXPPRNT_RPT_ID,
							ricReportService.NIC_RIC_EXPPRNT_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameterMap, reportService.getConnection());
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS
					.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporterXLS
					.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
							Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			response.setContentType("application/vnd.ms-excel");
			exporterXLS.exportReport();*/
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/showExceptionHandlngRptTabluarResult")
	public PaginatedResult<ExceptionHandlngRptDto> showExceptionHandlngRptTabluarResult(
			WebRequest request, Model model,
			ExceptionHandlngRptDto exceptionHandlngSrch) throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println("page >>" + request.getParameter("page"));
		System.out.println("pageSize >>" + request.getParameter("rp"));
		PaginatedResult<ExceptionHandlngRptDto> result = ricReportService
				.getExceptionHandlingRptRecordList(exceptionHandlngSrch, page,
						pageSize);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showExceptionHandlngRptPdfResult")
	public void showExceptionHandlngRptPdfResult(WebRequest request,
			Model model, ExceptionHandlngRptDto exceptionHandlngSrch,
			HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		System.out.println("Into the Controller");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap
				.put("startDate",
						(exceptionHandlngSrch.getForwardStartDt() != null) ? exceptionHandlngSrch
								.getForwardStartDt() : "");
		parameterMap
				.put("endDate",
						(exceptionHandlngSrch.getForwardEndDt() != null) ? exceptionHandlngSrch
								.getForwardEndDt() : "");
		parameterMap
				.put("resStartDate",
						(exceptionHandlngSrch.getResolutionStartDt() != null) ? exceptionHandlngSrch
								.getResolutionStartDt() : "");
		parameterMap
				.put("resEndDate",
						(exceptionHandlngSrch.getResolutionEndDt() != null) ? exceptionHandlngSrch
								.getResolutionEndDt() : "");
		parameterMap.put("reportId", "RIC_RPT_0013");
		parameterMap
				.put("regOfficerId",
						(!exceptionHandlngSrch.getRicOffice().equalsIgnoreCase(
								"ALL")) ? codesService.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								exceptionHandlngSrch.getRicOffice())
								.getCodeValueDesc() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		PaginatedResult<ExceptionHandlngRptDto> result = ricReportService
				.getExceptionHandlingRptRecordList(exceptionHandlngSrch, 1,
						Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_EXPHANDL_RPT_ID,
							ricReportService.NIC_RIC_EXPHANDL_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperReportsUtils.renderAsPdf(jasperReport, parameterMap,
					JRdataSource, os);
			response.setContentType("application/pdf");
			response.getOutputStream().write(os.toByteArray());

		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping(value = "/showExceptionHandlngRptXlsResult")
	public void showExceptionHandlngRptXlsResult(WebRequest request,
			Model model, ExceptionHandlngRptDto exceptionHandlngSrch,
			HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		System.out.println("Into the Controller");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap
				.put("startDate",
						(exceptionHandlngSrch.getForwardStartDt() != null) ? exceptionHandlngSrch
								.getForwardStartDt() : "");
		parameterMap
				.put("endDate",
						(exceptionHandlngSrch.getForwardEndDt() != null) ? exceptionHandlngSrch
								.getForwardEndDt() : "");
		parameterMap
				.put("resStartDate",
						(exceptionHandlngSrch.getResolutionStartDt() != null) ? exceptionHandlngSrch
								.getResolutionStartDt() : "");
		parameterMap
				.put("resEndDate",
						(exceptionHandlngSrch.getResolutionEndDt() != null) ? exceptionHandlngSrch
								.getResolutionEndDt() : "");
		parameterMap.put("reportId", "RIC_RPT_0013");
		parameterMap
				.put("regOfficerId",
						(!exceptionHandlngSrch.getRicOffice().equalsIgnoreCase(
								"ALL")) ? codesService.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								exceptionHandlngSrch.getRicOffice())
								.getCodeValueDesc() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		PaginatedResult<ExceptionHandlngRptDto> result = ricReportService
				.getExceptionHandlingRptRecordList(exceptionHandlngSrch, 1,
						Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_EXPHANDL_RPT_ID,
							ricReportService.NIC_RIC_EXPHANDL_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameterMap, reportService.getConnection());
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS
					.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporterXLS
					.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
							Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			response.setContentType("application/vnd.ms-excel");
			exporterXLS.exportReport();

		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/showlostNfoundRpt")
	public String showlostNfoundRpt(WebRequest request, Model model)
			throws DaoException {
		model.addAttribute("fnSelected", "RIC_RPT_017 :Lost And Found Report");
		Map<String, String> siteCodesMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_SITE_CODE);
		Iterator<Entry<String, String>> sitecodes = siteCodesMap.entrySet()
				.iterator();
		ricSiteCodesMap = new TreeMap<String, String>();
		while (sitecodes.hasNext()) {
			Map.Entry<String, String> entry = sitecodes.next();
			String key = entry.getKey().substring(0, 3);
			if (key.equalsIgnoreCase("RIC")) {
				ricSiteCodesMap.put(entry.getKey(), entry.getValue());
			}
		}
		model.addAttribute("siteCodes", ricSiteCodesMap);
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString(); 
		Iterator<Entry<String, String>> txnTypeCodes =  codesService.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_TYPE)
				.entrySet().iterator();
		Map<String, String>  txnTypeCodeMap = new TreeMap<String, String>();
		
		while (txnTypeCodes.hasNext()) {
			Map.Entry<String, String> entry = txnTypeCodes.next();
//			if (entry.getKey().equalsIgnoreCase("RIC_CARD_LOST")
//					|| entry.getKey().equalsIgnoreCase("RIC_CARD_FOUND")) {
				txnTypeCodeMap.put(entry.getKey(), entry.getValue());
			//}
		}
		if(txnTypeCodeMap.containsKey("CON")){
			txnTypeCodeMap.remove("CON");			
		}
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("txnTypeList", txnTypeCodeMap);
		model.addAttribute("ricLostNfoundRptDto", new RICTxnRptDto());
		return "show.lostNfoundRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showLostNfoundTabluarResult")
	public PaginatedResult<RICTxnRptDto> showLostNfoundTabluarResult(
			WebRequest request, Model model, RICTxnRptDto ricTxnRptDto)
			throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println(">>>" + ricTxnRptDto.getStartDate());
		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getRicLostNfoundRptRecordList(ricTxnRptDto, page, pageSize);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showLostNfoundPdfResult")
	public void showLostNfoundPdfResult(WebRequest request, Model model,
			RICTxnRptDto ricTxnRptDto, HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(
				"startDate",
				(ricTxnRptDto.getStartDate() != null) ? ricTxnRptDto
						.getStartDate() : "");
		parameterMap.put("endDate",
				(ricTxnRptDto.getEndDate() != null) ? ricTxnRptDto.getEndDate()
						: "");
		parameterMap.put("reportId", "RIC_RPT_017");
		parameterMap
				.put("txnType",
						(ricTxnRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricTxnRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap
				.put("regOfficerId",
						(!ricTxnRptDto.getRicOffice().equalsIgnoreCase(
								"ALL")) ? codesService.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								ricTxnRptDto.getRicOffice())
								.getCodeValueDesc() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName()); 
		if (ricTxnRptDto.getNoOfDays().equalsIgnoreCase("ALL")) {
			System.out.println("--");
			parameterMap.put("noOfDays", "--");
		} else {
			System.out.println("Days");
			parameterMap.put("noOfDays", ricTxnRptDto.getNoOfDays() + " Days");
		}

		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getRicLostNfoundRptRecordList(ricTxnRptDto, 1, Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_LSTNFND_RPT_ID,
							ricReportService.NIC_RIC_LSTNFND_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperReportsUtils.renderAsPdf(jasperReport, parameterMap,
					JRdataSource, os);
			response.setContentType("application/pdf");
			response.getOutputStream().write(os.toByteArray());

		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping(value = "/showLostNfoundXlsResult")
	public void showLostNfoundXlsResult(WebRequest request, Model model,
			RICTxnRptDto ricTxnRptDto, HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(
				"startDate",
				(ricTxnRptDto.getStartDate() != null) ? ricTxnRptDto
						.getStartDate() : "");
		parameterMap.put("endDate",
				(ricTxnRptDto.getEndDate() != null) ? ricTxnRptDto.getEndDate()
						: "");
		parameterMap.put("reportId", "RIC_RPT_017");
		parameterMap
				.put("txnType",
						(ricTxnRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricTxnRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap
				.put("regOfficerId",
						(!ricTxnRptDto.getRicOffice().equalsIgnoreCase(
								"ALL")) ? codesService.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								ricTxnRptDto.getRicOffice())
								.getCodeValueDesc() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName()); 
		if (ricTxnRptDto.getNoOfDays().equalsIgnoreCase("ALL")) {
			System.out.println("--");
			parameterMap.put("noOfDays", "--");
		} else {
			System.out.println("Days");
			parameterMap.put("noOfDays", ricTxnRptDto.getNoOfDays() + " Days");
		}

		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getRicLostNfoundRptRecordList(ricTxnRptDto, 1, Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_LSTNFND_RPT_ID,
							ricReportService.NIC_RIC_LSTNFND_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperReportsUtils.renderAsXls(jasperReport, parameterMap,
					JRdataSource, os);
			response.setContentType("application/vnd.ms-excel");
			response.getOutputStream().write(os.toByteArray());
			/*JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameterMap, reportService.getConnection());
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS
					.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
					response.getOutputStream());
			exporterXLS
					.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
							Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			response.setContentType("application/vnd.ms-excel");
			exporterXLS.exportReport();*/

		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	@RequestMapping(value = "/txnsubtype", method = RequestMethod.GET)   
	public @ResponseBody String getTxnSubType(@RequestParam String subtype) {
		
		String jsonStr = null;
		List<CodeValues> list=null;
		Map<String, String> entityMap=new HashMap<String, String>();
		if(subtype.equals("ALL")||subtype.equals("")){
			
			entityMap = codesService
					.getCodeValues(RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE);
			
		}else {
		list= codesService.getParentCodeValues(subtype);
		
		 
		  for(CodeValues codeValue:list ){
			entityMap.put(codeValue.getId().getCodeValue(), codeValue.getCodeValueDesc());
		  }
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(entityMap);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonStr;
		
	}
}
