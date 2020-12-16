package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.TemplatePassportImgDao;
import com.nec.asia.nic.comp.trans.domain.TemplatePassportImage;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
@Repository("templatePassportImgDao")
public class TemplatePassportImgDaoImpl extends GenericHibernateDao<TemplatePassportImage, Long> implements TemplatePassportImgDao{

	@Override
	public Integer saveNewImgTemplatePassport(TemplatePassportImage passportImage) {
		try {
			this.saveOrUpdate(passportImage);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public List<TemplatePassportImage>  findListTemplateIMGByTemplatePassportId(String templatePassportId, String docType) {
		
		try {
			DetachedCriteria detachedCriteria =DetachedCriteria.forClass(getPersistentClass());
			if (StringUtils.isNotEmpty(templatePassportId)) {
				detachedCriteria.add(Restrictions.eq("templatePassportId", Long.parseLong(templatePassportId)));
				detachedCriteria.add(Restrictions.eq("docType", docType));
			}
			List<TemplatePassportImage> list =  this.findAll(detachedCriteria);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<TemplatePassportImage>();
	}

	@Override
	public int updateTemplatePassportImg(byte[] docdata, String id, String docType) throws DaoException {
		Session session = null;
		int updateCount = 0;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("updateTemplatePassportImg");
			query.setParameter("docdata", docdata);
			query.setString("id", id);
			query.setString("docType", docType);
			updateCount = query.executeUpdate();
		} catch (HibernateException ex) {
			throw new DaoException(ex);
		}finally {
			session.close();
		}
		return updateCount;
	}

}
