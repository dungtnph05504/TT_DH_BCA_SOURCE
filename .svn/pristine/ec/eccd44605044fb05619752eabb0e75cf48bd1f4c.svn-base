package com.nec.asia.nic.storeforward;

import org.junit.Test;

import com.nec.asia.nic.framework.storeforward.StoreForwardService;
import com.nec.asia.nic.framework.storeforward.impl.StoreForwardServiceImpl;

/**
 * Test cases for implementation of Store and Forward pattern
 * 
 * @author bright_zheng
 *
 */
public class StoreForwardTest {
	
	StoreForwardService<String,TransactionUploadingSampleForwardHandler> 
		storeForwardService = new StoreForwardServiceImpl<String, TransactionUploadingSampleForwardHandler>();
	
	/** Test case of input as "OK" which will be processed without exception */
	private static final String input1 = "OK";

	/** Test case of input as "NOT OK" which will be processed with exception */
	private static final String input2 = "NOT OK";
	
	
	@Test
	public void testSuccessCase(){
		storeForwardService.forward(
				input1, 
				new TransactionUploadingSampleForwardHandler(input1));
	}
	
	@Test
	public void testFailureCase(){
		storeForwardService.forward(
				input2, 
				new TransactionUploadingSampleForwardHandler(input2));
	}
	
	@Test
	public void waitAndSee() throws InterruptedException{
		Thread.sleep(1*10*1000);
	}
}
