/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.framework.admin.rbac.dao.FunctionsDao;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.service.FunctionService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * @author chris - Function Service 
 */
@Service("functionService")
public class FunctionServiceImpl extends
		DefaultBusinessServiceImpl<Functions, String, FunctionsDao> implements FunctionService {

	/** must define the non-argument constructor because we use CGLib proxy */
	public FunctionServiceImpl() {
		super();
	}

	@Autowired
	public FunctionServiceImpl(FunctionsDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Functions> findBySystemId(String systemId){
		return this.dao.findBySystemId(systemId);
	}
}
