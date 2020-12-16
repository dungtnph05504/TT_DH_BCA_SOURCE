package com.nec.asia.nic.framework.report.dao;

import java.util.List;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;
import com.nec.asia.nic.framework.report.domain.Report;


/**
 * @author prasad_karnati
 *
 */

public interface ReportDao extends GenericDao<Report, String> {
	
	public List<Report> getReportsByCategory(String categoryType) throws Exception;
	public PaginatedResult<Report> getReportsByCategory1(String categoryType, int pageNo, int pageSize) throws Exception;
	public Report  findReportById(String reportId) throws Exception;
	public List<Report>  getReportDetailById(String reportId) throws Exception;
	
	public String saveReportDefinition(Report nicReport) throws Exception;
	public String updateReportDefinition(Report nicReport) throws Exception;
	public String deleteReport(Report nicReport) throws Exception;

}
