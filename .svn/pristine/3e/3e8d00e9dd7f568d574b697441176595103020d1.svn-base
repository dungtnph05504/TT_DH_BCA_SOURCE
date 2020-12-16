package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppInventoryItems;
import com.nec.asia.nic.framework.service.BusinessService;

public interface EppInventoryItemsService extends BusinessService<EppInventoryItems, Long>{
	List<EppInventoryItems> findInventoryItems(Integer inventoryId, Integer maxTotal);
	Boolean deleteInvItem(Long id);
	EppInventoryItems findInventoryItemsById(Long itemId, Integer category);
}
