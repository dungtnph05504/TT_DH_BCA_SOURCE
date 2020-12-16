package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.service.exception.InventoryRefServiceException;


/* 
 * Modification History:
 *  
 * 09 Oct 2013 (chris): init class.
 */
public interface InventoryRefService {
//	public static final String UPDATE_ERROR		= "Unsuccessful update NIC record.";
//	public static final String UPDATE_SUCCEED 	= "Record NIC successful updated";
//
//	public void updateCardStatus(String ccn, String cardStatus, String officerId, String workstationId, String siteCode) throws InventoryRefServiceException;
//	public void updateTransactionStatus(String ccn, String transactionStatus, String officerId, String workstationId, String siteCode) throws InventoryRefServiceException;
	
	public boolean updateDispatchedStatus(List<String> documentNoList, String siteCode, String remark) throws InventoryRefServiceException;
	public boolean updateIssuedStatus(List<String> documentNoList, String remark) throws InventoryRefServiceException;
	public boolean updateRejectedStatus(List<String> documentNoList, String remark) throws InventoryRefServiceException;
}
