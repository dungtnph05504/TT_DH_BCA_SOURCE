package service.personic.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="InventoryStatus")
@XmlType(name="InventoryStatus", propOrder = {"id","chipSeriesNo", "docChars", "docNum", "status"})
public class InventoryStatus {
	private Long id;
	private String chipSeriesNo;
	private String docChars;
	private String docNum;
	private String status;
	
	public String getChipSeriesNo() {
		return chipSeriesNo;
	}
	public void setChipSeriesNo(String chipSeriesNo) {
		this.chipSeriesNo = chipSeriesNo;
	}
	public String getDocChars() {
		return docChars;
	}
	public void setDocChars(String docChars) {
		this.docChars = docChars;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
