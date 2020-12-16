package com.nec.asia.nic.framework.job.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * The Class Emailer.
 *
 * @author Alvin Chua
 */
public class Emailer extends BatchJobQuartzImpl {

	/** The _logger. */
	private static Logger _logger = Logger.getLogger(Emailer.class);
	
	/**
	 * Instantiates a new emailer.
	 */
	public Emailer() {
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.quartz.BatchJobQuartzImpl#executeJob(org.quartz.JobExecutionContext)
	 */
	public void executeJob(JobExecutionContext context)
			throws JobExecutionException {
		_logger.info("Emailer is executing.");
		//this.addExecutionError("Test Error Message");
		this.addExecutionInfo("Test Info Message");
		this.addExecutionWarn("Test Warning Message");
		System.out.println("EMAIL EMAIL EMAIL");
		this.addExecutionInfo("Execution completed");
	}

}
