/**
 * 
 */
package com.nec.asia.nic.framework.report.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;
import com.nec.asia.nic.framework.report.domain.NicReportMenuForm;


/**
 * @author Prasad_Karnati
 *
 */

public interface GenericReportManagementDao extends GenericDao<Object, Integer>{

	public JRDataSource getDataForPdf(String namedQuery) throws Exception ;
	public List getFlexiGridData(NicReportMenuForm form) throws Exception ;
	public Map<String,String> getColumnAndParameterList(String tableName) throws Exception ;
	public PaginatedResult<Object> getReportData(String query,int pageNo,int pageSize,String tableName) throws Exception ;
	public Connection getConnection() ;
	public List getDynaJasperReportData(String query) throws Exception;
	public void closeConnection(Connection connection) throws Exception;
}
