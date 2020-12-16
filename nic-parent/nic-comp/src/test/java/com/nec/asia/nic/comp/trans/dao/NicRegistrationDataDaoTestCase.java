package com.nec.asia.nic.comp.trans.dao;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;


public class NicRegistrationDataDaoTestCase extends TestCase{
	public static ApplicationContext context = null;
	public static  NicRegistrationDataDao  registrationDataDao = null;
	
	public static final long LONG_SAMPLE = 123456789;
	public static final String STRING_SAMPLE = "RICHQ-2013-001837";
	public static final String STRING_SAMPLE2 = "123456789abc";
	
	
	
	public NicRegistrationDataDaoTestCase() {
		init();
	}
	
	
	
	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			registrationDataDao = context.getBean("registrationDataDao", NicRegistrationDataDao.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
//	
//	public void testDaoSave() {
//		log("start Save dao");
//		NicRegistrationData nicRegDataObj = new NicRegistrationData();
//		
//		Calendar cal = Calendar.getInstance();
//		cal.set(1981, Calendar.DECEMBER, 01);
//		
//		NicTransaction transObj = new NicTransaction();
//		transObj.setTransactionId("RICHQ-2013-100002");
//		transObj.setApplnRefNo(transObj.getTransactionId());
//		transObj.setDateOfApplication(new Date());
//		transObj.setQueueNumber("R0001");
//		transObj.setTransactionType("REG");
//		transObj.setTransactionSubtype("NEW_CITIZEN");
//		transObj.setTransactionStatus("NEW");
//		transObj.setNin("R780101015053Q");
//		
//		//nicRegDataObj.setNicId(Long.valueOf(2));
//		nicRegDataObj.setTransactionId("RICHQ-2013-100001");
//		//nicRegDataObj.setNicUploadJobId(Long.valueOf(3));
//		nicRegDataObj.setAddressLine1("Flat No");
//		nicRegDataObj.setAddressLine2("Street No");
//		nicRegDataObj.setAddressLine3("Locality");
//		nicRegDataObj.setAddressLine4("Town");
//		nicRegDataObj.setAddressLine5("MU-PL");
//		nicRegDataObj.setAddressLine6("00000");
//		nicRegDataObj.setPreferredMailingAddress("L");
//		nicRegDataObj.setOverseasAddress("Cargados Carajos Shoals [Saint Brandon Islands]");
//		nicRegDataObj.setOverseasCountry("MU");
//		nicRegDataObj.setDateOfBirth(cal.getTime());
//		nicRegDataObj.setFirstnameFull("MARCUS");
//		nicRegDataObj.setSurnameFull("KRAMER");
//		nicRegDataObj.setSurnameBirthFull("KRAMER");
//		nicRegDataObj.setCreateBy("JPRM");
//		nicRegDataObj.setCreateDate(new Date());
//		nicRegDataObj.setRegCompleteFlag(true);
//		nicRegDataObj.setNicTransaction(transObj);
//		nicRegDataObj.setEstCollectionDate(new Date());
//		
//		try {
//			String result = registrationDataDao.save(nicRegDataObj);
//			log("save result:" + result);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		log(" end  Save");
//	}
//	
//	public void testDaoFindById() { 
//		log("start find by Id");
//		
//		try {
//			NicRegistrationData t = registrationDataDao.findById("RICHQ-2013-001069");
//			log("result t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log(" end  find by id");
//	}
//	
//	public void xtestDaoSaveOrUpdate () {
//		log("start SaveOrUpdate dao");
//		NicRegistrationData nicRegDataObj = registrationDataDao.findById("RICHQ-2013-001122");//"RICHQ-2013-000002");
//		
//		Calendar cal = Calendar.getInstance();
//		cal.set(1981, Calendar.DECEMBER, 01);
//			
//		nicRegDataObj.setNicId(Long.valueOf(2));
//		nicRegDataObj.setNicUploadJobId(Long.valueOf(3));
//		nicRegDataObj.setAddressLine1(STRING_SAMPLE2);
//		nicRegDataObj.setDateOfBirth(cal.getTime());
//		nicRegDataObj.setFirstnameFull("MARKUS");
//		nicRegDataObj.setSurnameFull("KRAMER");
//		nicRegDataObj.setSurnameBirthFull("KRAMER");
//		nicRegDataObj.setCreateBy("JPRM2");
//		nicRegDataObj.setCreateDate(new Date());
//		nicRegDataObj.setRegCompleteFlag(true);
//		nicRegDataObj.setFpVerifyFlag(Boolean.TRUE);
//		nicRegDataObj.setCardVerifyFlag(Boolean.TRUE);
//		nicRegDataObj.setFpVerifyScore("01-9999,06-9999,02-9999,07-9999");
//		nicRegDataObj.setEmail("fairprice@xxx.com");
//		nicRegDataObj.setContactNo("7654321");
//		nicRegDataObj.setExpressFlag(Boolean.FALSE);		
//		
//		try {
//			registrationDataDao.saveOrUpdate(nicRegDataObj);
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		log(" end  SaveOrUpdate dao");
//	}
//	
//	public void xtestDaoMerge () {
//		log("start Merge dao");
//		NicRegistrationData nicObj = new NicRegistrationData();
//		Calendar cal = Calendar.getInstance();
//		cal.set(1981, Calendar.DECEMBER, 01);
//		
//		NicTransaction transObj = new NicTransaction();
//		transObj.setTransactionId("RICHQ-2013-000001");
//		transObj.setApplnRefNo(transObj.getTransactionId());
//		transObj.setDateOfApplication(new Date());
//		transObj.setQueueNumber("R0002");
//		transObj.setTransactionType("REG");
//		transObj.setTransactionSubtype("NEW_CITIZEN");
//		transObj.setTransactionStatus("NEW");
//		transObj.setNin("T121299012345N");
//		
//		nicObj.setNicId(Long.valueOf(2));
//		nicObj.setTransactionId(STRING_SAMPLE);
//		nicObj.setNicUploadJobId(Long.valueOf(3));
//		nicObj.setAddressLine1(STRING_SAMPLE2);
//		nicObj.setDateOfBirth(cal.getTime());
//		nicObj.setFirstnameFull("MARKUS");
//		nicObj.setSurnameFull("KRAMER");
//		nicObj.setSurnameBirthFull("KRAMER");
//		nicObj.setCreateBy("JPRM2");
//		nicObj.setCreateDate(new Date());
//		nicObj.setRegCompleteFlag(true);
//		nicObj.setNicTransaction(transObj);
//		
//		
//		try {
//			NicRegistrationData t = registrationDataDao.merge(nicObj);
//			log("result t : "+t+"\n"+ReflectionToStringBuilder.toString(t, ToStringStyle.MULTI_LINE_STYLE));
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
//			registrationDataDao.delete("RICHQ-2013-000001");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log(" end  delete String dao");
//	}
//	
//	public void xtestDaoDelete2 () {
//		log("start delete using obj id dao");
//		NicRegistrationData nicObj = new NicRegistrationData();
//		nicObj.setNicId(Long.valueOf(123456789));
//		
//		try {
//			registrationDataDao.delete(nicObj);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log(" end  delete obj dao");
//	}
//	
	
	public void xtestDaoFindAll() {
		log("start findAll dao");

		try {
			List<NicRegistrationData> list = registrationDataDao.findAll();
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
