package com.nec.asia.nic.comp.job;

import java.util.Date;

import org.exolab.castor.types.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.idserver.agent.payload.model.result.IdserverMatchResult;
import com.nec.asia.idserver.agent.payload.model.result.IdserverSubject;
import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.InquirySearchResultService;
import com.nec.asia.nic.comp.trans.service.exception.InquirySearchServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;

public class NicAfisLoadTestThread implements Runnable {

	String transactionId;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	ApplicationContext appContext = null;

	private InquirySearchResultService inquirySearchResultService;

	private IdserverAgentService idserverAgentService;

	public NicAfisLoadTestThread() {

	}

	@Override
	public void run() {
		Date date = new Date();
		System.out.println("\n-------------- NicAfisLoadTestThread: " + transactionId + " Name:" +Thread.currentThread().getName() +", Start: "+ date.toString());
		appContext = new ClassPathXmlApplicationContext("spring-context.xml");
		inquirySearchResultService = appContext.getBean("inquirySearchResultService", InquirySearchResultService.class);
		idserverAgentService = appContext.getBean("idserverAgentService", IdserverAgentService.class);

		int count = 0;
		try {

			IdserverMatchResult matchResult = idserverAgentService.inquiryFpScreeningByGivenIdentifier(transactionId);
			NicTransaction transaction = new NicTransaction();
			transaction.setTransactionId(transactionId);
			NicUploadJob uploadJob = new NicUploadJob();
			uploadJob.setWorkflowJobId(Long.valueOf(3000));

			saveSearchResult(matchResult, transaction, uploadJob);
			log("Number of transaction:" + count++);
		} catch (Exception e) {
			e.printStackTrace();
		}

		date = new Date();
		System.out.println("\n-------------- NicVerificationThread: " + Thread.currentThread().getName() + " End. " +  date.toString());
	}

	private long saveSearchResult(IdserverMatchResult matchResult, NicTransaction transDocs, NicUploadJob job) throws Exception {
		long searchResultId = 0;

		try {
			log("transid:" + transDocs.getTransactionId());

			String afisKeyNo = "000000000001";

			matchResult = this.cleanUpMatchResult(matchResult);

			searchResultId = inquirySearchResultService.saveSearchResult(InquirySearchResultService.SCREENING_FP_1_TO_N, matchResult, job.getWorkflowJobId(), transDocs.getTransactionId(), afisKeyNo);

			log("searchResultId:" + searchResultId);
			return searchResultId;
		} catch (InquirySearchServiceException ie) {
			throw new InquirySearchServiceException(ie);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	private IdserverMatchResult cleanUpMatchResult(IdserverMatchResult matchResult) throws TransactionServiceException {
		IdserverMatchResult updatedMatchResult = null;
		int hitCount = 0;
		if (matchResult != null) {
			updatedMatchResult = new IdserverMatchResult();
			updatedMatchResult.setHasHit(false);
			for (IdserverSubject hit : matchResult.getSubjectList()) {
				updatedMatchResult.setHasHit(true);
				updatedMatchResult.getSubjectList().add(hit);
				hitCount++;
			}
		}
		log("hitCount:" + hitCount);
		return updatedMatchResult;
	}

	public static void log(String message) {
		System.out.println(message);
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
