package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.EppArchiveDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppArchive;
import com.nec.asia.nic.comp.trans.service.EppArchiveService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("eppArchiveService")
public class EppArchiveServiceImpl extends DefaultBusinessServiceImpl<EppArchive, Long, EppArchiveDao> implements EppArchiveService{

	@Autowired
	private EppArchiveDao dao;
	
	@Override
	public BaseModelSingle<Boolean> deleteAllEppArchiveByArchiveCode(
			String archiveCode) {
		BaseModelSingle<Boolean> base = new BaseModelSingle<Boolean>(false, true, null);
		try {
			this.dao.deleteAllEppArchiveByArchiveCode(archiveCode);
			base.setModel(true);
		} catch (Exception e) {
			base.setError(false);
			base.setMessage(CreateMessageException.createMessageException(e) + " - deleteAllEppArchiveByArchiveCode thất bại.");
		}
		return base;
	}

	@Override
	public BaseModelSingle<Boolean> saveListArchive(List<EppArchive> listArchive) {
		BaseModelSingle<Boolean> base = new BaseModelSingle<Boolean>(false, true, null);
		try {
			this.dao.saveListArchive(listArchive);
			base.setModel(true);
		} catch (Exception e) {
			base.setError(false);
			base.setMessage(CreateMessageException.createMessageException(e) + " - deleteAllEppArchiveByArchiveCode thất bại.");
		}
		return base;
	}

	@Override
	public EppArchive findByTransactionId(String transactionId) throws Exception {
		EppArchive eppArchive = null;
		try {
			List<EppArchive> listArchive = this.dao.findByTransactionId(transactionId);
			if (listArchive != null && listArchive.size() > 0) {
				eppArchive = listArchive.get(0);
			}
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - findByTransactionId thất bại.");
		}
		return eppArchive;
	}

	@Override
	public void saveOrUpdateArchive(EppArchive eppArchive) throws Exception {
		try {
			this.dao.saveOrUpdateArchive(eppArchive);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - saveOrUpdateArchive thất bại.");
		}
	}

	@Override
	public EppArchive findByTranIdAndArchiveCode(String transactionId,
			String archiveCode) throws Exception {
		EppArchive rs = null;
		try {
			rs = this.dao.findByTranIdAndArchiveCode(transactionId, archiveCode);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - findByTranIdAndArchiveCode thất bại.");
		}
		return rs;
	}

}
