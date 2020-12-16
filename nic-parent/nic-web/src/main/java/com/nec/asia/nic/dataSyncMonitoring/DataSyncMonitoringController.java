/**
 * 
 */
package com.nec.asia.nic.dataSyncMonitoring;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.comp.report.service.AfisDataSyncService;
import com.nec.asia.nic.comp.trans.dto.NicEnquiryForm;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;

/**
 * @author Peddi Swapna
 * @Company: NEC Asia Pacific Ltd
 * @Since: Nov 25, 2013
 */

/* 
 * Modification History:
 * 
 * 20 Aug 2014 (chris): change "TRANSACTION_TYPE" to "JOB_TYPE" for CODE_ID. 
 */

@Controller
@RequestMapping(value = "/dataSyncMonitorController")
public class DataSyncMonitoringController extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(DataSyncMonitoringController.class);
	
	@Autowired
	private AfisDataSyncService afisDataSyncService;
	
	@Autowired
	//@Qualifier(value="codesService")
	private CodesService codesService;
	
	@Autowired
	private NicUploadJobService uploadJobService;
	
/*	@Value("#{appProperties.report_dataSyncMoni}")
	public String rptId;
*/	
	@RequestMapping(value="/init")
	public ModelAndView init(HttpServletRequest request, ModelMap model,  @ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm) {
		try {

			NicEnquiryForm nicEnqForm = new NicEnquiryForm();
			String selectedMonth = dataSyncMonitoringForm.getSelectedMonth();
			if(!StringUtils.isNotBlank(selectedMonth)){
			 selectedMonth =DateUtil.parseDate(new Date(),"MMMM-yyyy");
			}
			
			nicEnqForm.setSelectedMonth(selectedMonth);
			
			model.addAttribute("fnSelected", Constants.HEADING_DATA_SYNC_MONITORING);
		    model.addAttribute("dataSyncMonitoringForm", nicEnqForm);
		    

			return new ModelAndView("dataSyncMonitor-init", null);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
		

	}
	
	@ResponseBody
	@RequestMapping(value="/search" , method = RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView search(HttpServletRequest request,  @ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm, Model model,ModelMap modelMap,HttpServletResponse response) throws Exception{ 
		try{
			String selectedMonth =  dataSyncMonitoringForm.getSelectedMonth();
			
			Date selDate = DateUtil.strToDate(selectedMonth, "MMMM-yyyy");
			String selectedMon = DateUtil.parseDate(selDate, "MM-yyyy");
			List<DataSyncMainInfo> syncInfoList = new ArrayList<DataSyncMainInfo>();
			
			List<Object[]> resultList = afisDataSyncService.getSynchronizionDetails(selectedMon);
			
			if(CollectionUtils.isNotEmpty(resultList)){
				for(Object[] rowVals :resultList){
					DataSyncMainInfo info = new DataSyncMainInfo();
					BigDecimal toBeSyncronized = new BigDecimal("0");
					
					Date appDate = DateUtil.strToDate((String)rowVals[0], "dd-MM-yyyy");
					
					info.setApplicateDate(DateUtil.parseDate(appDate, DateUtil.FORMAT_DDdashMMMdashYYYY));
					info.setTotTrans((BigDecimal)rowVals[1]);
					info.setSyncronizedTrans((BigDecimal)rowVals[2]);
					info.setNotRequired((BigDecimal)rowVals[3]);
					
					BigDecimal totTransSync = ((BigDecimal)rowVals[2]).add((BigDecimal)rowVals[3]);
					if(((BigDecimal)rowVals[1]).compareTo(totTransSync)==0){
						info.setStatus("Y");
					}else{
						info.setStatus("N");
					}
					toBeSyncronized = (((BigDecimal)rowVals[1]).subtract(totTransSync));
					info.setToBeSyncronized(toBeSyncronized);
							
					syncInfoList.add(info);
				}
			}
			Map searchResultMap = new HashMap();
			searchResultMap.put("list", syncInfoList);
			return new ModelAndView("dataSyncMonitor-sr",searchResultMap);
		}catch(Exception ex){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("errorWithoutMenu");
			modelAndView.addObject("exception", ex);
			modelAndView.addObject("message", ex.getLocalizedMessage());
			modelAndView.addObject("messageStack", ex.getMessage());
			return modelAndView;
		}
			
	}
	
	
	@RequestMapping(value = "/generatePDF")
	public void generatePDF(@ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm,HttpServletRequest httpRequest, String type,HttpServletResponse response,WebRequest request1,HttpServletRequest request ) throws Exception {
		try{	
				String reportId = "DATA_SYNC_MONITORING";
				String selectedMonth =  dataSyncMonitoringForm.getSelectedMonth();
				
				Date selMon = DateUtil.strToDate(selectedMonth, "MMMM-yyyy");
				String selectedMon = DateUtil.parseDate(selMon, "MM-yyyy");
				
				String fileName = "NIC and AFIS Data Synchronization Monitoring".concat("_").concat(selectedMonth);
				
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("appDate", selectedMonth);
				parameterMap.put( "reportId",  reportId); 
				
				List<DataSyncMainInfo> syncInfoList = new ArrayList<DataSyncMainInfo>();
				
				List<Object[]> resultList = afisDataSyncService.getSynchronizionDetails(selectedMon);
				
				if(CollectionUtils.isNotEmpty(resultList)){
					for(Object[] rowVals :resultList){
						DataSyncMainInfo info = new DataSyncMainInfo();
						BigDecimal toBeSyncronized = new BigDecimal("0");
						Date appDate = DateUtil.strToDate((String)rowVals[0], "dd-MM-yyyy");
						
						info.setApplicateDate(DateUtil.parseDate(appDate, DateUtil.FORMAT_DDdashMMMdashYYYY));
						info.setTotTrans((BigDecimal)rowVals[1]);
						info.setSyncronizedTrans((BigDecimal)rowVals[2]);
						info.setNotRequired((BigDecimal)rowVals[3]);
						
						BigDecimal totTransSync = ((BigDecimal)rowVals[2]).add((BigDecimal)rowVals[3]);
						if(((BigDecimal)rowVals[1]).compareTo(totTransSync)==0){
							info.setStatus("Y");
						}else{
							info.setStatus("N");
						}
						toBeSyncronized = (((BigDecimal)rowVals[1]).subtract(totTransSync));
						info.setToBeSyncronized(toBeSyncronized);
						
						syncInfoList.add(info);
					}
				}
				
				JRDataSource JRdataSource = new JRBeanCollectionDataSource(
						syncInfoList);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
					InputStream reportStream = this.getClass().getResourceAsStream(
							"/report/templates/Data_Sync_Monitoring.jrxml");
					JasperReport jasperReport = JasperCompileManager
							.compileReport(reportStream);
					JasperReportsUtils.renderAsPdf(jasperReport, parameterMap,
							JRdataSource, os);
					byte[] bytes = os.toByteArray();
					
					if (bytes != null && bytes.length > 0) {
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition", "attachment; filename=\""
								+ fileName + ".pdf\"");
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
			
			}catch(Exception exp){
				exp.printStackTrace();
				response.getWriter().write("-1");
			}
			
	}
	
	
	@RequestMapping(value="/getDataSyncDetails")
	public ModelAndView searchViewDetails(HttpServletRequest request,HttpServletRequest httpServletRequest,  @ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm, Model model,ModelMap modelMap) throws Exception{ 
//		String selectedDate =  dataSyncMonitoringForm.getSelectedDate();
		String viewFPFalg = "Y";
		
		UserSession userSession = (UserSession) httpServletRequest.getSession().getAttribute("userSession");
		
		if(!userSession.getFunctions().contains("VIEW_FP")){
			viewFPFalg ="N";
		}
		
		
//		Date selDate = DateUtil.strToDate(selectedDate, "dd-MM-yyyy");
//		dataSyncMonitoringForm.setSelectedDate(DateUtil.parseDate(selDate,DateUtil.FORMAT_DDdashMMMdashYYYY ));
		ModelAndView mav = new ModelAndView("dataSyncMonitor-details-init");
		mav.addObject("viewFPFalg", viewFPFalg);
		
		return mav;
	}
	
	@RequestMapping(value="/getDataSyncDetailsByMonth")
	public ModelAndView getDataSyncDetailsByMonth(HttpServletRequest request,HttpServletRequest httpServletRequest,  @ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm, Model model,ModelMap modelMap) throws Exception{ 
//		String selectedDate =  dataSyncMonitoringForm.getSelectedDate();
		String viewFPFalg = "Y";
		
		UserSession userSession = (UserSession) httpServletRequest.getSession().getAttribute("userSession");
		
		if(!userSession.getFunctions().contains("VIEW_FP")){
			viewFPFalg ="N";
		}
		
		
//		Date selDate = DateUtil.strToDate(selectedDate, "dd-MM-yyyy");
//		dataSyncMonitoringForm.setSelectedDate(DateUtil.parseDate(selDate,DateUtil.FORMAT_DDdashMMMdashYYYY ));
		ModelAndView mav = new ModelAndView("dataSyncMonitor-monthDetails-init");
		mav.addObject("viewFPFalg", viewFPFalg);
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/searchViewDetailsForMonth")
	@ExceptionHandler
	public ModelAndView dataSyncDetailsForMon(HttpServletRequest request,  @ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm, Model model,ModelMap modelMap) throws Exception{ 
		try{
			String selectedMonth =  dataSyncMonitoringForm.getSelectedMonth();
			
			Date selDate = DateUtil.strToDate(selectedMonth, "MMMM-yyyy");
			
			selectedMonth = DateUtil.parseDate(selDate, "MM-yyyy");
			
			List<DataSyncInfo> syncInfoList = new ArrayList<DataSyncInfo>();
			
			List<Object[]> resultList = afisDataSyncService.getSynchronizionDetailsByMonth(selectedMonth);
			
			if(CollectionUtils.isNotEmpty(resultList)){
				for(Object[] rowVals :resultList){
					DataSyncInfo info = new DataSyncInfo();
					info.setTransId((String)rowVals[0]);
					info.setNin((String)rowVals[1]);
//					info.setJobType((String)rowVals[2]);
					//CodeValues jobTypeCodeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", (String)rowVals[2]);
					CodeValues jobTypeCodeValue = codesService.getCodeValueByIdName("JOB_TYPE", (String)rowVals[2]); //2014 Aug 20
					
					if(jobTypeCodeValue!=null){
						info.setJobType(jobTypeCodeValue.getCodeValueDesc());
					}else{
						info.setJobType((String)rowVals[2]);
					}
					
//					info.setSiteName((String)rowVals[3]);
					
					CodeValues siteCodeValue = codesService.getCodeValueByIdName("SITE_CODE", (String)rowVals[3]);
					if (siteCodeValue==null) {
						siteCodeValue = codesService.getCodeValueByIdName("REPORT_SITE_CODE", (String)rowVals[3]); //2014 Aug 20
					}
					
					if(siteCodeValue!=null){
						info.setSiteName(siteCodeValue.getCodeValueDesc());
					}else{
						info.setSiteName((String)rowVals[3]);
					}
					
					if(rowVals[4]!=null){
						info.setRetryTimes((BigDecimal)rowVals[4]);
					}
					if(rowVals[5]!=null){
						info.setLatestStatusCode((BigDecimal)rowVals[5]);
						
						CodeValues statusCodeValue = codesService.getCodeValueByIdName("DATA_SYNC_STATUS", ((BigDecimal)rowVals[5]).toString());
						
						if(statusCodeValue!=null){
							info.setLatestStatus(statusCodeValue.getCodeValueDesc());
						}else{
							info.setLatestStatus(((BigDecimal)rowVals[5]).toString());
						}
						
						
					}
					if(rowVals[6]!=null){
						Clob clobData = (Clob)rowVals[6];
						
						StringBuffer strOut = new StringBuffer();
						   String errorDesc;

						BufferedReader br = new BufferedReader(clobData.getCharacterStream());
						while ((errorDesc = br.readLine()) != null) {
							strOut.append(errorDesc);
							strOut.append(System.getProperty("line.separator"));
						}	    
						info.setErrorDesc(strOut.toString());	    
								    
					}
					
					if(rowVals[7]!=null){
						info.setDataSyncId((BigDecimal)rowVals[7]);
					}
					
					if(rowVals[8]!=null){
						
						CodeValues transStatusCodeValue = codesService.getCodeValueByIdName("TRANSACTION_STATUS", ((String)rowVals[8]).toString());
						
						if(transStatusCodeValue!=null){
							info.setTransactionStatus(transStatusCodeValue.getCodeValueDesc());
						}else{
							info.setTransactionStatus(((String)rowVals[8]).toString());
						}
					}
					
					syncInfoList.add(info);
				}
			}
			Map<String, List<DataSyncInfo>> searchResultMap = new HashMap<String, List<DataSyncInfo>>();
			searchResultMap.put("list", syncInfoList);
			return new ModelAndView("dataSyncMonitor-month-details-sr", searchResultMap);
		}catch(Exception ex){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("errorWithoutMenu");
			modelAndView.addObject("exception", ex);
			modelAndView.addObject("message", ex.getLocalizedMessage());
			modelAndView.addObject("messageStack", ex.getMessage());
			return modelAndView;
		}
			
	}

	
	@ResponseBody
	@RequestMapping(value="/searchViewDetails")
	@ExceptionHandler
	public ModelAndView getDataSyncDetails(HttpServletRequest request,  @ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm, Model model,ModelMap modelMap) throws Exception{ 
		try{
			String selectedDate =  dataSyncMonitoringForm.getSelectedDate();
			
			Date selDate = DateUtil.strToDate(selectedDate, "dd-MMM-yyyy");
			
			selectedDate = DateUtil.parseDate(selDate, "dd-MM-yyyy");
			
			List<DataSyncInfo> syncInfoList = new ArrayList<DataSyncInfo>();
			
			List<Object[]> resultList = afisDataSyncService.getSynchronizionDetailsByDate(selectedDate);
			
			if(CollectionUtils.isNotEmpty(resultList)){
				for(Object[] rowVals :resultList){
					DataSyncInfo info = new DataSyncInfo();
					info.setTransId((String)rowVals[0]);
					info.setNin((String)rowVals[1]);
//					info.setJobType((String)rowVals[2]);
					//CodeValues jobTypeCodeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", (String)rowVals[2]);
					CodeValues jobTypeCodeValue = codesService.getCodeValueByIdName("JOB_TYPE", (String)rowVals[2]); //2014 Aug 20
					
					if(jobTypeCodeValue!=null){
						info.setJobType(jobTypeCodeValue.getCodeValueDesc());
					}else{
						info.setJobType((String)rowVals[2]);
					}
					
//					info.setSiteName((String)rowVals[3]);
					
					CodeValues siteCodeValue = codesService.getCodeValueByIdName("SITE_CODE", (String)rowVals[3]);
					if (siteCodeValue==null) {
						siteCodeValue = codesService.getCodeValueByIdName("REPORT_SITE_CODE", (String)rowVals[3]);//2014 Aug 20
					}
					
					if(siteCodeValue!=null){
						info.setSiteName(siteCodeValue.getCodeValueDesc());
					}else{
						info.setSiteName((String)rowVals[3]);
					}
					
					if(rowVals[4]!=null){
						info.setRetryTimes((BigDecimal)rowVals[4]);
					}
					if(rowVals[5]!=null){
						info.setLatestStatusCode((BigDecimal)rowVals[5]);
						
						CodeValues statusCodeValue = codesService.getCodeValueByIdName("DATA_SYNC_STATUS", ((BigDecimal)rowVals[5]).toString());
						
						if(statusCodeValue!=null){
							info.setLatestStatus(statusCodeValue.getCodeValueDesc());
						}else{
							info.setLatestStatus(((BigDecimal)rowVals[5]).toString());
						}
						
						
					}
					if(rowVals[6]!=null){
						Clob clobData = (Clob)rowVals[6];
						
						StringBuffer strOut = new StringBuffer();
						   String errorDesc;

						BufferedReader br = new BufferedReader(
								clobData.getCharacterStream());
						while ((errorDesc = br.readLine()) != null) {
							strOut.append(errorDesc);
							strOut.append(System.getProperty("line.separator"));
						}	    
						info.setErrorDesc(strOut.toString());	    
								    
					}
					
					if(rowVals[7]!=null){
						info.setDataSyncId((BigDecimal)rowVals[7]);
					}
					
					if(rowVals[8]!=null){
						
						CodeValues transStatusCodeValue = codesService.getCodeValueByIdName("TRANSACTION_STATUS", ((String)rowVals[8]).toString());
						
						if(transStatusCodeValue!=null){
							info.setTransactionStatus(transStatusCodeValue.getCodeValueDesc());
						}else{
							info.setTransactionStatus(((String)rowVals[8]).toString());
						}
						
						
					}
					
					syncInfoList.add(info);
				}
			}
			Map searchResultMap = new HashMap();
			searchResultMap.put("list", syncInfoList);
			return new ModelAndView("dataSyncMonitor-details-sr",searchResultMap);
		}catch(Exception ex){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("errorWithoutMenu");
			modelAndView.addObject("exception", ex);
			modelAndView.addObject("message", ex.getLocalizedMessage());
			modelAndView.addObject("messageStack", ex.getMessage());
			return modelAndView;
		}
			
	}
	
	
	@RequestMapping(value = "/generateViewDetailsPDFForMonth")
	public void generateViewDetailsPDFForMonth(@ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm,HttpServletRequest httpRequest, String type,HttpServletResponse response,WebRequest request1,HttpServletRequest request ) throws Exception {
		try{	
			String selectedMonth =  dataSyncMonitoringForm.getSelectedMonth();
			
			Date selDate = DateUtil.strToDate(selectedMonth, "MMMM-yyyy");
			String fileName = "Detailed NIC and AFIS Data Synchronization Monitoring".concat("_").concat(selectedMonth);
			
			selectedMonth = DateUtil.parseDate(selDate, "MM-yyyy");
			
			Map<String, Object> parameterMap = new HashMap<String, Object>();

			parameterMap.put("appDate", dataSyncMonitoringForm.getSelectedMonth());
			parameterMap.put("reportId", "DETAILED_DATA_SYNC_MONITORING");
			
			List<DataSyncInfo> syncInfoList = new ArrayList<DataSyncInfo>();
			
			List<Object[]> resultList = afisDataSyncService.getSynchronizionDetailsByMonth(selectedMonth);
			
			if(CollectionUtils.isNotEmpty(resultList)){
				for(Object[] rowVals :resultList){
					DataSyncInfo info = new DataSyncInfo();
					info.setTransId((String)rowVals[0]);
					info.setNin((String)rowVals[1]);
//					info.setJobType((String)rowVals[2]);
					//CodeValue jobTypeCodeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", (String)rowVals[2]);
					CodeValues jobTypeCodeValue = codesService.getCodeValueByIdName("JOB_TYPE", (String)rowVals[2]); //2014 Aug 20
					
					if(jobTypeCodeValue!=null){
						info.setJobType(jobTypeCodeValue.getCodeValueDesc());
					}else{
						info.setJobType((String)rowVals[2]);
					}
					
//					info.setSiteName((String)rowVals[3]);
					CodeValues siteCodeValue = codesService.getCodeValueByIdName("SITE_CODE", (String)rowVals[3]);
					if (siteCodeValue==null) {
						siteCodeValue = codesService.getCodeValueByIdName("REPORT_SITE_CODE", (String)rowVals[3]); //2014 Aug 20
					}
					
					if(siteCodeValue!=null){
						info.setSiteName(siteCodeValue.getCodeValueDesc());
					}else{
						info.setSiteName((String)rowVals[3]);
					}
					
					
					if(rowVals[4]!=null){
						info.setRetryTimes((BigDecimal)rowVals[4]);
					}
					/*if(rowVals[5]!=null){
						info.setLatestStatus((BigDecimal)rowVals[5]);
					}*/
					
					if(rowVals[5]!=null){
//						info.setLatestStatus((BigDecimal)rowVals[5]);
						
						CodeValues statusCodeValue = codesService.getCodeValueByIdName("DATA_SYNC_STATUS", ((BigDecimal)rowVals[5]).toString());
						
						if(statusCodeValue!=null){
							info.setLatestStatus(statusCodeValue.getCodeValueDesc());
						}else{
							info.setLatestStatus(((BigDecimal)rowVals[5]).toString());
						}
						
						
					}
					
					if(rowVals[6]!=null){
						Clob clobData = (Clob)rowVals[6];
						
						StringBuffer strOut = new StringBuffer();
						   String errorDesc;

						BufferedReader br = new BufferedReader(
								clobData.getCharacterStream());
						while ((errorDesc = br.readLine()) != null) {
							strOut.append(errorDesc);
							strOut.append(System.getProperty("line.separator"));
						}	    
						info.setErrorDesc(strOut.toString());	    
								    
					}
					
					if(rowVals[8]!=null){
						
						CodeValues transStatusCodeValue = codesService.getCodeValueByIdName("TRANSACTION_STATUS", ((String)rowVals[8]).toString());
						
						if(transStatusCodeValue!=null){
							info.setTransactionStatus(transStatusCodeValue.getCodeValueDesc());
						}else{
							info.setTransactionStatus(((String)rowVals[8]).toString());
						}
						
						
					}
					
					syncInfoList.add(info);
				}
			}
			
			
			JRDataSource JRdataSource = new JRBeanCollectionDataSource(
					syncInfoList);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
				InputStream reportStream = this.getClass().getResourceAsStream(
						"/report/templates/Detailed_Month_Data_Sync_Monitoring.jrxml");
				JasperReport jasperReport = JasperCompileManager
						.compileReport(reportStream);
				JasperReportsUtils.renderAsPdf(jasperReport, parameterMap,
						JRdataSource, os);
				byte[] bytes = os.toByteArray();
				
				if (bytes != null && bytes.length > 0) {
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition", "attachment; filename=\""
							+ fileName + ".pdf\"");
					response.setContentLength(bytes.length);

					ServletOutputStream ouputStream = null;
					try {
						ouputStream = response.getOutputStream();
						ouputStream.write(bytes, 0, bytes.length);
						ouputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
						response.getWriter().write("-1");
					} finally {
						if (ouputStream != null) {
							try {
								ouputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
								response.getWriter().write("-1");
							}
						}
					}
				}
			
			}catch(Exception exp){
				exp.printStackTrace();
				response.getWriter().write("-1");
			}
			
	}
	
	@RequestMapping(value = "/generateViewDetailsPDF")
	public void generateViewDetailsPDF(@ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm,HttpServletRequest httpRequest, String type,HttpServletResponse response,WebRequest request1,HttpServletRequest request ) throws Exception {
		try{	
			String selectedDate =  dataSyncMonitoringForm.getSelectedDate();
			
			Date selDate = DateUtil.strToDate(selectedDate, "dd-MMM-yyyy");
			String fileName = "Detailed NIC and AFIS Data Synchronization Monitoring".concat("_").concat(selectedDate);
			
			selectedDate = DateUtil.parseDate(selDate, "dd-MM-yyyy");
			
			Map<String, Object> parameterMap = new HashMap<String, Object>();

			parameterMap.put("appDate", dataSyncMonitoringForm.getSelectedDate());
			parameterMap.put("reportId", "DETAILED_DATA_SYNC_MONITORING");
			
			List<DataSyncInfo> syncInfoList = new ArrayList<DataSyncInfo>();
			
			List<Object[]> resultList = afisDataSyncService.getSynchronizionDetailsByDate(selectedDate);
			
			if(CollectionUtils.isNotEmpty(resultList)){
				for(Object[] rowVals :resultList){
					DataSyncInfo info = new DataSyncInfo();
					info.setTransId((String)rowVals[0]);
					info.setNin((String)rowVals[1]);
//					info.setJobType((String)rowVals[2]);
					//CodeValue jobTypeCodeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", (String)rowVals[2]);
					CodeValues jobTypeCodeValue = codesService.getCodeValueByIdName("JOB_TYPE", (String)rowVals[2]); //2014 Aug 20
					
					if(jobTypeCodeValue!=null){
						info.setJobType(jobTypeCodeValue.getCodeValueDesc());
					}else{
						info.setJobType((String)rowVals[2]);
					}
					
//					info.setSiteName((String)rowVals[3]);
					CodeValues siteCodeValue = codesService.getCodeValueByIdName("SITE_CODE", (String)rowVals[3]);
					if (siteCodeValue==null) {
						siteCodeValue = codesService.getCodeValueByIdName("REPORT_SITE_CODE", (String)rowVals[3]); //2014 Aug 20
					}
					
					if(siteCodeValue!=null){
						info.setSiteName(siteCodeValue.getCodeValueDesc());
					}else{
						info.setSiteName((String)rowVals[3]);
					}
					
					
					if(rowVals[4]!=null){
						info.setRetryTimes((BigDecimal)rowVals[4]);
					}
					/*if(rowVals[5]!=null){
						info.setLatestStatus((BigDecimal)rowVals[5]);
					}*/
					
					if(rowVals[5]!=null){
//						info.setLatestStatus((BigDecimal)rowVals[5]);
						
						CodeValues statusCodeValue = codesService.getCodeValueByIdName("DATA_SYNC_STATUS", ((BigDecimal)rowVals[5]).toString());
						
						if(statusCodeValue!=null){
							info.setLatestStatus(statusCodeValue.getCodeValueDesc());
						}else{
							info.setLatestStatus(((BigDecimal)rowVals[5]).toString());
						}
						
						
					}
					
					if(rowVals[6]!=null){
						Clob clobData = (Clob)rowVals[6];
						
						StringBuffer strOut = new StringBuffer();
						   String errorDesc;

						BufferedReader br = new BufferedReader(
								clobData.getCharacterStream());
						while ((errorDesc = br.readLine()) != null) {
							strOut.append(errorDesc);
							strOut.append(System.getProperty("line.separator"));
						}	    
						info.setErrorDesc(strOut.toString());	    
								    
					}
					
					if(rowVals[8]!=null){
						
						CodeValues transStatusCodeValue = codesService.getCodeValueByIdName("TRANSACTION_STATUS", ((String)rowVals[8]).toString());
						
						if(transStatusCodeValue!=null){
							info.setTransactionStatus(transStatusCodeValue.getCodeValueDesc());
						}else{
							info.setTransactionStatus(((String)rowVals[8]).toString());
						}
						
						
					}
					
					syncInfoList.add(info);
				}
			}
			
			
			JRDataSource JRdataSource = new JRBeanCollectionDataSource(
					syncInfoList);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
				InputStream reportStream = this.getClass().getResourceAsStream(
						"/report/templates/Detailed_Data_Sync_Monitoring.jrxml");
				JasperReport jasperReport = JasperCompileManager
						.compileReport(reportStream);
				JasperReportsUtils.renderAsPdf(jasperReport, parameterMap,
						JRdataSource, os);
				byte[] bytes = os.toByteArray();
				
				if (bytes != null && bytes.length > 0) {
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition", "attachment; filename=\""
							+ fileName + ".pdf\"");
					response.setContentLength(bytes.length);

					ServletOutputStream ouputStream = null;
					try {
						ouputStream = response.getOutputStream();
						ouputStream.write(bytes, 0, bytes.length);
						ouputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
						response.getWriter().write("-1");
					} finally {
						if (ouputStream != null) {
							try {
								ouputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
								response.getWriter().write("-1");
							}
						}
					}
				}
			
			}catch(Exception exp){
				exp.printStackTrace();
				response.getWriter().write("-1");
			}
			
	}
	
	@ResponseBody
	@RequestMapping(value = "/getViewSyncDetails")
	public ModelAndView getViewSyncDetails(@ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm,HttpServletRequest httpRequest, String type,HttpServletResponse response,WebRequest request1,HttpServletRequest request ) throws Exception {
		Map searchResultMap = new HashMap();
		try{
			List<DataSyncInfo> syncInfoList = new ArrayList<DataSyncInfo>();
			String transId = null;
			String nin = null;
			Long jobId = null;
			String isRetryAvai = "N";
			BigDecimal retryDataSyncId = null;
			int i=0;
				transId = dataSyncMonitoringForm.getTransactionId();
				nin = dataSyncMonitoringForm.getNin();
				String[] jobTypes = {"CON","REG","REP","PAR_UPD"};
				
				List<Object[]> resultList = afisDataSyncService.getSyncRequestAndStatusDetails(transId);
				try{
					jobId = uploadJobService.getUploadJobId(transId, jobTypes);
				}catch(NicUploadJobServiceException ex){
					searchResultMap.put("jobId", "");
				}
				
				if(CollectionUtils.isNotEmpty(resultList)){
					for(Object[] rowVals :resultList){
						DataSyncInfo info = new DataSyncInfo();
						info.setCreateDate((String)rowVals[0]);
						info.setUpdateDate((String)rowVals[1]);
						if(((Character)rowVals[2])=='I'){
							info.setSyncMode("Insert");
						}else if(((Character)rowVals[2])=='D'){
							info.setSyncMode("Delete");
						}
						if(rowVals[3]!=null){
//							info.setLatestStatus((BigDecimal)rowVals[3]);
							
							CodeValues statusCodeValue = codesService.getCodeValueByIdName("DATA_SYNC_STATUS", ((BigDecimal)rowVals[3]).toString());
							
							if(statusCodeValue!=null){
								info.setLatestStatus(statusCodeValue.getCodeValueDesc());
							}else{
								info.setLatestStatus(((BigDecimal)rowVals[3]).toString());
							}
							
							if(((BigDecimal)rowVals[3]).compareTo(new BigDecimal("4000"))==0 && i==0){
								isRetryAvai = "Y";
								retryDataSyncId = (BigDecimal)rowVals[6];
							}
						}
						if(rowVals[4]!=null){
							info.setRetryTimes((BigDecimal)rowVals[4]);
						}
						if(rowVals[5]!=null){
							Clob clobData = (Clob)rowVals[5];
							
							StringBuffer strOut = new StringBuffer();
							   String errorDesc;

							BufferedReader br = new BufferedReader(
									clobData.getCharacterStream());
							while ((errorDesc = br.readLine()) != null) {
								strOut.append(errorDesc);
								strOut.append(System.getProperty("line.separator"));
							}	    
							info.setErrorDesc(strOut.toString());	    
									    
						}
						
						if(rowVals[6]!=null){
							info.setDataSyncId((BigDecimal)rowVals[6]);
						}
						
						syncInfoList.add(info);
						i++;
					}
				}
			
		
			searchResultMap.put("list", syncInfoList);
			searchResultMap.put("nin", nin);
			searchResultMap.put("jobId", jobId);
			searchResultMap.put("transId", transId);
			searchResultMap.put("isRetryAvai", isRetryAvai);
			searchResultMap.put("retryDataSyncId", retryDataSyncId);
			
			return new ModelAndView("dataSyncMonitor-reqAndStatus-det",searchResultMap);
		}catch(Exception ex){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("errorWithoutMenu");
			modelAndView.addObject("exception", ex);
			modelAndView.addObject("message", ex.getLocalizedMessage());
			modelAndView.addObject("messageStack", ex.getMessage());
			return modelAndView;
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/retryDetailedTrans")
	public String retryDetailedTrans(@ModelAttribute(value = "dataSyncMonitoringForm") NicEnquiryForm dataSyncMonitoringForm,HttpServletRequest httpRequest, String type,HttpServletResponse response,WebRequest request1,HttpServletRequest request ) throws Exception {
		try{
			String dataSyncId = dataSyncMonitoringForm.getDataSyncId();
			afisDataSyncService.resetDataSyncRequest(new Long(dataSyncId));
				
			return "Success";
			
		}catch(Exception ex){
			logger.error("Error occurred while retrying the Transaction:"+dataSyncMonitoringForm.getTransactionId());
			return "Error";
		}
	}

}
