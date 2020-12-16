package service.investigation.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ResponseDownloadBuff")
@XmlType(name = "ResponseDownloadBuff", propOrder = { "listData",
		"transactionCode", "buffType", "idQueue" })
public class ResponseDownloadBuff {

	private List<DataPersonBuff> listData;
	private String transactionCode;
	private String buffType;
	private Long idQueue;

	public Long getIdQueue() {
		return idQueue;
	}

	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}

	public String getBuffType() {
		return buffType;
	}

	public void setBuffType(String buffType) {
		this.buffType = buffType;
	}

	public List<DataPersonBuff> getListData() {
		return listData;
	}

	public void setListData(List<DataPersonBuff> listData) {
		this.listData = listData;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

}
