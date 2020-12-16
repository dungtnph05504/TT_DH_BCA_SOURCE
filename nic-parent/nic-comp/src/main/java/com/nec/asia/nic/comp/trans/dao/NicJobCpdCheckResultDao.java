package com.nec.asia.nic.comp.trans.dao;

import com.nec.asia.nic.comp.trans.domain.NicJobCpdCheckResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface NicJobCpdCheckResultDao extends GenericDao<NicJobCpdCheckResult, Long> {
	//default methods from super class
	//public NicJobCpdCheckResult findById(Long uploadJobId);
	//public Long save (NicJobCpdCheckResult e);
	//public void saveOrUpdate(NicJobCpdCheckResult e);
    //public NicJobCpdCheckResult merge(NicJobCpdCheckResult e);
	//public void delete(NicJobCpdCheckResult e);
	//public void delete(Long uploadJobId);
	//public List<NicJobCpdCheckResult> findAll ();
	
	
	/* update NIC_JOB_CPD_CHECK_RESULT on approve */
	public void updateDecisionCpdOnApprove(long approveJobId, String userId, String wkstnId) throws Exception;
	
	
	/* update NIC_JOB_CPD_CHECK_RESULT on reject */
	public void updateDecisionCpdOnReject(long rejectJobId, String userId, String wkstnId) throws Exception;
	
	
}
