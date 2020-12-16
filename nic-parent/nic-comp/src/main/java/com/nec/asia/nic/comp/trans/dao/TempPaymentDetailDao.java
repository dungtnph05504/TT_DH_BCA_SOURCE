package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.TempPaymentDetail;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface TempPaymentDetailDao extends GenericDao<TempPaymentDetail, Long>{
	List<TempPaymentDetail> findTempByTransactionId(String transactionId);
	TempPaymentDetail findTempByTransactionIdOrType(String transactionId, String subType);
}
