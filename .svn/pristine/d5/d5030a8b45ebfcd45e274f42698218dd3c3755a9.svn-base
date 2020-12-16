package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFreeVisa;
import com.nec.asia.nic.comp.trans.domain.EppPurpose;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.PurposeDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface PurposeService extends BusinessService<EppPurpose, Long>{
	
	public EppPurpose findByCode(String code) throws Exception;
	
	public boolean updatePurpose(EppPurpose obj) throws Exception;
	
	public PaginatedResult<PurposeDto> findPaginateByPurpose(String code, Integer status, int pageNo, int pageSize);

}
