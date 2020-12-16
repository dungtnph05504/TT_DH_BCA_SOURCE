package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.NicAfisDataDao;
import com.nec.asia.nic.comp.trans.dao.NicBlacklistAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicBlacklistDao;
import com.nec.asia.nic.comp.trans.dao.NicIdentificationAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppBlacklistAttachment;
import com.nec.asia.nic.comp.trans.domain.EppIdentificationAttachmnt;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicBlacklistService;
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
 
@Service("nicBlacklistService")
public class NicBlacklistServiceImpl 
					extends DefaultBusinessServiceImpl<EppBlacklist, Long, NicBlacklistDao>
					implements NicBlacklistService {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NicBlacklistAttachmentDao blacklistAttachmentDao;
	
	public NicBlacklistServiceImpl(){
		
	}

	@Autowired
	public NicBlacklistServiceImpl(NicBlacklistDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<EppBlacklist> findByNin(String nin) {
		try {
			return this.dao.findByNin(nin);
		}catch (Exception ex) {
			
		}
		return null;
	}
	
	@Override
	public List<EppBlacklist> findByInfoPerson(String fullname, String dob) {
		try {
			return this.dao.findByInfoPerson(fullname, dob);
		}catch (Exception ex) {
			
		}
		return null;
	}

	@Override
	public List<EppBlacklistAttachment> findAttachmentById(Long id) {
		try {
			return blacklistAttachmentDao.findAttachmentById(id);
		}catch (Exception ex) {
			
		}
		return null;
	}
	
	@Override
	public List<EppBlacklist> findByMutilCriterion(String fullname, String dob, String nin) {
		try {
			return this.dao.findByMutilCriterion(fullname, dob, nin);
		}catch (Exception ex) {
			
		}
		return null;
	}
}
