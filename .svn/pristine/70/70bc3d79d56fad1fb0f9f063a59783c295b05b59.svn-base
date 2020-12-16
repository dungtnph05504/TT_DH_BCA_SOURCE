package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFreeVisa;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.VisaDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface FreeVisaService extends BusinessService<EppFreeVisa, Long>{
	
	//public List<EppFreeVisa> findAllByCountryCodeAndPassportType(String countryCode, String passportType) throws Exception;	
	public EppFreeVisa findByCtryCode_Type_PPType(String countryCode, String ppType, String type) throws Exception;
	
	public boolean updateFreeVisa(EppFreeVisa obj) throws Exception;
	
	public PaginatedResult<VisaDto> findPaginatedByVisa(Integer status, String country, int pageNo, int pageSize);

}
