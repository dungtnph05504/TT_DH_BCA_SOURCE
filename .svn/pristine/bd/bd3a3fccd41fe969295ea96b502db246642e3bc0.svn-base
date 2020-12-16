package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicAfisDataDao;
import com.nec.asia.nic.comp.trans.domain.CardStockTrackingLog;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
 * @author JohnPaul_Millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Mar 4, 2014
 * <p></p>
 */

/* 
 * Modification History:
 *  
 * 22 Sep 2014 (chris): add method findReferenceAfisId
 */
@Repository("nicAfisDataDao")
public class NicAfisDataDaoImpl extends GenericHibernateDao<NicAfisData, String> 
	implements NicAfisDataDao{
	
	@SuppressWarnings("unchecked")
	public String findReferenceAfisId (String transactionId) {
		String afisRefId = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("baf.getRegistrationId");
			query.setString("transactionId", transactionId);
			List<Object> results = query.list();
			if (results!=null && results.size()>0 &&
					results.get(0)!=null && results.get(0) instanceof java.lang.Long) {
				afisRefId = results.get(0).toString();
			} else {
				logger.warn("[findReferenceAfisId] unexpected result returned for transactionId: {}, result: {}", transactionId, results);
			}
		} finally {
			session.close();
		}
		return afisRefId;
	}
	
	@Override
	public List<String> findOtherAfisHistoryByTransIdList (List<String> transactionIdList) throws DaoException {
		List<String> idList = null;
		try {
			if(transactionIdList.size()>1000) {
				throw new DaoException("Unsupported retrieval exceed 1000 records.");
			} 
			if(transactionIdList.size()>0) {
				String hql = "select transactionId from NicAfisData where afisKeyNo in (select afisKeyNo from NicAfisData where transactionId in (:ids)) and transactionId not in (:ids) ";
				List result = getHibernateTemplate().findByNamedParam(hql, "ids",transactionIdList);
				logger.debug("[findOtherAfisHistoryByTransIdList] result returned for transactionIdList: {}, result: {}", transactionIdList, result);
				idList = result;
			}
		} catch (Exception e) {
			throw new DaoException("Error in retrieval by transactionId List.", e);
		}
		logger.info("[findOtherAfisHistoryByTransIdList] result: {}", idList);
		return idList;
	}

	@Override
	public String getNextAfisKeyNo(String prefix) {
		String afisKeyNo = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("generate.afis.keynumber");
			query.setString("prefix", prefix);
			String results = (String) query.uniqueResult();
			if (results!=null) {
				afisKeyNo = results;
			} else {
				logger.warn("[getNextAfisKeyNo] unexpected result returned for prefix: {}, result: {}", prefix, results);
			}
		} finally {
			session.close();
		}
		return afisKeyNo;
	}
}
