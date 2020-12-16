package com.nec.asia.nic.framework.admin.rbac.dao;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.springframework.util.CollectionUtils;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;

public class WorkstationDaoTestCase extends GenericTestCase<Workstations, String, WorkstationsDao> {

	@Override
	protected String getBeanName() {
		return "workstationsDao";
	}
	
	String wkstnId = "TEST_NIC_WORKSTATIONS";
	@Override
	public void test01Save() {
		logger.info("start Workstations dao - save");
		try {
			Workstations wkstnObj = new Workstations();
										
			wkstnObj.setWkstnId(wkstnId);
			wkstnObj.setWkstnDesc("NIC Test Workstation");
			wkstnObj.setSystemId("NIC");
			wkstnObj.setCreateBy("SYSTEM");
			wkstnObj.setCreateWkstnId("SYSTEM");
			wkstnObj.setCreateDate(new Date());
			String id = bean.save(wkstnObj);
			logger.info("[Workstations] save result: "+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  Workstations dao - save");
	}

	@Override
	public void test02Update() {
		logger.info("start Workstations dao - update");
		try {
			Workstations wkstnObj = bean.findById(wkstnId);
			Assert.assertNotNull("Code Object is not found", wkstnObj);
			if (wkstnObj!=null) {
				logger.debug("Workstations t : "+wkstnId+"\n"+ReflectionToStringBuilder.toString(wkstnObj, ToStringStyle.MULTI_LINE_STYLE));
				wkstnObj.setWkstnDesc("NIC Test Workstation - Updated");
				wkstnObj.setUpdateBy("SYSTEM");
				wkstnObj.setUpdateDate(new Date());
				wkstnObj.setUpdateWkstnId("SYSTEM");
				wkstnObj.setDeleteBy("");
				wkstnObj.setDeleteWkstnId("");
				wkstnObj.setDeleteDate(null);
				wkstnObj.setDeleteFlag(Boolean.FALSE);
				bean.saveOrUpdate(wkstnObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  Workstations dao - update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start Workstations dao - find");

		try {
			Workstations wkstnObj = bean.findById(wkstnId);
			logger.debug("Workstations : "+wkstnObj+"\n"+ReflectionToStringBuilder.toString(wkstnObj, ToStringStyle.MULTI_LINE_STYLE));
			Assert.assertNotNull("Workstations Object is not found", wkstnObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  Workstations dao - find");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start Workstations dao - findAll");
		try {
			List<Workstations> wkstnList = bean.findAll();

			int count = 0;
			if (!CollectionUtils.isEmpty(wkstnList)) {				
				for (Workstations wkstnObj : wkstnList) {
					logger.debug("Workstations ["+(++count)+"] : "+wkstnId+"\t"+ReflectionToStringBuilder.toString(wkstnObj, ToStringStyle.SHORT_PREFIX_STYLE));
				}
			}
			Assert.assertNotNull("wkstnList is not found", wkstnList);
			Assert.assertNotSame("wkstnList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  Codes dao - findAll");
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("\n\nstart Workstations dao - findAllByExample");
		try {
			Workstations d = new Workstations();
			d.setDeleteFlag(Boolean.FALSE);
			List<Workstations> wkstnList = bean.findAll(d);

			int count = 0;
			if (!CollectionUtils.isEmpty(wkstnList)) {
				for (Workstations wkstnObj : wkstnList) {
					logger.debug("Workstations ["+(++count)+"] : "+wkstnObj.getWkstnId()+"\t"+ReflectionToStringBuilder.toString(wkstnObj, ToStringStyle.SIMPLE_STYLE));
				}
			}
			Assert.assertNotNull("Active wkstnList is not found", wkstnList);
			Assert.assertNotSame("Active wkstnList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  Workstations dao - findAllByExample");		
	}
	
	public void test99Delete() {
		logger.info("start Workstations dao - delete");
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
		logger.info(" end  Workstations dao - delete");
	}
	/* test customized method */
	
	
}
