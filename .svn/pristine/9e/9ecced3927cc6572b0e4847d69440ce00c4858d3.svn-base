package com.nec.asia.nic.comp.trans.dao;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.dao.NicTransactionPaymentDao;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;


public class NicTransactionPaymentDaoTestCase extends TestCase {
	public static ApplicationContext context = null;
	public static  NicTransactionPaymentDao  nicTransactionPaymentDao = null;
	public NicTransactionPaymentDaoTestCase() {
		init();
	}
	
	
	
	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			nicTransactionPaymentDao = context.getBean("transactionPaymentDao", NicTransactionPaymentDao.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	
	public void xtestDaoSave() {
		log("start Save dao");
		NicTransactionPayment nicObj = new NicTransactionPayment();
		
		NicTransaction transObj = new NicTransaction();
		transObj.setTransactionId("RICHQ-2013-000001");
		
		nicObj.setNicTransaction(transObj);
		nicObj.setCreateBy("SYSTEM");
		nicObj.setCreateDatetime(new Date());
		nicObj.setCreateWkstnId("SYSTEM");
		nicObj.setPaymentAmount(Double.valueOf(200));
		nicObj.setFeeAmount(Double.valueOf(144));
		nicObj.setReduceRateFlag(false);
		nicObj.setWaiverFlag(false);
		nicObj.setPaymentId("REG");
		
		
		
		try {
			String result = nicTransactionPaymentDao.save(nicObj);
			log("save result:" + result);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		log(" end  Save");
	}
	
	public void testDaoFindById() {
		log("start find by Id");
		
		try {
			NicTransactionPayment t = nicTransactionPaymentDao.findById("RICHQ-2017-000223");
			log(t.getTransactionId());
			log("result t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  find by id");
	}
	
	
	//only saves, error when transaction id exists already in trans payment table
	public void xtestDaoSaveOrUpdate () {
		log("start SaveOrUpdate dao");
		
		NicTransactionPayment nicObj = new NicTransactionPayment();
		NicTransaction transObj = new NicTransaction();
		transObj.setTransactionId("RICHQ-2013-000002");
		
		nicObj.setNicTransaction(transObj);
		nicObj.setCreateBy("SYSTEM");
		nicObj.setCreateDatetime(new Date());
		nicObj.setCreateWkstnId("SYSTEM");
		nicObj.setPaymentAmount(Double.valueOf(400.75));
		nicObj.setFeeAmount(Double.valueOf(144.50));
		nicObj.setReduceRateFlag(false);
		nicObj.setWaiverFlag(false);
		nicObj.setPaymentId("REG");
		nicObj.setTransactionId("RICHQ-2013-000002");
		
		try {
			nicTransactionPaymentDao.saveOrUpdate(nicObj);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		log(" end  SaveOrUpdate dao");
	}
	
	//ok
	public void xtestDaoMerge () {
		log("start Merge dao");
		NicTransactionPayment nicObj = new NicTransactionPayment();
		
		NicTransaction transObj = new NicTransaction();
		transObj.setTransactionId("RICHQ-2013-000002");
		
		nicObj.setNicTransaction(transObj);
		nicObj.setCreateBy("SYSTEM");
		nicObj.setCreateDatetime(new Date());
		nicObj.setCreateWkstnId("SYSTEM");
		nicObj.setPaymentAmount(Double.valueOf(200.55));
		nicObj.setFeeAmount(Double.valueOf(144.50));
		nicObj.setReduceRateFlag(false);
		nicObj.setWaiverFlag(false);
		nicObj.setPaymentId("REG2");
		nicObj.setBalance(Double.valueOf(35.60));
		nicObj.setCollectionOfficerId("OFFICER12");
		
		nicObj.setUpdateBy("SYSTEM");
		nicObj.setUpdateDatetime(new Date());
		nicObj.setUpdateWkstnId("SYSTEM");
		nicObj.setTransactionId("RICHQ-2013-000002");
		
		
		try {
			NicTransactionPayment t = nicTransactionPaymentDao.merge(nicObj);
			log("result:" + t.getTransactionId());
			//log("result t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  merge dao");
	}
	
	public void xtestDaoDelete1() {
		log("start delete using string dao");
	
		
		try {
			nicTransactionPaymentDao.delete("RICHQ-2013-000001");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  delete String dao");
	}
	
	public void xtestDaoDelete2 () {
		log("start delete using obj id dao");
		NicTransactionPayment nicObj = new NicTransactionPayment();

		nicObj.setTransactionId("RICHQ-2013-000001");
		nicObj.setPaymentId("REG");
		nicObj.setFeeAmount(Double.valueOf(144));
		try {
			nicTransactionPaymentDao.delete(nicObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  delete obj dao");
	}
	
	
	public void xtestDaoFindAll() {
		log("start findAll dao");

		try {
			List<NicTransactionPayment> list = nicTransactionPaymentDao.findAll();
			log("list count : "+ ((list==null)?0:list.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}
	
	public void testGetTransactionPayment() {
		log("start findAll dao");

		try {
			NicTransaction transObj = new NicTransaction();
			transObj.setTransactionId("RICHQ-2017-000223");
			NicTransaction dbo = nicTransactionPaymentDao.getTransactionPayment(transObj);
			log("result t : "+dbo+"\n"+ReflectionToStringBuilder.toString(dbo, ToStringStyle.MULTI_LINE_STYLE));
			log("result p : "+dbo.getNicTransactionPayment()+"\n"+ReflectionToStringBuilder.toString(dbo.getNicTransactionPayment(), ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
}
