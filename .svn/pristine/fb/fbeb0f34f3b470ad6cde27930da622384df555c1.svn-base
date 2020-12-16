package com.nec.asia.nic.framework.job.scheduler;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;


/**
 * The listener interface for receiving jobScheduleTrigger events.
 * The class that is interested in processing a jobScheduleTrigger
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addJobScheduleTriggerListener<code> method. When
 * the jobScheduleTrigger event occurs, that object's appropriate
 * method is invoked.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
public class JobScheduleTriggerListener implements TriggerListener {
	
	/** The _logger. */
	private static Logger _logger = Logger.getLogger(JobScheduleTriggerListener.class);
	
	/** The Constant triggerName. */
	public static final String triggerName="JobScheduleTriggerListener";
	
    /* (non-Javadoc)
     * @see org.quartz.TriggerListener#getName()
     */
    public String getName() {
        return triggerName;
    }

    /**
     * To determine whether to run or stop the cron job. To run crob job, set to false.
     * 
     * @param trigger Trigger
     * @param context JobExecutionContext
     * @return boolean
     */
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    /**
     * Point where the trigger start to fire the cron job.
     *
     * @param trigger Trigger
     * @param context JobExecutionContext
     */
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
    	_logger.debug(trigger.getKey().getName() + " has been scheduled to run.");
    }

    /**
     * When the trigger has misfired the cron job.
     *
     * @param trigger Trigger
     */
    public void triggerMisfired(Trigger trigger) {
    	_logger.debug(trigger.getKey().getName() + " has missed schedule.");
    }

    /**
     * When the triggered cron job has been completed.
     *
     * @param trigger Trigger
     * @param context JobExecutionContext
     * @param triggerInstructionCode int
     */
    public void triggerComplete(Trigger trigger, JobExecutionContext context, int triggerInstructionCode) {
    	_logger.debug(trigger.getKey().getName() + " has completed.");
    }

    /**
     * Send email.
     *
     * @param subject the subject
     * @param message the message
     * @param receipientEmailAddr the receipient email addr
     */
    private void notifyByEmail(String subject, String message, String receipientEmailAddr) {
//		EmailService emailService = (EmailService) SpringServiceManager.getBean("_emailService");
//		emailService.sendMessage(subject, message , receipientEmailAddr);			
    }

	@Override
	public void triggerComplete(Trigger arg0, JobExecutionContext arg1,
			CompletedExecutionInstruction arg2) {
		// TODO Auto-generated method stub
		
	}
}