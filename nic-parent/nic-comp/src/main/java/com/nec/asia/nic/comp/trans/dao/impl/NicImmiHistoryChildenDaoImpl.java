package com.nec.asia.nic.comp.trans.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryChilden;
import com.nec.asia.nic.comp.trans.dao.NicBlacklistDao;
import com.nec.asia.nic.comp.trans.dao.NicCardReconRptDao;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryChildenDao;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryDao;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.NicCardReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicCardReconRptId;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
@Repository("nicImmiHistoryChildenDao")
public class NicImmiHistoryChildenDaoImpl 
		extends GenericHibernateDao<ImmiHistoryChilden, Long> 
		implements NicImmiHistoryChildenDao{
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@Override
	public List<ImmiHistoryChilden> findByImmiId(Long id) {
 		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
 		detachedCriteria.add(Restrictions.eq("immihistoryId", id));
		List<ImmiHistoryChilden> resultList = this.findAll(detachedCriteria);
		return resultList;
	}


	
}
