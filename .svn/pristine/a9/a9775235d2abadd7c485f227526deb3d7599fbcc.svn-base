package com.nec.asia.nic.admin.report.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.domain.ReportParameter;
import com.nec.asia.nic.framework.report.domain.ReportParameterId;
import com.nec.asia.nic.framework.report.domain.ReportTemplateId;
import com.nec.asia.nic.framework.report.domain.Report;
import com.nec.asia.nic.framework.report.domain.ReportTemplate;
import com.nec.asia.nic.framework.report.domain.NicReportCodeTable;
import com.nec.asia.nic.framework.report.form.NicFlexiReport;
import com.nec.asia.nic.framework.report.form.NicReportForm;
import com.nec.asia.nic.framework.report.service.ReportManagementService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;

import org.apache.commons.lang.StringUtils;





@Controller
@RequestMapping(value = "/report")

public class ReportTemplateUploadController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(ReportTemplateUploadController.class);
	
	@Autowired
	private ReportManagementService nicReportCodeService;
	
	@Autowired
	//@Qualifier(value="codesService")
	private CodesService codesService;
	
	public ReportTemplateUploadController(){}	
	
	@RequestMapping(value="/view" , method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView viewReport(@ModelAttribute("nicReportForm") NicReportForm reportForm, BindingResult result,Model model) throws Exception {
		logger.debug("Loding the reportCategory types : definition()");		
		
		NicReportForm reportFormResponse = new NicReportForm();		
		try {

			reportFormResponse.setReportCategory(reportForm.getReportCategory());

			model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
		} catch (Exception exp) {
			logger.error("Error while loading reportCategory types", exp);
			//exp.printStackTrace();
		}
		ModelAndView  modelView = new ModelAndView ("report.defPage","nicReportForm", reportFormResponse);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
		modelView.addObject("nicReportForm", reportFormResponse);
		logger.debug("Exit loding the reportCategory types : definition()");
		return modelView;
	}
	
	private List<CodeValues> getCodeValueList(String codeId) throws NicUploadJobServiceException {
		return codesService.findAllByCodeId(codeId);
	}
	
	@RequestMapping(value = "/query_ajax/{id}")
	@ExceptionHandler
	public ModelAndView queryAjax(@PathVariable String id ,WebRequest request, NicReportCodeTable nicReportCodeTable, Model model) 
		throws Exception {
		NicReportForm nicReportForm =new NicReportForm();		
		ModelAndView modelView =new ModelAndView ("report.definition","nicReportForm",nicReportForm);
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		int pageSize = Constants.PAGE_SIZE_DEFAULT; 
		String reportCategory = id;
		logger.debug("Loding the reports using Report category : queryAjax");
		try {
			model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
		} catch (Exception exp) {
			logger.error("Error while loading reportCategory types ",exp);
			exp.printStackTrace();
		}
		//List<NicFlexiReport> reportList=nicReportCodeService.getStatisticalRepors(reportCategory);
		/*phúc edit 30/5/2019*/
		//pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		PaginatedResult<NicFlexiReport> result = nicReportCodeService.getStatisticalRepors1(reportCategory, pageNo, pageSize);
		//logger.debug("Exit loding the reports using Report category : queryAjax, reportList.size: {} " , reportList.size());
		//List<NicFlexiReport> list = result.getRows();
		//phúc edit
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(result.getTotal(), pageSize));
		model.addAttribute("startIndex", result.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", result.getTotal());
		model.addAttribute("endIndex", firstResults + result.getRowSize());
		model.addAttribute("jobList", result.getRows());
		model.addAttribute("idReport", id);
		//end
		modelView.addObject("nicReportForm", nicReportForm);
		return modelView;
	}
	
	@RequestMapping(value="/definition",method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView definition(Model model) throws Exception {  	
		logger.debug("Loding the reportCategory types : definition()");		
		NicReportForm nicReportForm =new NicReportForm();		
		try {
			model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
		} catch (Exception exp) {
			logger.error("Error while loading reportCategory types ",exp);
			exp.printStackTrace();
		}
		ModelAndView modelView =new ModelAndView ("report.definition","nicReportForm",nicReportForm);
		//model.addAttribute("jobList", new ArrayList<NicReportForm>());
		//phúc edit		
		model.addAttribute("pageSize", 10);
		model.addAttribute("pageNo", 1);
		model.addAttribute("totalPage", 1);
		model.addAttribute("startIndex", 0);
		model.addAttribute("totalRecord", 0);
		model.addAttribute("endIndex", 0);
		model.addAttribute("jobList", new ArrayList<NicReportForm>());
		model.addAttribute("idReport", "id");
		//end	
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		modelView.addObject("nicReportForm", nicReportForm);
		logger.debug("Exit loding the reportCategory types : definition()");
		
		return modelView;
	}
	@RequestMapping(value="/cancelFrdToReqPage",method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView reqFrdToReportLoad(@ModelAttribute("nicReportForm") NicReportForm nicReportForm,Model model) throws Exception{  	
		logger.debug("Loding the reportCategory types : definition()");	
		try {
			model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
		} catch (Exception exp) {
			logger.error("Error while loading reportCategory types",exp);
			exp.printStackTrace();
		}
        ModelAndView modelView =new ModelAndView ("report.definition","nicReportForm",nicReportForm);
		
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		modelView.addObject("nicReportForm", nicReportForm);
		logger.debug("Exit loding the reportCategory types : definition()");
		return modelView;
	}
	
	@RequestMapping(value="/backToList",method = RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView backToList(@ModelAttribute("nicReportForm") NicReportForm nicReportForm,Model model) throws Exception{  	
		logger.debug("Loding the reportCategory types : backToList()");		
		try {
			model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
			nicReportForm.setCategoryLoad("Y");
		} catch (Exception exp) {
			logger.error("Error while loading reportCategory types",exp);
			exp.printStackTrace();
		}
        ModelAndView modelView =new ModelAndView ("report.definition","nicReportForm",nicReportForm);
		
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		modelView.addObject("nicReportForm", nicReportForm);
		logger.debug("Exit loding the reportCategory types : backToList()");
		return modelView;
	}
	/**
	 * 
	 * @param reportForm
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST) 
	@ExceptionHandler
	public String save(@ModelAttribute("nicReportForm")NicReportForm reportForm, BindingResult result, WebRequest webReq,HttpServletRequest request) 
		throws Exception {		
		logger.debug(" ");
		Report reportDBO = new Report();
		String status = "";
		String statuss = "";
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		try{
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
			
			reportDBO.setReportId(reportForm.getReportId());
			reportDBO.setReportName(reportForm.getReportName());
			reportDBO.setDescription(reportForm.getDescription());
			reportDBO.setReportPriority(Integer.valueOf(reportForm.getReportPriority()));
			reportDBO.setReportCategory(reportForm.getReportCategory());
			if(reportForm.getHtmlFormat()!=null){
				reportDBO.setHtmlFormat(reportForm.getHtmlFormat());
			}if(reportForm.getServerFlag()!=null){
				reportDBO.setServerFlag(reportForm.getServerFlag());
			}			
			reportDBO.setCreateBy(userId);
			reportDBO.setCreateDate(DateUtil.getSystemDate());
			reportDBO.setCreateWkstnId(wkstnId);
		    reportDBO.setDeleteFlag(Constants.NO);
		    
			Set<ReportParameter>  reportParameterSet = new HashSet<ReportParameter>();
			String[] paramNameArray = reportForm.getReportParameterName().split("&&&");
			String[] paramDescArray = reportForm.getReportParameterDesc().split("&&&");
			String[] paramTypeArray = reportForm.getReportParameterType().split("&&&");
			String[] paramPtyArray = reportForm.getReportParameterPriority().split("&&&");
			String[] paramMtyArray = reportForm.getReportParameterMandatory().split("&&&");
			String[] paramAliasArray = reportForm.getReportParameterAlias().split("&&&");
//			String[] paramDeleteArray = nicReportForm.getDeleteCollection().split("&&&");
			for (int i=0; i<paramMtyArray.length; i++){
				logger.debug("paramNameArray "+paramNameArray[i]+"paramDescArray "+paramDescArray[i]+"paramTypeArray "+paramTypeArray[i]+
						"paramPtyArray "+paramPtyArray[i]+"paramMtyArray "+paramMtyArray[i]+"paramAliasArray "+paramAliasArray[i]);
				if (StringUtils.isNotBlank(paramMtyArray[i])){
					ReportParameter reportParameterDBO = new ReportParameter();
					ReportParameterId reportParameterId =new ReportParameterId();
					reportParameterId.setReport(reportDBO);
					reportParameterId.setParaName(paramNameArray[i].trim());
					reportParameterDBO.setId(reportParameterId);					
					reportParameterDBO.setParameterName(paramNameArray[i].trim());
					reportParameterDBO.setParamType(paramTypeArray[i].trim());
					reportParameterDBO.setDescription(paramDescArray[i].trim());
					if (StringUtils.isNotBlank(paramPtyArray[i].trim())){
						reportParameterDBO.setPriority(Integer.parseInt(paramPtyArray[i].trim()));
					}
					reportParameterDBO.setParameterAlias(paramAliasArray[i].trim());
					if (paramMtyArray[i].trim().equalsIgnoreCase(Constants.YES)){
						reportParameterDBO.setIsMandatory(Constants.YES);
					} else {
						reportParameterDBO.setIsMandatory(Constants.NO);
					}
					reportParameterDBO.setCreatedBy(userId);
					reportParameterDBO.setCreateDate(DateUtil.getSystemTimestamp());
					reportParameterDBO.setCreateWkstnId(wkstnId);
					reportParameterDBO.setDeleteFlag(Constants.NO);
					reportParameterSet.add(reportParameterDBO);
				} 
			}
		   
		    if (reportParameterSet != null &&  reportParameterSet.size() > 0){
				logger.debug("before save "+reportParameterSet.size());
				reportDBO.setReportParameters(reportParameterSet);
			}
		    if(reportDBO!=null){
				 status =nicReportCodeService.saveReportDefinition(reportDBO);
				 statuss="forward:"+status;
			}
			logger.debug("{}", statuss);
		}catch(Exception exp){	
			logger.error(" ");
			exp.printStackTrace();
		}
		logger.info("=====>> Exit Save Method  <<=====");
		return statuss;
	}
	/**
	 * 
	 * @param reportForm
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@ExceptionHandler
	public String updateReport(@ModelAttribute("nicReportForm")NicReportForm reportForm, BindingResult result, WebRequest webReq,HttpServletRequest request) 
		throws Exception {		
		logger.debug(" Updating the report definition details : updateReport ");
		Report reportDBO = new Report();
		String status = "";
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		
		try{
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
			
			List<Report> reportList = nicReportCodeService.getReportDetailById(reportForm.getReportId());
			reportDBO = reportList.get(0);
			reportDBO.setReportId(reportForm.getReportId());
			reportDBO.setReportName(reportForm.getReportName());
			reportDBO.setDescription(reportForm.getDescription());
			reportDBO.setReportPriority(Integer.valueOf(reportForm.getReportPriority()));
			reportDBO.setReportCategory(reportForm.getReportCategory());
			if (reportForm.getHtmlFormat() != null) {
				reportDBO.setHtmlFormat(reportForm.getHtmlFormat());
			}
			if (reportForm.getServerFlag() != null) {
				reportDBO.setServerFlag(reportForm.getServerFlag());
			}
				
			reportDBO.setUpdateBy(userId);
			reportDBO.setUpdateDate(DateUtil.getSystemDate());
			reportDBO.setUpdateWkstnId(wkstnId);
			
			String[] paramNameArray = reportForm.getReportParameterName().split("&&&");
			String[] paramDescArray = reportForm.getReportParameterDesc().split("&&&");
			String[] paramTypeArray = reportForm.getReportParameterType().split("&&&");
			String[] paramPtyArray = reportForm.getReportParameterPriority().split("&&&");
			String[] paramMtyArray = reportForm.getReportParameterMandatory().split("&&&");
			String[] paramAliasArray = reportForm.getReportParameterAlias().split("&&&");
			String[] paramDeleteArray = reportForm.getDeleteCollection().split("&&&");
			
			logger.debug("[size] paramNameArray ("+paramNameArray.length+"), paramDescArray ("+paramDescArray.length+"), paramTypeArray ("+paramTypeArray.length+"), paramPtyArray ("+paramPtyArray.length+"), paramMtyArray ("+paramMtyArray.length+"), paramAliasArray ("+paramAliasArray.length+").");
			List<ReportParameter> paramList = nicReportCodeService.getParamList(reportForm.getReportId());
			
			for (int i=0; i<paramMtyArray.length; i++){
				logger.debug("["+i+"] paramNameArray ("+paramNameArray[i]+"), paramDescArray ("+paramDescArray[i]+"), paramTypeArray ("+paramTypeArray[i]+
						"), paramPtyArray ("+paramPtyArray[i]+"), paramMtyArray ("+paramMtyArray[i]+"), paramAliasArray ("+paramAliasArray[i] +").");
				if (StringUtils.isNotBlank(paramMtyArray[i])){
					String s="";
					
					for(ReportParameter reportParameterDBO : paramList){
						if(reportParameterDBO.getId().getParaName().equals(paramNameArray[i].trim()) && paramDeleteArray[i].trim().equals("N")){																								
							reportParameterDBO.setParameterName(paramNameArray[i].trim());
							reportParameterDBO.setReportId(reportForm.getReportId());
							reportParameterDBO.setParamType(paramTypeArray[i].trim());
							reportParameterDBO.setDescription(paramDescArray[i].trim());
							reportParameterDBO.setUpdateBy(userId);
							reportParameterDBO.setUpdateDate(DateUtil.getSystemTimestamp());
							reportParameterDBO.setUpdateWkstnId(wkstnId);
							reportParameterDBO.setDeleteFlag(Constants.NO);
							reportParameterDBO.setParameterAlias(paramAliasArray[i].trim());
							if (paramMtyArray[i].trim().equalsIgnoreCase(Constants.YES)){
								reportParameterDBO.setIsMandatory(Constants.YES);
							} else {
								reportParameterDBO.setIsMandatory(Constants.NO);
							}
							
							if (StringUtils.isNotBlank(paramPtyArray[i])){
								reportParameterDBO.setPriority(Integer.parseInt(paramPtyArray[i].trim()));
							}
							nicReportCodeService.saveParameter(reportParameterDBO);
							break;
						}else if(reportParameterDBO.getId().getParaName().equals(paramNameArray[i].trim())&&paramDeleteArray[i].trim().equals("Y")){																								
															
							reportParameterDBO.setDeleteBy(userId);
							reportParameterDBO.setDeleteDate(DateUtil.getSystemTimestamp());
							reportParameterDBO.setDeleteWkstnId(wkstnId);
							reportParameterDBO.setDeleteFlag(Constants.YES);
							//nicReportCodeService.saveParameter(reportParameterDBO);
							nicReportCodeService.deleteParameter(reportParameterDBO);
							break;
						}
					}
					if(paramDeleteArray[i].trim().equals("New")){
						Report rep= new Report();
						rep.setReportId(reportForm.getReportId());
						
						ReportParameterId reportParameterId =new ReportParameterId();	
						reportParameterId.setParaName(paramNameArray[i].trim());
						reportParameterId.setReport(rep);
						
						ReportParameter reportParameterDBO = new ReportParameter();
						reportParameterDBO.setReportId(reportForm.getReportId());							
						reportParameterDBO.setId(reportParameterId);
						
						reportParameterDBO.setParameterName(paramNameArray[i].trim());
						reportParameterDBO.setParamType(paramTypeArray[i].trim());
						reportParameterDBO.setDescription(paramDescArray[i].trim());
						
						if (StringUtils.isNotEmpty(paramPtyArray[i])){
							reportParameterDBO.setPriority(Integer.parseInt(paramPtyArray[i].trim()));
						}
						reportParameterDBO.setParameterAlias(paramAliasArray[i].trim());
						if (paramMtyArray[i].trim().equalsIgnoreCase(Constants.YES)){
							reportParameterDBO.setIsMandatory(Constants.YES);
						} else {
							reportParameterDBO.setIsMandatory(Constants.NO);
						}

						reportParameterDBO.setCreatedBy(userId);
						reportParameterDBO.setCreateDate(DateUtil.getSystemTimestamp());
						reportParameterDBO.setCreateWkstnId(wkstnId);
						reportParameterDBO.setDeleteFlag(Constants.NO);
						nicReportCodeService.saveParameter(reportParameterDBO);
						//break; //[2017] no need to break the main loop
					}
				} 
			}							   
			    
			if (reportDBO != null) {
				//it is required clean up deleted parameters before update
				reportDBO.setReportParameters(null);
				status = nicReportCodeService.updateReportDefinition(reportDBO);
			}
			status="success";  
			   
		} catch (Exception exp) {
			status = "fail";
			logger.error("[updateReport] ", exp);
		}   
		
		logger.debug(" Exit from  Updating the report definition details : updateReport ");
		return status;
	}
	
	@RequestMapping(value = "/success")
	@ResponseBody
	@ExceptionHandler
	public String success(WebRequest request) throws Exception{
		
		return "success";
	}
	
	@RequestMapping(value = "/fail")
	@ResponseBody
	@ExceptionHandler
	public String fail(WebRequest request) throws Exception {
		return "fail";
	}
	
	@RequestMapping(value = "/exist")
	@ResponseBody
	@ExceptionHandler
	public String exist(WebRequest request) throws Exception {
		return "exist";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)	
	@ExceptionHandler
	public ModelAndView edit(@ModelAttribute("nicReportForm") NicReportForm reportForm, BindingResult result,Model model)throws Exception {
		logger.debug(" Edit the report definition details : edit  ");
		
		Report reportDBO = new Report();
		List<Report> list=nicReportCodeService.getReportDetailById(reportForm.getReportId());
		NicReportForm reportFormResponse =new NicReportForm();		
		reportDBO=list.get(0);		
		reportFormResponse.setReportId( reportDBO.getReportId());
		reportFormResponse.setReportName(reportDBO.getReportName());
		reportFormResponse.setDescription(reportDBO.getDescription());
		reportFormResponse.setReportPriority(String.valueOf(reportDBO.getReportPriority()));
		reportFormResponse.setReportCategory(reportDBO.getReportCategory());		
		reportFormResponse.setHtmlFormat(reportDBO.getHtmlFormat());
		reportFormResponse.setServerFlag( reportDBO.getServerFlag());
		//Set<ReportParameter> reportParameters = new HashSet<ReportParameter>(0);
		if(reportDBO.getReportParameters()!= null){
			reportFormResponse.setParameterList(reportDBO.getReportParameters());		      
		}
		
		model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
		
		reportFormResponse.setMode("edit");
		ModelAndView modelView =new ModelAndView ("report.defPage","nicReportForm", reportFormResponse);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		modelView.addObject("nicReportForm", reportFormResponse);
		logger.debug(" Exit from edit  report definition details : edit  ");
		return  modelView;
	}
	
	/**
	 * This method is soft delete for the report ID
	 * @param id String reportId
	 * @return status String 
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteRpt", method = RequestMethod.GET)	
	@ExceptionHandler
	public ModelAndView delete(@ModelAttribute("nicReportForm") NicReportForm reportForm, BindingResult result,HttpServletRequest request,Model model)throws Exception {
		
		logger.debug(" delete the report definition details : delete  ");
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		Report reportDBO = new Report();
		java.net.InetAddress localMachine = InetAddress.getLocalHost();
		reportDBO.setReportId(reportForm.getReportId());
		reportDBO.setDeleteWkstnId(localMachine.getHostName());
		reportDBO.setDeleteBy(userSession.getFirstName());
		reportDBO.setDeleteFlag(Constants.YES);
		reportDBO.setDeleteDate(DateUtil.getSystemDate());
		String status=nicReportCodeService.deleteReport(reportDBO);	
		logger.debug(" delete report definition : {}  ", status);
		try {
			model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
			//nicReportForm.setCategoryLoad("Y");
		} catch (Exception exp) {
			logger.error("Error while loading reportCategory types",exp);
		}
		ModelAndView modelView =new ModelAndView ("report.definition", "nicReportForm", reportForm);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		modelView.addObject("nicReportForm", reportForm);
		logger.debug(" Exit from delete  report definition details : delete  ");
			
		return modelView;
	}
	
	
	@RequestMapping(value="/upload" )
	@ExceptionHandler
	public ModelAndView upload(Model model) throws Exception {
		NicReportForm nicReportForm =new NicReportForm();
		try {
			model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
		} catch (Exception exp) {
			logger.error("Error while loading reportCategory types", exp);
		}
		logger.debug(" Exit from init upload report templates page.");
		return new ModelAndView ("report.upload","nicReportForm",nicReportForm);//@PathVariable String details
	}
	
	@RequestMapping(value="/forwardReq" )
	@ExceptionHandler
	public ModelAndView uploadFiles(@ModelAttribute("nicReportForm") NicReportForm reportForm) throws Exception { 
		
		
		NicReportForm reportFormResponse =new NicReportForm();		
		try {			
			reportFormResponse.setReportCategory(reportForm.getReportCategory());
			CodeValues codeValue = codesService.getCodeValueByIdName(Codes.REPORT_CATEGORY, reportForm.getReportCategory());
			
			if(codeValue!=null){
				reportFormResponse.setReportCategoryDesc(codeValue.getCodeValueDesc());
			}else{
				reportFormResponse.setReportCategoryDesc(reportForm.getReportCategory());
			}
			
			reportFormResponse.setReportName(reportForm.getReportName());
			reportFormResponse.setReportId(reportForm.getReportId());
		} catch (Exception exp) {
			logger.error("Error while prepare forwardReq ", exp);
		}
		
		return new ModelAndView ("report.upload","nicReportForm",reportFormResponse);
	}
	
	@RequestMapping(value="/queryUpdate" )
	@ExceptionHandler
	public ModelAndView displayCustomizedReportInfo(
			@ModelAttribute("nicReportForm") NicReportForm reportForm)
			throws Exception {

		NicReportForm reportFormResponse = new NicReportForm();
		try {
			reportFormResponse.setReportCategory(reportForm.getReportCategory());
			CodeValues codeValue = codesService.getCodeValueByIdName(Codes.REPORT_CATEGORY, reportForm.getReportCategory());

			if (codeValue != null) {
				reportFormResponse.setReportCategoryDesc(codeValue.getCodeValueDesc());
			} else {
				reportFormResponse.setReportCategoryDesc(reportForm.getReportCategory());
			}

			reportFormResponse.setReportName(reportForm.getReportName());
			reportFormResponse.setReportId(reportForm.getReportId());

			ReportTemplate reportTemplateDBO = nicReportCodeService.getReportTemplateById(reportForm.getReportId(), reportForm.getReportName());

			if (reportTemplateDBO != null) {
				reportFormResponse.setDescription(reportTemplateDBO.getQuery());
			}
		} catch (Exception exp) {
			logger.error("Error while prepare queryUpdate", exp);
		}

		return new ModelAndView("report.customizedRpt.update", "nicReportForm", reportFormResponse);
	}
	
	
	
	@RequestMapping(value = "/reportName", method = RequestMethod.POST)
	@ResponseBody
	@ExceptionHandler
	public String getReportName(String categoryName)throws Exception {
		logger.debug("=====>> Inside the getReportName method  <<====="+categoryName);
		List<Report> list = nicReportCodeService.getReportNameByCategory(categoryName);		

		Map<Object, Object> editData = new HashMap<Object, Object>();
		for (Report nicReport : list){			
			editData.put(nicReport.getReportId(), nicReport.getReportName());
		}

		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(editData);
		} catch (Throwable t) {
			logger.error("Error while convert to json", t);
		}
		logger.debug("=====>> Exit of the getReportName method  <<====="+categoryName);
		return jsonStr;
	}
	
	@RequestMapping(value = "/uploadFile/{id}", method = RequestMethod.POST)	
	@ExceptionHandler
    public ModelAndView uploadFile(@ModelAttribute("nicReportForm") NicReportForm reportForm,@PathVariable String id,	BindingResult result,HttpServletRequest request) throws Exception  {
		logger.debug("Enter In to uploadFile method  ");

		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession"); 
		String userId = "";
		String wkstnId = "";
		if (userSession!=null) {
        	userId = userSession.getUserId();
        	wkstnId = userSession.getWorkstationId();
        	if (StringUtils.isEmpty(wkstnId)) {
        		java.net.InetAddress localMachine = InetAddress.getLocalHost();
        		wkstnId = localMachine.getHostName();
        	}
		}
		
		NicReportForm nicReportForm =new NicReportForm();
		InputStream inputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    CommonsMultipartFile files = reportForm.getFileData();
	    String fileName = files.getOriginalFilename();
		String reportCategory = reportForm.getReportCategory();
		String reportName = id;
		String reportId = reportForm.getReportId();
		if(reportId.contains(",")){
			reportId=reportId.replaceFirst(",", "").trim();
		}
		String query = reportForm.getDescription();
		ReportTemplate reportTemplateDBO = new ReportTemplate();
		ReportTemplateId reportTempleteId =new ReportTemplateId();
		reportTempleteId.setReportId(reportId);
		reportTempleteId.setTemplateFileName(reportName);

		reportTemplateDBO.setId(reportTempleteId);
        try{
        	logger.debug("before reading file: {}", fileName);
        	inputStream= files.getInputStream();      
        	int readBytes = 0;
        	byte[] buffer = new byte[10000];
        	while ((readBytes = inputStream.read(buffer)) != -1) {
        		outputStream.write(buffer, 0, readBytes);
        		buffer=outputStream.toByteArray();
        	}
        	logger.debug("read file to stream: {}, size: {} ", fileName, outputStream.size());
        	
	        ReportTemplate reportTemplateObj = nicReportCodeService.getReportTemplateById(reportId, reportName);
	        if(reportTemplateObj!=null){
	        	reportTemplateDBO = reportTemplateObj;
	        	reportTemplateDBO.setUpdateWkstnId(wkstnId);
	            reportTemplateDBO.setUpdateBy(userId);
	            reportTemplateDBO.setUpdateDate(DateUtil.getSystemTimestamp());
	        } else {
	        	reportTemplateDBO.setCreateWkstnId(wkstnId);
	        	reportTemplateDBO.setCreateBy(userId);
	        	reportTemplateDBO.setCreateDate(DateUtil.getSystemTimestamp());
	        }
			reportTemplateDBO.setQuery(query);
			reportTemplateDBO.setMainReportFlg(Constants.YES);       
	        reportTemplateDBO.setDeleteFlag(Constants.NO);
	        reportTemplateDBO.setTemplateImage(outputStream.toByteArray());
	        
	        logger.debug("before saving report template: {}", reportTemplateDBO.getId().getTemplateFileName());
	        String status = nicReportCodeService.uploadFile(reportTemplateDBO);
	       
			nicReportForm.setReportCategory(reportCategory);
			nicReportForm.setReportName(reportName);
			nicReportForm.setReportId(reportId);
			nicReportForm.setDescription("");
			nicReportForm.setStatus(status);
	        
		} catch(Exception exp){
			logger.error("Exception While Uploading The Jrxml Report Templete ::: " ,exp);
		} finally {
			outputStream.close();
		}
        logger.debug("Exit from the uploadFile method ");
    	return new ModelAndView("report.upload","nicReportForm",nicReportForm);
    }
	
	
	@RequestMapping(value = "/submitQuery/{id}", method = RequestMethod.POST)	
	@ExceptionHandler
    public ModelAndView submitQuery(@ModelAttribute("nicReportForm")NicReportForm reportForm,@PathVariable String id,	BindingResult result,HttpServletRequest request) throws Exception  {
		logger.debug("Enter In to submitQuery  method ");

		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession"); 
		String userId = "";
		String wkstnId = "";
		if (userSession!=null) {
        	userId = userSession.getUserId();
        	wkstnId = userSession.getWorkstationId();
        	if (StringUtils.isEmpty(wkstnId)) {
        		java.net.InetAddress localMachine = InetAddress.getLocalHost();
        		wkstnId = localMachine.getHostName();
        	}
		}
		
		NicReportForm nicReportForm = new NicReportForm();

		String reportCategory = reportForm.getReportCategory();
		String reportName = id;
		String reportId = reportForm.getReportId();
		if(reportId.contains(",")){
			reportId=reportId.replaceFirst(",", "").trim();
		}
		String query=reportForm.getDescription();
		ReportTemplate reportTemplateDBO = new ReportTemplate();
		ReportTemplateId reportTempleteId =new ReportTemplateId();
		reportTempleteId.setReportId(reportId);
		reportTempleteId.setTemplateFileName(reportName);

		reportTemplateDBO.setId(reportTempleteId);

		try {
			ReportTemplate reportTemplateObj = nicReportCodeService.getReportTemplateById(reportId, reportName);
			if (reportTemplateObj != null) {
				reportTemplateDBO = reportTemplateObj;
				reportTemplateDBO.setUpdateWkstnId(wkstnId);
				reportTemplateDBO.setUpdateBy(userId);
				reportTemplateDBO.setUpdateDate(DateUtil.getSystemTimestamp());
			} else {
				reportTemplateDBO.setCreateWkstnId(wkstnId);
				reportTemplateDBO.setCreateBy(userId);
				reportTemplateDBO.setCreateDate(DateUtil.getSystemTimestamp());
			}
			reportTemplateDBO.setQuery(query);
			reportTemplateDBO.setMainReportFlg(Constants.YES);       
	        reportTemplateDBO.setDeleteFlag(Constants.NO);
	        
	        logger.debug("before saving report template: {}, query: {}", reportTemplateDBO.getId().getTemplateFileName(), query);
			String status = nicReportCodeService.uploadFile(reportTemplateDBO);
			
			nicReportForm.setReportCategory(reportCategory);
			nicReportForm.setReportName(reportName);
			nicReportForm.setReportId(reportId);
			nicReportForm.setDescription("");
			nicReportForm.setStatus(status);
		} catch (Exception exp) {
			logger.error("Exception While Uploading The Report Templete Query ::: ", exp);
		}
		 logger.debug("Exit from the submitQuery method ");
    	return new ModelAndView("report.upload","nicReportForm", nicReportForm);
    }
	

	@RequestMapping(value = "/ajax_query/{reportParam}")
	@ResponseBody
	@ExceptionHandler
	public PaginatedResult<NicFlexiReport> ajaxQuery(@PathVariable String reportParam,@ModelAttribute("nicReportForm")NicReportForm reportForm, WebRequest request) 
		throws Exception {
		int pageNo=1;
		int pageSize=1;
		String reportParams [] = reportParam.split(",");
		String reportId=reportParams[0];
		String reportName=reportParams[1];		
		List<NicFlexiReport> templateResults= nicReportCodeService.getReportTemplateDetails(pageNo, pageSize, reportId, reportName);		
		PaginatedResult<NicFlexiReport> results = new PaginatedResult<NicFlexiReport>(pageNo, pageSize, templateResults);
		logger.debug("Exit loding the reports using Report category : queryAjax " +templateResults.size());		
		return results;
	}
	
	@RequestMapping(value = "/deleteTemplate", method = RequestMethod.GET)
	@ResponseBody
	@ExceptionHandler
	public String deleteTemplate(HttpServletRequest request)throws Exception {
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String userId = "";
		String wkstnId = "";
		if (userSession!=null) {
        	userId = userSession.getUserId();
        	wkstnId = userSession.getWorkstationId();
        	if (StringUtils.isEmpty(wkstnId)) {
        		java.net.InetAddress localMachine = InetAddress.getLocalHost();
        		wkstnId = localMachine.getHostName();
        	}
		}
		
		logger.debug("=====>> Inside the deletetemplete  method  <<===== : {} ", id);
		
		ReportTemplate reportTemplate = new ReportTemplate();
		String repIdAndRepName[] = id.split(",");
		String reportId=repIdAndRepName[0];
		String templateFileName=repIdAndRepName[1];
		
		ReportTemplateId templateId = new ReportTemplateId();
		templateId.setReportId(reportId);
		templateId.setTemplateFileName(templateFileName);
		reportTemplate.setId(templateId);
		reportTemplate.setDeleteBy(userId);
		reportTemplate.setDeleteDate(DateUtil.getSystemTimestamp());
		reportTemplate.setDeleteWkstnId(wkstnId);
		String status = nicReportCodeService.deleteTemplate(reportTemplate);
		
		logger.debug("=====>> Exit of the deletre method  <<===== : {} ", id);
		return status;
	}
	
	//[2017] there is no method reference.
//	public String editDuplicate(String id) throws Exception {
//		
//		logger.debug(" Edit the report definition details : edit  ");
//		//String id1=request.getAttribute("ID", WebRequest.SCOPE_REQUEST);
//		//System.out.println("=====>> Inside the edit method  <<====="+id);
//		Report reportDBO = new Report();
//		List<Report> list= nicReportCodeService.getReportDetailById(id);		
//		reportDBO=list.get(0);
//		Map<String, Object> editData = new HashMap<String, Object>();
//	    editData.put("reportId", reportDBO.getReportId());
//		editData.put("reportName",reportDBO.getReportName());
//		editData.put("description", reportDBO.getDescription());
//		editData.put("reportCategory",reportDBO.getReportCategory());
//		editData.put("reportPriority",reportDBO.getReportPriority());
//		editData.put("htmlFormat", reportDBO.getHtmlFormat());
//		editData.put("serverFlag", reportDBO.getServerFlag());
//		
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonStr = null;
//		try {
//			jsonStr = mapper.writeValueAsString(editData);
//		} catch (Throwable t) {
//			logger.error("Error while convert to json", t);
//		}
//		
//		logger.debug(" Exit from edit  report definition details : edit  ");
//		return jsonStr;
//	}
	
	@RequestMapping(value="/viewByCategory/{category}" , method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView viewReportByCategory(@ModelAttribute("nicReportForm")NicReportForm reportForm,@PathVariable String category, BindingResult result,Model model) throws Exception {	
		
		logger.debug("Loding the reportCategory types : definition()");		
		
		NicReportForm reportFormResponse =new NicReportForm();
		reportFormResponse.setReportCategory(category);
		try {
			model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
		} catch (Exception exp) {
			logger.error("Error while loading reportCategory types",exp);
		}
		
		ModelAndView  modelView = new ModelAndView ("report.definition","nicReportForm", reportFormResponse);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		modelView.addObject("nicReportForm", reportFormResponse);
		logger.debug("Exit loding the reportCategory types : definition()");
		
		return modelView;
	}
	
	@RequestMapping(value="/cancelToReportLoad",method = RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView cancelToReportLoad(@ModelAttribute("nicReportForm") NicReportForm reportForm,Model model) throws Exception{  	
		logger.debug("Loding the reportCategory types : definition()");			
		try {
			model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
		} catch (Exception exp) {
			logger.error("Error while loading reportCategory types", exp);
			exp.printStackTrace();
		}
        ModelAndView modelView =new ModelAndView ("report.definition", "nicReportForm", reportForm);
		
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		modelView.addObject("nicReportForm", reportForm);
		logger.debug("Exit loding the reportCategory types : definition()");
		return modelView;
	}
	
	@RequestMapping(value = "/cancelBack", method = RequestMethod.POST)	
	@ExceptionHandler
	public ModelAndView back(@ModelAttribute("nicReportForm") NicReportForm reportForm, BindingResult result, Model model)throws Exception {
		logger.debug(" Edit the report definition details : edit  ");
		
		Report reportDBO = new Report();
		List<Report> list=nicReportCodeService.getReportDetailById(reportForm.getReportId());
		NicReportForm reportFormResponse =new NicReportForm();		
		reportDBO=list.get(0);
		
		reportFormResponse.setReportId(reportDBO.getReportId());
		reportFormResponse.setReportName(reportDBO.getReportName());
		reportFormResponse.setDescription(reportDBO.getDescription());
		reportFormResponse.setReportPriority(String.valueOf(reportDBO.getReportPriority()));
		reportFormResponse.setReportCategory(reportDBO.getReportCategory());		
		reportFormResponse.setHtmlFormat(reportDBO.getHtmlFormat());
		reportFormResponse.setServerFlag( reportDBO.getServerFlag());
		
		if(reportDBO.getReportParameters()!= null){
			reportFormResponse.setParameterList(reportDBO.getReportParameters());		      
		}

		model.addAttribute("reportCategoryList", getCodeValueList(Codes.REPORT_CATEGORY));
		
		reportFormResponse.setMode("edit");
		ModelAndView modelView = new ModelAndView ("report.defPage", "nicReportForm", reportFormResponse);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_REPORT_MGMT);
		modelView.addObject("nicReportForm", reportFormResponse);
		logger.debug(" Exit from edit  report definition details : edit  ");
		return  modelView;
	}
}
