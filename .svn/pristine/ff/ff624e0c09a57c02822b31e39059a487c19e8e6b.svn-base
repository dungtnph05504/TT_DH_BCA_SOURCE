package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.NicAfisDataDao;
import com.nec.asia.nic.comp.trans.dao.NicBlacklistDao;
import com.nec.asia.nic.comp.trans.dao.NicIdentificationAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicIdentificationDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppIdentification;
import com.nec.asia.nic.comp.trans.domain.EppIdentificationAttachmnt;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicBlacklistService;
import com.nec.asia.nic.comp.trans.service.NicIdentificationService;
import com.nec.asia.nic.comp.trans.service.exception.NicAfisDataServiceException;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
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
 
@Service("nicIdentificationService")
public class NicIdentificationSerivceImpl 
					extends DefaultBusinessServiceImpl<EppIdentification, Long, NicIdentificationDao>
					implements NicIdentificationService {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	private NicIdentificationAttachmentDao identifyAttachmentDao;
	
	public NicIdentificationSerivceImpl(){
		
	}

	@Autowired
	public NicIdentificationSerivceImpl(NicIdentificationDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<EppIdentification> findByNin(String nin) {
		try {
			return this.dao.findByNin(nin);
		}catch (Exception ex) {
			
		}
		return null;
	}
	
	@Override
	public List<EppIdentificationAttachmnt> findAttachmentById(Long id) {
		try {
			return identifyAttachmentDao.findAttachmentById(id);
		}catch (Exception ex) {
			
		}
		return null;
	}

	@Override
	public void cleanOldData(List<EppIdentification> hitByCriterion) throws Exception{
		try {
			this.dao.cleanOldData(hitByCriterion);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - cleanOldData thất bại.");
		}
		
	}

	@Override
	public void saveIdentification(EppIdentification eppIdentification)
			throws Exception {
		this.dao.saveIdentification(eppIdentification);
		
	}

	@Override
	public List<EppIdentification> findByTranId(String transactionId)
			throws Exception {
		List<EppIdentification> list = null;
		try {
			list = this.dao.findByTranId(transactionId);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - findByTranId thất bại.");
		}
		return list;
	}
}
