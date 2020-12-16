package service.perso.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.framework.admin.rbac.domain.Functions;

@XmlRootElement(name="MappingAuthenData")
@XmlType ( name = "MappingAuthenData",  propOrder = { "rolesUser", "rolesWorkstation", "functionsRole" } )

public class MappingAuthenData {
	private List<String> rolesUser = new ArrayList<String>();
	private List<String> rolesWorkstation = new ArrayList<String>();
	private List<String> functionsRole = new ArrayList<String>();
	
	public List<String> getRolesUser() {
		return rolesUser;
	}
	public void setRolesUser(List<String> rolesUser) {
		this.rolesUser = rolesUser;
	}
	public List<String> getRolesWorkstation() {
		return rolesWorkstation;
	}
	public void setRolesWorkstation(List<String> rolesWorkstation) {
		this.rolesWorkstation = rolesWorkstation;
	}
	public List<String> getFunctionsRole() {
		return functionsRole;
	}
	public void setFunctionsRole(List<String> functionsRole) {
		this.functionsRole = functionsRole;
	}
	
	
}
