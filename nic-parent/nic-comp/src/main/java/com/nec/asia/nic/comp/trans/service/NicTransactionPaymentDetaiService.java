package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;


public interface NicTransactionPaymentDetaiService {
	public List<NicTransactionPaymentDetail> findListDetailPaymentByTransactionId(
			String paymentID, String type, String subType, Boolean status);
	
	public BaseModelSingle<NicTransactionPaymentDetail> findDetailPaymentByTransactionId(String paymentID, String type, String subType);
	
	public BaseModelList<NicTransactionPaymentDetail> findListDetailPaymentList(String paymentID);
	
	public BaseModelList<NicTransactionPaymentDetail> findListDetailPaymentList(String paymentID, Boolean stage);
	
	public BaseModelSingle<Boolean> saveOrUpdatePaymentDetail(NicTransactionPaymentDetail detail) throws Exception;
}
