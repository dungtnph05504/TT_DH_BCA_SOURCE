package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.TempTransactionPackage;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface TempTranPackageDao extends GenericDao<TempTransactionPackage, Long>{
	List<TempTransactionPackage> findTempByPackageId(String packageId, Integer type);
	TempTransactionPackage findTempByPackageIdOrTranId(String packageId, String transactionId, Integer type);
}
