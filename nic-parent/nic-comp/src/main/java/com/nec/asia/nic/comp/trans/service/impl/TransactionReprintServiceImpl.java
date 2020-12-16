package com.nec.asia.nic.comp.trans.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.NicTransactionReprintDao;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReprint;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReprintId;
import com.nec.asia.nic.comp.trans.service.TransactionReprintService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/*
 * Modification History:
 */

@Service("transactionReprintService")
public class TransactionReprintServiceImpl extends DefaultBusinessServiceImpl<NicTransactionReprint, NicTransactionReprintId, NicTransactionReprintDao> implements TransactionReprintService {
	public TransactionReprintServiceImpl() {
	}

	@Autowired
	public TransactionReprintServiceImpl(NicTransactionReprintDao dao) {
		this.dao = dao;
	}
}
