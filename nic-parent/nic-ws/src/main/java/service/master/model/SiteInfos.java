package service.master.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="listInfo")
@XmlType(name="listInfo", propOrder={"listInfo"})
public class SiteInfos {
	private List<SiteInfo> listInfo;

	public List<SiteInfo> getListInfo() {
		return listInfo;
	}

	public void setListInfo(List<SiteInfo> listInfo) {
		this.listInfo = listInfo;
	}
	
}
