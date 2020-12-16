package com.nec.asia.nic.framework.storeforward;

import java.util.concurrent.Callable;

/**
 * 
 * The API of Store and Forward design pattern for:
 * 1. "Almost Realtime" transaction data uploading to NIC
 * 2. "Almost Realtime" status data synchronizing to NIC 
 * 
 * @author bright_zheng
 * @since 27 May 2013
 *
 * @param <D> the data as input of business processing
 * @param <H> the ForwardHandler implementation
 * 
 * @see ForwardHandler
 * 
 */
public interface StoreForwardService<D, H extends Callable<?>> {

	/**
	 * 
	 * @param data the data as the input for business processing
	 * @param callableHandler the handler implementation
	 */
	public void forward(D data, H callableHandler);
	
}
