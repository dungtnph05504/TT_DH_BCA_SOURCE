package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.DocumentHistoryDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicDocumentHistory;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
 * 
 * @author khang
 *
 */
/* 
 * Modification History:
 *  
 * 15 Jan 2016 (khang): init dao impl
 * 09 May 2016 (khang): add method addDocumentHistory(List<NicDocumentHistory>)
 */
@Repository("documentHistoryDao")
public class DocumentHistoryDaoImpl extends
		GenericHibernateDao<NicDocumentHistory, Long> implements DocumentHistoryDao {

	@Override
	public List<NicDocumentHistory> findAllByPassportNo(String passportNo) {
		List<NicDocumentHistory> documentHistoryList = new ArrayList<NicDocumentHistory>();
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(passportNo)) {
			detachedCriteria.add(Restrictions.eq("passportNo", passportNo));
			documentHistoryList = this.findAll(detachedCriteria);
		}
		logger.info("[findAllByPassportNo][{}] size:{}", new Object[] { passportNo, documentHistoryList.size()});
		return documentHistoryList;
	}
	
	@Override
	public int addDocumentHistory(List<NicDocumentHistory> documentHistoryList) throws Exception {
		Session session = null;
		int count = 0;
		try {
			session = this.openSession();
			Transaction tx = session.beginTransaction();
			for (NicDocumentHistory documentHistory : documentHistoryList) {
				session.save(documentHistory);
				if (++count % 50 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} catch (HibernateException ex) {			
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return count;
	}

	@Override
	public BaseModelSingle<Boolean> saveDocumentHistory(
			NicDocumentHistory nicDocumentHistory) {
		try {
			this.save(nicDocumentHistory);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(false, false, CreateMessageException.createMessageException(e) + " - saveDocumentHistory - thất bại.");
		}
	}
}
