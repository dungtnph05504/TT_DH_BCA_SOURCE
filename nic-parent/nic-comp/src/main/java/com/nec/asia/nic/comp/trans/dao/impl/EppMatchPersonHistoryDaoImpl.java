package com.nec.asia.nic.comp.trans.dao.impl;

import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.EppMatchPersonHistoryDao;
import com.nec.asia.nic.comp.trans.domain.EppMatchPersonHistory;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository
public class EppMatchPersonHistoryDaoImpl extends
		GenericHibernateDao<EppMatchPersonHistory, Long> implements
		EppMatchPersonHistoryDao {

	@Override
	public boolean saveMatchPersonHistory(
			EppMatchPersonHistory eppMatchPersonHistory) throws Exception {
		try {
			this.save(eppMatchPersonHistory);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

}
