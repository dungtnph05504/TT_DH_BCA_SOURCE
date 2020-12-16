package com.nec.asia.nic.framework.admin.code.dao;

import java.util.List;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface PaymentDefDao extends GenericDao<PaymentDef, PaymentDefId> {
	//default methods from super class
	//public PaymentDef findById(PaymentDefId id);
	//public Long save (PaymentDef e);
	//public void saveOrUpdate(PaymentDef e);
    //public PaymentDef merge(PaymentDef e);
	//public void delete(PaymentDef e);
	//public void delete(PaymentDefId id);
	//public List<PaymentDef> findAll ();

	public List<PaymentDef> findPaymentDefByTransType(String transactionType, String transactionSubtype);
	
	public PaymentDef findOnlyPaymentDefByTransType(String transactionType, String transactionSubtype);
	
	public List<PaymentDef> findPaymentDefByTransType(String transactionType, String transactionSubtype, String stylePay);
	
	public PaginatedResult<PaymentDef> findAllPaymentMatrixList(int PageNo, int PageSize) throws Exception;
	
	public String deletePaymentMatrix(PaymentDef paymentDef) throws Exception;
}
