package com.nec.asia.nic.framework.report.dao;

import java.util.List;

import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.dao.DaoException;

public interface GenericReportDao {
	List getAllEntity(Class entityClass, String propertyName) throws DaoException;

}
