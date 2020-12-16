package com.nec.asia.nic.framework.admin.rbac.dao;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.springframework.util.CollectionUtils;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;

public class RolesDaoTestCase extends GenericTestCase<Roles, String, RolesDao> {

	String roleId = "TEST_NIC_USER";
	
	@Override
	protected String getBeanName() {
		return "rolesDao";
	}
	
	@Override
	public void test01Save() {
		logger.info("start Roles dao - save");
		try {
			Roles roleObj = new Roles();						
			roleObj.setRoleId(roleId);
			roleObj.setRoleDesc("NIC Test Officer");
			roleObj.setSystemId("NIC");
			roleObj.setCreateBy("SYSTEM");
			roleObj.setCreateWkstnId("SYSTEM");
			roleObj.setCreateDate(new Date());
			roleObj.setDeleteFlag(Boolean.FALSE);
			String id = bean.save(roleObj);
			logger.info("[Roles] save result: "+id);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Roles dao - save");
	}

	@Override
	public void test02Update() {
		logger.info("start Roles dao - update");
		try {
			Roles roleObj = bean.findById(roleId);
			Assert.assertNotNull("Code Object is not found", roleObj);
			if (roleObj!=null) {
				logger.debug("Roles t : "+roleObj+"\t"+ReflectionToStringBuilder.toString(roleObj, ToStringStyle.MULTI_LINE_STYLE));
				roleObj.setRoleDesc("NIC Test Officer - Updated");
				roleObj.setUpdateBy("SYSTEM");
				roleObj.setUpdateDate(new Date());
				roleObj.setUpdateWkstnId("SYSTEM");
				roleObj.setDeleteFlag(Boolean.FALSE);
				bean.saveOrUpdate(roleObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Roles dao - update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start Roles dao - find");

		try {
			Roles roleObj = bean.findById(roleId);
			logger.debug("Roles : "+roleObj+"\t"+ReflectionToStringBuilder.toString(roleObj, ToStringStyle.SHORT_PREFIX_STYLE));
			Assert.assertNotNull("Roles Object is not found", roleObj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Roles dao - find");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start Roles dao - findAll");
		try {
			List<Roles> roleList = bean.findAll();

			int count = 0;
			if (!CollectionUtils.isEmpty(roleList)) {				
				for (Roles roleObj : roleList) {
					logger.debug("Roles ["+(++count)+"] : "+ReflectionToStringBuilder.toString(roleObj, ToStringStyle.SHORT_PREFIX_STYLE));
				}
			}
			Assert.assertNotNull("roleList is not found", roleList);
			Assert.assertNotSame("roleList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Codes dao - findAll");
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("start Roles dao - findAllByExample");
		try {
			Roles d = new Roles();
			d.setDeleteFlag(Boolean.FALSE);
			List<Roles> roleList = bean.findAll(d);

			int count = 0;
			if (!CollectionUtils.isEmpty(roleList)) {
				for (Roles roleObj : roleList) {
					logger.debug("Roles ["+(++count)+"] : "+ReflectionToStringBuilder.toString(roleObj, ToStringStyle.SIMPLE_STYLE));
				}
			}
			Assert.assertNotNull("Active roleList is not found", roleList);
			Assert.assertNotSame("Active roleList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  Roles dao - findAllByExample");		
	}
	
	public void test99Delete() {
		logger.info("start Roles dao - delete");
		try {
			Roles obj = bean.findById(roleId);
			if (obj!=null) {
				bean.delete(obj);
			} else {
				logger.warn("Roles[{}] not exists.", roleId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}	
		logger.info(" end  Roles dao - delete");
	}

	/* test customized method */
	
	
}
