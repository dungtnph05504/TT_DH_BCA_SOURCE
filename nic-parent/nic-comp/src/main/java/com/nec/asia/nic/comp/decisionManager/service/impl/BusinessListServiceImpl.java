package com.nec.asia.nic.comp.decisionManager.service.impl;

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
import com.nec.asia.nic.comp.signerGovs.dao.SignerGovsDao;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.signerGovs.service.SignerGovsService;
import com.nec.asia.nic.comp.trans.domain.NicBusinessList;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
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

@Service("businessListService")
public class BusinessListServiceImpl extends
		DefaultBusinessServiceImpl<NicBusinessList, Long, BusinessListDao>
		implements BusinessListService {
	@Autowired
	private ParametersDao parametersDao;
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public BusinessListServiceImpl() {
	}

	@Autowired
	public BusinessListServiceImpl(BusinessListDao dao) {
		this.dao = dao;
	}

	public PaginatedResult<NicBusinessList> listBusinessListAllBySearch(String search, int pageNo, int pageSize) throws Exception{
		PaginatedResult<NicBusinessList> pageResult = new PaginatedResult<NicBusinessList>();
		
		pageResult = dao.listBusinessListAllBySearch(search, pageNo, pageSize);
		return pageResult;
	}
	
	public boolean createBusinessList(NicBusinessList businessList){
		
		try {
			return dao.createBusinessList(businessList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean editBusinessList(NicBusinessList businessList){
		try {
			return dao.editBusinessList(businessList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public NicBusinessList findBySerial(int serial, String decisionNumber) {
		NicBusinessList result = new NicBusinessList();
		
		try {
			return dao.findBySerial(serial, decisionNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<NicBusinessList> findListByDecisionNumber(String decisionNumber) {
		List<NicBusinessList> result = new ArrayList<NicBusinessList>();
		
		try {
			return dao.findListByDecisionNumber(decisionNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public PaginatedResult<NicBusinessList> findListByDecisionNumber1(
			String decisionNumber, int pageNo, int pageSize) {
		List<NicBusinessList> result = new ArrayList<NicBusinessList>();
		
		try {
			return dao.findListByDecisionNumber1(decisionNumber, pageNo, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new PaginatedResult<>(0, 1, result);
	}
}