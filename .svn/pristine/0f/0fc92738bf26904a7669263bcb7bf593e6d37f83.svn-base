package com.nec.asia.nic.comp.job;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.queuesJobSchedule.service.LogJobScheduleService;
import com.nec.asia.nic.comp.queuesJobSchedule.service.QueuesJobScheduleService;
import com.nec.asia.nic.comp.trans.domain.LogJobSchedule;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;

/* 
 * Modification History:
 *  
 * 27 Jun 2017 (chris): clean up logging, change NicUploadJobService to non static
 */
public class LdsProcessTask implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(LdsProcessTask.class);
	
	private QueuesJobScheduleService queuesJobScheduleService; //static
	private int NTHREDS; // Thread Pool variable
	//NTHREDS = Runtime.getRuntime().availableProcessors(); //dynamic thread pool count

	public void runTask(){
		try {
			//log("----NTHREDS:"+NTHREDS);
			//log("JOB TASK called");
			List <QueuesJobSchedule> uploadJobList = new ArrayList<QueuesJobSchedule>();
			
			ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
			uploadJobList = queuesJobScheduleService.getListInQueuesForLds(); 
			//uploadJobList = uploadJobService.getTransactionJobsInQueueAFIS();
			log("uploadJobList size:"+uploadJobList.size());		
			if(uploadJobList != null && uploadJobList.size() > 0){
				for (QueuesJobSchedule nicObj : uploadJobList){
					NicLdsProcessThread t = new NicLdsProcessThread();
					t.setJobObj(nicObj);
					executor.execute(t);
					log("after execution of thread CODE:"+nicObj.getCode() + " || RecordID: " + nicObj.getId());
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
	
	/*private void setStartTime(QueuesJobSchedule obj) throws Exception{
		try {
			if(obj.getJobStartTime() == null){
				obj.setJobStartTime(new Date());
				uploadJobService.saveOrUpdate(obj);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private void setEndTime(QueuesJobSchedule obj) throws Exception{
		try {
			obj.setJobEndTime(new Date());
			uploadJobService.saveOrUpdate(obj);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}*/
	
	
	//getter & setter 
	public int getNTHREDS() {
		return NTHREDS;
	}

	public void setNTHREDS(int nTHREDS) {
		NTHREDS = nTHREDS;
		log("----NTHREDS:"+NTHREDS);
	}
	
	public QueuesJobScheduleService getQueuesJobScheduleService() {
		return queuesJobScheduleService;
	}

	public void setQueuesJobScheduleService(QueuesJobScheduleService queuesJobScheduleService) {
		//TransactionTask.uploadJobService = uploadJobService;
		this.queuesJobScheduleService = queuesJobScheduleService;
	}
	
	private void log(String message){
		logger.info(">>>>>"+message);
	}
	
	
}
