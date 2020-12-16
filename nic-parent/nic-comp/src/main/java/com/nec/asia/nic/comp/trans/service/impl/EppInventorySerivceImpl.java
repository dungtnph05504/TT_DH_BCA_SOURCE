package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.EppInventoryDao;
import com.nec.asia.nic.comp.trans.domain.EppInventory;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.InventoryDto;
import com.nec.asia.nic.comp.trans.service.EppInventoryService;
import com.nec.asia.nic.framework.PaginatedResult;
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
 
@Service("eppInventorySerivce")
public class EppInventorySerivceImpl 
					extends DefaultBusinessServiceImpl<EppInventory, Long, EppInventoryDao>
					implements EppInventoryService {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	public EppInventorySerivceImpl(){
		
	}

	@Autowired
	public EppInventorySerivceImpl(EppInventoryDao dao) {
		this.dao = dao;
	}
	
	
	@Override
	public List<CountPassport> findByStatus(String status){
		try {
			List<CountPassport> list = this.dao.findByStatus(status);
			if(list != null && list.size() > 0){
				return list;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<EppInventory> findAllInvestory(Integer officeId, String active) {
		try {
			return this.dao.findAllInvestory(officeId, active);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public EppInventory findInventoryByCode(String code, String active) {
		
		return dao.findInventoryByCode(code, active);
	}

	@Override
	public PaginatedResult<InventoryDto> findInventoryBySearch(
			AssignmentFilter filter, int pageNo, int pageSize) {
		try {
			PaginatedResult<InventoryDto> prInv = dao.findInventoryBySearch(filter, pageNo, pageSize);
			if(prInv != null){
				List<InventoryDto> invList = prInv.getRows();
				for(InventoryDto inv : invList){
					if(StringUtils.isNotEmpty(inv.getStatus())){
						switch (inv.getStatus()) {
							case "I":
								inv.setStatus("Chưa sử dụng");
								break;
							case "D":
								inv.setStatus("Đã in");
								break;
							case "H":
								inv.setStatus("In hỏng");
								break;
							case "L":
								inv.setStatus("Phôi lỗi");
								break;
	
							default:
								break;
						}
					}
					if(StringUtils.isNotEmpty(inv.getInvName())){
						switch (inv.getInvName()) {
						case "MB":
							inv.setInvName("Miền Bắc");
							break;
						case "MN":
							inv.setInvName("Miền Nam");
							break;
						default:
							break;
					}
					}
				}
			}
			return prInv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean deleteInv(Integer id) {
		try {
			EppInventory epp = dao.findInvById(id);
			if(epp != null){
				dao.delete(epp);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public EppInventory findInvById(Integer id) {
		return dao.findInvById(id);
	}

	@Override
	public PaginatedResult<InventoryDto> findInventoryItemsBySearch(
			AssignmentFilter filter, int pageNo, int pageSize) {
		try {
			PaginatedResult<InventoryDto> prInv = dao.findInventoryItemsBySearch(filter, pageNo, pageSize);
			if(prInv != null){
				List<InventoryDto> invList = prInv.getRows();
				for(InventoryDto inv : invList){
					if(StringUtils.isNotEmpty(inv.getStatus())){
						switch (inv.getStatus()) {
							case "1":
								inv.setStatus("Hộ chiếu thường");
								break;
							case "2":
								inv.setStatus("Hộ chiếu điện tử");
								break;
							default:
								break;
						}
					}
					if(StringUtils.isNotEmpty(inv.getInvCode())){
						switch (inv.getInvCode()) {
						case "MB":
							inv.setInvCode("Miền Bắc");
							break;
						case "MN":
							inv.setInvCode("Miền Nam");
							break;
						default:
							break;
					}
					}
				}
			}
			return prInv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
