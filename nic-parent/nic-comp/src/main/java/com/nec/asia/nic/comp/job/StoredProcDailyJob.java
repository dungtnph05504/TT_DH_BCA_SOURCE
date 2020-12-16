package com.nec.asia.nic.comp.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nec.asia.nic.comp.trans.service.NicUploadJobService;

public class StoredProcDailyJob extends QuartzJobBean implements StatefulJob{
	
	private static final Logger logger = Logger.getLogger(StoredProcDailyJob.class);
	
	public NicUploadJobService uploadJobService;
	private DailyTask dailyTask;
	
	
	
	public void setDailyTask(DailyTask dailyTask) {
		this.dailyTask = dailyTask;
	}


	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		
		Date x = new Date();
		
		logger.info("---------DAILY JOB:("+x+")");
		
		
		try {
			
			logger.info("dailyTask:"+dailyTask);
			dailyTask.runTask();
		
		}
		
        catch (Throwable th) {
            logger.info("DAILY JOB Exception:"+th.getMessage());
            th.printStackTrace();
            throw new JobExecutionException("Error encountered daily job:"+ th.getMessage());
        }
		
	}
	

}
