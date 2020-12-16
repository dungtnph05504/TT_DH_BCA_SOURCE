package com.nec.asia.nic.framework.admin.rbac.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;


public class UserServiceTestCase extends GenericTestCase<Users, String, UserService> {

	private String userId = "TESTSVC_NIC";
	@Override
	protected String getBeanName() {
		return "userService";
	}

	@Override
	public void test01Save() {
		logger.info("start UserService - Save");
		Assert.assertNotNull(bean);
		try {
			Users result = bean.findById(userId);
			if (result==null) {
				Users userObj = new Users();
				userObj.setUserId(userId);
				userObj.setSysAdminFlag(Boolean.TRUE);
				userObj.setUserStartDate(new Date());
				userObj.setUserEndDate(DateUtils.addYears(new Date(), 100));
				userObj.setDateOfPwdExpiry(DateUtils.addMonths(new Date(), 3));
				userObj.setSystemId("NIC");
				userObj.setCreateBy("SYSTEM");
				userObj.setCreateWkstnId("SYSTEM");
				userObj.setCreateDate(new Date());
				userObj.setDeleteFlag(Boolean.FALSE);
				String id = bean.save(userObj);
				logger.info("[Users] save result: "+id);
			} else {
				result.setUserId(userId);
				result.setSysAdminFlag(Boolean.TRUE);
				result.setUserEndDate(DateUtils.addYears(new Date(), 100));
				result.setDateOfPwdExpiry(DateUtils.addMonths(new Date(), 3));
				result.setCreateBy("SYSTEM");
				result.setCreateWkstnId("SYSTEM");
				result.setCreateDate(new Date());
				result.setDeleteFlag(Boolean.FALSE);
				bean.saveOrUpdate(result);
				logger.info("[Users] update result: "+userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  UserService - Save");
	}

	@Override
	public void test02Update() {
		logger.info("start UserService - Update");
		Assert.assertNotNull(bean);
		logger.info("skip method.");
		logger.info(" end  UserService - Update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start UserService - FindById");
		Assert.assertNotNull(bean);
		String userId = "ADMINISTRATOR";
		Users result = bean.findById(userId);
		Assert.assertNotNull(result);
		logger.info(" end  UserService - FindById");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start UserService - findAll");
		Assert.assertNotNull(bean);
		List<Users> resultList = this.bean.findAll();
		int count = 0;
		for (Users DBO : resultList) {
			logger.debug("Users ["+(++count)+"] : "+"\t"+ReflectionToStringBuilder.toString(DBO, ToStringStyle.SHORT_PREFIX_STYLE));
		}
		logger.info(" end  UserService - findAll");		
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("start UserService - FindAllByExample");
		Assert.assertNotNull(bean);
		logger.info("skip method.");
		logger.info(" end  UserService - FindAllByExample");		
	}
	

	public void test99Delete() {
		logger.info("start UserService - delete");
		try {
			Users obj = bean.findById(userId);
			if (obj!=null) {
				bean.delete(obj);
			} else {
				logger.warn("Users[{}] not exists.", userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}	
		logger.info(" end  UserService - delete");
	}
	
	/** customized function **/
	@Test
	public void testCMGetFunctionsByUserWkstn() {
		String userId = "ADMINISTRATOR";
		String workstationId = "CHRIS-WONGNB";
		List<String> functionList = this.bean.getFunctions(userId, workstationId);
		logger.info("Users ["+userId+"] : "+"\t"+functionList);
	}
	
}
