package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionPackageDao;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;


@Service("nicTransactionPackageService")
public class NicTransactionPackageServiceImpl 
	extends DefaultBusinessServiceImpl<NicTransactionPackage, Long, NicTransactionPackageDao>
	implements NicTransactionPackageService{

	@Autowired
	private NicTransactionPackageDao dao;
	
	@Override
	public boolean insertDataTable(NicTransactionPackage tran) {
		try {
			return this.dao.insertDataTable(tran);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public List<NicTransactionPackage> getPackageNameByTransactionId(
			String tranId) {
		try {
			List<NicTransactionPackage> listPackage = this.dao.getListPackageByTransactionId(tranId);
			return listPackage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<NicTransactionPackage>();
	}

	@Override
	public List<NicTransactionPackage> getListPackageByPackageId(
			String packageId) {
		try {
			List<NicTransactionPackage> listPackage = this.dao.getListPackageByPackageId(packageId);
			return listPackage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<NicTransactionPackage>();
	}

	@Override
	public NicTransactionPackage getListPackageByPackageIdAndTranID(
			String packageId, String tranID) {
		try {
			NicTransactionPackage listPackage = this.dao.getListPackageByPackageIdAndTranID(packageId, tranID);
			return listPackage;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public NicTransactionPackage getListPackageByPackageIdAndStage(
			String[] stage, String tranID) {
		try {
			NicTransactionPackage listPackage = this.dao.getListPackageByPackageIdAndStage(stage, tranID);
			return listPackage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public NicTransactionPackage findTransactionByIdOrType(String transId,
			int type) {
		try {
			NicTransactionPackage listPackage = this.dao.findTransactionByIdOrType(transId, type);
			return listPackage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean saveTranPackage(NicTransactionPackage tran) throws Exception {
		try {
			this.dao.saveOrUpdate(tran);
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	

}
