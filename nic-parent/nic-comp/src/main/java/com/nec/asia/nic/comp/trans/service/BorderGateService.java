package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.dto.BorderGateDto;
import com.nec.asia.nic.framework.PaginatedResult;

public interface BorderGateService {
	List<BorderGate> findAllBorderGate(String code);
	List<BorderGate> findBorderGateBySync(String syncData);
	List<BorderGate>	findAllBorderGate();
	PaginatedResult<BorderGateDto> findAllBorderGate(String code, int pageNo,
			int pageSize);
	void saveOrUpdateBorderGate(BorderGate borderGate);
	BorderGate findBorderGateByCode(String Code);
	Boolean deleteBorderGate(Long id) throws Exception;
	BorderGate findBorderGatebyId(Long id);
}
