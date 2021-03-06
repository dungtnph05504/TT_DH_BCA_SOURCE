/**
 * 
 */
package com.nec.asia.nic.framework.admin.code.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.report.form.CodeValueForm;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * @author prasad_karnati
 *
 */
public interface CodesService extends BusinessService<Codes, String> {

    
    public static final String CODE_ID_NATIONALITY = "NATIONALITY";
    
	public PaginatedResult<Codes> getAllcodes(int pageNo,int pageSize);
	public PaginatedResult<CodeValues> getCodevalueByCodeId(String codeId,int pageNo,int pageSize);
	public String saveCodeDefinition(CodeValueForm codeValueForm, String createBy, Date createDate, String createWkstnId);
	public CodeValues getCodeValueByIdName(String codeId ,String codeName);
	public String updateCodeValue(CodeValueForm codeValueForm, String updateBy, Date updateDate, String updateWkstnId);
	public List<CodeValues> findAllByCodeId(String codeName);
	public Map<String, String> getCodeValues(String codeName);
	public Map<String, String> getRICCodeValues(String codeName);
	public PaginatedResult<Codes> getAllForPagination(int pageNo,int pageSize,Order... order);
	public List<CodeValues>  getAllCodeValues(String codeId,String[] codeNames);
	public List<CodeValues>  getParentCodeValues(String parentId);		
	public Map<String, String> getAllCodeValues();
	public String deleteCode(String codeId, String deleteBy, Date deleteDate, String deleteWkstnId);
	public String deleteCodeValue(String codeId, String codeName, String deleteBy, Date deleteDate, String deleteWkstnId);
	public CodeValues findByStringCodeId(String codeId, String codeValue);
	public String getCodeValueDescByIdName(String codeId, String codeVal, String defaultValOnExceptionOrNull);
	public CodeValues getCountryByA08Id (String a08Id) throws Exception;
	public CodeValues findByCodeIdAndA08Id(String codeId, String a08Id) throws Exception;
	public List<String> getAllCodeDescs() throws Exception;
	public PaginatedResult<Codes> getCodesByCodeId(String codeId, int pageNo,
			int pageSize);
	public PaginatedResult<CodeValues> getCodevalueByCoditions(String codeId,
			String codeValue, String codeValueDesc, int pageNo, int pageSize);
	
}
