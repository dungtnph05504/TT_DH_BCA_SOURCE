package com.nec.asia.nic.comp.trans.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.NicAfisDataDao;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.exception.NicAfisDataServiceException;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * @author JohnPaul_Millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Mar 4, 2014
 * 
 */
 
 
/*
 * Modification History:
 * 
 * 13 Mar 2014 (chris): add the non-argument constructor
 * 22 Sep 2014 (chris): add method findAfisRefIdByTransactionId()
 * 11 Jan 2016 (chris): add method getNextAfisKeyNo()
 */
 
@Service("nicAfisDataService")
public class NicAfisDataServiceImpl extends DefaultBusinessServiceImpl<NicAfisData, String, NicAfisDataDao> 
									implements NicAfisDataService {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ParametersDao parametersDao;

	private static final String PARAM_SCOPE_AFIS = "AFIS";
	private static final String PARAM_NAME_KEYNO_PREFIX = "KEYNO_PREFIX";
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public NicAfisDataServiceImpl(){
		
	}

	@Autowired
	public NicAfisDataServiceImpl(NicAfisDataDao dao) {
		this.dao = dao;
	}
	

	@Override
	public NicAfisData findByTransactionId(String transId) throws NicAfisDataServiceException{
		try {
			return dao.findById(transId);
		}catch (Exception dao) {
			throw new NicAfisDataServiceException(dao);
		}
	}

	@Override
	public void saveAfisRefId(NicAfisData e) throws NicAfisDataServiceException {
		try {
			this.dao.saveOrUpdate(e);
		}catch (Exception ex) {
			throw new NicAfisDataServiceException(ex);
		}
		
	}
	
	@Override
	public String findAfisRefIdByTransactionId(String transactionId) throws NicAfisDataServiceException {
		String afisRefId = null;
		try {
			afisRefId = this.dao.findReferenceAfisId(transactionId);
		}catch (Exception ex) {
			throw new NicAfisDataServiceException(ex);
		}
		return afisRefId;
	}

	@Override
	public String getNextAfisKeyNo() throws NicAfisDataServiceException  {
		String afisKeyNo = null;
		String prefix = "1";
		try {
			ParametersId id = new ParametersId(PARAM_SCOPE_AFIS, PARAM_NAME_KEYNO_PREFIX);
			Parameters parameters = parametersDao.findById(id);
			if (parameters!=null && StringUtils.isNotEmpty(parameters.getParaShortValue())) {
				prefix = parameters.getParaShortValue();
			}
			afisKeyNo = this.dao.getNextAfisKeyNo(prefix);
		}catch (Exception ex) {
			throw new NicAfisDataServiceException(ex);
		}
		return afisKeyNo;
	}
}
