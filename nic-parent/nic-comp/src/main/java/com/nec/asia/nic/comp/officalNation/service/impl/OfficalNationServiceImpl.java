package com.nec.asia.nic.comp.officalNation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.decisionManager.dao.DecisionManagerDao;
import com.nec.asia.nic.comp.decisionManager.service.DecisionManagerService;
import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.officalNation.dao.OfficalNationDao;
import com.nec.asia.nic.comp.officalNation.service.OfficalNationService;
import com.nec.asia.nic.comp.signerGovs.dao.SignerGovsDao;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.signerGovs.service.SignerGovsService;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicOfficalNation;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
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

@Service("officalNationService")
public class OfficalNationServiceImpl extends
		DefaultBusinessServiceImpl<NicOfficalNation, Long, OfficalNationDao>
		implements OfficalNationService {
	@Autowired
	private ParametersDao parametersDao;
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public OfficalNationServiceImpl() {
	}

	@Autowired
	public OfficalNationServiceImpl(OfficalNationDao dao) {
		this.dao = dao;
	}

	public PaginatedResult<NicOfficalNation> findAllOfficalNation(String search, int pageNo, int pageSize, AssignmentFilterAll assignmentFilter) throws Exception{
		PaginatedResult<NicOfficalNation> pageResult = new PaginatedResult<NicOfficalNation>();
		
		pageResult = dao.listOfficalNationAllBySearch(search, pageNo, pageSize, assignmentFilter);
		return pageResult;
	}
	
	public boolean createOfficalNation(NicOfficalNation officalNation){
		
		try {
			return dao.createOfficalNation(officalNation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean editOfficalNation(NicOfficalNation officalNation){
		try {
			return dao.editOfficalNation(officalNation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public NicOfficalNation findOfficalNationByCode(String officalNationNo) {
		NicOfficalNation result = new NicOfficalNation();
		
		try {
			return dao.findByOfficalNationNo(officalNationNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}