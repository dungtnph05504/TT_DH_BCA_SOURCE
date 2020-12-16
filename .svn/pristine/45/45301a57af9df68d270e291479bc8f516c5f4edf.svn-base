package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.framework.service.BusinessService;

public interface NicTransactionPackageService extends BusinessService<NicTransactionPackage, Long> {
	public boolean insertDataTable(NicTransactionPackage tran);
	public boolean saveTranPackage(NicTransactionPackage tran) throws Exception;
	public List<NicTransactionPackage> getPackageNameByTransactionId(String tranId);
	public List<NicTransactionPackage> getListPackageByPackageId(String packageId);
	public NicTransactionPackage getListPackageByPackageIdAndTranID(String packageId, String tranID);
	public NicTransactionPackage getListPackageByPackageIdAndStage(String[] stage, String tranID);
	
	public NicTransactionPackage findTransactionByIdOrType(String transId, int type);
}