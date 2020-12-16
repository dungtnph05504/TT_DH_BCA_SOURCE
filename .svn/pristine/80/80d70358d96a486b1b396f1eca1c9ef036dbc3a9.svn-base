package com.nec.asia.nic.comp.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nec.asia.nic.comp.trans.service.PersoService;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

/**
 * The job to update dispatch info from Perso.
 * 
 * @author khang
 *
 */
@SuppressWarnings("deprecation")
public class UpdateDispatchInfoJob extends QuartzJobBean implements StatefulJob {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateDispatchInfoJob.class);
	
	PersoService persoService = (PersoService) SpringServiceManager.getBean("persoService");

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("-----------------UpdateDispatchInfoJob begins----------------------");
		try {
		    persoService.updateDispatchInfo();
		}
        catch (Throwable th) {
            logger.error("[UpdateDispatchInfoJob] Exception:"+th.getMessage(), th);
            throw new JobExecutionException("Error encountered Update Dispatch Info job:"+ th.getMessage());
        }
		logger.info("----------------------------------------------------------------");
		logger.info("--------- UpdateDispatchInfoJob Completed --------------------");
		logger.info("----------------------------------------------------------------");
	}
	
	
}
