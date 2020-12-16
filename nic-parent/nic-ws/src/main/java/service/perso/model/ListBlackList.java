package service.perso.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.comp.trans.domain.EppBlacklist;

@XmlRootElement(name="ListBlackList")
@XmlType ( name = "ListBlackList",  propOrder = { "listBl" } )

public class ListBlackList {
	private List<FullBlackList> listBl = new ArrayList<FullBlackList>();

	public List<FullBlackList> getListBl() {
		return listBl;
	}

	public void setListBl(List<FullBlackList> listBl) {
		this.listBl = listBl;
	}
}
