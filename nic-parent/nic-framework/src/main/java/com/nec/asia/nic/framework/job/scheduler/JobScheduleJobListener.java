package com.nec.asia.nic.framework.job.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionDetails;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistory;
import com.nec.asia.nic.framework.admin.job.domain.JobSchedule;
import com.nec.asia.nic.framework.job.ExecutionMessage;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.ActionOnEvent;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.JobStatus;
import com.nec.asia.nic.framework.job.scheduler.exception.CreateJobExecutionException;
import com.nec.asia.nic.framework.job.scheduler.exception.JobScheduleException;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;
import com.nec.asia.nic.utils.StringUtil;


/**
 * The listener interface for receiving jobScheduleJob events.
 * The class that is interested in processing a jobScheduleJob
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addJobScheduleJobListener<code> method. When
 * the jobScheduleJob event occurs, that object's appropriate
 * method is invoked.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
public class JobScheduleJobListener implements JobListener {
	
	/** The _logger. */
	private static Logger _logger = Logger.getLogger(JobScheduleJobListener.class);
	
	/** The Constant CONTEXT_EXECUTION_DETAILS_KEY. */
	private static final String CONTEXT_EXECUTION_DETAILS_KEY="DETAILS";
	
	/** The Constant SYSTEM_ID. */
	private static final String SYSTEM_ID="SYSTEMID";
	
	/** The Constant DATAMAP_SAVEIGNORED_TO_HISTORY_KEY. */
	private static final String DATAMAP_SAVEIGNORED_TO_HISTORY_KEY="SAVE_IGNORED_JOB_TO_HISTORY";
    
    /** The Constant CONTEXT_EXECUTION_PROCESSED_TASKS_KEY. */
    private static final String CONTEXT_EXECUTION_PROCESSED_TASKS_KEY="CONTEXT_EXECUTION_PROCESSED_TASKS";
    
    /** The Constant JOB_SCHEDULE_DELAYED_TRIGGER_TIME. */
    private static final String JOB_SCHEDULE_DELAYED_TRIGGER_TIME="JOB_SCHEDULE_DELAYED_TRIGGER_TIME";
	
    /** The Constant JOB_SCHEDULE_DELAYED_TRIGGER_TIME. */
    private static final String PARA_SCOPE_SYSTEM ="SYSTEM";
    
	/** The default delayed time trigger. */
	private final long defaultDelayedTimeTrigger=15000;
	
	/** The Constant triggerName. */
	public static final String triggerName = "JobScheduleJobListener";
	
    /* (non-Javadoc)
     * @see org.quartz.JobListener#getName()
     */
    public String getName() {   
        return triggerName;
    }   

    /* (non-Javadoc)
     * @see org.quartz.JobListener#jobToBeExecuted(org.quartz.JobExecutionContext)
     */
    public void jobToBeExecuted(JobExecutionContext context) {
    	try {
	    	JobScheduleService service = (JobScheduleService) SpringServiceManager.getBean("jobScheduleService");	 
	    	// group is actually the job id
	    	service.updateJobExecutionStatus(context.getJobDetail().getKey().getGroup(), context.getFireTime(), context.getNextFireTime(), JobStatus.Running);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    /* (non-Javadoc)
     * @see org.quartz.JobListener#jobExecutionVetoed(org.quartz.JobExecutionContext)
     */
    public void jobExecutionVetoed(JobExecutionContext context) {
    }

    /* (non-Javadoc)
     * @see org.quartz.JobListener#jobWasExecuted(org.quartz.JobExecutionContext, org.quartz.JobExecutionException)
     */
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

    	JobExecutionHistory history = new JobExecutionHistory();
    	String receipientEmailAddr=null;
    	String scheduler="";
    	
		try {
			scheduler=context.getScheduler().getSchedulerName();
		} catch (SchedulerException e) {
			_logger.error("Error getting the scheduler name.", e);
		}
    	    	
    	try {
        	List<ExecutionMessage> details=(List<ExecutionMessage>)context.get(CONTEXT_EXECUTION_DETAILS_KEY);
        	//String systemId = (String)context.get(SYSTEM_ID);
        	
        	List<JobExecutionDetails> executionDetails=new ArrayList<JobExecutionDetails>();
        	boolean bContainsError=false;
        	if (details!=null){
        		for (int i=0;i<details.size();i++){
        			ExecutionMessage message=(ExecutionMessage)details.get(i);
        			if (message!=null){
	        			JobExecutionDetails jobDetail=new JobExecutionDetails();
	        			jobDetail.setDetailType(message.getMessagetype());
	        			if (ExecutionMessage.ERROR.equalsIgnoreCase(message.getMessagetype())||ExecutionMessage.WARNING.equalsIgnoreCase(message.getMessagetype())){
	        				bContainsError=true;
	        			}
	        			String messageText=message.getMessage();
	        			if (messageText!=null){
	        				if (messageText.length()>200){
	        					messageText=messageText.substring(0, 200);
	        				}
	        				jobDetail.setMessage(messageText);
	        			}
	        			executionDetails.add(jobDetail);
        			}
        		}
        	}
        	
        	//write into job execution history table
        	history.setJobId(context.getTrigger().getJobKey().getGroup());
        	//history.setJobId(context.getTrigger().getJobGroup());
        	history.setExecutionDate(context.getFireTime());
        	history.setCompleteDate(new Date());
        	//history.setSystemId(systemId);
        	
        	if (jobException == null) {
        		if (bContainsError==false){
        			history.setStatus(0); //set status to OK
        			history.setMessage("Job completed successfully");
        		}else{
        			history.setStatus(1); //set status to Warning
        			history.setMessage("Job completed with error(s).");
        		}
        	}
        	else {
        		history.setStatus(2); 	//set status to Error
            	history.setMessage("Job fails to execute.");
            	history.setDetail(jobException.toString());
        	}
        	
        	JobScheduleService service = (JobScheduleService) SpringServiceManager.getBean("jobScheduleService");
        	ParametersService paramService = (ParametersService) SpringServiceManager.getBean("parametersService");
        	boolean createJobExecutionHistory = true;
        	if(context.getJobDetail().getJobDataMap()!=null  && jobException==null && !bContainsError) {
        	    String saveIgnoredToHistoryVal =context.getJobDetail().getJobDataMap().getString(DATAMAP_SAVEIGNORED_TO_HISTORY_KEY);
        	    if(saveIgnoredToHistoryVal!=null && saveIgnoredToHistoryVal.trim().equalsIgnoreCase("false")) { 
        	        Boolean hasProcessedAnyTasks = (Boolean)context.get(CONTEXT_EXECUTION_PROCESSED_TASKS_KEY);
        	        if(hasProcessedAnyTasks==null || !hasProcessedAnyTasks.booleanValue()) {
        	            createJobExecutionHistory = false;
        	        }
        	    }
        	}
        	        	
        	if(createJobExecutionHistory) {
        		service.createJobExecutionHistory(history, executionDetails);
        	}
        	else {
        	    _logger.debug("["+context.getJobDetail().getKey().getName()+"] saveIgnoredToHistory is false, logging history: "+history+", executionDetails: "+executionDetails);
        	}
        	
        	//retrieve delayed time trigger - Start
        	long delayedTimeTrigger;
        	
        	try{
        	    Parameters param = paramService.getParamDetails(PARA_SCOPE_SYSTEM, JOB_SCHEDULE_DELAYED_TRIGGER_TIME);
        		delayedTimeTrigger = Integer.parseInt(param.getParaLobValue()) * 1000L;
        	}catch (Exception e){
        		delayedTimeTrigger=defaultDelayedTimeTrigger;
        	}
        	
        	//retrieve delayed time trigger - End
        	
        	//retrieve action on failure
        	JobSchedule schedule = service.getJobScheduleByPrimaryKey(context.getTrigger().getJobKey().getGroup(), context.getTrigger().getKey().getName());
        	if (schedule!=null){
        		receipientEmailAddr = schedule.getEmailAddress();
                if (history!=null){
    	        	if (schedule.getActionOnSuccess() == ActionOnEvent.NotifyEmail||schedule.getActionOnFailure() == ActionOnEvent.NotifyEmail) {      		
    	        		//code to email notification
    	        		String subject="";
    	        		if (history.getStatus()==0){
    	        			subject =  "Job Alert: " + context.getJobDetail().getKey().getName() + " has completed successfully.";
    	        		}else if (history.getStatus()==1){
    	        			subject =  "Job Alert: " + context.getJobDetail().getKey().getName() + " has completed with warning.";
    	        		}else if (history.getStatus()==2){
    	        			subject =  "Job Alert: " + context.getJobDetail().getKey().getName() + " has completed with errors.";
    	        		}
    	        		
    	        		StringBuffer message =new StringBuffer();
    	        		message.append("Scheduler: " + scheduler + "\n");
    	        		message.append("Date/Time: " + history.getExecutionDate() + "\n");    	        		
    	        		message.append("Job ID: " + context.getJobDetail().getKey().getGroup() + "\n");
    	        		message.append("Status: " + history.getMessage() + "\n");
    	        		message.append("Remarks: " + StringUtil.nullToEmpty(history.getDetail()) + "\n\n");
    	        		message.append("Details: \n");
    	        		
    	            	if ((details!=null)&&bContainsError){
    	            		for (int i=0;i<details.size();i++){
    	            			ExecutionMessage executionMessage=(ExecutionMessage)details.get(i);
    	            			if (ExecutionMessage.ERROR.equalsIgnoreCase(executionMessage.getMessagetype())){
    	        	        		message.append("Error: " + StringUtil.nullToEmpty(executionMessage.getMessage()) + "\n");
    	            			}else if (ExecutionMessage.WARNING.equalsIgnoreCase(executionMessage.getMessagetype())){
    	        	        		message.append("Warning: " + StringUtil.nullToEmpty(executionMessage.getMessage()) + "\n");    	            				
    	            			}
    	            		}
    	            	}
    	            	
    	        		notifyByEmail(subject, message.toString(), receipientEmailAddr);
    	        	}else if ((schedule.getActionOnFailure() == ActionOnEvent.Rerun)&&(history.getStatus()==2)) {
	                	//schedule simple job to be run in X hours time again
    	        		service.setDelayedSimpleTrigger(context.getTrigger().getKey().getName(), context.getJobDetail().getKey().getName(), 
	                			context.getJobDetail().getKey().getGroup(), 10800000L);    	            	
    	        	}else if ((schedule.getActionOnFailure() == ActionOnEvent.RerunNotifyEmail)&&(history.getStatus()==2)) {
    	        		//code to schedule re-run and email notification
    	        		String subject = "Job Alert: " + context.getJobDetail().getKey().getName() + " has completed with errors.";
    	        		StringBuffer message =new StringBuffer();
    	        		message.append("Scheduler: " + scheduler + "\n");
    	        		message.append("Date/Time: " + history.getExecutionDate() + "\n");    	        		
    	        		message.append("Job ID: " + context.getJobDetail().getKey().getName() + "\n");
    	        		message.append("Status: " + history.getMessage() + "\n");
    	        		message.append("Remarks: " + StringUtil.nullToEmpty(history.getDetail()) + "\n\n");
    	        		message.append("Details: \n");

    	            	if ((details!=null)&&bContainsError){
    	            		for (int i=0;i<details.size();i++){
    	            			ExecutionMessage executionMessage=(ExecutionMessage)details.get(i);
    	            			if (ExecutionMessage.ERROR.equalsIgnoreCase(executionMessage.getMessagetype())){
    	        	        		message.append("Error: " + StringUtil.nullToEmpty(executionMessage.getMessage()) + "\n");
    	            			}else if (ExecutionMessage.WARNING.equalsIgnoreCase(executionMessage.getMessagetype())){
    	        	        		message.append("Warning: " + StringUtil.nullToEmpty(executionMessage.getMessage()) + "\n");    	            				
    	            			}
    	            		}
    	            	}
    	            	
    	            	if (jobException != null) {
    	                	//schedule simple job to be run in X hours time again
    	            		service.setDelayedSimpleTrigger(context.getTrigger().getKey().getName(), context.getJobDetail().getKey().getName(), 
    	                			context.getJobDetail().getKey().getGroup(), delayedTimeTrigger);
    	            	}
    	        		notifyByEmail(subject, message.toString(), receipientEmailAddr);	
    	        	}
        		}
        	}
        	service.updateJobStatus(context.getJobDetail().getKey().getGroup(), JobStatus.Waiting);
        }
        catch (CreateJobExecutionException e) {
        	_logger.error(e.getMessage(), e);
        }
        catch (JobScheduleException e) {
        	_logger.error(e.getMessage(), e);
        }
    }

    /**
     * Send email.
     *
     * @param subject the subject
     * @param message the message
     * @param receipientEmailAddr the receipient email addr
     */
    private void notifyByEmail(String subject, String message, String receipientEmailAddr) {
    	try{
	    	if (receipientEmailAddr!=null){
//				EmailService emailService = (EmailService) SpringServiceManager.getBean("_emailService");
//				emailService.sendMessage(subject, message , receipientEmailAddr);
	    	}
    	}catch(Exception e){
    		_logger.error("Error while sending out notification email. " + e.getMessage(), e);
    	}
    }
}