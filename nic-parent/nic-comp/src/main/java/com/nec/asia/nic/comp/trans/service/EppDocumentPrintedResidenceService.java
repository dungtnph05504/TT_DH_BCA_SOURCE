package com.nec.asia.nic.comp.trans.service;

import com.nec.asia.nic.comp.trans.domain.EppDocumentPrintedResidence;
import com.nec.asia.nic.framework.service.BusinessService;

public interface EppDocumentPrintedResidenceService extends
		BusinessService<EppDocumentPrintedResidence, Long> {
	public Long saveDocPrintedResidence(EppDocumentPrintedResidence eppDocumentPrintedResidence) throws Exception;
}
