package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicTransactionReprint;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReprintId;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * @author khang
 * @Company: NEC Asia Pacific Ltd
 * @Since: 31-May-2016
 */

/*
 * Modification History:
 */
public interface NicTransactionReprintDao extends GenericDao<NicTransactionReprint, NicTransactionReprintId> {

	public List<NicTransactionReprint> findByTransactionId(String transactionId) throws DaoException;
	public NicTransactionReprint findByRefArn(String refArn) throws DaoException;
	public NicTransactionReprint getLatestReprintByTransactionId(String transactionId) throws DaoException;
}
