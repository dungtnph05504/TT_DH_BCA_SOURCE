package com.nec.asia.nic.comp.report.service;

import java.util.List;

/**
 * 
 * @author Peddi_Swapna
 *
 */

public interface AfisDataSyncService {
	public List<Object[]> getSynchronizionDetails(String selectedMonth) throws Exception;

	public List<Object[]> getSynchronizionDetailsByDate(String selectedDate) throws Exception;

	public List<Object[]> getSyncRequestAndStatusDetails(String transId) throws Exception;

	public void resetDataSyncRequest(Long dataSyncId) throws Exception;

	public List<Object[]> getSynchronizionDetailsByMonth(String selectedMonth) throws Exception;
	
	
}
