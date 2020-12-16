package com.nec.asia.nic.framework.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * Generic Hibernate DAO implementation
 * 
 * @author bright_zheng
 * @param <E>
 * @param <ID>
 */
/*
 * Modification History:
 * refactored by Ambrocio, Paulo : to enable handling of detached objects June 25,2013
 * 21-Jan-2016 (Khang): Update findAllForPagination() to use session from Hibernate Template instead of use new session
 */
@Transactional(readOnly = false)
@Repository("genericHibernateDao")
public abstract class GenericHibernateDao<E, ID extends Serializable> implements GenericDao<E, ID> {

	/** logger can be used in all sub class */
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected boolean isSupportSoftDelete = false;

	public E findById(ID id) {
		E e = this.hibernateTemplate.get(getPersistentClass(), id);
		//this.hibernateTemplate.initialize(e);
		return e;
	}

	@SuppressWarnings("unchecked")
	public ID save(E e) {
		ID result = null;
		result = (ID) this.hibernateTemplate.save(e);
		E entity = this.hibernateTemplate.load(getPersistentClass(), result);
		hibernateTemplate.flush();
		hibernateTemplate.evict(entity);
		return result;
	}

	public void saveOrUpdate(E e) {

		E entity = hibernateTemplate.merge(e);
		this.hibernateTemplate.saveOrUpdate(entity);
		hibernateTemplate.flush();
		hibernateTemplate.evict(entity);
	}

	public E merge(E e) {
		E entity = this.hibernateTemplate.merge(e);
		hibernateTemplate.flush();
		hibernateTemplate.evict(entity);
		return entity;
	}

	public void delete(E e) {
		E merged = hibernateTemplate.merge(e);
		hibernateTemplate.delete(merged);
		hibernateTemplate.flush();
	}

	public void delete(ID id) {

		this.delete(this.findById(id));
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		List<E> res = (List<E>) this.hibernateTemplate.find("from " + getPersistentClass().getName());
		if (res != null)
			for (E entity : res) {
				hibernateTemplate.evict(entity);
			}
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll(E e) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		criteria.add(Example.create(e));
		List<E> res = (List<E>) this.hibernateTemplate.findByCriteria(criteria);
		if (res != null)
			for (E entity : res) {
				hibernateTemplate.evict(entity);
			}
		return res;
		/*
		 * Example example = Example.create(e)
		 * .excludeZeroes() //excludeZeroes
		 * //.excludeProperty("color") //exclude color property
		 * //.ignoreCase() //
		 * .enableLike(); //
		 * Criteria criteria = this.sessionFactory.getCurrentSession()
		 * .createCriteria(getPersistentClass())
		 * .add(example);
		 * return criteria.list();
		 */
	}
	
	//Phúc thêm 5/4/2019
	@SuppressWarnings("unchecked")
	protected List<E> findAllOrder(final DetachedCriteria detachedCriteria, Order... orders) {
		for (Order order : orders) {
			detachedCriteria.addOrder(order);
		}
		Session session = null;

		try {

			session = this.openSession();
			Criteria c = detachedCriteria.getExecutableCriteria(session);
			c.setResultTransformer(Criteria.ROOT_ENTITY);
			List<E> res = c.list();
			if (res != null)
				for (E entity : res) {
					hibernateTemplate.evict(entity);
				}
			//logger.debug("result size: {}", new Object[]{res.size()});
			return res;
			//		return (List<E>) getHibernateTemplate().execute(
			//				new HibernateCallback<List<E>>() {
			//					public List<E> doInHibernate(Session session)
			//							throws HibernateException {
			//						Criteria c = detachedCriteria.getExecutableCriteria(session);  
			//						c.setResultTransformer(Criteria.ROOT_ENTITY);    
			//						List<E> res = c.list();
			//						if(res!=null)
			//							for(E entity:res){
			//								hibernateTemplate.evict(entity);
			//							}
			//						//logger.debug("result size: {}", new Object[]{res.size()});
			//						return res;
			//					}  
			//		        });  

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	protected List<E> findAll(final DetachedCriteria detachedCriteria) {
		
		Session session = null;

		try {

			session = this.openSession();
			Criteria c = detachedCriteria.getExecutableCriteria(session);
			c.setResultTransformer(Criteria.ROOT_ENTITY);
			List<E> res = c.list();
			if (res != null)
				for (E entity : res) {
					hibernateTemplate.evict(entity);
				}
			//logger.debug("result size: {}", new Object[]{res.size()});
			return res;
			//		return (List<E>) getHibernateTemplate().execute(
			//				new HibernateCallback<List<E>>() {
			//					public List<E> doInHibernate(Session session)
			//							throws HibernateException {
			//						Criteria c = detachedCriteria.getExecutableCriteria(session);  
			//						c.setResultTransformer(Criteria.ROOT_ENTITY);    
			//						List<E> res = c.list();
			//						if(res!=null)
			//							for(E entity:res){
			//								hibernateTemplate.evict(entity);
			//							}
			//						//logger.debug("result size: {}", new Object[]{res.size()});
			//						return res;
			//					}  
			//		        });  

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	/*
	 * public PaginatedResult<E> findByPagination(E e, int pageNo, int pageSize){
	 * return null;
	 * }
	 */

	public PaginatedResult<E> findAllForPagination(int pageNo, int pageSize, Order... orders) {
		String name = getPersistentClass().getSimpleName();
		name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toLowerCase());
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass(), name);
		return this.findAllForPagination(detachedCriteria, pageNo, pageSize, orders);
	}

	public PaginatedResult<E> findAllForPagination(E e, int pageNo, int pageSize, Order... orders) {
		String name = getPersistentClass().getSimpleName();
		name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toLowerCase());
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass(), name);
		if (e != null) {
			Example example = Example.create(e);
			detachedCriteria.add(example);
		}
		return this.findAllForPagination(detachedCriteria, pageNo, pageSize, orders);
	}

