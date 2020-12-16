package service.transaction.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="PaymentDetail")
@XmlType(name="PaymentDetail", propOrder={"transactionId", "typePayment", "subTypePayment", "paymentAmount", "namePayment", "statusFee"})
public class PaymentDetail {
	private String transactionId;
	private String typePayment;
	private String subTypePayment;
	private Double paymentAmount;
	private String namePayment;
	private String statusFee;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTypePayment() {
		return typePayment;
	}
	public void setTypePayment(String typePayment) {
		this.typePayment = typePayment;
	}
	public String getSubTypePayment() {
		return subTypePayment;
	}
	public void setSubTypePayment(String subTypePayment) {
		this.subTypePayment = subTypePayment;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getNamePayment() {
		return namePayment;
	}
	public void setNamePayment(String namePayment) {
		this.namePayment = namePayment;
	}
	public String getStatusFee() {
		return statusFee;
	}
	public void setStatusFee(String statusFee) {
		this.statusFee = statusFee;
	}
	
	
}
