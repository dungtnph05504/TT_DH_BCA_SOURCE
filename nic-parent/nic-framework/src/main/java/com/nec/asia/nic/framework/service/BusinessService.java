package com.nec.asia.nic.framework.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;

/**
 * Business service interface for all business service implementation
 * 
 * @author bright_zheng
 *
 * @param <T>: the domain class
 */
public interface BusinessService<E, ID extends Serializable> {	

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
	
	/** To find all the entities with an example */
	public List<E> findAll (E e);
	
	
	public PaginatedResult<E> findAllForPagination(int pageNo, int pageSize,Order... order);
	
	/** To find all the entities for paginated Result by an example entity. */
	public PaginatedResult<E> findAllForPagination (E e, int pageNo, int pageSize,Order... order);
	
	public PaginatedResult<E> findAllForPagination(E e, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike,Order ...orders);
	
	public long count(E e);
}
