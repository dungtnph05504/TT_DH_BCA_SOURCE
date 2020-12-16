package com.nec.asia.nic.framework.admin.rbac.dao;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;

public class FunctionsDaoTestCase extends GenericTestCase<Functions, String, FunctionsDao> {

	@Override
	protected String getBeanName() {
		return "functionDao";
	}
	
	String functionId = "TEST_NIC_INV_NEW";
	
	@Override
	public void test01Save() {
		logger.info("start Functions dao - save");
		try {
			Functions functionObj = bean.findById(functionId);
			if (functionObj==null) {
				functionObj = new Functions();
				//NIC_INV_NEW	New Investigation button	BUTTON	/investigation/newInvestigation	NIC	SYSTEM	SYSTEM	07/08/13							
				functionObj.setFunctionId(functionId);
				functionObj.setFunctionDesc("New Investigation");
				functionObj.setFunctionCategory("BUTTON");
				functionObj.setFunctionUrl("/investigation/newInvestigation");
				functionObj.setSystemId("NIC");
				functionObj.setCreateBy("SYSTEM");
				functionObj.setCreateWkstnId("SYSTEM");
				functionObj.setCreateDate(new Date());
				String id = bean.save(functionObj);
				logger.info("[Functions] save result: "+id);
			} else {
				logger.info("[Functions] skip save operation for : "+functionId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Save Error");
		}
		logger.info(" end  Functions dao - save");
	}

	@Override
	public void test02Update() {
		logger.info("start Functions dao - update");
		try {
			Functions functionObj = bean.findById(functionId);
			Assert.assertNotNull("Functions Object is not found", functionObj);
			if (functionObj!=null) {
				logger.debug("Functions t : "+functionId+"\t"+ReflectionToStringBuilder.toString(functionObj, ToStringStyle.SIMPLE_STYLE)); //MULTI_LINE_STYLE
				functionObj.setUpdateBy("SYSTEM");
				functionObj.setUpdateDate(new Date());
				functionObj.setUpdateWkstnId("SYSTEM");
				functionObj.setDeleteBy("");
				functionObj.setDeleteWkstnId("");
				functionObj.setDeleteDate(null);
				functionObj.setDeleteFlag(Boolean.FALSE);
				//bean.saveOrUpdate(functionObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Update Error");
		}
		logger.info(" end  Functions dao - update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start Functions dao - find");

		try {
			Functions functionObj = bean.findById(functionId);
			logger.debug("Functions codeObj : "+functionId+"\t"+ReflectionToStringBuilder.toString(functionObj, ToStringStyle.SIMPLE_STYLE));
			Assert.assertNotNull("Functions Object should not be found", functionObj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Find By Id Error");
		}
		logger.info(" end  Functions dao - find");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start Functions dao - findAll");
		try {
			List<Functions> functionList = bean.findAll();

			int count = 0;
			if (!CollectionUtils.isEmpty(functionList)) {				
				for (Functions functionObj : functionList) {
					count++;
					logger.debug("Functions ["+(++count)+"] : "+functionObj+"\n"+ReflectionToStringBuilder.toString(functionObj, ToStringStyle.SHORT_PREFIX_STYLE));
				}
			}
			Assert.assertNotNull("functionList is not found", functionList);
			Assert.assertNotSame("functionList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Find All Error");
		}
		logger.info(" end  Codes dao - findAll");
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("\n\nstart Functions dao - findAllByExample");
		try {
			Functions d = new Functions();
			d.setDeleteFlag(Boolean.FALSE);
			List<Functions> functionList = bean.findAll(d);

			int count = 0;
			if (!CollectionUtils.isEmpty(functionList)) {
				for (Functions functionObj : functionList) {
					count++;
					logger.debug("Functions ["+(count)+"] : "+functionObj+"\n"+ReflectionToStringBuilder.toString(functionObj, ToStringStyle.SIMPLE_STYLE));
				}
			}
			Assert.assertNotNull("Active functionList is not found", functionList);
			Assert.assertNotSame("Active functionList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Find All By Example Error");
		}
		logger.info(" end  Functions dao - findAllByExample");		
	}

	public void test99Delete() {
		logger.info("start Functions dao - delete");
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
		logger.info(" end  Functions dao - delete");
	}
	/* test customized method */
	
	
}
