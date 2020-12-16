package com.nec.asia.nic.framework.springSupport;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * The Class SpringServiceManager.
 *
 * @author Alvin Chua
 */
public class SpringServiceManager implements ApplicationContextAware{
	
	/** The context. */
	static ApplicationContext context	=null;
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		setContext (applicationContext);
	}

	/**
	 * Sets the context.
	 *
	 * @param context the new context
	 */
	private static void setContext (ApplicationContext context) {
		SpringServiceManager.context	=context;
	}
	
	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	private static ApplicationContext getContext () {
		if (context==null) {
			throw new RuntimeException("The context is null, Spring not initialized, please make sure call this function after the initialization of spring framework");
		}
		return context;
	}
		
	/**
	 * Gets the bean.
	 *
	 * @param name the name
	 * @return the bean
	 */
	public static Object getBean (String name) {
		return getContext().getBean(name);
	}
}
