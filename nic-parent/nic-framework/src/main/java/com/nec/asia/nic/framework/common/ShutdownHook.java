package com.nec.asia.nic.framework.common;

import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

/**
 * Shutdown hook class to listen for shutdown event.
 *
 * @author Mahesh_Reddy
 * @version 1.0
 * @since 22 Apr 2011
 */
public class ShutdownHook  extends Thread {
	
	/** The Constant _logger. */
	private static final Logger _logger=Logger.getLogger(ShutdownHook.class);
	
	/** The is shutdown flag. */
	public static boolean isShutdownFlag = false;
	
	/** The Constant shutdownSemaphore. */
	private static final Semaphore shutdownSemaphore = new Semaphore(0);
	
	static{
		new ShutdownHook();
	}
	
	/**
	 * Instantiates a new shutdown hook.
	 */
	private ShutdownHook() {
		_logger.info("Core ShutdownHook is created: isShutdownFlag: "+ShutdownHook.isShutdownFlag);
		Runtime.getRuntime().addShutdownHook(this);
		_logger.info("Core ShutdownHook is registered");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		ShutdownHook.isShutdownFlag = true;
		shutdownSemaphore.release(1);
		 _logger.info("Core ShutdownHook is notified: isShutdownFlag: "+ShutdownHook.isShutdownFlag);
	}
	
	
	/**
	 * Wiat for shutdown.
	 */
	public static void waitForShutdown() {
		shutdownSemaphore.acquireUninterruptibly();
		shutdownSemaphore.release();
	}
}
