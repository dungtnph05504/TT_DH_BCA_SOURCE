package com.nec.asia.nic.comp.trans.dao;

import com.nec.asia.nic.comp.trans.domain.NicJobAfisCheckResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface NicJobAfisCheckResultDao extends GenericDao<NicJobAfisCheckResult, Long> {
	//default methods from super class
	//public NicJobAfisCheckResult findById(Long uploadJobId);
	//public Long save (NicJobAfisCheckResult e);
	//public void saveOrUpdate(NicJobAfisCheckResult e);
    //public NicJobAfisCheckResult merge(NicJobAfisCheckResult e);
	//public void delete(NicJobAfisCheckResult e);
	//public void delete(Long uploadJobId);
	//public List<NicJobAfisCheckResult> findAll ();
	
	/* update NIC_JOB_AFIS_CHECK_RESULT  on approve */
	public void updateDecisionAfisOnApprove(long approveJobId, String userId, String wkstnId) throws Exception;
	
	/* update NIC_JOB_AFIS_CHECK_RESULT  on reject */
	public void updateDecisionAfisOnReject(long rejectJobId, String userId, String wkstnId) throws Exception;
	
	
	
}
