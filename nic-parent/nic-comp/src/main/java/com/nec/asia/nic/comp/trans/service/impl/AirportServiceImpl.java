package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.AirportDao;
import com.nec.asia.nic.comp.trans.dao.ArchiveCodeDao;
import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.AirlineDto;
import com.nec.asia.nic.comp.trans.service.AirlineService;
import com.nec.asia.nic.comp.trans.service.AirportService;
import com.nec.asia.nic.comp.trans.service.ArchiveCodeService;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.utils.RegistrationConstants;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.HelperClass;


@Service("airportService")
public class AirportServiceImpl extends
		DefaultBusinessServiceImpl<EppAirport, Long, AirportDao>
		implements AirportService {
	@Autowired
	private AirportDao dao;
	
	@Autowired
	private CodesService codesService;
	
	@Override
	public EppAirport findAllByCode(String code) throws Exception{
		return dao.findByCode(code);
	}
	
	@Override
	public boolean updateAirport(EppAirport obj) throws Exception {
		try {
			if(obj != null){
				if(obj.getId() != null && obj.getId() > 0){
					obj.setLastModifiedDate(new Date());
				}
				this.dao.saveOrUpdate(obj);
				return true;
			}
		} catch (Throwable e) {
			throw new Exception(e.getMessage());
		}
		return false;
	}

	@Override
	public void saveOrUpdateAir(EppAirport epp) {
		dao.saveOrUpdate(epp);
	}

	@Override
	public PaginatedResult<AirlineDto> findPaginateBySearch(String code,
			String nationality, int pageNo, int pageSize) {
		try {
			PaginatedResult<EppAirport> result = this.dao.findPaginateBySearch(code, nationality, pageNo, pageSize);
			if(result != null){
				List<AirlineDto> list = new ArrayList<AirlineDto>();
				int i = (pageNo - 1) * pageSize;
				for(EppAirport epp : result.getRows()){
					AirlineDto dto = new AirlineDto();
					i++;
					dto.setStt(i);
					dto.setCloseTime(epp.getClosedTime() != null ? HelperClass.convertDateTimeToString(epp.getClosedTime()) : "");
					dto.setCode(epp.getCode());
					if(StringUtils.isNotEmpty(epp.getCountryCode())){
						String nat = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_VALUE_NATIONALITY, epp.getCountryCode(), "");
						dto.setCountryCode(nat);
					}
					dto.setCreateTime(epp.getCreateDate() != null ? HelperClass.convertDateTimeToString(epp.getCreateDate()) : "");
					dto.setDesc(epp.getDescriptions());
					dto.setId(epp.getId());
					dto.setName(epp.getName());
					list.add(dto);
				}
				PaginatedResult<AirlineDto> pr = new PaginatedResult<AirlineDto>(result.getTotal(), pageNo, list);
				return pr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean deleteEppAirport(Long id) throws Exception {
		try {
			EppAirport epp = this.dao.findById(id);
			if(epp != null){
				this.dao.delete(epp);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<EppAirport> findAllAirport() {
		try {
			return dao.findAll();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
