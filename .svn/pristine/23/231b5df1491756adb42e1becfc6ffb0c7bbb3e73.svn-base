package com.nec.asia.nic.framework.common;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * This is the semaphore class with boolean flag.
 * This class provides boolean gate logic
 * 
 * 
 * @author Mahesh
 * @version 1.0
 * @since 01 Jan 2010
 * 
 * @Modification History:
 * 18-Mar-2013 Mahesh: This is copied from afis-comp module so other modules can use it
 * 
 *
 */
public class BooleanLatch {
	
	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(BooleanLatch.class);
	
	/** The Constant booleanLatchList. */
	private static final Map<String, BooleanLatch> booleanLatchMap = new ConcurrentHashMap<String, BooleanLatch>(); 
	
	public static final BooleanLatch TRUE_INSTANCE = new BooleanLatch("TRUE_INSTANCE", true) {public void setFlag(boolean flag) {}};
	
	public static final BooleanLatch FALSE_INSTANCE = new BooleanLatch("FALSE_INSTANCE", false) {public void setFlag(boolean flag) {}};
	
	/** The true semaphore. */
	private final Semaphore trueSemaphore = new Semaphore(0);
	
	/** The false semaphore. */
	private final Semaphore falseSemaphore = new Semaphore(0);
	
	private long lastTrueTimestampMilli;
	private long lastFalseTimestampMilli;
	
	
	/** The name. */
	private final String name;
	
	/** The flag. */
	private boolean flag;
	
	/**
	 * Instantiates a new boolean latch.
	 *
	 * @param name used for logging
	 * @param flag the flag
	 */
	public BooleanLatch(String name, boolean flag) {
		this.flag = flag;
		this.name=name;
		this.flag = flag;
		if(flag) {
			trueSemaphore.release();
			lastTrueTimestampMilli = System.currentTimeMillis();
		}
		else {
			falseSemaphore.release();
			lastFalseTimestampMilli = System.currentTimeMillis();
		}
		booleanLatchMap.put(name, this);
		_logger.info("Initialized BooleanLatch: "+name+", flag:"+flag);
	}

	/**
	 * Sets the flag.
	 *
	 * @param flag the new flag
	 */
	public void setFlag(boolean flag) {
		if(this.flag!=flag)
		{
			this.flag=flag;
			if(flag) {
				lastTrueTimestampMilli = System.currentTimeMillis();
				trueSemaphore.release(trueSemaphore.getQueueLength()+1);
				falseSemaphore.drainPermits();
			}
			else {
				lastFalseTimestampMilli = System.currentTimeMillis();
				falseSemaphore.release(falseSemaphore.getQueueLength()+1);
				trueSemaphore.drainPermits();
			}
		}
	}
	
	/**
	 * Gets the flag.
	 *
	 * @return the flag
	 */
	public final boolean getFlag() {
		return flag;
	}	
	
	/**
	 * This method waits for flag to become true and returns.
	 * If flag is already true, it returns immediately
	 * 
	 * @return flag
	 */
	public boolean waitForTrue() {
		while(!flag) {
			if(_logger.isTraceEnabled()) _logger.trace("In waitForTrue, Before sleeping for BooleanLatch: "+name+", flag:"+flag);
			trueSemaphore.acquireUninterruptibly(); //wait for it to become true
			if(_logger.isTraceEnabled()) _logger.trace("In waitForTrue, After sleeping for BooleanLatch: "+name+", flag:"+flag);
			if(flag) {
				trueSemaphore.release();
			}
		}
		return flag;
	}
	
	/**
	 * This method waits for flag to become true for specified timeoutSeconds and returns.
	 * If flag is already true, it returns immediately
	 *
	 * @param timeoutSeconds the timeout seconds
	 * @return flag
	 */
	public boolean waitForTrue(int timeoutSeconds) {
		return waitForTrue(timeoutSeconds, TimeUnit.SECONDS);
	}
	
