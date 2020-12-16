package com.nec.asia.nic.comp.trans.dao.impl;

import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.EppDocumentPrintedResidenceDao;
import com.nec.asia.nic.comp.trans.domain.EppDocumentPrintedResidence;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository
public class EppDocumentPrintedResidenceDaoImpl extends
		GenericHibernateDao<EppDocumentPrintedResidence, Long> implements
		EppDocumentPrintedResidenceDao{

	@Override
	public Long saveDocPrintedResidence(
			EppDocumentPrintedResidence eppDocumentPrintedResidence)
			throws Exception {
		try {
			return this.save(eppDocumentPrintedResidence);
		} catch (Exception e) {
			throw e;
		}
	}
	
}
