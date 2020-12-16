/**
 *
 */
package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.idserver.agent.service.exception.IdserverAgentServiceException;
import com.nec.asia.idserver.spec.itf.transfer.udb.IDBImageDto;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.Constants;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 12, 2013
 */

/*
 * Modification History:
 * 
 * 21 Mar 2016 (chris): Use idserverAgent to retrieve Photo, Fingerprint if the BLOB is empty.
 */

@Service("nicTransactionDocumentService")
public class NicTransactionAttachmentServiceImpl
		extends DefaultBusinessServiceImpl<NicTransactionAttachment, Long, NicTransactionAttachmentDao>
		implements NicTransactionAttachmentService {

	@Autowired
	private IdserverAgentService idserverAgentService;

	/** must define the non-argument constructor because we use CGLib proxy */
	public NicTransactionAttachmentServiceImpl(){
	}

	@Autowired
	public NicTransactionAttachmentServiceImpl(NicTransactionAttachmentDao dao){
		this.dao = dao;
	}

	@Autowired
	public NicTransactionAttachmentDao transactionDocumentDao = null;

	@Override
	public NicTransactionAttachment findById(Long transactionDocId){
		NicTransactionAttachment  result = null;
		try{
			result =dao.findById(transactionDocId);
			
			//to load from BAF DB
			result = this.getAttachmentByRefId(result);
		}catch(Exception ex){
			logger.error("Error occurred while getting the candidate details list-- Start Investigation. Exception: "+ex.getMessage());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService#findNicTransactionAttachments(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public BaseModelList<NicTransactionAttachment> findNicTransactionAttachments(
			String transactionId, String docType, String serialNo) {
		try{
			BaseModelList<NicTransactionAttachment> result = this.dao.findNicTransactionAttachments(transactionId, docType, serialNo);

			for (NicTransactionAttachment doc : result.getListModel()) {
				//to load from BAF DB
				doc = this.getAttachmentByRefId(doc);
			}
			return result;
		}catch(Exception ex){
			logger.error("Error occurred while getting the candidate photo, signature and fingerprints -- Start Investigation. Exception: "+ex.getMessage());
			return new BaseModelList<NicTransactionAttachment>(null, false, CreateMessageException.createMessageException(ex) + " - findNicTransactionAttachments - thất bại.");
		}
	}


	@Override
	public byte[] getUserdeclarationPdf(List<NicTransactionAttachment> docList) {
		byte[] doc = null;
		for (NicTransactionAttachment document : docList) {
			if (document.getDocData() != null
					&& Constants.DOC_TYPE_USER_DECL
							.equalsIgnoreCase(document.getDocType())
							|| Constants.DOC_TYPE_COLLECTION_SLIP
							.equalsIgnoreCase(document.getDocType())) {
				//System.out.println("document>>"+document.getDocData());
				doc = document.getDocData();
			}
		}
		return doc;
	}

	@Override
	public List<NicTransactionAttachment> getNicTransactionAttachments(String transactionId, String[] docTypes, String[] serialNums) throws Exception{
		try{
			List<NicTransactionAttachment> result =  dao.findNicTransactionAttachments(transactionId, docTypes, serialNums);

			for (NicTransactionAttachment doc : result) {
				//to load from BAF DB
				doc = this.getAttachmentByRefId(doc);
			}
			return result;
		}catch(Exception ex){
			logger.error("Error occurred while getting the transaction documents. Exception: "+ex.getMessage());
			throw new Exception("Error occurred while getting the transaction documents. Exception: "+ex.getMessage());
		}
	}
	
	@Override
	public BaseModelList<NicTransactionAttachment> getNicTransactionAttachmentsOutTypes(String transactionId, String[] docTypes) throws Exception{
		try{
			List<NicTransactionAttachment> result =  dao.findNicTransactionAttachmentsOutTypes(transactionId, docTypes).getListModel();

			for (NicTransactionAttachment doc : result) {
				//to load from BAF DB
				doc = this.getAttachmentByRefId(doc);
			}
			return new BaseModelList<NicTransactionAttachment>(result, true, null);
		}catch(Exception ex){
			logger.error("Error occurred while getting the transaction documents. Exception: "+ex.getMessage());
			return new BaseModelList<NicTransactionAttachment>(null, false, CreateMessageException.createMessageException(ex) + " - findNicTransactionAttachmentsOutTypes - " + transactionId + " - thất bại.");
		}
	}

	@Override
	public List<NicTransactionAttachment> getTransactionProofDocuments(String txnId, String[] excludedDocTypes) throws Exception {
		try{
			return dao.getTransactionProofDocuments(txnId, excludedDocTypes);
		}catch(Exception ex){
			logger.error("Error occurred while getting the transaction documents. Exception: "+ex.getMessage());
			throw new Exception("Error occurred while getting the transaction documents. Exception: "+ex.getMessage());
		}
	}
	
	private NicTransactionAttachment getAttachmentByRefId(NicTransactionAttachment doc) throws IdserverAgentServiceException {
		if (doc.getDocData()==null && StringUtils.isNotBlank(doc.getStorageRefId())) {
			
			if (StringUtils.equals(NicTransactionAttachment.DOC_TYPE_FINGERPRINT, doc.getDocType())) {
				IDBImageDto imageDto = idserverAgentService.getFingerprintImage(doc.getStorageRefId());
				if (imageDto!=null && imageDto.getImageData()!=null) {
					doc.setDocData(imageDto.getImageData());
				}
			} else if (StringUtils.equals(NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, doc.getDocType())) {
				IDBImageDto imageDto = idserverAgentService.getFacialImage(doc.getStorageRefId());
				if (imageDto!=null && imageDto.getImageData()!=null) {
					doc.setDocData(imageDto.getImageData());
				}
			} else if (StringUtils.equals(NicTransactionAttachment.DOC_TYPE_TPL, doc.getDocType())) {
				IDBImageDto imageDto = idserverAgentService.getTemplateImage(doc.getStorageRefId());
				if (imageDto!=null && imageDto.getImageData()!=null) {
					doc.setDocData(imageDto.getImageData());
				}
			}
			logger.info("doc.getStorageRefId():{}, doc.getDocType():{}, retrieveStatus:{} ", new Object[] {doc.getStorageRefId(), doc.getDocType(), doc.getDocData()!=null} );
		}
		
		return doc;
	}

	@Override
	public List<NicTransactionAttachment> findFacialTEById(
			String transactionId, String docType, String remark) {
		List<NicTransactionAttachment> result = new ArrayList<NicTransactionAttachment>();
		try{
			result = dao.findFacialTEById(transactionId, docType, remark);

			for (NicTransactionAttachment doc : result) {
				//to load from BAF DB
				doc = this.getAttachmentByRefId(doc);
			}
		}catch(Exception ex){
			logger.error("Error occurred while getting the candidate photo, signature and fingerprints -- Start Investigation. Exception: "+ex.getMessage());
		}
		return result;
	}

	@Override
	public List<NicTransactionAttachment> findAttachmentsByTranId(
			String transactionId) throws Exception {
		List<NicTransactionAttachment> listTranAttachments = null;
		try {
			if (StringUtils.isNotBlank(transactionId)) {
				listTranAttachments = this.dao.findAttachmentsByTranId(transactionId);
			}
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - findAttachmentsByTranId thất bại.");
		}
		return listTranAttachments;
	}

	@Override
	public List<NicTransactionAttachment> findNicTransactionAttachs(
			String transactionId, String docType, String[] serialNo) throws Exception {
		try{
			List<NicTransactionAttachment> result = this.dao.findNicTransactionAttachments(transactionId, docType, serialNo);
			return result;
		}catch(Exception ex){
			throw new Exception(CreateMessageException.createMessageException(ex) + " - findNicTransactionAttachments - thất bại.");
		}
	}

}