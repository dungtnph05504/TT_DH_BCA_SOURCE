/**
 * 
 */
package com.nec.asia.nic.framework.report.dao;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;
import com.nec.asia.nic.framework.report.domain.ReportTemplateId;
import com.nec.asia.nic.framework.report.domain.ReportTemplate;
import com.nec.asia.nic.framework.report.form.NicFlexiReport;

/**
 * @author Prasad_Karnati
 *
 */
public interface ReportTemplateDao  extends GenericDao<ReportTemplate, ReportTemplateId>{
	
	public String uploadFile(ReportTemplate reportTemplate) throws Exception;
	
	public ReportTemplate getReportTemplateById(String reportId, String reportName) throws Exception;
	
	public String deleteTemplate(ReportTemplate templateObj) throws Exception;
	
	public String deleteTemplates(String reportId, String deleteBy, Date deleteDate, String deleteWkstnId) throws Exception;
	
	public List<NicFlexiReport> getReportTemplateDetails(int pageNo, int pageSize, String reportId, String reportName) throws Exception;
	
	public List<ReportTemplate> getReportTemplateByReportId(String reportId) throws Exception;

}
