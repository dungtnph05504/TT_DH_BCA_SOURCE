package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.dto.AirlineDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface AirlineService  extends BusinessService<EppAirline, Long>{
	
	public EppAirline findAllByCode(String code) throws Exception;	
	
	public void saveOrUpdateAir(EppAirline epp);
	
	public boolean updateAirline(EppAirline obj) throws Exception;
	
	public PaginatedResult<AirlineDto> findPaginateBySearch(String code, String nationality, int pageNo, int pageSize);

	public Boolean deleteEppAirline(Long id) throws Exception;	
	
	public List<EppAirline> findAllAirline();
}
