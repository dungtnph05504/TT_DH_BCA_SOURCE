package com.nec.asia.nic.comp.trans.dao;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.domain.NicAfisData;

public class NicAfisDataDaoTestCase extends TestCase {
	public static ApplicationContext context = null;
	public static NicAfisDataDao nicAfisDataDao = null;

	public NicAfisDataDaoTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			nicAfisDataDao = context.getBean("nicAfisDataDao",
					NicAfisDataDao.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	public void xtestDaoSave() {
		log("start Save dao");
		log(" end  Save");
	}

	public void xtestDaoFindById() {
		log("start find by Id");
		log(" end  find by id");
	}

	public void xtestDaoFindByNicId() {
		log("start findByNicId");
		log(" end  findByNicId");
	}

	public void xtestDaoSaveOrUpdate() {
		log("start SaveOrUpdate dao");
		log(" end  SaveOrUpdate dao");
	}

	public void xtestDaoMerge() {
		log("start Merge dao");
		log(" end  merge dao");
	}

	public void xtestDaoFindAll() {
		log("start findAll dao");

		try {
			List<NicAfisData> list = nicAfisDataDao.findAll();
			log("list count : " + ((list == null) ? 0 : list.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}
	
//	public void testFindReferenceAfisId() {
//		log("start findReferenceAfisId");
//		try {
//			String transactionId = "CCCG-2013-003283";
//			String afisRefId = nicAfisDataDao.findReferenceAfisId(transactionId);
//			log("afisRefId : " + afisRefId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log(" end  findReferenceAfisId");
//	}
	
	public void xtestGetNextAfisKeyNo() {
		log("start getNextAfisKeyNo");
		try {
			String prefix = "1";
			String afisKeyNo = nicAfisDataDao.getNextAfisKeyNo(prefix);
			log("afisKeyNo : " + afisKeyNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  getNextAfisKeyNo");
	}
	
	public void testFindHistory() {
		log("start findOtherAfisHistoryByTransIdList");
		try {
			List<String> transIdList = Arrays.asList(new String[] {"0042016032800001"});
			List<String> relatedTransIdList = nicAfisDataDao.findOtherAfisHistoryByTransIdList(transIdList);
			log("relatedTransIdList : " + relatedTransIdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findOtherAfisHistoryByTransIdList");
	}

	public static void log(String message) {
		System.out.println(message);
	}
}
