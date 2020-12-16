package service.master.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="FeeInfo")
@XmlType(name="FeeInfo", propOrder={"codeFee","nameFee","feeAmount","typePayment","a08Id","deleteFlag"})
public class FeeInfo {
	private String codeFee;
	private String nameFee;
	private Double feeAmount;
	private String typePayment;
	private Long a08Id;
	private String deleteFlag;
	public String getCodeFee() {
		return codeFee;
	}
	public void setCodeFee(String codeFee) {
		this.codeFee = codeFee;
	}
	public String getNameFee() {
		return nameFee;
	}
	public void setNameFee(String nameFee) {
		this.nameFee = nameFee;
	}
	
	public Double getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getTypePayment() {
		return typePayment;
	}
	public void setTypePayment(String typePayment) {
		this.typePayment = typePayment;
	}
	public Long getA08Id() {
		return a08Id;
	}
	public void setA08Id(Long a08Id) {
		this.a08Id = a08Id;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
}
