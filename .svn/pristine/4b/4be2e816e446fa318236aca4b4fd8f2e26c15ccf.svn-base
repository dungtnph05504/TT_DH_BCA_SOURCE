package com.nec.asia.nic.comp.listHandover.dao;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.NicHitTransaction;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface ListHandoverDao extends GenericDao<NicListHandover, String> {
	//default methods from super class
	//public PaymentDef findById(PaymentDefId id);
	//public Long save (PaymentDef e);
	//public void saveOrUpdate(PaymentDef e);
    //public PaymentDef merge(PaymentDef e);
	//public void delete(PaymentDef e);
	//public void delete(PaymentDefId id);
	//public List<PaymentDef> findAll ();

	public BaseModelList<NicListHandover> findListHandoverByPackageId(NicListHandoverId id, AssignmentFilterAll assignmentFilter) throws Exception;
	
	public PaginatedResult<NicListHandover> findAllListHandover(int PageNo, int PageSize, AssignmentFilterAll assignmentFilter) throws Exception;
	
	public boolean createHandoverList(NicListHandover handover) throws Exception;
	
	public List<NicListHandover> findListHandoverByPackageIdOrType(String packageId, int type, AssignmentFilterAll assignmentFilter) throws Exception;
	public PaginatedResult<NicListHandover> findListHandoverNoAssign(String arraySite[], String userAssign, String siteCode, int typeList, int pageNo, int pageSize, Date createDate, Date endDate);
	
	void listHandoverUpdateAssignJob(String packageId, int typeList, String siteCode, String[] listAssign, String userId);
	
	public List<NicListHandover> findListHandoverByOrther(String packageId, int typeList);
	public List<NicListHandover> findListHandoverByOrther1(String packageId, String typeList);
	public List<NicListHandover> findListHandoverListB(int typeList, String leaderId, String asignId, AssignmentFilterAll assignmentFilter);
	
	public List<NicListHandover> findListHandoverListBApprove(int typeList, String leaderId, String asignId, AssignmentFilterAll assignmentFilter);
	
	String getNUpdateCodeValueFromCodeId(String codeId);
	
	public NicListHandover findHandoverByTransactionId(String transactionId);
	
	public List<NicListHandover> findListHandoverByCriteria(
			String packageId, String type, Integer status) throws Exception;
	
	public BaseModelSingle<NicListHandover> findHandoverByCriteria(
			String packageId, String type, Integer status) throws Exception;
	
	public NicListHandover findHandoverByCriteriaSync(
			String packageId, String type, Integer status) throws Exception;
	
	public NicListHandover findHandoverByTransactionId(String transactionId, String type, Integer status, Boolean stage);
	public NicListHandover findHandoverByTransactionId1(String transactionId, int type, Integer[] status, Boolean stage);

	public BaseModelList<NicListHandover> findAllHandoverByTransactionId(String transactionId, String type, Integer status, Boolean stage);

	public List<CountPassport> countHandoverAProcess(String datefrom) throws Exception;
	
	public List<CountPassport> countHandoverA(String datefrom) throws Exception;
	
	public List<String> listTransactionA(String pack) throws Exception;
	
	public BaseModelSingle<Void> saveHandover(NicListHandover handover);

	public BaseModelSingle<Boolean> saveOrUpdateHandover(
			NicListHandover hanCheck);
	
}
