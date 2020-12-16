package com.nec.asia.nic.comp.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nec.asia.nic.comp.trans.service.ReconReportService;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

public class ReconReportDataGenerationJob extends QuartzJobBean implements StatefulJob {
	private static final Logger logger = LoggerFactory.getLogger(ReconReportDataGenerationJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		/* Transaction Recon Report */
		//To refresh all Transaction Recon Report Data
		logger.info("---------------------------------------------------");
		logger.info("---------Recon Report NIC Data Generation ---------");
		logger.info("---------------------------------------------------");
		try {
			ReconReportService reconReportservice = (ReconReportService) SpringServiceManager.getBean("reconReportService");
			reconReportservice.updateNicTransactionReconReportData(null, null, null, null);
		}		
        catch (Throwable th) {
            logger.error("[ReconReportDataGenerationJob] Exception:"+th.getMessage(), th);
            throw new JobExecutionException("Error encountered daily job:"+ th.getMessage());
        }
		logger.info("-------------------------------------------------------------");
		logger.info("---------Recon Report NIC Data Generation Completed ---------");
		logger.info("-------------------------------------------------------------");
	}
}
