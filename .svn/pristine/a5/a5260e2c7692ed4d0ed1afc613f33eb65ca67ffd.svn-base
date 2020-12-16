package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppArchive;
import com.nec.asia.nic.framework.service.BusinessService;

public interface EppArchiveService extends BusinessService<EppArchive, Long> {
	public BaseModelSingle<Boolean> deleteAllEppArchiveByArchiveCode(String archiveCode);
	public BaseModelSingle<Boolean> saveListArchive(List<EppArchive> listArchive);
	public EppArchive findByTransactionId(String transactionId) throws Exception;
	public void saveOrUpdateArchive(EppArchive eppArchive)throws Exception;
	public EppArchive findByTranIdAndArchiveCode(String transactionId,
			String archiveCode) throws Exception;
}
