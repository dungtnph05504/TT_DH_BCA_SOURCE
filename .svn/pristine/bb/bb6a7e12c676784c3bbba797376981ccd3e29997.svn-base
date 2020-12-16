package com.nec.asia.nic.framework.admin.code.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.code.domain.Codes;


public class CodesServiceTestCase extends GenericTestCase<Codes, String, CodesService> {

	String codeId = "TEST_SITE_CODE";
	
	@Override
	protected String getBeanName() {
		return "codesService";
	}

	@Override
	public void test01Save() {
		logger.info("start CodesService - Save");
		Assert.assertNotNull(bean);
		try {
			Codes codeObj = new Codes(codeId, "The list of office and office code for testing", Boolean.TRUE, Boolean.TRUE, "NIC", "SYSTEM", "SYSTEM", new Date(), null, null, null, null, null, null, Boolean.FALSE, null);
			bean.save(codeObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in Save: "+e.getMessage());
			Assert.fail();
		}
		logger.info(" end  CodesService - Save");
	}

	@Override
	public void test02Update() {
		logger.info("start CodesService - Update");
		Assert.assertNotNull(bean);
		logger.info(" end  CodesService - Update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start CodesService - FindById");
		Assert.assertNotNull(bean);
		String codeId = "SITE_CODE";
		Codes result = bean.findById(codeId);
		Assert.assertNotNull(result);
		logger.info(" end  CodesService - FindById");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start CodesService - findAll");
		Assert.assertNotNull(bean);
		List<Codes> resultList = this.bean.findAll();
		int count = 0;
		for (Codes DBO : resultList) {
			logger.debug("Codes ["+(++count)+"] : "+ReflectionToStringBuilder.toString(DBO, ToStringStyle.SHORT_PREFIX_STYLE));
		}
		logger.info(" end  CodesService - findAll");		
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("start CodesService - FindAllByExample");
		Assert.assertNotNull(bean);
		logger.info(" end  CodesService - FindAllByExample");		
	}

	@Test
	public void test99Delete() {
		logger.info("start CodesService - delete");
		try {
			Codes codeObj = bean.findById(codeId);
			if (codeObj!=null) {
				bean.delete(codeObj);
			} else {
				logger.warn("Codes[{}] not exists.", codeId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		logger.info(" end  CodesService - delete");
	}
}
