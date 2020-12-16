package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryResult;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryDao;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryResultDao;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository
public class NicImmiHistoryResultDaoImpl extends GenericHibernateDao<ImmiHistoryResult, Long> implements NicImmiHistoryResultDao{

	@Override
	public List<ImmiHistoryResult> resultCountXNCByNameBorderGate(String code,
			Integer systemResult, Integer superVisorResult, Integer adResult) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			if(StringUtils.isNotEmpty(code)){
				detachedCriteria.add(Restrictions.eq("gateUserId", code));
			}
			if(systemResult != null){
				if(systemResult == 1){
					detachedCriteria.add(Restrictions.eq("systemResult", true));
				}else{
					detachedCriteria.add(Restrictions.eq("systemResult", false));
					if(superVisorResult != null){
						if(superVisorResult == 1){
							detachedCriteria.add(Restrictions.eq("superviorResult", true));
						}else{
							detachedCriteria.add(Restrictions.eq("superviorResult", false));
							if(adResult != null){
								if(adResult == 1){
									detachedCriteria.add(Restrictions.eq("adResult", true));
								}else{
									detachedCriteria.add(Restrictions.eq("adResult", false));
								}
							}
						}
					}
				}
			}
			List<ImmiHistoryResult> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public ImmiHistoryResult getByImmiId(Long id) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			if (id > 0){
				detachedCriteria.add(Restrictions.eq("immiId", id));
			List<ImmiHistoryResult> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<ImmiHistoryResult> getListByImmiId(Long[] id, Boolean error) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			if (id != null){
				detachedCriteria.add(Restrictions.in("immiId", id));
			if(error){
				detachedCriteria.add(Restrictions.eq("adResult", 0));
			}	
			else{
				Criterion stage1 = Restrictions.eq("systemResult", 1);
				Criterion stage2 = Restrictions.eq("superviorResult", 1);
				Criterion stage3 = Restrictions.eq("adResult", 1);
				detachedCriteria.add(Restrictions.or(stage1, stage2, stage3));
			}
				
			List<ImmiHistoryResult> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
}
