package service.perso.model.sync;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ResponseFromA72")
@XmlType ( name = "ResponseFromA72",  propOrder = { "passport" } )
public class ResponseFromA72 {

	private List<PassportA72> passport = new ArrayList<PassportA72>();

	public ResponseFromA72(){
		
	}
	
	public ResponseFromA72(List<PassportA72> passport) {
		super();
		this.passport = passport;
	}

	public List<PassportA72> getPassport() {
		return passport;
	}

	public void setPassport(List<PassportA72> passport) {
		this.passport = passport;
	}
	
	
}
