package com.nec.asia.nic.comp.trans.dao;

import com.nec.asia.nic.comp.trans.domain.EppMatchPersonHistory;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppMatchPersonHistoryDao extends
		GenericDao<EppMatchPersonHistory, Long> {
	public boolean saveMatchPersonHistory(EppMatchPersonHistory eppMatchPersonHistory) throws Exception;
}
