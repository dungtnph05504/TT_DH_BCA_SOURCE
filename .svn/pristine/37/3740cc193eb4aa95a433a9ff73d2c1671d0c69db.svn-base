package com.nec.asia.nic.comp.job;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.nec.asia.nic.comp.job.command.NicCommandExecutor;
import com.nec.asia.nic.comp.job.utils.ApplicationContextProvider;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;

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
public class NicVerificationThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(NicVerificationThread.class);

	private ApplicationContext appContext;
	private NicUploadJob jobObj;
	private NicCommandExecutor<NicUploadJob> baseExecutor;

	public NicVerificationThread() {
	}
	
	public NicVerificationThread(NicUploadJob jobObj) {
		this.jobObj = jobObj;
	}

	@Override
	public void run() {
		log("-------------- NicVerificationThread: " + Thread.currentThread().getName());
		// processCommand();

		appContext = ApplicationContextProvider.getApplicationContext();

		baseExecutor = (NicCommandExecutor) appContext.getBean("baseExecutor");

		//log("\n-------------- NicVerificationThread baseExecutor:" + baseExecutor + "\n");


		baseExecutor.doCommand(jobObj);

		log("-------------- NicVerificationThread: " + Thread.currentThread().getName() + " End.");
	}

	public NicUploadJob getJobObj() {
		return jobObj;
	}

	public void setJobObj(NicUploadJob jobObj) {
		this.jobObj = jobObj;
	}
	
	private void log(String message){
		logger.info(message);
	}
}