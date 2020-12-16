package com.nec.asia.nic.comp.queuesJobSchedule.service;

import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
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

public interface QueuesJobScheduleService extends BusinessService<QueuesJobSchedule, Long>{
	
	public PaginatedResult<QueuesJobSchedule> findAllQueuesJobSchedule(Long recordID, String code, int pageNo, int pageSize, AssignmentFilterAll assignmentFilter) throws Exception;

	public boolean createQueuesJobSchedule(QueuesJobSchedule obj);
	
	public QueuesJobSchedule findQueuesJobScheduleByCode(String code);
	
	public QueuesJobSchedule findQueuesJobScheduleByRecordId(Long recordID);
	
	public boolean deleteQueuesJobSchedule(QueuesJobSchedule queuesJobSchedule) throws Exception;
	
	public List<QueuesJobSchedule> getListInQueuesByType()throws Exception;
	
	public List<QueuesJobSchedule> getListInQueuesForLds()throws Exception;
}
