/**
 * 
 */
package com.nec.asia.nic.statistic.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.service.NicStatisticsService;
import com.nec.asia.nic.enquiry.controller.NicTransactionInfo;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.statistic.EppStatisticRptForm;
import com.nec.asia.nic.statistic.EppStatisticTransInfo;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * @author Tue
 *
 */

@Controller
@RequestMapping(value = "/trpStatistic")
public class EppStatisticController {
	
	private static final Logger logger = LoggerFactory.getLogger(EppStatisticController.class);
	
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	@Autowired
	private NicStatisticsService nicStatisticsService;
	
	@Autowired
	private CodesService codesService;
	
	@Autowired
	private SiteService siteService;
	
	private static final String TRP_REPORT_TMPL = "/report/templates/Detailed_Trp_Statistics.jrxml";
	public static final String MEDIA_TYPE_EXCEL = "application/vnd.ms-excel";
	public static final String MEDIA_TYPE_PDF = "application/pdf";
	public static final String MEDIA_TYPE_CSV = "text/csv";
	
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}
	
	@RequestMapping(value="/view")
	@ExceptionHandler
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap, @ModelAttribute("eppStatRptForm") EppStatisticRptForm eppStatRptForm) {
		logger.debug("Method View is starting...");
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		try {
			String functionName = "view";
			Object[] args = {eppStatRptForm};
			long timeMs = System.currentTimeMillis() - startTimeMs;
			auditAccessLogService.saveAuditAccessLogForWeb(request, functionName, args, throwable, timeMs);
		} catch (Exception exp) {
			logMessage(exp);
		}
		
		modelMap.addAttribute("fnSelected", "ePassport TRP and Statistics Report");
		return new ModelAndView("trpStatistic.init", "eppStatRptForm", new EppStatisticRptForm());
	}
	
	@RequestMapping(value="/search")
	@ExceptionHandler
	public ModelAndView getTransList(HttpServletRequest request, ModelMap modelMap, @ModelAttribute("eppStatRptForm") EppStatisticRptForm eppStatRptForm) {
		logger.debug("Method View is starting...");
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		 
		String fromEstDate = eppStatRptForm.getFromDate();
		int noOfDays = eppStatRptForm.getNoOfDays();
		boolean isCollectedType = eppStatRptForm.getCollectedType();
		boolean isPendingType = eppStatRptForm.getPendingType();
		
		List<EppStatisticTransInfo> transList = new ArrayList<EppStatisticTransInfo>();
		Map<String, Object> transRptMap = new HashMap<String, Object>();
		List<Object[]> objList = null;
		try {
			if(isCollectedType && isPendingType) {
				objList = nicStatisticsService.getAllTransByEstDate(fromEstDate, noOfDays);
			} else if(isCollectedType && !isPendingType) {
				objList = nicStatisticsService.getCollectedTransByEstDate(fromEstDate, noOfDays);
			} else if(!isCollectedType && isPendingType) {
				objList = nicStatisticsService.getPendingTransByEstDate(fromEstDate, noOfDays);
			}
			
			if(!CollectionUtils.isEmpty(objList)) {
				for(Object[] obj : objList) {
					EppStatisticTransInfo record = new EppStatisticTransInfo();
					Date estDate = (Date)obj[0];
					record.setStatDate( DateUtil.parseDate(estDate, "MM/dd/yyyy"));
					if(isCollectedType && isPendingType) {
						record.setPendingAfis((BigDecimal)obj[1]+"");
						record.setPendingInvestigation((BigDecimal)obj[2]+"");
						record.setPendingVerified((BigDecimal)obj[3]+"");
						record.setPendingPerso((BigDecimal)obj[4]+"");
						record.setPendingQc((BigDecimal)obj[5]+"");
						record.setPendingDispatch((BigDecimal)obj[6]+"");
						record.setPendingReceive((BigDecimal)obj[7]+"");
						record.setPendingIssue((BigDecimal)obj[8]+"");
						record.setNumPassportCollected((BigDecimal)obj[9]+"");
					} else if(isCollectedType && !isPendingType) {
				/*		record.setPendingAfis("n/a");
						record.setPendingInvestigation("n/a");
						record.setPendingVerified("n/a");
						record.setPendingPerso("n/a");
						record.setPendingQc("n/a");
						record.setPendingDispatch("n/a");
						record.setPendingReceive("n/a");
						record.setPendingIssue("n/a");*/
						record.setNumPassportCollected((BigDecimal)obj[1]+"");
					} else if(!isCollectedType && isPendingType) {
						record.setPendingAfis((BigDecimal)obj[1]+"");
						record.setPendingInvestigation((BigDecimal)obj[2]+"");
						record.setPendingVerified((BigDecimal)obj[3]+"");
						record.setPendingPerso((BigDecimal)obj[4]+"");
						record.setPendingQc((BigDecimal)obj[5]+"");
						record.setPendingDispatch((BigDecimal)obj[6]+"");
						record.setPendingReceive((BigDecimal)obj[7]+"");
						record.setPendingIssue((BigDecimal)obj[8]+"");
//						record.setNumPassportCollected("n/a");
					}
					
					transList.add(record);
				}
			}
			
			transRptMap.put("transList", transList);
			transRptMap.put("totalRecords", transList.size());
			transRptMap.put("pageSize", pageSize);
		} catch (Exception ex) {
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "search";
				Object[] args = null;
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName, args, throwable, timeMs);
			} catch (Exception exp) {
				logMessage(exp);
			}
		}
		ModelAndView modelView = new ModelAndView("trpStatistic.init", transRptMap);
		modelMap.addAttribute("fnSelected", "ePassport TRP and Statistics Report");
		return modelView;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/transDetail/{filterParams}")
	@ExceptionHandler
	public PaginatedResult<NicTransactionInfo> getTransDetails(@PathVariable String filterParams, HttpServletRequest request) {
		logger.info("Entry at getTransDetails request ");
		
		
		List<NicTransactionInfo> transInfoList = new ArrayList<NicTransactionInfo>();
		PaginatedResult<NicTransaction> transList = null;
		PaginatedResult<NicTransactionInfo> transPagingList = null;
		String estDate = null;
		String transType = null;
		String[] status = null;
		try {
			PageRequest pageRequest = this.generateReqPage(request);
			if(StringUtils.isNotBlank(filterParams)) {
				if(filterParams.startsWith("PEN")) {
					transType = "PEN";
				} else {
					transType = "TOT";
				}
				
				String[] arrParam = filterParams.split("_");
				if(arrParam.length >= 3) {
					transType = StringUtils.isNotEmpty(transType)?transType : arrParam[0];
					estDate = arrParam[1].replace("-", "/");
					if(arrParam[2].contains(":")) {
						status = arrParam[2].split(":");
					} else {
						status = new String[] {arrParam[2]};
					}
				}
				
				transList = nicStatisticsService.findAllByStatusAndEstDate(status, estDate, transType, pageRequest);
				if(transList != null) {
					for(NicTransaction transaction : transList.getRows()) {
						NicTransactionInfo transInfo = this.getTransactionInfo(transaction);
						transInfoList.add(transInfo);
					}
					transPagingList = new PaginatedResult<NicTransactionInfo>(transList.getTotal(), transList.getPage(), transInfoList);
				}
			}
			
		} catch (Exception ex) {
			logMessage(ex);
		}
		
		return transPagingList;
	}
	
	private PageRequest generateReqPage(HttpServletRequest request) {
		 String pages = request.getParameter("page");
        String sortname = request.getParameter("sortname");
        String sortorder = request.getParameter("sortorder");
        String rp = request.getParameter("rp");

        int pageSize = Constants.PAGE_SIZE_DEFAULT;
        if ( StringUtils.isNotEmpty(rp) && !"NaN".equals(rp)) {
            pageSize = Integer.parseInt(rp);
        }

        int startIndex = 0;
        String pageNumber = pages;
        if (StringUtils.isNotBlank(pageNumber)) {
            startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
        }
        
        PageRequest pageRequest = new PageRequest();
        pageRequest.setCalculateRecordCount(true);
        pageRequest.setFirstRowIndex(startIndex);
        pageRequest.setMaxRecords(pageSize);
        pageRequest.setSortname(sortname);
        pageRequest.setSortorder(sortorder);
        pageRequest.setPageNo(Integer.parseInt(pageNumber));
		
		return pageRequest;
	}
	
	private NicTransactionInfo getTransactionInfo(NicTransaction nicTransaction) throws Exception {
		NicTransactionInfo nicTransactionInfo = null;
		try {
				nicTransactionInfo = new NicTransactionInfo();

				PropertyUtils.copyProperties(nicTransactionInfo, nicTransaction);

				if (nicTransaction.getDateOfApplication() != null) {
					nicTransactionInfo.setDatTimeOfApplication(DateUtil.parseDate(nicTransaction.getDateOfApplication(),DateUtil.FORMAT_MM_DD_YYYY_HH_MM_SS));
				}

				if (nicTransaction.getEstDateOfCollection() != null) {
					nicTransactionInfo.setEstCollectionDate(DateUtil.parseDate(nicTransaction.getEstDateOfCollection(),DateUtil.FORMAT_MM_DD_YYYY));
				}

				if (nicTransaction.getCreateDatetime() != null) {
					nicTransactionInfo.setCreateDateTime(DateUtil.parseDate(nicTransaction.getCreateDatetime(), "dd-MMM-yyyy hh:mm:ss"));
				}

				if (nicTransaction.getUpdateDatetime() != null) {
					nicTransactionInfo.setUpdateDateTime(DateUtil.parseDate(nicTransaction.getUpdateDatetime(), "dd-MMM-yyyy hh:mm:ss"));
				}

				if (nicTransaction.getPrevDateOfIssue() != null) {
					nicTransactionInfo.setPrevIssueDate(DateUtil.parseDate(nicTransaction.getPrevDateOfIssue(), "dd-MMM-yyyy"));
				}

				String regSiteCode = null;
				if (StringUtils.isNotBlank(nicTransactionInfo.getRegSiteCode())) {
					SiteRepository regSite = siteService.getSiteRepoById(nicTransactionInfo.getRegSiteCode());
					if (regSite != null) {
						regSiteCode = regSite.getSiteName();
					} else {
						regSiteCode = nicTransactionInfo.getRegSiteCode();
					}
				}

				nicTransactionInfo.setRegSiteCode(regSiteCode);

				String issSiteCode = null;
				if (StringUtils.isNotBlank(nicTransactionInfo.getIssSiteCode())) {
					SiteRepository regSite = siteService.getSiteRepoById(nicTransactionInfo.getIssSiteCode());
					if (regSite != null) {
						issSiteCode = regSite.getSiteName();
					} else {
						issSiteCode = nicTransactionInfo.getRegSiteCode();
					}
				}

				nicTransactionInfo.setIssSiteCode(issSiteCode);

				String transactionStatus = null;
				if (nicTransactionInfo != null && StringUtils.isNotBlank(nicTransactionInfo.getTransactionStatus())) {
					CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_STATUS", nicTransactionInfo.getTransactionStatus());

					if (codeValue != null) {
						transactionStatus = codeValue.getCodeValueDesc();
					} else {
						transactionStatus = nicTransactionInfo.getTransactionStatus();
					}
				}

				nicTransactionInfo.setTransactionStatus(transactionStatus);

				String transactionSubType = null;
				if (nicTransactionInfo != null && StringUtils.isNotBlank(nicTransactionInfo.getTransactionSubtype())) {
					CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_SUBTYPE", nicTransactionInfo.getTransactionSubtype());

					if (codeValue != null) {
						transactionSubType = codeValue.getCodeValueDesc();
					} else {
						transactionSubType = nicTransactionInfo.getTransactionSubtype();
					}
				}

				nicTransactionInfo.setTransactionSubtype(transactionSubType);

				String transactionType = null;
				if (nicTransactionInfo != null && StringUtils.isNotBlank(nicTransactionInfo.getTransactionType())) {
					CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", nicTransactionInfo.getTransactionType());

					transactionType = nicTransactionInfo.getTransactionType();
					if (codeValue != null) {
						transactionType = codeValue.getCodeValueDesc();
					} else {
						codeValue = codesService.getCodeValueByIdName("JOB_TYPE", nicTransactionInfo.getTransactionType());
						if (codeValue != null) {
							transactionType = codeValue.getCodeValueDesc();
						}
					}
				}

				nicTransactionInfo.setTransactionType(transactionType);

				String passportType = null;
				if (nicTransactionInfo != null && StringUtils.isNotBlank(nicTransactionInfo.getPassportType())) {
					CodeValues codeValue = codesService.getCodeValueByIdName("PASSPORT_TYPE", nicTransactionInfo.getPassportType());

					if (codeValue != null) {
						passportType = codeValue.getCodeValueDesc();
					} else {
						passportType = nicTransactionInfo.getPassportType();
					}
				}

				nicTransactionInfo.setPassportType(passportType);

				// Get name
				NicRegistrationData registrationData = nicTransaction.getNicRegistrationData();
				if (registrationData != null) {
					if (StringUtils.isNotBlank(registrationData.getSurnameFull())) {
						nicTransactionInfo.setLastName(registrationData.getSurnameFull());
					}
					
					if (StringUtils.isNotBlank(registrationData.getFirstnameFull())) {
						nicTransactionInfo.setFirstName(registrationData.getFirstnameFull());
					}
					
					if (StringUtils.isNotBlank(registrationData.getMiddlenameFull())) {
						nicTransactionInfo.setMiddleName(registrationData.getMiddlenameFull());
					}
				}
		} catch (Exception ex) {
			logger.info("Error occurred while getting the job enquiry transaction info for Transaction:" + nicTransaction.getTransactionId() + ", Reason: " + ex.getMessage());
		}

		return nicTransactionInfo;
	}
	
	@RequestMapping(value = "/exportPdf/{filterParams}")
	@ResponseBody
	public void exportPdf(@PathVariable String filterParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<NicTransactionInfo> transInfoList = filterTransByStatusAndEstDate(filterParams);
			Map<String, Object> parameterMap = buildHeaderRpt(filterParams, request);
			String fileName = "Detailed of "+ parameterMap.get("statisticsType") +" Statistic_"+parameterMap.get("collectedDate");
			
			JRDataSource JRdataSource = new JRBeanCollectionDataSource(transInfoList);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			InputStream reportStream = this.getClass().getResourceAsStream(TRP_REPORT_TMPL);
			JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
			JasperReportsUtils.renderAsPdf(jasperReport, parameterMap, JRdataSource, os);
			byte[] bytes = os.toByteArray();

			if (bytes != null && bytes.length > 0) {
				response.setContentType(MEDIA_TYPE_PDF);
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".pdf\"");
				response.setContentLength(bytes.length);

				ServletOutputStream ouputStream = null;
				try {
					ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
				} catch (IOException e) {
					response.getWriter().write("-1");
				} finally {
					if (ouputStream != null) {
						try {
							ouputStream.close();
						} catch (IOException e) {
							response.getWriter().write("-1");
						}
					}
				}
			}

		} catch (Exception exp) {
			exp.printStackTrace();
			response.getWriter().write("-1");
		}
			
	}
	
	@RequestMapping(value = "/exportCsv/{filterParams}")
	@ResponseBody
	public void exportCsv(@PathVariable String filterParams, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		try {
			List<NicTransactionInfo> transInfoList = filterTransByStatusAndEstDate(filterParams);
			Map<String, Object> parameterMap = buildHeaderRpt(filterParams, request);
			String fileName = "Detailed of "+ parameterMap.get("statisticsType") +" Statistic_"+parameterMap.get("collectedDate");
			
			JRDataSource JRdataSource = new JRBeanCollectionDataSource(transInfoList);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			InputStream reportStream = this.getClass().getResourceAsStream(TRP_REPORT_TMPL);
			JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
			Writer writer = new OutputStreamWriter(os);
			
			JasperReportsUtils.renderAsCsv(jasperReport, parameterMap, JRdataSource, writer);
			byte[] bytes = os.toByteArray();

			if (bytes != null && bytes.length > 0) {
				response.setContentType(MEDIA_TYPE_CSV);
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".csv\"");
				response.setContentLength(bytes.length);

				ServletOutputStream ouputStream = null;
				try {
					ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
				} catch (IOException e) {
					response.getWriter().write("-1");
				} finally {
					if (ouputStream != null) {
						try {
							ouputStream.close();
						} catch (IOException e) {
							response.getWriter().write("-1");
						}
					}
				}
			}

		} catch (Exception exp) {
			exp.printStackTrace();
			response.getWriter().write("-1");
		}
			
	}
	
	@RequestMapping(value = "/exportExcel/{filterParams}")
	@ResponseBody
	public void exportExcel(@PathVariable String filterParams, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		try {
			
			List<NicTransactionInfo> transInfoList = filterTransByStatusAndEstDate(filterParams);
			Map<String, Object> parameterMap = buildHeaderRpt(filterParams, request);
			String fileName = "Detailed of "+ parameterMap.get("statisticsType") +" Statistic_"+parameterMap.get("collectedDate");
			
			JRDataSource JRdataSource = new JRBeanCollectionDataSource(transInfoList);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			InputStream reportStream = this.getClass().getResourceAsStream(TRP_REPORT_TMPL);
			JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
			
			Map<JRExporterParameter, Object> exportParameterMap = new HashMap<JRExporterParameter, Object>();
			exportParameterMap.put(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exportParameterMap.put(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exportParameterMap.put(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
			
			JasperReportsUtils.renderAsXls(jasperReport, parameterMap, JRdataSource, os, exportParameterMap);
			byte[] bytes = os.toByteArray();

			if (bytes != null && bytes.length > 0) {
				response.setContentType(MEDIA_TYPE_EXCEL);
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xls\"");
				response.setContentLength(bytes.length);

				ServletOutputStream ouputStream = null;
				try {
					ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
				} catch (IOException e) {
					response.getWriter().write("-1");
				} finally {
					if (ouputStream != null) {
						try {
							ouputStream.close();
						} catch (IOException e) {
							response.getWriter().write("-1");
						}
					}
				}
			}

		} catch (Exception exp) {
			exp.printStackTrace();
			response.getWriter().write("-1");
		}
			
	}
	
	private List<NicTransactionInfo> filterTransByStatusAndEstDate(String filterParams) {
		List<NicTransactionInfo> transInfoList = new ArrayList<NicTransactionInfo>();
		List<NicTransaction> transList = null;
		String estDate = null;
		String transType = null;
		String[] status = null;
		try {
			if (StringUtils.isNotBlank(filterParams)) {
				if (filterParams.startsWith("PEN")) {
					transType = "PEN";
				} else {
					transType = "TOT";
				}

				String[] arrParam = filterParams.split("_");
				if (arrParam.length >= 3) {
					transType = StringUtils.isNotEmpty(transType) ? transType : arrParam[0];
					estDate = arrParam[1].replace("-", "/");
					if (arrParam[2].contains(":")) {
						status = arrParam[2].split(":");
					} else {
						status = new String[] { arrParam[2] };
					}
				}

				transList = nicStatisticsService.getAllByStatusAndEstDate(status, estDate, transType);
				if (transList != null) {
					for (NicTransaction transaction : transList) {
						NicTransactionInfo transInfo = this.getTransactionInfo(transaction);
						transInfoList.add(transInfo);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transInfoList;
	}
	
	private Map<String, Object> buildHeaderRpt(String filterParams, HttpServletRequest request) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		String collectedDate = "";
		String statisticType = "";
		String transType = "";
		String[] status = null;
		if (StringUtils.isNotBlank(filterParams)) {
			if (filterParams.startsWith("PEN")) {
				transType = "PEN";
			} else {
				transType = "TOT";
				statisticType = "Passport Collected/Issued";
			}

			String[] arrParam = filterParams.split("_");
			if (arrParam.length >= 3) {
				transType = StringUtils.isNotEmpty(transType) ? transType : arrParam[0];
				collectedDate = arrParam[1].replace("-", "/");
				if (arrParam[2].contains(":")) {
					status = arrParam[2].split(":");
				} else {
					status = new String[] { arrParam[2] };
				}
			}
			List<String> statusList = Arrays.asList(status);
			if(statusList.contains("WA")) {
				statisticType ="Pending AFIS";
			} else if (statusList.contains("FG")) {
				statisticType ="Pending Investigation";
			} else if (statusList.contains("VE")) {
				statisticType ="Pending Verified";
			} else if (statusList.contains("RP") || statusList.contains("WC")) {
				statisticType ="Pending Perso";
			} else if (statusList.contains("QC")) {
				statisticType ="Pending QC";
			} else if (statusList.contains("ST")) {
				statisticType ="Pending Dispatch";
			} else if (statusList.contains("RD") && transType.equals("PEN")) {
				statisticType ="Pending Receive";
			} else if (statusList.contains("OK")) {
				statisticType ="Pending Issue";
			}
			
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			
			parameterMap.put("userID", userSession.getUserName());
			parameterMap.put("collectedDate", collectedDate);
			parameterMap.put("statisticsType",statisticType);
		}
		return parameterMap;
	}

}
