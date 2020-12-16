package com.nec.asia.nic.ricreport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.nec.asia.nic.comp.trans.dto.RICPymtRptDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnRptDto;
import com.nec.asia.nic.comp.trans.dto.RICWaiverRptDto;
import com.nec.asia.nic.comp.trans.service.RICReportService;
import com.nec.asia.nic.framework.Constants;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.domain.ReportTemplate;
import com.nec.asia.nic.framework.report.service.ReportManagementService;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.ReportUtil;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/pymtReport")
public class RICPymtRptController extends AbstractController {

	@Autowired
	private CodesService codesService;

	@Autowired
	private RICReportService ricReportService;
	
	@Autowired
	private ReportManagementService reportService;

	Map<String, String> ricSiteCodesMap;

	@RequestMapping(value = "/showricPymtRpt")
	public String showTransCardStatus(WebRequest request, Model model) {
		model.addAttribute("fnSelected", "RIC_RPT_002 :RIC Payment Report");
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
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
		Map<String, String> citizenCodesMap = codesService
				.getCodeValues("CITIZEN_TYPE");
		model.addAttribute("siteCodes", ricSiteCodesMap);
		model.addAttribute("citizenTypeList", citizenCodesMap);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("ricPymtRptDto", new RICPymtRptDto());
		return "show.ricPymtRpt";
	}

