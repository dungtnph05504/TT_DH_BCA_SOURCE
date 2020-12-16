package com.nec.asia.nic.framework.admin.rbac.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import org.junit.Assert;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;

public class FunctionsServiceTestCase extends GenericTestCase<Functions, String, FunctionService> {
	String functionId = "NIC_CHANGE_PWD_TESTSVC";
	
	@Override
	protected String getBeanName() {
		return "functionService";
	}

	@Override
	public void test01Save() {
		logger.info("start FunctionService - Save");
		try {
			//NIC_CHANGE_PWD	Change Password	MENU	/changePasswordController	NIC	SYSTEM	SYSTEM	20/10/13							N
			Functions functionObj = new Functions();
			functionObj.setFunctionId(functionId);
			functionObj.setFunctionDesc("Change Password");
			functionObj.setFunctionCategory("MENU");
			functionObj.setFunctionUrl("/changePasswordController");
			functionObj.setSystemId("NIC");
			functionObj.setCreateBy("SYSTEM");
			functionObj.setCreateWkstnId("SYSTEM");
			functionObj.setCreateDate(new Date());
			functionObj.setDeleteFlag(Boolean.FALSE);
			String id = bean.save(functionObj);
			logger.info("[Functions] save result: "+id);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Save Error");
		}
		logger.info(" end  FunctionService - Save");
	}

	@Override
	public void test02Update() {
		logger.info("start FunctionService - Update");
		try {
			logger.info("skip method.");
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Update Error");
		}
		logger.info(" end  FunctionService - Update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start FunctionService - FindById");
		try {
			Functions result = bean.findById(functionId);
			Assert.assertNotNull("Result should not be null", result);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Find By Id Error");
		}
		logger.info(" end  FunctionService - FindById");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start FunctionService - findAll");
		try {
			List<Functions> resultList = this.bean.findAll();
			int count = 0;
			for (Functions DBO : resultList) {
				count++;
				logger.info("Functions ["+(count)+"] : "+"\t"+ReflectionToStringBuilder.toString(DBO, ToStringStyle.SHORT_PREFIX_STYLE));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Find All Error");
		}
		
		logger.info(" end  FunctionService - findAll");		
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("start FunctionService - FindAllByExample");
		try {
			logger.info("skip method.");
//			List<Functions> resultList = this.bean.findAll();
//			int count = 0;
//			for (Functions DBO : resultList) {
//				count++;
//				logger.info("Functions ["+(count)+"] : "+"\t"+ReflectionToStringBuilder.toString(DBO, ToStringStyle.SHORT_PREFIX_STYLE));
//			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Find All By Example Error");
		}
		logger.info(" end  FunctionService - FindAllByExample");		
	}
	
	public void test99Delete() {
		logger.info("start FunctionService - delete");
		try {
			Functions functionObj = bean.findById(functionId);
			if (functionObj!=null) {
				bean.delete(functionObj);
			} else {
				logger.warn("Functions[{}] not exists.", functionId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}	
		logger.info(" end  FunctionService - delete");
	}
}
