package com.nec.asia.nic.comp.job.service;

public interface ReportDataAccessService {
		
		/**
		 * Execute db statistics.
		 *
		 * @param userId the user id
		 * @param terminalId the terminal id
		 * @throws StoredProcedureExecutionException the stored procedure execution exception
		 */
		public void executeDBStatistics(String userId, String terminalId) throws Exception;
		
		/**
		 * Execute customized db statistics.
		 *
		 * @param storedProcedureNames the stored procedure names
		 * @param inputDate the input date
		 * @param userId the user id
		 * @param terminalId the terminal id
		 * @throws StoredProcedureExecutionException the stored procedure execution exception
		 */
		public void executeCustomizedDBStatistics(String[] storedProcedureNames, String inputDate, String userId, String terminalId) throws Exception;
	}
