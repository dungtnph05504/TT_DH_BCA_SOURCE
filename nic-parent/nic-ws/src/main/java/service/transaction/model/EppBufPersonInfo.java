package service.transaction.model;


import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="EppBufPersonInfo")
@XmlType(name="EppBufPersonInfo", propOrder={"transacionId", "dataImmihistory", "dataHistoryPassport", "dataFamily", "infoPerson", "idMaster"})
public class EppBufPersonInfo {
	
    private String transacionId;
    
    private String dataImmihistory;//xml thông tin lịch xử xuất nhập cảnh
   
    private String dataHistoryPassport;//xml thông tin lịch sử cấp phát hộ chiếu
   
    private String dataFamily;//xml thông tin bố, mẹ, vợ/chồng, con cái
    
    private String infoPerson; //xml thông tin con cái
    
    private String idMaster;

	public String getTransacionId() {
		return transacionId;
	}

	public void setTransacionId(String transacionId) {
		this.transacionId = transacionId;
	}

	public String getDataImmihistory() {
		return dataImmihistory;
	}

	public void setDataImmihistory(String dataImmihistory) {
		this.dataImmihistory = dataImmihistory;
	}

	public String getDataHistoryPassport() {
		return dataHistoryPassport;
	}

	public void setDataHistoryPassport(String dataHistoryPassport) {
		this.dataHistoryPassport = dataHistoryPassport;
	}

	public String getDataFamily() {
		return dataFamily;
	}

	public void setDataFamily(String dataFamily) {
		this.dataFamily = dataFamily;
	}

	public String getInfoPerson() {
		return infoPerson;
	}

	public void setInfoPerson(String infoPerson) {
		this.infoPerson = infoPerson;
	}

	public String getIdMaster() {
		return idMaster;
	}

	public void setIdMaster(String idMaster) {
		this.idMaster = idMaster;
	}
    
    
    
}
