package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.TempTranPackageDao;
import com.nec.asia.nic.comp.trans.domain.TempTransactionPackage;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;


@Repository
public class TempTranPackageDaoImpl extends GenericHibernateDao<TempTransactionPackage, Long> implements TempTranPackageDao{

	@Override
	public List<TempTransactionPackage> findTempByPackageId(String packageId,
			Integer type) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TempTransactionPackage.class);
			if(StringUtils.isNotEmpty(packageId)){
				detachedCriteria.add(Restrictions.eq("packageId", packageId));
			}
			if(type != null){
				detachedCriteria.add(Restrictions.eq("typeList", type));
			}
			List<TempTransactionPackage> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TempTransactionPackage findTempByPackageIdOrTranId(String packageId,
			String transactionId, Integer type) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TempTransactionPackage.class);
			if(StringUtils.isNotEmpty(packageId)){
				detachedCriteria.add(Restrictions.eq("packageId", packageId));
			}
			if(StringUtils.isNotEmpty(transactionId)){
				detachedCriteria.add(Restrictions.eq("transactionId", transactionId));
			}
			if(type != null){
				detachedCriteria.add(Restrictions.eq("typeList", type));
			}
			List<TempTransactionPackage> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
