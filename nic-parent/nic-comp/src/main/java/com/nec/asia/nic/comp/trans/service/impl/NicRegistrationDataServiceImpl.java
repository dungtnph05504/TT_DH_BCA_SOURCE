/**
 * 
 */
package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.dto.NicRegistrationDataDto;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.exception.RegistrationServiceException;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.DateUtil;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 11, 2013
 */

/*
 * Modification History:
 * 
 * 08 Jan 2014 (Sailaja): Added findAllForPaginationOnReprint method for
 * Re-print 19 Mar 2014 (Swapna): Added findAllTransIdForPagination method to
 * load only transaction ids, nins without loading remaining fields info.
 */
@Service("nicRegistrationDataService")
public class NicRegistrationDataServiceImpl
		extends
		DefaultBusinessServiceImpl<NicRegistrationData, String, NicRegistrationDataDao>
		implements NicRegistrationDataService {

	/** must define the non-argument constructor because we use CGLib proxy */
	public NicRegistrationDataServiceImpl() {

	}

	@Autowired
	public NicRegistrationDataServiceImpl(NicRegistrationDataDao dao) {
		this.dao = dao;
	}

	@Override
	public PaginatedResult<NicRegistrationData> findAllForPagination(
			PageRequest pageRequest, NicRegistrationData NicRegistrationData)
			throws RegistrationServiceException {
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = true;
			boolean enableLike = true;
			Order order = null;
			if (StringUtils.equals(pageRequest.getSortorder(), "asc")) {
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}
			return dao.findAllForPaginationByFilter(NicRegistrationData,
					pageNo, pageSize, ignoreCase, enableLike, order);
			// return dao.findAllForPagination(NicRegistrationData, pageNo,
			// pageSize);
		} catch (Exception e) {
			throw new RegistrationServiceException(e);
		}

	}

	@Override
	public List<NicTransactionAttachment> findAllRegistrationPhotos(
			PageRequest pageRequest, NicRegistrationData nicRegistrationData)
			throws RegistrationServiceException {
		//
		// NicTransaction txn = nicRegistrationData.getNicTransaction();
		//
		// List<NicTransactionAttachment> photos = new
		// ArrayList<NicTransactionAttachment>();
		// List<NicRegistrationData> regDatas =
		// dao.findAll(nicRegistrationData);
		// for(NicRegistrationData regData : regDatas) {
		// if(regData.getNicTransaction() != null) {
		// Set<NicTransactionAttachment> docs =
		// regData.getNicTransaction().getNicTransactionAttachments();
		// if(!CollectionUtils.isEmpty(docs)) {
		// photos.add(docs.iterator().next());
		// }
		// }
		// }
		return null;
	}

	@Override
	public PaginatedResult<NicRegistrationData> findAllForPagination(
			int currentPage, int pageSize, Order hibernateOrder,
			String siteCode, String officerId, Date fromDate, Date toDate)
			throws Exception {
		return dao.findAllForPagination(currentPage, pageSize, hibernateOrder,
				siteCode, officerId, fromDate, toDate);
	}

	public PaginatedResult<NicRegistrationDataDto> findAllForPagination(
			PageRequest pageRequest, NicRegistrationDataDto dto)
			throws RegistrationServiceException {
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = true;
			boolean enableLike = true;
			Order order = null;
			if (StringUtils.equals(pageRequest.getSortorder(), "asc")) {
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}

			NicRegistrationData nicRegistrationData = getDbo(dto);

			List<NicRegistrationDataDto> dtos = new ArrayList<NicRegistrationDataDto>();
			nicRegistrationData.setDateOfBirth(DateUtil.strToDate(
					dto.getDateOfBirthDisp(), "dd-MMM-yyyy"));
			Date from = DateUtil.strToDate(dto.getDateFrom(), "dd-MMM-yyyy");
			Date to = DateUtil.strToDate(dto.getDateTo(), "dd-MMM-yyyy");
			PaginatedResult<NicRegistrationData> prDbo = dao
					.findAllForPaginationByFilter(nicRegistrationData, from,
							to, pageNo, pageSize, ignoreCase, enableLike, order);
			List<NicRegistrationData> dbos = prDbo.getRows();
			for (NicRegistrationData dbo : dbos) {
				dtos.add(getDto(dbo));
			}

			PaginatedResult<NicRegistrationDataDto> prDto = new PaginatedResult<NicRegistrationDataDto>(
					prDbo.getTotal(), prDbo.getPage(), dtos);

			return prDto;
		} catch (Exception e) {
			throw new RegistrationServiceException(e);
		}

	}

	private NicRegistrationData getDbo(NicRegistrationDataDto dto) {
		NicRegistrationData dbo = new NicRegistrationData();
		BeanUtils.copyProperties(dto, dbo, new String[] { "nicTransaction" });
		if (dto.getNicTransaction() != null) {
			NicTransaction nicTransaction = new NicTransaction();
			BeanUtils.copyProperties(dto.getNicTransaction(), nicTransaction);
			// dbo.setNicTransaction(nicTransaction);
		}
		return dbo;
	}

	private NicRegistrationDataDto getDto(NicRegistrationData dbo) {
		NicRegistrationDataDto dto = new NicRegistrationDataDto();
		BeanUtils.copyProperties(dbo, dto, new String[] { "nicTransaction" });
		// if(dbo.getNicTransaction() != null) {
		// NicTransactionDto nicTransaction = new NicTransactionDto();
		// BeanUtils.copyProperties(dbo.getNicTransaction(), nicTransaction);
		// dto.setNicTransaction(nicTransaction);
		// }
		return dto;
	}

	@Override
	public List getAllRegistrations(Date fromDate, Date toDate,
			String[] siteCodes) throws Exception {
		return dao.getAllRegistrations(fromDate, toDate, siteCodes);
	}

	@Override
	public List<NicRegistrationData> getRegistrations(List<String> transIds)
			throws Exception {
		return dao.getRegistrations(transIds);
	}

	@Override
	public List getRegistrationsInfoBySite(Date fromDate, Date toDate,
			String siteCode) throws Exception {
		return dao.getRegistrationsInfoBySite(fromDate, toDate, siteCode);
	}

	@Override
	public List<NicRegistrationData> getFPEncodeValues(Date fromDate,
			Date toDate, String[] sites) throws Exception {
		return dao.getFPEncodeValues(fromDate, toDate, sites);
	}

	public PaginatedResult<NicRegistrationDataDto> findAllForPaginationOnReprint(
			PageRequest pageRequest, NicRegistrationDataDto dto)
			throws RegistrationServiceException {
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = true;
			boolean enableLike = true;
			Order order = null;
			if (StringUtils.equals(pageRequest.getSortorder(), "asc")) {
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}

			NicRegistrationData nicRegistrationData = getDbo(dto);

			List<NicRegistrationDataDto> dtos = new ArrayList<NicRegistrationDataDto>();
			nicRegistrationData.setDateOfBirth(DateUtil.strToDate(
					dto.getDateOfBirthDisp(), "dd-MMM-yyyy"));
			Date from = DateUtil.strToDate(dto.getDateFrom(), "dd-MMM-yyyy");
			Date to = DateUtil.strToDate(dto.getDateTo(), "dd-MMM-yyyy");
			PaginatedResult<NicRegistrationData> prDbo = dao
					.findAllForPaginationByFilterOnReprint(nicRegistrationData,
							from, to, pageNo, pageSize, ignoreCase, enableLike,
							order);
			List<NicRegistrationData> dbos = prDbo.getRows();
			for (NicRegistrationData dbo : dbos) {
				dtos.add(getDto(dbo));
			}

			PaginatedResult<NicRegistrationDataDto> prDto = new PaginatedResult<NicRegistrationDataDto>(
					prDbo.getTotal(), prDbo.getPage(), dtos);

			return prDto;
		} catch (Exception e) {
			throw new RegistrationServiceException(e);
		}

	}

	@Override
	public List<Object[]> getTotalCardsIssuedBySite(Date fromDate, Date toDate,
			String[] sites) throws Exception {
		return dao.getTotalCardsIssuedBySite(fromDate, toDate, sites);
	}

	@Override
	public PaginatedResult<NicRegistrationData> findAllTransIdForPagination(
			int currentPage, int pageSize, Order hibernateOrder,
			String[] siteCodes, String officerId, Date fromDate, Date toDate)
			throws Exception {
		return dao.findAllTransIdForPagination(currentPage, pageSize,
				hibernateOrder, siteCodes, officerId, fromDate, toDate);
	}

	@Override
	public List<NicRegistrationData> findAllByFields(
			Map<String, Object> fieldsMap) throws RegistrationServiceException,
			Exception {

		if (fieldsMap == null || fieldsMap.size() < 0)
			throw new RegistrationServiceException(
					"Input fields cannot be null or empty");

		return this.dao.findAllByFields(fieldsMap);

	}

	@Override
	public boolean isExistOnLookoutData(String firstName, String lastName,
			String middleName) throws RegistrationServiceException, Exception {

		return this.dao.isLookoutData(firstName, lastName, middleName);
	}

	@Override
	public NicRegistrationData getNicDataById(String transactionId) {
		try {
			NicRegistrationData data = this.dao.getNicDataById(transactionId);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public BaseModelSingle<NicRegistrationData> findRegistDataById(
			String transactionId) {
		try {
			return this.dao.findRegistDataById(transactionId);
		} catch (Exception e) {
			return new BaseModelSingle<NicRegistrationData>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findRegistDataById - " + transactionId
							+ " - thất bại.");
		}
	}

	@Override
	public List<NicRegistrationData> findRegByPersonInfo(String name,
			String gender, String dateOfBirth) throws Exception {
		List<NicRegistrationData> listRs = null;
		try {
			Date date = null;
			if (StringUtils.isNotBlank(dateOfBirth)) {
				date = DateUtil.strToDate(dateOfBirth, DateUtil.FORMAT_YYYYMMDD);
			}
			listRs = this.dao.findRegByPersonInfo(name, gender, date);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findRegByPersonInfo - thất bại.");
		}
		return listRs;
	}

}
