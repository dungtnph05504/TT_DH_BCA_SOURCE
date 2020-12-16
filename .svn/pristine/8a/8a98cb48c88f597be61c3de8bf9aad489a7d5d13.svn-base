package com.nec.asia.nic.acl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.AntPathMatcher;

/**
 * 
 * @author bright_zheng
 *
 */
@RunWith(JUnit4.class)
public class AclPathMatchTest {
	private static final Logger logger = LoggerFactory.getLogger(AclPathMatchTest.class);
	//this string comes from Spring DefaultAnnotationHandlerMapping
	private static final String pathString = "/hotels, /hotels.*, /hotels/, /hotels/search, /hotels/search.*, " +
			"/hotels/search/, /hotels/{id}, /hotels/{id}.*, /hotels/{id}/, /hotels/ajax, " +
			"/hotels/ajax.*, /hotels/ajax/, /bookings/delete/{id}, /bookings/delete/{id}.*, " +
			"/bookings/delete/{id}/";
	private static String[] pathPool;

	@BeforeClass
	public static void setUp(){
		logger.debug("Initial List: {}", new Object[] {pathString});
		pathPool = pathString.replaceAll("\\s*","").split(",");
	}
	
	@Test
	public void testAntStylePathMatch(){
		String toBeTestedPath;
		boolean result;
		//Positive Cases
		toBeTestedPath = "/hotels/search";
		result = match(toBeTestedPath);
		logger.debug("checking {} : {}", new Object[] {toBeTestedPath, result});
		Assert.assertEquals(true, result);

		toBeTestedPath = "/hotels";
		result = match(toBeTestedPath);
		logger.debug("checking {} : {}", new Object[] {toBeTestedPath, result});
		Assert.assertEquals(true, result);
		
		toBeTestedPath = "/hotels/1";
		result = match(toBeTestedPath);
		logger.debug("checking {} : {}", new Object[] {toBeTestedPath, result});
		Assert.assertEquals(true, result);
		
		toBeTestedPath = "/hotels/ajax";
		result = match(toBeTestedPath);
		logger.debug("checking {} : {}", new Object[] {toBeTestedPath, result});
		Assert.assertEquals(true, result);
		//Negative Cases
		toBeTestedPath = "/home/welcome";
		result = match(toBeTestedPath);
		logger.debug("checking {} : {}", new Object[] {toBeTestedPath, result});
		Assert.assertEquals(false, result);
	}
	
	private boolean match(String path){
		AntPathMatcher matcher = new AntPathMatcher();
		boolean isMatch = false;
		for(String pathPattern: pathPool){
			isMatch = matcher.match(pathPattern, path);
			if(isMatch) break;
		}
		return isMatch;
	}
}
