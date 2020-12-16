package com.nec.asia.nic.comp.trans.service;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.StatusticalIfor;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.domain.statusticalLogWs;
import com.nec.asia.nic.comp.trans.dto.SyncQueueDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface SyncQueueJobService extends BusinessService<SyncQueueJob, Long>{
	BaseModelSingle<Boolean> saveOrUpdateQueue(SyncQueueJob queue);
	void updateQueueJob(Long id, String status) throws Exception;
	BaseModelSingle<Boolean> updateStatusQueueJob(Long id, String status) throws Exception;
	public BaseModelList<SyncQueueJob> findQueueByReceiver(String receiver, String status, String objType, int maxTotal) throws Exception;
	public BaseModelSingle<SyncQueueJob> findQueueByStatus(String receiver, String status, String objType) throws Exception;
	public BaseModelSingle<SyncQueueJob> findQueueByObjectId(String objectId, String receiver, String status, String objType) throws Exception;
	public PaginatedResult<SyncQueueDto> findListQueueBySearch(String objectId, String objectType, String status, String receiver, int pageNo, int pageSize);
	public SyncQueueJob findQueueByInfo(Long id, String status) throws Exception;
	public List<StatusticalIfor> findStatusByDate(String date);
	public List<StatusticalIfor> findStatusAll(String loaiHangDoi,String dateFrom,String dateTo,String receiver);
	
	public List<statusticalLogWs> findQueueByAllProperty(String dateFrom,String dateTo,String status,String receiver,String objecType);
	public PaginatedResult<SyncQueueJob> findListQueueBySearch(String dateFrom,String dateTo,String status,String receiver,String objecType,int pageNo,int pageSize);
	
	public PaginatedResult<SyncQueueJob> findListQueueByDateCurrent(String dateCurrent,String status,String receiver,String objecType,int pageNo,int pageSize);

	public SyncQueueJob findSyncQueueJobByObjIdOrReceiver(String objectId, String objectType, String receiver, String status) throws Exception;
	
	public Boolean saveOrUpdateSyncQueue(SyncQueueJob syncQueueJob) throws Exception;
	public SyncQueueJob findSyncQueueJobByManyObjType(String transactionId,
			String[] strings, String siteCode, String codeStatusQueuePending)throws Exception;
	public int countByConditions(String objectType, String site, String status, Date date)throws Exception;
	public PaginatedResult<SyncQueueDto> getPageByCodition(String fromDate,
			String toDate, String receiver, String objType, int pageNo,
			int pageSize);
	public List<SyncQueueDto> getAllByCodition(String fromDate, String toDate,
			String receiver, String objType);
}
