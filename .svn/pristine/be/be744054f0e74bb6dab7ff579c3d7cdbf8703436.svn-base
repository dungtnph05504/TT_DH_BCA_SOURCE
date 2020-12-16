package com.nec.asia.nic.comp.workflowProcess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.workflowProcess.dao.WorkflowProcessDao;
import com.nec.asia.nic.comp.workflowProcess.service.WorkflowProcessService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.admin.code.domain.type.ParameterType;
import com.nec.asia.nic.framework.admin.code.dto.PaymentDefDTO;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.BaseDTOMapper;

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
 * 06 Dec 2013 (chris): change reduce amount=TOTAL-BASIC_FEE_FOR_SC instead of return BASIC_FEE_FOR_SC 
 * 19 Aug 2014 (chris): to fix reduceAmount = 0 if ReduceRateFlag is false.
 */

@Service("workflowProcessService")
public class WorkflowProcessServiceImpl extends
		DefaultBusinessServiceImpl<NicWorkflowProcess, Long, WorkflowProcessDao>
		implements WorkflowProcessService {
	@Autowired
	private ParametersDao parametersDao;
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public WorkflowProcessServiceImpl() {
	}

	@Autowired
	public WorkflowProcessServiceImpl(WorkflowProcessDao dao) {
		this.dao = dao;
	}
	
	public NicWorkflowProcess findAllById(long id){
		return null;
	}
	
	public PaginatedResult<NicWorkflowProcess> findAllWorkflowProcess(int pageNo, int pageSize, AssignmentFilterAll assignmentFilter) throws Exception{
		PaginatedResult<NicWorkflowProcess> pageResult = new PaginatedResult<NicWorkflowProcess>();
		
		pageResult = dao.findAllWorkflowProcess(pageNo, pageSize, assignmentFilter);
		return pageResult;
	}
	
	/* update payment matrix */
	//public boolean updatePaymentMatrix(PaymentDef paymentDef, String userId, String workstationId);
	
	/* update payment matrix delete flag */
	public boolean createWorkflowProcess(NicWorkflowProcess workflow){
			try {
				return dao.createWorkflowProcess(workflow);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
	
	public boolean editWorkflowProcess(NicWorkflowProcess workflow){
		try {
			return dao.editWorkflowProcess(workflow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
}
/*	@Autowired
	public ListHandoverServiceImpl(ListHandoverDao dao) {
		this.dao = dao;
	}*/
	
	public List<NicWorkflowProcess> findWorkflowProcessByCriteria(String workflowS){
		try {
			return dao.findWorkflowProcessByCriteria(workflowS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<NicWorkflowProcess> findWorkflowProcessByCriteriaEnd(String workflowE){
		try {
			return dao.findWorkflowProcessByCriteriaEnd(workflowE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public NicWorkflowProcess findWorkflowProcessByCriteria(String workflowS, String result){
		try {
			return dao.findWorkflowProcessByCriteria(workflowS, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}