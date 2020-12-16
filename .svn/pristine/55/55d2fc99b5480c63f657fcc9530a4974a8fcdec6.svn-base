package com.nec.asia.nic.framework.admin.rbac.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;


public class WorkStationServiceTestCase extends GenericTestCase<Workstations, String, WorkStationService> {

	String wkstnId = "NIC_WORKSTATIONS_TESTSVC";
	
	@Override
	protected String getBeanName() {
		return "workstationsService";
	}

	@Override
	public void test01Save() {
		logger.info("start WorkstationsServices - Save");
		Assert.assertNotNull(bean);
		try {
			Workstations wkstnObj = new Workstations();
			wkstnObj.setWkstnId(wkstnId);
			wkstnObj.setWkstnDesc("Test Workstations");
			wkstnObj.setSystemId("NIC");
			wkstnObj.setCreateBy("SYSTEM");
			wkstnObj.setCreateWkstnId("SYSTEM");
			wkstnObj.setCreateDate(new Date());
			String id = bean.save(wkstnObj);
			logger.info("[Workstations] save result: "+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  WorkstationsServices - Save");
	}

	@Override
	public void test02Update() {
		logger.info("start WorkstationsServices - Update");
		Assert.assertNotNull(bean);
		logger.info("skip method.");
		logger.info(" end  WorkstationsServices - Update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start WorkstationsServices - FindById");
		Assert.assertNotNull(bean);
		String wkstnId = "CHRIS-WONGNB";
		Workstations result = bean.findById(wkstnId);
		Assert.assertNotNull(result);
		logger.info(" end  WorkstationsServices - FindById");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start WorkstationsServices - findAll");
		Assert.assertNotNull(bean);
		List<Workstations> resultList = this.bean.findAll();
		int count = 0;
		for (Workstations DBO : resultList) {
			logger.debug("Workstations ["+(++count)+"] : "+"\t"+ReflectionToStringBuilder.toString(DBO, ToStringStyle.SHORT_PREFIX_STYLE));
		}
		logger.info(" end  WorkstationsServices - findAll");		
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("start WorkstationsServices - FindAllByExample");
		Assert.assertNotNull(bean);
		logger.info("skip method.");
		logger.info(" end  WorkstationsServices - FindAllByExample");		
	}
	
	public void test99Delete() {
		logger.info("start WorkstationsServices - delete");
		try {
			Workstations obj = bean.findById(wkstnId);
			if (obj!=null) {
				bean.delete(obj);
			} else {
				logger.warn("Workstations[{}] not exists.", wkstnId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}	
		logger.info(" end  WorkstationsServices - delete");
	}
}
