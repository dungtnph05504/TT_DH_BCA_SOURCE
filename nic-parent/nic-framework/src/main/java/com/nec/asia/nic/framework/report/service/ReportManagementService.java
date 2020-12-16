/**
 * 
 */
package com.nec.asia.nic.framework.report.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.report.domain.NicReportMenuForm;
import com.nec.asia.nic.framework.report.domain.Report;
import com.nec.asia.nic.framework.report.domain.ReportParameter;
import com.nec.asia.nic.framework.report.domain.ReportTemplate;
import com.nec.asia.nic.framework.report.form.CodeValueForm;
import com.nec.asia.nic.framework.report.form.NicFlexiReport;
import com.nec.asia.nic.framework.service.BusinessService;
/**
 * @author Prasad_Karnati
 *
 */
public interface ReportManagementService  extends BusinessService <Report,String> {

	//public List getaAllReportCategoryCodes(String tablename) throws Exception;
	public List<NicFlexiReport> getStatisticalRepors(String categoryType) throws Exception;
	public PaginatedResult<NicFlexiReport> getStatisticalRepors1(String categoryType, int pageNo, int pageSize) throws Exception;
	
	public String  saveReportDefinition(Report report) throws Exception;
	
	public List<Report>  getReportDetailById(String reportId) throws Exception;
	
	public String  deleteReport(Report report) throws Exception;
	
	public List<Report> getReportNameByCategory(String reportCategory) throws Exception;	
	
	public String uploadFile(ReportTemplate reportTemplate) throws Exception;
	
	public ReportTemplate  getReportTemplateById(String reportId ,String ReportName)throws Exception;
	
	public String deleteTemplate(ReportTemplate templateObj)throws Exception;	
	
	public String generateReport(String type, String token, HttpServletResponse response,HashMap<String,String> props, CodeValueForm codeValueForm);
	
	public NicReportMenuForm getReportParameters(String reportId);
	
	public List getFlexiGridData(NicReportMenuForm menuForm) throws Exception;
	
	public String  updateReportDefinition(Report report) throws Exception;
	
	public List<NicFlexiReport>  getReportTemplateDetails(int pageNo, int pageSize,String reportId ,String reportName)throws Exception;
	public List<ReportParameter> getParamList(String reportId);
	public String saveParameter(ReportParameter parameter);
	public String deleteParameter(ReportParameter parameter);
	
	public PaginatedResult<Object> getReportData(String query,int pageNo,int pageSize,String tableName) throws Exception;
	public String generateStatisticsReport(Map<String, Object> parameterMap,String reportId,String reportName,HttpServletResponse response,String token)throws Exception;
	public Connection getConnection();	
	public void showReport(String reportId, String rptFileName, String reportPath,
			String reportFormat,Map<String, Object> parameterMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	public void showXlsReport(String reportId, String rptFileName, String reportName, Map<String, Object> parameterMap, List fpqThresholdInfoList,HttpServletResponse response) throws Exception;
	public void exportToXls(JasperPrint jp, HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception;
	public List getDynaJasperReportData(HashMap<String, Object> props,String reportId, String reportName) throws Exception;
	
	public void exportToPdf(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void exportToXls(JasperPrint jp, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public List getReportData(String reportId, String query) throws Exception;
	
	public void exportToCsv(JasperPrint jp, HttpServletRequest request, HttpServletResponse response) throws Exception;
	

}
