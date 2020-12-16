package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.trans.dao.NicTransactionReconRptDao;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReconRptId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 * 15 May 2014 (chris): add method getNicTransactionsByDate()
 */
@Repository("nicTransactionReconRptDao")
public class NicTransactionReconRptDaoImpl 
		extends GenericHibernateDao<NicTransactionReconRpt, NicTransactionReconRptId> 
		implements NicTransactionReconRptDao{
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Object> getNicTransactionReconRptData(String regSiteCode, String issSiteCode, String applicationDate, String collectionDate){	
		List<Object> reportData = null; 
		reportData = (List<Object>) this.getSession()
	        .getNamedQuery("reconRpt.getNicTransactionReconRptData")
	        .setParameter("regSiteCode", regSiteCode)
	        .setParameter("issSiteCode", issSiteCode)
	        .setParameter("applicationDate", applicationDate)
	        .setParameter("collectionDate", collectionDate)
	        .list();	
		return reportData;
	}
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@Override
	public List<NicTransactionReconRpt> findAllReconReport(String regSiteCode,
			String issSiteCode, String applicationDate, String collectionDate) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(regSiteCode)) {
			detachedCriteria.add(Restrictions.eq("id.regSiteCode", regSiteCode));
		}
		if (StringUtils.isNotBlank(issSiteCode)) {
			detachedCriteria.add(Restrictions.eq("id.issSiteCode", issSiteCode));
		}
		if (StringUtils.isNotBlank(applicationDate)) {
			detachedCriteria.add(Restrictions.eq("id.applicationDate", applicationDate));
		}
		if (StringUtils.isNotBlank(collectionDate)) {
			detachedCriteria.add(Restrictions.eq("id.collectionDate", collectionDate));
		}		
		List<NicTransactionReconRpt> resultList = this.findAll(detachedCriteria);
		return resultList;
	}
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Object> getNicTransactionsByDate(String inputDate){	
		List<Object> reportData = null; 
		reportData = (List<Object>) this.getSession()
	        .getNamedQuery("reconRpt.getNicTransactionsByDate")
	        .setParameter("inputDate", inputDate)
	        .list();	
		return reportData;
	}
}
