package com.nec.asia.nic.comp.dispatch.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.dispatch.dao.DispatchPackageDetailDao;
import com.nec.asia.nic.comp.dispatch.domain.DispatchPackageDetail;
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
@Repository("dispatchPackageDetailDao")
public class DispatchPackageDetailDaoImpl extends
        GenericHibernateDao<DispatchPackageDetail, Long> implements DispatchPackageDetailDao {

    @Override
    public void saveOrUpdate(DispatchPackageDetail e) {
        super.saveOrUpdate(e);
    }

    @Override
    public DispatchPackageDetail findPackageDetail(String transactionId, String passportNo) throws DaoException{
        DispatchPackageDetail dispatchPackageDetail = null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
        if (StringUtils.isNotBlank(transactionId)) {
            detachedCriteria.add(Restrictions.eq("transactionId", transactionId));
        }
        if(StringUtils.isNotBlank(passportNo)) {
            detachedCriteria.add(Restrictions.eq("passportNo", passportNo));
        }

        List<DispatchPackageDetail> resultList = this.findAll(detachedCriteria);
        if (CollectionUtils.isNotEmpty(resultList)) {
        	dispatchPackageDetail = resultList.get(0);
        }
        logger.info("[findByTransactionId] Transaction Id: [{}] PassportNo: [{}] size:{}", new Object[] { transactionId, passportNo, resultList.size()});

        return dispatchPackageDetail;
    }
}
