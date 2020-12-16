package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppInventory;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.InventoryDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppInventoryDao extends GenericDao<EppInventory, Long>{
	
	public List<CountPassport> findByStatus(String status) throws Exception;
	public List<EppInventory> findAllInvestory(Integer officeId, String active);
	public EppInventory findInventoryByCode(String code, String active);
	public PaginatedResult<InventoryDto> findInventoryBySearch(AssignmentFilter filter,int pageNo, int pageSize);
	public PaginatedResult<InventoryDto> findInventoryItemsBySearch(AssignmentFilter filter,int pageNo, int pageSize);
	public List<String> getAllInventoryItems();
	public List<String> getAllBatchNo();
	
	public EppInventory findInvById(Integer id);
}
