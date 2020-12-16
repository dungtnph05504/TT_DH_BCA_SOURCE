package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.EppIssuanceDao;
import com.nec.asia.nic.comp.trans.domain.EppIssuance;
import com.nec.asia.nic.comp.trans.service.EppIssuanceService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("eppIssuanceService")
public class EppIssuanceServiceImpl extends
		DefaultBusinessServiceImpl<EppIssuance, Long, EppIssuanceDao> implements EppIssuanceService {
	
	@Autowired
	private EppIssuanceDao dao;
	
	@Override
	public void saveOrUpdateEppIssuance(EppIssuance eppIssuance)
			throws Exception {
		try {
			this.dao.saveOrUpdateEppIssuance(eppIssuance);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - saveOrUpdateEppIssuance thất bại.");
		}
	}

	@Override
	public EppIssuance findByTranId(String transactionId) throws Exception {
		EppIssuance eppIssuance = null;
		try {
			List<EppIssuance> list = this.dao.findAllByTranId(transactionId);
			if (list!= null && list.size() > 0) {
				eppIssuance = list.get(0);
			}
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - findByTranId: " + transactionId + " thất bại.");
		}
		return eppIssuance;
	}

	@Override
	public Long saveEppIssuance(EppIssuance eppIssuance) throws Exception {
		try {
			return this.dao.saveEppIssuance(eppIssuance);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - saveEppIssuance thất bại.");
		}
	}

}
