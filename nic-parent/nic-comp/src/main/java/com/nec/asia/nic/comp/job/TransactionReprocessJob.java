package com.nec.asia.nic.comp.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

/*
 * Modification History:
 * 
 * 04 May 2017 : change logic to get bean from SpringServiceManager, add @DisallowConcurrentExecution
 */

@DisallowConcurrentExecution
public class TransactionReprocessJob extends QuartzJobBean {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionReprocessJob.class);
	
	//NicUploadJobService uploadJobService = (NicUploadJobService) SpringServiceManager.getBean("uploadJobService");
	
	private TransactionReprocessTask reprocessTask;
	public void setReprocessTask(TransactionReprocessTask reprocessTask) {
		this.reprocessTask = reprocessTask;
	} 
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		/* Daily Job to reset reprocess count for transactions still failed after 3 retries. */
		logger.info("-----------------TransactionReprocessJob begins----------------------");
		if (reprocessTask==null) {
			logger.info("Getting TransactionReprocessTask bean from SpringServiceManager.");
			reprocessTask = (TransactionReprocessTask) SpringServiceManager.getBean("reprocessTask");
		}
		try {
			reprocessTask.runTask();
		}
        catch (Throwable th) {
            logger.error("[TransactionReprocessJob] Exception:"+th.getMessage(), th);
            throw new JobExecutionException("Error encountered TransactionReprocess job:"+ th.getMessage());
        }
		logger.info("----------------------------------------------------------------");
		logger.info("--------- TransactionReprocessJob Completed --------------------");
		logger.info("----------------------------------------------------------------");
	}
	
	
}
