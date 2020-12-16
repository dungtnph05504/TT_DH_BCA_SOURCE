package com.nec.asia.nic.comp.trans.service;

import com.nec.asia.nic.comp.trans.domain.EppMatchPersonHistory;
import com.nec.asia.nic.framework.service.BusinessService;

public interface EppMatchPersonHistoryService extends
		BusinessService<EppMatchPersonHistory, Long> {
	public boolean saveMatchPersonHistory(EppMatchPersonHistory eppMatchPersonHistory) throws Exception;
}
