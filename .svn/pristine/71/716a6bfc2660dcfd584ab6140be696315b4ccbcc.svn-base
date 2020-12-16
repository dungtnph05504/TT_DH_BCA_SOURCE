package com.nec.asia.nic.ricreport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.nec.asia.nic.comp.trans.dto.CardStatusRptDTO;
import com.nec.asia.nic.comp.trans.dto.RICTxnRptDto;
import com.nec.asia.nic.comp.trans.service.RICReportService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.domain.ReportTemplate;
import com.nec.asia.nic.framework.report.service.ReportManagementService;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.ReportUtil;
import com.nec.asia.nic.utils.Constants;
import com.nec.asia.nic.utils.ExporterService;
import com.nec.asia.nic.web.session.UserSession;

//@Controller
//@RequestMapping(value = "/cardStatusReport")
public class CardStatusRptController extends AbstractController {

	@Autowired
	private RICReportService ricReportService;

	@Autowired
	private ReportManagementService reportService;
	@Autowired
	private CodesService codesService;

	Map<String, String> ricSiteCodesMap;

	@RequestMapping(value = "/showUnColCardStRpt")
	public String showUnColCardStRpt(WebRequest request, Model model) {
		model.addAttribute("fnSelected",
				"RIC_RPT_015: Uncollected Card Status Report");
		
		
		Map<String, String> siteCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_SITE_CODE);
		Iterator<Entry<String, String>> sitecodes = siteCodesMap.entrySet()
				.iterator();
		ricSiteCodesMap = new TreeMap<String, String>();
		while (sitecodes.hasNext()) {
			Map.Entry<String, String> entry = sitecodes.next();
			String key = entry.getKey().substring(0, 3);
			//ricSiteCodesMap.put(entry.getKey(), entry.getValue());		
			if (key.equalsIgnoreCase("RIC")) {
			 ricSiteCodesMap.put(entry.getKey(), entry.getValue()); }
			 
		}
		/*
		 * String endDate = new SimpleDateFormat("d-MMM-yyyy").format( new
		 * java.util.Date()).toString(); String startDate = new
		 * SimpleDateFormat("d-MMM-yyyy").format( new
		 * java.util.Date()).toString();
		 */
		String currDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		model.addAttribute("siteCodes", ricSiteCodesMap);
		model.addAttribute("startDate", currDate);
		model.addAttribute("endDate", currDate);
		model.addAttribute("estColStartDt", currDate);
		model.addAttribute("estColEndDt", currDate);
		model.addAttribute("unColcardStusRptDto", new RICTxnRptDto());
		return "show.unColCrdStRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showUnColCardStusTabluarResult")
	public PaginatedResult<RICTxnRptDto> showUnColCardStusTabluarResult(
			WebRequest request, Model model,
			RICTxnRptDto unColCardStatuSrchCriteria) throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println("page >>" + page);
		System.out.println("pageSize >>" + pageSize);
		
		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getUNCollectedStatusRptRecordList(unColCardStatuSrchCriteria,
						page, pageSize);
		System.out.println("showUnColCardStusTabluarResult >>"
				+ result.getTotal());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showUnColCardStusPdfResult")
	public void showUnColCardStusPdfResult(WebRequest request, Model model,
			RICTxnRptDto unColCardStatuSrchCriteria,
			HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		System.out.println("showUnColCardStusPdfResult");
		Map<String, Object> parameterMap = new HashMap<String, Object>();		
		parameterMap
				.put("ricSiteDesc",
						(unColCardStatuSrchCriteria.getRicOffice() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										unColCardStatuSrchCriteria
												.getRicOffice()).getCodeValueDesc(): "ALL");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_015");
		parameterMap.put("noOfDays", unColCardStatuSrchCriteria.getNoOfDays());
		parameterMap
				.put("startDate",
						(unColCardStatuSrchCriteria.getStartDate() != null) ? unColCardStatuSrchCriteria
								.getStartDate() : "");
		parameterMap
				.put("endDate",
						(unColCardStatuSrchCriteria.getEndDate() != null) ? unColCardStatuSrchCriteria
								.getEndDate() : "");
		parameterMap
				.put("colStartDate",
						(unColCardStatuSrchCriteria.getEstColStartDt() != null) ? unColCardStatuSrchCriteria
								.getEstColStartDt() : "");
		parameterMap
				.put("colEndDate",
						(unColCardStatuSrchCriteria.getEstColEndDt() != null) ? unColCardStatuSrchCriteria
								.getEstColEndDt() : "");
		
		String sites="";
		if(unColCardStatuSrchCriteria
				.getRicOffice()==null||unColCardStatuSrchCriteria
						.getRicOffice().equals("ALL")){
		Map<String, String> siteCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_SITE_CODE);
		Iterator<Entry<String, String>> sitecodes = siteCodesMap.entrySet()
				.iterator();
		ricSiteCodesMap = new TreeMap<String, String>();
		while (sitecodes.hasNext()) {
			Map.Entry<String, String> entry = sitecodes.next();
			String key = entry.getKey().substring(0, 3);
			//ricSiteCodesMap.put(entry.getKey(), entry.getValue());		
			if (key.equalsIgnoreCase("RIC")) {
				sites=sites+entry.getKey()+",";
			}
			 
		}
		parameterMap.put( "ricSite",  Arrays.asList(sites.split(",")));
		}else {
			sites=unColCardStatuSrchCriteria.getRicOffice()+",";
			parameterMap.put( "ricSite",  Arrays.asList(sites.split(",")));
		}
		
		try {
			
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_UNCOLLECTED_CARD_STATUS_RPT_ID,
							ricReportService.NIC_RIC_UNCOLLECTED_CARD_STATUS_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			response.setContentType("application/pdf");
			response.getOutputStream().write(
					ReportUtil.getPdfReport(parameterMap, reportStream,
							reportService.getConnection()));
			
			
			System.out.println("showUnColCardStusPdfResult");
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*	
	@ResponseBody
	@RequestMapping(value = "/showUnColCardStusXlsResult")
	public void showUnColCardStusXlsResult(WebRequest request, Model model,
			RICTxnRptDto unColCardStatuSrchCriteria,
			HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		System.out.println("showUnColCardStusPdfResult");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("ricSite", unColCardStatuSrchCriteria.getRicOffice());
		parameterMap
				.put("ricSiteDesc",
						(unColCardStatuSrchCriteria.getRicOffice() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										unColCardStatuSrchCriteria
												.getRicOffice())
								.getCodeValueDesc()
								: "ALL");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_015");
		parameterMap.put("noOfDays", unColCardStatuSrchCriteria.getNoOfDays());
		parameterMap
				.put("startDate",
						(unColCardStatuSrchCriteria.getStartDate() != null) ? unColCardStatuSrchCriteria
								.getStartDate() : "");
		parameterMap
				.put("endDate",
						(unColCardStatuSrchCriteria.getEndDate() != null) ? unColCardStatuSrchCriteria
								.getEndDate() : "");
		parameterMap
				.put("colStartDate",
						(unColCardStatuSrchCriteria.getEstColStartDt() != null) ? unColCardStatuSrchCriteria
								.getEstColStartDt() : "");
		parameterMap
				.put("colEndDate",
						(unColCardStatuSrchCriteria.getEstColEndDt() != null) ? unColCardStatuSrchCriteria
								.getEstColEndDt() : "");
		try {
			System.out.println("showUnColCardStusPdfResult");
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_UNCOLLECTED_CARD_STATUS_RPT_ID,
							ricReportService.NIC_RIC_UNCOLLECTED_CARD_STATUS_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(template.getTemplateImage());
			
			JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameterMap, reportService.getConnection());
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			response.setContentType("application/vnd.ms-excel");
			exporterXLS.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/


	@ResponseBody
	@RequestMapping(value = "/showUnColCardStusExcelResult")
	public void showUnColCardStusExcelResult(WebRequest request, Model model,
			RICTxnRptDto unColCardStatuSrchCriteria,
			HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		System.out.println("showUnColCardStusExcelResult");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		System.out.println("Site code"
				+ unColCardStatuSrchCriteria.getRicOffice());
	//	parameterMap.put("ricSite", unColCardStatuSrchCriteria.getRicOffice());
		parameterMap
				.put("ricSiteDesc",
						(unColCardStatuSrchCriteria.getRicOffice() != null) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										unColCardStatuSrchCriteria
												.getRicOffice())
								.getCodeValueDesc()
								: "ALL");
		String sites="";
		if(unColCardStatuSrchCriteria
				.getRicOffice()==null||unColCardStatuSrchCriteria
						.getRicOffice().equals("ALL")){
		Map<String, String> siteCodesMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_SITE_CODE);
		Iterator<Entry<String, String>> sitecodes = siteCodesMap.entrySet()
				.iterator();
		ricSiteCodesMap = new TreeMap<String, String>();
		while (sitecodes.hasNext()) {
			Map.Entry<String, String> entry = sitecodes.next();
			String key = entry.getKey().substring(0, 3);
			//ricSiteCodesMap.put(entry.getKey(), entry.getValue());		
			if (key.equalsIgnoreCase("RIC")) {
				sites=sites+entry.getKey()+",";
			}
			 
		}
		parameterMap.put( "ricSite",  Arrays.asList(sites.split(",")));
		}else {
			sites=unColCardStatuSrchCriteria.getRicOffice()+",";
			parameterMap.put( "ricSite",  Arrays.asList(sites.split(",")));
		}
		
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_015");
		parameterMap.put("noOfDays", unColCardStatuSrchCriteria.getNoOfDays());
		parameterMap
				.put("startDate",
						(unColCardStatuSrchCriteria.getStartDate() != null) ? unColCardStatuSrchCriteria
								.getStartDate() : "");
		parameterMap
				.put("endDate",
						(unColCardStatuSrchCriteria.getEndDate() != null) ? unColCardStatuSrchCriteria
								.getEndDate() : "");
		parameterMap
				.put("colStartDate",
						(unColCardStatuSrchCriteria.getEstColStartDt() != null) ? unColCardStatuSrchCriteria
								.getEstColStartDt() : "");
		parameterMap
				.put("colEndDate",
						(unColCardStatuSrchCriteria.getEstColEndDt() != null) ? unColCardStatuSrchCriteria
								.getEstColEndDt() : "");
		parameterMap.put(
				"JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS",
				Boolean.TRUE);
		parameterMap.put("JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET",
				Boolean.FALSE);
		parameterMap.put("JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE",
				Boolean.TRUE);
		parameterMap.put("JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND",
				Boolean.FALSE);
		parameterMap.put("IS_IGNORE_PAGINATION", true);
		try {
			System.out.println("showUnColCardStusPdfResult");
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_UNCOLLECTED_CARD_STATUS_RPT_ID,
							ricReportService.NIC_RIC_UNCOLLECTED_CARD_STATUS_RPT_NAME);
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
			/*JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameterMap, reportService.getConnection());
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.getOutputStream().write(
					ReportUtil.getPdfReport(parameterMap, reportStream,
							reportService.getConnection()));		
			
			 * JasperPrint jp = JasperFillManager.fillReport(jasperReport,
			 * parameterMap, JRdataSource);
			 * 
			 * 
			 
			response = new ExporterService().export(
					ExporterService.EXTENSION_TYPE_EXCEL, print, response, os,
					"UncollectedCardRpt.xls");
			response.getOutputStream().write(os.toByteArray());*/

			/*
			 * JRXlsExporter exporterXLS = new JRXlsExporter();
			 * exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
			 * print);
			 * exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
			 * response.getOutputStream());
			 * exporterXLS.setParameter(JRXlsExporterParameter
			 * .IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			 * exporterXLS.setParameter(JRXlsExporterParameter
			 * .IS_DETECT_CELL_TYPE, Boolean.TRUE);
			 * exporterXLS.setParameter(JRXlsExporterParameter
			 * .IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			 * exporterXLS.setParameter
			 * (JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
			 * Boolean.TRUE);
			 * response.setContentType("application/vnd.ms-excel");
			 * exporterXLS.exportReport();
			 */
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/showCardCollectedRpt")
	public String showCardCollectedRpt(WebRequest request, Model model) {
		model.addAttribute("fnSelected",
				"RIC_RPT_007: Card Collected Status Report");
		Map<String, String> cardStatusMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_CARD_STATUS);
		model.addAttribute("cardStatusVal", cardStatusMap);
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
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		model.addAttribute("startDate", endDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("cardStatusRptDto", new CardStatusRptDTO());
		return "show.cardCollectedRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showCardColTabluarResult")
	public PaginatedResult<CardStatusRptDTO> showCardColTabluarResult(
			WebRequest request, Model model,
			CardStatusRptDTO cardColSrchCriteria) throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println("page >>" + page);
		System.out.println("pageSize >>" + pageSize);
		PaginatedResult<CardStatusRptDTO> result = ricReportService
				.getCardCollectedStatusRptRecordList(cardColSrchCriteria, page,
						pageSize);
		System.out.println("showCardColTabluarResult >>" + result.getTotal());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showCardColPdfResult")
	public void showCardColPdfResult(WebRequest request, Model model,
			CardStatusRptDTO cardColSrchCriteria, HttpServletResponse response,
			HttpServletRequest httpRequest) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("Report_ID", "RIC_RPT_007");
		parameterMap.put("RIC_SITE", (!cardColSrchCriteria.getRicOffice()
				.equalsIgnoreCase("ALL")) ? cardColSrchCriteria.getRicOffice()
				: "RIC");
		parameterMap
				.put("RIC_SITE_DESC",
						(!cardColSrchCriteria.getRicOffice().equalsIgnoreCase(
								"ALL")) ? codesService.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								cardColSrchCriteria.getRicOffice())
								.getCodeValueDesc() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("USER_ID", userSession.getUserName());
		parameterMap.put("FROM_DATE", cardColSrchCriteria.getCardCollStartDt());
		parameterMap.put("TO_DATE", cardColSrchCriteria.getCardCollEndDt());
		parameterMap.put("CARD_STATUS", cardColSrchCriteria.getCardStatus());
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_CARD_COLL_STATUS_RPT_ID,
							ricReportService.NIC_RIC_CARD_COLL_STATUS_RPT_NAME);
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
	@RequestMapping(value = "/showCardColXlsResult")
	public void showCardColXlsResult(WebRequest request, Model model,
			CardStatusRptDTO cardColSrchCriteria, HttpServletResponse response,
			HttpServletRequest httpRequest) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("Report_ID", "RIC_RPT_007");
		parameterMap.put("RIC_SITE", (!cardColSrchCriteria.getRicOffice()
				.equalsIgnoreCase("ALL")) ? cardColSrchCriteria.getRicOffice()
				: "RIC");
		parameterMap
				.put("RIC_SITE_DESC",
						(!cardColSrchCriteria.getRicOffice().equalsIgnoreCase(
								"ALL")) ? codesService.getCodeValueByIdName(
								RegistrationConstants.CODE_ID_SITE_CODE,
								cardColSrchCriteria.getRicOffice())
								.getCodeValueDesc() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("USER_ID", userSession.getUserName());
		parameterMap.put("FROM_DATE", cardColSrchCriteria.getCardCollStartDt());
		parameterMap.put("TO_DATE", cardColSrchCriteria.getCardCollEndDt());
		parameterMap.put("CARD_STATUS", cardColSrchCriteria.getCardStatus());
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_CARD_COLL_STATUS_RPT_ID,
							ricReportService.NIC_RIC_CARD_COLL_STATUS_RPT_NAME);
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
	@RequestMapping(value = "/showCardColExcelResult")
	public void showCardColExcelResult(WebRequest request, Model model,
			CardStatusRptDTO cardColSrchCriteria, HttpServletResponse response)
			throws Exception {

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		//TODO map to description
		parameterMap.put("regOfficerId", cardColSrchCriteria.getRicOffice());
		parameterMap.put("USER_ID", "USER1");
		parameterMap.put("Report_ID", "RIC_RPT_007");
		parameterMap.put("CARD_STATUS",
						(cardColSrchCriteria.getCardStatus() != null) ? cardColSrchCriteria
								.getCardStatus() : "");
		parameterMap.put("RIC_SITE",
						(cardColSrchCriteria.getRicOffice() != null) ? cardColSrchCriteria
								.getRicOffice() : "");
		parameterMap.put("TO_DATE",
						(cardColSrchCriteria.getCardCollEndDt() != null) ? cardColSrchCriteria
								.getCardCollEndDt() : "");
		parameterMap.put("FROM_DATE",
						(cardColSrchCriteria.getCardCollStartDt() != null) ? cardColSrchCriteria
								.getCardCollStartDt() : "");
		FileOutputStream fileOuputStream = null;
		PaginatedResult<CardStatusRptDTO> result = new PaginatedResult<CardStatusRptDTO>();
		//PaginatedResult<CardStatusRptDTO> result = cardIssuanceService
		//		.getCardCollectedStatusRptRecordList(cardColSrchCriteria, 1, 3,	true);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			InputStream reportStream = this.getClass().getResourceAsStream("/Card Collected Status Report.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
			JasperReportsUtils.renderAsXls(jasperReport, parameterMap,JRdataSource, os);

			//fileOuputStream = new FileOutputStream("c:\\pdf\\Card Collected Status Report.pdf");
			fileOuputStream = new FileOutputStream("Card Collected Status Report.pdf");
			fileOuputStream.write(os.toByteArray());
			fileOuputStream.close();

			response.setContentType("application/pdf");
			response.getOutputStream().write(os.toByteArray());
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/showCardRejectedRpt")
	public String showCardRejectedRpt(WebRequest request, Model model) {
		model.addAttribute("fnSelected",
				"RIC_RPT_008: Card Rejected Status Report ");
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();

		Map<String, String> rejReasonMap = codesService.getCodeValues(RegistrationConstants.CODE_ID_REASON_REJECT_ISSUANCE);
		model.addAttribute("rejReasonVal", rejReasonMap);
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
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("siteCodes", ricSiteCodesMap);
		model.addAttribute("cardStatusRptDto", new CardStatusRptDTO());
		return "show.cardRejectedRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showCardRejTabluarResult")
	public PaginatedResult<CardStatusRptDTO> showCardRejTabluarResult(
			WebRequest request, Model model,
			CardStatusRptDTO cardColSrchCriteria) throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		PaginatedResult<CardStatusRptDTO> result = ricReportService
				.getCardRejectedRptRecordList(cardColSrchCriteria, page,
						pageSize);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showCardRejPdfResult")
	public void showCardRejPdfResult(WebRequest request, Model model,
			CardStatusRptDTO cardColSrchCriteria, HttpServletResponse response,
			HttpServletRequest httpRequest) throws Exception {
		System.out.println("Entering in to controller");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(
				"regOfficerId",
				(cardColSrchCriteria.getRicOffice() != null&&!cardColSrchCriteria.getRicOffice().equals("ALL")) ? codesService
						.getCodeValueByIdName(
								cardColSrchCriteria.getRicOffice(),
								RegistrationConstants.CODE_ID_SITE_CODE)
						.getCodeValueDesc() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("USER_ID", userSession.getUserName());
		parameterMap.put("Report_ID", "RIC_RPT_008");
		parameterMap
				.put("REJ_RSN",
						(cardColSrchCriteria.getRejectionReason() != null) ? cardColSrchCriteria
								.getRejectionReason() : "");
		parameterMap
				.put("RIC_SITE",
						(cardColSrchCriteria.getRicOffice() != null) ? cardColSrchCriteria
								.getRicOffice() : "");
		parameterMap
				.put("TO_DATE",
						(cardColSrchCriteria.getCardCollEndDt() != null) ? cardColSrchCriteria
								.getCardCollEndDt() : "");
		parameterMap
				.put("FROM_DATE",
						(cardColSrchCriteria.getCardCollStartDt() != null) ? cardColSrchCriteria
								.getCardCollStartDt() : "");
		List<CardStatusRptDTO> result = ricReportService
				.getCardRejectedRptRecordListForPDF(cardColSrchCriteria);
		System.out.println(" result >> >>" + result.size());
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(result);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_CARD_REJ_STATUS_RPT_ID,
							ricReportService.NIC_RIC_CARD_REJ_STATUS_RPT_NAME);
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
	@RequestMapping(value = "/showCardRejXlsResult")
	public void showCardRejXlsResult(WebRequest request, Model model,
			CardStatusRptDTO cardColSrchCriteria, HttpServletResponse response,
			HttpServletRequest httpRequest) throws Exception {
		System.out.println("Entering in to controller");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(
				"regOfficerId",
				(cardColSrchCriteria.getRicOffice() != null&&!cardColSrchCriteria.getRicOffice().equals("ALL")) ? codesService
						.getCodeValueByIdName(
								cardColSrchCriteria.getRicOffice(),
								RegistrationConstants.CODE_ID_SITE_CODE)
						.getCodeValueDesc() : "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("USER_ID", userSession.getUserName());
		parameterMap.put("Report_ID", "RIC_RPT_008");
		parameterMap
				.put("REJ_RSN",
						(cardColSrchCriteria.getRejectionReason() != null) ? cardColSrchCriteria
								.getRejectionReason() : "");
		parameterMap
				.put("RIC_SITE",
						(cardColSrchCriteria.getRicOffice() != null) ? cardColSrchCriteria
								.getRicOffice() : "");
		parameterMap
				.put("TO_DATE",
						(cardColSrchCriteria.getCardCollEndDt() != null) ? cardColSrchCriteria
								.getCardCollEndDt() : "");
		parameterMap
				.put("FROM_DATE",
						(cardColSrchCriteria.getCardCollStartDt() != null) ? cardColSrchCriteria
								.getCardCollStartDt() : "");
		List<CardStatusRptDTO> result = ricReportService
				.getCardRejectedRptRecordListForPDF(cardColSrchCriteria);
		System.out.println(" result >> >>" + result.size());
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(result);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_CARD_REJ_STATUS_RPT_ID,
							ricReportService.NIC_RIC_CARD_REJ_STATUS_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameterMap, reportService.getConnection());
			JasperReportsUtils.renderAsXls(jasperReport, parameterMap,
					JRdataSource, os);
			response.setContentType("application/vnd.ms-excel");
			response.getOutputStream().write(os.toByteArray());
			/*JRXlsExporter exporterXLS = new JRXlsExporter();
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

	@RequestMapping(value = "/showCardExpiredRpt")
	public String showCardExpiredRpt(WebRequest request, Model model) {
		model.addAttribute("fnSelected",
				"RIC_RPT_009: Card Expired Status Report ");
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
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("cardStatusRptDto", new CardStatusRptDTO());
		return "show.cardExpiredRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showCardExpiredTabluarResult")
	public PaginatedResult<CardStatusRptDTO> showCardExpiredTabluarResult(
			WebRequest request, Model model,
			CardStatusRptDTO cardExpiredSrchCriteria) throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println("page >>" + page);
		System.out.println("pageSize >>" + pageSize);
		PaginatedResult<CardStatusRptDTO> result = ricReportService
				.getCardExpiredStatusRptRecordList(cardExpiredSrchCriteria,
						page, pageSize);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showCardExpiredPdfResult")
	public void showCardExpiredPdfResult(WebRequest request, Model model,
			CardStatusRptDTO cardColSrchCriteria, HttpServletResponse response)
			throws Exception {
		//TODO
	}

	// priya start changes 26/9/2013

	@RequestMapping(value = "/showReActivateCard")
	public String showReActivateCard(WebRequest request, Model model) {
		model.addAttribute("fnSelected",
				"RIC_RPT_010: Card Re-Activation Report");
		Map<String, String> cardStatusMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_CARD_STATUS);
		model.addAttribute("cardStatusVal", cardStatusMap);
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
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("cardStatusRptDto", new CardStatusRptDTO());
		return "show.cardReActivateRpt";
	}

	@RequestMapping(value = "/showDeActivateCard")
	public String showDeActivateCard(WebRequest request, Model model) {
		model.addAttribute("fnSelected",
				"RIC_RPT_011: Card DeActivation Report");
		Map<String, String> cardStatusMap = codesService
				.getCodeValues(RegistrationConstants.CODE_ID_CARD_STATUS);
		model.addAttribute("cardStatusVal", cardStatusMap);
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
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("cardStatusRptDto", new CardStatusRptDTO());
		return "show.cardDeActivateRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showCardReActTabluarResult")
	public PaginatedResult<CardStatusRptDTO> showCardReActTabluarResult(
			WebRequest request, Model model,
			CardStatusRptDTO cardColSrchCriteria) throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println("page >>" + page);
		System.out.println("pageSize >>" + pageSize);
		PaginatedResult<CardStatusRptDTO> result = ricReportService
				.getCardReActRptRecordList(cardColSrchCriteria, page, pageSize);

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showCardReActPdfResult")
	public void showCardReActPdfResult(WebRequest request, Model model,
			CardStatusRptDTO cardReActSrchCriteria,
			HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap
				.put("regOfficerId",
						(!cardReActSrchCriteria.getRicOffice()
								.equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										cardReActSrchCriteria.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_010");
		parameterMap
				.put("startDate",
						(cardReActSrchCriteria.getCardCollEndDt() != null) ? cardReActSrchCriteria
								.getCardCollEndDt() : "");
		parameterMap
				.put("endDate",
						(cardReActSrchCriteria.getCardCollStartDt() != null) ? cardReActSrchCriteria
								.getCardCollStartDt() : "");
		PaginatedResult<CardStatusRptDTO> result = ricReportService
				.getCardReActRptRecordList(cardReActSrchCriteria, 1,
						Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_CRDREACT_RPT_ID,
							ricReportService.NIC_RIC_CRDREACT_RPT_NAME);
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
	@RequestMapping(value = "/showCardReActExcelResult")
	public void showCardReActExcelResult(WebRequest request, Model model,
			CardStatusRptDTO cardReActSrchCriteria,
			HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap
				.put("regOfficerId",
						(!cardReActSrchCriteria.getRicOffice()
								.equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										cardReActSrchCriteria.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_010");
		parameterMap
				.put("startDate",
						(cardReActSrchCriteria.getCardCollEndDt() != null) ? cardReActSrchCriteria
								.getCardCollEndDt() : "");
		parameterMap
				.put("endDate",
						(cardReActSrchCriteria.getCardCollStartDt() != null) ? cardReActSrchCriteria
								.getCardCollStartDt() : "");
		PaginatedResult<CardStatusRptDTO> result = ricReportService
				.getCardReActRptRecordList(cardReActSrchCriteria, 1,
						Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_CRDREACT_RPT_ID,
							ricReportService.NIC_RIC_CRDREACT_RPT_NAME);
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

	@ResponseBody
	@RequestMapping(value = "/showCardDeActTabluarResult")
	public PaginatedResult<CardStatusRptDTO> showCardDeActTabluarResult(
			WebRequest request, Model model,
			CardStatusRptDTO cardColSrchCriteria) throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println("page >>" + page);
		System.out.println("pageSize >>" + pageSize);
		PaginatedResult<CardStatusRptDTO> result = ricReportService
				.getCardDeActRptRecordList(cardColSrchCriteria, page, pageSize);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showCardDeActPdfResult")
	public void showCardDeActPdfResult(WebRequest request, Model model,
			CardStatusRptDTO cardDeActSrchCriteria,
			HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap
				.put("regOfficerId",
						(!cardDeActSrchCriteria.getRicOffice()
								.equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										cardDeActSrchCriteria.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_011");
		parameterMap
				.put("startDate",
						(cardDeActSrchCriteria.getCardCollEndDt() != null) ? cardDeActSrchCriteria
								.getCardCollEndDt() : "");
		parameterMap
				.put("endDate",
						(cardDeActSrchCriteria.getCardCollStartDt() != null) ? cardDeActSrchCriteria
								.getCardCollStartDt() : "");
		PaginatedResult<CardStatusRptDTO> result = ricReportService
				.getCardDeActRptRecordList(cardDeActSrchCriteria, 1,
						Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_CRDDEACT_RPT_ID,
							ricReportService.NIC_RIC_CRDDEACT_RPT_NAME);
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
	@RequestMapping(value = "/showCardDeActExcelResult")
	public void showCardDeActExcelResult(WebRequest request, Model model,
			CardStatusRptDTO cardDeActSrchCriteria,
			HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap
				.put("regOfficerId",
						(!cardDeActSrchCriteria.getRicOffice()
								.equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										cardDeActSrchCriteria.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("userID", userSession.getUserName());
		parameterMap.put("reportId", "RIC_RPT_011");
		parameterMap
				.put("startDate",
						(cardDeActSrchCriteria.getCardCollEndDt() != null) ? cardDeActSrchCriteria
								.getCardCollEndDt() : "");
		parameterMap
				.put("endDate",
						(cardDeActSrchCriteria.getCardCollStartDt() != null) ? cardDeActSrchCriteria
								.getCardCollStartDt() : "");
		PaginatedResult<CardStatusRptDTO> result = ricReportService
				.getCardDeActRptRecordList(cardDeActSrchCriteria, 1,
						Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_CRDREACT_RPT_ID,
							ricReportService.NIC_RIC_CRDREACT_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);			
			JasperReportsUtils.renderAsXls(jasperReport, parameterMap,
					JRdataSource, os);
			response.setContentType("application/vnd.ms-excel");
			response.getOutputStream().write(os.toByteArray());

		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	 
	@RequestMapping(value = "/showCardDeliveryRpt")
	public String showCardDeliveryRpt(WebRequest request, Model model) {
		model.addAttribute("fnSelected",
				"RIC_RPT_014: Card Delivery Status Report");
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
		String endDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		String startDate = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("cardDelvStusRptDto", new RICTxnRptDto());
		return "show.cardDelStRpt";
	}

	@ResponseBody
	@RequestMapping(value = "/showCardDelvStusTabluarResult")
	public PaginatedResult<RICTxnRptDto> showCardDelvStusTabluarResult(
			WebRequest request, Model model,
			RICTxnRptDto cardDelStatuSrchCriteria) throws Exception {
		System.out.println("In the Controller");
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getCardDeliveryStatusRptRecordList(cardDelStatuSrchCriteria,
						page, pageSize);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showCardDelvStusPdfResult")
	public void showCardDelvStusPdfResult(WebRequest request, Model model,
			RICTxnRptDto cardDelStatuSrchCriteria, HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap
				.put("RIC_SITE",
						(!cardDelStatuSrchCriteria.getRicOffice()
								.equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										cardDelStatuSrchCriteria.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("USER_ID", userSession.getUserName()); 
		parameterMap.put("Report_ID", "RIC_RPT_014");
		parameterMap
				.put("FROM_DATE",
						(cardDelStatuSrchCriteria.getEstColStartDt() != null) ? cardDelStatuSrchCriteria
								.getEstColStartDt() : "");
		parameterMap
				.put("TO_DATE",
						(cardDelStatuSrchCriteria.getEstColEndDt() != null) ? cardDelStatuSrchCriteria
								.getEstColEndDt() : "");
		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getCardDeliveryStatusRptRecordList(cardDelStatuSrchCriteria,
						1, Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream(); 
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_CRDDELVRY_RPT_ID,
							ricReportService.NIC_RIC_CRDDELVRY_RPT_NAME);
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
	@RequestMapping(value = "/showCardDelvStusExcelResult")
	public void showCardDelvStusExcelResult(WebRequest request, Model model,
			RICTxnRptDto cardDelStatuSrchCriteria, HttpServletResponse response, HttpServletRequest httpRequest)
			throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap
				.put("RIC_SITE",
						(!cardDelStatuSrchCriteria.getRicOffice()
								.equalsIgnoreCase("ALL")) ? codesService
								.getCodeValueByIdName(
										RegistrationConstants.CODE_ID_SITE_CODE,
										cardDelStatuSrchCriteria.getRicOffice())
								.getCodeValueDesc()
								: "");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		parameterMap.put("USER_ID", userSession.getUserName()); 
		parameterMap.put("Report_ID", "RIC_RPT_014");
		parameterMap
				.put("FROM_DATE",
						(cardDelStatuSrchCriteria.getEstColStartDt() != null) ? cardDelStatuSrchCriteria
								.getEstColStartDt() : "");
		parameterMap
				.put("TO_DATE",
						(cardDelStatuSrchCriteria.getEstColEndDt() != null) ? cardDelStatuSrchCriteria
								.getEstColEndDt() : "");
		PaginatedResult<RICTxnRptDto> result = ricReportService
				.getCardDeliveryStatusRptRecordList(cardDelStatuSrchCriteria,
						1, Integer.MAX_VALUE);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream(); 
		try {
			ReportTemplate template = reportService
					.getReportTemplateById(
							ricReportService.NIC_RIC_CRDDELVRY_RPT_ID,
							ricReportService.NIC_RIC_CRDDELVRY_RPT_NAME);
			InputStream reportStream = new ByteArrayInputStream(
					template.getTemplateImage());
			
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperReportsUtils.renderAsXls(jasperReport, parameterMap,
					JRdataSource, os);
			response.setContentType("application/vnd.ms-excel");
			response.getOutputStream().write(os.toByteArray());
			/*JasperReport jasperReport = JasperCompileManager
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
}