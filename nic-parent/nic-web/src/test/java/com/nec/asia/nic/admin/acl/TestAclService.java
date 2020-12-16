package com.nec.asia.nic.admin.acl;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.framework.admin.code.service.ParametersService;

public class TestAclService extends TestCase {
	public static ApplicationContext context = null;

	public TestAclService() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext(new String[] {"nic/spring-context.xml"}); //"nic/spring-local-datasource.xml", 
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	/*
getRequestURI()         no      /app/test%3F/a%3F+b;jsessionid=S+ID
getRequestURL()         no      http://30thh.loc:8480/app/test%3F/a%3F+b;jsessionid=S+ID
	 */

	public void testMatchResource() {
		log("getBean - start");

		try {
			AclServiceImpl aclService = context.getBean(AclServiceImpl.class);
			if (aclService!=null) {
				log("Build is Okay, 'aclService'  is up: "+aclService);
				Resource[] resources = getResources(); //aclService.getGrantedResources(request);
				String currentUri = "/servlet/documentAttachment";
				Resource matchedResource = aclService.matchedResouce(resources, currentUri);
				
				log("matchedResource: "+matchedResource+", "+(matchedResource!=null?matchedResource.getCode():" no matched!"));
			} else {
				log("Build may not okay, Please check in details.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		log("getBean - end");
	}
	
	private static String[][] functions = new String[][] {{"NIC_DOCUMENT_ATTACHMENT","/documentAttachment"}, {"NIC_CHANGE_PWD","/changePasswordController"}};
	
	private static Resource[] getResources() {
		Resource[] resources = new Resource[functions.length];
		for (int i=0; i<functions.length; i++) {
			String functionId = functions[i][0];
			String functionUrl = functions[i][1];
			Resource resource = new Resource();
			resource.setCode(functionId);
			resource.setName(functionId);
			resource.setUri(functionUrl);
			resources[i] = resource;
		}
		for (Resource loopRs : resources) {
			log("loopResources: "+loopRs+", "+(loopRs!=null?loopRs.getCode():" no matched!"));
		}
		return resources;
	}

	public static void log(String message) {
		System.out.println(message);
	}
}
