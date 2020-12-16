package com.nec.asia.nic.framework.admin;
import java.io.Serializable;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oracle/spring-context.xml"})
public abstract class GenericTestCase<E, 
		ID extends Serializable, 
		Bean> {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected ApplicationContext context = null;
	protected Bean bean = null;
	
	public GenericTestCase() {
		//init();
	}
	
	protected abstract String getBeanName();

	@Before
	public void init() {
		try {
			//for JUnit 3, if junit 4, then it can use @ContextConfiguration
			//System.setProperty("log4j.configuration", "classpath:log4j.xml");
			//context = new ClassPathXmlApplicationContext("oracle/spring-context.xml");
			bean = (Bean) context.getBean(this.getBeanName());
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public abstract void test01Save();
	
	@Test
	public abstract void test02Update();
	
	@Test
	public abstract void test03FindById();
	
	@Test
	public abstract void test04FindAll();
	
	@Test
	public abstract void test05FindAllByExample();
	
	@Test
	public abstract void test99Delete();
		
	protected void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}
