package com.nec.asia.nic.comp.queuesJobSchedule.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.decisionManager.dao.BusinessListDao;
import com.nec.asia.nic.comp.decisionManager.dao.DecisionManagerDao;
import com.nec.asia.nic.comp.decisionManager.service.BusinessListService;
import com.nec.asia.nic.comp.decisionManager.service.DecisionManagerService;
import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.queuesJobSchedule.dao.LogJobScheduleDao;
import com.nec.asia.nic.comp.queuesJobSchedule.service.LogJobScheduleService;
import com.nec.asia.nic.comp.signerGovs.dao.SignerGovsDao;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.signerGovs.service.SignerGovsService;
import com.nec.asia.nic.comp.trans.domain.LogJobSchedule;
import com.nec.asia.nic.comp.trans.domain.NicBusinessList;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
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

@Service("logJobScheduleService")
public class LogJobScheduleImpl extends
		DefaultBusinessServiceImpl<LogJobSchedule, Long, LogJobScheduleDao>
		implements LogJobScheduleService {

	/** must define the non-argument constructor because we use CGLib proxy */
	public LogJobScheduleImpl() {
	}

	@Autowired
	public LogJobScheduleImpl(LogJobScheduleDao dao) {
		this.dao = dao;
	}
	@Override
	public PaginatedResult<LogJobSchedule> listLogJobScheduleAllBySearch(Long recordID, String code, int pageNo, int pageSize) throws Exception{
		PaginatedResult<LogJobSchedule> pageResult = new PaginatedResult<LogJobSchedule>();
		
		pageResult = dao.listLogJobScheduleAllBySearch(recordID, code, pageNo, pageSize);
		return pageResult;
	}
	@Override
	public boolean createLogJobSchedule(LogJobSchedule obj){
		
		try {
			return dao.createLogJobSchedule(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public List<LogJobSchedule> findListByCode(String code) {
		List<LogJobSchedule> result = new ArrayList<LogJobSchedule>();
		
		try {
			return dao.findByCode(code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	@Override
	public List<LogJobSchedule> findByRecordID(Long recordID) {
		List<LogJobSchedule> result = new ArrayList<LogJobSchedule>();
		
		try {
			return dao.findByRecordId(recordID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public PaginatedResult<LogJobSchedule> findListByCriteria(int pageNo,
			int pageSize, AssignmentFilterAll assignmentFilter) {
		PaginatedResult<LogJobSchedule> pageResult = new PaginatedResult<LogJobSchedule>();
		
		pageResult = dao.findListByCriteria(pageNo, pageSize, assignmentFilter);
		return pageResult;
	}

	@Override
	public PaginatedResult<LogJobSchedule> findListByCode1(String code,
			int pageNo, int pageSize) {
		List<LogJobSchedule> result = new ArrayList<LogJobSchedule>();
		
		try {
			return dao.findByCode1(code, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new PaginatedResult<>(0, 1, result);
	}
}