package com.nec.asia.nic.comp.trans.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.EppMatchPersonHistoryDao;
import com.nec.asia.nic.comp.trans.domain.EppMatchPersonHistory;
import com.nec.asia.nic.comp.trans.service.EppMatchPersonHistoryService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("eppMatchPersonHistoryService")
public class EppMatchPersonHistoryServiceImpl
		extends
		DefaultBusinessServiceImpl<EppMatchPersonHistory, Long, EppMatchPersonHistoryDao>
		implements EppMatchPersonHistoryService {
	
	@Autowired
	private EppMatchPersonHistoryDao dao;
	
	@Override
	public boolean saveMatchPersonHistory(
			EppMatchPersonHistory eppMatchPersonHistory) throws Exception {
		try {
			return this.dao.saveMatchPersonHistory(eppMatchPersonHistory);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - saveMatchPersonHistory thất bại.");
		}
	}

}
