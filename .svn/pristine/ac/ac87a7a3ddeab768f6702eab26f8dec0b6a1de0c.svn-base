package com.nec.asia.nic.framework.springSupport;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;


/**
 * The Class SpringPerformanceInterceptor.
 *
 * @author Alvin Chua
 */
public class SpringPerformanceInterceptor   implements org.aopalliance.intercept.MethodInterceptor {
    
    /** The logger. */
    private static Logger logger = Logger.getLogger("PERFORMANCE");
    
	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation method) throws Throwable {
		boolean isSuccess=false;
		long start = System.currentTimeMillis();
		try {
			Object result = method.proceed();
			isSuccess = true;
			return result;
		}
		finally {
			if(logger.isTraceEnabled()) {
				long end = System.currentTimeMillis();
				long timeMs = end - start;
	
				//Short class name
				String className = method.getMethod().getDeclaringClass().getName();
				int index = className.lastIndexOf(".");
				className= index>-1?className.substring(index+1):className;
				
				logger.trace("["+className+"."+method.getMethod().getName()+"] took: " + timeMs +"ms., isSuccess: "+isSuccess);
			}
		}
	}
}
