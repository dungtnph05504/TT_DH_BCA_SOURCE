package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppDetailReciept;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.FeeRecieptPayment;
import com.nec.asia.nic.framework.service.BusinessService;

public interface FeeRecieptPaymentService extends BusinessService<FeeRecieptPayment, Long> {
	public BaseModelList<FeeRecieptPayment> findAllFeeRecieptPayment(String recieptNo);
	
	public BaseModelSingle<Boolean> saveOrUpdateNew(FeeRecieptPayment e);
	
	public BaseModelSingle<Boolean> deleteObject(List<FeeRecieptPayment> e);
	
}