	public PaginatedResult<E> findAllForPagination(E e, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike, Order... orders) {
		String name = getPersistentClass().getSimpleName();
		name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toLowerCase());
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass(), name);
		if (e != null) {
			Example example = Example.create(e);
			if (ignoreCase) {
				example.ignoreCase();
			}
			if (enableLike) {
				example.enableLike();
			}
			detachedCriteria.add(example);
		}
		return this.findAllForPagination(detachedCriteria, pageNo, pageSize, orders);
	}

	/*
	 * public PaginatedResult<E> findByIdForPagination(ID id, int pageNo, int pageSize) {
	 * final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
	 * E e = this.findById(id);
	 * if (e!=null) {
	 * detachedCriteria.add(Example.create(e));
	 * return this.findAllForPagination(detachedCriteria, pageNo, pageSize);
	 * } else {
	 * return new PaginatedResult<E>(0, pageNo, null);
	 * }
	 * }
	 */

	/**
	 * To be use by individual dao implementation in the case of the findAllForPaginationByExample is not suitable to use.
	 */
	@SuppressWarnings("unchecked")
	protected PaginatedResult<E> findAllForPagination(final DetachedCriteria detachedCriteria, final int pageNo, final int pageSize, Order... orders) {
		for (Order order : orders) {
			detachedCriteria.addOrder(order);
		}

		Session session = null;
        //Transaction tx = null;
		try {

			session = this.openSession();
			//tx = session.beginTransaction();
			Criteria c = detachedCriteria.getExecutableCriteria(session);

			int total = ((Long) (c.setProjection(Projections.rowCount()).uniqueResult())).intValue();
			//int total = c.list().size();
			int maxNoPage = 1;
			if (total > pageSize) {
				maxNoPage = (int) Math.ceil(((double) total / pageSize));
			}
			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
			int maxResults = pageSize;
			if (pageNo == maxNoPage) {
				maxResults = total % pageSize;
				//Swapna:(30082013): Fixed pagination issue.
				if (maxResults == 0) {
					maxResults = pageSize;
				}
			}
			c.setProjection(null);
			c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			c.setFirstResult(firstResults);
			c.setMaxResults(maxResults);
			List<E> res = c.list();
			if (res != null)
				for (E entity : res) {
					hibernateTemplate.evict(entity);
				}
			logger.debug("total: {}, pageNo: {}, lastPage: {}, firstResults: {}, maxResults: {}, result size: {}", new Object[] {
			    total, pageNo, maxNoPage, firstResults, maxResults, res.size()
			});
			//tx.commit();
			return new PaginatedResult<E>(total, pageNo, res);

			/**
			 * 21-Jan-2016 (Khang): Change to use session from Hibernate Template instead of use new session
			 */
			//		return (PaginatedResult<E>) getHibernateTemplate().execute(
			//				new HibernateCallback<PaginatedResult<E>>() {
			//					public PaginatedResult<E> doInHibernate(Session session)
			//							throws HibernateException {
			//					    
			//						Criteria c = detachedCriteria.getExecutableCriteria(session); 
			//						
			//						int total = ((Long) (c.setProjection(Projections.rowCount()).uniqueResult())).intValue();
			//						int maxNoPage = 1;
			//						if (total>pageSize) {
			//							maxNoPage = (int) Math.ceil(((double)total / pageSize));
			//						}
			//						int firstResults = (pageNo - 1)<0?0:(pageNo - 1) * pageSize;
			//						int maxResults = pageSize;
			//						if (pageNo == maxNoPage) {
			//							maxResults = total % pageSize;
			//							//Swapna:(30082013): Fixed pagination issue.
			//							if(maxResults==0){
			//								maxResults= pageSize;
			//							}
			//						}
			//						c.setProjection(null);
			//						c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);    
			//						c.setFirstResult(firstResults);    
			//						c.setMaxResults(maxResults); 
			//						List<E> res = c.list();
			//						if(res!=null)
			//							for(E entity:res){
			//								hibernateTemplate.evict(entity);
			//							}
			//						logger.debug("total: {}, pageNo: {}, lastPage: {}, firstResults: {}, maxResults: {}, result size: {}", new Object[]{total, pageNo, maxNoPage, firstResults, maxResults, res.size()});
			//						return new PaginatedResult<E>(total, pageNo, res);
			//					}  
			//		        });  
		} catch (Exception e) {
			//if (tx!=null) tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			//session.flush();
			session.close();
		}
	}

	public long count(E e) {
		long total = 0;
		final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (e != null) {
			Example example = Example.create(e);
			detachedCriteria.add(example);
		}
		Long result = getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException {
				return (Long) detachedCriteria.getExecutableCriteria(session).setProjection(Projections.rowCount()).uniqueResult();
			}
		});
		if (result != null)
			total = result.longValue();
		return total;
	}

	public void flush() {
		this.hibernateTemplate.flush();
	}

	public void clear() {
		this.hibernateTemplate.clear();
	}

	private Class<E> persistentClass;

	private SessionFactory sessionFactory;

	private HibernateTemplate hibernateTemplate;

	public GenericHibernateDao() {

	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public Class<E> getPersistentClass() {
		if (persistentClass == null) {
			this.persistentClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return persistentClass;
	}

	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	public Session getSession() {
		return this.hibernateTemplate.getSessionFactory().getCurrentSession();
	}

	public Session openSession() {
		return this.hibernateTemplate.getSessionFactory().openSession();
	}

}
