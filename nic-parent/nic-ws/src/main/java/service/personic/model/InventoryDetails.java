package service.personic.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="InventoryDetails")
@XmlType(name="InventoryDetails", propOrder={"inventorys"})
public class InventoryDetails {
	private List<InventoryDetail> inventorys;

	public List<InventoryDetail> getInventorys() {
		return inventorys;
	}

	public void setInventorys(List<InventoryDetail> inventorys) {
		this.inventorys = inventorys;
	}
	
	
}
