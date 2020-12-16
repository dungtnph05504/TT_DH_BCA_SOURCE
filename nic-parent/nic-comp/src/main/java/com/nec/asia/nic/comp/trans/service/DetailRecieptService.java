package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppDetailReciept;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.framework.service.BusinessService;

public interface DetailRecieptService extends BusinessService<EppDetailReciept, Long>{
	public List<EppDetailReciept> findAllDetailReciept(String recieptNo);
	
	public Boolean saveOrUpdateNew(EppDetailReciept e) throws Exception;
	
	EppDetailReciept findDetailReciept(String recieptNo, String transactionId) throws Exception;
}
