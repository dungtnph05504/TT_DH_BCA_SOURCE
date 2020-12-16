package service.transaction.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="HandoverC")
@XmlType(name="HandoverC", propOrder={"packageId","type","approveName","idQueue","handovers"})
public class HandoverC {
	private String packageId;
	private String type;
	private String approveName;
	private Long idQueue;
	private List<DetailHandoverC> handovers;
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	public List<DetailHandoverC> getHandovers() {
		return handovers;
	}
	public void setHandovers(List<DetailHandoverC> handovers) {
		this.handovers = handovers;
	}
	public Long getIdQueue() {
		return idQueue;
	}
	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}
	
}
