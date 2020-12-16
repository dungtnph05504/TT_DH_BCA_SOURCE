package com.nec.asia.nic.comp.job;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.nec.asia.nic.comp.job.command.NicCommandExecutor;
import com.nec.asia.nic.comp.job.utils.ApplicationContextProvider;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;

/**
 * @author johnpaul_millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Jun 6, 2013
 *        <p>
 *        Thread Class for Verification Job. Calls NicCommandExecutor and
 *        executes Command class
 *        </p>
 */

/* 
 * Modification History:
 *  
 * 27 Jun 2017 (chris): clean up logging
 */
public class NicSyncPassportThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(NicSyncPassportThread.class);

	private ApplicationContext appContext;
	private QueuesJobSchedule jobObj;
	private NicCommandExecutor<QueuesJobSchedule> baseExecutor;

	public NicSyncPassportThread() {
	}
	
	public NicSyncPassportThread(QueuesJobSchedule jobObj) {
		this.jobObj = jobObj;
	}

	@Override
	public void run() {
		log("-------------- NicSyncPassportThread: " + Thread.currentThread().getName());
		// processCommand();

		appContext = ApplicationContextProvider.getApplicationContext();

		baseExecutor = (NicCommandExecutor) appContext.getBean("baseExecutorSync");

		//log("\n-------------- NicVerificationThread baseExecutor:" + baseExecutor + "\n");


		baseExecutor.doCommand(jobObj);

		log("-------------- NicSyncPassportThread: " + Thread.currentThread().getName() + " End.");
	}

	public QueuesJobSchedule getJobObj() {
		return jobObj;
	}

	public void setJobObj(QueuesJobSchedule jobObj) {
		this.jobObj = jobObj;
	}
	
	private void log(String message){
		logger.info(message);
	}
}