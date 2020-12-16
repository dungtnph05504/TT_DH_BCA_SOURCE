package com.nec.asia.nic.test.biometric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

public class TestDeleteRegistration extends TestBase {
	protected static final Logger logger = LoggerFactory.getLogger(TestDeleteRegistration.class);
	
	public void testDeleteFp () throws Exception {
	       IdserverAgentService idsvrAgentService = (IdserverAgentService )SpringServiceManager.getBean("idserverAgentService");
	      // String transactionId = "RICHQ-2013-000002";  //(application_ref_no)
	       String transactionId = "NIC_07";   
	       idsvrAgentService.deleteRegistration(TestBase.SYSTEM_ID, transactionId);
	}
	 
	 	    
}
