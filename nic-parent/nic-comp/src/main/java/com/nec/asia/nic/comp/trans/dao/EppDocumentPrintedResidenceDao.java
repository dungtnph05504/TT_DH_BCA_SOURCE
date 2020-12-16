package com.nec.asia.nic.comp.trans.dao;

import com.nec.asia.nic.comp.trans.domain.EppDocumentPrintedResidence;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppDocumentPrintedResidenceDao extends GenericDao<EppDocumentPrintedResidence, Long>{
	public Long saveDocPrintedResidence(EppDocumentPrintedResidence eppDocumentPrintedResidence) throws Exception;
}
