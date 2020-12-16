package com.nec.asia.nic.framework.security.ad.impl;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nec.asia.nic.framework.security.ad.ActiveDirectoryService;
import com.nec.asia.nic.framework.security.ad.helper.UserDTO;

import junit.framework.TestCase;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oracle/spring-context.xml"})
public class ActiveDirectoryServiceTestCase extends TestCase {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public ApplicationContext context = null;
	@Autowired
	@Qualifier("_ldapService")
	public ActiveDirectoryService service = null;
	
	public ActiveDirectoryServiceTestCase() {
		//init();
	}

	@Before
	public void init() {
		try {
			//For Junit 3
			//context = new ClassPathXmlApplicationContext("oracle/spring-context.xml");
			//service = context.getBean("_ldapService", ActiveDirectoryService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}catch (Error e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAuthenticate() {
		logger.info("start ActiveDirectoryService - authenticate");
		try {
			String userId = "administrator";
			String password = "Password1";
			//boolean authenticated = service.authenticate(userId, password);
			//logger.info("userId:"+userId+"\tauthenticated:"+authenticated);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  ActiveDirectoryService - authenticate");
	}
	
	@Test
	public void testFindUser() {
		logger.info("start ActiveDirectoryService - findUser");
		try {
			String userId = "baf";// "admin";
			List<UserDTO> userList = service.findUser(userId);
			for (UserDTO user: userList) {
				logger.info("userId:"+userId+"\tresult:"+ReflectionToStringBuilder.toString(user, ToStringStyle.SHORT_PREFIX_STYLE));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  ActiveDirectoryService - findUser");
	}
	
	@Test
	public void testIsUserExists() {
		logger.info("start ActiveDirectoryService - isUserExists");
		try {
			String userId = "administrator";// "admin";
			boolean exists = service.isUserExists(userId);
			logger.info("userId:"+userId+"\texists:"+exists);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  ActiveDirectoryService - isUserExists");
	}
	
}