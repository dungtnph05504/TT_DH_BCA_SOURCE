package com.nec.asia.nic.comp.legacy.service;

import java.util.List;

import javax.jws.WebService;

import com.nec.asia.nic.comp.legacy.domain.NicTabCitizens;
import com.nec.asia.nic.comp.legacy.service.exception.NicTabCitizensServiceException;
import com.nec.asia.nic.framework.service.BusinessService;


/**
 * The interface to integrate with Legacy Nic Transaction Database.
 * 
 * @author chris_wong
 *
 */
public interface NicTabCitizensService extends BusinessService<NicTabCitizens, Long> {
	
	
	public List<NicTabCitizens> fillAllByFields(String nin, String surname, String firstName, String surnameAtBirth, String sex, String dob) throws NicTabCitizensServiceException;
}
