package com.nec.asia.nic.comp.trans.service;

import java.util.List;
import java.util.Map;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface EppPersonService extends BusinessService<EppPerson, Long> {
	
	public List<EppPerson> findAllEppPerson(String searchName,String idNumber,String passportNo);
	
	public BaseModelSingle<Boolean> saveOrUpdateData(EppPerson detail);
	
	public List<EppPersonAttchmnt> findAllEppPersonAttchmnt(String[] type, String serialNo);
	
	public List<EppPersonAttchmnt> findAllEppPersonAttchmnt1(String[] type, Long personId);
	
	public List<EppPersonFamily> findAllEppPersonFamily(String searchName);
	
	public BaseModelList<EppPersonFamily> findAllEppPersonFamily1(Long personId);
	
	public BaseModelSingle<Boolean> saveOrUpdateDataFamily(EppPersonFamily detail);
	
	public Boolean saveOrUpdateDataAttchmnt(EppPersonAttchmnt detail);
	

	public List<EppPerson> findAllEppPerson2(String searchName, String gender, String dateOfBirth, String placeOfBirthId);
	public EppPerson getPersonById(Long id);
	
	public PaginatedResult<NicUploadJobDto> findAllEppPerson3(String searchName, String gender, String dateOfBirth, String placeOfBirthId, String nin, String type, int pageNo, int pageSize);

	public List<EppPerson> findByGlobalId(Long id);
	
	public BaseModelSingle<EppPerson> findByStringCode(String code);
	
	public void deletePerson(EppPerson epp);
	public void deletePersonAttach(EppPersonAttchmnt eppAtt);
	public void deletePersonFamily(EppPersonFamily eppFm);
	public List<EppPerson> findAllByFields (Map<String, Object> fieldsMap) throws Exception;
	
	public List<EppPerson> findAllByFields (String nin) throws Exception;

	public EppPerson findPersonByPersonCode(String personCode) throws Exception;

	public List<EppPerson> findListPersonByOrgPersonCode(String personCode)throws Exception;

	public Long saveEppPerson(EppPerson epp)throws Exception;
	
}
