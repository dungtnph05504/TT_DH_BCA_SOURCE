package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppInventory;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.InventoryDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface EppInventoryService extends BusinessService<EppInventory, Long> {
	
	public List<CountPassport> findByStatus(String status) throws Exception;

	public List<EppInventory> findAllInvestory(Integer officeId, String active);
	
	public EppInventory findInventoryByCode(String code, String active);
	
	public PaginatedResult<InventoryDto> findInventoryBySearch(AssignmentFilter filter,int pageNo, int pageSize);
	public PaginatedResult<InventoryDto> findInventoryItemsBySearch(AssignmentFilter filter,int pageNo, int pageSize);
	
	public Boolean deleteInv(Integer id);
	
	public EppInventory findInvById(Integer id);
	
}
