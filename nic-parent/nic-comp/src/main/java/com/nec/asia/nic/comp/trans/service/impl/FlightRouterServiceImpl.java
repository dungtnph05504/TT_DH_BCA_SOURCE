package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.ArchiveCodeDao;
import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.FlightRouterDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFlightRouter;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.FlightDto;
import com.nec.asia.nic.comp.trans.service.AirlineService;
import com.nec.asia.nic.comp.trans.service.AirportService;
import com.nec.asia.nic.comp.trans.service.ArchiveCodeService;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.FlightRouterService;
import com.nec.asia.nic.comp.trans.service.FlightService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;


@Service("flightRouterService")
public class FlightRouterServiceImpl extends
		DefaultBusinessServiceImpl<EppFlightRouter, Long, FlightRouterDao>
		implements FlightRouterService {
	@Autowired
	private FlightRouterDao dao;
	
	@Override
	public EppFlightRouter findByCode(String code) throws Exception{
		return dao.findByCode(code);
	}
	
	@Override
	public boolean updateFlightRouter(EppFlightRouter obj) throws Exception {
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
	public PaginatedResult<FlightDto> findPaginateBySearch(String code,
			String fromFlace, String toPlace, int pageNo, int pageSize) {
		try {
			PaginatedResult<EppFlightRouter> result = dao.findPaginateBySearch(code, fromFlace, toPlace, pageNo, pageSize);
			if(result != null){
				List<FlightDto> list = new ArrayList<FlightDto>();
				int i = (pageNo - 1) * pageSize;
				for(EppFlightRouter epp : result.getRows()){
					FlightDto dto = new FlightDto();
					i++;
					dto.setStt(i);
					dto.setFlightRouterCode(epp.getFlightRouterCode());
					dto.setFormPlace(epp.getFromPlaceCode());
					dto.setToPlace(epp.getToPlaceCode());
					dto.setId(epp.getId());
					dto.setName(epp.getName());
					dto.setNote(epp.getNote());
					list.add(dto);
				}
				PaginatedResult<FlightDto> pr = new PaginatedResult<>(result.getTotal(), pageNo, list);
				return pr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean deleteEppFlightRouter(Long id) {
		try {
			EppFlightRouter epp = this.dao.findById(id);
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
	public void saveOrUpdateAir(EppFlightRouter epp) {
		dao.saveOrUpdate(epp);
		
	}

	@Override
	public List<EppFlightRouter> findALlFlightRouter() {
		try {
			return dao.findAll();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
