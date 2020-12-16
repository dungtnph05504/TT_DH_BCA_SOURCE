package com.nec.asia.nic.framework.dao;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * The basic interface includes CRUD methods.
 * 
 * @author bright_zheng
 *
 * @param <E> The corresponding Entity
 * @param <ID> The ID of the Entity
 */
public interface DefaultDao<E, ID extends Serializable> {
 
	/** To load by PK */
    public E findById(ID id) throws DaoException;

    /** To save the entity */
	public ID save (E e) throws DaoException;

    /** To save or update the entity */
	public void saveOrUpdate(E e) throws DaoException;
	
	 /** To update the entity */
	public void update(E e) throws DaoException;
  
    /** To delete the entity */
	public void delete(E e) throws DaoException;
    /** To delete the entity by ID */
	public void delete(ID id) throws DaoException;
	
    /** To find all the entities without any criteria */
	public List<E> findAll () throws DaoException;
	
	// TODO: later to tune here for framework
	/** To find all the entities with example as matching criteria */
	public List<E> findAll (E e) throws DaoException;	
	
	public E merge(E e) throws DaoException;
	
}
