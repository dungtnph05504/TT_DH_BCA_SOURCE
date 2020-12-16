package service.perso.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="EppPersonsDto")
@XmlType ( name = "EppPersonsDto",  propOrder = { "epp_person"} )
public class EppPersonsDto {
	private List<EppPersonDto> epp_person = new ArrayList<EppPersonDto>();

	public EppPersonsDto(){
		
	}
	
	public List<EppPersonDto> getEpp_person() {
		return epp_person;
	}

	public void setEpp_person(List<EppPersonDto> epp_person) {
		this.epp_person = epp_person;
	}
	
}
