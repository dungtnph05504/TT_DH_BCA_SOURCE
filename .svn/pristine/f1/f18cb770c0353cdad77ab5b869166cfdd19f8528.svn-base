package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.TemplatePassportImage;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface TemplatePassportImgDao extends GenericDao<TemplatePassportImage, Long>{
	Integer saveNewImgTemplatePassport(TemplatePassportImage passportImage);

	List<TemplatePassportImage> findListTemplateIMGByTemplatePassportId(
			String templatePassportId, String docType);


	int updateTemplatePassportImg(byte[] docdata, String id, String docType)
			throws DaoException;


}
