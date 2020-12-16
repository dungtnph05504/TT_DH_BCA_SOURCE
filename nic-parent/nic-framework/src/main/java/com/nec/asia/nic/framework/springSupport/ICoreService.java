package com.nec.asia.nic.framework.springSupport;

import java.util.Observer;


/**
 * The Interface ICoreService.
 *
 * @author Alvin Chua
 */
public interface ICoreService {

	/** The Constant VIEW_RIGHTS. */
	public static final int VIEW_RIGHTS 		= 1;
	
	/** The Constant UPDATE_RIGHTS. */
	public static final int UPDATE_RIGHTS 	= 2;
	
	/** The Constant DELETE_RIGHTS. */
	public static final int DELETE_RIGHTS 	= 3;
	
	/**
	 * Gets the service name.
	 * 
	 * @return the unique identifier for the service
	 */
	public String getName();

	/**
	 * To load required resources.
	 * 
	 */
	public void start();

	/**
	 * To release any blocked resources and to clean the member variables.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void stop() throws Exception;

	/**
	 * To notify the Object obj to all the register overservers of this server.
	 *
	 * @param obj the obj
	 */
	public void notify(Object obj);

	/**
	 * Use this method to notify about changes in this service.
	 *
	 * @param observer the observer
	 */
	public void register(Observer observer);
}