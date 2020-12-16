package service.transaction.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import service.transaction.model.PersonFamily;

@XmlRootElement(name="InfoFamilys")
@XmlAccessorType(XmlAccessType.FIELD)
public class InfoFamilys {
	
	@XmlElement(name="PersonFamily")
	private List<PersonFamily> sonFamilies;

	public List<PersonFamily> getSonFamilies() {
		return sonFamilies;
	}

	public void setSonFamilies(List<PersonFamily> sonFamilies) {
		this.sonFamilies = sonFamilies;
	}
	
}
