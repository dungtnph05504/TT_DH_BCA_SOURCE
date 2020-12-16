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
public class SyncPassport extends QuartzJobBean {

	private static final Logger logger = Logger.getLogger(SyncPassport.class);
	
	//public NicUploadJobService uploadJobService;
	
	private SyncPassportTask syncPassportTask;
	
	public void setSyncPassportTask(SyncPassportTask syncPassportTask) {
		this.syncPassportTask = syncPassportTask;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.trace("-----------------SyncPassport begins----------------------");
		if (syncPassportTask==null) {
			logger.info("Getting SyncPassportTask bean from SpringServiceManager.");
			syncPassportTask = (SyncPassportTask) SpringServiceManager.getBean("syncPassportTask");
		}
		try {
			
			syncPassportTask.runTask();
		}
        catch (Throwable th) {
            logger.error("[syncPassportJob] Exception:"+th.getMessage(), th);
            throw new JobExecutionException("Error encountered syncPassportJob:"+ th.getMessage(), th);
        }
		logger.info("----------------------------------------------------------------");
		logger.info("--------- syncPassportJob Completed --------------------");
		logger.info("----------------------------------------------------------------");
	}
}
