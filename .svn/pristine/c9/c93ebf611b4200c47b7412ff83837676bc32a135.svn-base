package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppInventoryItems;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppInventoryItemsDao extends GenericDao<EppInventoryItems, Long>{
	List<EppInventoryItems> findInventoryItems(Integer inventoryId, Integer maxTotal);
	EppInventoryItems findInventoryItemsById(Long itemId, Integer category);
}
