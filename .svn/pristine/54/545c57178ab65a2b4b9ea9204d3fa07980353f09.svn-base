package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.SynQueueJobXnc;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface SynQueueJobXncDao extends GenericDao<SynQueueJobXnc, Long>{
	public SynQueueJobXnc findQueueXncByInfo(Long id, String status) throws Exception;
	public List<SynQueueJobXnc> findQueueXncBySite(String site, String status, String[] objType, int maxTotal) throws Exception;
	public SynQueueJobXnc findQueueXncByStatus(String site, String status, String objType) throws Exception;
}
