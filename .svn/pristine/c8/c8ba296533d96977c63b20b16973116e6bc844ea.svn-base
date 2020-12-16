/**
 * 
 */
package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppIdentification;
import com.nec.asia.nic.comp.trans.domain.EppIdentificationAttachmnt;
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
public interface NicIdentificationService extends BusinessService<EppIdentification, Long> {
	
	public List<EppIdentification> findByNin (String nin);
	
	public List<EppIdentificationAttachmnt> findAttachmentById(Long id);

	public void cleanOldData(List<EppIdentification> hitByCriterion) throws Exception;

	public void saveIdentification(EppIdentification eppIdentification) throws Exception;

	public List<EppIdentification> findByTranId(String transactionId) throws Exception;
}