	/**
	 * This method waits for flag to become true for specified timeout and returns.
	 * If flag is already true, it returns immediately
	 *
	 * @param timeoutInterval the timeout interval
	 * @param timeUnit the time unit
	 * @return flag
	 */
	public boolean waitForTrue(int timeoutInterval, TimeUnit timeUnit) {
		if(!flag) {
			try {
				if(_logger.isTraceEnabled()) _logger.trace("In waitForTrue, Before sleeping for BooleanLatch: "+name+" for timeoutInterval: "+timeoutInterval+", timeUnit: "+timeUnit.name()+", flag:"+flag);
				boolean hasPermit = trueSemaphore.tryAcquire(timeoutInterval, timeUnit); //wait for it to become true
				if(_logger.isTraceEnabled()) _logger.trace("In waitForTrue, After sleeping for BooleanLatch: "+name+", flag:"+flag);
				if(hasPermit) {
					if(flag) {
						trueSemaphore.release();
					}
				}
			}
			catch(Throwable th) {
			}
		}
		return flag;
	}
	
	/**
	 * This method waits for flag to become false and returns.
	 * If flag is already false, it returns immediately
	 * @return flag
	 */
	public boolean waitForFalse() {
		while(flag) {
			if(_logger.isTraceEnabled()) _logger.trace("In waitForFalse, Before sleeping for BooleanLatch: "+name+", flag:"+flag);
			falseSemaphore.acquireUninterruptibly(); //wait for it to become false
			if(_logger.isTraceEnabled()) _logger.trace("In waitForFalse, After sleeping for BooleanLatch: "+name+", flag:"+flag);
			if(!flag) {
				falseSemaphore.release();
			}
		}
		return flag;
	}
	
	/**
	 * This method waits for flag to become false for specified timeoutSeconds and returns.
	 * If flag is already false, it returns immediately
	 *
	 * @param timeoutSeconds the timeout seconds
	 * @return flag
	 */
	public boolean waitForFalse(int timeoutSeconds) {
		return waitForFalse(timeoutSeconds, TimeUnit.SECONDS);
	}
	
	/**
	 * This method waits for flag to become false for specified timeout and returns.
	 * If flag is already false, it returns immediately
	 *
	 * @param timeoutInterval the timeout interval
	 * @param timeUnit the time unit
	 * @return flag
	 */
	public boolean waitForFalse(int timeoutInterval, TimeUnit timeUnit) {
		if(flag) {
			try {
				if(_logger.isTraceEnabled()) _logger.trace("In waitForFalse, Before sleeping for BooleanLatch: "+name+" for timeoutInterval: "+timeoutInterval+", timeUnit: "+timeUnit.name()+", flag:"+flag);
				boolean hasPermit = falseSemaphore.tryAcquire(timeoutInterval,timeUnit); //wait for it to become false
				if(_logger.isTraceEnabled()) _logger.trace("In waitForFalse, After sleeping for BooleanLatch: "+name+", flag:"+flag);
				if(hasPermit) {
					if(!flag) {
						falseSemaphore.release();
					}
				}
			}
			catch(Throwable th){
			}
		}
		return flag;
	}

	/**
	 * Gets the boolean latch list.
	 *
	 * @return the boolean latch list
	 */
	public static Collection<BooleanLatch> getBooleanLatchList() {
		return booleanLatchMap.values();
	}
	
	public static BooleanLatch getBooleanLatchByName(String booleanLatchName) {
		return booleanLatchMap.get(booleanLatchName);
	}

	/**
	 * Gets the true semaphore.
	 *
	 * @return the true semaphore
	 */
	public Semaphore getTrueSemaphore() {
		return trueSemaphore;
	}

	/**
	 * Gets the false semaphore.
	 *
	 * @return the false semaphore
	 */
	public Semaphore getFalseSemaphore() {
		return falseSemaphore;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public long getLastTrueTimestampMilli() {
		return lastTrueTimestampMilli;
	}

	public long getLastFalseTimestampMilli() {
		return lastFalseTimestampMilli;
	}
	
	
}
