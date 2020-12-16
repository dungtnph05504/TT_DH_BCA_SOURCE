package service.master.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="FeeInfos")
@XmlType(name="FeeInfos", propOrder={"listInfo"})
public class FeeInfos {
	private List<FeeInfo> listInfo;

	public List<FeeInfo> getListInfo() {
		return listInfo;
	}

	public void setListInfo(List<FeeInfo> listInfo) {
		this.listInfo = listInfo;
	}
	
}
