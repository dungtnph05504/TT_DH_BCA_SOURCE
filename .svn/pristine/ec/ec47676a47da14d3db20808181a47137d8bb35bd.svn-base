package com.nec.asia.nic.comp.dispatch.dao;

import java.util.List;

import com.nec.asia.nic.comp.dispatch.domain.DispatchQueueData;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * 
 * @author khang
 *
 */
/* 
 * Modification History:
 * 
 */
public interface DispatchDataDao extends GenericDao<DispatchQueueData, Long> {
    public List<DispatchQueueData> findAllByBatchNumber(String batchNumber);

    public DispatchQueueData findQueueData(String transactionId, String passportNo) throws DaoException;
}
