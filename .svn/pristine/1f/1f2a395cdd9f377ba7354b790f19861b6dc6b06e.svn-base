package com.nec.asia.nic.comp.trans.dao;

import java.util.List;
import java.util.Map;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppPersonDao extends GenericDao<EppPerson, Long>{
	
	public List<EppPerson> findAllEppPerson(String searchName, String idNumber, String passportNo);
	
	public BaseModelSingle<EppPerson> findByStringCode(String code);
	
	public BaseModelSingle<Boolean> saveOrUpdateData(EppPerson detail);
	
	public List<EppPerson> findAllEppPerson2(String searchName, String gender, String dateOfBirth, String placeOfBirthId);
	
	public PaginatedResult<EppPerson> findAllEppPerson3(String searchName, String gender, String dateOfBirth, String placeOfBirthId, String nin, String type,int pageNo, int pageSize);
	public PaginatedResult<NicUploadJobDto> findAllEppPerson4(String searchName, String gender, String dateOfBirth, String placeOfBirthId, String nin, String type,int pageNo, int pageSize);

	public List<EppPerson> findByGlobalId(Long globalId);
	
	public List<EppPerson> findAllByFields (Map<String,Object> fields) throws Exception;
	
	public List<EppPerson> findAllByNin(String nin);

	public List<EppPerson> findPersonByPersonCode(String personCode) throws Exception;

	public List<EppPerson> findListPersonByOrgPersonCode(String[] orgPerson)throws Exception;
	
	public PaginatedResult<NicUploadJobDto> getResultPersonInStore(String searchName, String gender, String dateOfBirth, String placeOfBirthId, String nin, String type, int pageNo, int pageSize)throws Exception;

	public Long saveEppPerson(EppPerson epp) throws Exception;
}
