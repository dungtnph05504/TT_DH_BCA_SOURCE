package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.trans.dao.NicBlacklistDao;
import com.nec.asia.nic.comp.trans.dao.NicCardReconRptDao;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.NicCardReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicCardReconRptId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
@Repository("nicBlacklistDao")
public class NicBlacklistDaoImpl 
		extends GenericHibernateDao<EppBlacklist, Long> 
		implements NicBlacklistDao{
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@Override
	public List<EppBlacklist> findByNin(String nin) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(nin)) {
			detachedCriteria.add(Restrictions.eq("idNumber", nin));
		}
		List<EppBlacklist> resultList = this.findAll(detachedCriteria);
		return resultList;
	}
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@Override
	public List<EppBlacklist> findByInfoPerson(String fullname, String dob) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(fullname)) {
			detachedCriteria.add(Restrictions.eq("searchName", fullname));
		}
		
		if (StringUtils.isNotBlank(fullname)) {
			detachedCriteria.add(Restrictions.eq("dateOfBirth", dob));
		}
		List<EppBlacklist> resultList = this.findAll(detachedCriteria);
		return resultList;
	}
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@Override
	public List<EppBlacklist> findByMutilCriterion(String fullname, String dob, String nin) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
	
		Criterion searchName = Restrictions.eq("searchName", fullname);
		Criterion searchNameNull = Restrictions.isNotNull("searchName");
		
		Criterion searchDob = Restrictions.eq("dateOfBirth", dob);
		Criterion searchDobNotNull = Restrictions.isNotNull("dateOfBirth");
		
		Criterion searchNin = Restrictions.eq("idNumber", nin);
		Criterion searchNinNotNull = Restrictions.isNotNull("idNumber");
		
		detachedCriteria.add(Restrictions.or(
				Restrictions.and(searchName, searchNameNull), 
				Restrictions.and(searchDob,searchDobNotNull),
				Restrictions.and(searchNin,searchNinNotNull)
				));
		
		List<EppBlacklist> resultList = this.findAll(detachedCriteria);
		return resultList;
	}
}
