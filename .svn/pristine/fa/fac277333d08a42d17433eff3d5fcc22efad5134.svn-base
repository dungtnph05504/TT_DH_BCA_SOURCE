package com.nec.asia.nic.comp.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.job.command.NicCommandExecutor;
import com.nec.asia.nic.comp.trans.domain.NicJobAfisCheckResult;
import com.nec.asia.nic.comp.trans.domain.NicJobCpdCheckResult;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;


/**
 * @author johnpaul_millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Jun 3, 2013
 */
public class NicVerificationJobTest extends TestCase{

	
	private ApplicationContext context = null;
	private NicUploadJob nicObj;
	private NicCommandExecutor control;
	ApplicationContext appContext = null;
	private static final int NTHREDS = 3; // Thread Pool variable
	
	//@Autowired
	private NicUploadJobService uploadJobService = null;
	
	public NicVerificationJobTest() {
		init();
	}
	
	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			//setup executor
			control = (NicCommandExecutor)context.getBean("baseExecutor");
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
@Override
protected void setUp(){
	uploadJobService = context.getBean("uploadJobService", NicUploadJobService.class);
	
	nicObj = new NicUploadJob();
	nicObj.setWorkflowJobId(1L);
	nicObj.setTransactionId("RICHQ-2017-000163");
	nicObj.setCreateDatetime(new Date());
	nicObj.setCreateBy("JPRM");
	nicObj.setJobCurrentStage("");
	nicObj.setJobType("TEST");
	
	nicObj.setAfisCheckStatus("02");
	nicObj.setAfisRegisterStatus("02");
	nicObj.setCpdCheckStatus("02");
	nicObj.setJobStartTime(new Date());
	nicObj.setJobEndTime(new Date());
	nicObj.setPersoRegisterStatus("00");
	
}

	public void xtestJobRun(){
		boolean result;
		
		try {
				log("\n xxxxxxxxxxx   start verification testing");
				//get spring xml
				
				control.doCommand(nicObj);
				result = true;
				log("\n xxxxxxxxxxx   start end");
			
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		log(" end  testing");
		assertTrue(result);
	}
	
	
	public void testThread() throws InterruptedException{
		//162
		Long workflowJobId = new Long(21);
		ArrayList <NicUploadJob> uploadJobList = new ArrayList<NicUploadJob>();
		//TODO check for unprocessed jobs in NicUploadJob
		
		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		nicObj = uploadJobService.findById(workflowJobId);
		if (nicObj!=null && nicObj.getPersoRegisterStatus().equals("02")) {
			nicObj.setPersoRegisterStatus("09");
		}
		
		log("\n xxxxx:"+nicObj.getTransactionId());
		uploadJobList.add(nicObj);
		
		if(uploadJobList != null && uploadJobList.size() > 0){
		
			for (NicUploadJob nicObj : uploadJobList){
				//setup executor
				NicVerificationThread runnable = new NicVerificationThread();
				runnable.setJobObj(nicObj);
				Runnable worker = runnable;
				executor.execute(worker);
			}
			executor.shutdown();
	        while (!executor.isTerminated()) {
	        	//do nothing
	    		log("executing:"+nicObj.getTransactionId()+","+nicObj.getWorkflowJobId());
	        	Thread.sleep(60000L);
	        }
		}
	}
	
	public void xtestConnectSvc(){
		
		//note:not working yet
		try {
			log("start testConnectSvc:"+uploadJobService);
			NicUploadJob t = null;
			t = uploadJobService.findById(Long.valueOf(1));
			log("t:"+t);
			//uploadJobService.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
}
