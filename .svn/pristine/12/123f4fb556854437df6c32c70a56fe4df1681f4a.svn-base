package com.nec.asia.nic.security.dao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.framework.security.ad.ActiveDirectoryService;
import com.nec.asia.nic.framework.security.ad.helper.UserDTO;

import junit.framework.TestCase;

public class ADTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static ActiveDirectoryService service = null;
	public ADTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			service = context.getBean("_ldapService", ActiveDirectoryService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}catch (Error e) {
			e.printStackTrace();
		}
	}
	public void testAuthenticate() {
		log("start ActiveDirectoryService - authenticate");
		try {
			String userId = "administrator";
			String password = "Password1";
			boolean authenticated = service.authenticate(userId, password);
			log("userId:"+userId+"\tauthenticated:"+authenticated);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  ActiveDirectoryService - authenticate");
	}
	
	public void testFindUser() {
		log("start ActiveDirectoryService - findUser");
		try {
			String userId = "ppWF";// "admin";
			List<UserDTO> userList = service.findUser(userId);
			for (UserDTO user: userList) {
				log("userId:"+userId+"\tresult:"+ReflectionToStringBuilder.toString(user, ToStringStyle.SHORT_PREFIX_STYLE));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  ActiveDirectoryService - findUser");
	}
	
	public void testIsUserExists() {
		log("start ActiveDirectoryService - isUserExists");
		try {
			String userId = "baf";// "admin";
			boolean exists = service.isUserExists(userId);
			log("userId:"+userId+"\texists:"+exists);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  ActiveDirectoryService - isUserExists");
	}
	
			
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}