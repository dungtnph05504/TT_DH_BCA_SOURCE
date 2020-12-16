package service.perso.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppIdentification;

@XmlRootElement(name="ListIdentification")
@XmlType ( name = "ListIdentification",  propOrder = { "listID" } )

public class ListIdentification {
	private List<FullIdentification> listID = new ArrayList<FullIdentification>();

	public List<FullIdentification> getListID() {
		return listID;
	}

	public void setListID(List<FullIdentification> listID) {
		this.listID = listID;
	}
}
