package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.TempPaymentDetail;
import com.nec.asia.nic.framework.service.BusinessService;

public interface TempPaymentDetailService extends BusinessService<TempPaymentDetail, Long>{
	void saveOrUpdateTemp(TempPaymentDetail tempPaymentDetail);
	List<TempPaymentDetail> findTempByTransactionId(String transactionId);
	TempPaymentDetail findTempByTransactionId(String transactionId, String type);
}
