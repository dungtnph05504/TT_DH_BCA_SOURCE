package service.immihistory.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="AdminsStatus")
@XmlType(name="AdminsStatus", propOrder={"status"})
public class AdminsStatus {
	private List<AdminStatus> status;

	public List<AdminStatus> getStatus() {
		return status;
	}

	public void setStatus(List<AdminStatus> status) {
		this.status = status;
	}
	
	
}
