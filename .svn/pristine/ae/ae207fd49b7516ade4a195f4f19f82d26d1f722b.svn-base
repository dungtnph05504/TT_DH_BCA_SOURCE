package com.nec.asia.nic.framework.admin.code.service;

import java.util.List;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.admin.code.dto.PaymentDefDTO;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * 
 * @author chris_wong
 *
 */
/* 
 * Modification History:
 *  
 * 18 Aug 2013 (chris): add method to retrieve payment def for replacement transaction.
 * 26 Sep 2013 (chris): add BASIC_FEE_FOR_SC for senior citizen fix rate.
 */

public interface PaymentDefService extends BusinessService<PaymentDef, PaymentDefId>{
	public static final String SCOPE_PAYMENT = "PAYMENT";
	/** 
	 * @deprecated as Payment Rule defined fix rate for Senior Citizen for any transaction. To use PARA_BASIC_FEE_FOR_SC.
	 */
	public static final String PARA_REDUCE_AMOUNT = "REDUCE_AMOUNT";
	public static final String PARA_BASIC_FEE_FOR_SC = "BASIC_FEE_FOR_SC";
	public static final String PARA_BASIC_FEE = "BASIC_FEE";
	public static final String CODE_REPLACEMENT = "REP";

	//default methods from super class
//	public PaymentDef findById(PaymentDefId id);
//	public PaymentDefId save(PaymentDef entity);
//	public void saveOrUpdate(PaymentDef entity);
//	public PaymentDef merge(PaymentDef entity);
//	public void delete(PaymentDef entity);
//	public void delete(PaymentDefId id);
//	public List<PaymentDef> findAll ();
//	public List<PaymentDef> findAll (PaymentDef exampleEntity);
//	public PaginatedResult<PaymentDef> findAllForPagination (PaymentDef exampleEntity, int pageNo, int pageSize);
	public PaymentDef getPaymentDefForReplacement();
	
	public List<PaymentDef> findAllByTransType(String transactionType, String transactionSubtype);
	
	public double calculateReduceRateFeeAmount(PaymentDef PaymentDef);
	
	public PaginatedResult<PaymentDefDTO> findAllPaymentMatrixList(int pageNo, int pageSize) throws Exception;
	
	/* update payment matrix */
	//public boolean updatePaymentMatrix(PaymentDef paymentDef, String userId, String workstationId);
	
	/* update payment matrix delete flag */
	public boolean deletePaymentMatrix(PaymentDef paymentDef, String userId, String workstationId);
	
	public List<PaymentDef> findPaymentDefByTransType(String transactionType, String transactionSubtype, String stylePay);

	public PaymentDef findOnlyPaymentDefByTransType(String transactionType, String transactionSubtype);
}
