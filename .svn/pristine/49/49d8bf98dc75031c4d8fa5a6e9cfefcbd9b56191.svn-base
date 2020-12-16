package com.nec.asia.nic.comp.trans.dto;

import org.apache.commons.lang.StringUtils;

import com.nec.asia.nic.comp.trans.domain.EppInventoryItems;
import com.nec.asia.nic.comp.trans.domain.EppInventoryItemsDetail;
import com.nec.asia.nic.utils.HelperClass;

public class InventoryDto {
	private Integer stt;
	private String batchNo;
	private String chipNo;
	private String docChar;
	private String docNum;
	private String status;
	private String invCode;
	private String invName;
	private String itemStr;
	private String handoverNo;
	private String handoverName;
	private String receiptDate;
	private String receiptName;
	private Long item;
	private String category;
	
	
	public InventoryDto() {
		super();
	}
	
	public InventoryDto(Integer stt, String batchNo, String chipNo,
			String docChar, String docNum, String status, String invCode,
			String invName, String itemStr, Long item) {
		super();
		this.stt = stt;
		this.batchNo = batchNo;
		this.chipNo = chipNo;
		this.docChar = docChar;
		this.docNum = docNum;
		this.status = status;
		this.invCode = invCode;
		this.invName = invName;
		this.itemStr = itemStr;
		this.item = item;
	}

	public InventoryDto(EppInventoryItemsDetail epp) {
		
		this.batchNo = epp.getBatchNo();
		this.chipNo = epp.getChipSeriesNo();
		this.docChar = epp.getDocChars();
		this.docNum = epp.getDocNum();
		this.status = epp.getStatus();
		this.itemStr = epp.getInventoryItemsId() + "";
		this.item = epp.getId();
	}
	
	
	public InventoryDto(EppInventoryItems epp) {
		
		this.itemStr = epp.getInventoryId() + "";
		this.handoverNo = epp.getHandoverNo();
		String day = epp.getRecieptDate();
		if(StringUtils.isNotEmpty(day)){
			String dayFomat = HelperClass.convertStringToStringTk(day, 0);
			this.receiptDate = dayFomat;
		}
		this.category = epp.getCategoryId() + "";
		this.receiptName = epp.getRecieptName();
		this.handoverName = epp.getHandoverName();
		this.batchNo = epp.getBatchNo();
		this.item = epp.getId();
	}
	
	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getReceiptName() {
		return receiptName;
	}

	public void setReceiptName(String receiptName) {
		this.receiptName = receiptName;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public String getHandoverName() {
		return handoverName;
	}

	public void setHandoverName(String handoverName) {
		this.handoverName = handoverName;
	}

	public Integer getStt() {
		return stt;
	}
	public void setStt(Integer stt) {
		this.stt = stt;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getChipNo() {
		return chipNo;
	}
	public void setChipNo(String chipNo) {
		this.chipNo = chipNo;
	}
	public String getDocChar() {
		return docChar;
	}
	public void setDocChar(String docChar) {
		this.docChar = docChar;
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
	public String getInvCode() {
		return invCode;
	}
	public void setInvCode(String invCode) {
		this.invCode = invCode;
	}
	public String getInvName() {
		return invName;
	}
	public void setInvName(String invName) {
		this.invName = invName;
	}
	public Long getItem() {
		return item;
	}
	public void setItem(Long item) {
		this.item = item;
	}
	public String getItemStr() {
		return itemStr;
	}
	public void setItemStr(String itemStr) {
		this.itemStr = itemStr;
	}
	
	
}
