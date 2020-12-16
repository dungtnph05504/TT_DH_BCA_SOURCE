package com.nec.asia.nic.comp.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nec.asia.nic.comp.trans.service.PersoService;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

/**
 * The job to update status from Perso.
 * 
 * @author khang
 *
 */
public class UpdatePersoStatusJob extends QuartzJobBean implements StatefulJob {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdatePersoStatusJob.class);
	
	PersoService persoService = (PersoService) SpringServiceManager.getBean("persoService");

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("-----------------UpdatePersoStatusJob begins----------------------");
		try {
			logger.info("Instance: {}, Trigger:{}, Fired at: {}", 
					context.getScheduler().getSchedulerInstanceId(), 
					context.getTrigger().getKey(),
					context.getFireTime());			
		}catch (SchedulerException ex) { }
		try {
		    persoService.updatePersoStatus();
		}
        catch (Throwable th) {
            logger.error("[UpdatePersoStatusJob] Exception:"+th.getMessage(), th);
            throw new JobExecutionException("Error encountered Update Perso Status job:"+ th.getMessage());
        }
		logger.info("----------------------------------------------------------------");
		logger.info("--------- UpdatePersoStatusJob Completed --------------------");
		logger.info("----------------------------------------------------------------");
	}
	
	
}
