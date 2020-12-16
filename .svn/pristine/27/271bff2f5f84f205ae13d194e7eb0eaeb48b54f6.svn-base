package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFlightRouter;
import com.nec.asia.nic.comp.trans.domain.EppFreeVisa;
import com.nec.asia.nic.comp.trans.dto.FlightDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface FlightRouterService extends BusinessService<EppFlightRouter, Long>{
	
	//public List<EppFlightRouter> findAllByCriteria(String flightRouter, String fromPlace, String toPlace, int typeGet) throws Exception;	
	public EppFlightRouter findByCode(String code) throws Exception;
	
	public boolean updateFlightRouter(EppFlightRouter obj) throws Exception;
	
	public PaginatedResult<FlightDto> findPaginateBySearch(String code, String fromFlace, String toPlace, int pageNo, int pageSize);

	public Boolean deleteEppFlightRouter(Long id);
	
	public void saveOrUpdateAir(EppFlightRouter epp);
	
	public List<EppFlightRouter> findALlFlightRouter();
}
