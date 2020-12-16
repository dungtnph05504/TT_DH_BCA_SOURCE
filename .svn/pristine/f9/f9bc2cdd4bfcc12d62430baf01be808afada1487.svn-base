package com.nec.asia.nic.comp.queuesJobSchedule.service;

import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.LogJobSchedule;
import com.nec.asia.nic.comp.trans.domain.NicBusinessList;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
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
 * 18 Aug 2013 (chris): add method to retrieve payment def for replacement transaction.
 * 26 Sep 2013 (chris): add BASIC_FEE_FOR_SC for senior citizen fix rate.
 */

public interface LogJobScheduleService extends BusinessService<LogJobSchedule, Long>{
	
	public PaginatedResult<LogJobSchedule> listLogJobScheduleAllBySearch(Long recordID, String code, int PageNo, int PageSize)throws Exception;
	
	public boolean createLogJobSchedule(LogJobSchedule obj);
	
	public List<LogJobSchedule> findListByCode(String code);
	public PaginatedResult<LogJobSchedule> findListByCode1(String code, int pageNo, int pageSize);
	
	public List<LogJobSchedule> findByRecordID (Long recordID);
	
	public PaginatedResult<LogJobSchedule> findListByCriteria(int pageNo, int pageSize, AssignmentFilterAll assignmentFilter);
}
