package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.apache.commons.collections.CollectionUtils;

import com.nec.asia.nic.comp.trans.dao.NicJobAfisCheckResultDao;
import com.nec.asia.nic.comp.trans.domain.NicJobAfisCheckResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("jobAfisCheckResultDao")
public class NicJobAfisCheckResultDaoImpl extends
		GenericHibernateDao<NicJobAfisCheckResult, Long> implements NicJobAfisCheckResultDao{
	
	@Override
	public void updateDecisionAfisOnReject(long rejectJobId, String userId,	String wkstnId) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(NicJobAfisCheckResult.class);
			criteria.add(Restrictions.eq("uploadJobId", rejectJobId));
			List<NicJobAfisCheckResult> list = (List<NicJobAfisCheckResult>) getHibernateTemplate().findByCriteria(criteria);
			if(CollectionUtils.isNotEmpty(list)){
				NicJobAfisCheckResult rejectUpdateObj = list.get(0);
				rejectUpdateObj.setAfisDecision("Y");
				rejectUpdateObj.setAfisDecisionOfficerId(userId);
				rejectUpdateObj.setAfisDecisionWkstnId(wkstnId);
				rejectUpdateObj.setAfisDecisionTime(new Date());
				rejectUpdateObj.setUpdateBy(userId);
				rejectUpdateObj.setUpdateWkstnId(wkstnId);
				rejectUpdateObj.setUpdateDate(new Date());
				getHibernateTemplate().update(rejectUpdateObj);
			}
		} catch (Exception e) {
			logger.error("Error occurred while updating the decision to reject, Exception: "+e.getMessage());
			e.printStackTrace();
		}
		// return rejectJobID;
		
	}
	
	@Override
	public void updateDecisionAfisOnApprove(long approveJobId, String userId, String wkstnId) throws Exception {
		try{
		DetachedCriteria criteria = DetachedCriteria.forClass(NicJobAfisCheckResult.class);
		criteria.add(Restrictions.eq("uploadJobId", approveJobId));
		List<NicJobAfisCheckResult> list = (List<NicJobAfisCheckResult>) getHibernateTemplate().findByCriteria(criteria);
		if(CollectionUtils.isNotEmpty(list)){
			NicJobAfisCheckResult rejectUpdateObj = list.get(0);
			rejectUpdateObj.setAfisDecision("Y");
			rejectUpdateObj.setAfisDecisionOfficerId(userId);
			rejectUpdateObj.setAfisDecisionWkstnId(wkstnId);
			rejectUpdateObj.setAfisDecisionTime(new Date());
			rejectUpdateObj.setUpdateBy(userId);
			rejectUpdateObj.setUpdateWkstnId(wkstnId);
			rejectUpdateObj.setUpdateDate(new Date());
			getHibernateTemplate().update(rejectUpdateObj);
		}
		} catch (Exception e) {
			logger.error("Error occurred while updating the decision to approve, Exception: "+e.getMessage());
			e.printStackTrace();
		}

	}
	

}
