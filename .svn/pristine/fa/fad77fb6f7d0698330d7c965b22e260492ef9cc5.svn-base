package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.FeeRecieptPayment;
import com.nec.asia.nic.framework.service.BusinessService;

public interface RecieptManagerService  extends BusinessService<EppRecieptManager, Long>{
	public BaseModelList<EppRecieptManager> findAllRecieptManager(String recieptNo);
	
	public BaseModelSingle<Boolean> saveOrUpdateNew(EppRecieptManager e) throws Exception;
}
