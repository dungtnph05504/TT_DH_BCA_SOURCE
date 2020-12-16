package com.nec.asia.nic.util;

public class FileAttachmentApi {
	
	private Long ID;
	private Long BLACKLIST_ID;
	private Long IDENTIFICATION_ID;

	private String TYPE_;
	private String FILE_NAME;
	private String BASE64;
	
	public FileAttachmentApi(){
		
	}
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public Long getBLACKLIST_ID() {
		return BLACKLIST_ID;
	}
	public void setBLACKLIST_ID(Long bLACKLIST_ID) {
		BLACKLIST_ID = bLACKLIST_ID;
	}
	public String getTYPE_() {
		return TYPE_;
	}
	public void setTYPE_(String tYPE_) {
		TYPE_ = tYPE_;
	}
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	public String getBASE64() {
		return BASE64;
	}
	public void setBASE64(String bASE64) {
		BASE64 = bASE64;
	}
	
	public Long getIDENTIFICATION_ID() {
		return IDENTIFICATION_ID;
	}

	public void setIDENTIFICATION_ID(Long iDENTIFICATION_ID) {
		IDENTIFICATION_ID = iDENTIFICATION_ID;
	}
}
