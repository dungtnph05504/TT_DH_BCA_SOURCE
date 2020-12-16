package com.nec.asia.nic.comp.report.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.report.dao.AfisDataSyncDao;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/*
 * Modification History:
 * 28 Jun 2017 (chris): clean up logging
 */
@Repository("afisDataSyncDao")
public class AfisDataSyncDaoImpl extends GenericHibernateDao implements AfisDataSyncDao {

	@Override
	public List<Object[]> getSynchronizionDetails(String selectedMonth)
			throws Exception {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getSynchronizionDetails");
			query.setString("appMonthYear", selectedMonth);
			List<Object[]> results = query.list();
			return results;
		} catch (Exception ex) {
			logger.error("Error occurred while getting the syncronization details: Exception" + ex.getMessage(), ex);
			return null;
		} finally {
			session.close();
		}
	}

	@Override
	public List<Object[]> getSynchronizionDetailsByDate(String selectedDate) throws Exception {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getSynchronizionDetailsByDate");
			query.setString("appDate", selectedDate);
			List<Object[]> results = query.list();
			return results;

		} catch (Exception ex) {
			logger.error("Error occurred while getting the syncronization details for date:"+selectedDate+": Exception"+ ex.getMessage(), ex);
			return null;
		} finally {
			session.close();
		}
	}
	

	@Override
	public List<Object[]> getSynchronizionDetailsByMonth(String selectedMonth) throws Exception {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getSynchronizionDetailsByMonth");
			query.setString("appDate", selectedMonth);
			List<Object[]> results = query.list();
			return results;

		} catch (Exception ex) {
			logger.error("Error occurred while getting the syncronization details for month:"+selectedMonth+": Exception"+ ex.getMessage(), ex);
			return null;
		} finally {
			session.close();
		}
	}

	@Override
	public List<Object[]> getSyncRequestAndStatusDetails(String transId) throws Exception {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getSyncRequestAndStatusDet");
			query.setString("transId", transId);
			List<Object[]> results = query.list();
			return results;

		} catch (Exception ex) {
			logger.error("Error occurred while getting the syncronization request and status details for Transaction Id:"+transId+": Exception"+ ex.getMessage(), ex);
			return null;
		} finally {
			session.close();
		}
	}

	@Override
	public void resetDataSyncRequest(Long dataSyncId) throws Exception {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("resetDataSyncRequest");
			query.setLong("dataSyncId", dataSyncId);
			List<Object[]> results = query.list();

		} catch (Exception ex) {
			logger.error("Error occurred while reset the data sync request with Data Sync Id:"+dataSyncId+": Exception"+ ex.getMessage(), ex);
		} finally {
			session.close();
		}
	}

}
