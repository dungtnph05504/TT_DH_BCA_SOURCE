package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.EppPersonAttchmntDao;
import com.nec.asia.nic.comp.trans.dao.EppPersonDao;
import com.nec.asia.nic.comp.trans.dao.EppPersonFamilyDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.HelperClass;

@Service("eppPersonService")
public class EppPersonServiceImpl extends
		DefaultBusinessServiceImpl<EppPerson, Long, EppPersonDao> implements
		EppPersonService {

	@Autowired
	private EppPersonDao dao;
	@Autowired
	private EppPersonAttchmntDao attchmntDao;
	@Autowired
	private EppPersonFamilyDao familyDao;

	@Autowired
	private NicTransactionAttachmentDao attDao;

	@Autowired
	private CodesService codesService;

	@Autowired
	private NicRegistrationDataService registrationService;

	@Override
	public BaseModelSingle<EppPerson> findByStringCode(String code) {
		try {
			return dao.findByStringCode(code);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<EppPerson>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findByStringCode - " + code + " - thất bại.");
		}
	}

	@Override
	public List<EppPerson> findAllEppPerson(String searchName, String idNumber,
			String passportNo) {
		try {
			return dao.findAllEppPerson(searchName, idNumber, passportNo);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public EppPerson getPersonById(Long id) {
		try {
			return dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<EppPersonAttchmnt> findAllEppPersonAttchmnt(String[] type,
			String serialNo) {
		try {
			return attchmntDao.findAllEppPersonAttchmnt(type, serialNo);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<EppPersonFamily> findAllEppPersonFamily(String searchName) {
		try {
			return familyDao.findAllEppPersonFamily(searchName);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateData(EppPerson detail) {
		try {
			return dao.saveOrUpdateData(detail);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException.createMessageException(e)
							+ " - saveOrUpdateData: " + detail.getPersonCode()
							+ " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateDataFamily(
			EppPersonFamily detail) {
		try {
			return familyDao.saveOrUpdateData(detail);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException.createMessageException(e)
							+ " - saveOrUpdateDataFamily: " + detail.getName()
							+ " - thất bại.");
		}
	}

	@Override
	public Boolean saveOrUpdateDataAttchmnt(EppPersonAttchmnt detail) {
		try {
			return attchmntDao.saveOrUpdateData(detail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<EppPerson> findAllEppPerson2(String searchName, String gender,
			String dateOfBirth, String placeOfBirthId) {
		try {
			return dao.findAllEppPerson2(searchName, gender, dateOfBirth,
					placeOfBirthId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllEppPerson3(
			String searchName, String gender, String dateOfBirth,
			String placeOfBirthId, String nin, String type, int pageNo,
			int pageSize) {
		try {
			if (dateOfBirth != null && !dateOfBirth.trim().equals("")) {
				dateOfBirth = HelperClass.convertStringToString2(dateOfBirth
						.trim());
			}
			
			PaginatedResult<NicUploadJobDto> result = dao.getResultPersonInStore(
					searchName, gender, dateOfBirth, placeOfBirthId, nin, type,
					pageNo, pageSize);
			/*PaginatedResult<NicUploadJobDto> result = dao.findAllEppPerson4(
					searchName, gender, dateOfBirth, placeOfBirthId, nin, type,
					pageNo, pageSize);*/
			// PaginatedResult<EppPerson> ls = dao.findAllEppPerson3(searchName,
			// gender, dateOfBirth, placeOfBirthId, nin, type, pageNo,
			// pageSize);
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (result != null) {
				if (result.getRowSize() > 0) {
					list = result.getRows();
					for (NicUploadJobDto dto : list) {
						// NicUploadJobDto dto = new NicUploadJobDto();
						NicRegistrationData reg = registrationService
								.findById(dto.getTransactionId());
						dto.setGender((dto.getGender().equals("M")) ? "Nam" : (dto.getGender().equals("F")
								? "Nữ" : ""));
						if (StringUtils.isNotEmpty(dto.getDob())) {
							String dob = HelperClass.convertStringToStringTk(
									dto.getDob(), 0);
							if (reg != null) {
								dto.setDob(HelperClass.loadDateOfBirth(dob,
										reg.getDefDateOfBirth()));
							}
						}
						String noiSinh = codesService.getCodeValueDescByIdName(
								"DISTRICT", dto.getPlaceOfBirth(), "");
						if (StringUtils.isNotEmpty(noiSinh)) {
							dto.setPlaceOfBirth(noiSinh);
						}
						String noiCap = codesService.getCodeValueDescByIdName(
								"CODE_IDPlace", dto.getAddressNin(), "");
						if (StringUtils.isNotEmpty(noiCap)) {
							dto.setAddressNin(noiCap);
						} else {
							String ncReg = codesService
									.getCodeValueDescByIdName("CODE_IDPlace",
											reg.getAddressNin(), "");
							if (StringUtils.isNotEmpty(ncReg)) {
								dto.setAddressNin(ncReg);
							}
						}
						if (StringUtils.isNotEmpty(dto.getDateNin())) {
							dto.setDateNin(HelperClass.convertStringToStringTk(
									dto.getDateNin(), 0));
						}
						// dto.setDateNin(ep.);
						// listT.add(dto);
					}
					result.setRows(list);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return new PaginatedResult<NicUploadJobDto>(0, pageNo,
				new ArrayList<NicUploadJobDto>());
	}

	@Override
	public List<EppPersonAttchmnt> findAllEppPersonAttchmnt1(String[] type,
			Long personId) {
		try {
			return attchmntDao.findAllEppPersonAttchmnt1(type, personId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public BaseModelList<EppPersonFamily> findAllEppPersonFamily1(Long personId) {
		try {
			return familyDao.findAllEppPersonFamily1(personId);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<EppPersonFamily>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findAllEppPersonFamily1 - " + personId
							+ " - thất bại.");
		}
	}

	@Override
	public List<EppPerson> findByGlobalId(Long id) {
		try {
			return dao.findByGlobalId(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void deletePerson(EppPerson epp) {
		dao.delete(epp);
	}

	@Override
	public void deletePersonAttach(EppPersonAttchmnt eppAtt) {
		attchmntDao.delete(eppAtt);

	}

	@Override
	public void deletePersonFamily(EppPersonFamily eppFm) {
		familyDao.delete(eppFm);
	}

	@Override
	public List<EppPerson> findAllByFields(Map<String, Object> fieldsMap)
			throws Exception {
		return this.dao.findAllByFields(fieldsMap);
	}

	// Thêm hàm check CMND cho phần kiểm tra CPD
	@Override
	public List<EppPerson> findAllByFields(String nin) throws Exception {

		try {
			List<EppPerson> nicTransDBOResultList = new ArrayList<EppPerson>();

			if (StringUtils.isNotBlank(nin)) {
				List<EppPerson> nicTransDBOList = this.dao.findAllByNin(nin);
				if (CollectionUtils.isNotEmpty(nicTransDBOList)) {
					logger.info(
							"[findAllTransactionHistory] findById({}) result.size:{}",
							new Object[] { nin, nicTransDBOList.size() });
					// remove duplicate records
					for (EppPerson temp : nicTransDBOList) {
						boolean isDuplicate = false;
						for (EppPerson nicTransDBO : nicTransDBOResultList) {
							if (StringUtils.equals(nicTransDBO.getPersonCode(),
									temp.getPersonCode())) {
								isDuplicate = true;
								break;
							}
						}
						if (!isDuplicate) {
							nicTransDBOResultList.add(temp);
						}
					}
				}
				return nicTransDBOResultList;
			}

			return null;

			/*
			 * String hql =
			 * "select a.nicId from NicRegistrationData a where a.transactionId = ? "
			 * ;
			 * 
			 * Object[] parameters = { transactionId } ; List<String> resultList
			 * = (List<String>) this.getHibernateTemplate().find(hql,
			 * parameters);
			 * 
			 * 
			 * if (CollectionUtils.isNotEmpty(resultList)) { nicId =
			 * Long.parseLong(resultList.get(0)); }
			 */
			// return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}

	@Override
	public EppPerson findPersonByPersonCode(String personCode) throws Exception {
		EppPerson person = null;
		try {
			List<EppPerson> list = this.dao.findPersonByPersonCode(personCode);
			if (list != null && list.size() > 0) {
				person = list.get(0);
			}
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findPersonByPersonCode thất bại.");
		}
		return person;
	}

	@Override
	public List<EppPerson> findListPersonByOrgPersonCode(String personCode)
			throws Exception {
		List<EppPerson> list = null;
		String[] listOrgPerson = new String[2];
		try {
			EppPerson ps = this
					.findPersonByPersonCode(personCode);
			if (ps != null) {
				listOrgPerson[0] = ps.getPersonCode();
				if (StringUtils.isNotBlank(ps.getOrgPerson())) {
					listOrgPerson[1] = ps.getOrgPerson();
				}
				list = this.dao
						.findListPersonByOrgPersonCode(listOrgPerson);
			}
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findListPersonByOrgPersonCode thất bại.");
		}
		return list;
	}

	@Override
	public Long saveEppPerson(EppPerson epp) throws Exception {
		try {
			return this.dao.saveEppPerson(epp);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
					+ " - saveEppPerson thất bại.");
		}
	}
}
