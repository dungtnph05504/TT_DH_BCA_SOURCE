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
public class NicLdsProcessThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(NicLdsProcessThread.class);

	private ApplicationContext appContext;
	private QueuesJobSchedule jobObj;
	private NicCommandExecutor<QueuesJobSchedule> baseExecutor;

	public NicLdsProcessThread() {
	}
	
	public NicLdsProcessThread(QueuesJobSchedule jobObj) {
		this.jobObj = jobObj;
	}

	@Override
	public void run() {
		log("-------------- NicLdsProcessThread: " + Thread.currentThread().getName());
		// processCommand();

		appContext = ApplicationContextProvider.getApplicationContext();

		baseExecutor = (NicCommandExecutor) appContext.getBean("baseExecutorLds");

		//log("\n-------------- NicVerificationThread baseExecutor:" + baseExecutor + "\n");


		baseExecutor.doCommand(jobObj);

		log("-------------- NicLdsProcessThread: " + Thread.currentThread().getName() + " End.");
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