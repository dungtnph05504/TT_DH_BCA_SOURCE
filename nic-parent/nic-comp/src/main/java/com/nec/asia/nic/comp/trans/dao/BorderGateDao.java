package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface BorderGateDao extends GenericDao<BorderGate, Long>{
	List<BorderGate> findAllBorderGate(String code);
	List<BorderGate> findBorderGateBySync(String syncData);
	List<BorderGate>	findAllBorderGate();
	PaginatedResult<BorderGate> findAllBorderGate(String code, int pageNo,
			int pageSize);
}
