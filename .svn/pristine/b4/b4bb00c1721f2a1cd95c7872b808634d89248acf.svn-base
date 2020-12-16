package com.nec.asia.nic.comp.queuesJobSchedule.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.LogJobSchedule;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface LogJobScheduleDao extends GenericDao<LogJobSchedule, Long> {

	public PaginatedResult<LogJobSchedule> listLogJobScheduleAllBySearch(Long recordId, String code,int PageNo, int PageSize) throws Exception;
	
	public boolean createLogJobSchedule(LogJobSchedule logJobSchedule) throws Exception;
	
	public List<LogJobSchedule> findByCode (String code) throws Exception;
	public PaginatedResult<LogJobSchedule> findByCode1 (String code, int pageNo, int pageSize) throws Exception;
	
	public List<LogJobSchedule> findByRecordId (Long recordId) throws Exception;
	
	public PaginatedResult<LogJobSchedule> findListByCriteria(int pageNo, int pageSize, AssignmentFilterAll assignmentFilter);
}
