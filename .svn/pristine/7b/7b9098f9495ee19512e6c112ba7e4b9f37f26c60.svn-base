/**
 * 
 */
package com.nec.asia.nic.framework.report.dao;
import java.util.List;

import com.nec.asia.nic.framework.dao.GenericDao;
import com.nec.asia.nic.framework.report.domain.ReportParameter;
import com.nec.asia.nic.framework.report.domain.ReportParameterId;

/**
 * @author prasad_karnati
 *
 */
public interface ReportParameterDao extends GenericDao<ReportParameter, ReportParameterId> {

	public List<ReportParameter> getReportParameters(String reportId, String paraName);
	public String saveReportParameter(ReportParameter parameter);	
	public String deleteReportParameter(ReportParameter parameter) throws Exception;
}
