package com.nec.asia.nic.framework.security.ad.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.framework.common.ChecksumGenerator;
import com.nec.asia.nic.framework.security.service.AuthenticationService;
import com.nec.asia.nic.web.session.UserSession;

import junit.framework.TestCase;

public class AuthenticationServiceTestCase extends TestCase {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static ApplicationContext context = null;
	public static AuthenticationService service = null;
	public AuthenticationServiceTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("oracle/spring-context.xml");
			service = context.getBean("authenticationService", AuthenticationService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}catch (Error e) {
			e.printStackTrace();
		}
	}
	public void testAuthenticate() {
		logger.info("start authenticationService - authenticate");
		try {
			String userId = "ADMINISTRATOR";
			String password = "Password1"; 
			String workstationId = "CHRIS-WONGNB";
			UserSession userSession = service.login(userId, password, workstationId);
			logger.info("userId:"+userId+" userSession:"+userSession);
			if (userSession!=null) {
				logger.info("userSession.functionsList:"+userSession.getFunctionsList());
				logger.info("userSession.functions:"+userSession.getFunctions());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" end  authenticationService - authenticate");
	}
	
//	public void testChecksum() {
//		ChecksumGenerator util = new ChecksumGenerator();
//		String input = "ADMIN";
//		try {
//			String checksum = util.computeCheckSum(input);
//			logger.info("userId:"+input+" checksum:"+checksum);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
			
	public static void log(String message) {
		System.out.println("================================== <<< "+message+" >>>");
	}
}