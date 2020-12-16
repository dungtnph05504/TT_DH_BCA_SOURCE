package com.nec.asia.nic.comp.trans.dao;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;

import junit.framework.TestCase;


public class NicTransactionDocumentDaoTestCase extends TestCase{
	public static ApplicationContext context = null;
	public static NicTransactionAttachmentDao  transactionDocumentDao = null;
	public static DocumentDataDao  documentDataDao = null;
	public NicTransactionDocumentDaoTestCase() {
		init();
	}
	
	
	
	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			transactionDocumentDao = context.getBean("transactionDocumentDao", NicTransactionAttachmentDao.class);
			documentDataDao = context.getBean("documentDataDao", DocumentDataDao.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}


	public void testFindAllDispatchedDocuments() {
		
		try {
		List<NicDocumentData> t = documentDataDao.findAllDispatchedDocuments(100);
		System.out.println( t.size());
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
//	
//	public void xtestDaoSave() {
//		log("start Save dao");
//		NicTransactionDocument nicObj = new NicTransactionDocument();
//		
//		NicTransaction transObj = new NicTransaction();
//		transObj.setTransactionId("RICHQ-2013-000001");
//		transObj.setApplnRefNo(transObj.getTransactionId());
//		transObj.setDateOfApplication(new Date());
//		transObj.setQueueNumber("R0001");
//		transObj.setTransactionType("REG");
//		transObj.setTransactionSubtype("NEW_CITIZEN");
//		transObj.setTransactionStatus("NEW");
//		transObj.setNin("T121299012345N");
//		
//		nicObj.setTransactionId(transObj.getTransactionId());
//		nicObj.setDocType("PH_CAP");
//		nicObj.setSerialNo("02");
//		nicObj.setCreateBy("SYSTEM");
//		nicObj.setCreateDate(new Date());
//		
//		try {
//			long result = transactionDocumentDao.save(nicObj);
//			log("save result:" + result);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		log(" end  Save");
//	}
//	
//	public void xtestDaoFindById() {
//		log("start find by Id");
//		
//		try {
//			NicTransactionDocument t = transactionDocumentDao.findById(Long.valueOf(123456789));
//			log("result t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log(" end  find by id");
//	}
//	
//	public void testDaoSaveOrUpdate () {
//		log("start SaveOrUpdate dao");
//		try {
//			NicTransactionDocument nicObj = transactionDocumentDao.findById(Long.valueOf(381));
//			byte[] docData = FileUtils.readFileToByteArray(new java.io.File("C:/Users/chris_wong/Desktop/NIC/SIT/chip_image/0.dat"));
//			nicObj.setDocData(docData);
//			nicObj.setUpdateBy("SYSTEM");
//			nicObj.setUpdateDate(new Date());
//			nicObj.setUpdateWkstnId("SYSTEM");
//			nicObj.setStatus("A");
//			transactionDocumentDao.saveOrUpdate(nicObj);
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		log(" end  SaveOrUpdate dao");
//	}
//	
//	public void xtestDaoMerge () {
//		log("start Merge dao");
//		NicTransactionDocument nicObj = new NicTransactionDocument();
//		NicTransaction transObj = new NicTransaction();
//		transObj.setTransactionId("RICHQ-2013-000001");
//		transObj.setApplnRefNo(transObj.getTransactionId());
//		transObj.setDateOfApplication(new Date());
//		transObj.setQueueNumber("R0001");
//		transObj.setTransactionType("REG");
//		transObj.setTransactionSubtype("NEW_CITIZEN");
//		transObj.setTransactionStatus("NEW");
//		transObj.setNin("T121299012345N");
//		
//		nicObj.setTransactionId(transObj.getTransactionId());
//		nicObj.setDocType("PH_CAP");
//		nicObj.setSerialNo("02");
//		nicObj.setCreateBy("SYSTEM");
//		nicObj.setCreateDate(new Date());
//		nicObj.setUpdateBy("SYSTEM");
//		nicObj.setUpdateDate(new Date());
//		nicObj.setUpdateWkstnId("SYSTEM");
//		nicObj.setTransactionDocId(6);
//		nicObj.setStatus("S");
//		
//		
//		try {
//			NicTransactionDocument t = transactionDocumentDao.merge(nicObj);
//			log(t.getTransactionId());
//			
//			//log("result t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log(" end  merge dao");
//	}
//	
//	public void xtestDaoDelete1() {
//		log("start delete using obj dao");
//		
//		try {
//			transactionDocumentDao.delete(Long.valueOf(5));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log(" end  delete Long dao");
//	}
//	
//	public void xtestDaoDelete2 () {
//		log("start delete using obj id dao");
//		NicTransactionDocument nicObj = new NicTransactionDocument();
//		NicTransaction transObj = new NicTransaction();
//		transObj.setTransactionId("RICHQ-2013-000001");
//		nicObj.setTransactionId(transObj.getTransactionId());
//		//nicObj.setNicTransaction(transObj);
//		nicObj.setTransactionDocId(7);
//		nicObj.setDocType("PH_CAP");
//		nicObj.setSerialNo("02");
//		
//		
//		try {
//			transactionDocumentDao.delete(nicObj);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log(" end  delete obj dao");
//	}
//	
//	
	public void xtestDaoFindAll() {
		log("start findAll dao");

		try {
			List<NicTransactionAttachment> list = transactionDocumentDao.findAll();
			log("list count : "+ ((list==null)?0:list.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}
	
	//customized methods
	//[M1] getTransactiondocuments
	public void xtestDaoGetTransactiondocuments() {
		log("start getTransactiondocuments dao");
		try {
			NicTransaction input = new NicTransaction();
			input.setTransactionId("EPP-2015-201512113245");
			NicTransaction result = transactionDocumentDao.getTransactiondocuments(input);
			log("list count : "+ ((result.getNicTransactionAttachments()==null)?0:result.getNicTransactionAttachments().size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  getTransactiondocuments dao");
	}
	//[M2] findNicTransactionAttachments
	public void xtestDaoFindNicTransactionAttachments() {
		log("start findNicTransactionAttachments dao");
		try {
			String transactionId = "EPP-2015-201512113245"; 
			String docType = "JPG"; 
			String serialNo = "01";
			List<NicTransactionAttachment> list = transactionDocumentDao.findNicTransactionAttachments(transactionId, docType, serialNo);
			log("list count : "+ ((list==null)?0:list.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findNicTransactionAttachments dao");
	}
	//[M3] getNicTransactionAttachmentByDocType
	public void xtestDaoGetNicTransactionAttachmentByDocType() {
		log("start getNicTransactionAttachmentByDocType dao");
		try {
			String docType = "JPG";
			String regSiteCode = "";
			String transactionId = "EPP-2015-201512113245"; 
			String surnameFull = "";
			String status = "";
			String surnameAtBirth = "";
			String otherName = "";
			String gender = ""; 
			Date dateOfBirth = null;
			List<Object[]> list = transactionDocumentDao.getNicTransactionAttachmentByDocType(docType, regSiteCode, transactionId, surnameFull
					, status, surnameAtBirth, otherName, gender, dateOfBirth);
			log("list count : "+ ((list==null)?0:list.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  getNicTransactionAttachmentByDocType dao");
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
	
}
