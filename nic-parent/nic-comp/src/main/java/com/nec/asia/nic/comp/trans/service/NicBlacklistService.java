/**
 * 
 */
package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppBlacklistAttachment;
import com.nec.asia.nic.comp.trans.domain.EppIdentificationAttachmnt;
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
public interface NicBlacklistService extends BusinessService<EppBlacklist, Long> {
	
	public List<EppBlacklist> findByNin (String nin);
	
	public List<EppBlacklist> findByInfoPerson (String fullname, String dob);
	
	public List<EppBlacklistAttachment> findAttachmentById(Long id);
	
	public List<EppBlacklist> findByMutilCriterion(String fullname, String dob, String nin);
}
