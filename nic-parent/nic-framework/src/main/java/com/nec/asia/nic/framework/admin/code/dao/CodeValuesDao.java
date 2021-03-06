package com.nec.asia.nic.framework.admin.code.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;




import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.CodeValuesId;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface CodeValuesDao extends GenericDao<CodeValues, CodeValuesId> {
	
	public List<CodeValues> findAllByCodeId (String codeId);
    public CodeValues findByStringCodeId(String codeId,String codevalue);
	public PaginatedResult<CodeValues> getCodevalues(String codeId,int pageNo, int pageSize, Order... orders);
	
	public PaginatedResult<CodeValues> findAllForPagination(final DetachedCriteria detachedCriteria, final int pageNo, final int pageSize);
	
	public List<CodeValues> getCodeValuesByParent(String parentId,String code);
	
	boolean updateCodeValue(CodeValues codeValue);
	
	boolean savePendingCodeValue(CodeValues codeValue);
	
	public Map<String, String> getAllCodeValues();

	public List<CodeValues> getAllCodeValues(String codeId, String[] codeNames) throws Exception;
	
	public List<CodeValues>  getParentCodeValues(String parentId);
	
	public CodeValues findByCodeIdAndA08Id (String codeId,String a08Id) throws Exception;
	public PaginatedResult<CodeValues> getCodevalueByCoditions(String codeId,
			String codeValue, String codeValueDesc, int pageNo, int pageSize) throws Exception;
}
