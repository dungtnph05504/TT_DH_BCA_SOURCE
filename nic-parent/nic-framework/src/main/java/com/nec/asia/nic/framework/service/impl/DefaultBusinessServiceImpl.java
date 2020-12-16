package com.nec.asia.nic.framework.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * Default service implementation by wrapping the GenericHibernateDao
 * 
 * @see GenericHibernateDao
 * @author bright_zheng
 * @param <T>
 */
@Service("defaultBusinessService")
@Transactional
public class DefaultBusinessServiceImpl<E, ID extends Serializable, DAO extends GenericDao<E, ID>> implements BusinessService<E, ID> {

    /** logger can be used in all sub class */
    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** this 'dao' is the default dao for the specific business service */
    protected DAO dao;

    /** must define the non-argument constructor because we use CGLib proxy */
    public DefaultBusinessServiceImpl() {
    }

    public DefaultBusinessServiceImpl(DAO dao) {
        this.dao = dao;
    }

    public E findById(ID id) {
        return this.dao.findById(id);
    }

    public ID save(E e) {
        return this.dao.save(e);
    }

    public void saveOrUpdate(E e) {
        this.dao.saveOrUpdate(e);
    }

    public E merge(E e) {
        return this.dao.merge(e);
    }

    public void delete(E e) {
        this.dao.delete(e);
    }

    public void delete(ID id) {
        this.delete(this.findById(id));
    }

    public List<E> findAll() {
        return this.dao.findAll();
    }

    public List<E> findAll(E e) {
        return this.dao.findAll(e);
    }

    /*
     * public PaginatedResult<E> findByPagination (E e, int pageNo, int pageSize){
     * return this.dao.findByPagination(e, pageNo, pageSize);
     * }
     */
    public PaginatedResult<E> findAllForPagination(E e, int pageNo, int pageSize) {
        return this.dao.findAllForPagination(e, pageNo, pageSize);
    }

    @Override
    public PaginatedResult<E> findAllForPagination(int pageNo, int pageSize, Order... order) {

        return this.dao.findAllForPagination(pageNo, pageSize, order);
    }

    @Override
    public PaginatedResult<E> findAllForPagination(E e, int pageNo, int pageSize, Order... order) {

        return this.dao.findAllForPagination(e, pageNo, pageSize, order);
    }

    @Override
    public PaginatedResult<E> findAllForPagination(E e, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike, Order... orders) {

        return this.dao.findAllForPagination(e, pageNo, pageSize, ignoreCase, enableLike, orders);
    }

    @Override
    public long count(E e) {
        return this.dao.count(e);
    }

    /**
     * @return the dao
     */
    public DAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(DAO dao) {
        this.dao = dao;
    }

}
