package service.perso.model;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="RequestDataSpecial")
@XmlType ( name = "RequestDataSpecial",  propOrder = { "transactionId", "page" } )

public class RequestDataSpecial {
	private String transactionId;
	private String page;
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
	public String getTransactionId() {
		return transactionId;
	}


	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}
	
	
}
