/**
 * 
 */
package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 12, 2013
 */
/*
 * Modification History:
 * 07 April 2014 (Peddi Swapna): Added getTransactionProofDocuments method to get the proof documents.
 */
public interface NicTransactionAttachmentService extends BusinessService<NicTransactionAttachment, Long> {
	
	public NicTransactionAttachment findById(Long transactionDocId);
	
	public BaseModelList<NicTransactionAttachment> findNicTransactionAttachments(String transactionId, String docType, String serialNo);
	
	public List<NicTransactionAttachment> findFacialTEById(String transactionId, String docType, String remark);
	
	/* To get the user declaration document -- Added by Sailaja on 24/09/2013 */
	byte[] getUserdeclarationPdf(List<NicTransactionAttachment> docList);

	public List<NicTransactionAttachment> getNicTransactionAttachments(String transactionId, String[] docTypes, String[] serialNums) throws Exception;

	public List<NicTransactionAttachment> getTransactionProofDocuments(String txnId, String[] excludedDocTypes) throws Exception;	
	
	public BaseModelList<NicTransactionAttachment> getNicTransactionAttachmentsOutTypes(String transactionId, String[] docTypes) throws Exception;

	public List<NicTransactionAttachment> findAttachmentsByTranId(String transactionId) throws Exception;
	
	public List<NicTransactionAttachment> findNicTransactionAttachs(String transactionId, String docType, String[] serialNo)throws Exception;
}
