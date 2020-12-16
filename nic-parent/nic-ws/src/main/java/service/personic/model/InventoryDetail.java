package service.personic.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.comp.trans.domain.EppInventoryItemsDetail;

@XmlRootElement(name="InventoryItemsDetail")
@XmlType(name="InventoryItemsDetail", propOrder={"id", "inventoryItemsId", "updateTs", "updatedBy", "status", "batchNo", "chipSeriesNo", "docChars", "docNum", "issuedDate"})
public class InventoryDetail {
	
	private Long id;
	private Long inventoryItemsId;
	private Date updateTs;
	private String updatedBy;
	private String status;
	private String batchNo;
	private String chipSeriesNo;
	private String docChars;
	private String docNum;
	private String issuedDate;
	
	
	public InventoryDetail() {
		super();
	}
	
	public InventoryDetail(EppInventoryItemsDetail item) {
		this.id = item.getId();
		this.inventoryItemsId = item.getInventoryItemsId();
		this.updateTs = item.getUpdateTs();
		this.updatedBy = item.getUpdatedBy();
		this.status = item.getStatus();
		this.batchNo = item.getBatchNo();
		this.chipSeriesNo = item.getChipSeriesNo();
		this.docChars = item.getDocChars();
		this.docNum = item.getDocNum();
		this.issuedDate = item.getIssuedDate();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInventoryItemsId() {
		return inventoryItemsId;
	}
	public void setInventoryItemsId(Long inventoryItemsId) {
		this.inventoryItemsId = inventoryItemsId;
	}
	public Date getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
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
	public String getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}
	
	
}
