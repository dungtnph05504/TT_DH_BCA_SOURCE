package com.nec.asia.nic.comp.trans.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.dto.RICTxnRptDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnUpldRptDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

/* 
 * Modification History:
 *  
 * 04 Jan 2014 (chris): remove duplicate method declaration. 
 * 
 * 08 Jan 2014 (Sailaja): Added findAllForPaginationByFilterOnReprint method for Re-print
 * 16 Jan 2014 (priya): add method getRicUnCollectedCardStatusRptRecordList().
 * 24 Jan 2014 (eunike): changed method getRicUnCollectedCardStatusRptRecordList throws exception
 * 10 Feb 2014 (priys): add method   getRicExprsPrintRptRecordList, getCardDeliveryStatusRptRecordList
 * 19 Mar 2014 (Swapna): Added findAllTransIdForPagination method to load only transaction ids, nins without loading remaining fields info.
 * 14 Jan 2016 (Setia) : Added findAllByFields method to allow to input dynamic filter
 */
public interface NicRegistrationDataDao extends GenericDao<NicRegistrationData, String> {
	//default methods from super class
	//public NicRegistrationData findById(String transactionId);
	//public String save (NicRegistrationData e);
	//public void saveOrUpdate(NicRegistrationData e);
    //public NicRegistrationData merge(NicRegistrationData e);
	//public void delete(NicRegistrationData e);
	//public void delete(String transactionId);
	//public List<NicRegistrationData> findAll ();
	
	public NicTransaction getTransactionRegistrationData(NicTransaction transObj);
	
	
	public Long getNicIdByTransactionId(String transactionId);

	PaginatedResult<NicRegistrationData> findAllForPaginationByFilter(
			NicRegistrationData nicTransaction, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order order);
	
	PaginatedResult<NicRegistrationData> getRicTxnRptRecordList(RICTxnRptDto ricTxnRptcriteria,int page, int pageSize); 
	 
	PaginatedResult<NicRegistrationData> getRicTxnUploadRptRecordList(RICTxnUpldRptDto ricTxnUpldRptcriteria,int page, int pageSize); 

	PaginatedResult<NicRegistrationData> findAllForPaginationByFilter(
			NicRegistrationData nicTransaction, Date dateFrom, Date dateTo, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order order);
	
	public PaginatedResult<NicRegistrationData> findAllForPagination(int currentPage, int pageSize, Order hibernateOrder, String siteCode, String officerId, Date fromDate, Date toDate) throws Exception;

	public List getAllRegistrations(Date fromDate, Date toDate, String[] siteCodes) throws Exception;


	public List<NicRegistrationData> getRegistrations(List<String> transIds) throws Exception;


	public List getRegistrationsInfoBySite(Date fromDate, Date toDate, String siteCode) throws Exception;


	public List<NicRegistrationData> getFPEncodeValues(Date fromDate, Date toDate, String[] sites) throws Exception;

	public List<Object[]>  getTotalCardsIssuedBySite(Date fromDate, Date toDate, String[] sites) throws Exception;
	
	public PaginatedResult<NicRegistrationData> findAllForPaginationByFilterOnReprint(
			NicRegistrationData nicRegistrationData, Date from, Date to,
			int pageNo, int pageSize, boolean ignoreCase, boolean enableLike,
			Order order);

	 


	PaginatedResult<NicRegistrationData> getRicUnCollectedCardStatusRptRecordList(RICTxnRptDto unColCardStatuSrchCriteria,int page, int pageSize)throws Exception;
	PaginatedResult<NicRegistrationData> getRicExprsPrintRptRecordList(RICTxnRptDto exprsPrintRptSrchCriteria,int page, int pageSize);
	PaginatedResult<NicRegistrationData> getCardDeliveryStatusRptRecordList(RICTxnRptDto cardDelStatuSrchCriteria,int page, int pageSize) throws Exception;


	public PaginatedResult<NicRegistrationData> findAllTransIdForPagination(int currentPage, int pageSize, Order hibernateOrder, String[] siteCodes, String officerId, Date fromDate, Date toDate)  throws Exception;
	
	public List<NicRegistrationData> findAllByFields (Map<String,Object> fields) throws  Exception;
	
	public boolean isLookoutData (String firstName, String lastName, String middleName) throws Exception;

	public PaginatedResult<NicRegistrationData> findAllForPagination(NicRegistrationData nicRegistration, int pageNo, int pageSize);
	public NicRegistrationData getNicDataById(String transactionId);
	
	public BaseModelSingle<String> saveNicRegistrationData(NicRegistrationData nicRegData);
	
	public BaseModelSingle<Void> saveOrUpdateRegisData(NicRegistrationData nicRegData);
	
	public BaseModelSingle<NicRegistrationData> findRegistDataById(String transactionId);


	public List<NicRegistrationData> findRegByPersonInfo(String name,
			String gender, Date date);
}
