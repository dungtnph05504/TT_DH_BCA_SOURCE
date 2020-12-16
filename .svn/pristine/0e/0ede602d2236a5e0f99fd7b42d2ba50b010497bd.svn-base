package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFlightRouter;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface FlightRouterDao extends GenericDao<EppFlightRouter, Long>{
	
	public EppFlightRouter findByCode(String code);
	
	public PaginatedResult<EppFlightRouter> findPaginateBySearch(String code, String fromFlace, String toPlace, int pageNo, int pageSize);
}
