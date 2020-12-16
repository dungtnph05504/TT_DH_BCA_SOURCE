package com.nec.asia.nic.comp.workflowProcess.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface WorkflowProcessDao extends GenericDao<NicWorkflowProcess, Long> {
	//default methods from super class
	//public PaymentDef findById(PaymentDefId id);
	//public Long save (PaymentDef e);
	//public void saveOrUpdate(PaymentDef e);
    //public PaymentDef merge(PaymentDef e);
	//public void delete(PaymentDef e);
	//public void delete(PaymentDefId id);
	//public List<PaymentDef> findAll ();

	public NicWorkflowProcess findWorkflowProcessById(Long id) throws Exception;
	
	public PaginatedResult<NicWorkflowProcess> findAllWorkflowProcess(int PageNo, int PageSize, AssignmentFilterAll assignmentFilter) throws Exception;
	
	public boolean createWorkflowProcess(NicWorkflowProcess workflow) throws Exception;
	
	public boolean editWorkflowProcess(NicWorkflowProcess workflow) throws Exception;
	
	public List<NicWorkflowProcess> findWorkflowProcessByCriteria(String workflowS) throws Exception;
	public List<NicWorkflowProcess> findWorkflowProcessByCriteriaEnd(String workflowE) throws Exception;
	public NicWorkflowProcess findWorkflowProcessByCriteria(String workflowS, String result) throws Exception;
}
