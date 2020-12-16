package com.nec.asia.nic.framework.storeforward;

import java.util.concurrent.Callable;


/**
 * Handler of Store and Forward for handling real business logic execution 
 * 
 * @author bright_zheng
 * @since 27 May 2013
 *
 * @param <R> Return Type of the forward handler
 */
public interface ForwardHandler<R> extends Callable<R>{
	
	/**
	 * business processing
	 * 
	 * @throws StoreForwardException
	 */
	public R execute() throws StoreForwardException;
	
	/**
	 * handle success logic, for example, mark some particular flags/status
	 * 
	 * @param r the return of business processing
	 */
	public void handleSuccess(R r);

	/**
	 * handle failure logic if exception occurred during business processing
	 * for example, mark some particular flags/status
	 * 
	 * @param r the return of business processing
	 * @param e the exception we're facing
	 */
	public void handleFailure(StoreForwardException e);
}
