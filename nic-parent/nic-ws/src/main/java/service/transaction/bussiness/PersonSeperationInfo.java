package service.transaction.bussiness;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PersonSeperationInfo")
@XmlType(name = "PersonSeperationInfo", propOrder = { "transactionId",
		"personCode", "orgPersonCode", "matchType", "loginName" })
public class PersonSeperationInfo {
	private String transactionId;
	private String personCode;
	private String orgPersonCode;
	private String matchType;
	private String loginName;
	public String getOrgPersonCode() {
		return orgPersonCode;
	}

	public void setOrgPersonCode(String orgPersonCode) {
		this.orgPersonCode = orgPersonCode;
	}
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
