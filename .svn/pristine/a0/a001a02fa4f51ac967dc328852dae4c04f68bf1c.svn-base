package com.nec.asia.nic.framework.job.quartz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.bag.SynchronizedBag;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.nec.asia.nic.framework.job.BatchJob;
import com.nec.asia.nic.framework.job.ExecutionMessage;


/**
 * The Class BatchJobQuartzImpl.
 *
 * @author Alvin Chua
 */
public abstract class BatchJobQuartzImpl extends BatchJob implements Job {
	
    /** The _logger. */
    private static Logger _logger = Logger.getLogger(BatchJobQuartzImpl.class);
    
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public final void execute(JobExecutionContext context)
		throws JobExecutionException {
		List<ExecutionMessage> contextDetails=(List<ExecutionMessage>)context.get(CONTEXT_EXECUTION_DETAILS_KEY);
		if (contextDetails!=null){
			setDetails(contextDetails);
		}else{
			
		    setDetails(Collections.synchronizedList(new ArrayList<ExecutionMessage>()));
			context.put(CONTEXT_EXECUTION_DETAILS_KEY, getDetails());
		}
		
		this.executeJob(context);
		
	}
	
	/**
	 * Execute job.
	 *
	 * @param context the context
	 * @throws JobExecutionException the job execution exception
	 */
	public void executeJob(JobExecutionContext context) throws JobExecutionException{
		
	}
	
	
	
}
