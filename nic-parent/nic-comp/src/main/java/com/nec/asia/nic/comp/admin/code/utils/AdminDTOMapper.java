package com.nec.asia.nic.comp.admin.code.utils;

import org.springframework.stereotype.Component;

import com.nec.asia.nic.dx.admin.Payment;
import com.nec.asia.nic.dx.admin.ProofDocument;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDef;
import com.nec.asia.nic.utils.BaseDTOMapper;

/* 
 * Modification History:
 *
 * 15 Jun 2017 (chris): code merge, clean up
 */
@Component
public class AdminDTOMapper extends BaseDTOMapper {

	//ProofDocumentDef DBO to DTO
	public ProofDocument parseProofDocumentDefDTO(ProofDocumentDef proofDocumentRefDBO) {
		ProofDocument proofDocumentDefDTO = new ProofDocument();
		proofDocumentDefDTO.setDocumentID(proofDocumentRefDBO.getId().getDocumentId());
	    proofDocumentDefDTO.setDocumentName(proofDocumentRefDBO.getDocumentDesc());
	    proofDocumentDefDTO.setRequireIndicator(proofDocumentRefDBO.getRequireIndicator());
	    proofDocumentDefDTO.setTransactionType(proofDocumentRefDBO.getId().getTransactionType());
	    proofDocumentDefDTO.setTransactionSubtype(proofDocumentRefDBO.getId().getTransactionSubtype());
//		proofDocumentDefDTO.setCreateBy(proofDocumentRefDBO.getCreateBy());
//		proofDocumentDefDTO.setCreateDate(proofDocumentRefDBO.getCreateDate());
//		proofDocumentDefDTO.setCreateWkstnID(proofDocumentRefDBO.getCreateWkstnId());
//		proofDocumentDefDTO.setUpdateBy(proofDocumentRefDBO.getUpdateBy());
//		proofDocumentDefDTO.setUpdateDate(proofDocumentRefDBO.getUpdateDate());
//		proofDocumentDefDTO.setUpdateWkstnID(proofDocumentRefDBO.getUpdateWkstnId());
		return proofDocumentDefDTO;
	}

	public Payment parsePaymentDefDTO(PaymentDef paymentRefDBO) {
		Payment paymentDefDTO = new Payment();
		
		paymentDefDTO.setTransactionType(paymentRefDBO.getId().getTransactionType());
		paymentDefDTO.setTransactionSubtype(paymentRefDBO.getId().getTransactionSubtype());
		paymentDefDTO.setNoOfTimeLost(paymentRefDBO.getId().getNoOfTimeLost());
		paymentDefDTO.setFeeAmount(paymentRefDBO.getFeeAmount());
		paymentDefDTO.setReduceRateFlag(this.convertBooleanToFlag(paymentRefDBO.getReduceRateFlag()));
		paymentDefDTO.setWaivableFlag(this.convertBooleanToFlag(paymentRefDBO.getWaivableFlag()));
		//paymentDefDTO.setReduceRateFeeAmount(); to at service layer
		return paymentDefDTO;
	}
}
