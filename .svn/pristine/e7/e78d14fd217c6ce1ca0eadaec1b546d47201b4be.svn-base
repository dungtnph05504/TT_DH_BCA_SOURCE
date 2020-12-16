package com.nec.asia.nic.framework.batchJob;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.nec.asia.nic.framework.job.quartz.BatchJobQuartzStatefulImpl;


public class TestBatchJob  extends BatchJobQuartzStatefulImpl{
	private static Logger _logger = Logger.getLogger(TestBatchJob.class);
	
	
    public void executeJob(JobExecutionContext jEC) throws JobExecutionException
    {
        _logger.info("Inside TestBatchjob.executeJob - Start");
        try {
            
        } catch (Exception ex) {
            _logger.error("Error in TestBatchjob.executeJob: "+ex.getMessage(), ex);
            throw new JobExecutionException(ex);
        }
        finally {
            _logger.info("In TestBatchjob.executeJob finally " +System.currentTimeMillis());
        }        
        
        this.addExecutionInfo("TestBatchjob end (" + "processedCount" + ")");
    }

	

}
