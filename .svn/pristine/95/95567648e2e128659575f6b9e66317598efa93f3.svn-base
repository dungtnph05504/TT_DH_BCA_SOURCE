package com.nec.asia.nic.framework.admin.rbac.dao;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.springframework.util.CollectionUtils;

import com.nec.asia.nic.framework.admin.GenericTestCase;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;

public class UserDaoTestCase extends GenericTestCase<Users, String, UsersDao> {

	@Override
	protected String getBeanName() {
		return "usersDao";
	}
	
	String userId = "TEST_NIC";
	@Override
	public void test01Save() {
		logger.info("start Users dao - save");
		try {
			//userId = "PPWF";
			Users result = bean.findById(userId);
			if (result==null) {
				Users userObj = new Users();
											
				userObj.setUserId(userId);
				userObj.setSysAdminFlag(Boolean.TRUE);
				userObj.setSystemId("NIC");
				userObj.setCreateBy("SYSTEM");
				userObj.setCreateWkstnId("SYSTEM");
				userObj.setCreateDate(new Date());
				String id = bean.save(userObj);
				logger.info("[Users] save result: "+id);
			} else {
				result.setUserId(userId);
				result.setSysAdminFlag(Boolean.TRUE);
				result.setSystemId("NIC");
				result.setUpdateBy("SYSTEM");
				result.setUpdateWkstnId("SYSTEM");
				result.setUpdateDate(new Date());
				bean.saveOrUpdate(result);
				logger.info("[Users] update result: "+userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  Users dao - save");
	}

	@Override
	public void test02Update() {
		logger.info("start Users dao - update");
		try {
			Users userObj = bean.findById(userId);
			Assert.assertNotNull("User Object is not found", userObj);
			if (userObj!=null) {
				logger.debug("Users t : "+userId+"\n"+ReflectionToStringBuilder.toString(userObj, ToStringStyle.MULTI_LINE_STYLE));
				userObj.setUpdateBy("SYSTEM");
				userObj.setUpdateDate(new Date());
				userObj.setUpdateWkstnId("SYSTEM");
				userObj.setDeleteBy("");
				userObj.setDeleteWkstnId("");
				userObj.setDeleteDate(null);
				userObj.setDeleteFlag(Boolean.FALSE);
				bean.saveOrUpdate(userObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  Users dao - update");		
	}

	@Override
	public void test03FindById() {
		logger.info("start Users dao - find");

		try {
			Users userObj = bean.findById(userId);
			logger.debug("Users : "+userId+"\n"+ReflectionToStringBuilder.toString(userObj, ToStringStyle.MULTI_LINE_STYLE));
			Assert.assertNotNull("Users Object is not found", userObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  Users dao - find");		
	}

	@Override
	public void test04FindAll() {
		logger.info("start Users dao - findAll");
		try {
			List<Users> userList = bean.findAll();

			int count = 0;
			if (!CollectionUtils.isEmpty(userList)) {				
				for (Users userObj : userList) {
					logger.debug("Users ["+(++count)+"] : "+userObj.getUserId()+"\t"+ReflectionToStringBuilder.toString(userObj, ToStringStyle.SHORT_PREFIX_STYLE));
				}
			}
			Assert.assertNotNull("userList is not found", userList);
			Assert.assertNotSame("userList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  Users dao - findAll");
	}

	@Override
	public void test05FindAllByExample() {
		logger.info("start Users dao - findAllByExample");
		try {
			Users d = new Users();
			d.setSysAdminFlag(Boolean.TRUE);
			List<Users> userList = bean.findAll(d);

			int count = 0;
			if (!CollectionUtils.isEmpty(userList)) {
				for (Users userObj : userList) {
					logger.debug("Users ["+(++count)+"] : "+userObj.getUserId()+"\t"+ReflectionToStringBuilder.toString(userObj, ToStringStyle.SIMPLE_STYLE));
				}
			}
			Assert.assertNotNull("Active userList is not found", userList);
			Assert.assertNotSame("Active userList count should not be zero", count, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  Users dao - findAllByExample");		
	}
	
	public void test99Delete() {
		logger.info("start Users dao - delete");
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
		logger.info(" end  Users dao - delete");
	}
	
	/* test customized method */
	
	
}
