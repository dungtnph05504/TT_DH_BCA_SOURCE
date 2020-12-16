package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicMainDao;
import com.nec.asia.nic.comp.trans.domain.NicMain;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;


/*
 * Modification History:
 * 
 * 22 Apr 2014 (chris): fix bug on checkNicMainExistsByNin()
 */
@Repository("mainDao")
public class NicMainDaoImpl extends GenericHibernateDao<NicMain, Long> implements NicMainDao{
	
	public boolean checkNicMainExistsByNin(String nin) {
		boolean exists = false;
		/* [chris][2014 Apr 22] - comment the problematic code
		 * where gen_no and update_no should not be passed to the query
		 * The wrong sql is | select count(*) as y0_ from NICDB.NIC_MAIN this_ where (this_.NIN=? and this_.GEN_NO=? and this_.UPDATE_NO=?) |
		 */
		//NicMain exampleNicMain = new NicMain();
		//exampleNicMain.setNin(nin);
		//long size = this.count(exampleNicMain);
		//exists = (size >=1);
		String hql = "select nicId from NicMain a where a.nin = ?";
		List<Long> nicIdList = (List<Long>) this.getHibernateTemplate().find(hql, nin);
		if (CollectionUtils.isNotEmpty(nicIdList)) {
			exists = true;
		}
		return exists;
	}
	
	public List<NicMain> findAllByFields(String transactionId, String nin, String ccn, 
			String surname,	String firstName, String surnameAtBirth, String sex, String dob) {
		List<NicMain> nicMainList = new ArrayList<NicMain>();
		List<Object> paraList = new ArrayList<Object>();
		String hql = "";
			//"from NicMain a ";
			//"where a.transactionId in (select b.transactionId from NicRegistrationData b " +
			//"where nin like ? and surnameFull like ? and firstnameFull like ? and surnameBirthFull like ? and sex like ?) ";
		String hql2 = "";
		if (StringUtils.isNotBlank(transactionId)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " upper(transactionId) like ?";
			paraList.add(transactionId.toUpperCase());
		}
		if (StringUtils.isNotBlank(nin)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " upper(nin) like ?";
			paraList.add(nin.toUpperCase());
		}
		if (StringUtils.isNotBlank(ccn)) {//ccn only contains digits.
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " ccn like ?";
			paraList.add(ccn);
		}
		if (StringUtils.isNotBlank(surname)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " upper(surnameFull) like ?";
			paraList.add(surname.toUpperCase());
		}
		if (StringUtils.isNotBlank(firstName)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " upper(firstnameFull) like ?";
			paraList.add(firstName.toUpperCase());
		}
		if (StringUtils.isNotBlank(surnameAtBirth)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " upper(surnameBirthFull) like ?";
			paraList.add(surnameAtBirth.toUpperCase());
		}
		if (StringUtils.isNotBlank(sex)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " upper(sex) like ?";
			paraList.add(sex.toUpperCase());
		}
		if (StringUtils.isNotBlank(dob) && StringUtils.length(dob)==9) { //in DG date sharing format dd-mm-yyyy 
			String dayPart = StringUtils.substring(dob, 0, 2);
			String monthPart = StringUtils.substring(dob, 3, 5);
			String yearPart = StringUtils.substring(dob, 6, 10);
			
			boolean isUnknownDay = (StringUtils.equals(dayPart, "00"));
			boolean isUnknownMonth = (StringUtils.equals(monthPart, "00"));
			boolean isUnknownYear = (StringUtils.equals(yearPart, "0000"));
			
			String matchDateFormat = (isUnknownDay?"":"dd")+"-"+(isUnknownMonth?"":"mm")+"-"+(isUnknownYear?"":"yyyy");
			String matchDobString = (isUnknownDay?"":dayPart)+"-"+(isUnknownMonth?"":monthPart)+"-"+(isUnknownYear?"":yearPart);
			logger.debug("[NicMainDao.findAllByFields] dob: to_char(dateOfBirth, '{}') = {}", matchDateFormat, matchDobString);
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			if (isUnknownDay || isUnknownMonth || isUnknownYear) {
				hql2 += " (printDob = ? or "; //printDob is ddMMyyyy format
				String printDobString = dayPart+monthPart+yearPart;
				paraList.add(printDobString);
			}
			hql2 += " to_char(dateOfBirth, '"+matchDateFormat+"') = ? ";
			paraList.add(matchDobString);
			
			if (isUnknownDay || isUnknownMonth || isUnknownYear) {
				hql2 += " ) ";
			}
		}
		
		if (StringUtils.isNotBlank(hql2)) {
			hql = 	"from NicMain where "+hql2+" ";
			logger.info("[NicMainDao.findAllByFields] hql: {}", hql);
		}
		Object[] parameters = paraList.toArray();
		nicMainList = (List<NicMain>) this.getHibernateTemplate().find(hql, parameters);
		
		return nicMainList;
	}

	@Override
	public PaginatedResult<NicMain> findAllForPaginationByFilter(
			NicMain nicMain, int pageNo, int pageSize, boolean ignoreCase,
			boolean enableLike, Order order) {
		DetachedCriteria criteria = DetachedCriteria.forClass(NicMain.class);
		
		if(StringUtils.isNotEmpty(nicMain.getNin())){
			criteria.add(Restrictions.ilike("nin", nicMain.getNin().toLowerCase()));
		}
		if(StringUtils.isNotEmpty(nicMain.getSurname())){
			criteria.add(Restrictions.ilike("surname", nicMain.getSurname().toLowerCase()));
		}
		if(StringUtils.isNotEmpty(nicMain.getSurnameBirth())){
			criteria.add(Restrictions.ilike("surnameBirth", nicMain.getSurnameBirth().toLowerCase()));
		}
		if(StringUtils.isNotEmpty(nicMain.getOptionSurname())){
			criteria.add(Restrictions.ilike("optionSurname", nicMain.getOptionSurname().toLowerCase()));
		}
		if(StringUtils.isNotEmpty(nicMain.getGender())){
			criteria.add(Restrictions.ilike("gender", nicMain.getGender().toLowerCase()));
		}
		if(nicMain.getDateOfBirth() != null){
			criteria.add(Restrictions.eq("dateOfBirth", nicMain.getDateOfBirth()));
		}
		
		return findAllForPagination(criteria, pageNo, pageSize, order);
	}
}
