package com.nec.asia.nic.comp.trans.dao;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.criterion.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.trans.dao.NicIssuanceDataDao;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceDataId;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.utils.DateUtil;

public class NicIssuanceDataDaoTestCase extends TestCase {
	public static ApplicationContext context = null;
	public static NicIssuanceDataDao nicIssuanceDataDao = null;

	public NicIssuanceDataDaoTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			nicIssuanceDataDao = context.getBean("issuanceDataDao",
					NicIssuanceDataDao.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	public void xtestDaoSave() {
		log("start Save dao");
		NicIssuanceData nicIssDataObj = new NicIssuanceData();
		NicIssuanceDataId idObj = new NicIssuanceDataId();
		idObj.setPersoRefId("S201306201212");
		idObj.setTransactionId("RICHQ-2013-000001");

		NicTransaction transObj = new NicTransaction();
		transObj.setTransactionId("RICHQ-2013-000001");
		transObj.setApplnRefNo(transObj.getTransactionId());
		transObj.setDateOfApplication(new Date());
//		transObj.setQueueNumber("R0001");
		transObj.setTransactionType("REG");
//		transObj.setTransactionSubtype("NEW_CITIZEN");
		transObj.setTransactionStatus("NEW");
//		transObj.setNin("T121299012345N");

		nicIssDataObj.setNicTransaction(transObj);
		nicIssDataObj.setCardStatus("NEW");
		nicIssDataObj.setCcn("123459999");
		nicIssDataObj.setId(idObj);
		nicIssDataObj.setDateOfIssue(new Date());

		try {
			NicIssuanceDataId result = nicIssuanceDataDao.save(nicIssDataObj);
			log("save result:" + result);

		} catch (Exception e) {
			e.printStackTrace();

		}
		log(" end  Save");
	}

	public void xtestDaoFindById() { //
		log("start find by Id");
		NicIssuanceDataId nicObj = new NicIssuanceDataId();
		nicObj.setTransactionId("RICHQ-2013-001711");
		nicObj.setPersoRefId("1");

		try {
			NicIssuanceData t = nicIssuanceDataDao.findById(nicObj);
			if (t != null) {
				log("result transaction id : " + t.getId().getTransactionId());
				log("result issuance data DBO: "
						+ ReflectionToStringBuilder.toString(t,
								ToStringStyle.MULTI_LINE_STYLE));
			} else {
				log("no result!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  find by id");
	}

	public void xtestFindByPeriod() {
		log("start find by period");
		try {
			List<NicIssuanceData> resultList = nicIssuanceDataDao.findByPeriod(
					"RICHQ", null, null);
			if (resultList != null) {
				for (NicIssuanceData t : resultList) {
					log("result transaction id : "
							+ t.getId().getTransactionId());
					log("result issuance data DBO: "
							+ ReflectionToStringBuilder.toString(t,
									ToStringStyle.MULTI_LINE_STYLE));
				}
			} else {
				log("no result!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  find by id");
	}

	public void xtestDaoSaveOrUpdate() {
		log("start SaveOrUpdate dao");
		NicIssuanceData nicObj = new NicIssuanceData();
		NicIssuanceDataId idObj = new NicIssuanceDataId();
		idObj.setPersoRefId("S2345678");
		idObj.setTransactionId("RICHQ-2013-000001");

		NicTransaction transObj = new NicTransaction();
		transObj.setTransactionId("RICHQ-2013-000001");
		transObj.setApplnRefNo(transObj.getTransactionId());
		transObj.setDateOfApplication(new Date());
//		transObj.setQueueNumber("R0001");
		transObj.setTransactionType("REG");
//		transObj.setTransactionSubtype("NEW_CITIZEN X");
		transObj.setTransactionStatus("NEW");
//		transObj.setNin("T121299012345N");

		nicObj.setNicTransaction(transObj);
		nicObj.setCardStatus("NEW");
		nicObj.setCcn("123456788");
		nicObj.setId(idObj);

		try {
			nicIssuanceDataDao.saveOrUpdate(nicObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  SaveOrUpdate dao");
	}

	public void xtestDaoMerge() {
		log("start Merge dao");
		NicIssuanceData nicObj = new NicIssuanceData();
		NicIssuanceDataId idObj = new NicIssuanceDataId();
		idObj.setPersoRefId("S2345678");
		idObj.setTransactionId("RICHQ-2013-000001");

		NicTransaction transObj = new NicTransaction();
		transObj.setTransactionId("RICHQ-2013-000001");
		transObj.setApplnRefNo(transObj.getTransactionId());
		transObj.setDateOfApplication(new Date());
//		transObj.setQueueNumber("R0001");
		transObj.setTransactionType("REG");
//		transObj.setTransactionSubtype("NEW_CITIZEN X");
		transObj.setTransactionStatus("NEW");
//		transObj.setNin("T121299012345N");

		nicObj.setNicTransaction(transObj);
		nicObj.setCardStatus("NEW");
		nicObj.setCcn("123456788");
		nicObj.setId(idObj);

		try {
			NicIssuanceData t = nicIssuanceDataDao.merge(nicObj);
			log("result t : "
					+ t
					+ "\n"
					+ ReflectionToStringBuilder.toString(t,
							ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  merge dao");
	}

	public void xtestDaoDelete1() {
		log("start delete using obj dao");
		NicIssuanceData nicObj = new NicIssuanceData();
		NicIssuanceDataId idObj = new NicIssuanceDataId();
		idObj.setPersoRefId("S2345678");
		idObj.setTransactionId("RICHQ-2013-000001");
		nicObj.setId(idObj);

		try {
			nicIssuanceDataDao.delete(nicObj);

		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  delete String dao");
	}

	public void xtestDaoDelete2() {
		log("start delete using obj id dao");
		NicIssuanceDataId nicObj = new NicIssuanceDataId();
		nicObj.setTransactionId("RICHQ-2013-000001");
		nicObj.setPersoRefId("S2345678");

		try {
			nicIssuanceDataDao.delete(nicObj);

		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  delete obj dao");
	}

	public void xtestDaoFindAll() {
		log("start findAll dao");

		try {
			List<NicIssuanceData> list = nicIssuanceDataDao.findAll();
			log("list count : " + ((list == null) ? 0 : list.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}

	public void xtestDaoFindByExample() {
		log("start findAll dao");

		NicIssuanceData nicObj = new NicIssuanceData();
		nicObj.setCcn("123456789");

		try {
			List<NicIssuanceData> list = nicIssuanceDataDao.findAll(nicObj);
			log("list count : " + ((list == null) ? 0 : list.size()));

			NicIssuanceData x = list.get(0);
			x.setNicTransaction(new NicTransaction());

			log("result t : "
					+ x
					+ "\n"
					+ ReflectionToStringBuilder.toString(x,
							ToStringStyle.MULTI_LINE_STYLE));
			log("result id: "
					+ x.getId()
					+ "\n"
					+ ReflectionToStringBuilder.toString(x.getId(),
							ToStringStyle.MULTI_LINE_STYLE));

		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAll dao");
	}

	public void xtestDaoFindByPackageId() { //
		log("start findByPackageId");
		String packageId = "PKG.RICHQ.1310230001";
		try {
			log("packageId:" + packageId);
			List<NicIssuanceData> t = nicIssuanceDataDao
					.findByPackageId(packageId);
			if (t != null && t.size() > 0) {
				log("result.size:" + t.size());
				// log("result issuance data DBO: "+ReflectionToStringBuilder.toString(t,
				// ToStringStyle.MULTI_LINE_STYLE));
			} else {
				log("no result!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findByPackageId");
	}

	public void xtestDaoFindByPersoRefId() throws DaoException { //
		log("start findByPersoRefId");
		String persoRefId = "84";
		log("persoRefId:" + persoRefId);
		List<NicIssuanceData> t = nicIssuanceDataDao
				.findByPersoRefId(persoRefId);
		if (t != null && t.size() > 0) {
			log("result.size:" + t.size());
			// log("result issuance data DBO: "+ReflectionToStringBuilder.toString(t,
			// ToStringStyle.MULTI_LINE_STYLE));
		} else {
			log("no result!!");
		}
		log(" end  findByPersoRefId");
	}

	public void xtestDaoFindByNin() throws DaoException {
		log("start findByNin");
		String nin = "R780101015072B";
		log("nin:" + nin);
		List<NicIssuanceData> t = nicIssuanceDataDao.findByNin(nin);
		if (t != null && t.size() > 0) {
			log("result.size:" + t.size());
			// log("result issuance data DBO: "+ReflectionToStringBuilder.toString(t,
			// ToStringStyle.MULTI_LINE_STYLE));
		} else {
			log("no result!!");
		}
		log(" end  findByNin");
	}
	
	public void testFindAllForPagination() {
		log("start findAllForPagination");
		try {
			String nin = "R210366380826D";//R210366380826D
			String ccn = "";//94735329
			String packageId = "";//PKG.RICHQ.140224X001
			String cardStatus = "";//ACTIVE
			String regSiteCode = "";
			String issSiteCode = "";//CCCG
			Date regDatefrom = DateUtil.strToDate("", "dd-MMM-yyyy");
			Date regDateto = DateUtil.strToDate("21-Mar-2014", "dd-MMM-yyyy");
			Date collectDatefrom = DateUtil.strToDate("", "dd-MMM-yyyy");
			Date collectDateto = DateUtil.strToDate("21-Mar-2014", "dd-MMM-yyyy");//21-Mar-2014
			
			NicIssuanceData issuanceData = new NicIssuanceData();
			issuanceData.setNicTransaction(new NicTransaction());
			if (StringUtils.isNotBlank(nin)) 
				issuanceData.setNin(nin);
			if (StringUtils.isNotBlank(ccn)) 
				issuanceData.setCcn(ccn);
			if (StringUtils.isNotBlank(packageId)) 
				issuanceData.setPackageId(packageId);
			if (StringUtils.isNotBlank(cardStatus)) 
				issuanceData.setCardStatus(cardStatus);
			if (StringUtils.isNotBlank(regSiteCode)) 
				issuanceData.getNicTransaction().setRegSiteCode(regSiteCode);
			if (StringUtils.isNotBlank(issSiteCode)) 
				issuanceData.getNicTransaction().setIssSiteCode(issSiteCode);
			
			int pageNo = 1;
			int pageSize = 15;
			boolean ignoreCase = false;
			boolean enableLike = false;
			Order order = Order.asc("ccn");;
			PaginatedResult<NicIssuanceData> pageResults = nicIssuanceDataDao.findAllForPagination(
					issuanceData, regDatefrom, regDateto, collectDatefrom, collectDateto, pageNo, pageSize, ignoreCase, enableLike, order);
			if (pageResults != null) {
				for (NicIssuanceData t : pageResults.getRows()) {
					log("result transaction id : " + t.getId().getTransactionId());
					log("result issuance data DBO: " + ReflectionToStringBuilder.toString(t,	ToStringStyle.MULTI_LINE_STYLE));
				}
			} else {
				log("no result!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  findAllForPagination");
	}

	public static void log(String message) {
		System.out.println(message);
	}
}
