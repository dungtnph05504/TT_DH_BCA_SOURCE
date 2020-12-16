package com.nec.asia.nic.comp.legacy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.legacy.dao.NicTabCitizensDao;
import com.nec.asia.nic.comp.legacy.domain.NicTabCitizens;
import com.nec.asia.nic.comp.legacy.service.NicTabCitizensService;
import com.nec.asia.nic.comp.legacy.service.exception.NicTabCitizensServiceException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("nicTabCitizensService")
@Transactional
public class NicTabCitizensServiceImpl extends
		DefaultBusinessServiceImpl<NicTabCitizens, Long, NicTabCitizensDao>
		implements NicTabCitizensService {

	/** must define the non-argument constructor because we use CGLib proxy */
	public NicTabCitizensServiceImpl() {
	}

	@Autowired
	public NicTabCitizensServiceImpl(NicTabCitizensDao dao) {
		this.dao = dao;
	}

	@Override
	public List<NicTabCitizens> fillAllByFields(String nin, String surname,
			String firstName, String surnameAtBirth, String sex, String dob)
			throws NicTabCitizensServiceException {
		List<NicTabCitizens> resultList = null;
		
		NicTabCitizens searchExample = new NicTabCitizens();
		searchExample.setNicNumber(nin);
		searchExample.setNicSurname(surname);
		searchExample.setNicNames(firstName);
		searchExample.setNicMaidenname(surnameAtBirth);
		searchExample.setNicSex(sex);
		searchExample.setNicDobApprox(dob);
		try {
			resultList = dao.findAll(searchExample);
		} catch (Exception e) {
			throw new NicTabCitizensServiceException(e);
		}
		return resultList;
	}

}
