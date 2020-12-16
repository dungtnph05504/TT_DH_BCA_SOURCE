package service.perso.model;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="StatusHandover")
@XmlType ( name = "StatusHandover",  propOrder = { "handoverId","status" } )

public class StatusHandover {
	private String handoverId;
	private Boolean status;
	
	public String getHandoverId() {
		return handoverId;
	}
	public void setHandoverId(String handoverId) {
		this.handoverId = handoverId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
