package com.nec.asia.nic.comp.signerGovs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.signerGovs.dao.SignerGovsDao;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.signerGovs.service.SignerGovsService;
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

@Service("signerGovsService")
public class SignerGovsServiceImpl extends
		DefaultBusinessServiceImpl<SignerGovs, Long, SignerGovsDao>
		implements SignerGovsService {
	@Autowired
	private ParametersDao parametersDao;
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public SignerGovsServiceImpl() {
	}

	@Autowired
	public SignerGovsServiceImpl(SignerGovsDao dao) {
		this.dao = dao;
	}

	public PaginatedResult<SignerGovs> findAllSignerGovs(String search, int pageNo, int pageSize) throws Exception{
		PaginatedResult<SignerGovs> pageResult = new PaginatedResult<SignerGovs>();
		
		pageResult = dao.listSignerGovsAllBySearch(search, pageNo, pageSize);
		return pageResult;
	}
	
	public boolean createSignerGovs(SignerGovs signerGov){
		
		try {
			return dao.createSignerGovs(signerGov);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean editSignerGovs(SignerGovs signerGov){
		try {
			return dao.editSignerGovs(signerGov);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public SignerGovs findSignerByCode(String code) {
		SignerGovs result = new SignerGovs();
		
		try {
			return dao.findByCode(code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<SignerGovs> findListSignerByCode(String code) {
		List<SignerGovs> result = new ArrayList<SignerGovs>();
		
		try {
			return dao.findListByCode(code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}