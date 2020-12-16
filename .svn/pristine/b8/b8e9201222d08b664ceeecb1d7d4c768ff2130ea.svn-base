/**
 * 
 */
package com.nec.asia.nic.admin.report.controller;


import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.enquiry.controller.NicTransactionInfo;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.domain.NicReportMenuForm;
import com.nec.asia.nic.framework.report.domain.Report;
import com.nec.asia.nic.framework.report.domain.ReportParameter;
import com.nec.asia.nic.framework.report.domain.NicReportCodeTable;
import com.nec.asia.nic.framework.report.form.CodeValueForm;
import com.nec.asia.nic.framework.report.form.NicReportForm;
import com.nec.asia.nic.framework.report.form.NicFlexiReport;
import com.nec.asia.nic.framework.report.service.ReportManagementService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Prasad_Karnati
 *
 */

/*
 * Modification History:
 * 
 * 17 Feb 2016 (chris) : set report category.
 */

@Controller
@RequestMapping(value = "/reportmgmt")
public class ReportManagementController extends AbstractController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ReportManagementService reportManagementService;	
	
	@Autowired
	//@Qualifier(value="codesService")
	private CodesService codesService;
	
	@Autowired
	private SiteService eppSiteService;
	
	@RequestMapping(value = "/reportGeneration/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getReportCodes(@PathVariable String id,
			WebRequest request, Model model, HttpServletRequest httpReq)
			throws Exception {

		NicReportMenuForm nicReportMenuForm = new NicReportMenuForm();
		String category = null;

		try {
			if (id == null) {
				if (request.getAttribute("reportId", 1) != null) {
					id = ((String) request.getAttribute("reportId", 1));
				}
			}

			String[] reportParams = id.split(",");

			if (reportParams != null && reportParams.length == 3) {
				category = reportParams[2];
			}

			logger.debug(" Report Id getReportCodes " + id);
			if (!category.equalsIgnoreCase("CR")) {
				nicReportMenuForm = reportManagementService.getReportParameters(id);
			} else {
				//Customized Report
				List<Report> reports = reportManagementService.getReportDetailById(reportParams[0]);
				nicReportMenuForm.setReportId(reportParams[0]);
				nicReportMenuForm.setReportFileName(reportParams[1]);
				if (CollectionUtils.isNotEmpty(reports)) {
					nicReportMenuForm.setReportDesc(reports.get(0).getDescription());
				}
			}

			nicReportMenuForm.setReportCategory(category);

		} catch (Exception exp) {
			exp.printStackTrace();
		}

		if (!category.equalsIgnoreCase("CR")) {
			if (nicReportMenuForm.getReportType() == null) {
				ModelAndView modelView = new ModelAndView("reportmgmt.reportGeneration", "reportMenuForm", nicReportMenuForm);
				model.addAttribute("fnSelected", nicReportMenuForm.getReportFileName());
				modelView.addObject("reportMenuForm", nicReportMenuForm);
				return modelView;
			} else if (nicReportMenuForm.getReportType().equals("static-param")) {
				ModelAndView modelView = new ModelAndView("reportmgmt.generateStaticParamReport",
						"reportMenuForm", nicReportMenuForm);
				model.addAttribute("fnSelected", nicReportMenuForm.getReportFileName());
				modelView.addObject("reportMenuForm", nicReportMenuForm);

				return modelView;
			} else {
				ModelAndView modelView = new ModelAndView("reportmgmt.generateStaticReport", "reportMenuForm", nicReportMenuForm);
				model.addAttribute("fnSelected", nicReportMenuForm.getReportFileName());
				modelView.addObject("reportMenuForm", nicReportMenuForm);
				return modelView;
			}
		} else {
			ModelAndView modelView = new ModelAndView("reportmgmt.customReport", 
					"reportMenuForm", nicReportMenuForm);
			model.addAttribute("fnSelected", nicReportMenuForm.getReportFileName());
			modelView.addObject("reportMenuForm", nicReportMenuForm);
			return modelView;
		}
	}
	
	@RequestMapping(value = "/report_query" )
	@ResponseBody	
	public PaginatedResult<Object> getDataByColumnNames(@RequestParam String jsonForm,WebRequest request )	throws Exception {	
		logger.debug(" Get ColumnNames and Menu Detail    ");		
		HashMap<String,String> props = (HashMap<String,String>) new ObjectMapper().readValue(jsonForm, HashMap.class);
		
		String tableName = props.get("objName");
		String tablenamee= props.get("tableName");
		String reportId= props.get("reportId");
		String reportFileName=props.get("reportFileName");
		props.remove("objName");
		props.remove("tableName");
		props.remove("selectedCols");
		props.remove("_unSelectedCols");
		if(props.containsKey("unSelectedCols")){
			props.remove("unSelectedCols");
		}
		props.remove("reportId");
		props.remove("_selectedCols");
		props.remove("reportFileName");
		if(props.containsKey("frequency")){
			props.remove("frequency");
		}
		if(props.containsKey("loginUser")){
			props.remove("loginUser");
		}
		
		if(props.containsKey("rptFrmt")){
			props.remove("rptFrmt");
		}		
		
		if(props.containsKey("reportCategory")){
			props.remove("reportCategory");
		}		
		
		String startDateVal="";
		String endDateVal="";
		String startDate="";
		String endDate="";
		if(props!=null){			
			if(props.containsKey("startDate")){
				startDateVal=props.get("startDate").trim();
				String arry [];
				if(startDateVal.contains(" ")){
					 arry = startDateVal.split(" ");
					 startDateVal=arry[0];
				}				
				
			}
			if(props.containsKey("endDate")){
				endDateVal=props.get("endDate");
				String arry [];
				if(endDateVal.contains(" ")){
					 arry = endDateVal.split(" ");
					 endDateVal=arry[0];
				}
			}			
		 }
		
		if (startDateVal != null || endDateVal != null) {
			if (!startDateVal.equals("") || !endDateVal.equals("")) {
				List<ReportParameter> reportParamList = reportManagementService.getParamList(reportId);
				for (ReportParameter param : reportParamList) {
					if (param.getParamType().toUpperCase().equals("DATE:START_DATE")) {
						startDate = param.getParameterAlias();
					}
					if (param.getParamType().toUpperCase().equals("DATE:END_DATE")) {
						endDate = param.getParameterAlias();
					}
				}
			}
		}
		props.remove("startDate");
		props.remove("endDate");
		
		String x="";
		int i=1;
		String  namedQuery ="FROM  "+tableName+" T WHERE ";
		String  queryCheck="FROM  "+tableName+" T WHERE ";
		
		HashMap<String,String> propsMap=props;
		HashMap<String,String> propsMap1 = new HashMap<String, String>();
		
		for (Map.Entry<String, String> emap : propsMap.entrySet()) {
			String key = emap.getKey();
			String value = emap.getValue();
			if (value != null && !value.equals("All")
					&& !value.equals("---SELECT---") && !value.equals("")
					&& !value.equals(" ")) {
				propsMap1.put(key, value);
			}

		}
		for (Map.Entry<String,String> emap : propsMap1.entrySet()) {
    	    String key = emap.getKey();
    	    String value =emap.getValue();    	    
    	    if (!value.isEmpty()&&!value.equals("All")&&!value.equals("---SELECT---")) {//" AND to_char("+startDate+ ",'dd/mm/yyyy')='"+startDateVal+"'";

				if (propsMap1.size() == i) {
					if(key.contains("Date")){
					x= " to_char("+key+ ",'dd/mm/yyyy')='"+value+"'";	
					}else{
					 x = "T." + key + "=" + "'" + value + "'";
					}
				} else if (propsMap1.size() > 1) {
					if(key.contains("Date")){
						x= " to_char("+key+ ",'dd/mm/yyyy')='"+value+"' AND ";	
					}else{					
					 x = "T." + key + "=" + "'" + value + "'" + " AND ";
					}
				} else {
					if(key.contains("Date")){
						x= " to_char("+key+ ",'dd/mm/yyyy')='"+value+"'";
					}else{
	       				x = "T." + key + "=" + "'" + value + "'";
					}
				}
			}
    	    namedQuery=  namedQuery+x;  
    	    i=++i;
    	}
		if(startDateVal!=null&&endDateVal!=null&&!startDate.equals("")&&!endDateVal.equals("")){
			if(startDate.equals(endDate)) {
				if(namedQuery.equals(queryCheck)) {
					namedQuery =namedQuery+" "+startDate+ "  BETWEEN  to_date("+"'"+startDateVal+" 00:00','dd/mm/yyyy HH24:MI')"+" AND "+"to_date('"+endDateVal+" 23:59','dd/mm/yyyy HH24:MI')";	
				} else {
					namedQuery =namedQuery+" AND "+startDate+ "  BETWEEN  to_date("+"'"+startDateVal+" 00:00','dd/mm/yyyy HH24:MI')"+" AND "+"to_date('"+endDateVal+" 23:59','dd/mm/yyyy HH24:MI')";
				}	
			}			
		} else if(startDateVal!=null&&!startDateVal.equals("")){
			//to_char(APP_REF_NO,'DD/MM/YYYY') 
			if(startDate!=null){
				if(namedQuery.equals(queryCheck)){
					namedQuery =namedQuery+"  to_char("+startDate+ ",'dd/mm/yyyy')='"+startDateVal+"'";	
				}else{
					namedQuery =namedQuery+" AND to_char("+startDate+ ",'dd/mm/yyyy')='"+startDateVal+"'";	
				}
			}
		}else if(endDateVal!=null&&!endDateVal.equals("")){			
			if(endDate!=null){
				if(namedQuery.equals(queryCheck)){
					namedQuery =namedQuery+" to_char("+endDate+ ",'dd/mm/yyyy')='"+endDateVal+"'";	
				}else{
					namedQuery =namedQuery+" AND to_char("+endDate+ ",'dd/mm/yyyy')='"+endDateVal+"'";	
				}
				
			}
		}				

		NicReportMenuForm formMenu = new NicReportMenuForm();
		formMenu.setNamedQuery(namedQuery);
		formMenu.setReportFileName(reportFileName);
		formMenu.setTableName(tablenamee);

		int pageNo = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer.parseInt(request.getParameter("rp")): Constants.PAGE_SIZE_DEFAULT;
	    //System.out.println(" pageno ___________________________________"+pageNo);
	    //System.out.println(" pageSize ___________________________________"+pageSize);
		//if(dtos.size()>0)
		logger.debug("=====>> Size of the of upload files =====================================> ");
		PaginatedResult<Object> result = reportManagementService.getReportData(formMenu.getNamedQuery(), pageNo, pageSize, tableName); 
		
		return result;
	}

	@RequestMapping(value="/generation/{id}",method = RequestMethod.GET)
	public ModelAndView definition(@PathVariable String id, String var,Model model) {     
		
		logger.debug(" Inside the Report geneartion Method  ");				
		logger.debug(" Category id:  " + id);		
		NicReportForm reportForm =new NicReportForm();
		List<CodeValues> codevaluesList = null;
		try {
			codevaluesList = getCodeValueList(Codes.REPORT_CATEGORY);
			model.addAttribute("reportCategoryList", codevaluesList);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		reportForm.setReportCategory(id);
		
		boolean idExists = false;
		String defaultValue = null;
		if (CollectionUtils.isNotEmpty(codevaluesList)) {
			for (CodeValues codevalue: codevaluesList) {
				if (StringUtils.equalsIgnoreCase(id, codevalue.getId().getCodeValue())) {
					idExists = true;
				}
				if (codevalue.getCodePriority()!=null && codevalue.getCodePriority().intValue()==-1) {
					defaultValue = codevalue.getId().getCodeValue();
				}
			}
		}
		if (!idExists && defaultValue!=null) {
			logger.debug("overwritten Category id: {} ", defaultValue);	
			reportForm.setReportCategory(defaultValue);
		}
		if (idExists || defaultValue!=null) {
			reportForm.setCategoryLoad("Y");
		}
		
		ModelAndView modelView = new ModelAndView ("reportmgmt.generation","nicReportForm", reportForm);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		model.addAttribute("jobList", new ArrayList<NicFlexiReport>());
		modelView.addObject("nicReportForm", reportForm);
		return modelView;
	}
	
	private List<CodeValues> getCodeValueList(String codeId) throws NicUploadJobServiceException {
		return codesService.findAllByCodeId(codeId);
	}
	
	@RequestMapping(value = "/query_ajax/{id}")
	@ResponseBody
	public NicFlexiReport[] queryAjax(@PathVariable String id ,WebRequest request, NicReportCodeTable nicReportCodeTable) 
		throws Exception {
		int pageNo=1;
		//int pageSize=1;
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		logger.debug("Calling the report table to generate HTML Report queryAjax"+id);		 
		List<NicFlexiReport> statisticalReport = reportManagementService.getStatisticalRepors(id);
		if(statisticalReport.size()>0)
		logger.debug("=====>> Size of the Report Category =====================================> "+statisticalReport.size());
		PaginatedResult<NicFlexiReport> result = new PaginatedResult<NicFlexiReport>(pageNo, pageSize, statisticalReport);
		List<NicFlexiReport> txnList = new ArrayList<NicFlexiReport>();
		txnList = result.getRows();
		NicFlexiReport[] arr = new NicFlexiReport[txnList.size()];
         for(int i = 0; i < txnList.size(); i++){
            arr[i] = txnList.get(i);
         }
         return arr;
	}
		
	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	public String download(@RequestParam String jsonObj ,@ModelAttribute("reportMenuForm") NicReportMenuForm codeValueForm, String type, String token, HttpServletResponse response, WebRequest request1, HttpServletRequest request) throws Exception {
		String status="";
		String id=null;
		String repName="";
		logger.debug("Enter in to pdf download method  : ReportManagementController");
		HashMap<String,String> props;
		if(jsonObj!=null){
			props = (HashMap<String,String>) new ObjectMapper().readValue(jsonObj, HashMap.class);				 
			props.remove("selectedCols");
			props.remove("_unSelectedCols");
			props.remove("_selectedCols");
			if (props.containsKey("rptFrmt")) {
				props.remove("rptFrmt");
			}
			if (props.containsKey("rp")) {
				props.remove("rp");
			}

			if (props.containsKey("reportCategory")) {
				props.remove("reportCategory");
			}
		} else {
			props =new HashMap<String,String>();
		}
		id=props.get("reportId");
		repName=props.get("reportFileName");
		logger.debug(" List Of Columns  ");							
		try {
			//remove codeValueForm (replace CodeValueForm to NicReportMenuForm)
			status = reportManagementService.generateReport("pdf", token, response, props, null);
			logger.debug("Exit from the pdf download method  : ReportManagementController");
		} catch (Exception exp) {
			status = "fail";
			logger.debug("Error while generating the PDF ", exp);
		}			
		if(status.equals("fail")){
			request.setAttribute("", "Error Occured While Generating the Report");
			return "forward:"+"reportGeneration/"+id+","+repName;
		}

		return "success";
	}
	
	@RequestMapping(value = "/xls", method = RequestMethod.GET)
	public String xlsDownload(@RequestParam String jsonObj ,@ModelAttribute("reportMenuForm") NicReportMenuForm codeValueForm, String type, String token,HttpServletResponse response,WebRequest request1,HttpServletRequest request ) throws Exception {
		String status="";
		String id=null;
		String repName="";
		logger.debug("Enter in to pdf download method  : ReportManagementController");
		HashMap<String,String> props;
		if (jsonObj != null) {
			props = (HashMap<String, String>) new ObjectMapper().readValue(jsonObj, HashMap.class);
			
			props.remove("selectedCols");
			props.remove("_unSelectedCols");
			props.remove("_selectedCols");
			if (props.containsKey("rp")) {
				props.remove("rp");
			}
			if (props.containsKey("rptFrmt")) {
				props.remove("rptFrmt");
			}
			if (props.containsKey("reportCategory")) {
				props.remove("reportCategory");
			}	
		} else {
			props =new HashMap<String,String>();
		}
		id = props.get("reportId");
		repName = props.get("reportFileName");	
		logger.debug(" List Of Columns  ");							
		try {		
			//remove codeValueForm (replace CodeValueForm to NicReportMenuForm)
			status = reportManagementService.generateReport("xls", token, response,props, null);	
			logger.debug("Exit from the xls download method  : ReportManagementController");	
		} catch(Exception exp){
			status = "fail";
			logger.debug("Error while generating the xls ",exp);
		}
		
		if(status.equals("fail")){
			request.setAttribute("", "Error Occured While Generating the Report");	
			return "forward:"+"reportGeneration/"+id+","+repName;
		}
		return "success";
	}
	
	@RequestMapping(value = "/csv", method = RequestMethod.GET)
	public String csvDownload(@RequestParam String jsonObj, @ModelAttribute("reportMenuForm") NicReportMenuForm codeValueForm, String type, String token,
			HttpServletResponse response, WebRequest request1, HttpServletRequest request) throws Exception {
		String status = "";
		String id = null;
		String repName = "";
		logger.debug("Enter in to pdf download method  : ReportManagementController");
		HashMap<String, String> props;
		if (jsonObj != null) {
			props = (HashMap<String, String>) new ObjectMapper().readValue(jsonObj, HashMap.class);
			props.remove("selectedCols");
			props.remove("_unSelectedCols");
			props.remove("_selectedCols");
			if (props.containsKey("rp")) {
				props.remove("rp");
			}
			if (props.containsKey("rptFrmt")) {
				props.remove("rptFrmt");
			}
			if (props.containsKey("reportCategory")) {
				props.remove("reportCategory");
			}
		} else {
			props = new HashMap<String, String>();
		}
		
		id = props.get("reportId");
		repName = props.get("reportFileName");

		logger.debug(" List Of Columns  ");
		try {
			//remove codeValueForm (replace CodeValueForm to NicReportMenuForm)
			status = reportManagementService.generateReport("csv", token, response, props, null);
			logger.debug("Exit from the pdf download method  : ReportManagementController");
		} catch (Exception exp) {
			status = "fail";
			logger.debug("Error while generating the CSV ", exp);
		}
		if (status.equals("fail")) {
			request.setAttribute("", "Xảy ra lỗi trong khi tạo báo cáo");

			return "forward:" + "reportGeneration/" + id + "," + repName;
		}

		return "success";
	}
	
	@RequestMapping(value="/backtogeneration",method = RequestMethod.GET)
	public ModelAndView backToGenerationPage(String var, Model model) {     
		logger.debug(" Inside the Report geneartion Method  ");				
		NicReportForm reportForm = new NicReportForm();		
		try {
			model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
			
			} catch (Exception exp) {			
			exp.printStackTrace();
		}
		logger.debug("");
		ModelAndView modelView = new ModelAndView ("reportmgmt.generation","nicReportForm",reportForm);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		modelView.addObject("nicReportForm", reportForm);
		return modelView;
	}
	
	@ResponseBody	
	@RequestMapping(value = "/staticParamRep", method = RequestMethod.GET)
	public void downloadRep(@RequestParam String jsonObj ,@ModelAttribute("reportMenuForm") CodeValueForm codeValueForm,String type, String token,HttpServletResponse response,WebRequest request1,HttpServletRequest request ) throws Exception {
		 JasperReport jr;
		try{	
		HashMap<String,Object> props=null;
		 HttpSession session = request.getSession();
		 UserSession userSession = (UserSession) session.getAttribute("userSession");
		 String status=null;
		 if(jsonObj!=null){
			 props = (HashMap<String,Object>) new ObjectMapper().readValue(jsonObj, HashMap.class);
			String reportFmt=(String)props.get("rptFrmt");
			String reportId =(String)props.get("reportId");
			String reportName =(String)props.get("reportFileName");
			String reportCategory =(String)props.get("reportCategory");
			props.remove("rptFrmt");
//			props.remove("reportId");
			props.remove("reportFileName");	
			props.remove("reportCategory");	
			// 8 Jan 14 (Prasad) : removed hardcoded userid
			props.put("loginUser", userSession.getUserId());
			props.put("userID", userSession.getUserId());
			if(StringUtils.isNotBlank(reportCategory) && !reportCategory.equalsIgnoreCase("CR")){
				if("DFA1".equalsIgnoreCase(reportId)){
					VirtualFile vFile = VFS.getChild(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
				    URI fileNameDecodedTmp = org.jboss.vfs.VFSUtils.getPhysicalURI(vFile);
				    String path = fileNameDecodedTmp.getPath();
				    File f = new File(path+"report/DFA_Logo.jpg");
					InputStream stream =  new  ByteArrayInputStream(HelperClass.getBytesFromFile(f));

					String siteGroupId = (String)props.get("siteGroup");
					Map<String, Object> parameterMap = new HashMap<String, Object>();
					parameterMap.put( "reportId",  reportId);  
					parameterMap.put("userID", userSession.getUserId()); 		
					parameterMap.put("logo", stream);
					parameterMap.put("ricSiteGroup", siteGroupId); 
					SiteGroups siteGroups = eppSiteService.getSiteGroupById(siteGroupId);
					List<String> siteRepoIds = new ArrayList<String>();
					List list = null;
					Map<String, String> siteRepoMap = new HashMap<String, String>();
					if(siteGroups!=null){
						parameterMap.put("ricSiteGroupDesc", siteGroups.getGroupName()); 
						if(siteGroups.getSiteRepositories() != null && siteGroups.getSiteRepositories().size() > 0){
							for(SiteRepository site : siteGroups.getSiteRepositories()){
								if(site != null){
									siteRepoIds.add(site.getSiteId());
									if(!siteRepoMap.containsKey(site.getSiteId()))
										siteRepoMap.put(site.getSiteId(), site.getSiteName());
								}
							}
						}
					}
					
					Collections.sort(siteRepoIds);
					String siteRepoIdArray = "";
					for (int i = 0; i < siteRepoIds.size(); i++) {
						if(i == siteRepoIds.size() - 1)
							siteRepoIdArray = siteRepoIdArray + "'" + siteRepoIds.get(i) + "'";
						else
							siteRepoIdArray = siteRepoIdArray + "'" + siteRepoIds.get(i) + "',";
					} 
					String releaseDate = "RELEASE_DATE";
					if(StringUtils.isNotBlank(siteRepoIdArray)){					
						String query = "SELECT * FROM" + 
								"(" + 
									   " SELECT TRUNC(DA.DATE_OF_ISSUE, 'DD') " +  releaseDate + " , TR.REG_SITE_CODE " + 
									   " FROM EPP_CENTRAL.EPP_TRANSACTION TR, EPP_CENTRAL.EPP_DOCUMENT_DATA DA " + 
									   " WHERE  TR.TRANSACTION_ID = DA.TRANSACTION_ID" +
									   " AND DA.DATE_OF_ISSUE IS NOT NULL" +
									   " AND TR.REG_SITE_CODE IN ( " + siteRepoIdArray + ")" + 
									" )" + 
									" PIVOT" + 
									" (" + 
									 " COUNT(REG_SITE_CODE)" + 
									  " FOR REG_SITE_CODE IN (" + siteRepoIdArray + ")" + 
									" )" + 
									" ORDER BY " + releaseDate;
						
						list = reportManagementService.getReportData(reportId, query);
					}
					
					JasperPrint jp = generateApplicationStateOvervierPerSiteReport(list, siteRepoMap, path, parameterMap, releaseDate);
					jp.setName(reportName);
					
					if (StringUtils.isNotBlank(reportFmt) && reportFmt.equalsIgnoreCase("pdf")) {
						reportManagementService.exportToPdf(jp, null, response);
					} else if (StringUtils.isNotBlank(reportFmt) && reportFmt.equalsIgnoreCase("xls")) {
						reportManagementService.exportToXls(jp, null, response);
					} else if (StringUtils.isNotBlank(reportFmt) && reportFmt.equalsIgnoreCase("csv")) {
						reportManagementService.exportToCsv(jp, null, response);
					}
				}else{
					status =reportManagementService.generateStatisticsReport(props,reportId,reportName,response,reportFmt);
				}
			}else{
				 VirtualFile vFile = VFS.getChild(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
			     URI fileNameDecodedTmp = org.jboss.vfs.VFSUtils.getPhysicalURI(vFile);
			     
				 String path = fileNameDecodedTmp.getPath();
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put( "reportId",  reportId); 
				File f = new File(path+"report/DFA_Logo.jpg");
				InputStream stream =  new  ByteArrayInputStream(HelperClass.getBytesFromFile(f));
				parameterMap.put("logo", stream);
				parameterMap.put("reportTitle", reportName);
				
				List list = reportManagementService.getDynaJasperReportData(props, reportId, reportName);
				
				FastReportBuilder drb = new FastReportBuilder(); 
			/*	 AbstractColumn ageLocCol = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
				  			.setColumnProperty("ageLocation", String.class.getName())	//defines the field of the data source that this column will show, also its type
				 			.setTitle("Age/CC Location")								//the title for the column
				  			.setWidth(585).setFixedWidth(false)							//the width of the column
				 			.build();	*/
				Style headerStyle = new Style();
				Font columnFont=new Font(10, "SansSerif", false);
				headerStyle.setBorder(Border.THIN());
		 		headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		 		headerStyle.setTransparency(Transparency.OPAQUE);
		 		headerStyle.setStretchWithOverflow(true);
		 		headerStyle.setFont(columnFont);
				
				   DynaProperty[] dynaProperties = null;
				for (Integer i=0;i<list.size();i++) {
					
					DynaBean row = (DynaBean)list.get(i);
			        HashMap<String,Object> resultRow=new HashMap<String,Object>();
			        // each raw got the same column names, no need to fetch this for every line
			        if (dynaProperties == null) {
			            dynaProperties = row.getDynaClass().getDynaProperties();
			        }
			        for (Integer j=0;j<dynaProperties.length;j++) {
			            String columnName=dynaProperties[j].getName();
			            AbstractColumn siteCol = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
					  			.setColumnProperty(columnName, String.class.getName())	//defines the field of the data source that this column will show, also its type
					 			.setTitle(columnName)								//the title for the column
					  			.setWidth(485)
					  			.setFixedWidth(false)	
					  			.setStyle(headerStyle)								//the width of the column
					 			.build();	
					 
					 drb.addColumn(siteCol);
			        }
//					 drb.addColumn(ageGroupInfo.getName(), ageGroupInfo.getNameProperty(), String.class.getName(),120,columnStyle);	
				 }
				 DynamicReport dr = drb.build();
				jr = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(),parameterMap);
				JasperPrint jp = JasperFillManager.fillReport(jr, parameterMap,new JRBeanCollectionDataSource(convertDynaBeanListToArrayList(list)));
				
				reportManagementService.exportToPdf(jp,null,response);
			}
		}
			  
				
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/staticpdf")	 
	public void generateStatisticsReport (@RequestParam String jsonObj,Model model ,@ModelAttribute("reportMenuForm") NicReportMenuForm codeValueForm,String type, String token,HttpServletResponse response,WebRequest request1,HttpServletRequest request){//(@RequestParam String reportId,@RequestParam String reportName,HttpServletResponse response){		
		try{	
			HashMap<String,Object> props=null;;
			 HttpSession session = request.getSession();
			 UserSession userSession = (UserSession) session.getAttribute("userSession");
			 String status=null;
			 JasperReport jr;
			 if(jsonObj!=null){
				props = (HashMap<String,Object>) new ObjectMapper().readValue(jsonObj, HashMap.class);
				String reportFmt=(String)props.get("rptFrmt");
				String reportId =(String)props.get("reportId");
				String reportName =(String)props.get("reportFileName");
				String reportCategory =(String)props.get("reportCategory");
				String rptFrmt = (String)props.get("rptFrmt");
				props.remove("rptFrmt");
				props.remove("reportId");
				props.remove("reportFileName");	
				props.remove("reportCategory");	
				// 8 Jan 14 (Prasad) : removed hardcoded userid
				props.put("loginUser",  userSession.getUserId());
				if(StringUtils.isNotBlank(reportCategory) && !reportCategory.equalsIgnoreCase("CR")){
				  status =reportManagementService.generateStatisticsReport(props,reportId,reportName,response,reportFmt);
				}else{
					if(reportFmt.equalsIgnoreCase("HTML")){
						List list = reportManagementService.getDynaJasperReportData(props, reportId, reportName);
						
						ModelAndView modelView = new ModelAndView ("reportmgmt.queryResult");
						modelView.addObject("nicReportForm", list);
					}else{
						 VirtualFile vFile = VFS.getChild(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
					     URI fileNameDecodedTmp = org.jboss.vfs.VFSUtils.getPhysicalURI(vFile);
					     
						 String path = fileNameDecodedTmp.getPath();
						Map<String, Object> parameterMap = new HashMap<String, Object>();
						parameterMap.put( "reportId",  reportId); 
						File f = new File(path+"report/DFA_Logo.jpg");
						InputStream stream =  new  ByteArrayInputStream(HelperClass.getBytesFromFile(f));
						parameterMap.put("logo", stream);
						parameterMap.put("reportTitle", reportName);
						List list = null;
						try{
							list = reportManagementService.getDynaJasperReportData(props, reportId, reportName);
						}catch(Exception ex){
							response.getWriter().write("-1");
							return;
						}
						
						FastReportBuilder drb = new FastReportBuilder(); 
					/*	 AbstractColumn ageLocCol = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
						  			.setColumnProperty("ageLocation", String.class.getName())	//defines the field of the data source that this column will show, also its type
						 			.setTitle("Age/CC Location")								//the title for the column
						  			.setWidth(585).setFixedWidth(false)							//the width of the column
						 			.build();	*/
						Style headerStyle = new Style();
						Font columnFont=new Font(10, "SansSerif", false);
						headerStyle.setBorder(Border.THIN());
				 		headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
				 		headerStyle.setTransparency(Transparency.OPAQUE);
				 		headerStyle.setStretchWithOverflow(true);
				 		headerStyle.setFont(columnFont);
				 		headerStyle.setBackgroundColor(Color.LIGHT_GRAY);
				 		
				 		Style detailStyle = new Style();
				 		
				 		  
				 		detailStyle.setBorder(Border.THIN());
				 		detailStyle.setHorizontalAlign(HorizontalAlign.LEFT);
				 		detailStyle.setTransparency(Transparency.OPAQUE);
				 		detailStyle.setStretchWithOverflow(true);
				 		detailStyle.setFont(columnFont);
						   DynaProperty[] dynaProperties = null;
						//for (Integer i=0;i<list.size();i++) {
							
							DynaBean row = (DynaBean)list.get(0);
					        HashMap<String,Object> resultRow=new HashMap<String,Object>();
					        // each raw got the same column names, no need to fetch this for every line
					        if (dynaProperties == null) {
					            dynaProperties = row.getDynaClass().getDynaProperties();
					        }
					        for (Integer j=0;j<dynaProperties.length;j++) {
					            String columnName=dynaProperties[j].getName();
					            columnName = columnName.replaceAll("\\(\\*\\)", "");
					           // columnName = columnName.replaceAll("*", "");
					            AbstractColumn siteCol = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
							  			.setColumnProperty(columnName, String.class.getName())	//defines the field of the data source that this column will show, also its type
							 			.setTitle(columnName)								//the title for the column
							  			.setWidth(125)
							  			.setFixedWidth(false)	
							  			.setStyle(detailStyle)								//the width of the column
							 			.build();	
							 
							 drb.addColumn(siteCol);
							 
					       // }
					        
							
//							 drb.addColumn(ageGroupInfo.getName(), ageGroupInfo.getNameProperty(), String.class.getName(),120,columnStyle);	
						 }
					        
					        drb.setPrintColumnNames(true) ;  
							 
							 drb.setTemplateFile(path+"report/dynamic_report_template.jrxml");
							
//							 drb.setUseFullPageWidth(true); 
							 drb.setDefaultStyles(null, null, headerStyle, detailStyle);
						 DynamicReport dr = drb.build();
					
						
						
						jr = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(),parameterMap);
						
						
						JasperPrint jp = JasperFillManager.fillReport(jr, parameterMap,new JRBeanCollectionDataSource(convertDynaBeanListToArrayList(list)));
						
						if(dynaProperties.length>4){
							jp.setPageWidth((dynaProperties.length*125)+10);
						}else{
							jp.setPageWidth(800);
						}
						jp.setName(reportName);
						
						if(StringUtils.isNotBlank(rptFrmt) && rptFrmt.equalsIgnoreCase("pdf"))
							{
								reportManagementService.exportToPdf(jp,null,response);
							}else if (StringUtils.isNotBlank(rptFrmt) && rptFrmt.equalsIgnoreCase("xls")){
								reportManagementService.exportToXls(jp,null,response);
							}else if (StringUtils.isNotBlank(rptFrmt) && rptFrmt.equalsIgnoreCase("csv")){
								reportManagementService.exportToCsv(jp,null,response);
							}
					}
				}
				  
					
			 }
			}catch(Exception exp){
				exp.printStackTrace();
			}
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCustomTableData")	 
	public ModelAndView getCustomTableData (@RequestParam String jsonObj,Model model ,@ModelAttribute("reportMenuForm") NicReportMenuForm codeValueForm,String type, String token,HttpServletResponse response,WebRequest request1,HttpServletRequest request) throws Exception {//(@RequestParam String reportId,@RequestParam String reportName,HttpServletResponse response){		
		ModelAndView modelView = new ModelAndView ("reportmgmt.queryResult");
		try {
			HashMap<String, Object> props = null;
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			String status = null;
			JasperReport jr;
			if (jsonObj != null) {
				props = (HashMap<String, Object>) new ObjectMapper().readValue(jsonObj, HashMap.class);
				String reportFmt = (String) props.get("rptFrmt");
				String reportId = (String) props.get("reportId");
				String reportName = (String) props.get("reportFileName");
				String reportCategory = (String) props.get("reportCategory");
				List<DynaBean> list = reportManagementService.getDynaJasperReportData(props, reportId, reportName);
				
				//remove codeValueForm (replace CodeValueForm to NicReportMenuForm)
				//modelView.addObject("reportMenuForm", codeValueForm);
				modelView.addObject("list", convertDynaBeanListToArrayList(list));
			}
			return modelView;
		} catch (Exception exp) {
			exp.printStackTrace();
			response.getWriter().write("-1");
			return null;
		}
	
	}
	
	public ArrayList<Map<String,Object>> convertDynaBeanListToArrayList(List<DynaBean> theList) {
	    ArrayList<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	    DynaProperty[] dynaProperties = null;
	    for (Integer i=0;i<theList.size();i++) {
	        DynaBean row = theList.get(i);
	        Map<String,Object> resultRow=new LinkedHashMap<String,Object>();
	        // each raw got the same column names, no need to fetch this for every line
	        if (dynaProperties == null) {
	            dynaProperties = row.getDynaClass().getDynaProperties();
	        }
	        for (Integer j=0;j<dynaProperties.length;j++) {
	            String columnName=dynaProperties[j].getName();
	           String columnNameModified = columnName.replaceAll("\\(\\*\\)", "");
	            if(row.get(columnName)!=null){
	            	if(row.get(columnName) instanceof BigDecimal){
	            		resultRow.put(columnNameModified, ((BigDecimal)row.get(columnName)).toPlainString());
	            	}if(row.get(columnName) instanceof Long){
	            		resultRow.put(columnNameModified, ((Long)row.get(columnName)).toString());
	            	}else{
	            		resultRow.put(columnNameModified, (row.get(columnName)).toString());
	            	}
	            }else{
	            	resultRow.put(columnNameModified, "");
	            }
	        }
	        result.add(resultRow);
	    }

	    return result;
	}
	
	public ArrayList<Map<String,Object>> convertDynaBeanListToArrayListForDFA1(List<DynaBean> theList, String totalColPropertyName, String releaseDateColName) {
	    ArrayList<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	    DynaProperty[] dynaProperties = null;
		Map<String, Object> lastResultRow = new LinkedHashMap<String, Object>();
		lastResultRow.put(releaseDateColName, "Total Result");
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM, yyyy");
		BigDecimal bottomRightTotalCell = new BigDecimal(0);
		
		for (Integer i = 0; i < theList.size(); i++) {
	        DynaBean row = theList.get(i);
			Map<String, Object> resultRow = new LinkedHashMap<String, Object>();
	        
	        // each raw got the same column names, no need to fetch this for every line
	        if (dynaProperties == null) {
	            dynaProperties = row.getDynaClass().getDynaProperties();
	        }
	        BigDecimal totalColumnValue =  new BigDecimal(0);
	       
	       
			for (Integer j = 0; j < dynaProperties.length; j++) {
				BigDecimal lastCellValue = new BigDecimal(0);
				String columnName = dynaProperties[j].getName();
				String columnNameModified = columnName.replaceAll("\\(\\*\\)", "");
	            if(row.get(columnName)!=null){
	            	if(lastResultRow.containsKey(columnNameModified) && lastResultRow.get(columnNameModified) instanceof BigDecimal )
	            		lastCellValue = (BigDecimal) lastResultRow.get(columnNameModified);
	            	
	            	if(row.get(columnName) instanceof BigDecimal){
	            		resultRow.put(columnNameModified, ((BigDecimal)row.get(columnName)).toPlainString());
	            		totalColumnValue = totalColumnValue.add((BigDecimal)row.get(columnName));
	            		lastCellValue = lastCellValue.add((BigDecimal)row.get(columnName));
	            	} else if(row.get(columnName) instanceof Long){
	            		resultRow.put(columnNameModified, ((Long)row.get(columnName)).toString());
	            		totalColumnValue = totalColumnValue.add(new BigDecimal((Long)row.get(columnName)));
	            		lastCellValue = lastCellValue.add((BigDecimal)row.get(columnName));
	            	} else if(row.get(columnName) instanceof Date){	            		
	            		resultRow.put(columnNameModified, formatter.format((Date)row.get(columnName)));
	            	} else{
	            		resultRow.put(columnNameModified, (row.get(columnName)).toString());
	            	}
	            }else{
	            	resultRow.put(columnNameModified, "");
	            }
	            
	            if(!releaseDateColName.equalsIgnoreCase(columnNameModified))
	            	lastResultRow.put(columnNameModified, lastCellValue);
	        }
			
			bottomRightTotalCell = bottomRightTotalCell.add(totalColumnValue);
			resultRow.put(totalColPropertyName, totalColumnValue.toPlainString());
	        result.add(resultRow);
	    }
		
		// Format to string
		for(String key: lastResultRow.keySet()){
			if(lastResultRow.get(key) != null){
				if(lastResultRow.get(key) instanceof BigDecimal){
					lastResultRow.put(key, ((BigDecimal)lastResultRow.get(key)).toPlainString());
            	} else if(lastResultRow.get(key) instanceof Long){
            		lastResultRow.put(key, ((Long)lastResultRow.get(key)).toString());
            	} else if(lastResultRow.get(key) instanceof Date){	    
            		lastResultRow.put(key, formatter.format((Date)lastResultRow.get(key)));
            	} else{
            		lastResultRow.put(key, (lastResultRow.get(key)).toString());
            	}
			}
		}
		
		// Add sum value at bottom right corner.
		lastResultRow.put(totalColPropertyName, bottomRightTotalCell.toPlainString());
		
	    result.add(lastResultRow);
	    return result;
	}
	
	public JasperPrint generateApplicationStateOvervierPerSiteReport(List list, Map<String, String> siteRepoMap, String path, Map<String, Object> parameterMap, String releaseDateColName) throws JRException{
		JasperPrint jp;
		Style headerStyle = new Style();
		Font columnFont = new Font(10, "SansSerif", false);
		headerStyle.setBorder(Border.THIN());
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);
		headerStyle.setStretchWithOverflow(true);
		headerStyle.setFont(columnFont);
		headerStyle.setBackgroundColor(Color.LIGHT_GRAY);

		Style detailStyle = new Style();	
		detailStyle.setBorder(Border.THIN());
		detailStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		detailStyle.setTransparency(Transparency.OPAQUE);
		detailStyle.setStretchWithOverflow(true);
		detailStyle.setFont(columnFont);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setPrintColumnNames(true) ;  
		drb.setTemplateFile(path+"report/templates/DFA1_Application_State_Overview_per_Site.jrxml");
		drb.setDefaultStyles(null, null, headerStyle, detailStyle);
		
		// Last column
		String totalColumnPropertyName = "Total";
		AbstractColumn lastCol = ColumnBuilder
				.getNew()
				.setColumnProperty(totalColumnPropertyName, String.class.getName())
				.setTitle("Total Result")
				.setWidth(125)
				.setFixedWidth(false)
				.setHeaderStyle(headerStyle)
				.setStyle(detailStyle) // the width of the column
				.build();
		
		ArrayList<Map<String,Object>> detailData;
		if(list != null && list.size() > 0){
			DynaProperty[] dynaProperties = null;
			DynaBean row = (DynaBean) list.get(0);
			// each raw got the same column names, no need to fetch
			// this for every line
			if (dynaProperties == null) {
				dynaProperties = row.getDynaClass().getDynaProperties();
			}
			
			String columnName;
			String columnTittle;
			for (Integer j = 0; j < dynaProperties.length; j++) {
				columnName = dynaProperties[j].getName();
				columnName = columnName.replaceAll("\\(\\*\\)", "");
				columnTittle = columnName;
				if(siteRepoMap.containsKey(columnName.replace("'", ""))){
					columnTittle = columnName.replace("'", "") + ": " +siteRepoMap.get( columnName.replace("'", "")) ;
				} 
				if(releaseDateColName.equals(columnName)){
					columnTittle = "Ngày phát hành";
				}
				// columnName = columnName.replaceAll("*", "");
				AbstractColumn siteCol = ColumnBuilder
						.getNew()								// creates a new instance of a ColumnBuilder
						.setColumnProperty(columnName, String.class.getName())// defines the field of the data source that this column will show, also its type
						.setTitle(columnTittle)								// the title for the column
						.setWidth(releaseDateColName.equals(columnName) ? 125: 100)
						.setFixedWidth(false)
						.setHeaderStyle(headerStyle)
						.setStyle(detailStyle) // the width of the column
						.build();
	
				drb.addColumn(siteCol);
			}
			
			drb.addColumn(lastCol);
			
			detailData = convertDynaBeanListToArrayListForDFA1(list, totalColumnPropertyName, releaseDateColName);
		} else {
			detailData = new ArrayList<Map<String,Object>>();
			Map<String, Object> lastRow = new LinkedHashMap<String, Object>();
			
			//Add first column
			AbstractColumn firstCol = ColumnBuilder
					.getNew()
					.setColumnProperty(releaseDateColName, String.class.getName())
					.setTitle("Ngày phát hành")
					.setWidth(125)
					.setFixedWidth(false)
					.setHeaderStyle(headerStyle)
					.setStyle(detailStyle)
					.build();			
			drb.addColumn(firstCol);
			
			if(siteRepoMap.isEmpty()){ // Has no site repository
				drb.addColumn(lastCol);
				
				// Build detail data
				lastRow.put(releaseDateColName, "Tổng số");
				lastRow.put(totalColumnPropertyName, "0");
				detailData.add(lastRow);
			} else { // Has site repository but no detail data
				List<String> siteRepoIDs = new ArrayList<String>();
				for(String key : siteRepoMap.keySet())
					siteRepoIDs.add(key);
				Collections.sort(siteRepoIDs);
				
				String columnName;
				String columnTittle;
				for (int i = 0; i < siteRepoIDs.size(); i++) {
					columnName = siteRepoIDs.get(i);
					columnName = columnName.replaceAll("\\(\\*\\)", "");
					columnTittle = columnName;
					if(siteRepoMap.containsKey(columnName.replace("'", ""))){
						columnTittle = columnName.replace("'", "") + ": " + siteRepoMap.get( columnName.replace("'", "")) ;
					} 
					
					// columnName = columnName.replaceAll("*", "");
					AbstractColumn siteCol = ColumnBuilder
							.getNew()								// creates a new instance of a ColumnBuilder
							.setColumnProperty(columnName, String.class.getName())// defines the field of the data source that this column will show, also its type
							.setTitle(columnTittle)								// the title for the column
							.setWidth(releaseDateColName.equals(columnName) ? 125: 100)
							.setFixedWidth(false)
							.setHeaderStyle(headerStyle)
							.setStyle(detailStyle) // the width of the column
							.build();
		
					drb.addColumn(siteCol);
					
					lastRow.put(columnName, "0");
				}
				drb.addColumn(lastCol);
				
				lastRow.put(releaseDateColName, "Tổng số");
				lastRow.put(totalColumnPropertyName, "0");
				detailData.add(lastRow);
			}
		}
		
		DynamicReport dr = drb.build();
		JasperReport jr = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), parameterMap);
		jp  = JasperFillManager.fillReport(jr, parameterMap, new JRBeanCollectionDataSource(detailData));
		if(siteRepoMap.keySet().size() > 4){
			jp.setPageWidth((siteRepoMap.keySet().size() * 100) + 260);
		}else{
			jp.setPageWidth(800);
		}
		
		return jp;
	}
}
