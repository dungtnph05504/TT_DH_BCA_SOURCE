package com.nec.asia.nic.comp.job;

import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.job.service.ReportDataAccessService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;

/*
 * Modification History:
 * 
 * 15 Jan 2016 (cw) - remove daily store procedure to generate FP Data.
 */
public class DailyTask implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DailyTask.class);
	
	private static NicUploadJobService uploadJobService;
	private static ReportDataAccessService reportDataAccessService;

	public void runTask(){
		
		try {
			
			logger.info("DAILY JOB TASK called, uploadJobService contains:"+uploadJobService);
			logger.info("DAILY JOB TASK called, reportDataAccessService contains:"+reportDataAccessService);
			//uploadJobService.callPopulateNicFpSP("03-12-13");
			
//			ReportDataAccessService service = (ReportDataAccessService) SpringServiceManager.getBean("reportDataAccessService");
//			service.executeDBStatistics("nic", "SYSTEM");
//			service.executeCustomizedDBStatistics(new String[]{"NICDB.POPULATE_NIC_FP_DATA"}, null, "nicapp", "SYSTEM");
		} catch (Exception e) {
			logger.error("JOB EXCEPTION:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void setUploadJobService(NicUploadJobService uploadJobService) {
		DailyTask.uploadJobService = uploadJobService;
	}
	
	public void setReportDataAccessService(
			ReportDataAccessService reportDataAccessService) {
		DailyTask.reportDataAccessService = reportDataAccessService;
	}
}
