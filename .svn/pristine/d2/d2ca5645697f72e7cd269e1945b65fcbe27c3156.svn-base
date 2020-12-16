package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.dto.AirlineDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface AirlineDao extends GenericDao<EppAirline, Long>{
	
	public EppAirline findByCode(String code);
	
	public PaginatedResult<EppAirline> findPaginateBySearch(String code, String nationality, int pageNo, int pageSize);
}
