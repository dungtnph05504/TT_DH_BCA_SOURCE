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

public interface FlightDao extends GenericDao<EppFlight, Long>{
	
	public EppFlight findByFlightno_Type(String flightNo, String type);
	
	public List<EppFlight> findByFlightRouter(String routerCode);
	
	public PaginatedResult<EppFlight> findPaginateBySearch(String flightNo, String flightRouter, String airlineNo, int pageNo, int pageSize);

}
