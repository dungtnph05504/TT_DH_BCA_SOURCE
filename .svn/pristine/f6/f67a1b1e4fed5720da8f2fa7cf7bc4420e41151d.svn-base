package com.nec.asia.nic.framework.springSupport;

/**
 * @author Alvin Chua
 */
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * The Class SpringBeanHandler.
 */
public class SpringBeanHandler {
	
	/** The context. */
	static ApplicationContext context=null;

	/**
	 * Sets the context.
	 *
	 * @param context_file the new context
	 */
	public static void setContext (String context_file) {
		context=new ClassPathXmlApplicationContext(new String[]{context_file});		
	}

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	public static ApplicationContext getContext () {
		return context;
	}
}
