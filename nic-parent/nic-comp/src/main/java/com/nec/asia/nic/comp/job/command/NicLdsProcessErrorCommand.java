package com.nec.asia.nic.comp.job.command;

import com.nec.asia.nic.comp.trans.domain.NicUploadJob;

public class NicLdsProcessErrorCommand extends BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	@Override
	public void doSomething(NicUploadJob job) {
		logger.info("NicLdsProcessErrorCommand ----- {} , {} ", job.getTransactionId(), job.getJobType());
		logger.info("\n-----inside ERROR HANDLER COMMAND -  thread stop");
	}
}
