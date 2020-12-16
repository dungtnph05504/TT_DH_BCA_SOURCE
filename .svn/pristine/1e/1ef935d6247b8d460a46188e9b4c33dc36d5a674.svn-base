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
public class TransactionProcessing extends QuartzJobBean {

	private static final Logger logger = Logger.getLogger(TransactionProcessing.class);
	
	//public NicUploadJobService uploadJobService;
	
	private TransactionTask transactionTask;
	
	public void setTransactionTask(TransactionTask transactionTask) {
		this.transactionTask = transactionTask;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.trace("-----------------TransactionProcessingJob begins----------------------");
		if (transactionTask==null) {
			logger.info("Getting TransactionTask bean from SpringServiceManager.");
			transactionTask = (TransactionTask) SpringServiceManager.getBean("transactionTask");
		}
		try {
			
			transactionTask.runTask();
		}
        catch (Throwable th) {
            logger.error("[TransactionProcessingJob] Exception:"+th.getMessage(), th);
            throw new JobExecutionException("Error encountered TransactionProcessingJob:"+ th.getMessage(), th);
        }
		logger.info("----------------------------------------------------------------");
		logger.info("--------- TransactionProcessingJob Completed --------------------");
		logger.info("----------------------------------------------------------------");
	}
}
