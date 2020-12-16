/* Copyright 2006 NEC Singapore Pte Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of NEC Singapore Pte Ltd.
 * The use is subject to license terms from NEC Singapore Pte Ltd.
 *
 */

package com.nec.asia.nic.framework.hibernate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.exception.CoreException;





/**
 * Utility class to initialize Hibernate and retrieve Session Factory from JNDI.
 *
 * @version $Revision: 1.1.1.1 $
 * @author $Author: david $
 */
public class HibernateUtil {
	
	/** The Constant JNDI_NAME. */
	private static final String JNDI_NAME = "hibernate/SessionFactory";
	
	/** The Constant sessionFactory. */
	private static final SessionFactory sessionFactory;
	
	/** The _logger. */
	private static Logger _logger = Logger.getLogger(HibernateUtil.class);

	static {
		try {
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Exception e) {
			_logger.fatal("Create SessionFactory failure", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Retrieves SessionFactory from JNDI.
	 *
	 * @return SessionFactory
	 * @throws CoreException the core exception
	 */
	public static SessionFactory getSessionFactory() throws CoreException {
		SessionFactory factory = null;

		try {
			Context ctx = new InitialContext();
			factory = (SessionFactory) ctx.lookup(JNDI_NAME);
		} catch (NamingException e) {
			throw new CoreException("SessionFactory lookup failure", e);
		}

		return factory;
	}

	/**
	 * Retrieves current session binded to the global JTA transaction.
	 *
	 * @return Session
	 * @throws CoreException the core exception
	 */
	public static Session getCurrentSession() throws CoreException {
		return getSessionFactory().getCurrentSession();
	}

	/**
	 * Retrieves a new session.
	 *
	 * @return Session
	 * @throws CoreException the core exception
	 */
	public static Session getNewSession() throws CoreException {
		return getSessionFactory().openSession();
	}

	/**
	 * Detached a persistant object from current session.
	 *
	 * @param obj Object
	 * @throws DaoException the dao exception
	 * @throws CoreException the core exception
	 */
	public static void detachObject(Object obj) throws DaoException,
			CoreException {
		if (obj != null) {
			try {
				getCurrentSession().evict(obj);
			} catch (HibernateException e) {
				throw new DaoException("Failure to detach hibernate object", e);
			}
		}
	}
}
