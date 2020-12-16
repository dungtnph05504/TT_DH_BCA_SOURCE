package com.nec.asia.nic.framework.admin.code.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDef;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDefId;
import com.nec.asia.nic.framework.admin.code.dto.ProofDocumentDefDTO;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * 
 * @author chris_wong
 *
 */
/*
 * Modification History:
 *  * 
 * 2 Oct 2013 (Peddi Swapna): Modified getAllForPagination method to implement the external sorting.
 * 
 */
public interface ProofDocumentDefService extends BusinessService<ProofDocumentDef, ProofDocumentDefId>{
	//default methods from super class
//	public ProofDocumentDef findById(ProofDocumentDefId id);
//	public ProofDocumentDefId save(ProofDocumentDef entity);
//	public void saveOrUpdate(ProofDocumentDef entity);
//	public ProofDocumentDef merge(ProofDocumentDef entity);
//	public void delete(ProofDocumentDef entity);
//	public void delete(ProofDocumentDefId id);
//	public List<ProofDocumentDef> findAll ();
//	public List<ProofDocumentDef> findAll (ProofDocumentDef exampleEntity);
//	public PaginatedResult<ProofDocumentDef> findAllForPagination (ProofDocumentDef exampleEntity, int pageNo, int pageSize);
	
	public List<ProofDocumentDef> findAllByTransType(String transactionType, String transactionSubtype);
	
	public PaginatedResult<ProofDocumentDefDTO> findAllForPagination(int pageNo, int pageSize) throws Exception;
	
	public Map<String, Object> findProofDocumentDefForMatrix() throws Exception;
	
	public List<CodeValues>getCodeValue(String codeId);
	public String updateProofDocMatrix(ProofDocumentDef proofDocumentDef);
	public String deleteProofDocMatrix(ProofDocumentDef proofDocumentDef);
	public PaginatedResult<ProofDocumentDef> getAllForPagination(int pageNo,int pageSize,Order... order);
	public List<ProofDocumentDef>  findAllDistinctProofDocuments();
		
	
	
}
