package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFlightRouter;
import com.nec.asia.nic.comp.trans.domain.EppFreeVisa;
import com.nec.asia.nic.comp.trans.domain.EppPurpose;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface PurposeDao extends GenericDao<EppPurpose, Long>{
	
	public EppPurpose findByCode(String code);
	
	public PaginatedResult<EppPurpose> findPaginateByPurpose(String code, Integer status, int pageNo, int pageSize);
}
