/**
 * 
 */
package com.nec.asia.nic.comp.trans.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicDocumentHistory;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.RetrieveDocumentDataResponse;
import com.nec.asia.nic.comp.trans.service.exception.DocumentDataServiceException;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * 
 * @author chris_wong
 *
 */
/*
 * Modification History:
 * 
 * 04 Jan 2016 (chris): init interface 03 Feb 2016 (setia): Added method
 * updateDocByStatus 04 Feb 2016 (jesus): Added 2 findAllBy* methods 09 May 2016
 * (khang): add method updatePackageDispatchInfoByPassportNoList 20 May 2016
 * (khang/chris): move save Dispatch Info logic from SyncWSImpl to
 * DocumentDataServiceImpl
 */
public interface DocumentDataService extends
		BusinessService<NicDocumentData, NicDocumentDataId> {

	// default methods from super class
	// public NicDocumentData findById(NicDocumentDataId id);
	// public NicDocumentDataId save (NicDocumentData e);
	// public void saveOrUpdate(NicDocumentData e);
	// public NicDocumentData merge(NicDocumentData e);
	// public void delete(NicDocumentData e);
	// public void delete(NicDocumentDataId id);
	// public List<NicDocumentData> findAll ();

	public BaseModelSingle<NicDocumentData> findByDocNumber(String passportNo);

	public List<NicDocumentData> findAllByTransactionId(String transactionId,
			List<String> statusesToInclude);

	public List<NicDocumentData> findAllByTransactionId_notInStatuses(
			String transactionId, List<String> statusesToInclude);

	public BaseModelSingle<Collection<NicDocumentData>> findByTransactionId(
			String transactionId) throws DocumentDataServiceException;

	/**
	 * This method to update the status of document data
	 * 
	 * @param transactionId
	 * @param passportNo
	 * @param status
	 * @return
	 * @throws DocumentDataServiceException
	 */
	public NicDocumentData updateDocByStatus(String transactionId,
			String passportNo, TransactionStatus transStatus, String userId)
			throws DocumentDataServiceException;

	public List<NicDocumentData> findAllDispatchedDocuments(Integer maxResult)
			throws DocumentDataServiceException;

	public List<NicDocumentData> findAllSyncErrorDocuments(Integer maxResult)
			throws DocumentDataServiceException;

	public int updatePackageDispatchInfoByPassportNoList(
			List<String> passportNoList, String packageId,
			Date packageDateTime, String dispatchId, Date dispatchDateTime,
			String status, Date statusUpdateTime, String officerId,
			String workstationId) throws DocumentDataServiceException;

	public int countExistingPassportByPassportNoList(List<String> passportNoList)
			throws DocumentDataServiceException;

	public List<NicDocumentData> findAllByDocNumber(String passportNo);

	// public NicDocumentData findByDocNumber(String passportNo);

	public PaginatedResult<NicDocumentData> findAllForPagination(
			PageRequest pageRequest, NicDocumentData nicDocumentData)
			throws DocumentDataServiceException;

	public void updatePackageDispatchInfo(List<String> passportNoList,
			List<String> transactionIdList,
			List<NicDocumentHistory> documentHistoryList,
			List<NicTransactionLog> transactionLogList, String packageId,
			Date packageDateTime, String dispatchId, Date dispatchDateTime,
			String status, Date statusUpdateTime, String officerId,
			String workstationId) throws DocumentDataServiceException;

	public List<NicDocumentData> findAllDocumentsForSync(String siteCode)
			throws DocumentDataServiceException;

	public void updateSyncStatus(List<String> passportNoList, String officerId,
			String wkstnId, String siteCode)
			throws DocumentDataServiceException;

	public boolean updatePassportNo(String transactionId, String userId,
			String wkstnId, String passportNo);

	public List<NicDocumentData> findAllBySiteCodeAndStage(String site,
			String status, int year, int check);

	public BaseModelSingle<NicDocumentData> getDocumentDataById(
			String transactionId);

	public List<RetrieveDocumentDataResponse> getlistDocumentData(
			String siteCode, String passportNo, Date issueDatePassport,
			String typePassport, Date expireDatePassport, String handoverB);

	public RetrieveDocumentDataResponse detailDocumentData(String passportNo);

	public BaseModelSingle<Void> saveOrUpdateDocData(NicDocumentData nicDoc);

	public BaseModelList<NicDocumentData> findAllPassportByCondition(
			String passportNo, String archiveCode);

	public BaseModelSingle<NicDocumentData> findPassportByConditions(
			String passportNo, String name, String gender, String dateOfBirth,
			String dateOfDocIssue, String dateOfDocExpiry);

	public NicDocumentData findPassportToRestore(
			String passportNo, String reason, String name, String gender,
			String dateOfBirth) throws Exception;

	public NicDocumentData findDocDataById(String transactionId,
			String passportNo)throws Exception;

	public NicDocumentData findDocDataByTranId(String transactionId) throws Exception;

	public List<NicDocumentData> findValidPassportById(
			String passportNo) throws Exception;

	public List<NicDocumentData> findListDocByTranId(String transactionIdHit) throws Exception;

	public NicDocumentData findValidDocByListTranId(
			String[] listTranId)throws Exception;

	public NicDocumentData findDocDataToDel(String transactionId,
			String passportNo, String phoiSerialNo)throws Exception;

	public void deletePassport(NicDocumentData nicDocData)throws Exception;

	public List<NicDocumentData> findDocByListPassportNo(String[] listPassportNo)throws Exception;
	public List<NicDocumentData> findAllDocByListTranId(String[] listTranId)throws Exception;
}
