package service.perso.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.framework.admin.rbac.domain.Functions;

@XmlRootElement(name="UserData")
@XmlType ( name = "UserData",  propOrder = { "lstfunc", "lstrole", "lstuser", "lstwkst" } )

public class UserData {
	private List<FunctionsJson> lstfunc = new ArrayList<FunctionsJson>();
	private List<RolesJson> lstrole = new ArrayList<RolesJson>();
	private List<UsersJson> lstuser = new ArrayList<UsersJson>();
	private List<WorkstationsJson> lstwkst = new ArrayList<WorkstationsJson>();
	
	public List<FunctionsJson> getLstfunc() {
		return lstfunc;
	}
	public void setLstfunc(List<FunctionsJson> lstfunc) {
		this.lstfunc = lstfunc;
	}
	public List<RolesJson> getLstrole() {
		return lstrole;
	}
	public void setLstrole(List<RolesJson> lstrole) {
		this.lstrole = lstrole;
	}
	public List<UsersJson> getLstuser() {
		return lstuser;
	}
	public void setLstuser(List<UsersJson> lstuser) {
		this.lstuser = lstuser;
	}
	public List<WorkstationsJson> getLstwkst() {
		return lstwkst;
	}
	public void setLstwkst(List<WorkstationsJson> lstwkst) {
		this.lstwkst = lstwkst;
	}

	
}
