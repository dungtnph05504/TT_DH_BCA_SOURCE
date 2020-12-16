package com.nec.asia.nic.comp.dispatch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.dispatch.dao.DispatchDataDao;
import com.nec.asia.nic.comp.dispatch.domain.DispatchQueueData;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
* 
* @author khang
*
*/

/* 
* Modification History:
* 
*/
@Repository("dispatchDataDao")
public class DispatchDataDaoImpl extends
        GenericHibernateDao<DispatchQueueData, Long> implements DispatchDataDao {

    @Override
    public void saveOrUpdate(DispatchQueueData e) {
        super.saveOrUpdate(e);
    }
    
    @Override
    public List<DispatchQueueData> findAllByBatchNumber(String batchNumber) {
        List<DispatchQueueData> dispatchQueueDataList = new ArrayList<DispatchQueueData>();
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
        if (StringUtils.isNotEmpty(batchNumber)) {
            detachedCriteria.add(Restrictions.eq("batchNumber", batchNumber));
            
            List<DispatchQueueData> resultList = this.findAll(detachedCriteria);
            dispatchQueueDataList.addAll(resultList);
        }
        logger.info("[findAllByBatchNumber][{}] size:{}", new Object[] { batchNumber, dispatchQueueDataList.size()});
        return dispatchQueueDataList;
    }
    
    @Override
    public DispatchQueueData findQueueData(String transactionId, String passportNo) throws DaoException{
        DispatchQueueData dispatchQueueData = null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
        if (StringUtils.isNotBlank(transactionId)) {
            detachedCriteria.add(Restrictions.eq("transactionId", transactionId));
        }
        if(StringUtils.isNotBlank(passportNo)) {
            detachedCriteria.add(Restrictions.eq("passportNo", passportNo));
        }

        List<DispatchQueueData> resultList = this.findAll(detachedCriteria);
        if (CollectionUtils.isNotEmpty(resultList)) {
        	dispatchQueueData = resultList.get(0);
        }
        logger.info("[findQueueData] Transaction Id: [{}] PassportNo: [{}] size:{}", new Object[] { transactionId, passportNo, resultList.size()});

        return dispatchQueueData;
    }
}
