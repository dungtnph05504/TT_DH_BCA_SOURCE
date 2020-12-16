package com.nec.asia.nic.comp.listHandover.service;

import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.dx.trans.MainTransaction;
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

public interface ListHandoverService extends BusinessService<NicListHandover, String>{
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
	
	public BaseModelList<NicListHandover> findAllByPackageId(NicListHandoverId id);
	
	public BaseModelSingle<NicListHandover> findByPackageId(NicListHandoverId id);
	
	public PaginatedResult<NicListHandover> findAllHandoverList(int pageNo, int pageSize, AssignmentFilterAll assignmentFilter) throws Exception;
	
	/* update payment matrix */
	//public boolean updatePaymentMatrix(PaymentDef paymentDef, String userId, String workstationId);
	
	/* update payment matrix delete flag */
	public boolean createRecordHandover(NicListHandover hdvor);
	
	public BaseModelSingle<Void> saveHandover(NicListHandover handover) throws Exception;
	
	public int createHandoverPackage(MainTransaction mainTransaction);
	
	public PaginatedResult<NicUploadJobDto> findListHandoverNoAssign(String[] arraySite, String userAssign, String siteCode, int typeList, int pageNo, int pageSize, String createDate, String endDate);

	void listHandoverUpdateAssignJob(String[] packageId, int typeList, String siteCode, String[] listAssign, String userId);
	
	public NicListHandover findListHandoverByOrther(String packageId, int typeList);
	public NicListHandover findListHandoverByOrther1(String packageId, String typeList) throws Exception;
	String getNUpdateCodeValueFromCodeId(String codeId);
	
	public NicListHandover findHandoverByTransactionId(String transactionId);
	
	public NicListHandover findHandoverByTransactionId(String transactionId, int type, Integer status, Boolean stage);
	
	public BaseModelSingle<NicListHandover> findHandoverByCriteria(
			String packageId, String type, Integer status) throws Exception;
	
	public BaseModelList<NicListHandover> findAllHandoverByTransactionId(String transactionId, String type, Integer status, Boolean stage);
	
	public List<CountPassport> countHandoverAProcess(String datefrom) throws Exception;
	
	public List<CountPassport> countHandoverA(String datefrom) throws Exception;
	
	public List<String> listTransactionA(String pack) throws Exception;

	public List<NicListHandover> findListHandoverByCriteria(String packageId,
			String typeList, Integer status);

	public BaseModelSingle<Boolean> saveOrUpdateHandover(NicListHandover hanCheck);

}
