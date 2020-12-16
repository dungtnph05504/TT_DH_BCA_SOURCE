package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface NicTransactionPackageDao extends GenericDao<NicTransactionPackage, Long>{
	List<NicTransactionPackage> getListPackageByTransactionId(String tranId); 
	boolean insertDataTable(NicTransactionPackage tran);
	List<NicTransactionPackage> getListPackageByPackageId(String packageId);
	NicTransactionPackage getListPackageByPackageIdAndTranID(String packageId, String tranID);
	public NicTransactionPackage getListPackageByPackageIdAndStage(String[] stage, String tranID);
	public NicTransactionPackage findTransactionByIdOrType(String transId, int type);
}
