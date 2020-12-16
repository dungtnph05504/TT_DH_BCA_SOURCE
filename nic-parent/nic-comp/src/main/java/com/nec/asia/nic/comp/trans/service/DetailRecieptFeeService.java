package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.framework.service.BusinessService;

public interface DetailRecieptFeeService extends BusinessService<DetailRecieptFee, Long> {
	public BaseModelList<DetailRecieptFee> findAllDetailRecieptFee(String recieptNo);
	
	public BaseModelSingle<Boolean> saveOrUpdateNew(DetailRecieptFee e) throws Exception;
	
	BaseModelSingle<DetailRecieptFee> findDetailRecieptFee(String recieptNo, String code, String number) throws Exception;
}
