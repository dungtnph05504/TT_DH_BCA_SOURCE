package com.nec.asia.nic.framework.springSupport;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;


/**
 * A factory for creating SpringService objects.
 *
 * @author Alvin Chua
 */
public class SpringServiceFactory extends TransactionProxyFactoryBean implements BeanNameAware{

	/** The bean name. */
	String beanName	=null;
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
	 */
	public void setBeanName(String name) {
		beanName	=name;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.aop.framework.AbstractSingletonProxyFactoryBean#getObject()
	 */
	public Object getObject() throws BeansException{
		Object ren	=super.getObject();
		
		if (ren instanceof SpringCoreServiceStub) {
			((SpringCoreServiceStub)ren).setName(beanName);
			ServiceManager.getInstance().registerService((ICoreService)ren);
		}
		
		return ren;
	}
}
