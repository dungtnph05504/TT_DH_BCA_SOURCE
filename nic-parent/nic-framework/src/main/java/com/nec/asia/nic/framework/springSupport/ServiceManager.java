package com.nec.asia.nic.framework.springSupport;

import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


/**
 * The Class ServiceManager.
 *
 * @author Alvin Chua
 */
public class ServiceManager {

	/** The instance. */
	private static ServiceManager instance;


	/** The serviceList in order. */
	private ArrayList serviceList;

	/**
	 * Gets the instance.
	 * 
	 * @return the instance
	 */
	public static synchronized ServiceManager getInstance() {
		if (instance == null) {
			instance = new ServiceManager();
		}
		return instance;
	}

	/**
	 * The Constructor.
	 */
	private ServiceManager() {
		configLogger();
		start();
	}

	/**
	 * Start.
	 */
	public void start() {
		serviceList = new ArrayList();
	}

	/**
	 * Config logger.
	 */
	private void configLogger(){
		//check for the default property file
		//if(System.getProperty("log4j.configuration")!=null) return;
		
		Logger root = Logger.getRootLogger();
		if (!root.getAllAppenders().hasMoreElements()) {
			System.out.println("Runs with default log4j configuration... ");
			Layout layout = new PatternLayout("[%p %d]  %m%n");
			root.addAppender(new ConsoleAppender(layout, ConsoleAppender.SYSTEM_OUT));
		} else {
			Enumeration e = root.getAllAppenders();
			int i = 1;
			while (e.hasMoreElements()) {
				Object appender = e.nextElement();
				System.out.println("appenders["+(i++)+"]: "+appender);
			}
		}
	}
	
	/**
	 * Gets the service.
	 * 
	 * @param identifier
	 *            the identifier
	 * 
	 * @return the service
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public ICoreService getService(String identifier) throws Exception {
		if (identifier == null)
			return null;
		for (int i = 0; i < serviceList.size(); i++) {
			ICoreService svc = (ICoreService) serviceList.get(i);
			if(svc.getName().equals(identifier)){
				return svc;
			}
		}
		return null;
	}

	/**
	 * Register service.
	 * 
	 * @param service
	 *            the service
	 */
	public void registerService(ICoreService service) {
		if (service == null || service.getName() == null) {
				throw new RuntimeException("Service or its name cannot be null");
		}
			
		//replace the prev instance if any
		for (int i = 0; i < serviceList.size(); i++) {
			ICoreService svc = (ICoreService) serviceList.get(i);
			if(svc.getName().equals(service.getName())){
				Object object = serviceList.set(i,service);
				if(object!=null){
					//clear prev instance
					try {
						((ICoreService)object).stop();
					} catch (Exception e) {
					}
				}
				return;
			}
		}
				
		serviceList.add(service);
	}

	/**
	 * Shutdown.
	 */
	public void shutdown() {
		int i = serviceList.size()-1;
		while (i >=0) {
			ICoreService service = (ICoreService) serviceList.remove(i);
			i = serviceList.size()-1;
			try {
				service.stop();
			} catch (Exception e) {
			}
		}
	}

}
