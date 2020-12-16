package com.nec.asia.nic.framework.storeforward.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.framework.storeforward.StoreForwardService;

/**
 * The default implementation of Store and Forward design pattern
 * 
 * @author bright_zheng
 * @since 27 May 2013
 * 
 */

@Service("storeForwardService")
@Transactional
public class StoreForwardServiceImpl<D, H extends Callable<?>> 
	implements StoreForwardService<D, H> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public StoreForwardServiceImpl() {
	}

	/** thread pool for tasks dispatching concurrently */
	private static final ExecutorService pool = Executors.newCachedThreadPool();

	@Override
	public void forward(D data, H callableHandler) {
		// Future<?> future = pool.submit((Callable<?>) callableHandler);
		pool.submit((Callable<?>) callableHandler);
		logger.debug("submitted to Store-and-Forward thread pool: {}", String.valueOf(data));
	}

}
