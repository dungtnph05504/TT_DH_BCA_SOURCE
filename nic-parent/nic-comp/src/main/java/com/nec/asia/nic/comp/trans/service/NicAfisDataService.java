package com.nec.asia.nic.comp.trans.service;

import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.service.exception.NicAfisDataServiceException;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * @author JohnPaul_Millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Mar 4, 2014
 * 
 */
/*
 * Modification History:
 * 
 * 22 Sep 2014 (chris): add method findAfisRefIdByTransactionId()
 * 11 Jan 2016 (chris): add method getNextAfisKeyNo()
 */
public interface NicAfisDataService extends BusinessService<NicAfisData, String>{

	public String findAfisRefIdByTransactionId(String transactionId) throws NicAfisDataServiceException;
	public void saveAfisRefId(NicAfisData e) throws NicAfisDataServiceException;
	public NicAfisData findByTransactionId(String transId) throws NicAfisDataServiceException;
	
	public String getNextAfisKeyNo() throws NicAfisDataServiceException;
}
