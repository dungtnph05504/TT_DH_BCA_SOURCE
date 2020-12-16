package com.nec.asia.nic.framework.storeforward.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.nic.framework.storeforward.ForwardHandler;
import com.nec.asia.nic.framework.storeforward.StoreForwardException;

/**
 * Abstract Forward Handler
 * <p>
 * For all sub classes/implementations, please implement 3 business oriented APIs:<br>
 * 1. execute(): this is exactly where the business logic puts<br>
 * 2. handleSuccess(R r): to handle when business is executed successfully<br>
 * 3. handleFailure(StoreForwardException e): to handle if exception occurred during business processing<br>
 * 
 * @author bright_zheng
 * @since 27 May 2013
 *
 * @param <R> the type of return
 */
public abstract class AbstractForwardHandler<D, R> implements ForwardHandler<R> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/** the input data */
	protected D data;
	
	public AbstractForwardHandler(){
		
	}
	public AbstractForwardHandler(D data){
		this.data = data;
	}

	/**
	 * this is the API of Callable
	 * If the handler requires higher customization, override it.
	 */
	@Override
	public R call() throws Exception {
		R r = null;
		try{
			//execute the business logic			
			r = this.execute();
			
			//handle success if anything is fine
			this.handleSuccess(r);
		}catch (StoreForwardException e){
			//handler failure if StoreForwardException occurred 
			this.handleFailure(e);
		}
		return r;
	}
	
	abstract public R execute() throws StoreForwardException;

	abstract public void handleSuccess(R r);
	
	abstract public void handleFailure(StoreForwardException e);
	
	public void setData(D data){
		this.data=data;
	}

}
