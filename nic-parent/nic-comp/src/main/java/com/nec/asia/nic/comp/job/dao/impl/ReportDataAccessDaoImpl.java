package com.nec.asia.nic.comp.job.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.nec.asia.nic.comp.dispatch.domain.DispatchPackageDetail;
import com.nec.asia.nic.comp.job.dao.ReportDataAccessDao;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.hibernate.AbstractHibernateDao;


public class ReportDataAccessDaoImpl extends GenericHibernateDao<Object, Long> implements ReportDataAccessDao{

    /** The jdbc template. */
    private JdbcTemplate jdbcTemplate;

    /**
     * Sets the data source.
     *
     * @param dataSource the new data source
     */
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Gets the jdbc template.
     *
     * @return the jdbc template
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /* (non-Javadoc)
     * @see com.nec.asia.idserver.comp.report.dataAccess.ReportDataAccessDao#populateDBStatisticsReport()
     */
    public void populateDBStatisticsReport() throws DaoException {

        if (getJdbcTemplate() == null) {
            throw new IllegalStateException("JdbcTemplate has not been initialized.");
        }

        try {
            DBStatisticsStoredProcedure sp = new DBStatisticsStoredProcedure(getJdbcTemplate());
            sp.execute();
        } catch (DataAccessException dae) {
            throw new DaoException("Error encountered when executing stored procedure (" + POPULATE_NIC_FP_DATA + ").", dae);
        }

    }

    /* (non-Javadoc)
     * @see com.nec.asia.idserver.comp.report.dataAccess.ReportDataAccessDao#populateCustomizedDBStatisticsReport()
     */
    public void populateCustomizedDBStatisticsReport( String[] storedProcedureNames, String inputDate ) throws DaoException {

        if (getJdbcTemplate() == null) {
            throw new IllegalStateException("JdbcTemplate has not been initialized.");
        }

        for( String storedProcedureName : storedProcedureNames ){
	        try {
	            DBStatisticsStoredProcedure sp = new DBStatisticsStoredProcedure(getJdbcTemplate(), storedProcedureName);

	            Map<String, Object> inputMap = new HashMap<String, Object>(1);
	            inputMap.put("INPUT_DATE", inputDate );
	            sp.execute( inputMap );
	        } catch (DataAccessException dae) {
	        	dae.printStackTrace();
	            throw new DaoException("Error encountered when executing stored procedure (" + storedProcedureName + ").", dae);
	        }
        }

    }
    
    /**
     * The Class DBStatisticsStoredProcedure.
     */
    private class DBStatisticsStoredProcedure extends StoredProcedure {

        /**
         * Instantiates a new dB statistics stored procedure.
         *
         * @param jdbcTemplate the jdbc template
         */
        public DBStatisticsStoredProcedure(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, POPULATE_NIC_FP_DATA);
            compile();
        }

        /**
         * Instantiates a new dB statistics stored procedure.
         *
         * @param jdbcTemplate the jdbc template
         */
        public DBStatisticsStoredProcedure(JdbcTemplate jdbcTemplate, String storedProcedureName) {
            super(jdbcTemplate, storedProcedureName);
            declareParameter(new SqlParameter("INPUT_DATE", Types.VARCHAR));
            compile();
        }
        
        /**
         * Execute.
         *
         * @param inputMap the input map
         * @return the map
         */
        public Map execute( Map inputMap ) {
            return super.execute( inputMap );
        }

        /**
         * Execute.
         *
         * @return the map
         */
        public Map execute() {
            // this stored procedure has no input parameters, so an empty Map is supplied...
            return super.execute( new HashMap() );
        }
    }
 
	
	
	
}
