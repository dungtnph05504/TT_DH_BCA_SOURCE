package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFlightRouter;
import com.nec.asia.nic.comp.trans.dto.FlightDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface FlightService extends BusinessService<EppFlight, Long>{
	
	//public List<EppFlight> findAllByCriteria(String flightNo, String flightRouter, String airline, int type, int typeGet) throws Exception;	
	public EppFlight findByFlightno_Type(String flightNo, String type) throws Exception;
	
	public boolean updateFlight(EppFlight obj) throws Exception;
	
	public PaginatedResult<FlightDto> findPaginateBySearch(String flightNo, String routerCode, String airlineNo, int pageNo, int pageSize);

	public Boolean deleteEppFlight(Long id);
	
	public List<EppFlight> findByFlightRouter(String routerCode);
	
	public void saveOrUpdateFlight(EppFlight epp);
}
