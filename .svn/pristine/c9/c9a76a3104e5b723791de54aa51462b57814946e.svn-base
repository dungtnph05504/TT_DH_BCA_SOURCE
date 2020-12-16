package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppInventoryItemsDetail;
import com.nec.asia.nic.framework.service.BusinessService;

public interface EppInventoryItemsDetailService extends BusinessService<EppInventoryItemsDetail, Long> {
	
	public EppInventoryItemsDetail findByCondition(String docChar, String docNum) throws Exception;
	
	public List<EppInventoryItemsDetail> findInventoryItemsDetail(Long[] inventoryItemsId, Integer maxTotal, String syncStatus, String status);

	public EppInventoryItemsDetail findByConditions(String chipNo, String docChar, String docNum);
	
	public Boolean deleteInventory(Long id);
}
