package service.transaction.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="HandoverPA")
@XmlType(name="HandoverPA", propOrder={"handovers"})
public class HandoverPA {
	private List<DetailHandover> handovers;

	public List<DetailHandover> getHandovers() {
		return handovers;
	}

	public void setHandovers(List<DetailHandover> handovers) {
		this.handovers = handovers;
	}
	
	
}
