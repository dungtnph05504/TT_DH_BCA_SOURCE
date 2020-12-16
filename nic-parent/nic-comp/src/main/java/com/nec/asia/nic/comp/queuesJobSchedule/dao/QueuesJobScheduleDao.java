package com.nec.asia.nic.comp.queuesJobSchedule.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface QueuesJobScheduleDao extends GenericDao<QueuesJobSchedule, Long> {

	public PaginatedResult<QueuesJobSchedule> listQueuesJobScheduleAllBySearch(Long recordId, String code,int PageNo, int PageSize, AssignmentFilterAll assignmentFilter) throws Exception;
	
	public boolean createQueuesJobSchedule(QueuesJobSchedule queuesJobSchedule) throws Exception;
	
	public QueuesJobSchedule findByCode(String code) throws Exception;
	
	public QueuesJobSchedule findByRecordId(Long recordId) throws Exception;
	
	public boolean deleteQueuesJobSchedule(QueuesJobSchedule queuesJobSchedule) throws Exception;
	
	public List<QueuesJobSchedule> getListInQueuesByType()throws Exception;
	
	public List<QueuesJobSchedule> getListInQueuesForLds()throws Exception;
}
