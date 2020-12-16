package service.master.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="MasterInfos")
@XmlType(name="MasterInfos", propOrder={"listInfo"})
public class MasterInfos {
	private List<MasterInfo> listInfo;

	public List<MasterInfo> getListInfo() {
		return listInfo;
	}

	public void setListInfo(List<MasterInfo> listInfo) {
		this.listInfo = listInfo;
	}
	
	
}
