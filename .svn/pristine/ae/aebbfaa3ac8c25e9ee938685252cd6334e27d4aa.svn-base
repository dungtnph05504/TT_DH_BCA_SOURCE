package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.TempTranPackageDao;
import com.nec.asia.nic.comp.trans.domain.TempTransactionPackage;
import com.nec.asia.nic.comp.trans.service.TempTranPackageService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;


@Service("tempTranPackageService")
public class TempTranPackageServiceImpl extends DefaultBusinessServiceImpl<TempTransactionPackage, Long, TempTranPackageDao> implements TempTranPackageService{

	@Autowired
	TempTranPackageDao dao;
	
	@Override
	public List<TempTransactionPackage> findTempByPackageId(String packageId,
			Integer type) {
		
		return dao.findTempByPackageId(packageId, type);
	}

	@Override
	public TempTransactionPackage findTempByPackageIdOrTranId(String packageId,
			String transactionId, Integer type) {
		// TODO Auto-generated method stub
		return dao.findTempByPackageIdOrTranId(packageId, transactionId, type);
	}

	@Override
	public void saveOrUpdateTemp(TempTransactionPackage tempTran) {
		try {
			TempTransactionPackage temp = dao.findTempByPackageIdOrTranId(tempTran.getPackageId(), tempTran.getTransactionId(), null);
			if(temp == null){
				dao.saveOrUpdate(tempTran);
			}else{
				tempTran.setId(temp.getId());
				dao.saveOrUpdate(tempTran);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
