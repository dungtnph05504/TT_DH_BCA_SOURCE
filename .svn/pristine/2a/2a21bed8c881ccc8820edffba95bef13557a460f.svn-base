package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.EppInventoryItemsDetailDao;
import com.nec.asia.nic.comp.trans.domain.EppInventoryItemsDetail;
import com.nec.asia.nic.comp.trans.service.EppInventoryItemsDetailService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * @author JohnPaul_Millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Mar 4, 2014
 * 
 */
 
 
/*
 * Modification History:
 * 
 * 13 Mar 2014 (chris): add the non-argument constructor
 * 22 Sep 2014 (chris): add method findAfisRefIdByTransactionId()
 * 11 Jan 2016 (chris): add method getNextAfisKeyNo()
 */
 
@Service("eppInventoryItemsDetailService")
public class EppInventoryItemsDetailSerivceImpl 
					extends DefaultBusinessServiceImpl<EppInventoryItemsDetail, Long, EppInventoryItemsDetailDao>
					implements EppInventoryItemsDetailService {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	public EppInventoryItemsDetailSerivceImpl(){
		
	}

	@Autowired
	public EppInventoryItemsDetailSerivceImpl(EppInventoryItemsDetailDao dao) {
		this.dao = dao;
	}
	
	public EppInventoryItemsDetail findByCondition(String docChar, String docNum){
		try {
			return dao.findByCondition(docChar, docNum);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<EppInventoryItemsDetail> findInventoryItemsDetail(
			Long[] inventoryItemsId, Integer maxTotal, String syncStatus,
			String status) {		
		return dao.findInventoryItemsDetail(inventoryItemsId, maxTotal, syncStatus, status);
	}

	@Override
	public EppInventoryItemsDetail findByConditions(String chipNo,
			String docChar, String docNum) {
		// TODO Auto-generated method stub
		return dao.findByConditions(chipNo, docChar, docNum);
	}

	@Override
	public Boolean deleteInventory(Long id) {
		try {
			EppInventoryItemsDetail item = dao.findById(id);
			if(item != null){
				dao.delete(item);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
