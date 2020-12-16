/**
 * 
 */
package com.nec.asia.nic.framework.report.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.CodeValuesDao;
import com.nec.asia.nic.framework.admin.code.dao.CodesDao;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.type.ParameterType;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.report.dao.GenericReportDao;
import com.nec.asia.nic.framework.report.dao.GenericReportManagementDao;
import com.nec.asia.nic.framework.report.dao.ReportDao;
import com.nec.asia.nic.framework.report.dao.ReportParameterDao;
import com.nec.asia.nic.framework.report.dao.ReportTemplateDao;
import com.nec.asia.nic.framework.report.domain.NicReportMenuForm;
import com.nec.asia.nic.framework.report.domain.Report;
import com.nec.asia.nic.framework.report.domain.NicReportMenu;
import com.nec.asia.nic.framework.report.domain.ReportParameter;
import com.nec.asia.nic.framework.report.domain.ReportTemplate;
import com.nec.asia.nic.framework.report.domain.NicReportCodeTable;
import com.nec.asia.nic.framework.report.form.CodeValueForm;
import com.nec.asia.nic.framework.report.form.NicFlexiReport;
import com.nec.asia.nic.framework.report.service.ReportManagementService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.ExporterService;
import com.nec.asia.nic.utils.MapUtil;
import com.nec.asia.nic.utils.QueryUtil;
import com.nec.asia.nic.utils.TokenService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * @author Prasad_Karnati
 *
 */
