package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.trans.dao.DocumentHistoryDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicDocumentHistory;
import com.nec.asia.nic.comp.trans.service.DocumentHistoryService;
import com.nec.asia.nic.comp.trans.service.exception.DocumentDataServiceException;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * @author khang
 */
/*
 * Modification History: 15 Jan 2016 (khang): init service implementations
 */
@Service("documentHistoryService")
public class DocumentHistoryServiceImpl
		extends
		DefaultBusinessServiceImpl<NicDocumentHistory, Long, DocumentHistoryDao>
		implements DocumentHistoryService {

	/** must define the non-argument constructor because we use CGLib proxy */
	public DocumentHistoryServiceImpl() {
	}

	@Autowired
	public DocumentHistoryServiceImpl(DocumentHistoryDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public List<NicDocumentHistory> findAllByPassportNo(String passportNo) {
		List<NicDocumentHistory> documentHistoryList = this.dao
				.findAllByPassportNo(passportNo);
		return documentHistoryList;
	}

	@Override
	public int addDocumentHistory(List<NicDocumentHistory> documentHistoryList)
			throws Exception {
		int count = 0;
		try {
			count = this.dao.addDocumentHistory(documentHistoryList);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return count;
	}

	@Override
	public BaseModelSingle<Boolean> saveDocumentHistory(
			NicDocumentHistory nicDocumentHistory) {
		try {
			return this.dao.saveDocumentHistory(nicDocumentHistory);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException.createMessageException(e)
							+ " - saveDocumentHistory - thất bại.");
		}
	}
}
