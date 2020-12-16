package com.nec.asia.nic.comp.legacy.service.impl;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.legacy.domain.NicTabCitizens;
import com.nec.asia.nic.comp.legacy.service.NicTabCitizensService;

public class NicTabCitizensTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static NicTabCitizensService service = null;

	public NicTabCitizensTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			service = context.getBean("nicTabCitizensService", NicTabCitizensService.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public void testFillAllByFields() {
		log("start DataPackService - fillAllByFields");
		try {		
			String nin = "M200590150049C";
			String surname = null;
			String firstName = null;
			String surnameAtBirth = null;
			String sex = null;
			String dob = null;
			log("[fillAllByFields] - nin:" + nin);
			List<NicTabCitizens> resultList = service.fillAllByFields(nin, surname, firstName, surnameAtBirth, sex, dob);
			log("[fillAllByFields] resultList: "+resultList); 
			for (NicTabCitizens n: resultList) {
				log("[fillAllByFields] n: " + ReflectionToStringBuilder.toString(n, ToStringStyle.MULTI_LINE_STYLE));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertNull(e);
		}
		log(" end  DataPackService - fillAllByFields");
	}
	
		
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}
//org.hibernate.search.event.FullTextIndexEventListener