@Service("reportManagementService")
public class ReportManagementServiceImpl 
		extends	DefaultBusinessServiceImpl<Report, String, ReportDao> 
		implements ReportManagementService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportManagementServiceImpl.class);
	
	/** The Constant PDF. */
	private static final String PDF = "pdf";

	/** The Constant XLS. */
	private static final String XLS = "xls";

	/** The Constant CSV. */
	private static final String CSV = "csv";

	public ReportManagementServiceImpl() {
	}

	@Autowired
	public ReportManagementServiceImpl(ReportDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	private ReportParameterDao reportParameterDao;
	
	@Autowired
	private CodesDao codeDao;
	
	@Autowired
	private CodeValuesDao codeValuesDao;
	
	@Autowired
	private ReportTemplateDao reportTemplateDao;	
	
	@Autowired
	private ExporterService exporter;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private GenericReportManagementDao genericReportManagementDao;
	
	@Autowired
	private GenericReportDao eppReportDao;	


	public List getaAllReportCategoryCodes(String tablename) throws Exception {
		List<NicReportCodeTable> entityList=new ArrayList<NicReportCodeTable>();
		
		Codes code = codeDao.findById(tablename);
		Set<com.nec.asia.nic.framework.admin.code.domain.CodeValues> codeValuesList= code.getCodeValues();
		for(com.nec.asia.nic.framework.admin.code.domain.CodeValues codeValue:codeValuesList){
			NicReportCodeTable categoryList=new NicReportCodeTable();
			categoryList.setReportCategory(codeValue.getCodeValueDesc());
			entityList.add(categoryList);
			
			logger.info( "==============================="+codeValue.getCodeValueDesc());
		}
		
		return entityList;
	}
	/* 
	 * @param 
	 */
	@SuppressWarnings("unchecked")
	public List<NicFlexiReport> getStatisticalRepors(String categoryType) throws Exception {

		List<Report> entityList = this.dao.getReportsByCategory(categoryType);

		List<NicFlexiReport> flexiList = new ArrayList<NicFlexiReport>();

		for (Report report : entityList) {
			NicFlexiReport flexiReport = new NicFlexiReport();
			if (!report.getReportId().equals("NIC_RIC_RPT_001")
					&& !report.getReportId().equals("NIC_RIC_RPT_002")
					&& !report.getReportId().equals("NIC_RIC_RPT_003")
					&& !report.getReportId().equals("NIC_RIC_RPT_004")
					&& !report.getReportId().equals("NIC_RIC_RPT_005")
					&& !report.getReportId().equals("NIC_RIC_RPT_006")
					&& !report.getReportId().equals("NIC_RIC_RPT_007")
					&& !report.getReportId().equals("NIC_RIC_RPT_008")
					&& !report.getReportId().equals("NIC_RIC_RPT_009")
					&& !report.getReportId().equals("NIC_RIC_RPT_010")
					&& !report.getReportId().equals("NIC_RIC_RPT_011")
					&& !report.getReportId().equals("NIC_RIC_RPT_012")
					&& !report.getReportId().equals("NIC_RIC_RPT_013")
					&& !report.getReportId().equals("NIC_RIC_RPT_014")
					&& !report.getReportId().equals("NIC_RIC_RPT_015")
					&& !report.getReportId().equals("NIC_RIC_RPT_016")) {
				flexiReport.setReportId(report.getReportId());
				flexiReport.setReportName(report.getReportName());
				flexiReport.setDescription(report.getDescription());
				flexiReport.setReportPriority(String.valueOf(report.getReportPriority()));
				flexiReport.setHtmlFormat(report.getHtmlFormat());
				flexiList.add(flexiReport);
			}
		}

		return flexiList;
	}
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public String  saveReportDefinition(Report report) throws Exception{
		 String status="";
		List <Report> entity = getReportDetailById(report.getReportId());
		if(entity!=null&&entity.size()>0){
			if(entity.get(0).getReportId().equals(report.getReportId())){
				status ="exist";
			}
		}else{	
			status = this.dao.saveReportDefinition(report);
		}
	
		return status;
	}
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public String  updateReportDefinition(Report report) throws Exception{
		String status =	this.dao.updateReportDefinition(report);
		return status;
	}

	public List<Report> getReportDetailById(String reportId) throws Exception{
		List<Report> list = this.dao.getReportDetailById(reportId);
		return list;
	}

	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public String  deleteReport(Report report) throws Exception{		
		String status = "";
		if (report==null)
			throw new Exception("Report Object cannot be null");
		status = this.reportTemplateDao.deleteTemplates(report.getReportId(), report.getDeleteBy(), report.getDeleteDate(), report.getDeleteWkstnId());
		logger.trace("[deleteTemplates] by reportId({}) : {} ", report.getReportId(), status);
		status = this.dao.deleteReport(report);
		logger.trace("[deleteReport] by reportId({}) : {} ", report.getReportId(), status);
		return status;
	}

	public List<Report> getReportNameByCategory(String reportCategory) throws Exception{
		List<Report> entity = this.dao.getReportsByCategory(reportCategory);
		return entity;
	}

	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public String uploadFile(ReportTemplate reportTemplateDBO) throws Exception{
		String status="";
		try{
			status=	reportTemplateDao.uploadFile(reportTemplateDBO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;	
	}

	public ReportTemplate getReportTemplateById(String reportId, String reportTemplateFileName)throws Exception{
		ReportTemplate entity = reportTemplateDao.getReportTemplateById(reportId, reportTemplateFileName);
		return entity;
	}
	
	public List<NicFlexiReport> getReportTemplateDetails(int pageNo, int pageSize,String reportId ,String reportName)throws Exception{
		
		List<NicFlexiReport> result = reportTemplateDao.getReportTemplateDetails(pageNo, pageSize, reportId, reportName);
		
		return result;
	}
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public String deleteTemplate(ReportTemplate templateObj) throws Exception {
		String status =	reportTemplateDao.deleteTemplate(templateObj);
		return status;
	}
	
	@SuppressWarnings("unchecked")
	public String generateReport(String type, String token, HttpServletResponse response, HashMap<String,String> mapParams, CodeValueForm codeValueForm)  {						
			
		logger.debug("Preparing the named query for the Report >>>>>>>>>> "+mapParams.get("reportFileName"));	      
		HashMap<String, Object> params = new HashMap<String, Object>();
		String status="";
		String reportId = "";
		String reportFileName = "";
		String tableObjectName = "";
		String reportName="";
		if (mapParams.containsKey("reportId")) {
			reportId = mapParams.get("reportId");

		}
		if (mapParams.containsKey("reportFileName")) {
			reportFileName = mapParams.get("reportFileName");
			reportName=mapParams.get("reportFileName");
			mapParams.remove("reportFileName");

		}
		if (mapParams.containsKey("objName")) {
			//codeValueForm.setObjName(mapParams.get("objName"));
			tableObjectName = mapParams.get("objName");
		}
		
		 mapParams.remove("objName");
		
		for (Map.Entry<String, String> emap : mapParams.entrySet()) {
			params.put(emap.getKey(), emap.getValue());

		}
		
		String startDateVal="";
		String endDateVal="";
		String startDate="";
		String endDate="";
		
		if(mapParams!=null){			
			if(mapParams.containsKey("startDate")){
				startDateVal=mapParams.get("startDate");
				String arry [];
				if(startDateVal.contains(" ")){
					arry =startDateVal.split(" ");
					startDateVal=arry[0];	
				}
				
			}
			if(mapParams.containsKey("endDate")){
				endDateVal=mapParams.get("endDate");
				String arry [];
				if(endDateVal.contains(" ")){
				 arry =endDateVal.split(" ");
				endDateVal=arry[0];
				}
			}			
		 }
		
		if(startDateVal!=null||endDateVal!=null){
			if(!startDateVal.equals("")||!endDateVal.equals("")){
			List<ReportParameter> reportParamList=getParamList(reportId);
			for(ReportParameter param :reportParamList){
				
				if(param.getParamType().toUpperCase().equals("DATE:START_DATE")){
					startDate=param.getParameterAlias();
				}
				if(param.getParamType().toUpperCase().equals("DATE:END_DATE")){
					endDate=param.getParameterAlias();
				}
				
				
			}
			}
		}
		mapParams.remove("reportId");
		mapParams.remove("startDate");
		mapParams.remove("endDate");
		mapParams.remove("tableName");
		if(mapParams.containsKey("loginUser")){
			mapParams.remove("loginUser");
		}
		if(mapParams.containsKey("frequency")){
			reportFileName=reportFileName+"_"+mapParams.get("frequency");
			mapParams.remove("frequency");
			
		}		
		String x="";
		int i=1;
		String  namedQuery ="FROM  "+tableObjectName+" T WHERE ";
		String  queryCheck="FROM  "+tableObjectName+" T WHERE ";
		
		HashMap<String,String> propsMap=mapParams;
		HashMap<String,String> propsMap1=new HashMap<String, String>();
		
		/*if(propsMap.isEmpty()){
			namedQuery = namedQuery.replace("WHERE", "");
			namedQuery = namedQuery.replace("WHERE", "");
		}*/
		
      for (Map.Entry<String,String> emap : propsMap.entrySet()) {
			
			
  	    String key = emap.getKey();
  	    String value =emap.getValue();
  	    if(value!=null && !value.equals("All")&&!value.equals("---SELECT---")&&!value.equals("")&&!value.equals(" ")){
  	    	//mapParams.remove(key);
  	    	propsMap1.put(key, value);
  	    }/*else if(value.equals("---SELECT---")){
  	    	mapParams.remove(key);
  	    }else if(value.equals("")){
  	    	mapParams.remove(key);
  	    }else if(value.equals(" ")){
  	    	mapParams.remove(key);
  	    }else{
  	    	 propsMap1.put(key, value);
  	    }*/
  	 
        }
		
		for (Map.Entry<String,String> emap : propsMap1.entrySet()) {
			
    	    String key = emap.getKey();
    	    String value =emap.getValue();    	    
    	 
			if (!value.isEmpty()&&!value.equals("All")&&!value.equals("---SELECT---")) {
				/*if (mapParams.size() == i) {
					x = "T." + key + "=" + "'" + value + "'";
				} else if (mapParams.size() > 1) {
					x = "T." + key + "=" + "'" + value + "'" + " AND ";
				} else {
					x = "T." + key + "=" + "'" + value + "'";
				}*/
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
    	    ++i;
    	}
		if(startDateVal!=null&&endDateVal!=null&&!startDate.equals("")&&!endDateVal.equals("")){
			if(startDate.equals(endDate)){
				if(namedQuery.equals(queryCheck)){
					namedQuery =namedQuery+" "+startDate+ "  BETWEEN  to_date("+"'"+startDateVal+" 00:00','dd/mm/yyyy HH24:MI')"+" AND "+"to_date('"+endDateVal+" 23:59','dd/mm/yyyy HH24:MI')";	
				}else{
					namedQuery =namedQuery+" AND "+startDate+ "  BETWEEN  to_date("+"'"+startDateVal+" 00:00','dd/mm/yyyy HH24:MI')"+" AND "+"to_date('"+endDateVal+" 23:59','dd/mm/yyyy HH24:MI')";
				}
							
			}
		}else if(startDateVal!=null&&!startDateVal.equals("")){
			
			if(startDate!=null){
				if(namedQuery.equals(queryCheck)){
					namedQuery =namedQuery+"  to_char("+startDate+ ",'dd/mm/yyyy')='"+startDateVal+"'";	
				}else{
					namedQuery =namedQuery+"  AND to_char("+startDate+ ",'dd/mm/yyyy')='"+startDateVal+"'";	
				}
			}
		}else if(endDateVal!=null&&!endDateVal.equals("")){			
			if(endDate!=null){
				if(namedQuery.equals(queryCheck)){
					namedQuery =namedQuery+"  to_char("+endDate+ ",'dd/mm/yyyy')='"+endDateVal+"'";	
				}else{
					namedQuery =namedQuery+" AND to_char("+endDate+ ",'dd/mm/yyyy')='"+endDateVal+"'";	
				}
			}
		}
		logger.debug(" Named Query for the  Report : "+namedQuery);
		
		try {
			// 2. Retrieve template			
			ReportTemplate list = reportTemplateDao.getReportTemplateById(reportId, reportName);
			InputStream reportStream = new ByteArrayInputStream(list.getTemplateImage());
			// 3. Convert template to JasperDesign
			logger.debug(" Loading the Jasper Report tmplate file   ");
			JasperDesign jd = JRXmlLoader.load(reportStream);
			logger.debug(" Compailing  the jrxml file :");
			JasperReport jr = JasperCompileManager.compileReport(jd);
			logger.debug(" jrxml Compailation completed ");
			
			// 5. Create the JasperPrint object
			// Make sure to pass the JasperReport, report parameters, and data
			logger.debug(" Loading the data using named query ");
			JasperPrint jp = JasperFillManager.fillReport(jr, params,genericReportManagementDao.getDataForPdf(namedQuery));
			// 6. Create an output byte stream where data will be written
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 7. Export report
			exporter.export(type, jp, response, baos,reportFileName);
			// 8. Write to reponse stream
			
			write(token, response, baos);
			logger.debug(" Writing data in pdf completed");
			status="Success";
		}
		
		/*
		 * catch (JRException jre) { throw new RuntimeException(jre); }
		 */
		catch (Exception exp) {
			status="fail";
			logger.error(" Exception while generating the Pdf >>>>>>>>>>>>>>>>>>> ",exp);
		}
		return status;	
				
	}
	
		
	/**
	* Writes the report to the output stream
	*/
	private void write(String token, HttpServletResponse response,
			ByteArrayOutputStream baos) {
		 
		try {	
			// Retrieve output stream
			ServletOutputStream outputStream = response.getOutputStream();
			// Write to output stream
			baos.writeTo(outputStream);
			// Flush the stream
			outputStream.flush();
			
			// Remove download token
			tokenService.remove(token);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public NicReportMenuForm getReportParameters(String reportId){
		NicReportMenuForm nicReportMenuForm = new NicReportMenuForm();
		
		String reportParams [];
		String repName="";
		String repId;
		String tableName="";
		ArrayList<String> queryColumnList =null;
		if(reportId.contains(",")){
			reportParams=reportId.split(",");
			repId=reportParams[0];
			repName=reportParams[1];
		}else{
			repId=reportId;
		}
	
		List<ReportParameter> reportParamList = reportParameterDao.getReportParameters(repId, "");
		List<NicReportMenu> menuList= new ArrayList<NicReportMenu>();
		
		try {
			ReportTemplate entities =reportTemplateDao.getReportTemplateById(repId, repName);
			
			if((reportParamList==null||reportParamList.isEmpty()) && entities!=null  &&StringUtils.isBlank(entities.getQuery()) ){
				String reportFileName =entities.getId().getTemplateFileName();
				String repoId =entities.getId().getReportId();
				nicReportMenuForm.setReportFileName(reportFileName);
				nicReportMenuForm.setReportId(repoId);
				nicReportMenuForm.setReportType("staticrep");
				return nicReportMenuForm;
			}else {				
				for (ReportParameter paramList : reportParamList) {
					if (paramList.getParamType().startsWith("ENTITY:")) {
						String namePropertyName=null;
						String valuePropertyName=null;
						String hideAllOption=null;
						
						StringTokenizer token = new StringTokenizer(paramList.getParamType(), ":");
						token.nextToken();
						String entityClassName = token.nextToken();				
						if (token.hasMoreTokens()) {
							namePropertyName = token.nextToken();
						} else {
							continue;
						}
						if (token.hasMoreTokens()) {
							valuePropertyName = token.nextToken();
						} else {
							continue;
						}
						
						Map<String,String> list = new HashMap<String, String>();						
						if (token.hasMoreTokens()){
							hideAllOption=token.nextToken();
						}
						if(!(StringUtils.isNotBlank(hideAllOption) && hideAllOption.equalsIgnoreCase("HAO"))){
							list.put("null", "All");
						}
						
						Class entityClass = Class.forName(entityClassName);
						List entityBeans = eppReportDao.getAllEntity(entityClass, valuePropertyName);
						for (int i = 0; i < entityBeans.size(); i++) {
							try {
								String name=BeanUtils.getProperty(entityClass.cast(entityBeans.get(i)), namePropertyName);
								String value=BeanUtils.getProperty(entityClass.cast(entityBeans.get(i)), valuePropertyName);
								list.put(value, name);
							}  catch (Exception e) {
								continue;
							}
						}	
						
						NicReportMenu menuitems = new NicReportMenu();						
						menuitems.setPropertyType("multipleValue");
						menuitems.setPropertyName(paramList.getId().getParaName());
						menuitems.setPropId(paramList.getParameterAlias());
						menuitems.setMandatory(paramList.getIsMandatory());
						menuitems.setMultiMenu(list);					
						menuList.add(menuitems);
						nicReportMenuForm.setNicReportMenuList(menuList);
					}else if (paramList.getParamType().contains("CT:")) {
						 NicReportMenu menuitems = new NicReportMenu();
						 String array []=paramList.getParamType()
								 .split(":");
						 String paramType="";
						 if(array[1]!=null&& !array[1].equals("")){
							 paramType =array[1].trim();
						 }									
						
						menuitems.setPropertyType("multipleValue");
						menuitems.setPropertyName(paramList.getId().getParaName());
						menuitems.setPropId(paramList.getParameterAlias());

						Map<String,String> list = this.codeDao.getCodeValuesByCodeID(paramType);
						if (MapUtils.isNotEmpty(list)) {
							list = MapUtil.sortByValue(list);
						}
						menuitems.setMandatory(paramList.getIsMandatory());
						menuitems.setMultiMenu(list);					
						menuList.add(menuitems);
						nicReportMenuForm.setNicReportMenuList(menuList);
						
					}
					//[20160505 - Tue] Generate range number for Delayed report(SDD9, SDD10)
					else if (paramList.getParamType().contains("NR:")) {
						 NicReportMenu menuitems = new NicReportMenu();
						 String[] array = paramList.getParamType().split(":");
						 String paramType="";
						 if(array[1] != null&& !array[1].equals("")){
							 paramType = array[1].trim();
						 }									
						
						menuitems.setPropertyType("number_range");
						menuitems.setPropertyName(paramList.getId().getParaName());
						menuitems.setPropId(paramList.getParameterAlias());
						
						Map<String,String> list = new HashMap<String,String>();
						String[] numRanges = paramType.split("-");
						int numStart = 1;
						int numEnd = 6;
						if(numRanges.length > 1) {
							numStart = Integer.parseInt(numRanges[0]);
							numEnd = Integer.parseInt(numRanges[1]);
						}
						for(int num = numStart; num <= numEnd; num ++) {
							list.put(num+"", "+"+num);
						}
						if (MapUtils.isNotEmpty(list)) {
							list = MapUtil.sortByValue(list);
						}
						menuitems.setMandatory(paramList.getIsMandatory());
						menuitems.setMultiMenu(list);					
						menuList.add(menuitems);
						nicReportMenuForm.setNicReportMenuList(menuList);
					} // End
					else {
						NicReportMenu menuitems = new NicReportMenu();					
						menuitems.setPropertyName(paramList.getId().getParaName());
						if(paramList.getParamType().toUpperCase().equals("DATE:START_DATE")){
						  menuitems.setPropId("startDate");
						  menuitems.setPropertyType("date");
						  menuitems.setMandatory(paramList.getIsMandatory());
						}else if(paramList.getParamType().toUpperCase().equals("DATE:END_DATE") ){
						  menuitems.setPropId("endDate");
						  menuitems.setPropertyType("date");
						  menuitems.setMandatory(paramList.getIsMandatory());
						 }else{
							menuitems.setPropId(paramList.getParameterAlias());
							menuitems.setPropertyType(paramList.getParamType().toLowerCase());
							menuitems.setMandatory(paramList.getIsMandatory());
						}
						menuList.add(menuitems);
						nicReportMenuForm.setNicReportMenuList(menuList);
					} 	
				}
			}
	
			if(entities != null && StringUtils.isNotBlank(entities.getQuery()) && (entities.getQuery().contains("SELECT")||entities.getQuery().contains("FROM"))){
				
				String queryString =entities.getQuery();
				String reportFileName =entities.getId().getTemplateFileName();
				
				QueryUtil queryUtil =new QueryUtil();
				Map<String,List<String>> clmMap = queryUtil.getColumnNames(queryString);
				
				for (Map.Entry<String, List<String>> emap : clmMap.entrySet()) {
					tableName = emap.getKey();
					queryColumnList =(ArrayList<String>) emap.getValue();
		    	   
		    	    
		    	}		
			
				Map <String,String> columnParamList =genericReportManagementDao.getColumnAndParameterList(tableName);
				if(columnParamList.containsKey("sessionId")){
					columnParamList.remove("sessionId");
				}
		        if(columnParamList.containsKey("regOffId")){
		        	columnParamList.remove("regOffId");
				}
				nicReportMenuForm.setReportId(repId);
				nicReportMenuForm.setReportFileName(reportFileName);
				nicReportMenuForm.setTableName(columnParamList.get("tableName"));
				nicReportMenuForm.setObjName(columnParamList.get("objName"));		
				columnParamList.remove("tableName");
				columnParamList.remove("objName");
				Map <String,String> queryColumnMap = new HashMap<String, String>();
				
				if(queryColumnList!=null){
						for (Map.Entry<String, String> emap : columnParamList
								.entrySet()) {
								String key=emap.getKey();
								String value=emap.getValue().trim();
							for (String clName : queryColumnList) {
								
								if (clName.trim().equalsIgnoreCase(value)) {
		
									queryColumnMap.put(key, value);
									//columnParamList.remove(emap.getKey());
		
								}
		
							}
		
						}
				}
				for (Map.Entry<String, String> emap : queryColumnMap.entrySet()) {
					String key = emap.getKey();
					String value = emap.getValue().trim();
					if (columnParamList.containsKey(key)) {
						columnParamList.remove(emap.getKey());
					}
				}
				nicReportMenuForm.setColumnParamList(columnParamList);
				nicReportMenuForm.setQueryColumnList(queryColumnMap);
				nicReportMenuForm.setReportFileName(repName);
			} else {
				String reportFileName =entities.getId().getTemplateFileName();
				String repoId =entities.getId().getReportId();
				nicReportMenuForm.setReportFileName(reportFileName);
				nicReportMenuForm.setReportId(repoId);
				nicReportMenuForm.setReportType("static-param");
				return nicReportMenuForm;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}	
   
		return nicReportMenuForm;
	}
	
	public List getFlexiGridData(NicReportMenuForm form) throws Exception{
		
		return genericReportManagementDao.getFlexiGridData(form);
	}
	
	public List<ReportParameter> getParamList(String reportId){
		List<ReportParameter> reportParamList = reportParameterDao.getReportParameters(reportId, "");
		
		return reportParamList;
	}
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public String saveParameter(ReportParameter parameter){
		String status="";
		try{
			reportParameterDao.saveReportParameter(parameter);
			status = "success";
		}catch(Exception exp){
			status = "failed";	
		}
		return status;
	}
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public String deleteParameter(ReportParameter parameter){
		String status="";
		try{
			status = reportParameterDao.deleteReportParameter(parameter);
		} catch(Exception exp){
			status = "failed";	
		}
		return status;
	}

	public PaginatedResult<Object> getReportData(String query, int pageNo, int pageSize, String tableName) throws Exception {

		return genericReportManagementDao.getReportData(query, pageNo, pageSize, tableName);
	}

	public String generateStatisticsReport(Map<String, Object> parameterMap, String reportId, String reportName, HttpServletResponse response, String token) throws Exception {
		String status = getPdfReport(parameterMap, reportId, reportName, response, token);

		return status;
	}

	private   String getPdfReport(Map<String, Object> parameterMap,String reportId,String reportName,HttpServletResponse response,String reportFormate) throws Exception {
		
		JasperPrint jp;
		Connection conn = null;
		String status="";
		byte[] baos=null;
		//JasperPrint print; 
		try {
			ReportTemplate list = reportTemplateDao.getReportTemplateById(reportId, reportName);
			InputStream reportStream = new ByteArrayInputStream(list.getTemplateImage());
			
			conn = genericReportManagementDao.getConnection();
			JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);		
		   //JasperReportsUtils.renderAsXls(report, parameters, reportData, stream);
			//print = JasperFillManager.fillReport(jasperReport, parameterMap,conn);		
			/*JasperReport jasperReport = JasperCompileManager.compileReport(reportStream); */		
			jp = JasperFillManager.fillReport(jasperReport, parameterMap,conn); 
			ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
			// 7. Export report
			exporter.export(reportFormate, jp, response, baos1,reportName);
			// 8. Write to reponse stream	
			String token="";
			write(token, response, baos1);
			status="success";		
		} catch (JRException e) {
			status= "fail";
			e.printStackTrace();
			
		}finally {
			try {
				if ( conn != null && conn.isClosed( ) == false ) {
					conn.close( );
					conn = null;
				}
			} catch( SQLException e ) {
				status= "fail";
				e.printStackTrace( );
			}
		} 
		return status;
	}



	public Connection getConnection(){
		Connection conn = null;
		conn=genericReportManagementDao.getConnection();
		return conn;
		
	}

	public void showReport(String reportId, String rptFileName, String reportPath,	String reportFormat, Map<String, Object> parameterMap,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		JasperPrint jasperPrint = null;
		logger.debug("Inside the show report");
		try {
			jasperPrint = (JasperPrint) previewReportTemplate(reportId, reportPath, parameterMap, reportFormat);

			if (PDF.equalsIgnoreCase(reportFormat)) {
				exportToPdf(jasperPrint, request, response);
			} else if (XLS.equalsIgnoreCase(reportFormat)) {
				String fileName = jasperPrint.getName();

				if (StringUtils.isNotBlank(rptFileName)) {
					fileName = fileName.concat("_").concat(rptFileName);
				}
				exportToXls(jasperPrint, request, response, fileName);
			} else if (CSV.equalsIgnoreCase(reportFormat)) {

				String fileName = jasperPrint.getName();

				if (StringUtils.isNotBlank(rptFileName)) {
					fileName = fileName.concat("_").concat(rptFileName);
				}
				exportToCsv(jasperPrint, request, response, fileName);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	
	/**
	 * Export to xls.
	 *
	 * @param jasperPrint the jasper print
	 * @param request the request
	 * @param response the response
	 * @throws ReportServiceException the report service exception
	 */
	public void exportToXls(JasperPrint jasperPrint,
			HttpServletRequest requerst, HttpServletResponse response, String fileName)
			throws Exception {
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		try {
			exporter.exportReport();
		} catch (JRException e) {
			throw new Exception(e);
		}

		byte[] bytes = baos.toByteArray();
		if (bytes != null && bytes.length > 0) {
			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xls\"");
			response.setContentLength(bytes.length);

			ServletOutputStream ouputStream = null;
			try {
				ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
			} catch (IOException e) {
				throw new Exception(e);
			} finally {
				if (ouputStream != null) {
					try {
						ouputStream.close();
					} catch (IOException e) {
						throw new Exception(e);
					}
				}
			}
		}
	}
	
	/**
	 * Export to CSV.
	 *
	 * @param jasperPrint the jasper print
	 * @param request the request
	 * @param response the response
	 * @throws ReportServiceException the report service exception
	 */
	public void exportToCsv(JasperPrint jasperPrint,
			HttpServletRequest requerst, HttpServletResponse response, String fileName)
			throws Exception {
		JRCsvExporter exporterCSV = new JRCsvExporter();
		exporterCSV.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporterCSV.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		
		exporterCSV.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporterCSV.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterCSV.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);

		try {
			exporterCSV.exportReport();
		} catch (JRException e) {
			throw new Exception(e);
		}

		byte[] bytes = baos.toByteArray();
		if (bytes != null && bytes.length > 0) {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".csv\"");
			response.setContentLength(bytes.length);

			ServletOutputStream ouputStream = null;
			try {
				ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
			} catch (IOException e) {
				throw new Exception(e);
			} finally {
				if (ouputStream != null) {
					try {
						ouputStream.close();
					} catch (IOException e) {
						throw new Exception(e);
					}
				}
			}
		}
	}

	/**
	 * Export to pdf.
	 *
	 * @param jasperPrint the jasper print
	 * @param request the request
	 * @param response the response
	 * @throws ReportServiceException the report service exception
	 */
	public void exportToPdf(JasperPrint jasperPrint,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		try {
			exporter.exportReport();
		} catch (JRException e) {
			throw new Exception(e);
		}

		byte[] bytes = baos.toByteArray();
		if (bytes != null && bytes.length > 0) {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + jasperPrint.getName() + ".pdf\"");
			response.setContentLength(bytes.length);

			ServletOutputStream ouputStream = null;
			try {
				ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
			} catch (IOException e) {
				throw new Exception(e);
			} finally {
				if (ouputStream != null) {
					try {
						ouputStream.close();
					} catch (IOException e) {
						throw new Exception(e);
					}
				}
			}
		}
	}
	
	public Object previewReportTemplate(String reportId, String reportPath,	Map<String, Object> parameterMap, String reportFormat) throws Exception {
		try {			
			List<ReportTemplate> reportTemplates = reportTemplateDao.getReportTemplateByReportId(reportId);
			if (reportTemplates == null || reportTemplates.size()==0) {
				throw new Exception("Report template does not exist with  report id: " + reportId);
			}
			
			ReportTemplate mainReportTemplate = null;
			JasperReport mainJasperReport = null;
			HashMap<String, JasperReport> subReportTemplateMap = new HashMap<String, JasperReport>();
			for(ReportTemplate reportTemplate : reportTemplates) {
				if (reportTemplate == null) {
					throw new Exception("Report template does not exist with  report id: " + reportId+", FileName: "+reportTemplate.getFileName());
				}
				if(reportTemplate.getTemplateImage()==null) {
					throw new Exception("Compiled report template does not exist for report id: " + reportId+", FileName: "+reportTemplate.getFileName());
				}
				JasperReport jasperReport = loadJRObject(reportPath, reportTemplate);
				
				if("Y".equalsIgnoreCase(reportTemplate.getMainReportFlg())) {
					mainReportTemplate = reportTemplate;
					mainJasperReport=jasperReport;
				}
				else {
					String reportTemplateName = getReportTemplateNameWithoutFileExtension(reportTemplate.getId().getTemplateFileName());
					subReportTemplateMap.put(reportTemplateName, jasperReport);
				}
			}
			return fillReport(mainJasperReport, mainReportTemplate, reportTemplateDao, parameterMap, reportPath, reportFormat,  subReportTemplateMap);
		} 
		catch (Exception e) {
			e.printStackTrace( );
			return null;
		}
	}
	
	/**
	 * Fill report.
	 *
	 * @param report the report
	 * @param template the template
	 * @param templateDao the template dao
	 * @param paramList the param list
	 * @param reportPath the report path
	 * @param reportFormat the report format
	 * @param locale the locale
	 * @param subReportTemplateMap the sub report template map
	 * @return the jasper print
	 * @throws JRException the jR exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ReportTemplateException the report template exception
	 * @throws DaoException the dao exception
	 */
	private JasperPrint fillReport(JasperReport report, ReportTemplate template, ReportTemplateDao templateDao,Map<String, Object> parameters, String reportPath,
			String reportFormat, HashMap<String, JasperReport> subReportTemplateMap) throws JRException, IOException, Exception {
		JasperPrint jasperPrint = null;
		Connection conn = null;
		try {
//		JasperReportTemplateHelper helper = new JasperReportTemplateHelper();

		String sql = template.getQuery();
		conn = genericReportManagementDao.getConnection();
		if(subReportTemplateMap!=null && subReportTemplateMap.size()>0) {
			for (String  subReportTemplateName: subReportTemplateMap.keySet()) {
				JasperReport jasperSubReport = subReportTemplateMap.get(subReportTemplateName);
				parameters.put(subReportTemplateName, jasperSubReport);
			}
		}

		// Ignore pagination for XLS
		if (PDF.equalsIgnoreCase(reportFormat)) {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.FALSE);
		} else if (XLS.equalsIgnoreCase(reportFormat) || CSV.equalsIgnoreCase(reportFormat)) {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
		}

//		if (sql != null) {
//			JRResultSetDataSource rs = helper.prepareReportResultSet(
//					connection, sql, parameters);
//			jasperPrint = JasperFillManager.fillReport(report, parameters, rs);
//		} else {
			jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
//		}
		} catch (JRException e) {
			e.printStackTrace();
			
		}finally {
			try {
				if ( conn != null && conn.isClosed( ) == false ) {
					conn.close( );
					conn = null;
				}
			} catch( SQLException e ) {
				e.printStackTrace( );
			}
		}
		return jasperPrint;
	}
	
	
	
	/**
	 * Gets the report template name without file extension.
	 *
	 * @param reportTemplateFileName the report template file name
	 * @return the report template name without file extension
	 */
	private String getReportTemplateNameWithoutFileExtension(String reportTemplateFileName) {
		int index = reportTemplateFileName.toUpperCase().lastIndexOf(".JRXML");
		if(index>0) {
			return reportTemplateFileName.substring(0, index);
		}
		else {
			return reportTemplateFileName;
		}
	}

	
	/**
	 * Load jr object.
	 *
	 * @param reportPath the report path
	 * @param template the template
	 * @return the jasper report
	 * @throws JRException the jR exception
	 */
	private JasperReport loadJRObject(String reportPath, ReportTemplate template) throws JRException {
//		ByteArrayInputStream baiStream = new ByteArrayInputStream(template.getTemplateImage());
//		JasperReport jasperReport =  (JasperReport) JRLoader.loadObject(baiStream);
//		return jasperReport;	
		
		InputStream reportStream = new ByteArrayInputStream(template.getTemplateImage());
		
//		Connection conn = genericReportManagementDao.getConnection();
		return JasperCompileManager.compileReport(reportStream); 

	}
	
	
	public void showXlsReport(String reportId, String rptFileName, String reportName, Map<String, Object> parameterMap, List fpqThresholdInfoList,HttpServletResponse response) throws Exception {
		try{
		ReportTemplate list = reportTemplateDao.getReportTemplateById(reportId, reportName);
		
		if (list == null) {
			throw new Exception("Report template does not exist with  report id: " + reportId);
		}
		
		InputStream reportStream = new ByteArrayInputStream(list.getTemplateImage());
		parameterMap.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
		String fileName = list.getId().getTemplateFileName();
		
		if(StringUtils.isNotBlank(rptFileName)){
			fileName = fileName.concat("_").concat(rptFileName);
		}
		JasperDesign jd = JRXmlLoader.load(reportStream);
		JasperReport jr = JasperCompileManager.compileReport(jd);
		JasperPrint jp = JasperFillManager.fillReport(jr, parameterMap,new JRBeanCollectionDataSource(fpqThresholdInfoList));
		 exportToXls(jp,null,response,fileName);
		}catch(Exception ex){
			logger.error("Error occurred while generating the Report:"+reportName+", Exception: "+ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public List getDynaJasperReportData(HashMap<String, Object> props, String reportId, String reportName) throws Exception {
		ReportTemplate dto = reportTemplateDao.getReportTemplateById(reportId, reportName);
		if (dto == null) {
			throw new Exception("Report template does not exist with  report id: " + reportId);
		}
		
		if (!StringUtils.isNotBlank(dto.getQuery())) {
			throw new Exception("Invalid query for customized report with  Report Id: " + reportId);
		}
		
		return genericReportManagementDao.getDynaJasperReportData(dto.getQuery());
	}
	
	@Override
	public List getReportData(String reportId, String query) throws Exception {
		if (!StringUtils.isNotBlank(query)) {
			throw new Exception("Invalid query for customized report with  Report Id: " + reportId);
		}
		
		return genericReportManagementDao.getDynaJasperReportData(query);
	}

	@Override
	public void exportToXls(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
				Boolean.FALSE);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		try {
			exporter.exportReport();
		} catch (JRException e) {
			throw new Exception(e);
		}

		byte[] bytes = baos.toByteArray();
		if (bytes != null && bytes.length > 0) {
			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + jasperPrint.getName() + ".xls\"");
			response.setContentLength(bytes.length);

			ServletOutputStream ouputStream = null;
			try {
				ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
			} catch (IOException e) {
				throw new Exception(e);
			} finally {
				if (ouputStream != null) {
					try {
						ouputStream.close();
					} catch (IOException e) {
						throw new Exception(e);
					}
				}
			}
		}
	}

	@Override
	public void exportToCsv(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JRCsvExporter exporterCSV = new JRCsvExporter();
		exporterCSV.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporterCSV.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		
		exporterCSV.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporterCSV.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterCSV.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);

		try {
			exporterCSV.exportReport();
		} catch (JRException e) {
			throw new Exception(e);
		}

		byte[] bytes = baos.toByteArray();
		if (bytes != null && bytes.length > 0) {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + jasperPrint.getName() + ".csv\"");
			response.setContentLength(bytes.length);

			ServletOutputStream ouputStream = null;
			try {
				ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
			} catch (IOException e) {
				throw new Exception(e);
			} finally {
				if (ouputStream != null) {
					try {
						ouputStream.close();
					} catch (IOException e) {
						throw new Exception(e);
					}
				}
			}
		}
		
	}

	@Override
	public PaginatedResult<NicFlexiReport> getStatisticalRepors1(
			String categoryType, int pageNo, int pageSize) throws Exception {
		PaginatedResult<Report> entityList = this.dao.getReportsByCategory1(categoryType, pageNo, pageSize);

		List<NicFlexiReport> flexiList = new ArrayList<NicFlexiReport>();

		for (Report report : entityList.getRows()) {
			NicFlexiReport flexiReport = new NicFlexiReport();
			if (!report.getReportId().equals("NIC_RIC_RPT_001")
					&& !report.getReportId().equals("NIC_RIC_RPT_002")
					&& !report.getReportId().equals("NIC_RIC_RPT_003")
					&& !report.getReportId().equals("NIC_RIC_RPT_004")
					&& !report.getReportId().equals("NIC_RIC_RPT_005")
					&& !report.getReportId().equals("NIC_RIC_RPT_006")
					&& !report.getReportId().equals("NIC_RIC_RPT_007")
					&& !report.getReportId().equals("NIC_RIC_RPT_008")
					&& !report.getReportId().equals("NIC_RIC_RPT_009")
					&& !report.getReportId().equals("NIC_RIC_RPT_010")
					&& !report.getReportId().equals("NIC_RIC_RPT_011")
					&& !report.getReportId().equals("NIC_RIC_RPT_012")
					&& !report.getReportId().equals("NIC_RIC_RPT_013")
					&& !report.getReportId().equals("NIC_RIC_RPT_014")
					&& !report.getReportId().equals("NIC_RIC_RPT_015")
					&& !report.getReportId().equals("NIC_RIC_RPT_016")) {
				flexiReport.setReportId(report.getReportId());
				flexiReport.setReportName(report.getReportName());
				flexiReport.setDescription(report.getDescription());
				flexiReport.setReportPriority(String.valueOf(report.getReportPriority()));
				flexiReport.setHtmlFormat(report.getHtmlFormat());
				flexiList.add(flexiReport);
			}
		}

		return new PaginatedResult<>(entityList.getTotal(), pageNo, flexiList);
	}
 
 
}
