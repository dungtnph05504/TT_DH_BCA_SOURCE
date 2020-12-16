package com.nec.asia.nic.comp.job;

import java.net.InetAddress;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

/*
 * Modification History:
 * 
 * 07 Jan 2014 (chris) : init batch job
 */

public class InvestigationOfficerIdDailyResetJob extends QuartzJobBean implements StatefulJob {
	private static final Logger logger = LoggerFactory.getLogger(InvestigationOfficerIdDailyResetJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		/* Daily Job to reset investigation officer id for incomplete investigation. */
		logger.info("---------------------------------------------------");
		logger.info("---------  Reset Investigation Officer ID ---------");
		logger.info("---------------------------------------------------");
		try {
			String updateBy = "SYSTEM";
			String updateWkstnId = "SYSTEM";
			try {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				updateWkstnId = localMachine.getHostName().toUpperCase();
			} catch (Exception e) {			
			}
			NicUploadJobService uploadJobService = (NicUploadJobService) SpringServiceManager.getBean("uploadJobService");
			uploadJobService.updateJobToResetIncompletedInvestigation(updateBy, updateWkstnId);
		}
        catch (Throwable th) {
            logger.error("[InvestigationJobDailyResetJob] Exception:"+th.getMessage(), th);
            throw new JobExecutionException("Error encountered daily job:"+ th.getMessage());
        }
		logger.info("----------------------------------------------------------------");
		logger.info("--------- Reset Investigation Officer ID Job Completed ---------");
		logger.info("----------------------------------------------------------------");
	}
}
