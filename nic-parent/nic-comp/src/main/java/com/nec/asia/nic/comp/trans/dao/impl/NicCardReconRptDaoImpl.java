package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.trans.dao.NicCardReconRptDao;
import com.nec.asia.nic.comp.trans.domain.NicCardReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicCardReconRptId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
@Repository("nicCardReconRptDao")
public class NicCardReconRptDaoImpl 
		extends GenericHibernateDao<NicCardReconRpt, NicCardReconRptId> 
		implements NicCardReconRptDao{
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@Override
	public List<NicCardReconRpt> findAllReconReport(String siteCode,
			String reportCreateDate) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(siteCode)) {
			detachedCriteria.add(Restrictions.eq("id.siteCode", siteCode));
		}
		if (StringUtils.isNotBlank(reportCreateDate)) {
			detachedCriteria.add(Restrictions.eq("id.reportCreateDate", reportCreateDate));
		}	
		List<NicCardReconRpt> resultList = this.findAll(detachedCriteria);
		return resultList;
	}
	

}
