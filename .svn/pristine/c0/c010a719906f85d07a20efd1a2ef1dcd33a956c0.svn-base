package service.personic.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="InfoStatus")
@XmlType(name="InfoStatus", propOrder = {"status", "invStatus"})
public class InfoStatus {
	private List<SyncStatus> status;
	private List<InventoryStatus> invStatus;

	public List<SyncStatus> getStatus() {
		return status;
	}

	public void setStatus(List<SyncStatus> status) {
		this.status = status;
	}

	public List<InventoryStatus> getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(List<InventoryStatus> invStatus) {
		this.invStatus = invStatus;
	}
	
	
}
