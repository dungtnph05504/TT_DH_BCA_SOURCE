package com.nec.asia.nic.comp.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

/*
 * Modification History:
 * 
 * 04 May 2017 : change logic to get bean from SpringServiceManager, add @DisallowConcurrentExecution
 */

@DisallowConcurrentExecution
public class LdsProcess extends QuartzJobBean {

	private static final Logger logger = Logger.getLogger(LdsProcess.class);
	
	//public NicUploadJobService uploadJobService;
	
	private LdsProcessTask ldsProcessTask;
	
	public void setLdsProcessTask(LdsProcessTask ldsProcessTask) {
		this.ldsProcessTask = ldsProcessTask;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.trace("-----------------LdsProcess begins----------------------");
		if (ldsProcessTask==null) {
			logger.info("Getting LdsProcessTask bean from SpringServiceManager.");
			ldsProcessTask = (LdsProcessTask) SpringServiceManager.getBean("ldsProcessTask");
		}
		try {
			
			ldsProcessTask.runTask();
		}
        catch (Throwable th) {
            logger.error("[ldsProcessJob] Exception:"+th.getMessage(), th);
            throw new JobExecutionException("Error encountered ldsProcessJob:"+ th.getMessage(), th);
        }
		logger.info("----------------------------------------------------------------");
		logger.info("--------- ldsProcessJob Completed --------------------");
		logger.info("----------------------------------------------------------------");
	}
}
