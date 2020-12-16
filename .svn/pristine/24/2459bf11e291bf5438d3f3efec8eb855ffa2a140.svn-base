package com.nec.asia.nic.comp.trans.service.impl;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.idserver.agent.service.exception.IdserverAgentServiceException;
import com.nec.asia.nic.comp.job.dto.HitCandidateDTO;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.dx.trans.RejectionData;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.utils.DateUtil;

import junit.framework.TestCase;

public class NicUploadJobServiceTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static NicUploadJobService service = null;
	public static NicTransactionService nicTransactionService = null;
	public static IdserverAgentService idsvrAgentService = null;
	public NicUploadJobServiceTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			service = context.getBean("uploadJobService", NicUploadJobService.class);
			nicTransactionService = context.getBean("nicTransactionService", NicTransactionService.class);
			idsvrAgentService = context.getBean("idserverAgentService", IdserverAgentService.class);
		} catch (Error e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	public void xtestGetAfisHitCandidateListByJobId() {
		log("start NicUploadJobService - getAfisHitCandidateListByJobId");
		try {
			Long jobId = 5L;
			List<HitCandidateDTO> resultList = service.getAfisHitCandidateListByJobId(jobId);
			if (CollectionUtils.isNotEmpty(resultList)) {
				for (HitCandidateDTO dto : resultList) {
					log("dto.transactionId: "+dto.getTransactionId());
					log("dto.nin: "+dto.getNin());
					log("dto.firstName: "+dto.getFirstName());
					//log("dto.photo: "+dto.getPhoto().length());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		log(" end  NicUploadJobService - getAfisHitCandidateListByJobId");
	}
	
	public void xtestGetRejectionDataListBySiteCode() {
		log("start NicUploadJobService - getRejectionDataListBySiteCode");
		try {
			
			String siteCode = "RICHQ";
			Date start = DateUtil.strToSqlDate("01082013", DateUtil.FORMAT_DDMMYYYY);
			Date end = DateUtil.strToSqlDate("10082013", DateUtil.FORMAT_DDMMYYYY);
			List<RejectionData> resultList = service.getRejectionDataListBySiteCode(siteCode, start, end);
			int i = 0;
			if (CollectionUtils.isNotEmpty(resultList)) {
				for (RejectionData dto : resultList) {
					log("dto["+(i)+"].transactionId: "+dto.getTransactionID());
					log("dto["+(i)+"].transactionStatus: "+dto.getTransactionStatus());
					log("dto["+(i)+"].rejectReason: "+dto.getRejectReason());
					log("dto["+(i)+"].rejectRemarks: "+dto.getRejectRemarks());
					log("dto["+(i)+"].rejectBy: "+dto.getRejectBy());
					log("dto["+(i)+"].rejectDate: "+dto.getRejectDateTime());
					//log("dto["+(i)+"].rejectReason: "+dto.getRejectionInfo().getReason());
					//log("dto["+(i)+"].rejectRemarks: "+dto.getRejectionInfo().getRemark());
					i++;
					log("--------------------------------------------------------");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		log(" end  NicUploadJobService - getRejectionDataListBySiteCode");
	}
			
	public void xtestGetFPandMatch() {
		String transId = "RICHQ-2013-002039";
		try {
			NicTransaction nicTransDBO = nicTransactionService.findById(transId, false, true, true, false);
//			String nicId = ""+nicTransDBO.getNicRegistrationData().getNicId();
//			
//			List<AbstractBiometricData<IdserverImageFingerprint>> biometricDataList = buildFingerPrints(nicTransDBO.getNicTransactionDocuments());
//			//1:N
//			IdserverMatchResult matchResult = idsvrAgentService.inquiryFpScreening(biometricDataList);
//			if (matchResult!=null) {
//				for (IdserverSubject s : matchResult.getSubjectList()) {
//					log("[inquiryFpScreening] IdserverSubject: " + ReflectionToStringBuilder.toString(s, ToStringStyle.MULTI_LINE_STYLE));
//				}
//			}
			//1:1
			//IdserverSubject idSvrSubj = idsvrAgentService.inquiryFpVerification(biometricDataList, nicTransDBO.getTransactionId(), nicId);
		} catch (TransactionServiceException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} 
	}
	
//	private List<AbstractBiometricData<IdserverImageFingerprint>> buildFingerPrints(Collection<NicTransactionDocument> transDocsList){
//		List<AbstractBiometricData<IdserverImageFingerprint>> biometricDataList = new ArrayList<AbstractBiometricData<IdserverImageFingerprint>>();
//		try {
//			for(NicTransactionDocument tDoc : transDocsList){
//				if (tDoc.getDocType().substring(0, 2).equalsIgnoreCase("FP")){
//					log("flag FP: "+tDoc.getDocType());
//					IdserverImageFingerprint fingerprint = new IdserverImageFingerprint();
//					fingerprint.setFingerPosition(FingerPosition.getInstance(Short.valueOf(tDoc.getSerialNo())).getKey());
//					
//					log("finger pos:"+fingerprint.getFingerPosition());
//					
//					fingerprint.setImageType(IdserverImageType.WSQ);
//					fingerprint.setImageData(tDoc.getDocData());		
//					biometricDataList.add(fingerprint);	
//				} else {
//					log("flag not FP, "+tDoc.getDocType());	
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//			
//		return biometricDataList;
//	}
//	
	public void xtestResetOfficerId() {
		log("start NicUploadJobService - updateJobToResetIncompletedInvestigation");
		try {
			String updateBy = "SYSTEM";
			String updateWkstnId = "SYSTEM";
			
			service.updateJobToResetIncompletedInvestigation(updateBy, updateWkstnId);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		log(" end  NicUploadJobService - updateJobToResetIncompletedInvestigation");
	}
	
	public void xtestFindAllForPagination() {
		log("start NicUploadJobService - findAllForPagination");
		try {
			PageRequest pageRequest = new PageRequest();
			pageRequest.setPageNo(0);
			pageRequest.setSortname("uploadJobId");
			pageRequest.setSortorder("asc");
			NicUploadJob nicUploadJob = new NicUploadJob();
			String[] investStatus = null;
			Long jobId = null;
			boolean isShowErrTrans = true;
			service.findAllForPagination(pageRequest, nicUploadJob, investStatus, jobId, isShowErrTrans);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		log(" end  NicUploadJobService - findAllForPagination");
	}
	
	public void testGetJobsToReprocess() {
		log("start NicUploadJobService - getJobsToReprocess");
		try {
			service.getJobsToReprocess();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		log(" end  NicUploadJobService - getJobsToReprocess");
	}
		
	
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}
