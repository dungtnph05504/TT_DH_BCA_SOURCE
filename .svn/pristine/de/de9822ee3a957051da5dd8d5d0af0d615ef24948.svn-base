package com.nec.asia.nic.framework.admin.code.dao;

/*
 * Modification History:
 *  * 
 * 2 Oct 2013 (Peddi Swapna): Modified getAllForPagination method to implement the external sorting.
 * 
 */

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDef;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDefId;
import com.nec.asia.nic.framework.dao.GenericDao;

@Deprecated 
/**
 * @deprecated this class is not supported by this version 
 */
public interface ProofDocumentDefDao extends GenericDao<ProofDocumentDef, ProofDocumentDefId> {
	//default methods from super class
	//public ProofDocumentDef findById(ProofDocumentDefId id);
	//public Long save (ProofDocumentDef e);
	//public void saveOrUpdate(ProofDocumentDef e);
    //public ProofDocumentDef merge(ProofDocumentDef e);
	//public void delete(ProofDocumentDef e);
	//public void delete(ProofDocumentDefId id);
	//public List<ProofDocumentDef> findAll ();
	
	public List<ProofDocumentDef> findProofDocumentDefByTransType(String transactionType, String transactionSubtype);

	public PaginatedResult<ProofDocumentDef> getAllForPagination(int pageNo,int pageSize, Order... order);
	
	public List<ProofDocumentDef>  findAllDistinctProofDocuments();

	//public PaginatedResult<ProofDocumentDef> findAllProofDocMatrixList(int PageNo, int PageSize) throws Exception;

}
