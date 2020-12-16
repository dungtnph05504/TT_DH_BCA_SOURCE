package service.immihistory.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="BaseListRequest")
@XmlType(name="BaseListRequest", propOrder={"data", "gateCode"})
public class BaseListRequest<T> {
	private List<T> data = new ArrayList<T>();
	
	private String gateCode;
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public String getGateCode() {
		return gateCode;
	}
	public void setGateCode(String gateCode) {
		this.gateCode = gateCode;
	}
	
	
}
