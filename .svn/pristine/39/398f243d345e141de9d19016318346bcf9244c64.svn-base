package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicTransactionPackageDao;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;


@Repository
public class NicTransactionPackageDaoImpl extends GenericHibernateDao<NicTransactionPackage, Long> implements NicTransactionPackageDao{

	@Override
	public List<NicTransactionPackage> getListPackageByTransactionId(
			String tranId) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(getPersistentClass());
			dCriteria.add(Restrictions.eq("transactionId", tranId));
			return this.findAll(dCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return new ArrayList<NicTransactionPackage>();
	}

	@Override
	public boolean insertDataTable(NicTransactionPackage tran) {
		boolean check = false;
		try {
			//tran.setId(Long.valueOf(1));
			super.saveOrUpdate(tran);
			check = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return check;
	}

	@Override
	public List<NicTransactionPackage> getListPackageByPackageId(
			String packageId) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(getPersistentClass());
			dCriteria.add(Restrictions.eq("packageId", packageId));
			return this.findAll(dCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return new ArrayList<NicTransactionPackage>();
	}

	@Override
	public NicTransactionPackage getListPackageByPackageIdAndTranID(
			String packageId, String tranID) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(getPersistentClass());
			Criterion test1 = Restrictions.eq("packageId", packageId);
			Criterion test2 = Restrictions.eq("transactionId", tranID);
			dCriteria.add(Restrictions.and(test1, test2));
			List<NicTransactionPackage> list = this.findAll(dCriteria);
			if(list != null && list.size() > 0)
				return list.get(0);	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public NicTransactionPackage getListPackageByPackageIdAndStage(
			String[] stage, String tranID) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(getPersistentClass());
			Criterion test1 = Restrictions.in("stage", stage);
			Criterion test2 = Restrictions.eq("transactionId", tranID);
			dCriteria.add(Restrictions.and(test1, test2));
			List<NicTransactionPackage> list = this.findAll(dCriteria);
			if(list != null && list.size() > 0)
				return list.get(0);	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public NicTransactionPackage findTransactionByIdOrType(String transId,
			int type) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(getPersistentClass());
			Criterion test1 = Restrictions.eq("typeList", type);
			Criterion test2 = Restrictions.eq("transactionId", transId);
			dCriteria.add(Restrictions.and(test1, test2));
			List<NicTransactionPackage> list = this.findAll(dCriteria);
			if(list != null && list.size() > 0)
				return list.get(0);	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

}
