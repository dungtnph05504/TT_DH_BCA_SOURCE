package com.nec.asia.nic.comp.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;

/* 
 * Modification History:
 *  
 * 27 Jun 2017 (chris): clean up logging, change NicUploadJobService to non static
 */
public class TransactionReprocessTask implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(TransactionReprocessTask.class);
	
	private int NTHREDS; // Thread Pool variable
	
	private NicUploadJobService uploadJobService; //static

	public void runTask(){
		try {
			//log("----NTHREDS:"+NTHREDS);
			//log("JOB REPROCESS TASK called\n");
			//log("uploadJobService:"+uploadJobService);
			List <NicUploadJob> uploadJobList = new ArrayList<NicUploadJob>();
			
			ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
			uploadJobList = uploadJobService.getJobsToReprocess();
			log("reprocessJobList size:"+uploadJobList.size());		
			if(uploadJobList != null && uploadJobList.size() > 0){
				for (NicUploadJob nicObj : uploadJobList){
					//setup executor
					//log("before set start time, job id:"+nicObj.getWorkflowJobId());
					setStartTime(nicObj);
					//log("after set start time, job id:"+nicObj.getWorkflowJobId());
					
					NicVerificationThread t = new NicVerificationThread();
					
					t.setJobObj(nicObj);
					executor.execute(t);
					
					log("after execution of thread, job id:"+nicObj.getWorkflowJobId());
					//setEndTime(nicObj);
				}
				executor.shutdown();
		        while (!executor.isTerminated()) {
		        	//do nothing
		        }
			}//end if uploadJobList not empty
			else{	
				log("---------NO REPROCESS JOBS FOUND-------\n");
			}
		} catch (Exception e) {
			logger.error("JOB EXCEPTION:"+e.getMessage(), e);
			//e.printStackTrace();
		}
	}
	
	private void setStartTime(NicUploadJob obj) throws Exception{
		try {
			if(obj.getJobStartTime() == null){
				obj.setJobStartTime(new Date());
				uploadJobService.saveOrUpdate(obj);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	//getter & setter 
	public int getNTHREDS() {
		return NTHREDS;
	}

	public void setNTHREDS(int nTHREDS) {
		NTHREDS = nTHREDS;
		log("----NTHREDS:"+NTHREDS);
	}
	
	public NicUploadJobService getUploadJobService() {
		return uploadJobService;
	}

	public void setUploadJobService(NicUploadJobService uploadJobService) {
		//TransactionReprocessTask.uploadJobService = uploadJobService;
		this.uploadJobService = uploadJobService;
	}
	
	private void log(String message){
		logger.info(">>>>>"+message);
	}
	
	
}
