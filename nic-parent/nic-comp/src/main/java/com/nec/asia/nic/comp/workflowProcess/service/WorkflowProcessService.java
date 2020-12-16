package com.nec.asia.nic.comp.workflowProcess.service;

import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
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

public interface WorkflowProcessService extends BusinessService<NicWorkflowProcess, Long>{
	
	public NicWorkflowProcess findAllById(long id);
	
	public PaginatedResult<NicWorkflowProcess> findAllWorkflowProcess(int pageNo, int pageSize, AssignmentFilterAll assignmentFilter) throws Exception;
	
	public boolean createWorkflowProcess(NicWorkflowProcess workflow);
	
	public boolean editWorkflowProcess(NicWorkflowProcess workflow);
	
	public List<NicWorkflowProcess> findWorkflowProcessByCriteria(String workflowS) throws Exception;
	public List<NicWorkflowProcess> findWorkflowProcessByCriteriaEnd(String workflowE) throws Exception;
	public NicWorkflowProcess findWorkflowProcessByCriteria(String workflowS, String result) throws Exception;
}
