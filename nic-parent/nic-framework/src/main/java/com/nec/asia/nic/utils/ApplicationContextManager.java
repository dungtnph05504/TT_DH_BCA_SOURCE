package com.nec.asia.nic.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Tool for easy access Spring context & beans
 * 
 * @author bright_zheng
 *
 */
public class ApplicationContextManager implements ApplicationContextAware{
	
	private static ApplicationContext context	=null;
	
	/** implements the interface of Spring ApplicationContextAware */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context	= applicationContext;
	}
	
	public static ApplicationContext getContext () {
		return context;
	}
		
	public static Object getBean (String name) {
		return getContext().getBean(name);
	}
	
	public static <T> T getBean(String name, Class<T> requiredType){
		return getContext().getBean(name, requiredType);
	}
}
