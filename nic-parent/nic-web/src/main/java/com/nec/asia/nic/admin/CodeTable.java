package com.nec.asia.nic.admin;

import java.io.Serializable;

/**
 * demo code table bean
 * 
 * @author bright_zheng
 *
 */
public class CodeTable implements Serializable {
	private static final long serialVersionUID = 7872276957983212438L;
	private String codeType;
	private String codeName;
	private String codeValue;
	private String codeDesc;
	
	public CodeTable(){}
	
	public CodeTable(String codeType, String codeName, String codeValue, String codeDesc){
		this.codeType = codeType;
		this.codeName = codeName;
		this.codeValue = codeValue;
		this.codeDesc = codeDesc;
	}
	
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public String getCodeDesc() {
		return codeDesc;
	}
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
}
