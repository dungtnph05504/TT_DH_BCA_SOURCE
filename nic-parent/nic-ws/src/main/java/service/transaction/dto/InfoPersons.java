package service.transaction.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="InfoPersons")
@XmlAccessorType(XmlAccessType.FIELD)
public class InfoPersons {
	
	@XmlElement(name="InfoPerson")
	private List<InfoPerson> infoPersons = null;
	public InfoPersons() {
		super();
	}
	public List<InfoPerson> getInfoPersons() {
		return infoPersons;
	}
	public void setInfoPersons(List<InfoPerson> infoPersons) {
		this.infoPersons = infoPersons;
	}
}
