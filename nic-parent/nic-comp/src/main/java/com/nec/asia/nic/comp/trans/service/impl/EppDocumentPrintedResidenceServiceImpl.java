package com.nec.asia.nic.comp.trans.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.EppDocumentPrintedResidenceDao;
import com.nec.asia.nic.comp.trans.domain.EppDocumentPrintedResidence;
import com.nec.asia.nic.comp.trans.service.EppDocumentPrintedResidenceService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("eppDocumentPrintedResidenceService")
public class EppDocumentPrintedResidenceServiceImpl extends DefaultBusinessServiceImpl<EppDocumentPrintedResidence, Long, EppDocumentPrintedResidenceDao>
	implements EppDocumentPrintedResidenceService{

	@Autowired
	private EppDocumentPrintedResidenceDao dao;
	
	@Override
	public Long saveDocPrintedResidence(
			EppDocumentPrintedResidence eppDocumentPrintedResidence)
			throws Exception {
		try {
			return this.dao.saveDocPrintedResidence(eppDocumentPrintedResidence);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - saveDocPrintedResidence thất bại.");
		}
	}
	
}
