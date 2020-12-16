package service.perso.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="HanvdoverRequest")
@XmlType ( name = "HanvdoverRequest",  propOrder = { "handoverId","status" } )
public class HanvdoverRequest {
	
	private String handoverId;
	private int status;
	
	public HanvdoverRequest() {
		super();
	}

	public String getHandoverId() {
		return handoverId;
	}
	
	public void setHandoverId(String handoverId) {
		this.handoverId = handoverId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	 
	
}
