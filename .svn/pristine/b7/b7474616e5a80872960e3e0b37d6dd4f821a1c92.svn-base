package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.EppInventoryDao;
import com.nec.asia.nic.comp.trans.dao.EppPersonDao;
import com.nec.asia.nic.comp.trans.dao.RptStatisticsTransmitDataDao;
import com.nec.asia.nic.comp.trans.domain.EppInventory;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.InventoryDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("eppInventoryDao")
public class EppInventoryDaoImpl extends GenericHibernateDao<EppInventory, Long> implements EppInventoryDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<CountPassport> findByStatus(String status) throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("statusInvestory");
			query.setString("status", status);
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					 Object[] obj = (Object[]) list.get(i);
					 countP.setRegSite((String) obj[0]);
					 countP.setTotal((Integer) obj[1]);
					 lst.add(countP);
				}
			}
			//logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}", status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		}finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<EppInventory> findAllInvestory(Integer officeId, String active) {
		List<EppInventory> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
			if(officeId != null){
				criteria.add(Restrictions.eq("officeId", officeId));
			}
			if(StringUtils.isNotEmpty(active)){
				criteria.add(Restrictions.eq("active", active));
			}
			list = this.findAll(criteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public EppInventory findInventoryByCode(String code, String active) {
		// TODO Auto-generated method stub
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EppInventory.class);
			if(StringUtils.isNotEmpty(code)){
				detachedCriteria.add(Restrictions.eq("code", code));
			}
			if(StringUtils.isNotEmpty(active)){
				detachedCriteria.add(Restrictions.eq("active", active));
			}
			List<EppInventory> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PaginatedResult<InventoryDto> findInventoryBySearch(
			AssignmentFilter filter, int pageNo, int pageSize) {
		Session session = null;
		try {
			session = this.getSession();
			StringBuffer strCode = new StringBuffer("SELECT item.BATCH_NO, dtail.CHIP_SERIES_NO, dtail.DOC_CHARS, dtail.DOC_NUM, dtail.STATUS, dtail.INVENTORY_ITEMS_ID, inv.CODE, dtail.ID FROM EPP_CENTRAL.EPP_INVENTORY inv, EPP_CENTRAL.EPP_INVENTORY_ITEMS item, EPP_CENTRAL.EPP_INVENTORY_ITEMS_DETAIL dtail WHERE inv.ID = item.INVENTORY_ID AND item.ID = dtail.INVENTORY_ITEMS_ID");
			if(StringUtils.isNotEmpty(filter.getInvCode())){
				strCode.append(" AND inv.CODE = '" + filter.getInvCode() + "'");
			}
			if(StringUtils.isNotEmpty(filter.getBatchNo())){
				strCode.append(" AND dtail.BATCH_NO = '" + filter.getBatchNo() + "'");
			}
			if(StringUtils.isNotEmpty(filter.getStatus())){
				strCode.append(" AND dtail.STATUS = '" + filter.getStatus() + "'");
			}
			if(StringUtils.isNotEmpty(filter.getInvItems())){
				strCode.append(" AND dtail.INVENTORY_ITEMS_ID = '" + filter.getInvItems() + "'");
			}
			if(StringUtils.isNotEmpty(filter.getChipNo())){
				strCode.append(" AND dtail.CHIP_SERIES_NO = '" + filter.getChipNo() + "'");
			}
			strCode.append(" AND inv.ACTIVE = 'Y'");
			Query query = session.createSQLQuery(strCode.toString());
			int count = query.list().size();			
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
			List list = query.list();
			Iterator itr = list.iterator();
			int i = (pageNo - 1) * pageSize;
			if(list != null && list.size() > 0){
				List<InventoryDto> iveList = new ArrayList<InventoryDto>();
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					InventoryDto inv = new InventoryDto();
					i++;
					inv.setStt(i);
					inv.setBatchNo(record[0] != null ? record[0].toString() : "");
					inv.setChipNo(record[1] != null ? record[1].toString() : "");
					inv.setDocChar(record[2] != null ? record[2].toString() : "");
					inv.setDocNum(record[3] != null ? record[3].toString() : "");
					inv.setStatus(record[4] != null ? record[4].toString() : "");
					inv.setInvCode(record[5] != null ? record[5].toString() : "");
					inv.setInvName(record[6] != null ? record[6].toString() : "");
					inv.setItemStr(record[7] != null ? record[7].toString() : "");
					iveList.add(inv);
				}
				PaginatedResult<InventoryDto> result = new PaginatedResult<InventoryDto>(count, pageNo, iveList);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<String> getAllInventoryItems() {
		List<String> itemList = new ArrayList<String>();
		Session session = null;
		try {
			session = this.getSession();
			StringBuffer strCode = new StringBuffer("SELECT item.ID FROM EPP_CENTRAL.EPP_INVENTORY inv, EPP_CENTRAL.EPP_INVENTORY_ITEMS item WHERE inv.ID = item.INVENTORY_ID AND inv.ACTIVE = 'Y' ORDER BY item.ID asc");
			Query query = session.createSQLQuery(strCode.toString());
			List list = query.list();
			Iterator itr = list.iterator();
			if(list != null && list.size() > 0){
				while (itr.hasNext()) {
					Object record = (Object) itr.next();
					String item = record != null ? record.toString() : "";
					itemList.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return itemList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<String> getAllBatchNo() {
		List<String> itemList = new ArrayList<String>();
		Session session = null;
		try {
			session = this.getSession();
			StringBuffer strCode = new StringBuffer("SELECT DISTINCT item.BATCH_NO FROM EPP_CENTRAL.EPP_INVENTORY inv, EPP_CENTRAL.EPP_INVENTORY_ITEMS item WHERE inv.ID = item.INVENTORY_ID AND inv.ACTIVE = 'Y'");
			Query query = session.createSQLQuery(strCode.toString());
			List list = query.list();
			Iterator itr = list.iterator();
			if(list != null && list.size() > 0){
				while (itr.hasNext()) {
					Object record = (Object) itr.next();
					String item = record != null ? record.toString() : "";
					itemList.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return itemList;
	}

	@Override
	public EppInventory findInvById(Integer id) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
			if(id != null){
				criteria.add(Restrictions.eq("id", id));
			}
			
			List<EppInventory> list = this.findAll(criteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PaginatedResult<InventoryDto> findInventoryItemsBySearch(
			AssignmentFilter filter, int pageNo, int pageSize) {
		Session session = null;
		try {
			session = this.getSession();
			StringBuffer strCode = new StringBuffer("SELECT item.HANDOVER_NO, item.RECEIPT_NAME, item.HANDOVER_NAME, item.BATCH_NO, item.CATEGORY_ID, inv.CODE, item.ID FROM EPP_CENTRAL.EPP_INVENTORY inv, EPP_CENTRAL.EPP_INVENTORY_ITEMS item WHERE inv.ID = item.INVENTORY_ID");
			if(StringUtils.isNotEmpty(filter.getInvCode())){
				strCode.append(" AND inv.CODE = '" + filter.getInvCode() + "'");
			}
			if(StringUtils.isNotEmpty(filter.getBatchNo())){
				strCode.append(" AND item.BATCH_NO = '" + filter.getBatchNo() + "'");
			}			
			if(StringUtils.isNotEmpty(filter.getHandoverNo())){
				strCode.append(" AND item.HANDOVER_NO = '" + filter.getHandoverNo() + "'");
			}
			
			strCode.append(" AND inv.ACTIVE = 'Y'");
			Query query = session.createSQLQuery(strCode.toString());
			int count = query.list().size();			
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
			List list = query.list();
			Iterator itr = list.iterator();
			int i = (pageNo - 1) * pageSize;
			if(list != null && list.size() > 0){
				List<InventoryDto> iveList = new ArrayList<InventoryDto>();
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					InventoryDto inv = new InventoryDto();
					i++;
					inv.setStt(i);
					inv.setHandoverNo(record[0] != null ? record[0].toString() : "");
					inv.setReceiptDate(record[1] != null ? record[1].toString() : "");
					inv.setHandoverName(record[2] != null ? record[2].toString() : "");
					inv.setBatchNo(record[3] != null ? record[3].toString() : "");
					inv.setStatus(record[4] != null ? record[4].toString() : "");
					inv.setInvCode(record[5] != null ? record[5].toString() : "");
					inv.setItemStr(record[6] != null ? record[6].toString() : "");
					iveList.add(inv);
				}
				PaginatedResult<InventoryDto> result = new PaginatedResult<InventoryDto>(count, pageNo, iveList);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return null;
	}
	
}
