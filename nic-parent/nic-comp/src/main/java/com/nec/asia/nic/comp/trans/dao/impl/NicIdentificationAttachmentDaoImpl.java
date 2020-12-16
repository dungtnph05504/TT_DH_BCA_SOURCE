package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.trans.dao.NicIdentificationAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicIdentificationDao;
import com.nec.asia.nic.comp.trans.domain.EppIdentification;
import com.nec.asia.nic.comp.trans.domain.EppIdentificationAttachmnt;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
@Repository("nicIdentificationAttachmentDao")
public class NicIdentificationAttachmentDaoImpl 
		extends GenericHibernateDao<EppIdentificationAttachmnt, Long> 
		implements NicIdentificationAttachmentDao{
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@Override
	public List<EppIdentificationAttachmnt> findAttachmentById (Long id) {
		try{
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			if (id != null) {
				detachedCriteria.add(Restrictions.eq("identificationId", id));
			}
			List<EppIdentificationAttachmnt> resultList = this.findAll(detachedCriteria);
			
			if(resultList != null && resultList.size() > 0)
				return resultList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
