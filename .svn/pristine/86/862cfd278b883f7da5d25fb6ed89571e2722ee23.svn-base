package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.EppInventoryItemsDao;
import com.nec.asia.nic.comp.trans.domain.EppInventoryItems;
import com.nec.asia.nic.comp.trans.service.EppInventoryItemsService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;


@Service
public class EppInventoryItemsServiceImpl extends DefaultBusinessServiceImpl<EppInventoryItems, Long, EppInventoryItemsDao> implements EppInventoryItemsService{

	@Autowired
	private EppInventoryItemsDao dao;
	
	@Override
	public List<EppInventoryItems> findInventoryItems(Integer inventoryId,
			Integer maxTotal) {
		// TODO Auto-generated method stub
		return dao.findInventoryItems(inventoryId, maxTotal);
	}

	@Override
	public Boolean deleteInvItem(Long id) {
		try {
			EppInventoryItems inv = dao.findInventoryItemsById(id, null);
			if(inv != null){
				dao.delete(inv);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public EppInventoryItems findInventoryItemsById(Long itemId,
			Integer category) {
		return dao.findInventoryItemsById(itemId, category);
	}

}
