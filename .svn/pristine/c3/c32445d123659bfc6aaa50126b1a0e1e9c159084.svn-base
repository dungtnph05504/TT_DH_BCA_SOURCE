package com.nec.asia.nic.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MyJob2Impl extends QuartzJobBean implements Job {
	Logger logger = LoggerFactory.getLogger(MyJob2Impl.class);
	private static int count = 0;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap jdm = context.getJobDetail().getJobDataMap();
		//name=abc;age=6;gender=female
		logger.info("Parameters retrieved: name=[{}], age=[{}], gender=[{}]", 
				new Object[]{
					jdm.getString("name"),
					jdm.getInt("age"),
					jdm.getString("gender")
		});
		synchronized (this){
			logger.info("Job [{}] has been executed {} Time", context.getJobDetail().getKey().getName(), ++count);
		}
	}

}