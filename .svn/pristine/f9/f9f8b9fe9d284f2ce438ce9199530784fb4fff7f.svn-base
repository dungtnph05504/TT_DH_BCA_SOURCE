package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.SynQueueJobXnc;
import com.nec.asia.nic.framework.service.BusinessService;

public interface SynQueueJobXncService extends BusinessService<SynQueueJobXnc, Long>{
	void saveOrUpdateQueueXnc(SynQueueJobXnc queue);
	void updateQueueXncJob(Long id, String status) throws Exception;
	void updateStatusQueueXncJob(Long id, String status) throws Exception;
	public List<SynQueueJobXnc> findQueueXncBySite(String site, String status, String[] objType, int maxTotal) throws Exception;
	public SynQueueJobXnc findQueueXncByStatus(String site, String status, String objType) throws Exception;
}
