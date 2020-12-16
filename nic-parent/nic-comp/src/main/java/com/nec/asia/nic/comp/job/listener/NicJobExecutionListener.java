package com.nec.asia.nic.comp.job.listener;



import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
//import org.quartz.listeners.JobListenerSupport;
import org.quartz.JobListener;

public class NicJobExecutionListener implements JobListener{

	public static final String LISTENER_NAME = "NicJobExecutionListener";
	
	@Override
	public String getName() {
		return LISTENER_NAME;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		
		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("\n\n-----------------jobToBeExecuted");
		System.out.println("Job : " + jobName + " is going to start...\n\n");
		
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		
	}

	 @Override
	  public void jobWasExecuted(JobExecutionContext context, 
			  JobExecutionException jobException) {
		 
		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("\n\n-------------------Job : " + jobName + " is finished...");
		System.out.println("-------------------------------------------------------");
		System.out.println("---------------                          --------------");
		System.out.println("---------------            END           --------------");
		System.out.println("---------------                          --------------");
		System.out.println("---------------                          --------------");
		System.out.println("-------------------------------------------------------");
		System.out.println("\n\n");
	 
			if (jobException != null) {
				System.out.println("-----------------\nException thrown by: " + jobName
					+ " Exception: " + jobException.getMessage());
			}
	  }
	
	
}
