package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.TempTransactionPackage;
import com.nec.asia.nic.framework.service.BusinessService;

public interface TempTranPackageService extends BusinessService<TempTransactionPackage, Long>{
	List<TempTransactionPackage> findTempByPackageId(String packageId, Integer type);
	TempTransactionPackage findTempByPackageIdOrTranId(String packageId, String transactionId, Integer type);
	void saveOrUpdateTemp(TempTransactionPackage tempTran);
}
