package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicJobCpdCheckResultDao;
import com.nec.asia.nic.comp.trans.domain.NicJobAfisCheckResult;
import com.nec.asia.nic.comp.trans.domain.NicJobCpdCheckResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("jobCpdCheckResultDao")
public class NicJobCpdCheckResultDaoImpl extends
		GenericHibernateDao<NicJobCpdCheckResult, Long> implements
		NicJobCpdCheckResultDao {

	@Override
	public void updateDecisionCpdOnReject(long rejectJobId, String userId, String wkstnId) throws Exception {
		try {
		DetachedCriteria criteria = DetachedCriteria.forClass(NicJobCpdCheckResult.class);
		criteria.add(Restrictions.eq("uploadJobId", rejectJobId));
		List<NicJobCpdCheckResult> list = (List<NicJobCpdCheckResult>) getHibernateTemplate().findByCriteria(criteria);
		NicJobCpdCheckResult rejectUpdateObj = list.get(0);
		rejectUpdateObj.setDecision("Y");
		rejectUpdateObj.setDecisionOfficerId(userId);
		rejectUpdateObj.setDecisionTime(new Date());
		rejectUpdateObj.setUpdateBy(userId);
		rejectUpdateObj.setUpdateWkstnId(wkstnId);
		rejectUpdateObj.setUpdateDate(new Date());
		getHibernateTemplate().update(rejectUpdateObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occurred while updating the decision of CPD on reject, Reason:"+e.getMessage());
		}
	}

	
	


	
	@Override
	public void updateDecisionCpdOnApprove(long approveJobId, String userId,
			String wkstnId) throws Exception {
		try{
		DetachedCriteria criteria = DetachedCriteria.forClass(NicJobCpdCheckResult.class);
		criteria.add(Restrictions.eq("uploadJobId", approveJobId));
		List<NicJobCpdCheckResult> list = (List<NicJobCpdCheckResult>) getHibernateTemplate().findByCriteria(criteria);
		NicJobCpdCheckResult rejectUpdateObj = list.get(0);
		rejectUpdateObj.setDecision("Y");
		rejectUpdateObj.setDecisionOfficerId(userId);
		rejectUpdateObj.setDecisionTime(new Date());
		rejectUpdateObj.setUpdateBy(userId);
		rejectUpdateObj.setUpdateWkstnId(wkstnId);
		rejectUpdateObj.setUpdateDate(new Date());
		getHibernateTemplate().update(rejectUpdateObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occurred while updating the decision of CPD on approve, Reason:"+e.getMessage());
		}
		
	}


	
	
	
	

}
