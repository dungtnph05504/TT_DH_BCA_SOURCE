package com.nec.asia.nic.comp.listHandover.service;

import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
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

public interface PersoLocationsService extends BusinessService<NicPersoLocations, Long>{
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
	//public NicListHandover getPaymentDefForReplacement();
	
	public List<NicPersoLocations> findAllById(long id) throws Exception;
	
	public PaginatedResult<NicPersoLocations> findAllPersoLocations(int pageNo, int pageSize, AssignmentFilterAll assignmentFilter) throws Exception;
	
	/* update payment matrix */
	//public boolean updatePaymentMatrix(PaymentDef paymentDef, String userId, String workstationId);
	
	/* update payment matrix delete flag */
	public boolean createRecordPersoLocations(NicPersoLocations perso);
	
	public NicPersoLocations findPersoByCode(String code, Long persoId);
	public NicPersoLocations findPersoByCodeAnd(String code, Long persoId);
	public List<NicPersoLocations> findPersoByStatus(Integer status, String code);
}
