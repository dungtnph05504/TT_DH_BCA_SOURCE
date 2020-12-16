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
public class TransactionTask implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(TransactionTask.class);
	
	private NicUploadJobService uploadJobService; //static
	private int NTHREDS; // Thread Pool variable
	//NTHREDS = Runtime.getRuntime().availableProcessors(); //dynamic thread pool count

	public void runTask(){
		try {
			//log("----NTHREDS:"+NTHREDS);
			//log("JOB TASK called");
			List <NicUploadJob> uploadJobList = new ArrayList<NicUploadJob>();
			
			ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
			uploadJobList = uploadJobService.getTransactionJobsInQueue(); 
			//uploadJobList = uploadJobService.getTransactionJobsInQueueAFIS();
			log("uploadJobList size:"+uploadJobList.size());		
			if(uploadJobList != null && uploadJobList.size() > 0){
				for (NicUploadJob nicObj : uploadJobList){
					//setup executor
					//log("before set start time for:"+nicObj.getTransactionId());
					setStartTime(nicObj);
					//log("after set start time");
//					NicDemoThread t = new NicDemoThread();
//					t.setJobObj(nicObj);
//					executor.execute(t);
					NicVerificationThread t = new NicVerificationThread();
					t.setJobObj(nicObj);
					executor.execute(t);
					log("after execution of thread:"+nicObj.getTransactionId());
					//setEndTime(nicObj);
					
				}
				executor.shutdown();
		        while (!executor.isTerminated()) {
		        	//do nothing
		        }
			}//end if uploadJobList not empty
			else{
				log("---------NO NEW JOBS FOUND-------");
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
	
	private void setEndTime(NicUploadJob obj) throws Exception{
		try {
			obj.setJobEndTime(new Date());
			uploadJobService.saveOrUpdate(obj);
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
		//TransactionTask.uploadJobService = uploadJobService;
		this.uploadJobService = uploadJobService;
	}
	
	private void log(String message){
		logger.info(">>>>>"+message);
	}
	
	
}
