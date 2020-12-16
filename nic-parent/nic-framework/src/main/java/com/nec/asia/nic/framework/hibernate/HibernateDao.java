package com.nec.asia.nic.framework.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate4.HibernateCallback;

import com.nec.asia.nic.framework.dao.DaoException;


/**
 * @author setia_budiyono
 * @deprecated to use GenericHibernateDao.java
 */
public class HibernateDao<E, ID extends Serializable> extends AbstractHibernateDao {
	private Class<E> persistentClass;
	
	public HibernateDao() { }

	
	public List<E> findAll(){
		return this.getAllEntity(getPersistentClass());
	}
	
	public List<E> findAll(E e){
		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		criteria.add(Example.create(e));
		return (List<E>) this.getHibernateTemplate().findByCriteria(criteria);
		
	}
	
	  /** To save the entity */
	public ID save (E e) throws DaoException {
		try{
			return (ID) this.saveEntity(e);
		}catch (HibernateException he) {
			throw new DaoException (he);
		}
		
	}

    /** To save or update the entity */
	public void saveOrUpdate(E e) throws DaoException {
		try {
			this.saveOrUpdateEntity(e);			
		}catch (HibernateException he) {
			throw new DaoException (he);
		}
	}

	/** To update the entity */
	public void update(E e) throws DaoException {
		try {
			this.updateEntity(e);
		}catch (HibernateException he) {
			throw new DaoException (he);
		}
	}
	
	
    public E findById(ID id) throws DaoException {
    	try {
    		E e = this.getHibernateTemplate().get(getPersistentClass(), id);
    		return e;
    	}catch (HibernateException he) {
    		throw new DaoException (he);
    	}
    }

  
    /** To delete the entity */
	public void delete(E e)  throws DaoException {
		try {
			this.deleteEntity(e);			
		}catch (HibernateException he) {
			throw new DaoException (he);
		}
	}
	
    /** To delete the entity by ID */
	public void delete(ID id)  throws DaoException {
		this.getHibernateTemplate().get(getPersistentClass(), id);
	}
	
	
	public E merge(E e){
    	return this.getHibernateTemplate().merge(e);
	}
	
	
	
	@SuppressWarnings("unchecked")
	protected List<E> findAll(final DetachedCriteria detachedCriteria) {
		return (List<E>) getHibernateTemplate().execute(
				new HibernateCallback<List<E>>() {
					public List<E> doInHibernate(Session session)
							throws HibernateException {
						Criteria c = detachedCriteria.getExecutableCriteria(session);  
						c.setResultTransformer(Criteria.ROOT_ENTITY);    
						List<E> list = c.list();
						return list;
					}  
		        });  
	}
	
	protected Class<E> getPersistentClass() {
		if (this.persistentClass==null){
			this.persistentClass = 
				(Class<E>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return persistentClass;
	}
	
	
}
