package com.nec.asia.nic.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 
 * @author bright_zheng
 *
 */
public class MyJob1Impl extends QuartzJobBean implements Job {
	Logger logger = LoggerFactory.getLogger(MyJob1Impl.class);
	private static int count = 0;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		synchronized (this){
			logger.info("Job [{}] has been executed {} Time", context.getJobDetail().getDescription(), ++count);
		}
	}

}