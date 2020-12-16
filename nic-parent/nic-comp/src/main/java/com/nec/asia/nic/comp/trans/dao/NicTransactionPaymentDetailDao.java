package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface NicTransactionPaymentDetailDao extends GenericDao<NicTransactionPaymentDetail, Long>{
	public BaseModelList<NicTransactionPaymentDetail> findListDetailPaymentByTransactionId(String paymentID, String type, String subType, Boolean status);
	public BaseModelSingle<Boolean> saveOrUpdatePaymentDetail(NicTransactionPaymentDetail detail);
	public BaseModelSingle<NicTransactionPaymentDetail> findDetailPaymentByType(String paymentId, String type, String subType, Boolean status);

	public BaseModelList<NicTransactionPaymentDetail> findListDetailPaymentList(String paymentId);
	public BaseModelList<NicTransactionPaymentDetail> findListDetailPaymentList(String paymentId, Boolean stage);
}
