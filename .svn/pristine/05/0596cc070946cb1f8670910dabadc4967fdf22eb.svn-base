package com.nec.asia.nic.comp.trans.dao;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.framework.dao.GenericDao;
/*
 * Modification History:
 * 
 * 16 April 2014 (Peddi Swapna): Added getTransactionProofDocuments method to get all the proof documents.
 * 
 */
public interface NicTransactionAttachmentDao extends GenericDao<NicTransactionAttachment, Long> {

	public NicTransaction getTransactiondocuments(NicTransaction transObj);
	public BaseModelList<NicTransactionAttachment> findNicTransactionAttachments(String transactionId, String docType, String serialNo);
	//default methods from super class
	//public NicTransactionAttachment findById(long transactionDocId);
	//public Long save (NicTransactionAttachment e);
	//public void saveOrUpdate(NicTransactionAttachment e);
    //public NicTransactionAttachment merge(NicTransactionAttachment e);
	//public void delete(NicTransactionAttachment e);
	//public void delete(Long transactionDocId);
	//public List<NicTransactionAttachment> findAll ();
	List<Object[]> getNicTransactionAttachmentByDocType(String doctype, String regSiteCode, String transactionId, String surnameFull,
			String status, String surnameAtBirth, String otherName,
			String gender, Date dateOfBirth);
	public List<NicTransactionAttachment> findNicTransactionAttachments(String transactionId, String[] docTypes, String[] serialNums) throws Exception;
	public List<NicTransactionAttachment> getTransactionProofDocuments(String txnId, String[] docTypes) throws Exception;
	public List<NicTransactionAttachment> findFacialTEById(String transactionId, String docType, String remark);
	public BaseModelList<NicTransactionAttachment> findNicTransactionAttachmentsOutTypes(String transactionId, String[] docTypes) throws Exception;
	public BaseModelList<NicTransactionAttachment> findNicTransactionAttachmentsInTypes(String transactionId, String[] docTypes) throws Exception;
	public BaseModelSingle<Long> saveTranAttachment(NicTransactionAttachment tranAttachment);
	public BaseModelSingle<Void> deleteTranAttachment(NicTransactionAttachment attach);
	
	public List<NicTransactionAttachment> findAttachmentsByTranId(String transactionId) throws Exception;
	public List<NicTransactionAttachment> findNicTransactionAttachments(
			String transactionId, String docType, String[] serialNo);
}
