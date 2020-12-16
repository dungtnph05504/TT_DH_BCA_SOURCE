package com.nec.asia.nic.storeforward;

import com.nec.asia.nic.framework.storeforward.StoreForwardException;
import com.nec.asia.nic.framework.storeforward.impl.AbstractForwardHandler;

/**
 * This is a simple example of forward handler implementation
 * 
 * @author bright_zheng
 *
 */
public class TransactionUploadingSampleForwardHandler 
	extends AbstractForwardHandler<String, String>{	
	
	private static final String EXPECTED = "OK";

	public TransactionUploadingSampleForwardHandler(String data) {
		super(data);
	}

	@Override
	public String execute() throws StoreForwardException {		
		logger.debug("executing here");
		
		//mock logic here by doing string comparison
		if(!EXPECTED.equals(data)){
			//if the transactionId is not set as "OK", throws exception
			throw new StoreForwardException("Excepted: " + EXPECTED + " while actual: " + data);
		}
		//here we simply return the input
		//please think carefully for your return instead of copying my code
		return this.data;
	}

	@Override
	public void handleSuccess(String r) {
		logger.debug("handling success here - {}", r);
	}

	@Override
	public void handleFailure(StoreForwardException e) {
		logger.debug("handling failure here - {}", e.getMessage());
	}

}
