package com.nec.asia.nic.framework.springSupport;

import java.lang.reflect.Method;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * The Class SpringServiceHandler.
 *
 * @author Alvin Chua
 */
public class SpringServiceHandler implements MethodInterceptor{
	
	/** The method map. */
	Map methodMap	=null;
	
	/** The function name. */
	private final String functionName="doAction";
	
	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation arg0) throws Throwable {
		Object targetInstance	=methodMap.get(arg0.getMethod().getName());
		
		if (targetInstance!=null) {
			Method targetMethod	=findMatchedMethod (targetInstance, arg0);
		
			if (targetMethod!=null) 
				try {
					return targetMethod.invoke(targetInstance, arg0.getArguments());
				}catch (Throwable e) {
					while (e.getCause()!=null)
						e	=e.getCause();
					throw e;
				}
		}
		
		return arg0.proceed();
	}	
	
	
	/**
	 * Find matched method.
	 *
	 * @param targetInstance the target instance
	 * @param arg0 the arg0
	 * @return the method
	 */
	private Method findMatchedMethod (Object targetInstance, MethodInvocation arg0) {
		Class targetClass	=targetInstance.getClass();
		
		Method[] methods	=targetClass.getMethods();
		for (int i=0;i<methods.length;i++) {
			if (functionName.equals(methods[i].getName())||arg0.getMethod().getName().equals(methods[i].getName())) {
				Object parameters[]	=arg0.getArguments();
				Class definitions[]	=methods[i].getParameterTypes();
				if (parameters.length==definitions.length){
					boolean matched	=true;
					for (int j=0;j<parameters.length;j++) {
						Object parameter	=parameters[j];
						Class definition	=definitions[j];
						if (parameter !=null && !definition.isInstance(parameter)) {
							matched=false;
							break;
						}
					}
					if (matched) return methods[i];
				}
			}
		}
		
		return null;
	}


	/**
	 * Gets the method map.
	 *
	 * @return the method map
	 */
	public Map getMethodMap() {
		return methodMap;
	}


	/**
	 * Sets the method map.
	 *
	 * @param methodMap the new method map
	 */
	public void setMethodMap(Map methodMap) {
		this.methodMap = methodMap;
	}
}
