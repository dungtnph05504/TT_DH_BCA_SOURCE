package com.nec.asia.nic.framework.admin.rbac.service;

import java.util.List;

import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.service.BusinessService;

public interface FunctionService extends BusinessService<Functions, String> {

	public List<Functions> findBySystemId(String systemId);
}
