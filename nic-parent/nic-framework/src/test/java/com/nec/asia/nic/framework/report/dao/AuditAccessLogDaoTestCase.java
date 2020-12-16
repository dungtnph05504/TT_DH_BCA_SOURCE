package com.nec.asia.nic.framework.report.dao;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.springframework.util.CollectionUtils;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.audit.domain.AuditAccessLogs;

/**
 * @author chris
 *
 */

/* 
 * Modification History:
 * 
 * 24 Sep 2013 (chris): init
 */
public class AuditAccessLogDaoTestCase extends GenericTestCase<AuditAccessLogs, String, AuditAccessLogDao> {

	@Override
	protected String getBeanName() {
		return "auditAccessLogDao";
	}
	
	@Override
	public void test01Save() {
		logger.info("start AuditAccessLog dao - save");
		try {
			AuditAccessLogs logObj = new AuditAccessLogs();
			logObj.setAuditDate(new Date());
			logObj.setErrorFlag("N");
			logObj.setFunctionName("createUser");
			logObj.setServerId("localhost");
			logObj.setSessionId("JSESSION_ID");
			logObj.setParamValues("userId=chris,userName=chris,password=********");
			logObj.setAccessLogData("");
			logObj.setUserId("ADMIN");
			logObj.setWkstnId("SYSTEM");
			logObj.setTimeTaken(999L);
			bean.save(logObj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		logger.info(" end  AuditAccessLog dao - save");
	}

	@Override
	public void test02Update() {
		logger.info("start AuditAccessLog dao - update");
		try {
			//no applicable
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		logger.info(" end  AuditAccessLog dao - update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start AuditAccessLog dao - find");

		try {
			//AuditAccessLog logObj = bean.findById(codeId);
			//logger.info("AuditAccessLog logObj : "+logObj+"\n"+ReflectionToStringBuilder.toString(logObj, ToStringStyle.MULTI_LINE_STYLE));
			//Assert.assertNotNull("Code Object is not found", logObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  AuditAccessLog dao - find");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start AuditAccessLog dao - findAll");
		try {
			List<AuditAccessLogs> logList = bean.findAll();

			int count = 0;
			if (!CollectionUtils.isEmpty(logList)) {				
				for (AuditAccessLogs logObj : logList) {
					logger.debug("AuditAccessLogId ["+(++count)+"] : "+logObj);
				}
			}
			Assert.assertNotNull("logList is not found", logList);
			Assert.assertNotSame("logList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		logger.info(" end  AuditAccessLog dao - findAll");
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("start AuditAccessLog dao - findAllByExample");
		try {
			AuditAccessLogs d = new AuditAccessLogs();
			d.setUserId("BMSUSER");
			d.setErrorFlag("N");
			List<AuditAccessLogs> logList = bean.findAll(d);

			int count = 0;
			if (!CollectionUtils.isEmpty(logList)) {
				for (AuditAccessLogs logObj : logList) {
					logger.debug("AuditAccessLogId ["+(++count)+"] : "+ReflectionToStringBuilder.toString(logObj, ToStringStyle.SIMPLE_STYLE));
				}
			}
			Assert.assertNotNull("Active logList is not found", logList);
			Assert.assertNotSame("Active logList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		logger.info(" end  AuditAccessLog dao - findAllByExample");		
	}

	@Override
	public void test99Delete() {
		logger.info("start AuditAccessLog dao - delete");
		try {
			//no applicable
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		logger.info(" end  AuditAccessLog dao - delete");	
	}
}
