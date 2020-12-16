package com.nec.asia.nic.comp.job.dao;

import com.nec.asia.nic.framework.dao.DaoException;

public interface ReportDataAccessDao {
	
    /** The Constant POPULATE_NIC_FP_DATA. */
    public static final String POPULATE_NIC_FP_DATA = "NICDB.POPULATE_NIC_FP_DATA";
	
	/**
	 * Populate db statistics report.
	 *
	 * @throws DaoException the dao exception
	 */
	public void populateDBStatisticsReport() throws DaoException;

	/**
	 * Populate customized db statistics report.
	 *
	 * @throws DaoException the dao exception
	 */
	public void populateCustomizedDBStatisticsReport( String[] storedProcedureNames, String inputDate ) throws DaoException;

}
