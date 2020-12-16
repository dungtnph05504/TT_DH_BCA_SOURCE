package com.nec.asia.nic.comp.perso.dao.impl;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.perso.dao.PersoInfoDao;
import com.nec.asia.nic.comp.perso.domain.PersoDispatchInfo;
import com.nec.asia.nic.comp.perso.domain.PersoStatus;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
 * @author khang
 */
/*
 * Modification History:
 */

@Repository("persoInfoDao")
public class PersoInfoDaoImpl extends GenericHibernateDao<Object, Integer> implements PersoInfoDao {

    /**
     * Create Perso Dispatch Info
     * 
     * @param entity
     * @throws DaoException
     */
    @Override
    public void createPersoDispatchInfo(PersoDispatchInfo entity) throws DaoException {
        try {
            this.getHibernateTemplate().save(entity);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Create Perso Status
     * 
     * @param entity
     * @throws DaoException
     */
    @Override
    public void createPersoStatus(PersoStatus entity) throws DaoException {
        try {
            this.getHibernateTemplate().save(entity);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }
}
