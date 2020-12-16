package com.nec.asia.nic.framework.springSupport;

import java.util.Observable;
import java.util.Observer;


/**
 * The Class AbstractService.
 * 
 * @author Mahesh
 */
public abstract class AbstractService extends Observable implements ICoreService{
	
	/** The startup time. */
	private long startupTime;
	
	/**
	 * The Constructor of AbstractService
	 * Subclass may explicitly call super() in the constructor
	 * to do the default init process.
	 */
	protected AbstractService() {
		long initTIme = System.currentTimeMillis();
		
		init();
		
		start();
		
		ServiceManager.getInstance().registerService(this);
		startupTime = System.currentTimeMillis() - initTIme;
	}
	
	/**
	 * To Initialize service variables.
	 */
	public void init() {
	}

	/**
	 * Startup order of this service based on the dependency between other services 
	 * 1 - being Top Order
	 * 2 - Next level and so on 
	 * 0 - being the least.
	 * 
	 * @return the start order
	 */
	public int getStartOrder() {
		return 0;
	}
	
	/**
	 * Gets the service name.
	 * 
	 * @return the unique identifier for the service
	 */
	public abstract String getName();
	
	/**
	 * To load required resources.
	 * 
	 */
	public abstract void start();
	
	/**
	 * To release any blocked resources and to clean the member variables.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public abstract void stop() throws Exception;

	/**
	 * To authenticate the userID based on the function name for the
	 * given accessRight.
	 * 
	 * accessRight is the access right to be checked against DB value
	 * 1	View Only (ICoreService.VIEW_RIGHTS)
	 * 2 	View & Update Only (ICoreService.UPDATE_RIGHTS)
	 * 3	View,Update & Delete (ICoreService.DELETE_RIGHTS)
	 *
	 * @param userID the user id
	 * @param function the function
	 * @param accessRight the access right
	 * @throws Exception the exception
	 */
	protected void authenticate(String userID, String function, int accessRight) throws Exception{
		//get right for this user / role for this function 
		//check the db right is >= requested access right
	}
	
	/**
	 * Gets the total startup time of this service.
	 *
	 * @return the startup time
	 */
	public long getStartupTime() {
		return startupTime;
	}

	/**
	 * To notify the Object obj to all the register overservers of this server.
	 *
	 * @param obj the obj
	 */
	public void notify(Object obj){
		setChanged();
		notifyObservers(obj);
	}
	
	/**
	 * Use this method to notify about changes in this service.
	 *
	 * @param observer the observer
	 */
	public void register(Observer observer){
		if(observer!=null){
			addObserver(observer);
		}
	}
}
