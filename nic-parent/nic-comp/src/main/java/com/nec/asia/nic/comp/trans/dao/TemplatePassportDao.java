package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.TemplatePassport;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface TemplatePassportDao extends GenericDao<TemplatePassport, Long> {

	PaginatedResult<TemplatePassport> findListPassport(String nation,
			String passportType, int yearIssue, int pageNo, int pageSize);

	Integer saveNewPassport(TemplatePassport templatePassport);

	String getLastIdPassport();

	PaginatedResult<TemplatePassport> findAllListPassport(int pageNo,
			int pageSize);

	List<TemplatePassport> findListByTemplatePassportId(
			String templatePassportId);
	
	Integer updatePassport(TemplatePassport passport);
	List<String> getCodeNation();

	List<String> getCodeNationName(String codeValue);


	List<String> getNationName();
}
