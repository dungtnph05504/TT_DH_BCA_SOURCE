package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface NicImmiHistoryResultDao extends GenericDao<ImmiHistoryResult, Long>{
	public List<ImmiHistoryResult> resultCountXNCByNameBorderGate(String code, Integer systemResult, Integer superVisorResult, Integer adResult);

	public ImmiHistoryResult getByImmiId(Long id);
	
	public List<ImmiHistoryResult> getListByImmiId(Long[] id, Boolean error);
}
