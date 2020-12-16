package com.nec.asia.nic.comp.trans.dao;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;


public class NicTransactionLogDaoTestCase extends TestCase{
	public static ApplicationContext context = null;
	public static  NicTransactionLogDao  transactionLogDao = null;
	public NicTransactionLogDaoTestCase() {
		init();
	}
	
	
	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			transactionLogDao = context.getBean("transactionLogDao", NicTransactionLogDao.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void testDaoSave() {
		log("start Save dao");
		NicTransactionLog nicObj = new NicTransactionLog();
		nicObj.setRefId("CCM01-2013-000276");
		nicObj.setTransactionStatus("RIC_UPLOADED");
		nicObj.setTransactionStage("NIC");
		nicObj.setLogCreateTime(new Date());
		nicObj.setWkstnId("NIC_INV_STN1");
		
		try {
			long result = transactionLogDao.save(nicObj);
			log("save result:" + result);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		log(" end  Save");
	}
	
	public void xtestDaoFindById() {
		log("start find by Id");
		
		try {
			NicTransactionLog t = transactionLogDao.findById(Long.valueOf(4));
			log("result t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  find by id");
	}
	
	public void xtestDaoSaveOrUpdate () {
		log("start SaveOrUpdate dao");
		NicTransactionLog nicObj = new NicTransactionLog();
		nicObj.setRefId("1234455");
		nicObj.setTransactionStatus("P");
		nicObj.setLogCreateTime(new Date());
		nicObj.setWkstnId("W11");
//		nicObj.setLogId(5);
		
		try {
			transactionLogDao.saveOrUpdate(nicObj);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		log(" end  SaveOrUpdate dao");
	}
	
	
	//
	public void xtestDaoMerge () {
		log("start Merge dao");
		NicTransactionLog nicObj = new NicTransactionLog();
		nicObj.setRefId("1234455");
		nicObj.setTransactionStatus("S");
		nicObj.setLogCreateTime(new Date());
		nicObj.setWkstnId("W12");
//		nicObj.setLogId(6);
		
		
		try {
			NicTransactionLog t = transactionLogDao.merge(nicObj);
			log("result t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  merge dao");
	}
	
	public void xtestDaoDelete1() {
		log("start delete using obj dao");
		
		try {
			transactionLogDao.delete(Long.valueOf(3));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  delete Long dao");
	}
	
	public void xtestDaoDelete2 () {
		log("start delete using obj id dao");
		NicTransactionLog nicObj = new NicTransactionLog();
//		nicObj.setLogId(4);
		nicObj.setRefId("1234455");
		nicObj.setLogCreateTime(new Date());
		
		try {
			transactionLogDao.delete(nicObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  delete obj dao");
	}
	
	
	public void xtestDaoFindAll() {
		log("start findAll dao");

		try {
			List<NicTransactionLog> list = transactionLogDao.findAll();
			log("list count : "+ ((list==null)?0:list.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}
	
	public static void log(String message) {
		System.out.println(message);
	}

}
