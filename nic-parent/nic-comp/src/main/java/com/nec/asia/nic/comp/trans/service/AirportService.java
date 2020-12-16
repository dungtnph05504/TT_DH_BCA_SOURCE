package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.dto.AirlineDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface AirportService extends BusinessService<EppAirport, Long>{
	
	public EppAirport findAllByCode(String code) throws Exception;	
	
	public List<EppAirport> findAllAirport();
	
	public boolean updateAirport(EppAirport obj) throws Exception;
	
	public void saveOrUpdateAir(EppAirport epp);
	
	
	public PaginatedResult<AirlineDto> findPaginateBySearch(String code, String nationality, int pageNo, int pageSize);

	public Boolean deleteEppAirport(Long id) throws Exception;	
}
