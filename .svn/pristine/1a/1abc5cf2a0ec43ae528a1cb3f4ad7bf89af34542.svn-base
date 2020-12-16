package com.nec.asia.nic.comp.immihistory.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryImages;
import com.nec.asia.nic.util.DateHandler;


@XmlRootElement(name="ImmihistoryImage")
@XmlType(name="ImmihistoryImage", propOrder={"createdBy", "lastModifiedBy", "createdTime", "lastModifiedTime", "type", "data"})
public class ImmihistoryImage {
	
  
   
    private String createdBy;
   
    private String lastModifiedBy;
    @JsonSerialize(using=DateHandler.class)
    private Date createdTime;
    @JsonSerialize(using=DateHandler.class)
    private Date lastModifiedTime;
    
  
    private String type;
    
    private byte[] data;
    
    

	public ImmihistoryImage() {
		super();
	}
	
	public ImmihistoryImage(ImmiHistoryImages img) {
		
		this.createdBy = img.getCreatedBy();
		this.lastModifiedBy = img.getLastModifiedBy();
		this.createdTime = img.getCreatedTime();
		this.lastModifiedTime = img.getLastModifiedTime();
		
		this.type = img.getType();
		this.data = img.getData();
	}




	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
    
    
}
