package com.nec.asia.nic.comp.trans.dao;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.comp.trans.dto.RICPymtRptDto;
import com.nec.asia.nic.comp.trans.dto.RICWaiverRptDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface NicTransactionPaymentDao extends GenericDao<NicTransactionPayment, String> {

	public NicTransaction getTransactionPayment(NicTransaction transObj);
	//default methods from super class
	//public NicTransactionPayment findById(String transactionId);
	//public String save (NicTransactionPayment e);
	//public void saveOrUpdate(NicTransactionPayment e);
    //public NicTransactionPayment merge(NicTransactionPayment e);
	//public void delete(NicTransactionPayment e);
	//public void delete(String transactionId);
	//public List<NicTransactionPayment> findAll ();
	
	PaginatedResult<RICPymtRptDto> getRicPymtRptRecordList(RICPymtRptDto ricPymtRptDto ,int pageNo, int pageSize);

	PaginatedResult<RICWaiverRptDto> getRicWaiverRptRecordList(RICWaiverRptDto ricWaiverRptDto ,int pageNo, int pageSize);
	
	BaseModelSingle<NicTransactionPayment> findGetPaymentByTransaction(String transId);
	
	BaseModelSingle<String> saveTranPayment(NicTransactionPayment nicTranPay);

	public void saveOrUpdatePaymentAmount(NicTransactionPayment payments) throws Exception;
}
