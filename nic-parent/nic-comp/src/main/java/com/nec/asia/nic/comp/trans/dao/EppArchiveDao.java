package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppArchive;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppArchiveDao extends GenericDao<EppArchive, Long> {
	public Boolean deleteAllEppArchiveByArchiveCode(String archiveCode);
	public Boolean saveListArchive(List<EppArchive> listArchive);
	public List<EppArchive> findByTransactionId(String transactionId) throws Exception;
	public void saveOrUpdateArchive(EppArchive eppArchive) throws Exception;
	public EppArchive findByTranIdAndArchiveCode(String transactionId,
			String archiveCode)throws Exception;
}
