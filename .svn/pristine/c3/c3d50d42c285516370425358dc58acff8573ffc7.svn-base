package com.nec.asia.nic.framework.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;

/**
 * 
 * The basic interface includes CRUD methods.
 * 
 * @author bright_zheng
 *
 * @param <E> The corresponding Entity
 * @param <ID> The ID of the Entity
 */
public interface GenericDao<E, ID extends Serializable> {
 
	/** To load by PK */
    public E findById(ID id);
    //E findById(ID id, boolean lock);

    /** To save the entity */
	public ID save (E e);

    /** To save or update the entity */
	public void saveOrUpdate(E e);

    /** To merge the entity */
    public E merge(E e);

    /** To delete the entity */
	public void delete(E e);
    /** To delete the entity by ID */
	public void delete(ID id);
	
    /** To find all the entities without any criteria */
	public List<E> findAll ();
	
	// TODO: later to tune here for framework
	/** To find all the entities with example as matching criteria */
	public List<E> findAll (E e);	
	
	//@Deprecated //To use findAllForPagination
	//public PaginatedResult<E> findByPagination(E e, int pageNo, int pageSize);
	
	/** To find all the entities without any criteria, return paginated result */
	/*
	 * Paulo Ambrocio: 
	 * added varargs Order argument to support ordering of result
	 */
	public PaginatedResult<E> findAllForPagination(int pageNo, int pageSize,Order... order);
	
	/** To find all the entities with example as matching criteria, return paginated result  */
	/*
	 * Paulo Ambrocio: 
	 * added varargs Order argument to support ordering of result
	 */
	public PaginatedResult<E> findAllForPagination(E e, int pageNo, int pageSize,Order... order);
	
	//public PaginatedResult<E> findByIdForPagination(ID id, int pageNo, int pageSize);
	/*
	 * Paulo Ambrocio: added method to interface which is available in GenericHibernateDao
	 * added varargs Order argument to support ordering of result
	 */
	public PaginatedResult<E> findAllForPagination(E e, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike,Order ...orders);

	/*
	 * Paulo Ambrocio: exposed count method
	 */
	public long count(E e);
}
