package com.nec.asia.nic.comp.trans.dao;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicTransaction;

import junit.framework.TestCase;


public class NicTransactionDaoTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static NicTransactionDao transactionDao = null;
	public NicTransactionDaoTestCase() {
		init();
	}
	
	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			transactionDao = context.getBean("transactionDao", NicTransactionDao.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void xtestDaoSaveTransaction () {
		log("start SaveTransaction dao");
		NicTransaction transObj = new NicTransaction();
		transObj.setTransactionId("RICHQ-2013-001419");
		transObj.setApplnRefNo(transObj.getTransactionId());
		transObj.setDateOfApplication(new Date());
//		transObj.setQueueNumber("R0056");
		transObj.setTransactionType("REG");
//		transObj.setTransactionSubtype("NEW_CITIZEN");
		transObj.setTransactionStatus("NEW");
//		transObj.setNin("T121299012345N");
		try {
			String result = transactionDao.save(transObj);
			log("save result:" + result);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		log(" end  SaveTransaction dao");
	}
	
	public void testDaoFindById() { //Transaction
		log("start transaction dao");

		try {
			NicTransaction t = transactionDao.findById("004-2016-000008");
			log("transaction t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  transaction dao");
	}
	
	public void xtestDaoSaveOrUpdate () {
		log("start SaveOrUpdate dao");
		NicTransaction transObj = new NicTransaction();
		transObj.setTransactionId("RICHQ-2013-000001");
		transObj.setApplnRefNo(transObj.getTransactionId());
		transObj.setDateOfApplication(new Date());
//		transObj.setQueueNumber("R0001");
		transObj.setTransactionType("REG");
//		transObj.setTransactionSubtype("NEW_CITIZEN");
		transObj.setTransactionStatus("NEW");
//		transObj.setNin("T121299012345N");
		try {
			transactionDao.saveOrUpdate(transObj);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		log(" end  SaveOrUpdate dao");
	}
	
	public void xtestDaoMerge () {//
		log("start Merge dao");
		NicTransaction transObj = new NicTransaction();
		transObj.setTransactionId("RICHQ-2013-000001");
		transObj.setApplnRefNo(transObj.getTransactionId());
		transObj.setDateOfApplication(new Date());
//		transObj.setQueueNumber("R0001");
		transObj.setTransactionType("REG");
//		transObj.setTransactionSubtype("NEW_CITIZEN");
		transObj.setTransactionStatus("NEW");
//		transObj.setNin("T121299012345N");
		try {
			NicTransaction t = transactionDao.merge(transObj);
			log("transaction t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  merge dao");
	}
	
	public void xtestDaoDelete1() {
		log("start delete using transactionId String dao");

		try {
			transactionDao.delete("RICHQ-2013-001419");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  delete tranIdString dao");
	}
	
	public void xtestDaoDelete2 () {
		log("start delete using Transaction obj dao");
		NicTransaction transObj = new NicTransaction();
		transObj.setTransactionId("RICHQ-2013-000001");
//		transObj.setNin("T121299012345N");
		try {
			transactionDao.delete(transObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  delete tranObj dao");
	}
	
	
	public void xtestDaoFindAll() {
		log("start findAll dao");

		try {
			List<NicTransaction> list = transactionDao.findAll();
			log("transaction list count : "+ ((list==null)?0:list.size()));
			
			//log("transaction t : "+list.get(0)+"\n"+ReflectionToStringBuilder.toString(list.get(0), ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}
	
	
	public void xtestDaoFindAll2() {
		log("start findAll dao");

		try {
			NicTransaction transObj = new NicTransaction();
			//transObj.settran("");
			
			List<NicTransaction> list = transactionDao.findAll(transObj);
			log("transaction list count : "+ ((list==null)?0:list.size()));
			
			//log("transaction t : "+list.get(0)+"\n"+ReflectionToStringBuilder.toString(list.get(0), ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}
	public void xtestDaoFindTransactionStatus() { 
		log("start transaction dao.getTransactionStatusById");

		try {
			String transactionStatus = transactionDao.getTransactionStatusById("RICHQ-2013-001714");
			log("transactionStatus : "+transactionStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  transaction dao.getTransactionStatusById");
	}
	
	public void xtestDaoFindAllByFields() { //Transaction
		log("start transaction dao.findAllByFields");

		try {
			String surname = null;
			String firstName = "%VOUS%";
			String middleName = null;
			String sex = null;
			String dob = null; //"09-01-1990";
			List<NicTransaction> resultList = transactionDao.findAllByFields( surname, firstName, middleName, sex, dob);
			for (NicTransaction t : resultList) {
				log("transaction t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  transaction dao.findAllByFields");
	}
	
//	public void testDaofindAllByCcn() {
//		log("start findbyccn");
//		
//		try {
//			//T121299012345N
//			//T123456789012A
//			
//			//123456789 - RICHQ-2013-000001 - S2345678
//			//222222222
//			String ccn = "123459999";
//			
//			List<NicTransaction> list2 = transactionDao.findAllByCcn(ccn);
//			List<NicTransaction> list = new ArrayList<NicTransaction>();
//				
//			
//			for (NicTransaction nicTransObj: list2){
//				list.add(transactionDao.getTransactionIssuanceDatas(nicTransObj));
//			}
//			
//			
//			// to test
//			if(list.size() > 0){
//				
//				int x = 0;
//				 
//				for (NicTransaction nicTransObj: list){
//					 x+=1;
//					 log("trans obj ("+x+")");
//					NicTransaction t1 =  nicTransObj;
//					 log("test flag:"+t1.getClass());
//					 log("test flag2:"+t1.getTransactionId());
//					 log("test flag3:"+t1.getNicIssuanceDatas().size());
//					
//					 log("test flag 4, is issuance data initialized:"+Hibernate.isInitialized(t1.getNicIssuanceDatas()));
//					
//					  for (NicIssuanceData nicIssObj: t1.getNicIssuanceDatas()){
//						  
//						  log("!!!!! test flag 5:"+nicIssObj.getCcn());
//						  
//					  }
//					  
//				 }
//			}
//			
//			log("test end");
//			
//			//log("list ccn result : "+x.getN+"\n"+ReflectionToStringBuilder.toString(x, ToStringStyle.MULTI_LINE_STYLE));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log("end  findAll dao");
//	}
	
//	public void xtestDaofindTransWithDocs() {
//		log("start findAllWithDocs");
//		
//		try {
//			//T121299012345N
//			//T123456789012A
//			
//			//123456789 - RICHQ-2013-000001 - S2345678
//			//222222222
//			String transId = "RICHQ-2013-000001";
//
//			NicTransaction transObj = new NicTransaction();
//			transObj.setTransactionId(transId);
//	
//			transObj = transactionDao.findById(transId);
//			transObj = transactionDao.getTransactiondocuments(transObj);
//			
//			// to test
//			log("flag 1:"+transObj.getTransactionId());
//			log("flag 2:"+transObj.getNicTransactionDocuments());
//			
//			for(NicTransactionDocument transDoc: transObj.getNicTransactionDocuments()){
//				log("flag 3:"+ReflectionToStringBuilder.toString(transDoc, ToStringStyle.MULTI_LINE_STYLE));
//			}
//			
//			log("test end");
//			//log("list ccn result : "+x.getN+"\n"+ReflectionToStringBuilder.toString(x, ToStringStyle.MULTI_LINE_STYLE));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log("end  findAll dao");
//	}
	
//	public void xtestDaofindTransRegData() {
//		log("start findTransRegData");
//		
//		try {
//			
//			String transId = "RICHQ-2013-000001";
//
//			NicTransaction transObj = new NicTransaction();
//			transObj.setTransactionId(transId);
//
//			transObj = transactionDao.findById(transId);
//			transObj = transactionDao.getTransactionRegistrationData(transObj);
//			
//			// to test
//			log("flag 1:"+transObj.getTransactionId());
//			log("flag 2:"+transObj.getNicRegistrationData());
//		
//				log("flag 3:"+ReflectionToStringBuilder.toString(transObj, ToStringStyle.MULTI_LINE_STYLE));
//				log("flag 4:"+ReflectionToStringBuilder.toString(transObj.getNicRegistrationData(), ToStringStyle.MULTI_LINE_STYLE));
//			
//			log("test end");
//			//log("list ccn result : "+x.getN+"\n"+ReflectionToStringBuilder.toString(x, ToStringStyle.MULTI_LINE_STYLE));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log("end  findAll dao");
//	}
//	
	
	public static void log(String message) {
		System.out.println(message);
	}
}
