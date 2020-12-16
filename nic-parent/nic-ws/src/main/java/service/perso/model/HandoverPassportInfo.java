package service.perso.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "HandoverPassportInfo")
@XmlType ( name = "HandoverPassportInfo",  propOrder = { "handoverId","listPassportInfo", "createDate", "createBy", "amountDoc", "idQueue"} )
public class HandoverPassportInfo {
	private String handoverId;
	private String createDate;
	private String createBy;
	private int amountDoc;
	private Long idQueue;
    private List<PassportInfo> listPassportInfo = new ArrayList<PassportInfo>();

    
    public HandoverPassportInfo() {
    }

	public HandoverPassportInfo(String handoverId, String createDate,
			String createBy, int amountDoc, List<PassportInfo> listPassportInfo) {
		this.handoverId = handoverId;
		this.createDate = createDate;
		this.createBy = createBy;
		this.amountDoc = amountDoc;
		this.listPassportInfo = listPassportInfo;
	}

	public String getHandoverId() {
		return handoverId;
	}

	public void setHandoverId(String handoverId) {
		this.handoverId = handoverId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public int getAmountDoc() {
		return amountDoc;
	}

	public void setAmountDoc(int amountDoc) {
		this.amountDoc = amountDoc;
	}

	public List<PassportInfo> getListPassportInfo() {
		return listPassportInfo;
	}

	public void setListPassportInfo(List<PassportInfo> listPassportInfo) {
		this.listPassportInfo = listPassportInfo;
	}

	public Long getIdQueue() {
		return idQueue;
	}

	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}
	
	
}
