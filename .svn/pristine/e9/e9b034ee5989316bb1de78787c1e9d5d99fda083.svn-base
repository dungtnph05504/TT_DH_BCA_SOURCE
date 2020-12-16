package com.nec.asia.nix.dx.ws.client;

//import gov.mnis.signserver.ws.ResponseObject;
//import gov.mnis.signserver.ws.SigningService;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.perso.model.HandoverSync;
import service.perso.model.TransactionSyncRequest;

import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.dx.trans.BufEppDataPerson;
import com.nec.asia.nic.dx.trans.BufEppListRequest;
import com.nec.asia.nic.dx.trans.BufEppRequest;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.TransactionWS;;

public class TestChungWSClient extends TestCase {
	public static ApplicationContext context = null;
	public static TransactionWS bean = null;
	//public static NicUploadJobService nicUploadJobService = null;
	//0public static NicTransactionAttachmentService nicTransactionAttachmentService = null;
	public static ListHandoverService listHandoverService = null;
	
	public TestChungWSClient() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context-test.xml");
			bean = context.getBean("transactionWS", TransactionWS.class);
			//nicUploadJobService = context.getBean("nicUploadJobService", NicUploadJobService.class);
			//nicTransactionAttachmentService = context.getBean("nicTransactionAttachmentService", NicTransactionAttachmentService.class);
			listHandoverService = context.getBean("listHandoverService", ListHandoverService.class);
			//  nicTransactionAttachmentService = context.getBean("nicTransactionDocumentService", NicTransactionAttachmentService.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	
	public void testTerminate() {
		try {
			
			/*String tranId = "TUNT-2019-009211";
			NicUploadJob nicUp = nicUploadJobService.getUploadJobByTransactionIdSinger(null, tranId);
			List<NicTransactionAttachment> attachment = nicTransactionAttachmentService.findNicTransactionAttachments(tranId, null, null);
			TransactionSync tran = new TransactionSync();
			if(nicUp != null){
				tran.setNicUp(nicUp);
			}
			
			if(attachment != null && attachment.size() > 0){
				tran.setListattachment(attachment);
			}
			
			String xmlFamily = "";
			JAXBContext jaxbContext = JAXBContext.newInstance(TransactionSync.class, NicUploadJob.class, NicTransactionAttachment.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(tran, sw);
			xmlFamily = sw.toString();*/
			
			
			/*if(listH != null && listH.size() > 0){
				prModel.setStatus(99);
				prModel.setMessage("Size data: " + listH.size());
			}*/
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		/*log("start terminateNic");
		BufEppRequest request = new BufEppRequest();
		request.setTransactionId("TUNT-2019-009211");
		request.setTransactionMasterId("TUNT-2019-009241");
		request.setTransBMS(2473L);
		request.setTransCPD(null);
		try {
			BufEppDataPerson output = bean.downloadBufEppPerson(request);
			
			//BufEppListRequest outputList = bean.downloadListBufIdPerson("DSQ01-2019-009244");
			log(" end  terminateNic");
		} catch (FaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		//TODO EMIGRATION, DEATH, MISSING
//		String terminationType = "DEATH";
//		String terminationRemarks = "Police report sent to CSD Department.";
//		terminateNicRequest.setNin(nin);
//		terminateNicRequest.setTerminationDate(terminationDate);		
//		terminateNicRequest.setTerminationType(terminationType);
//		terminateNicRequest.setTerminationRemarks(terminationRemarks);
//		TerminateNicResponse output = bean.terminateNic(terminateNicRequest);
//		log("TerminateNicResponse: "+output+"\n"+ReflectionToStringBuilder.toString(output, ToStringStyle.MULTI_LINE_STYLE));
//		log(" end  terminateNic");
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
}
