package service.transaction.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="HandoverApprove")
@XmlType(name="HandoverApprove", propOrder={"siteCode", "idQueue", "handovers"})
public class HandoverApprove {
	private List<DetailHandover> handovers;
	private Long idQueue;
	private String siteCode;
	
	public List<DetailHandover> getHandovers() {
		return handovers;
	}

	public void setHandovers(List<DetailHandover> handovers) {
		this.handovers = handovers;
	}

	public Long getIdQueue() {
		return idQueue;
	}

	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	
	
}
