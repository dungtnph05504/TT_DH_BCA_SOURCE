/**
 * 
 */
package com.nec.asia.nic.comp.trans.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.dto.NicRegistrationDataDto;
import com.nec.asia.nic.comp.trans.service.exception.RegistrationServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 11, 2013
 */

/*
 * Modification History:
 * 
 * 08 Jan 2014 (Sailaja): Added findAllForPaginationOnReprint method for Re-print
 * 19 Mar 2014 (Swapna): Added findAllTransIdForPagination method to load only transaction ids, nins without loading remaining fields info.
 * 
 */
public interface NicRegistrationDataService extends BusinessService<NicRegistrationData, String> {

	PaginatedResult<NicRegistrationData> findAllForPagination(
			PageRequest pageRequest, NicRegistrationData NicRegistrationData)
			throws RegistrationServiceException;
	
	public List<NicTransactionAttachment> findAllRegistrationPhotos(PageRequest pageRequest, NicRegistrationData nicRegistrationData) throws RegistrationServiceException;
	
	
	public PaginatedResult<NicRegistrationData> findAllForPagination(int currentPage, int pageSize, Order hibernateOrder, String siteCode, String officerId, Date fromDate, Date toDate) throws TransactionServiceException, Exception;

	public PaginatedResult<NicRegistrationDataDto> findAllForPagination(
			PageRequest pageRequest, NicRegistrationDataDto nicRegData)
			throws RegistrationServiceException;

	public List getAllRegistrations(Date fromDate, Date toDate, String[] siteCodes) throws Exception;
	/*public NicRegistrationData findById(String transactionId);*/

	public List<NicRegistrationData> getRegistrations(List<String> transIds)  throws Exception;

	public List getRegistrationsInfoBySite(Date fromDate, Date toDate,String siteCode)  throws Exception;

	public List<NicRegistrationData> getFPEncodeValues(Date fromDate, Date toDate, String[] sites) throws Exception;

	PaginatedResult<NicRegistrationDataDto> findAllForPaginationOnReprint(
			PageRequest pageRequest, NicRegistrationDataDto nicRegData) throws Exception;

	
	public List<Object[]>  getTotalCardsIssuedBySite(Date fromDate, Date toDate, String[] sites) throws Exception;

	PaginatedResult<NicRegistrationData> findAllTransIdForPagination(int currentPage, int pageSize, Order hibernateOrder, String[] siteCodes, String officerId, Date fromDate, Date toDate) throws Exception;

	public List<NicRegistrationData> findAllByFields (Map<String,Object> fields) throws RegistrationServiceException, Exception;
	
	public boolean isExistOnLookoutData (String firstName, String lastName, String middleName) throws RegistrationServiceException, Exception;
	public NicRegistrationData getNicDataById(String transactionId);
	public BaseModelSingle<NicRegistrationData> findRegistDataById(String transactionId);

	List<NicRegistrationData> findRegByPersonInfo(String name, String gender,
			String dateOfBirth) throws Exception;
}
