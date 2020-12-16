package com.nec.asia.nic.comp.trans.dao.impl;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import oracle.jdbc.internal.OracleTypes;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryInfo;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryDao;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.ImmiHistoryDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
@Repository("nicImmiHistoryDao")
public class NicImmiHistoryDaoImpl 
		extends GenericHibernateDao<ImmiHistory, Long> 
		implements NicImmiHistoryDao{
	
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@Override
	public List<ImmiHistory> findByNinPPno(String nin, String passportNo) {
		try{
	 		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(nin) || StringUtils.isNotBlank(passportNo)) {
				//Long idP = null;
				//if(StringUtils.isNotEmpty(passportNo)){
					//idP = Long.parseLong(passportNo);
				//}
				Criterion pid = Restrictions.eq("identityCardNo", nin);
				Criterion ppNo = Restrictions.eq("passportNo", passportNo);
				detachedCriteria.add(Restrictions.or(pid, ppNo));
				List<ImmiHistory> resultList = this.findAll(detachedCriteria);
				return resultList;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ImmiHistory> findAllByTypeOrGate(String type, String borderGate) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			if (type != null) {
				detachedCriteria.add(Restrictions.eq("immiType", type));
			}
			
			if (StringUtils.isNotBlank(borderGate)) {
				detachedCriteria.add(Restrictions.eq("borderGateCode", borderGate));
			}
			
			List<ImmiHistory> resultList = this.findAll(detachedCriteria);
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<CountPassport> borderGateNC() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("borderGateNC");
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
	public List<CountPassport> borderGateNCVP() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("borderGateNCVP");
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
	public List<CountPassport> borderGateXC() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("borderGateXC");
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
	public List<CountPassport> borderGateXCVP() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("borderGateXCVP");
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ImmiHistory> listImmihistoryAll(NicUploadJobDto model, int pageNumber, int pageSize) throws Exception {
		Session session = null;
		List<ImmiHistory> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("listImmihistoryAll");
			int firstResult = (pageNumber - 1) * pageSize;
	        int maxResults = pageSize;
	        query.setFirstResult(firstResult);
	        query.setMaxResults(maxResults);
	        query.setString("fullNameI", model.getFullName());
			query.setString("genderI", model.getGender());
			query.setString("dobI", model.getDob());
			query.setString("fromDateI", model.getEsColectionDate());
			query.setString("borderGateI", model.getLeaderNote());
			query.setString("passportNoI", model.getPassportNo());
			query.setString("immiTypeI", model.getTransactionType());
			query.setString("countryCodeI", model.getNicSiteCode());
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
					lst = new ArrayList<ImmiHistory>();
				for (int i = 0; i < list.size(); i++) {
					ImmiHistory countP = new ImmiHistory();
					 Object[] obj = (Object[]) list.get(i);
					 countP.setFullName((String) obj[0]);
					 countP.setDateOfBirth((Date) obj[1]);
					 countP.setGender((String) obj[2]);
					 countP.setCountryCode((String) obj[3]);
					 countP.setImmiType((String) obj[4]);
					 countP.setCreatedTime((Date) obj[5]);
					 countP.setPassportNo((String) obj[6]);
					 countP.setVisaNo((String) obj[7]);
					 countP.setBorderGateCode((String) obj[8]);
					 if(pageNumber == 1){
						// countP.setStt(i + 1);
					 }
					 else{
					//	 countP.setStt(((pageNumber - 1) * pageSize) + i + 1);
					 }
					 lst.add(countP);
				}
			}
		} catch (HibernateException ex) {
			throw new Exception(ex);
		}finally {
			session.close();
		}
		return lst;
	}
	

	@Override
	public long listImmihistoryAllCount(NicUploadJobDto model) throws Exception {
		Session session = null;
		int total = 0;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("totalImmihistoryAll");
			query.setString("fullNameI", model.getFullName());
			query.setString("genderI", model.getGender());
			query.setString("dobI", model.getDob());
			query.setString("fromDateI", model.getEsColectionDate());
			query.setString("borderGateI", model.getLeaderNote());
			query.setString("passportNoI", model.getPassportNo());
			query.setString("immiTypeI", model.getTransactionType());
			query.setString("countryCodeI",model.getNicSiteCode());
			@SuppressWarnings("rawtypes")
			List list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				 total = (Integer) list.get(0);

			}	
		} catch (HibernateException ex) {
			throw new Exception(ex);
		}finally {
			session.close();
		}
		return total;
	}
	
	@Override
	public List<NicUploadJobDto> newImmihistory() throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("newImmihistory");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicUploadJobDto>();
				for (int i = 0; i < list.size(); i++) {
					NicUploadJobDto countP = new NicUploadJobDto();
					 Object[] obj = (Object[]) list.get(i);
					 countP.setFullName((String) obj[0]);
					 countP.setRegSiteCode((String) obj[1]);
					 countP.setImmiType((String) obj[2]);
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
	public ImmiHistory findImmiByTransactionId(String transactionId, Long id) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ImmiHistory.class);
			if(StringUtils.isNotEmpty(transactionId)){
				criteria.add(Restrictions.eq("transactionId", transactionId));
			}
			if(id != null){
				criteria.add(Restrictions.eq("id", id));
			}
			List<ImmiHistory> list = this.findAll(criteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Throwable e) {
			throw new Exception(e);
		}
		return null;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public PaginatedResult<ImmiHistoryDto> getAllImmihistory(String fullName,
			String dob, String gender, String passportNo,
			String visaNo, String nin, String nationality, int pageNo,
			int pageSize) {
		Session session = null;
		List<ImmiHistoryDto> lst = null;
		PaginatedResult<ImmiHistoryDto> result = null;
		try {
			Integer total = this.totalImmi(fullName, dob, gender, passportNo, visaNo, nin, nationality);
			session = this.openSession();
			Query query = session.getNamedQuery("searchImmiQuery");
			query.setInteger("pageNo", pageNo);
			query.setInteger("pageSize", pageSize);
			query.setString("fname", fullName);
			query.setString("dob", dob);
			query.setString("gender", gender);
			query.setString("ppNo", passportNo);
			query.setString("vsNo", visaNo);
			query.setString("nin", nin);
			query.setString("nationality", nationality);
			List list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<ImmiHistoryDto>();
				for (int i = 0; i < list.size(); i++) {
					ImmiHistoryDto immi = new ImmiHistoryDto();
					 Object[] obj = (Object[]) list.get(i);
					 immi.setFullName((String) obj[0]);
					 immi.setGender((String) obj[1]);
					 immi.setDob((String) obj[2]);
					 immi.setCountryCode((String) obj[3]);
					 immi.setPassportNo((String) obj[4]);
					 immi.setVisaNo((String) obj[5]);
					 immi.setNin((String) obj[6]);
					 immi.setPob((String) obj[7]);
					 lst.add(immi);
				}
				result = new PaginatedResult<ImmiHistoryDto>(total, pageNo, lst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PaginatedResult<ImmiHistoryDto> getAllImmihistoryA(String fullName,
			int startY, int endY, String gender, String passportNo,
			String visaNo, String nin, String nationality, int pageNo,
			int pageSize) {
		Session session = null;
		List<ImmiHistoryDto> lst = null;
		PaginatedResult<ImmiHistoryDto> result = null;
		try {
			Integer total = this.totalImmiA(fullName, startY, endY, gender, passportNo, visaNo, nin, nationality);
			session = this.openSession();
			Query query = session.getNamedQuery("searchImmiQueryA");
			query.setInteger("pageNo", pageNo);
			query.setInteger("pageSize", pageSize);
			query.setString("fname", fullName);
			query.setInteger("startY", startY);
			query.setInteger("endY", endY);
			query.setString("gender", gender);
			query.setString("ppNo", passportNo);
			query.setString("vsNo", visaNo);
			query.setString("nin", nin);
			query.setString("nationality", nationality);
			List list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<ImmiHistoryDto>();
				for (int i = 0; i < list.size(); i++) {
					ImmiHistoryDto immi = new ImmiHistoryDto();
					 Object[] obj = (Object[]) list.get(i);
					 immi.setFullName((String) obj[0]);
					 immi.setGender((String) obj[1]);
					 immi.setDob((String) obj[2]);
					 immi.setCountryCode((String) obj[3]);
					 immi.setPassportNo((String) obj[4]);
					 immi.setVisaNo((String) obj[5]);
					 immi.setNin((String) obj[6]);
					 immi.setPob((String) obj[7]);
					 lst.add(immi);
				}
				result = new PaginatedResult<ImmiHistoryDto>(total, pageNo, lst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer totalImmi(String fullName, String dob, String gender,
			String passportNo, String visaNo, String nin, String nationality) {
		Session session = null;
		Integer total = 0;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("totalImmi");
			query.setString("fname", fullName);
			query.setString("dob", dob);
			query.setString("gender", gender);
			query.setString("ppNo", passportNo);
			query.setString("vsNo", visaNo);
			query.setString("nin", nin);
			query.setString("nationality", nationality);
			List list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				 total = (Integer) list.get(0);
				 //total = ((Integer) obj[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return total;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer totalImmiA(String fullName, int startY, int endY,
			String gender, String passportNo, String visaNo, String nin,
			String nationality) {
		Session session = null;
		Integer total = 0;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("totalImmiA");
			query.setString("fname", fullName);
			query.setInteger("startY", startY);
			query.setInteger("endY", endY);
			query.setString("gender", gender);
			query.setString("ppNo", passportNo);
			query.setString("vsNo", visaNo);
			query.setString("nin", nin);
			query.setString("nationality", nationality);
			List list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				total = (Integer) list.get(0);
				 //total = ((Integer) obj[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return total;
	}

	@Override
	public Integer totalImmiByBorder(String immiType, String borderGate) {
		Session session = null;
		Integer total = 0;
		try {
			session = this.openSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			CallableStatement command = sessionImpl.connection().prepareCall("{ call EPP_CENTRAL.SP_SEARCH_ALL_IMMIHISTORY(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");			
			command.setInt(1, 1);
			command.setInt(2, 20);
			command.setString(3,  "");
			command.setString(4, "");
			command.setString(5,  "");
			command.setString(6, "");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 1967);
			cal.set(Calendar.MONTH, Calendar.MAY);
			cal.set(Calendar.DAY_OF_MONTH, 16);
			java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
			command.setDate(7, sqlDate);
			command.setString(8,  "");

			command.setNull(9,  OracleTypes.TIMESTAMP);
			command.setNull(10,  OracleTypes.TIMESTAMP);
			
			command.setString(11,  "");
			command.setString(12,  "");
			command.setString(13,  "");
			
			command.setNull(14,  OracleTypes.NUMBER);
			command.setString(15,  "");
			command.setString(16,  "");
			command.registerOutParameter(17, OracleTypes.CURSOR);
			command.execute();
			ResultSet result = (ResultSet) command.getObject(17);
			if(result != null){
				total = 1;
				while(result.next()){
					System.out.println(result.getString("FULL_NAME"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return total;
	}
	@Override
	public PaginatedResult<ImmiHistoryInfo> getListImmihistory(String fullname,
			String passportNo, String vidaNo, String nationCode, String dob,
			String gender, String fromDate, String endDate, String typeXnc,
			String ckXNC, String chuyenBay, String sophieuXNC, String purpose,
			String sapxep, int pageNo, int pageSite) {
		 PaginatedResult<ImmiHistoryInfo> resultPage = null;
		 Session session = null;
		  try {
			session = this.getSession();
			//org.hibernate.Transaction tx = session.beginTransaction();
			SessionImpl sessionImpl = (SessionImpl) session;
			CallableStatement command =sessionImpl.connection().prepareCall("{ call EPP_CENTRAL.SP_SEARCH_ALL_IMMIHISTORY(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			command.setInt(1, pageNo);
			command.setInt(2,pageSite);			
			command.setString(3,  fullname);
			command.setString(4, passportNo);
			command.setString(5,  vidaNo);
			command.setString(6, nationCode);
			 // java.sql.Date date = java.sql.Date.valueOf("2015-03-31");
		//	DateFormat formatDate = new SimpleDateFormat("");
           if(!StringUtils.isEmpty(dob)){
        	    String[] str = dob.split("/");
	            String dobForm = str[2] + "-" + str[1] + "-" + str[0];
        	   command.setTimestamp(7,  Timestamp.valueOf(dobForm +" 00:00:00.000"));
           }else{
        		command.setNull(7,  OracleTypes.DATE);
           }
		    if(!StringUtils.isEmpty(gender)){
		    	command.setString(8,  gender);
		    }else{
		    	command.setString(8,  "-1");
		    }
		    if(!StringUtils.isEmpty(fromDate)){
		    	String[] str = fromDate.split("/");
	            String dateFrom = str[2] + "-" + str[1] + "-" + str[0];
		    	command.setTimestamp(9, Timestamp.valueOf(dateFrom +" 00:00:00.000"));
		    }else{
		    	command.setNull(9,  OracleTypes.TIMESTAMP);
		    }
			if(!StringUtils.isEmpty(endDate)){
				String[] str = endDate.split("/");
	            String dateEnd = str[2] + "-" + str[1] + "-" + str[0];
		    	command.setTimestamp(10, Timestamp.valueOf(dateEnd +" 00:00:00.000"));
			//	command.setTimestamp(10, Timestamp.valueOf("2020-10-10 00:00:00.000"));
			}else{
				command.setNull(10,  OracleTypes.TIMESTAMP);
			}
			//command.setNull(10,  OracleTypes.TIMESTAMP);
			if(!StringUtils.isEmpty(typeXnc)){
				command.setString(11,  typeXnc);
			}else{
				command.setString(11,  "-1");
			}
			if(!StringUtils.isEmpty(ckXNC)){
				command.setString(12,ckXNC);
			}else{
				command.setString(12,  "-1");
			}
			
			command.setString(13,  chuyenBay);
			if(!StringUtils.isEmpty(sophieuXNC)){
				command.setInt(14, Integer.parseInt(sophieuXNC));
			}else{
				command.setNull(14,  OracleTypes.NUMBER);
			}
			resultPage = new PaginatedResult<>();
			command.setString(15,  purpose);
			command.setString(16,  sapxep);
			command.registerOutParameter(17, OracleTypes.CURSOR);
			command.execute();
			ResultSet result = (ResultSet) command.getObject(17);
			List<ImmiHistoryInfo> list = new ArrayList<ImmiHistoryInfo>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
			if(result != null){
				while(result.next()){
					ImmiHistoryInfo immi = new ImmiHistoryInfo();
					//immi.setId(result.getString());
					immi.setFullName(result.getString("FULL_NAME"));
					immi.setDateOfBirth(dateFormat.format(result.getDate(2)));
					immi.setGender(result.getString(3));
					immi.setNationCode(result.getString(4));
					immi.setPassportNo(result.getString(5));
					immi.setVisaNo(result.getString(6));
					immi.setCkXnc(result.getString(7));
					immi.setTypeXnc(result.getString(8));
					immi.setIssueDate(dateFormat.format(result.getDate(10)));
					immi.setSoPhieuXnc(result.getString(13));
					resultPage.setTotal(result.getInt(15));
					//immi.setGender(result.getString(""));
					//result.get
					list.add(immi);
					//System.out.println(immi.getDateOfBirth());
				}
			}

			
			resultPage.setPage(pageNo);
			resultPage.setRows(list);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return resultPage;
	}

	@Override
	public ImmiHistory getImmiHistoryById(Long id) {
		// TODO Auto-generated method stub
		try {
			return this.findById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
