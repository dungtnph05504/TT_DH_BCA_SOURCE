package com.nec.asia.nic.framework.admin.rbac.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;


public class RolesServiceTestCase extends GenericTestCase<Roles, String, RoleService> {

	String roleId = "NIC_USER_TESTSVC";
	
	@Override
	protected String getBeanName() {
		return "roleService";
	}

	@Override
	public void test01Save() {
		logger.info("start RoleService - Save");
		Assert.assertNotNull(bean);
		try {
			Roles roleObj = new Roles();
			roleObj.setRoleId(roleId);
			roleObj.setRoleDesc("NIC Test Officer");
			roleObj.setSystemId("NIC");
			roleObj.setCreateBy("SYSTEM");
			roleObj.setCreateWkstnId("SYSTEM");
			roleObj.setCreateDate(new Date());
			String id = bean.save(roleObj);
			logger.info("[Workstations] save result: "+id);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.info(" end  RoleService - Save");
	}

	@Override
	public void test02Update() {
		logger.info("start RoleService - Update");
		Assert.assertNotNull(bean);
		logger.info("skip method.");
		logger.info(" end  RoleService - Update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start RoleService - FindById");
		Assert.assertNotNull(bean);
		Roles result = bean.findById(roleId);
		Assert.assertNotNull(result);
		logger.info(" end  RoleService - FindById");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start RoleService - findAll");
		Assert.assertNotNull(bean);
		List<Roles> resultList = this.bean.findAll();
		int count = 0;
		for (Roles DBO : resultList) {
			logger.debug("Workstations ["+(++count)+"] : "+"\t"+ReflectionToStringBuilder.toString(DBO, ToStringStyle.SHORT_PREFIX_STYLE));
		}
		logger.info(" end  RoleService - findAll");		
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("start RoleService - FindAllByExample");
		Assert.assertNotNull(bean);
		logger.info(" end  RoleService - FindAllByExample");		
	}

	public void test99Delete() {
		logger.info("start RoleService - delete");
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
		logger.info(" end  RoleService - delete");
	}
}