	@RequestMapping(value = "/showricWaiverRpt")
	public String showricWaiverRpt(WebRequest request, Model model) {
		model.addAttribute("fnSelected", "RIC_RPT_003 :RIC Waiver Report");
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
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
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
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("txnTypeList", txnTypeCodesMap);
		model.addAttribute("txnSubTypeList", txnSubTypeCodesMap);
		model.addAttribute("siteCodes", ricSiteCodesMap);
		model.addAttribute("ricWaiverRptDto", new RICWaiverRptDto());
		return "show.ricWaiverRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showTabluarResult")
	public PaginatedResult<RICPymtRptDto> showTabluarResult(WebRequest request,
			Model model, RICPymtRptDto ricPymtRptDto) throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println("page >>" + page);
		System.out.println("pageSize >>" + pageSize);
		PaginatedResult<RICPymtRptDto> result = ricReportService
				.getRicPymtRptRecordList(ricPymtRptDto, page, pageSize);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showPdfResult")
	public void showPdfResult(WebRequest request, Model model,
			HttpServletRequest httpRequest, RICPymtRptDto ricPymtRptDto,
			HttpServletResponse response) throws Exception {
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_002");
		parameterMap.put( "ricSite",  (!ricPymtRptDto.getRicOffice().equalsIgnoreCase("ALL"))? 
				ricPymtRptDto.getRicOffice() 
		: "RIC"); 
		parameterMap.put(
				"ricSiteDesc",
				(!ricPymtRptDto.getRicOffice().equalsIgnoreCase("ALL")) ? codesService
						.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								ricPymtRptDto.getRicOffice())
						.getCodeValueDesc() : "");
		parameterMap.put("startDate", ricPymtRptDto.getStartDate());
		parameterMap.put("endDate", ricPymtRptDto.getEndDate());
		parameterMap
				.put("txnTypeDesc",
						(ricPymtRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricPymtRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap
				.put("txnSubtypeDesc",
						(ricPymtRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricPymtRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("citizenTypeDesc",
				(ricPymtRptDto.getCitizenType() != null && ricPymtRptDto
						.getCitizenType() != "") ? (ricPymtRptDto
						.getCitizenType().equalsIgnoreCase("Y") ? "YES" : "NO")
						: "ALL");
		parameterMap.put("txnType",ricPymtRptDto.getTxnType());
		parameterMap.put("txnSubtype",ricPymtRptDto
				.getTxnSubType());
		parameterMap.put("citizenType",ricPymtRptDto
				.getCitizenType()); 
		try {
			ReportTemplate template = reportService
					.getReportTemplateById("NIC_RIC_RPT_002",
							"RIC Payment Report");
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
	@RequestMapping(value = "/showXlsResult")
	public void showXlsResult(WebRequest request, Model model,
			HttpServletRequest httpRequest, RICPymtRptDto ricPymtRptDto,
			HttpServletResponse response) throws Exception {
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_002");
		parameterMap.put( "ricSite",  (!ricPymtRptDto.getRicOffice().equalsIgnoreCase("ALL"))? 
				ricPymtRptDto.getRicOffice() 
		: "RIC"); 
		parameterMap.put(
				"ricSiteDesc",
				(!ricPymtRptDto.getRicOffice().equalsIgnoreCase("ALL")) ? codesService
						.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								ricPymtRptDto.getRicOffice())
						.getCodeValueDesc() : "");
		parameterMap.put("startDate", ricPymtRptDto.getStartDate());
		parameterMap.put("endDate", ricPymtRptDto.getEndDate());
		parameterMap
				.put("txnTypeDesc",
						(ricPymtRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricPymtRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap
				.put("txnSubtypeDesc",
						(ricPymtRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricPymtRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("citizenTypeDesc",
				(ricPymtRptDto.getCitizenType() != null && ricPymtRptDto
						.getCitizenType() != "") ? (ricPymtRptDto
						.getCitizenType().equalsIgnoreCase("Y") ? "YES" : "NO")
						: "ALL");
		parameterMap.put("txnType",ricPymtRptDto.getTxnType());
		parameterMap.put("txnSubtype",ricPymtRptDto
				.getTxnSubType());
		parameterMap.put("citizenType",ricPymtRptDto
				.getCitizenType()); 
		try {
			ReportTemplate template = reportService
					.getReportTemplateById("NIC_RIC_RPT_002",
							"RIC Payment Report");
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			/*response.setContentType("application/pdf");
			response.getOutputStream().write(
					ReportUtil.getPdfReport(parameterMap, reportStream,
							reportService.getConnection()));*/
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
	@RequestMapping(value = "/showWavrTabluarResult")
	public PaginatedResult<RICWaiverRptDto> showWavrTabluarResult(WebRequest request,
			Model model, RICWaiverRptDto ricWaiverRptDto) throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println("page >>" + page);
		System.out.println("pageSize >>" + pageSize);
		PaginatedResult<RICWaiverRptDto> result = ricReportService
				.getRicWaiverRptRecordList(ricWaiverRptDto, page, pageSize);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/showWavrPdfResult")
	public void showWavrPdfResult(WebRequest request, Model model,
			HttpServletRequest httpRequest, RICWaiverRptDto ricWaiverRptDto,
			HttpServletResponse response) throws Exception {
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_003");
		parameterMap.put( "ricSite",  (!ricWaiverRptDto.getRicOffice().equalsIgnoreCase("ALL"))? 
				ricWaiverRptDto.getRicOffice() 
		: "RIC"); 
		parameterMap.put(
				"ricSiteDesc",
				(!ricWaiverRptDto.getRicOffice().equalsIgnoreCase("ALL")) ? codesService
						.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								ricWaiverRptDto.getRicOffice())
						.getCodeValueDesc() : "");
		parameterMap.put("startDate", ricWaiverRptDto.getStartDate());
		parameterMap.put("endDate", ricWaiverRptDto.getEndDate());
		parameterMap
				.put("txnTypeDesc",
						(ricWaiverRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricWaiverRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap
				.put("txnSubtypeDesc",
						(ricWaiverRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricWaiverRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("citizenTypeDesc",
				(ricWaiverRptDto.getCitizenType() != null && ricWaiverRptDto
						.getCitizenType() != "") ? (ricWaiverRptDto
						.getCitizenType().equalsIgnoreCase("Y") ? "YES" : "NO")
						: "ALL");
		parameterMap.put("txnType",ricWaiverRptDto.getTxnType());
		parameterMap.put("txnSubtype",ricWaiverRptDto
				.getTxnSubType());
		parameterMap.put("citizenType",ricWaiverRptDto
				.getCitizenType());
		parameterMap.put("servedById",ricWaiverRptDto
				.getServedBy()); 
		try {
			ReportTemplate template = reportService
					.getReportTemplateById("NIC_RIC_RPT_003",
							"RIC Wavier Report");
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
	@RequestMapping(value = "/showWaverExcelResult")
	public void showWavrXlsResult(WebRequest request, Model model,
			HttpServletRequest httpRequest, RICWaiverRptDto ricWaiverRptDto,
			HttpServletResponse response) throws Exception {
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_003");
		parameterMap.put( "ricSite",  (!ricWaiverRptDto.getRicOffice().equalsIgnoreCase("ALL"))? 
				ricWaiverRptDto.getRicOffice() 
		: "RIC"); 
		parameterMap.put(
				"ricSiteDesc",
				(!ricWaiverRptDto.getRicOffice().equalsIgnoreCase("ALL")) ? codesService
						.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								ricWaiverRptDto.getRicOffice())
						.getCodeValueDesc() : "");
		parameterMap.put("startDate", ricWaiverRptDto.getStartDate());
		parameterMap.put("endDate", ricWaiverRptDto.getEndDate());
		parameterMap
				.put("txnTypeDesc",
						(ricWaiverRptDto.getTxnType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_TYPE,
										ricWaiverRptDto.getTxnType())
								.getCodeValueDesc()
								: "");
		parameterMap
				.put("txnSubtypeDesc",
						(ricWaiverRptDto.getTxnSubType() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE,
										ricWaiverRptDto.getTxnSubType())
								.getCodeValueDesc()
								: "");
		parameterMap.put("citizenTypeDesc",
				(ricWaiverRptDto.getCitizenType() != null && ricWaiverRptDto
						.getCitizenType() != "") ? (ricWaiverRptDto
						.getCitizenType().equalsIgnoreCase("Y") ? "YES" : "NO")
						: "ALL");
		parameterMap.put("txnType",ricWaiverRptDto.getTxnType());
		parameterMap.put("txnSubtype",ricWaiverRptDto
				.getTxnSubType());
		parameterMap.put("citizenType",ricWaiverRptDto
				.getCitizenType());
		parameterMap.put("servedById",ricWaiverRptDto
				.getServedBy()); 
		
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
					.getReportTemplateById("NIC_RIC_RPT_003",
							"RIC Wavier Report");
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			
			/*response.setContentType("application/pdf");
			response.getOutputStream().write(
					ReportUtil.getPdfReport(parameterMap, reportStream,
							reportService.getConnection()));
			*/
			
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
