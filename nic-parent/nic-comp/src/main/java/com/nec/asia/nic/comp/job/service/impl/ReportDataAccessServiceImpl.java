package com.nec.asia.nic.comp.job.service.impl;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.nec.asia.nic.comp.job.dao.ReportDataAccessDao;
import com.nec.asia.nic.comp.job.service.ReportDataAccessService;
import com.nec.asia.nic.framework.dao.DaoException;

public class ReportDataAccessServiceImpl implements ReportDataAccessService{

	
	/** The dao. */
	private ReportDataAccessDao dao=null;
	
	
	
	/* (non-Javadoc)
	 * @see com.nec.asia.idserver.comp.report.ReportDataAccessService#executeDBStatistics(java.lang.String, java.lang.String)
	 */
	public void executeDBStatistics(String userId, String terminalId) throws Exception {
		try {
			dao.populateDBStatisticsReport();
		} catch (DaoException e) {
			throw new Exception("Error executing '" + ReportDataAccessDao.POPULATE_NIC_FP_DATA + "'. "+e.getMessage(),e);
		}		
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.idserver.comp.report.ReportDataAccessService#executeCustomizedDBStatistics(java.lang.String, java.lang.String)
	 */
	public void executeCustomizedDBStatistics(String[] storedProcedureNames, String inputDate, String userId, String terminalId) throws Exception {
		try {
			dao.populateCustomizedDBStatisticsReport( storedProcedureNames, inputDate );
		} catch (DaoException e) {
			e.printStackTrace();
			throw new Exception("Error executing '" + ReflectionToStringBuilder.toString( storedProcedureNames ) + "'. "+e.getMessage(),e);
		}		
	}
	
	/**
	 * Sets the dao.
	 *
	 * @param dao the new dao
	 */
	public void setDao(ReportDataAccessDao dao) {
		this.dao = dao;
	}
	
}
